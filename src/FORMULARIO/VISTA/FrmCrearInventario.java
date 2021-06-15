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
import Evento.Grafico.EvenSQLDataSet;
import Evento.Grafico.FunFreeChard;
import Evento.JTextField.EvenJTextField;
import Evento.Jframe.EvenJFRAME;
import Evento.Jtable.EvenJtable;
import FORMULARIO.BO.BO_inventario;
import FORMULARIO.BO.BO_item_inventario;
import FORMULARIO.BO.BO_producto;
import FORMULARIO.DAO.DAO_inventario;
import FORMULARIO.DAO.DAO_item_inventario;
import FORMULARIO.DAO.DAO_producto;
import FORMULARIO.ENTIDAD.inventario;
import FORMULARIO.ENTIDAD.item_inventario;
import FORMULARIO.ENTIDAD.producto;
import FORMULARIO.ENTIDAD.producto_categoria;
import FORMULARIO.ENTIDAD.producto_unidad;
import FORMULARIO.ENTIDAD.usuario;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import javax.swing.JOptionPane;

/**
 *
 * @author Digno
 */
public class FrmCrearInventario extends javax.swing.JInternalFrame {

    EvenJFRAME eveJfra = new EvenJFRAME();
    EvenJtable eveJtab = new EvenJtable();
    EvenConexion eveconn = new EvenConexion();
    EvenFecha evefec = new EvenFecha();
    producto prod = new producto();
    BO_producto pBO = new BO_producto();
    DAO_producto pdao = new DAO_producto();
    producto_categoria cate = new producto_categoria();
    producto_unidad unid = new producto_unidad();
    inventario inve = new inventario();
    DAO_inventario indao = new DAO_inventario();
    BO_inventario inbo = new BO_inventario();
    item_inventario iinve = new item_inventario();
    DAO_item_inventario iidao = new DAO_item_inventario();
    BO_item_inventario iibo = new BO_item_inventario();
    EvenJTextField evejtf = new EvenJTextField();
    EvenSQLDataSet evedata = new EvenSQLDataSet();
    FunFreeChard ffchar = new FunFreeChard();
    Connection conn = ConnPostgres.getConnPosgres();
    cla_color_pelete clacolor = new cla_color_pelete();
    usuario usu = new usuario();
    private int idproducto;
    private int idinventario;

    void abrir_formulario() {
        this.setTitle("CREAR INVENTARIO");
        eveJfra.centrar_formulario_internalframa(this);
        reestableser();
        color_formulario();
    }

    void reestableser() {
        idproducto = -1;
        idinventario = indao.getInt_idinventario_iniciado(conn);
        if (idinventario >= 0) {
            indao.cargar_inventario(conn, inve, idinventario);
            txtidinventario.setText(String.valueOf(inve.getC1idinventario()));
            txtfechainicio.setText(inve.getC2fecha_inicio());
            txtdescripcion.setText(inve.getC4descripcion());
            iidao.actualizar_tabla_item_inventario_diferencia(conn, idinventario, tblitem_inventario);
            txtpro_codbarra.setVisible(true);
            btniniciar_inventario.setEnabled(false);
        } else {
            txtfechainicio.setText(evefec.getString_formato_fecha_hora());
            int id = eveconn.getInt_ultimoID_mas_uno(conn, inve.getTb_inventario(), inve.getId_idinventario());
            txtidinventario.setText(String.valueOf(id));
            txtdescripcion.setText(null);
            txtpro_codbarra.setVisible(false);
            btniniciar_inventario.setEnabled(true);
        }
    }

    void color_formulario() {
        panel_producto.setBackground(clacolor.getColor_insertar_primario());
        panel_tabla.setBackground(clacolor.getColor_tabla());
        panel_inventario.setBackground(clacolor.getColor_base());
    }

    boolean validar_inventario() {
        if (evejtf.getBoo_JTextField_vacio(txtdescripcion, "DEBE CARGAR UNA DESCRIPCION")) {
            return false;
        }
        return true;
    }

    void boton_crear_inventario() {
        if (validar_inventario()) {
            inve.setC4descripcion(txtdescripcion.getText());
            inve.setC5total_precio_venta(0);
            inve.setC6total_precio_compra(0);
            inve.setC7estado("INICIADO");
            inve.setC8fk_idusuario(usu.getGlobal_idusuario());
            inbo.insertar_inventario(inve);
            reestableser();
        }
    }

