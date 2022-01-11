/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.VISTA;

import BASEDATO.EvenConexion;
import BASEDATO.LOCAL.ConnPostgres;
import Evento.Color.cla_color_pelete;
import Evento.Fecha.EvenFecha;
import Evento.JTextField.EvenJTextField;
import Evento.Jframe.EvenJFRAME;
import FILTRO.ClaAuxFiltroVenta;
import FORMULARIO.DAO.DAO_cliente;
import FORMULARIO.DAO.DAO_venta;
import FORMULARIO.DAO.DAO_venta_alquiler;
import FORMULARIO.ENTIDAD.cliente;
import java.awt.event.KeyEvent;
import java.sql.Connection;

/**
 *
 * @author Digno
 */
public class FrmRepVenta_alquiler extends javax.swing.JInternalFrame {

    /**
     * Creates new form FrmRepVenta
     */
    EvenJFRAME evetbl = new EvenJFRAME();
    Connection conn = ConnPostgres.getConnPosgres();
    DAO_venta_alquiler vdao = new DAO_venta_alquiler();
    EvenFecha evefec = new EvenFecha();
    ClaAuxFiltroVenta auxvent = new ClaAuxFiltroVenta();
    EvenJTextField evejtf = new EvenJTextField();
    EvenConexion eveconn = new EvenConexion();
    DAO_cliente cdao = new DAO_cliente();
    cliente clie = new cliente();
    private int fk_idcliente_local;
    cla_color_pelete clacolor = new cla_color_pelete();
    private boolean esfiltro_por_fecha;
    void abrir_formulario() {
        this.setTitle("REPORTE VENTA ALQUILER TODOS");
        evetbl.centrar_formulario_internalframa(this);
        color_formulario();
        reestableser();
        evefec.cargar_combobox_directo(jCfiltro_direco);
        esfiltro_por_fecha=true;
    }
    void color_formulario() {
        panel_reporte_venta.setBackground(clacolor.getColor_insertar_primario());
        panel_fecha.setBackground(clacolor.getColor_insertar_secundario());
        panel_estado.setBackground(clacolor.getColor_insertar_secundario());
        panel_cliente.setBackground(clacolor.getColor_insertar_secundario());
        panel_formapago.setBackground(clacolor.getColor_insertar_secundario());
    }
    String filtro_venta_todos() {
        String filtro = "";
        if ((txtfecha_desde.getText().trim().length() > 0) && (txtfecha_hasta.getText().trim().length() > 0)) {
            String fecdesde = evefec.getString_validar_fecha(txtfecha_desde.getText());
            String fechasta = evefec.getString_validar_fecha(txtfecha_hasta.getText());
            txtfecha_desde.setText(fecdesde);
            txtfecha_hasta.setText(fechasta);
            String filtro_estado = auxvent.filtro_estado_alquiler(jCestado_emitido, jCestado_finalizado, jCestado_usoreserva, jCestado_alquilado, jCestado_devolucion, jCestado_anulado);
            String filtro_formapago=auxvent.forma_pago_alquilado(jCpago_efectivo, jCpago_tarjeta, jCpago_transferencia, jCpago_credito, jCpago_combinado);
            String filtro_fecha = " and date(v.fecha_creado) >= '" + fecdesde + "' and date(v.fecha_creado) <= '" + fechasta + "' \n";
            String filtro_cliente = "";
            
            if (fk_idcliente_local >= 0) {
                filtro_cliente = " and v.fk_idcliente=" + fk_idcliente_local + "\n";
            }
            String filtro_fecha_direc=evefec.getFechaDirecto_combobox(jCfiltro_direco, "v.fecha_creado");
            if(esfiltro_por_fecha){
                filtro_fecha_direc="";
            }else{
                filtro_fecha="";
            }
            filtro = filtro + filtro_fecha;
            filtro = filtro + filtro_fecha_direc;
            filtro = filtro + filtro_estado;
            filtro = filtro + filtro_formapago;
            filtro = filtro + filtro_cliente;
        }
        return filtro;
    }

