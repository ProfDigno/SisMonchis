	package FORMULARIO.ENTIDAD;
public class producto {

    /**
     * @return the C18_unidad
     */
    public String getC18_unidad() {
        return C18_unidad;
    }

    /**
     * @param C18_unidad the C18_unidad to set
     */
    public void setC18_unidad(String C18_unidad) {
        this.C18_unidad = C18_unidad;
    }

    /**
     * @return the C19_categoria
     */
    public String getC19_categoria() {
        return C19_categoria;
    }

    /**
     * @param C19_categoria the C19_categoria to set
     */
    public void setC19_categoria(String C19_categoria) {
        this.C19_categoria = C19_categoria;
    }

    /**
     * @return the C20_marca
     */
    public String getC20_marca() {
        return C20_marca;
    }

    /**
     * @param C20_marca the C20_marca to set
     */
    public void setC20_marca(String C20_marca) {
        this.C20_marca = C20_marca;
    }

//---------------DECLARAR VARIABLES---------------
private int C1idproducto;
private String C2cod_barra;
private String C3nombre;
private double C4precio_venta_minorista;
private double C5precio_venta_mayorista;
private double C6cantidad_mayorista;
private double C7precio_compra;
private double C8stock;
private double C9stock_min;
private boolean C10activar;
private boolean C11venta_mayorista;
private boolean C12promocion;
private String C13ult_venta;
private String C14ult_compra;
private int C15fk_idproducto_unidad;
private int C16fk_idproducto_categoria;
private int C17fk_idproducto_marca;
private String C18_unidad;
private String C19_categoria;
private String C20_marca;
private int C21_aux_cantidad;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public producto() {
		setTb_producto("producto");
		setId_idproducto("idproducto");
	}
	public static String getTb_producto(){
		return nom_tabla;
	}
	public static void setTb_producto(String nom_tabla){
		producto.nom_tabla = nom_tabla;
	}
	public static String getId_idproducto(){
		return nom_idtabla;
	}
	public static void setId_idproducto(String nom_idtabla){
		producto.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idproducto(){
		return C1idproducto;
	}
	public void setC1idproducto(int C1idproducto){
		this.C1idproducto = C1idproducto;
	}
	public String getC2cod_barra(){
		return C2cod_barra;
	}
	public void setC2cod_barra(String C2cod_barra){
		this.C2cod_barra = C2cod_barra;
	}
	public String getC3nombre(){
		return C3nombre;
	}
	public void setC3nombre(String C3nombre){
		this.C3nombre = C3nombre;
	}
	public double getC4precio_venta_minorista(){
		return C4precio_venta_minorista;
	}
	public void setC4precio_venta_minorista(double C4precio_venta_minorista){
		this.C4precio_venta_minorista = C4precio_venta_minorista;
	}
	public double getC5precio_venta_mayorista(){
		return C5precio_venta_mayorista;
	}
	public void setC5precio_venta_mayorista(double C5precio_venta_mayorista){
		this.C5precio_venta_mayorista = C5precio_venta_mayorista;
	}
	public double getC6cantidad_mayorista(){
		return C6cantidad_mayorista;
	}
	public void setC6cantidad_mayorista(double C6cantidad_mayorista){
		this.C6cantidad_mayorista = C6cantidad_mayorista;
	}
	public double getC7precio_compra(){
		return C7precio_compra;
	}
	public void setC7precio_compra(double C7precio_compra){
		this.C7precio_compra = C7precio_compra;
	}
	public double getC8stock(){
		return C8stock;
	}
	public void setC8stock(double C8stock){
		this.C8stock = C8stock;
	}
	public double getC9stock_min(){
		return C9stock_min;
	}
	public void setC9stock_min(double C9stock_min){
		this.C9stock_min = C9stock_min;
	}
	public boolean getC10activar(){
		return C10activar;
	}
	public void setC10activar(boolean C10activar){
		this.C10activar = C10activar;
	}
	public boolean getC11venta_mayorista(){
		return C11venta_mayorista;
	}
	public void setC11venta_mayorista(boolean C11venta_mayorista){
		this.C11venta_mayorista = C11venta_mayorista;
	}
	public boolean getC12promocion(){
		return C12promocion;
	}
	public void setC12promocion(boolean C12promocion){
		this.C12promocion = C12promocion;
	}
	public String getC13ult_venta(){
		return C13ult_venta;
	}
	public void setC13ult_venta(String C13ult_venta){
		this.C13ult_venta = C13ult_venta;
	}
	public String getC14ult_compra(){
		return C14ult_compra;
	}
	public void setC14ult_compra(String C14ult_compra){
		this.C14ult_compra = C14ult_compra;
	}
	public int getC15fk_idproducto_unidad(){
		return C15fk_idproducto_unidad;
	}
	public void setC15fk_idproducto_unidad(int C15fk_idproducto_unidad){
		this.C15fk_idproducto_unidad = C15fk_idproducto_unidad;
	}
	public int getC16fk_idproducto_categoria(){
		return C16fk_idproducto_categoria;
	}
	public void setC16fk_idproducto_categoria(int C16fk_idproducto_categoria){
		this.C16fk_idproducto_categoria = C16fk_idproducto_categoria;
	}
	public int getC17fk_idproducto_marca(){
		return C17fk_idproducto_marca;
	}
	public void setC17fk_idproducto_marca(int C17fk_idproducto_marca){
		this.C17fk_idproducto_marca = C17fk_idproducto_marca;
	}
	public String toString() {
		return "producto(" + ",idproducto=" + C1idproducto + " ,cod_barra=" + C2cod_barra + " ,nombre=" + C3nombre + " ,precio_venta_minorista=" + C4precio_venta_minorista + " ,precio_venta_mayorista=" + C5precio_venta_mayorista + " ,cantidad_mayorista=" + C6cantidad_mayorista + " ,precio_compra=" + C7precio_compra + " ,stock=" + C8stock + " ,stock_min=" + C9stock_min + " ,activar=" + C10activar + " ,venta_mayorista=" + C11venta_mayorista + " ,promocion=" + C12promocion + " ,ult_venta=" + C13ult_venta + " ,ult_compra=" + C14ult_compra + " ,fk_idproducto_unidad=" + C15fk_idproducto_unidad + " ,fk_idproducto_categoria=" + C16fk_idproducto_categoria + " ,fk_idproducto_marca=" + C17fk_idproducto_marca + " )";
	}

    /**
     * @return the C21_aux_cantidad
     */
    public int getC21_aux_cantidad() {
        return C21_aux_cantidad;
    }

    /**
     * @param C21_aux_cantidad the C21_aux_cantidad to set
     */
    public void setC21_aux_cantidad(int C21_aux_cantidad) {
        this.C21_aux_cantidad = C21_aux_cantidad;
    }
}
