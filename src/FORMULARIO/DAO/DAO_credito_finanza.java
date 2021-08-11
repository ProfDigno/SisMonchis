package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.credito_finanza;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_credito_finanza {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "CREDITO_FINANZA GUARDADO CORRECTAMENTE";
    private String mensaje_update = "CREDITO_FINANZA MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO credito_finanza(idcredito_finanza,fecha_emision,descripcion,estado,monto_contado,monto_credito,tabla_origen,fk_idgrupo_credito_finanza,fk_idsaldo_credito_finanza,fk_idrecibo_pago_finanza,fk_idcompra) VALUES (?,?,?,?,?,?,?,?,?,?,?);";
    private String sql_update = "UPDATE credito_finanza SET fecha_emision=?,descripcion=?,estado=?,monto_contado=?,monto_credito=?,tabla_origen=?,fk_idgrupo_credito_finanza=?,fk_idsaldo_credito_finanza=?,fk_idrecibo_pago_finanza=?,fk_idcompra=? WHERE idcredito_finanza=?;";
    private String sql_select = "SELECT idcredito_finanza,fecha_emision,descripcion,estado,monto_contado,monto_credito,tabla_origen,fk_idgrupo_credito_finanza,fk_idsaldo_credito_finanza,fk_idrecibo_pago_finanza,fk_idcompra FROM credito_finanza order by 1 desc;";
    private String sql_cargar = "SELECT idcredito_finanza,fecha_emision,descripcion,estado,monto_contado,monto_credito,tabla_origen,fk_idgrupo_credito_finanza,fk_idsaldo_credito_finanza,fk_idrecibo_pago_finanza,fk_idcompra FROM credito_finanza WHERE idcredito_finanza=";
    private String sql_select_gcc = "select cc.idcredito_finanza as idc,\n"
            + "to_char(cc.fecha_emision,'yyyy-MM-dd') as fecha,\n"
            + "cc.descripcion,cc.estado,cc.tabla_origen,\n"
            + "TRIM(to_char(cc.monto_credito,'999G999G999')) as credito,\n"
            + "TRIM(to_char(cc.monto_contado,'999G999G999')) as contado\n"
            + " from credito_finanza cc\n"
            + " where  cc.fk_idgrupo_credito_finanza=";
    private String sql_update_anular = "UPDATE credito_finanza "
            + "SET estado=?,monto_contado=?,monto_credito=? "
            + "WHERE fk_idcompra=?;";
 
    public void insertar_credito_finanza(Connection conn, credito_finanza cf) {
        cf.setC1idcredito_finanza(eveconn.getInt_ultimoID_mas_uno(conn, cf.getTb_credito_finanza(), cf.getId_idcredito_finanza()));
        String titulo = "insertar_credito_finanza";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, cf.getC1idcredito_finanza());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, cf.getC3descripcion());
            pst.setString(4, cf.getC4estado());
            pst.setDouble(5, cf.getC5monto_contado());
            pst.setDouble(6, cf.getC6monto_credito());
            pst.setString(7, cf.getC7tabla_origen());
            pst.setInt(8, cf.getC8fk_idgrupo_credito_finanza());
            pst.setInt(9, cf.getC9fk_idsaldo_credito_finanza());
            pst.setInt(10, cf.getC10fk_idrecibo_pago_finanza());
            pst.setInt(11, cf.getC11fk_idcompra());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + cf.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, false);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + cf.toString(), titulo);
        }
    }

    public void update_credito_finanza(Connection conn, credito_finanza cf) {
        String titulo = "update_credito_finanza";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, cf.getC3descripcion());
            pst.setString(3, cf.getC4estado());
            pst.setDouble(4, cf.getC5monto_contado());
            pst.setDouble(5, cf.getC6monto_credito());
            pst.setString(6, cf.getC7tabla_origen());
            pst.setInt(7, cf.getC8fk_idgrupo_credito_finanza());
            pst.setInt(8, cf.getC9fk_idsaldo_credito_finanza());
            pst.setInt(9, cf.getC10fk_idrecibo_pago_finanza());
            pst.setInt(10, cf.getC11fk_idcompra());
            pst.setInt(11, cf.getC1idcredito_finanza());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + cf.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + cf.toString(), titulo);
        }
    }
 public void update_credito_finanza_anular(Connection conn, credito_finanza cf) {
        String titulo = "update_credito_finanza";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update_anular);
            pst.setString(1, cf.getC4estado());
            pst.setDouble(2, cf.getC5monto_contado());
            pst.setDouble(3, cf.getC6monto_credito());
            pst.setInt(4, cf.getC11fk_idcompra());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update_anular + "\n" + cf.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update_anular + "\n" + cf.toString(), titulo);
        }
    }
    public void cargar_credito_finanza(Connection conn, credito_finanza cf, int id) {
        String titulo = "Cargar_credito_finanza";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + id, titulo);
            if (rs.next()) {
                cf.setC1idcredito_finanza(rs.getInt(1));
                cf.setC2fecha_emision(rs.getString(2));
                cf.setC3descripcion(rs.getString(3));
                cf.setC4estado(rs.getString(4));
                cf.setC5monto_contado(rs.getDouble(5));
                cf.setC6monto_credito(rs.getDouble(6));
                cf.setC7tabla_origen(rs.getString(7));
                cf.setC8fk_idgrupo_credito_finanza(rs.getInt(8));
                cf.setC9fk_idsaldo_credito_finanza(rs.getInt(9));
                cf.setC10fk_idrecibo_pago_finanza(rs.getInt(10));
                cf.setC11fk_idcompra(rs.getInt(11));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + cf.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + cf.toString(), titulo);
        }
    }

    public void actualizar_tabla_credito_finanza(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_credito_finanza(tbltabla);
    }

    public void ancho_tabla_credito_finanza(JTable tbltabla) {
        int Ancho[] = {9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }

    public void actualizar_tabla_credito_finanza_por_grupo(Connection conn, JTable tbltabla, int idgrupo_credito_finanza) {
        eveconn.Select_cargar_jtable(conn, sql_select_gcc + idgrupo_credito_finanza + " order by 1 desc;", tbltabla);
        ancho_tabla_credito_finanza_por_grupo(tbltabla);
    }

    public void ancho_tabla_credito_finanza_por_grupo(JTable tbltabla) {
        int Ancho[] = {5, 10, 40, 10, 15, 10, 10};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
}