    private void seleccionar_cargar_filtro() {
        double sumaventa_efectivo = vdao.getDouble_suma_venta(conn,"sumaventa_efectivo", filtro_venta_todos());
        jFtotal_venta_efectivo.setValue(sumaventa_efectivo);
        double sumaventa_tarjeta = vdao.getDouble_suma_venta(conn,"sumaventa_tarjeta", filtro_venta_todos());
        jFtotal_venta_tarjeta.setValue(sumaventa_tarjeta);
        double sumaventa_total = vdao.getDouble_suma_venta(conn,"sumaventa_total", filtro_venta_todos());
        jFtotal_venta_total.setValue(sumaventa_total);
        double sumaventa_transferencia = vdao.getDouble_suma_venta(conn,"sumaventa_transferencia", filtro_venta_todos());
        jFtotal_venta_transferencia.setValue(sumaventa_transferencia);
        double sumaventa_credito = vdao.getDouble_suma_venta(conn,"sumaventa_credito", filtro_venta_todos());
        jFtotal_venta_credito.setValue(sumaventa_credito);
        double cantidad = vdao.getDouble_suma_venta(conn,"cantidad", filtro_venta_todos());
        jFcant_fila.setValue(cantidad);
    }

    void boton_imprimir() {
        if ((txtfecha_desde.getText().trim().length() > 0) && (txtfecha_hasta.getText().trim().length() > 0)) {
            vdao.imprimir_rep_alquiler_todos(conn, filtro_venta_todos());
        }
    }

    void reestableser() {
        txtfecha_desde.setText(evefec.getString_fecha_dia1());
        txtfecha_hasta.setText(evefec.getString_formato_fecha());
        jCestado_emitido.setSelected(true);
        jCestado_finalizado.setSelected(true);
        jCestado_alquilado.setSelected(true);
        jCestado_devolucion.setSelected(true);  
        jCestado_usoreserva.setSelected(true);        
        jCestado_anulado.setSelected(false);
        jCpago_efectivo.setSelected(true);
        jCpago_tarjeta.setSelected(true);
        jCpago_transferencia.setSelected(true);
        txtbucarCliente_nombre.setText(null);
        fk_idcliente_local = -1;
        seleccionar_cargar_filtro();
    }

    void seleccionar_cargar_cliente() {
        fk_idcliente_local = eveconn.getInt_Solo_seleccionar_JLista(conn, jList_cliente, "cliente", clie.getCliente_concat(), "idcliente");
        cdao.cargar_cliente(conn, clie, fk_idcliente_local);
        txtbucarCliente_nombre.setText(clie.getC3nombre());
        seleccionar_cargar_filtro();
    }

