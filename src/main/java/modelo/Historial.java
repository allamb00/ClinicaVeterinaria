/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author andre
 */

@Entity
@Table(name="historial")

public class Historial implements Serializable{
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int idCaso;
    
    @JoinColumn(name="mascota")
    @ManyToOne
    private Mascota mascota;
    
    @JoinColumn(name="veterinario")
    @ManyToOne
    private Usuario veterinario;
    
    @Column(name="antecedentes")
    private String antecedentes;
    
    @Column(name="material")
    private String material;
    
    @JoinColumn(name="ingreso")
    @OneToOne
    private Ingreso ingreso;
    
    @Column(name="cuadroClinico")
    private String cuadroClinico;
    
    @Column(name="anamnesis")
    private String anamnesis;

    @Column(name="exploracion")
    private String exploracion;

    @Column(name="pruebas")
    private String pruebas;

    @Column(name="diagnostico")
    private String diagnostico;

    @Column(name="fechaCaso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCaso;
    
    @ManyToMany
    @JoinTable(
        name = "caso_tratamiento", 
        joinColumns = @JoinColumn(name = "caso"), 
        inverseJoinColumns = @JoinColumn(name = "tratamiento"))
    private List<Tarifa> tratamientos;

    public int getIdCaso() {
        return idCaso;
    }

    public void setIdCaso(int idCaso) {
        this.idCaso = idCaso;
    }

    public Mascota getMascota() {
        return mascota;
    }

    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }

    public Usuario getVeterinario() {
        return veterinario;
    }

    public void setVeterinario(Usuario veterinario) {
        this.veterinario = veterinario;
    }

    public String getAntecedentes() {
        return antecedentes;
    }

    public void setAntecedentes(String antecedentes) {
        this.antecedentes = antecedentes;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public Ingreso getIngreso() {
        return ingreso;
    }

    public void setIngreso(Ingreso ingreso) {
        this.ingreso = ingreso;
    }

    public String getCuadroClinico() {
        return cuadroClinico;
    }

    public void setCuadroClinico(String cuadroClinico) {
        this.cuadroClinico = cuadroClinico;
    }

    public String getAnamnesis() {
        return anamnesis;
    }

    public void setAnamnesis(String anamnesis) {
        this.anamnesis = anamnesis;
    }

    public String getExploracion() {
        return exploracion;
    }

    public void setExploracion(String exploracion) {
        this.exploracion = exploracion;
    }

    public String getPruebas() {
        return pruebas;
    }

    public void setPruebas(String pruebas) {
        this.pruebas = pruebas;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public Date getFechaCaso() {
        return fechaCaso;
    }

    public void setFechaCaso(Date fechaCaso) {
        this.fechaCaso = fechaCaso;
    }

    public List<Tarifa> getTratamientos() {
        return tratamientos;
    }

    public void setTratamientos(List<Tarifa> tratamientos) {
        this.tratamientos = tratamientos;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.idCaso;
        hash = 97 * hash + Objects.hashCode(this.mascota);
        hash = 97 * hash + Objects.hashCode(this.veterinario);
        hash = 97 * hash + Objects.hashCode(this.antecedentes);
        hash = 97 * hash + Objects.hashCode(this.material);
        hash = 97 * hash + Objects.hashCode(this.ingreso);
        hash = 97 * hash + Objects.hashCode(this.cuadroClinico);
        hash = 97 * hash + Objects.hashCode(this.anamnesis);
        hash = 97 * hash + Objects.hashCode(this.exploracion);
        hash = 97 * hash + Objects.hashCode(this.pruebas);
        hash = 97 * hash + Objects.hashCode(this.diagnostico);
        hash = 97 * hash + Objects.hashCode(this.fechaCaso);
        hash = 97 * hash + Objects.hashCode(this.tratamientos);
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
        final Historial other = (Historial) obj;
        if (this.idCaso != other.idCaso) {
            return false;
        }
        if (!Objects.equals(this.antecedentes, other.antecedentes)) {
            return false;
        }
        if (!Objects.equals(this.material, other.material)) {
            return false;
        }
        if (!Objects.equals(this.cuadroClinico, other.cuadroClinico)) {
            return false;
        }
        if (!Objects.equals(this.anamnesis, other.anamnesis)) {
            return false;
        }
        if (!Objects.equals(this.exploracion, other.exploracion)) {
            return false;
        }
        if (!Objects.equals(this.pruebas, other.pruebas)) {
            return false;
        }
        if (!Objects.equals(this.diagnostico, other.diagnostico)) {
            return false;
        }
        if (!Objects.equals(this.mascota, other.mascota)) {
            return false;
        }
        if (!Objects.equals(this.veterinario, other.veterinario)) {
            return false;
        }
        if (!Objects.equals(this.ingreso, other.ingreso)) {
            return false;
        }
        if (!Objects.equals(this.fechaCaso, other.fechaCaso)) {
            return false;
        }
        if (!Objects.equals(this.tratamientos, other.tratamientos)) {
            return false;
        }
        return true;
    }
    
}
