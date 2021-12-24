package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.caja_detalle_alquilado;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import FORMULARIO.ENTIDAD.usuario;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_caja_detalle_alquilado {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
     usuario usu = new usuario();
    private String mensaje_insert = "CAJA_DETALLE_ALQUILADO GUARDADO CORRECTAMENTE";
    private String mensaje_update = "CAJA_DETALLE_ALQUILADO MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO caja_detalle_alquilado(idcaja_detalle_alquilado,fecha_emision,descripcion,tabla_origen,estado,cierre,monto_alquilado_efectivo,monto_alquilado_tarjeta,monto_alquilado_transferencia,monto_recibo_pago,monto_delivery,monto_gasto,monto_vale,monto_compra_contado,monto_compra_credito,monto_apertura_caja,monto_cierre_caja,fk_idgasto,fk_idcompra,fk_idventa_alquiler,fk_idvale,fk_idrecibo_pago_cliente,fk_idusuario) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
    private String sql_update = "UPDATE caja_detalle_alquilado SET fecha_emision=?,descripcion=?,tabla_origen=?,estado=?,cierre=?,monto_alquilado_efectivo=?,monto_alquilado_tarjeta=?,monto_alquilado_transferencia=?,monto_recibo_pago=?,monto_delivery=?,monto_gasto=?,monto_vale=?,monto_compra_contado=?,monto_compra_credito=?,monto_apertura_caja=?,monto_cierre_caja=?,fk_idgasto=?,fk_idcompra=?,fk_idventa_alquiler=?,fk_idvale=?,fk_idrecibo_pago_cliente=?,fk_idusuario=? WHERE idcaja_detalle_alquilado=?;";
    private String sql_select = "SELECT idcaja_detalle_alquilado,fecha_emision,descripcion,tabla_origen,estado,cierre,monto_alquilado_efectivo,monto_alquilado_tarjeta,monto_alquilado_transferencia,monto_recibo_pago,monto_delivery,monto_gasto,monto_vale,monto_compra_contado,monto_compra_credito,monto_apertura_caja,monto_cierre_caja,fk_idgasto,fk_idcompra,fk_idventa_alquiler,fk_idvale,fk_idrecibo_pago_cliente,fk_idusuario FROM caja_detalle_alquilado order by 1 desc;";
    private String sql_cargar = "SELECT idcaja_detalle_alquilado,fecha_emision,descripcion,tabla_origen,estado,cierre,monto_alquilado_efectivo,monto_alquilado_tarjeta,monto_alquilado_transferencia,monto_recibo_pago,monto_delivery,monto_gasto,monto_vale,monto_compra_contado,monto_compra_credito,monto_apertura_caja,monto_cierre_caja,fk_idgasto,fk_idcompra,fk_idventa_alquiler,fk_idvale,fk_idrecibo_pago_cliente,fk_idusuario FROM caja_detalle_alquilado WHERE idcaja_detalle_alquilado=";

    public void insertar_caja_detalle_alquilado(Connection conn, caja_detalle_alquilado cdalq) {
        cdalq.setC1idcaja_detalle_alquilado(eveconn.getInt_ultimoID_mas_uno(conn, cdalq.getTb_caja_detalle_alquilado(), cdalq.getId_idcaja_detalle_alquilado()));
        String titulo = "insertar_caja_detalle_alquilado";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, cdalq.getC1idcaja_detalle_alquilado());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, cdalq.getC3descripcion());
            pst.setString(4, cdalq.getC4tabla_origen());
            pst.setString(5, cdalq.getC5estado());
            pst.setString(6, cdalq.getC6cierre());
            pst.setDouble(7, cdalq.getC7monto_alquilado_efectivo());
            pst.setDouble(8, cdalq.getC8monto_alquilado_tarjeta());
            pst.setDouble(9, cdalq.getC9monto_alquilado_transferencia());
            pst.setDouble(10, cdalq.getC10monto_recibo_pago());
            pst.setDouble(11, cdalq.getC11monto_delivery());
            pst.setDouble(12, cdalq.getC12monto_gasto());
            pst.setDouble(13, cdalq.getC13monto_vale());
            pst.setDouble(14, cdalq.getC14monto_compra_contado());
            pst.setDouble(15, cdalq.getC15monto_compra_credito());
            pst.setDouble(16, cdalq.getC16monto_apertura_caja());
            pst.setDouble(17, cdalq.getC17monto_cierre_caja());
            pst.setInt(18, cdalq.getC18fk_idgasto());
            pst.setInt(19, cdalq.getC19fk_idcompra());
            pst.setInt(20, cdalq.getC20fk_idventa_alquiler());
            pst.setInt(21, cdalq.getC21fk_idvale());
            pst.setInt(22, cdalq.getC22fk_idrecibo_pago_cliente());
            pst.setInt(23, cdalq.getC23fk_idusuario());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + cdalq.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, false);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + cdalq.toString(), titulo);
        }
    }

    public void limpiar_caja_detalle_alquilado(caja_detalle_alquilado cdalq) {
        cdalq.setC3descripcion("LIMPIO");
        cdalq.setC4tabla_origen("sin origen");
        cdalq.setC5estado("nulo");
        cdalq.setC6cierre("A");
        cdalq.setC7monto_alquilado_efectivo(0);
        cdalq.setC8monto_alquilado_tarjeta(0);
        cdalq.setC9monto_alquilado_transferencia(0);
        cdalq.setC10monto_recibo_pago(0);
        cdalq.setC11monto_delivery(0);
        cdalq.setC12monto_gasto(0);
        cdalq.setC13monto_vale(0);
        cdalq.setC14monto_compra_contado(0);
        cdalq.setC15monto_compra_credito(0);
        cdalq.setC16monto_apertura_caja(0);
        cdalq.setC17monto_cierre_caja(0);
        cdalq.setC18fk_idgasto(0);
        cdalq.setC19fk_idcompra(0);
        cdalq.setC20fk_idventa_alquiler(0);
        cdalq.setC21fk_idvale(0);
        cdalq.setC22fk_idrecibo_pago_cliente(0);
        cdalq.setC23fk_idusuario(usu.getGlobal_idusuario());
    }

    public void update_caja_detalle_alquilado(Connection conn, caja_detalle_alquilado cdalq) {
        String titulo = "update_caja_detalle_alquilado";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, cdalq.getC3descripcion());
            pst.setString(3, cdalq.getC4tabla_origen());
            pst.setString(4, cdalq.getC5estado());
            pst.setString(5, cdalq.getC6cierre());
            pst.setDouble(6, cdalq.getC7monto_alquilado_efectivo());
            pst.setDouble(7, cdalq.getC8monto_alquilado_tarjeta());
            pst.setDouble(8, cdalq.getC9monto_alquilado_transferencia());
            pst.setDouble(9, cdalq.getC10monto_recibo_pago());
            pst.setDouble(10, cdalq.getC11monto_delivery());
            pst.setDouble(11, cdalq.getC12monto_gasto());
            pst.setDouble(12, cdalq.getC13monto_vale());
            pst.setDouble(13, cdalq.getC14monto_compra_contado());
            pst.setDouble(14, cdalq.getC15monto_compra_credito());
            pst.setDouble(15, cdalq.getC16monto_apertura_caja());
            pst.setDouble(16, cdalq.getC17monto_cierre_caja());
            pst.setInt(17, cdalq.getC18fk_idgasto());
            pst.setInt(18, cdalq.getC19fk_idcompra());
            pst.setInt(19, cdalq.getC20fk_idventa_alquiler());
            pst.setInt(20, cdalq.getC21fk_idvale());
            pst.setInt(21, cdalq.getC22fk_idrecibo_pago_cliente());
            pst.setInt(22, cdalq.getC23fk_idusuario());
            pst.setInt(23, cdalq.getC1idcaja_detalle_alquilado());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + cdalq.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + cdalq.toString(), titulo);
        }
    }

    public void cargar_caja_detalle_alquilado(Connection conn, caja_detalle_alquilado cdalq, int id) {
        String titulo = "Cargar_caja_detalle_alquilado";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + id, titulo);
            if (rs.next()) {
                cdalq.setC1idcaja_detalle_alquilado(rs.getInt(1));
                cdalq.setC2fecha_emision(rs.getString(2));
                cdalq.setC3descripcion(rs.getString(3));
                cdalq.setC4tabla_origen(rs.getString(4));
                cdalq.setC5estado(rs.getString(5));
                cdalq.setC6cierre(rs.getString(6));
                cdalq.setC7monto_alquilado_efectivo(rs.getDouble(7));
                cdalq.setC8monto_alquilado_tarjeta(rs.getDouble(8));
                cdalq.setC9monto_alquilado_transferencia(rs.getDouble(9));
                cdalq.setC10monto_recibo_pago(rs.getDouble(10));
                cdalq.setC11monto_delivery(rs.getDouble(11));
                cdalq.setC12monto_gasto(rs.getDouble(12));
                cdalq.setC13monto_vale(rs.getDouble(13));
                cdalq.setC14monto_compra_contado(rs.getDouble(14));
                cdalq.setC15monto_compra_credito(rs.getDouble(15));
                cdalq.setC16monto_apertura_caja(rs.getDouble(16));
                cdalq.setC17monto_cierre_caja(rs.getDouble(17));
                cdalq.setC18fk_idgasto(rs.getInt(18));
                cdalq.setC19fk_idcompra(rs.getInt(19));
                cdalq.setC20fk_idventa_alquiler(rs.getInt(20));
                cdalq.setC21fk_idvale(rs.getInt(21));
                cdalq.setC22fk_idrecibo_pago_cliente(rs.getInt(22));
                cdalq.setC23fk_idusuario(rs.getInt(23));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + cdalq.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + cdalq.toString(), titulo);
        }
    }

    public void actualizar_tabla_caja_detalle_alquilado(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_caja_detalle_alquilado(tbltabla);
    }

    public void ancho_tabla_caja_detalle_alquilado(JTable tbltabla) {
        int Ancho[] = {4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
}
