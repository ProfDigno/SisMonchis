SELECT ii.iditem_inventario as idii,p.cod_barra,
(pm.nombre||'-'||pu.nombre||'-'||p.nombre) as marca_unid_nombre,
ii.stock_sistema as st_sis,ii.stock_contado as st_con,(ii.stock_contado-ii.stock_sistema) as st_dif,ii.estado,
case when (ii.stock_contado-ii.stock_sistema)>0 then 'P' 
when (ii.stock_contado-ii.stock_sistema)<0 then 'N' else '0' end  as tipo,
i.idinventario as idin,to_char(i.fecha_inicio,'yyyy-MM-dd HH24:MI') as inicio,
to_char(i.fecha_fin,'yyyy-MM-dd HH24:MI') as final,i.descripcion, 
i.total_precio_venta as total_venta,i.total_precio_compra as total_compra,i.estado
FROM producto p,producto_unidad pu,producto_categoria pc,
 producto_marca pm,item_inventario ii,inventario i 
where p.fk_idproducto_unidad=pu.idproducto_unidad
and p.fk_idproducto_categoria=pc.idproducto_categoria
and p.fk_idproducto_marca=pm.idproducto_marca
and i.idinventario=ii.fk_idinventario
and ii.fk_idproducto=p.idproducto
and (ii.estado='TEMP' or ii.estado='CARGA')
and i.idinventario=13 order by 1 desc;
