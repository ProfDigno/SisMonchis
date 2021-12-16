	package FORMULARIO.DAO;
	import BASEDATO.EvenConexion;
	import FORMULARIO.ENTIDAD.dato_banco;
	import Evento.JasperReport.EvenJasperReport;
	import Evento.Jtable.EvenJtable;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import Evento.Fecha.EvenFecha;
	import java.sql.ResultSet;
	import java.sql.Connection;
	import java.sql.PreparedStatement;
	import javax.swing.JTable;
public class DAO_dato_banco {
	EvenConexion eveconn = new EvenConexion();
	EvenJtable evejt = new EvenJtable();
	EvenJasperReport rep = new EvenJasperReport();
	EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
	EvenFecha evefec = new EvenFecha();
	private String mensaje_insert = "DATO_BANCO GUARDADO CORRECTAMENTE";
	private String mensaje_update = "DATO_BANCO MODIFICADO CORECTAMENTE";
	private String sql_insert = "INSERT INTO dato_banco(iddato_banco,titular,documento,nro_cuenta,mibanco,fk_idbanco) VALUES (?,?,?,?,?,?);";
	private String sql_update = "UPDATE dato_banco SET titular=?,documento=?,nro_cuenta=?,mibanco=?,fk_idbanco=? WHERE iddato_banco=?;";
	private String sql_select = "SELECT iddato_banco,titular,documento,nro_cuenta,mibanco,fk_idbanco FROM dato_banco order by 1 desc;";
	private String sql_cargar = "SELECT iddato_banco,titular,documento,nro_cuenta,mibanco,fk_idbanco FROM dato_banco WHERE iddato_banco=";
	public void insertar_dato_banco(Connection conn, dato_banco dban){
		dban.setC1iddato_banco(eveconn.getInt_ultimoID_mas_uno(conn, dban.getTb_dato_banco(), dban.getId_iddato_banco()));
		String titulo = "insertar_dato_banco";
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql_insert);
			pst.setInt(1,dban.getC1iddato_banco());
			pst.setString(2,dban.getC2titular());
			pst.setString(3,dban.getC3documento());
			pst.setString(4,dban.getC4nro_cuenta());
			pst.setBoolean(5,dban.getC5mibanco());
			pst.setInt(6,dban.getC6fk_idbanco());
			pst.execute();
			pst.close();
			evemen.Imprimir_serial_sql(sql_insert + "\n" + dban.toString(), titulo);
			evemen.guardado_correcto(mensaje_insert, true);
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_insert + "\n" + dban.toString(), titulo);
		}
	}
	public void update_dato_banco(Connection conn, dato_banco dban){
		String titulo = "update_dato_banco";
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql_update);
			pst.setString(1,dban.getC2titular());
			pst.setString(2,dban.getC3documento());
			pst.setString(3,dban.getC4nro_cuenta());
			pst.setBoolean(4,dban.getC5mibanco());
			pst.setInt(5,dban.getC6fk_idbanco());
			pst.setInt(6,dban.getC1iddato_banco());
			pst.execute();
			pst.close();
			evemen.Imprimir_serial_sql(sql_update + "\n" + dban.toString(), titulo);
			evemen.modificado_correcto(mensaje_update, true);
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_update + "\n" + dban.toString(), titulo);
		}
	}
	public void cargar_dato_banco(Connection conn, dato_banco dban,int id){
		String titulo = "Cargar_dato_banco";
		try {
			ResultSet rs=eveconn.getResulsetSQL(conn,sql_cargar+id, titulo);
			if(rs.next()){
				dban.setC1iddato_banco(rs.getInt(1));
				dban.setC2titular(rs.getString(2));
				dban.setC3documento(rs.getString(3));
				dban.setC4nro_cuenta(rs.getString(4));
				dban.setC5mibanco(rs.getBoolean(5));
				dban.setC6fk_idbanco(rs.getInt(6));
				evemen.Imprimir_serial_sql(sql_cargar + "\n" + dban.toString(), titulo);
			}
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_cargar + "\n" + dban.toString(), titulo);
		}
	}
	public void actualizar_tabla_dato_banco(Connection conn,JTable tbltabla){
		eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
		ancho_tabla_dato_banco(tbltabla);
	}
	public void ancho_tabla_dato_banco(JTable tbltabla){
		int Ancho[]={16,16,16,16,16,16};
		evejt.setAnchoColumnaJtable(tbltabla, Ancho);
	}
}
