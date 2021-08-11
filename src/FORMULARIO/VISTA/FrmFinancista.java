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
import java.awt.event.KeyEvent;
import java.sql.Connection;

/**
 *
 * @author Digno
 */
public class FrmFinancista extends javax.swing.JInternalFrame {

    EvenConexion eveconn = new EvenConexion();
    EvenJFRAME evetbl = new EvenJFRAME();
    EvenJtable evejta = new EvenJtable();
    EvenFecha evefec = new EvenFecha();
    private financista fina = new financista();
    private BO_financista finaBO = new BO_financista();
    private DAO_financista finaDAO = new DAO_financista();
    private saldo_credito_finanza scfina = new saldo_credito_finanza();
    private credito_finanza cfina = new credito_finanza();
    private grupo_credito_finanza gcfina = new grupo_credito_finanza();
    private DAO_grupo_credito_finanza gcfina_dao = new DAO_grupo_credito_finanza();
    private DAO_credito_finanza cfina_dao = new DAO_credito_finanza();
    private usuario usu = new usuario();
    private EvenNumero_a_Letra nroletra = new EvenNumero_a_Letra();
    EvenJTextField evejtf = new EvenJTextField();
    Connection conn = ConnPostgres.getConnPosgres();
    cla_color_pelete clacolor = new cla_color_pelete();
    private boolean hab_guardar;
    private String estado_EMITIDO = "EMITIDO";
    private String estado_ABIERTO = "ABIERTO";
    private int idfinancista;

    private void abrir_formulario() {
        this.setTitle("FINANCISTA");
        evetbl.centrar_formulario_internalframa(this);
        reestableser_financista();
        finaDAO.actualizar_tabla_financista(conn, tblfinancista);
        color_formulario();
        primer_finanza();
    }

    void primer_finanza() {
        int idfinancista = (eveconn.getInt_ultimoID_mas_uno(conn, fina.getTb_financista(), fina.getId_idfinancista()));
        if (idfinancista == 0) {
            txtid.setText(null);
            txtnombre.setText("sin definir");
            txtdescripcion.setText("ninguna");
            txttelefono.setText("000");
            txtdireccion.setText("local");
            jCescredito.setSelected(false);
            txtsaldo_credito.setText("0");
            txtfec_inicio_credito.setText(evefec.getString_formato_fecha());
            txtdia_limite_credito.setText("0");
            boton_guardar();
            this.dispose();
        }
    }

    private void color_formulario() {
        panel_tabla_cliente.setBackground(clacolor.getColor_tabla());
        panel_insertar_cliente.setBackground(clacolor.getColor_insertar_primario());
    }

