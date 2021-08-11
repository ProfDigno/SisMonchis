	package FORMULARIO.DAO;
	import BASEDATO.EvenConexion;
	import FORMULARIO.ENTIDAD.saldo_credito_finanza;
	import Evento.JasperReport.EvenJasperReport;
	import Evento.Jtable.EvenJtable;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import Evento.Fecha.EvenFecha;
	import java.sql.ResultSet;
	import java.sql.Connection;
	import java.sql.PreparedStatement;
	import javax.swing.JTable;
public class DAO_saldo_credito_finanza {
	EvenConexion eveconn = new EvenConexion();
	EvenJtable evejt = new EvenJtable();
	EvenJasperReport rep = new EvenJasperReport();
	EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
	EvenFecha evefec = new EvenFecha();
	private String mensaje_insert = "SALDO_CREDITO_FINANZA GUARDADO CORRECTAMENTE";
	private String mensaje_update = "SALDO_CREDITO_FINANZA MODIFICADO CORECTAMENTE";
	private String sql_insert = "INSERT INTO saldo_credito_finanza(idsaldo_credito_finanza,fecha_emision,descripcion,monto_saldo_credito,monto_letra,estado,fk_idfinancista,fk_idusuario) VALUES (?,?,?,?,?,?,?,?);";
	private String sql_update = "UPDATE saldo_credito_finanza SET fecha_emision=?,descripcion=?,monto_saldo_credito=?,monto_letra=?,estado=?,fk_idfinancista=?,fk_idusuario=? WHERE idsaldo_credito_finanza=?;";
	private String sql_select = "SELECT idsaldo_credito_finanza,fecha_emision,descripcion,monto_saldo_credito,monto_letra,estado,fk_idfinancista,fk_idusuario FROM saldo_credito_finanza order by 1 desc;";
	private String sql_cargar = "SELECT idsaldo_credito_finanza,fecha_emision,descripcion,monto_saldo_credito,monto_letra,estado,fk_idfinancista,fk_idusuario FROM saldo_credito_finanza WHERE idsaldo_credito_finanza=";
	public void insertar_saldo_credito_finanza(Connection conn, saldo_credito_finanza scf){
		scf.setC1idsaldo_credito_finanza(eveconn.getInt_ultimoID_mas_uno(conn, scf.getTb_saldo_credito_finanza(), scf.getId_idsaldo_credito_finanza()));
		String titulo = "insertar_saldo_credito_finanza";
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql_insert);
			pst.setInt(1,scf.getC1idsaldo_credito_finanza());
			pst.setTimestamp(2,evefec.getTimestamp_sistema());
			pst.setString(3,scf.getC3descripcion());
			pst.setDouble(4,scf.getC4monto_saldo_credito());
			pst.setString(5,scf.getC5monto_letra());
			pst.setString(6,scf.getC6estado());
			pst.setInt(7,scf.getC7fk_idfinancista());
			pst.setInt(8,scf.getC8fk_idusuario());
			pst.execute();
			pst.close();
			evemen.Imprimir_serial_sql(sql_insert + "\n" + scf.toString(), titulo);
			evemen.guardado_correcto(mensaje_insert, false);
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_insert + "\n" + scf.toString(), titulo);
		}
	}
	public void update_saldo_credito_finanza(Connection conn, saldo_credito_finanza scf){
		String titulo = "update_saldo_credito_finanza";
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql_update);
			pst.setTimestamp(1,evefec.getTimestamp_sistema());
			pst.setString(2,scf.getC3descripcion());
			pst.setDouble(3,scf.getC4monto_saldo_credito());
			pst.setString(4,scf.getC5monto_letra());
			pst.setString(5,scf.getC6estado());
			pst.setInt(6,scf.getC7fk_idfinancista());
			pst.setInt(7,scf.getC8fk_idusuario());
			pst.setInt(8,scf.getC1idsaldo_credito_finanza());
			pst.execute();
			pst.close();
			evemen.Imprimir_serial_sql(sql_update + "\n" + scf.toString(), titulo);
			evemen.modificado_correcto(mensaje_update, true);
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_update + "\n" + scf.toString(), titulo);
		}
	}
	public void cargar_saldo_credito_finanza(Connection conn, saldo_credito_finanza scf,int id){
		String titulo = "Cargar_saldo_credito_finanza";
		try {
			ResultSet rs=eveconn.getResulsetSQL(conn,sql_cargar+id, titulo);
			if(rs.next()){
				scf.setC1idsaldo_credito_finanza(rs.getInt(1));
				scf.setC2fecha_emision(rs.getString(2));
				scf.setC3descripcion(rs.getString(3));
				scf.setC4monto_saldo_credito(rs.getDouble(4));
				scf.setC5monto_letra(rs.getString(5));
				scf.setC6estado(rs.getString(6));
				scf.setC7fk_idfinancista(rs.getInt(7));
				scf.setC8fk_idusuario(rs.getInt(8));
				evemen.Imprimir_serial_sql(sql_cargar + "\n" + scf.toString(), titulo);
			}
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_cargar + "\n" + scf.toString(), titulo);
		}
	}
	public void actualizar_tabla_saldo_credito_finanza(Connection conn,JTable tbltabla){
		eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
		ancho_tabla_saldo_credito_finanza(tbltabla);
	}
	public void ancho_tabla_saldo_credito_finanza(JTable tbltabla){
		int Ancho[]={12,12,12,12,12,12,12,12};
		evejt.setAnchoColumnaJtable(tbltabla, Ancho);
	}
}
