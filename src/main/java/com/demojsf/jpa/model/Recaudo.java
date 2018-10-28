/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demojsf.jpa.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author DESARROLLO
 */
@Entity
@Table(name = "recaudo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Recaudo.findAll", query = "SELECT r FROM Recaudo r")
    , @NamedQuery(name = "Recaudo.findByIdrecaudo", query = "SELECT r FROM Recaudo r WHERE r.idrecaudo = :idrecaudo")
    , @NamedQuery(name = "Recaudo.findByFechaReg", query = "SELECT r FROM Recaudo r WHERE r.fechaReg = :fechaReg")
    , @NamedQuery(name = "Recaudo.findByFechaRecaudo", query = "SELECT r FROM Recaudo r WHERE r.fechaRecaudo = :fechaRecaudo")
    , @NamedQuery(name = "Recaudo.findByNumConsRecaudo", query = "SELECT r FROM Recaudo r WHERE r.numConsRecaudo = :numConsRecaudo")
    , @NamedQuery(name = "Recaudo.findByFormaPago", query = "SELECT r FROM Recaudo r WHERE r.formaPago = :formaPago")
    , @NamedQuery(name = "Recaudo.findByObservacion", query = "SELECT r FROM Recaudo r WHERE r.observacion = :observacion")
    , @NamedQuery(name = "Recaudo.findByValor", query = "SELECT r FROM Recaudo r WHERE r.valor = :valor")
    , @NamedQuery(name = "Recaudo.findByValorFactura", query = "SELECT r FROM Recaudo r WHERE r.valorFactura = :valorFactura")
    , @NamedQuery(name = "Recaudo.findBySaldoAnt", query = "SELECT r FROM Recaudo r WHERE r.saldoAnt = :saldoAnt")
    , @NamedQuery(name = "Recaudo.findBySaldoNew", query = "SELECT r FROM Recaudo r WHERE r.saldoNew = :saldoNew")})
public class Recaudo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idrecaudo")
    private Integer idrecaudo;
    @Column(name = "fecha_reg")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaReg;
    @Column(name = "fecha_recaudo")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRecaudo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "num_cons_recaudo")
    private int numConsRecaudo;
    @Size(max = 45)
    @Column(name = "forma_pago")
    private String formaPago;
    @Size(max = 45)
    @Column(name = "observacion")
    private String observacion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor")
    private Double valor;
    @Column(name = "valor_factura")
    private Double valorFactura;
    @Column(name = "saldo_ant")
    private Double saldoAnt;
    @Column(name = "saldo_new")
    private Double saldoNew;
    @JoinColumn(name = "idcliente", referencedColumnName = "idcliente")
    @ManyToOne(optional = false)
    private Clientes idcliente;
    @JoinColumn(name = "idcontrato", referencedColumnName = "numcontrato")
    @ManyToOne(optional = false)
    private Contrato idcontrato;
    @JoinColumn(name = "idfactura", referencedColumnName = "idfactura")
    @ManyToOne(optional = false)
    private Factura idfactura;
    @JoinColumn(name = "idpropiedad", referencedColumnName = "idpropiedad")
    @ManyToOne(optional = false)
    private Propiedad idpropiedad;

    public Recaudo() {
    }

    public Recaudo(Integer idrecaudo) {
        this.idrecaudo = idrecaudo;
    }

    public Recaudo(Integer idrecaudo, int numConsRecaudo) {
        this.idrecaudo = idrecaudo;
        this.numConsRecaudo = numConsRecaudo;
    }

    public Integer getIdrecaudo() {
        return idrecaudo;
    }

    public void setIdrecaudo(Integer idrecaudo) {
        this.idrecaudo = idrecaudo;
    }

    public Date getFechaReg() {
        return fechaReg;
    }

    public void setFechaReg(Date fechaReg) {
        this.fechaReg = fechaReg;
    }

    public Date getFechaRecaudo() {
        return fechaRecaudo;
    }

    public void setFechaRecaudo(Date fechaRecaudo) {
        this.fechaRecaudo = fechaRecaudo;
    }

    public int getNumConsRecaudo() {
        return numConsRecaudo;
    }

    public void setNumConsRecaudo(int numConsRecaudo) {
        this.numConsRecaudo = numConsRecaudo;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Double getValorFactura() {
        return valorFactura;
    }

    public void setValorFactura(Double valorFactura) {
        this.valorFactura = valorFactura;
    }

    public Double getSaldoAnt() {
        return saldoAnt;
    }

    public void setSaldoAnt(Double saldoAnt) {
        this.saldoAnt = saldoAnt;
    }

    public Double getSaldoNew() {
        return saldoNew;
    }

    public void setSaldoNew(Double saldoNew) {
        this.saldoNew = saldoNew;
    }

    public Clientes getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(Clientes idcliente) {
        this.idcliente = idcliente;
    }

    public Contrato getIdcontrato() {
        return idcontrato;
    }

    public void setIdcontrato(Contrato idcontrato) {
        this.idcontrato = idcontrato;
    }

    public Factura getIdfactura() {
        return idfactura;
    }

    public void setIdfactura(Factura idfactura) {
        this.idfactura = idfactura;
    }

    public Propiedad getIdpropiedad() {
        return idpropiedad;
    }

    public void setIdpropiedad(Propiedad idpropiedad) {
        this.idpropiedad = idpropiedad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idrecaudo != null ? idrecaudo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Recaudo)) {
            return false;
        }
        Recaudo other = (Recaudo) object;
        if ((this.idrecaudo == null && other.idrecaudo != null) || (this.idrecaudo != null && !this.idrecaudo.equals(other.idrecaudo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.demojsf.jpa.model.Recaudo[ idrecaudo=" + idrecaudo + " ]";
    }
    
}
