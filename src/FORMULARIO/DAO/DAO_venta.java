/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.DAO;

import BASEDATO.LOCAL.ConnPostgres;
import BASEDATO.EvenConexion;
import CONFIGURACION.EvenDatosPc;
import Evento.Fecha.EvenFecha;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Jtable.EvenRender;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.ENTIDAD.cliente;
import FORMULARIO.ENTIDAD.venta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import javax.swing.JTable;

/**
 *
 * @author Digno
 */
public class DAO_venta {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenRender everende = new EvenRender();
    EvenFecha evefec = new EvenFecha();
    EvenDatosPc evepc = new EvenDatosPc();
    EvenJasperReport rep = new EvenJasperReport();
    venta ven = new venta();
    private String mensaje_insert = "VENTA GUARDADO CORRECTAMENTE";
    private String mensaje_update = "VENTA MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO public.venta(\n"
            + "idventa,fecha_emision,monto_venta_efectivo, \n"
            + "monto_delivery,redondeo,estado,observacion,forma_pago,delivery, fk_idcliente, \n"
            + "fk_idusuario, fk_identregador,entrega,monto_venta_tarjeta)\n"
            + "    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,? );";

    private String sql_estado = "UPDATE public.venta\n"
            + "   SET estado=?\n"
            + " WHERE idventa=?;";
    private String sql_forma_pago = "UPDATE public.venta\n"
            + "   SET forma_pago=?\n"
            + " WHERE idventa=?;";
    private String sql_entregador = "UPDATE public.venta\n"
            + "   SET fk_identregador=?\n"
            + " WHERE idventa=?;";
    private String sql_est_ser = "UPDATE public.venta\n"
            + "   SET estado=?\n"
            + " WHERE idventa=?;";
    private String orden = " order by v.idventa desc";
    private String sql_cargar = "SELECT  idventa,fecha_emision, monto_venta_efectivo, monto_delivery,redondeo, estado, \n"
            + "       observacion, forma_pago, delivery, fk_idcliente, \n"
            + "       fk_idusuario, fk_identregador,entrega,monto_venta_tarjeta\n"
            + "  FROM public.venta where idventa=";
    private String mensaje_terminar = "TERMINADO";
    private String mensaje_entregador = "CAMBIO ENTREGADOR";
    private String mensaje_pasa_cocina = "PASAR A COCINA";

