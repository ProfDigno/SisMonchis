/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.VISTA;

import BASEDATO.EvenConexion;
import BASEDATO.LOCAL.ConnPostgres;
import CONFIGURACION.EvenDatosPc;
import Evento.Color.cla_color_pelete;
import Evento.Fecha.EvenFecha;
import Evento.JTextField.EvenJTextField;
import Evento.Jframe.EvenJFRAME;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Utilitario.EvenUtil;
import FORMULARIO.BO.*;
import FORMULARIO.DAO.*;
import FORMULARIO.ENTIDAD.*;
import IMPRESORA_POS.*;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Digno
 */
public class FrmCaja_Cierre_alquiler extends javax.swing.JInternalFrame {

    PosImprimir_Venta posv = new PosImprimir_Venta();
    Connection connLocal = null;
    ConnPostgres cpt = new ConnPostgres();
    EvenJTextField evejtf = new EvenJTextField();
    EvenFecha evefec = new EvenFecha();
    EvenDatosPc evepc = new EvenDatosPc();
    EvenUtil eveut = new EvenUtil();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenConexion eveconn = new EvenConexion();
    usuario usu = new usuario();
    EvenJFRAME evetbl = new EvenJFRAME();
    private caja_cierre_alquilado cjcie = new caja_cierre_alquilado();
    private caja_detalle_alquilado caja = new caja_detalle_alquilado();
    private item_caja_cierre_alquilado iccierre = new item_caja_cierre_alquilado();
    private BO_caja_cierre_alquilado bocjcie = new BO_caja_cierre_alquilado();
    private DAO_caja_cierre_alquilado cjcie_dao = new DAO_caja_cierre_alquilado();
    private DAO_caja_detalle_alquilado cdao = new DAO_caja_detalle_alquilado();
    private DAO_item_caja_cierre_alquilado icidao = new DAO_item_caja_cierre_alquilado();
    PosImprimir_caja_cierre_alquiler poscc = new PosImprimir_caja_cierre_alquiler();
    ArrayList<Integer> array_caja_detalle_abierto = new ArrayList<Integer>();
    cla_color_pelete clacolor = new cla_color_pelete();
    double caja_detalle_SALDO = 0;
    double caja_detalle_DIFERENCIA = 0;
    double caja_detalle_CIERRE;
    private String estado_EMITIDO="EMITIDO";
    private String estado_CERRADO="CERRADO";
    private String estado_ABIERTO="ABIERTO";
    private String estado_ANULADO="ANULADO";
    private String forma_pago_EFECTIVO="EFECTIVO";

    void abrir_formulario() {
        this.setTitle("CAJA CIERRE ALQUILADO");
        connLocal = cpt.getConnPosgres();
        evetbl.centrar_formulario_internalframa(this);
        caja_detalle_cantidad_total(caja.getTabla_origen_caja_abrir(), "monto_apertura_caja", txtcant_caja, jFabrir_caja);
        caja_detalle_cantidad_total(caja.getTabla_origen_venta_alquiler() ,"monto_alquilado_efectivo", txtcant_venta_efectivo, jFtotal_venta_efectivo);
        caja_detalle_cantidad_total(caja.getTabla_origen_venta_alquiler() ,"monto_alquilado_tarjeta", txtcant_venta_tarjeta, jFtotal_venta_tarjeta);
        caja_detalle_cantidad_total(caja.getTabla_origen_venta_alquiler() ,"monto_alquilado_transferencia", txtcant_venta_transferencia, jFtotal_venta_transferencia);
        caja_detalle_cantidad_total(caja.getTabla_origen_recibo() ,"monto_recibo_pago", txtcant_recibo_credito_cliente, jFtotal_recibo_credito_cliente);
        caja_detalle_cantidad_total(caja.getTabla_origen_gasto(), "monto_gasto", txtcant_gasto, jFtotal_gasto);
        caja_detalle_cantidad_total(caja.getTabla_origen_compra_contado(), "monto_compra_contado", txtcant_compra, jFtotal_compra);
        caja_detalle_cantidad_total(caja.getTabla_origen_vale(), "monto_vale", txtcant_vale, jFtotal_vale);
//        caja_detalle_cantidad_total(caja.getTabla_origen_recibo(), "monto_recibo_pago", txtcant_recibo, jFtotal_recibo);
        caja_detalle_SALDO();
        color_formulario();
        ocultar_campos();
        txtmonto_caja_cierre.grabFocus();
    }

