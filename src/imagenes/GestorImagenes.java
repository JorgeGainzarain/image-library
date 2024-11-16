package imagenes;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import galerias.Galeria;
import utils.Func;

public class GestorImagenes {
	private static int total;
	private static int im = 0;
	
	private static int numCarpetas;
	static int NumDirs = 1;
	static int DirCompletado = 0;
	static int aux;
	static int counter = 0;
	
	public static int CrearCarpetas(String ruta,int max_carp_niv, int num_niv)
	{	
		System.out.println("Creando carpetas: " + ruta);
		numCarpetas = 0;
		String Ruta = "Galerias/" + ruta;
		File Images = new File(Ruta);
		if(Images.exists())
		{
			if(!Images.delete()) 
			{
				Func.ClearDirs(Images);
			}
		}
		else Images.mkdirs();	
		
		System.out.println("Max: " + max_carp_niv + " max niv: " + num_niv);
		CrearCarpetasRecursiv(max_carp_niv,num_niv, Ruta, 0);
		return numCarpetas;
	}	
	private static void CrearCarpetasRecursiv(int max_carp_niv,int num_niv_restantes,String Ruta, int num_niv)
	{
		if(numCarpetas > 997)
		{
			System.out.println("ERROR, SE HAN CREADO" + numCarpetas +"carpetas, se ha limitado el numero a 1000 para evitar problemas de rendimiento");
			return;
		}
		
		int carp_niv = (int) (Func.rnd(1, max_carp_niv));
		System.out.println("Carp niv: " + carp_niv);
		if(carp_niv == 0 || num_niv_restantes <= 0) return;
		
		String ruta = "";
		File carpeta;
		int niv_res = num_niv_restantes - 1;
		for(int i = 0; i < carp_niv; i++)
		{
			ruta = Ruta;
	    	if(num_niv == 0) ruta = Ruta + "/Dir" + i;
	    	if(num_niv == 1) ruta = Ruta + "/SubDir" + i;
	    	if(num_niv == 2) ruta = Ruta + "/" + Func.genNombre();
	    	if(num_niv == 3) ruta = Ruta + "/" + Func.getrndStringFromFile("lemario-general-del-espanol.txt");
	    	if(num_niv > 3) ruta = Ruta + "/Num_" + i;
			carpeta = new File(ruta);
			if(carpeta.mkdirs()) //System.out.println("Creada carpeta " + carpeta.getAbsolutePath());
			numCarpetas++;
			System.out.println("Carpeta creada");
			if(niv_res != 0) CrearCarpetasRecursiv(max_carp_niv,niv_res, ruta, num_niv + 1); 
		}
	}
	
