package com.proyecto.service;

import java.util.List;

import com.proyecto.entity.Producto;

public interface ProductoService {

	List<Producto> listaProdutos();

	void guardar(Producto producto);

	List<Producto> listaProductoPorCodigo(String codigo);

	List<Producto> listaProductoPorNombre(String nombre);

}