    void ocultar_campos() {
           Color color_campo=new Color(254,254,254);
        txtcant_caja.setBackground(color_campo);
        txtcant_compra.setBackground(color_campo);
        txtcant_gasto.setBackground(color_campo);
        txtcant_vale.setBackground(color_campo);
        txtcant_venta_efectivo.setBackground(color_campo);
        jFabrir_caja.setBackground(color_campo);
        jFtotal_venta_efectivo.setBackground(color_campo);
        jFtotal_gasto.setBackground(color_campo);
        jFtotal_compra.setBackground(color_campo);
        jFtotal_vale.setBackground(color_campo);
        jFcaja_detalle_sistema.setBackground(color_campo);
        jFcaja_detalle_DIFERENCIA.setBackground(color_campo);
    }

    void color_formulario() {
        panel_ingreso.setBackground(clacolor.getColor_insertar_primario());
        panel_egreso.setBackground(clacolor.getColor_insertar_secundario());
        panel_resultado.setBackground(clacolor.getColor_referencia());
    }

    void verificar_caja_abierto() {
        int idcaja_cierre = (eveconn.getInt_ultimoID_max(connLocal, cjcie.getTb_caja_cierre_alquilado(), cjcie.getId_idcaja_cierre_alquilado()));
        cjcie.setC1idcaja_cierre_alquilado(idcaja_cierre);
        cjcie_dao.cargar_caja_cierre_alquilado(connLocal, cjcie, idcaja_cierre);
        if (cjcie.getC4estado().equals(estado_CERRADO)) {
            JOptionPane.showMessageDialog(null, "NO HAY CAJA ABIERTA SE DEBE ABRIR UNO NUEVO");
            this.dispose();
        }
    }

    void cargar_datos_caja_cierre() {
        cjcie.setC4estado(estado_ABIERTO);
        cjcie.setC5fk_idusuario(usu.getGlobal_idusuario());
    }

    void cargar_datos_caja_detalle(double saldo_cierre) {
        cdao.limpiar_caja_detalle_alquilado(caja);
        caja.setC3descripcion("(VENTA-ALQUILER) CAJA CERRAR:");
        caja.setC4tabla_origen(caja.getTabla_origen_caja_cerrar());
        caja.setC5estado(estado_EMITIDO);
        caja.setC17monto_cierre_caja(saldo_cierre);
        cdao.insertar_caja_detalle_alquilado(connLocal, caja);
    }

    void boton_caja_cierre() {
        if (txtmonto_caja_cierre.getText().trim().length() > 0) {
            insertar_item_caja_cierre(caja_detalle_CIERRE);
            int idcaja_cierre = (eveconn.getInt_ultimoID_max(connLocal, cjcie.getTb_caja_cierre_alquilado(), cjcie.getId_idcaja_cierre_alquilado()));
            poscc.boton_imprimir_pos_caja_cierre(connLocal, idcaja_cierre);
            JOptionPane.showMessageDialog(null, "EL SISTEMA SE VA CERRAR", "CERRAR", JOptionPane.WARNING_MESSAGE);
            System.exit(0);
//            this.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "CARGAR UN MONTO TOTAL DE LA CAJA");
            txtmonto_caja_cierre.grabFocus();
        }
    }

    void caja_detalle_cantidad_total(String origen_tabla, String campo_total, JTextField txtcantidad, JFormattedTextField jftotal) {
        String titulo = "caja_detalle_cantidad_total";
        String sql = "select count(*) as cantidad,sum(" + campo_total + ") as total\n"
                + " from caja_detalle_alquilado c \n"
                + "where c.cierre='A' and c.estado!='"+estado_ANULADO+"' \n"
                + "and " + campo_total + ">0 "
                + "and c.tabla_origen='" + origen_tabla + "'";
        try {
            ResultSet rs = eveconn.getResulsetSQL(connLocal, sql, titulo);
            if (rs.next()) {
                String cantidad = rs.getString("cantidad");
                txtcantidad.setText(cantidad);
                int total = rs.getInt("total");
                jftotal.setValue(total);
            }
        } catch (SQLException e) {
            evemen.mensaje_error(e, sql, titulo);
        }
    }

