package com.proyecto.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.proyecto.config.AppConfig;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Venta.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "venta", schema = AppConfig.ECHEMA)
public class Venta implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String usuarioCr;
	private Date fechaCr;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_venta")
	@SequenceGenerator(name = "sequence_venta", sequenceName = "seq_venta", allocationSize = 1, schema = AppConfig.ECHEMA, catalog = AppConfig.CATALOGO_DB)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	@Column(name = "usuarioCr")
	public String getUsuarioCr() {
		return usuarioCr;
	}
	@CreationTimestamp
	@Column(name = "fechaCr")
	public Date getFechaCr() {
		return fechaCr;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public void setUsuarioCr(String usuarioCr) {
		this.usuarioCr = usuarioCr;
	}


	public void setFechaCr(Date fechaCr) {
		this.fechaCr = fechaCr;
	}

}
