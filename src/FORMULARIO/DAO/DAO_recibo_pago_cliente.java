package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.recibo_pago_cliente;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_recibo_pago_cliente {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "RECIBO_PAGO_CLIENTE GUARDADO CORRECTAMENTE";
    private String mensaje_update = "RECIBO_PAGO_CLIENTE MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO recibo_pago_cliente(idrecibo_pago_cliente,fecha_emision,descripcion,monto_recibo_pago,monto_letra,estado,fk_idcliente,fk_idusuario) VALUES (?,?,?,?,?,?,?,?);";
    private String sql_update = "UPDATE recibo_pago_cliente SET fecha_emision=?,descripcion=?,monto_recibo_pago=?,monto_letra=?,estado=?,fk_idcliente=?,fk_idusuario=? WHERE idrecibo_pago_cliente=?;";
    private String sql_cargar = "SELECT idrecibo_pago_cliente,fecha_emision,descripcion,monto_recibo_pago,monto_letra,estado,fk_idcliente,fk_idusuario FROM recibo_pago_cliente WHERE idrecibo_pago_cliente=";
    private String sql_select = "SELECT re.idrecibo_pago_cliente as idrecibo,to_char(re.fecha_emision,'yyyy-MM-dd HH24:MI') as fec_emision,\n"
            + "re.descripcion,TRIM(to_char(re.monto_recibo_pago,'999G999G999')) as monto,re.estado,fi.nombre\n"
            + "FROM recibo_pago_cliente re,cliente fi \n"
            + "where re.fk_idcliente=fi.idcliente\n"
            + "order by 1 desc;";
    public void insertar_recibo_pago_cliente(Connection conn, recibo_pago_cliente rbcl) {
        rbcl.setC1idrecibo_pago_cliente(eveconn.getInt_ultimoID_mas_uno(conn, rbcl.getTb_recibo_pago_cliente(), rbcl.getId_idrecibo_pago_cliente()));
        String titulo = "insertar_recibo_pago_cliente";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, rbcl.getC1idrecibo_pago_cliente());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, rbcl.getC3descripcion());
            pst.setDouble(4, rbcl.getC4monto_recibo_pago());
            pst.setString(5, rbcl.getC5monto_letra());
            pst.setString(6, rbcl.getC6estado());
            pst.setInt(7, rbcl.getC7fk_idcliente());
            pst.setInt(8, rbcl.getC8fk_idusuario());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + rbcl.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + rbcl.toString(), titulo);
        }
    }

    public void update_recibo_pago_cliente(Connection conn, recibo_pago_cliente rbcl) {
        String titulo = "update_recibo_pago_cliente";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, rbcl.getC3descripcion());
            pst.setDouble(3, rbcl.getC4monto_recibo_pago());
            pst.setString(4, rbcl.getC5monto_letra());
            pst.setString(5, rbcl.getC6estado());
            pst.setInt(6, rbcl.getC7fk_idcliente());
            pst.setInt(7, rbcl.getC8fk_idusuario());
            pst.setInt(8, rbcl.getC1idrecibo_pago_cliente());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + rbcl.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + rbcl.toString(), titulo);
        }
    }

    public void cargar_recibo_pago_cliente(Connection conn, recibo_pago_cliente rbcl, int id) {
        String titulo = "Cargar_recibo_pago_cliente";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + id, titulo);
            if (rs.next()) {
                rbcl.setC1idrecibo_pago_cliente(rs.getInt(1));
                rbcl.setC2fecha_emision(rs.getString(2));
                rbcl.setC3descripcion(rs.getString(3));
                rbcl.setC4monto_recibo_pago(rs.getDouble(4));
                rbcl.setC5monto_letra(rs.getString(5));
                rbcl.setC6estado(rs.getString(6));
                rbcl.setC7fk_idcliente(rs.getInt(7));
                rbcl.setC8fk_idusuario(rs.getInt(8));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + rbcl.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + rbcl.toString(), titulo);
        }
    }

    public void actualizar_tabla_recibo_pago_cliente(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_recibo_pago_cliente(tbltabla);
    }

    public void ancho_tabla_recibo_pago_cliente(JTable tbltabla) {
//        int Ancho[] = {12, 12, 12, 12, 12, 12, 12, 12};
        int Ancho[] = {10,15,30, 10, 10,25};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
}
