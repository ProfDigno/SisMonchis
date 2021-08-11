package FORMULARIO.BO;

import BASEDATO.EvenConexion;
import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
//import FORMULARIO.DAO.DAO_caja_detalle;
import FORMULARIO.DAO.*;
import FORMULARIO.ENTIDAD.*;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

public class BO_financista {
//    private DAO_finanza cli_dao = new DAO_finanza();
    private DAO_financista fina_dao = new DAO_financista();
    private DAO_saldo_credito_finanza sccli_dao = new DAO_saldo_credito_finanza();
    private DAO_credito_finanza ccli_dao = new DAO_credito_finanza();
    private DAO_grupo_credito_finanza gcc_dao = new DAO_grupo_credito_finanza();
    private DAO_recibo_pago_finanza rpcli_dao = new DAO_recibo_pago_finanza();
    private grupo_credito_finanza gcc2 = new grupo_credito_finanza();
    private grupo_credito_finanza gcc3 = new grupo_credito_finanza();
    private DAO_caja_detalle cdao = new DAO_caja_detalle();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();
    EvenConexion eveconn = new EvenConexion();
    private String estado_EMITIDO = "EMITIDO";
    private String estado_ABIERTO = "ABIERTO";
    private String estado_CERRADO = "CERRADO";

    public void insertar_financista(financista fina, JTable tbltabla) {
        String titulo = "insertar_financista";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            fina_dao.insertar_financista(conn, fina);
            fina_dao.actualizar_tabla_financista(conn, tbltabla);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, fina.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, fina.toString(), titulo);
            }
        }
    }

    public void update_financista(financista fina, JTable tbltabla) {
        if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR FINANCISTA", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_financista";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                fina_dao.update_financista(conn, fina);
                fina_dao.actualizar_tabla_financista(conn, tbltabla);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, fina.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, fina.toString(), titulo);
                }
            }
        }
    }

    public boolean getBoolean_insertar_finanza_con_recibo_pago(financista cli, credito_finanza ccli, credito_finanza ccli2,
            grupo_credito_finanza gcc, recibo_pago_finanza rpcli, saldo_credito_finanza sccli, caja_detalle caja) {
        String titulo = "getBoolean_insertar_finanza_con_recibo_pago";
        Connection conn = ConnPostgres.getConnPosgres();
        boolean insert = false;
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            rpcli_dao.insertar_recibo_pago_finanza(conn, rpcli);
            ccli_dao.insertar_credito_finanza(conn, ccli);
            gcc_dao.cargar_grupo_credito_finanza_id(conn, gcc2, cli.getC1idfinancista());
            gcc2.setC4estado(estado_CERRADO);
            gcc_dao.cerrar_grupo_credito_finanza(conn, gcc2);
            gcc2.setC4estado(estado_ABIERTO);
            gcc2.setC5fk_idfinancista(cli.getC1idfinancista());
            gcc_dao.insertar_grupo_credito_finanza(conn, gcc2);
            sccli_dao.insertar_saldo_credito_finanza(conn, sccli);
            gcc_dao.cargar_grupo_credito_finanza_id(conn, gcc3, cli.getC1idfinancista());
            int idsaldo_credito_finanza = (eveconn.getInt_ultimoID_max(conn, sccli.getTb_saldo_credito_finanza(), sccli.getId_idsaldo_credito_finanza()));
            ccli2.setC8fk_idgrupo_credito_finanza(gcc3.getC1idgrupo_credito_finanza());
            ccli2.setC9fk_idsaldo_credito_finanza(idsaldo_credito_finanza);
            ccli_dao.insertar_credito_finanza(conn, ccli2);
            fina_dao.update_finanza_saldo_credito(conn, cli);
            cdao.insertar_caja_detalle1(conn, caja);
            conn.commit();
            insert = true;
        } catch (SQLException e) {
            evmen.mensaje_error(e, cli.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, cli.toString(), titulo);
            }
        }
        return insert;
    }
    public boolean getBoolean_insertar_finanza_con_credito_inicio(financista cli,saldo_credito_finanza sccli,credito_finanza ccli,grupo_credito_finanza gcc) {
        String titulo = "insertar_finanza_con_credito_inicio";
        Connection conn = ConnPostgres.getConnPosgres();
        boolean insert=false;
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            fina_dao.insertar_financista(conn, cli);
            sccli_dao.insertar_saldo_credito_finanza(conn, sccli);
            gcc_dao.insertar_grupo_credito_finanza(conn, gcc);
            ccli_dao.insertar_credito_finanza(conn, ccli);
            
            conn.commit();
            insert=true;
        } catch (SQLException e) {
            evmen.mensaje_error(e, cli.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, cli.toString(), titulo);
            }
        }
        return insert;
    }
}
