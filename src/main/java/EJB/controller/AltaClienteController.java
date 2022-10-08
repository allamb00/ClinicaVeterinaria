/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB.controller;

import EJB.ClienteFacadeLocal;
import com.mycompany.clinicaveterinaria.DNI;
import java.io.Serializable;
import java.util.Date;
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
public class AltaClienteController implements Serializable{
    @Inject
    private Cliente cli;
    
    @EJB
    private ClienteFacadeLocal clienteEJB;
    
    @PostConstruct
    public void init(){
        
    }
    /**
     * Inserta el cliente especificado en altaCliente.xhtml
     */
    public String insertarCliente(){
        String navegacion = null;
        try{
            if(DNI.validar(cli.getDni())) {
                cli.setFechaRegistro(new Date());
                clienteEJB.create(cli);
                FacesContext.getCurrentInstance().addMessage(null, 
                                new FacesMessage(
                                FacesMessage.SEVERITY_INFO,
                                "Creación de cliente",
                                "Se ha añadido el cliente a la tabla"));
                navegacion = "vistaClientes";
            } else                
                FacesContext.getCurrentInstance().addMessage(null, 
                                new FacesMessage(
                                FacesMessage.SEVERITY_ERROR,
                                "Creación de usuario",
                                "El DNI no es correcto"));   
        }catch(Exception ex){
            System.out.println("Error al insertar el cliente en la base de datos: " + ex);
            FacesContext.getCurrentInstance().addMessage(null, 
                            new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            "Creación de cliente",
                            "Ha ocurrido un error al crear el cliente"));
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
