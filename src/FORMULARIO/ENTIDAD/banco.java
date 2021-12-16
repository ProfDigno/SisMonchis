	package FORMULARIO.ENTIDAD;
public class banco {

//---------------DECLARAR VARIABLES---------------
private int C1idbanco;
private String C2nombre;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public banco() {
		setTb_banco("banco");
		setId_idbanco("idbanco");
	}
	public static String getTb_banco(){
		return nom_tabla;
	}
	public static void setTb_banco(String nom_tabla){
		banco.nom_tabla = nom_tabla;
	}
	public static String getId_idbanco(){
		return nom_idtabla;
	}
	public static void setId_idbanco(String nom_idtabla){
		banco.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idbanco(){
		return C1idbanco;
	}
	public void setC1idbanco(int C1idbanco){
		this.C1idbanco = C1idbanco;
	}
	public String getC2nombre(){
		return C2nombre;
	}
	public void setC2nombre(String C2nombre){
		this.C2nombre = C2nombre;
	}
	public String toString() {
		return "banco(" + ",idbanco=" + C1idbanco + " ,nombre=" + C2nombre + " )";
	}
}
