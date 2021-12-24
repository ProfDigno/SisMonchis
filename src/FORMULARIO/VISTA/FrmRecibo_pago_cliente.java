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
import Evento.Jtable.EvenJtable;
import Evento.Utilitario.EvenNumero_a_Letra;
import FORMULARIO.BO.*;
import FORMULARIO.DAO.*;
import FORMULARIO.ENTIDAD.*;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import javax.swing.JOptionPane;

/**
 *
 * @author Digno
 */
public class FrmRecibo_pago_cliente extends javax.swing.JInternalFrame {

    EvenJFRAME evetbl = new EvenJFRAME();
    EvenJtable evejta = new EvenJtable();
    private recibo_pago_cliente rpcli = new recibo_pago_cliente();
    private BO_recibo_pago_cliente rpcli_bo = new BO_recibo_pago_cliente();
    private DAO_recibo_pago_cliente rpcli_dao = new DAO_recibo_pago_cliente();
    private BO_cliente clBO = new BO_cliente();
    EvenJTextField evejtf = new EvenJTextField();
    Connection conn = ConnPostgres.getConnPosgres();
    EvenConexion eveconn = new EvenConexion();
    cla_color_pelete clacolor = new cla_color_pelete();
    EvenFecha evefec = new EvenFecha();
    private DAO_cliente cdao = new DAO_cliente();
    private cliente clie = new cliente();
    private credito_cliente ccli = new credito_cliente();
    private credito_cliente ccli2 = new credito_cliente();
    private DAO_grupo_credito_cliente gccDAO = new DAO_grupo_credito_cliente();
    private grupo_credito_cliente gcc = new grupo_credito_cliente();
     private saldo_credito_cliente sccli = new saldo_credito_cliente();
    private  caja_detalle caja = new caja_detalle();
    caja_detalle_alquilado cdalq = new caja_detalle_alquilado();
    private DAO_caja_detalle_alquilado cdalq_dao = new DAO_caja_detalle_alquilado();
    private usuario usu = new usuario();
    private EvenNumero_a_Letra nroletra = new EvenNumero_a_Letra();
    private boolean hab_guardar;
    private int fk_idcliente;
    private String estado_EMITIDO = "EMITIDO";
    private String estado_ABIERTO = "ABIERTO";
    private double monto_recibo_pago;
    private double monto_saldo_credito;
    private String monto_letra;
    private String tabla_origen = caja.getTabla_origen_recibo();
    private int fk_idusuario;
    private int idrecibo_pago_cliente;
    private double Lmonto_saldo_credito;

    private void abrir_formulario() {
        this.setTitle("RECIBO PAGO CLIENTE");
        evetbl.centrar_formulario_internalframa(this);
        fk_idusuario=usu.getGlobal_idusuario();
        cargar_cliente();
        reestableser();
        rpcli_dao.actualizar_tabla_recibo_pago_cliente(conn, tblpro_categoria);
        color_formulario();
        
    }

    private void color_formulario() {
        panel_tabla_categoria.setBackground(clacolor.getColor_tabla());
        panel_insertar_categoria.setBackground(clacolor.getColor_insertar_primario());
    }

