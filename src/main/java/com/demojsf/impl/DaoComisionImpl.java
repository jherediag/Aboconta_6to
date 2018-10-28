
package com.demojsf.impl;

import com.demojsf.db.JdbcConnect;
import com.demojsf.model.Comision;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.demojsf.dao.DaoComision;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class DaoComisionImpl implements DaoComision<Comision> {

    @Override
    public void save(Comision c) {
        try {
            if (existe(c)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Existente!", "Liq_comision ya existe...."));
            } else {

                Connection connect = null;
                try {

            connect = JdbcConnect.getConnect();

            PreparedStatement pst = connect.
                    prepareStatement("Insert into Liq_comision (id_liq_comision,fecha_registro,mes_liquidado,num_fac_contrato,local,id_propietario,comision,val_fact_canon_arrendamiento,val_comision) values(?,?,?,?,?,?,?,?,?)");
            pst.setInt(1, c.getId_liq_comision());
            pst.setTimestamp(2, new Timestamp(c.getFecha_registro().getTime()));
            pst.setTimestamp(3, new Timestamp(c.getMes_liquidado().getTime()));
            pst.setInt(4, c.getNum_fac_contrato());
            pst.setString(5, c.getLocal());
            pst.setInt(6, c.getId_propietario());
            pst.setDouble(7, c.getComision());
            pst.setDouble(8, c.getVal_fact_canon_arrendamiento());
            pst.setDouble(9, c.getVal_comision());
            pst.executeUpdate();
            connect.commit();
        } catch (ClassNotFoundException | SQLException ex) {
            try {
                if (connect != null) {
                    connect.rollback();
                }
            } catch (SQLException ex1) {
                Logger.getLogger(DaoComisionImpl.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(DaoComisionImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        } catch (SQLException ex) {
            Logger.getLogger(DaoComisionImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DaoComisionImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }       

    @Override
    public void update(Comision c) {
        Connection connect = null;
        try {

            connect = JdbcConnect.getConnect();

            PreparedStatement pst = connect.
                    prepareStatement("Update Liq_comision set fecha_registro=?,mes_liquidado=?,num_fac_contrato=?,local=?,id_propietario=?,comision=?,val_fact_canon_arrendamiento=?,val_comision=? where id_liq_comision=?");
            pst.setInt(9, c.getId_liq_comision());
            pst.setTimestamp(1, new Timestamp(c.getFecha_registro().getTime()));
            pst.setTimestamp(2, new Timestamp(c.getMes_liquidado().getTime()));
            pst.setInt(3, c.getNum_fac_contrato());
            pst.setString(4, c.getLocal());
            pst.setInt(5, c.getId_propietario());
            pst.setDouble(6, c.getComision());
            pst.setDouble(7, c.getVal_fact_canon_arrendamiento());
            pst.setDouble(8, c.getVal_comision());
            pst.executeUpdate();
            connect.commit();
        } catch (ClassNotFoundException | SQLException ex) {
            try {
                if (connect != null) {
                    connect.rollback();
                }
            } catch (SQLException ex1) {
                Logger.getLogger(DaoComisionImpl.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(DaoComisionImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(Comision c) {
        Connection connect = null;
        try {

            connect = JdbcConnect.getConnect();

            PreparedStatement pst = connect.prepareStatement("Delete from Liq_comision where id_liq_comision=?");
            
            pst.setInt(1, c.getId_liq_comision());
            
            pst.executeUpdate();
            
            connect.commit();
        } catch (ClassNotFoundException | SQLException ex) {
            try {
                if (connect != null) {
                    connect.rollback();
                }
            } catch (SQLException ex1) {
                Logger.getLogger(DaoComisionImpl.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(DaoComisionImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Comision> getComision() {
        List<Comision> lista = new ArrayList<>();
        try {
            Connection connect = JdbcConnect.getConnect();
            PreparedStatement pst = connect.
                    prepareStatement("Select * from Liq_comision order by 1");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Comision c = new Comision();
                c.setId_liq_comision(rs.getInt(1));
                c.setFecha_registro(rs.getDate(2));
                c.setMes_liquidado(rs.getDate(3));
                c.setNum_fac_contrato(rs.getInt(4));
                c.setLocal(rs.getString(5));
                c.setId_propietario(rs.getInt(6));
                c.setComision(rs.getDouble(7));
                c.setVal_fact_canon_arrendamiento(rs.getDouble(8));
                c.setVal_comision(rs.getDouble(9));
                lista.add(c);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DaoComisionImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }
    
     public boolean existe(Comision c) throws SQLException, ClassNotFoundException {

        Connection connect = JdbcConnect.getConnect();
        PreparedStatement pst = connect.prepareStatement("Select * from Liq_comision where id_liq_comision=?");
        pst.setInt(1, c.getId_liq_comision());
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            return true;
        }

        return false;
    }   
}
