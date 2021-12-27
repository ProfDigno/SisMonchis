/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.VISTA;

import BASEDATO.EvenConexion;
import BASEDATO.LOCAL.ConnPostgres;
import Evento.JTextField.EvenJTextField;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_item_venta_alquiler;
import FORMULARIO.ENTIDAD.venta_alquiler;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Digno
 */
public class DialogCobrarReservado extends javax.swing.JDialog {

    /**
     * Creates new form DialogText
     */
    venta_alquiler ven_alq = new venta_alquiler();
    Connection connLocal = ConnPostgres.getConnPosgres();
    EvenJtable evejt = new EvenJtable();
    EvenJTextField evejtf = new EvenJTextField();
    DAO_item_venta_alquiler ivdao = new DAO_item_venta_alquiler();
    DefaultTableModel model_itemf = new DefaultTableModel();
    DefaultTableModel model_itemf_reser = new DefaultTableModel();
    EvenConexion eveconn = new EvenConexion();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();

    private void abrir_formulario() {
        this.setTitle("COBRAR RESERVADOS");
        crear_item_producto_utilizado();
        crear_item_producto_reservado();
        boton_reset();
    }

    private void cargar_tabla_reservado() {
        String titulo = "cargar_tabla_reservado";
        String sql_cant_reser_int = "select fk_idproducto as idp\n"
                + ",descripcion\n"
                + ",precio_venta as pventa\n"
                + ",(cantidad_total-cantidad_pagado) as cant\n"
                + ",((cantidad_total-cantidad_pagado)*precio_venta) as total\n"
                + "from item_venta_alquiler\n"
                + "where (cantidad_total-cantidad_pagado)>0 and fk_idventa_alquiler=" + ven_alq.getC1idventa_alquiler_global();

        try {
            ResultSet rs = eveconn.getResulsetSQL(connLocal, sql_cant_reser_int, titulo);
            while (rs.next()) {
                String idproducto = rs.getString(1);
                String descripcion = rs.getString(2);
                String Sprecio_venta = rs.getString(3);
                String cant_reserva = rs.getString(4);
                String Ssubtotal = rs.getString(5);
                String dato[] = {idproducto, descripcion, Sprecio_venta, cant_reserva, Ssubtotal};
                evejt.cargar_tabla_datos(tblitem_reservado, model_itemf_reser, dato);
            }
            evemen.Imprimir_serial_sql(sql_cant_reser_int, titulo);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cant_reser_int, titulo);
        }
        sumar_item_reservado(jFtotal_reservado, tblitem_reservado);
    }

    private void sumar_item_reservado(JFormattedTextField JFtotal, JTable tblitem) {
        double total_reservado = evejt.getDouble_sumar_tabla(tblitem, 4);
        JFtotal.setValue(total_reservado);
    }

    private void crear_item_producto_utilizado() {
        String dato[] = {"id", "descripcion", "pventa", "cant", "total"};
        evejt.crear_tabla_datos(tblitem_reservado_utilizado, model_itemf, dato);
    }

    private void ancho_item_producto_utilizado() {
        int Ancho[] = {10, 40, 20, 10, 20};
        evejt.setAnchoColumnaJtable(tblitem_reservado_utilizado, Ancho);
    }

    private void crear_item_producto_reservado() {
        String dato[] = {"id", "descripcion", "pventa", "cant", "total"};
        evejt.crear_tabla_datos(tblitem_reservado, model_itemf_reser, dato);
    }

    private void ancho_item_producto_reservado() {
        int Ancho[] = {10, 40, 20, 10, 20};
        evejt.setAnchoColumnaJtable(tblitem_reservado, Ancho);
    }

    private void cargar_item(int cant_reserva) {
        if (!evejt.getBoolean_validar_select(tblitem_reservado)) {
            String idproducto = evejt.getString_select(tblitem_reservado, 0);
            String descripcion = evejt.getString_select(tblitem_reservado, 1);
            String Sprecio_venta = evejt.getString_select(tblitem_reservado, 2);
            String Scant_reserva = String.valueOf(cant_reserva);
            int Iprecio_venta = Integer.parseInt(Sprecio_venta);
            int Isubtotal = Iprecio_venta * cant_reserva;
            String Ssubtotal = String.valueOf(Isubtotal);
            String dato[] = {idproducto, descripcion, Sprecio_venta, Scant_reserva, Ssubtotal};
            evejt.cargar_tabla_datos(tblitem_reservado_utilizado, model_itemf, dato);
            sumar_item_reservado(jFtotal_reservado_utilizado, tblitem_reservado_utilizado);
            boton_eliminar_item_reservado();
        }
    }

    private void boton_eliminar_item_utilizado() {
        if (!evejt.getBoolean_validar_select(tblitem_reservado_utilizado)) {
            if (evejt.getBoolean_Eliminar_Fila(tblitem_reservado_utilizado, model_itemf)) {
                sumar_item_reservado(jFtotal_reservado_utilizado, tblitem_reservado_utilizado);
            }
        }
    }

    private void boton_eliminar_item_reservado() {
        if (!evejt.getBoolean_validar_select(tblitem_reservado)) {
            if (evejt.getBoolean_Eliminar_Fila(tblitem_reservado, model_itemf_reser)) {
                sumar_item_reservado(jFtotal_reservado, tblitem_reservado);
            }
        }
    }

    private void boton_reset() {
        txtcantidad.setEnabled(false);
        ocultar_boton_cant(false, false, false, false, false);
        evejt.limpiar_tabla_datos(model_itemf);
        evejt.limpiar_tabla_datos(model_itemf_reser);
        cargar_tabla_reservado();
        sumar_item_reservado(jFtotal_reservado_utilizado, tblitem_reservado_utilizado);
    }

    private void ocultar_boton_cant(boolean btn1, boolean btn2, boolean btn3, boolean btn4, boolean btn5) {
        btncan_1.setEnabled(btn1);
        btncan_2.setEnabled(btn2);
        btncan_3.setEnabled(btn3);
        btncan_4.setEnabled(btn4);
        btncan_5.setEnabled(btn5);
    }

    private void seleccionar_reserva() {
        if (!evejt.getBoolean_validar_select(tblitem_reservado)) {
            int cant = evejt.getInt_select(tblitem_reservado, 3);
            txtcantidad.setEnabled(false);
            if (cant == 1) {
                ocultar_boton_cant(true, false, false, false, false);
            }
            if (cant == 2) {
                ocultar_boton_cant(true, true, false, false, false);
            }
            if (cant == 3) {
                ocultar_boton_cant(true, true, true, false, false);
            }
            if (cant == 4) {
                ocultar_boton_cant(true, true, true, true, false);
            }
            if (cant == 5) {
                ocultar_boton_cant(true, true, true, true, true);
            }
            if (cant > 5) {
                ocultar_boton_cant(true, true, true, true, true);
                txtcantidad.setEnabled(true);
            }
        }
    }

    private void cant_mayor() {
        if (!evejt.getBoolean_validar_select(tblitem_reservado)) {
            int cant = evejt.getInt_select(tblitem_reservado, 3);
            if (txtcantidad.getText().trim().length() > 0) {
                int cant_ingresado = Integer.parseInt(txtcantidad.getText());
                if (cant >= cant_ingresado) {
                    cargar_item(cant_ingresado);
                } else {
                    JOptionPane.showMessageDialog(this, "LA CANIDAD NO DEBE SUPERAR LA CANTIDAD DE LA TABLA");
                    txtcantidad.setText(null);
                }
            }
        }
    }

    public DialogCobrarReservado(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tblitem_reservado = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblitem_reservado_utilizado = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jFtotal_reservado = new javax.swing.JFormattedTextField();
        jLabel8 = new javax.swing.JLabel();
        jFtotal_reservado_utilizado = new javax.swing.JFormattedTextField();
        btneliminar_item = new javax.swing.JButton();
        btnreset = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        btncan_1 = new javax.swing.JButton();
        btncan_2 = new javax.swing.JButton();
        btncan_3 = new javax.swing.JButton();
        btncan_4 = new javax.swing.JButton();
        btncan_5 = new javax.swing.JButton();
        txtcantidad = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("TABLA ITEM RESERVADO"));

        tblitem_reservado.setModel(new javax.swing.table.DefaultTableModel(
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
        tblitem_reservado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblitem_reservadoMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblitem_reservado);

        tblitem_reservado_utilizado.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblitem_reservado_utilizado);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("TOTAL RESERVADO:");

        jFtotal_reservado.setEditable(false);
        jFtotal_reservado.setBackground(new java.awt.Color(204, 204, 255));
        jFtotal_reservado.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFtotal_reservado.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_reservado.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("TOTAL UTILIZADO:");

        jFtotal_reservado_utilizado.setEditable(false);
        jFtotal_reservado_utilizado.setBackground(new java.awt.Color(204, 204, 255));
        jFtotal_reservado_utilizado.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFtotal_reservado_utilizado.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_reservado_utilizado.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        btneliminar_item.setText("ELIMINAR ITEM");
        btneliminar_item.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneliminar_itemActionPerformed(evt);
            }
        });

        btnreset.setText("RESET");
        btnreset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnresetActionPerformed(evt);
            }
        });

        jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btncan_1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btncan_1.setText("1");
        btncan_1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncan_1ActionPerformed(evt);
            }
        });

        btncan_2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btncan_2.setText("2");
        btncan_2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncan_2ActionPerformed(evt);
            }
        });

        btncan_3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btncan_3.setText("3");
        btncan_3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncan_3ActionPerformed(evt);
            }
        });

        btncan_4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btncan_4.setText("4");
        btncan_4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncan_4ActionPerformed(evt);
            }
        });

        btncan_5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btncan_5.setText("5");
        btncan_5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncan_5ActionPerformed(evt);
            }
        });

        txtcantidad.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtcantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcantidadKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtcantidadKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btncan_1)
                    .addComponent(btncan_2)
                    .addComponent(btncan_3)
                    .addComponent(btncan_4)
                    .addComponent(btncan_5))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtcantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btncan_1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btncan_2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btncan_3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btncan_4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btncan_5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtcantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFtotal_reservado, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnreset, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFtotal_reservado_utilizado, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btneliminar_item, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 390, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jFtotal_reservado, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel7))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jFtotal_reservado_utilizado, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel8)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btneliminar_item)
                            .addComponent(btnreset))))
                .addGap(15, 15, 15))
        );

        jButton2.setText("CONFIRMAR RESERVAS UTILIZADO");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        ancho_item_producto_utilizado();
        ancho_item_producto_reservado();
    }//GEN-LAST:event_formWindowOpened

    private void btncan_1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncan_1ActionPerformed
        // TODO add your handling code here:
        cargar_item(1);
    }//GEN-LAST:event_btncan_1ActionPerformed

    private void btneliminar_itemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneliminar_itemActionPerformed
        // TODO add your handling code here:
        boton_eliminar_item_utilizado();
    }//GEN-LAST:event_btneliminar_itemActionPerformed

    private void btnresetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnresetActionPerformed
        // TODO add your handling code here:
        boton_reset();
    }//GEN-LAST:event_btnresetActionPerformed

    private void btncan_2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncan_2ActionPerformed
        // TODO add your handling code here:
        cargar_item(2);
    }//GEN-LAST:event_btncan_2ActionPerformed

    private void btncan_3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncan_3ActionPerformed
        // TODO add your handling code here:
        cargar_item(3);
    }//GEN-LAST:event_btncan_3ActionPerformed

    private void btncan_4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncan_4ActionPerformed
        // TODO add your handling code here:
        cargar_item(4);
    }//GEN-LAST:event_btncan_4ActionPerformed

    private void btncan_5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncan_5ActionPerformed
        // TODO add your handling code here:
        cargar_item(5);
    }//GEN-LAST:event_btncan_5ActionPerformed

    private void tblitem_reservadoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblitem_reservadoMouseReleased
        // TODO add your handling code here:
        seleccionar_reserva();
    }//GEN-LAST:event_tblitem_reservadoMouseReleased

    private void txtcantidadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcantidadKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            cant_mayor();
        }
    }//GEN-LAST:event_txtcantidadKeyPressed

    private void txtcantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcantidadKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txtcantidadKeyTyped

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
            java.util.logging.Logger.getLogger(DialogCobrarReservado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DialogCobrarReservado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DialogCobrarReservado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DialogCobrarReservado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DialogCobrarReservado dialog = new DialogCobrarReservado(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btncan_1;
    private javax.swing.JButton btncan_2;
    private javax.swing.JButton btncan_3;
    private javax.swing.JButton btncan_4;
    private javax.swing.JButton btncan_5;
    private javax.swing.JButton btneliminar_item;
    private javax.swing.JButton btnreset;
    private javax.swing.JButton jButton2;
    private javax.swing.JFormattedTextField jFtotal_reservado;
    private javax.swing.JFormattedTextField jFtotal_reservado_utilizado;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblitem_reservado;
    private javax.swing.JTable tblitem_reservado_utilizado;
    private javax.swing.JTextField txtcantidad;
    // End of variables declaration//GEN-END:variables
}
