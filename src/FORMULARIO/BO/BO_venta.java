/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_caja_detalle;
import FORMULARIO.DAO.DAO_cliente;
import FORMULARIO.DAO.DAO_item_venta;
import FORMULARIO.DAO.DAO_venta;
import FORMULARIO.ENTIDAD.caja_detalle;
import FORMULARIO.ENTIDAD.cliente;
import FORMULARIO.ENTIDAD.item_venta;
import FORMULARIO.ENTIDAD.venta;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

/**
 *
 * @author Digno
 */
public class BO_venta {

    private DAO_venta vdao = new DAO_venta();
    private DAO_item_venta ivdao = new DAO_item_venta();
    private DAO_caja_detalle cdao = new DAO_caja_detalle();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public boolean getBoolean_insertar_venta(Connection conn, JTable tblitem_producto, 
            item_venta item, venta ven, caja_detalle caja) {
        boolean insertado = false;
        String titulo = "getBoolean_insertar_venta";
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            vdao.insertar_venta(conn, ven);
            ivdao.insertar_item_venta_de_tabla(conn, tblitem_producto, item, ven);
            cdao.insertar_caja_detalle(conn, caja);
            
            conn.commit();
            insertado = true;
        } catch (SQLException e) {
            evmen.mensaje_error(e, ven.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, ven.toString(), titulo);
            }
        }
        return insertado;
    }

    public void update_estado_venta(Connection conn, venta ven) {
        String titulo = "insertar_venta";
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            vdao.update_estado_venta(conn, ven);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, ven.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, ven.toString(), titulo);
            }
        }
    }

    public void update_cambio_entregador(Connection conn, venta ven) {
        String titulo = "update_cambio_entregador";
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            vdao.update_cambio_entregador(conn, ven);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, ven.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, ven.toString(), titulo);
            }
        }
    }



    public void update_anular_venta(Connection conn, venta ven, caja_detalle caja) {
        String titulo = "update_anular_venta";
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            vdao.update_estado_venta(conn, ven);
            cdao.anular_caja_detalle(conn, caja);
            ivdao.recargar_stock_producto(conn, ven);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, ven.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, ven.toString(), titulo);
            }
        }
    }
    public void update_forma_pago_venta(Connection conn, venta ven, caja_detalle caja) {
        String titulo = "update_forma_pago_venta";
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            vdao.update_forma_pago_venta(conn, ven);
            cdao.update_forma_pago_caja_detalle(conn, caja);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, ven.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, ven.toString(), titulo);
            }
        }
    }
}
