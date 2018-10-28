/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demojsf.jpa.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author DESARROLLO
 */
@Embeddable
public class PropiedadPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "codigo")
    private String codigo;
    @Basic(optional = false)
    @Column(name = "idpropiedad")
    private int idpropiedad;

    public PropiedadPK() {
    }

    public PropiedadPK(String codigo, int idpropiedad) {
        this.codigo = codigo;
        this.idpropiedad = idpropiedad;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getIdpropiedad() {
        return idpropiedad;
    }

    public void setIdpropiedad(int idpropiedad) {
        this.idpropiedad = idpropiedad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigo != null ? codigo.hashCode() : 0);
        hash += (int) idpropiedad;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PropiedadPK)) {
            return false;
        }
        PropiedadPK other = (PropiedadPK) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        if (this.idpropiedad != other.idpropiedad) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.demojsf.jpa.model.PropiedadPK[ codigo=" + codigo + ", idpropiedad=" + idpropiedad + " ]";
    }
    
}
