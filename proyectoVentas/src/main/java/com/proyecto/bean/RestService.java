package com.proyecto.bean;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyecto.entity.Producto;
import com.proyecto.service.ProductoService;

@Controller
@RestController
public class RestService {

	@Autowired
	ProductoService productoService;
	@Autowired
	LoginBean login;


	private ResponseEntity<String> ERROR(String error, HttpHeaders httpHeaders) {
		return new ResponseEntity<String>("{\"mensaje\":\"" + error + "\"}", httpHeaders, HttpStatus.OK);
	}



	@ResponseBody
	@RequestMapping(value = "/ProductosNombreRest", method = RequestMethod.POST, produces = "application/json", consumes = {
			"text/plain", "application/json" })
	public ResponseEntity<String> ProductosPorNombre(@RequestParam String nombre,@RequestParam String token) {
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
		try {
			return new ResponseEntity<String>(
					new ObjectMapper().writeValueAsString(productoService.listaProductoPorNombre(nombre)), httpHeaders,
					HttpStatus.OK);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			return ERROR(e.getMessage(), httpHeaders);
		}

	}
	@ResponseBody
	@RequestMapping(value = "/ProductosCodigoRest", method = RequestMethod.POST, produces = "application/json", consumes = {
			"text/plain", "application/json" })
	public ResponseEntity<String> ProductosPorCodigo(@RequestParam String codigo,@RequestParam String token) {
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
		try {
			return new ResponseEntity<String>(
					new ObjectMapper().writeValueAsString(productoService.listaProductoPorCodigo(codigo)), httpHeaders,
					HttpStatus.OK);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			return ERROR(e.getMessage(), httpHeaders);
		}
		
	}
	@ResponseBody
	@RequestMapping(value = "/ProductosRest", method = RequestMethod.POST, produces = "application/json", consumes = {
			"text/plain", "application/json" })
	public ResponseEntity<String> Productos(@RequestParam String token) {
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
		try {
			return new ResponseEntity<String>(
					new ObjectMapper().writeValueAsString(productoService.listaProdutos()), httpHeaders,
					HttpStatus.OK);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			return ERROR(e.getMessage(), httpHeaders);
		}
		
	}

	
	@ResponseBody
	@RequestMapping(value = "/GuardaProducto", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> cargaProducto(@RequestParam String producto,@RequestParam String token) {
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
		String mensaje = "";
		try {
			Producto pre = new ObjectMapper().readerFor(Producto.class).readValue(producto);
			productoService.guardar(pre);
			
			mensaje = "Guardado";
		} catch (Exception e) {
			mensaje = "ERROR al guardar";
		}
		return ERROR(mensaje, httpHeaders);
	}
	
	@ResponseBody
	@RequestMapping(value = "/fotos", method = RequestMethod.GET, produces = "application/json")
	public String descargaArchivo(String ftpUrl) throws IOException, InterruptedException {
		String final_txt = "";
		URL url = new URL(ftpUrl);
		URLConnection conn = url.openConnection();
		InputStream is = conn.getInputStream();
		byte[] bytes = null;
		if (is != null) {
			bytes = new byte[is.available()];
			is.read(bytes);
		}
		final_txt = Base64.getEncoder().encodeToString(bytes);
		System.out.println(final_txt);
		return final_txt;
		
	}
	
}
