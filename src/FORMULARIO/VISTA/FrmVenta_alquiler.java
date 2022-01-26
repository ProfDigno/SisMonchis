/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.VISTA;

import FORMULARIO.ENTIDAD.cotizacion;
import BASEDATO.LOCAL.ConnPostgres;
import BASEDATO.EvenConexion;
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
import FILTRO.ClaAuxFiltroVenta;
import FORMULARIO.BO.BO_cliente;
import FORMULARIO.BO.BO_venta;
import FORMULARIO.BO.BO_venta_alquiler;
import FORMULARIO.DAO.*;
import FORMULARIO.ENTIDAD.*;
import static FORMULARIO.VISTA.FrmCliente.txtdelivery;
import static FORMULARIO.VISTA.FrmCliente.txtzona;
import IMPRESORA_POS.PosImprimir_Venta;
import IMPRESORA_POS.PosImprimir_venta_alquiler;
import IMPRESORA_POS.PosImprimir_venta_mesa;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Digno
 */
public class FrmVenta_alquiler extends javax.swing.JInternalFrame {

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
    EvenCombobox evecmb = new EvenCombobox();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    PosImprimir_venta_alquiler posv = new PosImprimir_venta_alquiler();
    cliente clie = new cliente();
    item_venta_alquiler item = new item_venta_alquiler();
    DAO_item_venta_alquiler ivdao = new DAO_item_venta_alquiler();
    public static venta_alquiler ven_alq = new venta_alquiler();
    BO_venta_alquiler vBO = new BO_venta_alquiler();
    DAO_venta_alquiler vdao = new DAO_venta_alquiler();
    factura fac = new factura();
    DAO_cliente cdao = new DAO_cliente();
    producto prod = new producto();
    DAO_producto pdao = new DAO_producto();

//    public static caja_detalle caja = new caja_detalle();
//    DAO_caja_detalle cjdao = new DAO_caja_detalle();
    BO_cliente cBO = new BO_cliente();
    zona_delivery zona = new zona_delivery();
    DAO_zona_delivery zdao = new DAO_zona_delivery();
    cotizacion coti = new cotizacion();
    DAO_cotizacion codao = new DAO_cotizacion();
    usuario usu = new usuario();
    DAO_entregador endao = new DAO_entregador();
    caja_detalle_alquilado cdalq = new caja_detalle_alquilado();
    private DAO_caja_detalle_alquilado cdalq_dao = new DAO_caja_detalle_alquilado();
    private DAO_grupo_credito_cliente gccDAO = new DAO_grupo_credito_cliente();
    private credito_cliente cclie = new credito_cliente();
    private grupo_credito_cliente gcc = new grupo_credito_cliente();
//    private financista fina = new financista();
    Connection connLocal = ConnPostgres.getConnPosgres();
    private java.util.List<JButton> botones_categoria;
    private java.util.List<JButton> botones_unidad;
    private java.util.List<JButton> botones_marca;
    public static DefaultTableModel model_itemf = new DefaultTableModel();
    cla_color_pelete clacolor = new cla_color_pelete();
    ClaAuxFiltroVenta auxvent = new ClaAuxFiltroVenta();
    private int cant_boton_cate;
    private int cant_boton_unid;
    private int cant_boton_marca;
    private String fk_idproducto_unidad;
    private String fk_idproducto_categoria;
    private String fk_idproducto_marca;
    private String cantidad_producto = "0";
//    private int fk_idcliente_servi;
    private double monto_total;
    private double monto_delivery;
    private String zona_delivery;
    private String tipo_entrega;
    private String entrega_delivery = "#>DELIVERY<#";
    private String entrega_paqueta = "#>PARA_LLEVAR<#";
    private String entrega_funcio = "#>FUNCIONARIO<#";
    private String estado_ANULADO = "ANULADO";
    private String estado_EMITIDO = "EMITIDO";
    private String estado_RESERVADO = "RESERVADO";
    private String estado_ALQUILADO = "ALQUILADO";
    private String estado_DEVOLUCION = "DEVOLUCION";
    private String estado_USO_RESERVA = "USO_RESERVA";
    private String estado_FINALIZAR = "FINALIZADO";
    private int idventa_alquiler_ultimo;
//    private String indice_venta;
    private int fk_idcliente_local;
//    private boolean esFuncionario;
//    private String grupo_en_ingrediente;
    private double monto_alquilado_efectivo = 0;
    private double monto_alquilado_tarjeta = 0;
    private double monto_alquilado_transferencia = 0;
    private double monto_alquilado_credito = 0;
    private double monto_alquilado_reservado = 0;
    private String forma_pago = "SIN FORMA";
    private String tabla_origen = "ALQUILER";
    private double valor_redondeo = 500;
    private int Iidproducto;
    private boolean esCargadoCodBarra;
    private String forma_pago_EFECTIVO = "EFECTIVO";
    private String forma_pago_TARJETA = "TARJETA";
    private String forma_pago_COMBINADO = "COMBINADO";
    private String forma_pago_TRANSFERENCIA = "TRANSFERENCIA";
    private String forma_pago_PAGOPARCIAL = "PAGO-PARCIAL";
    private String forma_pago_CREDITO = "CREDITO";
    private String condicion_CONTADO = "CONTADO";
    private String condicion_CREDITO = "CREDITO";
    private String condicion_PAGOPARCIAL = "PAGO-PARCIAL";
    private boolean habilitar_editar_precio_venta;
    private boolean hab_venta_combinado;
    private boolean hab_carga_entregador;
    private int fk_identregador;
    private String hora_estandar = "12";
    private String estado_venta_alquiler;
    private String observacion_inicio = "NINGUNA";

    private void abrir_formulario() {
        String servidor = "";
        this.setTitle("VENTA ALQUILER--> USUARIO:" + usu.getGlobal_nombre() + servidor);
        evetbl.centrar_formulario_internalframa(this);
        botones_categoria = new ArrayList<>();
        botones_unidad = new ArrayList<>();
        botones_marca = new ArrayList<>();
        jList_cliente.setVisible(false);
        jCimprimir_ticket.setSelected(true);
        codao.cargar_cotizacion(coti, 1);

        reestableser_venta();
        cargar_boton_categoria();
        crear_item_producto();
        cargar_entregador();
    }

    void cargar_entregador() {
        evecmb.cargarCombobox(connLocal, cmbentregador, "identregador", "nombre", "entregador", "where activar=true");
        hab_carga_entregador = true;
    }

    void color_formulario(Color colorpanel) {
        panel_tabla_busca_cli.setBackground(colorpanel);
        panel_insertar_pri_item.setBackground(colorpanel);
        panel_insertar_pri_producto.setBackground(colorpanel);
        panel_referencia_categoria.setBackground(colorpanel);
        panel_referencia_unidad.setBackground(colorpanel);
        panel_tabla_venta.setBackground(colorpanel);
        panel_tabla_item.setBackground(colorpanel);
        panel_referencia_venta.setBackground(colorpanel);
        panel_base_1.setBackground(colorpanel);
        panel_estado.setBackground(colorpanel);
    }

    void crear_item_producto() {
        String dato[] = {"id", "descripcion", "P.Unit", "CP", "CR", "T.pagado", "T.reserva"};
        evejt.crear_tabla_datos(tblitem_producto, model_itemf, dato);
    }

    void ancho_item_producto() {
        int Ancho[] = {5, 42, 10, 6, 6, 12, 12};
        evejt.setAnchoColumnaJtable(tblitem_producto, Ancho);
    }

