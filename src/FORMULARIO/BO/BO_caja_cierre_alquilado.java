	package FORMULARIO.BO;
	import BASEDATO.LOCAL.ConnPostgres;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import FORMULARIO.DAO.DAO_caja_cierre_alquilado;
	import FORMULARIO.ENTIDAD.caja_cierre_alquilado;
	import java.sql.Connection;
	import java.sql.SQLException;
	import javax.swing.JTable;
public class BO_caja_cierre_alquilado {
private DAO_caja_cierre_alquilado ccal_dao = new DAO_caja_cierre_alquilado();
	EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

	public void insertar_caja_cierre_alquilado(caja_cierre_alquilado ccal, JTable tbltabla) {
		String titulo = "insertar_caja_cierre_alquilado";
		Connection conn = ConnPostgres.getConnPosgres();
		try {
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			ccal_dao.insertar_caja_cierre_alquilado(conn, ccal);
			ccal_dao.actualizar_tabla_caja_cierre_alquilado(conn, tbltabla);
			conn.commit();
		} catch (SQLException e) {
			evmen.mensaje_error(e, ccal.toString(), titulo);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				evmen.Imprimir_serial_sql_error(e1, ccal.toString(), titulo);
			}
		}
	}
	public void update_caja_cierre_alquilado(caja_cierre_alquilado ccal, JTable tbltabla) {
		if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR CAJA_CIERRE_ALQUILADO", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
			String titulo = "update_caja_cierre_alquilado";
			Connection conn = ConnPostgres.getConnPosgres();
			try {
				if (conn.getAutoCommit()) {
					conn.setAutoCommit(false);
				}
				ccal_dao.update_caja_cierre_alquilado(conn, ccal);
				ccal_dao.actualizar_tabla_caja_cierre_alquilado(conn, tbltabla);
				conn.commit();
			} catch (SQLException e) {
				evmen.mensaje_error(e, ccal.toString(), titulo);
				try {
					conn.rollback();
				}catch (SQLException e1) {
					evmen.Imprimir_serial_sql_error(e1, ccal.toString(), titulo);
				}
			}
		}
	}
}
