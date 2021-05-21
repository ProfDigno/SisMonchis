package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.item_inventario;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import Evento.Jtable.EvenRender;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_item_inventario {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    EvenRender render = new EvenRender();
    private String mensaje_insert = "ITEM_INVENTARIO GUARDADO CORRECTAMENTE";
    private String mensaje_update = "ITEM_INVENTARIO MODIFICADO CORECTAMENTE";
    private String mensaje_delete = "ITEM_INVENTARIO ELIMINADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO item_inventario(iditem_inventario,stock_sistema,stock_contado,precio_venta,precio_compra,estado,fk_idinventario,fk_idproducto) VALUES (?,?,?,?,?,?,?,?);";
    private String sql_update = "UPDATE item_inventario SET stock_sistema=?,stock_contado=?,precio_venta=?,precio_compra=?,estado=?,fk_idinventario=?,fk_idproducto=? WHERE iditem_inventario=?;";
    private String sql_select = "SELECT iditem_inventario,stock_sistema,stock_contado,precio_venta,precio_compra,estado,fk_idinventario,fk_idproducto FROM item_inventario order by 1 desc;";
    private String sql_cargar = "SELECT iditem_inventario,stock_sistema,stock_contado,precio_venta,precio_compra,estado,fk_idinventario,fk_idproducto FROM item_inventario WHERE iditem_inventario=";
    private String sql_update_estado = "update inventario \n"
            + "set total_precio_venta=\n"
            + "(SELECT sum(ii.precio_venta*ii.stock_contado) as precioventa FROM item_inventario ii where ii.fk_idinventario=idinventario),\n"
            + "total_precio_compra=\n"
            + "(SELECT sum(ii.precio_compra*ii.stock_contado) as preciocompra FROM item_inventario ii where ii.fk_idinventario=idinventario),\n"
            + "estado=? \n"
            + "where idinventario=?;";
   private String sql_deletar = "delete from item_inventario  WHERE iditem_inventario=?;";
    public void insertar_item_inventario(Connection conn, item_inventario iinven) {
        iinven.setC1iditem_inventario(eveconn.getInt_ultimoID_mas_uno(conn, iinven.getTb_item_inventario(), iinven.getId_iditem_inventario()));
        String titulo = "insertar_item_inventario";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, iinven.getC1iditem_inventario());
            pst.setInt(2, iinven.getC2stock_sistema());
            pst.setInt(3, iinven.getC3stock_contado());
            pst.setDouble(4, iinven.getC4precio_venta());
            pst.setDouble(5, iinven.getC5precio_compra());
            pst.setString(6, iinven.getC6estado());
            pst.setInt(7, iinven.getC7fk_idinventario());
            pst.setInt(8, iinven.getC8fk_idproducto());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + iinven.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + iinven.toString(), titulo);
        }
    }

    public void update_item_inventario(Connection conn, item_inventario iinven) {
        String titulo = "update_item_inventario";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setInt(1, iinven.getC2stock_sistema());
            pst.setInt(2, iinven.getC3stock_contado());
            pst.setDouble(3, iinven.getC4precio_venta());
            pst.setDouble(4, iinven.getC5precio_compra());
            pst.setString(5, iinven.getC6estado());
            pst.setInt(6, iinven.getC7fk_idinventario());
            pst.setInt(7, iinven.getC8fk_idproducto());
            pst.setInt(8, iinven.getC1iditem_inventario());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + iinven.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + iinven.toString(), titulo);
        }
    }

    public void update_item_inventario_estado1(Connection conn, item_inventario iinven) {
        String titulo = "update_item_inventario_estado";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update_estado);
            pst.setString(1, iinven.getC6estado());
            pst.setInt(2, iinven.getC1iditem_inventario());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update_estado + "\n" + iinven.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update_estado + "\n" + iinven.toString(), titulo);
        }
    }
    public void deletar_item_inventario(Connection conn, item_inventario iinven) {
        String titulo = "deletar_item_inventario";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_deletar);
            pst.setInt(1, iinven.getC1iditem_inventario());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_deletar + "\n" + iinven.toString(), titulo);
            evemen.modificado_correcto(mensaje_delete, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_deletar + "\n" + iinven.toString(), titulo);
        }
    }
    public void cargar_item_inventario(Connection conn, item_inventario iinven, JTable tabla) {
        String titulo = "Cargar_item_inventario";
        int id = evejt.getInt_select_id(tabla);
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + id, titulo);
            if (rs.next()) {
                iinven.setC1iditem_inventario(rs.getInt(1));
                iinven.setC2stock_sistema(rs.getInt(2));
                iinven.setC3stock_contado(rs.getInt(3));
                iinven.setC4precio_venta(rs.getDouble(4));
                iinven.setC5precio_compra(rs.getDouble(5));
                iinven.setC6estado(rs.getString(6));
                iinven.setC7fk_idinventario(rs.getInt(7));
                iinven.setC8fk_idproducto(rs.getInt(8));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + iinven.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + iinven.toString(), titulo);
        }
    }

    public void actualizar_tabla_item_inventario(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_item_inventario(tbltabla);
    }

    public void ancho_tabla_item_inventario(JTable tbltabla) {
        int Ancho[] = {12, 12, 12, 12, 12, 12, 12, 12};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }

    public void actualizar_tabla_item_inventario_diferencia(Connection conn, int idinventario, JTable tbltabla) {
        String sql_diferencia = "SELECT ii.iditem_inventario as idii,p.cod_barra,\n"
                + "(pm.nombre||'-'||pu.nombre||'-'||p.nombre) as marca_unid_nombre,\n"
                + "ii.stock_sistema as st_sis,ii.stock_contado as st_con,(ii.stock_contado-ii.stock_sistema) as st_dif,ii.estado,\n"
                + "case when (ii.stock_contado-ii.stock_sistema)>0 then 'P' "
                + "when (ii.stock_contado-ii.stock_sistema)<0 then 'N' else '0' end  as tipo "
                + "FROM producto p,producto_unidad pu,producto_categoria pc,\n"
                + " producto_marca pm,item_inventario ii,inventario i \n"
                + "where p.fk_idproducto_unidad=pu.idproducto_unidad\n"
                + "and p.fk_idproducto_categoria=pc.idproducto_categoria\n"
                + "and p.fk_idproducto_marca=pm.idproducto_marca\n"
                + "and i.idinventario=ii.fk_idinventario\n"
                + "and ii.fk_idproducto=p.idproducto\n"
                + "and (ii.estado='TEMP' or ii.estado='CARGA')\n"
                + "and i.idinventario=" + idinventario
                + " order by 1 desc;";
        eveconn.Select_cargar_jtable(conn, sql_diferencia, tbltabla);
        ancho_tabla_item_inventario_diferencia(tbltabla);
        render.rendertabla_tipo_item_inventario(tbltabla, 7);
    }

    public void ancho_tabla_item_inventario_diferencia(JTable tbltabla) {
        int Ancho[] = {5, 15, 35, 9, 9, 9, 14, 4};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }

    public boolean getBoolean_buscar_codbarra_de_item_inventario(Connection conn, int idinventario, String codbarra) {
        boolean escodbarra = false;
        String titulo = "getBoolean_buscar_codbarra_de_item_inventario";
        String sql = "SELECT p.cod_barra\n"
                + "FROM producto p,producto_unidad pu,producto_categoria pc,\n"
                + " producto_marca pm,item_inventario ii,inventario i \n"
                + "where p.fk_idproducto_unidad=pu.idproducto_unidad\n"
                + "and p.fk_idproducto_categoria=pc.idproducto_categoria\n"
                + "and p.fk_idproducto_marca=pm.idproducto_marca\n"
                + "and i.idinventario=ii.fk_idinventario\n"
                + "and ii.fk_idproducto=p.idproducto\n"
                + "and (ii.estado='TEMP' or ii.estado='CARGA')\n"
                + "and i.idinventario=" + idinventario
                + " and p.cod_barra='" + codbarra + "'";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            if (rs.next()) {
                escodbarra = true;
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
        }
        return escodbarra;
    }

    public double getDouble_suma_item_inventario_valor_precio(Connection conn, String precio, String filtro) {
        String titulo = "getDouble_suma_item_inventario_valor_precio";
        double total = 0;
        String sql = "SELECT sum(" + precio + "*p.stock) as total_mayo\n"
                + "FROM producto p,producto_unidad pu,producto_categoria pc, producto_marca pm\n"
                + "where p.fk_idproducto_unidad=pu.idproducto_unidad\n"
                + "and p.fk_idproducto_categoria=pc.idproducto_categoria\n"
                + "and p.fk_idproducto_marca=pm.idproducto_marca\n"
                + "and p.idproducto!=0 and p.stock>0 \n" + filtro;
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            if (rs.next()) {
                total = rs.getDouble(1);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
        }
        return total;
    }
}
