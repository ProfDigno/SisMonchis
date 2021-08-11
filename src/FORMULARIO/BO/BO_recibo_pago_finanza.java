	package FORMULARIO.BO;
	import BASEDATO.LOCAL.ConnPostgres;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import FORMULARIO.DAO.DAO_recibo_pago_finanza;
	import FORMULARIO.ENTIDAD.recibo_pago_finanza;
	import java.sql.Connection;
	import java.sql.SQLException;
	import javax.swing.JTable;
public class BO_recibo_pago_finanza {
private DAO_recibo_pago_finanza rpf_dao = new DAO_recibo_pago_finanza();
	EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

	public void insertar_recibo_pago_finanza(recibo_pago_finanza rpf, JTable tbltabla) {
		String titulo = "insertar_recibo_pago_finanza";
		Connection conn = ConnPostgres.getConnPosgres();
		try {
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			rpf_dao.insertar_recibo_pago_finanza(conn, rpf);
			rpf_dao.actualizar_tabla_recibo_pago_finanza(conn, tbltabla);
			conn.commit();
		} catch (SQLException e) {
			evmen.mensaje_error(e, rpf.toString(), titulo);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				evmen.Imprimir_serial_sql_error(e1, rpf.toString(), titulo);
			}
		}
	}
	public void update_recibo_pago_finanza(recibo_pago_finanza rpf, JTable tbltabla) {
		if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR RECIBO_PAGO_FINANZA", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
			String titulo = "update_recibo_pago_finanza";
			Connection conn = ConnPostgres.getConnPosgres();
			try {
				if (conn.getAutoCommit()) {
					conn.setAutoCommit(false);
				}
				rpf_dao.update_recibo_pago_finanza(conn, rpf);
				rpf_dao.actualizar_tabla_recibo_pago_finanza(conn, tbltabla);
				conn.commit();
			} catch (SQLException e) {
				evmen.mensaje_error(e, rpf.toString(), titulo);
				try {
					conn.rollback();
				}catch (SQLException e1) {
					evmen.Imprimir_serial_sql_error(e1, rpf.toString(), titulo);
				}
			}
		}
	}
}
