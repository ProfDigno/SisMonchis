	package FORMULARIO.ENTIDAD;
public class producto_marca {

//---------------DECLARAR VARIABLES---------------
private int C1idproducto_marca;
private String C2nombre;
private boolean C3activar;
private int C4orden;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public producto_marca() {
		setTb_producto_marca("producto_marca");
		setId_idproducto_marca("idproducto_marca");
	}
	public static String getTb_producto_marca(){
		return nom_tabla;
	}
	public static void setTb_producto_marca(String nom_tabla){
		producto_marca.nom_tabla = nom_tabla;
	}
	public static String getId_idproducto_marca(){
		return nom_idtabla;
	}
	public static void setId_idproducto_marca(String nom_idtabla){
		producto_marca.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idproducto_marca(){
		return C1idproducto_marca;
	}
	public void setC1idproducto_marca(int C1idproducto_marca){
		this.C1idproducto_marca = C1idproducto_marca;
	}
	public String getC2nombre(){
		return C2nombre;
	}
	public void setC2nombre(String C2nombre){
		this.C2nombre = C2nombre;
	}
	public boolean getC3activar(){
		return C3activar;
	}
	public void setC3activar(boolean C3activar){
		this.C3activar = C3activar;
	}
	public int getC4orden(){
		return C4orden;
	}
	public void setC4orden(int C4orden){
		this.C4orden = C4orden;
	}
	public String toString() {
		return "producto_marca(" + ",idproducto_marca=" + C1idproducto_marca + " ,nombre=" + C2nombre + " ,activar=" + C3activar + " ,orden=" + C4orden + " )";
	}
}