	public static List<Imagen> crearImagenes(File file, int numImg, int numCarp)
	{
		List<Imagen> ret = new ArrayList<Imagen>();
		List<Integer> numImgs = new ArrayList<Integer>();

		if(numImg > numCarp)
		{
			int ImgRest = numImg;
			
			int totalAux = 0;
			
			int i = 0;
			
			int part = (int) (ImgRest / (numCarp));
			
		    while (totalAux + part < numImg) {
		        // Calcular la parte actual con un margen
		        
		        int selec = part;
		        selec += part * Func.rnd(-40, 40)/100;

		        // Asegurarse de que la parte no sea mayor que el número restante
		        selec = Math.min(selec, ImgRest);

		        numImgs.add(selec);
		        ImgRest -= selec;
		        
		        totalAux += selec;
		        i++;
		        part = (int) (ImgRest / (numCarp - i));
		    }
			
			
			int rest = numCarp - numImgs.size();
			for(int j = 0; j < rest; j++)
			{
				int add = ImgRest/rest;
				numImgs.add(add);
				totalAux += add;
				
			}
		}
		else 
		{
			for(int i = 0; i < numImg; i++)
			{
				numImgs.add(1);
			}
		}
		
//	    // Crear instancia de ProgressDialog
//	    ProgressDialog progressDialog = new ProgressDialog(null);
//	    progressDialog.setVisible(true);
//
//	    // Establecer máximo valor de la JProgressBar
//	    progressDialog.setProgressMax(numImg);
		
		
		numCarpetas = numCarp;
		total = numImg;	
		aux = 0;
		counter = 0;
		
		crearImagenes(file,0, ret, numImgs);
		
//		progressDialog.dispose();

		
		return ret;
	}
	private static void crearImagenes(File file, int niv, List<Imagen> ret, List<Integer> NumImgs)
	{
		if(niv == 0) 
		{
			System.out.println("Creando imagenes " + total + " imagenes en " + numCarpetas + " carpetas...");
		}
		
		//Elegir un numero aleatorio de imagenes que crear
		
		
		int rnd = 0;
		if(niv != 0)
		{
			if(NumImgs.size()!=0)
			{
				int index = Func.rnd(NumImgs.size());
				rnd = NumImgs.get(index);
				NumImgs.remove(index);
			}
			else return;
		}
	
		
	    aux += rnd;
		
		//Crear Imagenes en el directorio
		for(int i = 0; i < rnd; i++)
		{
			ret.add(new Imagen(file, im));
			im++;
			counter++;
		    double exactPercent = (double) counter / total * 100;
		    System.out.println((int)exactPercent + "%" + " (" + counter + "/" + total + ")");
		  
		}
		
		//Seleccionar Directorios hijos
		File files[] = file.listFiles();
		if(files == null) return;
		List<File> Dirs = new ArrayList<File>();
		for(int i = 0; i < files.length; i++)
		{
			if(files[i].isDirectory())
				{
					Dirs.add(files[i]);
				}
		}
		
		
		//Si no hay mas directorios que recorrer acaba
		if (niv == 0) NumDirs = Dirs.size();
		
		//Llamadas recursivas en los directorios
		Iterator<File> it = Dirs.iterator();
		while(it.hasNext())
		{
			crearImagenes(it.next(),niv+1,ret,NumImgs);	
		}
		
		if(niv == 1) 
		{
			DirCompletado++;
		}
	}
		
	
	    public static void sortByDate(Galeria galeria, boolean reversed) {
	        if (reversed)
	            Collections.sort(galeria.getImagenes(), getDateComparator().reversed());
	        else
	            Collections.sort(galeria.getImagenes(), getDateComparator());
	    }

	    public static void sortByWidth(Galeria galeria, boolean reversed) {
	        if (reversed)
	            Collections.sort(galeria.getImagenes(), getWidthComparator().reversed());
	        else
	            Collections.sort(galeria.getImagenes(), getWidthComparator());
	    }

	    public static void sortByLength(Galeria galeria, boolean reversed) {
	        if (reversed)
	            Collections.sort(galeria.getImagenes(), getHeightComparator().reversed());
	        else
	            Collections.sort(galeria.getImagenes(), getHeightComparator());
	    }

	    public static void sortBySize(Galeria galeria, boolean reversed) {
	        if (reversed)
	            Collections.sort(galeria.getImagenes(), getSizeComparator().reversed());
	        else
	            Collections.sort(galeria.getImagenes(), getSizeComparator());
	    }

	    public static void filterMoreLength(Galeria galeria, int num) {
	        galeria.getImagenes().removeIf(n -> n.getSize().getHeight() > num);
	    }

	    public static void filterLessLength(Galeria galeria, int num) {
	        galeria.getImagenes().removeIf(n -> n.getSize().getHeight() < num);
	    }

	    public static void filterSameLength(Galeria galeria, int num) {
	        galeria.getImagenes().removeIf(n -> n.getSize().getHeight() == num);
	    }

	    public static void filterMoreWidth(Galeria galeria, int num) {
	        galeria.getImagenes().removeIf(n -> n.getSize().getWidth() > num);
	    }

