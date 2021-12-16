/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.VISTA;

import FORMULARIO.ENTIDAD.cotizacion;
import BASEDATO.LOCAL.ConnPostgres;
import BASEDATO.EvenConexion;
//import BASEDATO.SERVIDOR.ConnPostgres_SER;
import CONFIGURACION.EvenDatosPc;
import Evento.Color.cla_color_pelete;
import Evento.Combobox.EvenCombobox;
import Evento.Fecha.EvenFecha;
import Evento.JTextField.EvenJTextField;
import Evento.Jframe.EvenJFRAME;
import Evento.Jtable.EvenJtable;
import Evento.Jtable.EvenRender;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Utilitario.EvenUtil;
import FORMULARIO.BO.BO_cliente;
import FORMULARIO.BO.BO_compra;
import FORMULARIO.BO.BO_venta;
import FORMULARIO.DAO.*;
import FORMULARIO.ENTIDAD.*;
import static FORMULARIO.VISTA.FrmCliente.txtdelivery;
import static FORMULARIO.VISTA.FrmCliente.txtzona;
import IMPRESORA_POS.PosImprimir_Compra;
import IMPRESORA_POS.PosImprimir_Compra_insumo;
import IMPRESORA_POS.PosImprimir_Venta;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Digno
 */
public class FrmCompra extends javax.swing.JInternalFrame {

    /**
     * Creates new form FrmVenta
     */
    EvenJFRAME evetbl = new EvenJFRAME();
    EvenJtable evejt = new EvenJtable();
    EvenRender everende = new EvenRender();
    EvenFecha evefec = new EvenFecha();
    EvenDatosPc evepc = new EvenDatosPc();
    EvenUtil eveut = new EvenUtil();
    EvenConexion eveconn = new EvenConexion();
    EvenJTextField evejtf = new EvenJTextField();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    caja_detalle caja = new caja_detalle();
    cotizacion coti = new cotizacion();
    private EvenCombobox cmb = new EvenCombobox();
    private DAO_cotizacion codao = new DAO_cotizacion();
    private DAO_producto ipdao = new DAO_producto();
    private producto prod = new producto();
    private proveedor ent_prov = new proveedor();
    private compra compi = new compra();
    private item_compra item = new item_compra();
    private DAO_compra cidao = new DAO_compra();
    private BO_compra ciBO = new BO_compra();
    private DAO_item_compra icidao = new DAO_item_compra();
    private DAO_caja_detalle cdao = new DAO_caja_detalle();
    private usuario usu = new usuario();
    private PosImprimir_Compra poscomp = new PosImprimir_Compra();
    private DAO_grupo_credito_finanza gccDAO = new DAO_grupo_credito_finanza();
    private credito_finanza cfina = new credito_finanza();
    private grupo_credito_finanza gcc = new grupo_credito_finanza();
    private financista fina = new financista();
    Connection conn = ConnPostgres.getConnPosgres();
    private DefaultTableModel model_itemf = new DefaultTableModel();
    private cla_color_pelete clacolor = new cla_color_pelete();
    private String cantidad_producto = "0";
    private String tiponota = "PEDIDO";
    private String estado = "EMITIDO";
    private double monto_compra;
    private String prod_tabla = "producto p,producto_unidad u,producto_marca pm ";
    private String prod_mostrar = "(pm.nombre||'-'||u.nombre||'-'||p.nombre) as producto";
    private String prod_buscar = "p.fk_idproducto_marca=pm.idproducto_marca \n"
            + "and p.fk_idproducto_unidad=u.idproducto_unidad \n"
            + "and concat(pm.nombre||'-'||u.nombre||'-'||p.nombre)";
    private int idproducto = -1;
    private String est_ANULADO = "ANULADO";
    private String est_EMITIDO = "EMITIDO";
    private String est_CONFIRMADO = "CONFIRMADO";
    private String tip_COMPRADO = "COMPRADO";
    private int idcompra_insumo_editar;
    private boolean habilitar_editar;
    private int idcompra_ultimo = 0;
    private boolean hab_update_precio_prod;
    private int idcompra_insumo_select;
    private DAO_proveedor DAO_prov = new DAO_proveedor();
    private int fk_idproveedor;
    private String tabla_origen = "COMPRA";
    private String condicion_CONTADO = "CONTADO";
    private String condicion_CREDITO = "CREDITO";
//    private String tabla_origen_CONTADO = "COMPRA_CONTADO";
//    private String tabla_origen_CREDITO = "COMPRA_CREDITO";
    private double monto_compra_contado;
    private double monto_compra_credito;
    private String condicion;
    private int fk_idfinancista;

    private void abrir_formulario() {
        String servidor = "";
        this.setTitle("COMPRA--> USUARIO:" + usu.getGlobal_nombre() + servidor);
        evetbl.centrar_formulario_internalframa(this);
        codao.cargar_cotizacion(coti, 1);
        crear_item_producto();
        cargar_finanza();
        reestableser_compra();
        color_formulario(clacolor.getColor_insertar_primario());
    }

    private void cargar_finanza() {
        cmb.cargarCombobox(conn, jCfinancista, "idfinancista", "nombre", "financista", "");
    }

    private void cargar_proveedor() {
        fk_idproveedor = 0;
        ent_prov.setIdproveedor_static(fk_idproveedor);
        DAO_prov.cargar_proveedor(conn, ent_prov, fk_idproveedor);
        txtprovee_nombre.setText(ent_prov.getC3nombre());
        txtprovee_ruc.setText(ent_prov.getC6ruc());
    }

    private void color_formulario(Color colorpanel) {
        panel_proveedor.setBackground(colorpanel);
        panel_tabla_compra.setBackground(colorpanel);
        panel_insertar_pri_compra.setBackground(colorpanel);
        panel_insertar_seg_compra.setBackground(colorpanel);
        panel_referencia_filtro.setBackground(colorpanel);
        panel_base_1.setBackground(colorpanel);
        panel_crear_compra.setBackground(colorpanel);
    }

    private void crear_item_producto() {
        String dato[] = {"id", "codbarra", "MARCA-UNID-PRODUCTO", "UNIDAD", "PRE_COMPRA", "CANTIDAD", "SUBTOTAL"};
        evejt.crear_tabla_datos(tblitem_producto, model_itemf, dato);
    }

    private void ancho_item_producto() {
        int Ancho[] = {5, 10, 42, 13, 10, 10, 10};
        evejt.setAnchoColumnaJtable(tblitem_producto, Ancho);
    }