    void reestableser_producto() {
        txtpro_codbarra.setText(null);
        txtpro_nombre.setText(null);
        txtpro_pventa_mayo.setText(null);
        txtpro_precio_compra.setText(null);
        txtpro_stock_actual.setText(null);
        txtpro_categoria.setText(null);
        txtpro_unidad.setText(null);
        txtpro_marca.setText(null);
        txtstock_contado.setText(null);
        txtstock_contado.setEditable(false);
        txtpro_codbarra.grabFocus();
    }

    void buscar_producto_cargar() {
        if (pdao.getBoolean_cargar_producto_por_codbarra(conn, prod, txtpro_codbarra.getText())) {
            if (iidao.getBoolean_buscar_codbarra_de_item_inventario(conn, idinventario, txtpro_codbarra.getText())) {
                JOptionPane.showMessageDialog(null, "ESTE PRODUCTO YA ESTA EN LA LISTA DE INVENTARIO");
            } else {
                idproducto = prod.getC1idproducto();
                txtpro_nombre.setText(prod.getC3nombre());
                txtpro_pventa_mayo.setText(evejtf.getString_format_nro_decimal(prod.getC5precio_venta_mayorista()));
                txtpro_precio_compra.setText(evejtf.getString_format_nro_decimal(prod.getC7precio_compra()));
                txtpro_stock_actual.setText(evejtf.getString_format_nro_entero(prod.getC8stock()));
                txtpro_categoria.setText(prod.getC19_categoria());
                txtpro_unidad.setText(prod.getC18_unidad());
                txtpro_marca.setText(prod.getC20_marca());
                txtstock_contado.setEditable(true);
                txtstock_contado.grabFocus();
            }
        } else {
            txtstock_contado.setEditable(false);
            JOptionPane.showMessageDialog(null, "EL PRODUCTO NO SE ENCONTRO");
        }
    }

