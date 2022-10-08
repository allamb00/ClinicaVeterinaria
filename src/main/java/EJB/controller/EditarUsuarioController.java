/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB.controller;

import EJB.RolFacadeLocal;
import EJB.UsuarioFacadeLocal;
import com.mycompany.clinicaveterinaria.Cifrado;
import com.mycompany.clinicaveterinaria.DNI;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import modelo.Rol;
import modelo.Usuario;

/**
 *
 * @author andre
 */
@Named
@ViewScoped
public class EditarUsuarioController implements Serializable{
    
    @Inject
    private Usuario usu;
    
    @Inject
    private VistaUsuariosController listUsuCon;
    
    @EJB
    private UsuarioFacadeLocal usuarioEJB;
    
    @EJB
    private RolFacadeLocal rolEJB;
    
    private int nRol;
    private boolean resetPass;
    private List<Rol> listaRoles;
    
    @PostConstruct
    public void init() { 
        usu = listUsuCon.getUsu();
        listaRoles = rolEJB.findAll();
        nRol = usu.getRol().getIdRol();
    }
    
    public String editarUsuario(){
        String navegacion = null;        
        try{
            if(DNI.validar(usu.getDni())) {
                if(resetPass)
                    usu.setPassword(Cifrado.cifrar(usu.getUserName(), usu.getUserName()));          
                usu.setRol(listaRoles.get(nRol - 1));
                usuarioEJB.edit(usu);
                navegacion = "vistaUsuarios";
                FacesContext.getCurrentInstance().addMessage(null, 
                                new FacesMessage(
                                FacesMessage.SEVERITY_INFO,
                                "Se ha editado el usuario",
                                "Edición correcta"));
            } else
                FacesContext.getCurrentInstance().addMessage(null, 
                                new FacesMessage(
                                FacesMessage.SEVERITY_ERROR,
                                "Creación de usuario",
                                "El DNI no es correcto"));   
        } catch(Exception ex){
            System.out.println("Error al consultar el usuario en la base de datos: " + ex.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, 
                            new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            "Error de edición",
                            "Ha ocurrido un error"));
        }
        return navegacion;
    }
    
    public Usuario getUsu() {
        return usu;
    }

    public void setUsu(Usuario usu) {
        this.usu = usu;
    }

    public boolean isResetPass() {
        return resetPass;
    }

    public void setResetPass(boolean resetPass) {
        this.resetPass = resetPass;
    }

    public int getnRol() {
        return nRol;
    }

    public void setnRol(int nRol) {
        this.nRol = nRol;
    }    

    public List<Rol> getListaRoles() {
        return listaRoles;
    }

    public void setListaRoles(List<Rol> listaRoles) {
        this.listaRoles = listaRoles;
    }
}
