package panels_and_dialogs;

import java.io.File;

import javax.swing.JDialog;


import galerias.Galeria;
import nuevaInterfaz.MainMenu;

@SuppressWarnings("serial")
public class LoadingDialog extends JDialog {
	
	public LoadingDialog(Galeria g) {
		super();
		setTitle("Cargando galeria " + g.getName() + "...");
		setSize(200,10);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(false);
	}
	
	public LoadingDialog(File file) {
		super();
		setTitle("Cargando " + file.getName() + " (" + file.listFiles().length + " imagenes)");
		setSize(250,10);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(false);
	}
	
	public LoadingDialog(String txt) {
		super();
		setTitle(txt);
		setSize(200,10);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(false);
	}
	
	public void start() {
		setVisible(true);
		MainMenu.getInstance().setEnabled(false);
	}
	
	public void stop() {
		dispose();
		MainMenu.getInstance().setEnabled(true);
	}
	
}
