/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB.controller;

import EJB.FacturaFacadeLocal;
import EJB.HistorialFacadeLocal;
import EJB.VentaFacadeLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import modelo.Cliente;
import modelo.Factura;
import modelo.Historial;
import modelo.Mascota;
import modelo.Venta;
import org.primefaces.model.FilterMeta;
import org.primefaces.util.LangUtils;

/**
 *
 * @author andre
 */
@Named
@RequestScoped
public class VistaVentasController implements Serializable{
    
    @Inject
    private Venta ven;
    @Inject
    private Factura fac;
    @Inject
    private Historial caso;
    
    @EJB
    private VentaFacadeLocal ventaEJB;
    @EJB
    private FacturaFacadeLocal facturaEJB;
    @EJB
    private HistorialFacadeLocal historialEJB;
    
    private List<Venta> listaVentas;
    private List<Venta> ventasFiltradas;
    private List<FilterMeta> filtrarPor;
    private List<Historial> listaCasosPagados;
    private List<Historial> listaCasosPendientes;
    private int idCaso;
    
    @PostConstruct
    public void init(){
        listaVentas = ventaEJB.findAll();
        listaCasosPendientes = eliminarCasosPagados(historialEJB.findAll(), listaVentas);
        listaCasosPendientes = eliminarCasosAunIngresados(listaCasosPendientes);
        filtrarPor = new ArrayList<>();
    }
    
    public boolean filtroGlobal(Object valor, Object filtro, Locale locale) {
        String textoFiltro = (filtro == null) ? null : filtro.toString().trim().toUpperCase();
        if (LangUtils.isBlank(textoFiltro)) {
            return true;
        }

        Venta ven = (Venta) valor;
        String propietario = ven.getConsulta().getMascota().getPropietario().getNombre()+" "+ven.getConsulta().getMascota().getPropietario().getApellidos();
        return String.valueOf(ven.getConsulta().getIdCaso()).contains(textoFiltro)
                || ven.getConsulta().getMascota().getNombre().toUpperCase().contains(textoFiltro)
                || propietario.toUpperCase().contains(textoFiltro)
                || ven.getFactura().getPorUsuario().getNombre().toUpperCase().contains(textoFiltro);
    }

    public void eliminar(){
        listaVentas.remove(ven);
        ventaEJB.remove(ven);
        ven = null;
    }

    public List<FilterMeta> getFiltrarPor() {
        return filtrarPor;
    }

    public void setFiltrarPor(List<FilterMeta> filtrarPor) {
        this.filtrarPor = filtrarPor;
    }

    public Factura getFac() {
        return fac;
    }

    public void setFac(Factura fac) {
        this.fac = fac;
    }

    public Historial getCaso() {
        return caso;
    }

    public void setCaso(Historial caso) {
        this.caso = caso;
    }

    public List<Venta> getListaVentas() {
        return listaVentas;
    }

    public void setListaVentas(List<Venta> listaVentas) {
        this.listaVentas = listaVentas;
    }

    public List<Venta> getVentasFiltradas() {
        return ventasFiltradas;
    }

    public void setVentasFiltradas(List<Venta> ventasFiltradas) {
        this.ventasFiltradas = ventasFiltradas;
    }

    public Venta getVen() {
        return ven;
    }

    public void setVen(Venta ven) {
        this.ven = ven;
    }

    public List<Historial> getListaCasosPagados() {
        return listaCasosPagados;
    }

    public void setListaCasosPagados(List<Historial> listaCasosPagados) {
        this.listaCasosPagados = listaCasosPagados;
    }

    public List<Historial> getListaCasosPendientes() {
        return listaCasosPendientes;
    }

    public void setListaCasosPendientes(List<Historial> listaCasosPendientes) {
        this.listaCasosPendientes = listaCasosPendientes;
    }
    
    private List<Historial> eliminarCasosPagados(List<Historial> listaCasos, List<Venta> listaVentas){
        for(Venta v : listaVentas)
            listaCasos.remove(v.getConsulta());
        return listaCasos;
    }
    
    private List<Historial> eliminarCasosAunIngresados(List<Historial> listaCasos){
        List<Historial> aunIngresados = new ArrayList<>(listaCasos);
        for(Historial c : listaCasos)
            if(c.getIngreso() != null && c.getIngreso().getFechaAlta() == null)
                aunIngresados.remove(c);
        return aunIngresados;
    }

    public int getIdCaso() {
        return idCaso;
    }

    public void setIdCaso(int idCaso) {
        this.idCaso = idCaso;
    }
    
    public String establecerCaso(int idCaso){
        for(Historial c : listaCasosPendientes)
            if(c.getIdCaso() == idCaso)
                caso = c;
        return "altaVenta";
    }
    
}
