	package FORMULARIO.BO;
	import BASEDATO.LOCAL.ConnPostgres;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import FORMULARIO.DAO.DAO_item_caja_cierre_alquilado;
	import FORMULARIO.ENTIDAD.item_caja_cierre_alquilado;
	import java.sql.Connection;
	import java.sql.SQLException;
	import javax.swing.JTable;
public class BO_item_caja_cierre_alquilado {
private DAO_item_caja_cierre_alquilado iccal_dao = new DAO_item_caja_cierre_alquilado();
	EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

	public void insertar_item_caja_cierre_alquilado(item_caja_cierre_alquilado iccal, JTable tbltabla) {
		String titulo = "insertar_item_caja_cierre_alquilado";
		Connection conn = ConnPostgres.getConnPosgres();
		try {
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			iccal_dao.insertar_item_caja_cierre_alquilado(conn, iccal);
			iccal_dao.actualizar_tabla_item_caja_cierre_alquilado(conn, tbltabla);
			conn.commit();
		} catch (SQLException e) {
			evmen.mensaje_error(e, iccal.toString(), titulo);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				evmen.Imprimir_serial_sql_error(e1, iccal.toString(), titulo);
			}
		}
	}
	public void update_item_caja_cierre_alquilado(item_caja_cierre_alquilado iccal, JTable tbltabla) {
		if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR ITEM_CAJA_CIERRE_ALQUILADO", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
			String titulo = "update_item_caja_cierre_alquilado";
			Connection conn = ConnPostgres.getConnPosgres();
			try {
				if (conn.getAutoCommit()) {
					conn.setAutoCommit(false);
				}
				iccal_dao.update_item_caja_cierre_alquilado(conn, iccal);
				iccal_dao.actualizar_tabla_item_caja_cierre_alquilado(conn, tbltabla);
				conn.commit();
			} catch (SQLException e) {
				evmen.mensaje_error(e, iccal.toString(), titulo);
				try {
					conn.rollback();
				}catch (SQLException e1) {
					evmen.Imprimir_serial_sql_error(e1, iccal.toString(), titulo);
				}
			}
		}
	}
}
