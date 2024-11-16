package nuevaInterfaz;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import galerias.Galeria;
import imagenes.GestorImagenes;
import imagenes.Imagen;
import panels_and_dialogs.FilterDialog;
import panels_and_dialogs.Header;
import panels_and_dialogs.LoadingDialog;
import threads.ImageCreationTask;
import utils.Func;

@SuppressWarnings("serial")
public class MainMenu extends JFrame {
	
	private static MainMenu instance;
	
	private JTabbedPane tabbedPane;
	
	private GalleryList lista;
	private ImagenesTable table;
	private Explorador exp;
	private Header header;
	private Visualizer vis;
	
	JDialog progressDialog;
	JProgressBar progressBar;
	
	private Galeria currGallery;
	
	private Dimension defDim = new Dimension(850,900);

	public List<File> lastFiles = new ArrayList<File>();
	
	
	public MainMenu() {
		setInstance(this);
	
		setTitle("Selecciona una galeria");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Dimension dim = defDim;
		tabbedPane = new JTabbedPane();
		
		showList();

        getContentPane().add(tabbedPane);
        
        setSize((int)dim.getWidth(), (int)dim.getHeight());
        this.setMinimumSize(dim);
        setPreferredSize(dim);
        setLocationRelativeTo(null);
        
        this.setResizable(true);
        
        setVisible(true);
    }
	
	public void showList()
	{
		tabbedPane.removeAll();
		lista = new GalleryList();
		tabbedPane.addTab("Lista galerias", lista);
		setJMenuBar(null);
		revalidate();
		
	}
	
	public void update()
	{
		lista.update();
		revalidate();
	}
	
	public void updateTable()
	{
		LoadingDialog ldialog = new LoadingDialog(currGallery);
		ldialog.start();
		int index = tabbedPane.getSelectedIndex();
		table = new ImagenesTable(currGallery);
		tabbedPane.remove(0);
		tabbedPane.add(new JScrollPane(table),"Tabla", 0);
		revalidate();
		tabbedPane.setSelectedIndex(index);
		ldialog.stop();
		
	}
	