    boolean validar_cargar_item_producto() {
        if (evejt.getBoolean_validar_select(tblproducto)) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtcantidad_total, "CARGAR UNA CANTIDAD TOTAL")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtcantidad_cobrado, "CARGAR UNA CANTIDAD COBRADO")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtcantidad_reserva, "CARGAR UNA CANTIDAD RESERVSA")) {
            return false;
        }
        if (txtcantidad_total.getText().trim().length() > 4) {
            JOptionPane.showMessageDialog(txtcantidad_total, "EXEDE EL LIMITE DE CANTIDAD PERMITIDO", "ERROR", JOptionPane.ERROR_MESSAGE);
            txtcantidad_total.setText(null);
            txtcantidad_total.grabFocus();
            return false;
        }
        return true;
    }

    private void sumar_item_venta() {
        double total_pagado = evejt.getDouble_sumar_tabla(tblitem_producto, 5);
        double total_reservado = evejt.getDouble_sumar_tabla(tblitem_producto, 6);
        monto_total = total_pagado;// + total_reservado;
        monto_alquilado_efectivo = total_pagado;
        monto_alquilado_tarjeta = total_pagado;
        monto_alquilado_transferencia = total_pagado;
        monto_alquilado_credito = total_pagado;
        monto_alquilado_reservado = total_reservado;
        jFtotal_pagado.setValue(total_pagado);
        jFtotal_reservado.setValue(total_reservado);
    }

    void copiar_itemventa(int idventa) {
        String titulo = "duplicar_itemventa";
        String sql = "select fk_idproducto,descripcion,precio_venta,cantidad,tipo,grupo "
                + "from item_venta where fk_idventa=" + idventa
                + " order by iditem_venta asc";
        try {
            ResultSet rs = eveconn.getResulsetSQL(connLocal, sql, titulo);
            while (rs.next()) {
                String idproducto = rs.getString("fk_idproducto");
                String descripcion = rs.getString("descripcion");
                String precioUni = rs.getString("precio_venta");
                String cantidad = rs.getString("cantidad");
                String grupo = rs.getString("grupo");
                double Dcantidad = Double.parseDouble(cantidad);
                double DprecioUni = Double.parseDouble(precioUni);
                String total = String.valueOf(DprecioUni * Dcantidad);
                String tipo = rs.getString("tipo");
                String dato[] = {idproducto, tipo, descripcion, precioUni, cantidad, total, grupo};
                evejt.cargar_tabla_datos(tblitem_producto, model_itemf, dato);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
        }

    }

    void cargar_item_producto() {
        if (validar_cargar_item_producto()) {
            pdao.cargar_producto_por_idproducto(connLocal, prod, Iidproducto);
            String idproducto = String.valueOf(prod.getC1idproducto());
            String descripcion = prod.getC20_marca() + "-" + prod.getC18_unidad() + "-" + prod.getC3nombre();
            int Iprecio_mostrar = 0;
            if (jCver_promocion.isSelected()) {
                Iprecio_mostrar = (int) prod.getC22precio_venta_promocion();
            } else {
                Iprecio_mostrar = (int) prod.getC4precio_venta_minorista();
            }
            int Iprecio_mayo = (int) prod.getC5precio_venta_mayorista();
            int Icant_mayo = (int) prod.getC6cantidad_mayorista();
            String cant_cobrado = txtcantidad_cobrado.getText();
            String cant_reserva = txtcantidad_reserva.getText();
            int Icant_cobrado = Integer.parseInt(cant_cobrado);
            int Isubtotal_cobrado = 0;
            int Icant_reserva = Integer.parseInt(cant_reserva);
            int Isubtotal_reserva = 0;
            String Sprecio_venta = "0";
            if (habilitar_editar_precio_venta) {
                Sprecio_venta = txtprecio_venta.getText();
                int Iprecio_venta = Integer.parseInt(Sprecio_venta);
                Isubtotal_cobrado = Icant_cobrado * Iprecio_venta;
                Isubtotal_reserva = Icant_reserva * Iprecio_venta;
            } else {
                if (Icant_cobrado < Icant_mayo) {
                    Isubtotal_cobrado = Icant_cobrado * Iprecio_mostrar;
                    Isubtotal_reserva = Icant_reserva * Iprecio_mostrar;
                    Sprecio_venta = String.valueOf(Iprecio_mostrar);
                } else {
                    if (jCver_promocion.isSelected()) {
                        Isubtotal_cobrado = Icant_cobrado * Iprecio_mostrar;
                        Isubtotal_reserva = Icant_reserva * Iprecio_mostrar;
                        Sprecio_venta = String.valueOf(Iprecio_mostrar);
                    } else {
                        Isubtotal_cobrado = Icant_cobrado * Iprecio_mayo;
                        Isubtotal_reserva = Icant_reserva * Iprecio_mayo;
                        Sprecio_venta = String.valueOf(Iprecio_mayo);
                    }
                }
            }
            String Ssubtotal_cobrado = String.valueOf(Isubtotal_cobrado);
            String Ssubtotal_reserva = String.valueOf(Isubtotal_reserva);
            String dato[] = {idproducto, descripcion, Sprecio_venta, cant_cobrado, cant_reserva, Ssubtotal_cobrado, Ssubtotal_reserva};
            evejt.cargar_tabla_datos(tblitem_producto, model_itemf, dato);
            txtcantidad_total.setText(null);
            reestableser_item_venta();
            sumar_item_venta();
        }
    }

    void calculo_cantidad(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            cargar_item_producto();
        } else {
            int Icant_cobrado = evejtf.getInt_sumar_restar_cantidad(evt, txtcantidad_cobrado, false, 0);
            int Icant_total = 0;
            if (txtcantidad_total.getText().trim().length() > 0) {
                Icant_total = Integer.parseInt(txtcantidad_total.getText());
                if (Icant_total < Icant_cobrado) {
                    txtcantidad_total.setText(txtcantidad_cobrado.getText());
                }
            }
            if (txtcantidad_cobrado.getText().trim().length() > 0) {
                Icant_cobrado = Integer.parseInt(txtcantidad_cobrado.getText());
            }
            if (Icant_cobrado > 0) {
                pdao.cargar_producto_por_idproducto(connLocal, prod, Iidproducto);
                int Iprecio_mostrar = 0;
                if (jCver_promocion.isSelected()) {
                    Iprecio_mostrar = (int) prod.getC22precio_venta_promocion();
                } else {
                    Iprecio_mostrar = (int) prod.getC4precio_venta_minorista();
                }
                int Iprecio_mayo = (int) prod.getC5precio_venta_mayorista();
                int Icant_mayo = (int) prod.getC6cantidad_mayorista();
                int Isubtotal = 0;
                int Icant_reserva = Icant_total - Icant_cobrado;
                if (Icant_reserva >= 0) {
                    txtcantidad_reserva.setText(String.valueOf(Icant_reserva));
                } else {
                    txtcantidad_total.setText(String.valueOf(Icant_cobrado));
                    txtcantidad_reserva.setText(String.valueOf(0));
                }
                String Sprecio_venta = "0";
                if (Icant_cobrado < Icant_mayo) {
                    Isubtotal = Icant_cobrado * Iprecio_mostrar;
                    Sprecio_venta = String.valueOf(Iprecio_mostrar);
                    color_campo_item_venta(Color.white);
                } else {
                    Isubtotal = Icant_cobrado * Iprecio_mayo;
                    Sprecio_venta = String.valueOf(Iprecio_mayo);
                    color_campo_item_venta(Color.yellow);
                }
                String Ssubtotal = String.valueOf(Isubtotal);
                txtprecio_venta.setText(Sprecio_venta);
                txtsubtotal.setText(Ssubtotal);
            }
        }
    }

    void color_campo_item_venta(Color color) {
        txtprecio_venta.setBackground(color);
        txtsubtotal.setBackground(color);
        txtcantidad_total.setBackground(color);
    }

    void reestableser_item_venta() {
        txtprecio_venta.setText(null);
        txtsubtotal.setText(null);
        txtstock.setText(null);
        txtbuscar_producto.setText(null);
        txtcod_barra.setText(null);
        txtcantidad_total.setText(null);
        txtcantidad_reserva.setText(null);
        txtcantidad_cobrado.setText(null);

        txtfec_retirado_previsto.setText(evefec.getString_formato_fecha());
        txthora_retirado_previsto.setText(hora_estandar);
        txtminuto_retirado_previsto.setText("00");
        txtfec_devolusion_previsto.setText(evefec.getString_formato_fecha());
        txthora_devolusion_previsto.setText(hora_estandar);
        txtminuto_devolusion_previsto.setText("00");
        panel_insertar_pri_producto.setBackground(clacolor.getColor_insertar_primario());
        color_campo_item_venta(Color.white);
        esCargadoCodBarra = false;
        habilitar_editar_precio_venta = false;
        txtcod_barra.grabFocus();
    }

    private void cargar_boton_categoria() {
        String titulo = "cargar_boton_categoria";
        String sql = "SELECT  c.idproducto_categoria, c.nombre,c.orden \n"
                + "From producto_categoria c,producto p \n"
                + "where c.idproducto_categoria=p.fk_idproducto_categoria \n"
                + "and c.activar=true \n"
                + "and p.activar=true \n"
                + "and p.alquilado=true \n"
                + "group by 1,2,3\n"
                + "order by c.orden desc;";
        try {
            ResultSet rs = eveconn.getResulsetSQL(connLocal, sql, titulo);
            panel_referencia_categoria.removeAll();
            botones_categoria.clear();
            int cant = 0;
            while (rs.next()) {
                cant++;
                String textboton = rs.getString("nombre");
                String nameboton = rs.getString("idproducto_categoria");
                crear_boton_categoria(textboton, nameboton);
                if (cant == 1) {
                    fk_idproducto_categoria = nameboton;
                    actualizar_tabla_producto(1);
                }
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
        }
    }

    void crear_boton_categoria(String textboton, String nameboton) {
        JButton boton = new JButton(textboton);
        boton.setFont(new Font("Arial", Font.BOLD, 12));
        boton.setName(nameboton);
        panel_referencia_categoria.add(boton);
        botones_categoria.add(boton);
        cant_boton_cate++;
        boton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                evejt.mostrar_JTabbedPane(jTab_producto_ingrediente, 0);
                for (int p = 0; p < cant_boton_cate; p++) {
                    botones_categoria.get(p).setBackground(new java.awt.Color(255, 255, 255));
                }
                ((JButton) e.getSource()).setBackground(new java.awt.Color(153, 153, 255));
                fk_idproducto_categoria = ((JButton) e.getSource()).getName();
                cargar_boton_unidad(fk_idproducto_categoria);
                cargar_boton_marca(fk_idproducto_categoria);
                System.out.println("fk_idproducto_categoria:" + fk_idproducto_categoria);
                actualizar_tabla_producto(1);
            }
        });
        panel_referencia_categoria.updateUI();
    }

    void cargar_boton_unidad(String idproducto_categoria) {
        String titulo = "cargar_boton_unidad";
        panel_referencia_unidad.removeAll();
        botones_unidad.clear();
        cant_boton_unid = 0;
        String sql = "select u.idproducto_unidad, u.nombre \n"
                + "from producto p,producto_categoria c,producto_unidad u \n"
                + "where p.fk_idproducto_categoria=c.idproducto_categoria \n"
                + "and p.fk_idproducto_unidad=u.idproducto_unidad\n"
                + "and c.idproducto_categoria=" + idproducto_categoria
                + " group by 1,2 order by u.nombre asc  limit 8;";
        try {
            ResultSet rs = eveconn.getResulsetSQL(connLocal, sql, titulo);
            while (rs.next()) {
                String textboton = rs.getString("nombre");
                String nameboton = rs.getString("idproducto_unidad");
                boton_agregar_unidad(textboton, nameboton);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
        }
    }

    void cargar_boton_marca(String idproducto_categoria) {
        String titulo = "cargar_boton_marca";
        panel_referencia_marca.removeAll();
        botones_marca.clear();
        cant_boton_marca = 0;
        String sql = "select u.idproducto_marca, u.nombre \n"
                + "from producto p,producto_categoria c,producto_marca u \n"
                + "where p.fk_idproducto_categoria=c.idproducto_categoria \n"
                + "and p.fk_idproducto_marca=u.idproducto_marca\n"
                + "and c.idproducto_categoria=" + idproducto_categoria
                + " group by 1,2 order by u.nombre asc  limit 8;";
        try {
            ResultSet rs = eveconn.getResulsetSQL(connLocal, sql, titulo);
            while (rs.next()) {
                String textboton = rs.getString("nombre");
                String nameboton = rs.getString("idproducto_marca");
                boton_agregar_marca(textboton, nameboton);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
        }
    }

    void boton_agregar_unidad(String textboton, String nameboton) {
        JButton boton = new JButton(textboton);
        boton.setFont(new Font("Arial", Font.BOLD, 10));
        boton.setName(nameboton);
        panel_referencia_unidad.add(boton);
        botones_unidad.add(boton);
        cant_boton_unid++;
        boton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                evejt.mostrar_JTabbedPane(jTab_producto_ingrediente, 0);
                for (int p = 0; p < cant_boton_unid; p++) {
                    botones_unidad.get(p).setBackground(new java.awt.Color(255, 255, 255));
                }
                ((JButton) e.getSource()).setBackground(new java.awt.Color(153, 153, 255));
                fk_idproducto_unidad = ((JButton) e.getSource()).getName();
                System.out.println("fk_idproducto_unidad: " + fk_idproducto_unidad);
                actualizar_tabla_producto(2);
            }
        });
        panel_referencia_unidad.updateUI();
    }

    void boton_agregar_marca(String textboton, String nameboton) {
        JButton boton = new JButton(textboton);
        boton.setFont(new Font("Arial", Font.BOLD, 10));
        boton.setName(nameboton);
        panel_referencia_marca.add(boton);
        botones_marca.add(boton);
        cant_boton_marca++;
        boton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                evejt.mostrar_JTabbedPane(jTab_producto_ingrediente, 0);
                for (int p = 0; p < cant_boton_marca; p++) {
                    botones_marca.get(p).setBackground(new java.awt.Color(255, 255, 255));
                }
                ((JButton) e.getSource()).setBackground(new java.awt.Color(153, 153, 255));
                fk_idproducto_marca = ((JButton) e.getSource()).getName();
                System.out.println("fk_idproducto_marca: " + fk_idproducto_marca);
                actualizar_tabla_producto(4);
            }
        });
        panel_referencia_marca.updateUI();
    }

    void actualizar_tabla_producto(int tipo) {
        String filtro_categoria = "";
        String filtro_unidad = "";
        String filtro_producto = "";
        String filtro_marca = "";
        if (tipo == 1) {
            filtro_categoria = " and p.fk_idproducto_categoria=" + fk_idproducto_categoria;
        }
        if (tipo == 2) {
            filtro_categoria = " and p.fk_idproducto_categoria=" + fk_idproducto_categoria;
            filtro_unidad = " and p.fk_idproducto_unidad=" + fk_idproducto_unidad;
        }
        if (tipo == 3) {
            if (txtbuscar_producto.getText().trim().length() > 0) {
                String por = "%";
                String temp = por + txtbuscar_producto.getText() + por;
                filtro_producto = " and concat(pm.nombre,'-',u.nombre,'-',p.nombre) ilike'" + temp + "' ";
            }
        }
        if (tipo == 4) {
            filtro_categoria = " and p.fk_idproducto_categoria=" + fk_idproducto_categoria;
            filtro_marca = " and p.fk_idproducto_marca=" + fk_idproducto_marca;
        }
        if (tipo == 5) {
            if (txtcod_barra.getText().trim().length() > 0) {
                String temp = txtcod_barra.getText();
                filtro_producto = " and p.cod_barra='" + temp + "' ";
            }
        }
        String precio_mostrar = "error";
        if (jCver_promocion.isSelected()) {
            precio_mostrar = "TRIM(to_char(p.precio_venta_promocion,'999G999G999')) as pv_promo";
        } else {
            precio_mostrar = "TRIM(to_char(p.precio_venta_minorista,'999G999G999')) as pv_mino";
        }
        String sql = "select p.idproducto as idp,\n"
                + "(pm.nombre||'-'||u.nombre||'-'||p.nombre) as marca_unid_nom,\n"
                + "p.stock," + precio_mostrar + ", \n"
                + "p.cantidad_mayorista as mayor,"
                + "TRIM(to_char(p.precio_venta_mayorista,'999G999G999')) as pv_mayo \n"
                + "from producto p,producto_categoria c,producto_unidad u,producto_marca pm \n"
                + "where p.fk_idproducto_categoria=c.idproducto_categoria \n"
                + "and p.fk_idproducto_unidad=u.idproducto_unidad \n"
                + "and p.fk_idproducto_marca=pm.idproducto_marca \n"
                + "and c.activar=true \n"
                + "and p.alquilado=true \n"
                + "and p.activar=true \n" + filtro_categoria + filtro_unidad + filtro_producto + filtro_marca
                + " order by p.idproducto  asc;";
        eveconn.Select_cargar_jtable(connLocal, sql, tblproducto);
        ancho_tabla_producto();
    }

    void pre_cargar_item_venta() {
        String Sprecio_venta = "0";
        int Iprecio_mino = 0;
        if (jCver_promocion.isSelected()) {
            Iprecio_mino = (int) prod.getC22precio_venta_promocion();
        } else {
            Iprecio_mino = (int) prod.getC4precio_venta_minorista();
        }
        int Istock = (int) prod.getC8stock();
        Sprecio_venta = String.valueOf(Iprecio_mino);
        String Sstock = String.valueOf(Istock);
        txtbuscar_producto.setText(prod.getC20_marca() + "-" + prod.getC18_unidad() + "-" + prod.getC3nombre());
        txtcod_barra.setText(prod.getC2cod_barra());
        if (prod.getC8stock() <= prod.getC9stock_min()) {
            panel_insertar_pri_producto.setBackground(Color.red);
        } else {
            panel_insertar_pri_producto.setBackground(clacolor.getColor_insertar_primario());
        }
        txtstock.setText(Sstock);
        txtcantidad_total.setText(null);
        txtprecio_venta.setText(Sprecio_venta);
        txtsubtotal.setText(Sprecio_venta);
        txtcantidad_total.grabFocus();
    }

    void buscar_cod_barra_producto() {
        if (pdao.getBoolean_cargar_producto_por_codbarra(connLocal, prod, txtcod_barra.getText())) {
            actualizar_tabla_producto(5);
            tblproducto.changeSelection(0, 0, false, false);
            color_campo_item_venta(Color.white);
            Iidproducto = prod.getC1idproducto();
            pre_cargar_item_venta();
        } else {
            JOptionPane.showMessageDialog(null, "NO SE ENCONTRO NINGUN PRODUCTO", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    void seleccionar_producto() {
        color_campo_item_venta(Color.white);
        Iidproducto = evejt.getInt_select_id(tblproducto);
        pdao.cargar_producto_por_idproducto(connLocal, prod, Iidproducto);
        pre_cargar_item_venta();
    }

    void ancho_tabla_producto() {
        int Ancho[] = {5, 53, 9, 12, 9, 12};
        evejt.setAnchoColumnaJtable(tblproducto, Ancho);
    }

    void cargar_cantidad_producto(int cant) {
        txtcantidad_total.setText(String.valueOf(cant));
        cargar_item_producto();
    }

    void limpiar_cliente() {
//        evejt.getBoolean_Eliminar_Fila_delivery(tblitem_producto, model_itemf);
        tipo_entrega = entrega_paqueta;
        lblidcliente.setText("id:0");
        fk_idcliente_local = 1;
        txtbucarCliente_nombre.setText(null);
        txtbucarCliente_telefono.setText(null);
        txtbucarCliente_ruc.setText(null);
        txtbucarCliente_direccion.setText(null);
        txtdireccion_alquiler.setText(null);
        txtmonto_delivery.setText(null);
        txtbucarCliente_telefono.grabFocus();

    }

    void seleccionar_cargar_cliente(int tipo) {
        if (tipo == 1) {
            fk_idcliente_local = eveconn.getInt_Solo_seleccionar_JLista(connLocal, jList_cliente, "cliente", clie.getCliente_concat(), "idcliente");
        }
        if (tipo == 2) {
            fk_idcliente_local = (eveconn.getInt_ultimoID_mas_uno(connLocal, clie.getTabla(), clie.getIdtabla())) - 1;
        }
        if (tipo == 3) {
            fk_idcliente_local = evejt.getInt_select_id(tblbuscar_cliente);
        }
        if (tipo == 4) {
            fk_idcliente_local = ven_alq.getC19fk_idcliente();
        }
        evejt.getBoolean_Eliminar_Fila_delivery(tblitem_producto, model_itemf);
        tipo_entrega = entrega_paqueta;
        System.out.println("idclientelocal:" + fk_idcliente_local);
        lblidcliente.setText("id:" + fk_idcliente_local);
        cdao.cargar_cliente(connLocal, clie, fk_idcliente_local);
        monto_delivery = clie.getC11deliveryDouble();
        zona_delivery = clie.getC10zona();
        txtbucarCliente_nombre.setText(clie.getC3nombre());
        txtbucarCliente_telefono.setText(clie.getC5telefono());
        txtbucarCliente_ruc.setText(clie.getC6ruc());
        txtbucarCliente_direccion.setText(clie.getC4direccion());
        txtdireccion_alquiler.setText(clie.getC4direccion());
        jFsaldo_credito.setValue(clie.getC13saldo_credito());
        txtmonto_delivery.setText(String.valueOf((int) monto_delivery));
//        ven_alq.setConfirmado_carga_reserva(false);
    }

    void boton_eliminar_item() {
        if (!evejt.getBoolean_validar_select(tblitem_producto)) {
            if (evejt.getBoolean_Eliminar_Fila(tblitem_producto, model_itemf)) {
                sumar_item_venta();
            }
        }
    }

    boolean validar_guardar_venta() {

        if (fk_idcliente_local == 1) {
            JOptionPane.showMessageDialog(null, "BUSCAR UN CLIENTE");
            boton_buscar_cliente_venta();
            return false;
        }
        if (evejt.getBoolean_validar_cant_cargado(tblitem_producto)) {
            return false;
        } else {

        }
        if (evejtf.getBoo_JTextField_vacio(txtfec_retirado_previsto, "INGRESAR FECHA ")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txthora_retirado_previsto, "INGRESAR HORA ")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtminuto_retirado_previsto, "INGRESAR MINUTO ")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtfec_devolusion_previsto, "INGRESAR FECHA ")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txthora_devolusion_previsto, "INGRESAR HORA ")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtminuto_devolusion_previsto, "INGRESAR MINUTO ")) {
            return false;
        }
        String fecha_retirado_previsto = txtfec_retirado_previsto.getText() + " " + txthora_retirado_previsto.getText() + ":" + txtminuto_retirado_previsto.getText() + ":00.00";
        String fecha_devolusion_previsto = txtfec_devolusion_previsto.getText() + " " + txthora_devolusion_previsto.getText() + ":" + txtminuto_devolusion_previsto.getText() + ":00.00";
        if (evefec.getTimestamp_fecha_cargado(fecha_retirado_previsto).equals(evefec.getTimestamp_fecha_cargado(fecha_devolusion_previsto))) {
            JOptionPane.showMessageDialog(jPanel_fecha, "LA FECHA DE RETIRADO Y DEVOLUSION NO PUEDE SER IGUAL ");
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtobservacion, "INGRESAR UNA OBSERVACION ")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtdireccion_alquiler, "INGRESAR UNA DIRECCION DONDE ENTREGAR ")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtmonto_delivery, "INGRESAR UN MONTO DE DELIVERY ")) {
            return false;
        }
        if (cmbentregador.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(cmbentregador, "SELECCIONAR UN ENTREGADOR");
            return false;
        } else {
            fk_identregador = evecmb.getInt_seleccionar_COMBOBOX(connLocal, cmbentregador, "identregador", "nombre", "entregador");
        }
        if (jRcond_pagoparcial.isSelected()) {
            if (evejtf.getBoo_JTextField_vacio(txtmonto_pago_parcial, "SE DEBE CARGAR UN PAGO PARCIAL")) {
                return false;
            }
            if (evejtf.getBoo_JTextField_vacio(txtfec_vence_credito, "SE DEBE CARGAR UNA FECHA DE VENCIMIENTO")) {
                return false;
            }
        }
        return true;
    }

    void reestableser_venta() {
        color_formulario(clacolor.getColor_insertar_primario());
        idventa_alquiler_ultimo = (eveconn.getInt_ultimoID_mas_uno(connLocal, ven_alq.getTb_venta_alquiler(), ven_alq.getId_idventa_alquiler()));
        vdao.actualizar_tabla_venta_alquiler(connLocal, tblventa, "");
        txtidventa.setText(String.valueOf(idventa_alquiler_ultimo));
        txtbuscar_fecha.setText(evefec.getString_formato_fecha());
        txtfec_vence_credito.setText(evefec.getString_formato_fecha());
        jCestado_emitido.setSelected(true);
        jCestado_finalizado.setSelected(true);
        jCestado_alquilado.setSelected(true);
        jCestado_devolucion.setSelected(true);
        jCestado_usoreserva.setSelected(true);
        jCestado_anulado.setSelected(false);
        actualizar_venta(3);
        actualizar_tabla_producto(1);
        tipo_entrega = entrega_paqueta;
        estado_venta_alquiler = estado_EMITIDO;
        monto_total = 0;
        monto_delivery = 0;
        txtcantidad_total.setText(null);
        txtobservacion.setText(observacion_inicio);
        btnconfirmar_venta_efectivo.setBackground(Color.white);
        jFtotal_pagado.setValue(monto_total);
        jFtotal_reservado.setValue(monto_total);
        jFsaldo_credito.setValue(monto_total);
        jRcond_contado.setSelected(true);
        select_condicion();
        evejt.limpiar_tabla_datos(model_itemf);
        evejt.mostrar_JTabbedPane(jTab_producto_ingrediente, 0);
        cmbentregador.setSelectedIndex(0);
        limpiar_cliente();

        reestableser_item_venta();
    }

    void boton_factura() {
        if (!evejt.getBoolean_validar_select(tblventa)) {
            fac.setFactura_cargada(true);
            int idventa = (evejt.getInt_select_id(tblventa));
//            vdao.cargar_venta(ven, idventa);
            evetbl.abrir_TablaJinternal(new FrmFactura());
        }
    }

    void actualizar_venta(int tipo) {
        String filtro = "";
        String campo_filtro = "";
//        String filtro_estado = auxvent.filtro_estado(jCestado_emitido, jCestado_terminado, jCestado_anulado);
        String filtro_estado = auxvent.filtro_estado_alquiler(jCestado_emitido, jCestado_finalizado, jCestado_usoreserva, jCestado_alquilado, jCestado_devolucion, jCestado_anulado);
        campo_filtro = "(c.idcliente||'-'||c.nombre) as cliente";
        if (tipo == 1) {
            filtro = filtro_estado;
        }
        if (tipo == 2) {
            if (txtbuscar_idventa.getText().trim().length() > 0) {
                filtro = " and v.idventa_alquiler=" + txtbuscar_idventa.getText() + " ";
            }
        }
        if (tipo == 3) {
            if (txtbuscar_fecha.getText().trim().length() > 0) {
                filtro = " and date(v.fecha_retirado_real)='" + txtbuscar_fecha.getText() + "' " + filtro_estado;
            }
        }
        vdao.actualizar_tabla_venta_alquiler(connLocal, tblventa, filtro);
        lblcantidad_filtro.setText("CANT FILTRO: ( " + tblventa.getRowCount() + " )");
    }

    void boton_estado_venta_anular() {
        if (!evejt.getBoolean_validar_select(tblventa)) {
            if (evemen.MensajeGeneral_warning("ESTAS SEGURO DE ANULAR LA VENTA", "ANULAR", "ANULAR", "CANCELAR")) {
                int idventa = evejt.getInt_select_id(tblventa);

                actualizar_venta(3);
            }
        }
    }

    void boton_forma_pago_EFECTIVO() {
        if (!evejt.getBoolean_validar_select(tblventa)) {
            if (evemen.MensajeGeneral_warning("ESTAS SEGURO DE PASAR FORMA DE PAGO A EFECTIVO", "FORMA PAGO", "EFECTIVO", "CANCELAR")) {
                int idventa = evejt.getInt_select_id(tblventa);

                actualizar_venta(3);
            }
        }
    }

    void boton_forma_pago_TARJETA() {
        if (!evejt.getBoolean_validar_select(tblventa)) {
            if (evemen.MensajeGeneral_warning("ESTAS SEGURO DE PASAR FORMA DE PAGO A TARJETA", "FORMA PAGO", "TARJETA", "CANCELAR")) {
                int idventa = evejt.getInt_select_id(tblventa);

                actualizar_venta(3);
            }
        }
    }

    private String gelString_condicion() {
        String condicion = "error";
        if (jRcond_contado.isSelected()) {
            condicion = condicion_CONTADO;
        }
        if (jRcond_credito.isSelected()) {
            condicion = condicion_CREDITO;
        }
        if (jRcond_pagoparcial.isSelected()) {
            condicion = condicion_PAGOPARCIAL;
        }
        return condicion;
    }

    private void select_condicion() {
        if (jRcond_contado.isSelected()) {
            btnconfirmar_venta_efectivo.setText(forma_pago_EFECTIVO);
            btnconfirmar_venta_tarjeta.setVisible(true);
            btnconfirmar_venta_transferencia.setVisible(true);
            btnconfirmar_venta_combinar.setVisible(true);
            ocultar_pago_parcial(false);
        }
        if (jRcond_credito.isSelected()) {
            btnconfirmar_venta_efectivo.setText(forma_pago_CREDITO);
            btnconfirmar_venta_tarjeta.setVisible(false);
            btnconfirmar_venta_transferencia.setVisible(false);
            btnconfirmar_venta_combinar.setVisible(false);
            ocultar_pago_parcial(false);
            lblvence_credito.setVisible(true);
            txtfec_vence_credito.setVisible(true);
        }
        if (jRcond_pagoparcial.isSelected()) {
            btnconfirmar_venta_efectivo.setText(forma_pago_EFECTIVO);
            btnconfirmar_venta_tarjeta.setVisible(true);
            btnconfirmar_venta_transferencia.setVisible(true);
            btnconfirmar_venta_combinar.setVisible(true);
            ocultar_pago_parcial(true);
        }
    }

    void cargar_dato_alquiler() {
        String fecha_retirado_previsto = txtfec_retirado_previsto.getText() + " " + txthora_retirado_previsto.getText() + ":" + txtminuto_retirado_previsto.getText() + ":00.00";
        String fecha_devolusion_previsto = txtfec_devolusion_previsto.getText() + " " + txthora_devolusion_previsto.getText() + ":" + txtminuto_devolusion_previsto.getText() + ":30.00";
        monto_delivery = Double.parseDouble(txtmonto_delivery.getText());
        ven_alq.setC2fecha_creado("now()");
        ven_alq.setC3fecha_retirado_previsto(fecha_retirado_previsto);
        ven_alq.setC4fecha_retirado_real(fecha_retirado_previsto);
        ven_alq.setC5fecha_devolusion_previsto(fecha_devolusion_previsto);
        ven_alq.setC6fecha_devolusion_real(fecha_devolusion_previsto);
        ven_alq.setC7monto_total(monto_total);
        ven_alq.setC8monto_alquilado_efectivo(monto_alquilado_efectivo);
        ven_alq.setC9monto_alquilado_tarjeta(monto_alquilado_tarjeta);
        ven_alq.setC10monto_alquilado_transferencia(monto_alquilado_transferencia);
        ven_alq.setC11monto_delivery(monto_delivery);
        ven_alq.setC12forma_pago(forma_pago);
        ven_alq.setC13condicion(gelString_condicion());
        ven_alq.setC14alquiler_retirado(false);
        ven_alq.setC15alquiler_devolusion(false);
        ven_alq.setC16direccion_alquiler(txtdireccion_alquiler.getText());
        ven_alq.setC17observacion(txtobservacion.getText());
        ven_alq.setC18estado(estado_venta_alquiler);
        ven_alq.setC19fk_idcliente(fk_idcliente_local);
        ven_alq.setC20fk_identregador(fk_identregador);
        ven_alq.setC21monto_alquilado_credito(monto_alquilado_credito);
        ven_alq.setC28monto_alquilado_reservado(monto_alquilado_reservado);
    }

    void cargar_dato_caja_alquilado() {
        cdalq_dao.limpiar_caja_detalle_alquilado(cdalq);
        cdalq.setC3descripcion("(ALQUILER) ID:" + idventa_alquiler_ultimo + " CLIENTE:" + txtbucarCliente_nombre.getText() + " DIREC:" + txtdireccion_alquiler.getText());
        cdalq.setC4tabla_origen(tabla_origen);
        cdalq.setC5estado(estado_venta_alquiler);
        cdalq.setC7monto_alquilado_efectivo(monto_alquilado_efectivo);
        cdalq.setC8monto_alquilado_tarjeta(monto_alquilado_tarjeta);
        cdalq.setC9monto_alquilado_transferencia(monto_alquilado_transferencia);
        cdalq.setC11monto_delivery(monto_delivery);
        cdalq.setC20fk_idventa_alquiler(idventa_alquiler_ultimo);
        cdalq.setC24monto_alquilado_credio(monto_alquilado_credito);
        cdalq.setC25forma_pago(forma_pago);
    }

    private String getDescripcion_item_venta() {
        String suma_descripcion = "";
        for (int row = 0; row < tblitem_producto.getRowCount(); row++) {
            String descripcion = ((tblitem_producto.getModel().getValueAt(row, 1).toString()));
            suma_descripcion = suma_descripcion + descripcion + ", ";
        }
        return suma_descripcion;
    }

    private void cargar_credito_cliente(boolean escredito, boolean espagoparcial) {
        if (escredito || espagoparcial) {
            double monto_credito = 0;
            if (espagoparcial) {
                monto_credito = monto_alquilado_credito;
            } else {
                monto_credito = monto_alquilado_efectivo + monto_alquilado_tarjeta + monto_alquilado_transferencia + monto_alquilado_credito;
            }
            gccDAO.cargar_grupo_credito_cliente_id(connLocal, gcc, fk_idcliente_local);
            cclie.setC3descripcion(getDescripcion_item_venta());
            cclie.setC4estado(estado_venta_alquiler);
            cclie.setC5monto_contado(0);
            cclie.setC6monto_credito(monto_credito);
            cclie.setC7tabla_origen(tabla_origen);
            cclie.setC8fk_idgrupo_credito_cliente(gcc.getC1idgrupo_credito_cliente());
            cclie.setC9fk_idsaldo_credito_cliente(0);
            cclie.setC10fk_idrecibo_pago_cliente(0);
            cclie.setC11fk_idventa_alquiler(idventa_alquiler_ultimo);
            cclie.setC12vence(true);
            cclie.setC13fecha_vence(txtfec_vence_credito.getText() + " 12:00:00.00");
            clie.setC1idcliente(fk_idcliente_local);
        }
    }

    ///###################CLIENTE#####################
    private void limpiar_buscardor_cliente() {
        jCfuncionario.setSelected(false);
        txtbuscador_cliente_nombre.setText(null);
        txtbuscador_cliente_telefono.setText(null);
        txtbuscador_cliente_ruc.setText(null);
        txtbuscador_cliente_telefono.grabFocus();
        cdao.buscar_tabla_cliente_zona(connLocal, tblbuscar_cliente, txtbuscador_cliente_telefono, jCfuncionario, 2);
    }

    private void boton_buscar_cliente_venta() {
        evejt.mostrar_JTabbedPane(jTabbedPane_VENTA, 2);
        limpiar_buscardor_cliente();
    }

    void boton_venta_efectivo() {
        sumar_item_venta();
        monto_alquilado_tarjeta = 0;
        monto_alquilado_transferencia = 0;
        if (jRcond_credito.isSelected()) {
            monto_alquilado_efectivo = 0;
            forma_pago = forma_pago_CREDITO;
        }
        if (jRcond_contado.isSelected()) {
            monto_alquilado_credito = 0;
            forma_pago = forma_pago_EFECTIVO;//"EFECTIVO";
        }
        if (jRcond_pagoparcial.isSelected()) {
            if (txtmonto_pago_parcial.getText().trim().length() > 0) {
                double pago_parcial = Double.parseDouble(txtmonto_pago_parcial.getText());
                monto_alquilado_efectivo = pago_parcial;
                monto_alquilado_credito = (monto_total - pago_parcial);
            }
            forma_pago = forma_pago_EFECTIVO;//"EFECTIVO";
//            forma_pago = forma_pago_PAGOPARCIAL;//"EFECTIVO";
        }
        hab_venta_combinado = false;
        boton_guardar_venta_alquiler();
    }

    void boton_venta_tarjeta() {
        sumar_item_venta();
        monto_alquilado_credito = 0;
        monto_alquilado_efectivo = 0;
        monto_alquilado_transferencia = 0;
        forma_pago = forma_pago_TARJETA;//"TARJETA";
        if (jRcond_pagoparcial.isSelected()) {
            if (txtmonto_pago_parcial.getText().trim().length() > 0) {
                double pago_parcial = Double.parseDouble(txtmonto_pago_parcial.getText());
                monto_alquilado_tarjeta = pago_parcial;
                monto_alquilado_credito = (monto_total - pago_parcial);
            }
//            forma_pago = forma_pago_PAGOPARCIAL;//"EFECTIVO";
        }
        hab_venta_combinado = false;
        boton_guardar_venta_alquiler();
    }

    void boton_venta_transferencia() {
        sumar_item_venta();
        monto_alquilado_credito = 0;
        monto_alquilado_efectivo = 0;
        monto_alquilado_tarjeta = 0;
        forma_pago = forma_pago_TRANSFERENCIA;//"TRANSFERENCIA";
        if (jRcond_pagoparcial.isSelected()) {
            if (txtmonto_pago_parcial.getText().trim().length() > 0) {
                double pago_parcial = Double.parseDouble(txtmonto_pago_parcial.getText());
                monto_alquilado_transferencia = pago_parcial;
                monto_alquilado_credito = (monto_total - pago_parcial);
            }
//            forma_pago = forma_pago_PAGOPARCIAL;//"EFECTIVO";
        }
        hab_venta_combinado = false;
        boton_guardar_venta_alquiler();
    }

    void boton_venta_combinado() {
        monto_alquilado_credito = 0;
        monto_alquilado_tarjeta = 0;
        forma_pago = forma_pago_EFECTIVO;//"EFECTIVO";
        hab_venta_combinado = true;
//        boton_guardar_venta_alquiler();
    }

    void calcular_subtotal_itemventa() {
        if ((txtprecio_venta.getText().trim().length() > 0) && (txtcantidad_cobrado.getText().trim().length() > 0) && (txtcantidad_total.getText().trim().length() > 0)) {
            int precio_venta = Integer.parseInt(txtprecio_venta.getText());
            int Icant_cobrado = 0;
            int Icant_total = 0;
            if (txtcantidad_cobrado.getText().trim().length() == 0) {
                Icant_cobrado = 1;
            } else {
                Icant_cobrado = Integer.parseInt(txtcantidad_cobrado.getText());
                Icant_total = Integer.parseInt(txtcantidad_total.getText());
            }
            int subtotal = precio_venta * Icant_cobrado;
            int Icant_reserva = Icant_total - Icant_cobrado;
            if (Icant_reserva >= 0) {
                txtcantidad_reserva.setText(String.valueOf(Icant_reserva));
            } else {
                txtcantidad_total.setText(String.valueOf(Icant_cobrado));
                txtcantidad_reserva.setText(String.valueOf(0));
            }
            txtsubtotal.setText(String.valueOf(subtotal));
//            txtcantidad_reserva.setText(String.valueOf(Icant_reserva));
        }
    }

    private boolean getBoolean_validar_hora(JTextField txttexto) {
        if (txttexto.getText().trim().length() > 0) {
            int hora = Integer.parseInt(txttexto.getText());
            if (hora >= 0 && hora <= 23) {
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "FORMATO DE HORA INCORRECTO");
                txttexto.setText(hora_estandar);
                txttexto.requestFocus();
                return false;
            }
        } else {
            txttexto.setText(hora_estandar);
            txttexto.requestFocus();
            return false;
        }
    }

    private boolean getBoolean_validar_minuto(JTextField txttexto) {
        if (txttexto.getText().trim().length() > 0) {
            int minuto = Integer.parseInt(txttexto.getText());
            if (minuto >= 0 && minuto <= 59) {
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "FORMATO DE MINUTO INCORRECTO");
                txttexto.setText("00");
                txttexto.requestFocus();
                return false;
            }
        } else {
            txttexto.setText("00");
            txttexto.requestFocus();
            return false;
        }
    }

    void boton_guardar_venta_alquiler() {
        if (validar_guardar_venta()) {
            boolean escredito = jRcond_credito.isSelected();
            boolean espagoparcial = jRcond_pagoparcial.isSelected();
            cargar_dato_alquiler();
            cargar_dato_caja_alquilado();
            cargar_credito_cliente(escredito, espagoparcial);
            if (hab_venta_combinado) {
                JDpago_combinado combi = new JDpago_combinado(null, true);
                combi.setVisible(true);
            }
            if (vBO.getBoolean_insertar_venta_alquiler1(ven_alq, cdalq, cclie, clie, escredito, espagoparcial, tblitem_producto)) {
                if (hab_venta_combinado) {
                    if (jCimprimir_ticket.isSelected()) {
                        posv.boton_imprimir_pos_VENTA(connLocal, idventa_alquiler_ultimo);
                    } else {
                        JOptionPane.showMessageDialog(null, "VENTA GUARDADO\nSIN TICKET");
                    }
                } else {
                    if (jCvuelto.isSelected()) {
                        FrmVuelto vuel = new FrmVuelto(null, true);
                        vuel.setVisible(true);
                    } else {
                        if (jCimprimir_ticket.isSelected()) {
                            posv.boton_imprimir_pos_VENTA(connLocal, idventa_alquiler_ultimo);
                        } else {
                            JOptionPane.showMessageDialog(null, "VENTA GUARDADO\nSIN TICKET");
                        }
                    }
                }
                if (ven_alq.isConfirmado_carga_reserva()) {
                    int idventa_alquiler = ven_alq.getC1idventa_alquiler_global();
                    ven_alq.setC18estado(estado_FINALIZAR);
                    ven_alq.setC1idventa_alquiler(idventa_alquiler);
                    cdalq.setC5estado(estado_FINALIZAR);
                    cdalq.setC20fk_idventa_alquiler(idventa_alquiler);
                    if (vBO.getBoolean_update_venta_alquiler_Finalizar(ven_alq, cdalq)) {
//                        vdao.actualizar_tabla_venta_alquiler(connLocal, tblventa);
                    }
                    ven_alq.setConfirmado_carga_reserva(false);
                    ven_alq.setC1idventa_alquiler_global(0);
                }
                reestableser_venta();
                limpiar_buscardor_cliente();
            }
        }
    }

    private void boton_anular_venta_alquiler() {
        if (!evejt.getBoolean_validar_select(tblventa)) {
            int idventa_alquiler = evejt.getInt_select_id(tblventa);
            int fk_idcliente = evejt.getInt_select(tblventa, 3);
            ven_alq.setC18estado(estado_ANULADO);
            ven_alq.setC1idventa_alquiler(idventa_alquiler);
            cclie.setC4estado(estado_ANULADO);
            cclie.setC11fk_idventa_alquiler(idventa_alquiler);
            clie.setC1idcliente(fk_idcliente);
            cdalq.setC5estado(estado_ANULADO);
            cdalq.setC20fk_idventa_alquiler(idventa_alquiler);
            if (vBO.getBoolean_update_venta_alquiler_anular(ven_alq, cdalq, cclie, clie)) {
                vdao.actualizar_tabla_venta_alquiler(connLocal, tblventa, "");
            }
        }
    }

    private void boton_venta_alquiler_alquilado() {
        if (!evejt.getBoolean_validar_select(tblventa)) {
            int idventa_alquiler = evejt.getInt_select_id(tblventa);
            ven_alq.setC18estado(estado_ALQUILADO);
            ven_alq.setC1idventa_alquiler(idventa_alquiler);
            cdalq.setC5estado(estado_ALQUILADO);
            cdalq.setC20fk_idventa_alquiler(idventa_alquiler);
            String lista_producto = ivdao.getString_cargar_item_venta_alquiler_cantidad_total(connLocal, idventa_alquiler);
            if (vBO.getBoolean_update_venta_alquiler_alquilado(lista_producto, ven_alq, cdalq)) {
                vdao.actualizar_tabla_venta_alquiler(connLocal, tblventa, "");
            }
        }
    }

    private void boton_venta_alquiler_devolucion() {
        if (!evejt.getBoolean_validar_select(tblventa)) {
            int idventa_alquiler = evejt.getInt_select_id(tblventa);
            String fecha_retirado_previsto = evejt.getString_select(tblventa, 1) + ":00.00";
            String fecha_devolusion_previsto = evefec.getString_formato_fecha_hora() + ":00.00";
            if (evefec.getTimestamp_fecha_cargado(fecha_retirado_previsto).equals(evefec.getTimestamp_fecha_cargado(fecha_devolusion_previsto))) {
                JOptionPane.showMessageDialog(jPanel_fecha, "LA FECHA DE ALQUILADO Y DEVOLUSION NO PUEDE SER IGUAL ", "ERROR DE FECHA", JOptionPane.ERROR_MESSAGE);
            } else {
                ven_alq.setC18estado(estado_DEVOLUCION);
                ven_alq.setC1idventa_alquiler(idventa_alquiler);
                cdalq.setC5estado(estado_DEVOLUCION);
                cdalq.setC20fk_idventa_alquiler(idventa_alquiler);
                String lista_producto = ivdao.getString_cargar_item_venta_alquiler_cantidad_total(connLocal, idventa_alquiler);
                if (vBO.getBoolean_update_venta_alquiler_Devolucion(lista_producto, ven_alq, cdalq)) {
                    vdao.actualizar_tabla_venta_alquiler(connLocal, tblventa, "");
                }
            }
        }
    }

    private void boton_venta_alquiler_imprimir_ticket() {
        if (!evejt.getBoolean_validar_select(tblventa)) {
            int idventa_alquiler = evejt.getInt_select_id(tblventa);
            posv.boton_imprimir_pos_VENTA(connLocal, idventa_alquiler);
        }
    }

    //
    private void boton_pagar_reservado() {
        if (!evejt.getBoolean_validar_select(tblventa)) {
            color_formulario(clacolor.getColor_shopp());
            evejt.mostrar_JTabbedPane(jTabbedPane_VENTA, 0);
            int idventa_alquiler = evejt.getInt_select_id(tblventa);
            ven_alq.setC1idventa_alquiler_global(idventa_alquiler);
            DialogCobrarReservado frm = new DialogCobrarReservado(null, true);
            frm.setVisible(true);
            if (ven_alq.isConfirmado_carga_reserva()) {
                vdao.cargar_venta_alquiler(connLocal, ven_alq, idventa_alquiler);
                estado_venta_alquiler = estado_USO_RESERVA;
                seleccionar_cargar_cliente(4);
                txtfec_retirado_previsto.setText(ven_alq.getC22fecha_retirado());
                txthora_retirado_previsto.setText(ven_alq.getC23hora_retirado());
                txtminuto_retirado_previsto.setText(ven_alq.getC24min_retirado());
                txtfec_devolusion_previsto.setText(ven_alq.getC25fecha_devolusion());
                txthora_devolusion_previsto.setText(ven_alq.getC26hora_devolusion());
                txtminuto_devolusion_previsto.setText(ven_alq.getC27min_devolusion());
                txtdireccion_alquiler.setText(ven_alq.getC16direccion_alquiler());
                txtmonto_delivery.setText("0");
                String observacion = txtobservacion.getText();
                if (observacion.equals(observacion_inicio)) {
                    txtobservacion.setText("Alq Nro: " + idventa_alquiler);
                } else {
                    txtobservacion.setText(ven_alq.getC17observacion() + " /Alquiler Nro: " + idventa_alquiler);
                }
                cmbentregador.setSelectedIndex(1);
                sumar_item_venta();
            } else {
                reestableser_venta();
            }
        }
    }

    private void seleccionar_venta_alquiler() {
        if (!evejt.getBoolean_validar_select(tblventa)) {
            int idventa = evejt.getInt_select_id(tblventa);
            String estado = evejt.getString_select(tblventa, 10);
            String monto_reserva = evejt.getString_select(tblventa, 7);
            if (estado.equals(estado_EMITIDO)) {
                btnestado_anulado.setEnabled(true);
                btnestado_alquilado.setEnabled(true);
                btnestado_devolucion.setEnabled(false);
                btnestado_cobrar_reservado.setEnabled(false);
            }
            if (estado.equals(estado_ANULADO)) {
                btnestado_anulado.setEnabled(false);
                btnestado_alquilado.setEnabled(false);
                btnestado_devolucion.setEnabled(false);
                btnestado_cobrar_reservado.setEnabled(false);
            }
            if (estado.equals(estado_ALQUILADO)) {
                btnestado_anulado.setEnabled(false);
                btnestado_alquilado.setEnabled(false);
                btnestado_devolucion.setEnabled(true);
                btnestado_cobrar_reservado.setEnabled(false);
            }
            if (estado.equals(estado_USO_RESERVA)) {
                btnestado_anulado.setEnabled(false);
                btnestado_alquilado.setEnabled(false);
                btnestado_devolucion.setEnabled(false);
                btnestado_cobrar_reservado.setEnabled(false);
            }
            if (estado.equals(estado_FINALIZAR)) {
                btnestado_anulado.setEnabled(false);
                btnestado_alquilado.setEnabled(false);
                btnestado_devolucion.setEnabled(false);
                btnestado_cobrar_reservado.setEnabled(false);
            }
            if (estado.equals(estado_DEVOLUCION)) {
                btnestado_anulado.setEnabled(false);
                btnestado_alquilado.setEnabled(false);
                btnestado_devolucion.setEnabled(false);
                if (monto_reserva.trim().length() > 1) {
                    btnestado_cobrar_reservado.setEnabled(true);
                } else {
                    btnestado_cobrar_reservado.setEnabled(false);
                }
            }
            ivdao.actualizar_tabla_item_venta_alquiler_cant_reser(connLocal, tblitem_venta_filtro, idventa);
        }

    }

    void ocultar_pago_parcial(boolean visible) {
        lblpago_parcial.setVisible(visible);
        txtmonto_pago_parcial.setVisible(visible);
        lblvence_credito.setVisible(visible);
        txtfec_vence_credito.setVisible(visible);
    }

    //######################## MESA ##########################
    public FrmVenta_alquiler() {
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
        gru_tipocli = new javax.swing.ButtonGroup();
        gru_condi = new javax.swing.ButtonGroup();
        jTabbedPane_VENTA = new javax.swing.JTabbedPane();
        panel_base_1 = new javax.swing.JPanel();
        panel_referencia_unidad = new javax.swing.JPanel();
        jTab_producto_ingrediente = new javax.swing.JTabbedPane();
        panel_insertar_pri_producto = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtbuscar_producto = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblproducto = new javax.swing.JTable();
        btnagregar_cantidad = new javax.swing.JButton();
        jLabel29 = new javax.swing.JLabel();
        txtcod_barra = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        txtsubtotal = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        txtprecio_venta = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        txtstock = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        txtcantidad_total = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtcantidad_cobrado = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtcantidad_reserva = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtobservacion = new javax.swing.JTextField();
        panel_insertar_pri_item = new javax.swing.JPanel();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jList_cliente = new javax.swing.JList<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblitem_producto = new javax.swing.JTable();
        btneliminar_item = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jFtotal_pagado = new javax.swing.JFormattedTextField();
        btnconfirmar_venta_efectivo = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        txtbucarCliente_ruc = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtbucarCliente_nombre = new javax.swing.JTextField();
        txtbucarCliente_direccion = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtbucarCliente_telefono = new javax.swing.JTextField();
        btnlimpiar_cliente = new javax.swing.JButton();
        btnnuevo_cliente = new javax.swing.JButton();
        btnbuscar_cliente = new javax.swing.JButton();
        lblidcliente = new javax.swing.JLabel();
        btnconfirmar_venta_tarjeta = new javax.swing.JButton();
        btnconfirmar_venta_combinar = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jFtotal_reservado = new javax.swing.JFormattedTextField();
        jLabel8 = new javax.swing.JLabel();
        jFsaldo_credito = new javax.swing.JFormattedTextField();
        jLabel24 = new javax.swing.JLabel();
        txtdireccion_alquiler = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        cmbentregador = new javax.swing.JComboBox<>();
        jLabel33 = new javax.swing.JLabel();
        txtmonto_delivery = new javax.swing.JTextField();
        btnconfirmar_venta_transferencia = new javax.swing.JButton();
        lblpago_parcial = new javax.swing.JLabel();
        txtmonto_pago_parcial = new javax.swing.JTextField();
        lblvence_credito = new javax.swing.JLabel();
        txtfec_vence_credito = new javax.swing.JTextField();
        panel_referencia_marca = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        txtidventa = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        panel_referencia_categoria = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jRcond_contado = new javax.swing.JRadioButton();
        jRcond_credito = new javax.swing.JRadioButton();
        jRcond_pagoparcial = new javax.swing.JRadioButton();
        jPanel_fecha = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        txtfec_devolusion_previsto = new javax.swing.JTextField();
        txtfec_retirado_previsto = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        txthora_devolusion_previsto = new javax.swing.JTextField();
        txthora_retirado_previsto = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        txtminuto_devolusion_previsto = new javax.swing.JTextField();
        txtminuto_retirado_previsto = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jCimprimir_ticket = new javax.swing.JCheckBox();
        jCvuelto = new javax.swing.JCheckBox();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jCver_promocion = new javax.swing.JCheckBox();
        panel_referencia_venta = new javax.swing.JPanel();
        panel_tabla_venta = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblventa = new javax.swing.JTable();
        btnestado_anulado = new javax.swing.JButton();
        btnfacturar = new javax.swing.JButton();
        btnimprimirNota = new javax.swing.JButton();
        panel_tabla_item = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblitem_venta_filtro = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        txtbuscar_idventa = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtbuscar_fecha = new javax.swing.JTextField();
        lblcantidad_filtro = new javax.swing.JLabel();
        btnestado_alquilado = new javax.swing.JButton();
        btnestado_devolucion = new javax.swing.JButton();
        btnestado_cobrar_reservado = new javax.swing.JButton();
        panel_estado = new javax.swing.JPanel();
        jCestado_emitido = new javax.swing.JCheckBox();
        jCestado_finalizado = new javax.swing.JCheckBox();
        jCestado_anulado = new javax.swing.JCheckBox();
        jCestado_devolucion = new javax.swing.JCheckBox();
        jCestado_usoreserva = new javax.swing.JCheckBox();
        jCestado_alquilado = new javax.swing.JCheckBox();
        btnalquiler_todos = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        panel_tabla_busca_cli = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblbuscar_cliente = new javax.swing.JTable();
        txtbuscador_cliente_nombre = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        txtbuscador_cliente_telefono = new javax.swing.JTextField();
        txtbuscador_cliente_ruc = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jCfuncionario = new javax.swing.JCheckBox();
        btnnuevoCliente = new javax.swing.JButton();
        btnseleccionarCerrar = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
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

        panel_referencia_unidad.setBackground(new java.awt.Color(102, 153, 255));
        panel_referencia_unidad.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panel_referencia_unidad.setLayout(new java.awt.GridLayout(1, 0));

        panel_insertar_pri_producto.setBackground(new java.awt.Color(153, 204, 255));
        panel_insertar_pri_producto.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("BUSCAR PRODUCTO:");

        txtbuscar_producto.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtbuscar_producto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscar_productoKeyReleased(evt);
            }
        });

        tblproducto.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
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
        tblproducto.setRowHeight(25);
        tblproducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblproductoMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblproducto);

        btnagregar_cantidad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Cantidad/flecha_derecha.png"))); // NOI18N
        btnagregar_cantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnagregar_cantidadActionPerformed(evt);
            }
        });

        jLabel29.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel29.setText("COD.BARRA:");

        txtcod_barra.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtcod_barra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcod_barraKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtcod_barraKeyReleased(evt);
            }
        });

        jLabel30.setText("SUBTOTAL:");

        txtsubtotal.setEditable(false);
        txtsubtotal.setBackground(new java.awt.Color(204, 204, 255));
        txtsubtotal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtsubtotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel31.setText("PRECIO VENTA:");

        txtprecio_venta.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtprecio_venta.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtprecio_venta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtprecio_ventaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtprecio_ventaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtprecio_ventaKeyTyped(evt);
            }
        });

        jLabel32.setText("STOCK:");

        txtstock.setEditable(false);
        txtstock.setBackground(new java.awt.Color(204, 204, 255));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("CANTIDAD"));

        txtcantidad_total.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtcantidad_total.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtcantidad_total.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcantidad_totalActionPerformed(evt);
            }
        });
        txtcantidad_total.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcantidad_totalKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtcantidad_totalKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtcantidad_totalKeyTyped(evt);
            }
        });

        jLabel13.setText("TOTAL:");

        txtcantidad_cobrado.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtcantidad_cobrado.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtcantidad_cobrado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                txtcantidad_cobradoMouseReleased(evt);
            }
        });
        txtcantidad_cobrado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcantidad_cobradoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtcantidad_cobradoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtcantidad_cobradoKeyTyped(evt);
            }
        });

        jLabel14.setText("COBRADO:");

        txtcantidad_reserva.setEditable(false);
        txtcantidad_reserva.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtcantidad_reserva.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtcantidad_reserva.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcantidad_reservaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtcantidad_reservaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtcantidad_reservaKeyTyped(evt);
            }
        });

        jLabel15.setText("RESERVA:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtcantidad_cobrado, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                    .addComponent(jLabel13)
                    .addComponent(txtcantidad_total)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addComponent(txtcantidad_reserva, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
            .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel13)
                .addGap(2, 2, 2)
                .addComponent(txtcantidad_total, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtcantidad_cobrado, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtcantidad_reserva, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panel_insertar_pri_productoLayout = new javax.swing.GroupLayout(panel_insertar_pri_producto);
        panel_insertar_pri_producto.setLayout(panel_insertar_pri_productoLayout);
        panel_insertar_pri_productoLayout.setHorizontalGroup(
            panel_insertar_pri_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertar_pri_productoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_insertar_pri_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_insertar_pri_productoLayout.createSequentialGroup()
                        .addGroup(panel_insertar_pri_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_insertar_pri_productoLayout.createSequentialGroup()
                                .addComponent(jLabel29)
                                .addGap(71, 71, 71)
                                .addComponent(jLabel1))
                            .addGroup(panel_insertar_pri_productoLayout.createSequentialGroup()
                                .addComponent(txtcod_barra, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtbuscar_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_insertar_pri_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtstock, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_insertar_pri_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
                            .addComponent(txtprecio_venta))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_insertar_pri_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_insertar_pri_productoLayout.createSequentialGroup()
                                .addComponent(jLabel30)
                                .addGap(68, 68, 68))
                            .addComponent(txtsubtotal, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)))
                    .addGroup(panel_insertar_pri_productoLayout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_insertar_pri_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnagregar_cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))))
                .addContainerGap())
        );
        panel_insertar_pri_productoLayout.setVerticalGroup(
            panel_insertar_pri_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertar_pri_productoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_insertar_pri_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel29)
                    .addComponent(jLabel1)
                    .addComponent(jLabel32)
                    .addComponent(jLabel31)
                    .addComponent(jLabel30))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_pri_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtprecio_venta)
                    .addComponent(txtbuscar_producto)
                    .addComponent(txtcod_barra, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtstock)
                    .addComponent(txtsubtotal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_pri_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panel_insertar_pri_productoLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnagregar_cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(125, 125, 125))
        );

        jTab_producto_ingrediente.addTab("PRODUCTOS", panel_insertar_pri_producto);

        jLabel2.setText("OBS:");

        txtobservacion.setText("ninguna");

        panel_insertar_pri_item.setBackground(new java.awt.Color(153, 204, 255));
        panel_insertar_pri_item.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLayeredPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLayeredPane1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        jLayeredPane1.add(jList_cliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 460, 140));

        tblitem_producto.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
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
        tblitem_producto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblitem_productoMouseReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tblitem_producto);

        jLayeredPane1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 480, 210));

        btneliminar_item.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/venta/eliminar.png"))); // NOI18N
        btneliminar_item.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneliminar_itemActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("TOTAL PAGADO:");

        jFtotal_pagado.setEditable(false);
        jFtotal_pagado.setBackground(new java.awt.Color(204, 204, 255));
        jFtotal_pagado.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFtotal_pagado.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_pagado.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        btnconfirmar_venta_efectivo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnconfirmar_venta_efectivo.setText("CONTADO");
        btnconfirmar_venta_efectivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnconfirmar_venta_efectivoActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(255, 102, 0));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton1.setText("CANCELAR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        txtbucarCliente_ruc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtbucarCliente_rucKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbucarCliente_rucKeyReleased(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("RUC:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setText("DIREC:");

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

        txtbucarCliente_direccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbucarCliente_direccionKeyReleased(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("TEL.:");

        txtbucarCliente_telefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtbucarCliente_telefonoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbucarCliente_telefonoKeyReleased(evt);
            }
        });

        btnlimpiar_cliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/venta/escoba.png"))); // NOI18N
        btnlimpiar_cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnlimpiar_clienteActionPerformed(evt);
            }
        });

        btnnuevo_cliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/mini_nuevo.png"))); // NOI18N
        btnnuevo_cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevo_clienteActionPerformed(evt);
            }
        });

        btnbuscar_cliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/mini_lupa.png"))); // NOI18N
        btnbuscar_cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbuscar_clienteActionPerformed(evt);
            }
        });

        lblidcliente.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblidcliente.setText("id");

        btnconfirmar_venta_tarjeta.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnconfirmar_venta_tarjeta.setText("TARJETA");
        btnconfirmar_venta_tarjeta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnconfirmar_venta_tarjetaActionPerformed(evt);
            }
        });

        btnconfirmar_venta_combinar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnconfirmar_venta_combinar.setText("COMBINAR");
        btnconfirmar_venta_combinar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnconfirmar_venta_combinarActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel16.setText("TOTAL RESERVADO:");

        jFtotal_reservado.setEditable(false);
        jFtotal_reservado.setBackground(new java.awt.Color(204, 204, 255));
        jFtotal_reservado.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFtotal_reservado.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_reservado.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("SALDO:");

        jFsaldo_credito.setEditable(false);
        jFsaldo_credito.setBackground(new java.awt.Color(204, 204, 255));
        jFsaldo_credito.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFsaldo_credito.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFsaldo_credito.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel24.setText("DIREC ENTREGA:");

        txtdireccion_alquiler.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtdireccion_alquilerKeyReleased(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(204, 0, 0));
        jLabel25.setText("ENTREGADOR:");

        cmbentregador.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbentregador.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbentregadorItemStateChanged(evt);
            }
        });
        cmbentregador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbentregadorActionPerformed(evt);
            }
        });

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(204, 0, 0));
        jLabel33.setText("MONTO:");

        txtmonto_delivery.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtmonto_delivery.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtmonto_delivery.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtmonto_deliveryKeyTyped(evt);
            }
        });

        btnconfirmar_venta_transferencia.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnconfirmar_venta_transferencia.setText("TRANSFERENCIA");
        btnconfirmar_venta_transferencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnconfirmar_venta_transferenciaActionPerformed(evt);
            }
        });

        lblpago_parcial.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblpago_parcial.setText("P.PARCIAL:");

        txtmonto_pago_parcial.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtmonto_pago_parcial.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        lblvence_credito.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblvence_credito.setText("VENCE:");

        txtfec_vence_credito.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtfec_vence_credito.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtfec_vence_creditoKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout panel_insertar_pri_itemLayout = new javax.swing.GroupLayout(panel_insertar_pri_item);
        panel_insertar_pri_item.setLayout(panel_insertar_pri_itemLayout);
        panel_insertar_pri_itemLayout.setHorizontalGroup(
            panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertar_pri_itemLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_insertar_pri_itemLayout.createSequentialGroup()
                        .addComponent(btnnuevo_cliente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnbuscar_cliente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnlimpiar_cliente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jFsaldo_credito, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panel_insertar_pri_itemLayout.createSequentialGroup()
                        .addGroup(panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel_insertar_pri_itemLayout.createSequentialGroup()
                                .addComponent(jLabel24)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtdireccion_alquiler))
                            .addGroup(panel_insertar_pri_itemLayout.createSequentialGroup()
                                .addGroup(panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(panel_insertar_pri_itemLayout.createSequentialGroup()
                                        .addComponent(txtbucarCliente_ruc, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtbucarCliente_telefono, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblidcliente, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txtbucarCliente_nombre, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                                    .addComponent(txtbucarCliente_direccion))))
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addComponent(jLayeredPane1)
            .addGroup(panel_insertar_pri_itemLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel_insertar_pri_itemLayout.createSequentialGroup()
                        .addComponent(btnconfirmar_venta_efectivo)
                        .addGap(2, 2, 2)
                        .addComponent(btnconfirmar_venta_tarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnconfirmar_venta_transferencia)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnconfirmar_venta_combinar))
                    .addGroup(panel_insertar_pri_itemLayout.createSequentialGroup()
                        .addGroup(panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jFtotal_pagado, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_insertar_pri_itemLayout.createSequentialGroup()
                                .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(30, 30, 30))
                            .addComponent(jFtotal_reservado))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btneliminar_item, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)))
                .addGap(3, 3, 3))
            .addGroup(panel_insertar_pri_itemLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel25)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbentregador, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel33)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtmonto_delivery)
                .addContainerGap())
            .addGroup(panel_insertar_pri_itemLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblpago_parcial)
                .addGap(18, 18, 18)
                .addComponent(txtmonto_pago_parcial, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblvence_credito)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfec_vence_credito)
                .addGap(45, 45, 45))
        );
        panel_insertar_pri_itemLayout.setVerticalGroup(
            panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertar_pri_itemLayout.createSequentialGroup()
                .addGroup(panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel3)
                    .addComponent(txtbucarCliente_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel6)
                    .addComponent(txtbucarCliente_direccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtbucarCliente_telefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtbucarCliente_ruc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(lblidcliente))
                .addGap(5, 5, 5)
                .addGroup(panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel24)
                    .addComponent(txtdireccion_alquiler, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnnuevo_cliente, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnbuscar_cliente, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnlimpiar_cliente, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jFsaldo_credito, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblvence_credito)
                        .addComponent(txtfec_vence_credito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtmonto_pago_parcial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblpago_parcial)))
                .addGap(5, 5, 5)
                .addGroup(panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(cmbentregador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25)
                    .addComponent(jLabel33)
                    .addComponent(txtmonto_delivery, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel_insertar_pri_itemLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFtotal_pagado, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jFtotal_reservado, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panel_insertar_pri_itemLayout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addGap(41, 41, 41))
                    .addComponent(btneliminar_item, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnconfirmar_venta_combinar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnconfirmar_venta_efectivo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                    .addComponent(btnconfirmar_venta_tarjeta, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnconfirmar_venta_transferencia, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        panel_referencia_marca.setBackground(new java.awt.Color(102, 153, 255));
        panel_referencia_marca.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panel_referencia_marca.setLayout(new java.awt.GridLayout(1, 0));

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel12.setText("ALQUILER:");

        txtidventa.setBackground(new java.awt.Color(0, 0, 255));
        txtidventa.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtidventa.setForeground(new java.awt.Color(255, 255, 0));
        txtidventa.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        panel_referencia_categoria.setBackground(new java.awt.Color(102, 153, 255));
        panel_referencia_categoria.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panel_referencia_categoria.setLayout(new java.awt.GridLayout(0, 1));
        jScrollPane3.setViewportView(panel_referencia_categoria);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("CONDICION"));

        gru_condi.add(jRcond_contado);
        jRcond_contado.setSelected(true);
        jRcond_contado.setText("CONTADO");
        jRcond_contado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRcond_contadoActionPerformed(evt);
            }
        });

        gru_condi.add(jRcond_credito);
        jRcond_credito.setText("CREDITO");
        jRcond_credito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRcond_creditoActionPerformed(evt);
            }
        });

        gru_condi.add(jRcond_pagoparcial);
        jRcond_pagoparcial.setText("P.PARCIAL");
        jRcond_pagoparcial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRcond_pagoparcialActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jRcond_contado)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRcond_credito)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRcond_pagoparcial))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jRcond_contado)
                .addComponent(jRcond_credito)
                .addComponent(jRcond_pagoparcial))
        );

        jPanel_fecha.setBorder(javax.swing.BorderFactory.createTitledBorder("FECHA"));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText("RETIRAR:");

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel20.setText("DEVOLUSION:");

        txtfec_devolusion_previsto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtfec_devolusion_previsto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtfec_devolusion_previstoKeyPressed(evt);
            }
        });

        txtfec_retirado_previsto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtfec_retirado_previsto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtfec_retirado_previstoKeyPressed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel17.setText("HORA:");

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel21.setText("HORA:");

        txthora_devolusion_previsto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txthora_devolusion_previsto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txthora_devolusion_previstoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txthora_devolusion_previstoKeyTyped(evt);
            }
        });

        txthora_retirado_previsto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txthora_retirado_previsto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txthora_retirado_previstoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txthora_retirado_previstoKeyTyped(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel18.setText(":");

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel22.setText(":");

        txtminuto_devolusion_previsto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtminuto_devolusion_previsto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtminuto_devolusion_previstoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtminuto_devolusion_previstoKeyTyped(evt);
            }
        });

        txtminuto_retirado_previsto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtminuto_retirado_previsto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtminuto_retirado_previstoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtminuto_retirado_previstoKeyTyped(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel19.setText("Hs");

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel23.setText("Hs");

        javax.swing.GroupLayout jPanel_fechaLayout = new javax.swing.GroupLayout(jPanel_fecha);
        jPanel_fecha.setLayout(jPanel_fechaLayout);
        jPanel_fechaLayout.setHorizontalGroup(
            jPanel_fechaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_fechaLayout.createSequentialGroup()
                .addGroup(jPanel_fechaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_fechaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtfec_retirado_previsto, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                    .addComponent(txtfec_devolusion_previsto))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_fechaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_fechaLayout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txthora_retirado_previsto, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtminuto_retirado_previsto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel19))
                    .addGroup(jPanel_fechaLayout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txthora_devolusion_previsto, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtminuto_devolusion_previsto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel23)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel_fechaLayout.setVerticalGroup(
            jPanel_fechaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_fechaLayout.createSequentialGroup()
                .addGroup(jPanel_fechaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtfec_retirado_previsto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(txthora_retirado_previsto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18)
                    .addComponent(txtminuto_retirado_previsto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_fechaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(txtfec_devolusion_previsto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21)
                    .addComponent(txthora_devolusion_previsto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22)
                    .addComponent(txtminuto_devolusion_previsto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23))
                .addGap(0, 9, Short.MAX_VALUE))
        );

        jCimprimir_ticket.setText("IMPRIMIR TICKET");

        jCvuelto.setText("VUELTO");

        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel34.setText("UNIDAD:");

        jLabel35.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel35.setText("MARCA:");

        jCver_promocion.setText("VER PRECIO PROMOCION");
        jCver_promocion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCver_promocionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_base_1Layout = new javax.swing.GroupLayout(panel_base_1);
        panel_base_1.setLayout(panel_base_1Layout);
        panel_base_1Layout.setHorizontalGroup(
            panel_base_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_base_1Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_base_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_base_1Layout.createSequentialGroup()
                        .addGroup(panel_base_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTab_producto_ingrediente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panel_base_1Layout.createSequentialGroup()
                                .addComponent(jPanel_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panel_base_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panel_base_1Layout.createSequentialGroup()
                                        .addComponent(jLabel12)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtidventa, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(panel_base_1Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtobservacion)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCver_promocion)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCimprimir_ticket)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCvuelto)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 25, Short.MAX_VALUE)
                        .addComponent(panel_insertar_pri_item, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_base_1Layout.createSequentialGroup()
                        .addGroup(panel_base_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel34)
                            .addComponent(jLabel35))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panel_base_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(panel_referencia_marca, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1052, Short.MAX_VALUE)
                            .addComponent(panel_referencia_unidad, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        panel_base_1Layout.setVerticalGroup(
            panel_base_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_base_1Layout.createSequentialGroup()
                .addGroup(panel_base_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane3)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel_base_1Layout.createSequentialGroup()
                        .addGroup(panel_base_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(panel_referencia_unidad, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel34))
                        .addGap(8, 8, 8)
                        .addGroup(panel_base_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(panel_referencia_marca, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel35))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_base_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panel_base_1Layout.createSequentialGroup()
                                .addGroup(panel_base_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jPanel_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(panel_base_1Layout.createSequentialGroup()
                                        .addGroup(panel_base_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel12)
                                            .addComponent(txtidventa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTab_producto_ingrediente, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panel_base_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtobservacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2)
                                    .addComponent(jCimprimir_ticket)
                                    .addComponent(jCvuelto)
                                    .addComponent(jCver_promocion)))
                            .addComponent(panel_insertar_pri_item, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane_VENTA.addTab("CREAR VENTA", panel_base_1);

        panel_tabla_venta.setBackground(new java.awt.Color(153, 153, 255));
        panel_tabla_venta.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tblventa.setModel(new javax.swing.table.DefaultTableModel(
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
        tblventa.setRowHeight(25);
        tblventa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblventaMouseReleased(evt);
            }
        });
        jScrollPane4.setViewportView(tblventa);

        javax.swing.GroupLayout panel_tabla_ventaLayout = new javax.swing.GroupLayout(panel_tabla_venta);
        panel_tabla_venta.setLayout(panel_tabla_ventaLayout);
        panel_tabla_ventaLayout.setHorizontalGroup(
            panel_tabla_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_tabla_ventaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4)
                .addContainerGap())
        );
        panel_tabla_ventaLayout.setVerticalGroup(
            panel_tabla_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_tabla_ventaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnestado_anulado.setBackground(new java.awt.Color(255, 51, 51));
        btnestado_anulado.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnestado_anulado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/venta/anular.png"))); // NOI18N
        btnestado_anulado.setText("ANULAR");
        btnestado_anulado.setToolTipText("");
        btnestado_anulado.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnestado_anulado.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnestado_anulado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnestado_anuladoActionPerformed(evt);
            }
        });

        btnfacturar.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnfacturar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/venta/ven_factura.png"))); // NOI18N
        btnfacturar.setText("FACTURAR ");
        btnfacturar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnfacturar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnfacturar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnfacturarActionPerformed(evt);
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

        panel_tabla_item.setBorder(javax.swing.BorderFactory.createTitledBorder("ITEM RESERVADO"));

        tblitem_venta_filtro.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane8.setViewportView(tblitem_venta_filtro);

        javax.swing.GroupLayout panel_tabla_itemLayout = new javax.swing.GroupLayout(panel_tabla_item);
        panel_tabla_item.setLayout(panel_tabla_itemLayout);
        panel_tabla_itemLayout.setHorizontalGroup(
            panel_tabla_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
        );
        panel_tabla_itemLayout.setVerticalGroup(
            panel_tabla_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
        );

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setText("IDVENTA:");

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

        lblcantidad_filtro.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblcantidad_filtro.setForeground(new java.awt.Color(0, 0, 204));
        lblcantidad_filtro.setText("jLabel12");

        btnestado_alquilado.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnestado_alquilado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/venta/flecha-salir.png"))); // NOI18N
        btnestado_alquilado.setText("ALQUILADO");
        btnestado_alquilado.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        btnestado_alquilado.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnestado_alquilado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnestado_alquiladoActionPerformed(evt);
            }
        });

        btnestado_devolucion.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnestado_devolucion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/venta/flecha-retornar.png"))); // NOI18N
        btnestado_devolucion.setText("DEVOLUCION");
        btnestado_devolucion.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        btnestado_devolucion.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnestado_devolucion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnestado_devolucionActionPerformed(evt);
            }
        });

        btnestado_cobrar_reservado.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnestado_cobrar_reservado.setText("COBRAR-RESERVADO");
        btnestado_cobrar_reservado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnestado_cobrar_reservadoActionPerformed(evt);
            }
        });

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
                    .addComponent(jCestado_devolucion)
                    .addComponent(jCestado_emitido)
                    .addComponent(jCestado_finalizado))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_estadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCestado_anulado)
                    .addComponent(jCestado_usoreserva)
                    .addComponent(jCestado_alquilado))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_estadoLayout.setVerticalGroup(
            panel_estadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_estadoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_estadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCestado_emitido)
                    .addComponent(jCestado_alquilado))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_estadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCestado_devolucion)
                    .addComponent(jCestado_usoreserva))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_estadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCestado_finalizado)
                    .addComponent(jCestado_anulado))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnalquiler_todos.setText("TODOS");
        btnalquiler_todos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnalquiler_todosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_referencia_ventaLayout = new javax.swing.GroupLayout(panel_referencia_venta);
        panel_referencia_venta.setLayout(panel_referencia_ventaLayout);
        panel_referencia_ventaLayout.setHorizontalGroup(
            panel_referencia_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_referencia_ventaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_referencia_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panel_tabla_venta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panel_referencia_ventaLayout.createSequentialGroup()
                        .addGroup(panel_referencia_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_referencia_ventaLayout.createSequentialGroup()
                                .addComponent(btnestado_anulado, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnimprimirNota, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnfacturar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panel_referencia_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel11))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panel_referencia_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtbuscar_fecha, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
                                    .addComponent(txtbuscar_idventa))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblcantidad_filtro, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panel_referencia_ventaLayout.createSequentialGroup()
                                .addComponent(panel_tabla_item, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panel_referencia_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(panel_estado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnalquiler_todos))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnestado_alquilado, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnestado_devolucion)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnestado_cobrar_reservado)
                        .addContainerGap(79, Short.MAX_VALUE))))
        );
        panel_referencia_ventaLayout.setVerticalGroup(
            panel_referencia_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_referencia_ventaLayout.createSequentialGroup()
                .addComponent(panel_tabla_venta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_referencia_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_referencia_ventaLayout.createSequentialGroup()
                        .addGroup(panel_referencia_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnestado_anulado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnimprimirNota, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)
                            .addComponent(btnfacturar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(panel_referencia_ventaLayout.createSequentialGroup()
                                .addGroup(panel_referencia_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel10)
                                    .addComponent(txtbuscar_idventa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblcantidad_filtro))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panel_referencia_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtbuscar_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11)))
                            .addGroup(panel_referencia_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnestado_alquilado, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnestado_devolucion, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_referencia_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panel_tabla_item, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panel_referencia_ventaLayout.createSequentialGroup()
                                .addComponent(panel_estado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnalquiler_todos))))
                    .addComponent(btnestado_cobrar_reservado, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane_VENTA.addTab("FILTRO VENTA", panel_referencia_venta);

        panel_tabla_busca_cli.setBackground(new java.awt.Color(51, 102, 255));
        panel_tabla_busca_cli.setBorder(javax.swing.BorderFactory.createTitledBorder("TABLA BUSCAR CLIENTE"));

        tblbuscar_cliente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        tblbuscar_cliente.setModel(new javax.swing.table.DefaultTableModel(
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
        tblbuscar_cliente.setRowHeight(30);
        tblbuscar_cliente.setRowMargin(5);
        tblbuscar_cliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblbuscar_clienteMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblbuscar_clienteMouseReleased(evt);
            }
        });
        tblbuscar_cliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblbuscar_clienteKeyPressed(evt);
            }
        });
        jScrollPane7.setViewportView(tblbuscar_cliente);

        txtbuscador_cliente_nombre.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtbuscador_cliente_nombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtbuscador_cliente_nombreKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscador_cliente_nombreKeyReleased(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 204));
        jLabel26.setText("Buscar Nombre:");

        jLabel27.setBackground(new java.awt.Color(255, 255, 204));
        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 204));
        jLabel27.setText("Buscar Telefono:");

        txtbuscador_cliente_telefono.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtbuscador_cliente_telefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtbuscador_cliente_telefonoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscador_cliente_telefonoKeyReleased(evt);
            }
        });

        txtbuscador_cliente_ruc.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtbuscador_cliente_ruc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtbuscador_cliente_rucKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscador_cliente_rucKeyReleased(evt);
            }
        });

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 204));
        jLabel28.setText("Buscar Ruc:");

        jCfuncionario.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jCfuncionario.setForeground(new java.awt.Color(255, 255, 255));
        jCfuncionario.setText("FUNCIONARIO");
        jCfuncionario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCfuncionarioActionPerformed(evt);
            }
        });

        btnnuevoCliente.setText("NUEVO CLIENTE");
        btnnuevoCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevoClienteActionPerformed(evt);
            }
        });

        btnseleccionarCerrar.setBackground(new java.awt.Color(255, 255, 153));
        btnseleccionarCerrar.setText("SELECCIONAR IR VENTA");
        btnseleccionarCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnseleccionarCerrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_tabla_busca_cliLayout = new javax.swing.GroupLayout(panel_tabla_busca_cli);
        panel_tabla_busca_cli.setLayout(panel_tabla_busca_cliLayout);
        panel_tabla_busca_cliLayout.setHorizontalGroup(
            panel_tabla_busca_cliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_tabla_busca_cliLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_tabla_busca_cliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7)
                    .addGroup(panel_tabla_busca_cliLayout.createSequentialGroup()
                        .addGroup(panel_tabla_busca_cliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel26)
                            .addComponent(txtbuscador_cliente_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_tabla_busca_cliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel27)
                            .addComponent(txtbuscador_cliente_telefono, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_tabla_busca_cliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel28)
                            .addGroup(panel_tabla_busca_cliLayout.createSequentialGroup()
                                .addComponent(txtbuscador_cliente_ruc, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnnuevoCliente)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_tabla_busca_cliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnseleccionarCerrar)
                            .addComponent(jCfuncionario))
                        .addContainerGap(395, Short.MAX_VALUE))))
        );
        panel_tabla_busca_cliLayout.setVerticalGroup(
            panel_tabla_busca_cliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_tabla_busca_cliLayout.createSequentialGroup()
                .addGroup(panel_tabla_busca_cliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel26)
                    .addComponent(jLabel27)
                    .addComponent(jLabel28)
                    .addComponent(jCfuncionario))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_tabla_busca_cliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtbuscador_cliente_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtbuscador_cliente_telefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtbuscador_cliente_ruc, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnnuevoCliente)
                    .addComponent(btnseleccionarCerrar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_tabla_busca_cli, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_tabla_busca_cli, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane_VENTA.addTab("BUSCAR CLIENTE", jPanel11);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane_VENTA)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane_VENTA, javax.swing.GroupLayout.DEFAULT_SIZE, 651, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        ancho_item_producto();
        ancho_tabla_producto();
        vdao.ancho_tabla_venta_alquiler(tblventa);