    private boolean validar_guardar() {
        if (evejtf.getBoo_JTextField_vacio(txtnombre, "DEBE CARGAR UN NOMBRE")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtdescripcion, "DEBE CARGAR UN DESCRIPCION")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txttelefono, "DEBE CARGAR UN TELEFONO")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtdireccion, "DEBE CARGAR UNA DIRECCION")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtsaldo_credito, "DEBE CARGAR UN SALDO DE CREDITO")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtfec_inicio_credito, "DEBE CARGAR UNA FECHA INICIO CREDITO")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtdia_limite_credito, "DEBE CARGAR UN DIA LIMITE CREDITO")) {
            return false;
        }
        return true;
    }

    private void cargar_financista() {
        fina.setC2nombre(txtnombre.getText());
        fina.setC3direccion(txtdireccion.getText());
        fina.setC4telefono(txttelefono.getText());
        fina.setC5descripcion(txtdescripcion.getText());
        fina.setC6escredito(jCescredito.isSelected());
        fina.setC7saldo_credito(Double.parseDouble("-" + txtsaldo_credito.getText()));
        fina.setC8fecha_inicio_credito(txtfec_inicio_credito.getText());
        fina.setC9dia_limite_credito(Integer.parseInt(txtdia_limite_credito.getText()));
    }

    private void cargar_saldo_credito_finanza(int idfinancista) {
        scfina.setC3descripcion("CREDITO DE CLIENTE DE INICIO");
        scfina.setC4monto_saldo_credito(fina.getC7saldo_credito());
        scfina.setC5monto_letra(nroletra.Convertir(txtsaldo_credito.getText(), true));
        scfina.setC6estado(estado_EMITIDO);
        scfina.setC7fk_idfinancista(idfinancista);
        scfina.setC8fk_idusuario(usu.getGlobal_idusuario());
    }

    private void cargar_grupo_credito_finanza(int idfinancista) {
        gcfina.setC4estado(estado_ABIERTO);
        gcfina.setC5fk_idfinancista(idfinancista);
    }

    private void cargar_credito_finanza(int idsaldo_credito_finanza, int idgrupo_credito_finanza) {
        cfina.setC3descripcion("CREDITO DE CLIENTE DE INICIO");
        cfina.setC4estado(estado_EMITIDO);
        cfina.setC5monto_contado(0);
        cfina.setC6monto_credito(fina.getC7saldo_credito());
        cfina.setC7tabla_origen("CLIENTE");
        cfina.setC8fk_idgrupo_credito_finanza(idgrupo_credito_finanza);
        cfina.setC11fk_idcompra(0);
        cfina.setC10fk_idrecibo_pago_finanza(0);
        cfina.setC9fk_idsaldo_credito_finanza(idsaldo_credito_finanza);
    }

    private void boton_guardar() {
        if (hab_guardar) {
            if (validar_guardar()) {
                int idfinancista = (eveconn.getInt_ultimoID_mas_uno(conn, fina.getTb_financista(), fina.getId_idfinancista()));
                int idsaldo_credito_finanza = (eveconn.getInt_ultimoID_mas_uno(conn, scfina.getTb_saldo_credito_finanza(), scfina.getId_idsaldo_credito_finanza()));
                int idgrupo_credito_finanza = (eveconn.getInt_ultimoID_mas_uno(conn, gcfina.getTb_grupo_credito_finanza(), gcfina.getId_idgrupo_credito_finanza()));
                cargar_financista();
                cargar_saldo_credito_finanza(idfinancista);
                cargar_grupo_credito_finanza(idfinancista);
                cargar_credito_finanza(idsaldo_credito_finanza, idgrupo_credito_finanza);
                if (finaBO.getBoolean_insertar_finanza_con_credito_inicio(fina, scfina, cfina, gcfina)) {
                    reestableser_financista();
                    gcfina_dao.actualizar_tabla_grupo_credito_finanza_idc(conn, tblgrupo_credito_finanza, idfinancista);
                    cfina_dao.actualizar_tabla_credito_finanza_por_grupo(conn, tblcredito_finanza, idgrupo_credito_finanza);
                }

            }
        }
    }

    private void boton_editar() {
        if (validar_guardar()) {
            fina.setC1idfinancista(Integer.parseInt(txtid.getText()));
            fina.setC2nombre(txtnombre.getText());
            fina.setC3direccion(txtdireccion.getText());
            fina.setC4telefono(txttelefono.getText());
            fina.setC5descripcion(txtdescripcion.getText());
            fina.setC6escredito(jCescredito.isSelected());
            fina.setC7saldo_credito(Double.parseDouble(txtsaldo_credito.getText()));
            fina.setC8fecha_inicio_credito(txtfec_inicio_credito.getText());
            fina.setC9dia_limite_credito(Integer.parseInt(txtdia_limite_credito.getText()));
            finaBO.update_financista(fina, tblfinancista);
        }
    }

    private void seleccionar_tabla() {
        idfinancista = evejta.getInt_select_id(tblfinancista);
        fina.setC1idfinancista_global(idfinancista);
        finaDAO.cargar_financista(conn, fina, idfinancista);
        txtid.setText(String.valueOf(fina.getC1idfinancista()));
        txtnombre.setText(fina.getC2nombre());
        txtdireccion.setText(fina.getC3direccion());
        txttelefono.setText(fina.getC4telefono());
        txtdescripcion.setText(fina.getC5descripcion());
        jCescredito.setSelected(fina.getC6escredito());
        txtsaldo_credito.setText(evejtf.getString_format_nro_entero(fina.getC7saldo_credito()));
        txtfec_inicio_credito.setText(fina.getC8fecha_inicio_credito());
        txtdia_limite_credito.setText(evejtf.getString_format_nro_entero(fina.getC9dia_limite_credito()));
        btnguardar.setEnabled(false);
        hab_guardar = false;
        btneditar.setEnabled(true);
        gcfina_dao.actualizar_tabla_grupo_credito_finanza_idc(conn, tblgrupo_credito_finanza, idfinancista);
        gcfina_dao.cargar_grupo_credito_finanza_id(conn, gcfina, idfinancista);
        cfina_dao.actualizar_tabla_credito_finanza_por_grupo(conn, tblcredito_finanza, gcfina.getC1idgrupo_credito_finanza());
    }

    private void actualizar_todo() {
        finaDAO.actualizar_tabla_financista(conn, tblfinancista);
    }

    private void reestableser_financista() {
        finaDAO.actualizar_tabla_financista(conn, tblfinancista);
        txtid.setText(null);
        txtnombre.setText(null);
        txtdescripcion.setText(null);
        txttelefono.setText(null);
        txtdireccion.setText(null);
        jCescredito.setSelected(false);
        txtsaldo_credito.setText("0");
        txtfec_inicio_credito.setText(evefec.getString_formato_fecha());
        txtdia_limite_credito.setText("0");
        btnguardar.setEnabled(true);
        hab_guardar = true;
        btneditar.setEnabled(false);
        btndeletar.setEnabled(false);
        txtnombre.grabFocus();
    }

    private void boton_nuevo() {
        reestableser_financista();
    }

    private void cargar_credito_finanza() {
        if (!evejta.getBoolean_validar_select(tblgrupo_credito_finanza)) {
            int idgrupo_credito_finanza = evejta.getInt_select_id(tblgrupo_credito_finanza);
            cfina_dao.actualizar_tabla_credito_finanza_por_grupo(conn, tblcredito_finanza, idgrupo_credito_finanza);
        }
    }

    public FrmFinancista() {
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
        jPanel1 = new javax.swing.JPanel();
        panel_insertar_cliente = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtid = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtnombre = new javax.swing.JTextField();
        btnnuevo = new javax.swing.JButton();
        btnguardar = new javax.swing.JButton();
        btneditar = new javax.swing.JButton();
        btndeletar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtdescripcion = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txttelefono = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtdireccion = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jCescredito = new javax.swing.JCheckBox();
        jLabel6 = new javax.swing.JLabel();
        txtsaldo_credito = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtfec_inicio_credito = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtdia_limite_credito = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        panel_tabla_cliente = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblfinancista = new javax.swing.JTable();
        btnpagar_credito = new javax.swing.JButton();
        btnactualizar_tabla = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        panel_tabla_cliente1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblgrupo_credito_finanza = new javax.swing.JTable();
        panel_tabla_cliente2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblcredito_finanza = new javax.swing.JTable();

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

        panel_insertar_cliente.setBackground(new java.awt.Color(153, 204, 255));
        panel_insertar_cliente.setBorder(javax.swing.BorderFactory.createTitledBorder("CREAR DATO"));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("ID:");

        txtid.setEditable(false);
        txtid.setBackground(new java.awt.Color(204, 204, 204));
        txtid.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("NOMBRE:");

        txtnombre.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtnombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtnombreKeyPressed(evt);
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

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("DESCRI:");

        txtdescripcion.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtdescripcion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtdescripcionKeyPressed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("TELEFONO:");

        txttelefono.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txttelefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txttelefonoKeyPressed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setText("DIRECCION:");

        txtdireccion.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtdireccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtdireccionKeyPressed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("DATOS DE CREDITO"));

        jCescredito.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jCescredito.setText("TIENE CREDITO");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setText("SALDO DE CREDITO:");

        txtsaldo_credito.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtsaldo_credito.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtsaldo_creditoKeyPressed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setText("FEC INICIO CREDITO:");

        txtfec_inicio_credito.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtfec_inicio_credito.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtfec_inicio_creditoKeyPressed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel8.setText("DIA LIMITE CREDITO:");

        txtdia_limite_credito.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtdia_limite_credito.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtdia_limite_creditoKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtdia_limite_credito, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtfec_inicio_credito, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                    .addComponent(txtsaldo_credito))
                .addContainerGap(116, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jCescredito)
                .addContainerGap(312, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jCescredito)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtsaldo_credito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtfec_inicio_credito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtdia_limite_credito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(37, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panel_insertar_clienteLayout = new javax.swing.GroupLayout(panel_insertar_cliente);
        panel_insertar_cliente.setLayout(panel_insertar_clienteLayout);
        panel_insertar_clienteLayout.setHorizontalGroup(
            panel_insertar_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertar_clienteLayout.createSequentialGroup()
                .addGroup(panel_insertar_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panel_insertar_clienteLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panel_insertar_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_insertar_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtnombre, javax.swing.GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE)
                            .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txttelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtdireccion)
                            .addComponent(txtdescripcion)))
                    .addGroup(panel_insertar_clienteLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(btnnuevo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnguardar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btneditar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btndeletar))
                    .addGroup(panel_insertar_clienteLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(430, Short.MAX_VALUE))
        );
        panel_insertar_clienteLayout.setVerticalGroup(
            panel_insertar_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertar_clienteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_insertar_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtnombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(txtdescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txttelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtdireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnnuevo)
                    .addComponent(btnguardar)
                    .addComponent(btneditar)
                    .addComponent(btndeletar))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_insertar_cliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_insertar_cliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("CREAR FINANCISTA", jPanel1);

        panel_tabla_cliente.setBackground(new java.awt.Color(51, 204, 255));
        panel_tabla_cliente.setBorder(javax.swing.BorderFactory.createTitledBorder("TABLA"));

        tblfinancista.setModel(new javax.swing.table.DefaultTableModel(
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
        tblfinancista.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblfinancistaMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblfinancista);

        javax.swing.GroupLayout panel_tabla_clienteLayout = new javax.swing.GroupLayout(panel_tabla_cliente);
        panel_tabla_cliente.setLayout(panel_tabla_clienteLayout);
        panel_tabla_clienteLayout.setHorizontalGroup(
            panel_tabla_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 945, Short.MAX_VALUE)
        );
        panel_tabla_clienteLayout.setVerticalGroup(
            panel_tabla_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 406, Short.MAX_VALUE)
        );

        btnpagar_credito.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnpagar_credito.setText("PAGAR CREDITO");
        btnpagar_credito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnpagar_creditoActionPerformed(evt);
            }
        });

        btnactualizar_tabla.setText("ACTUALIZAR TABLA");
        btnactualizar_tabla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnactualizar_tablaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_tabla_cliente, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnpagar_credito, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnactualizar_tabla, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(panel_tabla_cliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnpagar_credito, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                    .addComponent(btnactualizar_tabla, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("TABLA FINANZA", jPanel2);

        panel_tabla_cliente1.setBackground(new java.awt.Color(51, 204, 255));
        panel_tabla_cliente1.setBorder(javax.swing.BorderFactory.createTitledBorder("GRUPO CREDITO FINANZA"));

        tblgrupo_credito_finanza.setModel(new javax.swing.table.DefaultTableModel(
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
        tblgrupo_credito_finanza.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblgrupo_credito_finanzaMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblgrupo_credito_finanzaMouseReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tblgrupo_credito_finanza);

        javax.swing.GroupLayout panel_tabla_cliente1Layout = new javax.swing.GroupLayout(panel_tabla_cliente1);
        panel_tabla_cliente1.setLayout(panel_tabla_cliente1Layout);
        panel_tabla_cliente1Layout.setHorizontalGroup(
            panel_tabla_cliente1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 945, Short.MAX_VALUE)
        );
        panel_tabla_cliente1Layout.setVerticalGroup(
            panel_tabla_cliente1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        panel_tabla_cliente2.setBackground(new java.awt.Color(51, 204, 255));
        panel_tabla_cliente2.setBorder(javax.swing.BorderFactory.createTitledBorder("CREDITO FINANZA"));

        tblcredito_finanza.setModel(new javax.swing.table.DefaultTableModel(
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
        tblcredito_finanza.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblcredito_finanzaMouseReleased(evt);
            }
        });
        jScrollPane3.setViewportView(tblcredito_finanza);

        javax.swing.GroupLayout panel_tabla_cliente2Layout = new javax.swing.GroupLayout(panel_tabla_cliente2);
        panel_tabla_cliente2.setLayout(panel_tabla_cliente2Layout);
        panel_tabla_cliente2Layout.setHorizontalGroup(
            panel_tabla_cliente2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 945, Short.MAX_VALUE)
        );
        panel_tabla_cliente2Layout.setVerticalGroup(
            panel_tabla_cliente2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_tabla_cliente1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panel_tabla_cliente2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(panel_tabla_cliente1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_tabla_cliente2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("GRUPO CREDITO", jPanel4);

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
        finaDAO.ancho_tabla_financista(tblfinancista);
    }//GEN-LAST:event_formInternalFrameOpened

    private void tblfinancistaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblfinancistaMouseReleased
        // TODO add your handling code here:
        seleccionar_tabla();
    }//GEN-LAST:event_tblfinancistaMouseReleased

    private void btneditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneditarActionPerformed
        // TODO add your handling code here:
        boton_editar();
    }//GEN-LAST:event_btneditarActionPerformed

    private void btnnuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevoActionPerformed
        // TODO add your handling code here:
        boton_nuevo();
    }//GEN-LAST:event_btnnuevoActionPerformed

    private void txtnombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnombreKeyPressed
        // TODO add your handling code here:
