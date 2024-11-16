package nuevaInterfaz;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import imagenes.Imagen;
import utils.Func;

@SuppressWarnings("serial")
public class Explorador extends JPanel {
    
    static int  iter = 0;
    
    List<Imagen> imagenes = new ArrayList<Imagen>();
    Dimension imagesSize;
    public int numElem;
    public static Dimension ventDim = Func.redim(Toolkit.getDefaultToolkit().getScreenSize(), 0.7, 0.9);

    private File file;
    
    public Explorador(File file) {
        super();
        this.setFile(file);
        numElem = 0;
        this.setLayout(new GridLayout(0,8));

        Recorrer(file);
    }
         
    public void Recorrer(File file )
    {
        File[] hijos = file.listFiles();
        List<File> dirs = new ArrayList<File>();
        List<File> imgs = new ArrayList<File>();
        
        for(int i = 0; i < hijos.length; i++)
        {
        	System.out.println(hijos[i]);
        	if (hijos[i].isDirectory()) dirs.add(hijos[i]);
        	else if(hijos[i].isFile()) 
        		{
        			if(Func.getExtension(hijos[i]).compareTo("txt") != 0) imgs.add(hijos[i]);
        		}
        }
//        System.out.println("Dirs: " + dirs + "\nImgs: " + imgs);
        
        if(dirs.size()!=0)dirs.forEach(x->this.addCarpeta(x));
        if(imgs.size()!=0)imgs.forEach(x->this.addImage(x));
        
        JPanel addPanel = new JPanel(new BorderLayout());
        
        JLabel cross = new JLabel("+");
        cross.setFont(new Font("Arial", Font.PLAIN, 40));
        cross.setHorizontalAlignment(SwingConstants.CENTER);
        cross.setVerticalAlignment(SwingConstants.CENTER);
        
        addPanel.add(cross, BorderLayout.CENTER);
        addPanel.setPreferredSize(new Dimension(100,100));
        addPanel.setBorder(new LineBorder(Color.BLACK));
        cross.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		System.out.println("Add Button pressed");
        		MyInputDialog dialog = new MyInputDialog(MainMenu.getInstance(), "Nombre:");
                dialog.getPanel().removeAll();
                dialog.addTextLabel();
                
                JRadioButton imagen = new JRadioButton("Imagen");
                JRadioButton carpeta = new JRadioButton("Carpeta");
                
                
                imagen.addChangeListener(new ChangeListener() {

					@Override
					public void stateChanged(ChangeEvent e) {
						carpeta.setSelected(!imagen.isSelected());
					}
                	
                });
                carpeta.addChangeListener(new ChangeListener() {

					@Override
					public void stateChanged(ChangeEvent e) {
						imagen.setSelected(!carpeta.isSelected());
					}
                	
                });
                
                dialog.getPanel().add(imagen);
                dialog.getPanel().add(carpeta);
                
                
                dialog.addButtons();
        		
        		String name = dialog.showDialog();
        		
        		MainMenu menu = MainMenu.getInstance();
        		
        		if(name != null)
        		{
            		if(imagen.isSelected()) 
            		{
            			System.out.println("Path: " + file.getPath());
            			menu.getCurrGallery().getImagenes().add(new Imagen(file, name));
            		}
            		else
            		{
            			new File(file.getPath() + "/" + name).mkdirs();
            			menu.getCurrGallery().setNumCarpetas(menu.getCurrGallery().getNumCarpetas()+1);
            		}
            		
            		menu.updateExplorer(file);
            		menu.getCurrGallery().save();
        		}

        	}
        });
        
        add(addPanel);
        
        
        while(numElem < 48)
        {
        	addFiller();
        }
        
    }
    
    public void addImage(File file)
    {
    	numElem++;
    	Dimension iconDim = new Dimension(100, 100);
    	
        Imagen img = new Imagen(file,file.getName());
        imagenes.add(img);
        ImageIcon icono = img.getIcon(iconDim);
        JLabel labelImagen = new JLabel();
        labelImagen.setIcon(icono);
        String txt = img.getName();
        int txtSize = 11;
        if (txt.length() > 17)
            txt = txt.substring(0, 14) + ".Tiff";
        labelImagen.setText(txt);
        labelImagen.setHorizontalTextPosition(JLabel.CENTER);
        labelImagen.setVerticalTextPosition(JLabel.TOP);
        labelImagen.setFont(new Font("Arial", Font.PLAIN, txtSize));
        labelImagen.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        
        labelImagen.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		MainMenu.getInstance().addVisualizer(imagenes);
        		Visualizer vis = MainMenu.getInstance().getVis();
        		int num = 0;
        		int count = 0;
        		
        		for (Imagen image : imagenes) {
        			if(image.getID() == img.getID()) num = count;
        			count++;
        		}
        		vis.visualize(imagenes.get(num));
        	}
        });
        add(labelImagen);
        iter++;
    }
    
    public void addCarpeta(File file)
    {
    	numElem++;
    	Dimension CarpDim = new Dimension(100, 100);


        ImageIcon icono = new ImageIcon("Icons/folder.jpg");
        icono = Func.reSize(icono, CarpDim);
        JLabel labelImagen = new JLabel();
        labelImagen.setIcon(icono);
        String txt = file.getName();
        int txtSize = 11;
        if (txt.length() > 17) txt = txt.substring(0, 14) + ".Tiff";
        labelImagen.setText(txt);
        labelImagen.setHorizontalTextPosition(JLabel.CENTER);
        labelImagen.setVerticalTextPosition(JLabel.TOP);
        labelImagen.setFont(new Font("Arial", Font.PLAIN, txtSize));
        labelImagen.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        labelImagen.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		MainMenu.getInstance().updateExplorer(file);
        	}
        });
        add(labelImagen);
        iter++;
    }

    public void addFiller()
    {
    	numElem++;
    	Dimension FillerDim = new Dimension(100, 100);

        JLabel labelFiller = new JLabel();
        labelFiller.setPreferredSize(FillerDim);
        
        add(labelFiller);
        iter++;
    }
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		JScrollPane scrollPane = new JScrollPane(new Explorador(new File("TestFolder")));
		
		frame.getContentPane().add(scrollPane);
		frame.setVisible(true);
		frame.setSize(850, 900);
		frame.setLocationRelativeTo(null);
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
}
