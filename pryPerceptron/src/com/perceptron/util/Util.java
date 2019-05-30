package com.perceptron.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.zkoss.zul.Messagebox;

public class Util {
	
	public static final String meses[] = {"Enero", "Febrero", "Marzo", "Abril", "Mayo","Junio", 
			"Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
	
	public static String obtieneFecha(Date fecha) {
		Date date = fecha;
		if(date == null) {
			date = new Date();
		}
		int mes = Integer.parseInt(new SimpleDateFormat("MM").format(date));
		String dia = new SimpleDateFormat("dd").format(date);
		String anio = new SimpleDateFormat("yyyy").format(date);
		String salida = dia.concat(" de ").concat(meses[(mes-1)]).concat(" de ").concat(anio);
		return salida;
	}
	
	public static void mostrarMensaje(String p_mensaje){
		Messagebox.show(p_mensaje, "Información", Messagebox.OK, Messagebox.INFORMATION);
	}
	
	public static void mensajeInconveniente(){
		Messagebox.show("Ha ocurrido un inconveniente, si el problema persiste consulte a su administrador.", 
				"Información", Messagebox.OK, Messagebox.INFORMATION);
	}
	
	public static void mostrarConfirmacion(String mensaje, 
			org.zkoss.zk.ui.event.EventListener<org.zkoss.zk.ui.event.Event> evento){
		Messagebox.show(mensaje, "Confirmación", Messagebox.YES + Messagebox.NO, 
				Messagebox.QUESTION, evento);
	}
	
}
