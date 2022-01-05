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
public class caja_detalle {

    /**
     * @return the c16monto_recibo_pago
     */
    public double getC17monto_recibo_pago() {
        return c17monto_recibo_pago;
    }

    /**
     * @param c16monto_recibo_pago the c16monto_recibo_pago to set
     */
    public void setC17monto_recibo_pago(double c16monto_recibo_pago) {
        this.c17monto_recibo_pago = c16monto_recibo_pago;
    }

    /**
     * @return the c5monto_venta_tarjeta
     */
    public double getC5monto_venta_tarjeta() {
        return c5monto_venta_tarjeta;
    }

    /**
     * @param c5monto_venta_tarjeta the c5monto_venta_tarjeta to set
     */
    public void setC5monto_venta_tarjeta(double c5monto_venta_tarjeta) {
        this.c5monto_venta_tarjeta = c5monto_venta_tarjeta;
    }
    private int c1idcaja_detalle;
    private String c2fecha_emision;
    private String c3descripcion;
    private double c4monto_venta_efectivo;
    private double c5monto_venta_tarjeta;
    private double c6monto_delivery;
    private double c7monto_gasto;
    private double c8monto_compra;
    private double c9monto_vale;
    private double c10monto_caja;
    private double c11monto_cierre;
    private int c12id_origen;
    private String c13tabla_origen;
    private String c13tabla_origen_update;
    private String c14cierre;
    private String c15estado;
    private double c17monto_recibo_pago;
    private double c18monto_compra_credito;
    private int c16fk_idusuario;
    
    

    private static String tabla;
    private static String idtabla;

    public static String getTabla_origen_venta_efectivo() {
        return tabla_origen_venta_efectivo;
    }

