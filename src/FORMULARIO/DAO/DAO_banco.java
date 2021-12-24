package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.banco;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_banco {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "BANCO GUARDADO CORRECTAMENTE";
    private String mensaje_update = "BANCO MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO banco(idbanco,nombre) VALUES (?,?);";
    private String sql_update = "UPDATE banco SET nombre=? WHERE idbanco=?;";
    private String sql_select = "SELECT idbanco,nombre FROM banco order by 1 desc;";
    private String sql_cargar = "SELECT idbanco,nombre FROM banco WHERE idbanco=";

    public void insertar_banco(Connection conn, banco ban) {
        ban.setC1idbanco(eveconn.getInt_ultimoID_mas_uno(conn, ban.getTb_banco(), ban.getId_idbanco()));
        String titulo = "insertar_banco";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, ban.getC1idbanco());
            pst.setString(2, ban.getC2nombre());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + ban.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + ban.toString(), titulo);
        }
    }

    public void update_banco(Connection conn, banco ban) {
        String titulo = "update_banco";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setString(1, ban.getC2nombre());
            pst.setInt(2, ban.getC1idbanco());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + ban.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + ban.toString(), titulo);
        }
    }

    public void cargar_banco(Connection conn, banco ban, int id) {
        String titulo = "Cargar_banco";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + id, titulo);
            if (rs.next()) {
                ban.setC1idbanco(rs.getInt(1));
                ban.setC2nombre(rs.getString(2));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + ban.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + ban.toString(), titulo);
        }
    }

    public void actualizar_tabla_banco(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_banco(tbltabla);
    }

    public void ancho_tabla_banco(JTable tbltabla) {
        int Ancho[] = {50, 50};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
}
