package com.proyecto.entity.query;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Transient;

@Entity
@SqlResultSetMapping(name = "AgaSelectResult", entities = { @EntityResult(

		entityClass = AgasSelect.class, fields = { 
				@FieldResult(name = "rut", column = "rut"),
				@FieldResult(name = "nombres", column = "nombres"),
				@FieldResult(name = "apellidos", column = "apellidos")}) })

public class AgasSelect implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6794114287401864076L;
	private String rut;
	private String nombres;
	private String apellidos;
	
	@Id
	@Column(name = "rut")
	public String getRut() {
		return rut;
	}
	public void setRut(String rut) {
		this.rut = rut;
	}
	
	@Column(name = "nombres")
	public String getNombres() {
		return nombres;
	}
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}
	
	@Column(name = "apellidos")
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	@Transient
	public String getLabel() {
		return "("+rut+") "+ nombres + " " + apellidos;
	}
}
