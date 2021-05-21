	package FORMULARIO.ENTIDAD;
public class inventario {

//---------------DECLARAR VARIABLES---------------
private int C1idinventario;
private String C2fecha_inicio;
private String C3fecha_fin;
private String C4descripcion;
private double C5total_precio_venta;
private double C6total_precio_compra;
private String C7estado;
private int C8fk_idusuario;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public inventario() {
		setTb_inventario("inventario");
		setId_idinventario("idinventario");
	}
	public static String getTb_inventario(){
		return nom_tabla;
	}
	public static void setTb_inventario(String nom_tabla){
		inventario.nom_tabla = nom_tabla;
	}
	public static String getId_idinventario(){
		return nom_idtabla;
	}
	public static void setId_idinventario(String nom_idtabla){
		inventario.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idinventario(){
		return C1idinventario;
	}
	public void setC1idinventario(int C1idinventario){
		this.C1idinventario = C1idinventario;
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
	public String getC4descripcion(){
		return C4descripcion;
	}
	public void setC4descripcion(String C4descripcion){
		this.C4descripcion = C4descripcion;
	}
	public double getC5total_precio_venta(){
		return C5total_precio_venta;
	}
	public void setC5total_precio_venta(double C5total_precio_venta){
		this.C5total_precio_venta = C5total_precio_venta;
	}
	public double getC6total_precio_compra(){
		return C6total_precio_compra;
	}
	public void setC6total_precio_compra(double C6total_precio_compra){
		this.C6total_precio_compra = C6total_precio_compra;
	}
	public String getC7estado(){
		return C7estado;
	}
	public void setC7estado(String C7estado){
		this.C7estado = C7estado;
	}
	public int getC8fk_idusuario(){
		return C8fk_idusuario;
	}
	public void setC8fk_idusuario(int C8fk_idusuario){
		this.C8fk_idusuario = C8fk_idusuario;
	}
	public String toString() {
		return "inventario(" + ",idinventario=" + C1idinventario + " ,fecha_inicio=" + C2fecha_inicio + " ,fecha_fin=" + C3fecha_fin + " ,descripcion=" + C4descripcion + " ,total_precio_venta=" + C5total_precio_venta + " ,total_precio_compra=" + C6total_precio_compra + " ,estado=" + C7estado + " ,fk_idusuario=" + C8fk_idusuario + " )";
	}
}
