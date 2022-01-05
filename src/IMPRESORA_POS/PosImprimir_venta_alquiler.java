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
import Evento.Utilitario.EvenUtil;
import br.com.adilson.util.Extenso;
import br.com.adilson.util.PrinterMatrix;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.print.PrintException;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author Digno
 */
public class PosImprimir_venta_alquiler {

    EvenConexion eveconn = new EvenConexion();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    private static json_config config = new json_config();
    ClaImpresoraPos pos = new ClaImpresoraPos();
    EvenUtil eveu = new EvenUtil();
    private static json_imprimir_pos jsprint = new json_imprimir_pos();
    private static String v1_idventa = "0";
    private static String v2_fec_alq = "0";
    private static String v3_cliente = "0";
    private static String v4_telefono = "0";
    private static String v5_direccion = "0";
    private static String v6_monto_pago = "0";
     private static String v6_monto_rese = "0";
    private static String v6_delivery = "0";
    private static String v7_observacion = "0";
    private static String v2_estado="0";
    private static String tk_nombre_empresa = config.getNombre_sistema();
    private static String tk_ruta_archivo = "ticket_venta_alquiler.txt";
    static int cant_caracter = 200;
    private static String[] iv1_cant_pago = new String[cant_caracter];
    private static String[] iv2_precio = new String[cant_caracter];
    private static int[] iv2_precio_int = new int[cant_caracter];
    private static int[] iv1_cant_rese_int= new int[cant_caracter];
    private static String[] iv3_sub_pago = new String[cant_caracter];
    private static String[] iv4_descripcion = new String[cant_caracter];
    private static String[] iv1_cant_rese = new String[cant_caracter];
    private static String[] iv3_sub_rese = new String[cant_caracter];
    private static FileInputStream inputStream = null;
    private static int tk_iv_fila;
    private String nombre_ticket = "ticket_venta_alquiler";
    private static int tk_iv_sum_fila;
    private static String v2_fec_dev = "0";
    private static String v2_forma_pago;
    private static String v2_condicion;
    
   
    

    private void cargar_datos_venta_alquiler(Connection conn, int idventa) {
        String titulo = "cargar_datos_venta";
        String sql = "select v.idventa_alquiler as idva,\n"
                + "v.forma_pago,v.condicion,v.estado,\n"
                + "to_char(v.fecha_retirado_real,'yyyy-MM-dd HH24:MI') as fec_alq,\n"
                + "to_char(v.fecha_devolusion_real,'yyyy-MM-dd HH24:MI') as fec_dev,\n"
                + "(c.idcliente||'-'||c.nombre) as cliente,\n"
                + "c.telefono,v.direccion_alquiler as direccion,\n"
                + "TRIM(to_char((v.monto_alquilado_efectivo+v.monto_alquilado_tarjeta+\n"
                + "v.monto_alquilado_transferencia+v.monto_alquilado_credito),'999G999G999')) as monto_pago,\n"
                + "TRIM(to_char((v.monto_total-(v.monto_alquilado_efectivo+v.monto_alquilado_tarjeta+\n"
                + "v.monto_alquilado_transferencia+v.monto_alquilado_credito)),'999G999G999')) as monto_rese,\n"
                + "TRIM(to_char(v.monto_delivery,'999G999G999')) as delivery,\n"
                + "v.observacion,iv.cantidad_pagado as cant_pago,iv.descripcion,\n"
                + "TRIM(to_char(iv.precio_venta,'999G999G999')) as precio,"
                + "iv.precio_venta as precioint,\n"
                + "TRIM(to_char((iv.cantidad_pagado*iv.precio_venta),'999G999G999'))  as sub_pago,\n"
                + "(iv.cantidad_total-iv.cantidad_pagado) as cant_rese,\n"
                + "TRIM(to_char(((iv.cantidad_total-iv.cantidad_pagado)*iv.precio_venta),'999G999G999'))  as sub_rese\n"
                + "from venta_alquiler v,cliente c,item_venta_alquiler iv\n"
                + "where v.fk_idcliente=c.idcliente\n"
                + "and v.idventa_alquiler=iv.fk_idventa_alquiler\n"
                + "and  v.idventa_alquiler=" + idventa
                + " order by iv.iditem_venta_alquiler asc";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            tk_iv_fila = 0;
            tk_iv_sum_fila = 0;
            while (rs.next()) {
                v1_idventa = rs.getString("idva");
                v2_forma_pago = rs.getString("forma_pago");
                v2_condicion = rs.getString("condicion");
                v2_estado = rs.getString("estado");
                v2_fec_alq = rs.getString("fec_alq");
                v2_fec_dev = rs.getString("fec_dev");
                v3_cliente = rs.getString("cliente");
                v4_telefono = rs.getString("telefono");
                v5_direccion = rs.getString("direccion");
                v6_monto_pago = rs.getString("monto_pago");
                v6_monto_rese = rs.getString("monto_rese");
                v6_delivery = rs.getString("delivery");
                v7_observacion = rs.getString("observacion");
                iv1_cant_pago[tk_iv_fila] = rs.getString("cant_pago");
                iv1_cant_rese[tk_iv_fila] = rs.getString("cant_rese");
                iv1_cant_rese_int[tk_iv_fila] = rs.getInt("cant_rese");
                iv2_precio[tk_iv_fila] = rs.getString("precio");
                iv3_sub_pago[tk_iv_fila] = rs.getString("sub_pago");
                iv3_sub_rese[tk_iv_fila] = rs.getString("sub_rese");
                iv4_descripcion[tk_iv_fila] = rs.getString("descripcion");
                iv2_precio_int[tk_iv_fila] = rs.getInt("precioint");
                if (iv2_precio_int[tk_iv_fila] > 0) {
                    tk_iv_sum_fila++;
                }
                tk_iv_fila++;
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
        }
    }

