	package FORMULARIO.ENTIDAD;
public class recibo_pago_cliente {

//---------------DECLARAR VARIABLES---------------
private int C1idrecibo_pago_cliente;
private String C2fecha_emision;
private String C3descripcion;
private double C4monto_recibo_pago;
private String C5monto_letra;
private String C6estado;
private int C7fk_idcliente;
private int C8fk_idusuario;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public recibo_pago_cliente() {
		setTb_recibo_pago_cliente("recibo_pago_cliente");
		setId_idrecibo_pago_cliente("idrecibo_pago_cliente");
	}
	public static String getTb_recibo_pago_cliente(){
		return nom_tabla;
	}
	public static void setTb_recibo_pago_cliente(String nom_tabla){
		recibo_pago_cliente.nom_tabla = nom_tabla;
	}
	public static String getId_idrecibo_pago_cliente(){
		return nom_idtabla;
	}
	public static void setId_idrecibo_pago_cliente(String nom_idtabla){
		recibo_pago_cliente.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idrecibo_pago_cliente(){
		return C1idrecibo_pago_cliente;
	}
	public void setC1idrecibo_pago_cliente(int C1idrecibo_pago_cliente){
		this.C1idrecibo_pago_cliente = C1idrecibo_pago_cliente;
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
	public double getC4monto_recibo_pago(){
		return C4monto_recibo_pago;
	}
	public void setC4monto_recibo_pago(double C4monto_recibo_pago){
		this.C4monto_recibo_pago = C4monto_recibo_pago;
	}
	public String getC5monto_letra(){
		return C5monto_letra;
	}
	public void setC5monto_letra(String C5monto_letra){
		this.C5monto_letra = C5monto_letra;
	}
	public String getC6estado(){
		return C6estado;
	}
	public void setC6estado(String C6estado){
		this.C6estado = C6estado;
	}
	public int getC7fk_idcliente(){
		return C7fk_idcliente;
	}
	public void setC7fk_idcliente(int C7fk_idcliente){
		this.C7fk_idcliente = C7fk_idcliente;
	}
	public int getC8fk_idusuario(){
		return C8fk_idusuario;
	}
	public void setC8fk_idusuario(int C8fk_idusuario){
		this.C8fk_idusuario = C8fk_idusuario;
	}
	public String toString() {
		return "recibo_pago_cliente(" + ",idrecibo_pago_cliente=" + C1idrecibo_pago_cliente + " ,fecha_emision=" + C2fecha_emision + " ,descripcion=" + C3descripcion + " ,monto_recibo_pago=" + C4monto_recibo_pago + " ,monto_letra=" + C5monto_letra + " ,estado=" + C6estado + " ,fk_idcliente=" + C7fk_idcliente + " ,fk_idusuario=" + C8fk_idusuario + " )";
	}
}
