package com.proyecto.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.proyecto.config.AppConfig;
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = VentaProducto.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "rl_ventas_producto", schema = AppConfig.ECHEMA)
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
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_ventas_producto")
	@SequenceGenerator(name = "sequence_ventas_producto", sequenceName = "seq_ventas_producto", allocationSize = 1, schema = AppConfig.ECHEMA, catalog = AppConfig.CATALOGO_DB)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "producto", nullable = false)
	public Producto getProducto() {
		return producto;
	}
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "venta", nullable = false)
	public Venta getVenta() {
		return venta;
	}
	@Column(name = "cantidad")
	public Integer getCantidad() {
		return cantidad;
	}
	@Column(name = "precio")
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
