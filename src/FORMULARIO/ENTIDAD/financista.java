	package FORMULARIO.ENTIDAD;
public class financista {

    /**
     * @return the C1idfinancista_global
     */
    public static int getC1idfinancista_global() {
        return C1idfinancista_global;
    }

    /**
     * @param aC1idfinancista_global the C1idfinancista_global to set
     */
    public static void setC1idfinancista_global(int aC1idfinancista_global) {
        C1idfinancista_global = aC1idfinancista_global;
    }

//---------------DECLARAR VARIABLES---------------
private int C1idfinancista;
private String C2nombre;
private String C3direccion;
private String C4telefono;
private String C5descripcion;
private boolean C6escredito;
private double C7saldo_credito;
private String C8fecha_inicio_credito;
private int C9dia_limite_credito;
private static String nom_tabla;
private static String nom_idtabla;
private static int C1idfinancista_global;
//---------------TABLA-ID---------------
	public financista() {
		setTb_financista("financista");
		setId_idfinancista("idfinancista");
	}
	public static String getTb_financista(){
		return nom_tabla;
	}
	public static void setTb_financista(String nom_tabla){
		financista.nom_tabla = nom_tabla;
	}
	public static String getId_idfinancista(){
		return nom_idtabla;
	}
	public static void setId_idfinancista(String nom_idtabla){
		financista.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idfinancista(){
		return C1idfinancista;
	}
	public void setC1idfinancista(int C1idfinancista){
		this.C1idfinancista = C1idfinancista;
	}
	public String getC2nombre(){
		return C2nombre;
	}
	public void setC2nombre(String C2nombre){
		this.C2nombre = C2nombre;
	}
	public String getC3direccion(){
		return C3direccion;
	}
	public void setC3direccion(String C3direccion){
		this.C3direccion = C3direccion;
	}
	public String getC4telefono(){
		return C4telefono;
	}
	public void setC4telefono(String C4telefono){
		this.C4telefono = C4telefono;
	}
	public String getC5descripcion(){
		return C5descripcion;
	}
	public void setC5descripcion(String C5descripcion){
		this.C5descripcion = C5descripcion;
	}
	public boolean getC6escredito(){
		return C6escredito;
	}
	public void setC6escredito(boolean C6escredito){
		this.C6escredito = C6escredito;
	}
	public double getC7saldo_credito(){
		return C7saldo_credito;
	}
	public void setC7saldo_credito(double C7saldo_credito){
		this.C7saldo_credito = C7saldo_credito;
	}
	public String getC8fecha_inicio_credito(){
		return C8fecha_inicio_credito;
	}
	public void setC8fecha_inicio_credito(String C8fecha_inicio_credito){
		this.C8fecha_inicio_credito = C8fecha_inicio_credito;
	}
	public int getC9dia_limite_credito(){
		return C9dia_limite_credito;
	}
	public void setC9dia_limite_credito(int C9dia_limite_credito){
		this.C9dia_limite_credito = C9dia_limite_credito;
	}
	public String toString() {
		return "financista(" + ",idfinancista=" + C1idfinancista + " ,nombre=" + C2nombre + " ,direccion=" + C3direccion + " ,telefono=" + C4telefono + " ,descripcion=" + C5descripcion + " ,escredito=" + C6escredito + " ,saldo_credito=" + C7saldo_credito + " ,fecha_inicio_credito=" + C8fecha_inicio_credito + " ,dia_limite_credito=" + C9dia_limite_credito + " )";
	}
}
