package com.proyecto.ftp;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.poi.util.IOUtils;


public class ConectaFtp {
	
	

	private String server;
	private String user;
	private String pass;
	private String carpeta;
	private String img;

	
	private String SERVER = "192.168.0.8";
	private String USER = "pi";
	private String PASS = "123456";
	
	


	public ConectaFtp() {

	}

	public ConectaFtp(String server, String user, String pass, String carpeta, String img) {
		super();
		this.server = server;
		this.user = user;
		this.pass = pass;
		this.carpeta = carpeta;
		this.img = img;
	}

	public void borrar(String archivo, String ruta) throws IOException {
		FTPClient client = new FTPClient();
		client.connect(server);
		client.login(user, pass);
		client.changeWorkingDirectory(ruta);
		client.dele(archivo);
		client.logout();
		client.disconnect();
	}
	public boolean existeSingBox(String archivo, String ruta) throws IOException {
		FTPClient client = new FTPClient();
		client.connect(SERVER);
		client.login(USER, PASS);
		client.changeWorkingDirectory(ruta);
		for (String fi : client.listNames()) {
			if(fi.equals(archivo)) {
				client.logout();
				client.disconnect();
			return true;
			}
		}
		client.logout();
		client.disconnect();
		return false;
	}
	public void subeArchivo(InputStream file, String fileName, String ruta) throws SocketException, IOException {
		FTPClient client = new FTPClient();
		client.connect(server);
		client.login(user, pass);
		client.changeWorkingDirectory(ruta);
		client.setFileType(FTP.BINARY_FILE_TYPE, FTP.BINARY_FILE_TYPE);
		client.setFileTransferMode(FTP.BINARY_FILE_TYPE);
		client.enterLocalPassiveMode();
		if (!listaArchivosPorDirectorio(ruta).contains(fileName)) {
			client.storeFile(fileName, file);
		}
		client.logout();
		client.disconnect();
	}
	public void subeArchivoTarja(String server,  String user, String pass, InputStream file, String fileName, String ruta) throws SocketException, IOException {
		FTPClient client = new FTPClient();
		client.connect(server);
		client.login(user, pass);
		client.changeWorkingDirectory(ruta);
		client.setFileType(FTP.BINARY_FILE_TYPE, FTP.BINARY_FILE_TYPE);
		client.setFileTransferMode(FTP.BINARY_FILE_TYPE);
		client.enterLocalPassiveMode();
		client.storeFile(fileName, file);
		client.logout();
		client.disconnect();
	}

	public void subeArchivoEdi(InputStream file, String fileName, String ruta, String server, String usuario,
			String pasw) throws SocketException, IOException {
		FTPClient client = new FTPClient();
		client.connect(server);
		client.login(usuario, pasw);
		client.changeWorkingDirectory(ruta);
		client.setFileType(FTP.BINARY_FILE_TYPE, FTP.BINARY_FILE_TYPE);
		client.setFileTransferMode(FTP.BINARY_FILE_TYPE);
		client.enterLocalPassiveMode();
	    client.storeFile(fileName, file);
		client.logout();
		client.disconnect();
	}

	public List<String> listaArchivosPorDirectorio(String ruta) throws SocketException, IOException {
		FTPClient client = new FTPClient();
		client.connect(server);
		client.login(user, pass);
		client.changeWorkingDirectory(ruta);
		client.setFileType(FTP.BINARY_FILE_TYPE, FTP.BINARY_FILE_TYPE);
		client.setFileTransferMode(FTP.BINARY_FILE_TYPE);
		client.enterLocalPassiveMode();
		List<String> lista = new ArrayList<String>();
		for (String fi : client.listNames()) {
			lista.add(fi);
		}
		client.logout();
		client.disconnect();
		return lista;
	}

	

	public void creaDirectorio(String ruta, String directorio) throws SocketException, IOException {
		FTPClient client = new FTPClient();
		client.connect(server);
		client.login(user, pass);
		client.changeWorkingDirectory(ruta);
		client.setFileType(FTP.BINARY_FILE_TYPE, FTP.BINARY_FILE_TYPE);
		client.setFileTransferMode(FTP.BINARY_FILE_TYPE);
		client.enterLocalPassiveMode();
		client.makeDirectory(directorio);

	}

