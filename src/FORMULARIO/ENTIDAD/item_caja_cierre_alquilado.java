	package FORMULARIO.ENTIDAD;
public class item_caja_cierre_alquilado {

//---------------DECLARAR VARIABLES---------------
private int C1iditem_caja_cierre_alquilado;
private int C2fk_idcaja_cierre_alquilado;
private int C3fk_idcaja_detalle_alquilado;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public item_caja_cierre_alquilado() {
		setTb_item_caja_cierre_alquilado("item_caja_cierre_alquilado");
		setId_iditem_caja_cierre_alquilado("iditem_caja_cierre_alquilado");
	}
	public static String getTb_item_caja_cierre_alquilado(){
		return nom_tabla;
	}
	public static void setTb_item_caja_cierre_alquilado(String nom_tabla){
		item_caja_cierre_alquilado.nom_tabla = nom_tabla;
	}
	public static String getId_iditem_caja_cierre_alquilado(){
		return nom_idtabla;
	}
	public static void setId_iditem_caja_cierre_alquilado(String nom_idtabla){
		item_caja_cierre_alquilado.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1iditem_caja_cierre_alquilado(){
		return C1iditem_caja_cierre_alquilado;
	}
	public void setC1iditem_caja_cierre_alquilado(int C1iditem_caja_cierre_alquilado){
		this.C1iditem_caja_cierre_alquilado = C1iditem_caja_cierre_alquilado;
	}
	public int getC2fk_idcaja_cierre_alquilado(){
		return C2fk_idcaja_cierre_alquilado;
	}
	public void setC2fk_idcaja_cierre_alquilado(int C2fk_idcaja_cierre_alquilado){
		this.C2fk_idcaja_cierre_alquilado = C2fk_idcaja_cierre_alquilado;
	}
	public int getC3fk_idcaja_detalle_alquilado(){
		return C3fk_idcaja_detalle_alquilado;
	}
	public void setC3fk_idcaja_detalle_alquilado(int C3fk_idcaja_detalle_alquilado){
		this.C3fk_idcaja_detalle_alquilado = C3fk_idcaja_detalle_alquilado;
	}
	public String toString() {
		return "item_caja_cierre_alquilado(" + ",iditem_caja_cierre_alquilado=" + C1iditem_caja_cierre_alquilado + " ,fk_idcaja_cierre_alquilado=" + C2fk_idcaja_cierre_alquilado + " ,fk_idcaja_detalle_alquilado=" + C3fk_idcaja_detalle_alquilado + " )";
	}
}