//        vdao.ancho_tabla_venta(tblventa);
    }//GEN-LAST:event_formInternalFrameOpened

    private void txtbuscar_productoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_productoKeyReleased
        // TODO add your handling code here:
        actualizar_tabla_producto(3);
    }//GEN-LAST:event_txtbuscar_productoKeyReleased

    private void txtbucarCliente_direccionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbucarCliente_direccionKeyReleased
        // TODO add your handling code here:

    }//GEN-LAST:event_txtbucarCliente_direccionKeyReleased

    private void txtbucarCliente_nombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbucarCliente_nombreKeyReleased
        // TODO add your handling code here:
        eveconn.buscar_cargar_Jlista(connLocal, txtbucarCliente_nombre, jList_cliente, "cliente", "nombre", clie.getCliente_mostrar(), 4);
    }//GEN-LAST:event_txtbucarCliente_nombreKeyReleased

    private void txtbucarCliente_telefonoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbucarCliente_telefonoKeyReleased
        // TODO add your handling code here:
        eveconn.buscar_cargar_Jlista(connLocal, txtbucarCliente_telefono, jList_cliente, "cliente", "telefono", clie.getCliente_mostrar(), 4);
    }//GEN-LAST:event_txtbucarCliente_telefonoKeyReleased

    private void txtbucarCliente_rucKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbucarCliente_rucKeyReleased
        // TODO add your handling code here:
        eveconn.buscar_cargar_Jlista(connLocal, txtbucarCliente_ruc, jList_cliente, "cliente", "ruc", clie.getCliente_mostrar(), 4);
    }//GEN-LAST:event_txtbucarCliente_rucKeyReleased

    private void txtbucarCliente_nombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbucarCliente_nombreKeyPressed
        // TODO add your handling code here:
        evejtf.seleccionar_lista(evt, jList_cliente);
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtbucarCliente_telefono.grabFocus();
        }
    }//GEN-LAST:event_txtbucarCliente_nombreKeyPressed

    private void txtbucarCliente_telefonoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbucarCliente_telefonoKeyPressed
        // TODO add your handling code here:
        evejtf.seleccionar_lista(evt, jList_cliente);
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtbucarCliente_ruc.grabFocus();
        }
    }//GEN-LAST:event_txtbucarCliente_telefonoKeyPressed

    private void txtbucarCliente_rucKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbucarCliente_rucKeyPressed
        // TODO add your handling code here:
        evejtf.seleccionar_lista(evt, jList_cliente);
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtbucarCliente_nombre.grabFocus();
        }
    }//GEN-LAST:event_txtbucarCliente_rucKeyPressed

    private void btneliminar_itemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneliminar_itemActionPerformed
        // TODO add your handling code here:

        boton_eliminar_item();
    }//GEN-LAST:event_btneliminar_itemActionPerformed

    private void tblitem_productoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblitem_productoMouseReleased
        // TODO add your handling code here:

    }//GEN-LAST:event_tblitem_productoMouseReleased

    private void btnlimpiar_clienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnlimpiar_clienteActionPerformed
        // TODO add your handling code here:
        limpiar_cliente();
        sumar_item_venta();
    }//GEN-LAST:event_btnlimpiar_clienteActionPerformed

    private void btnconfirmar_venta_efectivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnconfirmar_venta_efectivoActionPerformed
        // TODO add your handling code here:
        boton_venta_efectivo();
    }//GEN-LAST:event_btnconfirmar_venta_efectivoActionPerformed

    private void jList_clienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jList_clienteKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            seleccionar_cargar_cliente(1);
        }
    }//GEN-LAST:event_jList_clienteKeyPressed

    private void jList_clienteMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList_clienteMouseReleased
        // TODO add your handling code here:
        seleccionar_cargar_cliente(1);
    }//GEN-LAST:event_jList_clienteMouseReleased

    private void btnestado_anuladoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnestado_anuladoActionPerformed
        // TODO add your handling code here:
