package com.perceptron.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.swing.*;
import javax.swing.JOptionPane;

public class Perceptron implements ActionListener {

	JButton Recalcular = new JButton("ENTRENAR");
	JMenuBar menu = new JMenuBar();
	JMenu compLG = new JMenu("ELEGIR ENTRE AND y OR");
	JMenuItem and = new JMenuItem("AND");
	JMenuItem or = new JMenuItem("OR");
	JButton testear = new JButton("PROBAR");
	ImageIcon icono = new ImageIcon("src\\Imagenes\\perceptronsimple.png");
	JLabel imagen = new JLabel(icono);

	// Variable de mensaje
	static String ValorMensaje;
	// Funcion activacion
	static float fActivacion = 0.0f;
	// Error
	static float error = 1.0f;
	// Iteraciones
	int iteraciones = 0;
	static float[][] entradas = {
			// Entrada 1, Entrada 2, Umbral
			// {x1 , x2 , -1 }
			{ 1f, 1f, -1f }, { 1f, -1f, -1f }, { -1f, 1f, -1f }, { -1, -1f, -1f } };
	// salidasDeseadas para el perceptron
	float[] salidasDeseadas = new float[4];
	int establecioSalidas = 0;
	static float[] pesos = { 1.2f, -1.2f, -0.4f };
	static float factorAprendizaje = .5f;

	public static void main(String args[]) {
		new Perceptron();
	}

