package FORMULARIO.ENTIDAD;

public class caja_detalle_alquilado {

    /**
     * @return the C24monto_alquilado_credio
     */
    public double getC24monto_alquilado_credio() {
        return C24monto_alquilado_credio;
    }

    /**
     * @param C24monto_alquilado_credio the C24monto_alquilado_credio to set
     */
    public void setC24monto_alquilado_credio(double C24monto_alquilado_credio) {
        this.C24monto_alquilado_credio = C24monto_alquilado_credio;
    }

//---------------DECLARAR VARIABLES---------------
    private int C1idcaja_detalle_alquilado;
    private String C2fecha_emision;
    private String C3descripcion;
    private String C4tabla_origen;
    private String C5estado;
    private String C6cierre;
    private double C7monto_alquilado_efectivo;
    private double C8monto_alquilado_tarjeta;
    private double C9monto_alquilado_transferencia;
    private double C10monto_recibo_pago;
    private double C11monto_delivery;
    private double C12monto_gasto;
    private double C13monto_vale;
    private double C14monto_compra_contado;
    private double C15monto_compra_credito;
    private double C16monto_apertura_caja;
    private double C17monto_cierre_caja;
    private int C18fk_idgasto;
    private int C19fk_idcompra;
    private int C20fk_idventa_alquiler;
    private int C21fk_idvale;
    private int C22fk_idrecibo_pago_cliente;
    private int C23fk_idusuario;
    private double C24monto_alquilado_credio;
    private String C25forma_pago;
    private static String nom_tabla;
    private static String nom_idtabla;
//---------------TABLA-ID---------------

    public caja_detalle_alquilado() {
        setTb_caja_detalle_alquilado("caja_detalle_alquilado");
        setId_idcaja_detalle_alquilado("idcaja_detalle_alquilado");
    }

    public static String getTb_caja_detalle_alquilado() {
        return nom_tabla;
    }

    public static void setTb_caja_detalle_alquilado(String nom_tabla) {
        caja_detalle_alquilado.nom_tabla = nom_tabla;
    }

    public static String getId_idcaja_detalle_alquilado() {
        return nom_idtabla;
    }

    public static void setId_idcaja_detalle_alquilado(String nom_idtabla) {
        caja_detalle_alquilado.nom_idtabla = nom_idtabla;
    }
//---------------GET-SET-CAMPOS---------------

    public int getC1idcaja_detalle_alquilado() {
        return C1idcaja_detalle_alquilado;
    }

    public void setC1idcaja_detalle_alquilado(int C1idcaja_detalle_alquilado) {
        this.C1idcaja_detalle_alquilado = C1idcaja_detalle_alquilado;
    }

    public String getC2fecha_emision() {
        return C2fecha_emision;
    }

    public void setC2fecha_emision(String C2fecha_emision) {
        this.C2fecha_emision = C2fecha_emision;
    }

    public String getC3descripcion() {
        return C3descripcion;
    }

    public void setC3descripcion(String C3descripcion) {
        this.C3descripcion = C3descripcion;
    }

    public String getC4tabla_origen() {
        return C4tabla_origen;
    }

    public void setC4tabla_origen(String C4tabla_origen) {
        this.C4tabla_origen = C4tabla_origen;
    }

    public String getC5estado() {
        return C5estado;
    }

    public void setC5estado(String C5estado) {
        this.C5estado = C5estado;
    }

    public String getC6cierre() {
        return C6cierre;
    }

    public void setC6cierre(String C6cierre) {
        this.C6cierre = C6cierre;
    }

    public double getC7monto_alquilado_efectivo() {
        return C7monto_alquilado_efectivo;
    }

    public void setC7monto_alquilado_efectivo(double C7monto_alquilado_efectivo) {
        this.C7monto_alquilado_efectivo = C7monto_alquilado_efectivo;
    }

    public double getC8monto_alquilado_tarjeta() {
        return C8monto_alquilado_tarjeta;
    }

    public void setC8monto_alquilado_tarjeta(double C8monto_alquilado_tarjeta) {
        this.C8monto_alquilado_tarjeta = C8monto_alquilado_tarjeta;
    }

    public double getC9monto_alquilado_transferencia() {
        return C9monto_alquilado_transferencia;
    }

    public void setC9monto_alquilado_transferencia(double C9monto_alquilado_transferencia) {
        this.C9monto_alquilado_transferencia = C9monto_alquilado_transferencia;
    }

    public double getC10monto_recibo_pago() {
        return C10monto_recibo_pago;
    }

    public void setC10monto_recibo_pago(double C10monto_recibo_pago) {
        this.C10monto_recibo_pago = C10monto_recibo_pago;
    }

