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
import FORMULARIO.DAO.*;
import FORMULARIO.ENTIDAD.*;
import static FORMULARIO.VISTA.FrmCliente.txtdelivery;
import static FORMULARIO.VISTA.FrmCliente.txtzona;
import IMPRESORA_POS.PosImprimir_Venta;
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
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Digno
 */
public class FrmVenta extends javax.swing.JInternalFrame {

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
    PosImprimir_Venta posv = new PosImprimir_Venta();
    cliente clie = new cliente();
    item_venta item = new item_venta();
    DAO_item_venta ivdao = new DAO_item_venta();
    venta ven = new venta();
    BO_venta vBO = new BO_venta();
    DAO_venta vdao = new DAO_venta();
    factura fac = new factura();
    DAO_cliente cdao = new DAO_cliente();
    producto prod = new producto();
    DAO_producto pdao = new DAO_producto();
    caja_detalle caja = new caja_detalle();
    DAO_caja_detalle cjdao = new DAO_caja_detalle();
    BO_cliente cBO = new BO_cliente();
    zona_delivery zona = new zona_delivery();
    DAO_zona_delivery zdao = new DAO_zona_delivery();
    cotizacion coti = new cotizacion();
    DAO_cotizacion codao = new DAO_cotizacion();
    usuario usu = new usuario();
    DAO_entregador endao = new DAO_entregador();
    Connection connLocal = ConnPostgres.getConnPosgres();
    private java.util.List<JButton> botones_categoria;
    private java.util.List<JButton> botones_unidad;
    private java.util.List<JButton> botones_marca;
    DefaultTableModel model_itemf = new DefaultTableModel();
    cla_color_pelete clacolor = new cla_color_pelete();
    ClaAuxFiltroVenta auxvent = new ClaAuxFiltroVenta();
    private int cant_boton_cate;
    private int cant_boton_unid;
    private int cant_boton_marca;
    private String fk_idproducto_unidad;
    private String fk_idproducto_categoria;
    private String fk_idproducto_marca;
    private String cantidad_producto = "0";
    private int fk_idcliente_servi;
    private double monto_venta;
    private double monto_delivery;
    private String zona_delivery;
    private String tipo_entrega;
    private String entrega_delivery = "#>DELIVERY<#";
    private String entrega_paqueta = "#>PARA_LLEVAR<#";
    private String entrega_funcio = "#>FUNCIONARIO<#";
    private String estado_ANULADO = "ANULADO";
    private String estado_EMITIDO = "EMITIDO";
    private int idventa_ultimo;
    private String indice_venta;
    private int fk_idcliente_local;
    private boolean esFuncionario;
    private String grupo_en_ingrediente;
    private double venta_efectivo = 0;
    private double venta_tarjeta = 0;
    private String forma_pago = "SIN FORMA";
    private String tabla_origen = "SIN ORIGEN";
    private double valor_redondeo = 500;
    private int Iidproducto;
    private boolean esCargadoCodBarra;
    private String forma_pago_EFECTIVO = "EFECTIVO";
    private String tabla_origen_EFECTIVO = "VENTA_EFECTIVO";
    private String forma_pago_TARJETA = "TARJETA";
    private String tabla_origen_TARJETA = "VENTA_TARJETA";
    private boolean habilitar_editar_precio_venta;

    void abrir_formulario() {
        String servidor = "";
        this.setTitle("VENTA--> USUARIO:" + usu.getGlobal_nombre() + servidor);
        evetbl.centrar_formulario(this);
        botones_categoria = new ArrayList<>();
        botones_unidad = new ArrayList<>();
        botones_marca = new ArrayList<>();
        jList_cliente.setVisible(false);
        jCimprimir_ticket.setSelected(true);
        codao.cargar_cotizacion(coti, 1);
        endao.actualizar_tabla_entregador_venta(connLocal, tblentregador);
        color_formulario();
        color_jpanel();
        reestableser_venta();
        cargar_boton_categoria();
        crear_item_producto();
    }

    void color_formulario() {
        panel_tabla_busca_cli.setBackground(clacolor.getColor_tabla());
        panel_insertar_pri_item.setBackground(clacolor.getColor_insertar_primario());
        panel_insertar_pri_producto.setBackground(clacolor.getColor_insertar_primario());
        panel_referencia_categoria.setBackground(clacolor.getColor_tabla());
        panel_referencia_unidad.setBackground(clacolor.getColor_tabla());
        panel_tabla_venta.setBackground(clacolor.getColor_tabla());
        panel_tabla_item.setBackground(clacolor.getColor_tabla());
        panel_tabla_entregador.setBackground(clacolor.getColor_tabla());
        panel_referencia_venta.setBackground(clacolor.getColor_referencia());
        panel_insertar_sec_cliente.setBackground(clacolor.getColor_insertar_secundario());
        panel_tabla_cliente.setBackground(clacolor.getColor_tabla());
        panel_base_1.setBackground(clacolor.getColor_base());
        panel_base_2.setBackground(clacolor.getColor_base());
    }

    void color_jpanel() {
        if (ven.isVenta_aux()) {
            panel_referencia_categoria.setBackground(Color.orange);
            panel_referencia_unidad.setBackground(Color.orange);
            panel_insertar_pri_producto.setBackground(Color.orange);
            panel_insertar_pri_item.setBackground(Color.orange);
            panel_tabla_venta.setBackground(Color.orange);
            panel_insertar_sec_cliente.setBackground(Color.orange);
            panel_tabla_busca_cli.setBackground(Color.orange);
        }
    }

    void crear_item_producto() {
        String dato[] = {"id", "descripcion", "precio", "C", "total"};
        evejt.crear_tabla_datos(tblitem_producto, model_itemf, dato);
    }

    void ancho_item_producto() {
        int Ancho[] = {5, 53, 15, 6, 14};
        evejt.setAnchoColumnaJtable(tblitem_producto, Ancho);
    }

