	package FORMULARIO.BO;
	import BASEDATO.LOCAL.ConnPostgres;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import FORMULARIO.DAO.DAO_dato_banco;
	import FORMULARIO.ENTIDAD.dato_banco;
	import java.sql.Connection;
	import java.sql.SQLException;
	import javax.swing.JTable;
public class BO_dato_banco {
private DAO_dato_banco dban_dao = new DAO_dato_banco();
	EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

	public void insertar_dato_banco(dato_banco dban, JTable tbltabla) {
		String titulo = "insertar_dato_banco";
		Connection conn = ConnPostgres.getConnPosgres();
		try {
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			dban_dao.insertar_dato_banco(conn, dban);
			dban_dao.actualizar_tabla_dato_banco(conn, tbltabla);
			conn.commit();
		} catch (SQLException e) {
			evmen.mensaje_error(e, dban.toString(), titulo);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				evmen.Imprimir_serial_sql_error(e1, dban.toString(), titulo);
			}
		}
	}
	public void update_dato_banco(dato_banco dban, JTable tbltabla) {
		if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR DATO_BANCO", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
			String titulo = "update_dato_banco";
			Connection conn = ConnPostgres.getConnPosgres();
			try {
				if (conn.getAutoCommit()) {
					conn.setAutoCommit(false);
				}
				dban_dao.update_dato_banco(conn, dban);
				dban_dao.actualizar_tabla_dato_banco(conn, tbltabla);
				conn.commit();
			} catch (SQLException e) {
				evmen.mensaje_error(e, dban.toString(), titulo);
				try {
					conn.rollback();
				}catch (SQLException e1) {
					evmen.Imprimir_serial_sql_error(e1, dban.toString(), titulo);
				}
			}
		}
	}
}
