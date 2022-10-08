/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB.controller;

import EJB.UsuarioFacadeLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import modelo.Usuario;
import org.primefaces.model.FilterMeta;
import org.primefaces.util.LangUtils;

/**
 *
 * @author andre
 */
@Named
@RequestScoped
public class VistaUsuariosController implements Serializable{
    
    @Inject
    private Usuario usu;
    
    @EJB
    private UsuarioFacadeLocal usuarioEJB;
    
    private List<Usuario> listaUsuarios;
    private List<Usuario> usuariosFiltrados;
    private List<FilterMeta> filtrarPor;
    
    @PostConstruct
    public void init(){
        listaUsuarios = descartarEliminados(usuarioEJB.findAll());
            
        filtrarPor = new ArrayList<>();
    }
    
    public boolean filtroGlobal(Object valor, Object filtro, Locale locale) {
        String textoFlitro = (filtro == null) ? null : filtro.toString().trim().toUpperCase();
        if (LangUtils.isBlank(textoFlitro)) {
            return true;
        }

        Usuario usu = (Usuario) valor;
        return usu.getNombre().toUpperCase().contains(textoFlitro)
                || usu.getUserName().toUpperCase().contains(textoFlitro)
                || usu.getApellidos().toUpperCase().contains(textoFlitro)
                || usu.getDni().toUpperCase().contains(textoFlitro)
                || usu.getTelefono().toUpperCase().contains(textoFlitro)
                || usu.getRol().getRol().toUpperCase().contains(textoFlitro);
    }

    public void eliminar(){
        listaUsuarios.remove(usu);
        usu.setEliminado(true);
        usuarioEJB.edit(usu);
        usu = null;
    }
    
    public void establecerUsuario(Usuario usu) {
        this.usu = usu;
    }
    
    public Usuario getUsu() {
        return usu;
    }

    public void setUsu(Usuario usu) {
        this.usu = usu;
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public List<Usuario> getUsuariosFiltrados() {
        return usuariosFiltrados;
    }

    public void setUsuariosFiltrados(List<Usuario> usuariosFiltrados) {
        this.usuariosFiltrados = usuariosFiltrados;
    }

    public List<FilterMeta> getFiltrarPor() {
        return filtrarPor;
    }

    public void setFiltrarPor(List<FilterMeta> filtrarPor) {
        this.filtrarPor = filtrarPor;
    }    
    
    private List<Usuario> descartarEliminados(List<Usuario> listaUsuarios) {
        List<Usuario> usuariosValidos = new ArrayList<>();
        for(Usuario u : listaUsuarios)
            if(!u.isEliminado())
                usuariosValidos.add(u);
        return usuariosValidos;
    }
}
