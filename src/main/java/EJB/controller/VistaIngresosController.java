/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB.controller;

import EJB.ConstantesFacadeLocal;
import EJB.HistorialFacadeLocal;
import EJB.IngresoFacadeLocal;
import EJB.TarifaFacadeLocal;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import modelo.Constantes;
import modelo.Historial;
import modelo.Ingreso;
import modelo.Mascota;
import modelo.Tarifa;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.FilterMeta;
import org.primefaces.util.LangUtils;

/**
 *
 * @author andre
 */
@Named
@RequestScoped
public class VistaIngresosController implements Serializable{
    
    @Inject
    private Ingreso ing;
    @Inject
    private Mascota pet;
    @Inject
    private Historial caso;
    @Inject
    private Constantes con;
    
    @EJB
    private IngresoFacadeLocal ingresoEJB;
    @EJB
    private HistorialFacadeLocal casoEJB;
    @EJB
    private ConstantesFacadeLocal constanteEJB;
    @EJB
    private TarifaFacadeLocal tarifaEJB;
    @EJB
    private HistorialFacadeLocal historialEJB;
    
    private List<Ingreso> listaIngresos;
    private List<Historial> listaCasos;
    private List<Constantes> listaConstantes;
    private List<Historial> listaCasosActivos;
    private List<Historial> listaCasosCerrados;
    private List<Historial> listaCasosFiltrados;
    private List<FilterMeta> filtrarPor;
    private Tarifa hospitalizacion;
    
    @PostConstruct
    public void init(){
        listaCasos = casoEJB.findAll();
        listaCasosActivos = activos(listaCasos);
        listaCasosCerrados = cerrados(listaCasos);
        listaIngresos = ingresoEJB.findAll();
        filtrarPor = new ArrayList<>();
        hospitalizacion = tarifaEJB.getHospitalizacion();
    }

    public void editar(RowEditEvent<Constantes> event){
        try {
            constanteEJB.edit(event.getObject());
            FacesContext.getCurrentInstance().addMessage(null, 
                            new FacesMessage(
                            FacesMessage.SEVERITY_INFO,
                            "Edición de constantes",
                            "Se han editado las mediciones"));
        }catch(Exception ex){
            System.out.println("Error al insertar el caso en la base de datos: " + ex);
            FacesContext.getCurrentInstance().addMessage(null, 
                            new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            "Edición de constantes",
                            "No se han podido editar las mediciones"));
        }
    }
    
    public boolean filtroGlobal(Object valor, Object filtro, Locale locale) {
        String textoFiltro = (filtro == null) ? null : filtro.toString().trim().toUpperCase();
        if (LangUtils.isBlank(textoFiltro)) {
            return true;
        }
        
        Historial caso = (Historial) valor;
        return caso.getMascota().getNombre().toUpperCase().contains(textoFiltro)
                || caso.getMascota().getRaza().toUpperCase().contains(textoFiltro)
                || caso.getMascota().getEspecie().toUpperCase().contains(textoFiltro)
                || String.valueOf(caso.getIdCaso()).contains(textoFiltro);
    }
    
    public void eliminar(Historial caso, Constantes c){
        constanteEJB.remove(c);
        caso.getIngreso().getConstantes().remove(c);
    }
    
    public void darElAlta(Historial caso){
        listaCasosActivos.remove(caso);
        caso.getIngreso().setFechaAlta(new Date());
        caso.getIngreso().setDiasIngreso(contarDiasHospitalizado(caso));
        caso.getTratamientos().add(hospitalizacion);
        
        ingresoEJB.edit(caso.getIngreso());
        historialEJB.edit(caso);
        listaCasosCerrados.add(caso);
    }
    
    private int contarDiasHospitalizado(Historial caso) {
        LocalDate ingreso = convertToLocalDate(caso.getIngreso().getFechaIngreso());
        LocalDate alta = convertToLocalDate(caso.getIngreso().getFechaAlta());
        int diasIngresado = (int) ChronoUnit.DAYS.between(ingreso, alta);
            
        return diasIngresado + 1;
    }
    
    private LocalDate convertToLocalDate(Date dateToConvert) {
        return dateToConvert.toInstant()
          .atZone(ZoneId.systemDefault())
          .toLocalDate();
    }
    
    public String altaConstantes(Historial caso){
        this.caso = caso;
        return "altaConstantes";
    }

    public Ingreso getIng() {
        return ing;
    }

    public void setIng(Ingreso ing) {
        this.ing = ing;
    }

    public Mascota getPet() {
        return pet;
    }

    public void setPet(Mascota pet) {
        this.pet = pet;
    }

    public List<Ingreso> getListaIngresos() {
        return listaIngresos;
    }

    public void setListaIngresos(List<Ingreso> listaIngresos) {
        this.listaIngresos = listaIngresos;
    }

    public Historial getCaso() {
        return caso;
    }

    public void setCaso(Historial caso) {
        this.caso = caso;
    }

    public List<Historial> getListaCasos() {
        return listaCasos;
    }

    public void setListaCasos(List<Historial> listaCasos) {
        this.listaCasos = listaCasos;
    }

    public Constantes getCon() {
        return con;
    }

    public void setCon(Constantes con) {
        this.con = con;
    }

    public List<Constantes> getListaConstantes() {
        return listaConstantes;
    }

    public void setListaConstantes(List<Constantes> listaConstantes) {
        this.listaConstantes = listaConstantes;
    }

    public List<Historial> getListaCasosActivos() {
        return listaCasosActivos;
    }

    public void setListaCasosActivos(List<Historial> listaCasosActivos) {
        this.listaCasosActivos = listaCasosActivos;
    }

    public List<Historial> getListaCasosCerrados() {
        return listaCasosCerrados;
    }

    public void setListaCasosCerrados(List<Historial> listaCasosCerrados) {
        this.listaCasosCerrados = listaCasosCerrados;
    }

    public List<Historial> getListaCasosFiltrados() {
        return listaCasosFiltrados;
    }

    public void setListaCasosFiltrados(List<Historial> listaCasosFiltrados) {
        this.listaCasosFiltrados = listaCasosFiltrados;
    }

    public List<FilterMeta> getFiltrarPor() {
        return filtrarPor;
    }

    public void setFiltrarPor(List<FilterMeta> filtrarPor) {
        this.filtrarPor = filtrarPor;
    }
    
    public boolean esActivo(Ingreso ingreso){
        return ingreso.getFechaAlta() == null;
    }
    
    private List<Historial> activos (List<Historial> casos) {
        List<Historial> activos = new ArrayList<>();
        for(Historial c : casos) {
            if(c.getIngreso()!= null && c.getIngreso().getFechaAlta() == null)
                activos.add(c);
        }
        return activos; //repetir con cerrados
    }
    
    private List<Historial> cerrados (List<Historial> casos) {
        List<Historial> cerrados = new ArrayList<>();
        for(Historial c: casos) {
            if(c.getIngreso() != null && c.getIngreso().getFechaAlta() != null)
                cerrados.add(c);
        }
        return cerrados;
    }
    
}
