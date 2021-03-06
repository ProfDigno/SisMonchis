/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.VISTA;

import BASEDATO.EvenConexion;
import BASEDATO.LOCAL.ConnPostgres;
import BASEDATO.LOCAL.VariablesBD;
import CONFIGURACION.ClaCorteAdmin;
import Config_JSON.json_config;
import Config_JSON.json_imprimir_pos;
import Evento.Color.cla_color_pelete;
import Evento.Fecha.EvenFecha;
import Evento.Grafico.EvenSQLDataSet;
import Evento.Grafico.FunFreeChard;
import Evento.Jframe.EvenJFRAME;
import FORMULARIO.DAO.DAO_caja_cierre;
import FORMULARIO.DAO.DAO_caja_cierre_alquilado;
import FORMULARIO.DAO.DAO_cliente;
import FORMULARIO.DAO.DAO_cotizacion;
import FORMULARIO.DAO.DAO_factura;
import FORMULARIO.DAO.DAO_producto;
import FORMULARIO.DAO.DAO_producto_categoria;
import FORMULARIO.DAO.DAO_venta;
import FORMULARIO.ENTIDAD.caja_cierre;
import FORMULARIO.ENTIDAD.caja_cierre_alquilado;
import FORMULARIO.ENTIDAD.cotizacion;
import FORMULARIO.ENTIDAD.factura;
import FORMULARIO.ENTIDAD.financista;
import INSERT_AUTO.Cla_insert_automatico;
import java.awt.Color;
import java.sql.Connection;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JOptionPane;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author Digno
 */
public class FrmMenuMonchis extends javax.swing.JFrame {

    /**
     * Creates new form FrmMenuQchurron
     */
    Connection conn = null;
    Connection connSER = null;
    ConnPostgres conPs = new ConnPostgres();
    VariablesBD var = new VariablesBD();
    control_vista covi = new control_vista();
    EvenJFRAME evetbl = new EvenJFRAME();
    EvenFecha evefec = new EvenFecha();
    cotizacion coti = new cotizacion();
    DAO_cotizacion codao = new DAO_cotizacion();
    factura fac = new factura();
    DAO_factura fdao = new DAO_factura();
    DAO_cliente cdao = new DAO_cliente();
    DAO_venta vdao = new DAO_venta();
    DAO_producto pdao = new DAO_producto();
    DAO_producto_categoria pcDAO = new DAO_producto_categoria();
    Cla_insert_automatico ins_auto = new Cla_insert_automatico();
    private financista fina = new financista();
    EvenConexion eveconn = new EvenConexion();
    caja_cierre cjcie = new caja_cierre();
    DAO_caja_cierre cjcie_dao = new DAO_caja_cierre();
    caja_cierre_alquilado cjciea = new caja_cierre_alquilado();
    DAO_caja_cierre_alquilado cjciea_dao = new DAO_caja_cierre_alquilado();
    cla_color_pelete clacolor = new cla_color_pelete();
    json_config jsconfig = new json_config();
    json_imprimir_pos jsprint = new json_imprimir_pos();
    EvenSQLDataSet evedata = new EvenSQLDataSet();
    FunFreeChard chard = new FunFreeChard();
    ClaCorteAdmin corte = new ClaCorteAdmin();
    private Timer tiempo;
    boolean vergrafico = true;
    int seg_ver_grafico = 0;
    String estado_CERRADO="CERRADO";
    void abrir_formulario() {
        conPs.ConnectDBpostgres(conn, false, false);
        conn = conPs.getConnPosgres();
        covi.setComanda_abierto(true);
        jsconfig.cargar_jsom_configuracion();
        jsprint.cargar_jsom_imprimir_pos();
        this.setExtendedState(MAXIMIZED_BOTH);
        iniciarTiempo();
        habilitar_menu(false);
        codao.cargar_cotizacion(coti, 1);
        txtvercion.setText("V:" + jsconfig.getVersion());
        corte.setFecha_corte("2022-04-24");
        jFdolar.setValue(coti.getDolar_guarani());
        jFreal.setValue(coti.getReal_guarani());
        grafico_mas_vendidos();
        grafico_venta_semanal();
//        pdao.actualizar_tabla_producto_stock_minimo(conn, tbl_producto_stock_minimo);
        pcDAO.update_orden_categoria_mas_vendido_mes();
        iniciar_color();
        actualizacion_version_v1();
        
//        primer_finanza();
    }

    void actualizacion_version_v1() {
        /**
         * ALTER TABLE cliente ADD COLUMN delivery boolean; update cliente set
         * delivery=true; update gastos set fk_idusuario=7 where fk_idusuario=1;
         * alter table itemventacomanda alter column preciocompra type
         * numeric(14,0) using preciocompra::numeric;
         */
        String sql = "DO $$ \n"
                + "    BEGIN\n"
                + "        BEGIN\n"
                + " ALTER TABLE producto ADD COLUMN precio_venta_promocion NUMERIC(15,0) DEFAULT 0;\n "
                + " ALTER TABLE producto ADD COLUMN stock_fijo NUMERIC(15,0) DEFAULT 0; \n"
//                + " ALTER TABLE credito_cliente ADD COLUMN fecha_vence TIMESTAMP DEFAULT current_date; \n"
//                + " update producto set alquilado=false;\n"
//                + " ALTER TABLE compra ADD COLUMN alquilado boolean; \n"
//                + " update compra set alquilado=false;\n"
                + ""        
                + "        EXCEPTION\n"
                + "            WHEN duplicate_column THEN RAISE NOTICE 'duplicate_column.';\n"
                + "        END;\n"
                + "    END;\n"
                + "$$ ";
        eveconn.SQL_execute_libre(conn, sql);
    }
//    void primer_finanza(){
//        int idfinancista = (eveconn.getInt_ultimoID_mas_uno(conn, fina.getTb_financista(), fina.getId_idfinancista()));
//        if(idfinancista==0){
//            evetbl.abrir_TablaJinternal(new FrmFinancista());
//        }
//    }

