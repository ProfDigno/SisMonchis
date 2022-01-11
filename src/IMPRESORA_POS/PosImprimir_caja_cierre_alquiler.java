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
import FORMULARIO.ENTIDAD.caja_detalle_alquilado;
import FORMULARIO.ENTIDAD.claNombreEstatico;
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
public class PosImprimir_caja_cierre_alquiler {

    EvenConexion eveconn = new EvenConexion();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    private static json_config config = new json_config();
    private static json_imprimir_pos jsprint = new json_imprimir_pos();
    caja_detalle_alquilado caja = new caja_detalle_alquilado();
    ClaImpresoraPos pos = new ClaImpresoraPos();
    private static String tk_usuario = "digno";
    private static String tk_nombre_empresa = config.getNombre_sistema();
    private static String tk_ruta_archivo = "ticket_caja_cierre_alquiler.txt";
    private static FileInputStream inputStream = null;
    private static String nombre_ticket = "-CIERRE ALQUILER";
    private static String tk_idcaja_cierre = "000";
    private static String tk_inicio = "inicio";
    private static String tk_fin = "fin";
    private static String tk_in_Abrir = "0";
    private static String tk_in_vefectivo = "0";
    private static String tk_in_vtarjeta = "0";
    private static String tk_in_vtransferencia = "0";
    private static String tk_eg_compra = "0";
    private static String tk_eg_gasto = "0";
    private static String tk_eg_vale = "0";
    private static String tk_sistema = "0";
    private static String tk_cierre = "0";
    private static String tk_diferencia = "0";
    private static String tk_venta_grupo0 = "0";
    private static String tk_venta_grupo1 = "0";
    private static String tk_venta_deli = "0";
    private static String tk_eg_recibo = "0";
    private static String titulo_codigo = "CIERRE ALQ:";
    private static String titulo_delivery = "DELIVERY";
    private static String titulo_cajaabrir = "CAJA ABRIR:";
    private static String titulo_alquiler_efectivo = "ALQ. EFECTIVO:";
    private static String titulo_alquiler_tarjeta = "ALQ. TARJETA:";
    private static String titulo_alquiler_transfe = "ALQ. TRANSFE.:";
    private static String titulo_compra = "TOTAL COMPRA:";
    private static String titulo_compra_credito = "TOTAL COMP-CREDITO:";
    private static String titulo_gasto = "TOTAL GASTO:";
    private static String titulo_vale = "TOTAL VALE:";
    private static String titulo_sistema = "SAL.SISTEMA:";
    private static String titulo_cierre = "CAJA CIERRE:";
    private static String titulo_diferencia = "DIFERENCIA:";
    private static String titulo_recibo = "RECIBO CRED.:";
    private static String titulo_fec_inicio = "INICIO:";
    private static String titulo_fec_fin = "FIN:";
    private static int tk_iv_fila_top;
    private static String[] iv1_cantidad_top = new String[200];
    private static String[] iv2_nombre_top = new String[200];
    private static int compra_fila;
    private static String[] compra_monto = new String[200];
    private static String[] compra_proveedor = new String[200];
    private static String[] compra_nota = new String[200];
    private static String tk_eg_comp_credito = "0";
    claNombreEstatico nom_sta=new claNombreEstatico();

