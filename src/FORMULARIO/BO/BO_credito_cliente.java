	package FORMULARIO.BO;
	import BASEDATO.LOCAL.ConnPostgres;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import FORMULARIO.DAO.DAO_credito_cliente;
	import FORMULARIO.ENTIDAD.credito_cliente;
	import java.sql.Connection;
	import java.sql.SQLException;
	import javax.swing.JTable;
public class BO_credito_cliente {
private DAO_credito_cliente crcl_dao = new DAO_credito_cliente();
	EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

	public void insertar_credito_cliente(credito_cliente crcl, JTable tbltabla) {
		String titulo = "insertar_credito_cliente";
		Connection conn = ConnPostgres.getConnPosgres();
		try {
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			crcl_dao.insertar_credito_cliente(conn, crcl);
			crcl_dao.actualizar_tabla_credito_cliente(conn, tbltabla);
			conn.commit();
		} catch (SQLException e) {
			evmen.mensaje_error(e, crcl.toString(), titulo);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				evmen.Imprimir_serial_sql_error(e1, crcl.toString(), titulo);
			}
		}
	}
	public void update_credito_cliente(credito_cliente crcl, JTable tbltabla) {
		if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR CREDITO_CLIENTE", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
			String titulo = "update_credito_cliente";
			Connection conn = ConnPostgres.getConnPosgres();
			try {
				if (conn.getAutoCommit()) {
					conn.setAutoCommit(false);
				}
				crcl_dao.update_credito_cliente(conn, crcl);
				crcl_dao.actualizar_tabla_credito_cliente(conn, tbltabla);
				conn.commit();
			} catch (SQLException e) {
				evmen.mensaje_error(e, crcl.toString(), titulo);
				try {
					conn.rollback();
				}catch (SQLException e1) {
					evmen.Imprimir_serial_sql_error(e1, crcl.toString(), titulo);
				}
			}
		}
	}
}
