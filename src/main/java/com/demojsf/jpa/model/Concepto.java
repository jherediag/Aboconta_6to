/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demojsf.jpa.model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author DESARROLLO
 */
@Entity
@Table(name = "concepto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Concepto.findAll", query = "SELECT c FROM Concepto c")
    , @NamedQuery(name = "Concepto.findByIdconcepto", query = "SELECT c FROM Concepto c WHERE c.idconcepto = :idconcepto")
    , @NamedQuery(name = "Concepto.findByCodConcepto", query = "SELECT c FROM Concepto c WHERE c.codConcepto = :codConcepto")
    , @NamedQuery(name = "Concepto.findByNomConcepto", query = "SELECT c FROM Concepto c WHERE c.nomConcepto = :nomConcepto")
    , @NamedQuery(name = "Concepto.findByPorcentajeIva", query = "SELECT c FROM Concepto c WHERE c.porcentajeIva = :porcentajeIva")})
public class Concepto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idconcepto")
    private Integer idconcepto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "cod_concepto")
    private String codConcepto;
    @Size(max = 45)
    @Column(name = "nom_concepto")
    private String nomConcepto;
    @Column(name = "porcentaje_iva")
    private Integer porcentajeIva;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idconc")
    private Collection<Detallecontrato> detallecontratoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idconc")
    private Collection<DetalleFactura> detalleFacturaCollection;

    public Concepto() {
    }

    public Concepto(Integer idconcepto) {
        this.idconcepto = idconcepto;
    }

    public Concepto(Integer idconcepto, String codConcepto) {
        this.idconcepto = idconcepto;
        this.codConcepto = codConcepto;
    }

    public Integer getIdconcepto() {
        return idconcepto;
    }

    public void setIdconcepto(Integer idconcepto) {
        this.idconcepto = idconcepto;
    }

    public String getCodConcepto() {
        return codConcepto;
    }

    public void setCodConcepto(String codConcepto) {
        this.codConcepto = codConcepto;
    }

    public String getNomConcepto() {
        return nomConcepto;
    }

    public void setNomConcepto(String nomConcepto) {
        this.nomConcepto = nomConcepto;
    }

    public Integer getPorcentajeIva() {
        return porcentajeIva;
    }

    public void setPorcentajeIva(Integer porcentajeIva) {
        this.porcentajeIva = porcentajeIva;
    }

    @XmlTransient
    public Collection<Detallecontrato> getDetallecontratoCollection() {
        return detallecontratoCollection;
    }

    public void setDetallecontratoCollection(Collection<Detallecontrato> detallecontratoCollection) {
        this.detallecontratoCollection = detallecontratoCollection;
    }

    @XmlTransient
    public Collection<DetalleFactura> getDetalleFacturaCollection() {
        return detalleFacturaCollection;
    }

    public void setDetalleFacturaCollection(Collection<DetalleFactura> detalleFacturaCollection) {
        this.detalleFacturaCollection = detalleFacturaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idconcepto != null ? idconcepto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Concepto)) {
            return false;
        }
        Concepto other = (Concepto) object;
        if ((this.idconcepto == null && other.idconcepto != null) || (this.idconcepto != null && !this.idconcepto.equals(other.idconcepto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.demojsf.jpa.model.Concepto[ idconcepto=" + idconcepto + " ]";
    }
    
}
