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
public class venta {



    /**
     * @return the idventaGlobal
     */
    public static int getIdventaGlobal() {
        return idventaGlobal;
    }

    /**
     * @param aIdventaGlobal the idventaGlobal to set
     */
    public static void setIdventaGlobal(int aIdventaGlobal) {
        idventaGlobal = aIdventaGlobal;
    }

    /**
     * @return the monto_ventaGlobal
     */
    public static double getMonto_ventaGlobal() {
        return monto_ventaGlobal;
    }

    /**
     * @param aMonto_ventaGlobal the monto_ventaGlobal to set
     */
    public static void setMonto_ventaGlobal(double aMonto_ventaGlobal) {
        monto_ventaGlobal = aMonto_ventaGlobal;
    }



    /**
     * @return the c12fk_idcliente_estatico
     */
    public static int getC12fk_idcliente_estatico() {
        return c10fk_idcliente_estatico;
    }

    /**
     * @param aC12fk_idcliente_estatico the c12fk_idcliente_estatico to set
     */
    public static void setC12fk_idcliente_estatico(int aC12fk_idcliente_estatico) {
        c10fk_idcliente_estatico = aC12fk_idcliente_estatico;
    }
    private int c1idventa;
    private static int c1idventa_estatico;
    private String c2fecha_emision;
    private double c3monto_venta;
    private double c4monto_delivery;
    private double c5redondeo;
    private String c6estado;
    private String c7observacion;
    private String c8forma_pago;
    private boolean c9delivery;
    private int c10fk_idcliente;
    private int c11fk_idusuario;
    private int c12fk_identregador;
    private String c13entrega;
    private static int c10fk_idcliente_estatico;
    
    private static String tabla;
    private static String idtabla;
    private static String campo_cliente_comanda;
    private static boolean venta_aux;
    private static int idventaGlobal;
    private static double monto_ventaGlobal;

    public static boolean isVenta_aux() {
        return venta_aux;
    }

    public static void setVenta_aux(boolean venta_aux) {
        venta.venta_aux = venta_aux;
    }

    
    
    public venta() {
        setTabla("venta");
        setIdtabla("idventa");
    }

    public int getC1idventa() {
        return c1idventa;
    }

    public void setC1idventa(int c1idventa) {
        this.c1idventa = c1idventa;
    }

    public String getC2fecha_emision() {
        return c2fecha_emision;
    }

    public void setC2fecha_emision(String c2fecha_inicio) {
        this.c2fecha_emision = c2fecha_inicio;
    }


    public String getC8forma_pago() {
        return c8forma_pago;
    }

    public void setC8forma_pago(String c4tipo_entrega) {
        this.c8forma_pago = c4tipo_entrega;
    }

    public String getC6estado() {
        return c6estado;
    }

    public void setC6estado(String c5estado) {
        this.c6estado = c5estado;
    }

    public double getC3monto_venta() {
        return c3monto_venta;
    }

    public void setC3monto_venta(double c6monto_venta) {
        this.c3monto_venta = c6monto_venta;
    }

    public double getC4monto_delivery() {
        return c4monto_delivery;
    }

    public void setC4monto_delivery(double c7monto_delivery) {
        this.c4monto_delivery = c7monto_delivery;
    }

    public boolean isC9delivery() {
        return c9delivery;
    }

    public void setC9delivery(boolean c8delivery) {
        this.c9delivery = c8delivery;
    }

    public String getC7observacion() {
        return c7observacion;
    }

    public void setC7observacion(String c9observacion) {
        this.c7observacion = c9observacion;
    }


    public double getC5redondeo() {
        return c5redondeo;
    }

    public void setC5redondeo(double c11equipo) {
        this.c5redondeo = c11equipo;
    }

    public int getC10fk_idcliente() {
        return c10fk_idcliente;
    }

    public void setC10fk_idcliente(int c12fk_idcliente) {
        this.c10fk_idcliente = c12fk_idcliente;
    }

    public int getC11fk_idusuario() {
        return c11fk_idusuario;
    }

    public void setC11fk_idusuario(int c13fk_idusuario) {
        this.c11fk_idusuario = c13fk_idusuario;
    }

    public int getC12fk_identregador() {
        return c12fk_identregador;
    }

    public void setC12fk_identregador(int c14fk_identregador) {
        this.c12fk_identregador = c14fk_identregador;
    }

    public static String getTabla() {
        return tabla;
    }

    public static void setTabla(String tabla) {
        venta.tabla = tabla;
    }

    public static String getIdtabla() {
        return idtabla;
    }

    public static void setIdtabla(String idtabla) {
        venta.idtabla = idtabla;
    }

    /**
     * @return the c1idventa_estatico
     */
    public static int getC1idventa_estatico() {
        return c1idventa_estatico;
    }

    /**
     * @param aC1idventa_estatico the c1idventa_estatico to set
     */
    public static void setC1idventa_estatico(int aC1idventa_estatico) {
        c1idventa_estatico = aC1idventa_estatico;
    }

    /**
     * @return the campo_cliente_comanda
     */
    public static String getCampo_cliente_comanda() {
        return campo_cliente_comanda;
    }

    /**
     * @param aCampo_cliente_comanda the campo_cliente_comanda to set
     */
    public static void setCampo_cliente_comanda(String aCampo_cliente_comanda) {
        campo_cliente_comanda = aCampo_cliente_comanda;
    }

    /**
     * @return the c13entrega
     */
    public String getC13entrega() {
        return c13entrega;
    }

    /**
     * @param c13entrega the c13entrega to set
     */
    public void setC13entrega(String c13entrega) {
        this.c13entrega = c13entrega;
    }
     
    
    
}