    private void cargar_datos_caja_cierre(Connection conn, int idcaja_cierre_alquilado) {
        String titulo = "cargar_datos_caja_cierre";
        String sql = "select cc.idcaja_cierre_alquilado,\n"
                + "to_char(cc.fecha_inicio,'yyyy-MM-dd HH24:MI') as inicio,\n"
                + "to_char(cc.fecha_fin,'yyyy-MM-dd HH24:MI') as fin,\n"
                + "TRIM(to_char(sum(cd.monto_apertura_caja),'999G999G999')) as in_Abrir,\n"
                + "TRIM(to_char(sum(cd.monto_alquilado_efectivo),'999G999G999')) as in_vefectivo,\n"
                + "TRIM(to_char(sum(cd.monto_alquilado_tarjeta),'999G999G999')) as in_vtarjeta,\n"
                + "TRIM(to_char(sum(cd.monto_alquilado_transferencia),'999G999G999')) as in_vtransferencia,\n"
                + "TRIM(to_char(sum(cd.monto_compra_contado),'999G999G999')) as eg_compra,\n"
                + "TRIM(to_char(sum(cd.monto_gasto),'999G999G999')) as eg_gasto,\n"
                + "TRIM(to_char(sum(cd.monto_vale),'999G999G999')) as eg_vale,\n"
                + "TRIM(to_char(sum(cd.monto_recibo_pago),'999G999G999')) as eg_recibo,\n"
                + "TRIM(to_char(sum(cd.monto_compra_credito),'999G999G999')) as comp_credito,\n"
                + "TRIM(to_char((sum(cd.monto_apertura_caja+cd.monto_alquilado_efectivo+cd.monto_alquilado_tarjeta+cd.monto_alquilado_transferencia+cd.monto_recibo_pago))-"
                + "(sum(cd.monto_compra_contado+cd.monto_gasto+cd.monto_vale)),'999G999G999')) as sistema,\n"
                + "TRIM(to_char(sum(cd.monto_cierre_caja),'999G999G999')) as cierre,\n"
                + "TRIM(to_char((sum(cd.monto_cierre_caja)-((sum(cd.monto_apertura_caja+cd.monto_alquilado_efectivo+cd.monto_alquilado_tarjeta+cd.monto_alquilado_transferencia+cd.monto_recibo_pago))-"
                + "(sum(cd.monto_compra_contado+cd.monto_gasto+cd.monto_vale)))),'999G999G999')) as diferencia\n"
                + "from caja_cierre_alquilado cc,item_caja_cierre_alquilado icc,caja_detalle_alquilado cd\n"
                + "where cc.idcaja_cierre_alquilado=icc.fk_idcaja_cierre_alquilado\n"
                + "and cd.idcaja_detalle_alquilado=icc.fk_idcaja_detalle_alquilado\n"
                + "and cc.idcaja_cierre_alquilado=" + idcaja_cierre_alquilado
                + "group by 1,2,3\n";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            if (rs.next()) {
                tk_idcaja_cierre = rs.getString("idcaja_cierre_alquilado");
                tk_inicio = rs.getString("inicio");
                tk_fin = rs.getString("fin");
                tk_in_Abrir = rs.getString("in_Abrir");
                tk_in_vefectivo = rs.getString("in_vefectivo");
                tk_in_vtarjeta = rs.getString("in_vtarjeta");
                tk_in_vtransferencia = rs.getString("in_vtransferencia");
                tk_eg_compra = rs.getString("eg_compra");
                tk_eg_gasto = rs.getString("eg_gasto");
                tk_eg_vale = rs.getString("eg_vale");
                tk_eg_recibo = rs.getString("eg_recibo");
                tk_eg_comp_credito = rs.getString("comp_credito");
                tk_sistema = rs.getString("sistema");
                tk_cierre = rs.getString("cierre");
                tk_diferencia = rs.getString("diferencia");
                tk_venta_deli = getString_venta_grupo_cantidad_total(conn, idcaja_cierre_alquilado);

            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
        }
    }

