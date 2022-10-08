/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB.controller;

import EJB.ClienteFacadeLocal;
import EJB.HistorialFacadeLocal;
import EJB.IngresoFacadeLocal;
import EJB.MascotaFacadeLocal;
import EJB.TarifaFacadeLocal;
import EJB.UsuarioFacadeLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import modelo.Cliente;
import modelo.Historial;
import modelo.Ingreso;
import modelo.Mascota;
import modelo.Tarifa;
import modelo.Usuario;

/**
 *
 * @author andre
 */
 @Named
 @ViewScoped
public class EditarHistorialController implements Serializable{
    @Inject
    private Cliente cli;    
    @Inject 
    private Mascota pet;    
    @Inject
    private Usuario vet;    
    @Inject
    private Historial caso;
    @Inject
    private Tarifa tra;
    @Inject
    private Ingreso ing;
    @Inject
    private VistaHistorialController listHistCon;
    
    @EJB
    private ClienteFacadeLocal clienteEJB;    
    @EJB
    private MascotaFacadeLocal mascotaEJB;    
    @EJB
    private UsuarioFacadeLocal usuarioEJB;    
    @EJB
    private HistorialFacadeLocal historialEJB;
    @EJB
    private TarifaFacadeLocal tratamientoEJB;
    @EJB
    private IngresoFacadeLocal ingresoEJB;
    
    private int idCliente;
    private int idMascota;
    private int idVeterinario;
    
    private List<Cliente> listaClientes;
    private List<Mascota> listaMascotas;
    private List<Usuario> listaVeterinarios;
    private List<Tarifa> listaTratamientos;
    
    private boolean esNecesarioIngreso;
    private List<Tarifa> tratamientosSeleccionados;

    
    @PostConstruct
    public void init(){
        listaClientes = clienteEJB.findAll();
        listaMascotas = mascotaEJB.findAll();
        listaVeterinarios = soloVeterinariosExistentes(usuarioEJB.findAll());
        listaTratamientos = tratamientoEJB.findAll();        
        caso = listHistCon.getCaso();
        
        esNecesarioIngreso = caso.getIngreso() != null;
        tratamientosSeleccionados = new ArrayList<>(caso.getTratamientos());
    }
    
    public String editarCaso(){
        String navegacion = null;
        try{
            setMascota();
            setTratamientos();
            setVeterinario();              
            if(esNecesarioIngreso)
                setIngreso();
            else
                if(caso.getIngreso() != null)
                    ingresoEJB.remove(caso.getIngreso());
            historialEJB.edit(caso);  
            FacesContext.getCurrentInstance().addMessage(null, 
                            new FacesMessage(
                            FacesMessage.SEVERITY_INFO,
                            "Registro de caso",
                            "Se ha añadido el caso a la tabla"));
            navegacion = "vistaHistorial";
        }catch(Exception ex){
            System.out.println("Error al insertar el caso en la base de datos: " + ex);
            FacesContext.getCurrentInstance().addMessage(null, 
                            new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            "Registro de caso",
                            "Ha ocurrido un error al crear el caso"));
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

    public int getIdMascota() {
        return idMascota;
    }

    public void setIdMascota(int idMascota) {
        this.idMascota = idMascota;
    }

    public int getIdVeterinario() {
        return idVeterinario;
    }

    public void setIdVeterinario(int idVeterinario) {
        this.idVeterinario = idVeterinario;
    }
    
    public boolean isEsNecesarioIngreso() {
        return esNecesarioIngreso;
    }

    public void setEsNecesarioIngreso(boolean esNecesarioIngreso) {
        this.esNecesarioIngreso = esNecesarioIngreso;
    }

    public List<Cliente> getListaClientes() {
        return listaClientes;
    }

    public void setListaClientes(List<Cliente> listaClientes) {
        this.listaClientes = listaClientes;
    }

    public Historial getCaso() {
        return caso;
    }

    public void setCaso(Historial caso) {
        this.caso = caso;
    }

    public List<Mascota> getListaMascotas() {
        return listaMascotas;
    }

    public void setListaMascotas(List<Mascota> listaMascotas) {
        this.listaMascotas = listaMascotas;
    }

    public Usuario getVet() {
        return vet;
    }

    public void setVet(Usuario vet) {
        this.vet = vet;
    }

    public List<Usuario> getListaVeterinarios() {
        return listaVeterinarios;
    }

    public void setListaVeterinarios(List<Usuario> listaVeterinarios) {
        this.listaVeterinarios = listaVeterinarios;
    }

    public List<Tarifa> getTratamientosSeleccionados() {
        return tratamientosSeleccionados;
    }

    public void setTratamientosSeleccionados(List<Tarifa> tratamientosSeleccionados) {
        this.tratamientosSeleccionados = tratamientosSeleccionados;
    }

    public Tarifa getTra() {
        return tra;
    }

    public void setTra(Tarifa tra) {
        this.tra = tra;
    }

    public List<Tarifa> getListaTratamientos() {
        return listaTratamientos;
    }

    public void setListaTratamientos(List<Tarifa> listaTratamientos) {
        this.listaTratamientos = listaTratamientos;
    }  

    private void setVeterinario() {
        for(Usuario v : listaVeterinarios)
            if(v.getIdUsuario() == idVeterinario)
                caso.setVeterinario(v);
    }

    private void setMascota() {
        for(Mascota m : listaMascotas)
            if(m.getIdMascota() == idMascota)
                caso.setMascota(m);
    }

    private void setTratamientos() {
        caso.setTratamientos(new ArrayList<Tarifa>(tratamientosSeleccionados));
    }

    private void setIngreso() {       
        ing.setFechaIngreso(new Date());
        ingresoEJB.create(ing);
        caso.setIngreso(ing);
    }

    private List<Usuario> soloVeterinariosExistentes(List<Usuario> totales) {
        List<Usuario> esValido = new ArrayList<>();
        for(Usuario u : totales)
            if(!u.isEliminado() || u.getRol().getIdRol() < 3)
                esValido.add(u);
        return esValido;
    }
}
