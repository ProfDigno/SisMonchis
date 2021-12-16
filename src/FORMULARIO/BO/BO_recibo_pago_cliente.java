	package FORMULARIO.BO;
	import BASEDATO.LOCAL.ConnPostgres;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import FORMULARIO.DAO.DAO_recibo_pago_cliente;
	import FORMULARIO.ENTIDAD.recibo_pago_cliente;
	import java.sql.Connection;
	import java.sql.SQLException;
	import javax.swing.JTable;
public class BO_recibo_pago_cliente {
private DAO_recibo_pago_cliente rbcl_dao = new DAO_recibo_pago_cliente();
	EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

	public void insertar_recibo_pago_cliente(recibo_pago_cliente rbcl, JTable tbltabla) {
		String titulo = "insertar_recibo_pago_cliente";
		Connection conn = ConnPostgres.getConnPosgres();
		try {
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			rbcl_dao.insertar_recibo_pago_cliente(conn, rbcl);
			rbcl_dao.actualizar_tabla_recibo_pago_cliente(conn, tbltabla);
			conn.commit();
		} catch (SQLException e) {
			evmen.mensaje_error(e, rbcl.toString(), titulo);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				evmen.Imprimir_serial_sql_error(e1, rbcl.toString(), titulo);
			}
		}
	}
	public void update_recibo_pago_cliente(recibo_pago_cliente rbcl, JTable tbltabla) {
		if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR RECIBO_PAGO_CLIENTE", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
			String titulo = "update_recibo_pago_cliente";
			Connection conn = ConnPostgres.getConnPosgres();
			try {
				if (conn.getAutoCommit()) {
					conn.setAutoCommit(false);
				}
				rbcl_dao.update_recibo_pago_cliente(conn, rbcl);
				rbcl_dao.actualizar_tabla_recibo_pago_cliente(conn, tbltabla);
				conn.commit();
			} catch (SQLException e) {
				evmen.mensaje_error(e, rbcl.toString(), titulo);
				try {
					conn.rollback();
				}catch (SQLException e1) {
					evmen.Imprimir_serial_sql_error(e1, rbcl.toString(), titulo);
				}
			}
		}
	}
}
