package galerias;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gson.Gson;

import utils.Func;

public class GestorGalerias {
	private List<Galeria> galerias;
	

	public GestorGalerias()
	{
		galerias = new ArrayList<Galeria>();
		this.update();
	}
	
	public List<Galeria> update()
	{
		File galsFolder = new File("Galerias");
		File[] gals = galsFolder.listFiles();
		Galeria aux;
		
		List<Galeria> ret = new ArrayList<Galeria>();


		if(gals.length == 0) return ret;
		
		for(int i = 0; i < gals.length; i++)
		{
			File[] hijos = gals[i].listFiles();
			for(int j = 0; j < hijos.length; j++)
			{
				if(hijos[j].isFile() & Func.getExtension(hijos[j]).compareTo("txt") == 0)
				{
					File txt = hijos[j];
//					System.out.println(txt.getName().substring(0,(int) txt.getName().length()-4) + "->" + txt.getParentFile().getName());
					
					//Comprobar que el archivo pertenece a la galeria (Mismo nombre que la carpeta)
					if(txt.exists() & txt.getName().substring(0,(int) txt.getName().length()-4).compareTo(txt.getParentFile().getName())==0)
					{
						aux = read(txt);
						if(!galerias.contains(aux) & aux != null)
						{
							System.out.println("Añadida Galeria " + aux.getName() + " numImagenes: " + aux.getImagenes().size());
							ret.add(aux);
						}
					}
				}
			}
		}
		galerias.addAll(ret);
		return ret;
	}
	
	
	public GestorGalerias(List<Galeria> gals) {
		galerias = gals;
	}
	
	public Galeria read(File file)
	{
		Gson gson = new Gson();

		FileReader reader;
		try {
			reader = new FileReader(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			return null;
		}
		return gson.fromJson(reader, Galeria.class);
	}
	
	public boolean addGaleria(Galeria gal)
	{
		if(!galerias.contains(gal)) galerias.add(gal);
		else return false;
		
		return true;
	}
	
	public Galeria createEmptyGallery(String nombre, int maxCarp, int maxNiv)
	{
		Galeria ret = new Galeria(nombre, maxCarp, maxNiv, 0);
		galerias.add(ret);
		return ret;
	}
	
	public Galeria createGaleria(String nombre, int maxCarp, int maxNiv)
	{	
		Galeria ret = new Galeria(nombre, maxCarp, maxNiv, Func.rnd(1000));
		galerias.add(ret);
		return ret;
	}
	
	public void poblarGaleria(String Nombre, int num)
	{
		//getGaleriaNamed(Nombre).poblarGaleria(num);
	}
	
	public Galeria getGaleriaNamed(String nombre)
	{
		Iterator<Galeria> it = galerias.iterator();
		Galeria aux;
		while(it.hasNext())
		{
			aux = it.next();
			if(aux.getName().compareTo(nombre)==0) return aux; 
		}
		return null;
	}
	
	
	public Galeria getGaleriaWithID(int ID)
	{
		Iterator<Galeria> it = galerias.iterator();
		Galeria aux;
		while(it.hasNext())
		{
			aux = it.next();
			if(aux.getID()==ID) return aux;
		}
		return null;
	}
	
	public List<Galeria> getGalerias() {
		return galerias;
	}

	public void setGalerias(List<Galeria> galerias) {
		this.galerias = galerias;
	}
}