	public ByteArrayInputStream descargaFtp(String ruta) throws IOException {
		
		FTPClient client = new FTPClient();
		client.connect(server);
		client.login(user, pass);
		client.changeWorkingDirectory(ruta);
		client.setFileType(FTP.BINARY_FILE_TYPE, FTP.BINARY_FILE_TYPE);
		client.setFileTransferMode(FTP.BINARY_FILE_TYPE);
		client.enterLocalPassiveMode();

		return new ByteArrayInputStream(IOUtils.toByteArray(client.retrieveFileStream(ruta)));

	}

	public InputStream descargaFtpInput(String ruta) throws IOException {
		
		FTPClient client = new FTPClient();
		client.connect(server);
		client.login(user, pass);
		client.changeWorkingDirectory(ruta);
		client.setFileType(FTP.BINARY_FILE_TYPE, FTP.BINARY_FILE_TYPE);
		client.setFileTransferMode(FTP.BINARY_FILE_TYPE);
		client.enterLocalPassiveMode();

		return client.retrieveFileStream(ruta);

	}

	public InputStream descargaArchivo(String ruta) throws IOException {
		FTPClient client = new FTPClient();
		client.connect(server);
		client.login(user, pass);
		client.changeWorkingDirectory(ruta);
		client.setFileType(FTP.BINARY_FILE_TYPE, FTP.BINARY_FILE_TYPE);
		client.setFileTransferMode(FTP.BINARY_FILE_TYPE);
		client.enterLocalPassiveMode();

		return client.retrieveFileStream(ruta);

	}

	public InputStream descargaArchivoSignBox(String ruta, String archivo) throws IOException, InterruptedException {

		FTPClient client = new FTPClient();
		client.connect(SERVER);
		client.login(USER, PASS);
		client.changeWorkingDirectory(ruta);
		client.setFileType(FTP.BINARY_FILE_TYPE, FTP.BINARY_FILE_TYPE);
		client.setFileTransferMode(FTP.BINARY_FILE_TYPE);
		
		client.enterLocalPassiveMode();

		return client.retrieveFileStream(archivo);

	}
	
	

	public InputStream descargaPlantilla(String archivo) throws IOException {
		FTPClient client = new FTPClient();
		client.connect(server);
		client.login(user, pass);
		client.changeWorkingDirectory(carpeta);
		client.setFileType(FTP.BINARY_FILE_TYPE, FTP.BINARY_FILE_TYPE);
		client.setFileTransferMode(FTP.BINARY_FILE_TYPE);
		client.enterLocalPassiveMode();
		InputStream input =  client.retrieveFileStream(archivo);
		return input;

	}

	public InputStream descargaImagenJasper(String archivo) throws IOException {
		FTPClient client = new FTPClient();
		client.connect(server);
		client.login(user, pass);
		client.changeWorkingDirectory(img);
		client.setFileType(FTP.BINARY_FILE_TYPE, FTP.BINARY_FILE_TYPE);
		client.setFileTransferMode(FTP.BINARY_FILE_TYPE);
		client.enterLocalPassiveMode();

		return client.retrieveFileStream(archivo);

	}

	public InputStream descargaFtpInputTarja(String ruta, String servidor, String usuario, String psw)
			throws IOException, InterruptedException {
		FTPClient client = new FTPClient();
		client.connect(servidor);
		client.login(usuario, psw);
		TimeUnit.MILLISECONDS.sleep(200);
		client.changeWorkingDirectory(ruta);
		TimeUnit.MILLISECONDS.sleep(200);
		client.setFileType(FTP.BINARY_FILE_TYPE, FTP.BINARY_FILE_TYPE);
		client.setFileTransferMode(FTP.BINARY_FILE_TYPE);
		client.enterLocalPassiveMode();

		return client.retrieveFileStream(ruta);

	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getCarpeta() {
		return carpeta;
	}

	public void setCarpeta(String carpeta) {
		this.carpeta = carpeta;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

}