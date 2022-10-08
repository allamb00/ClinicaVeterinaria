/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB.controller;

import EJB.TarifaFacadeLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import modelo.Tarifa;
import org.primefaces.model.FilterMeta;
import org.primefaces.util.LangUtils;

/**
 *
 * @author andre
 */
@Named
@RequestScoped
public class VistaTarifasController implements Serializable{
    
    @Inject
    private Tarifa tar;
    
    @EJB
    private TarifaFacadeLocal tarifaEJB;
    
    private List<Tarifa> listaTarifas;
    private List<Tarifa> tarifasFiltradas;
    private List<FilterMeta> filtrarPor;
    
    @PostConstruct
    public void init(){
        listaTarifas = tarifaEJB.findAll();
        filtrarPor = new ArrayList<>();
    }
    
    public boolean filtroGlobal(Object valor, Object filtro, Locale locale) {
        String textoFiltro = (filtro == null) ? null : filtro.toString().trim().toUpperCase();
        if (LangUtils.isBlank(textoFiltro)) {
            return true;
        }

        Tarifa tar = (Tarifa) valor;
        return tar.getTratamiento().toUpperCase().contains(textoFiltro)
                || tar.getTratamiento().toUpperCase().contains(textoFiltro)
                || String.valueOf(tar.getPrecio()).contains(textoFiltro)
                || String.valueOf(tar.getDescAlumnos()).contains(textoFiltro)
                || String.valueOf(tar.getDescProtectora()).contains(textoFiltro);
    }

    public void eliminar(){
        listaTarifas.remove(tar);
        tarifaEJB.remove(tar);
        tar = null;
    }
    
    public void establecerTarifa(Tarifa tar) {
        this.tar = tar;
    }

    public Tarifa getTar() {
        return tar;
    }

    public void setTar(Tarifa tar) {
        this.tar = tar;
    }

    public List<Tarifa> getListaTarifas() {
        return listaTarifas;
    }

    public void setListaTarifas(List<Tarifa> listaTarifas) {
        this.listaTarifas = listaTarifas;
    }

    public List<Tarifa> getTarifasFiltradas() {
        return tarifasFiltradas;
    }

    public void setTarifasFiltradas(List<Tarifa> tarifasFiltradas) {
        this.tarifasFiltradas = tarifasFiltradas;
    }
    
    public List<FilterMeta> getFiltrarPor() {
        return filtrarPor;
    }

    public void setFiltrarPor(List<FilterMeta> filtrarPor) {
        this.filtrarPor = filtrarPor;
    }
}
