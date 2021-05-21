package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_inventario;
import FORMULARIO.ENTIDAD.inventario;
import FORMULARIO.ENTIDAD.item_inventario;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

public class BO_inventario {

    private DAO_inventario inve_dao = new DAO_inventario();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_inventario(inventario inve) {
        String titulo = "insertar_inventario";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            inve_dao.insertar_inventario(conn, inve);
//            inve_dao.actualizar_tabla_inventario(conn, tbltabla);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, inve.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, inve.toString(), titulo);
            }
        }
    }

    public void update_inventario(inventario inve, JTable tbltabla) {
        if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR INVENTARIO", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_inventario";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                inve_dao.update_inventario(conn, inve);
                inve_dao.actualizar_tabla_inventario(conn, tbltabla);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, inve.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, inve.toString(), titulo);
                }
            }
        }
    }
    public void update_inventario_terminar(inventario inve) {
        if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE TERMINAR EL INVENTARIO", "TERMINAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_inventario_terminar";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                inve_dao.update_inventario_estado(conn, inve);
                inve_dao.update_producto_stock_inventario(conn, inve);
                inve_dao.update_estado_item_inventario(conn, inve);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, inve.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, inve.toString(), titulo);
                }
            }
        }
    }
}
