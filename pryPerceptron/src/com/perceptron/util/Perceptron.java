package com.perceptron.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
//import javax.swing.JOptionPane;

public class Perceptron  {

	// Funcion activacion
	static float fActivacion = 0.0f;
	// Error
	static float error = 1.0f;
	// Iteraciones
	int iteraciones = 0;
	static float[][] entradas = { { 1f, 1f, -1f }, { 1f, -1f, -1f }, { -1f, 1f, -1f }, { -1, -1f, -1f } };
	// salidasDeseadas para el perceptron
	float[] salidasDeseadas = new float[4];
	int establecioSalidas = 0;
	float[] pesos = { 1.2f, -1.2f, -0.4f };
	float factorAprendizaje = .5f;
	
	public Perceptron() {
	}
	
	public void definirSalidasDeseadas(String compuerta) {
		salidasDeseadas[0] =  1f;
		if("and".equals(compuerta)) {
			salidasDeseadas[1] = 1f;
			salidasDeseadas[2] = 1f;
		}else if("or".equals(compuerta)) {
			salidasDeseadas[1] = -1f;
			salidasDeseadas[2] = -1f;
		}
		salidasDeseadas[3] = -1f;
		establecioSalidas = 1;
	}
	
	public boolean entrenar() {
		boolean ret = false;
		if (establecioSalidas != 0) {
			// imprimimos los pesos que definimos anteriormente
			/*
			 * recorremos las entradas y se le van pasando a la fActivacion() esta funcion
			 * nos regresa la salida para dichas entradas. por ejemplo si elegimos la
			 * compuerta OR y se le manda la entradas: x1 = 1 , x2 = 0 la salida que nos
			 * deberia de regresa la fActivacion() es 1 esta salida se le manda al metodo
			 * error este verifica si hay o no error
			 */
			for (int i = 0; i <= entradas[0].length; i++) {
				fActivacion(entradas[i]);
				float error = error(salidasDeseadas[i]);

				if (error != 0f) {
					// Si hay error, recalcula los pesos
					calculaPesos(entradas[i], salidasDeseadas[i]);
					/*
					 * ponemos i=-1 para que empiece a sacar la funcion de activacion desde el
					 * inicio con los nuevos pesos
					 */
					i = -1;
				}
			}
			ret = true;
//			JOptionPane.showMessageDialog(null, "LA RED YA ESTA ENTRENADA");
		}
		return ret;
	}
	
	public float testeo(String x1, String x2) {
//		String x1 = JOptionPane.showInputDialog(null, "Ingresa la primera entrada");
//		String x2 = JOptionPane.showInputDialog(null, "Ingresa la segunda entrada");
		float[] entradasPrueba = new float[3];
		entradasPrueba[0] = Float.parseFloat(x1); // entrada neurona 1
		entradasPrueba[1] = Float.parseFloat(x2); // entrada neurona 2
		entradasPrueba[2] = -1f; // entrada para el umbral
		float resultado = testearRed(entradasPrueba);
//		JOptionPane.showMessageDialog(null, resultado);
		return resultado;
	}
	
	private float fActivacion(float[] entradas) {
		fActivacion = 0.0f;
		for (int i = 0; i < entradas.length; i++) {
			// se multiplica cada peso por cada entrada y se suma
			fActivacion += pesos[i] * entradas[i];
			// redondeamos a 2 decimales el valor de la funcion activacion
			String val = fActivacion + "";
			BigDecimal big = new BigDecimal(val);
			big = big.setScale(2, RoundingMode.HALF_UP);
			fActivacion = big.floatValue();
		}
		// se determina el valor de la salida
		if (fActivacion >= 0)
			fActivacion = 1;
		else if (fActivacion < 0)
			fActivacion = -1;

		return fActivacion;
	}

	// metodo para verificar si hay o no error
	private float error(float salidaDeseada) {
		error = salidaDeseada - fActivacion;
		return error;
	}

	// metodo para el reajuste de pesos
	private void calculaPesos(float[] entradas, float salidas) {
		if (error != 0) {
			for (int i = 0; i < entradas.length; i++) {
				this.pesos[i] = pesos[i] + (2.0f * .5f) * (salidas * entradas[i]);
				String val = this.pesos[i] + "";
				BigDecimal big = new BigDecimal(val);
				big = big.setScale(2, RoundingMode.HALF_UP);
				fActivacion = big.floatValue();
			}
		}
	}

	private float testearRed(float[] entradasPrueba) {
		float result;
		fActivacion = 0.0f;
		for (int i = 0; i <= 2; i++) {
			fActivacion += pesos[i] * entradasPrueba[i];
			String val = fActivacion + "";
			BigDecimal big = new BigDecimal(val);
			big = big.setScale(2, RoundingMode.HALF_UP);
			fActivacion = big.floatValue();
		}
		if (fActivacion >= 0)
			fActivacion = 1;
		else if (fActivacion < 0)
			fActivacion = -1;

		result = fActivacion;
		return result;
	}
	
}
