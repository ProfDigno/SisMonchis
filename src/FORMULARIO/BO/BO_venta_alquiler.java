	package FORMULARIO.BO;
	import BASEDATO.LOCAL.ConnPostgres;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import FORMULARIO.DAO.DAO_venta_alquiler;
	import FORMULARIO.ENTIDAD.venta_alquiler;
	import java.sql.Connection;
	import java.sql.SQLException;
	import javax.swing.JTable;
public class BO_venta_alquiler {
private DAO_venta_alquiler vealq_dao = new DAO_venta_alquiler();
	EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

	public void insertar_venta_alquiler(venta_alquiler vealq, JTable tbltabla) {
		String titulo = "insertar_venta_alquiler";
		Connection conn = ConnPostgres.getConnPosgres();
		try {
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			vealq_dao.insertar_venta_alquiler(conn, vealq);
			vealq_dao.actualizar_tabla_venta_alquiler(conn, tbltabla);
			conn.commit();
		} catch (SQLException e) {
			evmen.mensaje_error(e, vealq.toString(), titulo);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				evmen.Imprimir_serial_sql_error(e1, vealq.toString(), titulo);
			}
		}
	}
	public void update_venta_alquiler(venta_alquiler vealq, JTable tbltabla) {
		if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR VENTA_ALQUILER", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
			String titulo = "update_venta_alquiler";
			Connection conn = ConnPostgres.getConnPosgres();
			try {
				if (conn.getAutoCommit()) {
					conn.setAutoCommit(false);
				}
				vealq_dao.update_venta_alquiler(conn, vealq);
				vealq_dao.actualizar_tabla_venta_alquiler(conn, tbltabla);
				conn.commit();
			} catch (SQLException e) {
				evmen.mensaje_error(e, vealq.toString(), titulo);
				try {
					conn.rollback();
				}catch (SQLException e1) {
					evmen.Imprimir_serial_sql_error(e1, vealq.toString(), titulo);
				}
			}
		}
	}
}
