/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB.controller;
import EJB.HistorialFacadeLocal;
import EJB.IngresoFacadeLocal;
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
import modelo.Historial;
import modelo.Mascota;
import org.primefaces.model.FilterMeta;
import org.primefaces.util.LangUtils;

/**
 *
 * @author andre
 */
@Named
@RequestScoped
public class VistaHistorialController implements Serializable{
    
    @Inject
    private Cliente cli;
    @Inject
    private Mascota pet;
    @Inject
    private Historial caso;
    @EJB
    private IngresoFacadeLocal ingresoEJB;
    
    @EJB
    private HistorialFacadeLocal historialEJB;
    
    private List<Historial> listaHistorial;
    private List<Historial> historialFiltrado;
    private List<FilterMeta> filtrarPor;
    private String tratamientosStr;
    
    @PostConstruct
    public void init(){
        listaHistorial = historialEJB.findAll();
        filtrarPor = new ArrayList<>();
    }
    
    public boolean filtroGlobal(Object valor, Object filtro, Locale locale) {
        String textoFiltro = (filtro == null) ? null : filtro.toString().trim().toUpperCase();
        if (LangUtils.isBlank(textoFiltro)) {
            return true;
        }

        Historial caso = (Historial) valor;
        return caso.getMascota().getNombre().toUpperCase().contains(textoFiltro)
                || caso.getVeterinario().getNombre().toUpperCase().contains(textoFiltro)
                || caso.getMascota().getPropietario().getNombre().toUpperCase().contains(textoFiltro)
                || caso.getDiagnostico().toUpperCase().contains(textoFiltro)
                || String.valueOf(caso.getIdCaso()).contains(textoFiltro);
    }

    public void eliminar(){
        listaHistorial.remove(caso);
        historialEJB.remove(caso);
        ingresoEJB.remove(caso.getIngreso());
        caso = null;
    }

    public String getTratamientosStr() {
        return tratamientosStr;
    }

    public void setTratamientosStr(String tratamientosStr) {
        this.tratamientosStr = tratamientosStr;
    }

    public Cliente getCli() {
        return cli;
    }

    public void setCli(Cliente cli) {
        this.cli = cli;
    }

    public Mascota getPet() {
        return pet;
    }

    public void setPet(Mascota pet) {
        this.pet = pet;
    }

    public List<FilterMeta> getFiltrarPor() {
        return filtrarPor;
    }

    public void setFiltrarPor(List<FilterMeta> filtrarPor) {
        this.filtrarPor = filtrarPor;
    }

    public Historial getCaso() {
        return caso;
    }

    public void setCaso(Historial caso) {
        this.caso = caso;
    }

    public List<Historial> getListaHistorial() {
        return listaHistorial;
    }

    public void setListaHistorial(List<Historial> listaHistorial) {
        this.listaHistorial = listaHistorial;
    }

    public List<Historial> getHistorialFiltrado() {
        return historialFiltrado;
    }

    public void setHistorialFiltrado(List<Historial> historialFiltrado) {
        this.historialFiltrado = historialFiltrado;
    }
}
