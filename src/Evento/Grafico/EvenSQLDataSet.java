/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Evento.Grafico;

//import ClaseUTIL.FunMensaje;
//import ClaseUTIL.SQLprepar;
import BASEDATO.EvenConexion;
import Evento.Mensaje.EvenMensajeJoptionpane;
import java.sql.Connection;
import java.sql.ResultSet;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author Digno
 */
public class EvenSQLDataSet {

    EvenConexion eveconn = new EvenConexion();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();

    public void createDataset_caja_ingreso_egreso(Connection conn, DefaultCategoryDataset dataset, String campo_fecha, String campo, String filtro_fecha) {
        String titulo = "createDataset_caja_ingreso_egreso";
        String sql = "select " + campo_fecha + " as fecha,\n"
                + "sum(monto_venta) as INGRESO_venta,\n"
                + "sum(monto_gasto+monto_vale+monto_compra) as EGRESO_gasto_compra_vale, \n"
                + "sum(monto_venta) as VENTA,\n"
                + "sum(monto_gasto) as GASTO,\n"
                + "sum(monto_vale) as VALE,\n"
                + "sum(monto_delivery) as DELIVERY,\n"
                + "sum(monto_compra) as COMPRA \n"
                + "from caja_detalle \n"
                + "where  " + filtro_fecha
                + " group by 1 order by 1 asc";
        String seriedt = campo;
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            while (rs.next()) {
                String nombre = (rs.getString(1));
                double valor = rs.getDouble(campo);
                dataset.addValue(valor, seriedt, nombre);
            }
        } catch (Exception e) {
            evemen.Imprimir_serial_sql_error(e, sql, titulo);
        }
    }

    public void createDataset_cantidad_cliente(Connection conn, DefaultCategoryDataset dataset, String campo_fecha, String campo, String filtro_fecha) {
        String titulo = "createDataset_cantidad_cliente";
        String sql = "select " + campo_fecha + " as fecha,\n"
                + "count(*) as CANTIDAD_CLIENTE\n"
                + "from cliente \n"
                + "where  " + filtro_fecha
                + " group by 1 order by 1 asc";
        String seriedt = campo;
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            while (rs.next()) {
                String nombre = (rs.getString(1));
                double valor = rs.getDouble(campo);
                dataset.addValue(valor, seriedt, nombre);
            }
        } catch (Exception e) {
            evemen.Imprimir_serial_sql_error(e, sql, titulo);
        }
    }

    public DefaultPieDataset createDataset_precio_insumo(Connection conn, int idproducto) {
        String titulo = "createDataset_producto";
        DefaultPieDataset dataset = new DefaultPieDataset();
        String sql = "select (ip.nombre) as insumo,\n"
                + "((((ip.precio+((ip.precio*ip.merma)/100))/iu.conversion_unidad)*iip.cantidad)) as precio_insumo \n"
                + "from insumo_item_producto iip,insumo_producto ip,insumo_unidad iu\n"
                + "where iip.fk_idinsumo_producto=ip.idinsumo_producto\n"
                + "and ip.fk_idinsumo_unidad=iu.idinsumo_unidad \n"
                + "and iip.fk_idproducto=" + idproducto;
        String seriedt = "INSUMOS";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            while (rs.next()) {
                String nombre = (rs.getString(1));
                double valor = rs.getDouble(2);
                dataset.setValue(nombre, valor);
            }
        } catch (Exception e) {
            evemen.Imprimir_serial_sql_error(e, sql, titulo);
        }
        return dataset;
    }

    public void createDataset_INSUMO_PRODUCTO(Connection conn, DefaultCategoryDataset dataset,
            String campo_fecha, String campo, String filtro_fecha, int idinsumo_producto) {
        String titulo = "createDataset_cantidad_cliente";
        String sql = "select " + campo_fecha + ",\n"
                + "sum(ii.cantidad*iv.cantidad) as TOTAL_CANTIDAD,\n"
                + "sum(ii.precio*iv.cantidad) as TOTAL_MONTO\n"
                + "from insumo_producto ip,insumo_item_producto iip,itemven_insumo ii,"
                + "item_venta iv,venta v,insumo_categoria ic,insumo_unidad iu\n"
                + "where ip.idinsumo_producto=iip.fk_idinsumo_producto\n"
                + "and ip.fk_idinsumo_unidad=iu.idinsumo_unidad\n"
                + "and ip.fk_idinsumo_categoria=ic.idinsumo_categoria\n"
                + "and iip.idinsumo_item_producto=ii.fk_idinsumo_item_producto\n"
                + "and ii.fk_iditem_venta=iv.iditem_venta\n"
                + "and iv.fk_idventa=v.idventa\n"
                + "and (v.estado='TERMINADO' or v.estado='EMITIDO')\n"
                + "and ip.idinsumo_producto=" + idinsumo_producto + " " + filtro_fecha
                + " group by 1 "
                + "ORDER BY 1 ASC";
        String seriedt = campo;
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            while (rs.next()) {
                String nombre = (rs.getString(1));
                double valor = rs.getDouble(campo);
                dataset.addValue(valor, seriedt, nombre);
            }
        } catch (Exception e) {
            evemen.Imprimir_serial_sql_error(e, sql, titulo);
        }
    }

    public void createDataset_producto_mas_comprado(Connection conn, DefaultCategoryDataset dataset, String filtro_fecha, String idcategoria) {
        String titulo = "createDataset_caja_ingreso_egreso";
        String sql = "select \n"
                + "(ip.nombre||'-'||iu.nom_compra) as producto_insumo,\n"
                + "sum(ici.cantidad*ici.precio)  as subtotal\n"
                + "from compra_insumo ci,item_compra_insumo ici,insumo_producto ip,\n"
                + "insumo_categoria ic,insumo_unidad iu\n"
                + "where  ci.idcompra_insumo=ici.fk_idcompra_insumo\n"
                + "and ici.fk_idinsumo_producto=ip.idinsumo_producto\n"
                + "and ip.fk_idinsumo_categoria=ic.idinsumo_categoria\n"
                + "and ci.estado='CONFIRMADO' \n"
                + "and ip.fk_idinsumo_unidad=iu.idinsumo_unidad\n " + filtro_fecha + idcategoria
                + " group by 1 \n"
                + " order by sum(ici.cantidad*ici.precio) desc";
        String seriedt = "COMPRA INSUMO";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            while (rs.next()) {
                String nombre = (rs.getString(1));
                double valor = rs.getDouble(2);
                dataset.addValue(valor, seriedt, nombre);
            }
        } catch (Exception e) {
            evemen.Imprimir_serial_sql_error(e, sql, titulo);
        }
    }

    public void createDataset_producto_mas_vendido(Connection conn, DefaultCategoryDataset dataset, String filtro_fecha, String idcategoria) {
        String titulo = "createDataset_producto_mas_vendido";
        String sql = "select \n"
                + "(ic.nombre||'-'||ip.nombre||'-'||iu.nom_compra) as producto_insumo,\n"
                + "(sum(ii.precio*iv.cantidad)) as TOTAL_MONTO\n"
                + "from insumo_producto ip,\n"
                + "insumo_item_producto iip,\n"
                + "itemven_insumo ii,item_venta iv,venta v,insumo_categoria ic,insumo_unidad iu\n"
                + "where ip.idinsumo_producto=iip.fk_idinsumo_producto\n"
                + "and ip.fk_idinsumo_unidad=iu.idinsumo_unidad\n"
                + "and ip.fk_idinsumo_categoria=ic.idinsumo_categoria\n"
                + "and iip.idinsumo_item_producto=ii.fk_idinsumo_item_producto\n"
                + "and ii.fk_iditem_venta=iv.iditem_venta\n"
                + "and iv.fk_idventa=v.idventa\n"
                + "and (v.estado='TERMINADO' or v.estado='EMITIDO') \n" + filtro_fecha + idcategoria
                + " group by 1 "
                + "ORDER BY 2 desc";
        String seriedt = "VENTA INSUMO";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            while (rs.next()) {
                String nombre = (rs.getString(1));
                double valor = rs.getDouble(2);
                dataset.addValue(valor, seriedt, nombre);
            }
        } catch (Exception e) {
            evemen.Imprimir_serial_sql_error(e, sql, titulo);
        }
    }

    public DefaultCategoryDataset getDataset_producto_mas_vendido(Connection conn, int cant_dias, int top) {
        DefaultCategoryDataset dataset_producto = new DefaultCategoryDataset();
        String titulo = "getDataset_producto_mas_vendido";
        String sql = "select iv.descripcion,sum(iv.cantidad) as cant,p.stock\n"
                + "from item_venta iv,venta v,producto p\n"
                + "where iv.fk_idproducto!=0\n"
                + "and iv.fk_idproducto=p.idproducto\n"
                + "and iv.fk_idventa=v.idventa\n"
                + "and v.estado='EMITIDO'\n"
                + "and date(v.fecha_emision)>=date(date('now()') - integer '" + cant_dias + "')\n"
                + "and date(v.fecha_emision)<=date('now()')\n"
                + "group by 1,3\n"
                + "order by 2 desc limit " + top + " ;";
        String vendidos = "VENDIDO";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            while (rs.next()) {
                String nombre = (rs.getString(1));
                double valor = rs.getDouble(2);
                dataset_producto.addValue(valor, vendidos, nombre);
            }
        } catch (Exception e) {
            evemen.Imprimir_serial_sql_error(e, sql, titulo);
        }
        return dataset_producto;
    }

    public DefaultCategoryDataset getDataset_venta_semanal(Connection conn) {
        DefaultCategoryDataset dataset_producto = new DefaultCategoryDataset();
        String titulo = "getDataset_producto_mas_vendido";
        String sql = "select ('Semana:'||date_part('week', fecha_emision)) as semana,date(fecha_emision) as fecha,\n"
                + "CASE  \n"
                + "WHEN date_part('dow', fecha_emision)=0 THEN 'DOMINGO' \n"
                + "WHEN date_part('dow', fecha_emision)=1 THEN 'LUNES' \n"
                + "WHEN date_part('dow', fecha_emision)=2 THEN 'MARTES' \n"
                + "WHEN date_part('dow', fecha_emision)=3 THEN 'MIERCOLES' \n"
                + "WHEN date_part('dow', fecha_emision)=4 THEN 'JUEVES' \n"
                + "WHEN date_part('dow', fecha_emision)=5 THEN 'VIERNES' \n"
                + "WHEN date_part('dow', fecha_emision)=6 THEN 'SABADO' \n"
                + "ELSE 'sin dia' END as dia,\n"
                + "sum(monto_venta) as total\n"
                + "from venta \n"
                + "where estado='EMITIDO'\n"
                + "and date_part('week', fecha_emision)>=(date_part('week', date('now()'))-4) \n"
                + "and date_part('week', fecha_emision)<=date_part('week', date('now()'))\n"
                + "and date_part('year', fecha_emision)=date_part('year', date('now()'))\n"
                + "group by 1,2,3\n"
                + "order by date(fecha_emision) asc ;";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            while (rs.next()) {
                String serie = (rs.getString(1));
                String dia = (rs.getString(3));
                double valor = rs.getDouble(4);
                dataset_producto.addValue(valor, dia, serie);
            }
        } catch (Exception e) {
            evemen.Imprimir_serial_sql_error(e, sql, titulo);
        }
        return dataset_producto;
    }
}
