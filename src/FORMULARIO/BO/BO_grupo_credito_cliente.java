	package FORMULARIO.BO;
	import BASEDATO.LOCAL.ConnPostgres;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import FORMULARIO.DAO.DAO_grupo_credito_cliente;
	import FORMULARIO.ENTIDAD.grupo_credito_cliente;
	import java.sql.Connection;
	import java.sql.SQLException;
	import javax.swing.JTable;
public class BO_grupo_credito_cliente {
private DAO_grupo_credito_cliente gccl_dao = new DAO_grupo_credito_cliente();
	EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

	public void insertar_grupo_credito_cliente(grupo_credito_cliente gccl, JTable tbltabla) {
		String titulo = "insertar_grupo_credito_cliente";
		Connection conn = ConnPostgres.getConnPosgres();
		try {
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			gccl_dao.insertar_grupo_credito_cliente(conn, gccl);
			gccl_dao.actualizar_tabla_grupo_credito_cliente(conn, tbltabla);
			conn.commit();
		} catch (SQLException e) {
			evmen.mensaje_error(e, gccl.toString(), titulo);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				evmen.Imprimir_serial_sql_error(e1, gccl.toString(), titulo);
			}
		}
	}
	public void update_grupo_credito_cliente(grupo_credito_cliente gccl, JTable tbltabla) {
		if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR GRUPO_CREDITO_CLIENTE", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
			String titulo = "update_grupo_credito_cliente";
			Connection conn = ConnPostgres.getConnPosgres();
			try {
				if (conn.getAutoCommit()) {
					conn.setAutoCommit(false);
				}
				gccl_dao.update_grupo_credito_cliente(conn, gccl);
				gccl_dao.actualizar_tabla_grupo_credito_cliente(conn, tbltabla);
				conn.commit();
			} catch (SQLException e) {
				evmen.mensaje_error(e, gccl.toString(), titulo);
				try {
					conn.rollback();
				}catch (SQLException e1) {
					evmen.Imprimir_serial_sql_error(e1, gccl.toString(), titulo);
				}
			}
		}
	}
}
