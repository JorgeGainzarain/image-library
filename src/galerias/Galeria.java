package galerias;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gson.Gson;

import galerias.Galeria;
import imagenes.GestorImagenes;
import imagenes.Imagen;
import panels_and_dialogs.CreationMenu;
import threads.ImageCreationTask;
import utils.Func;


public class Galeria {
	
	private List<Imagen> originalImages = new ArrayList<Imagen>();
	private List<Imagen> imagenes = new ArrayList<Imagen>();
	private String name;
	private int numCarpetas;
	private int ID;
	
	
	static int count = 0;
	
	public Galeria(List<Imagen> imagenes)
	{
		this.setName("Aux");
		this.setImagenes(imagenes);
		this.setOriginalImages(imagenes);
	}
	
	public Galeria()
	{
		setImagenes(new ArrayList<Imagen>());
		setOriginalImages(new ArrayList<Imagen>());
		setName("Galeria vacia");
	}
	
	public Galeria(String name)
	{
		this(name,Func.rnd(2,8), Func.rnd(1,5), 0);
	}
	
	
	public Galeria(String Name, int max_carp_niv, int niv, int numImg)
	{
		File file = new File("Galerias/" + Name + "/" + Name +".txt");
		if(file.exists()) this.read(Name);
		else 
		{
			File gals = new File("Galerias");
			
			this.setID(gals.list().length);
			this.setName(Name);
			this.setNumCarpetas(GestorImagenes.CrearCarpetas(Name, max_carp_niv, niv));
			if(numImg != 0) {
				Thread thread = new Thread(new ImageCreationTask(this,numImg));
				thread.run();
			}
			this.save();
		}
	}
	
	
	public boolean save()
	{
		File file = new File("Galerias/" + name);
		File txt = new File(file.getPath() + "/" + name + ".txt");
		Gson gson = new Gson();
		
		if(txt.exists()) txt.delete();
		
		try 
		{
			txt.createNewFile();
			String JSON = gson.toJson(this);
				
			FileWriter writter = new FileWriter(txt);

			writter.write(JSON);
			writter.close();
		} 
		catch (IOException e) 
		{
			return false;
		}
		
		return true;		
	}
	
	public void read(String Name){
		Gson gson = new Gson();
		File file = new File("Galerias/" + Name + "/" + Name + ".txt");
		
		FileReader reader;
		try {
			reader = new FileReader(file);
			Galeria aux = gson.fromJson(reader, Galeria.class);
			this.setImagenes(aux.getImagenes());
			this.setName(aux.getName());
			this.setNumCarpetas(aux.getNumCarpetas());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
//		structure = new GalleryFolder(new File("Galerias/name"),0);
	}
	

	public void Analizar(File file){
		this.Analizar(file,0);
	}
	
	private void Analizar(File file, int niv){
		File[] files = file.listFiles();
		File aux;
		List<File> Dirs = new ArrayList<File>();
		for(int i = 0; i < files.length; i++)
		{
			aux = files[i];
			
			if (aux.isDirectory()) 
				{
					Dirs.add(aux);
				}
			else if(aux.isFile()) 
				{
					if(Func.getExtension(aux).compareTo("txt") != 0)
					{
						getImagenes().add(new Imagen(aux, count));
						count++;
					}
				}
			else System.out.println("Error: " + aux.getName());
		}
		
		if (Dirs.size() == 0) return;
		
		Iterator<File> it = Dirs.iterator();
		while(it.hasNext())
		{
			this.Analizar(it.next(), niv+1);
		}
	}
	
	public void removeImage(Imagen img) {
		imagenes.remove(img);
		save();
	}
	
	public void removeFilters() {
		imagenes.clear();
		imagenes.addAll(originalImages);
		save();
	}
	
	public Imagen findID(int ID){
		Iterator<Imagen> it = getImagenes().iterator();
		Imagen aux;
		while(it.hasNext())
		{
			aux = it.next();
			if(aux.getID() == ID) return aux;
		}
		return null;
	}
	
	public void poblar(int numImgs){
		if(CreationMenu.getInstance() != null) CreationMenu.getInstance().dispose();
		Thread thread = new Thread(new ImageCreationTask(this, numImgs));
		thread.start();
	}
	
	public void rename(String newName){
		getCarpeta().renameTo(new File("Galerias/" + newName));
		File txt = new File("Galerias/" + name + "/" + name + ".txt");
		txt.delete();
		
		setName(newName);
		save();
	}
	
	public List<Imagen> getImagenes() {
		return imagenes;
	}

	public void setImagenes(List<Imagen> imagenes) {
		this.imagenes = imagenes;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumCarpetas() {
		return numCarpetas;
	}

	public void setNumCarpetas(int numCarpetas) {
		this.numCarpetas = numCarpetas;
	}
	
	
	public File getCarpeta() {
		return new File("Galerias/" + name);
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}
	
	public static void main(String[] args)
	{
		new Galeria("Test",2,2,2);
	}

	public List<Imagen> getOriginalImages() {
		return originalImages;
	}

	public void setOriginalImages(List<Imagen> originalImages) {
		this.originalImages = originalImages;
	}

}
