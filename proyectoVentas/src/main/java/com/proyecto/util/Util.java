package com.proyecto.util;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.xmlbeans.impl.util.Base64;
import org.json.XML;
import org.omnifaces.util.Messages;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 * @author Marval.
 */
@SuppressWarnings("serial")
@ApplicationScoped
public class Util implements Serializable {

	private static final Logger logger = Logger.getLogger(Util.class);

	public static final Pattern DIACRITICS_AND_FRIENDS = Pattern
			.compile("[\\p{InCombiningDiacriticalMarks}\\p{IsLm}\\p{IsSk}]+");

	public static void addDetailMessage(String message) {
		addDetailMessage(message, null);
	}

	public static void addDetailMessage(String message, FacesMessage.Severity severity) {

		FacesMessage facesMessage = Messages.create("").detail(message).get();
		if (severity != null && severity != FacesMessage.SEVERITY_INFO) {
			facesMessage.setSeverity(severity);
		}
		Messages.add(null, facesMessage);
	}

	// Convierte InputStream a Byte
	public static byte[] convertirTobyteArray(InputStream is) throws IOException {
		byte[] bytes = null;
		if (is != null) {
			bytes = new byte[is.available()];
			is.read(bytes);
		}
		return bytes;
	}

	// Convierte InputStream a Document
	public Document InputStreamToXml(InputStream fXmlFile) throws Throwable {
		Reader reader = new InputStreamReader(fXmlFile, "UTF-8");
		InputSource is = new InputSource(reader);
		is.setEncoding("UTF-8");

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(is);
		return doc;
	}

	public static String StringFromInputStream(InputStream is) {

		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		String line;
		try {

			br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return sb.toString();

	}

	// Convierte Document a String
	public String xmlToString(Document doc) {
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer t = null;
		try {
			t = tf.newTransformer();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		}
		StringWriter sw = new StringWriter();
		try {
			t.transform(new DOMSource(doc), new StreamResult(sw));
		} catch (TransformerException e) {
			logger.info("Error " + e);
		}
		return sw.toString();
	}

	// Convierte String a JSON
	public static String stringToJson(String xml, List<String> error) {

		// logger.info("[Util - stringToJson] inicio ");

		Pattern p = Pattern.compile("[a-zA-Z]");
		Matcher match;

		logger.info("[Util - stringToJson] errores: " + error.toString());

		for (String limp : error) {
			match = p.matcher(limp);
			if (match.find() && limp.length() == 1) {
				xml = xml.replaceAll("\\<" + limp + "\\>", limp);
			} else {
				xml = xml.replaceAll("\\<" + limp, limp);
				xml = xml.replaceAll(limp + "\\>", limp);
				xml = limpiaXml(xml);

			}

			if (limp.equals("tag")) {
				return "";
			}

		}
		error.clear();

		try {
			org.json.JSONObject xmlJSONObj = XML.toJSONObject(xml);
			String jsonPrettyPrintString = xmlJSONObj.toString(4);
			return jsonPrettyPrintString;

		} catch (Exception e) {
			logger.info("[Util - stringToJson] error " + e);
			String[] er = String.valueOf(e.getMessage()).split(" ");
			error.add(er[1]);
			return stringToJson(xml, error);

		}

	}

	public static Date stringADate(String fecha) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		SimpleDateFormat formatterDMA = new SimpleDateFormat("dd-MM-yyyy");
		if (fecha.length() > 11) {
			return formatter.parse(fecha);
		} else {
			return formatterDMA.parse(fecha);
		}

	}

	public static String descomprimeGZIP(byte[] gzip) throws IOException {

		ByteArrayInputStream bis = new ByteArrayInputStream(gzip);
		GZIPInputStream gis = new GZIPInputStream(bis);
		BufferedReader br = new BufferedReader(new InputStreamReader(gis, "UTF-8"));
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		br.close();
		gis.close();
		bis.close();

		return sb.toString();
	}

