package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_caja_cierre_alquilado;
import FORMULARIO.DAO.DAO_caja_detalle_alquilado;
import FORMULARIO.DAO.DAO_cliente;
import FORMULARIO.DAO.DAO_credito_cliente;
import FORMULARIO.DAO.DAO_item_venta_alquiler;
import FORMULARIO.DAO.DAO_producto;
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
    private DAO_producto pro_dao = new DAO_producto();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();
 private String campoid="fk_idventa_alquiler"; 
    public boolean getBoolean_insertar_venta_alquiler1(venta_alquiler vealq, caja_detalle_alquilado cdalq, credito_cliente ccli, cliente clie, boolean escredito,boolean espagoparcial, JTable tbltabla) {
        boolean insertado = false;
        String titulo = "getBoolean_insertar_venta_alquiler";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            vealq_dao.insertar_venta_alquiler(conn, vealq);
            ivealq_dao.insertar_item_venta_alquiler_de_tabla(conn, tbltabla, vealq);
            cdalq_dao.insertar_caja_detalle_alquilado(conn, cdalq);
            if (escredito || espagoparcial) {
                ccli_dao.insertar_credito_cliente1(conn, ccli);
                cli_dao.update_cliente_saldo_credito(conn, clie);
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

    public void update_venta_alquiler(venta_alquiler vealq, JTable tbltabla) {
        if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR VENTA_ALQUILER", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_venta_alquiler";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                vealq_dao.update_venta_alquiler(conn, vealq);
//                vealq_dao.actualizar_tabla_venta_alquiler(conn, tbltabla);
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

    public boolean getBoolean_update_venta_alquiler_anular(venta_alquiler vealq, caja_detalle_alquilado cdalq, credito_cliente ccli, cliente clie) {
        boolean anulado = false;
        if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE ANULAR VENTA_ALQUILER", "ANULAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "getBoolean_update_venta_alquiler_anular";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                vealq_dao.update_venta_alquiler_anular(conn, vealq);
                ccli_dao.update_credito_cliente_anular(conn, ccli);
                cli_dao.update_cliente_saldo_credito(conn, clie);
                int datocampoid=cdalq.getC20fk_idventa_alquiler();
                cdalq_dao.update_caja_detalle_alquilado_estado_todos(conn, cdalq,datocampoid,campoid);
                conn.commit();
                anulado = true;
            } catch (SQLException e) {
                evmen.mensaje_error(e, vealq.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, vealq.toString(), titulo);
                }
            }
        }
        return anulado;
    }

    public boolean getBoolean_update_venta_alquiler_alquilado(String lista_producto, venta_alquiler vealq, caja_detalle_alquilado cdalq) {
        boolean retirar = false;
        if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE ALQUILAR VENTA_ALQUILER\nLISTA DE PRODUCTO A RETIRAR:\n" + lista_producto + "\nSALE DEL STOCK", "ALQUILAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "getBoolean_update_venta_alquiler_alquilado";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                vealq_dao.update_venta_alquiler_alquilado(conn, vealq);
                pro_dao.update_producto_stock_Alquilado(conn, vealq);
                int datocampoid=cdalq.getC20fk_idventa_alquiler();
                cdalq_dao.update_caja_detalle_alquilado_estado_todos(conn, cdalq,datocampoid,campoid);
                conn.commit();
                retirar = true;
            } catch (SQLException e) {
                evmen.mensaje_error(e, vealq.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, vealq.toString(), titulo);
                }
            }
        }
        return retirar;
    }

    public boolean getBoolean_update_venta_alquiler_Devolucion(String lista_producto, venta_alquiler vealq, caja_detalle_alquilado cdalq) {
        boolean devolusion = false;
        if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE PRECESAR DEVOLUCION VENTA_ALQUILER\nLISTA DE PRODUCTO A FINALIZAR:\n" + lista_producto + "\n(+INGRESA A STOCK+)", "DEVOLUCION", "ACEPTAR", "CANCELAR")) {
            String titulo = "getBoolean_update_venta_alquiler_Devolucion";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                vealq_dao.update_venta_alquiler_Devolucion(conn, vealq);
//                pro_dao.update_producto_stock_Devolucion(conn, vealq);
                int datocampoid=cdalq.getC20fk_idventa_alquiler();
                cdalq_dao.update_caja_detalle_alquilado_estado_todos(conn, cdalq,datocampoid,campoid);
                conn.commit();
                devolusion = true;
            } catch (SQLException e) {
                evmen.mensaje_error(e, vealq.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, vealq.toString(), titulo);
                }
            }
        }
        return devolusion;
    }

    public boolean getBoolean_update_venta_alquiler_Finalizar( venta_alquiler vealq, caja_detalle_alquilado cdalq) {
        boolean finalizar = false;
//        if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE PRECESAR DEVOLUCION VENTA_ALQUILER\nLISTA DE PRODUCTO A FINALIZAR:\n" + lista_producto + "\n(+INGRESA A STOCK+)", "DEVOLUCION", "ACEPTAR", "CANCELAR")) {
            String titulo = "getBoolean_update_venta_alquiler_Finalizar";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                vealq_dao.update_venta_alquiler_Finalizar(conn, vealq);
                int datocampoid=cdalq.getC20fk_idventa_alquiler();
                cdalq_dao.update_caja_detalle_alquilado_estado_todos(conn, cdalq,datocampoid,campoid);
                conn.commit();
                finalizar = true;
            } catch (SQLException e) {
                evmen.mensaje_error(e, vealq.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, vealq.toString(), titulo);
                }
            }
//        }
        return finalizar;
    }
}
