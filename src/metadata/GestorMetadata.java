package metadata;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.imaging.ImageFormats;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.ImageWriteException;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.ImageMetadata;
import org.apache.commons.imaging.formats.jpeg.iptc.IptcTypes;
import org.apache.commons.imaging.formats.tiff.TiffImageMetadata;
import org.apache.commons.imaging.formats.tiff.fieldtypes.FieldType;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfo;
import org.apache.commons.imaging.formats.tiff.write.TiffImageWriterLossy;
import org.apache.commons.imaging.formats.tiff.write.TiffOutputDirectory;
import org.apache.commons.imaging.formats.tiff.write.TiffOutputField;
import org.apache.commons.imaging.formats.tiff.write.TiffOutputSet;

import imagenes.Imagen;
import utils.Func;

public class GestorMetadata {
	public static TagInfo dateTag = createTagInfo(IptcTypes.DATE_CREATED,FieldType.ASCII);
	
	static TagInfo createTagInfo(IptcTypes Iptctype, FieldType type)
	{
		return new TagInfo(Iptctype.name, Iptctype.type, type);
	}
	
	
	public static File setupTiffWithMetadata(String ruta, BufferedImage imagen)
	{
//		System.out.println("Ruta: " + ruta);
	      File archivo = new File(ruta);
	      File aux = new File("AuxImage.Tiff");
	      aux.deleteOnExit();
	      
//	      count++;

	         try {
	        	if(!archivo.exists()) archivo.createNewFile();
	        	if(!aux.exists()) aux.createNewFile();
	        	
				Imaging.writeImage(imagen, aux, ImageFormats.TIFF);
				addFields(aux, archivo);
			
	         } catch (ImageWriteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			 } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			 } catch (ImageReadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			 } 	
	         aux.delete();
	         return archivo;
	}
	
    private static void addFields(final File file, final File dest) throws ImageReadException, IOException, ImageWriteException
    {
    	ImageMetadata metadata = Imaging.getMetadata(file);
    	FileOutputStream fos = new FileOutputStream(dest);
        OutputStream os = new BufferedOutputStream(fos);
    	//JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;
    	TiffImageMetadata exif = (TiffImageMetadata) metadata;
    	TiffOutputSet outputSet = exif.getOutputSet();
    	TiffOutputDirectory rootDir = outputSet.getRootDirectory();
    	
    	//Custom
    	String date = Func.año() + ":" + Func.mes() + ":" + Func.dia() + " " + Func.hora() + ":" + Func.minuto() + ":" + Func.segundo();
    	byte[] value = date.getBytes();
    	rootDir.add(new TiffOutputField(dateTag, FieldType.ASCII, value.length, value));
    	value = "Test".getBytes();
    	//rootDir.add(new TiffOutputField(creationDateTag,FieldType.ASCII, value.length, value));
    	
    	//Gps
   
        final double longitude = Math.floor(Math.random()*180-90);
        final double latitude = Math.floor(Math.random()*360-180);
    	
    	outputSet.setGPSInDegrees(latitude, longitude);
    	
        
        
        //Writers
            
    	Imaging.writeImage(Imaging.getBufferedImage(file), dest, ImageFormats.TIFF);
    	
    	new TiffImageWriterLossy().write(os, outputSet);
    	
    	os.close();
    }
    
    public static File setFields(Imagen img) throws ImageReadException, IOException, ImageWriteException
    {
//		System.out.println("Ruta: " + ruta);
	      File archivo = img.getFile();
	      File aux = new File("AuxImage.Tiff");
	      aux.deleteOnExit();
	      
//	      count++;

	         try {
	        	if(!archivo.exists()) archivo.createNewFile();
	        	if(!aux.exists()) aux.createNewFile();
	        	
				Imaging.writeImage(img.getImage(), aux, ImageFormats.TIFF);
				setFields(aux, archivo, img);
			
	         } catch (ImageWriteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			 } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			 } catch (ImageReadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			 } 	
	         aux.delete();
	         return archivo;
    }
    
    public static void setFields(final File file, final File dest, Imagen img) throws ImageReadException, IOException, ImageWriteException
    {
    	ImageMetadata metadata = Imaging.getMetadata(file);
    	FileOutputStream fos = new FileOutputStream(dest);
        OutputStream os = new BufferedOutputStream(fos);
    	//JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;
    	TiffImageMetadata exif = (TiffImageMetadata) metadata;
    	TiffOutputSet outputSet = exif.getOutputSet();
    	TiffOutputDirectory rootDir = outputSet.getRootDirectory();
    	
    	//Custom
    	String date = img.getDate();
    	byte[] value = date.getBytes();
    	rootDir.add(new TiffOutputField(dateTag, FieldType.ASCII, value.length, value));
    	value = "Date".getBytes();
    	//rootDir.add(new TiffOutputField(creationDateTag,FieldType.ASCII, value.length, value));
    	
    	//Gps    	
    	int lat = img.getLoc().getLatitude();
    	int lon = img.getLoc().getLongitude();
    	
    	double latitude = Math.signum(lat);
    	double longitude = Math.signum(lon);
    	outputSet.setGPSInDegrees(latitude, longitude);
    		
        
        //Writers
            
    	Imaging.writeImage(Imaging.getBufferedImage(file), dest, ImageFormats.TIFF);
    	
    	new TiffImageWriterLossy().write(os, outputSet);
    	
    	os.close();
    }
    
}
