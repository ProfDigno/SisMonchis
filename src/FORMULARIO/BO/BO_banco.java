	package FORMULARIO.BO;
	import BASEDATO.LOCAL.ConnPostgres;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import FORMULARIO.DAO.DAO_banco;
	import FORMULARIO.ENTIDAD.banco;
	import java.sql.Connection;
	import java.sql.SQLException;
	import javax.swing.JTable;
public class BO_banco {
private DAO_banco ban_dao = new DAO_banco();
	EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

	public void insertar_banco(banco ban, JTable tbltabla) {
		String titulo = "insertar_banco";
		Connection conn = ConnPostgres.getConnPosgres();
		try {
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			ban_dao.insertar_banco(conn, ban);
			ban_dao.actualizar_tabla_banco(conn, tbltabla);
			conn.commit();
		} catch (SQLException e) {
			evmen.mensaje_error(e, ban.toString(), titulo);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				evmen.Imprimir_serial_sql_error(e1, ban.toString(), titulo);
			}
		}
	}
	public void update_banco(banco ban, JTable tbltabla) {
		if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR BANCO", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
			String titulo = "update_banco";
			Connection conn = ConnPostgres.getConnPosgres();
			try {
				if (conn.getAutoCommit()) {
					conn.setAutoCommit(false);
				}
				ban_dao.update_banco(conn, ban);
				ban_dao.actualizar_tabla_banco(conn, tbltabla);
				conn.commit();
			} catch (SQLException e) {
				evmen.mensaje_error(e, ban.toString(), titulo);
				try {
					conn.rollback();
				}catch (SQLException e1) {
					evmen.Imprimir_serial_sql_error(e1, ban.toString(), titulo);
				}
			}
		}
	}
}
