
package com.demojsf.impl;

import com.demojsf.dao.DaoFactura;
import com.demojsf.db.JdbcConnect;
import com.demojsf.model.Contrato;
import com.demojsf.model.Factura;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DaoFacturaImpl implements DaoFactura<Factura> {

    @Override
    public void save(int num_factura,Date fec_crea,Date fec_factu,Date fec_venc,int contrato_ini,int contrato_fin,String exluir,String obser) {
          
        //OJO QUITAR ESTE SYSTEM.OUT
        //System.out.println("OJO!!!!... Si entroooo..... 1");
        
        Connection connect = null;
        try {
        
            connect = JdbcConnect.getConnect();
            
            String filtro="";
            
            //Si se coloco algun contrato se guarda un filtro de rango de contratos para aplicar en la consulta de contratos
            if(contrato_ini!=0)
                filtro+=" and between(numcontrato, "+contrato_ini+", "+contrato_fin+")";
            
            
            
            //*************************************************************************************//
            //*************************************************************************************//
            //*** Falta implementar filtro de fecha de vencimiento y tambien rango de contratos ***//
            //*************************************************************************************//
            //*************************************************************************************//
            
            
            
            
            //Buscamos los contratos activos y que la fecha de vencimiento sea menor a la fecha de factura
            //PreparedStatement pstContratos = connect.prepareStatement("Select numcontrato,idclie,idpropiedad,valor from Contrato where estado=\"Activo\" and fecvenc<="+ fec_factu.getTime() + filtro +" order by 1");
            PreparedStatement pstContratos = connect.prepareStatement("Select numcontrato,idclie,idpropied,valor from Contrato where estado=\"Activo\" order by 1");
            ResultSet rsContratos = pstContratos.executeQuery();
            
            //Preparamos sentncia del insert en la tabla de facturas
            PreparedStatement 
                pstInsertar_Facturas = connect.prepareStatement("Insert into Factura(idcontra,cons_factura,prefijo,resDian,factAutori,"
                        + "fecha_creacion,fecha_facturacion,fecha_vencFact,dias,observacion,idclie,idpropied,valor_canon,valor_otros,valor_iva,"
                        + "valor_total,valor_saldo_cxc,estado_factura) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            
            
            
            //OJO QUITAR ESTE SYSTEM.OUT
            //System.out.println("OJO!!!!... Si entroooo..... 2");
            
            
        
            try{
                
                //Buscamos la informacion de la tabla config
                PreparedStatement pstConfig = connect.prepareStatement("Select num_cons_fact,prefijo_dian,resolu_dian,factura_dian from config");
                ResultSet rsConfig = pstConfig.executeQuery();
                
                    //---->Se inicializan las variables de configuracion
                int consecutivo_fact=0;
                String prefijo="";
                String resolucion="";
                String rango_factu="";
                    //---->Se asignan los valores traidos de la tabla config a las variables inicializadas        
                while (rsConfig.next()) {
                    consecutivo_fact=rsConfig.getInt(1);
                    prefijo=rsConfig.getString(2);
                    resolucion=rsConfig.getString(3);
                    rango_factu=rsConfig.getString(4);
                }
                //Fin busqueda de la tabla de config
                
                connect.setAutoCommit(false);

 
                //OJO QUITAR ESTE SYSTEM.OUT
                //System.out.println("OJO!!!!... Si entroooo..... 3");
                
                
                //Comenzamos ciclo de los contratos activos y con fecha de venc menores de la fecha de facturacion
                while (rsContratos.next()) {
                    
                    //System.out.println("Si entro cicloCONTRATOS..... "+consecutivo_fact);
                    
                    //Creamos y preparamos el objeto factura con toda la informacion
                    Factura fac=new Factura();

                    fac.setNum_contrato(rsContratos.getInt(1));
                    fac.setCons_factura(consecutivo_fact++);
                    
                    fac.setPrefijo(prefijo);
                    fac.setResDian(resolucion);
                    fac.setRangoFactura(rango_factu);
                    
                    fac.setFecha_creacion(fec_crea);
                    fac.setFecha_facturacion(fec_factu);
                    fac.setFecha_vencimiento(fec_venc);

                    fac.setDias((int)((fec_venc.getTime()-fec_factu.getTime())/86400000));

                    fac.setObservacion(obser);
                    
                    fac.setCc_nit_cliente(rsContratos.getString(2));
                    fac.setCod_propiedad(rsContratos.getString(3));
                    
                    fac.setValorCanon(rsContratos.getDouble(4));

                    //****** Recorrer el detalle de factura (no esta implementado, solo coloque los valores que necesito de el objeto factura) ******//
                        fac.setValorOtros(0);
                        fac.setValorIva(0);
                    //****************************************** fin recorrer detalle factura ******************************************
                        
                    fac.setValorTotal(fac.getValorCanon()+fac.getValorOtros()+fac.getValorIva());
                    fac.setSaldoCXC(fac.getValorTotal());
                    
                    fac.setEstado_factura("Activo");

                    //Colocamos todos los parametros para realizar insert con el objeto pstInsertar_Facturas creado arriba
                    pstInsertar_Facturas.setInt(1, fac.getNum_contrato());
                    pstInsertar_Facturas.setInt(2, fac.getCons_factura());
                    pstInsertar_Facturas.setString(3, fac.getPrefijo());
                    pstInsertar_Facturas.setString(4, fac.getResDian());
                    pstInsertar_Facturas.setString(5, fac.getRangoFactura());
                    pstInsertar_Facturas.setTimestamp(6, new Timestamp(fac.getFecha_creacion().getTime()));
                    pstInsertar_Facturas.setTimestamp(7, new Timestamp(fac.getFecha_facturacion().getTime()));
                    pstInsertar_Facturas.setTimestamp(8, new Timestamp(fac.getFecha_vencimiento().getTime()));
                    pstInsertar_Facturas.setInt(9, fac.getDias());
                    pstInsertar_Facturas.setString(10, fac.getObservacion());
                    pstInsertar_Facturas.setString(11, fac.getCc_nit_cliente());
                    pstInsertar_Facturas.setString(12, fac.getCod_propiedad());
                    pstInsertar_Facturas.setDouble(13, fac.getValorCanon());
                    pstInsertar_Facturas.setDouble(14, fac.getValorOtros());
                    pstInsertar_Facturas.setDouble(15, fac.getValorIva());
                    pstInsertar_Facturas.setDouble(16, fac.getValorTotal());
                    pstInsertar_Facturas.setDouble(17, fac.getSaldoCXC());
                    pstInsertar_Facturas.setString(18, fac.getEstado_factura());
                    
                    //y tambien se realiza el INSERT correspondiente despues de llenar los datos, con el metodo executeUpdate()
                    pstInsertar_Facturas.executeUpdate();
                    
                }
                
                connect.commit();
                
            }catch (SQLException e){
                connect.rollback();
                
                
                System.out.println("ERROR en consulta SQL.");
                System.out.println("SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
                
            }
            finally
            {
              try
              {
                 connect.setAutoCommit(true);
                 connect.close();
              }
               catch (SQLException e) { 
               
               }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            try {
                if (connect != null) {
                    connect.rollback();
                    
                    System.out.println("ERROR en consulta SQL.");
                    System.out.println("SQLException: " + ex.getMessage());

                }
            } catch (SQLException ex1) {
                Logger.getLogger(DaoFacturaImpl.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(DaoFacturaImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   /* @Override
    public void update(Factura f) {
        Connection connect = null;
        try {

            connect = JdbcConnect.getConnect();

            PreparedStatement pst = connect.prepareStatement("Update Factura set cons_factura=?,Num_contrato=?,Fecha_creacion=?,Fecha_facturacion=?,Dias=?,Prefijo=?,Cod_propiedad=?,Cc_nit_cliente=?,Observacion=?,Valor=?,Saldo_factura=?,Iva=?,Estado_factura=?,Estado_comision=?,Estado_recaudo where Idfactura=?");
            
            pst.setInt(16, f.getIdfactura());
            pst.setInt(1, f.getCons_factura());
            pst.setInt(2, f.getNum_contrato());
            pst.setTimestamp(3, new Timestamp(f.getFecha_creacion().getTime()));
            pst.setTimestamp(4, new Timestamp(f.getFecha_facturacion().getTime()));
            pst.setInt(5, f.getDias());
            pst.setString(6, f.getPrefijo());
            pst.setString(7, f.getCod_propiedad());
            pst.setString(8, f.getCc_nit_cliente());
            pst.setString(9, f.getObservacion());
            pst.setInt(10, f.getValor());
            pst.setInt(11, f.getSaldo_factura());
            pst.setInt(12, f.getIva());
            pst.setString(13, f.getEstado_factura());
            pst.setString(14, f.getEstado_comision());
            pst.setString(15, f.getEstado_recaudo());
            
            pst.executeUpdate();
            connect.commit();
        } catch (ClassNotFoundException | SQLException ex) {
            try {
                if (connect != null) {
                    connect.rollback();
                }
            } catch (SQLException ex1) {
                Logger.getLogger(DaoFacturaImpl.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(DaoFacturaImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void updateComision(Factura f) {

        Connection connect = null;
        try {

            connect = JdbcConnect.getConnect();

            PreparedStatement pst = connect.prepareStatement("Update Factura set Estado_comision='ON',ValorComision=? where Idfactura=?");
            
            pst.setInt(2, f.getIdfactura());
            pst.setDouble(1, f.getValorComision());
            
      
            pst.executeUpdate();
            connect.commit();
        } catch (ClassNotFoundException | SQLException ex) {
            try {
                if (connect != null) {
                    connect.rollback();
                }
            } catch (SQLException ex1) {
                Logger.getLogger(DaoFacturaImpl.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(DaoFacturaImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void updateRecaudo(Factura f) {

        Connection connect = null;
        try {

            connect = JdbcConnect.getConnect();

            PreparedStatement pst = connect.prepareStatement("Update Factura set Estado_recaudo='ON' where Idfactura=?");
            
            pst.setInt(1, f.getIdfactura());
                        
            pst.executeUpdate();
            connect.commit();
        } catch (ClassNotFoundException | SQLException ex) {
            try {
                if (connect != null) {
                    connect.rollback();
                }
            } catch (SQLException ex1) {
                Logger.getLogger(DaoFacturaImpl.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(DaoFacturaImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(Factura f) {
        Connection connect = null;
        try {

            connect = JdbcConnect.getConnect();

            PreparedStatement pst = connect.prepareStatement("Delete from Factura where Idfactura=?");
            
            pst.setInt(1, f.getIdfactura());
            
            pst.executeUpdate();
            
            connect.commit();
        } catch (ClassNotFoundException | SQLException ex) {
            try {
                if (connect != null) {
                    connect.rollback();
                }
            } catch (SQLException ex1) {
                Logger.getLogger(DaoFacturaImpl.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(DaoFacturaImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/

    @Override
    public List<Factura> getFact() {
        List<Factura> lista = new ArrayList<>();
        try {
            Connection connect = JdbcConnect.getConnect();
            PreparedStatement pst = connect.prepareStatement("Select * from Factura order by 1");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Factura f = new Factura();     
                
                f.setIdfactura(rs.getInt(1));
                f.setCons_factura(rs.getInt(2));
                f.setNum_contrato(rs.getInt(3));
                f.setFecha_creacion(rs.getDate(4));
                f.setFecha_facturacion(rs.getDate(5));
                f.setDias(rs.getInt(6));
                f.setPrefijo(rs.getString(7));

                lista.add(f);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DaoFacturaImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }
        
    @Override
    public List<Factura> getListFact() {
        List<Factura> lista = new ArrayList<>();
        try {
            Connection connect = JdbcConnect.getConnect();
            PreparedStatement pst = connect.prepareStatement("Select f.idfactura,f.cons_factura,f.idcontra,f.fecha_creacion,f.fecha_facturacion,f.dias"
                    + ",f.prefijo,f.idpropied,f.idclie,f.observacion,f.valor,f.saldo_factura,f.iva,f.estado_factura,f.estado_comision,f.estado_recaudo,pr.porccomi from Factura f "
                    + "inner join Propiedad p on f.idpropied = p.idpropiedad "
                    + "inner join Propietarios pr on pr.idpropietario = p.idpropietario "
                    + "where Estado_comision!='ON' order by 1");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Factura f = new Factura();     
                
                f.setIdfactura(rs.getInt(1));
                f.setCons_factura(rs.getInt(2));
                f.setNum_contrato(rs.getInt(3));
                f.setFecha_creacion(rs.getDate(4));
                f.setFecha_facturacion(rs.getDate(5));
                f.setDias(rs.getInt(6));
                f.setPrefijo(rs.getString(7));
                f.setCod_propiedad(rs.getString(8));
                f.setCc_nit_cliente(rs.getString(9));

                lista.add(f);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DaoFacturaImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }
    
    @Override
    public List<Factura> getListRecau() {
        List<Factura> lista = new ArrayList<>();
        /*try {
            Connection connect = JdbcConnect.getConnect();
            PreparedStatement pst = connect.prepareStatement("Select f.idfactura,f.cons_factura,f.idcontra,f.fecha_creacion,f.fecha_facturacion,f.dias"
                    + ",f.prefijo,f.idpropied,f.idclie,f.observacion,f.valor,f.saldo_factura,f.iva,f.estado_factura,f.estado_comision,f.estado_recaudo from Factura f "
                    + "where Estado_recaudo!='ON' order by 1");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Factura f = new Factura();     
                
                f.setIdfactura(rs.getInt(1));
                f.setCons_factura(rs.getInt(2));
                f.setNum_contrato(rs.getInt(3));
                f.setFecha_creacion(rs.getDate(4));
                f.setFecha_facturacion(rs.getDate(5));
                f.setDias(rs.getInt(6));
                f.setPrefijo(rs.getString(7));
                f.setCod_propiedad(rs.getString(8));

                
                lista.add(f);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DaoFacturaImpl.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        return lista;
    }

}
