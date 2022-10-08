/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB.controller;

import EJB.UsuarioFacadeLocal;
import com.mycompany.clinicaveterinaria.Cifrado;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import modelo.Usuario;

/**
 *
 * @author andre
 */
@Named
@ViewScoped
public class CambiarPassController implements Serializable{
    
    @Inject
    private Usuario usu;
    
    @EJB
    private UsuarioFacadeLocal usuarioEJB;
    
    private String newPass;
    
    @PostConstruct
    public void init() { 
        usu = getSessionUser();
    }
    
    public String cambiarPass(){
        String navegacion = null;   
        
        try{
            usu.setPassword(Cifrado.cifrar(usu.getUserName(), usu.getPassword()));
            Usuario loggedUser = usuarioEJB.verificarUsuario(usu);
            
            if(loggedUser != null) {
                usu.setPassword(Cifrado.cifrar(usu.getUserName(), newPass));
                usuarioEJB.edit(usu);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario",usu);
                navegacion = "inicio.xhtml";
                
                FacesContext.getCurrentInstance().addMessage(null, 
                                new FacesMessage(
                                FacesMessage.SEVERITY_INFO,
                                "Edición correcta",
                                "Se ha cambiado la contraseña"));
            } else 
                FacesContext.getCurrentInstance().addMessage(null, 
                                new FacesMessage(
                                FacesMessage.SEVERITY_ERROR,
                                "Error de validación",
                                "La antigua contraseña no coincide"));   
        }catch(Exception ex){
            FacesContext.getCurrentInstance().addMessage(null, 
                            new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            "Error de edición",
                            "Ha ocurrido un error"));
        }
        return navegacion;
    }
    
    private Usuario getSessionUser() {
        return  (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
    }

    public Usuario getUsu() {
        return usu;
    }

    public void setUsu(Usuario usu) {
        this.usu = usu;
    }

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }
}
