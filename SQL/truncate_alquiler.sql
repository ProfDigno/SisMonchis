truncate caja_detalle_alquilado;
truncate credito_cliente;
truncate grupo_credito_cliente;
truncate item_caja_cierre_alquilado;
truncate item_venta_alquiler;
truncate recibo_pago_cliente;
truncate saldo_credito_cliente;
truncate venta_alquiler;
update cliente set saldo_credito=0,dia_limite_credito=30;