/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author andre
 */

@Entity
@Table(name="ingresos")

public class Ingreso implements Serializable{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int idIngreso;
    
    @Column(name="fechaAlta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAlta;
    
    @Column(name="fechaIngreso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaIngreso;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "ingreso_constantes", 
        joinColumns = @JoinColumn(name = "ingreso"), 
        inverseJoinColumns = @JoinColumn(name = "constantes"))
    private List<Constantes> constantes = new ArrayList<>();
    
    @Column(name="diasIngreso")
    private int diasIngreso;

    public int getIdIngreso() {
        return idIngreso;
    }

    public void setIdIngreso(int idIngreso) {
        this.idIngreso = idIngreso;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public List<Constantes> getConstantes() {
        return constantes;
    }

    public void setConstantes(List<Constantes> constantes) {
        this.constantes = constantes;
    }

    public int getDiasIngreso() {
        return diasIngreso;
    }

    public void setDiasIngreso(int diasIngreso) {
        this.diasIngreso = diasIngreso;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + this.idIngreso;
        hash = 47 * hash + Objects.hashCode(this.fechaAlta);
        hash = 47 * hash + Objects.hashCode(this.fechaIngreso);
        hash = 47 * hash + Objects.hashCode(this.constantes);
        hash = 47 * hash + this.diasIngreso;
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
        final Ingreso other = (Ingreso) obj;
        if (this.idIngreso != other.idIngreso) {
            return false;
        }
        if (this.diasIngreso != other.diasIngreso) {
            return false;
        }
        if (!Objects.equals(this.fechaAlta, other.fechaAlta)) {
            return false;
        }
        if (!Objects.equals(this.fechaIngreso, other.fechaIngreso)) {
            return false;
        }
        if (!Objects.equals(this.constantes, other.constantes)) {
            return false;
        }
        return true;
    }

}
