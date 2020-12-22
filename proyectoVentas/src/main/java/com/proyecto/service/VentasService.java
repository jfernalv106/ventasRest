package com.proyecto.service;

import com.proyecto.entity.Venta;
import com.proyecto.entity.VentaProducto;

public interface VentasService {

	void guardar(Venta venta);

	void guardaVentaProducto(VentaProducto ventaProducto);

}