    private boolean validar_guardar() {
        if (evejtf.getBoo_JTextField_vacio(txtrec_descripcion, "DEBE CARGAR UN NOMBRE")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtrec_monto_recibo_pago, "DEBE CARGAR UN MONTO")) {
            return false;
        }
        if(monto_recibo_pago > (Math.abs(clie.getC13saldo_credito()))){
            JOptionPane.showMessageDialog(null, "EL MONTO EXEDE EL MONTO MAXIMO DE PAGO","ERROR",JOptionPane.ERROR_MESSAGE);
            monto_recibo_pago = Math.abs(clie.getC13saldo_credito());
            txtrec_monto_recibo_pago.setText(String.valueOf((int)monto_recibo_pago));
            cargar_monto();
            return false;
        }
        return true;
    }

    private void cargar_monto() {
        if (txtrec_monto_recibo_pago.getText().trim().length() > 0) {
            String monto_recibo = txtrec_monto_recibo_pago.getText();
            monto_recibo_pago = Double.parseDouble(monto_recibo);
            monto_letra = nroletra.Convertir(monto_recibo, true);
            txtrec_monto_letra.setText(monto_letra);
            Lmonto_saldo_credito = clie.getC13saldo_credito() + monto_recibo_pago ;
            jFnuevo_saldo.setValue(Lmonto_saldo_credito);
            monto_saldo_credito = Math.abs(Lmonto_saldo_credito);
            if(Lmonto_saldo_credito >= 0){
                jFnuevo_saldo.setBackground(Color.yellow);
            }else{
                jFnuevo_saldo.setBackground(Color.white);
            }
        }
    }

    private void cargar_recibo_pago_cliente() {
        rpcli.setC3descripcion(txtrec_descripcion.getText());
        rpcli.setC4monto_recibo_pago(monto_recibo_pago);
        rpcli.setC5monto_letra(monto_letra);
        rpcli.setC6estado(estado_EMITIDO);
        rpcli.setC7fk_idcliente(fk_idcliente);
        rpcli.setC8fk_idusuario(fk_idusuario);
    }

    private void cargar_credito_cliente_recibo() {
        idrecibo_pago_cliente = (eveconn.getInt_ultimoID_mas_uno(conn, rpcli.getTb_recibo_pago_cliente(), rpcli.getId_idrecibo_pago_cliente()));
        gccDAO.cargar_grupo_credito_cliente_id(conn, gcc, fk_idcliente);
        ccli.setC3descripcion(txtrec_descripcion.getText());
        ccli.setC4estado(estado_EMITIDO);
        ccli.setC5monto_contado(monto_recibo_pago);
        ccli.setC6monto_credito(0);
        ccli.setC7tabla_origen(tabla_origen);
        ccli.setC8fk_idgrupo_credito_cliente(gcc.getC1idgrupo_credito_cliente());
        ccli.setC11fk_idventa_alquiler(0);
        ccli.setC10fk_idrecibo_pago_cliente(idrecibo_pago_cliente);
        ccli.setC9fk_idsaldo_credito_cliente(0);
    }
    private void cargar_credito_cliente_saldo() {
        ccli2.setC3descripcion("SALDO DEL CIERRE ANTERIOR");
        ccli2.setC4estado(estado_EMITIDO);
        ccli2.setC5monto_contado(0);
        ccli2.setC6monto_credito(monto_saldo_credito);
        ccli2.setC7tabla_origen(tabla_origen);
        ccli2.setC11fk_idventa_alquiler(0);
        ccli2.setC10fk_idrecibo_pago_cliente(0);
    }
    private void cargar_saldo_credito_cliente() {
        sccli.setC3descripcion("SALDO DEL CIERRE ANTERIOR");
        sccli.setC4monto_saldo_credito(monto_saldo_credito);
        String Smonto_saldo_credito=String.valueOf(monto_saldo_credito);
        sccli.setC5monto_letra(nroletra.Convertir(Smonto_saldo_credito, true));
        sccli.setC6estado(estado_EMITIDO);
        sccli.setC7fk_idcliente(fk_idcliente);
        sccli.setC8fk_idusuario(fk_idusuario);
    }
