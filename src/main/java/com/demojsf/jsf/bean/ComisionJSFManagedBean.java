
package com.demojsf.jsf.bean;

import com.demojsf.impl.DaoComisionImpl;
import com.demojsf.model.Comision;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import com.demojsf.model.Propiedad;
import com.demojsf.dao.DaoComision;

@Named(value = "comisionBean")
@ViewScoped

public class ComisionJSFManagedBean implements Serializable {

    private Comision comision = new Comision();
    private List<Comision> lista = new ArrayList<>();
    private DaoComisionImpl dao = new DaoComisionImpl();
    private boolean modoInsert = false;
    private boolean modoEdit = true;

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

    public void setComision(Comision comision) {
        this.comision = comision;
    }

    @PostConstruct
    public void iniciar() {
        lista = dao.getComision();
        comision.setId_liq_comision(lista.size() + 1);
    }

    public List<Comision> getLista() {
        return lista;
    }

    public void setLista(List<Comision> lista) {
        this.lista = lista;
    }

    public Comision getComision() {
        return comision;
    }

    public ComisionJSFManagedBean() {
    }

    public void save() {

        dao.save(comision);
        lista = dao.getComision();
        comision = new Comision();
        comision.setId_liq_comision(lista.size() + 1);
    }
    
    public void delete() {

        dao.delete(comision);
        lista = dao.getComision();
        comision = new Comision();
        comision.setId_liq_comision(lista.size() + 1);
        modoEdit = true;
        modoInsert = false;
    }

    public void update() {

        dao.update(comision);
        lista = dao.getComision();
        comision = new Comision();
        comision.setId_liq_comision(lista.size() + 1);
        modoEdit = true;
        modoInsert = false;
    }

    public void changeMode() {
        modoEdit = false;
        modoInsert = true;
    }
}
