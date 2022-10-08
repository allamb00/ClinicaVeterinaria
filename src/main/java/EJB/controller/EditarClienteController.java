/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB.controller;

import EJB.ClienteFacadeLocal;
import com.mycompany.clinicaveterinaria.DNI;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import modelo.Cliente;

/**
 *
 * @author andre
 */
@Named
@ViewScoped
public class EditarClienteController  implements Serializable{
    
    @Inject
    private Cliente cli;
    
    @Inject
    private VistaClientesController listCliCon;
    
    @EJB
    private ClienteFacadeLocal clienteEJB;
    
    @PostConstruct
    public void init() { 
        cli = listCliCon.getCli();
    }
    
    public String editarCliente(){
        String navegacion = null;        
        try{
            if(DNI.validar(cli.getDni())) {
                clienteEJB.edit(cli);
                System.out.println("Cliente editado");
                navegacion = "vistaClientes";
                FacesContext.getCurrentInstance().addMessage(null, 
                                new FacesMessage(
                                FacesMessage.SEVERITY_INFO,
                                "Se ha editado el cliente",
                                "Edición correcta"));
            } else
                FacesContext.getCurrentInstance().addMessage(null, 
                                new FacesMessage(
                                FacesMessage.SEVERITY_ERROR,
                                "Creación de usuario",
                                "El DNI no es correcto"));                 
        }catch(Exception ex){
            System.out.println("Error al consultar el cliente en la base de datos: " + ex);
            FacesContext.getCurrentInstance().addMessage(null, 
                            new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            "Error de edición",
                            "Ha ocurrido un error"));
        }
        return navegacion;
    }

    public Cliente getCli() {
        return cli;
    }

    public void setCli(Cliente cli) {
        this.cli = cli;
    }
    
}
