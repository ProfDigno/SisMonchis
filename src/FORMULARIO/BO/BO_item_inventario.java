package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_item_inventario;
import FORMULARIO.ENTIDAD.item_inventario;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

public class BO_item_inventario {

    private DAO_item_inventario iinven_dao = new DAO_item_inventario();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_item_inventario(item_inventario iinven) {
        String titulo = "insertar_item_inventario";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            iinven_dao.insertar_item_inventario(conn, iinven);
//            iinven_dao.actualizar_tabla_item_inventario(conn, tbltabla);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, iinven.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, iinven.toString(), titulo);
            }
        }
    }

    public void update_item_inventario(item_inventario iinven, JTable tbltabla) {
        if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR ITEM_INVENTARIO", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_item_inventario";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                iinven_dao.update_item_inventario(conn, iinven);
                iinven_dao.actualizar_tabla_item_inventario(conn, tbltabla);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, iinven.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, iinven.toString(), titulo);
                }
            }
        }
    }
    public void delete_item_inventario(item_inventario iinven) {
        if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE ELIMINAR ITEM_INVENTARIO", "ELIMINAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "delete_item_inventario";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                iinven_dao.deletar_item_inventario(conn, iinven);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, iinven.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, iinven.toString(), titulo);
                }
            }
        }
    }
    
}