	public static String limpiaXml(String xml) {

		String[] replaceEnTags = { "(", ")" };

		for (int i = 0; i < replaceEnTags.length; i++) {
			xml = xml.replaceAll("\\<\\" + replaceEnTags[i] + "\\>", "");
		}

		for (int i = 0; i < 9; i++) {
			xml = xml.replaceAll("<" + i, "&lt;" + i);
			xml = xml.replaceAll(i + ">", i + "&gt;");
		}
		xml = xml.replaceAll("\\<\\>", "");
		xml = xml.replaceAll("<!\\[CDATA\\[", "");
		xml = xml.replaceAll("\\]\\]\\>", "");
		xml = xml.replaceAll("&", "&#038;");
		xml = xml.replaceAll("Ë‹", "&apos;");

		xml = xml.replaceAll("< ", "&lt; ");
		xml = xml.replaceAll(" >", " &gt;");
		xml = xml.replaceAll("<	", "&lt; ");
		xml = xml.replaceAll("	>", " &gt;");

		return xml;
	}

	public static String limpiaJson(String xml) {
		xml = xml.replaceAll("\\<\\>", "");
		xml = xml.replaceAll("<!\\[CDATA\\[", "");
		xml = xml.replaceAll("\\]\\]\\>", "");

		return xml;
	}

