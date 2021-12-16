	package FORMULARIO.ENTIDAD;
public class transaccion_banco {

//---------------DECLARAR VARIABLES---------------
private int C1idtransaccion_banco;
private String C2fecha_creado;
private String C3nro_transaccion;
private double C4monto;
private String C5observacion;
private String C6concepto;
private int C7fk_iddato_banco_cliente;
private int C8fk_iddato_banco_local;
private int C9fk_idventa_alquiler;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public transaccion_banco() {
		setTb_transaccion_banco("transaccion_banco");
		setId_idtransaccion_banco("idtransaccion_banco");
	}
	public static String getTb_transaccion_banco(){
		return nom_tabla;
	}
	public static void setTb_transaccion_banco(String nom_tabla){
		transaccion_banco.nom_tabla = nom_tabla;
	}
	public static String getId_idtransaccion_banco(){
		return nom_idtabla;
	}
	public static void setId_idtransaccion_banco(String nom_idtabla){
		transaccion_banco.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idtransaccion_banco(){
		return C1idtransaccion_banco;
	}
	public void setC1idtransaccion_banco(int C1idtransaccion_banco){
		this.C1idtransaccion_banco = C1idtransaccion_banco;
	}
	public String getC2fecha_creado(){
		return C2fecha_creado;
	}
	public void setC2fecha_creado(String C2fecha_creado){
		this.C2fecha_creado = C2fecha_creado;
	}
	public String getC3nro_transaccion(){
		return C3nro_transaccion;
	}
	public void setC3nro_transaccion(String C3nro_transaccion){
		this.C3nro_transaccion = C3nro_transaccion;
	}
	public double getC4monto(){
		return C4monto;
	}
	public void setC4monto(double C4monto){
		this.C4monto = C4monto;
	}
	public String getC5observacion(){
		return C5observacion;
	}
	public void setC5observacion(String C5observacion){
		this.C5observacion = C5observacion;
	}
	public String getC6concepto(){
		return C6concepto;
	}
	public void setC6concepto(String C6concepto){
		this.C6concepto = C6concepto;
	}
	public int getC7fk_iddato_banco_cliente(){
		return C7fk_iddato_banco_cliente;
	}
	public void setC7fk_iddato_banco_cliente(int C7fk_iddato_banco_cliente){
		this.C7fk_iddato_banco_cliente = C7fk_iddato_banco_cliente;
	}
	public int getC8fk_iddato_banco_local(){
		return C8fk_iddato_banco_local;
	}
	public void setC8fk_iddato_banco_local(int C8fk_iddato_banco_local){
		this.C8fk_iddato_banco_local = C8fk_iddato_banco_local;
	}
	public int getC9fk_idventa_alquiler(){
		return C9fk_idventa_alquiler;
	}
	public void setC9fk_idventa_alquiler(int C9fk_idventa_alquiler){
		this.C9fk_idventa_alquiler = C9fk_idventa_alquiler;
	}
	public String toString() {
		return "transaccion_banco(" + ",idtransaccion_banco=" + C1idtransaccion_banco + " ,fecha_creado=" + C2fecha_creado + " ,nro_transaccion=" + C3nro_transaccion + " ,monto=" + C4monto + " ,observacion=" + C5observacion + " ,concepto=" + C6concepto + " ,fk_iddato_banco_cliente=" + C7fk_iddato_banco_cliente + " ,fk_iddato_banco_local=" + C8fk_iddato_banco_local + " ,fk_idventa_alquiler=" + C9fk_idventa_alquiler + " )";
	}
}