    void caja_detalle_SALDO() {
        String titulo = "caja_detalle_cantidad_total";
        String sql = "select ((sum("
                + "monto_alquilado_efectivo+"
                + "monto_alquilado_tarjeta+"
                + "monto_alquilado_transferencia+"
                + "monto_apertura_caja+"
                + "monto_recibo_pago))-"
                + "(sum("
                + "monto_gasto+"
                + "monto_compra_contado+"
                + "monto_vale))) as saldo \n"
                + "from caja_detalle_alquilado where cierre='A' and estado!='"+estado_ANULADO+"' ";
        try {
            ResultSet rs = eveconn.getResulsetSQL(connLocal, sql, titulo);
            if (rs.next()) {
                double saldo = rs.getDouble("saldo");
                jFcaja_detalle_sistema.setValue(saldo);
                caja_detalle_SALDO = saldo;
                if (caja_detalle_SALDO < 0) {
                    jFcaja_detalle_sistema.setBackground(Color.red);
                } else {
                    jFcaja_detalle_sistema.setBackground(Color.green);
                }
            }
        } catch (SQLException e) {
            evemen.mensaje_error(e, sql, titulo);
        }
    }

    void calcular_diferencia() {
        caja_detalle_CIERRE = evejtf.getDouble_format_nro_entero(txtmonto_caja_cierre);
        if (usu.getGlobal_nivel().equals("ADMIN")) {
            
            caja_detalle_DIFERENCIA = caja_detalle_CIERRE - caja_detalle_SALDO;
            jFcaja_detalle_DIFERENCIA.setValue(caja_detalle_DIFERENCIA);
            if (caja_detalle_DIFERENCIA < 0) {
                jFcaja_detalle_DIFERENCIA.setBackground(Color.red);
            } else {
                jFcaja_detalle_DIFERENCIA.setBackground(Color.green);
            }
        }
    }

    void cargar_arrayList_caja_abierto() {
        array_caja_detalle_abierto.clear();
        String titulo = "cargar_vector_caja_abierto";
        String sql = "select idcaja_detalle_alquilado "
                + "from caja_detalle_alquilado "
                + "where cierre='A' "
                + "order by idcaja_detalle_alquilado asc";
        try {
            ResultSet rs = eveconn.getResulsetSQL(connLocal, sql, titulo);
            while (rs.next()) {
                array_caja_detalle_abierto.add(rs.getInt(1));
            }
        } catch (SQLException e) {
            evemen.mensaje_error(e, sql, titulo);
        }
        System.out.println("total array=" + array_caja_detalle_abierto.size());

    }

    void insertar_item_caja_cierre(double saldo_cierre) {
        String titulo = "insertar_caja_cierre";
        try {
            if (connLocal.getAutoCommit()) {
                connLocal.setAutoCommit(false);
            }
            int idcaja_cierre = (eveconn.getInt_ultimoID_max(connLocal, cjcie.getTb_caja_cierre_alquilado(), cjcie.getId_idcaja_cierre_alquilado()));
            cargar_datos_caja_detalle(saldo_cierre);
            cargar_arrayList_caja_abierto();
            iccierre.setC2fk_idcaja_cierre_alquilado(idcaja_cierre);
            for (int fila = 0; fila < array_caja_detalle_abierto.size(); fila++) {
                iccierre.setC3fk_idcaja_detalle_alquilado(array_caja_detalle_abierto.get(fila));
                icidao.insertar_item_caja_cierre_alquilado(connLocal, iccierre);
            }
            cjcie.setC1idcaja_cierre_alquilado(idcaja_cierre);
            cjcie.setC4estado(estado_CERRADO);
            cjcie_dao.update_caja_cierre_alquilado(connLocal, cjcie);
            cdao.update_caja_detalle_CERRARTODO(connLocal);
            connLocal.commit();
        } catch (SQLException e) {
            evemen.mensaje_error(e, cjcie.toString(), titulo);
            try {
                connLocal.rollback();
            } catch (SQLException e1) {
                evemen.Imprimir_serial_sql_error(e1, cjcie.toString(), titulo);
            }
        }
    }

