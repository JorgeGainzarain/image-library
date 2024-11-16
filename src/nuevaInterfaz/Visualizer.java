package nuevaInterfaz;

import javax.swing.*;
import galerias.Galeria;
import imagenes.Imagen;
import panels_and_dialogs.InfoButtonsPanel;

import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class Visualizer extends JPanel{
	Galeria galery;
	
    JLabel imagenGrande;
    int actID;
    int num;

    JPanel PrevPanel;
    JPanel NextPanel;
    
    List<Imagen> images = new ArrayList<Imagen>();

//    JButton btnPrev;
//    JButton btnNext;
    
    public Visualizer(List<Imagen> images)
    {
    	super(new BorderLayout());
    	this.images = images;
    }

    public Visualizer(Galeria gal)
    {
        super(new BorderLayout());
        galery = gal;
    }
    
    public void visualize(Imagen img)
    {
    	int index = -1;
    	int i = 0;
    	for (Imagen curr : images)
    	{
    		if(curr.getID() == img.getID()) index = i;;
    		i++;
    	}
    	num = index;
    	
        JPanel imagePanel = new JPanel(new BorderLayout());
        
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        
        Dimension dimIcono = new Dimension(img.getWidth(),img.getHeight());
        Dimension ventanaDimension = new Dimension(1400,(int) screenSize.getHeight());
        Dimension infoDim = new Dimension(300,1050);

        ImageIcon nuevoIcono = img.getIcon(dimIcono);

        setSize(ventanaDimension);
//        setLocationRelativeTo(null);
//        setResizable(false);

        setBackground(Color.DARK_GRAY);
        
        imagePanel.setBackground(Color.DARK_GRAY);
//        imagePanel.setBorder(new LineBorder(Color.WHITE));

        imagenGrande = new JLabel(nuevoIcono);

        // Crear botones y agregarlos al JFrame
        if(num > 0)
        {
            PrevPanel = new JPanel(new BorderLayout());
            JButton btnPrev = new JButton("Prev");
            PrevPanel.add(btnPrev, BorderLayout.CENTER);
            
            JPanel filler1 = new JPanel();
    		filler1.setPreferredSize(new Dimension(30,350));
    		filler1.setBackground(Color.DARK_GRAY);
            PrevPanel.add(filler1,BorderLayout.NORTH);
            
            JPanel filler2 = new JPanel();
            filler2.setPreferredSize(new Dimension(30,350));
            filler2.setBackground(Color.DARK_GRAY);
            PrevPanel.add(filler2,BorderLayout.SOUTH);
            
            imagePanel.add(PrevPanel, BorderLayout.WEST);
            btnPrev.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    prev();
                }
            });
        }

        if(num < images.size()-1)
        {
            NextPanel = new JPanel(new BorderLayout());
            JButton btnNext = new JButton("Next");
            NextPanel.add(btnNext, BorderLayout.CENTER);
            
            JPanel filler1 = new JPanel();
    		filler1.setPreferredSize(new Dimension(30,350));
    		filler1.setBackground(Color.DARK_GRAY);
            NextPanel.add(filler1,BorderLayout.NORTH);
            
            JPanel filler2 = new JPanel();
            filler2.setPreferredSize(new Dimension(30,350));
            filler2.setBackground(Color.DARK_GRAY);
            NextPanel.add(filler2,BorderLayout.SOUTH);
            
            imagePanel.add(NextPanel, BorderLayout.EAST);
            btnNext.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    next();
                }
            });
        }

        imagePanel.add(imagenGrande, BorderLayout.CENTER);
        JLabel nameLabel = new JLabel(img.getName());
        JPanel namePanel = new JPanel();
        namePanel.setBackground(Color.DARK_GRAY);
        namePanel.setBorder(BorderFactory.createMatteBorder(0,0,5,0, Color.BLACK));
        
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        
        
        namePanel.add(nameLabel);
        imagePanel.add(namePanel, BorderLayout.NORTH);
        
        add(imagePanel, BorderLayout.CENTER);
        
        
        JPanel infoAndButtons = new InfoButtonsPanel(img);
        infoAndButtons.setPreferredSize(infoDim);
        add(infoAndButtons , BorderLayout.WEST);
        
        // Establecer la acción de cierre de la ventana
