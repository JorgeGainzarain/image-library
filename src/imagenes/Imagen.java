package imagenes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Set;

import javax.swing.ImageIcon;

import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.ImageMetadata;
import org.apache.commons.imaging.formats.tiff.TiffImageMetadata;
import org.apache.commons.imaging.formats.tiff.constants.GpsTagConstants;
import org.apache.commons.imaging.formats.tiff.constants.TiffTagConstants;

import metadata.GestorMetadata;
import utils.Date;
import utils.Func;
import utils.Location;


public class Imagen 
{
	private String ruta;
	private String name;
	private Date date;
	
	private Dimension size;
	private Location loc;
	private int ID;
	
	static int count = 0;
	
	public Imagen(String ruta)
	{
		this(new File(ruta),count);
		count++;
	}
	
	public Imagen(File file)
	{
		this(file.getPath());
	}
	
	
	public Imagen (String ruta, int iD)
	{
		this(ruta, iD, Func.extractRandomFromSet(names));
	}
	
	public Imagen (File file, int iD)
	{
		this(file.getPath(),iD, Func.extractRandomFromSet(names));
	}
	
	public Imagen(File file, String name)
	{
		this(file.getPath(),count, name);
		count++;
	}
	
	public Imagen(String Ruta, int iD, String name)
	{
		File file = new File(Ruta);
		
		this.name = name;
		
		try
		{
			if(!file.exists() || file.isDirectory())
			{
				file = crearImagen(Ruta, name, true);
//				file.createNewFile();
			}
				
			ImageMetadata metadata = Imaging.getMetadata(file);
			TiffImageMetadata tiffmtd = (TiffImageMetadata) metadata;
			int width = (tiffmtd.findField(TiffTagConstants.TIFF_TAG_IMAGE_WIDTH).getIntValue());
			int height = (tiffmtd.findField(TiffTagConstants.TIFF_TAG_IMAGE_LENGTH).getIntValue());
			String latitude = tiffmtd.findField(GpsTagConstants.GPS_TAG_GPS_LATITUDE).getValueDescription();
			char lat_Ref = tiffmtd.findField(GpsTagConstants.GPS_TAG_GPS_LATITUDE_REF).getStringValue().charAt(0);
			String longitude = tiffmtd.findField(GpsTagConstants.GPS_TAG_GPS_LONGITUDE).getValueDescription();
			char long_Ref = tiffmtd.findField(GpsTagConstants.GPS_TAG_GPS_LONGITUDE_REF).getStringValue().charAt(0);
			
			size = new Dimension(width,height);
			loc = new Location(latitude, lat_Ref, longitude, long_Ref);
			ID = iD;
			date = new Date(tiffmtd.findField(GestorMetadata.dateTag).getValueDescription());
			ruta = file.getPath();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	public String getCoords()
	{
        return loc.toString();
    }
	
	
	private static Set<String> names = Func.getSetStringFromFile("lemario-general-del-espanol.txt");
//	private static int count;
	
	
	public static File crearImagen(String Ruta, String name, boolean Abstract)
	{
		
		  int aaux = (int) Func.rnd(500, 1000);
		  int baux = (int) Func.rnd(500, 1000);
	      int ancho = aaux;
	      int alto = baux;
	      BufferedImage imagen = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);
	      
	      Graphics2D g2d = imagen.createGraphics();

	      if (Abstract) imagen = AbstractImage(imagen,g2d,ancho,alto);
	      //else imagen = RealImage(imagen,g2d,ancho,alto)
	      
	      
	      String ruta = Ruta + "/" + name + ".Tiff";
	      
	      File archivo = GestorMetadata.setupTiffWithMetadata(ruta, imagen); 
	      return archivo;
	}
	
	public BufferedImage getImage() {
		try {
			return Imaging.getBufferedImage(new File(ruta));
		} catch (ImageReadException | IOException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}
	
	public ImageIcon getIcon(Dimension d) {
		return Func.reSize(new ImageIcon(getImage()), d);
	}
	
	
	public static void main(String[] args)
	{
		new Imagen("TestImages",0);
	}
	
	
	
	
	private static BufferedImage AbstractImage(BufferedImage image, Graphics2D g2d, int ancho, int alto)
	{
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	      
		   // Dibujar un fondo gradiente aleatorio
		      Color colorFondo1 = new Color((int)(Math.random()*256), (int)(Math.random()*256), (int)(Math.random()*256));
		      Color colorFondo2 = new Color((int)(Math.random()*256), (int)(Math.random()*256), (int)(Math.random()*256));
		      g2d.setPaint(new GradientPaint(0, 0, colorFondo1, ancho, alto, colorFondo2));
		      g2d.fillRect(0, 0, ancho, alto);

		   // Dibujar un patrón aleatorio de formas geométricas
		      Random RandomF = new Random();
		      int tamañoFormas = (int)Math.floor(Math.random()*100%100+25);
		      int numFormas = (int)Math.floor(Math.random()*100%10+10);
		      for (int i = 0; i < numFormas; i++) {
		          int x = RandomF.nextInt(ancho);
		          int y = RandomF.nextInt(alto); 
		          int tipoForma = RandomF.nextInt(3);
		          switch(tipoForma) {
		              case 0: // Rectángulo
		                  g2d.setColor(Color.getHSBColor(RandomF.nextFloat(), 1.0f, 1.0f));
		                  g2d.fillRect(x, y, tamañoFormas, tamañoFormas);
		                  break;
		              case 1: // Círculo
		                  g2d.setColor(Color.getHSBColor(RandomF.nextFloat(), 1.0f, 1.0f));
		                  g2d.fillOval(x, y, tamañoFormas, tamañoFormas);
		                  break;
		              case 2: // Triángulo
		                  g2d.setColor(Color.getHSBColor(RandomF.nextFloat(), 1.0f, 1.0f));
		                  Path2D.Double path = new Path2D.Double();
		                  path.moveTo(x, y);
		                  path.lineTo(x + tamañoFormas, y);
		                  path.lineTo(x + tamañoFormas/2, y + tamañoFormas);
		                  path.closePath();
		                  g2d.fill(path);
		                  break;
		          }
		      }

		      
		      
		   // Dibujar un patrón de estrellas
		      Random random = new Random();
		      int tamañoEstrellas = 10;
		      int x1 = 0, y1 = 0;
		      int numPolig = (int)Math.floor(Math.random()*100%10+5);
		      Path2D.Double path = new Path2D.Double();
		      for (int i = 0; i < numPolig; i++) {
		          int x = random.nextInt(ancho);
		          int y = random.nextInt(alto);
		          g2d.setStroke(new BasicStroke(5));
		          if (i == 0) {
		              path.moveTo(x, y);
		          } else {
		              Color color1 = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
		              Color color2 = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
		              GradientPaint gradientPaint = new GradientPaint(x1, y1, color1, x, y, color2);
		              g2d.setPaint(gradientPaint);
		              g2d.drawLine(x1, y1, x, y);
		              g2d.drawLine(x1 + 5, y1, x + 5, y);
		              g2d.drawLine(x1 + 5, y1 + 10, x + 5, y + 10);
		              g2d.drawLine(x1, y1 + 5, x, y + 5);
		              path.lineTo(x, y);
		              path.lineTo(x + 10, y + 10);
		              path.lineTo(x1 + 10, y1 + 10);
		              g2d.fillOval(x, y, tamañoEstrellas, tamañoEstrellas);
		          }
		          x1 = x;
		          y1 = y;
		      }
		      path.closePath();
		      Color colorFigura = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256), 200);
		      g2d.setColor(colorFigura);
		      g2d.fill(path); 
		      return image;
	}	
	