    void ocultar_grafico() {
        panel_mas_vendidos.setVisible(!jCocultar_grafico.isSelected());
//        panel_stock_minimo.setVisible(!jCocultar_grafico.isSelected());
        panel_venta_semanal.setVisible(!jCocultar_grafico.isSelected());
    }

    void iniciar_color() {
        clacolor.setColor_insertar_primario(new Color(137, 201, 184));
        clacolor.setColor_insertar_secundario(new Color(132, 169, 172));
        clacolor.setColor_tabla(new Color(217, 173, 173));
        clacolor.setColor_referencia(new Color(239, 187, 207));
        clacolor.setColor_base(new Color(134, 117, 169));
        clacolor.setColor_shopp(new Color(255, 244, 125));
    }

    void titulo_sistema(String servidor) {
        String titulo = jsconfig.getNombre_sistema() + " V." + jsconfig.getVersion()
                + " BD: " + var.getPsLocalhost() + "/" + var.getPsPort() + "/" + var.getPsNomBD()
                + " Fecha: " + jsconfig.getFecha_sis() + servidor;
        this.setTitle(titulo);
    }

    void conectar_servidor(boolean mensaje) {
        String servidor = "";
        titulo_sistema(servidor);
    }

    private void habilitar_menu(boolean blo) {
        FrmMenuMonchis.btncaja_detalle.setEnabled(blo);
        FrmMenuMonchis.btncliente.setEnabled(blo);
        FrmMenuMonchis.btngasto.setEnabled(blo);
        FrmMenuMonchis.btnproducto.setEnabled(blo);
        FrmMenuMonchis.btnvale.setEnabled(blo);
        FrmMenuMonchis.btnventa.setEnabled(blo);
        FrmMenuMonchis.btncategoria.setEnabled(blo);
        FrmMenuMonchis.btnshopp.setEnabled(blo);
        FrmMenuMonchis.btncambiar_usuario.setEnabled(blo);
        FrmMenuMonchis.btncaja_cierre.setEnabled(blo);
        FrmMenuMonchis.btndelivery_venta.setEnabled(blo);
        FrmMenuMonchis.btncajacerrar.setEnabled(blo);
        FrmMenuMonchis.jMenu_caja.setEnabled(blo);
        FrmMenuMonchis.jMenu_cliente.setEnabled(blo);
        FrmMenuMonchis.jMenu_config.setEnabled(blo);
        FrmMenuMonchis.jMenu_delivery.setEnabled(blo);
        FrmMenuMonchis.jMenu_gasto.setEnabled(blo);
        FrmMenuMonchis.jMenu_producto.setEnabled(blo);
        FrmMenuMonchis.jMenu_venta.setEnabled(blo);
        FrmMenuMonchis.jMenu_fatura.setEnabled(blo);
        FrmMenuMonchis.jMenu_compra.setEnabled(blo);
        FrmMenuMonchis.jMenu_inventario.setEnabled(blo);
        FrmMenuMonchis.btncomprainsumo.setEnabled(blo);
        FrmMenuMonchis.btncotizacion.setEnabled(blo);
        FrmMenuMonchis.btninventario.setEnabled(blo);
    }

    void iniciarTiempo() {
        tiempo = new Timer();
        //le asignamos una tarea al timer
        tiempo.schedule(new FrmMenuMonchis.clasetimer(), 0, 1000 * 1);
        System.out.println("Timer Iniciado en COMANDA");
    }

    void pararTiempo() {
        tiempo.cancel();
        System.out.println("Timer Parado en COMANDA");
    }

    class clasetimer extends TimerTask {

        private int contador_segundo;

        public void run() {
            contador_segundo++;
            txtfechahora.setText(evefec.getString_formato_hora());
            if (contador_segundo >= 60) {
                contador_segundo = 0;
            }
            if (contador_segundo >= 10) {
                if (vergrafico) {
                    jCocultar_grafico.setSelected(true);
                    ocultar_grafico();
                    vergrafico = false;
                }
            }
        }
    }

