package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.grupo_credito_finanza;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_grupo_credito_finanza {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "GRUPO_CREDITO_FINANZA GUARDADO CORRECTAMENTE";
    private String mensaje_update = "GRUPO_CREDITO_FINANZA MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO grupo_credito_finanza(idgrupo_credito_finanza,fecha_inicio,fecha_fin,estado,fk_idfinancista) VALUES (?,?,?,?,?);";
    private String sql_update = "UPDATE grupo_credito_finanza SET fecha_inicio=?,fecha_fin=?,estado=?,fk_idfinancista=? WHERE idgrupo_credito_finanza=?;";
    private String sql_select = "SELECT idgrupo_credito_finanza,fecha_inicio,fecha_fin,estado,fk_idfinancista FROM grupo_credito_finanza order by 1 desc;";
    private String sql_cargar = "SELECT idgrupo_credito_finanza,fecha_inicio,fecha_fin,estado,fk_idfinancista FROM grupo_credito_finanza WHERE idgrupo_credito_finanza=";
private String sql_cargar_id = "SELECT idgrupo_credito_finanza "
            + "FROM grupo_credito_finanza "
            + "WHERE estado='ABIERTO' and fk_idfinancista=";
    private String sql_update_cerrar = "UPDATE grupo_credito_finanza SET fecha_fin=?,estado=? WHERE idgrupo_credito_finanza=?;";
   private String sql_select_idc = "select gcc.idgrupo_credito_finanza as idgcc,\n"
            + "to_char(gcc.fecha_inicio,'yyyy-MM-dd') as inicio,\n"
            + "to_char(gcc.fecha_fin,'yyyy-MM-dd') as fin,\n"
            + "gcc.estado,cl.nombre,\n"
            + "(select TRIM(to_char(sum(cc.monto_contado),'999G999G999')) \n"
            + "from credito_finanza cc \n"
            + "where cc.estado='EMITIDO'\n"
            + "and cc.fk_idgrupo_credito_finanza=gcc.idgrupo_credito_finanza) as contado,\n"
            + "(select TRIM(to_char(sum(cc.monto_credito),'999G999G999'))  \n"
            + "from credito_finanza cc \n"
            + "where cc.estado='EMITIDO'\n"
            + "and cc.fk_idgrupo_credito_finanza=gcc.idgrupo_credito_finanza) as credito,\n"
            + "(select TRIM(to_char(sum(cc.monto_contado - cc.monto_credito),'999G999G999'))  \n"
            + "from credito_finanza cc \n"
            + "where cc.estado='EMITIDO'\n"
            + "and cc.fk_idgrupo_credito_finanza=gcc.idgrupo_credito_finanza) as saldo\n"
            + "from grupo_credito_finanza gcc,financista cl\n"
            + "where gcc.fk_idfinancista=cl.idfinancista\n"
            + "and gcc.fk_idfinancista=";
    public void insertar_grupo_credito_finanza(Connection conn, grupo_credito_finanza gcf) {
        gcf.setC1idgrupo_credito_finanza(eveconn.getInt_ultimoID_mas_uno(conn, gcf.getTb_grupo_credito_finanza(), gcf.getId_idgrupo_credito_finanza()));
        String titulo = "insertar_grupo_credito_finanza";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, gcf.getC1idgrupo_credito_finanza());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setTimestamp(3, evefec.getTimestamp_sistema());
            pst.setString(4, gcf.getC4estado());
            pst.setInt(5, gcf.getC5fk_idfinancista());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + gcf.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, false);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + gcf.toString(), titulo);
        }
    }

    public void update_grupo_credito_finanza(Connection conn, grupo_credito_finanza gcf) {
        String titulo = "update_grupo_credito_finanza";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, gcf.getC4estado());
            pst.setInt(4, gcf.getC5fk_idfinancista());
            pst.setInt(5, gcf.getC1idgrupo_credito_finanza());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + gcf.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + gcf.toString(), titulo);
        }
    }

    public void cargar_grupo_credito_finanza(Connection conn, grupo_credito_finanza gcf, int id) {
        String titulo = "Cargar_grupo_credito_finanza";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + id, titulo);
            if (rs.next()) {
                gcf.setC1idgrupo_credito_finanza(rs.getInt(1));
                gcf.setC2fecha_inicio(rs.getString(2));
                gcf.setC3fecha_fin(rs.getString(3));
                gcf.setC4estado(rs.getString(4));
                gcf.setC5fk_idfinancista(rs.getInt(5));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + gcf.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + gcf.toString(), titulo);
        }
    }

    public void actualizar_tabla_grupo_credito_finanza(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_grupo_credito_finanza(tbltabla);
    }

    public void ancho_tabla_grupo_credito_finanza(JTable tbltabla) {
        int Ancho[] = {20, 20, 20, 20, 20};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
    public void cargar_grupo_credito_finanza_id(Connection conn, grupo_credito_finanza gcc, int fk_idfinanza) {
        String titulo = "cargar_grupo_credito_finanza_id";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar_id + fk_idfinanza, titulo);
            if (rs.next()) {
                gcc.setC1idgrupo_credito_finanza(rs.getInt(1));
                evemen.Imprimir_serial_sql(sql_cargar_id + "\n" + gcc.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar_id + "\n" + gcc.toString(), titulo);
        }
    }
    public void cerrar_grupo_credito_finanza(Connection conn, grupo_credito_finanza gcc) {
        String titulo = "cerrar_grupo_credito_finanza";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update_cerrar);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, gcc.getC4estado());
            pst.setInt(3, gcc.getC1idgrupo_credito_finanza());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update_cerrar + "\n" + gcc.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, false);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update_cerrar + "\n" + gcc.toString(), titulo);
        }
    }
    public void actualizar_tabla_grupo_credito_finanza_idc(Connection conn, JTable tbltabla,int idfinancista) {
        eveconn.Select_cargar_jtable(conn, sql_select_idc+idfinancista+" order by 1 desc", tbltabla);
        ancho_tabla_grupo_credito_finanza_idc(tbltabla);
    }
     public void ancho_tabla_grupo_credito_finanza_idc(JTable tbltabla) {
        int Ancho[] = {6, 12, 12,10, 30,10,10,10 };
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
}
