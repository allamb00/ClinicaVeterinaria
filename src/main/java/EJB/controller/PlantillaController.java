/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB.controller;

import java.io.IOException;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import modelo.Usuario;

/**
 *
 * @author andre
 */
@Named
@SessionScoped
public class PlantillaController implements Serializable{
    public void verificarYMostrar(){
        Usuario usu = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        if(usu == null) {
            try {
                String url = FacesContext.getCurrentInstance().getExternalContext().getApplicationContextPath();
                url += "/public/error.xhtml";
                FacesContext.getCurrentInstance().getExternalContext().redirect(url);
                
            } catch (IOException ex) {
                System.out.println("Error en el controlador de la plantilla: " + ex);
            }
        }
    }
    
}
