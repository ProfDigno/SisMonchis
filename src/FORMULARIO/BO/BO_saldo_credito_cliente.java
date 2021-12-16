	package FORMULARIO.BO;
	import BASEDATO.LOCAL.ConnPostgres;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import FORMULARIO.DAO.DAO_saldo_credito_cliente;
	import FORMULARIO.ENTIDAD.saldo_credito_cliente;
	import java.sql.Connection;
	import java.sql.SQLException;
	import javax.swing.JTable;
public class BO_saldo_credito_cliente {
private DAO_saldo_credito_cliente sccl_dao = new DAO_saldo_credito_cliente();
	EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

	public void insertar_saldo_credito_cliente(saldo_credito_cliente sccl, JTable tbltabla) {
		String titulo = "insertar_saldo_credito_cliente";
		Connection conn = ConnPostgres.getConnPosgres();
		try {
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			sccl_dao.insertar_saldo_credito_cliente(conn, sccl);
			sccl_dao.actualizar_tabla_saldo_credito_cliente(conn, tbltabla);
			conn.commit();
		} catch (SQLException e) {
			evmen.mensaje_error(e, sccl.toString(), titulo);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				evmen.Imprimir_serial_sql_error(e1, sccl.toString(), titulo);
			}
		}
	}
	public void update_saldo_credito_cliente(saldo_credito_cliente sccl, JTable tbltabla) {
		if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR SALDO_CREDITO_CLIENTE", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
			String titulo = "update_saldo_credito_cliente";
			Connection conn = ConnPostgres.getConnPosgres();
			try {
				if (conn.getAutoCommit()) {
					conn.setAutoCommit(false);
				}
				sccl_dao.update_saldo_credito_cliente(conn, sccl);
				sccl_dao.actualizar_tabla_saldo_credito_cliente(conn, tbltabla);
				conn.commit();
			} catch (SQLException e) {
				evmen.mensaje_error(e, sccl.toString(), titulo);
				try {
					conn.rollback();
				}catch (SQLException e1) {
					evmen.Imprimir_serial_sql_error(e1, sccl.toString(), titulo);
				}
			}
		}
	}
}
