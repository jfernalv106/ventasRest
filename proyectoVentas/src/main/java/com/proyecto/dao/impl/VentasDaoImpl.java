package com.proyecto.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.proyecto.dao.VentasDao;
import com.proyecto.entity.Venta;
import com.proyecto.entity.VentaProducto;
@Repository
public class VentasDaoImpl implements VentasDao{
	
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void guardar(Venta venta) {
		sessionFactory.getCurrentSession().persist(venta);
	}
	@Override
	public void guardarVentaProducto(VentaProducto venta) {
		sessionFactory.getCurrentSession().persist(venta);
	}
	
}