    private void sumar_item_compra() {
        evejt.calcular_subtotal(tblitem_producto, model_itemf, 5, 4, 6);
        double total_guarani = evejt.getDouble_sumar_tabla(tblitem_producto, 6);
        monto_compra = total_guarani;
        jFtotal_guarani.setValue(total_guarani);
    }

    private void boton_eliminar_item() {
        if (!evejt.getBoolean_validar_select(tblitem_producto)) {
            if (evejt.getBoolean_Eliminar_Fila(tblitem_producto, model_itemf)) {
                sumar_item_compra();
            }
        }
    }

    private void reestableser_compra() {
        idcompra_ultimo = (eveconn.getInt_ultimoID_mas_uno(conn, compi.getTb_compra(), compi.getId_idcompra()));
        txtidcompra.setText(String.valueOf(idcompra_ultimo));
        txtbuscar_fecha.setText(evefec.getString_formato_fecha());
        txtnro_nota.setText(null);
        jRcond_contado.setSelected(true);
        jList_producto.setVisible(false);
        jCalquiler.setSelected(false);
        evejt.limpiar_tabla_datos(model_itemf);
        txtobservacion.setText("Ninguna");
        monto_compra = 0;
        idproducto = 0;
        sumar_item_compra();
        cidao.actualizar_tabla_compra(conn, tblcompra);
        habilitar_editar = false;
        btnconfirmar_insertar.setText("CONFIRMAR");
        cargar_proveedor();
        select_alquiler();
        txtprod_buscar_nombre.grabFocus();
    }

    private void seleccionar_buscar_producto() {
        idproducto = eveconn.getInt_Solo_seleccionar_JLista(conn, jList_producto, prod_tabla, prod_buscar, "idproducto");
        ipdao.cargar_producto_por_idproducto(conn, prod, idproducto);
        txtprod_codbarra.setText(prod.getC2cod_barra());
        txtprod_buscar_nombre.setText(prod.getC20_marca() + "-" + prod.getC18_unidad() + "-" + prod.getC3nombre());
        txtprod_stock.setText(String.valueOf((int) prod.getC8stock()));
        txtprod_unidad.setText(prod.getC18_unidad());
        int precio_compra = (int) prod.getC7precio_compra();
        txtprod_pre_com.setText(String.valueOf(precio_compra));
        txtprod_cant.setBackground(Color.ORANGE);
        txtprod_cant.setText("1");
        txtprod_cant.grabFocus();
    }

    private void carcar_producto_codbarra() {
        if (txtprod_codbarra.getText().trim().length() > 0) {
            if (ipdao.getBoolean_cargar_producto_por_codbarra(conn, prod, txtprod_codbarra.getText())) {
                idproducto = prod.getC1idproducto();
                txtprod_buscar_nombre.setText(prod.getC20_marca() + "-" + prod.getC18_unidad() + "-" + prod.getC3nombre());
                txtprod_stock.setText(String.valueOf((int) prod.getC8stock()));
                txtprod_unidad.setText(prod.getC18_unidad());
                int precio_compra = (int) prod.getC7precio_compra();
                txtprod_pre_com.setText(String.valueOf(precio_compra));
                txtprod_cant.setBackground(Color.ORANGE);
                txtprod_cant.setText("1");
                txtprod_cant.grabFocus();
            } else {
                JOptionPane.showMessageDialog(null, "PRODUCTO NO ENCONTRADO");
            }
        }
    }

    private boolean validar_subtotal() {
        if (txtprod_cant.getText().trim().length() <= 0) {
            return false;
        }
        if (txtprod_pre_com.getText().trim().length() <= 0) {
            return false;
        }
        return true;
    }

    private void calcular_subtotal_item() {
        if (validar_subtotal()) {
            try {
                int cantidad = Integer.parseInt(txtprod_cant.getText());
                int precio = Integer.parseInt(txtprod_pre_com.getText());
                int subtotal = cantidad * precio;
                jFsutotal.setValue(subtotal);
            } catch (Exception e) {
                evemen.mensaje_error(e, "calcular_subtotal_item");
            }

        }
    }

