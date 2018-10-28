/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demojsf.jpa.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author DESARROLLO
 */
@Entity
@Table(name = "config")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Config.findAll", query = "SELECT c FROM Config c")
    , @NamedQuery(name = "Config.findByIdconfig", query = "SELECT c FROM Config c WHERE c.idconfig = :idconfig")
    , @NamedQuery(name = "Config.findByNitEmpresa", query = "SELECT c FROM Config c WHERE c.nitEmpresa = :nitEmpresa")
    , @NamedQuery(name = "Config.findByRazonSocial", query = "SELECT c FROM Config c WHERE c.razonSocial = :razonSocial")
    , @NamedQuery(name = "Config.findByNomLegal", query = "SELECT c FROM Config c WHERE c.nomLegal = :nomLegal")
    , @NamedQuery(name = "Config.findByTelefonoEmp", query = "SELECT c FROM Config c WHERE c.telefonoEmp = :telefonoEmp")
    , @NamedQuery(name = "Config.findByDireccionEmp", query = "SELECT c FROM Config c WHERE c.direccionEmp = :direccionEmp")
    , @NamedQuery(name = "Config.findByEmailEmp", query = "SELECT c FROM Config c WHERE c.emailEmp = :emailEmp")
    , @NamedQuery(name = "Config.findBySitioWebEmp", query = "SELECT c FROM Config c WHERE c.sitioWebEmp = :sitioWebEmp")
    , @NamedQuery(name = "Config.findByCiudadEmp", query = "SELECT c FROM Config c WHERE c.ciudadEmp = :ciudadEmp")
    , @NamedQuery(name = "Config.findByPaisEmp", query = "SELECT c FROM Config c WHERE c.paisEmp = :paisEmp")
    , @NamedQuery(name = "Config.findByResoludian", query = "SELECT c FROM Config c WHERE c.resoludian = :resoludian")
    , @NamedQuery(name = "Config.findByFacturaDian", query = "SELECT c FROM Config c WHERE c.facturaDian = :facturaDian")
    , @NamedQuery(name = "Config.findByPrefijoDian", query = "SELECT c FROM Config c WHERE c.prefijoDian = :prefijoDian")
    , @NamedQuery(name = "Config.findByNumConsFact", query = "SELECT c FROM Config c WHERE c.numConsFact = :numConsFact")
    , @NamedQuery(name = "Config.findByRegimenEmp", query = "SELECT c FROM Config c WHERE c.regimenEmp = :regimenEmp")})
public class Config implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idconfig")
    private Integer idconfig;
    @Size(max = 20)
    @Column(name = "nit_empresa")
    private String nitEmpresa;
    @Size(max = 80)
    @Column(name = "razon_social")
    private String razonSocial;
    @Size(max = 80)
    @Column(name = "nom_legal")
    private String nomLegal;
    @Size(max = 25)
    @Column(name = "telefono_emp")
    private String telefonoEmp;
    @Size(max = 80)
    @Column(name = "direccion_emp")
    private String direccionEmp;
    @Size(max = 50)
    @Column(name = "email_emp")
    private String emailEmp;
    @Size(max = 50)
    @Column(name = "sitio_web_emp")
    private String sitioWebEmp;
    @Size(max = 45)
    @Column(name = "ciudad_emp")
    private String ciudadEmp;
    @Size(max = 25)
    @Column(name = "pais_emp")
    private String paisEmp;
    @Size(max = 80)
    @Column(name = "Resolu_dian")
    private String resoludian;
    @Size(max = 80)
    @Column(name = "factura_dian")
    private String facturaDian;
    @Size(max = 15)
    @Column(name = "prefijo_dian")
    private String prefijoDian;
    @Column(name = "num_cons_fact")
    private Integer numConsFact;
    @Size(max = 45)
    @Column(name = "regimen_emp")
    private String regimenEmp;

    public Config() {
    }

    public Config(Integer idconfig) {
        this.idconfig = idconfig;
    }

    public Integer getIdconfig() {
        return idconfig;
    }

    public void setIdconfig(Integer idconfig) {
        this.idconfig = idconfig;
    }

    public String getNitEmpresa() {
        return nitEmpresa;
    }

    public void setNitEmpresa(String nitEmpresa) {
        this.nitEmpresa = nitEmpresa;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getNomLegal() {
        return nomLegal;
    }

    public void setNomLegal(String nomLegal) {
        this.nomLegal = nomLegal;
    }

    public String getTelefonoEmp() {
        return telefonoEmp;
    }

    public void setTelefonoEmp(String telefonoEmp) {
        this.telefonoEmp = telefonoEmp;
    }

    public String getDireccionEmp() {
        return direccionEmp;
    }

    public void setDireccionEmp(String direccionEmp) {
        this.direccionEmp = direccionEmp;
    }

    public String getEmailEmp() {
        return emailEmp;
    }

    public void setEmailEmp(String emailEmp) {
        this.emailEmp = emailEmp;
    }

    public String getSitioWebEmp() {
        return sitioWebEmp;
    }

    public void setSitioWebEmp(String sitioWebEmp) {
        this.sitioWebEmp = sitioWebEmp;
    }

    public String getCiudadEmp() {
        return ciudadEmp;
    }

    public void setCiudadEmp(String ciudadEmp) {
        this.ciudadEmp = ciudadEmp;
    }

    public String getPaisEmp() {
        return paisEmp;
    }

    public void setPaisEmp(String paisEmp) {
        this.paisEmp = paisEmp;
    }

    public String getResoludian() {
        return resoludian;
    }

    public void setResoludian(String resoludian) {
        this.resoludian = resoludian;
    }

    public String getFacturaDian() {
        return facturaDian;
    }

    public void setFacturaDian(String facturaDian) {
        this.facturaDian = facturaDian;
    }

    public String getPrefijoDian() {
        return prefijoDian;
    }

    public void setPrefijoDian(String prefijoDian) {
        this.prefijoDian = prefijoDian;
    }

    public Integer getNumConsFact() {
        return numConsFact;
    }

    public void setNumConsFact(Integer numConsFact) {
        this.numConsFact = numConsFact;
    }

    public String getRegimenEmp() {
        return regimenEmp;
    }

    public void setRegimenEmp(String regimenEmp) {
        this.regimenEmp = regimenEmp;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idconfig != null ? idconfig.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Config)) {
            return false;
        }
        Config other = (Config) object;
        if ((this.idconfig == null && other.idconfig != null) || (this.idconfig != null && !this.idconfig.equals(other.idconfig))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.demojsf.jpa.model.Config[ idconfig=" + idconfig + " ]";
    }
    
}
