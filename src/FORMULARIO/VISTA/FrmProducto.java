/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.VISTA;

import BASEDATO.LOCAL.ConnPostgres;
import BASEDATO.EvenConexion;
import Evento.Color.cla_color_pelete;
import Evento.Grafico.EvenSQLDataSet;
import Evento.Grafico.FunFreeChard;
import Evento.JTextField.EvenJTextField;
import Evento.Jframe.EvenJFRAME;
import Evento.Jtable.EvenJtable;
import FORMULARIO.BO.*;
import FORMULARIO.DAO.*;
import FORMULARIO.ENTIDAD.*;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import javax.swing.JOptionPane;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author Digno
 */
public class FrmProducto extends javax.swing.JInternalFrame {

    EvenJFRAME eveJfra = new EvenJFRAME();
    EvenJtable eveJtab = new EvenJtable();
    EvenConexion eveconn = new EvenConexion();
    producto prod = new producto();
    BO_producto pBO = new BO_producto();
    DAO_producto pdao = new DAO_producto();
    producto_categoria cate = new producto_categoria();
    producto_unidad unid = new producto_unidad();
    EvenJTextField evejtf = new EvenJTextField();
    EvenSQLDataSet evedata = new EvenSQLDataSet();
    FunFreeChard ffchar = new FunFreeChard();
    Connection conn = ConnPostgres.getConnPosgres();
    cla_color_pelete clacolor = new cla_color_pelete();
    private boolean isCargado_idcategoria;
    private boolean isCargado_idunidad;
    private boolean isCargado_idmarca;

    /**
     * Creates new form FrmZonaDelivery
     */
    void abrir_formulario() {
        this.setTitle("PRODUCTO");
        eveJfra.centrar_formulario_internalframa(this);
        reestableser();
        pdao.actualizar_tabla_producto(conn, tblproducto);
        color_formulario();
    }

    void color_formulario() {
        panel_insertar_producto.setBackground(clacolor.getColor_insertar_primario());
        panel_tabla_producto.setBackground(clacolor.getColor_tabla());
        panel_base_1.setBackground(clacolor.getColor_base());
    }