//        boton_estado_venta_anular();
        boton_anular_venta_alquiler();
    }//GEN-LAST:event_btnestado_anuladoActionPerformed

    private void btnfacturarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnfacturarActionPerformed
        // TODO add your handling code here:
        boton_factura();

    }//GEN-LAST:event_btnfacturarActionPerformed

    private void btnimprimirNotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnimprimirNotaActionPerformed
        // TODO add your handling code here:
//        boton_imprimirPos_venta();
        boton_venta_alquiler_imprimir_ticket();
    }//GEN-LAST:event_btnimprimirNotaActionPerformed

    private void txtbuscar_idventaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_idventaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            actualizar_venta(2);
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
            actualizar_venta(3);
        }
    }//GEN-LAST:event_txtbuscar_fechaKeyPressed

    private void btnnuevo_clienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevo_clienteActionPerformed
        // TODO add your handling code here:
//        evejt.mostrar_JTabbedPane(jTabbedPane_VENTA, 2);
    }//GEN-LAST:event_btnnuevo_clienteActionPerformed

    private void tblbuscar_clienteMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblbuscar_clienteMousePressed
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            seleccionar_cargar_cliente(3);
            limpiar_buscardor_cliente();
            evejt.mostrar_JTabbedPane(jTabbedPane_VENTA, 0);
        }
    }//GEN-LAST:event_tblbuscar_clienteMousePressed

    private void tblbuscar_clienteMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblbuscar_clienteMouseReleased
        // TODO add your handling code here:
        // anchotabla();
    }//GEN-LAST:event_tblbuscar_clienteMouseReleased

    private void tblbuscar_clienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblbuscar_clienteKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!evejt.getBoolean_validar_select(tblbuscar_cliente)) {
                seleccionar_cargar_cliente(3);
                limpiar_buscardor_cliente();
                evejt.mostrar_JTabbedPane(jTabbedPane_VENTA, 0);
            }
        }
    }//GEN-LAST:event_tblbuscar_clienteKeyPressed

    private void txtbuscador_cliente_nombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscador_cliente_nombreKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtbuscador_cliente_nombreKeyPressed

    private void txtbuscador_cliente_nombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscador_cliente_nombreKeyReleased
        // TODO add your handling code here:
        cdao.buscar_tabla_cliente_zona(connLocal, tblbuscar_cliente, txtbuscador_cliente_nombre, jCfuncionario, 1);
    }//GEN-LAST:event_txtbuscador_cliente_nombreKeyReleased

    private void txtbuscador_cliente_telefonoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscador_cliente_telefonoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtbuscador_cliente_telefonoKeyPressed

    private void txtbuscador_cliente_telefonoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscador_cliente_telefonoKeyReleased
        // TODO add your handling code here:
        cdao.buscar_tabla_cliente_zona(connLocal, tblbuscar_cliente, txtbuscador_cliente_telefono, jCfuncionario, 2);
    }//GEN-LAST:event_txtbuscador_cliente_telefonoKeyReleased

    private void txtbuscador_cliente_rucKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscador_cliente_rucKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtbuscador_cliente_rucKeyPressed

    private void txtbuscador_cliente_rucKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscador_cliente_rucKeyReleased
        // TODO add your handling code here:
        cdao.buscar_tabla_cliente_zona(connLocal, tblbuscar_cliente, txtbuscador_cliente_ruc, jCfuncionario, 3);
    }//GEN-LAST:event_txtbuscador_cliente_rucKeyReleased

    private void jCfuncionarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCfuncionarioActionPerformed
        // TODO add your handling code here:
        if (jCfuncionario.isSelected()) {
            panel_tabla_busca_cli.setBackground(Color.orange);
        } else {
            panel_tabla_busca_cli.setBackground(clacolor.getColor_tabla());//[51,102,255]
        }
        txtbuscador_cliente_nombre.grabFocus();
        cdao.buscar_tabla_cliente_zona(connLocal, tblbuscar_cliente, txtbuscador_cliente_nombre, jCfuncionario, 1);
    }//GEN-LAST:event_jCfuncionarioActionPerformed

    private void btnnuevoClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevoClienteActionPerformed
        // TODO add your handling code here:
        limpiar_buscardor_cliente();
        evejt.mostrar_JTabbedPane(jTabbedPane_VENTA, 2);
    }//GEN-LAST:event_btnnuevoClienteActionPerformed

    private void btnseleccionarCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnseleccionarCerrarActionPerformed
        // TODO add your handling code here:
        if (!evejt.getBoolean_validar_select(tblbuscar_cliente)) {
            seleccionar_cargar_cliente(3);
            limpiar_buscardor_cliente();
            evejt.mostrar_JTabbedPane(jTabbedPane_VENTA, 0);
        }
    }//GEN-LAST:event_btnseleccionarCerrarActionPerformed

    private void btnbuscar_clienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbuscar_clienteActionPerformed
        // TODO add your handling code here:
        boton_buscar_cliente_venta();
    }//GEN-LAST:event_btnbuscar_clienteActionPerformed

    private void btnagregar_cantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnagregar_cantidadActionPerformed
        // TODO add your handling code here:
        cargar_item_producto();
    }//GEN-LAST:event_btnagregar_cantidadActionPerformed

    private void tblventaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblventaMouseReleased
        // TODO add your handling code here:
        seleccionar_venta_alquiler();