    public FrmCaja_Cierre_alquiler() {
        initComponents();
        abrir_formulario();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel_ingreso = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtcant_venta_efectivo = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jFabrir_caja = new javax.swing.JFormattedTextField();
        jFtotal_venta_efectivo = new javax.swing.JFormattedTextField();
        txtcant_caja = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtcant_venta_tarjeta = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jFtotal_venta_tarjeta = new javax.swing.JFormattedTextField();
        jLabel18 = new javax.swing.JLabel();
        txtcant_venta_transferencia = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jFtotal_venta_transferencia = new javax.swing.JFormattedTextField();
        jLabel20 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txtcant_recibo_credito_cliente = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jFtotal_recibo_credito_cliente = new javax.swing.JFormattedTextField();
        panel_egreso = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtcant_gasto = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtcant_compra = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtcant_vale = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jFtotal_gasto = new javax.swing.JFormattedTextField();
        jFtotal_compra = new javax.swing.JFormattedTextField();
        jFtotal_vale = new javax.swing.JFormattedTextField();
        jLabel15 = new javax.swing.JLabel();
        txtcant_recibo = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jFtotal_recibo = new javax.swing.JFormattedTextField();
        panel_resultado = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jFcaja_detalle_sistema = new javax.swing.JFormattedTextField();
        txtmonto_caja_cierre = new javax.swing.JTextField();
        jFcaja_detalle_DIFERENCIA = new javax.swing.JFormattedTextField();
        btncaja_cierre = new javax.swing.JButton();
        btnimprimir_ultimo_cierre = new javax.swing.JButton();

        setClosable(true);

        panel_ingreso.setBackground(new java.awt.Color(102, 153, 255));
        panel_ingreso.setBorder(javax.swing.BorderFactory.createTitledBorder("INGRESO"));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("ABRIR CAJA:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("CANT. VENTA:");

        txtcant_venta_efectivo.setEditable(false);
        txtcant_venta_efectivo.setBackground(new java.awt.Color(204, 204, 204));
        txtcant_venta_efectivo.setFont(new java.awt.Font("Stencil", 0, 18)); // NOI18N
        txtcant_venta_efectivo.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("VENTA EFECTIVO:");

        jFabrir_caja.setEditable(false);
        jFabrir_caja.setBackground(new java.awt.Color(204, 204, 204));
        jFabrir_caja.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFabrir_caja.setFont(new java.awt.Font("Stencil", 0, 18)); // NOI18N

        jFtotal_venta_efectivo.setEditable(false);
        jFtotal_venta_efectivo.setBackground(new java.awt.Color(204, 204, 204));
        jFtotal_venta_efectivo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_venta_efectivo.setFont(new java.awt.Font("Stencil", 0, 18)); // NOI18N
        jFtotal_venta_efectivo.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        jFtotal_venta_efectivo.setSelectionColor(new java.awt.Color(0, 0, 0));

        txtcant_caja.setEditable(false);
        txtcant_caja.setBackground(new java.awt.Color(204, 204, 204));
        txtcant_caja.setFont(new java.awt.Font("Stencil", 0, 18)); // NOI18N
        txtcant_caja.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setText("CANT. VENTA:");

        txtcant_venta_tarjeta.setEditable(false);
        txtcant_venta_tarjeta.setBackground(new java.awt.Color(204, 204, 204));
        txtcant_venta_tarjeta.setFont(new java.awt.Font("Stencil", 0, 18)); // NOI18N
        txtcant_venta_tarjeta.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setText("VENTA TARJETA:");

        jFtotal_venta_tarjeta.setEditable(false);
        jFtotal_venta_tarjeta.setBackground(new java.awt.Color(204, 204, 204));
        jFtotal_venta_tarjeta.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_venta_tarjeta.setFont(new java.awt.Font("Stencil", 0, 18)); // NOI18N
        jFtotal_venta_tarjeta.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        jFtotal_venta_tarjeta.setSelectionColor(new java.awt.Color(0, 0, 0));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel18.setText("CANT. VENTA:");

        txtcant_venta_transferencia.setEditable(false);
        txtcant_venta_transferencia.setBackground(new java.awt.Color(204, 204, 204));
        txtcant_venta_transferencia.setFont(new java.awt.Font("Stencil", 0, 18)); // NOI18N
        txtcant_venta_transferencia.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel19.setText("VENTA TRANSFE:");

        jFtotal_venta_transferencia.setEditable(false);
        jFtotal_venta_transferencia.setBackground(new java.awt.Color(204, 204, 204));
        jFtotal_venta_transferencia.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_venta_transferencia.setFont(new java.awt.Font("Stencil", 0, 18)); // NOI18N
        jFtotal_venta_transferencia.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        jFtotal_venta_transferencia.setSelectionColor(new java.awt.Color(0, 0, 0));

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel20.setText("ABRIR CAJA:");

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel17.setText("CANT. RECIBO:");

        txtcant_recibo_credito_cliente.setEditable(false);
        txtcant_recibo_credito_cliente.setBackground(new java.awt.Color(204, 204, 204));
        txtcant_recibo_credito_cliente.setFont(new java.awt.Font("Stencil", 0, 18)); // NOI18N
        txtcant_recibo_credito_cliente.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel21.setText("TOTAL RECIBO:");

        jFtotal_recibo_credito_cliente.setEditable(false);
        jFtotal_recibo_credito_cliente.setBackground(new java.awt.Color(204, 204, 204));
        jFtotal_recibo_credito_cliente.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_recibo_credito_cliente.setFont(new java.awt.Font("Stencil", 0, 18)); // NOI18N

        javax.swing.GroupLayout panel_ingresoLayout = new javax.swing.GroupLayout(panel_ingreso);
        panel_ingreso.setLayout(panel_ingresoLayout);
        panel_ingresoLayout.setHorizontalGroup(
            panel_ingresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_ingresoLayout.createSequentialGroup()
                .addGroup(panel_ingresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_ingresoLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(panel_ingresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(panel_ingresoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel17)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_ingresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtcant_recibo_credito_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtcant_venta_transferencia, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtcant_venta_tarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtcant_venta_efectivo, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtcant_caja, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(panel_ingresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_ingresoLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_ingresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel19)
                            .addComponent(jLabel14)
                            .addComponent(jLabel3)
                            .addComponent(jLabel20)))
                    .addGroup(panel_ingresoLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel21)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_ingresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jFabrir_caja)
                    .addComponent(jFtotal_venta_efectivo, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
                    .addComponent(jFtotal_venta_tarjeta)
                    .addComponent(jFtotal_venta_transferencia)
                    .addComponent(jFtotal_recibo_credito_cliente))
                .addContainerGap())
        );
        panel_ingresoLayout.setVerticalGroup(
            panel_ingresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_ingresoLayout.createSequentialGroup()
                .addGroup(panel_ingresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jFabrir_caja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtcant_caja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_ingresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtcant_venta_efectivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jFtotal_venta_efectivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_ingresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14)
                    .addComponent(jFtotal_venta_tarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtcant_venta_tarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_ingresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jLabel19)
                    .addComponent(jFtotal_venta_transferencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtcant_venta_transferencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panel_ingresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(txtcant_recibo_credito_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21)
                    .addComponent(jFtotal_recibo_credito_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        panel_egreso.setBackground(new java.awt.Color(153, 204, 255));
        panel_egreso.setBorder(javax.swing.BorderFactory.createTitledBorder("EGRESO"));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("CANT. GASTO:");

        txtcant_gasto.setEditable(false);
        txtcant_gasto.setBackground(new java.awt.Color(204, 204, 204));
        txtcant_gasto.setFont(new java.awt.Font("Stencil", 0, 18)); // NOI18N
        txtcant_gasto.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("TOTAL GASTO:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("CANT. COMPRA:");

        txtcant_compra.setEditable(false);
        txtcant_compra.setBackground(new java.awt.Color(204, 204, 204));
        txtcant_compra.setFont(new java.awt.Font("Stencil", 0, 18)); // NOI18N
        txtcant_compra.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("TOTAL COMPRA:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("CANT. VALE:");

        txtcant_vale.setEditable(false);
        txtcant_vale.setBackground(new java.awt.Color(204, 204, 204));
        txtcant_vale.setFont(new java.awt.Font("Stencil", 0, 18)); // NOI18N
        txtcant_vale.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText("TOTAL VALE:");

        jFtotal_gasto.setEditable(false);
        jFtotal_gasto.setBackground(new java.awt.Color(204, 204, 204));
        jFtotal_gasto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_gasto.setFont(new java.awt.Font("Stencil", 0, 18)); // NOI18N

        jFtotal_compra.setEditable(false);
        jFtotal_compra.setBackground(new java.awt.Color(204, 204, 204));
        jFtotal_compra.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_compra.setFont(new java.awt.Font("Stencil", 0, 18)); // NOI18N

        jFtotal_vale.setEditable(false);
        jFtotal_vale.setBackground(new java.awt.Color(204, 204, 204));
        jFtotal_vale.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_vale.setFont(new java.awt.Font("Stencil", 0, 18)); // NOI18N

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel15.setText("CANT. RECIBO:");

        txtcant_recibo.setEditable(false);
        txtcant_recibo.setBackground(new java.awt.Color(204, 204, 204));
        txtcant_recibo.setFont(new java.awt.Font("Stencil", 0, 18)); // NOI18N
        txtcant_recibo.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel16.setText("TOTAL RECIBO:");

        jFtotal_recibo.setEditable(false);
        jFtotal_recibo.setBackground(new java.awt.Color(204, 204, 204));
        jFtotal_recibo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_recibo.setFont(new java.awt.Font("Stencil", 0, 18)); // NOI18N

        javax.swing.GroupLayout panel_egresoLayout = new javax.swing.GroupLayout(panel_egreso);
        panel_egreso.setLayout(panel_egresoLayout);
        panel_egresoLayout.setHorizontalGroup(
            panel_egresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_egresoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_egresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_egresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtcant_recibo, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtcant_vale, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtcant_compra, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtcant_gasto, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_egresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(panel_egresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jFtotal_compra)
                    .addComponent(jFtotal_gasto, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jFtotal_recibo)
                    .addComponent(jFtotal_vale, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        panel_egresoLayout.setVerticalGroup(
            panel_egresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_egresoLayout.createSequentialGroup()
                .addGroup(panel_egresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtcant_gasto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jFtotal_gasto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_egresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtcant_compra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jFtotal_compra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_egresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtcant_vale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(jFtotal_vale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_egresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txtcant_recibo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(jFtotal_recibo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 11, Short.MAX_VALUE))
        );

        panel_resultado.setBorder(javax.swing.BorderFactory.createTitledBorder("Resultado"));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel10.setText("SISTEMA:");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel11.setText("CIERRE:");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel12.setText("DIFERENCIA:");

        jFcaja_detalle_sistema.setEditable(false);
        jFcaja_detalle_sistema.setBackground(new java.awt.Color(204, 204, 204));
        jFcaja_detalle_sistema.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFcaja_detalle_sistema.setText("000");
        jFcaja_detalle_sistema.setFont(new java.awt.Font("Stencil", 0, 24)); // NOI18N
        jFcaja_detalle_sistema.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        jFcaja_detalle_sistema.setSelectionColor(new java.awt.Color(0, 0, 0));

        txtmonto_caja_cierre.setFont(new java.awt.Font("Stencil", 0, 24)); // NOI18N
        txtmonto_caja_cierre.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtmonto_caja_cierre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtmonto_caja_cierreActionPerformed(evt);
            }
        });
        txtmonto_caja_cierre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtmonto_caja_cierreKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtmonto_caja_cierreKeyReleased(evt);
            }
        });

        jFcaja_detalle_DIFERENCIA.setBackground(new java.awt.Color(204, 204, 204));
        jFcaja_detalle_DIFERENCIA.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFcaja_detalle_DIFERENCIA.setFont(new java.awt.Font("Stencil", 0, 18)); // NOI18N

        btncaja_cierre.setText("CAJA CIERRE");
        btncaja_cierre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncaja_cierreActionPerformed(evt);
            }
        });

        btnimprimir_ultimo_cierre.setText("IMPRIMIR ULTIMO CIERRE");
        btnimprimir_ultimo_cierre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnimprimir_ultimo_cierreActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_resultadoLayout = new javax.swing.GroupLayout(panel_resultado);
        panel_resultado.setLayout(panel_resultadoLayout);
        panel_resultadoLayout.setHorizontalGroup(
            panel_resultadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_resultadoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_resultadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_resultadoLayout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jFcaja_detalle_sistema, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_resultadoLayout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jFcaja_detalle_DIFERENCIA, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_resultadoLayout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtmonto_caja_cierre, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_resultadoLayout.createSequentialGroup()
                        .addComponent(btnimprimir_ultimo_cierre)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btncaja_cierre, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panel_resultadoLayout.setVerticalGroup(
            panel_resultadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_resultadoLayout.createSequentialGroup()
                .addGroup(panel_resultadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jFcaja_detalle_sistema, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_resultadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtmonto_caja_cierre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_resultadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(jFcaja_detalle_DIFERENCIA, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_resultadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btncaja_cierre, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnimprimir_ultimo_cierre))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_resultado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panel_ingreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(panel_egreso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel_ingreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_egreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_resultado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtmonto_caja_cierreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtmonto_caja_cierreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtmonto_caja_cierreActionPerformed

    private void txtmonto_caja_cierreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtmonto_caja_cierreKeyReleased
        // TODO add your handling code here:
        calcular_diferencia();
    }//GEN-LAST:event_txtmonto_caja_cierreKeyReleased

    private void btncaja_cierreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncaja_cierreActionPerformed
        // TODO add your handling code here:
        boton_caja_cierre();
    }//GEN-LAST:event_btncaja_cierreActionPerformed

    private void txtmonto_caja_cierreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtmonto_caja_cierreKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            boton_caja_cierre();
        }
    }//GEN-LAST:event_txtmonto_caja_cierreKeyPressed

    private void btnimprimir_ultimo_cierreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnimprimir_ultimo_cierreActionPerformed
        // TODO add your handling code here:
        int idcaja_cierre = (eveconn.getInt_ultimoID_max(connLocal, cjcie.getTb_caja_cierre_alquilado(), cjcie.getId_idcaja_cierre_alquilado()));
            poscc.boton_imprimir_pos_caja_cierre(connLocal, idcaja_cierre-1);
    }//GEN-LAST:event_btnimprimir_ultimo_cierreActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btncaja_cierre;
    private javax.swing.JButton btnimprimir_ultimo_cierre;
    private javax.swing.JFormattedTextField jFabrir_caja;
    private javax.swing.JFormattedTextField jFcaja_detalle_DIFERENCIA;
    private javax.swing.JFormattedTextField jFcaja_detalle_sistema;
    private javax.swing.JFormattedTextField jFtotal_compra;
    private javax.swing.JFormattedTextField jFtotal_gasto;
    private javax.swing.JFormattedTextField jFtotal_recibo;
    private javax.swing.JFormattedTextField jFtotal_recibo_credito_cliente;
    private javax.swing.JFormattedTextField jFtotal_vale;
    private javax.swing.JFormattedTextField jFtotal_venta_efectivo;
    private javax.swing.JFormattedTextField jFtotal_venta_tarjeta;
    private javax.swing.JFormattedTextField jFtotal_venta_transferencia;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel panel_egreso;
    private javax.swing.JPanel panel_ingreso;
    private javax.swing.JPanel panel_resultado;
    private javax.swing.JTextField txtcant_caja;
    private javax.swing.JTextField txtcant_compra;
    private javax.swing.JTextField txtcant_gasto;
    private javax.swing.JTextField txtcant_recibo;
    private javax.swing.JTextField txtcant_recibo_credito_cliente;
    private javax.swing.JTextField txtcant_vale;
    private javax.swing.JTextField txtcant_venta_efectivo;
    private javax.swing.JTextField txtcant_venta_tarjeta;
    private javax.swing.JTextField txtcant_venta_transferencia;
    private javax.swing.JTextField txtmonto_caja_cierre;
    // End of variables declaration//GEN-END:variables
}
