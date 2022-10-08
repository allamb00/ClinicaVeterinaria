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
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import modelo.Factura;
import modelo.Historial;
import modelo.Tarifa;
import modelo.Usuario;
import modelo.Venta;

/**
 *
 * @author andre
 */
 @Named
 @ViewScoped
public class AltaVentaController implements Serializable{
    @Inject
    private Historial caso;    
    @Inject 
    private Venta ven;
    @Inject 
    private Factura fac;
    @Inject
    private VistaVentasController vistVenCon;
    
    @EJB
    private HistorialFacadeLocal historialEJB;    
    @EJB
    private VentaFacadeLocal ventaEJB;
    @EJB
    private FacturaFacadeLocal facturaEJB;
    
    private List<Venta> listaVentas;
    private int selDescuento;
    private int iva = 21;
    private int costeTotal;

    
    @PostConstruct
    public void init(){
        caso = vistVenCon.getCaso();
        calcularFactura();
        ven.setFactura(fac);
    }
    /**
     * Inserta el cliente especificado en altaCliente.xhtml
     */
    public String insertarVenta(){
        String navegacion = null;
        try{
            setUsuario();
            setFechaCobro(new Date());
            setCaso(caso);
            setFactura(fac);
            facturaEJB.create(fac);
            ventaEJB.create(ven);
            FacesContext.getCurrentInstance().addMessage(null, 
                            new FacesMessage(
                            FacesMessage.SEVERITY_INFO,
                            "Cobro de tratamiento",
                            "Se ha registrado el cobro"));
            navegacion = "vistaVentas";
        }catch(Exception ex){
            System.out.println("Error al insertar la venta o la factura en la base de datos: " + ex);
            FacesContext.getCurrentInstance().addMessage(null, 
                            new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            "Cobro de tratamiento",
                            "Ha ocurrido un error al registrar el cobro"));
        }
        return navegacion;
    }
    
    public double costeTratamientos(){
        double coste = 0;
        for(Tarifa t : caso.getTratamientos()) {
            if(t.getCampo().getNombre().equals("HOSPITALIZACIÓN")) {
                coste += sumarHospitalizacion(t, 0);
            } else                
            coste += t.getPrecio();
        }
        return coste;
    }
    
    private double sumarHospitalizacion(Tarifa tarifaHospitalizacion, int descuento) {
        int diasIngresado = caso.getIngreso().getDiasIngreso();
        double coste = 0;
        switch(descuento) {
            case 1:
                coste = tarifaHospitalizacion.getDescAlumnos() * diasIngresado;
                break;
            case 2:
                coste = tarifaHospitalizacion.getDescProtectora() * diasIngresado;
                break;
            default:
                coste = tarifaHospitalizacion.getPrecio() * diasIngresado;
                break;
        }
        
        return coste;
    }    
    
    private LocalDate convertToLocalDate(Date dateToConvert) {
        return dateToConvert.toInstant()
          .atZone(ZoneId.systemDefault())
          .toLocalDate();
    }
    
    private void calcularFactura(){
        fac.setDescuento(descuentoTotal());
        fac.setIva(iva);
        fac.setSubtotal(subtotal());
        fac.setTotal(costeTratamientos() - fac.getDescuento());
    }
    
    private double descuentoTotal(){
        double descuentoTotal = 0;
        switch(caso.getMascota().getPropietario().getTieneDescuento()){
            case 1:
                for(Tarifa t : caso.getTratamientos())
                    if(t.getCampo().getNombre().equals("HOSPITALIZACIÓN")) {
                        descuentoTotal += sumarHospitalizacion(t, 1);
                    } else                
                    descuentoTotal += t.getDescAlumnos();
                break;
            case 2:
                for(Tarifa t : caso.getTratamientos())
                    if(t.getCampo().getNombre().equals("HOSPITALIZACIÓN")) {
                        descuentoTotal += sumarHospitalizacion(t, 2);
                    } else                
                    descuentoTotal += t.getDescProtectora();
                break;
        }
        return descuentoTotal;
    }
    
    private double subtotal(){
        return costeTratamientos() - costeTratamientos()/100*iva;
    }
    
    private void setCaso(Historial caso){
        ven.setConsulta(caso);
    }
    
    public Historial getCaso() {
        return caso;
    }

    public Venta getVen() {
        return ven;
    }

    public void setVen(Venta ven) {
        this.ven = ven;
    }

    public List<Venta> getListaVentas() {
        return listaVentas;
    }

    public void setListaVentas(List<Venta> listaVentas) {
        this.listaVentas = listaVentas;
    }
    
    private void setUsuario(){
        Usuario usuarioConectado = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");        
        fac.setPorUsuario(usuarioConectado);
    }
    
    private void setFechaCobro(Date ahora) {        
        fac.setFechaCobro(ahora);
    }
    
    private void setFactura(Factura fac) {        
        ven.setFactura(fac);
    }

    public int getSelDescuento() {
        return selDescuento;
    }

    public void setSelDescuento(int selDescuento) {
        this.selDescuento = selDescuento;
    }

    public Factura getFac() {
        return fac;
    }

    public void setFac(Factura fac) {
        this.fac = fac;
    }
    
}
