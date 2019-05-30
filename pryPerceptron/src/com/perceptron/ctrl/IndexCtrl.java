package com.perceptron.ctrl;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Label;

import com.perceptron.util.Util;

public class IndexCtrl extends GenericForwardComposer<Component> {
	private static final long serialVersionUID = 1L;
	
	Label lblFecha;
	
	public void doAfterCompose(Component comp) {
		try {
			super.doAfterCompose(comp);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error en la aplicación, por favor verifique " + e.getMessage());
		}
		lblFecha.setValue(Util.obtieneFecha(null));
	}
	
}
