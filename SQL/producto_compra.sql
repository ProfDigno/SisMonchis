select p.idproducto as id,(c.nombre||'-'||u.nombre||'-'||p.nombre) as categoria_unid_nombre,
case when ((select avg(iv.precio_compra)  
from item_compra iv,compra v,producto p2  
where  iv.fk_idcompra=v.idcompra 
and (v.estado='EMITIDO') 
and date(v.fecha_emision) >= '2021-03-01' and date(v.fecha_emision) <= '2021-03-22' 
and iv.fk_idproducto=p2.idproducto             
and p2.idproducto=p.idproducto)>0) 
    then (select avg(iv.precio_compra)  
from item_compra iv,compra v,producto p2  
where  iv.fk_idcompra=v.idcompra 
and (v.estado='EMITIDO') 
and date(v.fecha_emision) >= '2021-03-01' and date(v.fecha_emision) <= '2021-03-22' 
and iv.fk_idproducto=p2.idproducto             
and p2.idproducto=p.idproducto) else '0' end as pre_com,
case when ((select sum(iv.cantidad)  
from item_compra iv,compra v,producto p2 
where  iv.fk_idcompra=v.idcompra 
and (v.estado='EMITIDO') 
and date(v.fecha_emision) >= '2021-03-01' and date(v.fecha_emision) <= '2021-03-22' 
and iv.fk_idproducto=p2.idproducto             
and p2.idproducto=p.idproducto)>0) 
     then (select sum(iv.cantidad)  
from item_compra iv,compra v,producto p2  
where  iv.fk_idcompra=v.idcompra 
and (v.estado='EMITIDO') 
and date(v.fecha_emision) >= '2021-03-01' and date(v.fecha_emision) <= '2021-03-22' 
and iv.fk_idproducto=p2.idproducto             
and p2.idproducto=p.idproducto) else 0 end as cant,
case when ((select sum(iv.cantidad*iv.precio_compra)  
from item_compra iv,compra v,producto p2 
where  iv.fk_idcompra=v.idcompra 
and (v.estado='EMITIDO') 
and date(v.fecha_emision) >= '2021-03-01' and date(v.fecha_emision) <= '2021-03-23' 
and iv.fk_idproducto=p2.idproducto             
and p2.idproducto=p.idproducto)>0) 
     then (select sum(iv.cantidad*iv.precio_compra)  
from item_compra iv,compra v,producto p2 
where  iv.fk_idcompra=v.idcompra 
and (v.estado='EMITIDO') 
and date(v.fecha_emision) >= '2021-03-01' and date(v.fecha_emision) <= '2021-03-23' 
and iv.fk_idproducto=p2.idproducto             
and p2.idproducto=p.idproducto) else 0 end as total,
('('||c.idproducto_categoria||')-'||c.nombre) as categoria,
case when  ((select sum(iv.cantidad) as cant 
from item_compra iv,compra v,producto p 
where  iv.fk_idcompra=v.idcompra
and (v.estado='EMITIDO')
and date(v.fecha_emision) >= '2021-03-01' and date(v.fecha_emision) <= '2021-03-23' 
and iv.fk_idproducto=p.idproducto
and p.fk_idproducto_categoria=c.idproducto_categoria)>0) 
then (select sum(iv.cantidad) as cant 
from item_compra iv,compra v,producto p 
where  iv.fk_idcompra=v.idcompra
and (v.estado='EMITIDO')
and date(v.fecha_emision) >= '2021-03-01' and date(v.fecha_emision) <= '2021-03-23' 
and iv.fk_idproducto=p.idproducto
and p.fk_idproducto_categoria=c.idproducto_categoria) else 0 end as cant_com,
case when ((select sum(iv.cantidad*iv.precio_compra) as cant 
from item_compra iv,compra v,producto p 
where  iv.fk_idcompra=v.idcompra
and (v.estado='EMITIDO')
and date(v.fecha_emision) >= '2021-03-01' and date(v.fecha_emision) <= '2021-03-22' 
and iv.fk_idproducto=p.idproducto
and p.fk_idproducto_categoria=c.idproducto_categoria)>0)
then  (select sum(iv.cantidad*iv.precio_compra) as cant 
from item_compra iv,compra v,producto p 
where  iv.fk_idcompra=v.idcompra
and (v.estado='EMITIDO')
and date(v.fecha_emision) >= '2021-03-01' and date(v.fecha_emision) <= '2021-03-22' 
and iv.fk_idproducto=p.idproducto
and p.fk_idproducto_categoria=c.idproducto_categoria) else 0 end as total_com,
('Fecha Desde:2021-03-01 Hasta:2021-03-22') as fecha 
from producto p,producto_categoria c,producto_unidad u 
where  c.idproducto_categoria=p.fk_idproducto_categoria 
and u.idproducto_unidad=p.fk_idproducto_unidad 
and p.comprar=true
 --and c.idproducto_categoria=1
order by c.idproducto_categoria,4 desc; 