	Perceptron() {
		JFrame ventana = new JFrame();
		ventana.setVisible(true);
		ventana.setSize(800, 400);
		ventana.setDefaultCloseOperation(ventana.EXIT_ON_CLOSE);
		ventana.setTitle("RED NEURONAL MONOCAPA PERCEPTRON");
//		ventana.setResizable(false);
		ventana.setLocationRelativeTo(null);
		JDesktopPane esc = new JDesktopPane();
		// les asignamos una posicion a los componentes
		Recalcular.setBounds(140, 220, 100, 20);
		testear.setBounds(260, 220, 100, 20);
//		imagen.setBounds(5, 10, 500, 250);

		// ponemos a escuchar por algun evento a los botones y menuitem
		Recalcular.addActionListener(this);
		testear.addActionListener(this);
		or.addActionListener(this);
		and.addActionListener(this);

		// agregamos los 2 menuitem al menu
		compLG.add(and);
		compLG.add(or);
		// agregamos el menu al menubar
		menu.add(compLG);
		// agregamos todos los componentes al escritorio
		esc.add(Recalcular);
		esc.add(testear);
		esc.add(imagen);
		// indicamos cual es el menu y agreamos el escritorio a la ventana
		ventana.setJMenuBar(menu);
		ventana.add(esc);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == Recalcular) {
			recalcula();
		} else if (e.getSource() == testear) {
			testeo();
		}
		if (e.getSource() == or) {
			// definimos las salidas deseadas para la compuerta logica or
			salidasDeseadas[0] =  1f;
			salidasDeseadas[1] =  1f;
			salidasDeseadas[2] =  1f;
			salidasDeseadas[3] = -1f;
			establecioSalidas = 1;
		}
		if (e.getSource() == and) {
			// definimos las salidas deseadas para la compuerta logica and
			salidasDeseadas[0] =  1f;
			salidasDeseadas[1] = -1f;
			salidasDeseadas[2] = -1f;
			salidasDeseadas[3] = -1f;
			establecioSalidas = 1;
		}
	}
	
	private void recalcula() {
		if (establecioSalidas == 0) {
			JOptionPane.showMessageDialog(null,
					"Primero eliga la compuerta logica que debera aprender el perceptron");
		} else {
			System.out.println("Pesos Iniciales");
			ValorMensaje = "";
			ValorMensaje = "Pesos Iniciales \n";
			// imprimimos los pesos que definimos anteriormente
			for (int i = 0; i < pesos.length; i++) {
				System.out.println(pesos[i]);
				ValorMensaje = ValorMensaje + pesos[i] + "\n";
			}
			System.out.println("");
			ValorMensaje = ValorMensaje + "\n";
			int contador = 0;
			/*
			 * recorremos las entradas y se le van pasando a la fActivacion() esta funcion
			 * nos regresa la salida para dichas entradas. por ejemplo si elegimos la
			 * compuerta OR y se le manda la entradas: x1 = 1 , x2 = 0 la salida que nos
			 * deberia de regresa la fActivacion() es 1 esta salida se le manda al metodo
			 * error este verifica si hay o no error
			 */
			for (int i = 0; i <= entradas[0].length; i++) {
				System.out.println("ITERACION" + contador + ":");
				float fActivacion = fActivacion(entradas[i]);
				System.out.println("activacion: " + fActivacion);
				float error = error(salidasDeseadas[i]);
				System.out.println("Error: " + error);

				ValorMensaje = ValorMensaje + "ITERACIONES " + contador + "\n";
				ValorMensaje = ValorMensaje + "Activacion " + fActivacion + "\n";
				ValorMensaje = ValorMensaje + "Error " + error + "\n";

				if (error == 0f) {
					// Entra aqui si no hay error
					System.out.println("--------------------------------------");
					contador++;
				} else {
					// Si hay error, recalcula los pesos
					calculaPesos(entradas[i], salidasDeseadas[i]);
					/*
					 * ponemos i=-1 para que empiece a sacar la funcion de activacion desde el
					 * inicio con los nuevos pesos
					 */
					i = -1;
					contador = 0;
				}
			}
			JOptionPane.showMessageDialog(null, "LA RED YA ESTA ENTRENADA");
			System.out.println("Pesos Finales");
			ValorMensaje = ValorMensaje + "Pesos Finales" + "\n";
			for (int i = 0; i < pesos.length; i++) {
				System.out.println(pesos[i]);
				ValorMensaje = ValorMensaje + pesos[i] + "\n";
			}

			lanzarMensaje(ValorMensaje);

		}
		// ya una ves que el perceptron este entrenado podemos dar clic en el boton
		// testear y nos ira pidiendo las entradas
		// y nos debera de dar la salida correcta para cada entrada
	}
	
	private void testeo() {
		String x1 = JOptionPane.showInputDialog(null, "Ingresa la primera entrada");
		String x2 = JOptionPane.showInputDialog(null, "Ingresa la segunda entrada");
		float[] entradasPrueba = new float[3];
		entradasPrueba[0] = Float.parseFloat(x1); // entrada neurona 1
		entradasPrueba[1] = Float.parseFloat(x2); // entrada neurona 2
		entradasPrueba[2] = -1f; // entrada para el umbral
		float resultado = testearRed(entradasPrueba);
		JOptionPane.showMessageDialog(null, resultado);
	}
	
	public static float fActivacion(float[] entradas) {
		fActivacion = 0.0f;
		ValorMensaje = "";
		System.out.println("metodo fActivacion");
		ValorMensaje = "metodo fActivacion \n";
		for (int i = 0; i < entradas.length; i++) {

			// se multiplica cada peso por cada entrada y se suma
			fActivacion += pesos[i] * entradas[i];

			// redondeamos a 2 decimales el valor de la funcion activacion
			String val = fActivacion + "";
			BigDecimal big = new BigDecimal(val);
			big = big.setScale(2, RoundingMode.HALF_UP);
			fActivacion = big.floatValue();
			System.out.println("Multiplicacion");
			ValorMensaje = ValorMensaje + "Multiplicacion\n";
			System.out.println("w" + i + " * " + "x " + i);
			ValorMensaje = ValorMensaje + "w" + i + " * " + "x " + i + "\n";
			System.out.println(pesos[i] + "*" + entradas[i]);
			ValorMensaje = ValorMensaje + entradas[i] + "\n";

		}
		System.out.println("y = " + fActivacion);
		ValorMensaje = ValorMensaje + "y = " + fActivacion;

		lanzarMensaje(ValorMensaje);
		// se determina el valor de la salida
		if (fActivacion >= 0)
			fActivacion = 1;
		else if (fActivacion < 0)
			fActivacion = -1;

		return fActivacion;
	}

	// metodo para verificar si hay o no error
	public static float error(float salidaDeseada) {
		ValorMensaje = "";
		System.out.println("Salida deseada - salida");
		ValorMensaje = "Salida deseada - salida \n";
		error = salidaDeseada - fActivacion;
		System.out.println(salidaDeseada + " - " + fActivacion);
		ValorMensaje = salidaDeseada + " - " + fActivacion;
		lanzarMensaje(ValorMensaje);
		return error;
	}

	// metodo para el reajuste de pesos
	public void calculaPesos(float[] entradas, float salidas) {
		if (error != 0) {
			ValorMensaje = "";
			ValorMensaje = ValorMensaje + "Calcular Pesos \n";
			for (int i = 0; i < entradas.length; i++) {
				System.out.println(pesos[i] + " + (2 * .5) * " + salidas + " * " + entradas[i]);
				ValorMensaje = pesos[i] + " + (2 * .5) * " + salidas + " * " + entradas[i] + "\n";
				this.pesos[i] = pesos[i] + (2.0f * .5f) * (salidas * entradas[i]);
				String val = this.pesos[i] + "";
				BigDecimal big = new BigDecimal(val);
				big = big.setScale(2, RoundingMode.HALF_UP);
				fActivacion = big.floatValue();
				System.out.println("salida");
				ValorMensaje = "Los pesos cambiaron \n";
				System.out.println("AHORA LOS PESOS CAMBIARON A :" + this.pesos[i]);
				ValorMensaje = "AHORA LOS PESOS CAMBIARON A :" + this.pesos[i] + "\n";
			}
			lanzarMensaje(ValorMensaje);
		}
	}

	public float testearRed(float[] entradasPrueba) {
		float result;
		fActivacion = 0.0f;
		ValorMensaje = "";
		System.out.println("----------PROBANDO EL PERCEPTRON ---------");
		ValorMensaje = "----------PROBANDO EL PERCEPTRON --------- \n";
		for (int i = 0; i <= 2; i++) {
			fActivacion += pesos[i] * entradasPrueba[i];
			String val = fActivacion + "";
			BigDecimal big = new BigDecimal(val);
			big = big.setScale(2, RoundingMode.HALF_UP);
			fActivacion = big.floatValue();

			ValorMensaje = ValorMensaje + "Multiplicacion \n";
			System.out.println("Multiplicacion");
			ValorMensaje = ValorMensaje + "w" + i + " * " + "x " + i + "\n";
			System.out.println("w" + i + " * " + "x " + i);
			System.out.println(pesos[i] + "*" + entradasPrueba[i]);
			ValorMensaje = ValorMensaje + pesos[i] + "*" + entradasPrueba[i] + "\n";

			lanzarMensaje(ValorMensaje);
		}
		System.out.println("y = " + fActivacion);
		if (fActivacion >= 0)
			fActivacion = 1;
		else if (fActivacion < 0)
			fActivacion = -1;

		result = fActivacion;
		return result;
	}

	public static void lanzarMensaje(String Vars) {
		JOptionPane.showMessageDialog(null, Vars);

	}

}
