	package FORMULARIO.BO;
	import BASEDATO.LOCAL.ConnPostgres;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import FORMULARIO.DAO.DAO_saldo_credito_finanza;
	import FORMULARIO.ENTIDAD.saldo_credito_finanza;
	import java.sql.Connection;
	import java.sql.SQLException;
	import javax.swing.JTable;
public class BO_saldo_credito_finanza {
private DAO_saldo_credito_finanza scf_dao = new DAO_saldo_credito_finanza();
	EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

	public void insertar_saldo_credito_finanza(saldo_credito_finanza scf, JTable tbltabla) {
		String titulo = "insertar_saldo_credito_finanza";
		Connection conn = ConnPostgres.getConnPosgres();
		try {
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			scf_dao.insertar_saldo_credito_finanza(conn, scf);
			scf_dao.actualizar_tabla_saldo_credito_finanza(conn, tbltabla);
			conn.commit();
		} catch (SQLException e) {
			evmen.mensaje_error(e, scf.toString(), titulo);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				evmen.Imprimir_serial_sql_error(e1, scf.toString(), titulo);
			}
		}
	}
	public void update_saldo_credito_finanza(saldo_credito_finanza scf, JTable tbltabla) {
		if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR SALDO_CREDITO_FINANZA", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
			String titulo = "update_saldo_credito_finanza";
			Connection conn = ConnPostgres.getConnPosgres();
			try {
				if (conn.getAutoCommit()) {
					conn.setAutoCommit(false);
				}
				scf_dao.update_saldo_credito_finanza(conn, scf);
				scf_dao.actualizar_tabla_saldo_credito_finanza(conn, tbltabla);
				conn.commit();
			} catch (SQLException e) {
				evmen.mensaje_error(e, scf.toString(), titulo);
				try {
					conn.rollback();
				}catch (SQLException e1) {
					evmen.Imprimir_serial_sql_error(e1, scf.toString(), titulo);
				}
			}
		}
	}
}