    boolean validar_guardar() {
        if (evejtf.getBoo_JTextField_vacio(txt2_codbarra, "DEBE CARGAR UN CODIGO DE BARRA")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txt3_nombre, "DEBE CARGAR UN NOMBRE")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txt4_pventa_minorista, "DEBE CARGAR UN PRECIO VENTA MINORISTA")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txt5_pventa_mayorista, "DEBE CARGAR UN PRECIO VENTA MAYORISTA")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txt6_cantidad_mayorista, "DEBE CARGAR UN CANTIDAD MAYORISTA")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txt7_pcompra, "DEBE CARGAR UN PRECIO COMPRA")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txt8_stock, "DEBE CARGAR UN STOCK")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txt9_stock_min, "DEBE CARGAR UN STOCK MINIMO")) {
            return false;
        }
        if (!isCargado_idcategoria) {
            JOptionPane.showMessageDialog(txt16_categoria, "NO SE CARGO CORRECTAMENTE LA CATEGORIA", "ERROR", JOptionPane.ERROR_MESSAGE);
            txt16_categoria.setText(null);
            txt16_categoria.grabFocus();
            return false;
        } else {
            txt16_categoria.setBackground(Color.white);
        }
        if (!isCargado_idunidad) {
            JOptionPane.showMessageDialog(txt15_unidad, "NO SE CARGO CORRECTAMENTE LA UNIDAD", "ERROR", JOptionPane.ERROR_MESSAGE);
            txt15_unidad.setText(null);
            txt15_unidad.grabFocus();
            return false;
        } else {
            txt15_unidad.setBackground(Color.white);
        }
        if (!isCargado_idmarca) {
            JOptionPane.showMessageDialog(txt17_marca, "NO SE CARGO CORRECTAMENTE LA MARCA", "ERROR", JOptionPane.ERROR_MESSAGE);
            txt17_marca.setText(null);
            txt17_marca.grabFocus();
            return false;
        } else {
            txt17_marca.setBackground(Color.white);
        }
        return true;
    }

    void boton_guardar() {
        if (validar_guardar()) {
            prod.setC2cod_barra(txt2_codbarra.getText());
            prod.setC3nombre(txt3_nombre.getText());
            prod.setC4precio_venta_minorista(evejtf.getDouble_format_nro_entero(txt4_pventa_minorista));
            prod.setC5precio_venta_mayorista(evejtf.getDouble_format_nro_entero(txt5_pventa_mayorista));
            prod.setC6cantidad_mayorista(Double.parseDouble(txt6_cantidad_mayorista.getText()));
            prod.setC7precio_compra(evejtf.getDouble_format_nro_entero(txt7_pcompra));
            prod.setC8stock(Double.parseDouble(txt8_stock.getText()));
            prod.setC9stock_min(Double.parseDouble(txt9_stock_min.getText()));
            prod.setC10activar(jC10_activar.isSelected());
            prod.setC11venta_mayorista(JC11ventamayorista.isSelected());
            prod.setC12promocion(jC12_promocion.isSelected());
            pBO.insertar_producto(prod, tblproducto);
            reestableser();
        }
    }

    void boton_editar() {
        if (validar_guardar()) {
            prod.setC2cod_barra(txt2_codbarra.getText());
            prod.setC3nombre(txt3_nombre.getText());
            prod.setC4precio_venta_minorista(evejtf.getDouble_format_nro_entero(txt4_pventa_minorista));
            prod.setC5precio_venta_mayorista(evejtf.getDouble_format_nro_entero(txt5_pventa_mayorista));
            prod.setC6cantidad_mayorista(Double.parseDouble(txt6_cantidad_mayorista.getText()));
            prod.setC7precio_compra(evejtf.getDouble_format_nro_entero(txt7_pcompra));
            prod.setC8stock(Double.parseDouble(txt8_stock.getText()));
            prod.setC9stock_min(Double.parseDouble(txt9_stock_min.getText()));
            prod.setC10activar(jC10_activar.isSelected());
            prod.setC11venta_mayorista(JC11ventamayorista.isSelected());
            prod.setC12promocion(jC12_promocion.isSelected());
            pBO.update_producto(prod, tblproducto);
        }
    }

    void seleccionar_tabla() {
        int idproducto = eveJtab.getInt_select_id(tblproducto);
        pdao.cargar_producto_por_idproducto(conn, prod, idproducto);
        p1_txtid.setText(String.valueOf(prod.getC1idproducto()));
        txt2_codbarra.setText(prod.getC2cod_barra());
        txt3_nombre.setText(prod.getC3nombre());
        txt4_pventa_minorista.setText(evejtf.getString_format_nro_decimal(prod.getC4precio_venta_minorista()));
        txt5_pventa_mayorista.setText(evejtf.getString_format_nro_decimal(prod.getC5precio_venta_mayorista()));
        txt6_cantidad_mayorista.setText(evejtf.getString_format_nro_entero(prod.getC6cantidad_mayorista()));
        txt7_pcompra.setText(evejtf.getString_format_nro_decimal(prod.getC7precio_compra()));
        txt8_stock.setText(evejtf.getString_format_nro_entero(prod.getC8stock()));
        txt9_stock_min.setText(evejtf.getString_format_nro_entero(prod.getC9stock_min()));
        jC10_activar.setSelected(prod.getC10activar());
        JC11ventamayorista.setSelected(prod.getC11venta_mayorista());
        jC12_promocion.setSelected(prod.getC12promocion());
        txt16_categoria.setText(prod.getC19_categoria());
        txt15_unidad.setText(prod.getC18_unidad());
        txt17_marca.setText(prod.getC20_marca());
        isCargado_idcategoria = true;
        isCargado_idunidad = true;
        isCargado_idmarca = true;
        btnguardar.setEnabled(false);
        btneditar.setEnabled(true);
    }

    void reestableser() {
        isCargado_idcategoria = false;
        isCargado_idunidad = false;
        isCargado_idmarca = false;
        jList_categoria.setVisible(false);
        jList_unidad.setVisible(false);
        jList_marca.setVisible(false);
        p1_txtid.setText(null);
        txt2_codbarra.setText(null);
        txt3_nombre.setText(null);
        txt4_pventa_minorista.setText(null);
        txt5_pventa_mayorista.setText(null);
        txt6_cantidad_mayorista.setText(null);
        txt7_pcompra.setText(null);
        txt8_stock.setText(null);
        txt9_stock_min.setText(null);
        jC10_activar.setSelected(true);
        JC11ventamayorista.setSelected(false);
        jC12_promocion.setSelected(true);
        txt16_categoria.setText(null);
        txt15_unidad.setText(null);
        txt17_marca.setText(null);
        btnguardar.setEnabled(true);
        btneditar.setEnabled(false);
        btndeletar.setEnabled(false);
        txt2_codbarra.grabFocus();
    }

    void cargar_id_categoria() {
        int idcategoria = eveconn.getInt_seleccionar_JLista(conn, txt16_categoria, jList_categoria, cate.getTabla(), cate.getNombretabla(), cate.getIdtabla());
        prod.setC16fk_idproducto_categoria(idcategoria);
        isCargado_idcategoria = true;
        txt15_unidad.grabFocus();
    }

    void cargar_id_unidad() {
        int idunidad = eveconn.getInt_seleccionar_JLista(conn, txt15_unidad, jList_unidad, unid.getTabla(), unid.getNombretabla(), unid.getIdtabla());
        prod.setC15fk_idproducto_unidad(idunidad);
        isCargado_idunidad = true;
        txt17_marca.grabFocus();
    }

    void cargar_id_marca() {
        int idmarca = eveconn.getInt_seleccionar_JLista(conn, txt17_marca, jList_marca, "producto_marca", "nombre", "idproducto_marca");
        prod.setC17fk_idproducto_marca(idmarca);
        isCargado_idmarca = true;
        txt2_codbarra.grabFocus();
    }

    void boton_nuevo() {
        reestableser();
    }

    void buscar_cod_barra_producto() {
        if (pdao.getBoolean_cargar_producto_por_codbarra(conn, prod, txt2_codbarra.getText())) {
            p1_txtid.setText(String.valueOf(prod.getC1idproducto()));
            txt2_codbarra.setText(prod.getC2cod_barra());
            txt3_nombre.setText(prod.getC3nombre());
            txt4_pventa_minorista.setText(evejtf.getString_format_nro_decimal(prod.getC4precio_venta_minorista()));
            txt5_pventa_mayorista.setText(evejtf.getString_format_nro_decimal(prod.getC5precio_venta_mayorista()));
            txt6_cantidad_mayorista.setText(evejtf.getString_format_nro_entero(prod.getC6cantidad_mayorista()));
            txt7_pcompra.setText(evejtf.getString_format_nro_decimal(prod.getC7precio_compra()));
            txt8_stock.setText(evejtf.getString_format_nro_entero(prod.getC8stock()));
            txt9_stock_min.setText(evejtf.getString_format_nro_entero(prod.getC9stock_min()));
            jC10_activar.setSelected(prod.getC10activar());
            JC11ventamayorista.setSelected(prod.getC11venta_mayorista());
            jC12_promocion.setSelected(prod.getC12promocion());
            txt16_categoria.setText(prod.getC19_categoria());
            txt15_unidad.setText(prod.getC18_unidad());
            txt17_marca.setText(prod.getC20_marca());
            isCargado_idcategoria = true;
            isCargado_idunidad = true;
            isCargado_idmarca = true;
            btnguardar.setEnabled(false);
            btneditar.setEnabled(true);
        } else {
            txt3_nombre.grabFocus();
//            JOptionPane.showMessageDialog(null, "NO SE ENCONTRO NINGUN PRODUCTO", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public FrmProducto() {
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

        pro_gru = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        panel_base_1 = new javax.swing.JPanel();
        panel_insertar_producto = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        p1_txtid = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txt3_nombre = new javax.swing.JTextField();
        jC10_activar = new javax.swing.JCheckBox();
        JC11ventamayorista = new javax.swing.JCheckBox();
        jC12_promocion = new javax.swing.JCheckBox();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jList_unidad = new javax.swing.JList<>();
        jList_categoria = new javax.swing.JList<>();
        jList_marca = new javax.swing.JList<>();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txt16_categoria = new javax.swing.JTextField();
        txt17_marca = new javax.swing.JTextField();
        txt15_unidad = new javax.swing.JTextField();
        btnnuevo = new javax.swing.JButton();
        btnguardar = new javax.swing.JButton();
        btneditar = new javax.swing.JButton();
        btndeletar = new javax.swing.JButton();
        btnnuevo_unidad = new javax.swing.JButton();
        btnnuevo_categoria = new javax.swing.JButton();
        btnnuevo_marca = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txt2_codbarra = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txt4_pventa_minorista = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txt5_pventa_mayorista = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txt7_pcompra = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        txt6_cantidad_mayorista = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txt8_stock = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txt9_stock_min = new javax.swing.JTextField();
        panel_tabla_producto = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblproducto = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        txtbuscar_categoria = new javax.swing.JTextField();
        txtbuscar_unidad = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtbuscar_nombre = new javax.swing.JTextField();
        txtbuscar_marca = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();

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

        panel_insertar_producto.setBackground(new java.awt.Color(153, 204, 255));
        panel_insertar_producto.setBorder(javax.swing.BorderFactory.createTitledBorder("CREAR DATO"));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("ID:");

        p1_txtid.setEditable(false);
        p1_txtid.setBackground(new java.awt.Color(204, 204, 204));
        p1_txtid.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        p1_txtid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                p1_txtidActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("NOMBRE:");

        txt3_nombre.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txt3_nombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt3_nombreKeyPressed(evt);
            }
        });

        jC10_activar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jC10_activar.setText("ACTIVAR (MOSTRAR)");

        JC11ventamayorista.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        JC11ventamayorista.setText("VENTA MAYORISTA");

        jC12_promocion.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jC12_promocion.setText("PROMOCION");

        jLayeredPane1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLayeredPane1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jList_unidad.setBackground(new java.awt.Color(204, 204, 255));
        jList_unidad.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jList_unidad.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jList_unidad.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jList_unidad.setSelectionBackground(new java.awt.Color(255, 51, 51));
        jList_unidad.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jList_unidadMouseReleased(evt);
            }
        });
        jList_unidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jList_unidadKeyPressed(evt);
            }
        });
        jLayeredPane1.add(jList_unidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 70, 150, -1));

        jList_categoria.setBackground(new java.awt.Color(204, 204, 255));
        jList_categoria.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jList_categoria.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jList_categoria.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jList_categoria.setSelectionBackground(new java.awt.Color(255, 51, 51));
        jList_categoria.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jList_categoriaMouseReleased(evt);
            }
        });
        jList_categoria.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jList_categoriaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jList_categoriaKeyReleased(evt);
            }
        });
        jLayeredPane1.add(jList_categoria, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 160, -1));

        jList_marca.setBackground(new java.awt.Color(204, 204, 255));
        jList_marca.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jList_marca.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jList_marca.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jList_marca.setSelectionBackground(new java.awt.Color(255, 51, 51));
        jList_marca.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jList_marcaMouseReleased(evt);
            }
        });
        jList_marca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jList_marcaKeyPressed(evt);
            }
        });
        jLayeredPane1.add(jList_marca, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 70, 150, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("UNIDAD:");
        jLayeredPane1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 10, -1, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("CATEGORIA:");
        jLayeredPane1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        txt16_categoria.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt16_categoria.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt16_categoriaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt16_categoriaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt16_categoriaKeyTyped(evt);
            }
        });
        jLayeredPane1.add(txt16_categoria, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 160, -1));

        txt17_marca.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt17_marca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt17_marcaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt17_marcaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt17_marcaKeyTyped(evt);
            }
        });
        jLayeredPane1.add(txt17_marca, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 40, 150, -1));

        txt15_unidad.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt15_unidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt15_unidadKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt15_unidadKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt15_unidadKeyTyped(evt);
            }
        });
        jLayeredPane1.add(txt15_unidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 40, 150, -1));

        btnnuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/nuevo.png"))); // NOI18N
        btnnuevo.setText("NUEVO");
        btnnuevo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnnuevo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnnuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevoActionPerformed(evt);
            }
        });
        jLayeredPane1.add(btnnuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 80, -1));

        btnguardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/guardar.png"))); // NOI18N
        btnguardar.setText("GUARDAR");
        btnguardar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnguardar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnguardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguardarActionPerformed(evt);
            }
        });
        jLayeredPane1.add(btnguardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 130, 90, -1));

        btneditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/modificar.png"))); // NOI18N
        btneditar.setText("EDITAR");
        btneditar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btneditar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btneditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneditarActionPerformed(evt);
            }
        });
        jLayeredPane1.add(btneditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 130, 90, -1));

        btndeletar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/eliminar.png"))); // NOI18N
        btndeletar.setText("DELETAR");
        btndeletar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btndeletar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jLayeredPane1.add(btndeletar, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 130, 90, -1));

        btnnuevo_unidad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/mini_nuevo.png"))); // NOI18N
        btnnuevo_unidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevo_unidadActionPerformed(evt);
            }
        });
        jLayeredPane1.add(btnnuevo_unidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 10, 30, -1));

        btnnuevo_categoria.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/mini_nuevo.png"))); // NOI18N
        btnnuevo_categoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevo_categoriaActionPerformed(evt);
            }
        });
        jLayeredPane1.add(btnnuevo_categoria, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 10, 30, -1));

        btnnuevo_marca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/mini_nuevo.png"))); // NOI18N
        btnnuevo_marca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevo_marcaActionPerformed(evt);
            }
        });
        jLayeredPane1.add(btnnuevo_marca, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 10, 30, -1));

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel15.setText("MARCA:");
        jLayeredPane1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 10, -1, -1));

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setText("Cod BARRA:");

        txt2_codbarra.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txt2_codbarra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt2_codbarraKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt2_codbarraKeyTyped(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("PRECIOS"));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("V. MINORISTA:");

        txt4_pventa_minorista.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txt4_pventa_minorista.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txt4_pventa_minorista.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt4_pventa_minoristaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt4_pventa_minoristaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt4_pventa_minoristaKeyTyped(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setText("V. MAYORISTA:");

        txt5_pventa_mayorista.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txt5_pventa_mayorista.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txt5_pventa_mayorista.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt5_pventa_mayoristaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt5_pventa_mayoristaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt5_pventa_mayoristaKeyTyped(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("P. COMPRA:");

        txt7_pcompra.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txt7_pcompra.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txt7_pcompra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt7_pcompraKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt7_pcompraKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt7_pcompraKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txt5_pventa_mayorista, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
                    .addComponent(txt7_pcompra, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt4_pventa_minorista))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txt4_pventa_minorista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txt5_pventa_mayorista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel4)
                    .addComponent(txt7_pcompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 10, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(204, 204, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("CANTIDAD"));

        txt6_cantidad_mayorista.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txt6_cantidad_mayorista.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txt6_cantidad_mayorista.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt6_cantidad_mayoristaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt6_cantidad_mayoristaKeyTyped(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setText("CANT. MAYORISTA:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("STOCK ACTUAL:");

        txt8_stock.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txt8_stock.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txt8_stock.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt8_stockKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt8_stockKeyTyped(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel14.setText("STOCK MINIMO:");

        txt9_stock_min.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txt9_stock_min.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txt9_stock_min.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt9_stock_minKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt9_stock_minKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(jLabel5)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt9_stock_min, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txt8_stock, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txt6_cantidad_mayorista, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txt8_stock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txt9_stock_min, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt6_cantidad_mayorista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panel_insertar_productoLayout = new javax.swing.GroupLayout(panel_insertar_producto);
        panel_insertar_producto.setLayout(panel_insertar_productoLayout);
        panel_insertar_productoLayout.setHorizontalGroup(
            panel_insertar_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertar_productoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_insertar_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panel_insertar_productoLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt3_nombre))
                    .addGroup(panel_insertar_productoLayout.createSequentialGroup()
                        .addComponent(JC11ventamayorista)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jC10_activar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jC12_promocion))
                    .addGroup(panel_insertar_productoLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(44, 44, 44)
                        .addComponent(p1_txtid, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt2_codbarra, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_insertar_productoLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panel_insertar_productoLayout.createSequentialGroup()
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 519, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panel_insertar_productoLayout.setVerticalGroup(
            panel_insertar_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertar_productoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_insertar_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(p1_txtid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(txt2_codbarra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txt3_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 129, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(8, 8, 8)
                .addGroup(panel_insertar_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JC11ventamayorista)
                    .addComponent(jC10_activar)
                    .addComponent(jC12_promocion))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLayeredPane1)
                .addContainerGap())
        );

        panel_tabla_producto.setBackground(new java.awt.Color(51, 204, 255));
        panel_tabla_producto.setBorder(javax.swing.BorderFactory.createTitledBorder("TABLA"));

        tblproducto.setModel(new javax.swing.table.DefaultTableModel(
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
        tblproducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblproductoMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblproducto);

        jLabel8.setText("CATEGORIA:");

        txtbuscar_categoria.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscar_categoriaKeyReleased(evt);
            }
        });

        txtbuscar_unidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscar_unidadKeyReleased(evt);
            }
        });

        jLabel9.setText("UNIDAD:");

        jLabel10.setText("NOMBRE:");

        txtbuscar_nombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscar_nombreKeyReleased(evt);
            }
        });

        txtbuscar_marca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscar_marcaKeyReleased(evt);
            }
        });

        jLabel16.setText("MARCA:");

        javax.swing.GroupLayout panel_tabla_productoLayout = new javax.swing.GroupLayout(panel_tabla_producto);
        panel_tabla_producto.setLayout(panel_tabla_productoLayout);
        panel_tabla_productoLayout.setHorizontalGroup(
            panel_tabla_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_tabla_productoLayout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(panel_tabla_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(panel_tabla_productoLayout.createSequentialGroup()
                        .addGroup(panel_tabla_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(txtbuscar_categoria, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_tabla_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(txtbuscar_unidad, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_tabla_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16)
                            .addComponent(txtbuscar_marca, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_tabla_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(txtbuscar_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(179, Short.MAX_VALUE))))
        );
        panel_tabla_productoLayout.setVerticalGroup(
            panel_tabla_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_tabla_productoLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_tabla_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_tabla_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(panel_tabla_productoLayout.createSequentialGroup()
                            .addGroup(panel_tabla_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel8)
                                .addComponent(jLabel9))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(panel_tabla_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtbuscar_categoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtbuscar_unidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(panel_tabla_productoLayout.createSequentialGroup()
                            .addComponent(jLabel16)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtbuscar_marca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(panel_tabla_productoLayout.createSequentialGroup()
                            .addComponent(jLabel10)
                            .addGap(26, 26, 26)))
                    .addComponent(txtbuscar_nombre, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panel_base_1Layout = new javax.swing.GroupLayout(panel_base_1);
        panel_base_1.setLayout(panel_base_1Layout);
        panel_base_1Layout.setHorizontalGroup(
            panel_base_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_base_1Layout.createSequentialGroup()
                .addComponent(panel_insertar_producto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_tabla_producto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_base_1Layout.setVerticalGroup(
            panel_base_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_base_1Layout.createSequentialGroup()
                .addGroup(panel_base_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(panel_tabla_producto, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel_insertar_producto, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("PRODUCTO PAVA VENTA", panel_base_1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnguardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardarActionPerformed
        // TODO add your handling code here:
        boton_guardar();
    }//GEN-LAST:event_btnguardarActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        pdao.ancho_tabla_producto(tblproducto);
    }//GEN-LAST:event_formInternalFrameOpened

    private void tblproductoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblproductoMouseReleased
        // TODO add your handling code here:
        seleccionar_tabla();
    }//GEN-LAST:event_tblproductoMouseReleased

    private void btneditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneditarActionPerformed
        // TODO add your handling code here:
        boton_editar();
    }//GEN-LAST:event_btneditarActionPerformed

    private void btnnuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevoActionPerformed
        // TODO add your handling code here:
        boton_nuevo();
    }//GEN-LAST:event_btnnuevoActionPerformed

    private void txt3_nombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt3_nombreKeyPressed
        // TODO add your handling code here:
        evejtf.saltar_campo_enter(evt, txt3_nombre, txt4_pventa_minorista);
        if (evt.getKeyCode() == KeyEvent.VK_F1) {
            boton_guardar();
        }
    }//GEN-LAST:event_txt3_nombreKeyPressed

    private void txt4_pventa_minoristaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt4_pventa_minoristaKeyPressed
        // TODO add your handling code here:
        evejtf.saltar_campo_enter(evt, txt4_pventa_minorista, txt5_pventa_mayorista);
    }//GEN-LAST:event_txt4_pventa_minoristaKeyPressed

    private void txt4_pventa_minoristaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt4_pventa_minoristaKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txt4_pventa_minoristaKeyTyped

    private void txt7_pcompraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt7_pcompraKeyPressed
        // TODO add your handling code here:
        evejtf.saltar_campo_enter(evt, txt7_pcompra, txt8_stock);
    }//GEN-LAST:event_txt7_pcompraKeyPressed

    private void txt7_pcompraKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt7_pcompraKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txt7_pcompraKeyTyped

    private void txt8_stockKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt8_stockKeyPressed
        // TODO add your handling code here:
        evejtf.saltar_campo_enter(evt, txt8_stock, txt9_stock_min);
    }//GEN-LAST:event_txt8_stockKeyPressed

    private void txt8_stockKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt8_stockKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txt8_stockKeyTyped

    private void txt16_categoriaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt16_categoriaKeyPressed
        // TODO add your handling code here:
//        evejtf.saltar_campo_enter(evt, p12_txtcategoria, p13_txtunidad);
        evejtf.seleccionar_lista(evt, jList_categoria);

    }//GEN-LAST:event_txt16_categoriaKeyPressed

    private void txt16_categoriaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt16_categoriaKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txt16_categoriaKeyTyped

    private void txt15_unidadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt15_unidadKeyPressed
        // TODO add your handling code here:
        evejtf.seleccionar_lista(evt, jList_unidad);

    }//GEN-LAST:event_txt15_unidadKeyPressed

    private void txt15_unidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt15_unidadKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txt15_unidadKeyTyped

    private void txt16_categoriaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt16_categoriaKeyReleased
        // TODO add your handling code here:
        eveconn.buscar_cargar_Jlista(conn, txt16_categoria, jList_categoria, cate.getTabla(), cate.getNombretabla(), cate.getNombretabla(), 4);
        isCargado_idcategoria = false;
    }//GEN-LAST:event_txt16_categoriaKeyReleased

    private void jList_categoriaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList_categoriaMouseReleased
        // TODO add your handling code here:
        cargar_id_categoria();
    }//GEN-LAST:event_jList_categoriaMouseReleased

    private void jList_categoriaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jList_categoriaKeyReleased
        // TODO add your handling code here:

    }//GEN-LAST:event_jList_categoriaKeyReleased

    private void jList_categoriaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jList_categoriaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            cargar_id_categoria();
        }
    }//GEN-LAST:event_jList_categoriaKeyPressed

    private void jList_unidadMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList_unidadMouseReleased
        // TODO add your handling code here:
        cargar_id_unidad();
    }//GEN-LAST:event_jList_unidadMouseReleased

    private void jList_unidadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jList_unidadKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            cargar_id_unidad();
