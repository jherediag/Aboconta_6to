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
@Table(name = "detalle_factura")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DetalleFactura.findAll", query = "SELECT d FROM DetalleFactura d")
    , @NamedQuery(name = "DetalleFactura.findByNomConcepto", query = "SELECT d FROM DetalleFactura d WHERE d.nomConcepto = :nomConcepto")
    , @NamedQuery(name = "DetalleFactura.findByPrecio", query = "SELECT d FROM DetalleFactura d WHERE d.precio = :precio")
    , @NamedQuery(name = "DetalleFactura.findByPorcentajeIva", query = "SELECT d FROM DetalleFactura d WHERE d.porcentajeIva = :porcentajeIva")
    , @NamedQuery(name = "DetalleFactura.findByValorIva", query = "SELECT d FROM DetalleFactura d WHERE d.valorIva = :valorIva")
    , @NamedQuery(name = "DetalleFactura.findByValorAntIva", query = "SELECT d FROM DetalleFactura d WHERE d.valorAntIva = :valorAntIva")
    , @NamedQuery(name = "DetalleFactura.findByIdDetalle", query = "SELECT d FROM DetalleFactura d WHERE d.idDetalle = :idDetalle")
    , @NamedQuery(name = "DetalleFactura.findByFechaRegistro", query = "SELECT d FROM DetalleFactura d WHERE d.fechaRegistro = :fechaRegistro")})
public class DetalleFactura implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nom_concepto")
    private String nomConcepto;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "precio")
    private Double precio;
    @Column(name = "porcentaje_iva")
    private Integer porcentajeIva;
    @Column(name = "valor_iva")
    private Double valorIva;
    @Column(name = "valor_ant_iva")
    private Double valorAntIva;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_detalle")
    private Integer idDetalle;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @JoinColumn(name = "idconc", referencedColumnName = "idconcepto")
    @ManyToOne(optional = false)
    private Concepto idconc;
    @JoinColumn(name = "idfactu", referencedColumnName = "idfactura")
    @ManyToOne(optional = false)
    private Factura idfactu;

    public DetalleFactura() {
    }

    public DetalleFactura(Integer idDetalle) {
        this.idDetalle = idDetalle;
    }

    public DetalleFactura(Integer idDetalle, String nomConcepto) {
        this.idDetalle = idDetalle;
        this.nomConcepto = nomConcepto;
    }

    public String getNomConcepto() {
        return nomConcepto;
    }

    public void setNomConcepto(String nomConcepto) {
        this.nomConcepto = nomConcepto;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getPorcentajeIva() {
        return porcentajeIva;
    }

    public void setPorcentajeIva(Integer porcentajeIva) {
        this.porcentajeIva = porcentajeIva;
    }

    public Double getValorIva() {
        return valorIva;
    }

    public void setValorIva(Double valorIva) {
        this.valorIva = valorIva;
    }

    public Double getValorAntIva() {
        return valorAntIva;
    }

    public void setValorAntIva(Double valorAntIva) {
        this.valorAntIva = valorAntIva;
    }

    public Integer getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(Integer idDetalle) {
        this.idDetalle = idDetalle;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Concepto getIdconc() {
        return idconc;
    }

    public void setIdconc(Concepto idconc) {
        this.idconc = idconc;
    }

    public Factura getIdfactu() {
        return idfactu;
    }

    public void setIdfactu(Factura idfactu) {
        this.idfactu = idfactu;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDetalle != null ? idDetalle.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleFactura)) {
            return false;
        }
        DetalleFactura other = (DetalleFactura) object;
        if ((this.idDetalle == null && other.idDetalle != null) || (this.idDetalle != null && !this.idDetalle.equals(other.idDetalle))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.demojsf.jpa.model.DetalleFactura[ idDetalle=" + idDetalle + " ]";
    }
    
}