    public static String getTabla_origen_venta_tarjeta() {
        return tabla_origen_venta_tarjeta;
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

    public static String getTabla_origen_caja() {
        return tabla_origen_caja;
    }

    public static String getTabla_origen_cierre() {
        return tabla_origen_cierre;
    }
    public static String getTabla_origen_venta_combinado() {
        return tabla_origen_venta_combinado;
    }
    private static String tabla_origen_venta_efectivo="VENTA_EFECTIVO";
    private static String tabla_origen_venta_tarjeta="VENTA_TARJETA";
    private static String tabla_origen_venta_combinado="VENTA_COMBI";
    private static String tabla_origen_compra_contado="COMPRA_CONTADO";
    private static String tabla_origen_compra_credito="COMPRA_CREDITO";
    private static String tabla_origen_vale="VALE";
    private static String tabla_origen_gasto="GASTO";
    private static String tabla_origen_recibo="RECIBO";
    private static String tabla_origen_caja_abrir="CAJA_ABRIR";
    private static String tabla_origen_caja_cerrar="CAJA_CERRAR";

    public static String getTabla_origen_recibo() {
        return tabla_origen_recibo;
    }
    
    public static String getTabla_origen_caja_abrir() {
        return tabla_origen_caja_abrir;
    }

    public static String getTabla_origen_caja_cerrar() {
        return tabla_origen_caja_cerrar;
    }
    private static String tabla_origen_caja;
    private static String tabla_origen_cierre;
    public caja_detalle() {
        setTabla("caja_detalle");
        setIdtabla("idcaja_detalle");
    }

    public String getC15estado() {
        return c15estado;
    }

    public void setC15estado(String c12indice) {
        this.c15estado = c12indice;
    }


    public int getC1idcaja_detalle() {
        return c1idcaja_detalle;
    }

    public void setC1idcaja_detalle(int c1idcaja_detalle) {
        this.c1idcaja_detalle = c1idcaja_detalle;
    }

    public String getC2fecha_emision() {
        return c2fecha_emision;
    }

    public void setC2fecha_emision(String c2fecha_emision) {
        this.c2fecha_emision = c2fecha_emision;
    }

    public String getC3descripcion() {
        return c3descripcion;
    }

    public void setC3descripcion1(String c3descripcion) {
        this.c3descripcion = c3descripcion;
    }

    public double getC4monto_venta_efectivo() {
        return c4monto_venta_efectivo;
    }

    public void setC4monto_venta_efectivo(double c4monto_venta) {
        this.c4monto_venta_efectivo = c4monto_venta;
    }

    public double getC6monto_delivery() {
        return c6monto_delivery;
    }

    public void setC6monto_delivery(double c5monto_delivery) {
        this.c6monto_delivery = c5monto_delivery;
    }

    public double getC7monto_gasto() {
        return c7monto_gasto;
    }

    public void setC7monto_gasto(double c6monto_gasto) {
        this.c7monto_gasto = c6monto_gasto;
    }

    public double getC8monto_compra() {
        return c8monto_compra;
    }

    public void setC8monto_compra(double c7monto_compra) {
        this.c8monto_compra = c7monto_compra;
    }

    public double getC9monto_vale() {
        return c9monto_vale;
    }

    public void setC9monto_vale(double c8monto_vale) {
        this.c9monto_vale = c8monto_vale;
    }

    public int getC12id_origen() {
        return c12id_origen;
    }

    public void setC12id_origen(int c9id_origen) {
        this.c12id_origen = c9id_origen;
    }

    public String getC13tabla_origen() {
        return c13tabla_origen;
    }

    public void setC13tabla_origen(String c10tabla_origen) {
        this.c13tabla_origen = c10tabla_origen;
    }

    public int getC16fk_idusuario() {
        return c16fk_idusuario;
    }

    public void setC16fk_idusuario(int c11fk_idusuario) {
        this.c16fk_idusuario = c11fk_idusuario;
    }

    public static String getTabla() {
        return tabla;
    }

    public static void setTabla(String tabla) {
        caja_detalle.tabla = tabla;
    }

    public static String getIdtabla() {
        return idtabla;
    }

    public static void setIdtabla(String idtabla) {
        caja_detalle.idtabla = idtabla;
    }

    @Override
    public String toString() {
        return "caja_detalle{" + "c1idcaja_detalle=" + c1idcaja_detalle + ", c2fecha_emision=" + c2fecha_emision + ", c3descripcion=" + c3descripcion + ", c4monto_venta=" + c4monto_venta_efectivo + ", c5monto_delivery=" + c6monto_delivery + ", c6monto_gasto=" + c7monto_gasto + ", c7monto_compra=" + c8monto_compra + ", c8monto_vale=" + c9monto_vale + ", c9id_origen=" + c12id_origen + ", c10tabla_origen=" + c13tabla_origen + ", c11fk_idusuario=" + c16fk_idusuario + ", c12indice=" + c15estado + ", c14cierre=" + c14cierre + ", c15monto_caja=" + c10monto_caja + ", c16monto_cierre=" + c11monto_cierre + '}';
    }

    

    /**
     * @return the c14cierre
     */
    public String getC14cierre() {
        return c14cierre;
    }

    /**
     * @param c14cierre the c14cierre to set
     */
    public void setC14cierre(String c14cierre) {
        this.c14cierre = c14cierre;
    }

    /**
     * @return the c15monto_caja
     */
    public double getC10monto_caja() {
        return c10monto_caja;
    }

    /**
     * @param c15monto_caja the c15monto_caja to set
     */
    public void setC10monto_caja(double c15monto_caja) {
        this.c10monto_caja = c15monto_caja;
    }

    /**
     * @return the c16monto_cierre
     */
    public double getC11monto_cierre() {
        return c11monto_cierre;
    }

    /**
     * @param c16monto_cierre the c16monto_cierre to set
     */
    public void setC11monto_cierre(double c16monto_cierre) {
        this.c11monto_cierre = c16monto_cierre;
    }

    /**
     * @return the c13tabla_origen_update
     */
    public String getC13tabla_origen_update() {
        return c13tabla_origen_update;
    }

    /**
     * @param c13tabla_origen_update the c13tabla_origen_update to set
     */
    public void setC13tabla_origen_update(String c13tabla_origen_update) {
        this.c13tabla_origen_update = c13tabla_origen_update;
    }

    /**
     * @return the c18monto_compra_credito
     */
    public double getC18monto_compra_credito() {
        return c18monto_compra_credito;
    }

    /**
     * @param c18monto_compra_credito the c18monto_compra_credito to set
     */
    public void setC18monto_compra_credito(double c18monto_compra_credito) {
        this.c18monto_compra_credito = c18monto_compra_credito;
    }

    
    
}
