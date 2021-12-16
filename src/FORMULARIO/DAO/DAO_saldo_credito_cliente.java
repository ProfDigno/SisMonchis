	package FORMULARIO.DAO;
	import BASEDATO.EvenConexion;
	import FORMULARIO.ENTIDAD.saldo_credito_cliente;
	import Evento.JasperReport.EvenJasperReport;
	import Evento.Jtable.EvenJtable;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import Evento.Fecha.EvenFecha;
	import java.sql.ResultSet;
	import java.sql.Connection;
	import java.sql.PreparedStatement;
	import javax.swing.JTable;
public class DAO_saldo_credito_cliente {
	EvenConexion eveconn = new EvenConexion();
	EvenJtable evejt = new EvenJtable();
	EvenJasperReport rep = new EvenJasperReport();
	EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
	EvenFecha evefec = new EvenFecha();
	private String mensaje_insert = "SALDO_CREDITO_CLIENTE GUARDADO CORRECTAMENTE";
	private String mensaje_update = "SALDO_CREDITO_CLIENTE MODIFICADO CORECTAMENTE";
	private String sql_insert = "INSERT INTO saldo_credito_cliente(idsaldo_credito_cliente,fecha_emision,descripcion,monto_saldo_credito,monto_letra,estado,fk_idcliente,fk_idusuario) VALUES (?,?,?,?,?,?,?,?);";
	private String sql_update = "UPDATE saldo_credito_cliente SET fecha_emision=?,descripcion=?,monto_saldo_credito=?,monto_letra=?,estado=?,fk_idcliente=?,fk_idusuario=? WHERE idsaldo_credito_cliente=?;";
	private String sql_select = "SELECT idsaldo_credito_cliente,fecha_emision,descripcion,monto_saldo_credito,monto_letra,estado,fk_idcliente,fk_idusuario FROM saldo_credito_cliente order by 1 desc;";
	private String sql_cargar = "SELECT idsaldo_credito_cliente,fecha_emision,descripcion,monto_saldo_credito,monto_letra,estado,fk_idcliente,fk_idusuario FROM saldo_credito_cliente WHERE idsaldo_credito_cliente=";
	public void insertar_saldo_credito_cliente(Connection conn, saldo_credito_cliente sccl){
		sccl.setC1idsaldo_credito_cliente(eveconn.getInt_ultimoID_mas_uno(conn, sccl.getTb_saldo_credito_cliente(), sccl.getId_idsaldo_credito_cliente()));
		String titulo = "insertar_saldo_credito_cliente";
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql_insert);
			pst.setInt(1,sccl.getC1idsaldo_credito_cliente());
			pst.setTimestamp(2,evefec.getTimestamp_sistema());
			pst.setString(3,sccl.getC3descripcion());
			pst.setDouble(4,sccl.getC4monto_saldo_credito());
			pst.setString(5,sccl.getC5monto_letra());
			pst.setString(6,sccl.getC6estado());
			pst.setInt(7,sccl.getC7fk_idcliente());
			pst.setInt(8,sccl.getC8fk_idusuario());
			pst.execute();
			pst.close();
			evemen.Imprimir_serial_sql(sql_insert + "\n" + sccl.toString(), titulo);
			evemen.guardado_correcto(mensaje_insert, true);
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_insert + "\n" + sccl.toString(), titulo);
		}
	}
	public void update_saldo_credito_cliente(Connection conn, saldo_credito_cliente sccl){
		String titulo = "update_saldo_credito_cliente";
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql_update);
			pst.setTimestamp(1,evefec.getTimestamp_sistema());
			pst.setString(2,sccl.getC3descripcion());
			pst.setDouble(3,sccl.getC4monto_saldo_credito());
			pst.setString(4,sccl.getC5monto_letra());
			pst.setString(5,sccl.getC6estado());
			pst.setInt(6,sccl.getC7fk_idcliente());
			pst.setInt(7,sccl.getC8fk_idusuario());
			pst.setInt(8,sccl.getC1idsaldo_credito_cliente());
			pst.execute();
			pst.close();
			evemen.Imprimir_serial_sql(sql_update + "\n" + sccl.toString(), titulo);
			evemen.modificado_correcto(mensaje_update, true);
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_update + "\n" + sccl.toString(), titulo);
		}
	}
	public void cargar_saldo_credito_cliente(Connection conn, saldo_credito_cliente sccl,int id){
		String titulo = "Cargar_saldo_credito_cliente";
		try {
			ResultSet rs=eveconn.getResulsetSQL(conn,sql_cargar+id, titulo);
			if(rs.next()){
				sccl.setC1idsaldo_credito_cliente(rs.getInt(1));
				sccl.setC2fecha_emision(rs.getString(2));
				sccl.setC3descripcion(rs.getString(3));
				sccl.setC4monto_saldo_credito(rs.getDouble(4));
				sccl.setC5monto_letra(rs.getString(5));
				sccl.setC6estado(rs.getString(6));
				sccl.setC7fk_idcliente(rs.getInt(7));
				sccl.setC8fk_idusuario(rs.getInt(8));
				evemen.Imprimir_serial_sql(sql_cargar + "\n" + sccl.toString(), titulo);
			}
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_cargar + "\n" + sccl.toString(), titulo);
		}
	}
	public void actualizar_tabla_saldo_credito_cliente(Connection conn,JTable tbltabla){
		eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
		ancho_tabla_saldo_credito_cliente(tbltabla);
	}
	public void ancho_tabla_saldo_credito_cliente(JTable tbltabla){
		int Ancho[]={12,12,12,12,12,12,12,12};
		evejt.setAnchoColumnaJtable(tbltabla, Ancho);
	}
}
