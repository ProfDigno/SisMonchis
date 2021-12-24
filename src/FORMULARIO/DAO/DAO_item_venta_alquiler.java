package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.item_venta_alquiler;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import FORMULARIO.ENTIDAD.producto;
import FORMULARIO.ENTIDAD.venta_alquiler;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_item_venta_alquiler {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    DAO_producto pdao = new DAO_producto();
    private String mensaje_insert = "ITEM_VENTA_ALQUILER GUARDADO CORRECTAMENTE";
    private String mensaje_update = "ITEM_VENTA_ALQUILER MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO item_venta_alquiler(iditem_venta_alquiler,descripcion,precio_venta,precio_compra,cantidad_total,cantidad_pagado,fk_idventa_alquiler,fk_idproducto) VALUES (?,?,?,?,?,?,?,?);";
    private String sql_update = "UPDATE item_venta_alquiler SET descripcion=?,precio_venta=?,precio_compra=?,cantidad_total=?,cantidad_pagado=?,fk_idventa_alquiler=?,fk_idproducto=? WHERE iditem_venta_alquiler=?;";
    private String sql_select = "SELECT iditem_venta_alquiler,descripcion,precio_venta,precio_compra,cantidad_total,cantidad_pagado,fk_idventa_alquiler,fk_idproducto FROM item_venta_alquiler order by 1 desc;";
    private String sql_cargar = "SELECT iditem_venta_alquiler,descripcion,precio_venta,precio_compra,cantidad_total,cantidad_pagado,fk_idventa_alquiler,fk_idproducto FROM item_venta_alquiler WHERE iditem_venta_alquiler=";

    public void insertar_item_venta_alquiler(Connection conn, item_venta_alquiler ivealq) {
        ivealq.setC1iditem_venta_alquiler(eveconn.getInt_ultimoID_mas_uno(conn, ivealq.getTb_item_venta_alquiler(), ivealq.getId_iditem_venta_alquiler()));
        String titulo = "insertar_item_venta_alquiler";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, ivealq.getC1iditem_venta_alquiler());
            pst.setString(2, ivealq.getC2descripcion());
            pst.setDouble(3, ivealq.getC3precio_venta());
            pst.setDouble(4, ivealq.getC4precio_compra());
            pst.setDouble(5, ivealq.getC5cantidad_total());
            pst.setDouble(6, ivealq.getC6cantidad_pagado());
            pst.setInt(7, ivealq.getC7fk_idventa_alquiler());
            pst.setInt(8, ivealq.getC8fk_idproducto());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + ivealq.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, false);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + ivealq.toString(), titulo);
        }
    }

    public void update_item_venta_alquiler(Connection conn, item_venta_alquiler ivealq) {
        String titulo = "update_item_venta_alquiler";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setString(1, ivealq.getC2descripcion());
            pst.setDouble(2, ivealq.getC3precio_venta());
            pst.setDouble(3, ivealq.getC4precio_compra());
            pst.setDouble(4, ivealq.getC5cantidad_total());
            pst.setDouble(5, ivealq.getC6cantidad_pagado());
            pst.setInt(6, ivealq.getC7fk_idventa_alquiler());
            pst.setInt(7, ivealq.getC8fk_idproducto());
            pst.setInt(8, ivealq.getC1iditem_venta_alquiler());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + ivealq.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + ivealq.toString(), titulo);
        }
    }

    public void cargar_item_venta_alquiler(Connection conn, item_venta_alquiler ivealq, int id) {
        String titulo = "Cargar_item_venta_alquiler";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + id, titulo);
            if (rs.next()) {
                ivealq.setC1iditem_venta_alquiler(rs.getInt(1));
                ivealq.setC2descripcion(rs.getString(2));
                ivealq.setC3precio_venta(rs.getDouble(3));
                ivealq.setC4precio_compra(rs.getDouble(4));
                ivealq.setC5cantidad_total(rs.getDouble(5));
                ivealq.setC6cantidad_pagado(rs.getDouble(6));
                ivealq.setC7fk_idventa_alquiler(rs.getInt(7));
                ivealq.setC8fk_idproducto(rs.getInt(8));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + ivealq.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + ivealq.toString(), titulo);
        }
    }
    public void insertar_item_venta_alquiler_de_tabla(Connection conn, JTable tblitem_producto,  venta_alquiler ven) {
        for (int row = 0; row < tblitem_producto.getRowCount(); row++) {
            String idproducto = ((tblitem_producto.getModel().getValueAt(row, 0).toString()));
            String descripcion = ((tblitem_producto.getModel().getValueAt(row, 1).toString()));
            String precio_venta = ((tblitem_producto.getModel().getValueAt(row, 2).toString()));
            String cantidad_pagado = ((tblitem_producto.getModel().getValueAt(row, 3).toString()));
            String cantidad_reservado = ((tblitem_producto.getModel().getValueAt(row, 4).toString()));
            double Dcant_pagado =Double.parseDouble(cantidad_pagado);
            double Dcant_reservado =Double.parseDouble(cantidad_reservado);
            try {
                producto prod = new producto();
                item_venta_alquiler item = new item_venta_alquiler();
                pdao.cargar_producto_por_idproducto(conn, prod, Integer.parseInt(idproducto));
                item.setC2descripcion(descripcion);
                item.setC3precio_venta(Double.parseDouble(precio_venta));
                item.setC4precio_compra(prod.getC7precio_compra());
                item.setC5cantidad_total(Dcant_pagado+Dcant_reservado);
                item.setC6cantidad_pagado(Dcant_pagado);
                item.setC7fk_idventa_alquiler(ven.getC1idventa_alquiler());
                item.setC8fk_idproducto(Integer.parseInt(idproducto));
                insertar_item_venta_alquiler(conn, item);
//                prod.setC21_aux_cantidad(Integer.parseInt(cantidad));
//                prod.setC1idproducto(Integer.parseInt(idproducto));
//                pdao.update_producto_stock_descontar(conn, prod);
            } catch (Exception e) {
                evemen.mensaje_error(e, "insertar_item_venta_alquiler_de_tabla");
                break;
            }

        }
    }
    public void actualizar_tabla_item_venta_alquiler(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_item_venta_alquiler(tbltabla);
    }

    public void ancho_tabla_item_venta_alquiler(JTable tbltabla) {
        int Ancho[] = {12, 12, 12, 12, 12, 12, 12, 12};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
}
