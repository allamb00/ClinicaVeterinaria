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
public class EditarProductoController  implements Serializable{
    
    @Inject
    private Inventario pro;
    
    @Inject
    private VistaInventarioController listProCon;
    
    @EJB
    private InventarioFacadeLocal inventarioEJB;
    
    @PostConstruct
    public void init() { 
        pro = listProCon.getPro();
    }
    
    public String editarProducto(){
        String navegacion = null;        
        try{
            inventarioEJB.edit(pro);
            System.out.println("Producto editado");
            navegacion = "vistaInventario";
            FacesContext.getCurrentInstance().addMessage(null, 
                            new FacesMessage(
                            FacesMessage.SEVERITY_INFO,
                            "Se ha editado el producto",
                            "Edición correcta"));
        }catch(Exception ex){
            System.out.println("Error al consultar el producto en la base de datos: " + ex);
            FacesContext.getCurrentInstance().addMessage(null, 
                            new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            "Error de edición",
                            "Ha ocurrido un error"));
        }
        return navegacion;
    }

    public Inventario getPro() {
        return pro;
    }

    public void setPro(Inventario pro) {
        this.pro = pro;
    }
    
}
