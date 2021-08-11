/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IMPRESORA_POS;

import BASEDATO.EvenConexion;
import Config_JSON.json_config;
import Config_JSON.json_imprimir_pos;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.ENTIDAD.caja_detalle;
import br.com.adilson.util.Extenso;
import br.com.adilson.util.PrinterMatrix;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.print.PrintException;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Digno
 */
public class PosImprimir_caja_cierre {

    EvenConexion eveconn = new EvenConexion();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    private static json_config config = new json_config();
    private static json_imprimir_pos jsprint = new json_imprimir_pos();
    caja_detalle caja = new caja_detalle();
    ClaImpresoraPos pos = new ClaImpresoraPos();
    private static String tk_usuario = "digno";
    private static String tk_nombre_empresa = config.getNombre_sistema();
    private static String tk_ruta_archivo = "ticket_caja_cierre.txt";
    private static FileInputStream inputStream = null;
    private static String nombre_ticket = "-CIERRE";
    private static String tk_idcaja_cierre = "000";
    private static String tk_inicio = "inicio";
    private static String tk_fin = "fin";
    private static String tk_in_Abrir = "0";
    private static String tk_in_vefectivo = "0";
    private static String tk_in_vtarjeta = "0";
    private static String tk_eg_compra = "0";
    private static String tk_eg_gasto = "0";
    private static String tk_eg_vale = "0";
    private static String tk_sistema = "0";
    private static String tk_cierre = "0";
    private static String tk_diferencia = "0";
    private static String tk_venta_grupo0 = "0";
    private static String tk_venta_grupo1 = "0";
    private static String tk_venta_deli = "0";
    private static String tk_eg_recibo="0";
    private static String nom_venta_deli = "DELIVERY";
    private static int tk_iv_fila_top;
    private static String[] iv1_cantidad_top = new String[200];
    private static String[] iv2_nombre_top = new String[200];
    private static int compra_fila;
    private static String[] compra_monto = new String[200];
    private static String[] compra_proveedor = new String[200];
    private static String[] compra_nota = new String[200];
    private static String tk_eg_comp_credito="0";
    

    private void cargar_datos_caja_cierre(Connection conn, int idcaja_cierre) {
        String titulo = "cargar_datos_caja_cierre";
        String sql = "select cc.idcaja_cierre,\n"
                + "to_char(cc.fecha_inicio,'yyyy-MM-dd HH24:MI') as inicio,\n"
                + "to_char(cc.fecha_fin,'yyyy-MM-dd HH24:MI') as fin,\n"
                + "TRIM(to_char(sum(cd.monto_caja),'999G999G999')) as in_Abrir,\n"
                + "TRIM(to_char(sum(cd.monto_venta_efectivo),'999G999G999')) as in_vefectivo,\n"
                + "TRIM(to_char(sum(cd.monto_venta_tarjeta),'999G999G999')) as in_vtarjeta,\n"
                + "TRIM(to_char(sum(cd.monto_compra),'999G999G999')) as eg_compra,\n"
                + "TRIM(to_char(sum(cd.monto_gasto),'999G999G999')) as eg_gasto,\n"
                + "TRIM(to_char(sum(cd.monto_vale),'999G999G999')) as eg_vale,\n"
                + "TRIM(to_char(sum(cd.monto_recibo_pago),'999G999G999')) as eg_recibo,\n"
                + "TRIM(to_char(sum(cd.monto_compra_credito),'999G999G999')) as comp_credito,\n"
                + "TRIM(to_char((sum(cd.monto_caja+cd.monto_venta_efectivo+cd.monto_venta_tarjeta))-"
                + "(sum(cd.monto_compra+cd.monto_gasto+cd.monto_vale+cd.monto_recibo_pago)),'999G999G999')) as sistema,\n"
                + "TRIM(to_char(sum(cd.monto_cierre),'999G999G999')) as cierre,\n"
                + "TRIM(to_char((sum(cd.monto_cierre)-((sum(cd.monto_caja+cd.monto_venta_efectivo+cd.monto_venta_tarjeta))-"
                + "(sum(cd.monto_compra+cd.monto_gasto+cd.monto_vale+cd.monto_recibo_pago)))),'999G999G999')) as diferencia\n"
                + "from caja_cierre cc,item_caja_cierre icc,caja_detalle cd\n"
                + "where cc.idcaja_cierre=icc.fk_idcaja_cierre\n"
                + "and cd.idcaja_detalle=icc.fk_idcaja_detalle\n"
                + "and cc.idcaja_cierre=" + idcaja_cierre
                + "group by 1,2,3\n";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            if (rs.next()) {
                tk_idcaja_cierre = rs.getString("idcaja_cierre");
                tk_inicio = rs.getString("inicio");
                tk_fin = rs.getString("fin");
                tk_in_Abrir = rs.getString("in_Abrir");
                tk_in_vefectivo = rs.getString("in_vefectivo");
                tk_in_vtarjeta = rs.getString("in_vtarjeta");
                tk_eg_compra = rs.getString("eg_compra");
                tk_eg_gasto = rs.getString("eg_gasto");
                tk_eg_vale = rs.getString("eg_vale");
                tk_eg_recibo = rs.getString("eg_recibo");
                tk_eg_comp_credito = rs.getString("comp_credito");
                tk_sistema = rs.getString("sistema");
                tk_cierre = rs.getString("cierre");
                tk_diferencia = rs.getString("diferencia");
                String grupo_delivery = " and iv.fk_idproducto=0 ";
                tk_venta_deli = getString_venta_grupo_cantidad_total(conn, idcaja_cierre, grupo_delivery);

            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
        }
    }

