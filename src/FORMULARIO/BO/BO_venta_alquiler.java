package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_caja_cierre_alquilado;
import FORMULARIO.DAO.DAO_caja_detalle_alquilado;
import FORMULARIO.DAO.DAO_cliente;
import FORMULARIO.DAO.DAO_credito_cliente;
import FORMULARIO.DAO.DAO_item_venta_alquiler;
import FORMULARIO.DAO.DAO_venta_alquiler;
import FORMULARIO.ENTIDAD.caja_cierre_alquilado;
import FORMULARIO.ENTIDAD.caja_detalle_alquilado;
import FORMULARIO.ENTIDAD.cliente;
import FORMULARIO.ENTIDAD.credito_cliente;
import FORMULARIO.ENTIDAD.item_venta_alquiler;
import FORMULARIO.ENTIDAD.venta_alquiler;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

public class BO_venta_alquiler {

    private DAO_venta_alquiler vealq_dao = new DAO_venta_alquiler();
    private DAO_item_venta_alquiler ivealq_dao = new DAO_item_venta_alquiler();
    private DAO_caja_detalle_alquilado cdalq_dao = new DAO_caja_detalle_alquilado();
    private DAO_credito_cliente ccli_dao = new DAO_credito_cliente();
    private DAO_cliente cli_dao = new DAO_cliente();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public boolean getBoolean_insertar_venta_alquiler(venta_alquiler vealq, caja_detalle_alquilado cdalq, credito_cliente ccli, cliente clie, boolean escredito, JTable tbltabla) {
        boolean insertado = false;
        String titulo = "insertar_venta_alquiler";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            vealq_dao.insertar_venta_alquiler(conn, vealq);
            ivealq_dao.insertar_item_venta_alquiler_de_tabla(conn, tbltabla, vealq);
            if (escredito) {
                ccli_dao.insertar_credito_cliente(conn, ccli);
                cli_dao.update_cliente_saldo_credito(conn, clie);
            } else {
                cdalq_dao.insertar_caja_detalle_alquilado(conn, cdalq);
            }
            conn.commit();
            insertado = true;
        } catch (SQLException e) {
            evmen.mensaje_error(e, vealq.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, vealq.toString(), titulo);
            }
        }
        return insertado;
    }
//    public boolean getBoolean_insertar_compra_credito(Connection conn, JTable tblitem_venta,
//            item_compra item, compra com,credito_finanza cfina,financista fina, caja_detalle caja) {
//        boolean insertado = false;
//        String titulo = "getBoolean_insertar_venta_credito";
//        try {
//            if (conn.getAutoCommit()) {
//                conn.setAutoCommit(false);
//            }
//            com_dao.insertar_compra(conn, com);
//            icdao.insertar_item_compra_de_tabla(conn, tblitem_venta, com);
//            ccli_dao.insertar_credito_finanza(conn, cfina);
//            cli_dao.update_finanza_saldo_credito(conn, fina);
//            cdao.insertar_caja_detalle1(conn, caja);
//            conn.commit();
//            insertado = true;
//        } catch (SQLException e) {
//            evmen.mensaje_error(e, com.toString(), titulo);
//            try {
//                conn.rollback();
//            } catch (SQLException e1) {
//                evmen.Imprimir_serial_sql_error(e1, com.toString(), titulo);
//            }
//        }
//        return insertado;
//    }

    public void update_venta_alquiler(venta_alquiler vealq, JTable tbltabla) {
        if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR VENTA_ALQUILER", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_venta_alquiler";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                vealq_dao.update_venta_alquiler(conn, vealq);
                vealq_dao.actualizar_tabla_venta_alquiler(conn, tbltabla);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, vealq.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, vealq.toString(), titulo);
                }
            }
        }
    }
}