    private void abrir_caja_cierre() {
        int idcaja_cierre = (eveconn.getInt_ultimoID_max(conn, cjcie.getTb_caja_cierre(), cjcie.getId_idcaja_cierre()));
        cjcie.setC1idcaja_cierre(idcaja_cierre);
        cjcie_dao.cargar_caja_cierre(cjcie);
        if (cjcie.getC4estado().equals(estado_CERRADO)) {
            JOptionPane.showMessageDialog(null, "NO HAY CAJA ABIERTA SE DEBE ABRIR UNO NUEVO");
            evetbl.abrir_TablaJinternal(new FrmCaja_Abrir());
        } else {
            evetbl.abrir_TablaJinternal(new FrmCaja_Cierre());
        }
    }
    private void abrir_caja_cierre_alquiler() {
        int idcaja_cierre = (eveconn.getInt_ultimoID_max(conn, cjciea.getTb_caja_cierre_alquilado(), cjciea.getId_idcaja_cierre_alquilado()));
        cjciea.setC1idcaja_cierre_alquilado(idcaja_cierre);
        cjciea_dao.cargar_caja_cierre_alquilado(conn, cjciea, idcaja_cierre);
        if (cjciea.getC4estado().equals(estado_CERRADO)) {
            JOptionPane.showMessageDialog(null, "NO HAY CAJA ABIERTA SE DEBE ABRIR UNO NUEVO");
            evetbl.abrir_TablaJinternal(new FrmCaja_Abrir_alquiler());
        } else {
            evetbl.abrir_TablaJinternal(new FrmCaja_Cierre_alquiler());
        }
    }
    void grafico_mas_vendidos() {
        int cant_dias = 7;
        int top = 20;
        DefaultCategoryDataset dataset = evedata.getDataset_producto_mas_vendido(conn, cant_dias, top);
        String titulo = "TOP " + top + " MAS VENDIDOS ultimos " + cant_dias + " dias";
        String plano_horizontal = "PRODUCTOS";
        String plano_vertical = "CANTIDAD";
        chard.crear_graficoBar3D(panel_mas_vendidos, dataset, titulo, plano_horizontal, plano_vertical);
    }

    void grafico_venta_semanal() {
        DefaultCategoryDataset dataset = evedata.getDataset_venta_semanal(conn);
        String titulo = "VENTA SEMANAL 4 SEMANA";
        String plano_horizontal = "SEMANA";
        String plano_vertical = "MONTO";
        chard.crear_graficoBar3D(panel_venta_semanal, dataset, titulo, plano_horizontal, plano_vertical);
    }

