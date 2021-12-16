	package FORMULARIO.ENTIDAD;
public class grupo_credito_cliente {

//---------------DECLARAR VARIABLES---------------
private int C1idgrupo_credito_cliente;
private String C2fecha_inicio;
private String C3fecha_fin;
private String C4estado;
private int C5fk_idcliente;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public grupo_credito_cliente() {
		setTb_grupo_credito_cliente("grupo_credito_cliente");
		setId_idgrupo_credito_cliente("idgrupo_credito_cliente");
	}
	public static String getTb_grupo_credito_cliente(){
		return nom_tabla;
	}
	public static void setTb_grupo_credito_cliente(String nom_tabla){
		grupo_credito_cliente.nom_tabla = nom_tabla;
	}
	public static String getId_idgrupo_credito_cliente(){
		return nom_idtabla;
	}
	public static void setId_idgrupo_credito_cliente(String nom_idtabla){
		grupo_credito_cliente.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idgrupo_credito_cliente(){
		return C1idgrupo_credito_cliente;
	}
	public void setC1idgrupo_credito_cliente(int C1idgrupo_credito_cliente){
		this.C1idgrupo_credito_cliente = C1idgrupo_credito_cliente;
	}
	public String getC2fecha_inicio(){
		return C2fecha_inicio;
	}
	public void setC2fecha_inicio(String C2fecha_inicio){
		this.C2fecha_inicio = C2fecha_inicio;
	}
	public String getC3fecha_fin(){
		return C3fecha_fin;
	}
	public void setC3fecha_fin(String C3fecha_fin){
		this.C3fecha_fin = C3fecha_fin;
	}
	public String getC4estado(){
		return C4estado;
	}
	public void setC4estado(String C4estado){
		this.C4estado = C4estado;
	}
	public int getC5fk_idcliente(){
		return C5fk_idcliente;
	}
	public void setC5fk_idcliente(int C5fk_idcliente){
		this.C5fk_idcliente = C5fk_idcliente;
	}
	public String toString() {
		return "grupo_credito_cliente(" + ",idgrupo_credito_cliente=" + C1idgrupo_credito_cliente + " ,fecha_inicio=" + C2fecha_inicio + " ,fecha_fin=" + C3fecha_fin + " ,estado=" + C4estado + " ,fk_idcliente=" + C5fk_idcliente + " )";
	}
}
