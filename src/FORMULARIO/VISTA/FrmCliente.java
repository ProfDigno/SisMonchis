/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.VISTA;

import BASEDATO.LOCAL.ConnPostgres;
import BASEDATO.EvenConexion;
import Evento.Color.cla_color_pelete;
import Evento.Fecha.EvenFecha;
import Evento.JTextField.EvenJTextField;
import Evento.Jframe.EvenJFRAME;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Utilitario.EvenNumero_a_Letra;
import FORMULARIO.BO.*;
import FORMULARIO.DAO.*;
import FORMULARIO.ENTIDAD.*;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author Digno
 */
public class FrmCliente extends javax.swing.JInternalFrame {

    EvenJFRAME evetbl = new EvenJFRAME();
    EvenFecha evefec = new EvenFecha();
    EvenConexion eveconn = new EvenConexion();
    EvenJTextField evejtf = new EvenJTextField();
    EvenJtable evejt = new EvenJtable();
    Connection conn = ConnPostgres.getConnPosgres();
    cliente clie = new cliente();
    BO_cliente cBO = new BO_cliente();
    DAO_cliente cdao = new DAO_cliente();
    zona_delivery zona = new zona_delivery();
    DAO_zona_delivery zdao = new DAO_zona_delivery();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    cla_color_pelete clacolor = new cla_color_pelete();
    private EvenNumero_a_Letra nroletra = new EvenNumero_a_Letra();
    private usuario usu = new usuario();
    private saldo_credito_cliente scfina = new saldo_credito_cliente();
    private credito_cliente cfina = new credito_cliente();
    private grupo_credito_cliente gcfina = new grupo_credito_cliente();
    private DAO_grupo_credito_cliente gcfina_dao = new DAO_grupo_credito_cliente();
    private DAO_credito_cliente cfina_dao = new DAO_credito_cliente();
    private String estado_EMITIDO = "EMITIDO";
    private String estado_ABIERTO = "ABIERTO";

    /**
     * Creates new form FrmZonaDelivery
     */
    private void abrir_formulario_cliente() {
        this.setTitle("CLIENTE");
        evetbl.centrar_formulario_internalframa(this);
        reestableser_cliente();
        color_formulario();
//        cdao.actualizar_tabla_cliente(conn, tblpro_cliente);
//        txtfecha_desde.setText(evefec.getString_fecha_dia1());
//        txtfecha_hasta.setText(evefec.getString_formato_fecha());
        actualizar_todo();
    }

    void color_formulario() {
        panel_insert.setBackground(clacolor.getColor_insertar_primario());
//        panel_tabla.setBackground(clacolor.getColor_insertar_secundario());
//        panel_filtro.setBackground(clacolor.getColor_insertar_secundario());
//        panel_fecha.setBackground(clacolor.getColor_base());
//        panel_buscar.setBackground(clacolor.getColor_insertar_primario());
    }