    public FrmMenuMonchis() {
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

        escritorio = new javax.swing.JDesktopPane();
        btncliente = new javax.swing.JButton();
        btnproducto = new javax.swing.JButton();
        btnventa = new javax.swing.JButton();
        btncaja_detalle = new javax.swing.JButton();
        btngasto = new javax.swing.JButton();
        btnvale = new javax.swing.JButton();
        lblusuario = new javax.swing.JLabel();
        btncategoria = new javax.swing.JButton();
        btnshopp = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jFdolar = new javax.swing.JFormattedTextField();
        jFreal = new javax.swing.JFormattedTextField();
        btncambiar_usuario = new javax.swing.JButton();
        txtfechahora = new javax.swing.JLabel();
        txtvercion = new javax.swing.JLabel();
        btncomprainsumo = new javax.swing.JButton();
        btncotizacion = new javax.swing.JButton();
        btndelivery_venta = new javax.swing.JButton();
        btncajacerrar = new javax.swing.JButton();
        btncaja_cierre = new javax.swing.JButton();
        btninventario = new javax.swing.JButton();
        panel_mas_vendidos = new javax.swing.JPanel();
        jCocultar_grafico = new javax.swing.JCheckBox();
        panel_venta_semanal = new javax.swing.JPanel();
        btncajacerraralquiler = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu_venta = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem33 = new javax.swing.JMenuItem();
        jMenuItem34 = new javax.swing.JMenuItem();
        jMenu_compra = new javax.swing.JMenu();
        jMenuItem14 = new javax.swing.JMenuItem();
        jMenuItem19 = new javax.swing.JMenuItem();
        jMenu6 = new javax.swing.JMenu();
        jMenuItem20 = new javax.swing.JMenuItem();
        jMenu_cliente = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu_producto = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem32 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenu7 = new javax.swing.JMenu();
        jMenuItem35 = new javax.swing.JMenuItem();
        jMenuItem21 = new javax.swing.JMenuItem();
        jMenuItem22 = new javax.swing.JMenuItem();
        jMenuItem23 = new javax.swing.JMenuItem();
        jMenu_delivery = new javax.swing.JMenu();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem27 = new javax.swing.JMenuItem();
        jMenu_config = new javax.swing.JMenu();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem15 = new javax.swing.JMenuItem();
        jMenuItem16 = new javax.swing.JMenuItem();
        jMenuItem17 = new javax.swing.JMenuItem();
        jMenu_caja = new javax.swing.JMenu();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenuItem28 = new javax.swing.JMenuItem();
        jMenuItem29 = new javax.swing.JMenuItem();
        jMenuItem30 = new javax.swing.JMenuItem();
        jMenu_gasto = new javax.swing.JMenu();
        jMenuItem12 = new javax.swing.JMenuItem();
        jMenuItem13 = new javax.swing.JMenuItem();
        jMenu_fatura = new javax.swing.JMenu();
        jMenuItem18 = new javax.swing.JMenuItem();
        jMenu_inventario = new javax.swing.JMenu();
        jMenuItem24 = new javax.swing.JMenuItem();
        jMenuItem25 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem26 = new javax.swing.JMenuItem();
        jMenuItem31 = new javax.swing.JMenuItem();
        jMenu8 = new javax.swing.JMenu();
        jMenu9 = new javax.swing.JMenu();
        jMenuItem36 = new javax.swing.JMenuItem();
        jMenuItem37 = new javax.swing.JMenuItem();
        jMenuItem38 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jMenuItem39 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        btncliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/MENU/cliente.png"))); // NOI18N
        btncliente.setText("CLIENTE/FUNCIO");
        btncliente.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btncliente.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btncliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnclienteActionPerformed(evt);
            }
        });

        btnproducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/MENU/producto_beer.png"))); // NOI18N
        btnproducto.setText("PRODUCTO");
        btnproducto.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnproducto.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnproducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnproductoActionPerformed(evt);
            }
        });

        btnventa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/MENU/venta_beer.png"))); // NOI18N
        btnventa.setText("VENTA");
        btnventa.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnventa.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnventa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnventaActionPerformed(evt);
            }
        });

        btncaja_detalle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/MENU/caja_calendar.png"))); // NOI18N
        btncaja_detalle.setText("CAJA DIARIO");
        btncaja_detalle.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btncaja_detalle.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btncaja_detalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncaja_detalleActionPerformed(evt);
            }
        });

        btngasto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/MENU/gasto.png"))); // NOI18N
        btngasto.setText("GASTO");
        btngasto.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btngasto.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btngasto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btngastoActionPerformed(evt);
            }
        });

        btnvale.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/MENU/vale.png"))); // NOI18N
        btnvale.setText("VALE");
        btnvale.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnvale.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnvale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnvaleActionPerformed(evt);
            }
        });

        lblusuario.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblusuario.setForeground(new java.awt.Color(255, 255, 255));
        lblusuario.setText("usuario");

        btncategoria.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/MENU/categoria.png"))); // NOI18N
        btncategoria.setText("CATEGORIA");
        btncategoria.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btncategoria.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btncategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncategoriaActionPerformed(evt);
            }
        });

        btnshopp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/MENU/shopp.png"))); // NOI18N
        btnshopp.setText("CHOPP");
        btnshopp.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnshopp.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnshopp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnshoppActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(102, 204, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("COTIZACION"));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("DOLAR:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("REAL:");

        jFdolar.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFdolar.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFdolar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jFreal.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFreal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFreal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jFreal))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFdolar, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jFdolar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jFreal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btncambiar_usuario.setText("CAMBIAR USUARIO");
        btncambiar_usuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncambiar_usuarioActionPerformed(evt);
            }
        });

        txtfechahora.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtfechahora.setForeground(new java.awt.Color(255, 255, 255));
        txtfechahora.setText("jLabel3");

        txtvercion.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtvercion.setForeground(new java.awt.Color(255, 255, 255));
        txtvercion.setText("jLabel3");

        btncomprainsumo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/MENU/compra.png"))); // NOI18N
        btncomprainsumo.setText("COMPRA");
        btncomprainsumo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btncomprainsumo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btncomprainsumo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncomprainsumoActionPerformed(evt);
            }
        });

        btncotizacion.setText("COTIZACION");
        btncotizacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncotizacionActionPerformed(evt);
            }
        });

        btndelivery_venta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/MENU/delivery.png"))); // NOI18N
        btndelivery_venta.setText("DELIVERY");
        btndelivery_venta.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btndelivery_venta.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btndelivery_venta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndelivery_ventaActionPerformed(evt);
            }
        });

        btncajacerrar.setText("CAJA CERRAR");
        btncajacerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncajacerrarActionPerformed(evt);
            }
        });

        btncaja_cierre.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/MENU/caja_cierre.png"))); // NOI18N
        btncaja_cierre.setText("CAJA CIERRE");
        btncaja_cierre.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btncaja_cierre.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btncaja_cierre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncaja_cierreActionPerformed(evt);
            }
        });

        btninventario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/MENU/inventario.png"))); // NOI18N
        btninventario.setText("INVENTARIO");
        btninventario.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btninventario.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btninventario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btninventarioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_mas_vendidosLayout = new javax.swing.GroupLayout(panel_mas_vendidos);
        panel_mas_vendidos.setLayout(panel_mas_vendidosLayout);
        panel_mas_vendidosLayout.setHorizontalGroup(
            panel_mas_vendidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 553, Short.MAX_VALUE)
        );
        panel_mas_vendidosLayout.setVerticalGroup(
            panel_mas_vendidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jCocultar_grafico.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jCocultar_grafico.setForeground(new java.awt.Color(255, 255, 255));
        jCocultar_grafico.setText("OCULTAR GRAFICO");
        jCocultar_grafico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCocultar_graficoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_venta_semanalLayout = new javax.swing.GroupLayout(panel_venta_semanal);
        panel_venta_semanal.setLayout(panel_venta_semanalLayout);
        panel_venta_semanalLayout.setHorizontalGroup(
            panel_venta_semanalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panel_venta_semanalLayout.setVerticalGroup(
            panel_venta_semanalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 252, Short.MAX_VALUE)
        );

        btncajacerraralquiler.setText("CAJA CERRAR ALQUILER");
        btncajacerraralquiler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncajacerraralquilerActionPerformed(evt);
            }
        });

        escritorio.setLayer(btncliente, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorio.setLayer(btnproducto, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorio.setLayer(btnventa, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorio.setLayer(btncaja_detalle, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorio.setLayer(btngasto, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorio.setLayer(btnvale, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorio.setLayer(lblusuario, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorio.setLayer(btncategoria, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorio.setLayer(btnshopp, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorio.setLayer(jPanel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorio.setLayer(btncambiar_usuario, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorio.setLayer(txtfechahora, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorio.setLayer(txtvercion, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorio.setLayer(btncomprainsumo, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorio.setLayer(btncotizacion, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorio.setLayer(btndelivery_venta, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorio.setLayer(btncajacerrar, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorio.setLayer(btncaja_cierre, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorio.setLayer(btninventario, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorio.setLayer(panel_mas_vendidos, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorio.setLayer(jCocultar_grafico, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorio.setLayer(panel_venta_semanal, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorio.setLayer(btncajacerraralquiler, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout escritorioLayout = new javax.swing.GroupLayout(escritorio);
        escritorio.setLayout(escritorioLayout);
        escritorioLayout.setHorizontalGroup(
            escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(escritorioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(escritorioLayout.createSequentialGroup()
                        .addGroup(escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnventa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btngasto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btncomprainsumo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnvale, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnproducto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btncaja_detalle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btncaja_cierre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btncategoria, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(escritorioLayout.createSequentialGroup()
                                .addComponent(btncliente)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btndelivery_venta))
                            .addGroup(escritorioLayout.createSequentialGroup()
                                .addComponent(btnshopp, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btninventario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(escritorioLayout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addGroup(escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(escritorioLayout.createSequentialGroup()
                                        .addComponent(txtfechahora)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtvercion)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblusuario))
                                    .addComponent(jCocultar_grafico)))
                            .addGroup(escritorioLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btncajacerraralquiler, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
                                    .addComponent(btncajacerrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btncotizacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btncambiar_usuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 95, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(escritorioLayout.createSequentialGroup()
                        .addComponent(panel_mas_vendidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panel_venta_semanal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        escritorioLayout.setVerticalGroup(
            escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(escritorioLayout.createSequentialGroup()
                .addGroup(escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(escritorioLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(escritorioLayout.createSequentialGroup()
                                .addGroup(escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnventa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnproducto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btncategoria, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btncliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btndelivery_venta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btncomprainsumo, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnvale, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnshopp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btngasto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btncaja_detalle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btncaja_cierre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btninventario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(escritorioLayout.createSequentialGroup()
                                .addGroup(escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtfechahora)
                                    .addComponent(txtvercion)
                                    .addComponent(lblusuario))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btncajacerrar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btncajacerraralquiler)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btncotizacion)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btncambiar_usuario)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jCocultar_grafico)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panel_venta_semanal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel_mas_vendidos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(70, Short.MAX_VALUE))
        );

        jMenu_venta.setText("VENTA");

        jMenuItem1.setText("CREAR VENTA");
        jMenu_venta.add(jMenuItem1);

        jMenu3.setText("REPORTE");

        jMenuItem33.setText("VENTA TODOS");
        jMenuItem33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem33ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem33);

        jMenuItem34.setText("VENTA DETALLE");
        jMenuItem34.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem34ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem34);

        jMenu_venta.add(jMenu3);

        jMenuBar1.add(jMenu_venta);

        jMenu_compra.setText("COMPRA");

        jMenuItem14.setText("PROVEEDOR");
        jMenuItem14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem14ActionPerformed(evt);
            }
        });
        jMenu_compra.add(jMenuItem14);

        jMenuItem19.setText("COMPRA PRODUCTO");
        jMenuItem19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem19ActionPerformed(evt);
            }
        });
        jMenu_compra.add(jMenuItem19);

        jMenu6.setText("REPORTE");

        jMenuItem20.setText("COMPRA TODOS");
        jMenuItem20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem20ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem20);

        jMenu_compra.add(jMenu6);

        jMenuBar1.add(jMenu_compra);

        jMenu_cliente.setText("CLIENTE");

        jMenuItem2.setText("CREAR CLIENTE");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu_cliente.add(jMenuItem2);

        jMenuItem3.setText("ZONA DELIVERY");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu_cliente.add(jMenuItem3);

        jMenuBar1.add(jMenu_cliente);

        jMenu_producto.setText("PRODUCTO");

        jMenuItem4.setText("CREAR PRODUCTO");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu_producto.add(jMenuItem4);

        jMenuItem32.setText("MARCA");
        jMenuItem32.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem32ActionPerformed(evt);
            }
        });
        jMenu_producto.add(jMenuItem32);

        jMenuItem5.setText("CATEGORIA");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu_producto.add(jMenuItem5);

        jMenuItem6.setText("UNIDAD");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu_producto.add(jMenuItem6);

        jMenu7.setText("REPORTE");

        jMenuItem35.setText("VENTA PRODUCTO POR CATEGORIA");
        jMenuItem35.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem35ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem35);

        jMenuItem21.setText("INVENTARIO VALORIZADO VENTA");
        jMenuItem21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem21ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem21);

        jMenuItem22.setText("INVENTARIO VALORIZADO COMPRA");
        jMenuItem22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem22ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem22);

        jMenuItem23.setText("GANANCIA COMPRA VENTA POR FECHA");
        jMenuItem23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem23ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem23);

        jMenu_producto.add(jMenu7);

        jMenuBar1.add(jMenu_producto);

        jMenu_delivery.setText("DELIVERY");

        jMenuItem8.setText("ZONA");
        jMenu_delivery.add(jMenuItem8);

        jMenuItem9.setText("ENTREGADOR");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu_delivery.add(jMenuItem9);

        jMenuItem27.setText("DELIVERY VENTA");
        jMenuItem27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem27ActionPerformed(evt);
            }
        });
        jMenu_delivery.add(jMenuItem27);

        jMenuBar1.add(jMenu_delivery);

        jMenu_config.setText("CONFIGURACION");

        jMenuItem10.setText("USUARIO");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu_config.add(jMenuItem10);

        jMenu1.setText("BACKUP");

        jMenuItem15.setText("DATOS BACKUP");
        jMenuItem15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem15ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem15);

        jMenuItem16.setText("CREAR BACKUP");
        jMenuItem16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem16ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem16);

        jMenu_config.add(jMenu1);

        jMenuItem17.setText("COTIZACION");
        jMenuItem17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem17ActionPerformed(evt);
            }
        });
        jMenu_config.add(jMenuItem17);

        jMenuBar1.add(jMenu_config);

        jMenu_caja.setText("CAJA");

        jMenuItem11.setText("CAJA DIARIO");
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        jMenu_caja.add(jMenuItem11);

        jMenuItem28.setText("CAJA ABRIR");
        jMenuItem28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem28ActionPerformed(evt);
            }
        });
        jMenu_caja.add(jMenuItem28);

        jMenuItem29.setText("CAJA CIERRE");
        jMenuItem29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem29ActionPerformed(evt);
            }
        });
        jMenu_caja.add(jMenuItem29);

        jMenuItem30.setText("CAJA ABRIR-CERRAR");
        jMenuItem30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem30ActionPerformed(evt);
            }
        });
        jMenu_caja.add(jMenuItem30);

        jMenuBar1.add(jMenu_caja);

        jMenu_gasto.setText("GASTO");

        jMenuItem12.setText("GASTO");
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12ActionPerformed(evt);
            }
        });
        jMenu_gasto.add(jMenuItem12);

        jMenuItem13.setText("GASTO TIPO");
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem13ActionPerformed(evt);
            }
        });
        jMenu_gasto.add(jMenuItem13);

        jMenuBar1.add(jMenu_gasto);

        jMenu_fatura.setText("FACTURA");

        jMenuItem18.setText("FILTRO FACTURA");
        jMenuItem18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem18ActionPerformed(evt);
            }
        });
        jMenu_fatura.add(jMenuItem18);

        jMenuBar1.add(jMenu_fatura);

        jMenu_inventario.setText("INVENTARIO");

        jMenuItem24.setText("CREAR INVENTARIO");
        jMenuItem24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem24ActionPerformed(evt);
            }
        });
        jMenu_inventario.add(jMenuItem24);

        jMenuItem25.setText("TODO INVENTARIO");
        jMenuItem25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem25ActionPerformed(evt);
            }
        });
        jMenu_inventario.add(jMenuItem25);

        jMenuBar1.add(jMenu_inventario);

        jMenu2.setText("FINANZA");

        jMenuItem7.setText("FINANCISTA");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem7);

        jMenuBar1.add(jMenu2);

        jMenu4.setText("BANCO");

        jMenuItem26.setText("NOMBRE BANCO");
        jMenuItem26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem26ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem26);

        jMenuItem31.setText("DATO BANCO");
        jMenuItem31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem31ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem31);

        jMenuBar1.add(jMenu4);

        jMenu8.setText("ALQUILER");

        jMenu9.setText("CAJA");

        jMenuItem36.setText("CAJA ABRIR-CERRAR");
        jMenuItem36.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem36ActionPerformed(evt);
            }
        });
        jMenu9.add(jMenuItem36);

        jMenuItem37.setText("CAJA DIARIO");
        jMenuItem37.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem37ActionPerformed(evt);
            }
        });
        jMenu9.add(jMenuItem37);

        jMenuItem38.setText("CAJA CIERRE");
        jMenuItem38.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem38ActionPerformed(evt);
            }
        });
        jMenu9.add(jMenuItem38);

        jMenu8.add(jMenu9);

        jMenu5.setText("REPORTE");

        jMenuItem39.setText("VENTA ALQUILER TODOS");
        jMenuItem39.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem39ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem39);

        jMenu8.add(jMenu5);

        jMenuBar1.add(jMenu8);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(escritorio)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(escritorio)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmZonaDelivery());
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmProducto_categoria());
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmEntregador());
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmProducto_unidad());
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmCliente());
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void btnclienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnclienteActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmCliente());
    }//GEN-LAST:event_btnclienteActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmProducto());
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void btnproductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnproductoActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmProducto());
    }//GEN-LAST:event_btnproductoActionPerformed

    private void btnventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnventaActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmVenta());
    }//GEN-LAST:event_btnventaActionPerformed

    private void btncaja_detalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncaja_detalleActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmCajaDetalle());
    }//GEN-LAST:event_btncaja_detalleActionPerformed

    private void jMenuItem13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem13ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmGasto_tipo());
    }//GEN-LAST:event_jMenuItem13ActionPerformed

    private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmGasto());
    }//GEN-LAST:event_jMenuItem12ActionPerformed

    private void btngastoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btngastoActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmGasto());
    }//GEN-LAST:event_btngastoActionPerformed

    private void btnvaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnvaleActionPerformed
        // TODO add your handling code here:
//        evetbl.abrir_TablaJinternal(new FrmVale());
    }//GEN-LAST:event_btnvaleActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmUsuario());
    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        conectar_servidor(true);
        if (corte.verificar_corte_admin(conn)) {
            JDiaLogin log = new JDiaLogin(this, true);
            log.setVisible(true);
        }
//        pdao.ancho_tabla_producto_stock_minimo(tbl_producto_stock_minimo);
    }//GEN-LAST:event_formWindowOpened

    private void jMenuItem15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem15ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmBackup());
    }//GEN-LAST:event_jMenuItem15ActionPerformed

    private void jMenuItem16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem16ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmCrearBackup());
    }//GEN-LAST:event_jMenuItem16ActionPerformed

    private void btncategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncategoriaActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmProducto_categoria());
    }//GEN-LAST:event_btncategoriaActionPerformed

    private void btnshoppActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnshoppActionPerformed
        // TODO add your handling code here:
//        evetbl.abrir_TablaJinternal(new FrmBalanceCaja());
        evetbl.abrir_TablaJinternal(new FrmVenta_alquiler());
    }//GEN-LAST:event_btnshoppActionPerformed

    private void jMenuItem17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem17ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmCotizacion());
    }//GEN-LAST:event_jMenuItem17ActionPerformed

    private void jMenuItem18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem18ActionPerformed
        // TODO add your handling code here:
        fac.setFactura_cargada(false);
        evetbl.abrir_TablaJinternal(new FrmFactura());
    }//GEN-LAST:event_jMenuItem18ActionPerformed

    private void btncambiar_usuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncambiar_usuarioActionPerformed
        // TODO add your handling code here:
        JDiaLogin log = new JDiaLogin(this, true);
        log.setVisible(true);
    }//GEN-LAST:event_btncambiar_usuarioActionPerformed

    private void btncomprainsumoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncomprainsumoActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmCompra());
    }//GEN-LAST:event_btncomprainsumoActionPerformed

    private void btncotizacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncotizacionActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmCotizacion());
    }//GEN-LAST:event_btncotizacionActionPerformed

    private void btndelivery_ventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndelivery_ventaActionPerformed
        // TODO add your handling code here:
