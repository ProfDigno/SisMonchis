package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.producto_marca;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_producto_marca {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "PRODUCTO_MARCA GUARDADO CORRECTAMENTE";
    private String mensaje_update = "PRODUCTO_MARCA MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO producto_marca(idproducto_marca,nombre,activar,orden) VALUES (?,?,?,?);";
    private String sql_update = "UPDATE producto_marca SET nombre=?,activar=?,orden=? WHERE idproducto_marca=?;";
    private String sql_select = "SELECT idproducto_marca,nombre,activar,orden FROM producto_marca order by 1 desc;";
    private String sql_cargar = "SELECT idproducto_marca,nombre,activar,orden FROM producto_marca WHERE idproducto_marca=";

    public void insertar_producto_marca(Connection conn, producto_marca marca) {
        marca.setC1idproducto_marca(eveconn.getInt_ultimoID_mas_uno(conn, marca.getTb_producto_marca(), marca.getId_idproducto_marca()));
        String titulo = "insertar_producto_marca";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, marca.getC1idproducto_marca());
            pst.setString(2, marca.getC2nombre());
            pst.setBoolean(3, marca.getC3activar());
            pst.setInt(4, marca.getC4orden());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + marca.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + marca.toString(), titulo);
        }
    }

    public void update_producto_marca(Connection conn, producto_marca marca) {
        String titulo = "update_producto_marca";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setString(1, marca.getC2nombre());
            pst.setBoolean(2, marca.getC3activar());
            pst.setInt(3, marca.getC4orden());
            pst.setInt(4, marca.getC1idproducto_marca());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + marca.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + marca.toString(), titulo);
        }
    }

    public void cargar_producto_marca(Connection conn, producto_marca marca, JTable tabla) {
        String titulo = "Cargar_producto_marca";
        int id = evejt.getInt_select_id(tabla);
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + id, titulo);
            if (rs.next()) {
                marca.setC1idproducto_marca(rs.getInt(1));
                marca.setC2nombre(rs.getString(2));
                marca.setC3activar(rs.getBoolean(3));
                marca.setC4orden(rs.getInt(4));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + marca.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + marca.toString(), titulo);
        }
    }
     public void cargar_producto_marca_por_id(Connection conn, producto_marca marca, int  idproducto_marca) {
        String titulo = "cargar_producto_marca_por_id";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idproducto_marca, titulo);
            if (rs.next()) {
                marca.setC1idproducto_marca(rs.getInt(1));
                marca.setC2nombre(rs.getString(2));
                marca.setC3activar(rs.getBoolean(3));
                marca.setC4orden(rs.getInt(4));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + marca.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + marca.toString(), titulo);
        }
    }

    public void actualizar_tabla_producto_marca(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
//		ancho_tabla_producto_marca(tbltabla);
    }
//	public void ancho_tabla_producto_marca(JTable tbltabla){
//		int Ancho[]={25,25,25,25};
//		evejt.setAnchoColumnaJtable(tbltabla, Ancho);
//	}

    public void actualizar_tabla_producto_marca(Connection conn, JTable tbltabla, String fechadesde, String fechahasta) {
        String sql_select = "select ca.idproducto_marca as idc,ca.nombre,ca.activar,"
                + " case when "
                + " ((select sum(iv.cantidad) as cant \n"
                + "from item_venta iv,venta v,cliente cl,producto p \n"
                + "where  iv.fk_idventa=v.idventa and v.fk_idcliente=cl.idcliente\n"
                + "and (v.estado='TERMINADO' or v.estado='EMITIDO') \n"
                + "and date(v.fecha_emision) >= '" + fechadesde + "' and date(v.fecha_emision) <= '" + fechahasta + "' \n"
                + "and iv.fk_idproducto=p.idproducto\n"
                + "and p.fk_idproducto_marca=ca.idproducto_marca)>0) "
                + "then "
                + "TRIM(to_char((select sum(iv.cantidad) as cant \n"
                + "from item_venta iv,venta v,cliente cl,producto p \n"
                + "where  iv.fk_idventa=v.idventa and v.fk_idcliente=cl.idcliente\n"
                + "and (v.estado='TERMINADO' or v.estado='EMITIDO') \n"
                + "and date(v.fecha_emision) >= '" + fechadesde + "' and date(v.fecha_emision) <= '" + fechahasta + "' \n"
                + "and iv.fk_idproducto=p.idproducto\n"
                + "and p.fk_idproducto_marca=ca.idproducto_marca),'999999999')) "
                + "else TRIM(to_char(0,'999999999')) end as cant_ven,    "
                + "TRIM(to_char((select sum(iv.cantidad*iv.precio_venta) as cant \n"
                + "from item_venta iv,venta v,cliente cl,producto p \n"
                + "where  iv.fk_idventa=v.idventa and v.fk_idcliente=cl.idcliente\n"
                + "and (v.estado='TERMINADO' or v.estado='EMITIDO') \n"
                + "and date(v.fecha_emision) >= '" + fechadesde + "' and date(v.fecha_emision) <= '" + fechahasta + "' \n"
                + "and iv.fk_idproducto=p.idproducto\n"
                + "and p.fk_idproducto_marca=ca.idproducto_marca),'999G999G999')) as total_ven,"
                + "ca.orden as ord   "
                + "from producto_marca ca "
                + "order by ca.orden asc ";
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_producto_marca(tbltabla);
    }

    public void ancho_tabla_producto_marca(JTable tbltabla) {
        int Ancho[] = {8, 42, 10, 15, 20, 5};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
}
