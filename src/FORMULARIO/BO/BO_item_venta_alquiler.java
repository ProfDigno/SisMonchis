	package FORMULARIO.BO;
	import BASEDATO.LOCAL.ConnPostgres;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import FORMULARIO.DAO.DAO_item_venta_alquiler;
	import FORMULARIO.ENTIDAD.item_venta_alquiler;
	import java.sql.Connection;
	import java.sql.SQLException;
	import javax.swing.JTable;
public class BO_item_venta_alquiler {
private DAO_item_venta_alquiler ivealq_dao = new DAO_item_venta_alquiler();
	EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

	public void insertar_item_venta_alquiler(item_venta_alquiler ivealq, JTable tbltabla) {
		String titulo = "insertar_item_venta_alquiler";
		Connection conn = ConnPostgres.getConnPosgres();
		try {
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			ivealq_dao.insertar_item_venta_alquiler(conn, ivealq);
			ivealq_dao.actualizar_tabla_item_venta_alquiler(conn, tbltabla);
			conn.commit();
		} catch (SQLException e) {
			evmen.mensaje_error(e, ivealq.toString(), titulo);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				evmen.Imprimir_serial_sql_error(e1, ivealq.toString(), titulo);
			}
		}
	}
	public void update_item_venta_alquiler(item_venta_alquiler ivealq, JTable tbltabla) {
		if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR ITEM_VENTA_ALQUILER", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
			String titulo = "update_item_venta_alquiler";
			Connection conn = ConnPostgres.getConnPosgres();
			try {
				if (conn.getAutoCommit()) {
					conn.setAutoCommit(false);
				}
				ivealq_dao.update_item_venta_alquiler(conn, ivealq);
				ivealq_dao.actualizar_tabla_item_venta_alquiler(conn, tbltabla);
				conn.commit();
			} catch (SQLException e) {
				evmen.mensaje_error(e, ivealq.toString(), titulo);
				try {
					conn.rollback();
				}catch (SQLException e1) {
					evmen.Imprimir_serial_sql_error(e1, ivealq.toString(), titulo);
				}
			}
		}
	}
}
