package nuevaInterfaz;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;
import galerias.Galeria;
import imagenes.Imagen;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;
import java.awt.event.MouseAdapter;


@SuppressWarnings("serial")
public class ImagenesTable extends JTable {

//    public JTable table;

	private Visualizer visualizer;
    private DefaultTableModel model;
    private int numElem;

    private Galeria galery;
    //private List<Imagen> imagenes = new ArrayList<Imagen>();

    public ImagenesTable(Galeria gal) {
    	super();
    	galery = gal;
    	//this.setImagenes(imagenes);
    	setNumElem(0);

        // Datos de ejemplo
        List<Object[]> data = new ArrayList<Object[]>();
        galery.getImagenes().forEach(x-> 
        		data.add(new Object[]
        		{
        			""+x.getID(), x.getName(),x.getDateR(), x.getHour(), ""+x.getRuta(), 
        			"" + x.getWidth(), "" + x.getHeight(), "" + x.getLatitude() + " " + x.getLatitudeRef(),
        			"" + x.getLongitude() + " " + x.getLongitudeRef(), x.getIcon(new Dimension(30,30))
        		}));

        // Crear modelo de tabla
        model = new DefaultTableModel() {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 9 ? ImageIcon.class : super.getColumnClass(columnIndex);
            }
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        model.addColumn("ID");
        model.addColumn("Nombre");
        model.addColumn("Fecha");
        model.addColumn("Hora");
        model.addColumn("Ruta");
        model.addColumn("Ancho");
        model.addColumn("Largo");
        model.addColumn("Latitud");
        model.addColumn("Longitud");
        model.addColumn("Img");

        // Agregar datos al modelo
        for (Object[] row : data) {
            model.addRow(row);
            setNumElem(getNumElem() + 1);
        }

        // Crear tabla
        this.setModel(model);

        // Agregar ordenación por columnas
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(model);
        setRowSorter(sorter);
        setRowHeight(30);
        
        TableColumn imgColumn = this.getColumnModel().getColumn(9); // obtén la columna correspondiente al índice 6
        imgColumn.setPreferredWidth(30); // establece el ancho preferido de la columna a 150 píxeles
        imgColumn.setMaxWidth(30);
        
        TableColumn idColumn = this.getColumnModel().getColumn(0); // obtén la columna correspondiente al índice 0
        idColumn.setPreferredWidth(30); // establece el ancho preferido de la columna a 150 píxeles
        idColumn.setMaxWidth(30);
       
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        centerRenderer.setVerticalAlignment(SwingConstants.CENTER);
        idColumn.setCellRenderer(centerRenderer);

        selectionModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {
                    int selectedRow = getSelectedRow();
                    if(selectedRow == -1) return;
                    int ID = Integer.parseInt((String) getValueAt(selectedRow,0));
                    
                    Imagen img = galery.findID(ID);
                    if(img == null) 
                    {
                    	return;
                    }
                    MainMenu.getInstance().addVisualizer();
                    visualizer = MainMenu.getInstance().getVis();
                    
                    visualizer.visualize(ID);
                    ID = -1;
                    selectedRow = -1;
                    getSelectionModel().clearSelection();
                }
            }
        });
        
        for (int i = 0; i < getTableHeader().getMouseListeners().length; i++) {
        	getTableHeader().removeMouseListener(getTableHeader().getMouseListeners()[i]);
        }
        
        getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Verificar si hay una imagen abierta actualmente
                if (visualizer != null && visualizer.isShowing()) {
//                	visualizer = null;
                    return; // No realizar ninguna acción si hay una imagen abierta
                }
                
                // Resto del código para ordenar las columnas
//                super.mouseClicked(e);
            }
        });

        
        //Custom Sort
        sorter.setComparator(0, StringToIntComparator);
        sorter.setComparator(2, DateComparator);
        sorter.setComparator(3, HourComparator);	
        

        
        
        // Establecer modelo de la tabla
        setModel(model);

        setSize(850,getRowCount()*50);
        
        setPreferredScrollableViewportSize(getSize());
        
        setVisible(true);
    }

    
    
    
    
    
    
    
    //StringToInt
    Comparator<String> StringToIntComparator = new Comparator<String>(){
    	   public int compare(String s1, String s2) {
    	        return Integer.compare(Integer.parseInt(s1), Integer.parseInt(s2));
    	    }
    };
    
    //Fecha
    Comparator<String> DateComparator = new Comparator<String>() {
    	@Override
    	public int compare(String s1, String s2) {
    		String[] parts1 = s1.split("/");
    		int y1 = Integer.parseInt(parts1[2]);
    		int m1 = Integer.parseInt(parts1[1]);
    		int d1 = Integer.parseInt(parts1[0]);
    		
    		String[] parts2 = s2.split("/");
    		int y2 = Integer.parseInt(parts2[2]);
    		int m2 = Integer.parseInt(parts2[1]);
    		int d2 = Integer.parseInt(parts2[0]);
    		
    		if(y1 > y2) return 1;
    		else if(y1 < y2) return -1;
    		else
    		{
    			if(m1 > m2) return 1;
    			else if(m1 < m2) return -1;
    			else
    			{
    				if(d1 > d2) return 1;
    				else if(d1 < d2) return -1;
    				else return 0;
    			}
    		}
    			
    	}
    };
    
    Comparator<String> HourComparator = new Comparator<String>() {
    	@Override
    	public int compare(String s1, String s2) {
    		String[] parts1 = s1.split(":");
    		int y1 = Integer.parseInt(parts1[0]);
    		int m1 = Integer.parseInt(parts1[1]);
    		int d1 = Integer.parseInt(parts1[2]);
    		
    		String[] parts2 = s2.split(":");
    		int y2 = Integer.parseInt(parts2[0]);
    		int m2 = Integer.parseInt(parts2[1]);
    		int d2 = Integer.parseInt(parts2[2]);
    		
    		
    		if(y1 > y2) return 1;
    		else if(y1 < y2) return -1;
    		else
    		{
    			if(m1 > m2) return 1;
    			else if(m1 < m2) return -1;
    			else
    			{
    				if(d1 > d2) return 1;
    				else if(d1 < d2) return -1;
    				else return 0;
    			}
    		}
    		   		
    	}
    };
    
    	
	//Position
    Comparator<String> PositionComparator = new Comparator<String>() {
		@Override
		public int compare(String s1, String s2) {
			return 0;
		};
	};
	

	public int getNumElem() {
		return numElem;
	}

	public void setNumElem(int numElem) {
		this.numElem = numElem;
	}
	
	public Galeria getGalery()
	{
		return this.galery;
	}
	public void setGalery(Galeria gal)
	{
		this.galery = gal;
	}
	

	public List<Imagen> getImagenes() {
		return galery.getImagenes();
	}

	public void setImagenes(List<Imagen> imagenes) {
		this.galery.setImagenes(imagenes);
	}

	public Visualizer getVisualizer() {
		return visualizer;
	}

	public void setVisualizer(Visualizer visualizer) {
		this.visualizer = visualizer;
	}
}







