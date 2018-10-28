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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author DESARROLLO
 */
@Entity
@Table(name = "detallecontrato")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Detallecontrato.findAll", query = "SELECT d FROM Detallecontrato d")
    , @NamedQuery(name = "Detallecontrato.findByIdDetalleContratoConcep", query = "SELECT d FROM Detallecontrato d WHERE d.idDetalleContratoConcep = :idDetalleContratoConcep")
    , @NamedQuery(name = "Detallecontrato.findByFechaReg", query = "SELECT d FROM Detallecontrato d WHERE d.fechaReg = :fechaReg")
    , @NamedQuery(name = "Detallecontrato.findByValor", query = "SELECT d FROM Detallecontrato d WHERE d.valor = :valor")
    , @NamedQuery(name = "Detallecontrato.findByValorTotal", query = "SELECT d FROM Detallecontrato d WHERE d.valorTotal = :valorTotal")
    , @NamedQuery(name = "Detallecontrato.findByValorPagado", query = "SELECT d FROM Detallecontrato d WHERE d.valorPagado = :valorPagado")
    , @NamedQuery(name = "Detallecontrato.findByPeriodoIni", query = "SELECT d FROM Detallecontrato d WHERE d.periodoIni = :periodoIni")
    , @NamedQuery(name = "Detallecontrato.findByPeriodoFin", query = "SELECT d FROM Detallecontrato d WHERE d.periodoFin = :periodoFin")
    , @NamedQuery(name = "Detallecontrato.findByPorcIva", query = "SELECT d FROM Detallecontrato d WHERE d.porcIva = :porcIva")
    , @NamedQuery(name = "Detallecontrato.findByEstado", query = "SELECT d FROM Detallecontrato d WHERE d.estado = :estado")})
public class Detallecontrato implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idDetalleContratoConcep")
    private Integer idDetalleContratoConcep;
    @Column(name = "fecha_reg")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaReg;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor")
    private Double valor;
    @Column(name = "valor_total")
    private Double valorTotal;
    @Column(name = "valor_pagado")
    private Double valorPagado;
    @Column(name = "periodo_ini")
    @Temporal(TemporalType.TIMESTAMP)
    private Date periodoIni;
    @Column(name = "periodo_fin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date periodoFin;
    @Column(name = "porc_iva")
    private Integer porcIva;
    @Size(max = 11)
    @Column(name = "estado")
    private String estado;
    @JoinColumn(name = "idconc", referencedColumnName = "idconcepto")
    @ManyToOne(optional = false)
    private Concepto idconc;
    @JoinColumn(name = "idcontra", referencedColumnName = "numcontrato")
    @ManyToOne(optional = false)
    private Contrato idcontra;

    public Detallecontrato() {
    }

    public Detallecontrato(Integer idDetalleContratoConcep) {
        this.idDetalleContratoConcep = idDetalleContratoConcep;
    }

    public Integer getIdDetalleContratoConcep() {
        return idDetalleContratoConcep;
    }

    public void setIdDetalleContratoConcep(Integer idDetalleContratoConcep) {
        this.idDetalleContratoConcep = idDetalleContratoConcep;
    }

    public Date getFechaReg() {
        return fechaReg;
    }

    public void setFechaReg(Date fechaReg) {
        this.fechaReg = fechaReg;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Double getValorPagado() {
        return valorPagado;
    }

    public void setValorPagado(Double valorPagado) {
        this.valorPagado = valorPagado;
    }

    public Date getPeriodoIni() {
        return periodoIni;
    }

    public void setPeriodoIni(Date periodoIni) {
        this.periodoIni = periodoIni;
    }

    public Date getPeriodoFin() {
        return periodoFin;
    }

    public void setPeriodoFin(Date periodoFin) {
        this.periodoFin = periodoFin;
    }

    public Integer getPorcIva() {
        return porcIva;
    }

    public void setPorcIva(Integer porcIva) {
        this.porcIva = porcIva;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Concepto getIdconc() {
        return idconc;
    }

    public void setIdconc(Concepto idconc) {
        this.idconc = idconc;
    }

    public Contrato getIdcontra() {
        return idcontra;
    }

    public void setIdcontra(Contrato idcontra) {
        this.idcontra = idcontra;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDetalleContratoConcep != null ? idDetalleContratoConcep.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Detallecontrato)) {
            return false;
        }
        Detallecontrato other = (Detallecontrato) object;
        if ((this.idDetalleContratoConcep == null && other.idDetalleContratoConcep != null) || (this.idDetalleContratoConcep != null && !this.idDetalleContratoConcep.equals(other.idDetalleContratoConcep))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.demojsf.jpa.model.Detallecontrato[ idDetalleContratoConcep=" + idDetalleContratoConcep + " ]";
    }
    
}