//        seleccionar_tabla_venta();
    }//GEN-LAST:event_tblventaMouseReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        reestableser_venta();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        // TODO add your handling code here:
//        ven.setVenta_aux(false);
    }//GEN-LAST:event_formInternalFrameClosing

    private void txtcantidad_totalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcantidad_totalKeyReleased
        // TODO add your handling code here:
//        calcular_subtotal_itemventa();
//        calculo_cantidad(evt);
    }//GEN-LAST:event_txtcantidad_totalKeyReleased

    private void tblproductoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblproductoMouseReleased
        // TODO add your handling code here:
        seleccionar_producto();
    }//GEN-LAST:event_tblproductoMouseReleased

    private void txtcod_barraKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcod_barraKeyReleased
        // TODO add your handling code here:
//        actualizar_tabla_producto(5);
    }//GEN-LAST:event_txtcod_barraKeyReleased

    private void txtcod_barraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcod_barraKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            buscar_cod_barra_producto();
        }
        if (evt.getKeyCode() == KeyEvent.VK_F1) {
            boton_venta_efectivo();
        }
        if (evt.getKeyCode() == KeyEvent.VK_F2) {
            boton_venta_tarjeta();
        }
    }//GEN-LAST:event_txtcod_barraKeyPressed

    private void txtcantidad_totalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcantidad_totalKeyPressed
        // TODO add your handling code here:
