package com.proyecto.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.dao.UsuarioDao;
import com.proyecto.entity.Usuario;
import com.proyecto.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {
	@Autowired
	UsuarioDao usuarioDao;

	@Transactional
	@Override
	public void guardar(Usuario usuario) {
		usuarioDao.guardar(usuario);
	}

	@Transactional
	@Override
	public Boolean validaUsuario(String rut, String pass) {
		return usuarioDao.validaUsuario(rut, pass);
	}
}
