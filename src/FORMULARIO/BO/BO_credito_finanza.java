	package FORMULARIO.BO;
	import BASEDATO.LOCAL.ConnPostgres;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import FORMULARIO.DAO.DAO_credito_finanza;
	import FORMULARIO.ENTIDAD.credito_finanza;
	import java.sql.Connection;
	import java.sql.SQLException;
	import javax.swing.JTable;
public class BO_credito_finanza {
private DAO_credito_finanza cf_dao = new DAO_credito_finanza();
	EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

	public void insertar_credito_finanza(credito_finanza cf, JTable tbltabla) {
		String titulo = "insertar_credito_finanza";
		Connection conn = ConnPostgres.getConnPosgres();
		try {
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			cf_dao.insertar_credito_finanza(conn, cf);
			cf_dao.actualizar_tabla_credito_finanza(conn, tbltabla);
			conn.commit();
		} catch (SQLException e) {
			evmen.mensaje_error(e, cf.toString(), titulo);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				evmen.Imprimir_serial_sql_error(e1, cf.toString(), titulo);
			}
		}
	}
	public void update_credito_finanza(credito_finanza cf, JTable tbltabla) {
		if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR CREDITO_FINANZA", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
			String titulo = "update_credito_finanza";
			Connection conn = ConnPostgres.getConnPosgres();
			try {
				if (conn.getAutoCommit()) {
					conn.setAutoCommit(false);
				}
				cf_dao.update_credito_finanza(conn, cf);
				cf_dao.actualizar_tabla_credito_finanza(conn, tbltabla);
				conn.commit();
			} catch (SQLException e) {
				evmen.mensaje_error(e, cf.toString(), titulo);
				try {
					conn.rollback();
				}catch (SQLException e1) {
					evmen.Imprimir_serial_sql_error(e1, cf.toString(), titulo);
				}
			}
		}
	}
}