//    private void cargar_caja_detalle() {
//        caja.setC3descripcion1(txtrec_descripcion.getText());
//        caja.setC4monto_venta_efectivo(0);
//        caja.setC5monto_venta_tarjeta(0);
//        caja.setC6monto_delivery(0);
//        caja.setC7monto_gasto(0);
//        caja.setC8monto_compra(0);
//        caja.setC9monto_vale(0);
//        caja.setC10monto_caja(0);
//        caja.setC11monto_cierre(0);
//        caja.setC12id_origen(idrecibo_pago_cliente);
//        caja.setC13tabla_origen(tabla_origen);
////        caja.setC14cierre("A");
//        caja.setC15estado(estado_EMITIDO);
//        caja.setC16fk_idusuario(fk_idusuario);
//        caja.setC17monto_recibo_pago(monto_recibo_pago);
//        caja.setC18monto_compra_credito(0);
//    }
    void cargar_dato_caja_alquilado() {
        cdalq_dao.limpiar_caja_detalle_alquilado(cdalq);
        cdalq.setC3descripcion("(RECIBO CLIENTE) ID:" +idrecibo_pago_cliente + " DESCRIP:"+ txtrec_descripcion.getText());
        cdalq.setC4tabla_origen(tabla_origen);
        cdalq.setC5estado(estado_EMITIDO);
        cdalq.setC10monto_recibo_pago(monto_recibo_pago);
        cdalq.setC22fk_idrecibo_pago_cliente(idrecibo_pago_cliente);
    }
    private void boton_guardar() {
        if (hab_guardar) {
            if (validar_guardar()) {
                cargar_recibo_pago_cliente();
                cargar_credito_cliente_recibo();
                cargar_saldo_credito_cliente();
                cargar_credito_cliente_saldo();
                cargar_dato_caja_alquilado();
                clie.setC1idcliente(fk_idcliente);
                if (clBO.getBoolean_insertar_cliente_con_recibo_pago(clie, ccli,ccli2, gcc, rpcli,sccli,cdalq)) {
                    reestableser();
                    rpcli_dao.actualizar_tabla_recibo_pago_cliente(conn, tblpro_categoria);
//                    cdao.actualizar_tabla_cliente(conn, FrmCliente.tblpro_cliente);
                }
            }
        }
    }

    private void boton_editar() {
        if (validar_guardar()) {
            rpcli.setC1idrecibo_pago_cliente(Integer.parseInt(txtid.getText()));
            rpcli.setC3descripcion(txtrec_descripcion.getText());
//            rpcli_bo.update_recibo_pago_finanza(rpcli, tblpro_categoria);
        }
    }

    private void seleccionar_tabla() {
        int id = evejta.getInt_select_id(tblpro_categoria);
        rpcli_dao.cargar_recibo_pago_cliente(conn, rpcli, id);
        txtid.setText(String.valueOf(rpcli.getC1idrecibo_pago_cliente()));
        txtrec_descripcion.setText(rpcli.getC3descripcion());
        btnguardar.setEnabled(false);
        hab_guardar = false;
        btneditar.setEnabled(true);
    }

    private void reestableser() {
        txtid.setText(null);
        txtrec_fecha_emision.setText(evefec.getString_formato_fecha_hora());
        txtrec_descripcion.setText("RECIBO DE PAGO PARA: "+clie.getC3nombre());
        txtrec_monto_recibo_pago.setText(null);
        txtrec_monto_letra.setText(null);
        btnguardar.setEnabled(true);
        hab_guardar = true;
        btneditar.setEnabled(false);
        btndeletar.setEnabled(false);
        txtrec_descripcion.grabFocus();
    }

    private void limpiar_cliente_local() {
        fk_idcliente = 0;
        txtcli_nombre.setText(null);
        txtcli_ruc.setText(null);
        txtcli_direccion.setText(null);
        txtcli_telefono.setText(null);
        jFcli_saldo_credito.setValue(null);
        txtcli_fec_limite.setText(null);
        txtcli_nombre.grabFocus();
    }

    private void cargar_cliente() {
        cdao.cargar_cliente(conn, clie, clie.getC1idcliente_global());
        cargar_cliente_local(clie);
    }

    private void cargar_cliente_local(cliente cli) {
        fk_idcliente = cli.getC1idcliente();
        txtcli_nombre.setText(cli.getC3nombre());
        txtcli_ruc.setText(cli.getC6ruc());
        txtcli_direccion.setText(cli.getC4direccion());
        txtcli_telefono.setText(cli.getC5telefono());
        jFcli_saldo_credito.setValue(cli.getC13saldo_credito());
//        txtcli_fec_limite.setText(cli.getC);
    }

    private void boton_nuevo() {
        reestableser();
    }

    public FrmRecibo_pago_cliente() {
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

        jTabbedPane1 = new javax.swing.JTabbedPane();
        panel_insertar_categoria = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtid = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtrec_descripcion = new javax.swing.JTextField();
        btnnuevo = new javax.swing.JButton();
        btnguardar = new javax.swing.JButton();
        btneditar = new javax.swing.JButton();
        btndeletar = new javax.swing.JButton();
        panel_dato_cliente = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtcli_nombre = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtcli_direccion = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtcli_ruc = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtcli_telefono = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel7 = new javax.swing.JLabel();
        jFcli_saldo_credito = new javax.swing.JFormattedTextField();
        jLabel8 = new javax.swing.JLabel();
        btncli_nuevo = new javax.swing.JButton();
        btncli_buscar = new javax.swing.JButton();
        txtcli_fec_limite = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtrec_fecha_emision = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtrec_monto_recibo_pago = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtrec_monto_letra = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jFnuevo_saldo = new javax.swing.JFormattedTextField();
        panel_tabla_categoria = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblpro_categoria = new javax.swing.JTable();

        setClosable(true);
        setIconifiable(true);
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });

        panel_insertar_categoria.setBackground(new java.awt.Color(153, 204, 255));
        panel_insertar_categoria.setBorder(javax.swing.BorderFactory.createTitledBorder("CREAR DATO"));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("ID:");

        txtid.setEditable(false);
        txtid.setBackground(new java.awt.Color(204, 204, 204));
        txtid.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("DESCRIPCION:");

        txtrec_descripcion.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtrec_descripcion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtrec_descripcionKeyPressed(evt);
            }
        });

        btnnuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/nuevo.png"))); // NOI18N
        btnnuevo.setText("NUEVO");
        btnnuevo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnnuevo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnnuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevoActionPerformed(evt);
            }
        });

        btnguardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/guardar.png"))); // NOI18N
        btnguardar.setText("GUARDAR");
        btnguardar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnguardar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnguardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguardarActionPerformed(evt);
            }
        });

        btneditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/modificar.png"))); // NOI18N
        btneditar.setText("EDITAR");
        btneditar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btneditar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btneditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneditarActionPerformed(evt);
            }
        });

        btndeletar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/eliminar.png"))); // NOI18N
        btndeletar.setText("DELETAR");
        btndeletar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btndeletar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        panel_dato_cliente.setBackground(new java.awt.Color(204, 204, 255));
        panel_dato_cliente.setBorder(javax.swing.BorderFactory.createTitledBorder("CLIENTE"));

        jLabel3.setText("NOMBRE:");

        txtcli_nombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcli_nombreKeyPressed(evt);
            }
        });

        jLabel4.setText("DIRECCION:");

        jLabel5.setText("RUC:");

        txtcli_ruc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcli_rucKeyPressed(evt);
            }
        });

        jLabel6.setText("TELEFONO:");

        jLabel7.setText("SALDO:");

        jFcli_saldo_credito.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFcli_saldo_credito.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jLabel8.setText("FECHA LIMITE:");

        btncli_nuevo.setText("NUEVO");
        btncli_nuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncli_nuevoActionPerformed(evt);
            }
        });

        btncli_buscar.setText("BUSCAR");
        btncli_buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncli_buscarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_dato_clienteLayout = new javax.swing.GroupLayout(panel_dato_cliente);
        panel_dato_cliente.setLayout(panel_dato_clienteLayout);
        panel_dato_clienteLayout.setHorizontalGroup(
            panel_dato_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_dato_clienteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_dato_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(panel_dato_clienteLayout.createSequentialGroup()
                        .addGroup(panel_dato_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_dato_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtcli_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtcli_direccion, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_dato_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_dato_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_dato_clienteLayout.createSequentialGroup()
                                .addComponent(txtcli_ruc, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btncli_nuevo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(panel_dato_clienteLayout.createSequentialGroup()
                                .addComponent(txtcli_telefono, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btncli_buscar, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE))))
                    .addGroup(panel_dato_clienteLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFcli_saldo_credito, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtcli_fec_limite, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panel_dato_clienteLayout.setVerticalGroup(
            panel_dato_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_dato_clienteLayout.createSequentialGroup()
                .addGroup(panel_dato_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel3)
                    .addComponent(txtcli_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtcli_ruc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btncli_nuevo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_dato_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel4)
                    .addComponent(txtcli_direccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtcli_telefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btncli_buscar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_dato_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jFcli_saldo_credito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(txtcli_fec_limite, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel9.setText("FECHA:");

        txtrec_fecha_emision.setEditable(false);
        txtrec_fecha_emision.setBackground(new java.awt.Color(204, 204, 255));
        txtrec_fecha_emision.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtrec_fecha_emision.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtrec_fecha_emisionKeyPressed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel10.setText("MONTO:");

        txtrec_monto_recibo_pago.setFont(new java.awt.Font("Stencil", 0, 36)); // NOI18N
        txtrec_monto_recibo_pago.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtrec_monto_recibo_pago.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtrec_monto_recibo_pagoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtrec_monto_recibo_pagoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtrec_monto_recibo_pagoKeyTyped(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel11.setText("MONTO LETRA:");

        txtrec_monto_letra.setEditable(false);
        txtrec_monto_letra.setBackground(new java.awt.Color(204, 204, 255));
        txtrec_monto_letra.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtrec_monto_letra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtrec_monto_letraKeyPressed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel12.setText("NUEVO SALDO:");

        jFnuevo_saldo.setEditable(false);
        jFnuevo_saldo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFnuevo_saldo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFnuevo_saldo.setFont(new java.awt.Font("Stencil", 0, 36)); // NOI18N

        javax.swing.GroupLayout panel_insertar_categoriaLayout = new javax.swing.GroupLayout(panel_insertar_categoria);
        panel_insertar_categoria.setLayout(panel_insertar_categoriaLayout);
        panel_insertar_categoriaLayout.setHorizontalGroup(
            panel_insertar_categoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertar_categoriaLayout.createSequentialGroup()
                .addGroup(panel_insertar_categoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panel_dato_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panel_insertar_categoriaLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtrec_fecha_emision, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(panel_insertar_categoriaLayout.createSequentialGroup()
                .addGroup(panel_insertar_categoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_insertar_categoriaLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panel_insertar_categoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_insertar_categoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtrec_monto_letra, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtrec_descripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panel_insertar_categoriaLayout.createSequentialGroup()
                                .addComponent(txtrec_monto_recibo_pago, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jFnuevo_saldo))))
                    .addGroup(panel_insertar_categoriaLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(btnnuevo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnguardar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btneditar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btndeletar)))
                .addContainerGap(263, Short.MAX_VALUE))
        );
        panel_insertar_categoriaLayout.setVerticalGroup(
            panel_insertar_categoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_insertar_categoriaLayout.createSequentialGroup()
                .addGroup(panel_insertar_categoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(txtrec_fecha_emision, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addComponent(panel_dato_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_insertar_categoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtrec_descripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_categoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel10)
                    .addComponent(txtrec_monto_recibo_pago, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(jFnuevo_saldo, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_categoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtrec_monto_letra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(97, 97, 97)
                .addGroup(panel_insertar_categoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnnuevo)
                    .addComponent(btnguardar)
                    .addComponent(btneditar)
                    .addComponent(btndeletar))
                .addContainerGap())
        );

        jTabbedPane1.addTab("CREAR RECIBO", panel_insertar_categoria);

        panel_tabla_categoria.setBackground(new java.awt.Color(51, 204, 255));
        panel_tabla_categoria.setBorder(javax.swing.BorderFactory.createTitledBorder("TABLA"));

        tblpro_categoria.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblpro_categoria.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblpro_categoriaMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblpro_categoria);

        javax.swing.GroupLayout panel_tabla_categoriaLayout = new javax.swing.GroupLayout(panel_tabla_categoria);
        panel_tabla_categoria.setLayout(panel_tabla_categoriaLayout);
        panel_tabla_categoriaLayout.setHorizontalGroup(
            panel_tabla_categoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1004, Short.MAX_VALUE)
        );
        panel_tabla_categoriaLayout.setVerticalGroup(
            panel_tabla_categoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_tabla_categoriaLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("TABLAS", panel_tabla_categoria);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnguardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardarActionPerformed
        // TODO add your handling code here:
        boton_guardar();
    }//GEN-LAST:event_btnguardarActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        rpcli_dao.ancho_tabla_recibo_pago_cliente(tblpro_categoria);
    }//GEN-LAST:event_formInternalFrameOpened

    private void tblpro_categoriaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblpro_categoriaMouseReleased
        // TODO add your handling code here:
        seleccionar_tabla();
    }//GEN-LAST:event_tblpro_categoriaMouseReleased

    private void btneditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneditarActionPerformed
        // TODO add your handling code here:
        boton_editar();
    }//GEN-LAST:event_btneditarActionPerformed

    private void btnnuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevoActionPerformed
        // TODO add your handling code here:
        boton_nuevo();
    }//GEN-LAST:event_btnnuevoActionPerformed

    private void txtrec_descripcionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtrec_descripcionKeyPressed
        // TODO add your handling code here:
//        evejtf.saltar_campo_enter(evt, txtnombre, txtprecio_venta);
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            boton_guardar();
        }
    }//GEN-LAST:event_txtrec_descripcionKeyPressed

    private void txtcli_nombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcli_nombreKeyPressed
        // TODO add your handling code here:
