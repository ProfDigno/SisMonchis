	package FORMULARIO.ENTIDAD;
public class credito_finanza {

//---------------DECLARAR VARIABLES---------------
private int C1idcredito_finanza;
private String C2fecha_emision;
private String C3descripcion;
private String C4estado;
private double C5monto_contado;
private double C6monto_credito;
private String C7tabla_origen;
private int C8fk_idgrupo_credito_finanza;
private int C9fk_idsaldo_credito_finanza;
private int C10fk_idrecibo_pago_finanza;
private int C11fk_idcompra;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public credito_finanza() {
		setTb_credito_finanza("credito_finanza");
		setId_idcredito_finanza("idcredito_finanza");
	}
	public static String getTb_credito_finanza(){
		return nom_tabla;
	}
	public static void setTb_credito_finanza(String nom_tabla){
		credito_finanza.nom_tabla = nom_tabla;
	}
	public static String getId_idcredito_finanza(){
		return nom_idtabla;
	}
	public static void setId_idcredito_finanza(String nom_idtabla){
		credito_finanza.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idcredito_finanza(){
		return C1idcredito_finanza;
	}
	public void setC1idcredito_finanza(int C1idcredito_finanza){
		this.C1idcredito_finanza = C1idcredito_finanza;
	}
	public String getC2fecha_emision(){
		return C2fecha_emision;
	}
	public void setC2fecha_emision(String C2fecha_emision){
		this.C2fecha_emision = C2fecha_emision;
	}
	public String getC3descripcion(){
		return C3descripcion;
	}
	public void setC3descripcion(String C3descripcion){
		this.C3descripcion = C3descripcion;
	}
	public String getC4estado(){
		return C4estado;
	}
	public void setC4estado(String C4estado){
		this.C4estado = C4estado;
	}
	public double getC5monto_contado(){
		return C5monto_contado;
	}
	public void setC5monto_contado(double C5monto_contado){
		this.C5monto_contado = C5monto_contado;
	}
	public double getC6monto_credito(){
		return C6monto_credito;
	}
	public void setC6monto_credito(double C6monto_credito){
		this.C6monto_credito = C6monto_credito;
	}
	public String getC7tabla_origen(){
		return C7tabla_origen;
	}
	public void setC7tabla_origen(String C7tabla_origen){
		this.C7tabla_origen = C7tabla_origen;
	}
	public int getC8fk_idgrupo_credito_finanza(){
		return C8fk_idgrupo_credito_finanza;
	}
	public void setC8fk_idgrupo_credito_finanza(int C8fk_idgrupo_credito_finanza){
		this.C8fk_idgrupo_credito_finanza = C8fk_idgrupo_credito_finanza;
	}
	public int getC9fk_idsaldo_credito_finanza(){
		return C9fk_idsaldo_credito_finanza;
	}
	public void setC9fk_idsaldo_credito_finanza(int C9fk_idsaldo_credito_finanza){
		this.C9fk_idsaldo_credito_finanza = C9fk_idsaldo_credito_finanza;
	}
	public int getC10fk_idrecibo_pago_finanza(){
		return C10fk_idrecibo_pago_finanza;
	}
	public void setC10fk_idrecibo_pago_finanza(int C10fk_idrecibo_pago_finanza){
		this.C10fk_idrecibo_pago_finanza = C10fk_idrecibo_pago_finanza;
	}
	public int getC11fk_idcompra(){
		return C11fk_idcompra;
	}
	public void setC11fk_idcompra(int C11fk_idcompra){
		this.C11fk_idcompra = C11fk_idcompra;
	}
	public String toString() {
		return "credito_finanza(" + ",idcredito_finanza=" + C1idcredito_finanza + " ,fecha_emision=" + C2fecha_emision + " ,descripcion=" + C3descripcion + " ,estado=" + C4estado + " ,monto_contado=" + C5monto_contado + " ,monto_credito=" + C6monto_credito + " ,tabla_origen=" + C7tabla_origen + " ,fk_idgrupo_credito_finanza=" + C8fk_idgrupo_credito_finanza + " ,fk_idsaldo_credito_finanza=" + C9fk_idsaldo_credito_finanza + " ,fk_idrecibo_pago_finanza=" + C10fk_idrecibo_pago_finanza + " ,fk_idcompra=" + C11fk_idcompra + " )";
	}
}
