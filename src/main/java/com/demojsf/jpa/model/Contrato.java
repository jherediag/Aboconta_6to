/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demojsf.jpa.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author DESARROLLO
 */
@Entity
@Table(name = "contrato")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Contrato.findAll", query = "SELECT c FROM Contrato c")
    , @NamedQuery(name = "Contrato.findByNumcontrato", query = "SELECT c FROM Contrato c WHERE c.numcontrato = :numcontrato")
    , @NamedQuery(name = "Contrato.findByFeccreacion", query = "SELECT c FROM Contrato c WHERE c.feccreacion = :feccreacion")
    , @NamedQuery(name = "Contrato.findByFecinicio", query = "SELECT c FROM Contrato c WHERE c.fecinicio = :fecinicio")
    , @NamedQuery(name = "Contrato.findByFecvenc", query = "SELECT c FROM Contrato c WHERE c.fecvenc = :fecvenc")
    , @NamedQuery(name = "Contrato.findByValor", query = "SELECT c FROM Contrato c WHERE c.valor = :valor")
    , @NamedQuery(name = "Contrato.findByObservacion", query = "SELECT c FROM Contrato c WHERE c.observacion = :observacion")
    , @NamedQuery(name = "Contrato.findByEstado", query = "SELECT c FROM Contrato c WHERE c.estado = :estado")})
public class Contrato implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "numcontrato")
    private Integer numcontrato;
    @Column(name = "feccreacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date feccreacion;
    @Column(name = "fecinicio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecinicio;
    @Column(name = "fecvenc")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecvenc;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor")
    private Double valor;
    @Size(max = 100)
    @Column(name = "observacion")
    private String observacion;
    @Size(max = 11)
    @Column(name = "estado")
    private String estado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idcontra")
    private Collection<Detallecontrato> detallecontratoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idcontra")
    private Collection<Factura> facturaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idcontrato")
    private Collection<Comision> comisionCollection;
    @JoinColumn(name = "idclie", referencedColumnName = "idcliente")
    @ManyToOne(optional = false)
    private Clientes idclie;
    @JoinColumn(name = "idpropied", referencedColumnName = "idpropiedad")
    @ManyToOne(optional = false)
    private Propiedad idpropied;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idcontrato")
    private Collection<Recaudo> recaudoCollection;

    public Contrato() {
    }

    public Contrato(Integer numcontrato) {
        this.numcontrato = numcontrato;
    }

    public Integer getNumcontrato() {
        return numcontrato;
    }

    public void setNumcontrato(Integer numcontrato) {
        this.numcontrato = numcontrato;
    }

    public Date getFeccreacion() {
        return feccreacion;
    }

    public void setFeccreacion(Date feccreacion) {
        this.feccreacion = feccreacion;
    }

    public Date getFecinicio() {
        return fecinicio;
    }

    public void setFecinicio(Date fecinicio) {
        this.fecinicio = fecinicio;
    }

    public Date getFecvenc() {
        return fecvenc;
    }

    public void setFecvenc(Date fecvenc) {
        this.fecvenc = fecvenc;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @XmlTransient
    public Collection<Detallecontrato> getDetallecontratoCollection() {
        return detallecontratoCollection;
    }

    public void setDetallecontratoCollection(Collection<Detallecontrato> detallecontratoCollection) {
        this.detallecontratoCollection = detallecontratoCollection;
    }

    @XmlTransient
    public Collection<Factura> getFacturaCollection() {
        return facturaCollection;
    }

    public void setFacturaCollection(Collection<Factura> facturaCollection) {
        this.facturaCollection = facturaCollection;
    }

    @XmlTransient
    public Collection<Comision> getComisionCollection() {
        return comisionCollection;
    }

    public void setComisionCollection(Collection<Comision> comisionCollection) {
        this.comisionCollection = comisionCollection;
    }

    public Clientes getIdclie() {
        return idclie;
    }

    public void setIdclie(Clientes idclie) {
        this.idclie = idclie;
    }

    public Propiedad getIdpropied() {
        return idpropied;
    }

    public void setIdpropied(Propiedad idpropied) {
        this.idpropied = idpropied;
    }

    @XmlTransient
    public Collection<Recaudo> getRecaudoCollection() {
        return recaudoCollection;
    }

    public void setRecaudoCollection(Collection<Recaudo> recaudoCollection) {
        this.recaudoCollection = recaudoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numcontrato != null ? numcontrato.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Contrato)) {
            return false;
        }
        Contrato other = (Contrato) object;
        if ((this.numcontrato == null && other.numcontrato != null) || (this.numcontrato != null && !this.numcontrato.equals(other.numcontrato))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.demojsf.jpa.model.Contrato[ numcontrato=" + numcontrato + " ]";
    }
    
}
