	package FORMULARIO.BO;
	import BASEDATO.LOCAL.ConnPostgres;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import FORMULARIO.DAO.DAO_producto_marca;
	import FORMULARIO.ENTIDAD.producto_marca;
	import java.sql.Connection;
	import java.sql.SQLException;
	import javax.swing.JTable;
public class BO_producto_marca {
private DAO_producto_marca marca_dao = new DAO_producto_marca();
	EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

	public void insertar_producto_marca(producto_marca marca, JTable tbltabla) {
		String titulo = "insertar_producto_marca";
		Connection conn = ConnPostgres.getConnPosgres();
		try {
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			marca_dao.insertar_producto_marca(conn, marca);
			marca_dao.actualizar_tabla_producto_marca(conn, tbltabla);
			conn.commit();
		} catch (SQLException e) {
			evmen.mensaje_error(e, marca.toString(), titulo);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				evmen.Imprimir_serial_sql_error(e1, marca.toString(), titulo);
			}
		}
	}
	public void update_producto_marca(producto_marca marca, JTable tbltabla) {
		if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR PRODUCTO_MARCA", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
			String titulo = "update_producto_marca";
			Connection conn = ConnPostgres.getConnPosgres();
			try {
				if (conn.getAutoCommit()) {
					conn.setAutoCommit(false);
				}
				marca_dao.update_producto_marca(conn, marca);
				marca_dao.actualizar_tabla_producto_marca(conn, tbltabla);
				conn.commit();
			} catch (SQLException e) {
				evmen.mensaje_error(e, marca.toString(), titulo);
				try {
					conn.rollback();
				}catch (SQLException e1) {
					evmen.Imprimir_serial_sql_error(e1, marca.toString(), titulo);
				}
			}
		}
	}
}