    private boolean validar_guardar_cliente() {
        txtfecha_inicio.setText(evefec.getString_formato_fecha());
        if (evejtf.getBoo_JTextField_vacio(txtnombre, "DEBE CARGAR UN NOMBRE")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtruc, "DEBE CARGAR UN RUC")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txttelefono, "DEBE CARGAR UN TELEFONO")) {
            return false;
        }
        if (evejtf.getBoo_JTextarea_vacio(txtdireccion, "DEBE CARGAR UNA DIRECCION")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtzona, "DEBE CARGAR UNA ZONA")) {
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
        if (txtfecha_nacimiento.getText().trim().length() == 0) {
            txtfecha_nacimiento.setText(evefec.getString_formato_fecha());
            clie.setC7fecha_cumple(evefec.getString_formato_fecha());
//            return false;
        }
        return true;
    }

    private String tipo_cliente() {
        String tipo = "cliente";
        if (jRtipo_cliente.isSelected()) {
            tipo = "cliente";
        }
        if (jRtipo_funcionario.isSelected()) {
            tipo = "funcionario";
        }
        return tipo;
    }

    private void cargar_cliente() {
        clie.setC2fecha_inicio("now");
        clie.setC3nombre(txtnombre.getText());
        clie.setC4direccion(txtdireccion.getText());
        clie.setC5telefono(txttelefono.getText());
        clie.setC6ruc(txtruc.getText());
        clie.setC7fecha_cumple(txtfecha_nacimiento.getText());
        clie.setC8tipo(tipo_cliente());
        clie.setC12escredito(jCescredito.isSelected());
        clie.setC13saldo_credito(Double.parseDouble("-" + txtsaldo_credito.getText()));
        clie.setC14fecha_inicio_credito(txtfec_inicio_credito.getText());
        clie.setC15dia_limite_credito(Integer.parseInt(txtdia_limite_credito.getText()));
    }

    private void cargar_saldo_credito_cliente(int idcliente) {
        scfina.setC3descripcion("CREDITO DE CLIENTE DE INICIO");
        scfina.setC4monto_saldo_credito(clie.getC13saldo_credito());
        scfina.setC5monto_letra(nroletra.Convertir(txtsaldo_credito.getText(), true));
        scfina.setC6estado(estado_EMITIDO);
        scfina.setC7fk_idcliente(idcliente);
        scfina.setC8fk_idusuario(usu.getGlobal_idusuario());
    }

    private void cargar_grupo_credito_cliente(int idcliente) {
        gcfina.setC4estado(estado_ABIERTO);
        gcfina.setC5fk_idcliente(idcliente);
    }

    private void cargar_credito_cliente(int idsaldo_credito_cliente, int idgrupo_credito_cliente) {
        cfina.setC3descripcion("CREDITO DE CLIENTE DE INICIO");
        cfina.setC4estado(estado_EMITIDO);
        cfina.setC5monto_contado(0);
        cfina.setC6monto_credito(clie.getC13saldo_credito());
        cfina.setC7tabla_origen("CLIENTE");
        cfina.setC8fk_idgrupo_credito_cliente(idgrupo_credito_cliente);
        cfina.setC11fk_idventa_alquiler(0);
        cfina.setC10fk_idrecibo_pago_cliente(0);
        cfina.setC9fk_idsaldo_credito_cliente(idsaldo_credito_cliente);
    }

    private void boton_guardar_cliente() {
        if (validar_guardar_cliente()) {
            int idcliente = (eveconn.getInt_ultimoID_mas_uno(conn, clie.getTabla(), clie.getIdtabla()));
            int idsaldo_credito_cliente = (eveconn.getInt_ultimoID_mas_uno(conn, scfina.getTb_saldo_credito_cliente(), scfina.getId_idsaldo_credito_cliente()));
            int idgrupo_credito_cliente = (eveconn.getInt_ultimoID_mas_uno(conn, gcfina.getTb_grupo_credito_cliente(), gcfina.getId_idgrupo_credito_cliente()));
            cargar_cliente();
            cargar_saldo_credito_cliente(idcliente);
            cargar_grupo_credito_cliente(idcliente);
            cargar_credito_cliente(idsaldo_credito_cliente, idgrupo_credito_cliente);
            if (cBO.getBoolean_insertar_cliente_con_credito_inicio(clie, scfina, cfina, gcfina)) {
                reestableser_cliente();
                gcfina_dao.actualizar_tabla_grupo_credito_cliente_idc(conn, tblgrupo_credito_cliente, idcliente);
                cfina_dao.actualizar_tabla_credito_cliente_por_grupo(conn, tblcredito_cliente, idgrupo_credito_cliente);
            }
        }
    }

    private void boton_guardar_credito_inicio() {
        if (tblgrupo_credito_cliente.getRowCount() == 0) {
            int idcliente = evejt.getInt_select_id(tblcliente_credito_resumen);
            int idsaldo_credito_cliente = (eveconn.getInt_ultimoID_mas_uno(conn, scfina.getTb_saldo_credito_cliente(), scfina.getId_idsaldo_credito_cliente()));
            int idgrupo_credito_cliente = (eveconn.getInt_ultimoID_mas_uno(conn, gcfina.getTb_grupo_credito_cliente(), gcfina.getId_idgrupo_credito_cliente()));
            cargar_saldo_credito_cliente(idcliente);
            cargar_grupo_credito_cliente(idcliente);
            cargar_credito_cliente(idsaldo_credito_cliente, idgrupo_credito_cliente);
            if (cBO.getBoolean_insertar_credito_inicio(scfina, cfina, gcfina)) {
                gcfina_dao.actualizar_tabla_grupo_credito_cliente_idc(conn, tblgrupo_credito_cliente, idcliente);
                cfina_dao.actualizar_tabla_credito_cliente_por_grupo(conn, tblcredito_cliente, idgrupo_credito_cliente);
            }
        } else {
            JOptionPane.showMessageDialog(null, "YA TIENE CREDITO GRUPO INICIADO ");
        }
    }

    private void boton_editar_cliente() {
        if (validar_guardar_cliente()) {
            clie.setC1idcliente(Integer.parseInt(txtidcliente.getText()));
            clie.setC2fecha_inicio(txtfecha_inicio.getText());
            clie.setC3nombre(txtnombre.getText());
            clie.setC4direccion(txtdireccion.getText());
            clie.setC5telefono(txttelefono.getText());
            clie.setC6ruc(txtruc.getText());
            clie.setC7fecha_cumple(txtfecha_nacimiento.getText());
            clie.setC8tipo(tipo_cliente());
            clie.setC12escredito(jCescredito.isSelected());
            clie.setC13saldo_credito(Double.parseDouble("-" + txtsaldo_credito.getText()));
            clie.setC14fecha_inicio_credito(txtfec_inicio_credito.getText());
            clie.setC15dia_limite_credito(Integer.parseInt(txtdia_limite_credito.getText()));
            cBO.update_cliente(clie);
        }
    }

    private void seleccionar_tabla_cliente() {
        int idcliente = evejt.getInt_select_id(tblcliente_credito_resumen);
        cdao.cargar_cliente(conn, clie, idcliente);
        txtidcliente.setText(String.valueOf(clie.getC1idcliente()));
        txtfecha_inicio.setText(clie.getC2fecha_inicio());
        txtnombre.setText(clie.getC3nombre());
        txtdireccion.setText(clie.getC4direccion());
        txttelefono.setText(clie.getC5telefono());
        txtruc.setText(clie.getC6ruc());
        txtfecha_nacimiento.setText(clie.getC7fecha_cumple());
        jCescredito.setSelected(clie.isC12escredito());
        txtsaldo_credito.setText(String.valueOf(clie.getC13saldo_credito()));
        txtfec_inicio_credito.setText(clie.getC14fecha_inicio_credito());
        txtdia_limite_credito.setText(String.valueOf(clie.getC15dia_limite_credito()));
        if (clie.getC8tipo().equals("cliente")) {
            jRtipo_cliente.setSelected(true);
        }
        if (clie.getC8tipo().equals("funcionario")) {
            jRtipo_funcionario.setSelected(true);
        }
//        cargar_tabla_venta(idcliente);
//        sumar_monto_venta_cliente(idcliente);
        clie.setC1idcliente_global(idcliente);
        gcfina_dao.actualizar_tabla_grupo_credito_cliente_idc(conn, tblgrupo_credito_cliente, idcliente);
        gcfina_dao.cargar_grupo_credito_cliente_id(conn, gcfina, idcliente);
        cfina_dao.actualizar_tabla_credito_cliente_por_grupo(conn, tblcredito_cliente, gcfina.getC1idgrupo_credito_cliente());
        txtzona.setText(clie.getC10zona());
        txtdelivery.setText(clie.getC11delivery());
        btnguardar.setEnabled(false);
        btneditar.setEnabled(true);
    }

    private void reestableser_cliente() {
        jLzona.setVisible(false);
        jRtipo_cliente.setSelected(true);
        txtidcliente.setText(null);
        txtnombre.setText(null);
        txtfecha_inicio.setText(null);
        txtdireccion.setText(null);
        txttelefono.setText(null);
        txtruc.setText(null);
        txtfecha_nacimiento.setText(null);
        txtzona.setText(null);
        txtdelivery.setText(null);
        jCescredito.setSelected(false);
        txtsaldo_credito.setText("0");
        txtfec_inicio_credito.setText(evefec.getString_formato_fecha());
        txtdia_limite_credito.setText("0");
        btnguardar.setEnabled(true);
        btneditar.setEnabled(false);
        btndeletar.setEnabled(false);
        txtnombre.grabFocus();
    }

    private void cargar_zona_cliente() {
        int idzona = eveconn.getInt_seleccionar_JLista(conn, txtzona, jLzona, zona.getTabla(), zona.getNombretabla(), zona.getIdtabla());
        clie.setC9fk_idzona_delivery(idzona);
        zdao.cargar_zona_delivery(zona, idzona);
        txtdelivery.setText(String.valueOf(zona.getDelivery()));
    }

    private void boton_nuevo_cliente() {
        reestableser_cliente();
    }