    public FrmRepVenta_alquiler() {
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

        panel_reporte_venta = new javax.swing.JPanel();
        panel_fecha = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtfecha_desde = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtfecha_hasta = new javax.swing.JTextField();
        btnbuscar_fecha = new javax.swing.JButton();
        jCfiltro_direco = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        panel_estado = new javax.swing.JPanel();
        jCestado_emitido = new javax.swing.JCheckBox();
        jCestado_finalizado = new javax.swing.JCheckBox();
        jCestado_anulado = new javax.swing.JCheckBox();
        jCestado_devolucion = new javax.swing.JCheckBox();
        jCestado_usoreserva = new javax.swing.JCheckBox();
        jCestado_alquilado = new javax.swing.JCheckBox();
        panel_cliente = new javax.swing.JPanel();
        jList_cliente = new javax.swing.JList<>();
        jLabel3 = new javax.swing.JLabel();
        txtbucarCliente_nombre = new javax.swing.JTextField();
        panel_formapago = new javax.swing.JPanel();
        jCpago_efectivo = new javax.swing.JCheckBox();
        jCpago_tarjeta = new javax.swing.JCheckBox();
        jCpago_combinado = new javax.swing.JCheckBox();
        jCpago_transferencia = new javax.swing.JCheckBox();
        jCpago_credito = new javax.swing.JCheckBox();
        btnimprimir = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jFtotal_venta_efectivo = new javax.swing.JFormattedTextField();
        jFcant_fila = new javax.swing.JFormattedTextField();
        jLabel2 = new javax.swing.JLabel();
        btnreset = new javax.swing.JButton();
        jFtotal_venta_tarjeta = new javax.swing.JFormattedTextField();
        jLabel4 = new javax.swing.JLabel();
        jFtotal_venta_total = new javax.swing.JFormattedTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jFtotal_venta_transferencia = new javax.swing.JFormattedTextField();
        jLabel9 = new javax.swing.JLabel();
        jFtotal_venta_credito = new javax.swing.JFormattedTextField();

        setClosable(true);
        setResizable(true);

        panel_reporte_venta.setBorder(javax.swing.BorderFactory.createTitledBorder("REPORTE VENTA"));

        panel_fecha.setBorder(javax.swing.BorderFactory.createTitledBorder("FECHA"));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Desde:");

        txtfecha_desde.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtfecha_desde.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtfecha_desdeKeyPressed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Hasta:");

        txtfecha_hasta.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtfecha_hasta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtfecha_hastaKeyPressed(evt);
            }
        });

        btnbuscar_fecha.setText("BUSCAR");
        btnbuscar_fecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbuscar_fechaActionPerformed(evt);
            }
        });

        jCfiltro_direco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCfiltro_direcoActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setText("Filtro Directo:");

        javax.swing.GroupLayout panel_fechaLayout = new javax.swing.GroupLayout(panel_fecha);
        panel_fecha.setLayout(panel_fechaLayout);
        panel_fechaLayout.setHorizontalGroup(
            panel_fechaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_fechaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_fechaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_fechaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_fechaLayout.createSequentialGroup()
                        .addComponent(txtfecha_desde, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtfecha_hasta, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnbuscar_fecha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panel_fechaLayout.createSequentialGroup()
                        .addComponent(jCfiltro_direco, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panel_fechaLayout.setVerticalGroup(
            panel_fechaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_fechaLayout.createSequentialGroup()
                .addGroup(panel_fechaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCfiltro_direco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panel_fechaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtfecha_desde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtfecha_hasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnbuscar_fecha))
                .addContainerGap())
        );

        panel_estado.setBorder(javax.swing.BorderFactory.createTitledBorder("ESTADO VENTA"));

        jCestado_emitido.setText("EMITIDO");
        jCestado_emitido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCestado_emitidoActionPerformed(evt);
            }
        });

        jCestado_finalizado.setText("FINALIZADO");
        jCestado_finalizado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCestado_finalizadoActionPerformed(evt);
            }
        });

        jCestado_anulado.setText("ANULADO");
        jCestado_anulado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCestado_anuladoActionPerformed(evt);
            }
        });

        jCestado_devolucion.setText("DEVOLUCION");
        jCestado_devolucion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCestado_devolucionActionPerformed(evt);
            }
        });

        jCestado_usoreserva.setText("USO_RESERVA");
        jCestado_usoreserva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCestado_usoreservaActionPerformed(evt);
            }
        });

        jCestado_alquilado.setText("ALQUILADO");
        jCestado_alquilado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCestado_alquiladoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_estadoLayout = new javax.swing.GroupLayout(panel_estado);
        panel_estado.setLayout(panel_estadoLayout);
        panel_estadoLayout.setHorizontalGroup(
            panel_estadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_estadoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_estadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_estadoLayout.createSequentialGroup()
                        .addComponent(jCestado_emitido)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCestado_alquilado)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCestado_devolucion)
                        .addGap(6, 6, 6)
                        .addComponent(jCestado_usoreserva)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCestado_finalizado))
                    .addComponent(jCestado_anulado))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_estadoLayout.setVerticalGroup(
            panel_estadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_estadoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panel_estadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCestado_emitido)
                    .addComponent(jCestado_usoreserva)
                    .addComponent(jCestado_alquilado)
                    .addComponent(jCestado_devolucion)
                    .addComponent(jCestado_finalizado))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCestado_anulado))
        );

        panel_cliente.setBorder(javax.swing.BorderFactory.createTitledBorder("POR CLIENTE"));

        jList_cliente.setBackground(new java.awt.Color(204, 204, 255));
        jList_cliente.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jList_cliente.setSelectionBackground(new java.awt.Color(255, 51, 51));
        jList_cliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jList_clienteMouseReleased(evt);
            }
        });
        jList_cliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jList_clienteKeyPressed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("CLI:");

        txtbucarCliente_nombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtbucarCliente_nombreKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbucarCliente_nombreKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout panel_clienteLayout = new javax.swing.GroupLayout(panel_cliente);
        panel_cliente.setLayout(panel_clienteLayout);
        panel_clienteLayout.setHorizontalGroup(
            panel_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_clienteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jList_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel_clienteLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtbucarCliente_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_clienteLayout.setVerticalGroup(
            panel_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_clienteLayout.createSequentialGroup()
                .addGroup(panel_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtbucarCliente_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jList_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 26, Short.MAX_VALUE))
        );

        panel_formapago.setBorder(javax.swing.BorderFactory.createTitledBorder("FORMA PAGO"));

        jCpago_efectivo.setText("EFECTIVO");
        jCpago_efectivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCpago_efectivoActionPerformed(evt);
            }
        });

        jCpago_tarjeta.setText("TARJETA");
        jCpago_tarjeta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCpago_tarjetaActionPerformed(evt);
            }
        });

        jCpago_combinado.setText("COMBINADO");
        jCpago_combinado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCpago_combinadoActionPerformed(evt);
            }
        });

        jCpago_transferencia.setText("TRANSFERENCIA");
        jCpago_transferencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCpago_transferenciaActionPerformed(evt);
            }
        });

        jCpago_credito.setText("CREDITO");
        jCpago_credito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCpago_creditoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_formapagoLayout = new javax.swing.GroupLayout(panel_formapago);
        panel_formapago.setLayout(panel_formapagoLayout);
        panel_formapagoLayout.setHorizontalGroup(
            panel_formapagoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_formapagoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jCpago_efectivo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCpago_tarjeta)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCpago_transferencia)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCpago_credito)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jCpago_combinado))
        );
        panel_formapagoLayout.setVerticalGroup(
            panel_formapagoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_formapagoLayout.createSequentialGroup()
                .addGroup(panel_formapagoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCpago_efectivo)
                    .addComponent(jCpago_tarjeta)
                    .addComponent(jCpago_combinado)
                    .addComponent(jCpago_transferencia)
                    .addComponent(jCpago_credito))
                .addGap(0, 15, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panel_reporte_ventaLayout = new javax.swing.GroupLayout(panel_reporte_venta);
        panel_reporte_venta.setLayout(panel_reporte_ventaLayout);
        panel_reporte_ventaLayout.setHorizontalGroup(
            panel_reporte_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_estado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panel_fecha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panel_cliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panel_formapago, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panel_reporte_ventaLayout.setVerticalGroup(
            panel_reporte_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_reporte_ventaLayout.createSequentialGroup()
                .addComponent(panel_fecha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_estado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_formapago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        btnimprimir.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnimprimir.setText("IMPRIMIR");
        btnimprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnimprimirActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("ALQUILER EFECTIVO:");

        jFtotal_venta_efectivo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        jFtotal_venta_efectivo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_venta_efectivo.setFont(new java.awt.Font("Stencil", 0, 24)); // NOI18N

        jFcant_fila.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        jFcant_fila.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFcant_fila.setFont(new java.awt.Font("Stencil", 0, 18)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("CANTIDAD FILA:");

        btnreset.setText("RESET");
        btnreset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnresetActionPerformed(evt);
            }
        });

        jFtotal_venta_tarjeta.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        jFtotal_venta_tarjeta.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_venta_tarjeta.setFont(new java.awt.Font("Stencil", 0, 24)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("ALQUILER TARJETA:");

        jFtotal_venta_total.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        jFtotal_venta_total.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_venta_total.setFont(new java.awt.Font("Stencil", 0, 36)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel7.setText("TOTAL VENTA TOTAL:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel8.setText("ALQUILER TRANSFERENCIA:");

        jFtotal_venta_transferencia.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        jFtotal_venta_transferencia.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_venta_transferencia.setFont(new java.awt.Font("Stencil", 0, 24)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel9.setText("ALQUILER CREDITO:");

        jFtotal_venta_credito.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        jFtotal_venta_credito.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_venta_credito.setFont(new java.awt.Font("Stencil", 0, 24)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnimprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panel_reporte_venta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 22, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jFtotal_venta_efectivo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jFtotal_venta_tarjeta, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jFtotal_venta_transferencia, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jFtotal_venta_credito, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jFtotal_venta_total, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(486, 486, 486)
                        .addComponent(btnreset)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFcant_fila, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFtotal_venta_efectivo, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFtotal_venta_tarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFtotal_venta_transferencia, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFtotal_venta_credito, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFtotal_venta_total, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jFcant_fila, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(btnreset))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnimprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panel_reporte_venta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtfecha_desdeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfecha_desdeKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtfecha_hasta.grabFocus();
        }
    }//GEN-LAST:event_txtfecha_desdeKeyPressed

    private void txtfecha_hastaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfecha_hastaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            //            actualizar_gasto(2);
            txtfecha_desde.grabFocus();
        }
    }//GEN-LAST:event_txtfecha_hastaKeyPressed

    private void btnimprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnimprimirActionPerformed
        // TODO add your handling code here:
        boton_imprimir();
    }//GEN-LAST:event_btnimprimirActionPerformed

    private void jList_clienteMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList_clienteMouseReleased
        // TODO add your handling code here:
        seleccionar_cargar_cliente();
    }//GEN-LAST:event_jList_clienteMouseReleased

    private void jList_clienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jList_clienteKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            seleccionar_cargar_cliente();
            seleccionar_cargar_filtro();
        }
    }//GEN-LAST:event_jList_clienteKeyPressed

    private void txtbucarCliente_nombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbucarCliente_nombreKeyPressed
        // TODO add your handling code here:
        evejtf.seleccionar_lista(evt, jList_cliente);
