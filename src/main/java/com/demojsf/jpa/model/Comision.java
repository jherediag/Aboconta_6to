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
@Table(name = "comision")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Comision.findAll", query = "SELECT c FROM Comision c")
    , @NamedQuery(name = "Comision.findByIdcomision", query = "SELECT c FROM Comision c WHERE c.idcomision = :idcomision")
    , @NamedQuery(name = "Comision.findByFechaReg", query = "SELECT c FROM Comision c WHERE c.fechaReg = :fechaReg")
    , @NamedQuery(name = "Comision.findByPeriodoComi", query = "SELECT c FROM Comision c WHERE c.periodoComi = :periodoComi")
    , @NamedQuery(name = "Comision.findByObservacion", query = "SELECT c FROM Comision c WHERE c.observacion = :observacion")
    , @NamedQuery(name = "Comision.findByPorcComi", query = "SELECT c FROM Comision c WHERE c.porcComi = :porcComi")
    , @NamedQuery(name = "Comision.findByValorComi", query = "SELECT c FROM Comision c WHERE c.valorComi = :valorComi")
    , @NamedQuery(name = "Comision.findByValorCanon", query = "SELECT c FROM Comision c WHERE c.valorCanon = :valorCanon")
    , @NamedQuery(name = "Comision.findByValorPropietario", query = "SELECT c FROM Comision c WHERE c.valorPropietario = :valorPropietario")})
public class Comision implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcomision")
    private Integer idcomision;
    @Column(name = "fecha_reg")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaReg;
    @Column(name = "periodo_comi")
    private Integer periodoComi;
    @Size(max = 45)
    @Column(name = "observacion")
    private String observacion;
    @Column(name = "porc_comi")
    private Integer porcComi;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor_comi")
    private Double valorComi;
    @Column(name = "valor_canon")
    private Double valorCanon;
    @Column(name = "valor_propietario")
    private Double valorPropietario;
    @JoinColumn(name = "idcontrato", referencedColumnName = "numcontrato")
    @ManyToOne(optional = false)
    private Contrato idcontrato;
    @JoinColumn(name = "idpropiedad", referencedColumnName = "idpropiedad")
    @ManyToOne(optional = false)
    private Propiedad idpropiedad;
    @JoinColumn(name = "idpropietario", referencedColumnName = "idpropietario")
    @ManyToOne(optional = false)
    private Propietarios idpropietario;

    public Comision() {
    }

    public Comision(Integer idcomision) {
        this.idcomision = idcomision;
    }

    public Integer getIdcomision() {
        return idcomision;
    }

    public void setIdcomision(Integer idcomision) {
        this.idcomision = idcomision;
    }

    public Date getFechaReg() {
        return fechaReg;
    }

    public void setFechaReg(Date fechaReg) {
        this.fechaReg = fechaReg;
    }

    public Integer getPeriodoComi() {
        return periodoComi;
    }

    public void setPeriodoComi(Integer periodoComi) {
        this.periodoComi = periodoComi;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Integer getPorcComi() {
        return porcComi;
    }

    public void setPorcComi(Integer porcComi) {
        this.porcComi = porcComi;
    }

    public Double getValorComi() {
        return valorComi;
    }

    public void setValorComi(Double valorComi) {
        this.valorComi = valorComi;
    }

    public Double getValorCanon() {
        return valorCanon;
    }

    public void setValorCanon(Double valorCanon) {
        this.valorCanon = valorCanon;
    }

    public Double getValorPropietario() {
        return valorPropietario;
    }

    public void setValorPropietario(Double valorPropietario) {
        this.valorPropietario = valorPropietario;
    }

    public Contrato getIdcontrato() {
        return idcontrato;
    }

    public void setIdcontrato(Contrato idcontrato) {
        this.idcontrato = idcontrato;
    }

    public Propiedad getIdpropiedad() {
        return idpropiedad;
    }

    public void setIdpropiedad(Propiedad idpropiedad) {
        this.idpropiedad = idpropiedad;
    }

    public Propietarios getIdpropietario() {
        return idpropietario;
    }

    public void setIdpropietario(Propietarios idpropietario) {
        this.idpropietario = idpropietario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcomision != null ? idcomision.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Comision)) {
            return false;
        }
        Comision other = (Comision) object;
        if ((this.idcomision == null && other.idcomision != null) || (this.idcomision != null && !this.idcomision.equals(other.idcomision))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.demojsf.jpa.model.Comision[ idcomision=" + idcomision + " ]";
    }
    
}
