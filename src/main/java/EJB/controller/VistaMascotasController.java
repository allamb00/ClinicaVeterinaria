/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB.controller;

import EJB.MascotaFacadeLocal;
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
import modelo.Mascota;
import org.primefaces.model.FilterMeta;
import org.primefaces.util.LangUtils;

/**
 *
 * @author andre
 */
@Named
@RequestScoped
public class VistaMascotasController implements Serializable{
    
    @Inject
    private Cliente cli;
    @Inject
    private Mascota pet;
    
    @EJB
    private MascotaFacadeLocal mascotaEJB;
    
    private List<Mascota> listaMascotas;
    private List<Mascota> mascotasFiltradas;
    private List<FilterMeta> filtrarPor;
    
    @PostConstruct
    public void init(){
        listaMascotas = mascotaEJB.findAll();
        filtrarPor = new ArrayList<>();
    }
    
    public boolean filtroGlobal(Object valor, Object filtro, Locale locale) {
        String textoFiltro = (filtro == null) ? null : filtro.toString().trim().toUpperCase();
        if (LangUtils.isBlank(textoFiltro)) {
            return true;
        }

        Mascota pet = (Mascota) valor;
        return pet.getNombre().toUpperCase().contains(textoFiltro)
                || pet.getEspecie().toUpperCase().contains(textoFiltro)
                || pet.getRaza().toUpperCase().contains(textoFiltro)
                || pet.getPropietario().getNombre().contains(textoFiltro)
                || pet.getPropietario().getApellidos().contains(textoFiltro)
                || pet.getPropietario().getTelefono().contains(textoFiltro);
    }

    public void eliminar(){
        listaMascotas.remove(pet);
        mascotaEJB.remove(pet);
        pet = null;
    }
    
    public void establecerMascota(Mascota pet) {
        this.pet = pet;
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

    public List<Mascota> getListaMascotas() {
        return listaMascotas;
    }

    public void setListaMascotas(List<Mascota> listaMascotas) {
        this.listaMascotas = listaMascotas;
    }

    public List<Mascota> getMascotasFiltradas() {
        return mascotasFiltradas;
    }

    public void setMascotasFiltradas(List<Mascota> mascotasFiltradas) {
        this.mascotasFiltradas = mascotasFiltradas;
    }

    public List<FilterMeta> getFiltrarPor() {
        return filtrarPor;
    }

    public void setFiltrarPor(List<FilterMeta> filtrarPor) {
        this.filtrarPor = filtrarPor;
    }
}
