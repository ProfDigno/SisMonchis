package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.credito_cliente;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_credito_cliente {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "CREDITO_CLIENTE GUARDADO CORRECTAMENTE";
    private String mensaje_update = "CREDITO_CLIENTE MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO credito_cliente(idcredito_cliente,fecha_emision,descripcion,estado,"
            + "monto_contado,monto_credito,tabla_origen,fk_idgrupo_credito_cliente,"
            + "fk_idsaldo_credito_cliente,fk_idrecibo_pago_cliente,fk_idventa_alquiler,vence,fecha_vence) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?);";
    private String sql_update = "UPDATE credito_cliente SET fecha_emision=?,descripcion=?,estado=?,monto_contado=?,monto_credito=?,tabla_origen=?,fk_idgrupo_credito_cliente=?,fk_idsaldo_credito_cliente=?,fk_idrecibo_pago_cliente=?,fk_idventa_alquiler=? WHERE idcredito_cliente=?;";
    private String sql_select = "SELECT idcredito_cliente,fecha_emision,descripcion,estado,monto_contado,monto_credito,tabla_origen,fk_idgrupo_credito_cliente,fk_idsaldo_credito_cliente,fk_idrecibo_pago_cliente,fk_idventa_alquiler FROM credito_cliente order by 1 desc;";
    private String sql_cargar = "SELECT idcredito_cliente,fecha_emision,descripcion,estado,monto_contado,monto_credito,tabla_origen,fk_idgrupo_credito_cliente,fk_idsaldo_credito_cliente,fk_idrecibo_pago_cliente,fk_idventa_alquiler FROM credito_cliente WHERE idcredito_cliente=";
    private String sql_select_gcc = "select cc.idcredito_cliente as idc,\n"
            + "to_char(cc.fecha_emision,'yyyy-MM-dd HH24:MI') as fecha,\n"
            + "case when vence=true then to_char(cc.fecha_vence,'yyyy-MM-dd') else 'NO VENCE' end as vence,"
            + "cc.descripcion,cc.estado,cc.tabla_origen,\n"
            + "TRIM(to_char(cc.monto_credito,'999G999G999')) as credito,\n"
            + "TRIM(to_char(cc.monto_contado,'999G999G999')) as contado\n"
            + " from credito_cliente cc\n"
            + " where  cc.estado!='ANULADO' and cc.fk_idgrupo_credito_cliente=";
        private String sql_anular = "UPDATE credito_cliente SET estado=? WHERE fk_idventa_alquiler=?;";
 
    public void insertar_credito_cliente1(Connection conn, credito_cliente crcl) {
        crcl.setC1idcredito_cliente(eveconn.getInt_ultimoID_mas_uno(conn, crcl.getTb_credito_cliente(), crcl.getId_idcredito_cliente()));
        String titulo = "insertar_credito_cliente";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, crcl.getC1idcredito_cliente());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, crcl.getC3descripcion());
            pst.setString(4, crcl.getC4estado());
            pst.setDouble(5, crcl.getC5monto_contado());
            pst.setDouble(6, crcl.getC6monto_credito());
            pst.setString(7, crcl.getC7tabla_origen());
            pst.setInt(8, crcl.getC8fk_idgrupo_credito_cliente());
            pst.setInt(9, crcl.getC9fk_idsaldo_credito_cliente());
            pst.setInt(10, crcl.getC10fk_idrecibo_pago_cliente());
            pst.setInt(11, crcl.getC11fk_idventa_alquiler());
            pst.setBoolean(12, crcl.getC12vence());
            pst.setTimestamp(13, evefec.getTimestamp_fecha_cargado(crcl.getC13fecha_vence()));
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + crcl.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, false);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + crcl.toString(), titulo);
        }
    }

    public void update_credito_cliente(Connection conn, credito_cliente crcl) {
        String titulo = "update_credito_cliente";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, crcl.getC3descripcion());
            pst.setString(3, crcl.getC4estado());
            pst.setDouble(4, crcl.getC5monto_contado());
            pst.setDouble(5, crcl.getC6monto_credito());
            pst.setString(6, crcl.getC7tabla_origen());
            pst.setInt(7, crcl.getC8fk_idgrupo_credito_cliente());
            pst.setInt(8, crcl.getC9fk_idsaldo_credito_cliente());
            pst.setInt(9, crcl.getC10fk_idrecibo_pago_cliente());
            pst.setInt(10, crcl.getC11fk_idventa_alquiler());
            pst.setInt(11, crcl.getC1idcredito_cliente());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + crcl.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + crcl.toString(), titulo);
        }
    }

    public void cargar_credito_cliente(Connection conn, credito_cliente crcl, int id) {
        String titulo = "Cargar_credito_cliente";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + id, titulo);
            if (rs.next()) {
                crcl.setC1idcredito_cliente(rs.getInt(1));
                crcl.setC2fecha_emision(rs.getString(2));
                crcl.setC3descripcion(rs.getString(3));
                crcl.setC4estado(rs.getString(4));
                crcl.setC5monto_contado(rs.getDouble(5));
                crcl.setC6monto_credito(rs.getDouble(6));
                crcl.setC7tabla_origen(rs.getString(7));
                crcl.setC8fk_idgrupo_credito_cliente(rs.getInt(8));
                crcl.setC9fk_idsaldo_credito_cliente(rs.getInt(9));
                crcl.setC10fk_idrecibo_pago_cliente(rs.getInt(10));
                crcl.setC11fk_idventa_alquiler(rs.getInt(11));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + crcl.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + crcl.toString(), titulo);
        }
    }

    public void actualizar_tabla_credito_cliente(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_credito_cliente(tbltabla);
    }

    public void ancho_tabla_credito_cliente(JTable tbltabla) {
        int Ancho[] = {9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }

    public void actualizar_tabla_credito_cliente_por_grupo(Connection conn, JTable tbltabla, int idgrupo_credito_cliente) {
        eveconn.Select_cargar_jtable(conn, sql_select_gcc + idgrupo_credito_cliente + " order by 1 desc;", tbltabla);
        ancho_tabla_credito_cliente_por_grupo(tbltabla);
    }

    public void ancho_tabla_credito_cliente_por_grupo(JTable tbltabla) {
        int Ancho[] = {5, 12,10,27, 10, 15, 10, 10};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
    public void update_credito_cliente_anular(Connection conn, credito_cliente crcl) {
        String titulo = "update_credito_cliente";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_anular);
            pst.setString(1, crcl.getC4estado());
            pst.setInt(2, crcl.getC11fk_idventa_alquiler());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_anular + "\n" + crcl.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, false);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_anular + "\n" + crcl.toString(), titulo);
        }
    }
}
