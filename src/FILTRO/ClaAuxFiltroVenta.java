/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FILTRO;

import javax.swing.JCheckBox;

/**
 *
 * @author Digno
 */
public class ClaAuxFiltroVenta {
    public String filtro_estado(JCheckBox jCestado_emitido,JCheckBox jCestado_terminado,JCheckBox jCestado_anulado) {
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
            estado = condi + " v.estado='EMITIDO' ";
            sumaestado = sumaestado + estado;
        }
        if (jCestado_terminado.isSelected()) {
            contestado++;
            if (contestado == 1) {
                condi = " and(";
                fin = ") ";
            } else {
                condi = " or";
            }
            estado = condi + " v.estado='TERMINADO' ";
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
            estado = condi + " (v.estado='ANULADO' or v.estado='ANULADO_temp') ";
            sumaestado = sumaestado + estado;
        }
        return sumaestado + fin;
    }
    public String filtro_estado_alquiler(JCheckBox jCestado_emitido,JCheckBox jCestado_finalizado,
            JCheckBox jCestado_usoreserva,JCheckBox jCestado_alquilado,
            JCheckBox jCestado_devolucion,JCheckBox jCestado_anulado) {
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
            estado = condi + " v.estado='EMITIDO' ";
            sumaestado = sumaestado + estado;
        }
        if (jCestado_finalizado.isSelected()) {
            contestado++;
            if (contestado == 1) {
                condi = " and(";
                fin = ") ";
            } else {
                condi = " or";
            }
            estado = condi + " v.estado='FINALIZADO' ";
            sumaestado = sumaestado + estado;
        }
        if (jCestado_usoreserva.isSelected()) {
            contestado++;
            if (contestado == 1) {
                condi = " and(";
                fin = ") ";
            } else {
                condi = " or";
            }
            estado = condi + " v.estado='USO_RESERVA' ";
            sumaestado = sumaestado + estado;
        }
        if (jCestado_alquilado.isSelected()) {
            contestado++;
            if (contestado == 1) {
                condi = " and(";
                fin = ") ";
            } else {
                condi = " or";
            }
            estado = condi + " v.estado='ALQUILADO' ";
            sumaestado = sumaestado + estado;
        }
        if (jCestado_devolucion.isSelected()) {
            contestado++;
            if (contestado == 1) {
                condi = " and(";
                fin = ") ";
            } else {
                condi = " or";
            }
            estado = condi + " v.estado='DEVOLUCION' ";
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
            estado = condi + " v.estado='ANULADO' ";
            sumaestado = sumaestado + estado;
        }
        return sumaestado + fin;
    }
    public String forma_pago(JCheckBox jCpago_efectivo,JCheckBox jCpago_tarjeta,JCheckBox jCpago_combinado) {
        String estado = "";
        String sumaestado = "";
        int contestado = 0;
        String condi = "";
        String fin = "";
        if (jCpago_efectivo.isSelected()) {
            contestado++;
            if (contestado == 1) {
                condi = " and(";
                fin = ") ";
            } else {
                condi = " or";
            }
            estado = condi + " v.forma_pago='EFECTIVO' ";
            sumaestado = sumaestado + estado;
        }
        if (jCpago_tarjeta.isSelected()) {
            contestado++;
            if (contestado == 1) {
                condi = " and(";
                fin = ") ";
            } else {
                condi = " or";
            }
            estado = condi + " v.forma_pago='TARJETA' ";
            sumaestado = sumaestado + estado;
        }
        if (jCpago_combinado.isSelected()) {
            contestado++;
            if (contestado == 1) {
                condi = " and(";
                fin = ") ";
            } else {
                condi = " or";
            }
            estado = condi + " v.forma_pago='COMBINADO' ";
            sumaestado = sumaestado + estado;
        }
        return sumaestado + fin;
    }
    public String forma_pago_alquilado(JCheckBox jCpago_efectivo,JCheckBox jCpago_tarjeta,JCheckBox jCpago_transferencia,JCheckBox jCpago_credito,JCheckBox jCpago_combinado) {
        String estado = "";
        String sumaestado = "";
        int contestado = 0;
        String condi = "";
        String fin = "";
        if (jCpago_efectivo.isSelected()) {
            contestado++;
            if (contestado == 1) {
                condi = " and(";
                fin = ") ";
            } else {
                condi = " or";
            }
            estado = condi + " v.forma_pago='EFECTIVO' ";
            sumaestado = sumaestado + estado;
        }
        if (jCpago_tarjeta.isSelected()) {
            contestado++;
            if (contestado == 1) {
                condi = " and(";
                fin = ") ";
            } else {
                condi = " or";
            }
            estado = condi + " v.forma_pago='TARJETA' ";
            sumaestado = sumaestado + estado;
        }
        if (jCpago_transferencia.isSelected()) {
            contestado++;
            if (contestado == 1) {
                condi = " and(";
                fin = ") ";
            } else {
                condi = " or";
            }
            estado = condi + " v.forma_pago='TRANSFERENCIA' ";
            sumaestado = sumaestado + estado;
        }
        if (jCpago_credito.isSelected()) {
            contestado++;
            if (contestado == 1) {
                condi = " and(";
                fin = ") ";
            } else {
                condi = " or";
            }
            estado = condi + " v.forma_pago='CREDITO' ";
            sumaestado = sumaestado + estado;
        }
        if (jCpago_combinado.isSelected()) {
            contestado++;
            if (contestado == 1) {
                condi = " and(";
                fin = ") ";
            } else {
                condi = " or";
            }
            estado = condi + " v.forma_pago='COMBINADO' ";
            sumaestado = sumaestado + estado;
        }
        return sumaestado + fin;
    }
        public String filtro_estado_compra(JCheckBox jCestado_emitido,JCheckBox jCestado_anulado) {
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
            estado = condi + " c.estado='EMITIDO' ";
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
            estado = condi + " (c.estado='ANULADO' or c.estado='ANULADO_temp') ";
            sumaestado = sumaestado + estado;
        }
        return sumaestado + fin;
    }
    public String filtro_tipocliente(JCheckBox jCtipo_cliente,JCheckBox jCtipo_funcionario) {
        String estado = "";
        String sumaestado = "";
        int contestado = 0;
        String condi = "";
        String fin = "";
        if (jCtipo_cliente.isSelected()) {
            contestado++;
            if (contestado == 1) {
                condi = " and(";
                fin = ") ";
            } else {
                condi = " or";
            }
            estado = condi + " c.tipo='cliente' ";
            sumaestado = sumaestado + estado;
        }
        if (jCtipo_funcionario.isSelected()) {
            contestado++;
            if (contestado == 1) {
                condi = " and(";
                fin = ") ";
            } else {
                condi = " or";
            }
            estado = condi + " c.tipo='funcionario' ";
            sumaestado = sumaestado + estado;
        }
        return sumaestado + fin;
    }
    public String filtro_grupo_producto(JCheckBox jCgrupo_0,JCheckBox jCgrupo_1,JCheckBox jCdelivery) {
        String estado = "";
        String sumaestado = "";
        int contestado = 0;
        String condi = "";
        String fin = "";
        if (jCgrupo_0.isSelected()) {
            contestado++;
            if (contestado == 1) {
                condi = " and(";
                fin = ") ";
            } else {
                condi = " or";
            }
            estado = condi + " iv.grupo=0 and iv.tipo!='D' ";
            sumaestado = sumaestado + estado;
        }
        if (jCgrupo_1.isSelected()) {
            contestado++;
            if (contestado == 1) {
                condi = " and(";
                fin = ") ";
            } else {
                condi = " or";
            }
            estado = condi + " iv.grupo=1 and iv.tipo!='D' ";
            sumaestado = sumaestado + estado;
        }
        if (jCdelivery.isSelected()) {
            contestado++;
            if (contestado == 1) {
                condi = " and(";
                fin = ") ";
            } else {
                condi = " or";
            }
            estado = condi + "  iv.tipo='D' ";
            sumaestado = sumaestado + estado;
        }
        return sumaestado + fin;
    }
}
