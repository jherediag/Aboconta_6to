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
@Table(name = "factura")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Factura.findAll", query = "SELECT f FROM Factura f")
    , @NamedQuery(name = "Factura.findByIdfactura", query = "SELECT f FROM Factura f WHERE f.idfactura = :idfactura")
    , @NamedQuery(name = "Factura.findByConsFactura", query = "SELECT f FROM Factura f WHERE f.consFactura = :consFactura")
    , @NamedQuery(name = "Factura.findByPrefijo", query = "SELECT f FROM Factura f WHERE f.prefijo = :prefijo")
    , @NamedQuery(name = "Factura.findByResDian", query = "SELECT f FROM Factura f WHERE f.resDian = :resDian")
    , @NamedQuery(name = "Factura.findByFactAutori", query = "SELECT f FROM Factura f WHERE f.factAutori = :factAutori")
    , @NamedQuery(name = "Factura.findByFechaCreacion", query = "SELECT f FROM Factura f WHERE f.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "Factura.findByFechaFacturacion", query = "SELECT f FROM Factura f WHERE f.fechaFacturacion = :fechaFacturacion")
    , @NamedQuery(name = "Factura.findByFechavencFact", query = "SELECT f FROM Factura f WHERE f.fechavencFact = :fechavencFact")
    , @NamedQuery(name = "Factura.findByDias", query = "SELECT f FROM Factura f WHERE f.dias = :dias")
    , @NamedQuery(name = "Factura.findByObservacion", query = "SELECT f FROM Factura f WHERE f.observacion = :observacion")
    , @NamedQuery(name = "Factura.findByValorCanon", query = "SELECT f FROM Factura f WHERE f.valorCanon = :valorCanon")
    , @NamedQuery(name = "Factura.findByValorOtros", query = "SELECT f FROM Factura f WHERE f.valorOtros = :valorOtros")
    , @NamedQuery(name = "Factura.findByValorIva", query = "SELECT f FROM Factura f WHERE f.valorIva = :valorIva")
    , @NamedQuery(name = "Factura.findByValorTotal", query = "SELECT f FROM Factura f WHERE f.valorTotal = :valorTotal")
    , @NamedQuery(name = "Factura.findByValorSaldoCxc", query = "SELECT f FROM Factura f WHERE f.valorSaldoCxc = :valorSaldoCxc")
    , @NamedQuery(name = "Factura.findByEstadoFactura", query = "SELECT f FROM Factura f WHERE f.estadoFactura = :estadoFactura")})
public class Factura implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idfactura")
    private Integer idfactura;
    @Column(name = "cons_factura")
    private Integer consFactura;
    @Size(max = 15)
    @Column(name = "prefijo")
    private String prefijo;
    @Size(max = 65)
    @Column(name = "resDian")
    private String resDian;
    @Size(max = 65)
    @Column(name = "factAutori")
    private String factAutori;
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "fecha_facturacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFacturacion;
    @Column(name = "fecha_vencFact")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechavencFact;
    @Column(name = "dias")
    private Integer dias;
    @Size(max = 45)
    @Column(name = "observacion")
    private String observacion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor_canon")
    private Double valorCanon;
    @Size(max = 45)
    @Column(name = "valor_otros")
    private String valorOtros;
    @Column(name = "valor_iva")
    private Double valorIva;
    @Column(name = "valor_total")
    private Double valorTotal;
    @Column(name = "valor_saldo_cxc")
    private Double valorSaldoCxc;
    @Size(max = 10)
    @Column(name = "estado_factura")
    private String estadoFactura;
    @JoinColumn(name = "idclie", referencedColumnName = "idcliente")
    @ManyToOne(optional = false)
    private Clientes idclie;
    @JoinColumn(name = "idcontra", referencedColumnName = "numcontrato")
    @ManyToOne(optional = false)
    private Contrato idcontra;
    @JoinColumn(name = "idpropied", referencedColumnName = "idpropiedad")
    @ManyToOne(optional = false)
    private Propiedad idpropied;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idfactu")
    private Collection<DetalleFactura> detalleFacturaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idfactura")
    private Collection<Recaudo> recaudoCollection;

    public Factura() {
    }

    public Factura(Integer idfactura) {
        this.idfactura = idfactura;
    }

    public Integer getIdfactura() {
        return idfactura;
    }

    public void setIdfactura(Integer idfactura) {
        this.idfactura = idfactura;
    }

    public Integer getConsFactura() {
        return consFactura;
    }

    public void setConsFactura(Integer consFactura) {
        this.consFactura = consFactura;
    }

    public String getPrefijo() {
        return prefijo;
    }

    public void setPrefijo(String prefijo) {
        this.prefijo = prefijo;
    }

    public String getResDian() {
        return resDian;
    }

    public void setResDian(String resDian) {
        this.resDian = resDian;
    }

    public String getFactAutori() {
        return factAutori;
    }

    public void setFactAutori(String factAutori) {
        this.factAutori = factAutori;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaFacturacion() {
        return fechaFacturacion;
    }

    public void setFechaFacturacion(Date fechaFacturacion) {
        this.fechaFacturacion = fechaFacturacion;
    }

    public Date getFechavencFact() {
        return fechavencFact;
    }

    public void setFechavencFact(Date fechavencFact) {
        this.fechavencFact = fechavencFact;
    }

    public Integer getDias() {
        return dias;
    }

    public void setDias(Integer dias) {
        this.dias = dias;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Double getValorCanon() {
        return valorCanon;
    }

    public void setValorCanon(Double valorCanon) {
        this.valorCanon = valorCanon;
    }

    public String getValorOtros() {
        return valorOtros;
    }

    public void setValorOtros(String valorOtros) {
        this.valorOtros = valorOtros;
    }

    public Double getValorIva() {
        return valorIva;
    }

    public void setValorIva(Double valorIva) {
        this.valorIva = valorIva;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Double getValorSaldoCxc() {
        return valorSaldoCxc;
    }

    public void setValorSaldoCxc(Double valorSaldoCxc) {
        this.valorSaldoCxc = valorSaldoCxc;
    }

    public String getEstadoFactura() {
        return estadoFactura;
    }

    public void setEstadoFactura(String estadoFactura) {
        this.estadoFactura = estadoFactura;
    }

    public Clientes getIdclie() {
        return idclie;
    }

    public void setIdclie(Clientes idclie) {
        this.idclie = idclie;
    }

    public Contrato getIdcontra() {
        return idcontra;
    }

    public void setIdcontra(Contrato idcontra) {
        this.idcontra = idcontra;
    }

    public Propiedad getIdpropied() {
        return idpropied;
    }

    public void setIdpropied(Propiedad idpropied) {
        this.idpropied = idpropied;
    }

    @XmlTransient
    public Collection<DetalleFactura> getDetalleFacturaCollection() {
        return detalleFacturaCollection;
    }

    public void setDetalleFacturaCollection(Collection<DetalleFactura> detalleFacturaCollection) {
        this.detalleFacturaCollection = detalleFacturaCollection;
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
        hash += (idfactura != null ? idfactura.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Factura)) {
            return false;
        }
        Factura other = (Factura) object;
        if ((this.idfactura == null && other.idfactura != null) || (this.idfactura != null && !this.idfactura.equals(other.idfactura))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.demojsf.jpa.model.Factura[ idfactura=" + idfactura + " ]";
    }
    
}
