package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.item_caja_cierre_alquilado;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_item_caja_cierre_alquilado {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "ITEM_CAJA_CIERRE_ALQUILADO GUARDADO CORRECTAMENTE";
    private String mensaje_update = "ITEM_CAJA_CIERRE_ALQUILADO MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO item_caja_cierre_alquilado(iditem_caja_cierre_alquilado,fk_idcaja_cierre_alquilado,fk_idcaja_detalle_alquilado) VALUES (?,?,?);";
    private String sql_update = "UPDATE item_caja_cierre_alquilado SET fk_idcaja_cierre_alquilado=?,fk_idcaja_detalle_alquilado=? WHERE iditem_caja_cierre_alquilado=?;";
    private String sql_select = "SELECT iditem_caja_cierre_alquilado,fk_idcaja_cierre_alquilado,fk_idcaja_detalle_alquilado FROM item_caja_cierre_alquilado order by 1 desc;";
    private String sql_cargar = "SELECT iditem_caja_cierre_alquilado,fk_idcaja_cierre_alquilado,fk_idcaja_detalle_alquilado FROM item_caja_cierre_alquilado WHERE iditem_caja_cierre_alquilado=";

    public void insertar_item_caja_cierre_alquilado(Connection conn, item_caja_cierre_alquilado iccierre) {
        iccierre.setC1iditem_caja_cierre_alquilado(eveconn.getInt_ultimoID_mas_uno(conn, iccierre.getTb_item_caja_cierre_alquilado(), iccierre.getId_iditem_caja_cierre_alquilado()));
        String titulo = "insertar_item_caja_cierre_alquilado";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, iccierre.getC1iditem_caja_cierre_alquilado());
            pst.setInt(2, iccierre.getC2fk_idcaja_cierre_alquilado());
            pst.setInt(3, iccierre.getC3fk_idcaja_detalle_alquilado());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + iccierre.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, false);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + iccierre.toString(), titulo);
        }
    }

    public void update_item_caja_cierre_alquilado(Connection conn, item_caja_cierre_alquilado iccierre) {
        String titulo = "update_item_caja_cierre_alquilado";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setInt(1, iccierre.getC2fk_idcaja_cierre_alquilado());
            pst.setInt(2, iccierre.getC3fk_idcaja_detalle_alquilado());
            pst.setInt(3, iccierre.getC1iditem_caja_cierre_alquilado());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + iccierre.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + iccierre.toString(), titulo);
        }
    }

    public void cargar_item_caja_cierre_alquilado(Connection conn, item_caja_cierre_alquilado iccierre, int id) {
        String titulo = "Cargar_item_caja_cierre_alquilado";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + id, titulo);
            if (rs.next()) {
                iccierre.setC1iditem_caja_cierre_alquilado(rs.getInt(1));
                iccierre.setC2fk_idcaja_cierre_alquilado(rs.getInt(2));
                iccierre.setC3fk_idcaja_detalle_alquilado(rs.getInt(3));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + iccierre.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + iccierre.toString(), titulo);
        }
    }

    public void actualizar_tabla_item_caja_cierre_alquilado(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_item_caja_cierre_alquilado(tbltabla);
    }

    public void ancho_tabla_item_caja_cierre_alquilado(JTable tbltabla) {
        int Ancho[] = {33, 33, 33};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
}
