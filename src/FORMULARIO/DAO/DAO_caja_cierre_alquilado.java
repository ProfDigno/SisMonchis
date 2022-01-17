package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.caja_cierre_alquilado;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_caja_cierre_alquilado {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "CAJA_CIERRE_ALQUILADO GUARDADO CORRECTAMENTE";
    private String mensaje_update = "CAJA_CIERRE_ALQUILADO MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO caja_cierre_alquilado(idcaja_cierre_alquilado,fecha_inicio,fecha_fin,estado,fk_idusuario) VALUES (?,?,?,?,?);";
    private String sql_update = "UPDATE caja_cierre_alquilado SET fecha_inicio=?,fecha_fin=?,estado=?,fk_idusuario=? WHERE idcaja_cierre_alquilado=?;";
     private String sql_update_cerrar = "UPDATE caja_cierre_alquilado SET fecha_fin=?,estado=?,fk_idusuario=? WHERE idcaja_cierre_alquilado=?;";
//    private String sql_select = "SELECT idcaja_cierre_alquilado,fecha_inicio,fecha_fin,estado,fk_idusuario FROM caja_cierre_alquilado order by 1 desc;";
    private String sql_cargar = "SELECT idcaja_cierre_alquilado,fecha_inicio,fecha_fin,estado,fk_idusuario FROM caja_cierre_alquilado WHERE idcaja_cierre_alquilado=";
    private String sql_select = "select cc.idcaja_cierre_alquilado as idcc,\n"
            + "to_char(cc.fecha_inicio,'yyyy-MM-dd HH24:MI') as inicio,\n"
            + "to_char(cc.fecha_fin,'yyyy-MM-dd HH24:MI') as fin,\n"
            + "TRIM(to_char(sum(cd.monto_apertura_caja),'999G999G999')) as in_Abrir,\n"
            + "TRIM(to_char(sum(cd.monto_alquilado_efectivo),'999G999G999')) as in_vEfectivo,\n"
            + "TRIM(to_char(sum(cd.monto_alquilado_tarjeta),'999G999G999')) as in_vTarjeta,\n"
            + "TRIM(to_char(sum(cd.monto_alquilado_transferencia),'999G999G999')) as in_vTransfe,\n"
            + "TRIM(to_char(sum(cd.monto_recibo_pago),'999G999G999')) as in_recibo,\n"
            + "TRIM(to_char(sum(cd.monto_compra_contado),'999G999G999')) as eg_compra,\n"
            + "TRIM(to_char(sum(cd.monto_compra_credito),'999G999G999')) as compra_cre,\n"
            + "TRIM(to_char(sum(cd.monto_gasto),'999G999G999')) as eg_gasto,\n"
            + "TRIM(to_char(sum(cd.monto_vale),'999G999G999')) as eg_vale,\n"
            + "TRIM(to_char((sum(cd.monto_apertura_caja+cd.monto_alquilado_efectivo+cd.monto_alquilado_tarjeta+cd.monto_alquilado_transferencia+cd.monto_recibo_pago))-"
            + "(sum(cd.monto_compra_contado+cd.monto_gasto+cd.monto_vale)),'999G999G999')) as sistema,\n"
            + "TRIM(to_char(sum(cd.monto_cierre_caja),'999G999G999')) as cierre,\n"
            + "TRIM(to_char((sum(cd.monto_cierre_caja)-((sum(cd.monto_apertura_caja+cd.monto_alquilado_efectivo+cd.monto_alquilado_tarjeta+cd.monto_alquilado_transferencia+cd.monto_recibo_pago))-"
            + "(sum(cd.monto_compra_contado+cd.monto_gasto+cd.monto_vale)))),'999G999G999')) as diferencia\n"
            + "from caja_cierre_alquilado cc,item_caja_cierre_alquilado icc,caja_detalle_alquilado cd\n"
            + "where cc.idcaja_cierre_alquilado=icc.fk_idcaja_cierre_alquilado\n"
            + "and cd.idcaja_detalle_alquilado=icc.fk_idcaja_detalle_alquilado\n"
            + "and cd.estado!='ANULADO' "
            + "group by 1,2,3\n"
            + "order by 1 desc;";
    public void insertar_caja_cierre_alquilado(Connection conn, caja_cierre_alquilado ccal) {
        ccal.setC1idcaja_cierre_alquilado(eveconn.getInt_ultimoID_mas_uno(conn, ccal.getTb_caja_cierre_alquilado(), ccal.getId_idcaja_cierre_alquilado()));
        String titulo = "insertar_caja_cierre_alquilado";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, ccal.getC1idcaja_cierre_alquilado());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setTimestamp(3, evefec.getTimestamp_sistema());
            pst.setString(4, ccal.getC4estado());
            pst.setInt(5, ccal.getC5fk_idusuario());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + ccal.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + ccal.toString(), titulo);
        }
    }

    public void update_caja_cierre_alquilado(Connection conn, caja_cierre_alquilado ccal) {
        String titulo = "update_caja_cierre_alquilado";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, ccal.getC4estado());
            pst.setInt(4, ccal.getC5fk_idusuario());
            pst.setInt(5, ccal.getC1idcaja_cierre_alquilado());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + ccal.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + ccal.toString(), titulo);
        }
    }
    public void update_caja_cierre_alquilado_CERRAR(Connection conn, caja_cierre_alquilado ccal) {
        String titulo = "update_caja_cierre_alquilado_CERRAR";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update_cerrar);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, ccal.getC4estado());
            pst.setInt(3, ccal.getC5fk_idusuario());
            pst.setInt(4, ccal.getC1idcaja_cierre_alquilado());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update_cerrar + "\n" + ccal.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update_cerrar + "\n" + ccal.toString(), titulo);
        }
    }
    public void cargar_caja_cierre_alquilado(Connection conn, caja_cierre_alquilado ccal, int id) {
        String titulo = "Cargar_caja_cierre_alquilado";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + id, titulo);
            if (rs.next()) {
                ccal.setC1idcaja_cierre_alquilado(rs.getInt(1));
                ccal.setC2fecha_inicio(rs.getString(2));
                ccal.setC3fecha_fin(rs.getString(3));
                ccal.setC4estado(rs.getString(4));
                ccal.setC5fk_idusuario(rs.getInt(5));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + ccal.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + ccal.toString(), titulo);
        }
    }

    public void actualizar_tabla_caja_cierre_alquilado(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_caja_cierre_alquilado(tbltabla);
    }

    public void ancho_tabla_caja_cierre_alquilado(JTable tbltabla) {
        int Ancho[] = {4,10,10,8,8,8,8,8,7,7,7,7,7,7,7};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
}