    private boolean validar_carga_item_producto() {
        if (evejtf.getBoo_JTextField_vacio(txtprod_buscar_nombre, "CARGAR NOMBRE")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtprod_cant, "CARGAR CANTIDAD")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtprod_pre_com, "CARGAR PRECIO")) {
            return false;
        }
        if (idproducto == -1) {
            JOptionPane.showMessageDialog(null, "NO SE ENCONTRO NINGUN PRODUCTO\nESCRIBIR EL PRODUCTO", "ERROR", JOptionPane.ERROR_MESSAGE);
            reestableser_item_producto();
            return false;
        }
        return true;
    }

    private void reestableser_item_producto() {
        txtprod_buscar_nombre.setText(null);
        txtprod_unidad.setText(null);
        txtprod_cant.setText(null);
        txtprod_pre_com.setText(null);
        txtprod_stock.setText(null);
        txtprod_codbarra.setText(null);
        jFsutotal.setValue(0);
        idproducto = -1;
        txtprod_codbarra.grabFocus();
    }

    private void update_precio_prod() {
        if (hab_update_precio_prod) {
            ipdao.update_producto_precio_compra(conn, prod);
            hab_update_precio_prod = false;
        }
    }

    private void cargar_item_producto() {
        if (validar_carga_item_producto()) {
            txtprod_pre_com.setBackground(Color.WHITE);
            txtprod_cant.setBackground(Color.WHITE);
            String codbarra = txtprod_codbarra.getText();
            String fk_idproducto = String.valueOf(idproducto);
            String descripcion = txtprod_buscar_nombre.getText();
            String unidad = txtprod_unidad.getText();
            String precioUni = txtprod_pre_com.getText();
            String cantidad = txtprod_cant.getText();
            try {
                int Dcantidad = Integer.parseInt(cantidad);
                int DprecioUni = Integer.parseInt(precioUni);
                String total = String.valueOf(DprecioUni * Dcantidad);
                String dato[] = {fk_idproducto, codbarra, descripcion, unidad, precioUni, cantidad, total};
                evejt.cargar_tabla_datos(tblitem_producto, model_itemf, dato);
                prod.setC1idproducto(idproducto);
                prod.setC7precio_compra(DprecioUni);
                update_precio_prod();
                sumar_item_compra();
                reestableser_item_producto();
            } catch (Exception e) {
                evemen.mensaje_error(e, "cargar_item_producto");
            }

        }
    }

    private void boton_cancelar_compra() {
        if (evemen.MensajeGeneral_warning("ESTAS SEGURO DE CANCELAR", "CANCELAR", "ACEPTAR", "NO-CANCELAR")) {
            reestableser_compra();
            reestableser_item_producto();
        }
    }

    private boolean validar_compra() {
        if (evejt.getBoolean_validar_cant_cargado(tblitem_producto)) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtobservacion, "CARGAR UNA OBSERVACION")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtnro_nota, "CARGAR UN NUMERO DE NOTA")) {
            return false;
        }
        if (jRcond_credito.isSelected()) {
            if (jCfinancista.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(null, "SI ES CREDITO...SE DEBE SELECCIONAR UN FINANCISTA");
                return false;
            }
        }
        return true;
    }

    private void cargar_datos_compra() {
        compi.setC3estado(estado);
        compi.setC4observacion(txtobservacion.getText());
        compi.setC5forma_pago(tiponota);
        compi.setC6monto_compra(monto_compra);
        compi.setC7fk_idproveedor(ent_prov.getIdproveedor_static());
        compi.setC8fk_idusuario(usu.getGlobal_idusuario());
        int nro_nota = Integer.parseInt(txtnro_nota.getText());
        compi.setC9nro_nota(nro_nota);
        String condicion = "";
        if (jRcond_contado.isSelected()) {
            condicion = condicion_CONTADO;
        }
        if (jRcond_credito.isSelected()) {
            condicion = condicion_CREDITO;
        }
        fk_idfinancista = cmb.getInt_seleccionar_COMBOBOX(conn, jCfinancista, "idfinancista", "nombre", "financista");
        compi.setC10condicion(condicion);
        compi.setC11fk_idfinancista(fk_idfinancista);
        compi.setC12alquilado(jCalquiler.isSelected());
    }

    private void cargar_datos_caja() {
        caja.setC2fecha_emision(evefec.getString_formato_fecha_hora());
        caja.setC3descripcion1("(COMPRA) id:" + idcompra_ultimo + " Pro:" + txtprovee_nombre.getText());
        caja.setC4monto_venta_efectivo(0);
        caja.setC5monto_venta_tarjeta(0);
        caja.setC6monto_delivery(0);
        caja.setC7monto_gasto(0);
        caja.setC8monto_compra(monto_compra_contado);
        caja.setC9monto_vale(0);
        caja.setC10monto_caja(0);
        caja.setC11monto_cierre(0);
        caja.setC12id_origen(idcompra_ultimo);
        caja.setC13tabla_origen(tabla_origen);
        caja.setC15estado(est_EMITIDO);
        caja.setC16fk_idusuario(usu.getGlobal_idusuario());
        caja.setC17monto_recibo_pago(0);
        caja.setC18monto_compra_credito(monto_compra_credito);
    }

    private String getDescripcion_item_venta() {
        String suma_descripcion = "";
        for (int row = 0; row < tblitem_producto.getRowCount(); row++) {
            String descripcion = ((tblitem_producto.getModel().getValueAt(row, 2).toString()));
            suma_descripcion = suma_descripcion + descripcion + ", ";
        }
        return suma_descripcion;
    }

    private void cargar_credito_finanza() {
        gccDAO.cargar_grupo_credito_finanza_id(conn, gcc, fk_idfinancista);
        cfina.setC3descripcion(getDescripcion_item_venta());
        cfina.setC4estado(est_EMITIDO);
        cfina.setC5monto_contado(monto_compra_contado);
        cfina.setC6monto_credito(monto_compra_credito);
        cfina.setC7tabla_origen(tabla_origen);
        cfina.setC8fk_idgrupo_credito_finanza(gcc.getC1idgrupo_credito_finanza());
        cfina.setC9fk_idsaldo_credito_finanza(0);
        cfina.setC10fk_idrecibo_pago_finanza(0);
        cfina.setC11fk_idcompra(idcompra_ultimo);
    }

    private void boton_comfirmar_compra() {
        if (validar_compra()) {

            if (jRcond_contado.isSelected()) {
                monto_compra_credito = 0;
                monto_compra_contado = monto_compra;
                condicion = condicion_CONTADO;
                tabla_origen = caja.getTabla_origen_compra_contado();
                sumar_item_compra();
                cargar_datos_compra();
                cargar_datos_caja();
                if (ciBO.getBoolean_compra1(tblitem_producto, compi, caja)) {
                    poscomp.boton_imprimir_pos_compra(conn, idcompra_ultimo);
                    reestableser_compra();
                }
            }
            if (jRcond_credito.isSelected()) {
                monto_compra_credito = monto_compra;
                monto_compra_contado = 0;
                condicion = condicion_CREDITO;
                tabla_origen = caja.getTabla_origen_compra_credito();
                cargar_datos_compra();
                cargar_credito_finanza();
                cargar_datos_caja();
                fina.setC1idfinancista(fk_idfinancista);
                if (ciBO.getBoolean_insertar_compra_credito(conn, tblitem_producto, item, compi, cfina, fina, caja)) {
                    poscomp.boton_imprimir_pos_compra(conn, idcompra_ultimo);
                    reestableser_compra();
                }
            }
        }
    }

    private void seleccionar_compra() {
        if (!evejt.getBoolean_validar_select(tblcompra)) {
            String estado = evejt.getString_select(tblcompra, 4);
            String condicion = evejt.getString_select(tblcompra, 5);
            String est_credito = evejt.getString_select(tblcompra, 9);
            idcompra_insumo_select = evejt.getInt_select_id(tblcompra);
            if (estado.equals(est_ANULADO)) {
                btnanularventa.setEnabled(false);
            }
            if (estado.equals(est_EMITIDO)) {
                btnanularventa.setEnabled(true);
                if (est_credito.equals("CERRADO")) {
                    btnanularventa.setEnabled(false);
                }
                if (est_credito.equals("null")) {
                    btnanularventa.setEnabled(false);
                }
                if (condicion.equals(condicion_CONTADO)) {
                    btnanularventa.setEnabled(true);
                }
            }
            if (estado.equals(est_CONFIRMADO)) {
                btnanularventa.setEnabled(false);
            }
            if (condicion.equals(condicion_CONTADO)) {
                tabla_origen = caja.getTabla_origen_compra_contado();
                System.out.println("tabla_origen:" + tabla_origen);
            }
            if (condicion.equals(condicion_CREDITO)) {
                tabla_origen = caja.getTabla_origen_compra_credito();
                System.out.println("tabla_origen:" + tabla_origen);
            }
            int idcompra_insumo = evejt.getInt_select_id(tblcompra);
            icidao.tabla_item_compra_insumo_filtro(conn, tblitem_compra_insumo, idcompra_insumo);
        }
    }

    private void anular_compra(int idcompra_insumo, int idfinancista) {
        compi.setC1idcompra(idcompra_insumo);
        compi.setC3estado(est_ANULADO);
        caja.setC15estado(est_ANULADO);
        caja.setC13tabla_origen(tabla_origen);
        caja.setC12id_origen(idcompra_insumo);
        cfina.setC4estado(est_ANULADO);
        cfina.setC5monto_contado(0);
        cfina.setC6monto_credito(0);
        cfina.setC11fk_idcompra(idcompra_insumo);
        fina.setC1idfinancista(idfinancista);
        ciBO.update_anular_compra(conn, compi, caja, cfina, fina);
        cidao.actualizar_tabla_compra(conn, tblcompra);
    }

    private void boton_anular_compra() {
//        JOptionPane.showMessageDialog(null,"ANULAR NO ESTA HABILITADO POR EL MOMENTO.. HASTA ANULAR CREDITO COMPRA");
        if (!evejt.getBoolean_validar_select(tblcompra)) {
            if (evemen.MensajeGeneral_warning("ESTAS SEGURO DE ANULAR ESTA COMPRA", "ANULAR", "ACEPTAR", "CANCELAR")) {
                int idcompra = evejt.getInt_select_id(tblcompra);
                int idfinancista = evejt.getInt_select(tblcompra, 7);
                anular_compra(idcompra, idfinancista);
            }
        }
    }

    private void boton_imprimir_pos() {
        if (!evejt.getBoolean_validar_select(tblcompra)) {
            int idcompra = evejt.getInt_select_id(tblcompra);
            poscomp.boton_imprimir_pos_compra(conn, idcompra);
        }
    }

    private String filtro_estado() {
        String estado = "";
        String sumaestado = "";
        int contestado = 0;
        String condi = "";
        String fin = "";
        if (jCestado_emitido.isSelected()) {
            contestado++;
            if (contestado == 1) {
                condi = " and(";
                fin = ") ";
            } else {
                condi = " or";
            }
            estado = condi + " c.estado='" + est_EMITIDO + "' ";
            sumaestado = sumaestado + estado;
        }
        if (jCestado_confirmado.isSelected()) {
            contestado++;
            if (contestado == 1) {
                condi = " and(";
                fin = ") ";
            } else {
                condi = " or";
            }
            estado = condi + " c.estado='" + est_CONFIRMADO + "' ";
            sumaestado = sumaestado + estado;
        }
        if (jCestado_anulado.isSelected()) {
            contestado++;
            if (contestado == 1) {
                condi = " and(";
                fin = ") ";
            } else {
                condi = " or";
            }
            estado = condi + " c.estado='" + est_ANULADO + "' ";
            sumaestado = sumaestado + estado;
        }
        return sumaestado + fin;
    }

    private void buscar_nombre_producto() {
        if (txtprod_buscar_nombre.getText().trim().length() > 0) {
            if (idproducto == -1) {
                JOptionPane.showMessageDialog(null, "NO SE ENCONTRO NINGUN PRODUCTO\nESCRIBIR EL PRODUCTO", "ERROR", JOptionPane.ERROR_MESSAGE);
                reestableser_item_producto();
            } else {
                txtprod_cant.setBackground(Color.ORANGE);
                txtprod_cant.grabFocus();
            }
        } else {
            JOptionPane.showMessageDialog(null, "ESCRIBIR NOMBRE DEL PRODUCTO\nLUEGO SELECCIONAR EL CUADRO DE BUSQUEDA", "ERROR", JOptionPane.ERROR_MESSAGE);
            txtprod_buscar_nombre.grabFocus();
        }
    }