//        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
//            txtbucarCliente_telefono.grabFocus();
//        }
    }//GEN-LAST:event_txtbucarCliente_nombreKeyPressed

    private void txtbucarCliente_nombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbucarCliente_nombreKeyReleased
        // TODO add your handling code here:
        eveconn.buscar_cargar_Jlista(conn, txtbucarCliente_nombre, jList_cliente, "cliente", "nombre", clie.getCliente_mostrar(), 4);
    }//GEN-LAST:event_txtbucarCliente_nombreKeyReleased

    private void jCestado_emitidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCestado_emitidoActionPerformed
        // TODO add your handling code here:
        seleccionar_cargar_filtro();
    }//GEN-LAST:event_jCestado_emitidoActionPerformed

    private void jCestado_finalizadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCestado_finalizadoActionPerformed
        // TODO add your handling code here:
        seleccionar_cargar_filtro();
    }//GEN-LAST:event_jCestado_finalizadoActionPerformed

    private void jCestado_anuladoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCestado_anuladoActionPerformed
        // TODO add your handling code here:
        seleccionar_cargar_filtro();
    }//GEN-LAST:event_jCestado_anuladoActionPerformed

    private void btnresetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnresetActionPerformed
        // TODO add your handling code here:
        reestableser();
    }//GEN-LAST:event_btnresetActionPerformed

    private void btnbuscar_fechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbuscar_fechaActionPerformed
        // TODO add your handling code here:
        esfiltro_por_fecha=true;
        seleccionar_cargar_filtro();
    }//GEN-LAST:event_btnbuscar_fechaActionPerformed

    private void jCpago_efectivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCpago_efectivoActionPerformed
        // TODO add your handling code here:
        seleccionar_cargar_filtro();
    }//GEN-LAST:event_jCpago_efectivoActionPerformed

    private void jCpago_tarjetaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCpago_tarjetaActionPerformed
        // TODO add your handling code here:
        seleccionar_cargar_filtro();
    }//GEN-LAST:event_jCpago_tarjetaActionPerformed

    private void jCpago_combinadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCpago_combinadoActionPerformed
        // TODO add your handling code here:
        seleccionar_cargar_filtro();
    }//GEN-LAST:event_jCpago_combinadoActionPerformed

    private void jCestado_devolucionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCestado_devolucionActionPerformed
        // TODO add your handling code here:
        seleccionar_cargar_filtro();
    }//GEN-LAST:event_jCestado_devolucionActionPerformed

    private void jCestado_usoreservaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCestado_usoreservaActionPerformed
        // TODO add your handling code here:
        seleccionar_cargar_filtro();
    }//GEN-LAST:event_jCestado_usoreservaActionPerformed

    private void jCestado_alquiladoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCestado_alquiladoActionPerformed
        // TODO add your handling code here:
        seleccionar_cargar_filtro();
    }//GEN-LAST:event_jCestado_alquiladoActionPerformed

    private void jCpago_transferenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCpago_transferenciaActionPerformed
        // TODO add your handling code here:
        seleccionar_cargar_filtro();
    }//GEN-LAST:event_jCpago_transferenciaActionPerformed

    private void jCpago_creditoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCpago_creditoActionPerformed
        // TODO add your handling code here:
         seleccionar_cargar_filtro();
    }//GEN-LAST:event_jCpago_creditoActionPerformed

    private void jCfiltro_direcoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCfiltro_direcoActionPerformed
        // TODO add your handling code here:
        esfiltro_por_fecha=false;
        seleccionar_cargar_filtro();
    }//GEN-LAST:event_jCfiltro_direcoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnbuscar_fecha;
    private javax.swing.JButton btnimprimir;
    private javax.swing.JButton btnreset;
    private javax.swing.JCheckBox jCestado_alquilado;
    private javax.swing.JCheckBox jCestado_anulado;
    private javax.swing.JCheckBox jCestado_devolucion;
    private javax.swing.JCheckBox jCestado_emitido;
    private javax.swing.JCheckBox jCestado_finalizado;
    private javax.swing.JCheckBox jCestado_usoreserva;
    private javax.swing.JComboBox<String> jCfiltro_direco;
    private javax.swing.JCheckBox jCpago_combinado;
    private javax.swing.JCheckBox jCpago_credito;
    private javax.swing.JCheckBox jCpago_efectivo;
    private javax.swing.JCheckBox jCpago_tarjeta;
    private javax.swing.JCheckBox jCpago_transferencia;
    private javax.swing.JFormattedTextField jFcant_fila;
    private javax.swing.JFormattedTextField jFtotal_venta_credito;
    private javax.swing.JFormattedTextField jFtotal_venta_efectivo;
    private javax.swing.JFormattedTextField jFtotal_venta_tarjeta;
    private javax.swing.JFormattedTextField jFtotal_venta_total;
    private javax.swing.JFormattedTextField jFtotal_venta_transferencia;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jList_cliente;
    private javax.swing.JPanel panel_cliente;
    private javax.swing.JPanel panel_estado;
    private javax.swing.JPanel panel_fecha;
    private javax.swing.JPanel panel_formapago;
    private javax.swing.JPanel panel_reporte_venta;
    private javax.swing.JTextField txtbucarCliente_nombre;
    private javax.swing.JTextField txtfecha_desde;
    private javax.swing.JTextField txtfecha_hasta;
    // End of variables declaration//GEN-END:variables
}