//    private String filtro_fecha() {
//        String fecdesde = evefec.getString_validar_fecha(txtfecha_desde.getText());
//        String fechasta = evefec.getString_validar_fecha(txtfecha_hasta.getText());
//        txtfecha_desde.setText(fecdesde);
//        txtfecha_hasta.setText(fechasta);
//        String filtro_fecha = " and date(v.fecha_emision)>='" + fecdesde + "' \n"
//                + "and date(v.fecha_emision)<='" + fechasta + "'   ";
//        return filtro_fecha;
//    }
//    void cargar_tabla_venta(int idcliente) {
//        String sql = "select v.idventa,date(v.fecha_emision) as fecha,\n"
//                + "('('||iv.cantidad||')-'||iv.descripcion) as cant_producto,\n"
//                + "TRIM(to_char((iv.cantidad*iv.precio_venta),'999G999G999')) as monto\n"
//                + "from venta v,item_venta iv\n"
//                + "where v.fk_idcliente=" + idcliente
//                + " and v.idventa=iv.fk_idventa\n"
//                + "" + filtro_fecha()
//                + " order by 1 desc";
//        eveconn.Select_cargar_jtable(conn, sql, tblventa);
//        int Ancho[] = {10, 22, 56, 12};
//        evejt.setAnchoColumnaJtable(tblventa, Ancho);
//    }
//    void sumar_monto_venta_cliente(int idcliente) {
//        String titulo = "sumar_monto_venta_cliente";
//        String sql = "select (sum(iv.cantidad*iv.precio_venta)) as monto\n"
//                + "from venta v,item_venta iv\n"
//                + "where v.fk_idcliente=" + idcliente
//                + " and v.idventa=iv.fk_idventa\n"
//                + " " + filtro_fecha()
//                + " ";
//        try {
//            PreparedStatement pst = conn.prepareStatement(sql);
//            ResultSet rs = pst.executeQuery();
//            evemen.Imprimir_serial_sql(sql, titulo);
//            if (rs.next()) {
//                double monto = rs.getDouble("monto");
//                jFsuma_monto.setValue(monto);
//            }
//        } catch (SQLException e) {
//            evemen.Imprimir_serial_sql_error(e, sql, titulo);
//        }
//    }
//    private void seleccionar_tabla() {
//        int idcliente = evejt.getInt_select_id(tblcliente_credito_resumen);
//        clie.setC1idcliente_global(idcliente);
//        gcfina_dao.actualizar_tabla_grupo_credito_cliente_idc(conn, tblgrupo_credito_cliente, idcliente);
//        gcfina_dao.cargar_grupo_credito_cliente_id(conn, gcfina, idcliente);
//        cfina_dao.actualizar_tabla_credito_cliente_por_grupo(conn, tblcredito_cliente, gcfina.getC1idgrupo_credito_cliente());
//    }
    private void actualizar_todo() {
        cdao.actualizar_tabla_cliente2(conn, tblcliente_credito_resumen);
    }

    private void cargar_credito_cliente() {
        if (!evejt.getBoolean_validar_select(tblgrupo_credito_cliente)) {
            int idgrupo_credito_cliente = evejt.getInt_select_id(tblgrupo_credito_cliente);
            cfina_dao.actualizar_tabla_credito_cliente_por_grupo(conn, tblcredito_cliente, idgrupo_credito_cliente);
        }
    }

    public FrmCliente() {
        initComponents();
        abrir_formulario_cliente();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        gru_tipo = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        panel_insert = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jRtipo_cliente = new javax.swing.JRadioButton();
        jRtipo_funcionario = new javax.swing.JRadioButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtdireccion = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtruc = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txttelefono = new javax.swing.JTextField();
        txtnombre = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtidcliente = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtfecha_inicio = new javax.swing.JTextField();
        lblfecnac = new javax.swing.JLabel();
        txtfecha_nacimiento = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jLzona = new javax.swing.JList<>();
        jLabel8 = new javax.swing.JLabel();
        txtzona = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtdelivery = new javax.swing.JTextField();
        btnnuevo = new javax.swing.JButton();
        btnguardar = new javax.swing.JButton();
        btneditar = new javax.swing.JButton();
        btndeletar = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        panel_insert1 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jRtipo_cliente1 = new javax.swing.JRadioButton();
        jRtipo_funcionario1 = new javax.swing.JRadioButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        txtdireccion1 = new javax.swing.JTextArea();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        txtruc1 = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        txttelefono1 = new javax.swing.JTextField();
        txtnombre1 = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        txtidcliente1 = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        txtfecha_inicio1 = new javax.swing.JTextField();
        lblfecnac1 = new javax.swing.JLabel();
        txtfecha_nacimiento1 = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jLayeredPane2 = new javax.swing.JLayeredPane();
        jLzona1 = new javax.swing.JList<>();
        jLabel28 = new javax.swing.JLabel();
        txtzona1 = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        txtdelivery1 = new javax.swing.JTextField();
        btnnuevo1 = new javax.swing.JButton();
        btnguardar1 = new javax.swing.JButton();
        btneditar1 = new javax.swing.JButton();
        btndeletar1 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jCescredito = new javax.swing.JCheckBox();
        jLabel13 = new javax.swing.JLabel();
        txtsaldo_credito = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtfec_inicio_credito = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtdia_limite_credito = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        panel_tabla_cliente = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblcliente_credito_resumen = new javax.swing.JTable();
        btnpagar_credito = new javax.swing.JButton();
        btnactualizar_tabla = new javax.swing.JButton();
        btncrearcredito_inicio = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        panel_tabla_cliente1 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblgrupo_credito_cliente = new javax.swing.JTable();
        panel_tabla_cliente2 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblcredito_cliente = new javax.swing.JTable();

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

        panel_insert.setBackground(new java.awt.Color(153, 204, 255));
        panel_insert.setBorder(javax.swing.BorderFactory.createTitledBorder("CREAR DATO"));

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel16.setText("Tipo:");

        gru_tipo.add(jRtipo_cliente);
        jRtipo_cliente.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jRtipo_cliente.setText("CLIENTE");
        jRtipo_cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRtipo_clienteActionPerformed(evt);
            }
        });

        gru_tipo.add(jRtipo_funcionario);
        jRtipo_funcionario.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jRtipo_funcionario.setText("FUNCIONARIO");
        jRtipo_funcionario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRtipo_funcionarioActionPerformed(evt);
            }
        });

        txtdireccion.setColumns(20);
        txtdireccion.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N
        txtdireccion.setRows(5);
        txtdireccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtdireccionKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(txtdireccion);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Direccion:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Ruc:");

        txtruc.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtruc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtrucKeyPressed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Telefono:");

        txttelefono.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txttelefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txttelefonoKeyPressed(evt);
            }
        });

        txtnombre.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtnombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtnombreKeyPressed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Nombre:");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("ID:");

        txtidcliente.setEditable(false);
        txtidcliente.setBackground(new java.awt.Color(204, 204, 204));
        txtidcliente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Fecha Inicio:");

        txtfecha_inicio.setEditable(false);
        txtfecha_inicio.setBackground(new java.awt.Color(204, 204, 204));
        txtfecha_inicio.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        lblfecnac.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblfecnac.setText("Fec. Nac.:");

        txtfecha_nacimiento.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtfecha_nacimiento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtfecha_nacimientoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtfecha_nacimientoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtfecha_nacimientoKeyTyped(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel12.setText("año-mes-dia");

        jLayeredPane1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLzona.setBackground(new java.awt.Color(204, 204, 255));
        jLzona.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLzona.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jLzona.setSelectionBackground(new java.awt.Color(255, 51, 51));
        jLzona.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLzonaMouseReleased(evt);
            }
        });
        jLzona.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jLzonaKeyReleased(evt);
            }
        });
        jLayeredPane1.add(jLzona, new org.netbeans.lib.awtextra.AbsoluteConstraints(91, 40, 220, 100));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("ZONA:");
        jLayeredPane1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 14, -1, -1));

        txtzona.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtzona.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtzonaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtzonaKeyReleased(evt);
            }
        });
        jLayeredPane1.add(txtzona, new org.netbeans.lib.awtextra.AbsoluteConstraints(82, 13, 210, -1));

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel17.setText("DELIVERY:");
        jLayeredPane1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 20, -1, -1));

        txtdelivery.setEditable(false);
        txtdelivery.setBackground(new java.awt.Color(204, 204, 204));
        txtdelivery.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLayeredPane1.add(txtdelivery, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 10, 147, -1));

        btnnuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/nuevo.png"))); // NOI18N
        btnnuevo.setText("NUEVO");
        btnnuevo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnnuevo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnnuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevoActionPerformed(evt);
            }
        });
        jLayeredPane1.add(btnnuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 70, -1, -1));

        btnguardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/guardar.png"))); // NOI18N
        btnguardar.setText("GUARDAR");
        btnguardar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnguardar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnguardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguardarActionPerformed(evt);
            }
        });
        jLayeredPane1.add(btnguardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 70, -1, -1));

        btneditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/modificar.png"))); // NOI18N
        btneditar.setText("EDITAR");
        btneditar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btneditar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btneditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneditarActionPerformed(evt);
            }
        });
        jLayeredPane1.add(btneditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 70, -1, -1));

        btndeletar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/eliminar.png"))); // NOI18N
        btndeletar.setText("DELETAR");
        btndeletar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btndeletar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jLayeredPane1.add(btndeletar, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 70, -1, -1));

        javax.swing.GroupLayout panel_insertLayout = new javax.swing.GroupLayout(panel_insert);
        panel_insert.setLayout(panel_insertLayout);
        panel_insertLayout.setHorizontalGroup(
            panel_insertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertLayout.createSequentialGroup()
                .addGroup(panel_insertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_insertLayout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRtipo_cliente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jRtipo_funcionario)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblfecnac)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtfecha_nacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12))
                    .addGroup(panel_insertLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panel_insertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 546, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panel_insertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 443, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(panel_insertLayout.createSequentialGroup()
                                    .addGroup(panel_insertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(panel_insertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(panel_insertLayout.createSequentialGroup()
                                            .addComponent(txtidcliente, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jLabel7)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txtfecha_inicio, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(panel_insertLayout.createSequentialGroup()
                                            .addComponent(txtruc, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jLabel5)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txttelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(txtnombre)))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_insertLayout.setVerticalGroup(
            panel_insertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_insertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel1)
                    .addComponent(txtidcliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(txtfecha_inicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel2)
                    .addComponent(txtnombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel3)
                    .addComponent(txtruc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txttelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jRtipo_cliente)
                    .addComponent(jRtipo_funcionario)
                    .addComponent(lblfecnac)
                    .addComponent(txtfecha_nacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE))
        );

        panel_insert1.setBackground(new java.awt.Color(153, 204, 255));
        panel_insert1.setBorder(javax.swing.BorderFactory.createTitledBorder("CREAR DATO"));

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel20.setText("Tipo:");

        gru_tipo.add(jRtipo_cliente1);
        jRtipo_cliente1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jRtipo_cliente1.setSelected(true);
        jRtipo_cliente1.setText("CLIENTE");
        jRtipo_cliente1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRtipo_cliente1ActionPerformed(evt);
            }
        });

        gru_tipo.add(jRtipo_funcionario1);
        jRtipo_funcionario1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jRtipo_funcionario1.setText("FUNCIONARIO");
        jRtipo_funcionario1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRtipo_funcionario1ActionPerformed(evt);
            }
        });

        txtdireccion1.setColumns(20);
        txtdireccion1.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N
        txtdireccion1.setRows(5);
        txtdireccion1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtdireccion1KeyPressed(evt);
            }
        });
        jScrollPane7.setViewportView(txtdireccion1);

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel21.setText("Direccion:");

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel22.setText("Ruc:");

        txtruc1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtruc1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtruc1KeyPressed(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel23.setText("Telefono:");

        txttelefono1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txttelefono1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txttelefono1KeyPressed(evt);
            }
        });

        txtnombre1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtnombre1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtnombre1KeyPressed(evt);
            }
        });

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel24.setText("Nombre:");

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel25.setText("ID:");

        txtidcliente1.setEditable(false);
        txtidcliente1.setBackground(new java.awt.Color(204, 204, 204));
        txtidcliente1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel26.setText("Fecha Inicio:");

        txtfecha_inicio1.setEditable(false);
        txtfecha_inicio1.setBackground(new java.awt.Color(204, 204, 204));
        txtfecha_inicio1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        lblfecnac1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblfecnac1.setText("Fec. Nac.:");

        txtfecha_nacimiento1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtfecha_nacimiento1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtfecha_nacimiento1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtfecha_nacimiento1KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtfecha_nacimiento1KeyTyped(evt);
            }
        });

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel27.setText("año-mes-dia");

        jLayeredPane2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLzona1.setBackground(new java.awt.Color(204, 204, 255));
        jLzona1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLzona1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jLzona1.setSelectionBackground(new java.awt.Color(255, 51, 51));
        jLzona1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLzona1MouseReleased(evt);
            }
        });
        jLzona1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jLzona1KeyReleased(evt);
            }
        });
        jLayeredPane2.add(jLzona1, new org.netbeans.lib.awtextra.AbsoluteConstraints(91, 40, 220, 100));

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel28.setText("ZONA:");
        jLayeredPane2.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 14, -1, -1));

        txtzona1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtzona1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtzona1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtzona1KeyReleased(evt);
            }
        });
        jLayeredPane2.add(txtzona1, new org.netbeans.lib.awtextra.AbsoluteConstraints(82, 13, 210, -1));

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel29.setText("DELIVERY:");
        jLayeredPane2.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 20, -1, -1));

        txtdelivery1.setEditable(false);
        txtdelivery1.setBackground(new java.awt.Color(204, 204, 204));
        txtdelivery1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLayeredPane2.add(txtdelivery1, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 10, 147, -1));

        btnnuevo1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/nuevo.png"))); // NOI18N
        btnnuevo1.setText("NUEVO");
        btnnuevo1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnnuevo1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnnuevo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevo1ActionPerformed(evt);
            }
        });
        jLayeredPane2.add(btnnuevo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 70, -1, -1));

        btnguardar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/guardar.png"))); // NOI18N
        btnguardar1.setText("GUARDAR");
        btnguardar1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnguardar1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnguardar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguardar1ActionPerformed(evt);
            }
        });
        jLayeredPane2.add(btnguardar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 70, -1, -1));

        btneditar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/modificar.png"))); // NOI18N
        btneditar1.setText("EDITAR");
        btneditar1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btneditar1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btneditar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneditar1ActionPerformed(evt);
            }
        });
        jLayeredPane2.add(btneditar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 70, -1, -1));

        btndeletar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/eliminar.png"))); // NOI18N
        btndeletar1.setText("DELETAR");
        btndeletar1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btndeletar1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jLayeredPane2.add(btndeletar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 70, -1, -1));

        javax.swing.GroupLayout panel_insert1Layout = new javax.swing.GroupLayout(panel_insert1);
        panel_insert1.setLayout(panel_insert1Layout);
        panel_insert1Layout.setHorizontalGroup(
            panel_insert1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insert1Layout.createSequentialGroup()
                .addGroup(panel_insert1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_insert1Layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRtipo_cliente1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jRtipo_funcionario1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblfecnac1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtfecha_nacimiento1, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel27))
                    .addGroup(panel_insert1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panel_insert1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLayeredPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 546, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panel_insert1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 443, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(panel_insert1Layout.createSequentialGroup()
                                    .addGroup(panel_insert1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel22, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel24, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel25, javax.swing.GroupLayout.Alignment.TRAILING))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(panel_insert1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(panel_insert1Layout.createSequentialGroup()
                                            .addComponent(txtidcliente1, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jLabel26)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txtfecha_inicio1, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(panel_insert1Layout.createSequentialGroup()
                                            .addComponent(txtruc1, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jLabel23)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txttelefono1, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(txtnombre1)))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_insert1Layout.setVerticalGroup(
            panel_insert1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insert1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_insert1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel25)
                    .addComponent(txtidcliente1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26)
                    .addComponent(txtfecha_inicio1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insert1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel24)
                    .addComponent(txtnombre1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insert1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel22)
                    .addComponent(txtruc1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23)
                    .addComponent(txttelefono1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insert1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel21)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insert1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(jRtipo_cliente1)
                    .addComponent(jRtipo_funcionario1)
                    .addComponent(lblfecnac1)
                    .addComponent(txtfecha_nacimiento1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLayeredPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("DATOS DE CREDITO"));

        jCescredito.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jCescredito.setText("TIENE CREDITO");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel13.setText("SALDO DE CREDITO:");

        txtsaldo_credito.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtsaldo_credito.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtsaldo_creditoKeyPressed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel18.setText("FEC INICIO CREDITO:");

        txtfec_inicio_credito.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtfec_inicio_credito.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtfec_inicio_creditoKeyPressed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel19.setText("DIA LIMITE CREDITO:");

        txtdia_limite_credito.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtdia_limite_credito.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtdia_limite_creditoKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18)
                    .addComponent(jLabel13)
                    .addComponent(jLabel19))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(txtdia_limite_credito, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(83, Short.MAX_VALUE))
                    .addComponent(txtsaldo_credito)
                    .addComponent(txtfec_inicio_credito)))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jCescredito)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jCescredito)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtsaldo_credito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txtfec_inicio_credito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(txtdia_limite_credito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(panel_insert1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(119, 119, 119))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_insert1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(panel_insert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 494, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_insert, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab("CREAR CLIENTE", jPanel3);

        panel_tabla_cliente.setBackground(new java.awt.Color(51, 204, 255));
        panel_tabla_cliente.setBorder(javax.swing.BorderFactory.createTitledBorder("TABLA"));

        tblcliente_credito_resumen.setModel(new javax.swing.table.DefaultTableModel(
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
        tblcliente_credito_resumen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblcliente_credito_resumenMouseReleased(evt);
            }
        });
        jScrollPane4.setViewportView(tblcliente_credito_resumen);

        javax.swing.GroupLayout panel_tabla_clienteLayout = new javax.swing.GroupLayout(panel_tabla_cliente);
        panel_tabla_cliente.setLayout(panel_tabla_clienteLayout);
        panel_tabla_clienteLayout.setHorizontalGroup(
            panel_tabla_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 1060, Short.MAX_VALUE)
        );
        panel_tabla_clienteLayout.setVerticalGroup(
            panel_tabla_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 425, Short.MAX_VALUE)
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

        btncrearcredito_inicio.setText("CREAR CREDITO DE INICIO");
        btncrearcredito_inicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncrearcredito_inicioActionPerformed(evt);
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btncrearcredito_inicio)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(panel_tabla_cliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnpagar_credito, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                    .addComponent(btnactualizar_tabla, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btncrearcredito_inicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1072, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 518, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 15, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab("TABLA CREDITO", jPanel1);

        panel_tabla_cliente1.setBackground(new java.awt.Color(51, 204, 255));
        panel_tabla_cliente1.setBorder(javax.swing.BorderFactory.createTitledBorder("GRUPO CREDITO FINANZA"));

        tblgrupo_credito_cliente.setModel(new javax.swing.table.DefaultTableModel(
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
        tblgrupo_credito_cliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblgrupo_credito_clienteMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblgrupo_credito_clienteMouseReleased(evt);
            }
        });
        jScrollPane5.setViewportView(tblgrupo_credito_cliente);

        javax.swing.GroupLayout panel_tabla_cliente1Layout = new javax.swing.GroupLayout(panel_tabla_cliente1);
        panel_tabla_cliente1.setLayout(panel_tabla_cliente1Layout);
        panel_tabla_cliente1Layout.setHorizontalGroup(
            panel_tabla_cliente1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 1060, Short.MAX_VALUE)
        );
        panel_tabla_cliente1Layout.setVerticalGroup(
            panel_tabla_cliente1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        panel_tabla_cliente2.setBackground(new java.awt.Color(51, 204, 255));
        panel_tabla_cliente2.setBorder(javax.swing.BorderFactory.createTitledBorder("CREDITO FINANZA"));

        tblcredito_cliente.setModel(new javax.swing.table.DefaultTableModel(
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
        tblcredito_cliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblcredito_clienteMouseReleased(evt);
            }
        });
        jScrollPane6.setViewportView(tblcredito_cliente);

        javax.swing.GroupLayout panel_tabla_cliente2Layout = new javax.swing.GroupLayout(panel_tabla_cliente2);
        panel_tabla_cliente2.setLayout(panel_tabla_cliente2Layout);
        panel_tabla_cliente2Layout.setHorizontalGroup(
            panel_tabla_cliente2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6)
        );
        panel_tabla_cliente2Layout.setVerticalGroup(
            panel_tabla_cliente2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_tabla_cliente1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panel_tabla_cliente2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(panel_tabla_cliente1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_tabla_cliente2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1072, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 518, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 11, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab("GRUPO CREDITO", jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1077, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnguardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardarActionPerformed
        // TODO add your handling code here:
        boton_guardar_cliente();
    }//GEN-LAST:event_btnguardarActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
//        cdao.ancho_tabla_cliente(tblpro_cliente);
        cdao.ancho_tabla_cliente2(tblcliente_credito_resumen);
    }//GEN-LAST:event_formInternalFrameOpened

    private void btneditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneditarActionPerformed
        // TODO add your handling code here:
        boton_editar_cliente();
    }//GEN-LAST:event_btneditarActionPerformed

    private void btnnuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevoActionPerformed
        // TODO add your handling code here:
        boton_nuevo_cliente();
    }//GEN-LAST:event_btnnuevoActionPerformed

    private void jRtipo_clienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRtipo_clienteActionPerformed
        // TODO add your handling code here:
//        jCdelivery.setSelected(true);
    }//GEN-LAST:event_jRtipo_clienteActionPerformed

    private void jRtipo_funcionarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRtipo_funcionarioActionPerformed
        // TODO add your handling code here:
//        jCdelivery.setSelected(false);
    }//GEN-LAST:event_jRtipo_funcionarioActionPerformed

    private void txtrucKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtrucKeyPressed
        // TODO add your handling code here:
//        pasarCampoEnter(evt, txtruc, txttelefono);
        evejtf.saltar_campo_enter(evt, txtruc, txttelefono);
    }//GEN-LAST:event_txtrucKeyPressed

    private void txttelefonoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txttelefonoKeyPressed
        // TODO add your handling code here:
//        pasarCampoEnter2(evt, txttelefono, txtdireccion);
        evejtf.saltar_campo_enter(evt, txttelefono, txtfecha_nacimiento);
    }//GEN-LAST:event_txttelefonoKeyPressed

    private void txtnombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnombreKeyPressed
        // TODO add your handling code here:
//        pasarCampoEnter(evt, txtnombre, txtruc);
        evejtf.saltar_campo_enter(evt, txtnombre, txtruc);
    }//GEN-LAST:event_txtnombreKeyPressed

    private void txtfecha_nacimientoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfecha_nacimientoKeyReleased
        // TODO add your handling code here:
        evejtf.verificar_fecha(evt, txtfecha_nacimiento);
    }//GEN-LAST:event_txtfecha_nacimientoKeyReleased

    private void txtfecha_nacimientoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfecha_nacimientoKeyTyped
        // TODO add your handling code here:
