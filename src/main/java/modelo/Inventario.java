/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author andre
 */

@Entity
@Table(name="inventario")

public class Inventario implements Serializable{
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int idProducto;
    
    @Column(name="nombre")
    private String nombre;
    
    @Column(name="fabricante")
    private String fabricante;
    
    @Column(name="cantidad")
    private int cantidad;
    
    @Column(name="caracteristicas")
    private String caracteristicas;
    
    @Column(name="numeroLote")
    private int numeroLote;
    
    @Column(name="almacenamiento")
    private String almacenamiento;
    
    @Column(name="fechaEntrada")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEntrada;

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(String caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public int getNumeroLote() {
        return numeroLote;
    }

    public void setNumeroLote(int numeroLote) {
        this.numeroLote = numeroLote;
    }

    public String getAlmacenamiento() {
        return almacenamiento;
    }

    public void setAlmacenamiento(String almacenamiento) {
        this.almacenamiento = almacenamiento;
    }

    public Date getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(Date fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + this.idProducto;
        hash = 67 * hash + Objects.hashCode(this.nombre);
        hash = 67 * hash + Objects.hashCode(this.fabricante);
        hash = 67 * hash + this.cantidad;
        hash = 67 * hash + Objects.hashCode(this.caracteristicas);
        hash = 67 * hash + this.numeroLote;
        hash = 67 * hash + Objects.hashCode(this.almacenamiento);
        hash = 67 * hash + Objects.hashCode(this.fechaEntrada);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Inventario other = (Inventario) obj;
        if (this.idProducto != other.idProducto) {
            return false;
        }
        if (this.cantidad != other.cantidad) {
            return false;
        }
        if (this.numeroLote != other.numeroLote) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.fabricante, other.fabricante)) {
            return false;
        }
        if (!Objects.equals(this.caracteristicas, other.caracteristicas)) {
            return false;
        }
        if (!Objects.equals(this.almacenamiento, other.almacenamiento)) {
            return false;
        }
        if (!Objects.equals(this.fechaEntrada, other.fechaEntrada)) {
            return false;
        }
        return true;
    }

}
