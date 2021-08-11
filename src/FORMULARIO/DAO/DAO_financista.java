package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.financista;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_financista {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "FINANCISTA GUARDADO CORRECTAMENTE";
    private String mensaje_update = "FINANCISTA MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO financista(idfinancista,nombre,direccion,telefono,descripcion,escredito,saldo_credito,fecha_inicio_credito,dia_limite_credito) VALUES (?,?,?,?,?,?,?,?,?);";
    private String sql_update = "UPDATE financista SET nombre=?,direccion=?,telefono=?,descripcion=?,escredito=?,saldo_credito=?,fecha_inicio_credito=?,dia_limite_credito=? WHERE idfinancista=?;";
//    private String sql_select = "SELECT idfinancista,nombre,direccion,telefono,descripcion,escredito,saldo_credito,fecha_inicio_credito,dia_limite_credito FROM financista order by 1 desc;";
    private String sql_select = "SELECT idfinancista as idc,nombre,descripcion,telefono,\n"
            + "direccion,"
            + "TRIM(to_char(saldo_credito,'999G999G999')) as saldo,\n"
            + "case \n"
            + "when escredito=true then to_char(fecha_inicio_credito + dia_limite_credito,'yyyy-MM-dd') \n"
            + "when escredito=false then 'sin credito' \n"
            + "else 'error' end as fec_limite \n"
            + "FROM financista order by 1 desc;";
    private String sql_cargar = "SELECT idfinancista,nombre,direccion,telefono,descripcion,escredito,saldo_credito,fecha_inicio_credito,dia_limite_credito FROM financista WHERE idfinancista=";
    private String sql_update_saldo = "update financista set saldo_credito=\n"
            + "(select sum(cc.monto_contado - cc.monto_credito) as saldo\n"
            + "from grupo_credito_finanza gcc,credito_finanza cc\n"
            + "where gcc.idgrupo_credito_finanza=cc.fk_idgrupo_credito_finanza\n"
            + "and gcc.estado='ABIERTO'\n"
            + "and cc.estado='EMITIDO'\n"
            + "and gcc.fk_idfinancista=financista.idfinancista) where financista.idfinancista=?;";

    public void insertar_financista(Connection conn, financista fina) {
        fina.setC1idfinancista(eveconn.getInt_ultimoID_mas_uno(conn, fina.getTb_financista(), fina.getId_idfinancista()));
        String titulo = "insertar_financista";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, fina.getC1idfinancista());
            pst.setString(2, fina.getC2nombre());
            pst.setString(3, fina.getC3direccion());
            pst.setString(4, fina.getC4telefono());
            pst.setString(5, fina.getC5descripcion());
            pst.setBoolean(6, fina.getC6escredito());
            pst.setDouble(7, fina.getC7saldo_credito());
            pst.setDate(8, evefec.getDateSQL_sistema());
            pst.setInt(9, fina.getC9dia_limite_credito());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + fina.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + fina.toString(), titulo);
        }
    }

    public void update_financista(Connection conn, financista fina) {
        String titulo = "update_financista";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setString(1, fina.getC2nombre());
            pst.setString(2, fina.getC3direccion());
            pst.setString(3, fina.getC4telefono());
            pst.setString(4, fina.getC5descripcion());
            pst.setBoolean(5, fina.getC6escredito());
            pst.setDouble(6, fina.getC7saldo_credito());
            pst.setDate(7, evefec.getDateSQL_sistema());
            pst.setInt(8, fina.getC9dia_limite_credito());
            pst.setInt(9, fina.getC1idfinancista());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + fina.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + fina.toString(), titulo);
        }
    }

    public void cargar_financista(Connection conn, financista fina, int id) {
        String titulo = "Cargar_financista";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + id, titulo);
            if (rs.next()) {
                fina.setC1idfinancista(rs.getInt(1));
                fina.setC2nombre(rs.getString(2));
                fina.setC3direccion(rs.getString(3));
                fina.setC4telefono(rs.getString(4));
                fina.setC5descripcion(rs.getString(5));
                fina.setC6escredito(rs.getBoolean(6));
                fina.setC7saldo_credito(rs.getDouble(7));
                fina.setC8fecha_inicio_credito(rs.getString(8));
                fina.setC9dia_limite_credito(rs.getInt(9));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + fina.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + fina.toString(), titulo);
        }
    }

    public void actualizar_tabla_financista(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_financista(tbltabla);
    }

    public void ancho_tabla_financista(JTable tbltabla) {
        int Ancho[] = {10, 25, 25, 10, 20, 10, 10};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }

    public void update_finanza_saldo_credito(Connection conn, financista cli) {
        String titulo = "update_finanza_saldo_credito";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update_saldo);
            pst.setInt(1, cli.getC1idfinancista());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update_saldo + "\n" + cli.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, false);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update_saldo + "\n" + cli.toString(), titulo);
        }
    }
//    public void update_cliente_saldo_credito(Connection conn, financista cli) {
//        String titulo = "update_cliente_saldo_credito";
//        PreparedStatement pst = null;
//        try {
//            pst = conn.prepareStatement(sql_update_saldo);
//            pst.setInt(1, cli.getC1idfinancista());
//            pst.execute();
//            pst.close();
//            evemen.Imprimir_serial_sql(sql_update_saldo + "\n" + cli.toString(), titulo);
//            evemen.modificado_correcto(mensaje_update, false);
//        } catch (Exception e) {
//            evemen.mensaje_error(e, sql_update_saldo + "\n" + cli.toString(), titulo);
//        }
//    }
}
