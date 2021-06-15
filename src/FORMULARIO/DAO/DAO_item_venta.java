/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.DAO;

import BASEDATO.LOCAL.ConnPostgres;
import BASEDATO.EvenConexion;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Utilitario.EvenUtil;
import FORMULARIO.ENTIDAD.item_venta;
import FORMULARIO.ENTIDAD.producto;
import FORMULARIO.ENTIDAD.venta;
import FORMULARIO.ENTIDAD.zona_delivery;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JTable;

/**
 *
 * @author Digno
 */
public class DAO_item_venta {

    EvenConexion evconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenUtil eveut = new EvenUtil();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    DAO_producto pdao = new DAO_producto();
    private String mensaje_insert = " GUARDADO CORRECTAMENTE";
    private String sql_insert = "INSERT INTO public.item_venta(\n"
            + "            iditem_venta, descripcion, precio_venta, precio_compra, cantidad, \n"
            + "             fk_idventa, fk_idproducto)\n"
            + "    VALUES (?, ?, ?, ?, ?, ?, ?);";
    private String sql_select = "select descripcion,precio_venta as precio,cantidad as ca,(precio_venta*cantidad) as total "
            + "from item_venta where fk_idventa=";

    public void insertar_item_venta(Connection conn, item_venta item) {
        item.setC1iditem_venta(evconn.getInt_ultimoID_mas_uno(conn, item.getTabla(), item.getIdtabla()));
        String titulo = "insertar_item_venta";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, item.getC1iditem_venta());
            pst.setString(2, item.getC2descripcion());
            pst.setDouble(3, item.getC3precio_venta());
            pst.setDouble(4, item.getC4precio_compra());
            pst.setDouble(5, item.getC5cantidad());
            pst.setInt(6, item.getC6fk_idventa());
            pst.setInt(7, item.getC7fk_idproducto());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + item.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, false);
        } catch (SQLException e) {
            evemen.mensaje_error(e, sql_insert + "\n" + item.toString(), titulo);
        }
    }

    public void insertar_item_venta_de_tabla(Connection conn, JTable tblitem_producto, item_venta item, venta ven) {
        for (int row = 0; row < tblitem_producto.getRowCount(); row++) {
            String idproducto = ((tblitem_producto.getModel().getValueAt(row, 0).toString()));
            String descripcion = ((tblitem_producto.getModel().getValueAt(row, 1).toString()));
            String precio_venta = ((tblitem_producto.getModel().getValueAt(row, 2).toString()));
            String cantidad = ((tblitem_producto.getModel().getValueAt(row, 3).toString()));
            try {
                producto prod = new producto();
                pdao.cargar_producto_por_idproducto(conn, prod, Integer.parseInt(idproducto));
                item.setC2descripcion(eveut.getString_salto_de_linea(descripcion));
                item.setC3precio_venta(Double.parseDouble(precio_venta));
                item.setC4precio_compra(prod.getC7precio_compra());
                item.setC5cantidad(Double.parseDouble(cantidad));
                item.setC6fk_idventa(ven.getC1idventa_estatico());
                item.setC7fk_idproducto(Integer.parseInt(idproducto));
                insertar_item_venta(conn, item);
                prod.setC21_aux_cantidad(Integer.parseInt(cantidad));
                prod.setC1idproducto(Integer.parseInt(idproducto));
                pdao.update_producto_stock_descontar(conn, prod);
            } catch (Exception e) {
                evemen.mensaje_error(e, "insertar_item_venta_de_tabla");
                break;
            }

        }
    }

    public void tabla_item_venta_filtro(Connection conn, JTable tblitem_producto_filtro, item_venta item) {
        evconn.Select_cargar_jtable(conn, sql_select + item.getC6fk_idventa(), tblitem_producto_filtro);
        ancho_tabla_item_venta_filtro(tblitem_producto_filtro);
    }

    public void ancho_tabla_item_venta_filtro(JTable tblitem_producto_filtro) {
        int Ancho[] = {55, 15, 5, 15};
        evejt.setAnchoColumnaJtable(tblitem_producto_filtro, Ancho);
    }

    public void recargar_stock_producto(Connection conn, venta ven) {
        String titulo = "recargar_stock_producto";
        String sql = "select fk_idproducto,cantidad "
                + "from item_venta "
                + "where fk_idventa=" + ven.getC1idventa();
        try {
            ResultSet rs = evconn.getResulsetSQL(conn, sql, titulo);
            while (rs.next()) {
                int fk_idproducto = (rs.getInt("fk_idproducto"));
                int cantidad = (rs.getInt("cantidad"));
                producto prod = new producto();
                prod.setC21_aux_cantidad(cantidad);
                prod.setC1idproducto(fk_idproducto);
                pdao.update_producto_stock_aumentar(conn, prod);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql + "\n", titulo);
        }
    }
}
