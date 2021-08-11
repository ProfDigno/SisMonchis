	package FORMULARIO.ENTIDAD;
public class grupo_credito_finanza {

//---------------DECLARAR VARIABLES---------------
private int C1idgrupo_credito_finanza;
private String C2fecha_inicio;
private String C3fecha_fin;
private String C4estado;
private int C5fk_idfinancista;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public grupo_credito_finanza() {
		setTb_grupo_credito_finanza("grupo_credito_finanza");
		setId_idgrupo_credito_finanza("idgrupo_credito_finanza");
	}
	public static String getTb_grupo_credito_finanza(){
		return nom_tabla;
	}
	public static void setTb_grupo_credito_finanza(String nom_tabla){
		grupo_credito_finanza.nom_tabla = nom_tabla;
	}
	public static String getId_idgrupo_credito_finanza(){
		return nom_idtabla;
	}
	public static void setId_idgrupo_credito_finanza(String nom_idtabla){
		grupo_credito_finanza.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idgrupo_credito_finanza(){
		return C1idgrupo_credito_finanza;
	}
	public void setC1idgrupo_credito_finanza(int C1idgrupo_credito_finanza){
		this.C1idgrupo_credito_finanza = C1idgrupo_credito_finanza;
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
	public int getC5fk_idfinancista(){
		return C5fk_idfinancista;
	}
	public void setC5fk_idfinancista(int C5fk_idfinancista){
		this.C5fk_idfinancista = C5fk_idfinancista;
	}
	public String toString() {
		return "grupo_credito_finanza(" + ",idgrupo_credito_finanza=" + C1idgrupo_credito_finanza + " ,fecha_inicio=" + C2fecha_inicio + " ,fecha_fin=" + C3fecha_fin + " ,estado=" + C4estado + " ,fk_idfinancista=" + C5fk_idfinancista + " )";
	}
}
