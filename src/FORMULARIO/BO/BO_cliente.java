/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.BO;

import BASEDATO.EvenConexion;
import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.*;
import FORMULARIO.ENTIDAD.*;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

/**
 *
 * @author Digno
 */
public class BO_cliente {

    private DAO_cliente pidao = new DAO_cliente();
    private DAO_saldo_credito_cliente sccli_dao = new DAO_saldo_credito_cliente();
    private DAO_credito_cliente ccli_dao = new DAO_credito_cliente();
    private DAO_grupo_credito_cliente gcc_dao = new DAO_grupo_credito_cliente();
    private DAO_recibo_pago_cliente rpcli_dao = new DAO_recibo_pago_cliente();
    private grupo_credito_cliente gcc2 = new grupo_credito_cliente();
    private grupo_credito_cliente gcc3 = new grupo_credito_cliente();
    private DAO_caja_detalle_alquilado cdalq_dao = new DAO_caja_detalle_alquilado();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();
    private String estado_EMITIDO = "EMITIDO";
    private String estado_ABIERTO = "ABIERTO";
    private String estado_CERRADO = "CERRADO";
    EvenConexion eveconn = new EvenConexion();
    public void insertar_cliente(Connection conn,cliente ingre, JTable tbltabla) {
        String titulo = "insertar_cliente";
//        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            pidao.insertar_cliente(conn, ingre);    
            pidao.actualizar_tabla_cliente(conn, tbltabla);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, ingre.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, ingre.toString(), titulo);
            }
        }
    }

    public void update_cliente(cliente ingre, JTable tbltabla) {
        if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR ESTE CLIENTE", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_cliente";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                pidao.update_cliente(conn, ingre);
                pidao.actualizar_tabla_cliente(conn, tbltabla);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, ingre.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, ingre.toString(), titulo);
                }
            }
        }
    }
    public boolean getBoolean_insertar_cliente_con_credito_inicio(cliente cli,saldo_credito_cliente sccli,credito_cliente ccli,grupo_credito_cliente gcc) {
        String titulo = "insertar_cliente_con_credito_inicio";
        Connection conn = ConnPostgres.getConnPosgres();
        boolean insert=false;
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            pidao.insertar_cliente(conn, cli);    
            sccli_dao.insertar_saldo_credito_cliente(conn, sccli);
            gcc_dao.insertar_grupo_credito_cliente(conn, gcc);
            ccli_dao.insertar_credito_cliente(conn, ccli);
            
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
    public boolean getBoolean_insertar_credito_inicio(saldo_credito_cliente sccli,credito_cliente ccli,grupo_credito_cliente gcc) {
        String titulo = "getBoolean_insertar_credito_inicio";
        Connection conn = ConnPostgres.getConnPosgres();
        boolean insert=false;
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }   
            sccli_dao.insertar_saldo_credito_cliente(conn, sccli);
            gcc_dao.insertar_grupo_credito_cliente(conn, gcc);
            ccli_dao.insertar_credito_cliente(conn, ccli);
            
            conn.commit();
            insert=true;
        } catch (SQLException e) {
            evmen.mensaje_error(e, sccli.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, sccli.toString(), titulo);
            }
        }
        return insert;
    }
     public boolean getBoolean_insertar_cliente_con_recibo_pago(cliente cli, credito_cliente ccli, credito_cliente ccli2,
            grupo_credito_cliente gcc, recibo_pago_cliente rpcli, saldo_credito_cliente sccli, caja_detalle_alquilado caja) {
        String titulo = "getBoolean_insertar_cliente_con_recibo_pago";
        Connection conn = ConnPostgres.getConnPosgres();
        boolean insert = false;
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            rpcli_dao.insertar_recibo_pago_cliente(conn, rpcli);
            ccli_dao.insertar_credito_cliente(conn, ccli);
            gcc_dao.cargar_grupo_credito_cliente_id(conn, gcc2, cli.getC1idcliente());
            gcc2.setC4estado(estado_CERRADO);
            gcc_dao.cerrar_grupo_credito_cliente(conn, gcc2);
            gcc2.setC4estado(estado_ABIERTO);
            gcc2.setC5fk_idcliente(cli.getC1idcliente());
            gcc_dao.insertar_grupo_credito_cliente(conn, gcc2);
            sccli_dao.insertar_saldo_credito_cliente(conn, sccli);
            gcc_dao.cargar_grupo_credito_cliente_id(conn, gcc3, cli.getC1idcliente());
            int idsaldo_credito_cliente = (eveconn.getInt_ultimoID_max(conn, sccli.getTb_saldo_credito_cliente(), sccli.getId_idsaldo_credito_cliente()));
            ccli2.setC8fk_idgrupo_credito_cliente(gcc3.getC1idgrupo_credito_cliente());
            ccli2.setC9fk_idsaldo_credito_cliente(idsaldo_credito_cliente);
            ccli_dao.insertar_credito_cliente(conn, ccli2);
            pidao.update_cliente_saldo_credito(conn, cli);
            cdalq_dao.insertar_caja_detalle_alquilado(conn, caja);
//            cdao.insertar_caja_detalle1(conn, caja);
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
}