//        evejtf.saltar_campo_enter(evt, txtnombre, txtprecio_venta);
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            boton_guardar();
        }
    }//GEN-LAST:event_txtnombreKeyPressed

    private void txtdescripcionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtdescripcionKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtdescripcionKeyPressed

    private void txttelefonoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txttelefonoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txttelefonoKeyPressed

    private void txtdireccionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtdireccionKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtdireccionKeyPressed

    private void txtsaldo_creditoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtsaldo_creditoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtsaldo_creditoKeyPressed

    private void txtfec_inicio_creditoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfec_inicio_creditoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtfec_inicio_creditoKeyPressed

    private void txtdia_limite_creditoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtdia_limite_creditoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtdia_limite_creditoKeyPressed

    private void btnpagar_creditoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnpagar_creditoActionPerformed
        // TODO add your handling code here:
        if (tblfinancista.getSelectedRow() >= 0) {
            evetbl.abrir_TablaJinternal(new FrmRecibo_pago_finanza());
        }
    }//GEN-LAST:event_btnpagar_creditoActionPerformed

    private void tblgrupo_credito_finanzaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblgrupo_credito_finanzaMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tblgrupo_credito_finanzaMouseReleased

    private void btnactualizar_tablaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnactualizar_tablaActionPerformed
        // TODO add your handling code here:
        actualizar_todo();
    }//GEN-LAST:event_btnactualizar_tablaActionPerformed

    private void tblcredito_finanzaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblcredito_finanzaMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tblcredito_finanzaMouseReleased

    private void tblgrupo_credito_finanzaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblgrupo_credito_finanzaMousePressed
        // TODO add your handling code here:
        cargar_credito_finanza();
    }//GEN-LAST:event_tblgrupo_credito_finanzaMousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnactualizar_tabla;
    private javax.swing.JButton btndeletar;
    private javax.swing.JButton btneditar;
    private javax.swing.JButton btnguardar;
    private javax.swing.JButton btnnuevo;
    private javax.swing.JButton btnpagar_credito;
    private javax.swing.JCheckBox jCescredito;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel panel_insertar_cliente;
    private javax.swing.JPanel panel_tabla_cliente;
    private javax.swing.JPanel panel_tabla_cliente1;
    private javax.swing.JPanel panel_tabla_cliente2;
    public static javax.swing.JTable tblcredito_finanza;
    public static javax.swing.JTable tblfinancista;
    public static javax.swing.JTable tblgrupo_credito_finanza;
    private javax.swing.JTextField txtdescripcion;
    private javax.swing.JTextField txtdia_limite_credito;
    private javax.swing.JTextField txtdireccion;
    private javax.swing.JTextField txtfec_inicio_credito;
    private javax.swing.JTextField txtid;
    private javax.swing.JTextField txtnombre;
    private javax.swing.JTextField txtsaldo_credito;
    private javax.swing.JTextField txttelefono;
    // End of variables declaration//GEN-END:variables
}