    public double getC11monto_delivery() {
        return C11monto_delivery;
    }

    public void setC11monto_delivery(double C11monto_delivery) {
        this.C11monto_delivery = C11monto_delivery;
    }

    public double getC12monto_gasto() {
        return C12monto_gasto;
    }

    public void setC12monto_gasto(double C12monto_gasto) {
        this.C12monto_gasto = C12monto_gasto;
    }

    public double getC13monto_vale() {
        return C13monto_vale;
    }

    public void setC13monto_vale(double C13monto_vale) {
        this.C13monto_vale = C13monto_vale;
    }

    public double getC14monto_compra_contado() {
        return C14monto_compra_contado;
    }

    public void setC14monto_compra_contado(double C14monto_compra_contado) {
        this.C14monto_compra_contado = C14monto_compra_contado;
    }

    public double getC15monto_compra_credito() {
        return C15monto_compra_credito;
    }

    public void setC15monto_compra_credito(double C15monto_compra_credito) {
        this.C15monto_compra_credito = C15monto_compra_credito;
    }

    public double getC16monto_apertura_caja() {
        return C16monto_apertura_caja;
    }

    public void setC16monto_apertura_caja(double C16monto_apertura_caja) {
        this.C16monto_apertura_caja = C16monto_apertura_caja;
    }

    public double getC17monto_cierre_caja() {
        return C17monto_cierre_caja;
    }

    public void setC17monto_cierre_caja(double C17monto_cierre_caja) {
        this.C17monto_cierre_caja = C17monto_cierre_caja;
    }

    public int getC18fk_idgasto() {
        return C18fk_idgasto;
    }

    public void setC18fk_idgasto(int C18fk_idgasto) {
        this.C18fk_idgasto = C18fk_idgasto;
    }

    public int getC19fk_idcompra() {
        return C19fk_idcompra;
    }

    public void setC19fk_idcompra(int C19fk_idcompra) {
        this.C19fk_idcompra = C19fk_idcompra;
    }

    public int getC20fk_idventa_alquiler() {
        return C20fk_idventa_alquiler;
    }

    public void setC20fk_idventa_alquiler(int C20fk_idventa_alquiler) {
        this.C20fk_idventa_alquiler = C20fk_idventa_alquiler;
    }

    public int getC21fk_idvale() {
        return C21fk_idvale;
    }

    public void setC21fk_idvale(int C21fk_idvale) {
        this.C21fk_idvale = C21fk_idvale;
    }

    public int getC22fk_idrecibo_pago_cliente() {
        return C22fk_idrecibo_pago_cliente;
    }

    public void setC22fk_idrecibo_pago_cliente(int C22fk_idrecibo_pago_cliente) {
        this.C22fk_idrecibo_pago_cliente = C22fk_idrecibo_pago_cliente;
    }

    public int getC23fk_idusuario() {
        return C23fk_idusuario;
    }

    public void setC23fk_idusuario(int C23fk_idusuario) {
        this.C23fk_idusuario = C23fk_idusuario;
    }

    public String getC25forma_pago() {
        return C25forma_pago;
    }

    public void setC25forma_pago(String C25forma_pago) {
        this.C25forma_pago = C25forma_pago;
    }
    

    public String toString() {
        return "caja_detalle_alquilado(" + ",idcaja_detalle_alquilado=" + C1idcaja_detalle_alquilado + " ,fecha_emision=" + C2fecha_emision + " ,descripcion=" + C3descripcion + " ,tabla_origen=" + C4tabla_origen + " ,estado=" + C5estado + " ,cierre=" + C6cierre + " ,monto_alquilado_efectivo=" + C7monto_alquilado_efectivo + " ,monto_alquilado_tarjeta=" + C8monto_alquilado_tarjeta + " ,monto_alquilado_transferencia=" + C9monto_alquilado_transferencia + " ,monto_recibo_pago=" + C10monto_recibo_pago + " ,monto_delivery=" + C11monto_delivery + " ,monto_gasto=" + C12monto_gasto + " ,monto_vale=" + C13monto_vale + " ,monto_compra_contado=" + C14monto_compra_contado + " ,monto_compra_credito=" + C15monto_compra_credito + " ,monto_apertura_caja=" + C16monto_apertura_caja + " ,monto_cierre_caja=" + C17monto_cierre_caja + " ,fk_idgasto=" + C18fk_idgasto + " ,fk_idcompra=" + C19fk_idcompra + " ,fk_idventa_alquiler=" + C20fk_idventa_alquiler + " ,fk_idvale=" + C21fk_idvale + " ,fk_idrecibo_pago_cliente=" + C22fk_idrecibo_pago_cliente + " ,fk_idusuario=" + C23fk_idusuario + " )";
    }

    
}