//    private void cargar_datos_caja_insertar1(int idcompra) {
//        cidao.cargar_compra(conn, compi, idcompra);
//        caja.setC2fecha_emision(evefec.getString_formato_fecha_hora());
//        caja.setC3descripcion("(COMPRA) id:" + idcompra + " Usuario:" + usu.getGlobal_nombre());
//        caja.setC4monto_venta_efectivo(0);
//        caja.setC5monto_venta_tarjeta(0);
//        caja.setC6monto_delivery(0);
//        caja.setC7monto_gasto(0);
//        caja.setC8monto_compra(compi.getC6monto_compra());
//        caja.setC9monto_vale(0);
//        caja.setC10monto_caja(0);
//        caja.setC11monto_cierre(0);
//        caja.setC12id_origen(idcompra);
//        caja.setC13tabla_origen(tabla_origen);
//        caja.setC15estado(est_EMITIDO);
//        caja.setC16fk_idusuario(usu.getGlobal_idusuario());
//        cdao.insertar_caja_detalle1(conn, caja);
//    }
    private void cargar_item_compra_cantidad(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            if (txtprod_cant.getText().trim().length() > 0) {
                String cant_actual = txtprod_cant.getText().trim();
                int Icant_actual = Integer.parseInt(cant_actual);
                Icant_actual = Icant_actual - 1;
                if (Icant_actual <= 0) {
                    Icant_actual = 1;
                }
                txtprod_cant.setText(String.valueOf(Icant_actual));
            }
            calcular_subtotal_item();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            if (txtprod_cant.getText().trim().length() > 0) {
                String cant_actual = txtprod_cant.getText().trim();
                int Icant_actual = Integer.parseInt(cant_actual);
                Icant_actual = Icant_actual + 1;
                txtprod_cant.setText(String.valueOf(Icant_actual));
            }
            calcular_subtotal_item();
        }
    }

    private void select_alquiler() {
        if (jCalquiler.isSelected()) {
            jCalquiler.setForeground(Color.RED);
            color_formulario(clacolor.getColor_shopp());
            
        } else {
            jCalquiler.setForeground(Color.BLACK);
            color_formulario(clacolor.getColor_insertar_primario());
        }
    }

    public FrmCompra() {
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

        gru_campo = new javax.swing.ButtonGroup();
        gru_cond = new javax.swing.ButtonGroup();
        jTabbedPane_VENTA = new javax.swing.JTabbedPane();
        panel_crear_compra = new javax.swing.JPanel();
        jTab_producto_ingrediente = new javax.swing.JTabbedPane();
        panel_insertar_pri_compra = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtprod_buscar_nombre = new javax.swing.JTextField();
        panel_insertar_seg_compra = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtobservacion = new javax.swing.JTextField();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jList_producto = new javax.swing.JList<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblitem_producto = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        txtprod_unidad = new javax.swing.JTextField();
        txtprod_cant = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtprod_pre_com = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jFsutotal = new javax.swing.JFormattedTextField();
        btnnuevo_insumo = new javax.swing.JButton();
        txtprod_codbarra = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtprod_stock = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jFtotal_guarani = new javax.swing.JFormattedTextField();
        btnconfirmar_insertar = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        btneliminar_item = new javax.swing.JButton();
        btnsumar = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        txtidcompra = new javax.swing.JTextField();
        panel_proveedor = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txtprovee_nombre = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtprovee_ruc = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        btnbuscar_provee = new javax.swing.JButton();
        btnnuevo_provee = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        txtnro_nota = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jRcond_contado = new javax.swing.JRadioButton();
        jRcond_credito = new javax.swing.JRadioButton();
        jPanel4 = new javax.swing.JPanel();
        jCfinancista = new javax.swing.JComboBox<>();
        jCalquiler = new javax.swing.JCheckBox();
        panel_base_1 = new javax.swing.JPanel();
        panel_tabla_compra = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblcompra = new javax.swing.JTable();
        btnanularventa = new javax.swing.JButton();
        btnimprimirNota = new javax.swing.JButton();
        panel_referencia_filtro = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txtbuscar_idventa = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtbuscar_fecha = new javax.swing.JTextField();
        jCestado_emitido = new javax.swing.JCheckBox();
        jCestado_confirmado = new javax.swing.JCheckBox();
        jCestado_anulado = new javax.swing.JCheckBox();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblitem_compra_insumo = new javax.swing.JTable();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosing(evt);
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

        panel_insertar_pri_compra.setBackground(new java.awt.Color(153, 204, 255));
        panel_insertar_pri_compra.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("BUSCAR PRODUCTO:");

        txtprod_buscar_nombre.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtprod_buscar_nombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtprod_buscar_nombreKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtprod_buscar_nombreKeyReleased(evt);
            }
        });

        panel_insertar_seg_compra.setBackground(new java.awt.Color(102, 204, 255));
        panel_insertar_seg_compra.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("OBSERVACION:");

        txtobservacion.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtobservacion.setText("ninguna");
        txtobservacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtobservacionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_insertar_seg_compraLayout = new javax.swing.GroupLayout(panel_insertar_seg_compra);
        panel_insertar_seg_compra.setLayout(panel_insertar_seg_compraLayout);
        panel_insertar_seg_compraLayout.setHorizontalGroup(
            panel_insertar_seg_compraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertar_seg_compraLayout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(txtobservacion)
                .addContainerGap())
        );
        panel_insertar_seg_compraLayout.setVerticalGroup(
            panel_insertar_seg_compraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertar_seg_compraLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_insertar_seg_compraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtobservacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(61, Short.MAX_VALUE))
        );

        jLayeredPane1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLayeredPane1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jList_producto.setBackground(new java.awt.Color(204, 204, 255));
        jList_producto.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jList_producto.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jList_producto.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6", "Item 7", "Item 8", "Item 9", "Item 10" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jList_producto.setSelectionBackground(new java.awt.Color(255, 51, 51));
        jList_producto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jList_productoMouseReleased(evt);
            }
        });
        jList_producto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jList_productoKeyPressed(evt);
            }
        });
        jLayeredPane1.add(jList_producto, new org.netbeans.lib.awtextra.AbsoluteConstraints(27, 8, 670, 190));

        tblitem_producto.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        tblitem_producto.setModel(new javax.swing.table.DefaultTableModel(
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
        tblitem_producto.setRowHeight(25);
        tblitem_producto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblitem_productoKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblitem_producto);

        jLayeredPane1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 1150, 200));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("UNIDAD:");

        txtprod_unidad.setEditable(false);
        txtprod_unidad.setBackground(new java.awt.Color(204, 204, 255));
        txtprod_unidad.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        txtprod_cant.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtprod_cant.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtprod_cant.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtprod_cantKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtprod_cantKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtprod_cantKeyTyped(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("CANTIDAD:");

        txtprod_pre_com.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtprod_pre_com.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtprod_pre_comKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtprod_pre_comKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtprod_pre_comKeyTyped(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("PRECIO COMPRA:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("PRECIO COMPRA:");

        jFsutotal.setEditable(false);
        jFsutotal.setBackground(new java.awt.Color(204, 204, 255));
        jFsutotal.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        jFsutotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFsutotal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        btnnuevo_insumo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/mini_nuevo.png"))); // NOI18N
        btnnuevo_insumo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevo_insumoActionPerformed(evt);
            }
        });

        txtprod_codbarra.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtprod_codbarra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtprod_codbarraKeyPressed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel14.setText("COD_BARRA:");

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel16.setText("STOCK:");

        txtprod_stock.setEditable(false);
        txtprod_stock.setBackground(new java.awt.Color(204, 204, 255));
        txtprod_stock.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jPanel2.setBackground(new java.awt.Color(255, 255, 153));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("TOTAL / GUARANI"));

        jFtotal_guarani.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFtotal_guarani.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_guarani.setFont(new java.awt.Font("Tahoma", 1, 26)); // NOI18N

        btnconfirmar_insertar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnconfirmar_insertar.setText("CONFIRMAR");
        btnconfirmar_insertar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnconfirmar_insertarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jFtotal_guarani)
                    .addComponent(btnconfirmar_insertar, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jFtotal_guarani, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnconfirmar_insertar, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE))
        );

        jButton1.setBackground(new java.awt.Color(255, 102, 0));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setText("CANCELAR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btneliminar_item.setText("ELIMINAR ITEM");
        btneliminar_item.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneliminar_itemActionPerformed(evt);
            }
        });

        btnsumar.setText("SUMAR TABLA");
        btnsumar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsumarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_insertar_pri_compraLayout = new javax.swing.GroupLayout(panel_insertar_pri_compra);
        panel_insertar_pri_compra.setLayout(panel_insertar_pri_compraLayout);
        panel_insertar_pri_compraLayout.setHorizontalGroup(
            panel_insertar_pri_compraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_insertar_pri_compraLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_insertar_pri_compraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1167, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel_insertar_pri_compraLayout.createSequentialGroup()
                        .addGroup(panel_insertar_pri_compraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtprod_codbarra, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_insertar_pri_compraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_insertar_pri_compraLayout.createSequentialGroup()
                                .addComponent(txtprod_buscar_nombre)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnnuevo_insumo, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panel_insertar_pri_compraLayout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_insertar_pri_compraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(txtprod_unidad, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(panel_insertar_pri_compraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16)
                            .addComponent(txtprod_stock, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(panel_insertar_pri_compraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_insertar_pri_compraLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4))
                            .addGroup(panel_insertar_pri_compraLayout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(txtprod_cant, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_insertar_pri_compraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(txtprod_pre_com, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_insertar_pri_compraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jFsutotal, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(4, 4, 4))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel_insertar_pri_compraLayout.createSequentialGroup()
                        .addComponent(panel_insertar_seg_compra, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_insertar_pri_compraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
                            .addComponent(btneliminar_item, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnsumar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panel_insertar_pri_compraLayout.setVerticalGroup(
            panel_insertar_pri_compraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertar_pri_compraLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_insertar_pri_compraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_insertar_pri_compraLayout.createSequentialGroup()
                        .addGroup(panel_insertar_pri_compraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_insertar_pri_compraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel5)
                                .addComponent(jLabel4)
                                .addComponent(jLabel16))
                            .addComponent(jLabel3)
                            .addComponent(jLabel1)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_insertar_pri_compraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnnuevo_insumo, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                            .addComponent(txtprod_unidad, javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(txtprod_codbarra, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtprod_buscar_nombre, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panel_insertar_pri_compraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtprod_pre_com, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtprod_cant, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE))))
                    .addGroup(panel_insertar_pri_compraLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFsutotal, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panel_insertar_pri_compraLayout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(txtprod_stock)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_pri_compraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panel_insertar_pri_compraLayout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btneliminar_item)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnsumar))
                    .addComponent(panel_insertar_seg_compra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(60, Short.MAX_VALUE))
        );

        jTab_producto_ingrediente.addTab("PRODUCTOS INSUMO", panel_insertar_pri_compra);

        jLabel12.setText("IDCOMPRA:");

        txtidcompra.setEditable(false);
        txtidcompra.setBackground(new java.awt.Color(0, 0, 255));
        txtidcompra.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtidcompra.setForeground(new java.awt.Color(255, 255, 0));
        txtidcompra.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        panel_proveedor.setBackground(new java.awt.Color(204, 204, 255));
        panel_proveedor.setBorder(javax.swing.BorderFactory.createTitledBorder("PROVEEDOR"));

        jLabel8.setText("PROVEEDOR:");

        jLabel13.setForeground(new java.awt.Color(255, 0, 0));
        jLabel13.setText("F1: BUSCAR");

        txtprovee_ruc.setEditable(false);

        jLabel9.setText("RUC:");

        btnbuscar_provee.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/mini_lupa.png"))); // NOI18N
        btnbuscar_provee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbuscar_proveeActionPerformed(evt);
            }
        });

        btnnuevo_provee.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/mini_nuevo.png"))); // NOI18N
        btnnuevo_provee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevo_proveeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_proveedorLayout = new javax.swing.GroupLayout(panel_proveedor);
        panel_proveedor.setLayout(panel_proveedorLayout);
        panel_proveedorLayout.setHorizontalGroup(
            panel_proveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_proveedorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtprovee_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtprovee_ruc, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnbuscar_provee, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnnuevo_provee, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_proveedorLayout.setVerticalGroup(
            panel_proveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnbuscar_provee, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_proveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel8)
                .addComponent(txtprovee_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel13)
                .addComponent(txtprovee_ruc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel9))
            .addComponent(btnnuevo_provee, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        jLabel15.setText("NUMERO DE NOTA:");

        txtnro_nota.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtnro_nota.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jPanel3.setBackground(new java.awt.Color(255, 255, 204));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("CONDICION"));

        gru_cond.add(jRcond_contado);
        jRcond_contado.setSelected(true);
        jRcond_contado.setText("CONTADO");

        gru_cond.add(jRcond_credito);
        jRcond_credito.setText("CREDITO");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jRcond_contado)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRcond_credito))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRcond_contado)
                    .addComponent(jRcond_credito)))
        );

        jPanel4.setBackground(new java.awt.Color(204, 204, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("FINANCISTA"));

        jCfinancista.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jCfinancista, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jCfinancista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jCalquiler.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jCalquiler.setText("ALQUILER");
        jCalquiler.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCalquilerItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout panel_crear_compraLayout = new javax.swing.GroupLayout(panel_crear_compra);
        panel_crear_compra.setLayout(panel_crear_compraLayout);
        panel_crear_compraLayout.setHorizontalGroup(
            panel_crear_compraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTab_producto_ingrediente, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(panel_crear_compraLayout.createSequentialGroup()
                .addGroup(panel_crear_compraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_crear_compraLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtidcompra, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtnro_nota, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCalquiler)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panel_crear_compraLayout.createSequentialGroup()
                        .addComponent(panel_proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(10, 10, 10))
        );
        panel_crear_compraLayout.setVerticalGroup(
            panel_crear_compraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_crear_compraLayout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(panel_crear_compraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel12)
                    .addComponent(txtidcompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(txtnro_nota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCalquiler))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_crear_compraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel_proveedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTab_producto_ingrediente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane_VENTA.addTab("CREAR COMPRA INSUMO", panel_crear_compra);

        panel_tabla_compra.setBackground(new java.awt.Color(153, 153, 255));
        panel_tabla_compra.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tblcompra.setModel(new javax.swing.table.DefaultTableModel(
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
        tblcompra.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblcompraMouseReleased(evt);
            }
        });
        jScrollPane4.setViewportView(tblcompra);

        javax.swing.GroupLayout panel_tabla_compraLayout = new javax.swing.GroupLayout(panel_tabla_compra);
        panel_tabla_compra.setLayout(panel_tabla_compraLayout);
        panel_tabla_compraLayout.setHorizontalGroup(
            panel_tabla_compraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_tabla_compraLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4)
                .addContainerGap())
        );
        panel_tabla_compraLayout.setVerticalGroup(
            panel_tabla_compraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_tabla_compraLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 368, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnanularventa.setBackground(new java.awt.Color(255, 51, 51));
        btnanularventa.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnanularventa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/venta/anular.png"))); // NOI18N
        btnanularventa.setText("ANULAR");
        btnanularventa.setToolTipText("");
        btnanularventa.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnanularventa.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnanularventa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnanularventaActionPerformed(evt);
            }
        });

        btnimprimirNota.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnimprimirNota.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/venta/ven_imprimir.png"))); // NOI18N
        btnimprimirNota.setText("IMPRIMIR");
        btnimprimirNota.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnimprimirNota.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnimprimirNota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnimprimirNotaActionPerformed(evt);
            }
        });

        panel_referencia_filtro.setBackground(new java.awt.Color(204, 204, 255));
        panel_referencia_filtro.setBorder(javax.swing.BorderFactory.createTitledBorder("FILTRO COMPRA"));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setText("IDCOMPRA:");

        txtbuscar_idventa.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtbuscar_idventa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtbuscar_idventaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtbuscar_idventaKeyTyped(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setText("FECHA:");

        txtbuscar_fecha.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtbuscar_fecha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtbuscar_fechaKeyPressed(evt);
            }
        });

        jCestado_emitido.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jCestado_emitido.setText("EMITIDO");
        jCestado_emitido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCestado_emitidoActionPerformed(evt);
            }
        });

        jCestado_confirmado.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jCestado_confirmado.setText("CONFIRMADO");
        jCestado_confirmado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCestado_confirmadoActionPerformed(evt);
            }
        });

        jCestado_anulado.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jCestado_anulado.setText("ANULADO");
        jCestado_anulado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCestado_anuladoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_referencia_filtroLayout = new javax.swing.GroupLayout(panel_referencia_filtro);
        panel_referencia_filtro.setLayout(panel_referencia_filtroLayout);
        panel_referencia_filtroLayout.setHorizontalGroup(
            panel_referencia_filtroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_referencia_filtroLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_referencia_filtroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCestado_anulado)
                    .addComponent(jCestado_confirmado)
                    .addGroup(panel_referencia_filtroLayout.createSequentialGroup()
                        .addGroup(panel_referencia_filtroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_referencia_filtroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtbuscar_idventa, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
                            .addComponent(txtbuscar_fecha)))
                    .addComponent(jCestado_emitido))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_referencia_filtroLayout.setVerticalGroup(
            panel_referencia_filtroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_referencia_filtroLayout.createSequentialGroup()
                .addGroup(panel_referencia_filtroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtbuscar_idventa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_referencia_filtroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtbuscar_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCestado_emitido)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCestado_confirmado)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCestado_anulado)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jPanel12.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tblitem_compra_insumo.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane8.setViewportView(tblitem_compra_insumo);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 608, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panel_base_1Layout = new javax.swing.GroupLayout(panel_base_1);
        panel_base_1.setLayout(panel_base_1Layout);
        panel_base_1Layout.setHorizontalGroup(
            panel_base_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_base_1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnimprimirNota, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnanularventa, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(168, 168, 168)
                .addComponent(panel_referencia_filtro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(panel_tabla_compra, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panel_base_1Layout.setVerticalGroup(
            panel_base_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_base_1Layout.createSequentialGroup()
                .addComponent(panel_tabla_compra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_base_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel_referencia_filtro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panel_base_1Layout.createSequentialGroup()
                        .addGroup(panel_base_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnanularventa, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
                            .addComponent(btnimprimirNota, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jTabbedPane_VENTA.addTab("FILTRO COMPRA", panel_base_1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane_VENTA)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane_VENTA, javax.swing.GroupLayout.PREFERRED_SIZE, 608, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
//        evetbl.maximizar_jinternal(this);
        ancho_item_producto();
        cidao.ancho_tabla_compra(tblcompra);
    }//GEN-LAST:event_formInternalFrameOpened

    private void txtprod_buscar_nombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtprod_buscar_nombreKeyReleased
        // TODO add your handling code here:
        eveconn.buscar_cargar_Jlista(conn, txtprod_buscar_nombre, jList_producto,
                prod_tabla,
                prod_buscar,
                prod_mostrar, 10);
    }//GEN-LAST:event_txtprod_buscar_nombreKeyReleased

    private void btneliminar_itemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneliminar_itemActionPerformed
        // TODO add your handling code here:

        boton_eliminar_item();
    }//GEN-LAST:event_btneliminar_itemActionPerformed

    private void btnconfirmar_insertarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnconfirmar_insertarActionPerformed
        // TODO add your handling code here:
        boton_comfirmar_compra();
    }//GEN-LAST:event_btnconfirmar_insertarActionPerformed

    private void btnanularventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnanularventaActionPerformed
        // TODO add your handling code here:
        boton_anular_compra();
    }//GEN-LAST:event_btnanularventaActionPerformed

    private void btnimprimirNotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnimprimirNotaActionPerformed
        // TODO add your handling code here:
        boton_imprimir_pos();
    }//GEN-LAST:event_btnimprimirNotaActionPerformed

    private void jCestado_emitidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCestado_emitidoActionPerformed
        // TODO add your handling code here:
        cidao.actualizar_tabla_compra_buscar(conn, tblcompra, filtro_estado());
    }//GEN-LAST:event_jCestado_emitidoActionPerformed

    private void jCestado_confirmadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCestado_confirmadoActionPerformed
        // TODO add your handling code here:
        cidao.actualizar_tabla_compra_buscar(conn, tblcompra, filtro_estado());
    }//GEN-LAST:event_jCestado_confirmadoActionPerformed

    private void jCestado_anuladoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCestado_anuladoActionPerformed
        // TODO add your handling code here:
        cidao.actualizar_tabla_compra_buscar(conn, tblcompra, filtro_estado());
    }//GEN-LAST:event_jCestado_anuladoActionPerformed

    private void txtbuscar_idventaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_idventaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txtbuscar_idventa.getText().trim().length() > 0) {
                String filtro = " and ci.idcompra_insumo=" + txtbuscar_idventa.getText() + " ";
//                cidao.actualizar_tabla_compra_insumo(connLocal, tblcompra_insumo, filtro);
            }
        }
    }//GEN-LAST:event_txtbuscar_idventaKeyPressed

    private void txtbuscar_idventaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_idventaKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txtbuscar_idventaKeyTyped

    private void txtbuscar_fechaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_fechaKeyPressed
        // TODO add your handling code here:
        evejtf.verificar_fecha(evt, txtbuscar_fecha);
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txtbuscar_fecha.getText().trim().length() > 0) {
                String filtro = " and date(ci.fecha_emision)='" + txtbuscar_fecha.getText() + "'";
//                cidao.actualizar_tabla_compra_insumo(connLocal, tblcompra_insumo, filtro);
            }
        }
    }//GEN-LAST:event_txtbuscar_fechaKeyPressed

    private void tblcompraMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblcompraMouseReleased
        // TODO add your handling code here:
        seleccionar_compra();
    }//GEN-LAST:event_tblcompraMouseReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        boton_cancelar_compra();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        // TODO add your handling code here:
    }//GEN-LAST:event_formInternalFrameClosing

    private void txtobservacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtobservacionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtobservacionActionPerformed

    private void txtprod_buscar_nombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtprod_buscar_nombreKeyPressed
        // TODO add your handling code here:
        evejtf.seleccionar_lista(evt, jList_producto);
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            buscar_nombre_producto();
        }
    }//GEN-LAST:event_txtprod_buscar_nombreKeyPressed

    private void jList_productoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jList_productoKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            seleccionar_buscar_producto();
        }
    }//GEN-LAST:event_jList_productoKeyPressed

    private void jList_productoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList_productoMouseReleased
        // TODO add your handling code here:
        seleccionar_buscar_producto();
    }//GEN-LAST:event_jList_productoMouseReleased

    private void txtprod_cantKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtprod_cantKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txtprod_cantKeyTyped

    private void txtprod_pre_comKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtprod_pre_comKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txtprod_pre_comKeyTyped

    private void txtprod_cantKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtprod_cantKeyReleased
        // TODO add your handling code here:
        calcular_subtotal_item();
    }//GEN-LAST:event_txtprod_cantKeyReleased

    private void txtprod_pre_comKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtprod_pre_comKeyReleased
        // TODO add your handling code here:
        calcular_subtotal_item();
    }//GEN-LAST:event_txtprod_pre_comKeyReleased

    private void txtprod_cantKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtprod_cantKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtprod_cant.setBackground(Color.WHITE);
            txtprod_pre_com.setBackground(Color.ORANGE);
            txtprod_pre_com.grabFocus();
        }
        cargar_item_compra_cantidad(evt);
    }//GEN-LAST:event_txtprod_cantKeyPressed

    private void txtprod_pre_comKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtprod_pre_comKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            cargar_item_producto();
        }
        if (evt.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            hab_update_precio_prod = true;
        }
    }//GEN-LAST:event_txtprod_pre_comKeyPressed

    private void btnnuevo_insumoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevo_insumoActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_btnnuevo_insumoActionPerformed

    private void btnsumarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsumarActionPerformed
        // TODO add your handling code here:
        sumar_item_compra();
    }//GEN-LAST:event_btnsumarActionPerformed

    private void tblitem_productoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblitem_productoKeyPressed
        // TODO add your handling code here:
        sumar_item_compra();
    }//GEN-LAST:event_tblitem_productoKeyPressed

    private void btnbuscar_proveeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbuscar_proveeActionPerformed
        // TODO add your handling code here:
        FrmJD_buscarproveedor frm = new FrmJD_buscarproveedor(null, true);
        frm.setVisible(true);
    }//GEN-LAST:event_btnbuscar_proveeActionPerformed

    private void btnnuevo_proveeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevo_proveeActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmProveedor());
    }//GEN-LAST:event_btnnuevo_proveeActionPerformed

    private void txtprod_codbarraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtprod_codbarraKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            carcar_producto_codbarra();
        }
    }//GEN-LAST:event_txtprod_codbarraKeyPressed

    private void jCalquilerItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCalquilerItemStateChanged
        // TODO add your handling code here:
        select_alquiler();
    }//GEN-LAST:event_jCalquilerItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnanularventa;
    private javax.swing.JButton btnbuscar_provee;
    private javax.swing.JButton btnconfirmar_insertar;
    private javax.swing.JButton btneliminar_item;
    private javax.swing.JButton btnimprimirNota;
    private javax.swing.JButton btnnuevo_insumo;
    private javax.swing.JButton btnnuevo_provee;
    private javax.swing.JButton btnsumar;
    private javax.swing.ButtonGroup gru_campo;
    private javax.swing.ButtonGroup gru_cond;
    private javax.swing.JButton jButton1;
    private javax.swing.JCheckBox jCalquiler;
    private javax.swing.JCheckBox jCestado_anulado;
    private javax.swing.JCheckBox jCestado_confirmado;
    private javax.swing.JCheckBox jCestado_emitido;
    private javax.swing.JComboBox<String> jCfinancista;
    private javax.swing.JFormattedTextField jFsutotal;
    private javax.swing.JFormattedTextField jFtotal_guarani;
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
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JList<String> jList_producto;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JRadioButton jRcond_contado;
    private javax.swing.JRadioButton jRcond_credito;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTabbedPane jTab_producto_ingrediente;
    private javax.swing.JTabbedPane jTabbedPane_VENTA;
    private javax.swing.JPanel panel_base_1;
    private javax.swing.JPanel panel_crear_compra;
    private javax.swing.JPanel panel_insertar_pri_compra;
    private javax.swing.JPanel panel_insertar_seg_compra;
    private javax.swing.JPanel panel_proveedor;
    private javax.swing.JPanel panel_referencia_filtro;
    private javax.swing.JPanel panel_tabla_compra;
    private javax.swing.JTable tblcompra;
    private javax.swing.JTable tblitem_compra_insumo;
    private javax.swing.JTable tblitem_producto;
    private javax.swing.JTextField txtbuscar_fecha;
    private javax.swing.JTextField txtbuscar_idventa;
    private javax.swing.JTextField txtidcompra;
    private javax.swing.JTextField txtnro_nota;
    private javax.swing.JTextField txtobservacion;
    public static javax.swing.JTextField txtprod_buscar_nombre;
    private javax.swing.JTextField txtprod_cant;
    private javax.swing.JTextField txtprod_codbarra;
    private javax.swing.JTextField txtprod_pre_com;
    private javax.swing.JTextField txtprod_stock;
    private javax.swing.JTextField txtprod_unidad;
    public static javax.swing.JTextField txtprovee_nombre;
    public static javax.swing.JTextField txtprovee_ruc;
    // End of variables declaration//GEN-END:variables

}
