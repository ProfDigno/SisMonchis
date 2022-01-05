package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.producto;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import FORMULARIO.ENTIDAD.venta_alquiler;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;
import javax.swing.JTextField;

public class DAO_producto {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "PRODUCTO GUARDADO CORRECTAMENTE";
    private String mensaje_update = "PRODUCTO MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO producto(idproducto,cod_barra,nombre,precio_venta_minorista,precio_venta_mayorista,cantidad_mayorista,precio_compra,stock,stock_min,activar,venta_mayorista,promocion,ult_venta,ult_compra,fk_idproducto_unidad,fk_idproducto_categoria,fk_idproducto_marca,alquilado) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
    private String sql_update = "UPDATE producto SET cod_barra=?,nombre=?,precio_venta_minorista=?,precio_venta_mayorista=?,"
            + "cantidad_mayorista=?,precio_compra=?,stock=?,stock_min=?,"
            + "activar=?,venta_mayorista=?,promocion=?,ult_venta=?,ult_compra=?,"
            + "fk_idproducto_unidad=?,fk_idproducto_categoria=?,fk_idproducto_marca=?,alquilado=? "
            + "WHERE idproducto=?;";
    private String sql_select = "SELECT p.idproducto as idp,p.cod_barra,pc.nombre as categoria,\n"
            + "(pm.nombre||'-'||pu.nombre||'-'||p.nombre) as marca_unid_nombre,\n"
            + "TRIM(to_char(p.precio_venta_minorista,'999G999G999')) as pventa,p.stock,\n"
            + "TRIM(to_char(p.precio_compra,'999G999G999')) as pcompra "
            + "FROM producto p,producto_unidad pu,producto_categoria pc, producto_marca pm \n"
            + "where p.fk_idproducto_unidad=pu.idproducto_unidad\n"
            + "and p.fk_idproducto_categoria=pc.idproducto_categoria\n"
            + "and p.fk_idproducto_marca=pm.idproducto_marca\n"
            + "order by 1 desc;";
    private String sql_cargar = "SELECT p.idproducto,p.cod_barra,p.nombre,p.precio_venta_minorista,p.precio_venta_mayorista,p.cantidad_mayorista,p.precio_compra,\n"
            + "p.stock,p.stock_min,p.activar,p.venta_mayorista,p.promocion,\n"
            + "p.ult_venta,p.ult_compra,p.fk_idproducto_unidad,p.fk_idproducto_categoria,p.fk_idproducto_marca,\n"
            + "pu.nombre as unidad,pc.nombre as categoria,pm.nombre as marca,p.alquilado \n"
            + "FROM producto p,producto_unidad pu,producto_categoria pc, producto_marca pm \n"
            + "where p.fk_idproducto_unidad=pu.idproducto_unidad\n"
            + "and p.fk_idproducto_categoria=pc.idproducto_categoria\n"
            + "and p.fk_idproducto_marca=pm.idproducto_marca\n"
            + "and p.idproducto=";
    private String sql_update_stock_descontar = "UPDATE public.producto\n"
            + "   SET   stock=(stock-?) "
            + " WHERE idproducto=?;";
    private String sql_update_stock_aumentar = "UPDATE public.producto\n"
            + "   SET   stock=(stock+?) "
            + " WHERE idproducto=?;";
    private String sql_update_pcompra = "UPDATE public.producto\n"
            + "   SET   precio_compra=? "
            + " WHERE idproducto=?;";
    private String sql_cargar_codbarra = "SELECT p.idproducto,p.cod_barra,p.nombre,p.precio_venta_minorista,p.precio_venta_mayorista,p.cantidad_mayorista,p.precio_compra,\n"
            + "p.stock,p.stock_min,p.activar,p.venta_mayorista,p.promocion,\n"
            + "p.ult_venta,p.ult_compra,p.fk_idproducto_unidad,p.fk_idproducto_categoria,p.fk_idproducto_marca,\n"
            + "pu.nombre as unidad,pc.nombre as categoria,pm.nombre as marca,p.alquilado \n"
            + "FROM producto p,producto_unidad pu,producto_categoria pc, producto_marca pm \n"
            + "where p.fk_idproducto_unidad=pu.idproducto_unidad\n"
            + "and p.fk_idproducto_categoria=pc.idproducto_categoria\n"
            + "and p.fk_idproducto_marca=pm.idproducto_marca\n"
            + "and p.cod_barra=";
    private String sql_stock_minimo = "SELECT p.idproducto as idp,\n"
            + "(pm.nombre||'-'||pu.nombre||'-'||p.nombre) as marca_unid_nombre,\n"
            + "p.stock,p.stock_min as mini,(select sum(iv.cantidad) as v_7dia\n"
            + "from item_venta iv,venta v\n"
            + "where iv.fk_idproducto!=0\n"
            + "and iv.fk_idventa=v.idventa\n"
            + "and v.estado='EMITIDO'\n"
            + "and date(v.fecha_emision)>=date(date('now()') - integer '7')\n"
            + "and date(v.fecha_emision)<=date('now()')\n"
            + "and iv.fk_idproducto=p.idproducto) as v_7dia\n"
            + "FROM producto p,producto_unidad pu,producto_categoria pc, producto_marca pm \n"
            + "where p.fk_idproducto_unidad=pu.idproducto_unidad\n"
            + "and p.fk_idproducto_categoria=pc.idproducto_categoria\n"
            + "and p.fk_idproducto_marca=pm.idproducto_marca\n"
            + "and p.stock<p.stock_min\n"
            + "and p.stock_min>0\n"
            + "and p.activar=true\n"
            + "order by 4 desc";
    private String sql_stock_alquilado = "update producto set stock=(stock-(item_venta_alquiler.cantidad_total)) \n"
            + "from item_venta_alquiler \n"
            + "where producto.idproducto=item_venta_alquiler.fk_idproducto\n"
            + "and item_venta_alquiler.fk_idventa_alquiler=";
    private String sql_stock_devolucion = "update producto set stock=(stock+(item_venta_alquiler.cantidad_total)) \n"
            + "from item_venta_alquiler \n"
            + "where producto.idproducto=item_venta_alquiler.fk_idproducto\n"
            + "and item_venta_alquiler.fk_idventa_alquiler=";
    public void insertar_producto(Connection conn, producto pro) {
        pro.setC1idproducto(eveconn.getInt_ultimoID_mas_uno(conn, pro.getTb_producto(), pro.getId_idproducto()));
        String titulo = "insertar_producto";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, pro.getC1idproducto());
            pst.setString(2, pro.getC2cod_barra());
            pst.setString(3, pro.getC3nombre());
            pst.setDouble(4, pro.getC4precio_venta_minorista());
            pst.setDouble(5, pro.getC5precio_venta_mayorista());
            pst.setDouble(6, pro.getC6cantidad_mayorista());
            pst.setDouble(7, pro.getC7precio_compra());
            pst.setDouble(8, pro.getC8stock());
            pst.setDouble(9, pro.getC9stock_min());
            pst.setBoolean(10, pro.getC10activar());
            pst.setBoolean(11, pro.getC11venta_mayorista());
            pst.setBoolean(12, pro.getC12promocion());
            pst.setDate(13, evefec.getDateSQL_sistema());
            pst.setDate(14, evefec.getDateSQL_sistema());
            pst.setInt(15, pro.getC15fk_idproducto_unidad());
            pst.setInt(16, pro.getC16fk_idproducto_categoria());
            pst.setInt(17, pro.getC17fk_idproducto_marca());
            pst.setBoolean(18, pro.getC18alquilado());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + pro.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + pro.toString(), titulo);
        }
    }

    public void update_producto(Connection conn, producto pro) {
        String titulo = "update_producto";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setString(1, pro.getC2cod_barra());
            pst.setString(2, pro.getC3nombre());
            pst.setDouble(3, pro.getC4precio_venta_minorista());
            pst.setDouble(4, pro.getC5precio_venta_mayorista());
            pst.setDouble(5, pro.getC6cantidad_mayorista());
            pst.setDouble(6, pro.getC7precio_compra());
            pst.setDouble(7, pro.getC8stock());
            pst.setDouble(8, pro.getC9stock_min());
            pst.setBoolean(9, pro.getC10activar());
            pst.setBoolean(10, pro.getC11venta_mayorista());
            pst.setBoolean(11, pro.getC12promocion());
            pst.setDate(12, evefec.getDateSQL_sistema());
            pst.setDate(13, evefec.getDateSQL_sistema());
            pst.setInt(14, pro.getC15fk_idproducto_unidad());
            pst.setInt(15, pro.getC16fk_idproducto_categoria());
            pst.setInt(16, pro.getC17fk_idproducto_marca());
            pst.setBoolean(17, pro.getC18alquilado());
            pst.setInt(18, pro.getC1idproducto());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + pro.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + pro.toString(), titulo);
        }
    }

    public void cargar_producto_por_idproducto(Connection conn, producto pro, int id) {
        String titulo = "Cargar_producto";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + id, titulo);
            if (rs.next()) {
                pro.setC1idproducto(rs.getInt(1));
                pro.setC2cod_barra(rs.getString(2));
                pro.setC3nombre(rs.getString(3));
                pro.setC4precio_venta_minorista(rs.getDouble(4));
                pro.setC5precio_venta_mayorista(rs.getDouble(5));
                pro.setC6cantidad_mayorista(rs.getDouble(6));
                pro.setC7precio_compra(rs.getDouble(7));
                pro.setC8stock(rs.getDouble(8));
                pro.setC9stock_min(rs.getDouble(9));
                pro.setC10activar(rs.getBoolean(10));
                pro.setC11venta_mayorista(rs.getBoolean(11));
                pro.setC12promocion(rs.getBoolean(12));
                pro.setC13ult_venta(rs.getString(13));
                pro.setC14ult_compra(rs.getString(14));
                pro.setC15fk_idproducto_unidad(rs.getInt(15));
                pro.setC16fk_idproducto_categoria(rs.getInt(16));
                pro.setC17fk_idproducto_marca(rs.getInt(17));
                pro.setC18_unidad(rs.getString(18));
                pro.setC19_categoria(rs.getString(19));
                pro.setC20_marca(rs.getString(20));
                pro.setC18alquilado(rs.getBoolean(21));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + pro.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + pro.toString(), titulo);
        }
    }

    public boolean getBoolean_cargar_producto_por_codbarra(Connection conn, producto pro, String codbarra) {
        boolean encontado = false;
        String titulo = "getBoolean_cargar_producto_por_codbarra";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar_codbarra + "'" + codbarra + "'", titulo);
            if (rs.next()) {
                pro.setC1idproducto(rs.getInt(1));
                pro.setC2cod_barra(rs.getString(2));
                pro.setC3nombre(rs.getString(3));
                pro.setC4precio_venta_minorista(rs.getDouble(4));
                pro.setC5precio_venta_mayorista(rs.getDouble(5));
                pro.setC6cantidad_mayorista(rs.getDouble(6));
                pro.setC7precio_compra(rs.getDouble(7));
                pro.setC8stock(rs.getDouble(8));
                pro.setC9stock_min(rs.getDouble(9));
                pro.setC10activar(rs.getBoolean(10));
                pro.setC11venta_mayorista(rs.getBoolean(11));
                pro.setC12promocion(rs.getBoolean(12));
                pro.setC13ult_venta(rs.getString(13));
                pro.setC14ult_compra(rs.getString(14));
                pro.setC15fk_idproducto_unidad(rs.getInt(15));
                pro.setC16fk_idproducto_categoria(rs.getInt(16));
                pro.setC17fk_idproducto_marca(rs.getInt(17));
                pro.setC18_unidad(rs.getString(18));
                pro.setC19_categoria(rs.getString(19));
                pro.setC20_marca(rs.getString(20));
                pro.setC18alquilado(rs.getBoolean(21));
                encontado = true;
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + pro.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + pro.toString(), titulo);
        }
        return encontado;
    }

    public void actualizar_tabla_producto(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_producto(tbltabla);
    }

    public void ancho_tabla_producto(JTable tbltabla) {
        int Ancho[] = {8, 18, 13, 35, 10, 7, 10};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }

    public void buscar_tabla_producto(Connection conn, JTable tbltabla, JTextField txtbuscar, int tipo) {
        String filtro = "";
        if (txtbuscar.getText().trim().length() > 0) {
            String buscar = txtbuscar.getText();
            if (tipo == 1) {
                filtro = " and pc.nombre ilike'%" + buscar + "%' ";
            }
            if (tipo == 2) {
                filtro = " and pu.nombre ilike'%" + buscar + "%' ";
            }
            if (tipo == 3) {
                filtro = " and p.nombre ilike'%" + buscar + "%' ";
            }
            if (tipo == 4) {
                filtro = " and pm.nombre ilike'%" + buscar + "%' ";
            }
        }
        String sql = "SELECT p.idproducto,p.cod_barra,pc.nombre as categoria,\n"
                + "(pm.nombre||'-'||pu.nombre||'-'||p.nombre) as marca_unid_nombre,\n"
                + "TRIM(to_char(p.precio_venta_minorista,'999G999G999')) as pventa,p.stock\n"
                + "FROM producto p,producto_unidad pu,producto_categoria pc, producto_marca pm \n"
                + "where p.fk_idproducto_unidad=pu.idproducto_unidad\n"
                + "and p.fk_idproducto_categoria=pc.idproducto_categoria\n"
                + "and p.fk_idproducto_marca=pm.idproducto_marca\n" + filtro
                + "order by 1 desc;";
        eveconn.Select_cargar_jtable(conn, sql, tbltabla);
        ancho_tabla_producto(tbltabla);
    }

    public void update_producto_stock_descontar(Connection conn, producto prod) {
        String titulo = "update_producto_stock_descontar";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update_stock_descontar);
            pst.setDouble(1, prod.getC21_aux_cantidad());
            pst.setInt(2, prod.getC1idproducto());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update_stock_descontar + "\n" + prod.toString(), titulo);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update_stock_descontar + "\n" + prod.toString(), titulo);
        }
    }

    public void update_producto_stock_aumentar(Connection conn, producto prod) {
        String titulo = "update_producto_stock_aumentar";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update_stock_aumentar);
            pst.setDouble(1, prod.getC21_aux_cantidad());
            pst.setInt(2, prod.getC1idproducto());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update_stock_aumentar + "\n" + prod.toString(), titulo);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update_stock_aumentar + "\n" + prod.toString(), titulo);
        }
    }

    public void update_producto_precio_compra(Connection conn, producto prod) {
        String titulo = "update_producto_stock_aumentar";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update_pcompra);
            pst.setDouble(1, prod.getC7precio_compra());
            pst.setInt(2, prod.getC1idproducto());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update_pcompra + "\n" + prod.toString(), titulo);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update_pcompra + "\n" + prod.toString(), titulo);
        }
    }

    public void actualizar_tabla_producto_en_grupo(Connection conn, JTable tblpro_producto, String fechadesde, String fechahasta, int idproducto_categoria) {
        String sql = "select p.idproducto as id,(c.nombre||'-'||u.nombre||'-'||p.nombre) as nombre,"
                + "TRIM(to_char((select avg(iv.precio_venta)  \n"
                + "from item_venta iv,venta v,cliente cl,producto p2 \n"
                + "where  iv.fk_idventa=v.idventa and v.fk_idcliente=cl.idcliente\n"
                + "and (v.estado='TERMINADO' or v.estado='EMITIDO') \n"
                + "and date(v.fecha_emision) >= '" + fechadesde + "' and date(v.fecha_emision) <= '" + fechahasta + "' \n"
                + "and iv.fk_idproducto=p2.idproducto              \n"
                + "and p2.idproducto=p.idproducto),'999G999G999')) as pre_ven, "
                + "case when "
                + "((select sum(iv.cantidad)  \n"
                + "from item_venta iv,venta v,cliente cl,producto p2 \n"
                + "where  iv.fk_idventa=v.idventa and v.fk_idcliente=cl.idcliente\n"
                + "and (v.estado='TERMINADO' or v.estado='EMITIDO') \n"
                + "and date(v.fecha_emision) >= '" + fechadesde + "' and date(v.fecha_emision) <= '" + fechahasta + "' \n"
                + "and iv.fk_idproducto=p2.idproducto              \n"
                + "and p2.idproducto=p.idproducto)>0) "
                + "then "
                + "TRIM(to_char((select sum(iv.cantidad)  \n"
                + "from item_venta iv,venta v,cliente cl,producto p2 \n"
                + "where  iv.fk_idventa=v.idventa and v.fk_idcliente=cl.idcliente\n"
                + "and (v.estado='TERMINADO' or v.estado='EMITIDO') \n"
                + "and date(v.fecha_emision) >= '" + fechadesde + "' and date(v.fecha_emision) <= '" + fechahasta + "' \n"
                + "and iv.fk_idproducto=p2.idproducto              \n"
                + "and p2.idproducto=p.idproducto),'999999999')) "
                + "else to_char(0,'9') end as cant, "
                + "TRIM(to_char((select sum(iv.cantidad*iv.precio_venta)  \n"
                + "from item_venta iv,venta v,cliente cl,producto p2 \n"
                + "where  iv.fk_idventa=v.idventa and v.fk_idcliente=cl.idcliente\n"
                + "and (v.estado='TERMINADO' or v.estado='EMITIDO') \n"
                + "and date(v.fecha_emision) >= '" + fechadesde + "' and date(v.fecha_emision) <= '" + fechahasta + "' \n"
                + "and iv.fk_idproducto=p2.idproducto              \n"
                + "and p2.idproducto=p.idproducto),'999G999G999')) as total "
                + "from producto p,producto_categoria c,producto_unidad u "
                + "where c.idproducto_categoria=" + idproducto_categoria + " "
                + "and c.idproducto_categoria=p.fk_idproducto_categoria "
                + "and u.idproducto_unidad=p.fk_idproducto_unidad "
                + "order by 4 desc ";
        eveconn.Select_cargar_jtable(conn, sql, tblpro_producto);
        ancho_tabla_producto_en_categoria(tblpro_producto);
    }

    public void ancho_tabla_producto_en_categoria(JTable tbltabla) {
        int Ancho[] = {10, 50, 15, 10, 15};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }

    public void imprimir_rep_inventario_venta(Connection conn, String filtro) {
        String sql = "SELECT p.idproducto as idpro,p.cod_barra as codbarra,pc.nombre as categoria,pm.nombre as marca,\n"
                + "(pu.nombre||'-'||p.nombre) as unid_nombre,\n"
                + "p.precio_venta_minorista as pvmino,\n"
                + "p.precio_venta_mayorista as pvmayo,p.stock as stock,\n"
                + "(p.precio_venta_minorista*p.stock) as total_mino,\n"
                + "(p.precio_venta_mayorista*p.stock) as total_mayo\n"
                + "FROM producto p,producto_unidad pu,producto_categoria pc, producto_marca pm\n"
                + "where p.fk_idproducto_unidad=pu.idproducto_unidad\n"
                + "and p.fk_idproducto_categoria=pc.idproducto_categoria\n"
                + "and p.fk_idproducto_marca=pm.idproducto_marca\n"
                + "and p.idproducto!=0  \n" + filtro
                + " order by pc.nombre,pm.nombre desc;";
        String titulonota = "INVENTARIO VENTA";
        String direccion = "src/REPORTE/PRODUCTO/repInventarioVenta.jrxml";
        rep.imprimirjasper(conn, sql, titulonota, direccion);
    }

    public void imprimir_rep_inventario_compra(Connection conn, String filtro) {
        String sql = "SELECT p.idproducto as idpro,p.cod_barra as codbarra,pc.nombre as categoria,pm.nombre as marca,\n"
                + "(pu.nombre||'-'||p.nombre) as unid_nombre,\n"
                + "p.precio_compra as pcompra,p.stock as stock,\n"
                + "(p.precio_compra*p.stock) as total_compra\n"
                + "FROM producto p,producto_unidad pu,producto_categoria pc, producto_marca pm\n"
                + "where p.fk_idproducto_unidad=pu.idproducto_unidad\n"
                + "and p.fk_idproducto_categoria=pc.idproducto_categoria\n"
                + "and p.fk_idproducto_marca=pm.idproducto_marca\n"
                + "and p.idproducto!=0 \n" + filtro
                + " order by pc.nombre,pm.nombre desc;";
        String titulonota = "INVENTARIO COMPRA";
        String direccion = "src/REPORTE/PRODUCTO/repInventarioValorCompra.jrxml";
        rep.imprimirjasper(conn, sql, titulonota, direccion);
    }

    public void imprimir_rep_ganacia(Connection conn, String filtro, String fecha) {
        String sql = "SELECT p.idproducto as idpro,p.cod_barra as codbarra,pc.nombre as categoria,pm.nombre as marca,\n"
                + "(pu.nombre||'-'||p.nombre) as unid_nombre,sum(iv.cantidad) as cantidad,\n"
                + "sum(iv.precio_venta*iv.cantidad) as total_venta,\n"
                + "sum(iv.precio_compra*iv.cantidad) as total_compra,\n"
                + "(sum(iv.precio_venta*iv.cantidad)-sum(iv.precio_compra*iv.cantidad)) as ganancia,('" + fecha + "') as fecha\n"
                + "FROM producto p,producto_unidad pu,producto_categoria pc, producto_marca pm,item_venta iv,venta v \n"
                + "where p.fk_idproducto_unidad=pu.idproducto_unidad\n"
                + "and iv.fk_idventa=v.idventa\n"
                + "and iv.fk_idproducto=p.idproducto\n"
                + "and p.fk_idproducto_categoria=pc.idproducto_categoria\n"
                + "and p.fk_idproducto_marca=pm.idproducto_marca\n"
                + "and p.idproducto!=0 \n"
                + "and v.estado='EMITIDO'\n" + filtro
                + "group by 1,2,3,4,5,10\n"
                + " order by pc.nombre,pm.nombre desc;";
        String titulonota = "GANANCIA COMPRA VENTA";
        String direccion = "src/REPORTE/PRODUCTO/repGananciaCompraVenta.jrxml";
        rep.imprimirjasper(conn, sql, titulonota, direccion);
    }

    public double getDouble_suma_inventario_valor_precio(Connection conn, String precio, String filtro) {
        String titulo = "getDouble_suma_inventario_valor_precio";
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
    public void update_producto_stock_Alquilado(Connection conn,venta_alquiler vealq) {
        String titulo = "update_producto_stock_Alquilado";
        PreparedStatement pst = null;
        String sql=sql_stock_alquilado+vealq.getC1idventa_alquiler();
        try {
            pst = conn.prepareStatement(sql);
            pst.executeUpdate();
            pst.close();
            evemen.Imprimir_serial_sql(sql , titulo);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql , titulo);
        }
    }
    public void update_producto_stock_Devolucion(Connection conn,venta_alquiler vealq) {
        String titulo = "update_producto_stock_Devolucion";
        PreparedStatement pst = null;
        String sql=sql_stock_devolucion+vealq.getC1idventa_alquiler();
        try {
            pst = conn.prepareStatement(sql);
            pst.executeUpdate();
            pst.close();
            evemen.Imprimir_serial_sql(sql , titulo);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql , titulo);
        }
    }
    public void actualizar_tabla_producto_stock_minimo(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_stock_minimo, tbltabla);
        ancho_tabla_producto_stock_minimo(tbltabla);
    }

    public void ancho_tabla_producto_stock_minimo(JTable tbltabla) {
        int Ancho[] = {10, 60, 10, 10, 10};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
}
