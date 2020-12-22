package com.proyecto.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.proyecto.dao.UsuarioDao;
import com.proyecto.entity.Usuario;
@Repository
public class UsuarioDaoImpl implements UsuarioDao{
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void guardar(Usuario usuario) {
		sessionFactory.getCurrentSession().persist(usuario);
	}
	@Override
	public Boolean validaUsuario(String rut,String pass) {
		String query="select case when usr.rut is not null then true else false end from pto.usuario as usr where usr.rut=:rut and usr.pass=:pass";
		return (Boolean)sessionFactory.getCurrentSession().createNativeQuery(query).setParameter("pass", pass ).setParameter("rut", rut ).uniqueResult();
	}
}