    boolean validar_cargar_item_producto() {
        if (evejt.getBoolean_validar_select(tblproducto)) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtcantidad_producto, "CARGAR UNA CANTIDAD")) {
            return false;
        }
        if (txtcantidad_producto.getText().trim().length() > 4) {
            JOptionPane.showMessageDialog(txtcantidad_producto, "EXEDE EL LIMITE DE CANTIDAD PERMITIDO", "ERROR", JOptionPane.ERROR_MESSAGE);
            txtcantidad_producto.setText(null);
            txtcantidad_producto.grabFocus();
            return false;
        }
        return true;
    }

    void sumar_item_venta() {
        double total_guarani = evejt.getDouble_sumar_tabla(tblitem_producto, 4);
        monto_venta = total_guarani;
//        double redondeo = monto_venta - monto_venta % valor_redondeo;
        double redondeo = monto_venta;
        txtredondeo.setText(String.valueOf((int) redondeo));
        evejtf.getString_format_nro_entero(txtredondeo);
        jFtotal_guarani.setValue(total_guarani);
        jFtotal_dolar.setValue(total_guarani / coti.getDolar_guarani_STATIC());
        jFtotal_real.setValue(total_guarani / coti.getReal_guarani_STATIC());
    }

    void cargar_directo_delivery() {
        if (fk_idcliente_servi >= 1 || fk_idcliente_local >= 1) {
            color_boton_entrega(Color.white, Color.orange);
            evejt.getBoolean_Eliminar_Fila_delivery(tblitem_producto, model_itemf);
            tipo_entrega = entrega_delivery;
            double precio = monto_delivery;
            String idproducto = "0";
            String descripcion = "#DELIVERY#>>" + zona_delivery;
            String precioUni = String.valueOf(precio);
            String cantidad = "1";
            double Dcantidad = Double.parseDouble(cantidad);
            String total = String.valueOf(precio * Dcantidad);
            String dato[] = {idproducto, descripcion, precioUni, cantidad, total};
            evejt.cargar_tabla_datos(tblitem_producto, model_itemf, dato);
            txtcantidad_producto.setText(null);
            sumar_item_venta();
        }
    }

    void boton_tipo_entrega_Delivery() {
        evejt.mostrar_JTabbedPane(jTab_producto_ingrediente, 0);
        if (fk_idcliente_servi >= 1 || fk_idcliente_local >= 1) {
            color_boton_entrega(Color.white, Color.orange);
            evejt.getBoolean_Eliminar_Fila_delivery(tblitem_producto, model_itemf);
            tipo_entrega = entrega_delivery;
            double precio = monto_delivery;
            String idproducto = "0";
            String descripcion = "#DELIVERY#>>" + zona_delivery;
            String precioUni = String.valueOf(precio);
            String cantidad = "1";
            double Dcantidad = Double.parseDouble(cantidad);
            String total = String.valueOf(precio * Dcantidad);
            String dato[] = {idproducto, descripcion, precioUni, cantidad, total};
            evejt.cargar_tabla_datos(tblitem_producto, model_itemf, dato);
            txtcantidad_producto.setText(null);
            sumar_item_venta();
        } else {
            JOptionPane.showMessageDialog(txtbucarCliente_nombre, "NO SE ENCONTRO NINGUN CLIENTE CARGADO", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
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
            int Iprecio_mino = (int) prod.getC4precio_venta_minorista();
            int Iprecio_mayo = (int) prod.getC5precio_venta_mayorista();
            int Icant_mayo = (int) prod.getC6cantidad_mayorista();
            String cantidad = txtcantidad_producto.getText();
            int Icantidad = Integer.parseInt(cantidad);
            int Isubtotal = 0;
            String Sprecio_venta = "0";
            if (habilitar_editar_precio_venta) {
                Sprecio_venta = txtprecio_venta.getText();
                int Iprecio_venta = Integer.parseInt(Sprecio_venta);
                Isubtotal = Icantidad * Iprecio_venta;
            } else {
                if (Icantidad < Icant_mayo) {
                    Isubtotal = Icantidad * Iprecio_mino;
                    Sprecio_venta = String.valueOf(Iprecio_mino);
                } else {
                    Isubtotal = Icantidad * Iprecio_mayo;
                    Sprecio_venta = String.valueOf(Iprecio_mayo);
                }
            }
            String Ssubtotal = String.valueOf(Isubtotal);
            String dato[] = {idproducto, descripcion, Sprecio_venta, cantidad, Ssubtotal};
            evejt.cargar_tabla_datos(tblitem_producto, model_itemf, dato);
            txtcantidad_producto.setText(null);
            reestableser_item_venta();
            sumar_item_venta();
        }
    }

    void calculo_cantidad(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            cargar_item_producto();
        } else {
            int Icantidad = evejtf.getInt_sumar_restar_cantidad(evt, txtcantidad_producto, false, 0);
            if (Icantidad > 0) {
                pdao.cargar_producto_por_idproducto(connLocal, prod, Iidproducto);
                int Iprecio_mino = (int) prod.getC4precio_venta_minorista();
                int Iprecio_mayo = (int) prod.getC5precio_venta_mayorista();
                int Icant_mayo = (int) prod.getC6cantidad_mayorista();
                int Isubtotal = 0;
                String Sprecio_venta = "0";
                if (Icantidad < Icant_mayo) {
                    Isubtotal = Icantidad * Iprecio_mino;
                    Sprecio_venta = String.valueOf(Iprecio_mino);
                    color_campo_item_venta(Color.white);
                } else {
                    Isubtotal = Icantidad * Iprecio_mayo;
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
        txtcantidad_producto.setBackground(color);
    }

    void reestableser_item_venta() {
        txtprecio_venta.setText(null);
        txtsubtotal.setText(null);
        txtstock.setText(null);
        txtbuscar_producto.setText(null);
        txtcod_barra.setText(null);
        panel_insertar_pri_producto.setBackground(clacolor.getColor_insertar_primario());
        lblmensaje.setVisible(false);
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
                + "group by 1,2,3\n"
                + "order by c.orden asc;";
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
        String sql = "select p.idproducto as idp,\n"
                + "(pm.nombre||'-'||u.nombre||'-'||p.nombre) as marca_unid_nom,\n"
                + "p.stock,"
                + "TRIM(to_char(p.precio_venta_minorista,'999G999G999')) as pv_mino, \n"
                + "p.cantidad_mayorista as mayor,"
                + "TRIM(to_char(p.precio_venta_mayorista,'999G999G999')) as pv_mayo \n"
                + "from producto p,producto_categoria c,producto_unidad u,producto_marca pm \n"
                + "where p.fk_idproducto_categoria=c.idproducto_categoria \n"
                + "and p.fk_idproducto_unidad=u.idproducto_unidad \n"
                + "and p.fk_idproducto_marca=pm.idproducto_marca \n"
                + "and c.activar=true \n"
                + "and p.activar=true \n" + filtro_categoria + filtro_unidad + filtro_producto + filtro_marca
                + " order by p.idproducto  asc;";
        eveconn.Select_cargar_jtable(connLocal, sql, tblproducto);
        ancho_tabla_producto();
    }

    void pre_cargar_item_venta() {
        String Sprecio_venta = "0";
        int Iprecio_mino = (int) prod.getC4precio_venta_minorista();
        int Istock = (int) prod.getC8stock();
        Sprecio_venta = String.valueOf(Iprecio_mino);
        String Sstock = String.valueOf(Istock);
        txtbuscar_producto.setText(prod.getC20_marca() + "-" + prod.getC18_unidad() + "-" + prod.getC3nombre());
        txtcod_barra.setText(prod.getC2cod_barra());
        if (prod.getC8stock() <= prod.getC9stock_min()) {
            panel_insertar_pri_producto.setBackground(Color.red);
            lblmensaje.setVisible(true);
            lblmensaje.setText("ESTOS MINIMO:" + prod.getC9stock_min());
        } else {
            panel_insertar_pri_producto.setBackground(clacolor.getColor_insertar_primario());
            lblmensaje.setVisible(false);
        }
        txtstock.setText(Sstock);
        txtcantidad_producto.setText(null);
        txtprecio_venta.setText(Sprecio_venta);
        txtsubtotal.setText(Sprecio_venta);
        txtcantidad_producto.grabFocus();
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
        txtcantidad_producto.setText(String.valueOf(cant));
        cargar_item_producto();
    }

    void color_boton_entrega(Color paquete, Color delivery) {
        evejt.mostrar_JTabbedPane(jTab_producto_ingrediente, 0);
//        btnagregar_local.setBackground(local);
        btnagregar_paquete.setBackground(paquete);
        btnagregar_delivery.setBackground(delivery);
    }

    void boton_tipo_entrega_Paquete() {
        color_boton_entrega(Color.orange, Color.white);
        evejt.getBoolean_Eliminar_Fila_delivery(tblitem_producto, model_itemf);
        sumar_item_venta();
        tipo_entrega = entrega_paqueta;
    }

    void limpiar_cliente() {
        evejt.getBoolean_Eliminar_Fila_delivery(tblitem_producto, model_itemf);
        tipo_entrega = entrega_paqueta;
        lblidcliente.setText("id:0");
        fk_idcliente_servi = 1;
        fk_idcliente_local = 1;
        txtbucarCliente_nombre.setText(null);
        txtbucarCliente_telefono.setText(null);
        txtbucarCliente_ruc.setText(null);
        txtbucarCliente_direccion.setText(null);
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
            fk_idcliente_servi = evejt.getInt_select_id(tblbuscar_cliente);
            fk_idcliente_local = evejt.getInt_select_id(tblbuscar_cliente);
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
        if (clie.getC8tipo().equals("funcionario")) {
            esFuncionario = true;
            lbltipocliente.setText("FUNCIONARIO");
        } else {
            esFuncionario = false;
            lbltipocliente.setText("CLIENTE");
        }
        cargar_directo_delivery();
    }

    void boton_eliminar_item() {
        if (!evejt.getBoolean_validar_select(tblitem_producto)) {
            if (evejt.getBoolean_Eliminar_Fila(tblitem_producto, model_itemf)) {
                sumar_item_venta();
            }
        }
    }

    boolean validar_guardar_venta() {
        idventa_ultimo = (eveconn.getInt_ultimoID_mas_uno(connLocal, ven.getTabla(), ven.getIdtabla()));
        if (evejt.getBoolean_validar_cant_cargado(tblitem_producto)) {
            return false;
        }
        if (fk_idcliente_servi == 1) {
            txtbucarCliente_nombre.setText("OCACIONAL");
            txtbucarCliente_telefono.setText("xxx");
            txtbucarCliente_ruc.setText("xxx");
            txtbucarCliente_direccion.setText("CDE");
        }
        return true;
    }

    void validar_redondeo() {
        double redondeo = evejtf.getDouble_format_nro_entero(txtredondeo);
        if ((redondeo + valor_redondeo) < monto_venta) {
            txtredondeo.setBackground(Color.red);
        } else {
            txtredondeo.setBackground(Color.green);
        }
    }

    void reestableser_venta() {
        idventa_ultimo = (eveconn.getInt_ultimoID_mas_uno(connLocal, ven.getTabla(), ven.getIdtabla()));
        txtidventa.setText(String.valueOf(idventa_ultimo));
        indice_venta = eveut.getString_crear_indice();
        color_boton_entrega(Color.white, Color.white);
        txtredondeo.setBackground(Color.green);
        txtbuscar_fecha.setText(evefec.getString_formato_fecha());
        jCestado_emitido.setSelected(true);
        jCestado_terminado.setSelected(false);
        jCestado_anulado.setSelected(false);
        jCfecha_todos.setSelected(false);
        actualizar_venta(3);
        actualizar_tabla_producto(1);
        tipo_entrega = entrega_paqueta;
        monto_venta = 0;
        monto_delivery = 0;
        txtcantidad_producto.setText(null);
        lbltipocliente.setText("OCASIONAL");
        txtobservacion.setText("Ninguna");
        btnconfirmar_venta_efectivo.setBackground(Color.white);
        jFtotal_guarani.setValue(monto_venta);
        jFtotal_dolar.setValue(monto_venta);
        jFtotal_real.setValue(monto_venta);
        txtredondeo.setText("0");
        evejt.limpiar_tabla_datos(model_itemf);
        evejt.mostrar_JTabbedPane(jTab_producto_ingrediente, 0);
        limpiar_cliente();
        reestableser_item_venta();
    }

    void cargar_datos_venta() {
        monto_delivery = evejt.getDouble_monto_validado(tblitem_producto, 4, 0, "0");
        boolean delivery = false;
        if (monto_delivery > 0) {
            delivery = true;
        }
        if (esFuncionario) {
            tipo_entrega = entrega_funcio;
        }
        ven.setC2fecha_emision("now");//falta hora
        ven.setC3monto_venta(monto_venta);
        ven.setC4monto_delivery(monto_delivery);
//        double redondeo=MathUtils.round((double) i, -1);
        double redondeo = evejtf.getDouble_format_nro_entero(txtredondeo);
        ven.setC5redondeo(redondeo);
        ven.setC6estado(estado_EMITIDO);
        ven.setC7observacion(txtobservacion.getText());
        ven.setC8forma_pago(forma_pago);
        ven.setC9delivery(delivery);
        ven.setC10fk_idcliente(fk_idcliente_local);
        ven.setC11fk_idusuario(usu.getGlobal_idusuario());
        ven.setC12fk_identregador(1);
        ven.setC13entrega(tipo_entrega);
        ven.setIdventaGlobal(idventa_ultimo);
        ven.setMonto_ventaGlobal(monto_venta);
    }

    void cargar_datos_caja() {
        caja.setC2fecha_emision(evefec.getString_formato_fecha_hora());
        caja.setC3descripcion("(VENTA) id:" + idventa_ultimo + " Cli:" + txtbucarCliente_nombre.getText());
        caja.setC4monto_venta_efectivo(venta_efectivo);
        caja.setC5monto_venta_tarjeta(venta_tarjeta);
        caja.setC6monto_delivery(monto_delivery);
        caja.setC7monto_gasto(0);
        caja.setC8monto_compra(0);
        caja.setC9monto_vale(0);
        caja.setC10monto_caja(0);
        caja.setC11monto_cierre(0);
        caja.setC12id_origen(idventa_ultimo);
        caja.setC13tabla_origen(tabla_origen);
        caja.setC15estado(estado_EMITIDO);
        caja.setC16fk_idusuario(usu.getGlobal_idusuario());
    }

    void boton_guardar_venta() {
        if (validar_guardar_venta()) {
            cargar_datos_venta();
            cargar_datos_caja();
            if (vBO.getBoolean_insertar_venta(connLocal, tblitem_producto, item, ven, caja)) {
                if (jCvuelto.isSelected()) {
                    FrmVuelto vuel = new FrmVuelto(null, true);
                    vuel.setVisible(true);
                } else {
                    if (jCimprimir_ticket.isSelected()) {
                        imprimir_venta(idventa_ultimo);
                    } else {
                        JOptionPane.showMessageDialog(null, "VENTA GUARDADO\nSIN TICKET");
                    }
                }
                reestableser_venta();
                endao.actualizar_tabla_entregador_venta(connLocal, tblentregador);
                if (ven.isVenta_aux()) {
                    ven.setVenta_aux(false);
                    this.dispose();
                }
            }
        }
    }

    void imprimir_venta(int idventa) {
        posv.boton_imprimir_pos_VENTA(connLocal, idventa);
    }

    void boton_imprimirPos_venta() {
        if (!evejt.getBoolean_validar_select(tblventa)) {
            int idventa = (evejt.getInt_select_id(tblventa));
            imprimir_venta(idventa);
        }
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
        String filtro_estado = auxvent.filtro_estado(jCestado_emitido, jCestado_terminado, jCestado_anulado);
        campo_filtro = "(c.idcliente||'-'||c.nombre) as cliente";
        if (tipo == 1) {
            filtro = filtro_estado;
        }
        if (tipo == 2) {
            if (txtbuscar_idventa.getText().trim().length() > 0) {
                filtro = " and v.idventa=" + txtbuscar_idventa.getText() + " ";
            }
        }
        if (tipo == 3) {
            if (txtbuscar_fecha.getText().trim().length() > 0) {
                if (jCfecha_todos.isSelected()) {
                    filtro = filtro_estado;
                } else {
                    filtro = " and date(v.fecha_emision)='" + txtbuscar_fecha.getText() + "' " + filtro_estado;
                }
            }
        }
        lblcantidad_filtro.setText("CANT FILTRO: ( " + tblventa.getRowCount() + " )");
        vdao.actualizar_tabla_venta(connLocal, tblventa, filtro, campo_filtro);
    }

    void boton_estado_venta_anular() {
        if (!evejt.getBoolean_validar_select(tblventa)) {
            if (evemen.MensajeGeneral_warning("ESTAS SEGURO DE ANULAR LA VENTA", "ANULAR", "ANULAR", "CANCELAR")) {
                int idventa = evejt.getInt_select_id(tblventa);
                String forma_pago = evejt.getString_select(tblventa, 5);
                System.out.println("Forma Pago:" + forma_pago);
                ven.setC6estado(estado_ANULADO);
                ven.setC1idventa(idventa);
                caja.setC15estado(estado_ANULADO);
                String tabla_origen = "";
                if (forma_pago.equals(forma_pago_EFECTIVO)) {
                    tabla_origen = tabla_origen_EFECTIVO;
                }
                if (forma_pago.equals(forma_pago_TARJETA)) {
                    tabla_origen = tabla_origen_TARJETA;
                }
                caja.setC13tabla_origen(tabla_origen);
                caja.setC12id_origen(idventa);
                vBO.update_anular_venta(connLocal, ven, caja);
                actualizar_venta(3);
            }
        }
    }

    void boton_forma_pago_EFECTIVO() {
        if (!evejt.getBoolean_validar_select(tblventa)) {
            if (evemen.MensajeGeneral_warning("ESTAS SEGURO DE PASAR FORMA DE PAGO A EFECTIVO", "FORMA PAGO", "EFECTIVO", "CANCELAR")) {
                int idventa = evejt.getInt_select_id(tblventa);
                String forma_pago = evejt.getString_select(tblventa, 5);
                String tabla_origen = "";
                if (forma_pago.equals(forma_pago_EFECTIVO)) {
                    tabla_origen = tabla_origen_EFECTIVO;
                }
                if (forma_pago.equals(forma_pago_TARJETA)) {
                    tabla_origen = tabla_origen_TARJETA;
                }
                vdao.cargar_venta(ven, idventa);
                caja.setC4monto_venta_efectivo(ven.getC5redondeo());
                caja.setC5monto_venta_tarjeta(0);
                caja.setC13tabla_origen_update(tabla_origen_EFECTIVO);
                caja.setC13tabla_origen(tabla_origen);
                caja.setC12id_origen(idventa);
                ven.setC8forma_pago(forma_pago_EFECTIVO);
                ven.setC1idventa(idventa);
                vBO.update_forma_pago_venta(connLocal, ven, caja);
                actualizar_venta(3);
            }
        }
    }

    void boton_forma_pago_TARJETA() {
        if (!evejt.getBoolean_validar_select(tblventa)) {
            if (evemen.MensajeGeneral_warning("ESTAS SEGURO DE PASAR FORMA DE PAGO A TARJETA", "FORMA PAGO", "TARJETA", "CANCELAR")) {
                int idventa = evejt.getInt_select_id(tblventa);
                String forma_pago = evejt.getString_select(tblventa, 5);
                String tabla_origen = "";
                if (forma_pago.equals(forma_pago_EFECTIVO)) {
                    tabla_origen = tabla_origen_EFECTIVO;
                }
                if (forma_pago.equals(forma_pago_TARJETA)) {
                    tabla_origen = tabla_origen_TARJETA;
                }
                vdao.cargar_venta(ven, idventa);
                caja.setC4monto_venta_efectivo(0);
                caja.setC5monto_venta_tarjeta(ven.getC5redondeo());
                caja.setC13tabla_origen_update(tabla_origen_TARJETA);
                caja.setC13tabla_origen(tabla_origen);
                caja.setC12id_origen(idventa);
                ven.setC8forma_pago(forma_pago_TARJETA);
                ven.setC1idventa(idventa);
                vBO.update_forma_pago_venta(connLocal, ven, caja);
                actualizar_venta(3);
            }
        }
    }

    ///###################CLIENTE#####################
    private void abrir_formulario_cliente() {
        reestableser_cliente();
    }

    private boolean validar_guardar_cliente() {
        txtcli_fecha_inicio.setText(evefec.getString_formato_fecha());
        if (evejtf.getBoo_JTextField_vacio(txtcli_nombre, "DEBE CARGAR UN NOMBRE")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtcli_ruc, "DEBE CARGAR UN RUC")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtcli_telefono, "DEBE CARGAR UN TELEFONO")) {
            return false;
        }
        if (evejtf.getBoo_JTextarea_vacio(txtcli_direccion, "DEBE CARGAR UNA DIRECCION")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtcli_zona, "DEBE CARGAR UNA ZONA")) {
            return false;
        }
        if (txtcli_fecha_nacimiento.getText().trim().length() == 0) {
            txtcli_fecha_nacimiento.setText(evefec.getString_formato_fecha());
            clie.setC7fecha_cumple(evefec.getString_formato_fecha());
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

    void insertar_cliente_servidor() {
    }

    private void boton_guardar_cliente() {
        if (validar_guardar_cliente()) {
            clie.setC2fecha_inicio("now");
            clie.setC3nombre(txtcli_nombre.getText());
            clie.setC4direccion(txtcli_direccion.getText());
            clie.setC5telefono(txtcli_telefono.getText());
            clie.setC6ruc(txtcli_ruc.getText());
            clie.setC7fecha_cumple(txtcli_fecha_nacimiento.getText());
            clie.setC8tipo(tipo_cliente());
            cBO.insertar_cliente(connLocal, clie, tblpro_cliente);
            insertar_cliente_servidor();
            reestableser_cliente();
            seleccionar_cargar_cliente(2);

        }
    }

    private void boton_editar_cliente() {
        if (validar_guardar_cliente()) {
            clie.setC1idcliente(Integer.parseInt(txtcli_idcliente.getText()));
            clie.setC2fecha_inicio(txtcli_fecha_inicio.getText());
            clie.setC3nombre(txtcli_nombre.getText());
            clie.setC4direccion(txtcli_direccion.getText());
            clie.setC5telefono(txtcli_telefono.getText());
            clie.setC6ruc(txtcli_ruc.getText());
            clie.setC7fecha_cumple(txtcli_fecha_nacimiento.getText());
            clie.setC8tipo(tipo_cliente());
            cBO.update_cliente(clie, tblpro_cliente);
        }
    }

    private void cargar_cliente_copiado(int idcliente) {
        cdao.cargar_cliente(connLocal, clie, idcliente);
        fk_idcliente_local = idcliente;
        lblidcliente.setText("id:" + idcliente);
        monto_delivery = clie.getC11deliveryDouble();
        zona_delivery = clie.getC10zona();
        txtbucarCliente_nombre.setText(clie.getC3nombre());
        txtbucarCliente_telefono.setText(clie.getC5telefono());
        txtbucarCliente_ruc.setText(clie.getC6ruc());
        txtbucarCliente_direccion.setText(clie.getC4direccion());
        if (clie.getC8tipo().equals("funcionario")) {
            esFuncionario = true;
            lbltipocliente.setText("FUNCIONARIO");
        } else {
            esFuncionario = false;
            lbltipocliente.setText("CLIENTE");
        }
    }

    private void seleccionar_tabla_cliente() {
        int id = evejt.getInt_select_id(tblpro_cliente);
        cdao.cargar_cliente(connLocal, clie, id);
        txtcli_idcliente.setText(String.valueOf(clie.getC1idcliente()));
        txtcli_fecha_inicio.setText(clie.getC2fecha_inicio());
        txtcli_nombre.setText(clie.getC3nombre());
        txtcli_direccion.setText(clie.getC4direccion());
        txtcli_telefono.setText(clie.getC5telefono());
        txtcli_ruc.setText(clie.getC6ruc());
        txtcli_fecha_nacimiento.setText(clie.getC7fecha_cumple());
        if (clie.getC8tipo().equals("cliente")) {
            jRtipo_cliente.setSelected(true);
        }
        if (clie.getC8tipo().equals("funcionario")) {
            jRtipo_funcionario.setSelected(true);
        }
        txtcli_zona.setText(clie.getC10zona());
        txtcli_delivery.setText(clie.getC11delivery());
        btnguardar.setEnabled(false);
        btneditar.setEnabled(true);
    }

    private void reestableser_cliente() {
        jLzona.setVisible(false);
        jRtipo_cliente.setSelected(true);
        txtcli_idcliente.setText(null);
        txtcli_nombre.setText(null);
        txtcli_fecha_inicio.setText(null);
        txtcli_direccion.setText(null);
        txtcli_telefono.setText(null);
        txtcli_ruc.setText(null);
        txtcli_fecha_nacimiento.setText(null);
        txtcli_zona.setText(null);
        txtcli_delivery.setText(null);
        btnguardar.setEnabled(true);
        btneditar.setEnabled(false);
        btndeletar.setEnabled(false);
        txtcli_nombre.grabFocus();
        evejt.mostrar_JTabbedPane(jTabbedPane_VENTA, 0);
    }

    private void cargar_zona_cliente() {
        int idzona = eveconn.getInt_Solo_seleccionar_JLista(connLocal, jLzona, zona.getTabla(), zona.getZona_concat(), zona.getIdtabla());
        clie.setC9fk_idzona_delivery(idzona);
        zdao.cargar_zona_delivery(zona, idzona);
        txtcli_delivery.setText(String.valueOf(zona.getDelivery()));
        txtcli_zona.setText(zona.getNombre());
    }

    private void boton_nuevo_cliente() {
        reestableser_cliente();
    }

    private void limpiar_buscardor_cliente() {
        jCfuncionario.setSelected(false);
        txtbuscador_cliente_nombre.setText(null);
        txtbuscador_cliente_telefono.setText(null);
        txtbuscador_cliente_ruc.setText(null);
        txtbuscador_cliente_telefono.grabFocus();
        cdao.buscar_tabla_cliente_zona(connLocal, tblbuscar_cliente, txtbuscador_cliente_telefono, jCfuncionario, 2);
    }

    private void boton_buscar_cliente_venta() {
        evejt.mostrar_JTabbedPane(jTabbedPane_VENTA, 3);
        limpiar_buscardor_cliente();
    }

    private void seleccionar_tabla_venta() {
        if (!evejt.getBoolean_validar_select(tblventa)) {
            item.setC6fk_idventa(evejt.getInt_select_id(tblventa));
            String estado = evejt.getString_select(tblventa, 5);
            if (estado.equals(estado_EMITIDO)) {
                btnanularventa.setEnabled(true);
            }
            if (estado.equals(estado_ANULADO)) {
                btnanularventa.setEnabled(false);
            }
            ivdao.tabla_item_cliente_filtro(connLocal, tblitem_venta_filtro, item);
        }
    }

    void boton_venta_efectivo() {
        double redondeo = evejtf.getDouble_format_nro_entero(txtredondeo);
        venta_efectivo = redondeo;
        venta_tarjeta = 0;
        forma_pago = forma_pago_EFECTIVO;//"EFECTIVO";
        tabla_origen = tabla_origen_EFECTIVO;//"VENTA_EFECTIVO";
        boton_guardar_venta();
    }

    void boton_venta_tarjeta() {
        double redondeo = evejtf.getDouble_format_nro_entero(txtredondeo);
        venta_efectivo = 0;
        venta_tarjeta = redondeo;
        forma_pago = forma_pago_TARJETA;//"TARJETA";
        tabla_origen = tabla_origen_TARJETA;//"VENTA_TARJETA";
        boton_guardar_venta();
    }

    void calcular_subtotal_itemventa() {
        if (txtprecio_venta.getText().trim().length() > 0) {
            int precio_venta = Integer.parseInt(txtprecio_venta.getText());
            int cantidad = 0;
            if (txtcantidad_producto.getText().trim().length() == 0) {
                cantidad = 1;
            } else {
                cantidad = Integer.parseInt(txtcantidad_producto.getText());
            }
            int subtotal = precio_venta * cantidad;
            txtsubtotal.setText(String.valueOf(subtotal));
        }
    }

    //######################## MESA ##########################
    public FrmVenta() {
        initComponents();
        abrir_formulario();
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

        gru_campo = new javax.swing.ButtonGroup();
        gru_tipocli = new javax.swing.ButtonGroup();
        jTabbedPane_VENTA = new javax.swing.JTabbedPane();
        panel_base_1 = new javax.swing.JPanel();
        panel_referencia_categoria = new javax.swing.JPanel();
        panel_referencia_unidad = new javax.swing.JPanel();
        jTab_producto_ingrediente = new javax.swing.JTabbedPane();
        panel_insertar_pri_producto = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtbuscar_producto = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblproducto = new javax.swing.JTable();
        txtcantidad_producto = new javax.swing.JTextField();
        btnagregar_cantidad = new javax.swing.JButton();
        jLabel29 = new javax.swing.JLabel();
        txtcod_barra = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        txtsubtotal = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        txtprecio_venta = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        txtstock = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        lblmensaje = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtobservacion = new javax.swing.JTextField();
        panel_insertar_pri_item = new javax.swing.JPanel();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jList_cliente = new javax.swing.JList<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblitem_producto = new javax.swing.JTable();
        btnagregar_delivery = new javax.swing.JButton();
        btneliminar_item = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jFtotal_guarani = new javax.swing.JFormattedTextField();
        jLabel8 = new javax.swing.JLabel();
        jFtotal_real = new javax.swing.JFormattedTextField();
        jLabel9 = new javax.swing.JLabel();
        jFtotal_dolar = new javax.swing.JFormattedTextField();
        btnconfirmar_venta_efectivo = new javax.swing.JButton();
        btnagregar_paquete = new javax.swing.JButton();
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
        lbltipocliente = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        btnconfirmar_venta_tarjeta = new javax.swing.JButton();
        txtredondeo = new javax.swing.JTextField();
        jCimprimir_ticket = new javax.swing.JCheckBox();
        jCvuelto = new javax.swing.JCheckBox();
        panel_referencia_marca = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        txtidventa = new javax.swing.JTextField();
        panel_referencia_venta = new javax.swing.JPanel();
        panel_tabla_venta = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblventa = new javax.swing.JTable();
        btnanularventa = new javax.swing.JButton();
        btnfacturar = new javax.swing.JButton();
        btnimprimirNota = new javax.swing.JButton();
        panel_tabla_item = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblitem_venta_filtro = new javax.swing.JTable();
        panel_tabla_entregador = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        tblentregador = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        txtbuscar_idventa = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtbuscar_fecha = new javax.swing.JTextField();
        jCestado_emitido = new javax.swing.JCheckBox();
        jCestado_terminado = new javax.swing.JCheckBox();
        jCestado_anulado = new javax.swing.JCheckBox();
        lblcantidad_filtro = new javax.swing.JLabel();
        jCfecha_todos = new javax.swing.JCheckBox();
        btnpasar_venta_efectivo = new javax.swing.JButton();
        btnpasar_venta_tarjeta = new javax.swing.JButton();
        panel_base_2 = new javax.swing.JPanel();
        panel_insertar_sec_cliente = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jRtipo_cliente = new javax.swing.JRadioButton();
        jRtipo_funcionario = new javax.swing.JRadioButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtcli_direccion = new javax.swing.JTextArea();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtcli_ruc = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtcli_telefono = new javax.swing.JTextField();
        txtcli_nombre = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtcli_idcliente = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtcli_fecha_inicio = new javax.swing.JTextField();
        lblfecnac = new javax.swing.JLabel();
        txtcli_fecha_nacimiento = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jLayeredPane2 = new javax.swing.JLayeredPane();
        jLzona = new javax.swing.JList<>();
        jLabel21 = new javax.swing.JLabel();
        txtcli_zona = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        txtcli_delivery = new javax.swing.JTextField();
        btnnuevo = new javax.swing.JButton();
        btnguardar = new javax.swing.JButton();
        btneditar = new javax.swing.JButton();
        btndeletar = new javax.swing.JButton();
        btnnuevazona = new javax.swing.JButton();
        btnlimpiarzona = new javax.swing.JButton();
        panel_tabla_cliente = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblpro_cliente = new javax.swing.JTable();
        jLabel23 = new javax.swing.JLabel();
        txtbuscar_nombre = new javax.swing.JTextField();
        txtbuscar_telefono = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        txtbuscar_ruc = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
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

        panel_referencia_categoria.setBackground(new java.awt.Color(102, 153, 255));
        panel_referencia_categoria.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panel_referencia_categoria.setLayout(new java.awt.GridLayout(0, 1));

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

        txtcantidad_producto.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtcantidad_producto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtcantidad_producto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcantidad_productoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtcantidad_productoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtcantidad_productoKeyTyped(evt);
            }
        });

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

        jLabel34.setBackground(new java.awt.Color(0, 0, 0));
        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(255, 255, 255));
        jLabel34.setText("F1: CONFIRMAR EFECTIVO / F2: CONFIRMAR TARJETA");

        lblmensaje.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblmensaje.setForeground(new java.awt.Color(255, 255, 0));
        lblmensaje.setText("jLabel35");

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
                            .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE)
                            .addComponent(txtprecio_venta))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_insertar_pri_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_insertar_pri_productoLayout.createSequentialGroup()
                                .addComponent(jLabel30)
                                .addGap(68, 68, 68))
                            .addComponent(txtsubtotal)))
                    .addGroup(panel_insertar_pri_productoLayout.createSequentialGroup()
                        .addGroup(panel_insertar_pri_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel34, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblmensaje, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_insertar_pri_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtcantidad_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnagregar_cantidad, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        panel_insertar_pri_productoLayout.setVerticalGroup(
            panel_insertar_pri_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertar_pri_productoLayout.createSequentialGroup()
                .addGap(4, 4, 4)
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
                .addGap(3, 3, 3)
                .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_pri_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_insertar_pri_productoLayout.createSequentialGroup()
                        .addComponent(txtcantidad_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(232, 232, 232)
                        .addComponent(btnagregar_cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_insertar_pri_productoLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblmensaje)))
                .addGap(28, 28, 28))
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
        jLayeredPane1.add(jList_cliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 460, 160));

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

        jLayeredPane1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 480, 200));

        btnagregar_delivery.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/venta/delivery.png"))); // NOI18N
        btnagregar_delivery.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnagregar_deliveryActionPerformed(evt);
            }
        });

        btneliminar_item.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/venta/eliminar.png"))); // NOI18N
        btneliminar_item.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneliminar_itemActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("GUARANI:");

        jFtotal_guarani.setEditable(false);
        jFtotal_guarani.setBackground(new java.awt.Color(204, 204, 255));
        jFtotal_guarani.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFtotal_guarani.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_guarani.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("REAL:");

        jFtotal_real.setEditable(false);
        jFtotal_real.setBackground(new java.awt.Color(204, 204, 255));
        jFtotal_real.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00 R"))));
        jFtotal_real.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_real.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText("DOLAR:");

        jFtotal_dolar.setEditable(false);
        jFtotal_dolar.setBackground(new java.awt.Color(204, 204, 255));
        jFtotal_dolar.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00 $"))));
        jFtotal_dolar.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_dolar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        btnconfirmar_venta_efectivo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnconfirmar_venta_efectivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/venta/efectivo.png"))); // NOI18N
        btnconfirmar_venta_efectivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnconfirmar_venta_efectivoActionPerformed(evt);
            }
        });

        btnagregar_paquete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/venta/paquete.png"))); // NOI18N
        btnagregar_paquete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnagregar_paqueteActionPerformed(evt);
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

        lbltipocliente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbltipocliente.setForeground(new java.awt.Color(255, 0, 0));
        lbltipocliente.setText("tipo");

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel33.setText("REDONDEO:");

        btnconfirmar_venta_tarjeta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/venta/credito.png"))); // NOI18N
        btnconfirmar_venta_tarjeta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnconfirmar_venta_tarjetaActionPerformed(evt);
            }
        });

        txtredondeo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtredondeo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtredondeo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtredondeoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtredondeoKeyTyped(evt);
            }
        });

        jCimprimir_ticket.setText("IMPRIMIR TICKET");

        javax.swing.GroupLayout panel_insertar_pri_itemLayout = new javax.swing.GroupLayout(panel_insertar_pri_item);
        panel_insertar_pri_item.setLayout(panel_insertar_pri_itemLayout);
        panel_insertar_pri_itemLayout.setHorizontalGroup(
            panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertar_pri_itemLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                            .addComponent(txtbucarCliente_direccion))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panel_insertar_pri_itemLayout.createSequentialGroup()
                        .addComponent(btnnuevo_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnbuscar_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnlimpiar_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbltipocliente, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addComponent(jLayeredPane1)
            .addGroup(panel_insertar_pri_itemLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_insertar_pri_itemLayout.createSequentialGroup()
                        .addGroup(panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_insertar_pri_itemLayout.createSequentialGroup()
                                .addComponent(jFtotal_guarani)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtredondeo, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(9, 9, 9))
                            .addGroup(panel_insertar_pri_itemLayout.createSequentialGroup()
                                .addGroup(panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panel_insertar_pri_itemLayout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addGap(95, 95, 95)
                                        .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panel_insertar_pri_itemLayout.createSequentialGroup()
                                        .addComponent(btnagregar_paquete, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnagregar_delivery)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btneliminar_item, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jFtotal_real, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jFtotal_dolar, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panel_insertar_pri_itemLayout.createSequentialGroup()
                        .addGroup(panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCimprimir_ticket)
                            .addGroup(panel_insertar_pri_itemLayout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnconfirmar_venta_efectivo, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnconfirmar_venta_tarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbltipocliente)
                    .addComponent(btnbuscar_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(btnnuevo_cliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnlimpiar_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnagregar_paquete, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btnagregar_delivery, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btneliminar_item, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel7)
                    .addComponent(jLabel33)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jFtotal_guarani, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                    .addComponent(jFtotal_real)
                    .addComponent(jFtotal_dolar)
                    .addComponent(txtredondeo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btnconfirmar_venta_tarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnconfirmar_venta_efectivo, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jCimprimir_ticket)
                .addContainerGap())
        );

        jCvuelto.setText("VUELTO");

        panel_referencia_marca.setBackground(new java.awt.Color(102, 153, 255));
        panel_referencia_marca.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panel_referencia_marca.setLayout(new java.awt.GridLayout(1, 0));

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel12.setText("IDVENTA:");

        txtidventa.setBackground(new java.awt.Color(0, 0, 255));
        txtidventa.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtidventa.setForeground(new java.awt.Color(255, 255, 0));
        txtidventa.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        javax.swing.GroupLayout panel_base_1Layout = new javax.swing.GroupLayout(panel_base_1);
        panel_base_1.setLayout(panel_base_1Layout);
        panel_base_1Layout.setHorizontalGroup(
            panel_base_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_base_1Layout.createSequentialGroup()
                .addComponent(panel_referencia_categoria, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_base_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_base_1Layout.createSequentialGroup()
                        .addGroup(panel_base_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_base_1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtobservacion, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jCvuelto)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtidventa, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jTab_producto_ingrediente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(panel_insertar_pri_item, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8))
                    .addComponent(panel_referencia_unidad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel_referencia_marca, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panel_base_1Layout.setVerticalGroup(
            panel_base_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_base_1Layout.createSequentialGroup()
                .addGroup(panel_base_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_base_1Layout.createSequentialGroup()
                        .addComponent(panel_referencia_unidad, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panel_referencia_marca, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_base_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_base_1Layout.createSequentialGroup()
                                .addComponent(jTab_producto_ingrediente, javax.swing.GroupLayout.PREFERRED_SIZE, 485, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panel_base_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtobservacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2)
                                    .addComponent(jCvuelto)
                                    .addComponent(jLabel12)
                                    .addComponent(txtidventa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(panel_insertar_pri_item, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(panel_referencia_categoria, javax.swing.GroupLayout.PREFERRED_SIZE, 588, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        tblventa.setRowHeight(30);
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

        panel_tabla_item.setBorder(javax.swing.BorderFactory.createEtchedBorder());

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

        panel_tabla_entregador.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tblentregador.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        tblentregador.setModel(new javax.swing.table.DefaultTableModel(
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
        tblentregador.setRowHeight(25);
        tblentregador.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblentregadorMouseReleased(evt);
            }
        });
        jScrollPane10.setViewportView(tblentregador);

        javax.swing.GroupLayout panel_tabla_entregadorLayout = new javax.swing.GroupLayout(panel_tabla_entregador);
        panel_tabla_entregador.setLayout(panel_tabla_entregadorLayout);
        panel_tabla_entregadorLayout.setHorizontalGroup(
            panel_tabla_entregadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
        );
        panel_tabla_entregadorLayout.setVerticalGroup(
            panel_tabla_entregadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
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

        jCestado_emitido.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCestado_emitido.setText("EMITIDO");
        jCestado_emitido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCestado_emitidoActionPerformed(evt);
            }
        });

        jCestado_terminado.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCestado_terminado.setText("TERMINADO");
        jCestado_terminado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCestado_terminadoActionPerformed(evt);
            }
        });

        jCestado_anulado.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCestado_anulado.setText("ANULADO");
        jCestado_anulado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCestado_anuladoActionPerformed(evt);
            }
        });

        lblcantidad_filtro.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblcantidad_filtro.setForeground(new java.awt.Color(0, 0, 204));
        lblcantidad_filtro.setText("jLabel12");

        jCfecha_todos.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCfecha_todos.setText("FACHA TODOS");
        jCfecha_todos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCfecha_todosActionPerformed(evt);
            }
        });

        btnpasar_venta_efectivo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnpasar_venta_efectivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/venta/efectivo.png"))); // NOI18N
        btnpasar_venta_efectivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnpasar_venta_efectivoActionPerformed(evt);
            }
        });

        btnpasar_venta_tarjeta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/venta/credito.png"))); // NOI18N
        btnpasar_venta_tarjeta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnpasar_venta_tarjetaActionPerformed(evt);
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
                            .addGroup(panel_referencia_ventaLayout.createSequentialGroup()
                                .addComponent(panel_tabla_item, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(118, 118, 118)
                                .addComponent(panel_tabla_entregador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panel_referencia_ventaLayout.createSequentialGroup()
                                .addComponent(btnanularventa, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnimprimirNota, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnfacturar)
                                .addGap(115, 115, 115)
                                .addGroup(panel_referencia_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panel_referencia_ventaLayout.createSequentialGroup()
                                        .addComponent(jLabel10)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtbuscar_idventa, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel11)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtbuscar_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(lblcantidad_filtro))
                                    .addGroup(panel_referencia_ventaLayout.createSequentialGroup()
                                        .addComponent(jCestado_emitido)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jCestado_terminado)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jCestado_anulado)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jCfecha_todos)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnpasar_venta_efectivo, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnpasar_venta_tarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(246, Short.MAX_VALUE))))
        );
        panel_referencia_ventaLayout.setVerticalGroup(
            panel_referencia_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_referencia_ventaLayout.createSequentialGroup()
                .addComponent(panel_tabla_venta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_referencia_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_referencia_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(btnanularventa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnimprimirNota, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)
                        .addComponent(btnfacturar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panel_referencia_ventaLayout.createSequentialGroup()
                        .addGroup(panel_referencia_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(txtbuscar_idventa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)
                            .addComponent(txtbuscar_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblcantidad_filtro))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel_referencia_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jCestado_emitido)
                            .addComponent(jCestado_terminado)
                            .addComponent(jCestado_anulado)
                            .addComponent(jCfecha_todos)))
                    .addGroup(panel_referencia_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                        .addComponent(btnpasar_venta_tarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnpasar_venta_efectivo, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_referencia_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panel_tabla_item, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel_tabla_entregador, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        jTabbedPane_VENTA.addTab("FILTRO VENTA", panel_referencia_venta);

        panel_insertar_sec_cliente.setBackground(new java.awt.Color(153, 204, 255));
        panel_insertar_sec_cliente.setBorder(javax.swing.BorderFactory.createTitledBorder("CREAR DATO"));

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel16.setText("Tipo:");

        gru_tipocli.add(jRtipo_cliente);
        jRtipo_cliente.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jRtipo_cliente.setSelected(true);
        jRtipo_cliente.setText("CLIENTE");
        jRtipo_cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRtipo_clienteActionPerformed(evt);
            }
        });

        gru_tipocli.add(jRtipo_funcionario);
        jRtipo_funcionario.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jRtipo_funcionario.setText("FUNCIONARIO");
        jRtipo_funcionario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRtipo_funcionarioActionPerformed(evt);
            }
        });

        txtcli_direccion.setColumns(20);
        txtcli_direccion.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        txtcli_direccion.setRows(5);
        txtcli_direccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcli_direccionKeyPressed(evt);
            }
        });
        jScrollPane5.setViewportView(txtcli_direccion);

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setText("Direccion:");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setText("Ruc:");

        txtcli_ruc.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtcli_ruc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcli_rucKeyPressed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel15.setText("Telefono:");

        txtcli_telefono.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtcli_telefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcli_telefonoKeyPressed(evt);
            }
        });

        txtcli_nombre.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtcli_nombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcli_nombreKeyPressed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel17.setText("Nombre:");

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel18.setText("ID:");

        txtcli_idcliente.setEditable(false);
        txtcli_idcliente.setBackground(new java.awt.Color(204, 204, 204));
        txtcli_idcliente.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel19.setText("Fecha Inicio:");

        txtcli_fecha_inicio.setEditable(false);
        txtcli_fecha_inicio.setBackground(new java.awt.Color(204, 204, 204));
        txtcli_fecha_inicio.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        lblfecnac.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblfecnac.setText("Fec. Nac.:");

        txtcli_fecha_nacimiento.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtcli_fecha_nacimiento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcli_fecha_nacimientoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtcli_fecha_nacimientoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtcli_fecha_nacimientoKeyTyped(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel20.setText("año-mes-dia");

        jLayeredPane2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLzona.setBackground(new java.awt.Color(204, 204, 255));
        jLzona.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLzona.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
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
        jLayeredPane2.add(jLzona, new org.netbeans.lib.awtextra.AbsoluteConstraints(91, 70, 330, 90));

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel21.setText("ZONA:");
        jLayeredPane2.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 14, -1, -1));

        txtcli_zona.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtcli_zona.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcli_zonaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtcli_zonaKeyReleased(evt);
            }
        });
        jLayeredPane2.add(txtcli_zona, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, 327, -1));

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel22.setText("DELIVERY:");
        jLayeredPane2.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        txtcli_delivery.setEditable(false);
        txtcli_delivery.setBackground(new java.awt.Color(204, 204, 204));
        txtcli_delivery.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLayeredPane2.add(txtcli_delivery, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 40, 147, -1));

        btnnuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/nuevo.png"))); // NOI18N
        btnnuevo.setText("NUEVO");
        btnnuevo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnnuevo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnnuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevoActionPerformed(evt);
            }
        });
        jLayeredPane2.add(btnnuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 120, -1, -1));

        btnguardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/guardar.png"))); // NOI18N
        btnguardar.setText("GUARDAR");
        btnguardar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnguardar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnguardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguardarActionPerformed(evt);
            }
        });
        jLayeredPane2.add(btnguardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 120, -1, -1));

        btneditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/modificar.png"))); // NOI18N
        btneditar.setText("EDITAR");
        btneditar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btneditar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btneditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneditarActionPerformed(evt);
            }
        });
        jLayeredPane2.add(btneditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 120, -1, -1));

        btndeletar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/eliminar.png"))); // NOI18N
        btndeletar.setText("DELETAR");
        btndeletar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btndeletar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jLayeredPane2.add(btndeletar, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 120, -1, -1));

        btnnuevazona.setText("NUEVA ZONA");
        btnnuevazona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevazonaActionPerformed(evt);
            }
        });
        jLayeredPane2.add(btnnuevazona, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 70, -1, -1));

        btnlimpiarzona.setText("LIMPIAR ZONA");
        btnlimpiarzona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnlimpiarzonaActionPerformed(evt);
            }
        });
        jLayeredPane2.add(btnlimpiarzona, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 70, -1, -1));

        javax.swing.GroupLayout panel_insertar_sec_clienteLayout = new javax.swing.GroupLayout(panel_insertar_sec_cliente);
        panel_insertar_sec_cliente.setLayout(panel_insertar_sec_clienteLayout);
        panel_insertar_sec_clienteLayout.setHorizontalGroup(
            panel_insertar_sec_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertar_sec_clienteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_insertar_sec_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_sec_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panel_insertar_sec_clienteLayout.createSequentialGroup()
                        .addComponent(txtcli_idcliente, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtcli_fecha_inicio, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_insertar_sec_clienteLayout.createSequentialGroup()
                        .addComponent(txtcli_ruc, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtcli_telefono, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))
                    .addComponent(txtcli_nombre)
                    .addComponent(jScrollPane5)))
            .addGroup(panel_insertar_sec_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel_insertar_sec_clienteLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLayeredPane2))
                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel_insertar_sec_clienteLayout.createSequentialGroup()
                    .addGap(33, 33, 33)
                    .addComponent(jLabel16)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jRtipo_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(14, 14, 14)
                    .addComponent(jRtipo_funcionario)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(lblfecnac)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(txtcli_fecha_nacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jLabel20)))
        );
        panel_insertar_sec_clienteLayout.setVerticalGroup(
            panel_insertar_sec_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertar_sec_clienteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_insertar_sec_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel18)
                    .addComponent(txtcli_idcliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19)
                    .addComponent(txtcli_fecha_inicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_sec_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel17)
                    .addComponent(txtcli_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_sec_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel14)
                    .addComponent(txtcli_ruc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(txtcli_telefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_sec_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_sec_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jRtipo_cliente)
                    .addComponent(jRtipo_funcionario)
                    .addComponent(lblfecnac)
                    .addComponent(txtcli_fecha_nacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLayeredPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panel_tabla_cliente.setBackground(new java.awt.Color(51, 204, 255));
        panel_tabla_cliente.setBorder(javax.swing.BorderFactory.createTitledBorder("TABLA"));

        tblpro_cliente.setModel(new javax.swing.table.DefaultTableModel(
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
        tblpro_cliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblpro_clienteMouseReleased(evt);
            }
        });
        jScrollPane6.setViewportView(tblpro_cliente);

        jLabel23.setText("NOMBRE:");

        txtbuscar_nombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscar_nombreKeyReleased(evt);
            }
        });

        txtbuscar_telefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscar_telefonoKeyReleased(evt);
            }
        });

        jLabel24.setText("TELEFONO:");

        txtbuscar_ruc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscar_rucKeyReleased(evt);
            }
        });

        jLabel25.setText("RUC:");

        javax.swing.GroupLayout panel_tabla_clienteLayout = new javax.swing.GroupLayout(panel_tabla_cliente);
        panel_tabla_cliente.setLayout(panel_tabla_clienteLayout);
        panel_tabla_clienteLayout.setHorizontalGroup(
            panel_tabla_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_tabla_clienteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_tabla_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_tabla_clienteLayout.createSequentialGroup()
                        .addComponent(jLabel23)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtbuscar_nombre))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_tabla_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtbuscar_telefono, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_tabla_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtbuscar_ruc, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25))
                .addGap(1, 1, 1))
            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 762, Short.MAX_VALUE)
        );
        panel_tabla_clienteLayout.setVerticalGroup(
            panel_tabla_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_tabla_clienteLayout.createSequentialGroup()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 517, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_tabla_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(jLabel24)
                    .addComponent(jLabel25))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_tabla_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtbuscar_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtbuscar_telefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtbuscar_ruc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout panel_base_2Layout = new javax.swing.GroupLayout(panel_base_2);
        panel_base_2.setLayout(panel_base_2Layout);
        panel_base_2Layout.setHorizontalGroup(
            panel_base_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_base_2Layout.createSequentialGroup()
                .addComponent(panel_insertar_sec_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_tabla_cliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_base_2Layout.setVerticalGroup(
            panel_base_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_base_2Layout.createSequentialGroup()
                .addGroup(panel_base_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panel_insertar_sec_cliente, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel_tabla_cliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane_VENTA.addTab("CREAR CLIENTE", panel_base_2);

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
                        .addContainerGap(376, Short.MAX_VALUE))))
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
            .addComponent(jTabbedPane_VENTA)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        ancho_item_producto();
        ancho_tabla_producto();
        vdao.ancho_tabla_venta(tblventa);
        cdao.ancho_tabla_cliente(tblpro_cliente);
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

    private void btnagregar_deliveryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnagregar_deliveryActionPerformed
        // TODO add your handling code here:
        boton_tipo_entrega_Delivery();
    }//GEN-LAST:event_btnagregar_deliveryActionPerformed

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

    private void btnagregar_paqueteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnagregar_paqueteActionPerformed
        // TODO add your handling code here:
        boton_tipo_entrega_Paquete();
    }//GEN-LAST:event_btnagregar_paqueteActionPerformed

    private void btnanularventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnanularventaActionPerformed
        // TODO add your handling code here:
        boton_estado_venta_anular();
    }//GEN-LAST:event_btnanularventaActionPerformed

    private void btnfacturarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnfacturarActionPerformed
        // TODO add your handling code here:
        boton_factura();

    }//GEN-LAST:event_btnfacturarActionPerformed

    private void btnimprimirNotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnimprimirNotaActionPerformed
        // TODO add your handling code here:
        boton_imprimirPos_venta();
    }//GEN-LAST:event_btnimprimirNotaActionPerformed

    private void jCestado_emitidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCestado_emitidoActionPerformed
        // TODO add your handling code here:
        actualizar_venta(3);
    }//GEN-LAST:event_jCestado_emitidoActionPerformed

    private void jCestado_terminadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCestado_terminadoActionPerformed
        // TODO add your handling code here:
        actualizar_venta(3);
    }//GEN-LAST:event_jCestado_terminadoActionPerformed

    private void jCestado_anuladoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCestado_anuladoActionPerformed
        // TODO add your handling code here:
        actualizar_venta(3);
    }//GEN-LAST:event_jCestado_anuladoActionPerformed

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

    private void jCfecha_todosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCfecha_todosActionPerformed
        // TODO add your handling code here:
        actualizar_venta(3);
    }//GEN-LAST:event_jCfecha_todosActionPerformed

    private void jRtipo_clienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRtipo_clienteActionPerformed
        // TODO add your handling code here:
        //        jCdelivery.setSelected(true);
    }//GEN-LAST:event_jRtipo_clienteActionPerformed

    private void jRtipo_funcionarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRtipo_funcionarioActionPerformed
        // TODO add your handling code here:
        //        jCdelivery.setSelected(false);
    }//GEN-LAST:event_jRtipo_funcionarioActionPerformed

    private void txtcli_direccionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcli_direccionKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcli_direccionKeyPressed

    private void txtcli_rucKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcli_rucKeyPressed
        // TODO add your handling code here:
        //        pasarCampoEnter(evt, txtruc, txttelefono);
        evejtf.saltar_campo_enter(evt, txtcli_ruc, txtcli_telefono);
    }//GEN-LAST:event_txtcli_rucKeyPressed

    private void txtcli_telefonoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcli_telefonoKeyPressed
        // TODO add your handling code here:
        //        pasarCampoEnter2(evt, txttelefono, txtdireccion);
        evejtf.saltar_campo_enter(evt, txtcli_telefono, txtcli_fecha_nacimiento);
    }//GEN-LAST:event_txtcli_telefonoKeyPressed

    private void txtcli_nombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcli_nombreKeyPressed
        // TODO add your handling code here:
        //        pasarCampoEnter(evt, txtnombre, txtruc);
        evejtf.saltar_campo_enter(evt, txtcli_nombre, txtcli_ruc);
    }//GEN-LAST:event_txtcli_nombreKeyPressed

    private void txtcli_fecha_nacimientoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcli_fecha_nacimientoKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtcli_fecha_nacimiento.setText(evefec.getString_validar_fecha(txtcli_fecha_nacimiento.getText()));
            evejtf.saltar_campo_enter(evt, txtcli_fecha_nacimiento, txtcli_zona);
        }

    }//GEN-LAST:event_txtcli_fecha_nacimientoKeyPressed

    private void txtcli_fecha_nacimientoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcli_fecha_nacimientoKeyReleased
        // TODO add your handling code here:
        evejtf.verificar_fecha(evt, txtcli_fecha_nacimiento);
    }//GEN-LAST:event_txtcli_fecha_nacimientoKeyReleased

    private void txtcli_fecha_nacimientoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcli_fecha_nacimientoKeyTyped
        // TODO add your handling code here:
        //        fo.soloFechaText(evt);
    }//GEN-LAST:event_txtcli_fecha_nacimientoKeyTyped

    private void jLzonaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLzonaMouseReleased
        // TODO add your handling code here:
        cargar_zona_cliente();
    }//GEN-LAST:event_jLzonaMouseReleased

    private void jLzonaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jLzonaKeyReleased
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            cargar_zona_cliente();
        }
    }//GEN-LAST:event_jLzonaKeyReleased

    private void txtcli_zonaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcli_zonaKeyPressed
        // TODO add your handling code here:
        evejtf.seleccionar_lista(evt, jLzona);
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtcli_zona.setBackground(Color.WHITE);
            txtcli_direccion.setBackground(Color.YELLOW);
            txtcli_direccion.grabFocus();
        }
    }//GEN-LAST:event_txtcli_zonaKeyPressed

    private void txtcli_zonaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcli_zonaKeyReleased
        // TODO add your handling code here:
        eveconn.buscar_cargar_Jlista(connLocal, txtcli_zona, jLzona, zona.getTabla(), zona.getNombretabla(), zona.getZona_mostrar(), 10);
    }//GEN-LAST:event_txtcli_zonaKeyReleased

    private void btnnuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevoActionPerformed
        // TODO add your handling code here:
        boton_nuevo_cliente();
    }//GEN-LAST:event_btnnuevoActionPerformed

    private void btnguardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardarActionPerformed
        // TODO add your handling code here:
        boton_guardar_cliente();
    }//GEN-LAST:event_btnguardarActionPerformed

    private void btneditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneditarActionPerformed
        // TODO add your handling code here:
        boton_editar_cliente();
    }//GEN-LAST:event_btneditarActionPerformed

    private void tblpro_clienteMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblpro_clienteMouseReleased
        // TODO add your handling code here:
        seleccionar_tabla_cliente();
    }//GEN-LAST:event_tblpro_clienteMouseReleased

    private void txtbuscar_nombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_nombreKeyReleased
        // TODO add your handling code here:
        cdao.buscar_tabla_cliente(connLocal, tblpro_cliente, txtbuscar_nombre, 1);
    }//GEN-LAST:event_txtbuscar_nombreKeyReleased

    private void txtbuscar_telefonoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_telefonoKeyReleased
        // TODO add your handling code here:
        cdao.buscar_tabla_cliente(connLocal, tblpro_cliente, txtbuscar_telefono, 2);
    }//GEN-LAST:event_txtbuscar_telefonoKeyReleased

    private void txtbuscar_rucKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_rucKeyReleased
        // TODO add your handling code here:
        cdao.buscar_tabla_cliente(connLocal, tblpro_cliente, txtbuscar_ruc, 3);
    }//GEN-LAST:event_txtbuscar_rucKeyReleased

    private void btnnuevo_clienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevo_clienteActionPerformed
        // TODO add your handling code here:
        evejt.mostrar_JTabbedPane(jTabbedPane_VENTA, 2);
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
        seleccionar_tabla_venta();
    }//GEN-LAST:event_tblventaMouseReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        reestableser_venta();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        // TODO add your handling code here:
        ven.setVenta_aux(false);
    }//GEN-LAST:event_formInternalFrameClosing

    private void btnnuevazonaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevazonaActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmZonaDelivery());
    }//GEN-LAST:event_btnnuevazonaActionPerformed

    private void btnlimpiarzonaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnlimpiarzonaActionPerformed
        // TODO add your handling code here:
        txtcli_zona.setText(null);
        txtcli_delivery.setText(null);
        txtcli_zona.grabFocus();
    }//GEN-LAST:event_btnlimpiarzonaActionPerformed

    private void tblentregadorMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblentregadorMouseReleased
        // TODO add your handling code here:
