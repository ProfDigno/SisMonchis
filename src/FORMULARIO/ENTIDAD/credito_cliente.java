	package FORMULARIO.ENTIDAD;
public class credito_cliente {

    /**
     * @return the C12vence
     */
    public boolean getC12vence() {
        return C12vence;
    }

    /**
     * @param C12vence the C12vence to set
     */
    public void setC12vence(boolean C12vence) {
        this.C12vence = C12vence;
    }

    /**
     * @return the C13fecha_vence
     */
    public String getC13fecha_vence() {
        return C13fecha_vence;
    }

    /**
     * @param C13fecha_vence the C13fecha_vence to set
     */
    public void setC13fecha_vence(String C13fecha_vence) {
        this.C13fecha_vence = C13fecha_vence;
    }

//---------------DECLARAR VARIABLES---------------
private int C1idcredito_cliente;
private String C2fecha_emision;
private String C3descripcion;
private String C4estado;
private double C5monto_contado;
private double C6monto_credito;
private String C7tabla_origen;
private int C8fk_idgrupo_credito_cliente;
private int C9fk_idsaldo_credito_cliente;
private int C10fk_idrecibo_pago_cliente;
private int C11fk_idventa_alquiler;
private boolean C12vence;
private String C13fecha_vence;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public credito_cliente() {
		setTb_credito_cliente("credito_cliente");
		setId_idcredito_cliente("idcredito_cliente");
	}
	public static String getTb_credito_cliente(){
		return nom_tabla;
	}
	public static void setTb_credito_cliente(String nom_tabla){
		credito_cliente.nom_tabla = nom_tabla;
	}
	public static String getId_idcredito_cliente(){
		return nom_idtabla;
	}
	public static void setId_idcredito_cliente(String nom_idtabla){
		credito_cliente.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idcredito_cliente(){
		return C1idcredito_cliente;
	}
	public void setC1idcredito_cliente(int C1idcredito_cliente){
		this.C1idcredito_cliente = C1idcredito_cliente;
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
	public int getC8fk_idgrupo_credito_cliente(){
		return C8fk_idgrupo_credito_cliente;
	}
	public void setC8fk_idgrupo_credito_cliente(int C8fk_idgrupo_credito_cliente){
		this.C8fk_idgrupo_credito_cliente = C8fk_idgrupo_credito_cliente;
	}
	public int getC9fk_idsaldo_credito_cliente(){
		return C9fk_idsaldo_credito_cliente;
	}
	public void setC9fk_idsaldo_credito_cliente(int C9fk_idsaldo_credito_cliente){
		this.C9fk_idsaldo_credito_cliente = C9fk_idsaldo_credito_cliente;
	}
	public int getC10fk_idrecibo_pago_cliente(){
		return C10fk_idrecibo_pago_cliente;
	}
	public void setC10fk_idrecibo_pago_cliente(int C10fk_idrecibo_pago_cliente){
		this.C10fk_idrecibo_pago_cliente = C10fk_idrecibo_pago_cliente;
	}
	public int getC11fk_idventa_alquiler(){
		return C11fk_idventa_alquiler;
	}
	public void setC11fk_idventa_alquiler(int C11fk_idventa_alquiler){
		this.C11fk_idventa_alquiler = C11fk_idventa_alquiler;
	}
	public String toString() {
		return "credito_cliente(" + ",idcredito_cliente=" + C1idcredito_cliente + " ,fecha_emision=" + C2fecha_emision + " ,descripcion=" + C3descripcion + " ,estado=" + C4estado + " ,monto_contado=" + C5monto_contado + " ,monto_credito=" + C6monto_credito + " ,tabla_origen=" + C7tabla_origen + " ,fk_idgrupo_credito_cliente=" + C8fk_idgrupo_credito_cliente + " ,fk_idsaldo_credito_cliente=" + C9fk_idsaldo_credito_cliente + " ,fk_idrecibo_pago_cliente=" + C10fk_idrecibo_pago_cliente + " ,fk_idventa_alquiler=" + C11fk_idventa_alquiler + " )";
	}
}
