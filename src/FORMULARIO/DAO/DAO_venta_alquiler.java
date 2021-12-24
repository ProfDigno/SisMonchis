package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.venta_alquiler;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_venta_alquiler {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "VENTA_ALQUILER GUARDADO CORRECTAMENTE";
    private String mensaje_update = "VENTA_ALQUILER MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO venta_alquiler(idventa_alquiler,fecha_creado,fecha_retirado_previsto,fecha_retirado_real,fecha_devolusion_previsto,fecha_devolusion_real,monto_total,monto_alquilado_efectivo,monto_alquilado_tarjeta,monto_alquilado_transferencia,monto_delivery,forma_pago,condicion,alquiler_retirado,alquiler_devolusion,direccion_alquiler,observacion,estado,fk_idcliente,fk_identregador,monto_alquilado_credito) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
    private String sql_update = "UPDATE venta_alquiler SET fecha_creado=?,fecha_retirado_previsto=?,fecha_retirado_real=?,fecha_devolusion_previsto=?,fecha_devolusion_real=?,monto_total=?,monto_alquilado_efectivo=?,monto_alquilado_tarjeta=?,monto_alquilado_transferencia=?,monto_delivery=?,forma_pago=?,condicion=?,alquiler_retirado=?,alquiler_devolusion=?,direccion_alquiler=?,observacion=?,estado=?,fk_idcliente=?,fk_identregador=? WHERE idventa_alquiler=?;";
    private String sql_select = "select va.idventa_alquiler as idva,va.fecha_retirado_real as retirado,va.fecha_devolusion_real as devolusion,\n"
            + "cl.nombre,va.direccion_alquiler as direccion, \n"
            + "TRIM(to_char((va.monto_alquilado_efectivo+va.monto_alquilado_tarjeta+va.monto_alquilado_transferencia+va.monto_alquilado_credito),'999G999G999')) as monto_total,\n"
            + "TRIM(to_char((va.monto_total-(va.monto_alquilado_efectivo+va.monto_alquilado_tarjeta+va.monto_alquilado_transferencia+va.monto_alquilado_credito)),'999G999G999')) as reservado,\n"
            + "va.forma_pago,va.condicion,va.estado\n"
            + "from venta_alquiler va,cliente cl\n"
            + "where va.fk_idcliente=cl.idcliente\n"
            + "order by 1 desc";
    private String sql_cargar = "SELECT idventa_alquiler,fecha_creado,fecha_retirado_previsto,fecha_retirado_real,fecha_devolusion_previsto,fecha_devolusion_real,monto_total,monto_alquilado_efectivo,monto_alquilado_tarjeta,monto_alquilado_transferencia,monto_delivery,forma_pago,condicion,alquiler_retirado,alquiler_devolusion,direccion_alquiler,observacion,estado,fk_idcliente,fk_identregador FROM venta_alquiler WHERE idventa_alquiler=";

    public void insertar_venta_alquiler(Connection conn, venta_alquiler vealq) {
        vealq.setC1idventa_alquiler(eveconn.getInt_ultimoID_mas_uno(conn, vealq.getTb_venta_alquiler(), vealq.getId_idventa_alquiler()));
        String titulo = "insertar_venta_alquiler";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, vealq.getC1idventa_alquiler());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setTimestamp(3, evefec.getTimestamp_fecha_cargado(vealq.getC3fecha_retirado_previsto()));
            pst.setTimestamp(4, evefec.getTimestamp_fecha_cargado(vealq.getC4fecha_retirado_real()));
            pst.setTimestamp(5, evefec.getTimestamp_fecha_cargado(vealq.getC5fecha_devolusion_previsto()));
            pst.setTimestamp(6, evefec.getTimestamp_fecha_cargado(vealq.getC6fecha_devolusion_real()));
            pst.setDouble(7, vealq.getC7monto_total());
            pst.setDouble(8, vealq.getC8monto_alquilado_efectivo());
            pst.setDouble(9, vealq.getC9monto_alquilado_tarjeta());
            pst.setDouble(10, vealq.getC10monto_alquilado_transferencia());
            pst.setDouble(11, vealq.getC11monto_delivery());
            pst.setString(12, vealq.getC12forma_pago());
            pst.setString(13, vealq.getC13condicion());
            pst.setBoolean(14, vealq.getC14alquiler_retirado());
            pst.setBoolean(15, vealq.getC15alquiler_devolusion());
            pst.setString(16, vealq.getC16direccion_alquiler());
            pst.setString(17, vealq.getC17observacion());
            pst.setString(18, vealq.getC18estado());
            pst.setInt(19, vealq.getC19fk_idcliente());
            pst.setInt(20, vealq.getC20fk_identregador());
            pst.setDouble(21, vealq.getC21monto_alquilado_credito());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + vealq.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + vealq.toString(), titulo);
        }
    }

    public void update_venta_alquiler(Connection conn, venta_alquiler vealq) {
        String titulo = "update_venta_alquiler";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setTimestamp(3, evefec.getTimestamp_sistema());
            pst.setTimestamp(4, evefec.getTimestamp_sistema());
            pst.setTimestamp(5, evefec.getTimestamp_sistema());
            pst.setDouble(6, vealq.getC7monto_total());
            pst.setDouble(7, vealq.getC8monto_alquilado_efectivo());
            pst.setDouble(8, vealq.getC9monto_alquilado_tarjeta());
            pst.setDouble(9, vealq.getC10monto_alquilado_transferencia());
            pst.setDouble(10, vealq.getC11monto_delivery());
            pst.setString(11, vealq.getC12forma_pago());
            pst.setString(12, vealq.getC13condicion());
            pst.setBoolean(13, vealq.getC14alquiler_retirado());
            pst.setBoolean(14, vealq.getC15alquiler_devolusion());
            pst.setString(15, vealq.getC16direccion_alquiler());
            pst.setString(16, vealq.getC17observacion());
            pst.setString(17, vealq.getC18estado());
            pst.setInt(18, vealq.getC19fk_idcliente());
            pst.setInt(19, vealq.getC20fk_identregador());
            pst.setInt(20, vealq.getC1idventa_alquiler());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + vealq.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + vealq.toString(), titulo);
        }
    }

    public void cargar_venta_alquiler(Connection conn, venta_alquiler vealq, int id) {
        String titulo = "Cargar_venta_alquiler";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + id, titulo);
            if (rs.next()) {
                vealq.setC1idventa_alquiler(rs.getInt(1));
                vealq.setC2fecha_creado(rs.getString(2));
                vealq.setC3fecha_retirado_previsto(rs.getString(3));
                vealq.setC4fecha_retirado_real(rs.getString(4));
                vealq.setC5fecha_devolusion_previsto(rs.getString(5));
                vealq.setC6fecha_devolusion_real(rs.getString(6));
                vealq.setC7monto_total(rs.getDouble(7));
                vealq.setC8monto_alquilado_efectivo(rs.getDouble(8));
                vealq.setC9monto_alquilado_tarjeta(rs.getDouble(9));
                vealq.setC10monto_alquilado_transferencia(rs.getDouble(10));
                vealq.setC11monto_delivery(rs.getDouble(11));
                vealq.setC12forma_pago(rs.getString(12));
                vealq.setC13condicion(rs.getString(13));
                vealq.setC14alquiler_retirado(rs.getBoolean(14));
                vealq.setC15alquiler_devolusion(rs.getBoolean(15));
                vealq.setC16direccion_alquiler(rs.getString(16));
                vealq.setC17observacion(rs.getString(17));
                vealq.setC18estado(rs.getString(18));
                vealq.setC19fk_idcliente(rs.getInt(19));
                vealq.setC20fk_identregador(rs.getInt(20));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + vealq.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + vealq.toString(), titulo);
        }
    }

    public void actualizar_tabla_venta_alquiler(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_venta_alquiler(tbltabla);
    }

    public void ancho_tabla_venta_alquiler(JTable tbltabla) {
        int Ancho[] = {6,11,11,18,18,7,7,9,7,7};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
}
