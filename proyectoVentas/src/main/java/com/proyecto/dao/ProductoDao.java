package com.proyecto.dao;

import java.util.List;

import com.proyecto.entity.Producto;

public interface ProductoDao {

	void guardar(Producto producto);

	List<Producto> listaProdutos();

	List<Producto> listaProductoPorCodigo(String codigo);

	List<Producto> listaProductoPorNombre(String nombre);

}