//        SELECCIONAR_cambiar_entregador();
    }//GEN-LAST:event_tblentregadorMouseReleased

    private void txtcantidad_productoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcantidad_productoKeyReleased
        // TODO add your handling code here:
        calcular_subtotal_itemventa();
//        calculo_cantidad(evt);
    }//GEN-LAST:event_txtcantidad_productoKeyReleased

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

    private void txtcantidad_productoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcantidad_productoKeyPressed
        // TODO add your handling code here:
        calculo_cantidad(evt);
    }//GEN-LAST:event_txtcantidad_productoKeyPressed

    private void btnconfirmar_venta_tarjetaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnconfirmar_venta_tarjetaActionPerformed
        // TODO add your handling code here:
        boton_venta_tarjeta();
    }//GEN-LAST:event_btnconfirmar_venta_tarjetaActionPerformed

    private void txtredondeoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtredondeoKeyReleased
        // TODO add your handling code here:
//        evejtf.getString_format_nro_entero(txtredondeo);
        validar_redondeo();
    }//GEN-LAST:event_txtredondeoKeyReleased

    private void txtredondeoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtredondeoKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txtredondeoKeyTyped

    private void txtcantidad_productoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcantidad_productoKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txtcantidad_productoKeyTyped

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
            txtcantidad_producto.grabFocus();
        }
    }//GEN-LAST:event_txtprecio_ventaKeyPressed

    private void btnpasar_venta_efectivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnpasar_venta_efectivoActionPerformed
        // TODO add your handling code here:
        boton_forma_pago_EFECTIVO();
    }//GEN-LAST:event_btnpasar_venta_efectivoActionPerformed

    private void btnpasar_venta_tarjetaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnpasar_venta_tarjetaActionPerformed
        // TODO add your handling code here:
        boton_forma_pago_TARJETA();
    }//GEN-LAST:event_btnpasar_venta_tarjetaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnagregar_cantidad;
    private javax.swing.JButton btnagregar_delivery;
    private javax.swing.JButton btnagregar_paquete;
    private javax.swing.JButton btnanularventa;
    private javax.swing.JButton btnbuscar_cliente;
    private javax.swing.JButton btnconfirmar_venta_efectivo;
    private javax.swing.JButton btnconfirmar_venta_tarjeta;
    private javax.swing.JButton btndeletar;
    private javax.swing.JButton btneditar;
    private javax.swing.JButton btneliminar_item;
    private javax.swing.JButton btnfacturar;
    private javax.swing.JButton btnguardar;
    private javax.swing.JButton btnimprimirNota;
    private javax.swing.JButton btnlimpiar_cliente;
    private javax.swing.JButton btnlimpiarzona;
    private javax.swing.JButton btnnuevazona;
    private javax.swing.JButton btnnuevo;
    private javax.swing.JButton btnnuevoCliente;
    private javax.swing.JButton btnnuevo_cliente;
    private javax.swing.JButton btnpasar_venta_efectivo;
    private javax.swing.JButton btnpasar_venta_tarjeta;
    private javax.swing.JButton btnseleccionarCerrar;
    private javax.swing.ButtonGroup gru_campo;
    private javax.swing.ButtonGroup gru_tipocli;
    private javax.swing.JButton jButton1;
    private javax.swing.JCheckBox jCestado_anulado;
    private javax.swing.JCheckBox jCestado_emitido;
    private javax.swing.JCheckBox jCestado_terminado;
    private javax.swing.JCheckBox jCfecha_todos;
    private javax.swing.JCheckBox jCfuncionario;
    private javax.swing.JCheckBox jCimprimir_ticket;
    private javax.swing.JCheckBox jCvuelto;
    private javax.swing.JFormattedTextField jFtotal_dolar;
    private javax.swing.JFormattedTextField jFtotal_guarani;
    private javax.swing.JFormattedTextField jFtotal_real;
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
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLayeredPane jLayeredPane2;
    private javax.swing.JList<String> jList_cliente;
    private javax.swing.JList<String> jLzona;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JRadioButton jRtipo_cliente;
    private javax.swing.JRadioButton jRtipo_funcionario;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTabbedPane jTab_producto_ingrediente;
    private javax.swing.JTabbedPane jTabbedPane_VENTA;
    private javax.swing.JLabel lblcantidad_filtro;
    private javax.swing.JLabel lblfecnac;
    private javax.swing.JLabel lblidcliente;
    private javax.swing.JLabel lblmensaje;
    private javax.swing.JLabel lbltipocliente;
    private javax.swing.JPanel panel_base_1;
    private javax.swing.JPanel panel_base_2;
    private javax.swing.JPanel panel_insertar_pri_item;
    private javax.swing.JPanel panel_insertar_pri_producto;
    private javax.swing.JPanel panel_insertar_sec_cliente;
    private javax.swing.JPanel panel_referencia_categoria;
    private javax.swing.JPanel panel_referencia_marca;
    private javax.swing.JPanel panel_referencia_unidad;
    private javax.swing.JPanel panel_referencia_venta;
    private javax.swing.JPanel panel_tabla_busca_cli;
    private javax.swing.JPanel panel_tabla_cliente;
    private javax.swing.JPanel panel_tabla_entregador;
    private javax.swing.JPanel panel_tabla_item;
    private javax.swing.JPanel panel_tabla_venta;
    private javax.swing.JTable tblbuscar_cliente;
    private javax.swing.JTable tblentregador;
    private javax.swing.JTable tblitem_producto;
    private javax.swing.JTable tblitem_venta_filtro;
    private javax.swing.JTable tblpro_cliente;
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
    private javax.swing.JTextField txtbuscar_nombre;
    private javax.swing.JTextField txtbuscar_producto;
    private javax.swing.JTextField txtbuscar_ruc;
    private javax.swing.JTextField txtbuscar_telefono;
    private javax.swing.JTextField txtcantidad_producto;
    public static javax.swing.JTextField txtcli_delivery;
    private javax.swing.JTextArea txtcli_direccion;
    private javax.swing.JTextField txtcli_fecha_inicio;
    private javax.swing.JTextField txtcli_fecha_nacimiento;
    private javax.swing.JTextField txtcli_idcliente;
    private javax.swing.JTextField txtcli_nombre;
    private javax.swing.JTextField txtcli_ruc;
    private javax.swing.JTextField txtcli_telefono;
    public static javax.swing.JTextField txtcli_zona;
    private javax.swing.JTextField txtcod_barra;
    private javax.swing.JTextField txtidventa;
    private javax.swing.JTextField txtobservacion;
    private javax.swing.JTextField txtprecio_venta;
    private javax.swing.JTextField txtredondeo;
    private javax.swing.JTextField txtstock;
    private javax.swing.JTextField txtsubtotal;
    // End of variables declaration//GEN-END:variables
}
