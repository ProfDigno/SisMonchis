	package FORMULARIO.BO;
	import BASEDATO.LOCAL.ConnPostgres;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import FORMULARIO.DAO.DAO_grupo_credito_finanza;
	import FORMULARIO.ENTIDAD.grupo_credito_finanza;
	import java.sql.Connection;
	import java.sql.SQLException;
	import javax.swing.JTable;
public class BO_grupo_credito_finanza {
private DAO_grupo_credito_finanza gcf_dao = new DAO_grupo_credito_finanza();
	EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

	public void insertar_grupo_credito_finanza(grupo_credito_finanza gcf, JTable tbltabla) {
		String titulo = "insertar_grupo_credito_finanza";
		Connection conn = ConnPostgres.getConnPosgres();
		try {
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			gcf_dao.insertar_grupo_credito_finanza(conn, gcf);
			gcf_dao.actualizar_tabla_grupo_credito_finanza(conn, tbltabla);
			conn.commit();
		} catch (SQLException e) {
			evmen.mensaje_error(e, gcf.toString(), titulo);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				evmen.Imprimir_serial_sql_error(e1, gcf.toString(), titulo);
			}
		}
	}
	public void update_grupo_credito_finanza(grupo_credito_finanza gcf, JTable tbltabla) {
		if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR GRUPO_CREDITO_FINANZA", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
			String titulo = "update_grupo_credito_finanza";
			Connection conn = ConnPostgres.getConnPosgres();
			try {
				if (conn.getAutoCommit()) {
					conn.setAutoCommit(false);
				}
				gcf_dao.update_grupo_credito_finanza(conn, gcf);
				gcf_dao.actualizar_tabla_grupo_credito_finanza(conn, tbltabla);
				conn.commit();
			} catch (SQLException e) {
				evmen.mensaje_error(e, gcf.toString(), titulo);
				try {
					conn.rollback();
				}catch (SQLException e1) {
					evmen.Imprimir_serial_sql_error(e1, gcf.toString(), titulo);
				}
			}
		}
	}
}
