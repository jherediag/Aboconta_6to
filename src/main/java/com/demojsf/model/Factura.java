
package com.demojsf.model;

import java.util.Date;

 public class Factura {

    private int idfactura;
    private int cons_factura;
    private int num_contrato;
    private Date fecha_creacion;
    private Date fecha_facturacion;
    private Date fecha_vencimiento;
    private int dias;
    private String prefijo;
    private String resDian;
    private String rangoFactura;
    private String cod_propiedad;
    private String cc_nit_cliente;
    private String observacion;
    private double valorCanon;
    private double valorOtros;
    private double valorIva;
    private double valorTotal;
    private double saldoCXC;
    private String estado_factura;

    public Factura() {
        this.fecha_creacion=new Date();
        this.fecha_facturacion=new Date();
        this.fecha_vencimiento=new Date();
    }

    public String getResDian() {
        return resDian;
    }

    public void setResDian(String resDian) {
        this.resDian = resDian;
    }

    public String getRangoFactura() {
        return rangoFactura;
    }

    public void setRangoFactura(String rangoFactura) {
        this.rangoFactura = rangoFactura;
    }

    public double getValorCanon() {
        return valorCanon;
    }

    public void setValorCanon(double valorCanon) {
        this.valorCanon = valorCanon;
    }

    public double getValorOtros() {
        return valorOtros;
    }

    public void setValorOtros(double valorOtros) {
        this.valorOtros = valorOtros;
    }

    public double getValorIva() {
        return valorIva;
    }

    public void setValorIva(double valorIva) {
        this.valorIva = valorIva;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public double getSaldoCXC() {
        return saldoCXC;
    }

    public void setSaldoCXC(double saldoCXC) {
        this.saldoCXC = saldoCXC;
    }
    
    
    
    public int getIdfactura() {
        return idfactura;
    }

    public void setIdfactura(int idfactura) {
        this.idfactura = idfactura;
    }

    public int getCons_factura() {
        return cons_factura;
    }

    public void setCons_factura(int cons_factura) {
        this.cons_factura = cons_factura;
    }

    public int getNum_contrato() {
        return num_contrato;
    }

    public void setNum_contrato(int num_contrato) {
        this.num_contrato = num_contrato;
    }

    public Date getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(Date fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public Date getFecha_facturacion() {
        return fecha_facturacion;
    }

    public void setFecha_facturacion(Date fecha_facturacion) {
        this.fecha_facturacion = fecha_facturacion;
    }
    
    public Date getFecha_vencimiento() {
        return fecha_vencimiento;
    }

    public void setFecha_vencimiento(Date fecha_vencimiento) {
        this.fecha_vencimiento = fecha_vencimiento;
    }
    
    public int getDias() {
        return dias;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }

    public String getPrefijo() {
        return prefijo;
    }

    public void setPrefijo(String prefijo) {
        this.prefijo = prefijo;
    }

    public String getCod_propiedad() {
        return cod_propiedad;
    }

    public void setCod_propiedad(String cod_propiedad) {
        this.cod_propiedad = cod_propiedad;
    }

    public String getCc_nit_cliente() {
        return cc_nit_cliente;
    }

    public void setCc_nit_cliente(String cc_nit_cliente) {
        this.cc_nit_cliente = cc_nit_cliente;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getEstado_factura() {
        return estado_factura;
    }

    public void setEstado_factura(String estado_factura) {
        this.estado_factura = estado_factura;
    }
    
    @Override
    public String toString() {
        return "Factura{" + "idfactura=" + idfactura + ", cons_factura=" + cons_factura + ", num_contrato=" + num_contrato + ", fecha_creacion=" + fecha_creacion + ", "
                + "fecha_facturacion=" + fecha_facturacion + ", dias=" + dias + ", prefijo=" + prefijo + ", "
                + "cod_propiedad=" + cod_propiedad + ", cc_nit_cliente=" + cc_nit_cliente + ", observacion=" + observacion + ","
                + ", estado_factura" + estado_factura + '}';
    }

}
