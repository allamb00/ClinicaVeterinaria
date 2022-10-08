/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB.controller;

import EJB.ConstantesFacadeLocal;
import EJB.HistorialFacadeLocal;
import EJB.IngresoFacadeLocal;
import java.io.Serializable;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import modelo.Constantes;
import modelo.Historial;

/**
 *
 * @author andre
 */
 @Named
 @ViewScoped
public class AltaConstantesController implements Serializable{
     
    @Inject
    private Constantes con;
    @Inject
    private Historial caso;
    @Inject
    private VistaIngresosController listIngCon;
    
    @EJB
    private ConstantesFacadeLocal constanteEJB;
    @EJB
    private HistorialFacadeLocal historialEJB;
    @EJB
    private IngresoFacadeLocal ingresoEJB;
    
    private String pLat;
    private String pSim;
    private String pReg;
    private String pRit;
    private String pSinc;
    
    @PostConstruct
    public void init(){
        caso = listIngCon.getCaso();        
    }
    /**
     * Inserta el cliente especificado en altaCliente.xhtml
     */
    public String insertarConstantes(){
        String navegacion = null;
        try{            
            con.setPulso(pulsoStr());
            con.setHoraMedicion(new Date());
            caso.getIngreso().getConstantes().add(con);
            constanteEJB.create(con);
            ingresoEJB.edit(caso.getIngreso());
            FacesContext.getCurrentInstance().addMessage(null, 
                            new FacesMessage(
                            FacesMessage.SEVERITY_INFO,
                            "Medición de constantes",
                            "Se han añadido las nuevas mediciones"));
            navegacion = "vistaIngresos";
        }catch(Exception ex){
            System.out.println("Error al insertar las nuevas constantes en la base de datos: " + ex);
            FacesContext.getCurrentInstance().addMessage(null, 
                            new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            "Medición de constantes",
                            "Ha ocurrido un error al añadir las nuevas mediciones"));
        }
        return navegacion;
    }    

    public Constantes getCon() {
        return con;
    }

    public void setCon(Constantes con) {
        this.con = con;
    }

    public Historial getCaso() {
        return caso;
    }

    public void setCaso(Historial caso) {
        this.caso = caso;
    }

    public String getpLat() {
        return pLat;
    }

    public void setpLat(String pLat) {
        this.pLat = pLat;
    }

    public String getpSim() {
        return pSim;
    }

    public void setpSim(String pSim) {
        this.pSim = pSim;
    }

    public String getpReg() {
        return pReg;
    }

    public void setpReg(String pReg) {
        this.pReg = pReg;
    }

    public String getpRit() {
        return pRit;
    }

    public void setpRit(String pRit) {
        this.pRit = pRit;
    }

    public String getpSinc() {
        return pSinc;
    }

    public void setpSinc(String pSinc) {
        this.pSinc = pSinc;
    }
    
    public String pulsoStr(){
        String pulso = pLat;
        pulso += " " + pSim;
        pulso += " " + pReg;
        pulso += " " + pRit;
        pulso += " " + pSinc;
        return pulso;
    }
}