//        calculo_cantidad(evt);
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtcantidad_cobrado.requestFocus();
        }
    }//GEN-LAST:event_txtcantidad_totalKeyPressed

    private void btnconfirmar_venta_tarjetaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnconfirmar_venta_tarjetaActionPerformed
        // TODO add your handling code here:
        boton_venta_tarjeta();
    }//GEN-LAST:event_btnconfirmar_venta_tarjetaActionPerformed

    private void txtcantidad_totalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcantidad_totalKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txtcantidad_totalKeyTyped

    private void txtprecio_ventaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtprecio_ventaKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txtprecio_ventaKeyTyped

    private void txtprecio_ventaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtprecio_ventaKeyReleased
        // TODO add your handling code here:
        habilitar_editar_precio_venta = true;
        calcular_subtotal_itemventa();
    }//GEN-LAST:event_txtprecio_ventaKeyReleased

    private void txtprecio_ventaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtprecio_ventaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtcantidad_total.grabFocus();
        }
    }//GEN-LAST:event_txtprecio_ventaKeyPressed

    private void btnconfirmar_venta_combinarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnconfirmar_venta_combinarActionPerformed
        // TODO add your handling code here:
        boton_venta_combinado();
    }//GEN-LAST:event_btnconfirmar_venta_combinarActionPerformed

    private void txtcantidad_cobradoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcantidad_cobradoKeyPressed
        // TODO add your handling code here:
        calculo_cantidad(evt);
    }//GEN-LAST:event_txtcantidad_cobradoKeyPressed

    private void txtcantidad_cobradoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcantidad_cobradoKeyReleased
        // TODO add your handling code here:
        calcular_subtotal_itemventa();
    }//GEN-LAST:event_txtcantidad_cobradoKeyReleased

    private void txtcantidad_cobradoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcantidad_cobradoKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txtcantidad_cobradoKeyTyped

    private void txtcantidad_reservaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcantidad_reservaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcantidad_reservaKeyPressed

    private void txtcantidad_reservaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcantidad_reservaKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcantidad_reservaKeyReleased

    private void txtcantidad_reservaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcantidad_reservaKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txtcantidad_reservaKeyTyped

    private void txtcantidad_totalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcantidad_totalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcantidad_totalActionPerformed

    private void txtcantidad_cobradoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtcantidad_cobradoMouseReleased
        // TODO add your handling code here:
        calcular_subtotal_itemventa();
    }//GEN-LAST:event_txtcantidad_cobradoMouseReleased

    private void txtdireccion_alquilerKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtdireccion_alquilerKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtdireccion_alquilerKeyReleased

    private void txtfec_retirado_previstoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfec_retirado_previstoKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtfec_retirado_previsto.setText(evefec.getString_validar_fecha(txtfec_retirado_previsto.getText()));
            evejtf.saltar_campo_enter(evt, txtfec_retirado_previsto, txthora_retirado_previsto);
        }
    }//GEN-LAST:event_txtfec_retirado_previstoKeyPressed

    private void txthora_retirado_previstoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txthora_retirado_previstoKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (getBoolean_validar_hora(txthora_retirado_previsto)) {
                evejtf.saltar_campo_enter(evt, txthora_retirado_previsto, txtminuto_retirado_previsto);
            }
        }
    }//GEN-LAST:event_txthora_retirado_previstoKeyPressed

    private void txtminuto_retirado_previstoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtminuto_retirado_previstoKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (getBoolean_validar_minuto(txtminuto_retirado_previsto)) {
                evejtf.saltar_campo_enter(evt, txtminuto_retirado_previsto, txtfec_devolusion_previsto);
            }
        }
    }//GEN-LAST:event_txtminuto_retirado_previstoKeyPressed

    private void txtfec_devolusion_previstoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfec_devolusion_previstoKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtfec_devolusion_previsto.setText(evefec.getString_validar_fecha(txtfec_devolusion_previsto.getText()));
            evejtf.saltar_campo_enter(evt, txtfec_devolusion_previsto, txthora_devolusion_previsto);
        }
    }//GEN-LAST:event_txtfec_devolusion_previstoKeyPressed

    private void txthora_devolusion_previstoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txthora_devolusion_previstoKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (getBoolean_validar_hora(txthora_devolusion_previsto)) {
                evejtf.saltar_campo_enter(evt, txthora_devolusion_previsto, txtminuto_devolusion_previsto);
            }
        }
    }//GEN-LAST:event_txthora_devolusion_previstoKeyPressed

    private void txtminuto_devolusion_previstoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtminuto_devolusion_previstoKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (getBoolean_validar_minuto(txtminuto_devolusion_previsto)) {
                evejtf.saltar_campo_enter(evt, txtminuto_devolusion_previsto, txtfec_retirado_previsto);
            }
        }
    }//GEN-LAST:event_txtminuto_devolusion_previstoKeyPressed

    private void txtmonto_deliveryKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtmonto_deliveryKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txtmonto_deliveryKeyTyped

    private void txthora_retirado_previstoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txthora_retirado_previstoKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txthora_retirado_previstoKeyTyped

    private void txthora_devolusion_previstoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txthora_devolusion_previstoKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txthora_devolusion_previstoKeyTyped

    private void txtminuto_retirado_previstoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtminuto_retirado_previstoKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txtminuto_retirado_previstoKeyTyped

    private void txtminuto_devolusion_previstoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtminuto_devolusion_previstoKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txtminuto_devolusion_previstoKeyTyped

    private void cmbentregadorItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbentregadorItemStateChanged
        // TODO add your handling code here:

    }//GEN-LAST:event_cmbentregadorItemStateChanged

    private void cmbentregadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbentregadorActionPerformed
        // TODO add your handling code here:
        if (hab_carga_entregador) {
            fk_identregador = evecmb.getInt_seleccionar_COMBOBOX(connLocal, cmbentregador, "identregador", "nombre", "entregador");
        }
    }//GEN-LAST:event_cmbentregadorActionPerformed

    private void btnconfirmar_venta_transferenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnconfirmar_venta_transferenciaActionPerformed
        // TODO add your handling code here:
        boton_venta_transferencia();
    }//GEN-LAST:event_btnconfirmar_venta_transferenciaActionPerformed

    private void jRcond_contadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRcond_contadoActionPerformed
        // TODO add your handling code here:
        select_condicion();
    }//GEN-LAST:event_jRcond_contadoActionPerformed

    private void jRcond_creditoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRcond_creditoActionPerformed
        // TODO add your handling code here:
        select_condicion();
    }//GEN-LAST:event_jRcond_creditoActionPerformed

    private void jRcond_pagoparcialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRcond_pagoparcialActionPerformed
        // TODO add your handling code here:
        select_condicion();
    }//GEN-LAST:event_jRcond_pagoparcialActionPerformed

    private void btnestado_alquiladoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnestado_alquiladoActionPerformed
        // TODO add your handling code here:
        boton_venta_alquiler_alquilado();
    }//GEN-LAST:event_btnestado_alquiladoActionPerformed

    private void btnestado_devolucionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnestado_devolucionActionPerformed
        // TODO add your handling code here:
        boton_venta_alquiler_devolucion();
    }//GEN-LAST:event_btnestado_devolucionActionPerformed

    private void btnestado_cobrar_reservadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnestado_cobrar_reservadoActionPerformed
        // TODO add your handling code here:
        boton_pagar_reservado();
    }//GEN-LAST:event_btnestado_cobrar_reservadoActionPerformed

    private void jCestado_emitidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCestado_emitidoActionPerformed
        // TODO add your handling code here:
        actualizar_venta(1);
