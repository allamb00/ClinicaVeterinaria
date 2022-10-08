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
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.util.Date;
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
public class AltaUsuarioController implements Serializable{
    @Inject
    private Usuario usu;
    
    @EJB
    private UsuarioFacadeLocal usuarioEJB;
    @EJB
    private RolFacadeLocal rolEJB;
    
    private int nRol;
    private List<Rol> listaRoles;
    
    @PostConstruct
    public void init(){
        listaRoles = rolEJB.findAll();
    }
    /**
     * Inserta el usuario especificado en altaUsuario.xhtml
     */
    public String insertarUsuario(){
        String navegacion = null;
        try{
            if(DNI.validar(usu.getDni())) {
                if(usu.getColor().isEmpty())
                    usu.setColor("ffffff");         
                usu.setRol(listaRoles.get(nRol - 1));
                usu.setFechaRegistro(new Date());
                usu.setUserName(createUserName(usu.getNombre(), usu.getApellidos()));
                usu.setPassword(Cifrado.cifrar(usu.getUserName(), usu.getUserName()));
                usuarioEJB.create(usu);
                FacesContext.getCurrentInstance().addMessage(null, 
                                new FacesMessage(
                                FacesMessage.SEVERITY_INFO,
                                "Creación de usuario",
                                "Se ha añadido el usuario a la tabla"));
                navegacion = "vistaUsuarios";
            } else
                FacesContext.getCurrentInstance().addMessage(null, 
                                new FacesMessage(
                                FacesMessage.SEVERITY_ERROR,
                                "Creación de usuario",
                                "El DNI no es correcto"));                    
        } catch(Exception ex){
            FacesContext.getCurrentInstance().addMessage(null, 
                            new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            "Creación de usuario",
                            "Ha ocurrido un error al crear el usuario"));
        }
        return navegacion;
    }
    
    private String createUserName(String n, String a){
        String userName = "";
        String name = eliminarTildes(n);
        String apellidos = eliminarTildes(a);
        
        //Primera parte
        String[] pParte = name.split(" ");
        for(String s : pParte)
            userName += s.substring(0, 1);
        
        //Segunda parte
        String[] sParte = apellidos.split(" ");
        if(sParte[0].length() >= 4)
            userName += sParte[0].substring(0, 4);
        else
            userName += sParte[0];
        if(sParte.length > 1)
            userName += sParte[1].substring(0, 1);
        
        //Solución de duplicidad
        DecimalFormat formatter = new DecimalFormat("00");
        for(int i = 0; i <= 99; i++){
            if(!usuarioEJB.userAlreadyExists(userName + formatter.format(i)))
                return userName + formatter.format(i);
        }
        
        return null;
    }
    
    private static String eliminarTildes(String s) {
        s = Normalizer.normalize(s, Normalizer.Form.NFD);
        s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return s;
    }
    
    public Usuario getUsu() {
        return usu;
    }

    public void setUsu(Usuario usu) {
        this.usu = usu;
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
