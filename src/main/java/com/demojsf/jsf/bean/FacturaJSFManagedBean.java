
package com.demojsf.jsf.bean;

import com.demojsf.impl.DaoFacturaImpl;
import com.demojsf.model.Factura;
import com.demojsf.dao.DaoFactura;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;


@Named(value = "facturaBean")
@ViewScoped

public class FacturaJSFManagedBean implements Serializable {


    private Factura factura = new Factura();
    private List<Factura> lista = new ArrayList<>();
    private List<Factura> listafactu = new ArrayList<>();
    private List<Factura> listarecau = new ArrayList<>();
    private DaoFacturaImpl dao = new DaoFacturaImpl();
    private List<Factura> filteredFactura;
    private boolean modoInsert = false;
    private boolean modoEdit = true;
    
    ///******** Inicio de variables de formulario ********///
    
    private int num_factura;
    private Date fec_crea = new Date();
    private Date fec_factu = new Date();
    private Date fec_venc = new Date();
    private int contrato_ini;
    private int contrato_fin;
    private String exluir;
    private String obser;

    public int getNum_factura() {
        return num_factura;
    }

    public void setNum_factura(int num_factura) {
        this.num_factura = num_factura;
    }

    public int getContrato_ini() {
        return contrato_ini;
    }

    public void setContrato_ini(int contrato_ini) {
        this.contrato_ini = contrato_ini;
    }

    public int getContrato_fin() {
        return contrato_fin;
    }

    public void setContrato_fin(int contrato_fin) {
        this.contrato_fin = contrato_fin;
    }

    public Date getFec_crea() {
        return fec_crea;
    }

    public void setFec_crea(Date fec_crea) {
        this.fec_crea = fec_crea;
    }

    public Date getFec_factu() {
        return fec_factu;
    }

    public void setFec_factu(Date fec_factu) {
        this.fec_factu = fec_factu;
    }

    public Date getFec_venc() {
        return fec_venc;
    }

    public void setFec_venc(Date fec_venc) {
        this.fec_venc = fec_venc;
    }

    public String getExluir() {
        return exluir;
    }

    public void setExluir(String exluir) {
        this.exluir = exluir;
    }

    public String getObser() {
        return obser;
    }

    public void setObser(String obser) {
        this.obser = obser;
    }

    

   ///******** Fin de variables de formulario ********///
    
    
    public boolean isModoInsert() {
        return modoInsert;
    }

    public void setModoInsert(boolean modoInsert) {
        this.modoInsert = modoInsert;
    }

    public boolean isModoEdit() {
        return modoEdit;
    }

    public void setModoEdit(boolean modoEdit) {
        this.modoEdit = modoEdit;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    @PostConstruct
    public void iniciar() {
        lista = dao.getFact();
        listafactu = dao.getListFact();
        listarecau = dao.getListRecau();
        factura.setIdfactura(lista.size() + 1);
    }

    public List<Factura> getFilteredFactura() {
        return filteredFactura;
    }

    public void setFilteredFactura(List<Factura> filteredFactura) {
        this.filteredFactura = filteredFactura;
    }
    
  
    public List<Factura> getListarecau() {
        return listarecau;
    }

    public void setListarecau(List<Factura> listarecau) {
        this.listarecau = listarecau;
    }
    
    public List<Factura> getListafactu() {
        return listafactu;
    }
    
    public void setListafactu(List<Factura> listafactu) {
        this.listafactu = listafactu;
    }

    public List<Factura> getLista() {
        return lista;
    }

    public void setLista(List<Factura> lista) {
        this.lista = lista;
    }

    public Factura getFactura() {
        return factura;
    }

    public FacturaJSFManagedBean() {
    }

    public void save() {

        dao.save(num_factura,fec_crea,fec_factu,fec_venc,contrato_ini,contrato_fin,exluir,obser);
        lista = dao.getFact();
        //factura = new Factura();
        //factura.setIdfactura(lista.size() + 1);
    }
    
   /* public void delete() {

        dao.delete(factura);
        lista = dao.getFact();
        factura = new Factura();
        factura.setIdfactura(lista.size() + 1);
        modoEdit = true;
        modoInsert = false;
    }

    public void update() {

        dao.update(factura);
        lista = dao.getFact();
        factura = new Factura();
        factura.setIdfactura(lista.size() + 1);
        modoEdit = true;
        modoInsert = false;
    }
    
    public void updateComision() {

        dao.updateComision(factura);
        filteredFactura.clear();
        listafactu = dao.getListFact();
        factura = new Factura();
        factura.setIdfactura(listafactu.size() + 1);
        
        

    }
    
    public void updateRecaudo() {

        dao.updateRecaudo(factura);       
        filteredFactura.clear();       
        listarecau = dao.getListRecau();
        factura = new Factura();
        factura.setIdfactura(listarecau.size() + 1);

    }*/

    public void changeMode() {
        modoEdit = false;
        modoInsert = true;
    }
}
