	package FORMULARIO.ENTIDAD;
public class venta_alquiler {

    /**
     * @return the C21monto_alquilado_credito
     */
    public double getC21monto_alquilado_credito() {
        return C21monto_alquilado_credito;
    }

    /**
     * @param C21monto_alquilado_credito the C21monto_alquilado_credito to set
     */
    public void setC21monto_alquilado_credito(double C21monto_alquilado_credito) {
        this.C21monto_alquilado_credito = C21monto_alquilado_credito;
    }

//---------------DECLARAR VARIABLES---------------
private int C1idventa_alquiler;
private String C2fecha_creado;
private String C3fecha_retirado_previsto;
private String C4fecha_retirado_real;
private String C5fecha_devolusion_previsto;
private String C6fecha_devolusion_real;
private double C7monto_total;
private double C8monto_alquilado_efectivo;
private double C9monto_alquilado_tarjeta;
private double C10monto_alquilado_transferencia;
private double C11monto_delivery;
private String C12forma_pago;
private String C13condicion;
private boolean C14alquiler_retirado;
private boolean C15alquiler_devolusion;
private String C16direccion_alquiler;
private String C17observacion;
private String C18estado;
private int C19fk_idcliente;
private int C20fk_identregador;
private double C21monto_alquilado_credito;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public venta_alquiler() {
		setTb_venta_alquiler("venta_alquiler");
		setId_idventa_alquiler("idventa_alquiler");
	}
	public static String getTb_venta_alquiler(){
		return nom_tabla;
	}
	public static void setTb_venta_alquiler(String nom_tabla){
		venta_alquiler.nom_tabla = nom_tabla;
	}
	public static String getId_idventa_alquiler(){
		return nom_idtabla;
	}
	public static void setId_idventa_alquiler(String nom_idtabla){
		venta_alquiler.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idventa_alquiler(){
		return C1idventa_alquiler;
	}
	public void setC1idventa_alquiler(int C1idventa_alquiler){
		this.C1idventa_alquiler = C1idventa_alquiler;
	}
	public String getC2fecha_creado(){
		return C2fecha_creado;
	}
	public void setC2fecha_creado(String C2fecha_creado){
		this.C2fecha_creado = C2fecha_creado;
	}
	public String getC3fecha_retirado_previsto(){
		return C3fecha_retirado_previsto;
	}
	public void setC3fecha_retirado_previsto(String C3fecha_retirado_previsto){
		this.C3fecha_retirado_previsto = C3fecha_retirado_previsto;
	}
	public String getC4fecha_retirado_real(){
		return C4fecha_retirado_real;
	}
	public void setC4fecha_retirado_real(String C4fecha_retirado_real){
		this.C4fecha_retirado_real = C4fecha_retirado_real;
	}
	public String getC5fecha_devolusion_previsto(){
		return C5fecha_devolusion_previsto;
	}
	public void setC5fecha_devolusion_previsto(String C5fecha_devolusion_previsto){
		this.C5fecha_devolusion_previsto = C5fecha_devolusion_previsto;
	}
	public String getC6fecha_devolusion_real(){
		return C6fecha_devolusion_real;
	}
	public void setC6fecha_devolusion_real(String C6fecha_devolusion_real){
		this.C6fecha_devolusion_real = C6fecha_devolusion_real;
	}
	public double getC7monto_total(){
		return C7monto_total;
	}
	public void setC7monto_total(double C7monto_total){
		this.C7monto_total = C7monto_total;
	}
	public double getC8monto_alquilado_efectivo(){
		return C8monto_alquilado_efectivo;
	}
	public void setC8monto_alquilado_efectivo(double C8monto_alquilado_efectivo){
		this.C8monto_alquilado_efectivo = C8monto_alquilado_efectivo;
	}
	public double getC9monto_alquilado_tarjeta(){
		return C9monto_alquilado_tarjeta;
	}
	public void setC9monto_alquilado_tarjeta(double C9monto_alquilado_tarjeta){
		this.C9monto_alquilado_tarjeta = C9monto_alquilado_tarjeta;
	}
	public double getC10monto_alquilado_transferencia(){
		return C10monto_alquilado_transferencia;
	}
	public void setC10monto_alquilado_transferencia(double C10monto_alquilado_transferencia){
		this.C10monto_alquilado_transferencia = C10monto_alquilado_transferencia;
	}
	public double getC11monto_delivery(){
		return C11monto_delivery;
	}
	public void setC11monto_delivery(double C11monto_delivery){
		this.C11monto_delivery = C11monto_delivery;
	}
	public String getC12forma_pago(){
		return C12forma_pago;
	}
	public void setC12forma_pago(String C12forma_pago){
		this.C12forma_pago = C12forma_pago;
	}
	public String getC13condicion(){
		return C13condicion;
	}
	public void setC13condicion(String C13condicion){
		this.C13condicion = C13condicion;
	}
	public boolean getC14alquiler_retirado(){
		return C14alquiler_retirado;
	}
	public void setC14alquiler_retirado(boolean C14alquiler_retirado){
		this.C14alquiler_retirado = C14alquiler_retirado;
	}
	public boolean getC15alquiler_devolusion(){
		return C15alquiler_devolusion;
	}
	public void setC15alquiler_devolusion(boolean C15alquiler_devolusion){
		this.C15alquiler_devolusion = C15alquiler_devolusion;
	}
	public String getC16direccion_alquiler(){
		return C16direccion_alquiler;
	}
	public void setC16direccion_alquiler(String C16direccion_alquiler){
		this.C16direccion_alquiler = C16direccion_alquiler;
	}
	public String getC17observacion(){
		return C17observacion;
	}
	public void setC17observacion(String C17observacion){
		this.C17observacion = C17observacion;
	}
	public String getC18estado(){
		return C18estado;
	}
	public void setC18estado(String C18estado){
		this.C18estado = C18estado;
	}
	public int getC19fk_idcliente(){
		return C19fk_idcliente;
	}
	public void setC19fk_idcliente(int C19fk_idcliente){
		this.C19fk_idcliente = C19fk_idcliente;
	}
	public int getC20fk_identregador(){
		return C20fk_identregador;
	}
	public void setC20fk_identregador(int C20fk_identregador){
		this.C20fk_identregador = C20fk_identregador;
	}
	public String toString() {
		return "venta_alquiler(" + ",idventa_alquiler=" + C1idventa_alquiler + " ,fecha_creado=" + C2fecha_creado + " ,fecha_retirado_previsto=" + C3fecha_retirado_previsto + " ,fecha_retirado_real=" + C4fecha_retirado_real + " ,fecha_devolusion_previsto=" + C5fecha_devolusion_previsto + " ,fecha_devolusion_real=" + C6fecha_devolusion_real + " ,monto_total=" + C7monto_total + " ,monto_alquilado_efectivo=" + C8monto_alquilado_efectivo + " ,monto_alquilado_tarjeta=" + C9monto_alquilado_tarjeta + " ,monto_alquilado_transferencia=" + C10monto_alquilado_transferencia + " ,monto_delivery=" + C11monto_delivery + " ,forma_pago=" + C12forma_pago + " ,condicion=" + C13condicion + " ,alquiler_retirado=" + C14alquiler_retirado + " ,alquiler_devolusion=" + C15alquiler_devolusion + " ,direccion_alquiler=" + C16direccion_alquiler + " ,observacion=" + C17observacion + " ,estado=" + C18estado + " ,fk_idcliente=" + C19fk_idcliente + " ,fk_identregador=" + C20fk_identregador + " )";
	}
}