    String getString_venta_grupo_cantidad_total(Connection conn, int fk_idcaja_cierre_alquilado) {
        String total = "0";
        String titulo = "getString_venta_grupo_cantidad_total";
        String sql = "select TRIM(to_char(sum(cda.monto_delivery),'999G999G999')) as delivery\n"
                + "from caja_detalle_alquilado cda,item_caja_cierre_alquilado icc\n"
                + "where cda.idcaja_detalle_alquilado=icc.fk_idcaja_detalle_alquilado\n"
                + "and icc.fk_idcaja_cierre_alquilado="+fk_idcaja_cierre_alquilado
                + " and cda.tabla_origen='ALQUILER'\n"
                + "and cda.estado!='ANULADO'";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            if (rs.next()) {
                total = rs.getString("delivery");
            }
        } catch (SQLException e) {
            evemen.mensaje_error(e, sql, titulo);
        }
        if (total == null) {
            total = "0";
        }
        return total;
    }

    private void cargar_datos_itemventa_top_producto(Connection conn, int idcaja_cierre_alquilado) {
        String titulo = "cargar_datos_itemventa_top_producto";
        String sql = "select (pm.nombre||'-'||pu.nombre||'-'||p.nombre) as producto,sum(iv.cantidad_pagado) as cant\n"
                + "from caja_cierre_alquilado cc,item_caja_cierre_alquilado icc,caja_detalle_alquilado cd,\n"
                + "venta_alquiler v,item_venta_alquiler iv,producto p,producto_categoria pc,producto_unidad pu,producto_marca pm\n"
                + "where cc.idcaja_cierre_alquilado=" + idcaja_cierre_alquilado
                + " and (cd.tabla_origen='ALQUILER')\n"
                + "and (v.estado!='ANULADO')\n"
                + "and v.idventa_alquiler=iv.fk_idventa_alquiler\n"
                + "and cc.idcaja_cierre_alquilado=icc.fk_idcaja_cierre_alquilado\n"
                + "and icc.fk_idcaja_detalle_alquilado=cd.idcaja_detalle_alquilado\n"
                + "and cd.fk_idventa_alquiler=v.idventa_alquiler\n"
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

    private void cargar_datos_compra_proveedor(Connection conn, int idcaja_cierre_alquilado) {
        String titulo = "cargar_datos_compra_proveedor";
        String sql = "select ('Nota:('||co.nro_nota||')') as nota ,('('||co.condicion||')'||pv.nombre) as provee,\n"
                + "TRIM(to_char(co.monto_compra,'999G999G999')) as monto \n"
                + "from item_caja_cierre_alquilado icc,caja_detalle_alquilado cd,compra co,proveedor pv\n"
                + "where icc.fk_idcaja_cierre_alquilado=" + idcaja_cierre_alquilado
                + " and icc.fk_idcaja_detalle_alquilado=cd.idcaja_detalle_alquilado\n"
                + "and co.fk_idproveedor=pv.idproveedor\n"
                + "and cd.fk_idcompra=co.idcompra\n"
                + "and (cd.tabla_origen='" + nom_sta.getTabla_origen_compra_contado() + "' "
                + "or cd.tabla_origen='" + nom_sta.getTabla_origen_compra_credito() + "') \n"
                + "and co.estado!='ANULADO' \n"
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
        String linea_separador="==========================================";
        mensaje_impresora = mensaje_impresora + "=======" + config.getNombre_sistema() + nombre_ticket + "========" + saltolinea;
        mensaje_impresora = mensaje_impresora + titulo_codigo + tk_idcaja_cierre + saltolinea;
        mensaje_impresora = mensaje_impresora + titulo_fec_inicio + tk_inicio + saltolinea;
        mensaje_impresora = mensaje_impresora + titulo_fec_fin + tk_fin + saltolinea;
        mensaje_impresora = mensaje_impresora + linea_separador + saltolinea;
        mensaje_impresora = mensaje_impresora + titulo_cajaabrir + tabular + tk_in_Abrir + saltolinea;
        mensaje_impresora = mensaje_impresora + titulo_alquiler_efectivo + tabular + tk_in_vefectivo + saltolinea;
        mensaje_impresora = mensaje_impresora + titulo_alquiler_tarjeta + tabular + tk_in_vtarjeta + saltolinea;
        mensaje_impresora = mensaje_impresora + titulo_alquiler_transfe + tabular + tk_in_vtransferencia + saltolinea;
        mensaje_impresora = mensaje_impresora + titulo_recibo + tabular + tk_eg_recibo + saltolinea;
        mensaje_impresora = mensaje_impresora + "------------------------------------------" + saltolinea;
        mensaje_impresora = mensaje_impresora + titulo_delivery  + tabular + tk_venta_deli + saltolinea;
        mensaje_impresora = mensaje_impresora + linea_separador + saltolinea;
        mensaje_impresora = mensaje_impresora + titulo_gasto + tabular + tk_eg_gasto + saltolinea;
        mensaje_impresora = mensaje_impresora + titulo_vale + tabular + tk_eg_vale + saltolinea;
        mensaje_impresora = mensaje_impresora + titulo_compra + tabular + tk_eg_compra + saltolinea;
        mensaje_impresora = mensaje_impresora + linea_separador + saltolinea;
        mensaje_impresora = mensaje_impresora + titulo_sistema + tabular + tk_sistema + saltolinea;
        mensaje_impresora = mensaje_impresora + titulo_cierre + tabular + tk_cierre + saltolinea;
        mensaje_impresora = mensaje_impresora + titulo_diferencia + tabular + tk_diferencia + saltolinea;
        mensaje_impresora = mensaje_impresora + linea_separador + saltolinea;
        mensaje_impresora = mensaje_impresora + titulo_compra_credito + tabular + tk_eg_comp_credito + saltolinea;
        return mensaje_impresora;
    }
private static String getString_completar_caracter(String campo) {
        String nuevocampo = "";
        int max_char = 10;
        if (campo.trim().length() < max_char) {
            int cant_campo = campo.trim().length();
            int cant_spacio_add = max_char - cant_campo;
            for (int i = 0; i < cant_spacio_add; i++) {
                nuevocampo = nuevocampo + " ";
            }
            nuevocampo = nuevocampo + campo;
        } else {
            nuevocampo = "error";
        }
        return nuevocampo;
    }
    private static void crear_archivo_texto_impresion() throws PrintException, FileNotFoundException, IOException {
        int totalColumna = jsprint.getTotal_columna();
        PrinterMatrix printer = new PrinterMatrix();
        Extenso e = new Extenso();
        e.setNumber(101.85);
        //Definir el tamanho del papel 
        int tempfila = 0;
        int totalfila = jsprint.getTt_fila_cc() + tk_iv_fila_top + (compra_fila * 2);
        printer.setOutSize(totalfila, totalColumna);
        printer.printTextWrap(1 + tempfila, 1, jsprint.getSep_inicio(), totalColumna, jsprint.getLinea_cabezera() + config.getNombre_sistema() + jsprint.getLinea_cabezera());
        printer.printTextWrap(2 + tempfila, 2, 10, totalColumna, titulo_codigo + tk_idcaja_cierre);
        printer.printTextWrap(3 + tempfila, 3, jsprint.getSep_inicio(), totalColumna, titulo_fec_inicio);
        printer.printTextWrap(3 + tempfila, 3, jsprint.getSep_fecha(), totalColumna, tk_inicio);
        printer.printTextWrap(4 + tempfila, 4, jsprint.getSep_inicio(), totalColumna, titulo_fec_fin);
        printer.printTextWrap(4 + tempfila, 4, jsprint.getSep_fecha(), totalColumna, tk_fin);
        printer.printTextWrap(5 + tempfila, 5, jsprint.getSep_inicio(), totalColumna, jsprint.getLinea_separador());
        printer.printTextWrap(6 + tempfila, 6, jsprint.getSep_inicio(), totalColumna, titulo_cajaabrir);
        printer.printTextWrap(6 + tempfila, 6, jsprint.getSep_item_subtotal(), totalColumna, getString_completar_caracter(tk_in_Abrir));
        printer.printTextWrap(7 + tempfila, 7, jsprint.getSep_inicio(), totalColumna, titulo_alquiler_efectivo);
        printer.printTextWrap(7 + tempfila, 7, jsprint.getSep_item_subtotal(), totalColumna, getString_completar_caracter(tk_in_vefectivo));
        printer.printTextWrap(8 + tempfila, 8, jsprint.getSep_inicio(), totalColumna, titulo_alquiler_tarjeta);
        printer.printTextWrap(8 + tempfila, 8, jsprint.getSep_item_subtotal(), totalColumna, getString_completar_caracter(tk_in_vtarjeta));
        printer.printTextWrap(9 + tempfila, 9, jsprint.getSep_inicio(), totalColumna, titulo_alquiler_transfe);
        printer.printTextWrap(9 + tempfila, 9, jsprint.getSep_item_subtotal(), totalColumna, getString_completar_caracter(tk_in_vtransferencia));
        printer.printTextWrap(10 + tempfila, 10, jsprint.getSep_inicio(), totalColumna, titulo_recibo);
        printer.printTextWrap(10 + tempfila, 10, jsprint.getSep_item_subtotal(), totalColumna, getString_completar_caracter(tk_eg_recibo));
        printer.printTextWrap(11 + tempfila, 11, jsprint.getSep_inicio(), totalColumna, jsprint.getLinea_ven_detalle());
        printer.printTextWrap(12 + tempfila, 12, jsprint.getSep_inicio(), totalColumna, titulo_delivery + ":");
        printer.printTextWrap(12 + tempfila, 12, jsprint.getSep_item_subtotal(), totalColumna, getString_completar_caracter(tk_venta_deli));
        printer.printTextWrap(13 + tempfila, 13, jsprint.getSep_inicio(), totalColumna, jsprint.getLinea_separador());
        printer.printTextWrap(14 + tempfila, 14, jsprint.getSep_inicio(), totalColumna, titulo_gasto);
        printer.printTextWrap(14 + tempfila, 14, jsprint.getSep_item_subtotal(), totalColumna, getString_completar_caracter(tk_eg_gasto));
        printer.printTextWrap(15 + tempfila, 15, jsprint.getSep_inicio(), totalColumna, titulo_vale);
        printer.printTextWrap(15 + tempfila, 15, jsprint.getSep_item_subtotal(), totalColumna, getString_completar_caracter(tk_eg_vale));
        printer.printTextWrap(16 + tempfila, 16, jsprint.getSep_inicio(), totalColumna, titulo_compra);
        printer.printTextWrap(16 + tempfila, 16, jsprint.getSep_item_subtotal(), totalColumna, getString_completar_caracter(tk_eg_compra));
        
        printer.printTextWrap(18 + tempfila, 18, jsprint.getSep_inicio(), totalColumna, jsprint.getLinea_separador());
        printer.printTextWrap(19 + tempfila, 19, jsprint.getSep_inicio(), totalColumna, titulo_sistema);
        printer.printTextWrap(19 + tempfila, 19, jsprint.getSep_item_subtotal(), totalColumna, getString_completar_caracter(tk_sistema));
        printer.printTextWrap(20 + tempfila, 20, jsprint.getSep_inicio(), totalColumna, titulo_cierre);
        printer.printTextWrap(20 + tempfila, 20, jsprint.getSep_item_subtotal(), totalColumna, getString_completar_caracter(tk_cierre));
        printer.printTextWrap(21 + tempfila, 21, jsprint.getSep_inicio(), totalColumna, titulo_diferencia);
        printer.printTextWrap(21 + tempfila, 21, jsprint.getSep_item_subtotal(), totalColumna, getString_completar_caracter(tk_diferencia));
        printer.printTextWrap(22 + tempfila, 22, jsprint.getSep_inicio(), totalColumna, jsprint.getLinea_separador());
        printer.printTextWrap(23 + tempfila, 23, jsprint.getSep_inicio(), totalColumna, titulo_compra_credito);
        printer.printTextWrap(23 + tempfila, 23, jsprint.getSep_item_subtotal(), totalColumna, getString_completar_caracter(tk_eg_comp_credito));
        printer.printTextWrap(24 + tempfila, 24, jsprint.getSep_inicio(), totalColumna,
                jsprint.getLinea_ven_top_1() + jsprint.getCant_top_venta() + jsprint.getLinea_ven_top_2());
        for (int i = 0; i < tk_iv_fila_top; i++) {
            printer.printTextWrap(25 + tempfila, 25, jsprint.getSep_inicio(), totalColumna, iv1_cantidad_top[i] + " X");
            printer.printTextWrap(25 + tempfila, 25, jsprint.getSep_item_precio(), jsprint.getTt_text_descrip(), getString_completar_caracter(iv2_nombre_top[i]));
            tempfila = tempfila + 1;
        }
        printer.printTextWrap(26 + tempfila, 26, jsprint.getSep_inicio(), totalColumna, "-----COMPRA-PROVEEDOR----");
        for (int i = 0; i < compra_fila; i++) {
            printer.printTextWrap(27 + tempfila, 27, jsprint.getSep_inicio(), jsprint.getTt_text_descrip(), compra_proveedor[i]);
            printer.printTextWrap(28 + tempfila, 28, jsprint.getSep_inicio(), totalColumna, compra_nota[i]);
            printer.printTextWrap(28 + tempfila, 28, jsprint.getSep_item_subtotal(), totalColumna, getString_completar_caracter(compra_monto[i]));
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

    private void crear_archivo_enviar_impresora_alquiler() {
        String titulo = "crear_archivo_enviar_impresora_alquiler";
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
            crear_archivo_enviar_impresora_alquiler();
        }
    }

    public void boton_imprimir_pos_caja_cierre(Connection conn, int idcaja_cierre_alquilado) {
        cargar_datos_caja_cierre(conn, idcaja_cierre_alquilado);
        cargar_datos_itemventa_top_producto(conn, idcaja_cierre_alquilado);
        cargar_datos_compra_proveedor(conn, idcaja_cierre_alquilado);
        crear_mensaje_textarea_y_confirmar();
    }
}
