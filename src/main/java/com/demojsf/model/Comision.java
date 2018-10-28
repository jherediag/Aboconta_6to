
package com.demojsf.model;

import java.util.Date;
 
 public class Comision {

    private int id_liq_comision;
    private Date fecha_registro;
    private Date mes_liquidado;
    private int num_fac_contrato;
    private String local;
    private int id_propietario;
    private double comision;
    private double val_fact_canon_arrendamiento;
    private double val_comision;

    public int getId_liq_comision() {
        return id_liq_comision;
    }

    public void setId_liq_comision(int id_liq_comision) {
        this.id_liq_comision = id_liq_comision;
    }

    public Date getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(Date fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public Date getMes_liquidado() {
        return mes_liquidado;
    }

    public void setMes_liquidado(Date mes_liquidado) {
        this.mes_liquidado = mes_liquidado;
    }

    public int getNum_fac_contrato() {
        return num_fac_contrato;
    }

    public void setNum_fac_contrato(int num_fac_contrato) {
        this.num_fac_contrato = num_fac_contrato;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public int getId_propietario() {
        return id_propietario;
    }

    public void setId_propietario(int id_propietario) {
        this.id_propietario = id_propietario;
    }

    public double getComision() {
        return comision;
    }

    public void setComision(double comision) {
        this.comision = comision;
    }

    public double getVal_fact_canon_arrendamiento() {
        return val_fact_canon_arrendamiento;
    }

    public void setVal_fact_canon_arrendamiento(double val_fact_canon_arrendamiento) {
        this.val_fact_canon_arrendamiento = val_fact_canon_arrendamiento;
    }

    public double getVal_comision() {
        return val_comision;
    }

    public void setVal_comision(double val_comision) {
        this.val_comision = val_comision;
    }
    
     @Override
    public String toString() {
        return "Liq_comision{" + "id_liq_comision=" + id_liq_comision + ", fecha_registro=" 
                + fecha_registro + ", mes_liquidado=" + mes_liquidado + ", num_fac_contrato=" 
                + num_fac_contrato + ", local=" + local + ", id_propietario=" 
                + id_propietario + ", comision=" + comision + ", val_fact_canon_arrendamiento=" 
                + val_fact_canon_arrendamiento + ", val_comision=" + val_comision + '}';
    }
    
    
}
