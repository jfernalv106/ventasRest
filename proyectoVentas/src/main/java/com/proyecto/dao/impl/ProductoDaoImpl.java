package com.proyecto.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.proyecto.dao.ProductoDao;
import com.proyecto.entity.Producto;

@Repository
public class ProductoDaoImpl implements ProductoDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void guardar(Producto producto) {
		sessionFactory.getCurrentSession().persist(producto);
	}
	@Override
	public List<Producto> listaProdutos() {
		String query = "FROM Producto as pr";
		return sessionFactory.getCurrentSession().createQuery(query, Producto.class).list();
	}
	@Override
	public List<Producto> listaProductoPorCodigo(String codigo) {
		String query = "FROM Producto as pr WHERE pr.codigo like :codigo";
		return sessionFactory.getCurrentSession().createQuery(query, Producto.class)
				.setParameter("codigo", codigo + "%").list();
	}
	@Override
	public List<Producto> listaProductoPorNombre(String nombre) {
		String query = "FROM Producto as pr WHERE pr.nombre like :codigo";
		return sessionFactory.getCurrentSession().createQuery(query, Producto.class)
				.setParameter("nombre", "%" + nombre + "%").list();
	}
}
