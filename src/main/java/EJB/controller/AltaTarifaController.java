/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB.controller;

import EJB.CampoFacadeLocal;
import EJB.TarifaFacadeLocal;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import modelo.Campo;
import modelo.Tarifa;

/**
 *
 * @author andre
 */
 @Named
 @ViewScoped
public class AltaTarifaController implements Serializable{
    @Inject
    private Tarifa tar;
    
    @EJB
    private TarifaFacadeLocal tarifaEJB;
    @EJB
    private CampoFacadeLocal campoEJB;
    
    private List<Campo> listaCampos;
    private int idCampo;
    
    @PostConstruct
    public void init(){
        listaCampos = campoEJB.findAll();
    }
    /**
     * Inserta la tarifa especificada en altaTarifa.xhtml
     */
    public String insertarTarifa(){
        String navegacion = null;
        try{
            asignarCampo();
            tarifaEJB.create(tar);
            FacesContext.getCurrentInstance().addMessage(null, 
                            new FacesMessage(
                            FacesMessage.SEVERITY_INFO,
                            "Creación de tarifa",
                            "Se ha añadido la tarifa a la tabla"));
            navegacion = "vistaTarifas";
        }catch(Exception ex){
            System.out.println("Error al insertar la tarifa en la base de datos: " + ex);
            FacesContext.getCurrentInstance().addMessage(null, 
                            new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            "Creación de tarifa",
                            "Ha ocurrido un error al crear la tarifa"));
        }
        return navegacion;
    }

    public Tarifa getTar() {
        return tar;
    }

    public void setTar(Tarifa tar) {
        this.tar = tar;
    }

    public List<Campo> getListaCampos() {
        return listaCampos;
    }

    public void setListaCampos(List<Campo> listaCampos) {
        this.listaCampos = listaCampos;
    }

    public int getIdCampo() {
        return idCampo;
    }

    public void setIdCampo(int idCampo) {
        this.idCampo = idCampo;
    }
    
    private void asignarCampo(){
        for(Campo c : listaCampos)
            if(c.getIdCampo() == idCampo)
                tar.setCampo(c);
    }
}
