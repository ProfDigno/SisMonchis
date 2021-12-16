	package FORMULARIO.DAO;
	import BASEDATO.EvenConexion;
	import FORMULARIO.ENTIDAD.transaccion_banco;
	import Evento.JasperReport.EvenJasperReport;
	import Evento.Jtable.EvenJtable;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import Evento.Fecha.EvenFecha;
	import java.sql.ResultSet;
	import java.sql.Connection;
	import java.sql.PreparedStatement;
	import javax.swing.JTable;
public class DAO_transaccion_banco {
	EvenConexion eveconn = new EvenConexion();
	EvenJtable evejt = new EvenJtable();
	EvenJasperReport rep = new EvenJasperReport();
	EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
	EvenFecha evefec = new EvenFecha();
	private String mensaje_insert = "TRANSACCION_BANCO GUARDADO CORRECTAMENTE";
	private String mensaje_update = "TRANSACCION_BANCO MODIFICADO CORECTAMENTE";
	private String sql_insert = "INSERT INTO transaccion_banco(idtransaccion_banco,fecha_creado,nro_transaccion,monto,observacion,concepto,fk_iddato_banco_cliente,fk_iddato_banco_local,fk_idventa_alquiler) VALUES (?,?,?,?,?,?,?,?,?);";
	private String sql_update = "UPDATE transaccion_banco SET fecha_creado=?,nro_transaccion=?,monto=?,observacion=?,concepto=?,fk_iddato_banco_cliente=?,fk_iddato_banco_local=?,fk_idventa_alquiler=? WHERE idtransaccion_banco=?;";
	private String sql_select = "SELECT idtransaccion_banco,fecha_creado,nro_transaccion,monto,observacion,concepto,fk_iddato_banco_cliente,fk_iddato_banco_local,fk_idventa_alquiler FROM transaccion_banco order by 1 desc;";
	private String sql_cargar = "SELECT idtransaccion_banco,fecha_creado,nro_transaccion,monto,observacion,concepto,fk_iddato_banco_cliente,fk_iddato_banco_local,fk_idventa_alquiler FROM transaccion_banco WHERE idtransaccion_banco=";
	public void insertar_transaccion_banco(Connection conn, transaccion_banco tsban){
		tsban.setC1idtransaccion_banco(eveconn.getInt_ultimoID_mas_uno(conn, tsban.getTb_transaccion_banco(), tsban.getId_idtransaccion_banco()));
		String titulo = "insertar_transaccion_banco";
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql_insert);
			pst.setInt(1,tsban.getC1idtransaccion_banco());
			pst.setTimestamp(2,evefec.getTimestamp_sistema());
			pst.setString(3,tsban.getC3nro_transaccion());
			pst.setDouble(4,tsban.getC4monto());
			pst.setString(5,tsban.getC5observacion());
			pst.setString(6,tsban.getC6concepto());
			pst.setInt(7,tsban.getC7fk_iddato_banco_cliente());
			pst.setInt(8,tsban.getC8fk_iddato_banco_local());
			pst.setInt(9,tsban.getC9fk_idventa_alquiler());
			pst.execute();
			pst.close();
			evemen.Imprimir_serial_sql(sql_insert + "\n" + tsban.toString(), titulo);
			evemen.guardado_correcto(mensaje_insert, true);
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_insert + "\n" + tsban.toString(), titulo);
		}
	}
	public void update_transaccion_banco(Connection conn, transaccion_banco tsban){
		String titulo = "update_transaccion_banco";
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql_update);
			pst.setTimestamp(1,evefec.getTimestamp_sistema());
			pst.setString(2,tsban.getC3nro_transaccion());
			pst.setDouble(3,tsban.getC4monto());
			pst.setString(4,tsban.getC5observacion());
			pst.setString(5,tsban.getC6concepto());
			pst.setInt(6,tsban.getC7fk_iddato_banco_cliente());
			pst.setInt(7,tsban.getC8fk_iddato_banco_local());
			pst.setInt(8,tsban.getC9fk_idventa_alquiler());
			pst.setInt(9,tsban.getC1idtransaccion_banco());
			pst.execute();
			pst.close();
			evemen.Imprimir_serial_sql(sql_update + "\n" + tsban.toString(), titulo);
			evemen.modificado_correcto(mensaje_update, true);
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_update + "\n" + tsban.toString(), titulo);
		}
	}
	public void cargar_transaccion_banco(Connection conn, transaccion_banco tsban,int id){
		String titulo = "Cargar_transaccion_banco";
		try {
			ResultSet rs=eveconn.getResulsetSQL(conn,sql_cargar+id, titulo);
			if(rs.next()){
				tsban.setC1idtransaccion_banco(rs.getInt(1));
				tsban.setC2fecha_creado(rs.getString(2));
				tsban.setC3nro_transaccion(rs.getString(3));
				tsban.setC4monto(rs.getDouble(4));
				tsban.setC5observacion(rs.getString(5));
				tsban.setC6concepto(rs.getString(6));
				tsban.setC7fk_iddato_banco_cliente(rs.getInt(7));
				tsban.setC8fk_iddato_banco_local(rs.getInt(8));
				tsban.setC9fk_idventa_alquiler(rs.getInt(9));
				evemen.Imprimir_serial_sql(sql_cargar + "\n" + tsban.toString(), titulo);
			}
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_cargar + "\n" + tsban.toString(), titulo);
		}
	}
	public void actualizar_tabla_transaccion_banco(Connection conn,JTable tbltabla){
		eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
		ancho_tabla_transaccion_banco(tbltabla);
	}
	public void ancho_tabla_transaccion_banco(JTable tbltabla){
		int Ancho[]={11,11,11,11,11,11,11,11,11};
		evejt.setAnchoColumnaJtable(tbltabla, Ancho);
	}
}
