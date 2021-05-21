select v.idventa as idventa,to_char(v.fecha_inicio,'yyyy-MM-dd HH24:MI') as fecha,v.nombre_mesa as mesa,
c.ruc as ruc,('('||c.idcliente||')'||c.nombre) as cliente,c.tipo,
v.estado as estado,v.monto_venta as monto,v.monto_delivery as delivery,u.usuario as usuario
from venta v,cliente c,usuario u
where v.fk_idcliente=c.idcliente
and v.fk_idusuario=u.idusuario
--and c.idcliente=1
--and v.estado='TERMINADO'
--and c.tipo='cliente'
--and date(v.fecha_inicio)>'2021-02-21' and date(v.fecha_inicio)<'2021-02-24'
order by v.idventa desc;


select v.idventa as idventa,to_char(v.fecha_inicio,'yyyy-MM-dd HH24:MI') as fecha,v.nombre_mesa as mesa,v.estado as estado,
('('||c.idcliente||')'||c.nombre) as cliente,c.tipo,('('||iv.tipo||')'||iv.descripcion) producto,
iv.cantidad as cant,(iv.cantidad*iv.precio_venta) as monto,u.usuario as usuario
from venta v,cliente c,usuario u,item_venta iv
where v.fk_idcliente=c.idcliente
and v.fk_idusuario=u.idusuario
and v.idventa=iv.fk_idventa
--and iv.grupo=1
--and c.idcliente=1
--and v.estado='ANULADO_temp'
--and c.tipo='funcionario'
--and date(v.fecha_inicio)>'2021-02-21' and date(v.fecha_inicio)<'2021-02-24'
order by iv.iditem_venta asc;