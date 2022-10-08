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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author andre
 */

@Entity
@Table(name="facturas")
public class Factura implements Serializable{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int idFactura;
        
    @Column(name="tipoPago")
    private String tipoPago;
    
    @Column(name="iva")
    private double iva;
    
    @Column(name="total")
    private double total;
    
    @Column(name="subtotal")
    private double subtotal;
    
    @Column(name="descuento")
    private double descuento;
    
    @Column(name="fechaCobro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCobro;
    
    @JoinColumn(name="porUsuario")
    @ManyToOne
    private Usuario porUsuario;

    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    public String getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(String tipoPago) {
        this.tipoPago = tipoPago;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public Date getFechaCobro() {
        return fechaCobro;
    }

    public void setFechaCobro(Date fechaCobro) {
        this.fechaCobro = fechaCobro;
    }

    public Usuario getPorUsuario() {
        return porUsuario;
    }

    public void setPorUsuario(Usuario porUsuario) {
        this.porUsuario = porUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + this.idFactura;
        hash = 41 * hash + Objects.hashCode(this.tipoPago);
        hash = 41 * hash + (int) (Double.doubleToLongBits(this.iva) ^ (Double.doubleToLongBits(this.iva) >>> 32));
        hash = 41 * hash + (int) (Double.doubleToLongBits(this.total) ^ (Double.doubleToLongBits(this.total) >>> 32));
        hash = 41 * hash + (int) (Double.doubleToLongBits(this.subtotal) ^ (Double.doubleToLongBits(this.subtotal) >>> 32));
        hash = 41 * hash + (int) (Double.doubleToLongBits(this.descuento) ^ (Double.doubleToLongBits(this.descuento) >>> 32));
        hash = 41 * hash + Objects.hashCode(this.fechaCobro);
        hash = 41 * hash + Objects.hashCode(this.porUsuario);
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
        final Factura other = (Factura) obj;
        if (this.idFactura != other.idFactura) {
            return false;
        }
        if (Double.doubleToLongBits(this.iva) != Double.doubleToLongBits(other.iva)) {
            return false;
        }
        if (Double.doubleToLongBits(this.total) != Double.doubleToLongBits(other.total)) {
            return false;
        }
        if (Double.doubleToLongBits(this.subtotal) != Double.doubleToLongBits(other.subtotal)) {
            return false;
        }
        if (Double.doubleToLongBits(this.descuento) != Double.doubleToLongBits(other.descuento)) {
            return false;
        }
        if (!Objects.equals(this.tipoPago, other.tipoPago)) {
            return false;
        }
        if (!Objects.equals(this.fechaCobro, other.fechaCobro)) {
            return false;
        }
        if (!Objects.equals(this.porUsuario, other.porUsuario)) {
            return false;
        }
        return true;
    }
    
       
}