    public void insertar_venta(Connection conn, venta ven) {
        int idventa = (eveconn.getInt_ultimoID_mas_uno(conn, ven.getTabla(), ven.getIdtabla()));
        ven.setC1idventa_estatico(idventa);
        ven.setC1idventa(idventa);
        String titulo = "insertar_venta";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, ven.getC1idventa());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setDouble(3, ven.getC3monto_venta_efectivo());
            pst.setDouble(4, ven.getC4monto_delivery());
            pst.setDouble(5, ven.getC5redondeo());
            pst.setString(6, ven.getC6estado());
            pst.setString(7, ven.getC7observacion());
            pst.setString(8, ven.getC8forma_pago());
            pst.setBoolean(9, ven.isC9delivery());
            pst.setInt(10, ven.getC10fk_idcliente());
            pst.setInt(11, ven.getC11fk_idusuario());
            pst.setInt(12, ven.getC12fk_identregador());
            pst.setString(13, ven.getC13entrega());
            pst.setDouble(14, ven.getC14monto_venta_tarjeta());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + ven.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, false);
        } catch (SQLException e) {
            evemen.mensaje_error(e, sql_insert + "\n" + ven.toString(), titulo);
        }
    }

    public void cargar_venta(venta ven, int idventa) {
        String titulo = "cargar_venta";
        Connection conn = ConnPostgres.getConnPosgres();

        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idventa, titulo);
            if (rs.next()) {
                ven.setC1idventa(rs.getInt(1));
                ven.setC2fecha_emision(rs.getString(2));
                ven.setC3monto_venta_efectivo(rs.getDouble(3));
                ven.setC4monto_delivery(rs.getDouble(4));
                ven.setC5redondeo(rs.getDouble(5));
                ven.setC6estado(rs.getString(6));
                ven.setC7observacion(rs.getString(7));
                ven.setC8forma_pago(rs.getString(8));
                ven.setC9delivery(rs.getBoolean(9));
                ven.setC10fk_idcliente(rs.getInt(10));
                ven.setC11fk_idusuario(rs.getInt(11));
                ven.setC12fk_identregador(rs.getInt(12));
                ven.setC13entrega(rs.getString(13));
                ven.setC14monto_venta_tarjeta(rs.getDouble(14));
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + ven.toString(), titulo);
        }
    }

    public void update_estado_venta(Connection conn, venta ven) {
        String titulo = "update_estado_venta";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_estado);
            pst.setString(1, ven.getC6estado());
            pst.setInt(2, ven.getC1idventa());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_estado + "\n" + ven.toString(), titulo);
            evemen.modificado_correcto(mensaje_terminar, false);
        } catch (SQLException e) {
            evemen.mensaje_error(e, sql_estado + "\n" + ven.toString(), titulo);
        }
    }

    public void update_forma_pago_venta(Connection conn, venta ven) {
        String titulo = "update_forma_pago_venta";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_forma_pago);
            pst.setString(1, ven.getC8forma_pago());
            pst.setInt(2, ven.getC1idventa());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_forma_pago + "\n" + ven.toString(), titulo);
            evemen.modificado_correcto(mensaje_terminar, false);
        } catch (SQLException e) {
            evemen.mensaje_error(e, sql_forma_pago + "\n" + ven.toString(), titulo);
        }
    }

    public void update_cambio_entregador(Connection conn, venta ven) {
        String titulo = "update_cambio_entregador";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_entregador);
            pst.setInt(1, ven.getC12fk_identregador());
            pst.setInt(2, ven.getC1idventa());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_entregador + "\n" + ven.toString(), titulo);
            evemen.modificado_correcto(mensaje_entregador, false);
        } catch (SQLException e) {
            evemen.mensaje_error(e, sql_entregador + "\n" + ven.toString(), titulo);
        }
    }

    private void update_estado_venta_servidor(Connection conn, String indice, String estado) {
        String titulo = "update_estado_venta_servidor";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_est_ser);
            pst.setString(1, estado);
            pst.setString(2, indice);
            pst.execute();
            conn.commit();
            pst.close();
            evemen.Imprimir_serial_sql(sql_est_ser + "\n", titulo);
        } catch (SQLException e) {
            evemen.mensaje_error(e, sql_est_ser + "\n", titulo);
        }
    }

    public void actualizar_tabla_venta(Connection conn, JTable tblventa, String filtro, String campo_filtro) {
        String sql_select = "select v.idventa,to_char(v.fecha_emision,'yyyy-MM-dd HH24:MI') as fecha,\n"
                + "(c.idcliente||'-'||c.nombre) as cliente,\n"
                + "c.telefono,v.estado,v.forma_pago,v.entrega as entrega,\n"
                + "TRIM(to_char(v.monto_venta_efectivo,'999G999G999')) as v_efectivo,\n"
                + "TRIM(to_char(v.monto_venta_tarjeta,'999G999G999')) as v_tarjeta, \n"
                + "TRIM(to_char((v.monto_venta_efectivo+v.monto_venta_tarjeta),'999G999G999')) as v_total\n"
                + "from venta v,cliente c,entregador e\n"
                + "where v.fk_idcliente=c.idcliente "
                + "and v.fk_identregador=e.identregador\n"
                + " " + filtro + "  order by v.idventa desc;";
        eveconn.Select_cargar_jtable(conn, sql_select, tblventa);
        ancho_tabla_venta(tblventa);
        everende.rendertabla_estados(tblventa, 4);
    }

    public void ancho_tabla_venta(JTable tblventa) {
        int Ancho[] = {6, 10, 20, 7, 8, 8, 11, 6, 6, 6};
        evejt.setAnchoColumnaJtable(tblventa, Ancho);
    }

    public void venta_terminado_hoy(Connection connLocal, Connection connServi) {
        String sql = "select indice,estado from venta where  "
                + " equipo='" + evepc.getString_nombre_pc() + "' "
                + "and date(fecha_inicio)=date('now()')";
        try {
            PreparedStatement pst = connServi.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            evemen.Imprimir_serial_sql(sql, "venta_terminado_hoy");
            while (rs.next()) {
                String indice = rs.getString("indice");
                String estado = rs.getString("estado");
                update_estado_venta_servidor(connLocal, indice, estado);
            }
        } catch (SQLException e) {
            evemen.Imprimir_serial_sql_error(e, sql, "venta_terminado_hoy");
        }
    }

    public void imprimir_rep_venta_todos(Connection conn, String filtro) {
        String sql = "select v.idventa as idventa,to_char(v.fecha_emision,'yyyy-MM-dd HH24:MI') as fecha,v.forma_pago as mesa,\n"
                + "c.ruc as ruc,('('||c.idcliente||')'||c.nombre) as cliente,c.tipo,\n"
                + "v.estado as estado,v.monto_venta_efectivo as monto,v.monto_delivery as delivery,u.usuario as usuario\n"
                + "from venta v,cliente c,usuario u\n"
                + "where v.fk_idcliente=c.idcliente\n"
                + "and v.fk_idusuario=u.idusuario\n"
                + " " + filtro + "\n"
                + "order by v.idventa desc;";
        String titulonota = "VENTA TODOS";
        String direccion = "src/REPORTE/VENTA/repVentaTodos.jrxml";
        rep.imprimirjasper(conn, sql, titulonota, direccion);
    }

    public void imprimir_rep_venta_todos_2(Connection conn, String filtro) {
        String sql = "select v.idventa as idventa,to_char(v.fecha_emision,'yyyy-MM-dd HH24:MI') as fecha,v.forma_pago as f_pago,\n"
                + "c.ruc as ruc,('('||c.idcliente||')'||c.nombre) as cliente,\n"
                + "v.estado as estado,v.monto_venta_efectivo as v_efectivo,v.monto_venta_tarjeta as v_tarjeta,\n"
                + "(v.monto_venta_efectivo+v.monto_venta_tarjeta) as v_total,u.usuario as usuario\n"
                + "from venta v,cliente c,usuario u\n"
                + "where v.fk_idcliente=c.idcliente\n"
                + "and v.fk_idusuario=u.idusuario\n"
                + " " + filtro + "\n"
                + "order by v.idventa desc;";
        String titulonota = "VENTA TODOS";
        String direccion = "src/REPORTE/VENTA/repVentaTodos_2.jrxml";
        rep.imprimirjasper(conn, sql, titulonota, direccion);
    }

    public double getDouble_suma_venta(Connection conn, String campo, String filtro) {
        double sumaventa = 0;
        String titulo = "getDouble_suma_venta";
        String sql = "select count(*) as cantidad,"
                + "sum(v.monto_venta_efectivo) as sumaventa_efectivo,\n"
                + "sum(v.monto_venta_tarjeta) as sumaventa_tarjeta, "
                + "sum(v.monto_venta_tarjeta+v.monto_venta_efectivo) as sumaventa_total "
                + "from venta v,cliente c,usuario u\n"
                + "where v.fk_idcliente=c.idcliente\n"
                + "and v.fk_idusuario=u.idusuario\n"
                + " " + filtro + "\n"
                + " ";
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            evemen.Imprimir_serial_sql(sql, titulo);
            if (rs.next()) {
                sumaventa = rs.getDouble(campo);
            }
        } catch (SQLException e) {
            evemen.Imprimir_serial_sql_error(e, sql, titulo);
        }
        return sumaventa;
    }

    public void imprimir_rep_venta_detalle(Connection conn, String filtro) {
        String sql = "select v.idventa as idventa,to_char(v.fecha_emision,'yyyy-MM-dd HH24:MI') as fecha,\n"
                + "v.forma_pago as mesa,v.estado as estado,\n"
                + "('('||c.idcliente||')'||c.nombre) as cliente,c.tipo as tipo,\n"
                + "(iv.descripcion) producto,\n"
                + "iv.cantidad as cant,(iv.cantidad*iv.precio_venta) as monto,\n"
                + "u.usuario as usuario\n"
                + "from venta v,cliente c,usuario u,item_venta iv\n"
                + "where v.fk_idcliente=c.idcliente\n"
                + "and v.fk_idusuario=u.idusuario\n"
                + "and v.idventa=iv.fk_idventa\n"
                + " " + filtro + "\n"
                + "order by iv.iditem_venta asc;";
        String titulonota = "VENTA DETALLE";
        String direccion = "src/REPORTE/VENTA/repVentaDetalle.jrxml";
        rep.imprimirjasper(conn, sql, titulonota, direccion);
    }

    public double getDouble_suma_venta_detalle(Connection conn, String campo, String filtro) {
        double sumaventa = 0;
        String titulo = "getDouble_suma_venta";
        String sql = "select count(*) as cantidad,sum(iv.cantidad*iv.precio_venta) as sumaventa\n"
                + "from venta v,cliente c,usuario u,item_venta iv\n"
                + "where v.fk_idcliente=c.idcliente\n"
                + "and v.fk_idusuario=u.idusuario\n"
                + "and v.idventa=iv.fk_idventa\n"
                + " " + filtro + "\n"
                + " ";
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            evemen.Imprimir_serial_sql(sql, titulo);
            if (rs.next()) {
                sumaventa = rs.getDouble(campo);
            }
        } catch (SQLException e) {
            evemen.Imprimir_serial_sql_error(e, sql, titulo);
        }
        return sumaventa;
    }
}
