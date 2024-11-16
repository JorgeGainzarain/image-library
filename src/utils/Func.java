package utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;



public class Func {

	static String[] Nombres = {};

	static int[] generarTelef() {
		int[] ret = new int[9];
		for (int i = 0; i < 9; i++) {
			ret[i] = (int) Math.floor(Math.random() * 100 % 10);
		}
		return ret;
	}

	static int agua() {
		int rnd = (int) Math.floor(Math.random() * 100);
		if (rnd % 3 == 0)
			return -1;
		if (rnd % 3 == 1)
			return 0;
		else
			return 1;
	}

	public static int rnd(int min, int max) {
		if(min == max) return 1;
		int dif = max - min;
		return (int) Math.floor(Math.random() * max % dif + min);
	}

	public static int rnd(int max) {
		return rnd(0, max);
	}

	public static String genNombre() {
		return getrndStringFromFile("nombres-propios-es.txt");
	}

	static String genApellido() {
		return getrndStringFromFile("apellidos-es.txt");
	}

	static String getVegetal() {
		return getrndStringFromFile("Vegetales.txt");
	}

	public static String getrndStringFromFile(String Ruta) {
		Set<String> ret = getSetStringFromFile(Ruta);
		return getRandomFromSet(ret);
	}

	public static Set<String> getSetStringFromFile(String Ruta) {
		try {
			BufferedReader lector = new BufferedReader(new FileReader("lemarios-master/" + Ruta));
			// StringBuilder cadena = new StringBuilder();
			Set<String> ret = new HashSet<String>();
			String line = null;

			@SuppressWarnings("unused")
			int i = 0;
			while ((line = lector.readLine()) != null) {
				ret.add(line);
				i++;
			}
			lector.close();
			// String contenido = cadena.toString();
			// System.out.println(contenido);
			return ret;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	static boolean crearCarpeta(File f) {
		boolean b = f.mkdirs();
		if (b)
			System.out.println("Directorio (" + f.getName() + ") creado con exito");
		else
			System.out.println("No se ha podido crear el directorio (" + f.getName() + ")");
		return b;
	}

	static boolean crearArchivo(File f) {
		boolean b = false;
		try {
			b = f.createNewFile();
			System.out.println("Archivo (" + f.getName() + ") creado con exito");
		} catch (IOException e) {
			System.out.println("No se ha podido crear el archivo (" + f.getName() + ")");
		}
		return b;

	}

	static String fillWithZeros(int num, int tam) {
		String ret = "" + num;
		while (tam - ret.length() > 0) {
			ret = "0" + ret;
			// System.out.println(ret);
		}
		return ret;
	}

	static <E> E getRandomFromSet(Set<E> s) {
		@SuppressWarnings("unchecked")
		E[] Array = (E[]) s.toArray();
		return Array[rnd(Array.length)];
	}

	public static <E> E extractRandomFromSet(Set<E> s) {
		@SuppressWarnings("unchecked")
		E[] Array = (E[]) s.toArray();
		E selec = Array[rnd(Array.length)];
		s.remove(selec);
		return selec;
	}

	static char genLetra() {
		// 97-122
		return (char) rnd(97, 122);
	}

	public static String año() {
		String año;
		double añoaux;
		añoaux = Func.rnd(0,23);
		if (añoaux < 10)
			año = "200" + (int) añoaux;
		else 
		{
			año = "20" + (int) añoaux;
		}
			
		return año;
	}

	public static String mes() {
		String mes;
		double mesaux;
		mesaux = Func.rnd(1,12);
		if ((int) mesaux < 10)
			mes = "0" + (int) mesaux;
		else
			mes = "" + (int) mesaux;
		return mes;
	}

	public static String dia() {
		String dia;
		double diaaux;
		diaaux = Func.rnd(1,29);
		if ((int) diaaux < 10)
			dia = "0" + (int) diaaux;
		else
			dia = "" + (int) diaaux;
		return dia;
	}

	public static String hora() {
		String hora;
		double horaaux;
		horaaux = Func.rnd(23);
		if ((int) horaaux < 10)
			hora = "0" + (int) horaaux;
		else
			hora = "" + (int) horaaux;
		return hora;
	}

	public static String minuto() {
		String minuto;
		double minutoAux;
		minutoAux = Func.rnd(59);
		if (minutoAux < 10)
			minuto = "0" + (int) minutoAux;
		else
			minuto = "" + (int) minutoAux;
		return minuto;
	}

	public static String segundo() {
		String segundo;
		double segundoaux;
		segundoaux = Func.rnd(59);
		if (segundoaux < 10)
			segundo = "0" + (int) segundoaux;
		else
			segundo = "" + (int) segundoaux;
		return segundo;
	}

	public static void ListarDirs(File file) {
		File files[] = file.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory())
				System.out.println("Directorio " + files[i]);
			if (files[i].isFile())
				System.out.println("Archivo " + files[i]);
		}
	}

	public static void ClearDirs(File file) {
		if (file.isFile()) {
			if(file.delete()) System.out.println(file.getName() + " borrado");
			return;
		}
		String[] hijos = file.list();
		
		if (!file.delete()) {
			// System.out.println("Recorriendo " + file.getName());
			for (int i = 0; i < hijos.length; i++) {
				File aux = new File(file + "/" + hijos[i]);
				if (!aux.delete()) {
					ClearDirs(aux);
					aux.delete();
				}
			}
		}
	}

	public static Integer[] toDate(String Fecha) {

		String[] Div1 = Fecha.replace("'", "").split(" ");
		String[] Date = Div1[0].split(":");
		String[] Hours = Div1[1].split(":");

		int Year = Integer.parseInt(Date[0]);
		int Month = Integer.parseInt(Date[1]);
		int Day = Integer.parseInt(Date[2]);

		int Hour = Integer.parseInt(Hours[0]);
		int Minute = Integer.parseInt(Hours[1]);
		int Seconds = Integer.parseInt(Hours[2]);

		Integer[] ret = { Year, Month, Day, Hour, Minute, Seconds };
		return ret;

	}

	public static ImageIcon reSize(ImageIcon icon, Dimension dim) {
		Image imagen = icon.getImage();
		int ancho = dim.width;
		int alto = dim.height;
		Image nuevaImagen = imagen.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
		return new ImageIcon(nuevaImagen);
	}

	public static MouseAdapter getHoverMouseListener(ImageIcon icono, Dimension ventanaDimension, Dimension dimIcono) {
		return new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				// Crea una nueva ventana para mostrar la imagen en grande
				JFrame ventana = new JFrame();
				ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				ventana.setSize(ventanaDimension);
				ventana.setLocationRelativeTo(null);
				ventana.setResizable(false);

				ventana.getContentPane().setBackground(Color.GRAY);
				ventana.setVisible(true);

				// Crea un JLabel con la imagen y lo agrega a la nueva ventana

				ImageIcon nuevoIcono = Func.reSize(icono, dimIcono);
				JLabel imagenGrande = new JLabel(nuevoIcono);
				ventana.getContentPane().add(imagenGrande);
			}
		};
	}
	

//
//	
	
	public static String getExtension(File file)
	{
		String nombre = file.getName();
		int indicePunto = nombre.lastIndexOf(".");
		if(indicePunto == -1)
		{
			return "";
		}
		else
		{
			return nombre.substring(indicePunto+1);
		}
	}

	public static Dimension redim(Dimension dim, double f) {
		return new Dimension((int) (dim.width * f), (int) (dim.height * f));
	}

	public static Dimension redim(Dimension dim, double f1, double f2) {
		return new Dimension((int) (dim.width * f1), (int) (dim.height * f2));
	}

}
