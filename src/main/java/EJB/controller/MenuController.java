/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB.controller;

import EJB.MenuFacadeLocal;
import EJB.UsuarioFacadeLocal;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import modelo.Menu;
import modelo.Usuario;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuElement;
import org.primefaces.model.menu.MenuModel;

/**
 *
 * @author andre
 */
@Named
@SessionScoped
public class MenuController implements Serializable{
    @Inject
    private Usuario usu;
    
    @EJB
    private MenuFacadeLocal menuEJB;    
    @EJB
    private UsuarioFacadeLocal usuarioEJB;
    
    private MenuModel modelo;
    
    @PostConstruct
    public void init(){
        modelo = obtenerMenu();
    }

    public MenuModel obtenerMenu(){
        usu = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        MenuModel modelo = new DefaultMenuModel();
        String url = FacesContext.getCurrentInstance().getExternalContext().getApplicationContextPath();
        
        List<Menu> listaMenus = menuEJB.obtenerMenusUsuario(usu);

        List<MenuElement> menuList = new ArrayList<>(); //lista de menus
        DefaultSubMenu subMenu; //subMenu auxiliar
        for(Menu m : listaMenus) { //para cada item
            if(m.getIdMenuMenu() == null) //si no tiene padre
                if(m.getTipo().equals("S")){ // y es un submenu
                    subMenu = DefaultSubMenu.builder().label(m.getNombre()).build();
                    for(Menu mi : listaMenus){ //se busca en toda la lista
                        if(mi.getIdMenuMenu() != null && mi.getIdMenuMenu().equals(m)) //si tiene algun hijo
                            subMenu.getElements().add(DefaultMenuItem.builder().value(mi.getNombre()).url(url+mi.getUrl()).styleClass("noUnderline").build()); //y se añade al padre
                    }
                    menuList.add(subMenu); //se añade el padre a la lista
                }
                else  
                    menuList.add(DefaultMenuItem.builder().value(m.getNombre()).url(url+m.getUrl()).build()); // si es un item se añade a la lista 
        }
        
        for(MenuElement m : menuList) //se añaden todos los elementos al modelo
            modelo.getElements().add(m);
        
        return modelo;
    }
    
    public void destruirSesion() {
        try {
            registrarUltimaSesion();
            String url = FacesContext.getCurrentInstance().getExternalContext().getApplicationContextPath();
            url += "/";
            FacesContext.getCurrentInstance().getExternalContext().redirect(url);            
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        } catch (IOException ex) {
            System.out.println("Error al cerrar sesión: " + ex.getMessage());
        }
    }
    
    private void registrarUltimaSesion() {        
        Usuario usu = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        usu.setUltimaSesion(new Date());
        usuarioEJB.edit(usu);
    }
    public MenuModel getModelo() {
        return modelo;
    }

    public void setModelo(MenuModel modelo) {
        this.modelo = modelo;
    }

    public Usuario getUsu() {
        return usu;
    }

    public void setUsu(Usuario usu) {
        this.usu = usu;
    }
    
}
