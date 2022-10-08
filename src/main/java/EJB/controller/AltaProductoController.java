/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB.controller;

import EJB.InventarioFacadeLocal;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import modelo.Inventario;

/**
 *
 * @author andre
 */
@Named
@ViewScoped
public class AltaProductoController implements Serializable{
    @Inject
    private Inventario pro;
    
    @EJB
    private InventarioFacadeLocal inventarioEJB;
    
    private int nCantidad;
    
    @PostConstruct
    public void init(){
        
    }
    /**
     * Inserta el cliente especificado en altaCliente.xhtml
     */
    public String insertarProducto(){
        String navegacion = null;
        try{
            for(int n = 0; n < nCantidad; n++)
                inventarioEJB.create(pro);
            FacesContext.getCurrentInstance().addMessage(null, 
                            new FacesMessage(
                            FacesMessage.SEVERITY_INFO,
                            "Registro de producto",
                            "Se ha añadido el producto a la tabla"));
            navegacion = "vistaInventario";
        }catch(Exception ex){
            System.out.println("Error al añadir el producto en la base de datos: " + ex);
            FacesContext.getCurrentInstance().addMessage(null, 
                            new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            "Registro de producto",
                            "Ha ocurrido un error al añadir el producto"));
        }
        return navegacion;
    }

    public Inventario getPro() {
        return pro;
    }

    public void setPro(Inventario pro) {
        this.pro = pro;
    }

    public int getnCantidad() {
        return nCantidad;
    }

    public void setnCantidad(int nCantidad) {
        this.nCantidad = nCantidad;
    }
    
}
