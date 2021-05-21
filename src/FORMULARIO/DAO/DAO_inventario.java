package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.inventario;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import FORMULARIO.ENTIDAD.producto;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_inventario {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "INVENTARIO GUARDADO CORRECTAMENTE";
    private String mensaje_update = "INVENTARIO MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO inventario(idinventario,fecha_inicio,fecha_fin,descripcion,total_precio_venta,total_precio_compra,estado,fk_idusuario) VALUES (?,?,?,?,?,?,?,?);";
    private String sql_update = "UPDATE inventario SET fecha_inicio=?,fecha_fin=?,descripcion=?,total_precio_venta=?,total_precio_compra=?,estado=?,fk_idusuario=? WHERE idinventario=?;";
    private String sql_select = "SELECT idinventario as idin,to_char(fecha_inicio,'yyyy-MM-dd HH24:MI') as inicio,\n"
            + "to_char(fecha_fin,'yyyy-MM-dd HH24:MI') as final,descripcion,\n"
            + "TRIM(to_char(total_precio_venta,'999G999G999')) as total_venta,"
            + "TRIM(to_char(total_precio_compra,'999G999G999')) as total_compra,estado FROM inventario order by 1 desc;";
    private String sql_cargar = "SELECT idinventario,fecha_inicio,fecha_fin,descripcion,total_precio_venta,total_precio_compra,estado,fk_idusuario FROM inventario WHERE idinventario=";
    private String sql_update_estado = "update inventario \n"
            + "set total_precio_venta=\n"
            + "(SELECT sum(ii.precio_venta*ii.stock_contado) as precioventa FROM item_inventario ii where ii.fk_idinventario=idinventario),\n"
            + "total_precio_compra=\n"
            + "(SELECT sum(ii.precio_compra*ii.stock_contado) as preciocompra FROM item_inventario ii where ii.fk_idinventario=idinventario),\n"
            + "fecha_fin=?,estado=? \n"
            + "where idinventario=?;";
    private String sql_update_stock_inventario = "update producto set stock=ii.stock_contado \n"
            + "from item_inventario ii,inventario i\n"
            + "where idproducto=ii.fk_idproducto\n"
            + "and i.idinventario=ii.fk_idinventario\n"
            + "and i.idinventario=?;";
    private String sql_update_estado_item_inve = "update item_inventario set estado='CARGA' \n"
            + " where  fk_idinventario=?;";

    public void insertar_inventario(Connection conn, inventario inve) {
        inve.setC1idinventario(eveconn.getInt_ultimoID_mas_uno(conn, inve.getTb_inventario(), inve.getId_idinventario()));
        String titulo = "insertar_inventario";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, inve.getC1idinventario());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setTimestamp(3, evefec.getTimestamp_sistema());
            pst.setString(4, inve.getC4descripcion());
            pst.setDouble(5, inve.getC5total_precio_venta());
            pst.setDouble(6, inve.getC6total_precio_compra());
            pst.setString(7, inve.getC7estado());
            pst.setInt(8, inve.getC8fk_idusuario());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + inve.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + inve.toString(), titulo);
        }
    }

    public void update_inventario(Connection conn, inventario inve) {
        String titulo = "update_inventario";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, inve.getC4descripcion());
            pst.setDouble(4, inve.getC5total_precio_venta());
            pst.setDouble(5, inve.getC6total_precio_compra());
            pst.setString(6, inve.getC7estado());
            pst.setInt(7, inve.getC8fk_idusuario());
            pst.setInt(8, inve.getC1idinventario());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + inve.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + inve.toString(), titulo);
        }
    }

    public void update_inventario_estado(Connection conn, inventario inve) {
        String titulo = "update_inventario_estado";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update_estado);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, inve.getC7estado());
            pst.setInt(3, inve.getC1idinventario());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update_estado + "\n" + inve.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update_estado + "\n" + inve.toString(), titulo);
        }
    }

    public void cargar_inventario(Connection conn, inventario inve, int idinventario) {
        String titulo = "Cargar_inventario";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idinventario, titulo);
            if (rs.next()) {
                inve.setC1idinventario(rs.getInt(1));
                inve.setC2fecha_inicio(rs.getString(2));
                inve.setC3fecha_fin(rs.getString(3));
                inve.setC4descripcion(rs.getString(4));
                inve.setC5total_precio_venta(rs.getDouble(5));
                inve.setC6total_precio_compra(rs.getDouble(6));
                inve.setC7estado(rs.getString(7));
                inve.setC8fk_idusuario(rs.getInt(8));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + inve.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + inve.toString(), titulo);
        }
    }

    public void actualizar_tabla_inventario(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_inventario(tbltabla);
    }

    public void ancho_tabla_inventario(JTable tbltabla) {
        int Ancho[] = {10, 15, 15, 25, 10, 10, 15};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }

    public int getInt_idinventario_iniciado(Connection conn) {
        String titulo = "getInt_idinventario_iniciado";
        String sql = "select idinventario from inventario where estado='INICIADO';";
        int idinventario = -1;
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            while (rs.next()) {
                idinventario = rs.getInt("idinventario");
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
        }
        return idinventario;
    }

    public void update_producto_stock_inventario(Connection conn, inventario inve) {
        String titulo = "update_producto_stock_inventario";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update_stock_inventario);
            pst.setInt(1, inve.getC1idinventario());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update_stock_inventario + "\n" + inve.toString(), titulo);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update_stock_inventario + "\n" + inve.toString(), titulo);
        }
    }

    public void update_estado_item_inventario(Connection conn, inventario inve) {
        String titulo = "update_estado_item_inventario";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update_estado_item_inve);
            pst.setInt(1, inve.getC1idinventario());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update_estado_item_inve + "\n" + inve.toString(), titulo);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update_estado_item_inve + "\n" + inve.toString(), titulo);
        }
    }

    public void imprimir_inventario(Connection conn, int idinventario) {
        String sql = "SELECT ii.iditem_inventario as idii,p.cod_barra as codbarra,\n"
                + "(pm.nombre||'-'||pu.nombre||'-'||p.nombre) as marca_unid_nombre,\n"
                + "ii.stock_sistema as st_sis,ii.stock_contado as st_con,(ii.stock_contado-ii.stock_sistema) as st_dif,\n"
                + "case when (ii.stock_contado-ii.stock_sistema)>0 then 'P' \n"
                + "when (ii.stock_contado-ii.stock_sistema)<0 then 'N' else '0' end  as tipo,\n"
                + "i.idinventario as idin,to_char(i.fecha_inicio,'yyyy-MM-dd HH24:MI') as inicio,\n"
                + "to_char(i.fecha_fin,'yyyy-MM-dd HH24:MI') as final,i.descripcion, \n"
                + "i.total_precio_venta as total_venta,i.total_precio_compra as total_compra,i.estado as iestado\n"
                + "FROM producto p,producto_unidad pu,producto_categoria pc,\n"
                + " producto_marca pm,item_inventario ii,inventario i \n"
                + "where p.fk_idproducto_unidad=pu.idproducto_unidad\n"
                + "and p.fk_idproducto_categoria=pc.idproducto_categoria\n"
                + "and p.fk_idproducto_marca=pm.idproducto_marca\n"
                + "and i.idinventario=ii.fk_idinventario\n"
                + "and ii.fk_idproducto=p.idproducto\n"
                + "and (ii.estado='TEMP' or ii.estado='CARGA')\n"
                + "and i.idinventario="+idinventario
                + " order by 1 desc;";
        String titulonota = "INVENTARIO";
        String direccion = "src/REPORTE/PRODUCTO/repCrearInventario.jrxml";
        rep.imprimirjasper(conn, sql, titulonota, direccion);
    }
}
