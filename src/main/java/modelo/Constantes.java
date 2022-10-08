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
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author andre
 */

@Entity
@Table(name="constantes")
public class Constantes implements Serializable{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int idConstantes;
    
    @Column(name="freqCardiaca")
    private int freqCardiaca;
    
    @Column(name="freqRespiratoria")
    private int freqRespiratoria;
    
    @Column(name="pulso")
    private String pulso;
    
    @Column(name="tiempoRellenoCapilar")
    private double tiempoRellenoCapilar;
    
    @Column(name="mucosas")
    private String mucosas;
    
    @Column(name="exploracionGanglios")
    private String exploracionGanglios;
    
    @Column(name="tRecPliegueCutaneo")
    private double tRecPliegueCutaneo;
    
    @Column(name="horaMedicion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date horaMedicion;
    
    @ManyToMany(mappedBy = "constantes", fetch = FetchType.EAGER)
    private List<Ingreso> usadaEn = new ArrayList();

    public int getIdConstantes() {
        return idConstantes;
    }

    public void setIdConstantes(int idConstantes) {
        this.idConstantes = idConstantes;
    }

    public int getFreqCardiaca() {
        return freqCardiaca;
    }

    public void setFreqCardiaca(int freqCardiaca) {
        this.freqCardiaca = freqCardiaca;
    }

    public int getFreqRespiratoria() {
        return freqRespiratoria;
    }

    public void setFreqRespiratoria(int freqRespiratoria) {
        this.freqRespiratoria = freqRespiratoria;
    }

    public String getPulso() {
        return pulso;
    }

    public void setPulso(String pulso) {
        this.pulso = pulso;
    }

    public double getTiempoRellenoCapilar() {
        return tiempoRellenoCapilar;
    }

    public void setTiempoRellenoCapilar(double tiempoRellenoCapilar) {
        this.tiempoRellenoCapilar = tiempoRellenoCapilar;
    }

    public String getMucosas() {
        return mucosas;
    }

    public void setMucosas(String mucosas) {
        this.mucosas = mucosas;
    }

    public String getExploracionGanglios() {
        return exploracionGanglios;
    }

    public void setExploracionGanglios(String exploracionGanglios) {
        this.exploracionGanglios = exploracionGanglios;
    }

    public double gettRecPliegueCutaneo() {
        return tRecPliegueCutaneo;
    }

    public void settRecPliegueCutaneo(double tRecPliegueCutaneo) {
        this.tRecPliegueCutaneo = tRecPliegueCutaneo;
    }

    public Date getHoraMedicion() {
        return horaMedicion;
    }

    public void setHoraMedicion(Date horaMedicion) {
        this.horaMedicion = horaMedicion;
    }

    public List<Ingreso> getUsadaEn() {
        return usadaEn;
    }

    public void setUsadaEn(List<Ingreso> usadaEn) {
        this.usadaEn = usadaEn;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.idConstantes;
        hash = 37 * hash + this.freqCardiaca;
        hash = 37 * hash + this.freqRespiratoria;
        hash = 37 * hash + Objects.hashCode(this.pulso);
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.tiempoRellenoCapilar) ^ (Double.doubleToLongBits(this.tiempoRellenoCapilar) >>> 32));
        hash = 37 * hash + Objects.hashCode(this.mucosas);
        hash = 37 * hash + Objects.hashCode(this.exploracionGanglios);
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.tRecPliegueCutaneo) ^ (Double.doubleToLongBits(this.tRecPliegueCutaneo) >>> 32));
        hash = 37 * hash + Objects.hashCode(this.horaMedicion);
//        hash = 37 * hash + Objects.hashCode(this.usadaEn);
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
        final Constantes other = (Constantes) obj;
        if (this.idConstantes != other.idConstantes) {
            return false;
        }
        if (this.freqCardiaca != other.freqCardiaca) {
            return false;
        }
        if (this.freqRespiratoria != other.freqRespiratoria) {
            return false;
        }
        if (Double.doubleToLongBits(this.tiempoRellenoCapilar) != Double.doubleToLongBits(other.tiempoRellenoCapilar)) {
            return false;
        }
        if (Double.doubleToLongBits(this.tRecPliegueCutaneo) != Double.doubleToLongBits(other.tRecPliegueCutaneo)) {
            return false;
        }
        if (!Objects.equals(this.pulso, other.pulso)) {
            return false;
        }
        if (!Objects.equals(this.mucosas, other.mucosas)) {
            return false;
        }
        if (!Objects.equals(this.exploracionGanglios, other.exploracionGanglios)) {
            return false;
        }
        if (!Objects.equals(this.horaMedicion, other.horaMedicion)) {
            return false;
        }
//        if (!Objects.equals(this.usadaEn, other.usadaEn)) {
//            return false;
//        }
        return true;
    }
    
}