//        seleccionar_cargar_filtro();
    }//GEN-LAST:event_jCestado_emitidoActionPerformed

    private void jCestado_finalizadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCestado_finalizadoActionPerformed
        // TODO add your handling code here:
        actualizar_venta(1);
//        seleccionar_cargar_filtro();
    }//GEN-LAST:event_jCestado_finalizadoActionPerformed

    private void jCestado_anuladoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCestado_anuladoActionPerformed
        // TODO add your handling code here:
        actualizar_venta(1);
//        seleccionar_cargar_filtro();
    }//GEN-LAST:event_jCestado_anuladoActionPerformed

    private void jCestado_devolucionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCestado_devolucionActionPerformed
        // TODO add your handling code here:
        actualizar_venta(1);
//        seleccionar_cargar_filtro();
    }//GEN-LAST:event_jCestado_devolucionActionPerformed

    private void jCestado_usoreservaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCestado_usoreservaActionPerformed
        // TODO add your handling code here:
        actualizar_venta(1);
//        seleccionar_cargar_filtro();
    }//GEN-LAST:event_jCestado_usoreservaActionPerformed

    private void jCestado_alquiladoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCestado_alquiladoActionPerformed
        // TODO add your handling code here:
        actualizar_venta(1);
//        seleccionar_cargar_filtro();
    }//GEN-LAST:event_jCestado_alquiladoActionPerformed

    private void btnalquiler_todosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnalquiler_todosActionPerformed
        // TODO add your handling code here:
        actualizar_venta(0);
    }//GEN-LAST:event_btnalquiler_todosActionPerformed

    private void txtfec_vence_creditoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfec_vence_creditoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtfec_vence_creditoKeyPressed

    private void jCver_promocionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCver_promocionActionPerformed
        // TODO add your handling code here:
        actualizar_tabla_producto(1);
    }//GEN-LAST:event_jCver_promocionActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnagregar_cantidad;
    private javax.swing.JButton btnalquiler_todos;
    private javax.swing.JButton btnbuscar_cliente;
    private javax.swing.JButton btnconfirmar_venta_combinar;
    private javax.swing.JButton btnconfirmar_venta_efectivo;
    private javax.swing.JButton btnconfirmar_venta_tarjeta;
    private javax.swing.JButton btnconfirmar_venta_transferencia;
    private javax.swing.JButton btneliminar_item;
    private javax.swing.JButton btnestado_alquilado;
    private javax.swing.JButton btnestado_anulado;
    private javax.swing.JButton btnestado_cobrar_reservado;
    private javax.swing.JButton btnestado_devolucion;
    private javax.swing.JButton btnfacturar;
    private javax.swing.JButton btnimprimirNota;
    private javax.swing.JButton btnlimpiar_cliente;
    private javax.swing.JButton btnnuevoCliente;
    private javax.swing.JButton btnnuevo_cliente;
    private javax.swing.JButton btnseleccionarCerrar;
    private javax.swing.JComboBox<String> cmbentregador;
    private javax.swing.ButtonGroup gru_campo;
    private javax.swing.ButtonGroup gru_condi;
    private javax.swing.ButtonGroup gru_tipocli;
    private javax.swing.JButton jButton1;
    private javax.swing.JCheckBox jCestado_alquilado;
    private javax.swing.JCheckBox jCestado_anulado;
    private javax.swing.JCheckBox jCestado_devolucion;
    private javax.swing.JCheckBox jCestado_emitido;
    private javax.swing.JCheckBox jCestado_finalizado;
    private javax.swing.JCheckBox jCestado_usoreserva;
    private javax.swing.JCheckBox jCfuncionario;
    private javax.swing.JCheckBox jCimprimir_ticket;
    private javax.swing.JCheckBox jCver_promocion;
    private javax.swing.JCheckBox jCvuelto;
    private javax.swing.JFormattedTextField jFsaldo_credito;
    public static javax.swing.JFormattedTextField jFtotal_pagado;
    private javax.swing.JFormattedTextField jFtotal_reservado;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
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
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JList<String> jList_cliente;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel_fecha;
    private javax.swing.JRadioButton jRcond_contado;
    private javax.swing.JRadioButton jRcond_credito;
    private javax.swing.JRadioButton jRcond_pagoparcial;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTabbedPane jTab_producto_ingrediente;
    private javax.swing.JTabbedPane jTabbedPane_VENTA;
    private javax.swing.JLabel lblcantidad_filtro;
    private javax.swing.JLabel lblidcliente;
    private javax.swing.JLabel lblpago_parcial;
    private javax.swing.JLabel lblvence_credito;
    private javax.swing.JPanel panel_base_1;
    private javax.swing.JPanel panel_estado;
    private javax.swing.JPanel panel_insertar_pri_item;
    private javax.swing.JPanel panel_insertar_pri_producto;
    private javax.swing.JPanel panel_referencia_categoria;
    private javax.swing.JPanel panel_referencia_marca;
    private javax.swing.JPanel panel_referencia_unidad;
    private javax.swing.JPanel panel_referencia_venta;
    private javax.swing.JPanel panel_tabla_busca_cli;
    private javax.swing.JPanel panel_tabla_item;
    private javax.swing.JPanel panel_tabla_venta;
    private javax.swing.JTable tblbuscar_cliente;
    public static javax.swing.JTable tblitem_producto;
    private javax.swing.JTable tblitem_venta_filtro;
    private javax.swing.JTable tblproducto;
    private javax.swing.JTable tblventa;
    private javax.swing.JTextField txtbucarCliente_direccion;
    private javax.swing.JTextField txtbucarCliente_nombre;
    private javax.swing.JTextField txtbucarCliente_ruc;
    private javax.swing.JTextField txtbucarCliente_telefono;
    private javax.swing.JTextField txtbuscador_cliente_nombre;
    private javax.swing.JTextField txtbuscador_cliente_ruc;
    private javax.swing.JTextField txtbuscador_cliente_telefono;
    private javax.swing.JTextField txtbuscar_fecha;
    private javax.swing.JTextField txtbuscar_idventa;
    private javax.swing.JTextField txtbuscar_producto;
    private javax.swing.JTextField txtcantidad_cobrado;
    private javax.swing.JTextField txtcantidad_reserva;
    private javax.swing.JTextField txtcantidad_total;
    private javax.swing.JTextField txtcod_barra;
    private javax.swing.JTextField txtdireccion_alquiler;
    private javax.swing.JTextField txtfec_devolusion_previsto;
    private javax.swing.JTextField txtfec_retirado_previsto;
    private javax.swing.JTextField txtfec_vence_credito;
    private javax.swing.JTextField txthora_devolusion_previsto;
    private javax.swing.JTextField txthora_retirado_previsto;
    private javax.swing.JTextField txtidventa;
    private javax.swing.JTextField txtminuto_devolusion_previsto;
    private javax.swing.JTextField txtminuto_retirado_previsto;
    private javax.swing.JTextField txtmonto_delivery;
    private javax.swing.JTextField txtmonto_pago_parcial;
    private javax.swing.JTextField txtobservacion;
    private javax.swing.JTextField txtprecio_venta;
    private javax.swing.JTextField txtstock;
    private javax.swing.JTextField txtsubtotal;
    // End of variables declaration//GEN-END:variables
}