//        fo.soloFechaText(evt);
    }//GEN-LAST:event_txtfecha_nacimientoKeyTyped

    private void txtzonaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtzonaKeyReleased
        // TODO add your handling code here:
        eveconn.buscar_cargar_Jlista(conn, txtzona, jLzona, zona.getTabla(), zona.getNombretabla(), zona.getNombretabla(), 4);
    }//GEN-LAST:event_txtzonaKeyReleased

    private void jLzonaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLzonaMouseReleased
        // TODO add your handling code here:
        cargar_zona_cliente();
    }//GEN-LAST:event_jLzonaMouseReleased

    private void txtzonaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtzonaKeyPressed
        // TODO add your handling code here:
        evejtf.seleccionar_lista(evt, jLzona);
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtzona.setBackground(Color.WHITE);
            txtdireccion.setBackground(Color.YELLOW);
            txtdireccion.grabFocus();
        }
    }//GEN-LAST:event_txtzonaKeyPressed

    private void jLzonaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jLzonaKeyReleased
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            cargar_zona_cliente();
        }
    }//GEN-LAST:event_jLzonaKeyReleased

    private void txtfecha_nacimientoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfecha_nacimientoKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtfecha_nacimiento.setText(evefec.getString_validar_fecha(txtfecha_nacimiento.getText()));
            evejtf.saltar_campo_enter(evt, txtfecha_nacimiento, txtzona);
        }

    }//GEN-LAST:event_txtfecha_nacimientoKeyPressed

    private void txtdireccionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtdireccionKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtdireccionKeyPressed

    private void tblcliente_credito_resumenMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblcliente_credito_resumenMouseReleased
        // TODO add your handling code here:
