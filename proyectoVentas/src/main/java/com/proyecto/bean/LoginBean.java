package com.proyecto.bean;

import static com.proyecto.util.Util.addDetailMessage;

import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.inject.Specializes;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.omnifaces.util.Faces;
import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.annotation.SessionScope;

import com.github.adminfaces.template.session.AdminSession;

/**
 *
 * Clase encargada del login del sistema.
 * 
 * @author Marval.
 * @version 1.0.
 */
@Named
@Specializes
@SessionScope
public class LoginBean extends AdminSession implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -347371486804535102L;
	/**
	 * 
	 */

	int validarCredenciales = 0;
	boolean todoOkPermisos = false;
	boolean esAdmin = false;

	private String currentUser = "";
	private String email;
	private String password;
	private String nombreCompletoUsu = "";
	private String ip;

	private boolean remember;
	private boolean login;
	private boolean permisoDenegado;
	private boolean presentarBienv;




	private String empresa;
	private String versionNav;


	@SuppressWarnings("unused")
	@Inject
	private AdminSession adminSession;
	



	@Autowired
	private HttpServletRequest request;



	

	/**
	 * Método constructor de la clase.
	 */


	/**
	 * Método encargado de realizar login de usuario.
	 * 
	 * @return String.
	 * @throws IOException.
	 */
	public void login() throws IOException {

	
		
	

	

		this.esAdmin = false;

		this.currentUser = email;
		this.ip = getClientIP();


		if (this.login) {

		

			setLogin(true);


		
			Faces.getExternalContext().getFlash().setKeepMessages(true);
			ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
			String path = ctx.getContextPath();
			FacesContext.getCurrentInstance().getExternalContext().redirect(path + "/inicio");

		} else {
			PrimeFaces.current()
					.executeScript("Swal.fire(\"Acceso Denegado!\", \"Credenciales incorrectas.\", \"warning\");");
			return;
		}

		this.presentarBienv = true;

		addDetailMessage("");
		if (!currentUser.equals("")) {
			login = true;
		}



	}

	/**
	 * Verifica los accesos en el menú para los usuarios.
	 * 
	 * @param currentUser
	 */
	
	
	

	


	public void valida() throws IOException {

		ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String path = ctx.getContextPath();

		if (!login) {

			FacesContext.getCurrentInstance().getExternalContext().redirect(path + "/");
		}

		if (this.permisoDenegado) {
			PrimeFaces.current().executeScript(
					"Swal.fire(\"Acceso denegado! \", \" Su usuario no cuenta con permisos para ingresar a este menú. \", \"warning\");");
			this.permisoDenegado = false;
		}
	}

	public void onLoad() {

		if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
			return;
		}

		if (this.presentarBienv) {

			String submensaje = "";

			
				submensaje = "Usuario: " + this.currentUser + " - Empresa: " + this.empresa + ".";

			
		
		

			PrimeFaces.current().executeScript(
					"Swal.fire(\'Bienvenido " + this.nombreCompletoUsu + ". \', \'" + submensaje + "\', \'success\');");
		}

		this.presentarBienv = false;
	}

	public void cierraSession() {
		login = false;
	}

	private String getClientIP() {
		String xfHeader = request.getHeader("X-Forwarded-For");
		if (xfHeader == null) {
			return request.getRemoteAddr();
		}
		return xfHeader.split(",")[0];
	}

	@Override
	public boolean isLoggedIn() {

		return true;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isRemember() {
		return remember;
	}

	public void setRemember(boolean remember) {
		this.remember = remember;
	}

	public String getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(String currentUser) {
		this.currentUser = currentUser;
	}

	

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public boolean isLogin() {
		return login;
	}

	public void setLogin(boolean login) {
		this.login = login;
	}

	



	public String getNombreCompletoUsu() {
		return nombreCompletoUsu;
	}

	public void setNombreCompletoUsu(String nombreCompletoUsu) {
		this.nombreCompletoUsu = nombreCompletoUsu;
	}

	
	

	public boolean isPermisoDenegado() {
		return permisoDenegado;
	}

	public void setPermisoDenegado(boolean permisoDenegado) {
		this.permisoDenegado = permisoDenegado;
	}

	

	public boolean isPresentarBienv() {
		return presentarBienv;
	}

	public void setPresentarBienv(boolean presentarBienv) {
		this.presentarBienv = presentarBienv;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	

	public boolean isEsAdmin() {
		return esAdmin;
	}

	public void setEsAdmin(boolean esAdmin) {
		this.esAdmin = esAdmin;
	}

	

	public String getVersionNav() {
		return versionNav;
	}

	public void setVersionNav(String versionNav) {
		this.versionNav = versionNav;
	}

}