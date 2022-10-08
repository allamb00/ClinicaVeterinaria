/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB.controller;

import EJB.InventarioFacadeLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import modelo.Inventario;
import org.primefaces.model.FilterMeta;
import org.primefaces.util.LangUtils;

/**
 *
 * @author andre
 */
@Named
@RequestScoped
public class VistaInventarioController implements Serializable{
    
    @Inject
    private Inventario pro;
    
    @EJB
    private InventarioFacadeLocal inventarioEJB;
    
    private List<Inventario> listaInventario;
    private List<Inventario> inventarioFiltrado;
    private List<FilterMeta> filtrarPor;
    
    @PostConstruct
    public void init(){
        listaInventario = inventarioEJB.findAll();
        filtrarPor = new ArrayList<>();
    }
    
    public boolean filtroGlobal(Object valor, Object filtro, Locale locale) {
        String textoFiltro = (filtro == null) ? null : filtro.toString().trim().toUpperCase();
        if (LangUtils.isBlank(textoFiltro)) {
            return true;
        }

        Inventario pro = (Inventario) valor;
        return pro.getNombre().toUpperCase().contains(textoFiltro)
                || pro.getAlmacenamiento().toUpperCase().contains(textoFiltro)
                || pro.getCaracteristicas().toUpperCase().contains(textoFiltro)
                || pro.getFabricante().toUpperCase().contains(textoFiltro)
                || String.valueOf(pro.getNumeroLote()).contains(textoFiltro)
                || String.valueOf(pro.getCantidad()).contains(textoFiltro);
    }

    public void eliminar(){
        listaInventario.remove(pro);
        inventarioEJB.remove(pro);
        pro = null;
    }
    
    public void establecerProducto(Inventario pro) {
        this.pro = pro;
    }

    public Inventario getPro() {
        return pro;
    }

    public void setPro(Inventario pro) {
        this.pro = pro;
    }

    public List<Inventario> getListaInventario() {
        return listaInventario;
    }

    public void setListaInventario(List<Inventario> listaInventario) {
        this.listaInventario = listaInventario;
    }

    public List<Inventario> getInventarioFiltrado() {
        return inventarioFiltrado;
    }

    public void setInventarioFiltrado(List<Inventario> inventarioFiltrado) {
        this.inventarioFiltrado = inventarioFiltrado;
    }

    public List<FilterMeta> getFiltrarPor() {
        return filtrarPor;
    }

    public void setFiltrarPor(List<FilterMeta> filtrarPor) {
        this.filtrarPor = filtrarPor;
    }
    
}
