package com.perceptron.ctrl;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;

import com.perceptron.util.Perceptron;
import com.perceptron.util.Util;

public class IndexCtrl extends GenericForwardComposer<Component> {
	private static final long serialVersionUID = 1L;
	private Perceptron perceptron;
	
	Label lblFecha;
	Intbox intPrimerEntrada;
	Intbox intSegundaEntrada;
	Listbox listCompuerta;
	private boolean entrena;
	
	public void doAfterCompose(Component comp) {
		try {
			super.doAfterCompose(comp);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error en la aplicación, por favor verifique " + e.getMessage());
		}
		lblFecha.setValue(Util.obtieneFecha(null));
		perceptron = new Perceptron();
		entrena = false;
	}
	
	public void onClick$btnEntrenar(){
		if(listCompuerta.getSelectedItem() == null){
			Util.mostrarAdvertencia("Por favor seleccione la compuerta.");
			return;
		}
		perceptron.definirSalidasDeseadas(listCompuerta.getSelectedItem().getLabel().toLowerCase());
		perceptron.entrenar();
		entrena = true;
		Util.mostrarMensaje("Red entrenada");
	}
	
	public void onClick$btnProbar() {
		if(intPrimerEntrada.getValue() == null) {
			Util.mostrarAdvertencia("Por favor ingrese la primer entrada.");
			return;
		}
		if(intSegundaEntrada.getValue() == null) {
			Util.mostrarAdvertencia("Por favor ingrese la segunda entrada.");
			return;
		}
		if(listCompuerta.getSelectedIndex() == -1) {
			Util.mostrarAdvertencia("Por favor seleccione la compuerta.");
			return;
		}
		
		
		if(!entrena) {
			Util.mostrarAdvertencia("Primero debe entrenar la red.");
			return;
		}
		
		String x1 = String.valueOf(intPrimerEntrada.getValue());
		String x2 = String.valueOf(intSegundaEntrada.getValue());
		float result = perceptron.testeo(x1, x2);
		
		Util.mostrarMensaje("Resultado del cálculo: " + String.valueOf(result));
//		entrena = false;
	}
	
}