//        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
//            if (txtcli_nombre.getText().trim().length() > 0) {
//                txtbuscador_cliente_nombre.setText(txtcli_nombre.getText());
//                cdao.actualizar_tabla_cliente_buscar(conn, tblbuscar_cliente, "nombre", txtbuscador_cliente_nombre.getText());
//                eveJtab.mostrar_JTabbedPane(tab_venta, 1);
//            }
//        }
    }//GEN-LAST:event_txtcli_nombreKeyPressed

    private void txtcli_rucKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcli_rucKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
//            buscar_cargar_cliente_ruc();
        }
    }//GEN-LAST:event_txtcli_rucKeyPressed

    private void btncli_nuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncli_nuevoActionPerformed
        // TODO add your handling code here:
//        eveJfra.abrir_TablaJinternal(new FrmCliente());
    }//GEN-LAST:event_btncli_nuevoActionPerformed

    private void btncli_buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncli_buscarActionPerformed
        // TODO add your handling code here:
//        eveJtab.mostrar_JTabbedPane(tab_venta, 1);
//        txtbuscador_cliente_nombre.grabFocus();
    }//GEN-LAST:event_btncli_buscarActionPerformed

    private void txtrec_fecha_emisionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtrec_fecha_emisionKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtrec_fecha_emisionKeyPressed

    private void txtrec_monto_recibo_pagoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtrec_monto_recibo_pagoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtrec_monto_recibo_pagoKeyPressed

    private void txtrec_monto_letraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtrec_monto_letraKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtrec_monto_letraKeyPressed

    private void txtrec_monto_recibo_pagoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtrec_monto_recibo_pagoKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txtrec_monto_recibo_pagoKeyTyped

    private void txtrec_monto_recibo_pagoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtrec_monto_recibo_pagoKeyReleased
        // TODO add your handling code here:
        cargar_monto();
    }//GEN-LAST:event_txtrec_monto_recibo_pagoKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btncli_buscar;
    private javax.swing.JButton btncli_nuevo;
    private javax.swing.JButton btndeletar;
    private javax.swing.JButton btneditar;
    private javax.swing.JButton btnguardar;
    private javax.swing.JButton btnnuevo;
    private javax.swing.JFormattedTextField jFcli_saldo_credito;
    private javax.swing.JFormattedTextField jFnuevo_saldo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel panel_dato_cliente;
    private javax.swing.JPanel panel_insertar_categoria;
    private javax.swing.JPanel panel_tabla_categoria;
    private javax.swing.JTable tblpro_categoria;
    private javax.swing.JTextField txtcli_direccion;
    private javax.swing.JTextField txtcli_fec_limite;
    private javax.swing.JTextField txtcli_nombre;
    private javax.swing.JTextField txtcli_ruc;
    private javax.swing.JTextField txtcli_telefono;
    private javax.swing.JTextField txtid;
    private javax.swing.JTextField txtrec_descripcion;
    private javax.swing.JTextField txtrec_fecha_emision;
    private javax.swing.JTextField txtrec_monto_letra;
    private javax.swing.JTextField txtrec_monto_recibo_pago;
    // End of variables declaration//GEN-END:variables
}
