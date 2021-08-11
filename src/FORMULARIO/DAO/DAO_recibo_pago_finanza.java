package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.recibo_pago_finanza;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_recibo_pago_finanza {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "RECIBO_PAGO_FINANZA GUARDADO CORRECTAMENTE";
    private String mensaje_update = "RECIBO_PAGO_FINANZA MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO recibo_pago_finanza(idrecibo_pago_finanza,fecha_emision,descripcion,monto_recibo_pago,monto_letra,estado,fk_idfinancista,fk_idusuario) VALUES (?,?,?,?,?,?,?,?);";
    private String sql_update = "UPDATE recibo_pago_finanza SET fecha_emision=?,descripcion=?,monto_recibo_pago=?,monto_letra=?,estado=?,fk_idfinancista=?,fk_idusuario=? WHERE idrecibo_pago_finanza=?;";
    private String sql_select = "SELECT re.idrecibo_pago_finanza as idrecibo,to_char(re.fecha_emision,'yyyy-MM-dd HH24:MI') as fec_emision,\n"
            + "re.descripcion,TRIM(to_char(re.monto_recibo_pago,'999G999G999')) as monto,re.estado,fi.nombre\n"
            + "FROM recibo_pago_finanza re,financista fi \n"
            + "where re.fk_idfinancista=fi.idfinancista\n"
            + "order by 1 desc;";
    private String sql_cargar = "SELECT idrecibo_pago_finanza,fecha_emision,descripcion,monto_recibo_pago,monto_letra,estado,fk_idfinancista,fk_idusuario FROM recibo_pago_finanza WHERE idrecibo_pago_finanza=";

    public void insertar_recibo_pago_finanza(Connection conn, recibo_pago_finanza rpf) {
        rpf.setC1idrecibo_pago_finanza(eveconn.getInt_ultimoID_mas_uno(conn, rpf.getTb_recibo_pago_finanza(), rpf.getId_idrecibo_pago_finanza()));
        String titulo = "insertar_recibo_pago_finanza";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, rpf.getC1idrecibo_pago_finanza());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, rpf.getC3descripcion());
            pst.setDouble(4, rpf.getC4monto_recibo_pago());
            pst.setString(5, rpf.getC5monto_letra());
            pst.setString(6, rpf.getC6estado());
            pst.setInt(7, rpf.getC7fk_idfinancista());
            pst.setInt(8, rpf.getC8fk_idusuario());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + rpf.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + rpf.toString(), titulo);
        }
    }

    public void update_recibo_pago_finanza(Connection conn, recibo_pago_finanza rpf) {
        String titulo = "update_recibo_pago_finanza";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, rpf.getC3descripcion());
            pst.setDouble(3, rpf.getC4monto_recibo_pago());
            pst.setString(4, rpf.getC5monto_letra());
            pst.setString(5, rpf.getC6estado());
            pst.setInt(6, rpf.getC7fk_idfinancista());
            pst.setInt(7, rpf.getC8fk_idusuario());
            pst.setInt(8, rpf.getC1idrecibo_pago_finanza());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + rpf.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + rpf.toString(), titulo);
        }
    }

    public void cargar_recibo_pago_finanza(Connection conn, recibo_pago_finanza rpf, int id) {
        String titulo = "Cargar_recibo_pago_finanza";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + id, titulo);
            if (rs.next()) {
                rpf.setC1idrecibo_pago_finanza(rs.getInt(1));
                rpf.setC2fecha_emision(rs.getString(2));
                rpf.setC3descripcion(rs.getString(3));
                rpf.setC4monto_recibo_pago(rs.getDouble(4));
                rpf.setC5monto_letra(rs.getString(5));
                rpf.setC6estado(rs.getString(6));
                rpf.setC7fk_idfinancista(rs.getInt(7));
                rpf.setC8fk_idusuario(rs.getInt(8));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + rpf.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + rpf.toString(), titulo);
        }
    }

    public void actualizar_tabla_recibo_pago_finanza(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_recibo_pago_finanza(tbltabla);
    }

    public void ancho_tabla_recibo_pago_finanza(JTable tbltabla) {
        int Ancho[] = {10,15,30, 10, 10,25};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
}
