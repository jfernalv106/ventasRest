package com.proyecto.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.proyecto.config.AppConfig;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "rut", scope = Usuario.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "usuario", schema = AppConfig.ECHEMA)
public class Usuario implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 839617716959951355L;
	private String rut;
	private String nombres;
	private String apellidos;
	private String pass;
	private String mail;
	@Id
	@Column(name = "rut", unique = true, nullable = false)
	public String getRut() {
		return rut;
	}
	@Column(name = "nombres")
	public String getNombres() {
		return nombres;
	}

	@Column(name = "apellidos")
	public String getApellidos() {
		return apellidos;
	}

	@Column(name = "pass")
	public String getPass() {
		return pass;
	}

	@Column(name = "mail")
	public String getMail() {
		return mail;
	}

	public void setRut(String rut) {
		this.rut = rut;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

}
