package nuevaInterfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Comparator;
import java.util.Iterator;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import galerias.Galeria;
import galerias.GestorGalerias;
import panels_and_dialogs.CreationMenu;

@SuppressWarnings("serial")
public class GalleryList extends JPanel{
	
	GestorGalerias gestor;
	int numGalerias;
	
	int numElem;
	
	public GalleryList() 
	{
	    super();
	    
	    GridLayout layout = new GridLayout(0,4);
	    layout.setHgap(10);
	    layout.setVgap(10);
	    setLayout(layout);
	    
	    this.gestor = new GestorGalerias();
	    
	    numGalerias = gestor.getGalerias().size();
	    numElem = 1; //Contando el AddPanel
	    
	    gestor.getGalerias().sort(new Comparator<Galeria>() {

			@Override
			public int compare(Galeria o1, Galeria o2) {
				if(o1.getID() < o2.getID()) return -1;
				else if(o1.getID() > o2.getID()) return 1;
				else return 0;
			}
	    	
	    });
	    Iterator<Galeria> it = gestor.getGalerias().iterator();

	    while (it.hasNext()) {
	    	add(galPanel(it.next()));
	    	numElem++;
	    }

	    //Panel de añadir
		JButton addPanel = new JButton("Add Gallery");
		addPanel.setBackground(Color.WHITE);
		addPanel.setBorder(new LineBorder(Color.BLACK,2));
		addPanel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CreationMenu menu = new CreationMenu(gestor);
				menu.setModal(true);
				menu.setVisible(true);
								
			}
			
		});
		
		add(addPanel);
        
		
		//Paneles auxiliares
		int n = numElem;
		while(n < 16)
		{
			add(new JPanel());
			n++;
		}

	}
	
	public void update(){

		removeAll();
		numElem = 1;
	    GridLayout layout = new GridLayout(0,4);
	    layout.setHgap(10);
	    layout.setVgap(10);
	    setLayout(layout);
		
		Iterator<Galeria> it = gestor.getGalerias().iterator();
		
	    while (it.hasNext()) {
	    	add(galPanel(it.next()));
	    	numElem++;
	    }
		
	    //Panel de añadir
		JButton addPanel = new JButton("Add Gallery");
		addPanel.setBackground(Color.WHITE);
		addPanel.setBorder(new LineBorder(Color.BLACK,2));
		addPanel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CreationMenu menu = new CreationMenu(gestor);
				menu.setModal(true);
				menu.setVisible(true);
								
			}
			
		});
		
		add(addPanel);
		
		//Paneles auxiliares
		int n = numElem;
		while(n < 16)
		{
			add(new JPanel());
			n++;
		}
	    
		revalidate();
	}

	
	public JPanel galPanel(Galeria gal)
	{
		JPanel ret = new JPanel(new BorderLayout());
		
		ret.setPreferredSize(new Dimension(100,100));
		ret.setBackground(Color.DARK_GRAY);
		ret.setBorder(new LineBorder(Color.BLACK,2));
		
		JLabel name;
		if(gal != null) name = new JLabel(gal.getName());
		else return ret;
		ret.add(name, BorderLayout.NORTH);
		name.setForeground(Color.WHITE);
		name.setHorizontalAlignment(SwingConstants.CENTER);
		name.setBackground(Color.RED);
		name.setBorder(new MatteBorder(0, 0, 3, 0, Color.BLACK));
		
		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new GridLayout(0,1));
		infoPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MainMenu.getInstance().selectGallery(gal);
			}
		});
		infoPanel.setBackground(Color.DARK_GRAY);
		
		JLabel idLabel = new JLabel("ID: " + gal.getID());
		idLabel.setForeground(Color.WHITE);
		idLabel.setHorizontalAlignment(SwingConstants.CENTER);
		infoPanel.add(idLabel);
		
		JLabel numCarpLabel = new JLabel("Num Carpetas: " + gal.getNumCarpetas());
		numCarpLabel.setForeground(Color.WHITE);
		numCarpLabel.setHorizontalAlignment(SwingConstants.CENTER);
		infoPanel.add(numCarpLabel);
		
		JLabel numImagesLabel = new JLabel("Num Imagenes: " + gal.getImagenes().size());
		numImagesLabel.setForeground(Color.WHITE);
		numImagesLabel.setHorizontalAlignment(SwingConstants.CENTER);
		infoPanel.add(numImagesLabel);
		
		ret.add(infoPanel,BorderLayout.CENTER);
		
		return ret;
	}
	

}
