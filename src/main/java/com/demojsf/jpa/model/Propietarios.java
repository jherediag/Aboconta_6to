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
@Table(name = "propietarios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Propietarios.findAll", query = "SELECT p FROM Propietarios p")
    , @NamedQuery(name = "Propietarios.findByCcnit", query = "SELECT p FROM Propietarios p WHERE p.ccnit = :ccnit")
    , @NamedQuery(name = "Propietarios.findByNombre", query = "SELECT p FROM Propietarios p WHERE p.nombre = :nombre")
    , @NamedQuery(name = "Propietarios.findByDireccion", query = "SELECT p FROM Propietarios p WHERE p.direccion = :direccion")
    , @NamedQuery(name = "Propietarios.findByTelefono", query = "SELECT p FROM Propietarios p WHERE p.telefono = :telefono")
    , @NamedQuery(name = "Propietarios.findByCelular", query = "SELECT p FROM Propietarios p WHERE p.celular = :celular")
    , @NamedQuery(name = "Propietarios.findByEmail", query = "SELECT p FROM Propietarios p WHERE p.email = :email")
    , @NamedQuery(name = "Propietarios.findByCiudad", query = "SELECT p FROM Propietarios p WHERE p.ciudad = :ciudad")
    , @NamedQuery(name = "Propietarios.findBySexo", query = "SELECT p FROM Propietarios p WHERE p.sexo = :sexo")
    , @NamedQuery(name = "Propietarios.findByPorccomi", query = "SELECT p FROM Propietarios p WHERE p.porccomi = :porccomi")
    , @NamedQuery(name = "Propietarios.findByIdpropietario", query = "SELECT p FROM Propietarios p WHERE p.idpropietario = :idpropietario")})
public class Propietarios implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 11)
    @Column(name = "ccnit")
    private String ccnit;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 50)
    @Column(name = "direccion")
    private String direccion;
    @Size(max = 20)
    @Column(name = "telefono")
    private String telefono;
    @Size(max = 20)
    @Column(name = "celular")
    private String celular;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 45)
    @Column(name = "email")
    private String email;
    @Size(max = 30)
    @Column(name = "ciudad")
    private String ciudad;
    @Size(max = 1)
    @Column(name = "sexo")
    private String sexo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "porccomi")
    private int porccomi;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpropietario")
    private Integer idpropietario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idpropietario")
    private Collection<Comision> comisionCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idpropietario")
    private Collection<Propiedad> propiedadCollection;

    public Propietarios() {
    }

    public Propietarios(Integer idpropietario) {
        this.idpropietario = idpropietario;
    }

    public Propietarios(Integer idpropietario, String ccnit, String nombre, int porccomi) {
        this.idpropietario = idpropietario;
        this.ccnit = ccnit;
        this.nombre = nombre;
        this.porccomi = porccomi;
    }

    public String getCcnit() {
        return ccnit;
    }

    public void setCcnit(String ccnit) {
        this.ccnit = ccnit;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public int getPorccomi() {
        return porccomi;
    }

    public void setPorccomi(int porccomi) {
        this.porccomi = porccomi;
    }

    public Integer getIdpropietario() {
        return idpropietario;
    }

    public void setIdpropietario(Integer idpropietario) {
        this.idpropietario = idpropietario;
    }

    @XmlTransient
    public Collection<Comision> getComisionCollection() {
        return comisionCollection;
    }

    public void setComisionCollection(Collection<Comision> comisionCollection) {
        this.comisionCollection = comisionCollection;
    }

    @XmlTransient
    public Collection<Propiedad> getPropiedadCollection() {
        return propiedadCollection;
    }

    public void setPropiedadCollection(Collection<Propiedad> propiedadCollection) {
        this.propiedadCollection = propiedadCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpropietario != null ? idpropietario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Propietarios)) {
            return false;
        }
        Propietarios other = (Propietarios) object;
        if ((this.idpropietario == null && other.idpropietario != null) || (this.idpropietario != null && !this.idpropietario.equals(other.idpropietario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.demojsf.jpa.model.Propietarios[ idpropietario=" + idpropietario + " ]";
    }
    
}
