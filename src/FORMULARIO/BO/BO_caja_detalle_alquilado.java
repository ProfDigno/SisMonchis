package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_caja_detalle_alquilado;
import FORMULARIO.ENTIDAD.caja_detalle_alquilado;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

public class BO_caja_detalle_alquilado {

    private DAO_caja_detalle_alquilado cdalq_dao = new DAO_caja_detalle_alquilado();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_caja_detalle_alquilado(caja_detalle_alquilado cdalq, JTable tbltabla) {
        String titulo = "insertar_caja_detalle_alquilado";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            cdalq_dao.insertar_caja_detalle_alquilado(conn, cdalq);
            cdalq_dao.actualizar_tabla_caja_detalle_alquilado(conn, tbltabla);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, cdalq.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, cdalq.toString(), titulo);
            }
        }
    }

    public void update_caja_detalle_alquilado(caja_detalle_alquilado cdalq, JTable tbltabla) {
        if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR CAJA_DETALLE_ALQUILADO", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_caja_detalle_alquilado";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                cdalq_dao.update_caja_detalle_alquilado(conn, cdalq);
                cdalq_dao.actualizar_tabla_caja_detalle_alquilado(conn, tbltabla);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, cdalq.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, cdalq.toString(), titulo);
                }
            }
        }
    }
}
