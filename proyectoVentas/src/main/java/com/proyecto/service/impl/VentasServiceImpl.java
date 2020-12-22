package com.proyecto.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.dao.VentasDao;
import com.proyecto.entity.Venta;
import com.proyecto.entity.VentaProducto;
import com.proyecto.service.VentasService;

@Service
public class VentasServiceImpl implements VentasService {
	
	
	@Autowired
	VentasDao ventasDao;
	
	@Transactional
	@Override
	public void guardar(Venta venta) {
		ventasDao.guardar(venta);
	}
	@Transactional
	@Override
	public void guardaVentaProducto(VentaProducto ventaProducto) {
		ventasDao.guardarVentaProducto(ventaProducto);
	}
}
