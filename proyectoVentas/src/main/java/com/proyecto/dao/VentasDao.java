package com.proyecto.dao;

import com.proyecto.entity.Venta;
import com.proyecto.entity.VentaProducto;

public interface VentasDao {

	void guardar(Venta venta);

	void guardarVentaProducto(VentaProducto venta);

}
