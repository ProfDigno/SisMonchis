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
import Evento.Mensaje.EvenMensajeJoptionpane;
import FILTRO.ClaAuxFiltroVenta;
import FORMULARIO.DAO.DAO_cliente;
import FORMULARIO.DAO.DAO_producto;
import FORMULARIO.DAO.DAO_producto_categoria;
import FORMULARIO.DAO.DAO_producto_marca;
import FORMULARIO.DAO.DAO_venta;
import FORMULARIO.ENTIDAD.cliente;
import FORMULARIO.ENTIDAD.producto_categoria;
import FORMULARIO.ENTIDAD.producto_marca;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;

/**
 *
 * @author Digno
 */
public class FrmRepGananciaCompraVenta extends javax.swing.JInternalFrame {

    /**
     * Creates new form FrmRepVenta
     */
    EvenJFRAME evetbl = new EvenJFRAME();
    Connection conn = ConnPostgres.getConnPosgres();
//    DAO_venta vdao = new DAO_venta();
    DAO_producto_categoria pcdao = new DAO_producto_categoria();
    DAO_producto_marca pmdao = new DAO_producto_marca();
    EvenFecha evefec = new EvenFecha();
    ClaAuxFiltroVenta auxvent = new ClaAuxFiltroVenta();
    EvenJTextField evejtf = new EvenJTextField();
    EvenConexion eveconn = new EvenConexion();
//    DAO_cliente cdao = new DAO_cliente();
    producto_categoria cate = new producto_categoria();
    producto_marca marca = new producto_marca();
    DAO_producto pdao = new DAO_producto();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    cla_color_pelete clacolor = new cla_color_pelete();
    private int fk_idproducto_categoria_local;
    private int fk_idproducto_marca_local;

    void abrir_formulario() {
        this.setTitle("REPORTE GANACIA POR VENTA");
        evetbl.centrar_formulario(this);
        color_formulario();
        reestableser();
    }
    void color_formulario() {
        panel_cate_mar.setBackground(clacolor.getColor_insertar_primario());
        panel_fecha.setBackground(clacolor.getColor_insertar_secundario());
    }
    String suma_filtro() {
        String filtro_fecha = "";
        if ((txtfecha_desde.getText().trim().length() > 0) && (txtfecha_hasta.getText().trim().length() > 0)) {
            String fecdesde = evefec.getString_validar_fecha(txtfecha_desde.getText());
            String fechasta = evefec.getString_validar_fecha(txtfecha_hasta.getText());
            txtfecha_desde.setText(fecdesde);
            txtfecha_hasta.setText(fechasta);
            filtro_fecha = " and date(v.fecha_emision) >= '" + fecdesde + "' and date(v.fecha_emision) <= '" + fechasta + "' ";
        }
        String suma_filtro = "";
        String filtro_categoria = "";
        if (fk_idproducto_categoria_local >= 0) {
            filtro_categoria = " and pc.idproducto_categoria=" + fk_idproducto_categoria_local + "\n";
        }
        String filtro_marca = "";
        if (fk_idproducto_marca_local >= 0) {
            filtro_marca = " and pm.idproducto_marca=" + fk_idproducto_marca_local + "\n";
        }
        suma_filtro = filtro_categoria + filtro_marca + filtro_fecha;
        return suma_filtro;
    }

    void boton_imprimir() {
        boton_calcular();
        String fecdesde = evefec.getString_validar_fecha(txtfecha_desde.getText());
        String fechasta = evefec.getString_validar_fecha(txtfecha_hasta.getText());
        String fecha = "Desde: " + fecdesde + " Hasta:" + fechasta;
        pdao.imprimir_rep_ganacia(conn, suma_filtro(), fecha);
    }

    void boton_calcular() {
        String titulo = "boton_calcular";
        String sql = "SELECT sum(iv.cantidad) as cantidad,\n"
                + "sum(iv.precio_venta*iv.cantidad) as total_venta,\n"
                + "sum(iv.precio_compra*iv.cantidad) as total_compra,\n"
                + "(sum(iv.precio_venta*iv.cantidad)-sum(iv.precio_compra*iv.cantidad)) as ganancia\n"
                + "FROM producto p,producto_unidad pu,producto_categoria pc, producto_marca pm,item_venta iv,venta v \n"
                + "where p.fk_idproducto_unidad=pu.idproducto_unidad\n"
                + "and iv.fk_idventa=v.idventa\n"
                + "and iv.fk_idproducto=p.idproducto\n"
                + "and p.fk_idproducto_categoria=pc.idproducto_categoria\n"
                + "and p.fk_idproducto_marca=pm.idproducto_marca\n"
                + "and p.idproducto!=0 \n"
                + "and v.estado='EMITIDO' " + suma_filtro();
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            if (rs.next()) {
                double total_venta = rs.getDouble("total_venta");
                double total_compra = rs.getDouble("total_compra");
                double ganancia = rs.getDouble("ganancia");
                jFtotal_venta.setValue(total_venta);
                jFtotal_compra.setValue(total_compra);
                jFtotal_ganancia.setValue(ganancia);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
        }
    }

