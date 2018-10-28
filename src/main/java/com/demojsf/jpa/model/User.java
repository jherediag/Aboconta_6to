/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demojsf.jpa.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author DESARROLLO
 */
@Entity
@Table(name = "user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
    , @NamedQuery(name = "User.findByIduser", query = "SELECT u FROM User u WHERE u.userPK.iduser = :iduser")
    , @NamedQuery(name = "User.findByCorreo", query = "SELECT u FROM User u WHERE u.userPK.correo = :correo")
    , @NamedQuery(name = "User.findByClave", query = "SELECT u FROM User u WHERE u.clave = :clave")
    , @NamedQuery(name = "User.findByM1Reg", query = "SELECT u FROM User u WHERE u.m1Reg = :m1Reg")
    , @NamedQuery(name = "User.findByM11Proprio", query = "SELECT u FROM User u WHERE u.m11Proprio = :m11Proprio")
    , @NamedQuery(name = "User.findByM12Propdad", query = "SELECT u FROM User u WHERE u.m12Propdad = :m12Propdad")
    , @NamedQuery(name = "User.findByM13Cli", query = "SELECT u FROM User u WHERE u.m13Cli = :m13Cli")
    , @NamedQuery(name = "User.findByM14Cont", query = "SELECT u FROM User u WHERE u.m14Cont = :m14Cont")
    , @NamedQuery(name = "User.findByM2Fact", query = "SELECT u FROM User u WHERE u.m2Fact = :m2Fact")
    , @NamedQuery(name = "User.findByM3Rec", query = "SELECT u FROM User u WHERE u.m3Rec = :m3Rec")
    , @NamedQuery(name = "User.findByM4Com", query = "SELECT u FROM User u WHERE u.m4Com = :m4Com")
    , @NamedQuery(name = "User.findByM5Inf", query = "SELECT u FROM User u WHERE u.m5Inf = :m5Inf")})
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UserPK userPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "clave")
    private String clave;
    @Basic(optional = false)
    @NotNull
    @Column(name = "m1_reg")
    private short m1Reg;
    @Basic(optional = false)
    @NotNull
    @Column(name = "m11_proprio")
    private short m11Proprio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "m12_propdad")
    private short m12Propdad;
    @Basic(optional = false)
    @NotNull
    @Column(name = "m13_cli")
    private short m13Cli;
    @Basic(optional = false)
    @NotNull
    @Column(name = "m14_cont")
    private short m14Cont;
    @Basic(optional = false)
    @NotNull
    @Column(name = "m2_fact")
    private short m2Fact;
    @Basic(optional = false)
    @NotNull
    @Column(name = "m3_rec")
    private short m3Rec;
    @Basic(optional = false)
    @NotNull
    @Column(name = "m4_com")
    private short m4Com;
    @Basic(optional = false)
    @NotNull
    @Column(name = "m5_inf")
    private short m5Inf;

    public User() {
    }

    public User(UserPK userPK) {
        this.userPK = userPK;
    }

    public User(UserPK userPK, String clave, short m1Reg, short m11Proprio, short m12Propdad, short m13Cli, short m14Cont, short m2Fact, short m3Rec, short m4Com, short m5Inf) {
        this.userPK = userPK;
        this.clave = clave;
        this.m1Reg = m1Reg;
        this.m11Proprio = m11Proprio;
        this.m12Propdad = m12Propdad;
        this.m13Cli = m13Cli;
        this.m14Cont = m14Cont;
        this.m2Fact = m2Fact;
        this.m3Rec = m3Rec;
        this.m4Com = m4Com;
        this.m5Inf = m5Inf;
    }

    public User(int iduser, String correo) {
        this.userPK = new UserPK(iduser, correo);
    }

    public UserPK getUserPK() {
        return userPK;
    }

    public void setUserPK(UserPK userPK) {
        this.userPK = userPK;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public short getM1Reg() {
        return m1Reg;
    }

    public void setM1Reg(short m1Reg) {
        this.m1Reg = m1Reg;
    }

    public short getM11Proprio() {
        return m11Proprio;
    }

    public void setM11Proprio(short m11Proprio) {
        this.m11Proprio = m11Proprio;
    }

    public short getM12Propdad() {
        return m12Propdad;
    }

    public void setM12Propdad(short m12Propdad) {
        this.m12Propdad = m12Propdad;
    }

    public short getM13Cli() {
        return m13Cli;
    }

    public void setM13Cli(short m13Cli) {
        this.m13Cli = m13Cli;
    }

    public short getM14Cont() {
        return m14Cont;
    }

    public void setM14Cont(short m14Cont) {
        this.m14Cont = m14Cont;
    }

    public short getM2Fact() {
        return m2Fact;
    }

    public void setM2Fact(short m2Fact) {
        this.m2Fact = m2Fact;
    }

    public short getM3Rec() {
        return m3Rec;
    }

    public void setM3Rec(short m3Rec) {
        this.m3Rec = m3Rec;
    }

    public short getM4Com() {
        return m4Com;
    }

    public void setM4Com(short m4Com) {
        this.m4Com = m4Com;
    }

    public short getM5Inf() {
        return m5Inf;
    }

    public void setM5Inf(short m5Inf) {
        this.m5Inf = m5Inf;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userPK != null ? userPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.userPK == null && other.userPK != null) || (this.userPK != null && !this.userPK.equals(other.userPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.demojsf.jpa.model.User[ userPK=" + userPK + " ]";
    }
    
}
