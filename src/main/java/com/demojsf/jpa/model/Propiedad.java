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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "propiedad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Propiedad.findAll", query = "SELECT p FROM Propiedad p")
    , @NamedQuery(name = "Propiedad.findByCodigo", query = "SELECT p FROM Propiedad p WHERE p.propiedadPK.codigo = :codigo")
    , @NamedQuery(name = "Propiedad.findByNombre", query = "SELECT p FROM Propiedad p WHERE p.nombre = :nombre")
    , @NamedQuery(name = "Propiedad.findByPais", query = "SELECT p FROM Propiedad p WHERE p.pais = :pais")
    , @NamedQuery(name = "Propiedad.findByCiudad", query = "SELECT p FROM Propiedad p WHERE p.ciudad = :ciudad")
    , @NamedQuery(name = "Propiedad.findByBarrio", query = "SELECT p FROM Propiedad p WHERE p.barrio = :barrio")
    , @NamedQuery(name = "Propiedad.findBySector", query = "SELECT p FROM Propiedad p WHERE p.sector = :sector")
    , @NamedQuery(name = "Propiedad.findByZona", query = "SELECT p FROM Propiedad p WHERE p.zona = :zona")
    , @NamedQuery(name = "Propiedad.findByDireccion", query = "SELECT p FROM Propiedad p WHERE p.direccion = :direccion")
    , @NamedQuery(name = "Propiedad.findByAreaTot", query = "SELECT p FROM Propiedad p WHERE p.areaTot = :areaTot")
    , @NamedQuery(name = "Propiedad.findByAreaCons", query = "SELECT p FROM Propiedad p WHERE p.areaCons = :areaCons")
    , @NamedQuery(name = "Propiedad.findByEstrato", query = "SELECT p FROM Propiedad p WHERE p.estrato = :estrato")
    , @NamedQuery(name = "Propiedad.findByTipo", query = "SELECT p FROM Propiedad p WHERE p.tipo = :tipo")
    , @NamedQuery(name = "Propiedad.findByValor", query = "SELECT p FROM Propiedad p WHERE p.valor = :valor")
    , @NamedQuery(name = "Propiedad.findByObservacion", query = "SELECT p FROM Propiedad p WHERE p.observacion = :observacion")
    , @NamedQuery(name = "Propiedad.findByEstOcupacion", query = "SELECT p FROM Propiedad p WHERE p.estOcupacion = :estOcupacion")
    , @NamedQuery(name = "Propiedad.findByEstFacturacion", query = "SELECT p FROM Propiedad p WHERE p.estFacturacion = :estFacturacion")
    , @NamedQuery(name = "Propiedad.findByIdpropiedad", query = "SELECT p FROM Propiedad p WHERE p.propiedadPK.idpropiedad = :idpropiedad")})
public class Propiedad implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PropiedadPK propiedadPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 20)
    @Column(name = "pais")
    private String pais;
    @Size(max = 20)
    @Column(name = "ciudad")
    private String ciudad;
    @Size(max = 20)
    @Column(name = "barrio")
    private String barrio;
    @Size(max = 20)
    @Column(name = "sector")
    private String sector;
    @Size(max = 20)
    @Column(name = "zona")
    private String zona;
    @Size(max = 50)
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "area_tot")
    private Integer areaTot;
    @Column(name = "area_cons")
    private Integer areaCons;
    @Size(max = 1)
    @Column(name = "estrato")
    private String estrato;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "tipo")
    private String tipo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor")
    private Double valor;
    @Size(max = 100)
    @Column(name = "observacion")
    private String observacion;
    @Size(max = 1)
    @Column(name = "est_ocupacion")
    private String estOcupacion;
    @Size(max = 1)
    @Column(name = "est_facturacion")
    private String estFacturacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idpropied")
    private Collection<Factura> facturaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idpropiedad")
    private Collection<Comision> comisionCollection;
    @JoinColumn(name = "idpropietario", referencedColumnName = "idpropietario")
    @ManyToOne(optional = false)
    private Propietarios idpropietario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idpropied")
    private Collection<Contrato> contratoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idpropiedad")
    private Collection<Recaudo> recaudoCollection;

    public Propiedad() {
    }

    public Propiedad(PropiedadPK propiedadPK) {
        this.propiedadPK = propiedadPK;
    }

    public Propiedad(PropiedadPK propiedadPK, String nombre, String tipo) {
        this.propiedadPK = propiedadPK;
        this.nombre = nombre;
        this.tipo = tipo;
    }

    public Propiedad(String codigo, int idpropiedad) {
        this.propiedadPK = new PropiedadPK(codigo, idpropiedad);
    }

    public PropiedadPK getPropiedadPK() {
        return propiedadPK;
    }

    public void setPropiedadPK(PropiedadPK propiedadPK) {
        this.propiedadPK = propiedadPK;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Integer getAreaTot() {
        return areaTot;
    }

    public void setAreaTot(Integer areaTot) {
        this.areaTot = areaTot;
    }

    public Integer getAreaCons() {
        return areaCons;
    }

    public void setAreaCons(Integer areaCons) {
        this.areaCons = areaCons;
    }

    public String getEstrato() {
        return estrato;
    }

    public void setEstrato(String estrato) {
        this.estrato = estrato;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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

    public String getEstOcupacion() {
        return estOcupacion;
    }

    public void setEstOcupacion(String estOcupacion) {
        this.estOcupacion = estOcupacion;
    }

    public String getEstFacturacion() {
        return estFacturacion;
    }

    public void setEstFacturacion(String estFacturacion) {
        this.estFacturacion = estFacturacion;
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

    public Propietarios getIdpropietario() {
        return idpropietario;
    }

    public void setIdpropietario(Propietarios idpropietario) {
        this.idpropietario = idpropietario;
    }

    @XmlTransient
    public Collection<Contrato> getContratoCollection() {
        return contratoCollection;
    }

    public void setContratoCollection(Collection<Contrato> contratoCollection) {
        this.contratoCollection = contratoCollection;
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
        hash += (propiedadPK != null ? propiedadPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Propiedad)) {
            return false;
        }
        Propiedad other = (Propiedad) object;
        if ((this.propiedadPK == null && other.propiedadPK != null) || (this.propiedadPK != null && !this.propiedadPK.equals(other.propiedadPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.demojsf.jpa.model.Propiedad[ propiedadPK=" + propiedadPK + " ]";
    }
    
}