	    public static void filterLessWidth(Galeria galeria, int num) {
	        galeria.getImagenes().removeIf(n -> n.getSize().getWidth() < num);
	    }

	    public static void filterSameWidth(Galeria galeria, int num) {
	        galeria.getImagenes().removeIf(n -> n.getSize().getWidth() == num);
	    }
	    

	    public static void filterMoreDate(Galeria galeria, String date) {
	        galeria.getImagenes().removeIf(n -> compareDate(Func.toDate(n.getDate()), Func.toDate(date)) < 0);
	    }

	    public static void filterLessDate(Galeria galeria, String date) {
	        galeria.getImagenes().removeIf(n -> compareDate(Func.toDate(n.getDate()), Func.toDate(date)) > 0);
	    }

	    public static void filterSameDate(Galeria galeria, String date) {
	        galeria.getImagenes().removeIf(n -> compareDate(Func.toDate(n.getDate()), Func.toDate(date)) == 0);
	    }

	    public static void filterFromYear(Galeria galeria, int year) {
	        galeria.getImagenes().removeIf(n -> Func.toDate(n.getDate())[0] == year);
	    }

	    public static void filterFromMonth(Galeria galeria, int month) {
	        galeria.getImagenes().removeIf(n -> Func.toDate(n.getDate())[1] == month);
	    }

	    public static void filterFromDay(Galeria galeria, int day) {
	        galeria.getImagenes().removeIf(n -> Func.toDate(n.getDate())[2] == day);
	    }
	    
		public static int compareDate(Integer[] Date1, Integer[] Date2) {
			for (int i = 0; i < Date1.length; i++) {
				if (Date1[i] > Date2[i])
					return 1;
				else if (Date1[i] < Date2[i])
					return -1;
			}
			return 0;
		}

	    public static void filterFromYearMonth(Galeria galeria, int year, int month) {
	        galeria.getImagenes().removeIf(n -> Func.toDate(n.getDate())[0] == year && Func.toDate(n.getDate())[1] == month);
	    }

	    public static void filterFromYearMonthDay(Galeria galeria, int year, int month, int day) {
	        galeria.getImagenes().removeIf(n -> Func.toDate(n.getDate())[0] == year && Func.toDate(n.getDate())[1] == month && Func.toDate(n.getDate())[2] == day);
	    }
	    
		public static Comparator<Imagen> getSizeComparator() {
			return new Comparator<Imagen>() {
				@Override
				public int compare(Imagen o1, Imagen o2) {
					return Integer.compare((int) (o1.getSize().getWidth() + o1.getSize().getHeight()),(int) (o2.getSize().getWidth() + o2.getSize().getHeight()));
				};
			};
		}

		public static Comparator<Imagen> getWidthComparator() {
			return new Comparator<Imagen>() {
				@Override
				public int compare(Imagen o1, Imagen o2) {
					return Integer.compare((int) (o1.getSize().getWidth()),(int) (o2.getSize().getWidth()));
				};
			};
		}

		public static Comparator<Imagen> getHeightComparator() {
			return new Comparator<Imagen>() {
				@Override
				public int compare(Imagen o1, Imagen o2) {
					return Integer.compare((int) (o1.getSize().getHeight()),(int) (o2.getSize().getHeight()));
				};
			};
		}

		public static Comparator<Imagen> getDateComparator() {
			return new Comparator<Imagen>() {
				@Override
				public int compare(Imagen o1, Imagen o2) {
					return compareDate(Func.toDate(o1.getDate()), Func.toDate(o2.getDate()));
				};
			};
		}
		
		public static void main(String[] args)
		{
			File file = new File("TestFolder");
			if(!file.exists()) file.mkdirs();
			for(int i = 0; i < 100; i++)
			{
				Imagen.crearImagen("TestFolder", "Imagen " + i, true);
			}
		}
		public static int getIm() {
			return im;
		}
		public static void setIm(int im) {
			GestorImagenes.im = im;
		}

}