	public static boolean comparaFechas(Date mayor, Date menor) {
		Calendar calendarM = Calendar.getInstance(), calendarm = Calendar.getInstance();
		calendarM.setTime(mayor);
		calendarm.setTime(menor);
		if (calendarM.compareTo(calendarm) > 0) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean comparaFechasIguales(Date mayor, Date menor) {
		Calendar calendarM = Calendar.getInstance(), calendarm = Calendar.getInstance();
		calendarM.setTime(mayor);
		calendarm.setTime(menor);
		if (calendarM.compareTo(calendarm) == 0) {
			return true;
		} else {
			return false;
		}
	}

	public static String separaBlXML(String xml) {
		xml = xml.replaceAll("\\<\\>", "");
		xml = xml.replaceAll("\\<Documentos\\>", "");
		xml = xml.replaceAll("\\</Documentos\\>", "");
		xml = xml.replaceAll("\\<xml\\>", "");
		xml = xml.replaceAll("\\</xml\\>", "");

		return xml;
	}

	public static String numeroDecimalApalabra(BigDecimal numero) {
		numero = numero.setScale(2, BigDecimal.ROUND_FLOOR);
		Integer numerico = Integer.valueOf(numero.intValue());
		Integer decimal = (numero.subtract(new BigDecimal(numerico))).multiply(new BigDecimal(100)).intValue();
		return numeroEnteroPalabra(numero.intValue()) + " , " + numeroEnteroPalabra(decimal);
	}

	public static String numeroEnteroPalabra(Integer numero) {
		String resultado = "";
		Integer cmill = ((numero % 1000000000) < 100000000) ? 0 : numero % 1000000000;
		Integer dmill = ((numero % 100000000) < 10000000) ? 0 : numero % 100000000;
		Integer mill = ((numero % 10000000) < 1000000) ? 0 : numero % 10000000;
		Integer cmil = ((numero % 1000000) < 100000) ? 0 : numero % 1000000;
		Integer dmil = ((numero % 100000) < 10000) ? 0 : numero % 100000;
		Integer mil = ((numero % 10000) < 1000) ? 0 : numero % 10000;
		Integer centenas = ((numero % 1000) < 100) ? 0 : numero % 1000;
		Integer decenas = ((numero % 100) < 10) ? 0 : numero % 100;
		Integer unidad = numero % 10;
		resultado += (cmill != 0) ? unidadesMillon(cmill) : "";
		resultado += (dmill != 0 && cmill == 0) ? unidadesMillon(dmill) : "";
		resultado += (mill != 0 && dmill == 0) ? unidadesMillon(mill) : "";
		resultado += (cmil != 0) ? unidadDiesMil(cmil) : "";
		resultado += (dmil != 0 && cmil == 0) ? unidadDiesMil(dmil) : "";
		resultado += (mil != 0 && cmil == 0) ? unidadMil(mil) : "";
		resultado += (centenas != 0) ? centanas(centenas) + ((centenas < 200) ? "TO " : " ") : "";
		resultado += (decenas != 0 && decenas >= 20) ? decena(decenas) : "";
		resultado += (decenas != 0 && decenas < 20) ? Constantes.DECENAS.get(decenas - 10) : "";
		resultado += (unidad >= 0 && decenas == 0) ? Constantes.UNIDADES.get(unidad) : "";
		return resultado;
	}

	public static String decena(Integer n) {
		Integer numero = (n - (n % 10)) / 10;
		String resultado = Constantes.DECENASVE.get(numero - 2);
		resultado += (n % 10 != 0) ? " Y " + Constantes.UNIDADES.get(n % 10) : "";

		return resultado;

	}

	public static String centanas(Integer centena) {
		String resultado = "";
		Integer numero = (centena - (centena % 100)) / 100;
		resultado += Constantes.CENTENAS.get(numero - 1);

		return resultado;
	}

	public static String unidadMil(Integer mil) {
		String resultado = "";
		Integer numero = (mil - (mil % 1000)) / 1000;
		resultado += (numero == 1) ? Constantes.MIL : "";
		resultado += (numero != 1) ? Constantes.UNIDADES.get(numero) + " " + Constantes.MIL + " " : "";
		resultado += " ";
		return resultado;
	}

	public static String unidadDiesMil(Integer mil) {
		String resultado = "";
		Integer numero = (mil - (mil % 1000)) / 1000;
		resultado += (numero != 0) ? numeroEnteroPalabra(numero) + " " + Constantes.MIL + " " : "";
		return resultado;
	}

	public static String unidadesMillon(Integer mill) {
		String resultado = "";
		Integer numero = (mill - (mill % 1000000)) / 1000000;
		resultado += (numero == 0) ? Constantes.MILLON : "";
		resultado += (numero != 0) ? numeroEnteroPalabra(numero) + " " + Constantes.MILLONES + " " : "";
		return resultado;
	}

	public static String fechaDateToString(Date fecha, String formato) {
		SimpleDateFormat formatter = new SimpleDateFormat(formato);
		return formatter.format(fecha);

	}

	public static String limpiaXMLFactura(String xml) {

		xml.replaceAll("Ñ", "N");
		xml.replaceAll("ñ", "n");

		return xml;

	}

	public static String limpiaDiacritics(String str) {
		str = Normalizer.normalize(str, Normalizer.Form.NFD);
		str = DIACRITICS_AND_FRIENDS.matcher(str).replaceAll("");
		str = str.replaceAll("&", "&amp;");
		str = str.replaceAll("Ë‹", "&apos;");
		return str;
	}

	public static String limitarTexto(String entrada, Integer largo) {

		if (entrada != null) {
			if (entrada.length() < largo) {
				return entrada;
			}
			return entrada.substring(0, largo);
		} else {
			return "";
		}

	}

	public static Date sumarRestarDiasFecha(Date fecha, int dias) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);
		calendar.add(Calendar.DAY_OF_YEAR, dias);
		return calendar.getTime();

	}

	public static String mensajeValidacion(String titulo, String texto) {
		String mensaje = "Swal.fire({";
		mensaje += " type: 'warning',";
		mensaje += " allowOutsideClick: 'false',";
		mensaje += "title: '" + limpiaTextoXmlHtml(titulo) + "',";
		mensaje += "text: '" + limpiaTextoXmlHtml(texto) + "'";

		mensaje += "})";
		return mensaje;
	}

	public static String mensajeSwalConAcento(String titulo, String texto, String tipo) {
		
		String mensaje = "Swal.fire({";
		mensaje += " type: '" + tipo + "',";
		mensaje += " allowOutsideClick: 'false',";
		mensaje += "title: '" + titulo + "',";
		mensaje += "text: '" + texto + "',";

		mensaje += "})";
		return mensaje;
	}
	
	public static String mensajeSwal(String titulo, String texto, String tipo) {
		String mensaje = "Swal.fire({";
		mensaje += " type: '" + tipo + "',";
		mensaje += " allowOutsideClick: 'false',";
		mensaje += "title: '" + limpiaTextoXmlHtml(titulo) + "',";
		mensaje += "text: '" + limpiaTextoXmlHtml(texto) + "'";

		mensaje += "})";
		return mensaje;
	}

	public static String limpiaTextoXmlHtml(String texto) {
		if (texto == null) {
			return "";
		}
		String textoLimpio = "";
		textoLimpio = texto.trim();

		char[] parametrosBad_a = { 'á', 'à', 'ä', 'â', 'ª', 'Á', 'À', 'Â', 'Ä' };
		char[] parametrosOK_a = { 'a', 'a', 'a', 'a', 'a', 'A', 'A', 'A', 'A' };
		for (int i = 0; i < parametrosBad_a.length; i++) {
			textoLimpio = textoLimpio.replace(parametrosBad_a[i], parametrosOK_a[i]);

		}

		char[] parametrosBad_e = { 'é', 'è', 'ë', 'ê', 'É', 'È', 'Ê', 'Ë' };
		char[] parametrosOK_e = { 'e', 'e', 'e', 'e', 'E', 'E', 'E', 'E' };
		for (int i = 0; i < parametrosBad_e.length; i++) {
			textoLimpio = textoLimpio.replace(parametrosBad_e[i], parametrosOK_e[i]);
		}

		char[] parametrosBad_i = { 'í', 'ì', 'ï', 'î', 'Í', 'Ì', 'Ï', 'Î' };
		char[] parametrosOK_i = { 'i', 'i', 'i', 'i', 'I', 'I', 'I', 'I' };
		for (int i = 0; i < parametrosBad_i.length; i++) {
			textoLimpio = textoLimpio.replace(parametrosBad_i[i], parametrosOK_i[i]);
		}

		char[] parametrosBad_o = { 'ó', 'ò', 'ö', 'ô', 'Ó', 'Ò', 'Ö', 'Ô' };
		char[] parametrosOK_o = { 'o', 'o', 'o', 'o', 'O', 'O', 'O', 'O' };
		for (int i = 0; i < parametrosBad_o.length; i++) {
			textoLimpio = textoLimpio.replace(parametrosBad_o[i], parametrosOK_o[i]);
		}

		char[] parametrosBad_u = { 'ú', 'ù', 'ü', 'û', 'Ú', 'Ù', 'Û', 'Ü' };
		char[] parametrosOK_u = { 'u', 'u', 'u', 'u', 'U', 'U', 'U', 'U' };
		for (int i = 0; i < parametrosBad_u.length; i++) {
			textoLimpio = textoLimpio.replace(parametrosBad_u[i], parametrosOK_u[i]);
		}

		char[] parametrosBad_otro = { 'ñ', 'Ñ', 'ç', 'Ç' };
		char[] parametrosOK_otro = { 'n', 'N', 'c', 'C' };
		for (int i = 0; i < parametrosBad_otro.length; i++) {
			textoLimpio = textoLimpio.replace(parametrosBad_otro[i], parametrosOK_otro[i]);
		}

		String[] parametrosBad_otro2 = { "'", "|", "°", "º", "ª", "‘", "´", "<", ">", "\"", "¬", "‹", "’", "–" };
		for (int i = 0; i < parametrosBad_otro2.length; i++) {
			textoLimpio = textoLimpio.replace(parametrosBad_otro2[i], "");
		}

		textoLimpio = textoLimpio.replaceAll("[\n\r]", " ");
		textoLimpio = textoLimpio.replaceAll("[\n]", " ");

		String textoLimpio2 = "";
		char[] c_arr = textoLimpio.toCharArray();
		for (int i = 0; i < c_arr.length; i++) {
			int asciiValue = (int) c_arr[i];
			if (asciiValue < 32 || asciiValue > 126) {
				textoLimpio2 += " ";
			} else {
				textoLimpio2 += (char) asciiValue;
			}

		}
		textoLimpio = textoLimpio2;
		//textoLimpio = textoLimpio.replaceAll("&", "&amp;");
		textoLimpio = textoLimpio.replaceAll("Ë‹", "&apos;");
		return textoLimpio;
	}

	public static String ultimaApertura(String estructura) {
		String[] bls = estructura.split(" / ");
		return bls[bls.length - 1];
	}

	public static String ftpBase64() throws IOException {
		String ftpUrl = "ftp://tarja:admin@192.168.1.25/tarja/171310/TCLU512185-2/15387505514051874708366.jpg";
		URL url = new URL(ftpUrl);
		URLConnection conn = url.openConnection();
		byte[] decodBls = Base64.encode(Util.convertirTobyteArray(conn.getInputStream()));
		return new String(decodBls);
	}

	public static ByteArrayOutputStream resizeImagen(InputStream input, int width, int height) throws Exception {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		BufferedImage src = ImageIO.read(input);
		BufferedImage dest = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = dest.createGraphics();
		AffineTransform at = AffineTransform.getScaleInstance((double) width / src.getWidth(),
				(double) height / src.getHeight());
		g.drawRenderedImage(src, at);
		ImageIO.write(dest, "JPG", os);
		return os;

	}

	// Rotates clockwise 90 degrees. Uses rotation on center and then translating it
	// to origin
	public  static AffineTransform rotate90(BufferedImage source, int rotate) {
		AffineTransform transform = new AffineTransform();
		transform.rotate(rotate * (Math.PI / 2), source.getWidth() / 2, source.getHeight() / 2);
		double offset = (source.getWidth() - source.getHeight()) / 2;
		transform.translate(rotate * offset, rotate * offset);
		return transform;
	}

	// Metodo encargado de armar el texto para los errores de excel
	public static String errorLectura(String nombreColumna, int numeroFila) {
		StringBuilder error = new StringBuilder();
		error.append("Error en la COLUMNA: '").append(nombreColumna).append("' FILA: ").append(numeroFila);
		return error.toString();
	}

	public static Date convertirFecha(String celda) throws ParseException {
		celda = celda.replaceAll("-", "/");
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
		String celdaFinal;
		Date fecha;
		if (celda.equals("null")) {
			fecha = null;
		} else {
			if (celda.contains(".")) {
				double original = Double.parseDouble(celda);
				Date fechaAlternativa = DateUtil.getJavaDate(original);
				celdaFinal = formatter.format(fechaAlternativa);
				fecha = formatter.parse(celdaFinal);
			} else {
				celdaFinal = celda;
				fecha = formatter.parse(celdaFinal);
			}

		}

		return fecha;
	}

	public static Integer cortarNumCheque(String nroDoc) {

		if (nroDoc != null && nroDoc.length() > 9) {

			return Integer.parseInt(nroDoc.substring(0, 9));
		}

		return Integer.parseInt(nroDoc);

	}

	public static boolean isValidRut(String rut) {
		boolean ret = false;
		if (rut != null && rut.trim().length() > 0) {
			try {
				rut = rut.replaceAll("[.]", "").replaceAll("-", "").trim().toUpperCase();
				char dv = rut.charAt(rut.length() - 1);
				String mantisa = rut.substring(0, rut.length() - 1);
				if (isInteger(mantisa)) {
					int mantisaInt = Integer.parseInt(mantisa);
					ret = validarRut(mantisaInt, dv);
				}
			} catch (Throwable e) {
				logger.info("[isValidRut] [" + rut + "] " + e);
			}
		}
		return ret;
	}

	private static boolean validarRut(int rut, char dv) {
		int m = 0, s = 1;
		for (; rut != 0; rut /= 10) {
			s = (s + rut % 10 * (9 - m++ % 6)) % 11;

		}
		return Character.toUpperCase(dv) == (char) (s != 0 ? s + 47 : 75);
	}

	public static boolean isInteger(String cad) {
		for (int i = 0; i < cad.length(); i++) {
			if (!Character.isDigit(cad.charAt(i))) {
				return false;
			}
		}
		return true;

	}

}