    private String cargar_datos_para_mensaje_textarea() {
        String mensaje_impresora = "";
        String saltolinea = "\n";
        String tabular = "\t";
        mensaje_impresora = mensaje_impresora + "===============" + config.getNombre_sistema() + "================" + saltolinea;
        mensaje_impresora = mensaje_impresora + config.getTel_sistema() + saltolinea;
        mensaje_impresora = mensaje_impresora + config.getDir_sistema() + saltolinea;
        mensaje_impresora = mensaje_impresora + "VENTA:" + v1_idventa + saltolinea;
        mensaje_impresora = mensaje_impresora + "CONDICION:" + v2_condicion + saltolinea;
        mensaje_impresora = mensaje_impresora + "FECHA ALQUILADO: " + v2_fec_alq + saltolinea;
        mensaje_impresora = mensaje_impresora + "FECHA DEVOLUCION: " + v2_fec_dev + saltolinea;
        mensaje_impresora = mensaje_impresora + "CLIENTE: " + v3_cliente + saltolinea;
        mensaje_impresora = mensaje_impresora + "TELEFONO: " + v4_telefono + saltolinea;
        mensaje_impresora = mensaje_impresora + "DIRECCION: " + v5_direccion + saltolinea;
        mensaje_impresora = mensaje_impresora + "==========================================" + saltolinea;
        for (int i = 0; i < tk_iv_fila; i++) {
            if (iv2_precio_int[i] > 0) {
                mensaje_impresora = mensaje_impresora + iv4_descripcion[i] + saltolinea;
                String item = iv1_cant_pago[i] + tabular + iv2_precio[i] + tabular + iv3_sub_pago[i] + saltolinea;
                mensaje_impresora = mensaje_impresora + item;
            }
        }
        mensaje_impresora = mensaje_impresora + "==========================================" + saltolinea;
        for (int i = 0; i < tk_iv_fila; i++) {
            if (iv1_cant_rese_int[i] > 0) {
                mensaje_impresora = mensaje_impresora + iv4_descripcion[i] + saltolinea;
                String item = iv1_cant_rese[i] + tabular + iv2_precio[i] + tabular + iv3_sub_rese[i] + saltolinea;
                mensaje_impresora = mensaje_impresora + item;
            }
        }
        mensaje_impresora = mensaje_impresora + "==========================================" + saltolinea;
        mensaje_impresora = mensaje_impresora + "ESTADO: " + v2_estado + saltolinea;
        mensaje_impresora = mensaje_impresora + "FORMA PAGO: " + v2_forma_pago + saltolinea;
        mensaje_impresora = mensaje_impresora + "OBSERVACION: " + v7_observacion + saltolinea;
        mensaje_impresora = mensaje_impresora + "MONTO PAGO :" + tabular + tabular + v6_monto_pago + saltolinea;
        mensaje_impresora = mensaje_impresora + "DELIVERY :" + tabular + tabular + v6_delivery + saltolinea;
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
        int totalfila = jsprint.getTt_fila_ven_alq() + (tk_iv_fila + tk_iv_fila + tk_iv_sum_fila);
        printer.setOutSize(totalfila, totalColumna);
        printer.printTextWrap(1 + tempfila, 1, jsprint.getSep_inicio(), totalColumna, jsprint.getLinea_cabezera() + config.getNombre_sistema() + jsprint.getLinea_cabezera());
        printer.printTextWrap(2 + tempfila, 2, jsprint.getSep_inicio(), totalColumna, config.getTel_sistema());
        printer.printTextWrap(3 + tempfila, 3, jsprint.getSep_inicio(), totalColumna, config.getDir_sistema());
        printer.printTextWrap(4 + tempfila, 4, jsprint.getSep_inicio(), totalColumna, jsprint.getLinea_separador());
        printer.printTextWrap(5 + tempfila, 5, jsprint.getSep_inicio(), totalColumna, "ALQ:" + v1_idventa);
        printer.printTextWrap(5 + tempfila, 5, jsprint.getSep_fecha(), totalColumna, "CONDI:" + v2_condicion);
        printer.printTextWrap(6 + tempfila, 6, jsprint.getSep_inicio(), totalColumna, "ALQUILADO: " + v2_fec_alq);
        printer.printTextWrap(7 + tempfila, 7, jsprint.getSep_inicio(), totalColumna, "DEVOLUCION:" + v2_fec_dev);
        printer.printTextWrap(8 + tempfila, 8, jsprint.getSep_inicio(), totalColumna, "CLI: " + v3_cliente);
        printer.printTextWrap(9 + tempfila, 9, jsprint.getSep_inicio(), totalColumna, "TEL: " + v4_telefono);
        printer.printTextWrap(10 + tempfila, 10, jsprint.getSep_inicio(), totalColumna, "DIR: " + v5_direccion);
        printer.printTextWrap(11 + tempfila, 11, jsprint.getSep_inicio(), totalColumna, jsprint.getLinea_separador());
        printer.printTextWrap(12 + tempfila, 12, jsprint.getSep_inicio(), totalColumna, "#####--ITEM-PAGADO--#####");
        for (int i = 0; i < tk_iv_fila; i++) {
            printer.printTextWrap(13 + tempfila, 13, jsprint.getSep_inicio(), jsprint.getTt_text_descrip(), iv4_descripcion[i]);
            if (iv2_precio_int[i] > 0) {
                printer.printTextWrap(14 + tempfila, 14, jsprint.getSep_item_cant(), totalColumna, iv1_cant_pago[i] + " X");
                printer.printTextWrap(14 + tempfila, 14, jsprint.getSep_item_precio(), totalColumna, iv2_precio[i] + " =");
                printer.printTextWrap(14 + tempfila, 14, jsprint.getSep_item_subtotal(), totalColumna, getString_completar_caracter(iv3_sub_pago[i]));
                tempfila = tempfila + 2;
            } else {
                tempfila = tempfila + 1;
            }
        }
        printer.printTextWrap(14 + tempfila, 14, jsprint.getSep_inicio(), totalColumna, jsprint.getLinea_separador());
        printer.printTextWrap(15 + tempfila, 15, jsprint.getSep_inicio(), totalColumna, "#####--ITEM-RESERVA--#####");
        for (int i = 0; i < tk_iv_fila; i++) {
            if (iv1_cant_rese_int[i] > 0) {
                printer.printTextWrap(16 + tempfila, 16, jsprint.getSep_inicio(), jsprint.getTt_text_descrip(), iv4_descripcion[i]);
                printer.printTextWrap(17 + tempfila, 17, jsprint.getSep_item_cant(), totalColumna, iv1_cant_rese[i] + " X");
                printer.printTextWrap(17 + tempfila, 17, jsprint.getSep_item_precio(), totalColumna, iv2_precio[i] + " =");
                printer.printTextWrap(17 + tempfila, 17, jsprint.getSep_item_subtotal(), totalColumna, getString_completar_caracter(iv3_sub_rese[i]));
                tempfila = tempfila + 2;
            } else {
                tempfila = tempfila + 0;
            }
        }
        printer.printTextWrap(17 + tempfila, 17, jsprint.getSep_inicio(), totalColumna, "MONTO RESERVA:");
        printer.printTextWrap(17 + tempfila, 17, jsprint.getSep_item_subtotal(), totalColumna, getString_completar_caracter(v6_monto_rese));
        printer.printTextWrap(18 + tempfila, 18, jsprint.getSep_inicio(), totalColumna, jsprint.getLinea_separador());
        printer.printTextWrap(19 + tempfila, 19, jsprint.getSep_inicio(), totalColumna, "ESTADO ALQ: " + v2_estado);
        printer.printTextWrap(20 + tempfila, 20, jsprint.getSep_inicio(), totalColumna, "FORMA PAGO: " + v2_forma_pago);
        printer.printTextWrap(21 + tempfila, 21, jsprint.getSep_inicio(), totalColumna, "OBSERVACION: " + v7_observacion);
        printer.printTextWrap(22 + tempfila, 22, jsprint.getSep_inicio(), totalColumna, "TOTAL PAGO:");
        printer.printTextWrap(22 + tempfila, 22, jsprint.getSep_total_gral(), totalColumna, getString_completar_caracter(v6_monto_pago));
        printer.printTextWrap(23 + tempfila, 23, jsprint.getSep_inicio(), totalColumna, "DELIVERY :");
        printer.printTextWrap(23 + tempfila, 23, jsprint.getSep_total_gral(), totalColumna, getString_completar_caracter(v6_delivery));
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
        int eleccion = JOptionPane.showOptionDialog(null, new JScrollPane(ta), tk_ruta_archivo,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, opciones, "IMPRIMIR");
        if (eleccion == JOptionPane.YES_OPTION) {
            crear_archivo_enviar_impresora();
        }
    }

    public void boton_imprimir_pos_VENTA(Connection conn, int idventa) {
        cargar_datos_venta_alquiler(conn, idventa);
        crear_mensaje_textarea_y_confirmar();
    }
}