	public String getRuta() {
		return ruta;
	}
	public void setRuta(String Ruta) {
		ruta = Ruta;
	}
	public String getName() {
		return name;
	}
	public void setName(String Name) {
		name = Name;
	}
	
	public String getDateR() {
		return date.getDate();
	}
	
	public Date getDateO() {
		return date;
	}
	
	public Date getTime() {
		return new Date(0,0,0,date.getHour(), date.getMinute(), date.getSeconds());
	}
	
	public String getHour() {
		return date.getTime();
	}
	
	public String getDate() {
		return date.toString();
	}
	public void setDate(String date) {
		this.date = new Date(date);;
	}
	public Dimension getSize() {
		return size;
	}
	public void setSize(Dimension Size) {
		this.size = Size;
	}
	public Location getLoc() {
		return loc;
	}
	public void setLoc(Location Loc) {
		this.loc = Loc;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}

	public int getWidth() {
		// TODO Auto-generated method stub
		return this.getSize().width;
	}
	public int getHeight() {
		return this.getSize().height;
	}

	public String getLongitude() {
		int lon = this.getLoc().getLongitude();
		return lon + "º";
	}

	public char getLongitudeRef() {
		// TODO Auto-generated method stub
		return this.getLoc().getLongitude_Ref();
	}
	
	public String getLatitude() {
		int lat = this.getLoc().getLatitude();
		return lat + "º";
	}
	
	public char getLatitudeRef() {
		return this.getLoc().getLatitude_Ref();
	}
	
	public File getFile()
	{
		return new File(ruta);
	}
	
}