//        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setPreferredSize(ventanaDimension);
        setVisible(true);  
    }

    public void visualize(int ID)
    {   
    	
        actID = ID;
        
        JPanel imagePanel = new JPanel(new BorderLayout());
        Imagen img = galery.findID(ID);
        if(img == null) System.out.println("img null");
        
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        
        Dimension dimIcono = new Dimension(img.getWidth(),img.getHeight());
        Dimension ventanaDimension = new Dimension(1400,(int) screenSize.getHeight());
        Dimension infoDim = new Dimension(300,1050);

        ImageIcon nuevoIcono = img.getIcon(dimIcono);

        setSize(ventanaDimension);
//        setLocationRelativeTo(null);
//        setResizable(false);

        setBackground(Color.DARK_GRAY);
        
        imagePanel.setBackground(Color.DARK_GRAY);
//        imagePanel.setBorder(new LineBorder(Color.WHITE));

        imagenGrande = new JLabel(nuevoIcono);

        // Crear botones y agregarlos al JFrame
        if(galery.findID(ID-1) != null)
        {
            PrevPanel = new JPanel(new BorderLayout());
            JButton btnPrev = new JButton("Prev");
            PrevPanel.add(btnPrev, BorderLayout.CENTER);
            
            JPanel filler1 = new JPanel();
    		filler1.setPreferredSize(new Dimension(30,350));
    		filler1.setBackground(Color.DARK_GRAY);
            PrevPanel.add(filler1,BorderLayout.NORTH);
            
            JPanel filler2 = new JPanel();
            filler2.setPreferredSize(new Dimension(30,350));
            filler2.setBackground(Color.DARK_GRAY);
            PrevPanel.add(filler2,BorderLayout.SOUTH);
            
            imagePanel.add(PrevPanel, BorderLayout.WEST);
            btnPrev.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    prev();
                }
            });
        }

        if(galery.findID(ID+1)!= null)
        {
            NextPanel = new JPanel(new BorderLayout());
            JButton btnNext = new JButton("Next");
            NextPanel.add(btnNext, BorderLayout.CENTER);
            
            JPanel filler1 = new JPanel();
    		filler1.setPreferredSize(new Dimension(30,350));
    		filler1.setBackground(Color.DARK_GRAY);
            NextPanel.add(filler1,BorderLayout.NORTH);
            
            JPanel filler2 = new JPanel();
            filler2.setPreferredSize(new Dimension(30,350));
            filler2.setBackground(Color.DARK_GRAY);
            NextPanel.add(filler2,BorderLayout.SOUTH);
            
            imagePanel.add(NextPanel, BorderLayout.EAST);
            btnNext.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    next();
                }
            });
        }

        imagePanel.add(imagenGrande, BorderLayout.CENTER);
        JLabel nameLabel = new JLabel(img.getName());
        JPanel namePanel = new JPanel();
        namePanel.setBackground(Color.DARK_GRAY);
        namePanel.setBorder(BorderFactory.createMatteBorder(0,0,5,0, Color.BLACK));
        
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        
        
        namePanel.add(nameLabel);
        imagePanel.add(namePanel, BorderLayout.NORTH);
        
        add(imagePanel, BorderLayout.CENTER);
        
        
        JPanel infoAndButtons = new InfoButtonsPanel(img);
        infoAndButtons.setPreferredSize(infoDim);
        add(infoAndButtons , BorderLayout.WEST);
        
        // Establecer la acción de cierre de la ventana
//        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setPreferredSize(ventanaDimension);
        setVisible(true);  
        
    }

    public void next()
    {
        removeAll();
        if(galery != null)
        {
            actID++;
            visualize(actID);
        }
        else
        {
        	num++;
        	visualize(images.get(num));
        }

    }

    public void prev()
    {
        removeAll();
        removeAll();
        if(galery != null)
        {
            actID--;
            visualize(actID);
        }
        else
        {
        	num--;
        	visualize(images.get(num));
        }
    }

}


