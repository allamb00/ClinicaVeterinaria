/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB.controller;

import EJB.ClienteFacadeLocal;
import EJB.MascotaFacadeLocal;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import modelo.Cliente;
import modelo.Mascota;

/**
 *
 * @author andre
 */
 @Named
 @ViewScoped
public class AltaMascotaController implements Serializable{
    @Inject
    private Cliente cli;
    
    @Inject 
    private Mascota pet;
    
    @EJB
    private ClienteFacadeLocal clienteEJB;
    
    @EJB
    private MascotaFacadeLocal mascotaEJB;
    
    private List<Cliente> listaClientes;
    private boolean esMacho;
    private int idCliente;

    
    @PostConstruct
    public void init(){
        listaClientes = clienteEJB.findAll();
    }
    /**
     * Inserta el cliente especificado en altaCliente.xhtml
     */
    public String insertarMascota(){
        String navegacion = null;
        try{
            setSexo(pet);
            setPropietario(pet);
            pet.setFechaRegistro(new Date());
            mascotaEJB.create(pet);
            FacesContext.getCurrentInstance().addMessage(null, 
                            new FacesMessage(
                            FacesMessage.SEVERITY_INFO,
                            "Creación de mascota",
                            "Se ha añadido el mascota a la tabla"));
            navegacion = "vistaMascotas";
        }catch(Exception ex){
            System.out.println("Error al insertar la mascota en la base de datos: " + ex);
            FacesContext.getCurrentInstance().addMessage(null, 
                            new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            "Creación de mascota",
                            "Ha ocurrido un error al crear la mascota"));
        }
        return navegacion;
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

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
    
    public boolean isEsMacho() {
        return esMacho;
    }

    public void setEsMacho(boolean esMacho) {
        this.esMacho = esMacho;
    }

    public List<Cliente> getListaClientes() {
        return listaClientes;
    }

    public void setListaClientes(List<Cliente> listaClientes) {
        this.listaClientes = listaClientes;
    }
    
    private void setPropietario(Mascota pet) {
        for(Cliente c : listaClientes)
            if(c.getIdCliente() == idCliente)
                pet.setPropietario(c);
    }
    
    private void setSexo(Mascota pet) {
        if(esMacho)
            pet.setSexo("M");
        else
            pet.setSexo("H");
    }
    
}