//        evetbl.abrir_TablaJinternal(new FrmEntregador_venta());
    }//GEN-LAST:event_btndelivery_ventaActionPerformed

    private void jMenuItem27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem27ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmEntregador_venta());
    }//GEN-LAST:event_jMenuItem27ActionPerformed

    private void jMenuItem28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem28ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmCaja_Abrir());
    }//GEN-LAST:event_jMenuItem28ActionPerformed

    private void jMenuItem29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem29ActionPerformed
        // TODO add your handling code here:
        abrir_caja_cierre();
    }//GEN-LAST:event_jMenuItem29ActionPerformed

    private void jMenuItem30ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem30ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmCaja_abrir_cerrar());
    }//GEN-LAST:event_jMenuItem30ActionPerformed

    private void btncajacerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncajacerrarActionPerformed
        // TODO add your handling code here:
        abrir_caja_cierre();
    }//GEN-LAST:event_btncajacerrarActionPerformed

    private void jMenuItem32ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem32ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmProducto_marca());
    }//GEN-LAST:event_jMenuItem32ActionPerformed

    private void btncaja_cierreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncaja_cierreActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmCaja_abrir_cerrar());
    }//GEN-LAST:event_btncaja_cierreActionPerformed

    private void jMenuItem33ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem33ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmRepVenta());
    }//GEN-LAST:event_jMenuItem33ActionPerformed

    private void jMenuItem34ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem34ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmRepVentaDetalle());
    }//GEN-LAST:event_jMenuItem34ActionPerformed

    private void jMenuItem14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem14ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmProveedor());
    }//GEN-LAST:event_jMenuItem14ActionPerformed

    private void jMenuItem19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem19ActionPerformed
        // TODO add your handling code here:.
        evetbl.abrir_TablaJinternal(new FrmCompra());
    }//GEN-LAST:event_jMenuItem19ActionPerformed

    private void jMenuItem20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem20ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmRepCompra());
    }//GEN-LAST:event_jMenuItem20ActionPerformed

    private void jMenuItem35ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem35ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmRepVentaProductoCaategoria());
    }//GEN-LAST:event_jMenuItem35ActionPerformed

    private void jMenuItem21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem21ActionPerformed
        // TODO add your handling code here:
