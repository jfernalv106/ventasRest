package com.proyecto.service;

import com.proyecto.entity.Usuario;

public interface UsuarioService {

	void guardar(Usuario usuario);

	Boolean validaUsuario(String rut, String pass);

}
