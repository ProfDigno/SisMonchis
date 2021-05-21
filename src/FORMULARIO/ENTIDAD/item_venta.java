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
public class item_venta {

    private int c1iditem_venta;
    private String c2descripcion;
    private double c3precio_venta;
    private double c4precio_compra;
    private double c5cantidad;
    private int c6fk_idventa;
    private int c7fk_idproducto;
    private static String tabla;
    private static String idtabla;

    public item_venta() {
        setTabla("item_venta");
        setIdtabla("iditem_venta");
    }

    public int getC1iditem_venta() {
        return c1iditem_venta;
    }

    public void setC1iditem_venta(int c1iditem_venta) {
        this.c1iditem_venta = c1iditem_venta;
    }

    public String getC2descripcion() {
        return c2descripcion;
    }

    public void setC2descripcion(String c2descripcion) {
        this.c2descripcion = c2descripcion;
    }

    public double getC3precio_venta() {
        return c3precio_venta;
    }

    public void setC3precio_venta(double c3precio_venta) {
        this.c3precio_venta = c3precio_venta;
    }

    public double getC4precio_compra() {
        return c4precio_compra;
    }

    public void setC4precio_compra(double c4precio_compra) {
        this.c4precio_compra = c4precio_compra;
    }

    public double getC5cantidad() {
        return c5cantidad;
    }

    public void setC5cantidad(double c5cantidad) {
        this.c5cantidad = c5cantidad;
    }

    public int getC6fk_idventa() {
        return c6fk_idventa;
    }

    public void setC6fk_idventa(int c7fk_idventa) {
        this.c6fk_idventa = c7fk_idventa;
    }

    public int getC7fk_idproducto() {
        return c7fk_idproducto;
    }

    public void setC7fk_idproducto(int c8fk_idproducto) {
        this.c7fk_idproducto = c8fk_idproducto;
    }

    public static String getTabla() {
        return tabla;
    }

    public static void setTabla(String tabla) {
        item_venta.tabla = tabla;
    }

    public static String getIdtabla() {
        return idtabla;
    }

    public static void setIdtabla(String idtabla) {
        item_venta.idtabla = idtabla;
    }

    @Override
    public String toString() {
        return "item_venta{" + "c1iditem_venta=" + c1iditem_venta + ", c2descripcion=" + c2descripcion + ", c3precio_venta=" + c3precio_venta + ", c4precio_compra=" + c4precio_compra + ", c5cantidad=" + c5cantidad + ", c7fk_idventa=" + c6fk_idventa + ", c8fk_idproducto=" + c7fk_idproducto + '}';
    }
    
}
