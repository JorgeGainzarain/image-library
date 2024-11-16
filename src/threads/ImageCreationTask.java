package threads;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

import galerias.Galeria;
import imagenes.Imagen;
import nuevaInterfaz.MainMenu;
import utils.Func;

public class ImageCreationTask implements Runnable {
	
	Galeria currGallery;
	private List<Imagen> images;
	
	int numImages;
	
	private static int numCarpetas;
	private static int total;
	private static int im = 0;
	static int NumDirs = 1;
	static int DirCompletado = 0;
	static int aux;
	static int counter = 0;
	
	JProgressBar progressBar = new JProgressBar();
	JDialog dialog;
	
	public ImageCreationTask(Galeria g, int num) {
		super();
		currGallery = g;
		numImages = num;
	}
	
	@Override
    public void run() {
        // Simula la creación de imágenes
		JTabbedPane tabbedPane = MainMenu.getInstance().getTabbedPane();
		
		int tab = tabbedPane.getSelectedIndex();
		
		progressBar.setStringPainted(true);
		
		im = currGallery.getImagenes().size();
		dialog = new JDialog(MainMenu.getInstance());
		dialog.setTitle("Creando imagenes (0/" + numImages + ")");
    	dialog.add(progressBar);
    	dialog.pack();
    	dialog.setSize(250,70);
    	dialog.setLocationRelativeTo(null);
    	dialog.setVisible(true);
    	
    	MainMenu.getInstance().setEnabled(false);
    	
    	images = crearImagenes(currGallery.getCarpeta(), numImages, currGallery.getNumCarpetas());
    	currGallery.getImagenes().addAll(images);
    	currGallery.getOriginalImages().addAll(images);
    	
    	dialog.setTitle("Aplicando cambios");
    	
        currGallery.save();
        MainMenu menu = MainMenu.getInstance();
        if(menu.getCurrGallery()!= null) menu.updateTable();
        else 
        {
        	menu.update();
        	menu.revalidate();
       	}
		
		
		progressBar.setValue(100);
		
		SwingUtilities.invokeLater(() -> dialog.dispose());
		MainMenu.getInstance().setEnabled(true);
		
		tabbedPane.setSelectedIndex(tab);
		JOptionPane.showMessageDialog(null, "Imagenes creadas con éxito.");
        
    }
	
	public List<Imagen> crearImagenes(File file, int numImg, int numCarp)
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
		
		numCarpetas = numCarp;
		total = numImg;	
		aux = 0;
		counter = 0;
		
		crearImagenes(file,0, ret, numImgs);
		
//		progressDialog.dispose();

		return ret;
	}
	private void crearImagenes(File file, int niv, List<Imagen> ret, List<Integer> NumImgs)
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
//		    MainMenu.getInstance().getProgressBar().setValue((int) exactPercent);
		    if(exactPercent == 100) exactPercent = 99;
		    progressBar.setValue((int) exactPercent);
		    dialog.setTitle("Creando imagenes (" + counter + "/" + total + ")");
		    
		  
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

	public List<Imagen> getImages() {
		return images;
	}

	public void setImages(List<Imagen> images) {
		this.images = images;
	}

}