//            evejtf.saltar_campo_enter(evt, p13_txtunidad, p2_txtnombre);
        }
    }//GEN-LAST:event_jList_unidadKeyPressed

    private void txt15_unidadKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt15_unidadKeyReleased
        // TODO add your handling code here:
        eveconn.buscar_cargar_Jlista(conn, txt15_unidad, jList_unidad, unid.getTabla(), unid.getNombretabla(), unid.getNombretabla(), 4);
        isCargado_idunidad = false;
    }//GEN-LAST:event_txt15_unidadKeyReleased

    private void txtbuscar_categoriaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_categoriaKeyReleased
        // TODO add your handling code here:
        pdao.buscar_tabla_producto(conn, tblproducto, txtbuscar_categoria, 1);
    }//GEN-LAST:event_txtbuscar_categoriaKeyReleased

    private void txtbuscar_unidadKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_unidadKeyReleased
        // TODO add your handling code here:
        pdao.buscar_tabla_producto(conn, tblproducto, txtbuscar_unidad, 2);
    }//GEN-LAST:event_txtbuscar_unidadKeyReleased

    private void txtbuscar_nombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_nombreKeyReleased
        // TODO add your handling code here:
        pdao.buscar_tabla_producto(conn, tblproducto, txtbuscar_nombre, 3);
    }//GEN-LAST:event_txtbuscar_nombreKeyReleased

    private void btnnuevo_categoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevo_categoriaActionPerformed
        // TODO add your handling code here:
        eveJfra.abrir_TablaJinternal(new FrmProducto_categoria());
    }//GEN-LAST:event_btnnuevo_categoriaActionPerformed

    private void btnnuevo_unidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevo_unidadActionPerformed
        // TODO add your handling code here:
        eveJfra.abrir_TablaJinternal(new FrmProducto_unidad());
    }//GEN-LAST:event_btnnuevo_unidadActionPerformed

    private void txt4_pventa_minoristaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt4_pventa_minoristaKeyReleased
        // TODO add your handling code here:
        evejtf.getDouble_format_nro_entero(txt4_pventa_minorista);
    }//GEN-LAST:event_txt4_pventa_minoristaKeyReleased

    private void txt7_pcompraKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt7_pcompraKeyReleased
        // TODO add your handling code here:
        evejtf.getDouble_format_nro_entero(txt7_pcompra);
    }//GEN-LAST:event_txt7_pcompraKeyReleased

    private void txt2_codbarraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt2_codbarraKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            buscar_cod_barra_producto();
        }
