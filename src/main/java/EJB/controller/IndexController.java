/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB.controller;

import EJB.UsuarioFacadeLocal;
import com.mycompany.clinicaveterinaria.Cifrado;
import java.io.Serializable;
import java.util.Date;
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
public class IndexController implements Serializable{
    @Inject
    private Usuario usu;
    
    @EJB
    private UsuarioFacadeLocal usuarioEJB;
    
    @PostConstruct
    public void init(){
    }
    
    public String validarUsuario(){
        String navegacion = null;
        
        if(usu.getUserName().equals("") || usu.getPassword().equals(""))
            FacesContext.getCurrentInstance().addMessage(null, 
                            new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            "Error de validación",
                            "Los campos no pueden estar en blanco"));
        else
            try{
                System.out.println("Usuario: " + usu.getUserName());
                System.out.println("Pass: " + usu.getPassword());
                usu.setPassword(Cifrado.cifrar(usu.getUserName(), usu.getPassword()));
                System.out.println("Pass: " + usu.getPassword() + "\n");
                Usuario loggedUser = usuarioEJB.verificarUsuario(usu);
                if(loggedUser != null) {
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario",loggedUser);
                    registrarUltimaSesion(loggedUser);
                    navegacion = "private/inicio.xhtml";
                } else 
                    FacesContext.getCurrentInstance().addMessage(null, 
                                    new FacesMessage(
                                    FacesMessage.SEVERITY_ERROR,
                                    "Error de validación",
                                    "Usuario o contraseña incorrectos"));
            }catch(Exception ex){
                System.out.println("Error al consultar el usuario en la base de datos: " + ex);
            }
        return navegacion;
    }
    
    public Usuario getUsu() {
        return usu;
    }

    public void setUsu(Usuario usu) {
        this.usu = usu;
    }

    private void registrarUltimaSesion(Usuario usu) {       
        usu.setUltimaSesion(new Date());
        usuarioEJB.edit(usu);
    }
}