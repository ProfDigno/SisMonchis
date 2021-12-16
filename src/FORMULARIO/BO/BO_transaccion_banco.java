	package FORMULARIO.BO;
	import BASEDATO.LOCAL.ConnPostgres;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import FORMULARIO.DAO.DAO_transaccion_banco;
	import FORMULARIO.ENTIDAD.transaccion_banco;
	import java.sql.Connection;
	import java.sql.SQLException;
	import javax.swing.JTable;
public class BO_transaccion_banco {
private DAO_transaccion_banco tsban_dao = new DAO_transaccion_banco();
	EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

	public void insertar_transaccion_banco(transaccion_banco tsban, JTable tbltabla) {
		String titulo = "insertar_transaccion_banco";
		Connection conn = ConnPostgres.getConnPosgres();
		try {
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			tsban_dao.insertar_transaccion_banco(conn, tsban);
			tsban_dao.actualizar_tabla_transaccion_banco(conn, tbltabla);
			conn.commit();
		} catch (SQLException e) {
			evmen.mensaje_error(e, tsban.toString(), titulo);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				evmen.Imprimir_serial_sql_error(e1, tsban.toString(), titulo);
			}
		}
	}
	public void update_transaccion_banco(transaccion_banco tsban, JTable tbltabla) {
		if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR TRANSACCION_BANCO", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
			String titulo = "update_transaccion_banco";
			Connection conn = ConnPostgres.getConnPosgres();
			try {
				if (conn.getAutoCommit()) {
					conn.setAutoCommit(false);
				}
				tsban_dao.update_transaccion_banco(conn, tsban);
				tsban_dao.actualizar_tabla_transaccion_banco(conn, tbltabla);
				conn.commit();
			} catch (SQLException e) {
				evmen.mensaje_error(e, tsban.toString(), titulo);
				try {
					conn.rollback();
				}catch (SQLException e1) {
					evmen.Imprimir_serial_sql_error(e1, tsban.toString(), titulo);
				}
			}
		}
	}
}