	public void selectGallery(Galeria g)
	{				
		LoadingDialog ldialog = new LoadingDialog(g);
		ldialog.start();
		g.removeFilters();
		
		//Gallerypanel
		setTitle(g.getName());
		currGallery = g;
        // Crear barra de menú
        JMenuBar menuBar = new JMenuBar();

        JButton back = new JButton("Back");
        
        back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				showList();
				revalidate();
			}
        	
        });
        menuBar.add(back);
        
        // Crear menú
        JMenu menu = new JMenu("options");

        // Crear elementos de menú

        JMenuItem poblarItem = new JMenuItem("Poblar");
        poblarItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MyInputDialog dialog = new MyInputDialog(MainMenu.instance,"Numero de imagenes {0 = random(1-500)}");
				
				Boolean b = true;
				Object o = dialog.showDialog();
				if (o == null) {
					return;
				}

				
				
				while(dialog.isShowing() || b) {
					try{
						int num = Integer.parseInt((String) o);
						if(num == 0) num = Func.rnd(1, 500);
						b = false;
						
		                // Inicia un hilo para la creación de imágenes
		                Thread thread = new Thread(new ImageCreationTask(currGallery, num));
		                thread.start();
					}
					catch (Exception ex) {
						JOptionPane.showMessageDialog(null, "Introduce un Numero");
					}
				}
				
				GestorImagenes.setIm(currGallery.getImagenes().size());

			}
        	
        });

        // Agregar elementos de menú al menú
        menu.add(poblarItem);

        // Agregar menú a la barra de menú
       
       
        
        //Menu filtros
        JMenu filtros = new JMenu("Filtros");
        
        JMenuItem dateFilter = new JMenuItem("Fecha");
        dateFilter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new FilterDialog(MainMenu.getInstance(), currGallery,0);
			}
        	
        });
        filtros.add(dateFilter);
        
        JMenuItem hourFilter = new JMenuItem("Hora");
        hourFilter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new FilterDialog(MainMenu.getInstance(), currGallery,1);
			}
        	
        });
        
        filtros.add(hourFilter);
        
        JMenuItem sizeFilter = new JMenuItem("Size");
        sizeFilter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new FilterDialog(MainMenu.getInstance(), currGallery,2);
			}
        	
        });
        
        filtros.add(sizeFilter);
        
        JMenuItem removeFilters = new JMenuItem("Remove");
        removeFilters.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				currGallery.removeFilters();
				int index = tabbedPane.getSelectedIndex();
				updateExplorer(currGallery.getCarpeta());
				updateTable();
				revalidate();
				tabbedPane.setSelectedIndex(index);
			}
        	
        });
        filtros.add(removeFilters);
        
        menuBar.add(menu);
        menuBar.add(filtros);
        

        // Agregar barra de menú al marco
        setJMenuBar(menuBar);
        
		tabbedPane.removeAll();
		
		
		Explorador exp = new Explorador(g.getCarpeta());
		JScrollPane scrollPane = new JScrollPane(exp);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setColumnHeaderView(new Header(g.getCarpeta(),lastFiles));
		
		table = new ImagenesTable(g);
		tabbedPane.add("Tabla", new JScrollPane(table));
		tabbedPane.add("Explorador", scrollPane);
		tabbedPane.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				int tabIndex = tabbedPane.getSelectedIndex();
				MainMenu menu = MainMenu.getInstance();
				if(tabIndex == 1) 
				{
					menu.setSize(defDim);
					menu.setResizable(false);
					menu.setLocationRelativeTo(null);
					
				}
				else menu.setResizable(true);
			}
			
		});
		ldialog.stop();
		revalidate();

	}
	
	
	public void addVisualizer(){
		addVisualizer(null);
	}
	
	public void addVisualizer(List<Imagen> images)
	{
		if(images == null) vis = new Visualizer(currGallery);
		else vis = new Visualizer(images);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setSize((int) (screenSize.width * 0.9),(int) (screenSize.height*0.95));
		setLocationRelativeTo(null);

		tabbedPane.add(vis);
		tabbedPane.setSelectedComponent(vis);
		tabbedPane.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				if(tabbedPane.getSelectedComponent() != vis) {
					tabbedPane.remove(vis);
					setSize(defDim);
					setLocationRelativeTo(null);
					setResizable(true);
				}
			}
			
		});
		setResizable(false);
		revalidate();
	}
	
	
	public void updateExplorer(File folder) {
		LoadingDialog ldialog = new LoadingDialog(folder);
		ldialog.start();
		
		tabbedPane.remove(1);
		exp = new Explorador(folder);
		JScrollPane scrollPane = new JScrollPane(exp);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setColumnHeaderView(new Header(folder,lastFiles));
		tabbedPane.add("Explorador", scrollPane); 
		tabbedPane.setSelectedIndex(1);
		revalidate();
		
		ldialog.stop();
	}
	
	
	public static void main(String[] args) {
		new MainMenu();
	}
	
	
	
	public ImagenesTable getTable() {
		return table;
	}
	public void setTable(ImagenesTable table) {
		this.table = table;
	}
	public Explorador getExp() {
		return exp;
	}
	public void setExp(Explorador exp) {
		this.exp = exp;
	}
	public Header getHeader() {
		return header;
	}
	public void setHeader(Header header) {
		this.header = header;
	}

	public static MainMenu getInstance() {
		return instance;
	}

	public static void setInstance(MainMenu instance) {
		MainMenu.instance = instance;
	}

	public Galeria getCurrGallery() {
		return currGallery;
	}

	public void setCurrGallery(Galeria currGallery) {
		this.currGallery = currGallery;
	}

	public JDialog getProgressDialog() {
		return progressDialog;
	}

	public void setProgressDialog(JDialog progressDialog) {
		this.progressDialog = progressDialog;
	}

	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	public void setTabbedPane(JTabbedPane tabbedPane) {
		this.tabbedPane = tabbedPane;
	}

	public GalleryList getLista() {
		return lista;
	}

	public void setLista(GalleryList lista) {
		this.lista = lista;
	}

	public JProgressBar getProgressBar() {
		return progressBar;
	}

	public void setProgressBar(JProgressBar progressBar) {
		this.progressBar = progressBar;
	}

	public Visualizer getVis() {
		return vis;
	}

	public void setVis(Visualizer vis) {
		this.vis = vis;
	}

	public Dimension getDefDim() {
		return defDim;
	}

	public void setDefDim(Dimension defDim) {
		this.defDim = defDim;
	}


	
}
