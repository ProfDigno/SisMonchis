/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.ENTIDAD;

/**
 *
 * @author Digno
 */
public class claNombreEstatico {
    private static String tabla_origen_venta_efectivo = "VENTA_EFECTIVO";
    private static String tabla_origen_venta_tarjeta = "VENTA_TARJETA";
    private static String tabla_origen_venta_combinado = "VENTA_COMBI";
    private static String tabla_origen_compra_contado = "COMPRA_CONTADO";
    private static String tabla_origen_compra_credito = "COMPRA_CREDITO";
    private static String tabla_origen_vale = "VALE";
    private static String tabla_origen_gasto = "GASTO";
    private static String tabla_origen_recibo = "RECIBO";
    private static String tabla_origen_caja_abrir = "CAJA_ABRIR";
    private static String tabla_origen_caja_cerrar = "CAJA_CERRAR";
    private static String tabla_origen_venta_alquiler = "ALQUILER";

    public static String getTabla_origen_venta_alquiler() {
        return tabla_origen_venta_alquiler;
    }
    
    public static String getTabla_origen_venta_efectivo() {
        return tabla_origen_venta_efectivo;
    }

    public static String getTabla_origen_venta_tarjeta() {
        return tabla_origen_venta_tarjeta;
    }

    public static String getTabla_origen_venta_combinado() {
        return tabla_origen_venta_combinado;
    }

    public static String getTabla_origen_compra_contado() {
        return tabla_origen_compra_contado;
    }

    public static String getTabla_origen_compra_credito() {
        return tabla_origen_compra_credito;
    }

    public static String getTabla_origen_vale() {
        return tabla_origen_vale;
    }

    public static String getTabla_origen_gasto() {
        return tabla_origen_gasto;
    }

    public static String getTabla_origen_recibo() {
        return tabla_origen_recibo;
    }

    public static String getTabla_origen_caja_abrir() {
        return tabla_origen_caja_abrir;
    }

    public static String getTabla_origen_caja_cerrar() {
        return tabla_origen_caja_cerrar;
    }
}