    void reestableser() {
        txtfecha_desde.setText(evefec.getString_fecha_dia1());
        txtfecha_hasta.setText(evefec.getString_formato_fecha());
        txtbucar_categoria.setText(null);
        txtbucar_marca.setText(null);
        fk_idproducto_categoria_local = -1;
        fk_idproducto_marca_local = -1;
    }

    void seleccionar_cargar_categoria() {
        fk_idproducto_categoria_local = eveconn.getInt_Solo_seleccionar_JLista(conn, jList_categoria, "producto_categoria", "nombre", "idproducto_categoria");
        pcdao.cargar_producto_categoria_reporte(cate, fk_idproducto_categoria_local);
        txtbucar_categoria.setText(cate.getNombre());
        txtbucar_marca.grabFocus();
    }

    void seleccionar_cargar_marca() {
        fk_idproducto_marca_local = eveconn.getInt_Solo_seleccionar_JLista(conn, jList_marca, "producto_marca", "nombre", "idproducto_marca");
        pmdao.cargar_producto_marca_por_id(conn, marca, fk_idproducto_marca_local);
        txtbucar_marca.setText(marca.getC2nombre());
        btnimprimir.grabFocus();
    }

    public FrmRepGananciaCompraVenta() {
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

        panel_cate_mar = new javax.swing.JPanel();
        jList_categoria = new javax.swing.JList<>();
        jLabel3 = new javax.swing.JLabel();
        txtbucar_categoria = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtbucar_marca = new javax.swing.JTextField();
        jList_marca = new javax.swing.JList<>();
        btnimprimir = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jFtotal_venta = new javax.swing.JFormattedTextField();
        btnreset = new javax.swing.JButton();
        btncalcular = new javax.swing.JButton();
        panel_fecha = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtfecha_desde = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtfecha_hasta = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jFtotal_compra = new javax.swing.JFormattedTextField();
        jLabel7 = new javax.swing.JLabel();
        jFtotal_ganancia = new javax.swing.JFormattedTextField();

        setClosable(true);

        panel_cate_mar.setBorder(javax.swing.BorderFactory.createTitledBorder("INVENTARIO VALORIZADO"));

        jList_categoria.setBackground(new java.awt.Color(204, 204, 255));
        jList_categoria.setBorder(javax.swing.BorderFactory.createEtchedBorder());
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
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("CATEGORIA:");

        txtbucar_categoria.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtbucar_categoriaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbucar_categoriaKeyReleased(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("MARCA:");

        txtbucar_marca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtbucar_marcaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbucar_marcaKeyReleased(evt);
            }
        });

        jList_marca.setBackground(new java.awt.Color(204, 204, 255));
        jList_marca.setBorder(javax.swing.BorderFactory.createEtchedBorder());
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

        javax.swing.GroupLayout panel_cate_marLayout = new javax.swing.GroupLayout(panel_cate_mar);
        panel_cate_mar.setLayout(panel_cate_marLayout);
        panel_cate_marLayout.setHorizontalGroup(
            panel_cate_marLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_cate_marLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_cate_marLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jList_categoria, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panel_cate_marLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtbucar_categoria, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_cate_marLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_cate_marLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtbucar_marca, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE))
                    .addComponent(jList_marca, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panel_cate_marLayout.setVerticalGroup(
            panel_cate_marLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_cate_marLayout.createSequentialGroup()
                .addGroup(panel_cate_marLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_cate_marLayout.createSequentialGroup()
                        .addGroup(panel_cate_marLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtbucar_categoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jList_categoria, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_cate_marLayout.createSequentialGroup()
                        .addGroup(panel_cate_marLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtbucar_marca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jList_marca, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        btnimprimir.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnimprimir.setText("IMPRIMIR");
        btnimprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnimprimirActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("TOTAL VENTA:");

        jFtotal_venta.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        jFtotal_venta.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_venta.setFont(new java.awt.Font("Stencil", 0, 24)); // NOI18N

        btnreset.setText("RESET");
        btnreset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnresetActionPerformed(evt);
            }
        });

        btncalcular.setText("CALCULAR");
        btncalcular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncalcularActionPerformed(evt);
            }
        });

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

        javax.swing.GroupLayout panel_fechaLayout = new javax.swing.GroupLayout(panel_fecha);
        panel_fecha.setLayout(panel_fechaLayout);
        panel_fechaLayout.setHorizontalGroup(
            panel_fechaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_fechaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtfecha_desde, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtfecha_hasta, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_fechaLayout.setVerticalGroup(
            panel_fechaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_fechaLayout.createSequentialGroup()
                .addGroup(panel_fechaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtfecha_desde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtfecha_hasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 9, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("TOTAL COMPRA:");

        jFtotal_compra.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        jFtotal_compra.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_compra.setFont(new java.awt.Font("Stencil", 0, 24)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setText("TOTAL GANANCIA:");

        jFtotal_ganancia.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        jFtotal_ganancia.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_ganancia.setFont(new java.awt.Font("Stencil", 0, 24)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panel_cate_mar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel_fecha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1)
                    .addComponent(jFtotal_venta, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel7)
                    .addComponent(jFtotal_ganancia, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
                    .addComponent(jFtotal_compra, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnreset)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btncalcular, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnimprimir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(5, 5, 5)
                .addComponent(jFtotal_venta, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jFtotal_compra, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jFtotal_ganancia, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnreset)
                    .addComponent(btncalcular))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnimprimir, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_cate_mar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnimprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnimprimirActionPerformed
        // TODO add your handling code here:
        boton_imprimir();
    }//GEN-LAST:event_btnimprimirActionPerformed

    private void jList_categoriaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList_categoriaMouseReleased
        // TODO add your handling code here:
        seleccionar_cargar_categoria();
    }//GEN-LAST:event_jList_categoriaMouseReleased

    private void jList_categoriaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jList_categoriaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            seleccionar_cargar_categoria();
        }
    }//GEN-LAST:event_jList_categoriaKeyPressed

    private void txtbucar_categoriaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbucar_categoriaKeyPressed
        // TODO add your handling code here:
        evejtf.seleccionar_lista(evt, jList_categoria);