    void boton_cargar_item_inventario() {
        if (txtstock_contado.getText().trim().length() > 0) {
            if (idproducto >= 0) {
                int stock = (int) prod.getC8stock();
                iinve.setC2stock_sistema(stock);
                int contado = Integer.parseInt(txtstock_contado.getText());
                iinve.setC3stock_contado(contado);
                iinve.setC4precio_venta(prod.getC5precio_venta_mayorista());
                iinve.setC5precio_compra(prod.getC7precio_compra());
                iinve.setC6estado("TEMP");
                iinve.setC7fk_idinventario(idinventario);
                iinve.setC8fk_idproducto(idproducto);
                iibo.insertar_item_inventario(iinve);
                iidao.actualizar_tabla_item_inventario_diferencia(conn, idinventario, tblitem_inventario);
                reestableser_producto();
            }else{
                JOptionPane.showMessageDialog(null, "EL PRODUCTO NO SE CARGO");
                txtpro_codbarra.grabFocus();
            }
        }else{
            JOptionPane.showMessageDialog(null, "CARGAR LA CANTIDAD EXISTENTE","ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }

    void boton_eliminar_item() {
        if (!eveJtab.getBoolean_validar_select(tblitem_inventario)) {
            int iditem_inventario = eveJtab.getInt_select_id(tblitem_inventario);
            iinve.setC6estado("DELETE");
            iinve.setC1iditem_inventario(iditem_inventario);
            iibo.delete_item_inventario(iinve);
            iidao.actualizar_tabla_item_inventario_diferencia(conn, idinventario, tblitem_inventario);
        }
    }

    void boton_terminar_inventario() {
        if (tblitem_inventario.getRowCount() > 0) {
            inve.setC7estado("TERMINADO");
            inve.setC1idinventario(idinventario);
            inbo.update_inventario_terminar(inve);
            reestableser();
            iidao.actualizar_tabla_item_inventario_diferencia(conn, idinventario, tblitem_inventario);
        } else {
            JOptionPane.showMessageDialog(null, "CARGAR POR LO MENOS UN PRODUCTO");
        }
    }

    public FrmCrearInventario() {
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

        jPanel1 = new javax.swing.JPanel();
        panel_inventario = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtidinventario = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtfechainicio = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtdescripcion = new javax.swing.JTextField();
        btniniciar_inventario = new javax.swing.JButton();
        btnterminar_inventario = new javax.swing.JButton();
        panel_producto = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtpro_codbarra = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtpro_nombre = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtpro_categoria = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtpro_marca = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtpro_unidad = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtpro_pventa_mayo = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtpro_precio_compra = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtpro_stock_actual = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        txtstock_contado = new javax.swing.JTextField();
        btncargar = new javax.swing.JButton();
        panel_tabla = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblitem_inventario = new javax.swing.JTable();
        btneliminar_item_inventario = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setResizable(true);
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

        panel_inventario.setBorder(javax.swing.BorderFactory.createTitledBorder("CREAR INVENTARIO"));

        jLabel1.setText("ID INVENTARIO:");

        jLabel2.setText("FECHA DE INICIO:");

        jLabel3.setText("DESCRIPCION:");

        btniniciar_inventario.setText("INICIAR INVENTARIO");
        btniniciar_inventario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btniniciar_inventarioActionPerformed(evt);
            }
        });

        btnterminar_inventario.setText("TERMINAR INVENTARIO");
        btnterminar_inventario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnterminar_inventarioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_inventarioLayout = new javax.swing.GroupLayout(panel_inventario);
        panel_inventario.setLayout(panel_inventarioLayout);
        panel_inventarioLayout.setHorizontalGroup(
            panel_inventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_inventarioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_inventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_inventarioLayout.createSequentialGroup()
                        .addGroup(panel_inventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_inventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtdescripcion)
                            .addGroup(panel_inventarioLayout.createSequentialGroup()
                                .addGroup(panel_inventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtidinventario, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtfechainicio, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(panel_inventarioLayout.createSequentialGroup()
                        .addComponent(btniniciar_inventario, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnterminar_inventario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panel_inventarioLayout.setVerticalGroup(
            panel_inventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_inventarioLayout.createSequentialGroup()
                .addGroup(panel_inventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtidinventario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_inventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtfechainicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_inventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtdescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addGroup(panel_inventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btniniciar_inventario)
                    .addComponent(btnterminar_inventario))
                .addContainerGap())
        );

        panel_producto.setBorder(javax.swing.BorderFactory.createTitledBorder("BUSCAR PRODUCTO"));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("COD BARRA:");

        txtpro_codbarra.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtpro_codbarra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtpro_codbarraKeyPressed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("NOMBRE:");

        txtpro_nombre.setEditable(false);
        txtpro_nombre.setBackground(new java.awt.Color(204, 204, 204));
        txtpro_nombre.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("CATEGORIA:");

        txtpro_categoria.setEditable(false);
        txtpro_categoria.setBackground(new java.awt.Color(204, 204, 204));
        txtpro_categoria.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("MARCA:");

        txtpro_marca.setEditable(false);
        txtpro_marca.setBackground(new java.awt.Color(204, 204, 204));
        txtpro_marca.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("UNIDAD:");

        txtpro_unidad.setEditable(false);
        txtpro_unidad.setBackground(new java.awt.Color(204, 204, 204));
        txtpro_unidad.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("P. VENTA MAYO:");

        txtpro_pventa_mayo.setEditable(false);
        txtpro_pventa_mayo.setBackground(new java.awt.Color(204, 204, 204));
        txtpro_pventa_mayo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("P. COMPRA:");

        txtpro_precio_compra.setEditable(false);
        txtpro_precio_compra.setBackground(new java.awt.Color(204, 204, 204));
        txtpro_precio_compra.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setText("STOCK ACTUAL:");

        txtpro_stock_actual.setEditable(false);
        txtpro_stock_actual.setBackground(new java.awt.Color(204, 204, 204));
        txtpro_stock_actual.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("CANTIDAD CONTADO"));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel12.setText("CONTADO:");

        txtstock_contado.setFont(new java.awt.Font("Stencil", 0, 24)); // NOI18N
        txtstock_contado.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtstock_contado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtstock_contadoKeyPressed(evt);
            }
        });

        btncargar.setText("CARGAR");
        btncargar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncargarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtstock_contado, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btncargar, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.CENTER, jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                .addComponent(txtstock_contado)
                .addComponent(btncargar, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.CENTER)
        );

