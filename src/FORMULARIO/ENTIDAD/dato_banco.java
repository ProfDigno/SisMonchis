	package FORMULARIO.ENTIDAD;
public class dato_banco {

//---------------DECLARAR VARIABLES---------------
private int C1iddato_banco;
private String C2titular;
private String C3documento;
private String C4nro_cuenta;
private boolean C5mibanco;
private int C6fk_idbanco;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public dato_banco() {
		setTb_dato_banco("dato_banco");
		setId_iddato_banco("iddato_banco");
	}
	public static String getTb_dato_banco(){
		return nom_tabla;
	}
	public static void setTb_dato_banco(String nom_tabla){
		dato_banco.nom_tabla = nom_tabla;
	}
	public static String getId_iddato_banco(){
		return nom_idtabla;
	}
	public static void setId_iddato_banco(String nom_idtabla){
		dato_banco.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1iddato_banco(){
		return C1iddato_banco;
	}
	public void setC1iddato_banco(int C1iddato_banco){
		this.C1iddato_banco = C1iddato_banco;
	}
	public String getC2titular(){
		return C2titular;
	}
	public void setC2titular(String C2titular){
		this.C2titular = C2titular;
	}
	public String getC3documento(){
		return C3documento;
	}
	public void setC3documento(String C3documento){
		this.C3documento = C3documento;
	}
	public String getC4nro_cuenta(){
		return C4nro_cuenta;
	}
	public void setC4nro_cuenta(String C4nro_cuenta){
		this.C4nro_cuenta = C4nro_cuenta;
	}
	public boolean getC5mibanco(){
		return C5mibanco;
	}
	public void setC5mibanco(boolean C5mibanco){
		this.C5mibanco = C5mibanco;
	}
	public int getC6fk_idbanco(){
		return C6fk_idbanco;
	}
	public void setC6fk_idbanco(int C6fk_idbanco){
		this.C6fk_idbanco = C6fk_idbanco;
	}
	public String toString() {
		return "dato_banco(" + ",iddato_banco=" + C1iddato_banco + " ,titular=" + C2titular + " ,documento=" + C3documento + " ,nro_cuenta=" + C4nro_cuenta + " ,mibanco=" + C5mibanco + " ,fk_idbanco=" + C6fk_idbanco + " )";
	}
}
