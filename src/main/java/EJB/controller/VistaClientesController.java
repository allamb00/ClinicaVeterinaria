/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB.controller;

import EJB.ClienteFacadeLocal;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import modelo.Cliente;
import org.primefaces.model.FilterMeta;
import org.primefaces.util.LangUtils;

/**
 *
 * @author andre
 */
@Named
@RequestScoped
public class VistaClientesController implements Serializable{
    
    @Inject
    private Cliente cli;
    
    @EJB
    private ClienteFacadeLocal clienteEJB;
    
    private List<Cliente> listaClientes;
    private List<Cliente> clientesFiltrados;
    private List<FilterMeta> filtrarPor;
    
    @PostConstruct
    public void init(){
        listaClientes = clienteEJB.findAll();
        filtrarPor = new ArrayList<>();
    }
    
    public boolean filtroGlobal(Object valor, Object filtro, Locale locale) {
        String textoFiltro = (filtro == null) ? null : filtro.toString().trim().toUpperCase();
        if (LangUtils.isBlank(textoFiltro)) {
            return true;
        }

        Cliente cli = (Cliente) valor;
        return cli.getNombre().toUpperCase().contains(textoFiltro)
                || cli.getApellidos().toUpperCase().contains(textoFiltro)
                || cli.getDni().toUpperCase().contains(textoFiltro)
                || cli.getTelefono().toUpperCase().contains(textoFiltro);
    }

    public void eliminar(){
        listaClientes.remove(cli);
        clienteEJB.remove(cli);
        cli = null;
    }
    
    public void establecerCliente(Cliente cli) {
        this.cli = cli;
    }

    public Cliente getCli() {
        return cli;
    }

    public void setCli(Cliente cli) {
        this.cli = cli;
    }

    public List<Cliente> getListaClientes() {
        return listaClientes;
    }

    public void setListaClientes(List<Cliente> listaClientes) {
        this.listaClientes = listaClientes;
    }

    public List<Cliente> getClientesFiltrados() {
        return clientesFiltrados;
    }

    public void setClientesFiltrados(List<Cliente> clientesFiltrados) {
        this.clientesFiltrados = clientesFiltrados;
    }

    public List<FilterMeta> getFiltrarPor() {
        return filtrarPor;
    }

    public void setFiltrarPor(List<FilterMeta> filtrarPor) {
        this.filtrarPor = filtrarPor;
    }
    
    public String tieneDescuento(){
        switch(cli.getTieneDescuento()){
            case 1:
                return "Estudiantes";
            case 2:
                return "Protectora";
        }
        return "Sin descuento";
    }
}
