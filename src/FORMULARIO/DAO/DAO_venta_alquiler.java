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
import java.sql.SQLException;
import javax.swing.JTable;

public class DAO_venta_alquiler {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "VENTA_ALQUILER GUARDADO CORRECTAMENTE";
    private String mensaje_update = "VENTA_ALQUILER MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO venta_alquiler(idventa_alquiler,fecha_creado,"
            + "fecha_retirado_previsto,fecha_retirado_real,fecha_devolusion_previsto,fecha_devolusion_real,"
            + "monto_total,monto_alquilado_efectivo,monto_alquilado_tarjeta,monto_alquilado_transferencia,monto_delivery,"
            + "forma_pago,condicion,alquiler_retirado,alquiler_devolusion,direccion_alquiler,observacion,estado,"
            + "fk_idcliente,fk_identregador,monto_alquilado_credito,monto_alquilado_reservado) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
    private String sql_update = "UPDATE venta_alquiler SET fecha_creado=?,fecha_retirado_previsto=?,fecha_retirado_real=?,fecha_devolusion_previsto=?,fecha_devolusion_real=?,monto_total=?,monto_alquilado_efectivo=?,monto_alquilado_tarjeta=?,monto_alquilado_transferencia=?,monto_delivery=?,forma_pago=?,condicion=?,alquiler_retirado=?,alquiler_devolusion=?,direccion_alquiler=?,observacion=?,estado=?,fk_idcliente=?,fk_identregador=? WHERE idventa_alquiler=?;";
    private String sql_cargar = "SELECT idventa_alquiler,fecha_creado,fecha_retirado_previsto,fecha_retirado_real,fecha_devolusion_previsto,fecha_devolusion_real,"
            + "monto_total,monto_alquilado_efectivo,monto_alquilado_tarjeta,monto_alquilado_transferencia,monto_delivery,"
            + "forma_pago,condicion,alquiler_retirado,alquiler_devolusion,direccion_alquiler,observacion,estado,"
            + "fk_idcliente,fk_identregador,monto_alquilado_credito,"
            + "TRIM(to_char(fecha_retirado_real,'yyyy-MM-dd')) as fecha_retirado,"
            + "TRIM(to_char(fecha_retirado_real,'HH24')) as hora_retirado,"
            + "TRIM(to_char(fecha_retirado_real,'MI')) as min_retirado,"
            + "TRIM(to_char(fecha_devolusion_real,'yyyy-MM-dd')) as fecha_devolusion,"
            + "TRIM(to_char(fecha_devolusion_real,'HH24')) as hora_devolusion,"
            + "TRIM(to_char(fecha_devolusion_real,'MI')) as min_devolusion "
            + "FROM venta_alquiler WHERE idventa_alquiler=";
    private String sql_anular_venta = "update venta_alquiler set estado=? where idventa_alquiler=?;";
    private String sql_alquilado = "update venta_alquiler set fecha_retirado_real=?,alquiler_retirado=?,estado=? where idventa_alquiler=?;";
    private String sql_devolusion_alq = "update venta_alquiler set fecha_devolusion_real=?,alquiler_devolusion=?,estado=? where idventa_alquiler=?;";
    private String sql_finalizar_alq = "update venta_alquiler set estado=? where idventa_alquiler=?;";

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
            pst.setDouble(22, vealq.getC28monto_alquilado_reservado());
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
                vealq.setC21monto_alquilado_credito(rs.getDouble(21));
                vealq.setC22fecha_retirado(rs.getString(22));
                vealq.setC23hora_retirado(rs.getString(23));
                vealq.setC24min_retirado(rs.getString(24));
                vealq.setC25fecha_devolusion(rs.getString(25));
                vealq.setC26hora_devolusion(rs.getString(26));
                vealq.setC27min_devolusion(rs.getString(27));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + vealq.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + vealq.toString(), titulo);
        }
    }

    public void update_venta_alquiler_anular(Connection conn, venta_alquiler vealq) {
        String titulo = "update_venta_alquiler_anular";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_anular_venta);
            pst.setString(1, vealq.getC18estado());
            pst.setInt(2, vealq.getC1idventa_alquiler());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_anular_venta + "\n" + vealq.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_anular_venta + "\n" + vealq.toString(), titulo);
        }
    }

    public void update_venta_alquiler_alquilado(Connection conn, venta_alquiler vealq) {
        String titulo = "update_venta_alquiler_alquilado";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_alquilado);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setBoolean(2, true);
            pst.setString(3, vealq.getC18estado());
            pst.setInt(4, vealq.getC1idventa_alquiler());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_alquilado + "\n" + vealq.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_alquilado + "\n" + vealq.toString(), titulo);
        }
    }

    public void update_venta_alquiler_Devolucion(Connection conn, venta_alquiler vealq) {
        String titulo = "update_venta_alquiler_Devolucion";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_devolusion_alq);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setBoolean(2, true);
            pst.setString(3, vealq.getC18estado());
            pst.setInt(4, vealq.getC1idventa_alquiler());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_devolusion_alq + "\n" + vealq.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_devolusion_alq + "\n" + vealq.toString(), titulo);
        }
    }

    public void update_venta_alquiler_Finalizar(Connection conn, venta_alquiler vealq) {
        String titulo = "update_venta_alquiler_Finalizar";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_finalizar_alq);
            pst.setString(1, vealq.getC18estado());
            pst.setInt(2, vealq.getC1idventa_alquiler());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_finalizar_alq + "\n" + vealq.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, false);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_finalizar_alq + "\n" + vealq.toString(), titulo);
        }
    }

    public void actualizar_tabla_venta_alquiler(Connection conn, JTable tbltabla, String filtro) {
        String sql_select = "select v.idventa_alquiler as idva,"
                + "TRIM(to_char(v.fecha_retirado_real,'yyyy-MM-dd HH24:MI')) as retirado,"
                + "TRIM(to_char(v.fecha_devolusion_real,'yyyy-MM-dd HH24:MI')) as devolusion,\n"
                + "cl.idcliente as idc,cl.nombre,v.direccion_alquiler as direccion, \n"
                + "TRIM(to_char((v.monto_total),'999G999G999')) as mon_total,\n"
                + "TRIM(to_char((v.monto_alquilado_reservado),'999G999G999')) as reservado,\n"
                + "v.forma_pago,v.condicion,v.estado,\n"
                + "TRIM(to_char((v.monto_alquilado_efectivo+v.monto_alquilado_tarjeta+v.monto_alquilado_transferencia),'999G999G999')) as mon_pago,\n"
                + "TRIM(to_char((v.monto_alquilado_credito),'999G999G999')) as mon_credi\n"
                + "from venta_alquiler v,cliente cl\n"
                + "where v.fk_idcliente=cl.idcliente\n" + filtro
                + " order by 1 desc";
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_venta_alquiler(tbltabla);
    }

    public void ancho_tabla_venta_alquiler(JTable tbltabla) {
        int Ancho[] = {4, 10, 10, 2, 13, 13, 6, 6, 8, 8, 9, 6, 6};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }

    public double getDouble_suma_venta(Connection conn, String campo, String filtro) {
        double sumaventa = 0;
        String titulo = "getDouble_suma_venta";
        String sql = "select count(*) as cantidad,"
                + "sum(v.monto_alquilado_efectivo) as sumaventa_efectivo,\n"
                + "sum(v.monto_alquilado_tarjeta) as sumaventa_tarjeta, "
                + "sum(v.monto_alquilado_transferencia) as sumaventa_transferencia, "
                + "sum(v.monto_alquilado_credito) as sumaventa_credito, "
                + "sum(v.monto_alquilado_tarjeta+v.monto_alquilado_efectivo+v.monto_alquilado_transferencia+v.monto_alquilado_credito) as sumaventa_total "
                + "from venta_alquiler v,cliente c\n"
                + "where v.fk_idcliente=c.idcliente\n"
                + " " + filtro + "\n"
                + " ";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            if (rs.next()) {
                sumaventa = rs.getDouble(campo);
            }
        } catch (SQLException e) {
            evemen.Imprimir_serial_sql_error(e, sql, titulo);
        }
        return sumaventa;
    }

    public void imprimir_rep_alquiler_todos(Connection conn, String filtro) {
        String sql = "select v.idventa_alquiler as idva,\n"
                + "to_char(v.fecha_retirado_real,'yyyy-MM-dd HH24:MI') as retirado,\n"
                + "to_char(v.fecha_devolusion_real,'yyyy-MM-dd HH24:MI') as devolucion,\n"
                + "('('||c.idcliente||')'||c.nombre) as cliente,\n"
                + "v.monto_alquilado_efectivo as efectivo,\n"
                + "v.monto_alquilado_tarjeta as tarjeta,\n"
                + "v.monto_alquilado_transferencia as transfe,\n"
                + "v.monto_alquilado_credito as credito,v.forma_pago as pago,v.estado as estado\n"
                + "from venta_alquiler v,cliente c\n"
                + "where v.fk_idcliente=c.idcliente\n"
                + " " + filtro + "\n"
                + "order by v.idventa_alquiler desc;";
        String titulonota = "ALQUILER TODOS";
        String direccion = "src/REPORTE/ALQUILER/repAlquilerTodos.jrxml";
        rep.imprimirjasper(conn, sql, titulonota, direccion);
    }
}
