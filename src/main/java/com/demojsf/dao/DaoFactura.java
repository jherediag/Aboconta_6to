
package com.demojsf.dao;

import java.util.Date;
import java.util.List;


public interface DaoFactura<Factura> {
    
    void save(int num_factura,Date fec_crea,Date fec_factu,Date fec_venc,int contrato_ini,int contrato_fin,String exluir,String obser);
    List<Factura> getFact();
    List<Factura> getListFact();
    List<Factura> getListRecau();
}
