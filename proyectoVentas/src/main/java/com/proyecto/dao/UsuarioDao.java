package com.proyecto.dao;

import com.proyecto.entity.Usuario;

public interface UsuarioDao {

	void guardar(Usuario usuario);

	Boolean validaUsuario(String rut, String pass);

}
