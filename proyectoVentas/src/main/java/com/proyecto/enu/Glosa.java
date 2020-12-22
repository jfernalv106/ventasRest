package com.proyecto.enu;

public enum Glosa {

	CAN_DIAS("<<CAN_DIAS>>"), FECHA_INICIO("<<FECHA_INICIO>>"), FECHA_FIN("<<FECHA_FIN>>"), COD_BULTO("<<COD_BULTO>>"),
	DES_BULTO("<<DES_BULTO>>"), TXT_OBSERVACION("<<TXT_OBSERVACION>>"), MAYOR_PAGANTE("<<MAYOR_PAGANTE>>"),
	NOMBRE_NAVE("<<NOMBRE_NAVE>>"), CODIGO_DESTADU("<<CODIGO_DESTADU>>"), NUMERO_DESTADU("<<NUMERO_DESTADU>>"),
	FECHA_DESTADU("<<FECHA_DESTADU>>"), BL_ARMADOR("<<BL_ARMADOR>>"), SKU("<<SKU>>"), CAN_SERVICIO("<<CAN_SERVICIO>>"),
	TON_SERVICIO("<<TON_SERVICIO>>"), VOL_SERVICIO("<<VOL_SERVICIO>>"), NUMERO_PR("<<NUMERO_PR>>"),
	FCH_OPERACOMP("<<FCH_OPERACOMPL>>"), CONTENEDOR("<<CONTENEDOR>>"), USD("<<VALOR_USD>>");

	private Glosa(String glosa) {
		this.glosa = glosa;
	}

	private String glosa;

	public String getGlosa() {
		return glosa;
	}

	public void setGlosa(String glosa) {
		this.glosa = glosa;
	}

}