    String getString_venta_grupo_cantidad_total(Connection conn, int fk_idcaja_cierre, String grupo) {
        String total = "0";
        String titulo = "venta_grupo_cantidad_total";
        String sql = "select count(*) as cantidad,\n"
                + "TRIM(to_char(sum((iv.precio_venta*iv.cantidad)+0),'999G999G999')) as total\n"
                + " from caja_detalle c,item_caja_cierre icc,venta v,item_venta iv \n"
                + "where c.idcaja_detalle=icc.fk_idcaja_detalle\n"
                + "and v.idventa=iv.fk_idventa\n"
                + "and c.id_origen=v.idventa\n"
                + "and icc.fk_idcaja_cierre=" + fk_idcaja_cierre + "\n"
                + "and (v.estado='EMITIDO' or v.estado='TERMINADO')\n"
                + " " + grupo + "\n"
                + "and (c.tabla_origen ilike'%VENTA%');";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            if (rs.next()) {
                total = rs.getString("total");
            }
        } catch (SQLException e) {
            evemen.mensaje_error(e, sql, titulo);
        }
        if (total == null) {
            total = "0";
        }
        return total;
    }

    private void cargar_datos_itemventa_top_producto(Connection conn, int idcaja_cierre) {
        String titulo = "cargar_datos_itemventa_top_producto";
        String sql = "select (pm.nombre||'-'||pu.nombre||'-'||p.nombre) as producto,sum(iv.cantidad) as cant\n"
                + "from caja_cierre cc,item_caja_cierre icc,caja_detalle cd,\n"
                + "venta v,item_venta iv,producto p,producto_categoria pc,producto_unidad pu,producto_marca pm\n"
                + "where cc.idcaja_cierre=" + idcaja_cierre
                + " and (cd.tabla_origen ilike'%VENTA%')\n"
                + "and (v.estado='EMITIDO' or v.estado='TERMINADO')\n"
                + "and v.idventa=iv.fk_idventa\n"
                + "and cc.idcaja_cierre=icc.fk_idcaja_cierre\n"
                + "and icc.fk_idcaja_detalle=cd.idcaja_detalle\n"
                + "and cd.id_origen=v.idventa\n"
                + "and iv.fk_idproducto=p.idproducto\n"
                + "and p.fk_idproducto_categoria=pc.idproducto_categoria\n"
                + "and p.fk_idproducto_unidad=pu.idproducto_unidad\n"
                + "and p.fk_idproducto_marca=pm.idproducto_marca\n"
                + "group by 1\n"
                + "order by 2 desc limit " + jsprint.getCant_top_venta();
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            tk_iv_fila_top = 0;
            while (rs.next()) {
                iv1_cantidad_top[tk_iv_fila_top] = rs.getString("cant");
                iv2_nombre_top[tk_iv_fila_top] = rs.getString("producto");
                tk_iv_fila_top++;
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
        }
    }

    private void cargar_datos_compra_proveedor(Connection conn, int idcaja_cierre) {
        String titulo = "cargar_datos_compra_proveedor";
        String sql = "select ('Nota:('||co.nro_nota||')') as nota ,('('||co.condicion||')'||pv.nombre) as provee,\n"
                + "TRIM(to_char(co.monto_compra,'999G999G999')) as monto \n"
                + "from item_caja_cierre icc,caja_detalle cd,compra co,proveedor pv\n"
                + "where icc.fk_idcaja_cierre="+idcaja_cierre
                + " and icc.fk_idcaja_detalle=cd.idcaja_detalle\n"
                + "and co.fk_idproveedor=pv.idproveedor\n"
                + "and cd.id_origen=co.idcompra\n"
                + "and (cd.tabla_origen='"+caja.getTabla_origen_compra_contado()+"' "
                + "or cd.tabla_origen='"+caja.getTabla_origen_compra_credito()+"') \n"
                + "and co.estado='EMITIDO' \n"
                + "order by co.monto_compra desc;";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            compra_fila = 0;
            while (rs.next()) {
                compra_nota[compra_fila] = rs.getString("nota");
                compra_proveedor[compra_fila] = rs.getString("provee");
                compra_monto[compra_fila] = rs.getString("monto");
                compra_fila++;
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
        }
    }

    private String cargar_datos_para_mensaje_textarea() {
        String mensaje_impresora = "";
        String saltolinea = "\n";
        String tabular = "------->> ";
        mensaje_impresora = mensaje_impresora + "=======" + config.getNombre_sistema() + nombre_ticket + "========" + saltolinea;
        mensaje_impresora = mensaje_impresora + "CODIGO:" + tk_idcaja_cierre + saltolinea;
        mensaje_impresora = mensaje_impresora + "INICIO: " + tk_inicio + saltolinea;
        mensaje_impresora = mensaje_impresora + "FIN: " + tk_fin + saltolinea;
        mensaje_impresora = mensaje_impresora + "==========================================" + saltolinea;
        mensaje_impresora = mensaje_impresora + " CAJA ABRIR: " + tabular + tk_in_Abrir + saltolinea;
        mensaje_impresora = mensaje_impresora + "VENTA EFECTIVO: " + tabular + tk_in_vefectivo + saltolinea;
        mensaje_impresora = mensaje_impresora + "VENTA TARJETA: " + tabular + tk_in_vtarjeta + saltolinea;
        mensaje_impresora = mensaje_impresora + "------------------------------------------" + saltolinea;
        mensaje_impresora = mensaje_impresora + nom_venta_deli + ": " + tabular + tk_venta_deli + saltolinea;
        mensaje_impresora = mensaje_impresora + "==========================================" + saltolinea;
        mensaje_impresora = mensaje_impresora + " TOTAL GASTO: " + tabular + tk_eg_gasto + saltolinea;
        mensaje_impresora = mensaje_impresora + "  TOTAL VALE: " + tabular + tk_eg_vale + saltolinea;
        mensaje_impresora = mensaje_impresora + "TOTAL COMPRA: " + tabular + tk_eg_compra + saltolinea;
        mensaje_impresora = mensaje_impresora + "TOTAL RECIBO: " + tabular + tk_eg_recibo + saltolinea;
        mensaje_impresora = mensaje_impresora + "==========================================" + saltolinea;
        mensaje_impresora = mensaje_impresora + "SAL.SISTEMA: " + tabular + tk_sistema + saltolinea;
        mensaje_impresora = mensaje_impresora + "     CIERRE: " + tabular + tk_cierre + saltolinea;
        mensaje_impresora = mensaje_impresora + " DIFERENCIA: " + tabular + tk_diferencia + saltolinea;
        mensaje_impresora = mensaje_impresora + "==========================================" + saltolinea;
        mensaje_impresora = mensaje_impresora + "TOTAL COMP-CREDITO: " + tabular + tk_eg_comp_credito + saltolinea;
        return mensaje_impresora;
    }

    private static void crear_archivo_texto_impresion() throws PrintException, FileNotFoundException, IOException {
        int totalColumna = jsprint.getTotal_columna();
        PrinterMatrix printer = new PrinterMatrix();
        Extenso e = new Extenso();
        e.setNumber(101.85);
        //Definir el tamanho del papel 
        int tempfila = 0;
        int totalfila = jsprint.getTt_fila_cc() + tk_iv_fila_top+(compra_fila*2);
        printer.setOutSize(totalfila, totalColumna);
        printer.printTextWrap(1 + tempfila, 1, jsprint.getSep_inicio(), totalColumna, jsprint.getLinea_cabezera() + config.getNombre_sistema() + jsprint.getLinea_cabezera());
        printer.printTextWrap(2 + tempfila, 2, 10, totalColumna, "CODIGO:" + tk_idcaja_cierre);
        printer.printTextWrap(3 + tempfila, 3, jsprint.getSep_inicio(), totalColumna, "INICIO:");
        printer.printTextWrap(3 + tempfila, 3, jsprint.getSep_fecha(), totalColumna, tk_inicio);
        printer.printTextWrap(4 + tempfila, 4, jsprint.getSep_inicio(), totalColumna, "FIN:");
        printer.printTextWrap(4 + tempfila, 4, jsprint.getSep_fecha(), totalColumna, tk_fin);
        printer.printTextWrap(5 + tempfila, 5, jsprint.getSep_inicio(), totalColumna, jsprint.getLinea_separador());
        printer.printTextWrap(6 + tempfila, 6, jsprint.getSep_inicio(), totalColumna, "CAJA ABRIR: ");
        printer.printTextWrap(6 + tempfila, 6, jsprint.getSep_numero(), totalColumna, tk_in_Abrir);
        printer.printTextWrap(7 + tempfila, 7, jsprint.getSep_inicio(), totalColumna, "VENTA EFECTIVO: ");
        printer.printTextWrap(7 + tempfila, 7, jsprint.getSep_numero(), totalColumna, tk_in_vefectivo);
        printer.printTextWrap(8 + tempfila, 8, jsprint.getSep_inicio(), totalColumna, "VENTA TARJETA: ");
        printer.printTextWrap(8 + tempfila, 8, jsprint.getSep_numero(), totalColumna, tk_in_vtarjeta);
        printer.printTextWrap(9 + tempfila, 9, jsprint.getSep_inicio(), totalColumna, jsprint.getLinea_ven_detalle());
        printer.printTextWrap(11 + tempfila, 11, jsprint.getSep_inicio(), totalColumna, nom_venta_deli + ":");
        printer.printTextWrap(11 + tempfila, 11, jsprint.getSep_numero(), totalColumna, tk_venta_deli);
        printer.printTextWrap(12 + tempfila, 12, jsprint.getSep_inicio(), totalColumna, jsprint.getLinea_separador());
        printer.printTextWrap(13 + tempfila, 13, jsprint.getSep_inicio(), totalColumna, "TOTAL GASTO:");
        printer.printTextWrap(13 + tempfila, 13, jsprint.getSep_numero(), totalColumna, tk_eg_gasto);
        printer.printTextWrap(14 + tempfila, 14, jsprint.getSep_inicio(), totalColumna, "TOTAL VALE:");
        printer.printTextWrap(14 + tempfila, 14, jsprint.getSep_numero(), totalColumna, tk_eg_vale);
        printer.printTextWrap(15 + tempfila, 15, jsprint.getSep_inicio(), totalColumna, "TOTAL COMPRA:");
        printer.printTextWrap(15 + tempfila, 15, jsprint.getSep_numero(), totalColumna, tk_eg_compra);
        printer.printTextWrap(16 + tempfila, 16, jsprint.getSep_inicio(), totalColumna, "TOTAL RECIBO:");
        printer.printTextWrap(16 + tempfila, 16, jsprint.getSep_numero(), totalColumna, tk_eg_recibo);
        printer.printTextWrap(17 + tempfila, 17, jsprint.getSep_inicio(), totalColumna, jsprint.getLinea_separador());
        printer.printTextWrap(18 + tempfila, 18, jsprint.getSep_inicio(), totalColumna, "SISTEMA:");
        printer.printTextWrap(18 + tempfila, 18, jsprint.getSep_numero(), totalColumna, tk_sistema);
        printer.printTextWrap(19 + tempfila, 19, jsprint.getSep_inicio(), totalColumna, "CIERRE:");
        printer.printTextWrap(19 + tempfila, 19, jsprint.getSep_numero(), totalColumna, tk_cierre);
        printer.printTextWrap(20 + tempfila, 20, jsprint.getSep_inicio(), totalColumna, "DIFERENCIA:");
        printer.printTextWrap(20 + tempfila, 20, jsprint.getSep_numero(), totalColumna, "(" + tk_diferencia + ")");
        printer.printTextWrap(21 + tempfila, 21, jsprint.getSep_inicio(), totalColumna, jsprint.getLinea_separador());
        printer.printTextWrap(22 + tempfila, 22, jsprint.getSep_inicio(), totalColumna, "TOTAL COMP-CREDITO:");
        printer.printTextWrap(22 + tempfila, 22, jsprint.getSep_numero(), totalColumna, tk_eg_comp_credito);
        printer.printTextWrap(23 + tempfila, 23, jsprint.getSep_inicio(), totalColumna,
                jsprint.getLinea_ven_top_1() + jsprint.getCant_top_venta() + jsprint.getLinea_ven_top_2());
        for (int i = 0; i < tk_iv_fila_top; i++) {
            printer.printTextWrap(24 + tempfila, 24, jsprint.getSep_inicio(), totalColumna, iv1_cantidad_top[i] + " X");
            printer.printTextWrap(24 + tempfila, 24, jsprint.getSep_item_precio(), jsprint.getTt_text_descrip(), iv2_nombre_top[i]);
            tempfila = tempfila + 1;
        }
        printer.printTextWrap(25 + tempfila, 25, jsprint.getSep_inicio(), totalColumna, "-----COMPRA-PROVEEDOR----");
        for (int i = 0; i < compra_fila; i++) {
            printer.printTextWrap(26 + tempfila, 26, jsprint.getSep_inicio(), jsprint.getTt_text_descrip(), compra_proveedor[i]);
            printer.printTextWrap(27 + tempfila, 27, jsprint.getSep_inicio(), totalColumna, compra_nota[i]);
            printer.printTextWrap(27 + tempfila, 27, jsprint.getSep_numero(), totalColumna, compra_monto[i]);
            tempfila = tempfila + 2;
        }
        printer.toFile(tk_ruta_archivo);
        try {
            inputStream = new FileInputStream(tk_ruta_archivo);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            System.err.println(ex);
        }
        if (inputStream == null) {
            return;
        }

    }

    void crear_archivo_enviar_impresora() {
        String titulo = "crear_archivo_enviar_impresora";
        try {
            crear_archivo_texto_impresion();
            pos.setInputStream(inputStream);
            pos.imprimir_ticket_Pos();
        } catch (Exception e) {
            evemen.mensaje_error(e, titulo);
        }
    }

    private void crear_mensaje_textarea_y_confirmar() {
        JTextArea ta = new JTextArea(20, 30);
        ta.setText(cargar_datos_para_mensaje_textarea());
        System.out.println(cargar_datos_para_mensaje_textarea());
        Object[] opciones = {"IMPRIMIR", "CANCELAR"};
        int eleccion = JOptionPane.showOptionDialog(null, new JScrollPane(ta), nombre_ticket,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, opciones, "IMPRIMIR");
        if (eleccion == JOptionPane.YES_OPTION) {
            crear_archivo_enviar_impresora();
        }
    }

    public void boton_imprimir_pos_caja_cierre(Connection conn, int idcaja_cierre) {
        cargar_datos_caja_cierre(conn, idcaja_cierre);
        cargar_datos_itemventa_top_producto(conn, idcaja_cierre);
        cargar_datos_compra_proveedor(conn, idcaja_cierre);
        crear_mensaje_textarea_y_confirmar();
    }
}
