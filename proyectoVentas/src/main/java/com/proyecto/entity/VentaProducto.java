package com.proyecto.entity;

import java.io.Serializable;

public class VentaProducto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1930101731411305888L;
	private Integer id;
	private Producto producto;
	private Venta venta;
	private Integer cantidad;
	private Integer precio;

	public Integer getId() {
		return id;
	}

	public Producto getProducto() {
		return producto;
	}

	public Venta getVenta() {
		return venta;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public Integer getPrecio() {
		return precio;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public void setVenta(Venta venta) {
		this.venta = venta;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public void setPrecio(Integer precio) {
		this.precio = precio;
	}

}
