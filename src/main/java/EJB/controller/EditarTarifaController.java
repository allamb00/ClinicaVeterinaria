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
public class EditarTarifaController  implements Serializable{
    
    @Inject
    private Tarifa tar;    
    @Inject
    private VistaTarifasController lisTarCon;
    
    @EJB
    private CampoFacadeLocal campoEJB;
    @EJB
    private TarifaFacadeLocal tarifaEJB;
    
    private List<Campo> listaCampos;
    private int idCampo;
    
    @PostConstruct
    public void init() { 
        tar = lisTarCon.getTar();
        listaCampos = campoEJB.findAll();
    }
    
    public String editarTarifa(){
        String navegacion = null;        
        try{
            asignarCampo();
            tarifaEJB.edit(tar);
            System.out.println("Tarifa editada");
            navegacion = "vistaTarifas";
            FacesContext.getCurrentInstance().addMessage(null, 
                            new FacesMessage(
                            FacesMessage.SEVERITY_INFO,
                            "Se ha editado la tarifa",
                            "Edición correcta"));
        }catch(Exception ex){
            System.out.println("Error al consultar la tarifa en la base de datos: " + ex);
            FacesContext.getCurrentInstance().addMessage(null, 
                            new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            "Error de edición",
                            "Ha ocurrido un error"));
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