//        evejtf.saltar_campo_enter(evt, txt2_codbarra, txt3_nombre);

    }//GEN-LAST:event_txt2_codbarraKeyPressed

    private void txt2_codbarraKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt2_codbarraKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txt2_codbarraKeyTyped

    private void txt5_pventa_mayoristaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt5_pventa_mayoristaKeyPressed
        // TODO add your handling code here:
        evejtf.saltar_campo_enter(evt, txt5_pventa_mayorista, txt7_pcompra);
    }//GEN-LAST:event_txt5_pventa_mayoristaKeyPressed

    private void txt5_pventa_mayoristaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt5_pventa_mayoristaKeyReleased
        // TODO add your handling code here:
        evejtf.getDouble_format_nro_entero(txt5_pventa_mayorista);
    }//GEN-LAST:event_txt5_pventa_mayoristaKeyReleased

    private void txt5_pventa_mayoristaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt5_pventa_mayoristaKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txt5_pventa_mayoristaKeyTyped

    private void txt6_cantidad_mayoristaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt6_cantidad_mayoristaKeyPressed
        // TODO add your handling code here:
        evejtf.saltar_campo_enter(evt, txt6_cantidad_mayorista, txt16_categoria);
    }//GEN-LAST:event_txt6_cantidad_mayoristaKeyPressed

    private void txt6_cantidad_mayoristaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt6_cantidad_mayoristaKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txt6_cantidad_mayoristaKeyTyped

    private void txt9_stock_minKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt9_stock_minKeyPressed
        // TODO add your handling code here:
        evejtf.saltar_campo_enter(evt, txt9_stock_min, txt6_cantidad_mayorista);
    }//GEN-LAST:event_txt9_stock_minKeyPressed

    private void txt9_stock_minKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt9_stock_minKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txt9_stock_minKeyTyped

    private void p1_txtidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_p1_txtidActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_p1_txtidActionPerformed

    private void jList_marcaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList_marcaMouseReleased
        // TODO add your handling code here:
        cargar_id_marca();
    }//GEN-LAST:event_jList_marcaMouseReleased

    private void jList_marcaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jList_marcaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            cargar_id_marca();
        }
    }//GEN-LAST:event_jList_marcaKeyPressed

    private void txt17_marcaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt17_marcaKeyPressed
        // TODO add your handling code here:
        evejtf.seleccionar_lista(evt, jList_marca);
    }//GEN-LAST:event_txt17_marcaKeyPressed

    private void txt17_marcaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt17_marcaKeyReleased
        // TODO add your handling code here:
        eveconn.buscar_cargar_Jlista(conn, txt17_marca, jList_marca, "producto_marca", "nombre", "nombre", 4);
        isCargado_idmarca = false;
    }//GEN-LAST:event_txt17_marcaKeyReleased

    private void txt17_marcaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt17_marcaKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txt17_marcaKeyTyped

    private void btnnuevo_marcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevo_marcaActionPerformed
        // TODO add your handling code here:
        eveJfra.abrir_TablaJinternal(new FrmProducto_marca());
    }//GEN-LAST:event_btnnuevo_marcaActionPerformed

    private void txtbuscar_marcaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_marcaKeyReleased
        // TODO add your handling code here:
        pdao.buscar_tabla_producto(conn, tblproducto, txtbuscar_marca, 4);
    }//GEN-LAST:event_txtbuscar_marcaKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox JC11ventamayorista;
    private javax.swing.JButton btndeletar;
    private javax.swing.JButton btneditar;
    private javax.swing.JButton btnguardar;
    private javax.swing.JButton btnnuevo;
    private javax.swing.JButton btnnuevo_categoria;
    private javax.swing.JButton btnnuevo_marca;
    private javax.swing.JButton btnnuevo_unidad;
    private javax.swing.JCheckBox jC10_activar;
    private javax.swing.JCheckBox jC12_promocion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JList<String> jList_categoria;
    private javax.swing.JList<String> jList_marca;
    private javax.swing.JList<String> jList_unidad;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField p1_txtid;
    private javax.swing.JPanel panel_base_1;
    private javax.swing.JPanel panel_insertar_producto;
    private javax.swing.JPanel panel_tabla_producto;
    private javax.swing.ButtonGroup pro_gru;
    private javax.swing.JTable tblproducto;
    private javax.swing.JTextField txt15_unidad;
    private javax.swing.JTextField txt16_categoria;
    private javax.swing.JTextField txt17_marca;
    private javax.swing.JTextField txt2_codbarra;
    private javax.swing.JTextField txt3_nombre;
    private javax.swing.JTextField txt4_pventa_minorista;
    private javax.swing.JTextField txt5_pventa_mayorista;
    private javax.swing.JTextField txt6_cantidad_mayorista;
    private javax.swing.JTextField txt7_pcompra;
    private javax.swing.JTextField txt8_stock;
    private javax.swing.JTextField txt9_stock_min;
    private javax.swing.JTextField txtbuscar_categoria;
    private javax.swing.JTextField txtbuscar_marca;
    private javax.swing.JTextField txtbuscar_nombre;
    private javax.swing.JTextField txtbuscar_unidad;
    // End of variables declaration//GEN-END:variables
}
