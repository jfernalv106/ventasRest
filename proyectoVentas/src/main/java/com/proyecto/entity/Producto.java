package com.proyecto.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.proyecto.config.AppConfig;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "codigo", scope = Producto.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "producto", schema = AppConfig.ECHEMA)
public class Producto implements Serializable {

	
	private static final long serialVersionUID = 8107410413574476554L;
	private String codigo;
	private String nombre;
	private String descripcion;
	private String imagen;
	private Integer precio;
	private Integer stock;
	private Date fechaCr;
	private Date fechaUp;
	private String usuarioCr;
	private String usuarioUp;

	@Id
	@Column(name = "codigo", unique = true, nullable = false)
	public String getCodigo() {
		return codigo;
	}

	@Column(name = "nombre")
	public String getNombre() {
		return nombre;
	}

	@Column(name = "descripcion")
	public String getDescripcion() {
		return descripcion;
	}

	@Column(name = "imagen")
	public String getImagen() {
		return imagen;
	}

	@Column(name = "precio")
	public Integer getPrecio() {
		return precio;
	}

	@CreationTimestamp
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "America/Santiago")
	@Column(name = "fecha_cr")
	public Date getFechaCr() {
		return fechaCr;
	}

	@UpdateTimestamp
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "America/Santiago")
	@Column(name = "fecha_up")
	public Date getFechaUp() {
		return fechaUp;
	}

	@Column(name = "stock")
	public Integer getStock() {
		return stock;
	}

	public String getUsuarioCr() {
		return usuarioCr;
	}

	@Column(name = "usuario_up")
	public String getUsuarioUp() {
		return usuarioUp;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public void setPrecio(Integer precio) {
		this.precio = precio;
	}

	public void setFechaCr(Date fechaCr) {
		this.fechaCr = fechaCr;
	}

	public void setFechaUp(Date fechaUp) {
		this.fechaUp = fechaUp;
	}

	public void setUsuarioCr(String usuarioCr) {
		this.usuarioCr = usuarioCr;
	}

	public void setUsuarioUp(String usuarioUp) {
		this.usuarioUp = usuarioUp;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

}
