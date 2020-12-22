/**
 * funciones globales javascrips 
 */

PrimeFaces.locales['es'] = {
    closeText: 'Cerrar',
    prevText: 'Anterior',
    nextText: 'Siguiente',
    monthNames: ['Enero','Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],
    monthNamesShort: ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun','Jul','Ago','Sep','Oct','Nov','Dic'],
    dayNames: ['Domingo','Lunes','Martes','Miércoles','Jueves','Viernes','Sábado'],
    dayNamesShort: ['Dom','Lun', 'Mar', 'Mie', 'Jue', 'Vie', 'Sab'],
    dayNamesMin: ['D','L','M','Mi','J','V','S'],
    weekHeader: 'Semana',
    firstDay: 1,
    isRTL: false,
    showMonthAfterYear: false,
    yearSuffix: '',
    timeOnlyTitle: 'Sólo hora',
    timeText: 'Tiempo',
    hourText: 'Hora',
    minuteText: 'Minuto',
    secondText: 'Segundo',
    currentText: 'Fecha actual',
    ampm: false,
    month: 'Mes',
    week: 'Semana',
    day: 'Día',
    allDayText : 'Todo el día'
};
function bloquearScroll(){
	document.body.style.overflow = 'hidden';
}

function habilitarScroll(){
	document.body.style.overflow = 'visible';
}
function borderColor(id) {
    document.getElementById(id).style.border = "red";
}



function validaRun(rut) {
	
	var cont = 0;
	var format;
	rut = rut.replace(".", "");
	rut = rut.replace("-", "");
	if (rut.length > 4 ) {
		rut = rut.replace(".", "");
		rut = rut.replace("-", "");
		
		format = "-" + rut.substring(rut.length - 1);
		for (var i = rut.length - 2; i >= 0; i--) {
			format = rut.substring(i, i + 1) + format;
			cont++;
//			if (cont == 3 && i != 0) {
//				format = "." + format;
//				cont = 0;
//			}
		}
		return format;
	}
	return rut;
}
function borderColorMandatorio(objet) {
	if(objet.value==null){
		document.getElementById(objet.id).style.border = "red";
	} 
   
}