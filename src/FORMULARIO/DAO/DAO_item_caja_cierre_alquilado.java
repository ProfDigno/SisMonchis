	package FORMULARIO.DAO;
	import BASEDATO.EvenConexion;
	import FORMULARIO.ENTIDAD.item_caja_cierre_alquilado;
	import Evento.JasperReport.EvenJasperReport;
	import Evento.Jtable.EvenJtable;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import Evento.Fecha.EvenFecha;
	import java.sql.ResultSet;
	import java.sql.Connection;
	import java.sql.PreparedStatement;
	import javax.swing.JTable;
public class DAO_item_caja_cierre_alquilado {
	EvenConexion eveconn = new EvenConexion();
	EvenJtable evejt = new EvenJtable();
	EvenJasperReport rep = new EvenJasperReport();
	EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
	EvenFecha evefec = new EvenFecha();
	private String mensaje_insert = "ITEM_CAJA_CIERRE_ALQUILADO GUARDADO CORRECTAMENTE";
	private String mensaje_update = "ITEM_CAJA_CIERRE_ALQUILADO MODIFICADO CORECTAMENTE";
	private String sql_insert = "INSERT INTO item_caja_cierre_alquilado(idcaja_detalle_alquilado,fecha_emision,descripcion,tabla_origen,estado,cierre,monto_alquilado_efectivo,monto_alquilado_tarjeta,monto_alquilado_transferencia,monto_recibo_pago,monto_delivery,monto_gasto,monto_vale,monto_compra_contado,monto_compra_credito,monto_apertura_caja,monto_cierre_caja,fk_idgasto,fk_idcompra,fk_idventa_alquiler,fk_idvale,fk_idrecibo_pago_cliente,fk_idusuario) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
	private String sql_update = "UPDATE item_caja_cierre_alquilado SET fecha_emision=?,descripcion=?,tabla_origen=?,estado=?,cierre=?,monto_alquilado_efectivo=?,monto_alquilado_tarjeta=?,monto_alquilado_transferencia=?,monto_recibo_pago=?,monto_delivery=?,monto_gasto=?,monto_vale=?,monto_compra_contado=?,monto_compra_credito=?,monto_apertura_caja=?,monto_cierre_caja=?,fk_idgasto=?,fk_idcompra=?,fk_idventa_alquiler=?,fk_idvale=?,fk_idrecibo_pago_cliente=?,fk_idusuario=? WHERE idcaja_detalle_alquilado=?;";
	private String sql_select = "SELECT idcaja_detalle_alquilado,fecha_emision,descripcion,tabla_origen,estado,cierre,monto_alquilado_efectivo,monto_alquilado_tarjeta,monto_alquilado_transferencia,monto_recibo_pago,monto_delivery,monto_gasto,monto_vale,monto_compra_contado,monto_compra_credito,monto_apertura_caja,monto_cierre_caja,fk_idgasto,fk_idcompra,fk_idventa_alquiler,fk_idvale,fk_idrecibo_pago_cliente,fk_idusuario FROM item_caja_cierre_alquilado order by 1 desc;";
	private String sql_cargar = "SELECT idcaja_detalle_alquilado,fecha_emision,descripcion,tabla_origen,estado,cierre,monto_alquilado_efectivo,monto_alquilado_tarjeta,monto_alquilado_transferencia,monto_recibo_pago,monto_delivery,monto_gasto,monto_vale,monto_compra_contado,monto_compra_credito,monto_apertura_caja,monto_cierre_caja,fk_idgasto,fk_idcompra,fk_idventa_alquiler,fk_idvale,fk_idrecibo_pago_cliente,fk_idusuario FROM item_caja_cierre_alquilado WHERE idcaja_detalle_alquilado=";
	public void insertar_item_caja_cierre_alquilado(Connection conn, item_caja_cierre_alquilado iccal){
		iccal.setC1idcaja_detalle_alquilado(eveconn.getInt_ultimoID_mas_uno(conn, iccal.getTb_item_caja_cierre_alquilado(), iccal.getId_idcaja_detalle_alquilado()));
		String titulo = "insertar_item_caja_cierre_alquilado";
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql_insert);
			pst.setInt(1,iccal.getC1idcaja_detalle_alquilado());
			pst.setTimestamp(2,evefec.getTimestamp_sistema());
			pst.setString(3,iccal.getC3descripcion());
			pst.setString(4,iccal.getC4tabla_origen());
			pst.setString(5,iccal.getC5estado());
			pst.setString(6,iccal.getC6cierre());
			pst.setDouble(7,iccal.getC7monto_alquilado_efectivo());
			pst.setDouble(8,iccal.getC8monto_alquilado_tarjeta());
			pst.setDouble(9,iccal.getC9monto_alquilado_transferencia());
			pst.setDouble(10,iccal.getC10monto_recibo_pago());
			pst.setDouble(11,iccal.getC11monto_delivery());
			pst.setDouble(12,iccal.getC12monto_gasto());
			pst.setDouble(13,iccal.getC13monto_vale());
			pst.setDouble(14,iccal.getC14monto_compra_contado());
			pst.setDouble(15,iccal.getC15monto_compra_credito());
			pst.setDouble(16,iccal.getC16monto_apertura_caja());
			pst.setDouble(17,iccal.getC17monto_cierre_caja());
			pst.setInt(18,iccal.getC18fk_idgasto());
			pst.setInt(19,iccal.getC19fk_idcompra());
			pst.setInt(20,iccal.getC20fk_idventa_alquiler());
			pst.setInt(21,iccal.getC21fk_idvale());
			pst.setInt(22,iccal.getC22fk_idrecibo_pago_cliente());
			pst.setInt(23,iccal.getC23fk_idusuario());
			pst.execute();
			pst.close();
			evemen.Imprimir_serial_sql(sql_insert + "\n" + iccal.toString(), titulo);
			evemen.guardado_correcto(mensaje_insert, true);
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_insert + "\n" + iccal.toString(), titulo);
		}
	}
	public void update_item_caja_cierre_alquilado(Connection conn, item_caja_cierre_alquilado iccal){
		String titulo = "update_item_caja_cierre_alquilado";
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql_update);
			pst.setTimestamp(1,evefec.getTimestamp_sistema());
			pst.setString(2,iccal.getC3descripcion());
			pst.setString(3,iccal.getC4tabla_origen());
			pst.setString(4,iccal.getC5estado());
			pst.setString(5,iccal.getC6cierre());
			pst.setDouble(6,iccal.getC7monto_alquilado_efectivo());
			pst.setDouble(7,iccal.getC8monto_alquilado_tarjeta());
			pst.setDouble(8,iccal.getC9monto_alquilado_transferencia());
			pst.setDouble(9,iccal.getC10monto_recibo_pago());
			pst.setDouble(10,iccal.getC11monto_delivery());
			pst.setDouble(11,iccal.getC12monto_gasto());
			pst.setDouble(12,iccal.getC13monto_vale());
			pst.setDouble(13,iccal.getC14monto_compra_contado());
			pst.setDouble(14,iccal.getC15monto_compra_credito());
			pst.setDouble(15,iccal.getC16monto_apertura_caja());
			pst.setDouble(16,iccal.getC17monto_cierre_caja());
			pst.setInt(17,iccal.getC18fk_idgasto());
			pst.setInt(18,iccal.getC19fk_idcompra());
			pst.setInt(19,iccal.getC20fk_idventa_alquiler());
			pst.setInt(20,iccal.getC21fk_idvale());
			pst.setInt(21,iccal.getC22fk_idrecibo_pago_cliente());
			pst.setInt(22,iccal.getC23fk_idusuario());
			pst.setInt(23,iccal.getC1idcaja_detalle_alquilado());
			pst.execute();
			pst.close();
			evemen.Imprimir_serial_sql(sql_update + "\n" + iccal.toString(), titulo);
			evemen.modificado_correcto(mensaje_update, true);
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_update + "\n" + iccal.toString(), titulo);
		}
	}
	public void cargar_item_caja_cierre_alquilado(Connection conn, item_caja_cierre_alquilado iccal,int id){
		String titulo = "Cargar_item_caja_cierre_alquilado";
		try {
			ResultSet rs=eveconn.getResulsetSQL(conn,sql_cargar+id, titulo);
			if(rs.next()){
				iccal.setC1idcaja_detalle_alquilado(rs.getInt(1));
				iccal.setC2fecha_emision(rs.getString(2));
				iccal.setC3descripcion(rs.getString(3));
				iccal.setC4tabla_origen(rs.getString(4));
				iccal.setC5estado(rs.getString(5));
				iccal.setC6cierre(rs.getString(6));
				iccal.setC7monto_alquilado_efectivo(rs.getDouble(7));
				iccal.setC8monto_alquilado_tarjeta(rs.getDouble(8));
				iccal.setC9monto_alquilado_transferencia(rs.getDouble(9));
				iccal.setC10monto_recibo_pago(rs.getDouble(10));
				iccal.setC11monto_delivery(rs.getDouble(11));
				iccal.setC12monto_gasto(rs.getDouble(12));
				iccal.setC13monto_vale(rs.getDouble(13));
				iccal.setC14monto_compra_contado(rs.getDouble(14));
				iccal.setC15monto_compra_credito(rs.getDouble(15));
				iccal.setC16monto_apertura_caja(rs.getDouble(16));
				iccal.setC17monto_cierre_caja(rs.getDouble(17));
				iccal.setC18fk_idgasto(rs.getInt(18));
				iccal.setC19fk_idcompra(rs.getInt(19));
				iccal.setC20fk_idventa_alquiler(rs.getInt(20));
				iccal.setC21fk_idvale(rs.getInt(21));
				iccal.setC22fk_idrecibo_pago_cliente(rs.getInt(22));
				iccal.setC23fk_idusuario(rs.getInt(23));
				evemen.Imprimir_serial_sql(sql_cargar + "\n" + iccal.toString(), titulo);
			}
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_cargar + "\n" + iccal.toString(), titulo);
		}
	}
	public void actualizar_tabla_item_caja_cierre_alquilado(Connection conn,JTable tbltabla){
		eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
		ancho_tabla_item_caja_cierre_alquilado(tbltabla);
	}
	public void ancho_tabla_item_caja_cierre_alquilado(JTable tbltabla){
		int Ancho[]={4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4};
		evejt.setAnchoColumnaJtable(tbltabla, Ancho);
	}
}
