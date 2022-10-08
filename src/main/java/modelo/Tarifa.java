/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author andre
 */

@Entity
@Table(name="tarifas")
public class Tarifa implements Serializable{
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int idTarifa;    
    
    @Column(name="tratamiento")
    private String tratamiento;
    
    @Column(name="precio")
    private double precio;
    
    @Column(name="descAlumnos")
    private double descAlumnos;
    
    @Column(name="descProtectora")
    private double descProtectora;
    
    @ManyToMany(mappedBy = "tratamientos")
    private Set<Historial> usadaEn;
    
    @JoinColumn(name="campo")
    @ManyToOne
    private Campo campo;

    public int getIdTarifa() {
        return idTarifa;
    }

    public void setIdTarifa(int idTarifa) {
        this.idTarifa = idTarifa;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getDescAlumnos() {
        return descAlumnos;
    }

    public void setDescAlumnos(double descAlumnos) {
        this.descAlumnos = descAlumnos;
    }

    public double getDescProtectora() {
        return descProtectora;
    }

    public void setDescProtectora(double descProtectora) {
        this.descProtectora = descProtectora;
    }

    public Set<Historial> getUsadaEn() {
        return usadaEn;
    }

    public void setUsadaEn(Set<Historial> usadaEn) {
        this.usadaEn = usadaEn;
    }

    public Campo getCampo() {
        return campo;
    }

    public void setCampo(Campo campo) {
        this.campo = campo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.idTarifa;
        hash = 79 * hash + Objects.hashCode(this.tratamiento);
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.precio) ^ (Double.doubleToLongBits(this.precio) >>> 32));
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.descAlumnos) ^ (Double.doubleToLongBits(this.descAlumnos) >>> 32));
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.descProtectora) ^ (Double.doubleToLongBits(this.descProtectora) >>> 32));
//        hash = 79 * hash + Objects.hashCode(this.usadaEn);
        hash = 79 * hash + Objects.hashCode(this.campo);
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
        final Tarifa other = (Tarifa) obj;
        if (this.idTarifa != other.idTarifa) {
            return false;
        }
        if (Double.doubleToLongBits(this.precio) != Double.doubleToLongBits(other.precio)) {
            return false;
        }
        if (Double.doubleToLongBits(this.descAlumnos) != Double.doubleToLongBits(other.descAlumnos)) {
            return false;
        }
        if (Double.doubleToLongBits(this.descProtectora) != Double.doubleToLongBits(other.descProtectora)) {
            return false;
        }
        if (!Objects.equals(this.tratamiento, other.tratamiento)) {
            return false;
        }
//        if (!Objects.equals(this.usadaEn, other.usadaEn)) {
//            return false;
//        }
        if (!Objects.equals(this.campo, other.campo)) {
            return false;
        }
        return true;
    }
      
    
}