//        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
//            txtbucarCliente_telefono.grabFocus();
//        }
    }//GEN-LAST:event_txtbucar_categoriaKeyPressed

    private void txtbucar_categoriaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbucar_categoriaKeyReleased
        // TODO add your handling code here:
        eveconn.buscar_cargar_Jlista(conn, txtbucar_categoria, jList_categoria, "producto_categoria", "nombre", "nombre", 4);
    }//GEN-LAST:event_txtbucar_categoriaKeyReleased

    private void btnresetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnresetActionPerformed
        // TODO add your handling code here:
        reestableser();
    }//GEN-LAST:event_btnresetActionPerformed

    private void jList_marcaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList_marcaMouseReleased
        // TODO add your handling code here:
        seleccionar_cargar_marca();
    }//GEN-LAST:event_jList_marcaMouseReleased

    private void jList_marcaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jList_marcaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            seleccionar_cargar_marca();
        }
    }//GEN-LAST:event_jList_marcaKeyPressed

    private void txtbucar_marcaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbucar_marcaKeyPressed
        // TODO add your handling code here:
        evejtf.seleccionar_lista(evt, jList_marca);
    }//GEN-LAST:event_txtbucar_marcaKeyPressed

    private void txtbucar_marcaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbucar_marcaKeyReleased
        // TODO add your handling code here:
        eveconn.buscar_cargar_Jlista(conn, txtbucar_marca, jList_marca, "producto_marca", "nombre", "nombre", 4);
    }//GEN-LAST:event_txtbucar_marcaKeyReleased

    private void btncalcularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncalcularActionPerformed
        // TODO add your handling code here:
        boton_calcular();
    }//GEN-LAST:event_btncalcularActionPerformed

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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btncalcular;
    private javax.swing.JButton btnimprimir;
    private javax.swing.JButton btnreset;
    private javax.swing.JFormattedTextField jFtotal_compra;
    private javax.swing.JFormattedTextField jFtotal_ganancia;
    private javax.swing.JFormattedTextField jFtotal_venta;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JList<String> jList_categoria;
    private javax.swing.JList<String> jList_marca;
    private javax.swing.JPanel panel_cate_mar;
    private javax.swing.JPanel panel_fecha;
    private javax.swing.JTextField txtbucar_categoria;
    private javax.swing.JTextField txtbucar_marca;
    private javax.swing.JTextField txtfecha_desde;
    private javax.swing.JTextField txtfecha_hasta;
    // End of variables declaration//GEN-END:variables
}
