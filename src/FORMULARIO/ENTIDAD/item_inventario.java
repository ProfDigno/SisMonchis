	package FORMULARIO.ENTIDAD;
public class item_inventario {

//---------------DECLARAR VARIABLES---------------
private int C1iditem_inventario;
private int C2stock_sistema;
private int C3stock_contado;
private double C4precio_venta;
private double C5precio_compra;
private String C6estado;
private int C7fk_idinventario;
private int C8fk_idproducto;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public item_inventario() {
		setTb_item_inventario("item_inventario");
		setId_iditem_inventario("iditem_inventario");
	}
	public static String getTb_item_inventario(){
		return nom_tabla;
	}
	public static void setTb_item_inventario(String nom_tabla){
		item_inventario.nom_tabla = nom_tabla;
	}
	public static String getId_iditem_inventario(){
		return nom_idtabla;
	}
	public static void setId_iditem_inventario(String nom_idtabla){
		item_inventario.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1iditem_inventario(){
		return C1iditem_inventario;
	}
	public void setC1iditem_inventario(int C1iditem_inventario){
		this.C1iditem_inventario = C1iditem_inventario;
	}
	public int getC2stock_sistema(){
		return C2stock_sistema;
	}
	public void setC2stock_sistema(int C2stock_sistema){
		this.C2stock_sistema = C2stock_sistema;
	}
	public int getC3stock_contado(){
		return C3stock_contado;
	}
	public void setC3stock_contado(int C3stock_contado){
		this.C3stock_contado = C3stock_contado;
	}
	public double getC4precio_venta(){
		return C4precio_venta;
	}
	public void setC4precio_venta(double C4precio_venta){
		this.C4precio_venta = C4precio_venta;
	}
	public double getC5precio_compra(){
		return C5precio_compra;
	}
	public void setC5precio_compra(double C5precio_compra){
		this.C5precio_compra = C5precio_compra;
	}
	public String getC6estado(){
		return C6estado;
	}
	public void setC6estado(String C6estado){
		this.C6estado = C6estado;
	}
	public int getC7fk_idinventario(){
		return C7fk_idinventario;
	}
	public void setC7fk_idinventario(int C7fk_idinventario){
		this.C7fk_idinventario = C7fk_idinventario;
	}
	public int getC8fk_idproducto(){
		return C8fk_idproducto;
	}
	public void setC8fk_idproducto(int C8fk_idproducto){
		this.C8fk_idproducto = C8fk_idproducto;
	}
	public String toString() {
		return "item_inventario(" + ",iditem_inventario=" + C1iditem_inventario + " ,stock_sistema=" + C2stock_sistema + " ,stock_contado=" + C3stock_contado + " ,precio_venta=" + C4precio_venta + " ,precio_compra=" + C5precio_compra + " ,estado=" + C6estado + " ,fk_idinventario=" + C7fk_idinventario + " ,fk_idproducto=" + C8fk_idproducto + " )";
	}
}