//        pdao.imprimir_rep_inventario_venta(conn);
        evetbl.abrir_TablaJinternal(new FrmRepInventarioValorizadoVenta());
    }//GEN-LAST:event_jMenuItem21ActionPerformed

    private void jMenuItem22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem22ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmRepInventarioValorizadoCompra());
    }//GEN-LAST:event_jMenuItem22ActionPerformed

    private void jMenuItem23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem23ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmRepGananciaCompraVenta());
    }//GEN-LAST:event_jMenuItem23ActionPerformed

    private void jMenuItem24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem24ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmCrearInventario());
    }//GEN-LAST:event_jMenuItem24ActionPerformed

    private void jMenuItem25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem25ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmInventario());
    }//GEN-LAST:event_jMenuItem25ActionPerformed

    private void btninventarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btninventarioActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmCrearInventario());
    }//GEN-LAST:event_btninventarioActionPerformed

    private void jCocultar_graficoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCocultar_graficoActionPerformed
        // TODO add your handling code here:
        ocultar_grafico();
    }//GEN-LAST:event_jCocultar_graficoActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmFinancista());
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem26ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmBanco());
    }//GEN-LAST:event_jMenuItem26ActionPerformed

    private void jMenuItem31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem31ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmDato_Banco());
    }//GEN-LAST:event_jMenuItem31ActionPerformed

    private void btncajacerraralquilerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncajacerraralquilerActionPerformed
        // TODO add your handling code here:
        abrir_caja_cierre_alquiler();
    }//GEN-LAST:event_btncajacerraralquilerActionPerformed

    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed
        // TODO add your handling code here:
         evetbl.abrir_TablaJinternal(new FrmCajaDetalle());
    }//GEN-LAST:event_jMenuItem11ActionPerformed

    private void jMenuItem36ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem36ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmCaja_abrir_cerrar_alquiler());
    }//GEN-LAST:event_jMenuItem36ActionPerformed

    private void jMenuItem38ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem38ActionPerformed
        // TODO add your handling code here:
        abrir_caja_cierre_alquiler();
    }//GEN-LAST:event_jMenuItem38ActionPerformed

    private void jMenuItem37ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem37ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmCajaDetalle_alquiler());
    }//GEN-LAST:event_jMenuItem37ActionPerformed

    private void jMenuItem39ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem39ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmRepVenta_alquiler());
    }//GEN-LAST:event_jMenuItem39ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmMenuMonchis.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmMenuMonchis.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmMenuMonchis.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmMenuMonchis.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmMenuMonchis().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton btncaja_cierre;
    public static javax.swing.JButton btncaja_detalle;
    public static javax.swing.JButton btncajacerrar;
    public static javax.swing.JButton btncajacerraralquiler;
    public static javax.swing.JButton btncambiar_usuario;
    public static javax.swing.JButton btncategoria;
    public static javax.swing.JButton btncliente;
    public static javax.swing.JButton btncomprainsumo;
    public static javax.swing.JButton btncotizacion;
    public static javax.swing.JButton btndelivery_venta;
    public static javax.swing.JButton btngasto;
    public static javax.swing.JButton btninventario;
    public static javax.swing.JButton btnproducto;
    public static javax.swing.JButton btnshopp;
    public static javax.swing.JButton btnvale;
    public static javax.swing.JButton btnventa;
    public static javax.swing.JDesktopPane escritorio;
    private javax.swing.JCheckBox jCocultar_grafico;
    public static javax.swing.JFormattedTextField jFdolar;
    public static javax.swing.JFormattedTextField jFreal;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenu jMenu8;
    private javax.swing.JMenu jMenu9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem16;
    private javax.swing.JMenuItem jMenuItem17;
    private javax.swing.JMenuItem jMenuItem18;
    private javax.swing.JMenuItem jMenuItem19;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem20;
    private javax.swing.JMenuItem jMenuItem21;
    private javax.swing.JMenuItem jMenuItem22;
    private javax.swing.JMenuItem jMenuItem23;
    private javax.swing.JMenuItem jMenuItem24;
    private javax.swing.JMenuItem jMenuItem25;
    private javax.swing.JMenuItem jMenuItem26;
    private javax.swing.JMenuItem jMenuItem27;
    private javax.swing.JMenuItem jMenuItem28;
    private javax.swing.JMenuItem jMenuItem29;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem30;
    private javax.swing.JMenuItem jMenuItem31;
    private javax.swing.JMenuItem jMenuItem32;
    private javax.swing.JMenuItem jMenuItem33;
    private javax.swing.JMenuItem jMenuItem34;
    private javax.swing.JMenuItem jMenuItem35;
    private javax.swing.JMenuItem jMenuItem36;
    private javax.swing.JMenuItem jMenuItem37;
    private javax.swing.JMenuItem jMenuItem38;
    private javax.swing.JMenuItem jMenuItem39;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    public static javax.swing.JMenu jMenu_caja;
    public static javax.swing.JMenu jMenu_cliente;
    public static javax.swing.JMenu jMenu_compra;
    public static javax.swing.JMenu jMenu_config;
    public static javax.swing.JMenu jMenu_delivery;
    public static javax.swing.JMenu jMenu_fatura;
    public static javax.swing.JMenu jMenu_gasto;
    public static javax.swing.JMenu jMenu_inventario;
    public static javax.swing.JMenu jMenu_producto;
    public static javax.swing.JMenu jMenu_venta;
    private javax.swing.JPanel jPanel1;
    public static javax.swing.JLabel lblusuario;
    private javax.swing.JPanel panel_mas_vendidos;
    private javax.swing.JPanel panel_venta_semanal;
    private javax.swing.JLabel txtfechahora;
    private javax.swing.JLabel txtvercion;
    // End of variables declaration//GEN-END:variables
}
