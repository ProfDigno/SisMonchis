select sum(iv.cantidad) as cant,pc.idproducto_categoria,pc.nombre 
from producto_categoria pc,producto p,item_venta iv,venta v
where p.fk_idproducto_categoria=pc.idproducto_categoria
and iv.fk_idproducto=p.idproducto
and iv.fk_idventa=v.idventa
and v.estado='EMITIDO'
group by 2,3 order by 1 desc;