        javax.swing.GroupLayout panel_productoLayout = new javax.swing.GroupLayout(panel_producto);
        panel_producto.setLayout(panel_productoLayout);
        panel_productoLayout.setHorizontalGroup(
            panel_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_productoLayout.createSequentialGroup()
                .addGroup(panel_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_productoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panel_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtpro_nombre)
                            .addGroup(panel_productoLayout.createSequentialGroup()
                                .addGroup(panel_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtpro_categoria)
                                    .addComponent(txtpro_marca)
                                    .addComponent(txtpro_unidad, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                                    .addComponent(txtpro_stock_actual, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                                    .addComponent(txtpro_precio_compra)
                                    .addComponent(txtpro_pventa_mayo))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(panel_productoLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtpro_codbarra, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_productoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panel_productoLayout.setVerticalGroup(
            panel_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_productoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtpro_codbarra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtpro_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtpro_categoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtpro_marca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtpro_unidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtpro_pventa_mayo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtpro_precio_compra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtpro_stock_actual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panel_tabla.setBorder(javax.swing.BorderFactory.createTitledBorder("ITEM INVENTARIO"));

        tblitem_inventario.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblitem_inventario);

        btneliminar_item_inventario.setText("ELIMINAR");
        btneliminar_item_inventario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneliminar_item_inventarioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_tablaLayout = new javax.swing.GroupLayout(panel_tabla);
        panel_tabla.setLayout(panel_tablaLayout);
        panel_tablaLayout.setHorizontalGroup(
            panel_tablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 591, Short.MAX_VALUE)
            .addGroup(panel_tablaLayout.createSequentialGroup()
                .addComponent(btneliminar_item_inventario)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panel_tablaLayout.setVerticalGroup(
            panel_tablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_tablaLayout.createSequentialGroup()
                .addComponent(jScrollPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btneliminar_item_inventario))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(panel_producto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel_inventario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_tabla, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_tabla, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(panel_inventario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_producto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btniniciar_inventarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btniniciar_inventarioActionPerformed
        // TODO add your handling code here:
        boton_crear_inventario();
    }//GEN-LAST:event_btniniciar_inventarioActionPerformed

    private void txtpro_codbarraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtpro_codbarraKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            buscar_producto_cargar();
        }
    }//GEN-LAST:event_txtpro_codbarraKeyPressed

    private void btncargarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncargarActionPerformed
        // TODO add your handling code here:
        boton_cargar_item_inventario();
    }//GEN-LAST:event_btncargarActionPerformed

    private void txtstock_contadoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtstock_contadoKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            boton_cargar_item_inventario();
        }
    }//GEN-LAST:event_txtstock_contadoKeyPressed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        iidao.ancho_tabla_item_inventario_diferencia(tblitem_inventario);
    }//GEN-LAST:event_formInternalFrameOpened

    private void btneliminar_item_inventarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneliminar_item_inventarioActionPerformed
        // TODO add your handling code here:
        boton_eliminar_item();
    }//GEN-LAST:event_btneliminar_item_inventarioActionPerformed

    private void btnterminar_inventarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnterminar_inventarioActionPerformed
        // TODO add your handling code here:
        boton_terminar_inventario();
    }//GEN-LAST:event_btnterminar_inventarioActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btncargar;
    private javax.swing.JButton btneliminar_item_inventario;
    private javax.swing.JButton btniniciar_inventario;
    private javax.swing.JButton btnterminar_inventario;
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panel_inventario;
    private javax.swing.JPanel panel_producto;
    private javax.swing.JPanel panel_tabla;
    private javax.swing.JTable tblitem_inventario;
    private javax.swing.JTextField txtdescripcion;
    private javax.swing.JTextField txtfechainicio;
    private javax.swing.JTextField txtidinventario;
    private javax.swing.JTextField txtpro_categoria;
    private javax.swing.JTextField txtpro_codbarra;
    private javax.swing.JTextField txtpro_marca;
    private javax.swing.JTextField txtpro_nombre;
    private javax.swing.JTextField txtpro_precio_compra;
    private javax.swing.JTextField txtpro_pventa_mayo;
    private javax.swing.JTextField txtpro_stock_actual;
    private javax.swing.JTextField txtpro_unidad;
    private javax.swing.JTextField txtstock_contado;
    // End of variables declaration//GEN-END:variables
}