//        seleccionar_tabla();
        seleccionar_tabla_cliente();
    }//GEN-LAST:event_tblcliente_credito_resumenMouseReleased

    private void btnpagar_creditoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnpagar_creditoActionPerformed
        // TODO add your handling code here:
        if (tblcliente_credito_resumen.getSelectedRow() >= 0) {
            evetbl.abrir_TablaJinternal(new FrmRecibo_pago_cliente());
        }
    }//GEN-LAST:event_btnpagar_creditoActionPerformed

    private void btnactualizar_tablaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnactualizar_tablaActionPerformed
        // TODO add your handling code here:
        actualizar_todo();
    }//GEN-LAST:event_btnactualizar_tablaActionPerformed

    private void tblgrupo_credito_clienteMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblgrupo_credito_clienteMousePressed
        // TODO add your handling code here:
        cargar_credito_cliente();
    }//GEN-LAST:event_tblgrupo_credito_clienteMousePressed

    private void tblgrupo_credito_clienteMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblgrupo_credito_clienteMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tblgrupo_credito_clienteMouseReleased

    private void tblcredito_clienteMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblcredito_clienteMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tblcredito_clienteMouseReleased

    private void txtsaldo_creditoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtsaldo_creditoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtsaldo_creditoKeyPressed

    private void txtfec_inicio_creditoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfec_inicio_creditoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtfec_inicio_creditoKeyPressed

    private void txtdia_limite_creditoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtdia_limite_creditoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtdia_limite_creditoKeyPressed

    private void btncrearcredito_inicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncrearcredito_inicioActionPerformed
        // TODO add your handling code here:
        boton_guardar_credito_inicio();
    }//GEN-LAST:event_btncrearcredito_inicioActionPerformed

    private void jRtipo_cliente1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRtipo_cliente1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRtipo_cliente1ActionPerformed

    private void jRtipo_funcionario1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRtipo_funcionario1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRtipo_funcionario1ActionPerformed

    private void txtdireccion1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtdireccion1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtdireccion1KeyPressed

    private void txtruc1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtruc1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtruc1KeyPressed

    private void txttelefono1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txttelefono1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txttelefono1KeyPressed

    private void txtnombre1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnombre1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnombre1KeyPressed

    private void txtfecha_nacimiento1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfecha_nacimiento1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtfecha_nacimiento1KeyPressed

    private void txtfecha_nacimiento1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfecha_nacimiento1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtfecha_nacimiento1KeyReleased

    private void txtfecha_nacimiento1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfecha_nacimiento1KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtfecha_nacimiento1KeyTyped

    private void jLzona1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLzona1MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jLzona1MouseReleased

    private void jLzona1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jLzona1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jLzona1KeyReleased

    private void txtzona1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtzona1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtzona1KeyPressed

    private void txtzona1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtzona1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtzona1KeyReleased

    private void btnnuevo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevo1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnnuevo1ActionPerformed

    private void btnguardar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardar1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnguardar1ActionPerformed

    private void btneditar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneditar1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btneditar1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnactualizar_tabla;
    private javax.swing.JButton btncrearcredito_inicio;
    private javax.swing.JButton btndeletar;
    private javax.swing.JButton btndeletar1;
    private javax.swing.JButton btneditar;
    private javax.swing.JButton btneditar1;
    private javax.swing.JButton btnguardar;
    private javax.swing.JButton btnguardar1;
    private javax.swing.JButton btnnuevo;
    private javax.swing.JButton btnnuevo1;
    private javax.swing.JButton btnpagar_credito;
    private javax.swing.ButtonGroup gru_tipo;
    private javax.swing.JCheckBox jCescredito;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLayeredPane jLayeredPane2;
    private javax.swing.JList<String> jLzona;
    private javax.swing.JList<String> jLzona1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JRadioButton jRtipo_cliente;
    private javax.swing.JRadioButton jRtipo_cliente1;
    private javax.swing.JRadioButton jRtipo_funcionario;
    private javax.swing.JRadioButton jRtipo_funcionario1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblfecnac;
    private javax.swing.JLabel lblfecnac1;
    private javax.swing.JPanel panel_insert;
    private javax.swing.JPanel panel_insert1;
    private javax.swing.JPanel panel_tabla_cliente;
    private javax.swing.JPanel panel_tabla_cliente1;
    private javax.swing.JPanel panel_tabla_cliente2;
    public static javax.swing.JTable tblcliente_credito_resumen;
    public static javax.swing.JTable tblcredito_cliente;
    public static javax.swing.JTable tblgrupo_credito_cliente;
    public static javax.swing.JTextField txtdelivery;
    public static javax.swing.JTextField txtdelivery1;
    private javax.swing.JTextField txtdia_limite_credito;
    private javax.swing.JTextArea txtdireccion;
    private javax.swing.JTextArea txtdireccion1;
    private javax.swing.JTextField txtfec_inicio_credito;
    private javax.swing.JTextField txtfecha_inicio;
    private javax.swing.JTextField txtfecha_inicio1;
    private javax.swing.JTextField txtfecha_nacimiento;
    private javax.swing.JTextField txtfecha_nacimiento1;
    private javax.swing.JTextField txtidcliente;
    private javax.swing.JTextField txtidcliente1;
    private javax.swing.JTextField txtnombre;
    private javax.swing.JTextField txtnombre1;
    private javax.swing.JTextField txtruc;
    private javax.swing.JTextField txtruc1;
    private javax.swing.JTextField txtsaldo_credito;
    private javax.swing.JTextField txttelefono;
    private javax.swing.JTextField txttelefono1;
    public static javax.swing.JTextField txtzona;
    public static javax.swing.JTextField txtzona1;
    // End of variables declaration//GEN-END:variables
}
