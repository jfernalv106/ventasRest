package com.proyecto.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.dao.ProductoDao;
import com.proyecto.entity.Producto;
import com.proyecto.service.ProductoService;

@Service
public class ProductoServiceImpl implements ProductoService{

	@Autowired
	ProductoDao productoDao;
	
	@Transactional
	@Override
	public void guardar(Producto producto) {
		productoDao.guardar(producto);
	}
	@Transactional
	@Override
	public List<Producto> listaProdutos() {	
		return productoDao.listaProdutos();
	}
	@Transactional
	@Override
	public List<Producto> listaProductoPorCodigo(String codigo) {
		return productoDao.listaProductoPorCodigo(codigo);
	}
	@Transactional
	@Override
	public List<Producto> listaProductoPorNombre(String nombre) {
		return productoDao.listaProductoPorNombre(nombre);
	}
}
