package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_caja_detalle;
import FORMULARIO.DAO.DAO_caja_detalle_alquilado;
import FORMULARIO.DAO.DAO_compra;
import FORMULARIO.DAO.DAO_credito_finanza;
import FORMULARIO.DAO.DAO_financista;
import FORMULARIO.DAO.DAO_item_compra;
import FORMULARIO.ENTIDAD.caja_detalle;
import FORMULARIO.ENTIDAD.caja_detalle_alquilado;
import FORMULARIO.ENTIDAD.compra;
import FORMULARIO.ENTIDAD.credito_finanza;
import FORMULARIO.ENTIDAD.financista;
import FORMULARIO.ENTIDAD.item_compra;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;

public class BO_compra {
    
    private DAO_compra com_dao = new DAO_compra();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();
    private DAO_item_compra icdao = new DAO_item_compra();
    private DAO_caja_detalle cdao = new DAO_caja_detalle();
    private DAO_credito_finanza ccli_dao = new DAO_credito_finanza();
    private DAO_financista cli_dao = new DAO_financista();
    private DAO_caja_detalle_alquilado cdalq_dao = new DAO_caja_detalle_alquilado();
    private String campoid="fk_idcompra"; 
    public boolean getBoolean_compra1(JTable tblitem_compra_insumo, compra comp, caja_detalle caja, caja_detalle_alquilado cdalq, boolean esalquiler) {
        boolean insertado = false;
        String titulo = "getBoolean_compra";
        Connection conn = null;
        try {
            conn = ConnPostgres.getConnPosgres();
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            com_dao.insertar_compra(conn, comp);
            icdao.insertar_item_compra_de_tabla(conn, tblitem_compra_insumo, comp);
            if (esalquiler) {
                cdalq_dao.insertar_caja_detalle_alquilado(conn, cdalq);
            } else {
                cdao.insertar_caja_detalle1(conn, caja);
            }
            conn.commit();
            insertado = true;
        } catch (SQLException e) {
            insertado = false;
            evmen.mensaje_error(e, comp.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, comp.toString(), titulo);
            }
        }
        return insertado;
    }

    public void insertar_compra(compra com, JTable tbltabla) {
        String titulo = "insertar_compra";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            com_dao.insertar_compra(conn, com);
            com_dao.actualizar_tabla_compra(conn, tbltabla);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, com.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, com.toString(), titulo);
            }
        }
    }
    
    public void update_compra(compra com, JTable tbltabla) {
        if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR COMPRA", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_compra";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                com_dao.update_compra(conn, com);
                com_dao.actualizar_tabla_compra(conn, tbltabla);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, com.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, com.toString(), titulo);
                }
            }
        }
    }

    public void update_anular_compra(Connection conn, compra comp, caja_detalle caja, credito_finanza cf, financista fina, caja_detalle_alquilado cdalq, boolean esalquiler) {
        String titulo = "update_anular_compra";
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            com_dao.update_estado_compra(conn, comp);
            if(esalquiler){
                int datocampoid=cdalq.getC19fk_idcompra();
                cdalq_dao.update_caja_detalle_alquilado_estado_todos(conn, cdalq,datocampoid,campoid);
                JOptionPane.showMessageDialog(null, "ANULAR CAJA ALQUILER");
            }else{
                cdao.anular_caja_detalle(conn, caja);
                JOptionPane.showMessageDialog(null, "ANULAR CAJA normal");
            }
            ccli_dao.update_credito_finanza_anular(conn, cf);
            cli_dao.update_finanza_saldo_credito(conn, fina);
            icdao.descontar_stock_producto(conn, comp);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, comp.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, comp.toString(), titulo);
            }
        }
    }

    public boolean getBoolean_insertar_compra_credito(Connection conn, JTable tblitem_venta,
            item_compra item, compra com, credito_finanza cfina, financista fina, caja_detalle caja, caja_detalle_alquilado cdalq, boolean esalquiler) {
        boolean insertado = false;
        String titulo = "getBoolean_insertar_venta_credito";
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            com_dao.insertar_compra(conn, com);
            icdao.insertar_item_compra_de_tabla(conn, tblitem_venta, com);
            ccli_dao.insertar_credito_finanza(conn, cfina);
            cli_dao.update_finanza_saldo_credito(conn, fina);
            if (esalquiler) {
                cdalq_dao.insertar_caja_detalle_alquilado(conn, cdalq);
            } else {
                cdao.insertar_caja_detalle1(conn, caja);
            }
            conn.commit();
            insertado = true;
        } catch (SQLException e) {
            evmen.mensaje_error(e, com.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, com.toString(), titulo);
            }
        }
        return insertado;
    }
}
