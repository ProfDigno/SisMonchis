	package FORMULARIO.ENTIDAD;
public class item_venta_alquiler {

//---------------DECLARAR VARIABLES---------------
private int C1iditem_venta_alquiler;
private String C2descripcion;
private double C3precio_venta;
private double C4precio_compra;
private double C5cantidad_total;
private double C6cantidad_pagado;
private int C7fk_idventa_alquiler;
private int C8fk_idproducto;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public item_venta_alquiler() {
		setTb_item_venta_alquiler("item_venta_alquiler");
		setId_iditem_venta_alquiler("iditem_venta_alquiler");
	}
	public static String getTb_item_venta_alquiler(){
		return nom_tabla;
	}
	public static void setTb_item_venta_alquiler(String nom_tabla){
		item_venta_alquiler.nom_tabla = nom_tabla;
	}
	public static String getId_iditem_venta_alquiler(){
		return nom_idtabla;
	}
	public static void setId_iditem_venta_alquiler(String nom_idtabla){
		item_venta_alquiler.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1iditem_venta_alquiler(){
		return C1iditem_venta_alquiler;
	}
	public void setC1iditem_venta_alquiler(int C1iditem_venta_alquiler){
		this.C1iditem_venta_alquiler = C1iditem_venta_alquiler;
	}
	public String getC2descripcion(){
		return C2descripcion;
	}
	public void setC2descripcion(String C2descripcion){
		this.C2descripcion = C2descripcion;
	}
	public double getC3precio_venta(){
		return C3precio_venta;
	}
	public void setC3precio_venta(double C3precio_venta){
		this.C3precio_venta = C3precio_venta;
	}
	public double getC4precio_compra(){
		return C4precio_compra;
	}
	public void setC4precio_compra(double C4precio_compra){
		this.C4precio_compra = C4precio_compra;
	}
	public double getC5cantidad_total(){
		return C5cantidad_total;
	}
	public void setC5cantidad_total(double C5cantidad_total){
		this.C5cantidad_total = C5cantidad_total;
	}
	public double getC6cantidad_pagado(){
		return C6cantidad_pagado;
	}
	public void setC6cantidad_pagado(double C6cantidad_pagado){
		this.C6cantidad_pagado = C6cantidad_pagado;
	}
	public int getC7fk_idventa_alquiler(){
		return C7fk_idventa_alquiler;
	}
	public void setC7fk_idventa_alquiler(int C7fk_idventa_alquiler){
		this.C7fk_idventa_alquiler = C7fk_idventa_alquiler;
	}
	public int getC8fk_idproducto(){
		return C8fk_idproducto;
	}
	public void setC8fk_idproducto(int C8fk_idproducto){
		this.C8fk_idproducto = C8fk_idproducto;
	}
	public String toString() {
		return "item_venta_alquiler(" + ",iditem_venta_alquiler=" + C1iditem_venta_alquiler + " ,descripcion=" + C2descripcion + " ,precio_venta=" + C3precio_venta + " ,precio_compra=" + C4precio_compra + " ,cantidad_total=" + C5cantidad_total + " ,cantidad_pagado=" + C6cantidad_pagado + " ,fk_idventa_alquiler=" + C7fk_idventa_alquiler + " ,fk_idproducto=" + C8fk_idproducto + " )";
	}
}
