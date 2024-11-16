package panels_and_dialogs;

import java.awt.BorderLayout;
import utils.Date;
import java.awt.GridBagConstraints;
import javax.swing.JSpinner;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.SpinnerNumberModel;
import org.jdatepicker.DateModel;
import org.jdatepicker.JDatePicker;

import galerias.Galeria;
import imagenes.Imagen;
import nuevaInterfaz.MainMenu;

@SuppressWarnings("serial")
public class FilterDialog extends JDialog{
	
	Galeria currGallery;
	
	private JTabbedPane tabbedPane;
	
	JPanel sizePanel;
	JPanel date;
	
	JPanel datePanel;
	JPanel hourPanel;
	
	JSpinner yearSpinner;
	JSpinner monthSpinner;
	JSpinner daySpinner;

	private JSpinner WSpinner;

	private JSpinner HSpinner;
	
	public FilterDialog(JFrame parent, Galeria currGallery, int n) {
		super(parent, true);
		setTitle("Filtros");
		
		this.currGallery = currGallery;
		
		tabbedPane = new JTabbedPane();
		getContentPane().add(tabbedPane);
		
		createDatePanel();
		createHourPanel();
		createSizePanel();
		
		tabbedPane.add("Date",date);
		tabbedPane.add("Hour",hourPanel);
		tabbedPane.add("Size",sizePanel);
		
		tabbedPane.setSelectedIndex(n);
		
		pack();
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}
	
	public void createSizePanel() {
		sizePanel = new JPanel(new BorderLayout());
		sizePanel.add(new JLabel("Selecciona el tamaño"),BorderLayout.NORTH);
		
		JPanel size = new JPanel(new GridLayout(0,2));
		sizePanel.add(size,BorderLayout.CENTER);
		size.add(new JLabel("Width"));
		size.add(new JLabel("Height"));
		
		SpinnerNumberModel numberModel = new SpinnerNumberModel(0,0,1000,10);
		WSpinner = new JSpinner(numberModel);
		
		size.add(WSpinner);
		
		SpinnerNumberModel numberModel2 = new SpinnerNumberModel(0,0,1000,10);
		HSpinner = new JSpinner(numberModel2);
		size.add(HSpinner);
		
		
        ButtonGroup buttonGroup = new ButtonGroup();
        
        JRadioButton menorQueRadioButton = new JRadioButton("Menor que");
        buttonGroup.add(menorQueRadioButton);
        
        JRadioButton mayorQueRadioButton = new JRadioButton("Mayor que");
        buttonGroup.add(mayorQueRadioButton);
		
        size.add(menorQueRadioButton);
        
        size.add(mayorQueRadioButton);
		
		JButton acceptButton = new JButton("Aceptar");
		acceptButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int width = (int) WSpinner.getValue();
				int height = (int) HSpinner.getValue();
				
				
	           	if(menorQueRadioButton.isSelected()) {
            		currGallery.getImagenes().removeIf(x-> x.getWidth() < width & x.getHeight() < height);
            	}
            	else if(mayorQueRadioButton.isSelected()) {
            		currGallery.getImagenes().removeIf(x-> x.getWidth() > width & x.getHeight() > height);
            	}
				
	            currGallery.save();
	            	
	            MainMenu.getInstance().updateTable();
	            MainMenu.getInstance().revalidate();
	            dispose();
			}
			
		});
		sizePanel.add(acceptButton,BorderLayout.SOUTH);
		
	}
	
	public void createHourPanel() {
        // Crear los componentes de selección de hora
        SpinnerNumberModel hourModel = new SpinnerNumberModel(0, 0, 23, 1);
        JSpinner hourSpinner = new JSpinner(hourModel);

        SpinnerNumberModel minuteModel = new SpinnerNumberModel(0, 0, 59, 1);
        JSpinner minuteSpinner = new JSpinner(minuteModel);
        
        SpinnerNumberModel secondModel = new SpinnerNumberModel(0,0,59,1);
        JSpinner secondSpinner = new JSpinner(secondModel);


        // Crear el botón de aceptar
        JButton acceptButton = new JButton("Aceptar");
 

        // Crear el panel y establecer el diseño GridBagLayout
        hourPanel = new JPanel();
        hourPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Agregar los componentes al panel
        hourPanel.add(new JLabel("Hora: "), gbc);
        gbc.gridx++;
        hourPanel.add(hourSpinner, gbc);
        gbc.gridx++;
        hourPanel.add(new JLabel(":"), gbc);
        gbc.gridx++;
        hourPanel.add(minuteSpinner, gbc);
        gbc.gridx++;
        hourPanel.add(secondSpinner, gbc);
        

        gbc.gridx = 0;
        gbc.gridy++;
        
        ButtonGroup buttonGroup = new ButtonGroup();
        
        JRadioButton menorQueRadioButton = new JRadioButton("Antes de");
        buttonGroup.add(menorQueRadioButton);
        
        JRadioButton mayorQueRadioButton = new JRadioButton("Despues de");
        buttonGroup.add(mayorQueRadioButton);
        
        hourPanel.add(menorQueRadioButton, gbc);
        gbc.gridx++;
        hourPanel.add(mayorQueRadioButton, gbc);
        
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        hourPanel.add(acceptButton, gbc);
        
        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener la hora seleccionada
                int hour = (int) hourSpinner.getValue();
                int minute = (int) minuteSpinner.getValue();
                int second = (int) secondSpinner.getValue();
                
            	
            	List<Imagen> aux = new ArrayList<Imagen>();
            	aux.addAll(currGallery.getImagenes());
            	
            	
            	Date date = new Date( 0, 0, 0, hour, minute, second);
            	
            	
            	if(menorQueRadioButton.isSelected()) {
            		currGallery.getImagenes().removeIf(x-> x.getTime().compareTo(date)==1);
            	}
            	else if(mayorQueRadioButton.isSelected()) {
            		currGallery.getImagenes().removeIf(x-> x.getTime().compareTo(date)==-1);
            	}
            	
            	currGallery.save();
            	
            	MainMenu.getInstance().updateTable();
            	MainMenu.getInstance().revalidate();
            	dispose();

            }
        });
        

	}
	
	public void createDatePanel() {
        date = new JPanel(new BorderLayout());
        JDatePicker datePicker = new JDatePicker();
        
        date.add(datePicker,BorderLayout.NORTH);
       
        
        datePanel = new JPanel(new GridBagLayout());
        date.add(datePanel, BorderLayout.CENTER);
        
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        
        gbc.insets = new Insets(10, 0, 0, 0);
        ButtonGroup buttonGroup = new ButtonGroup();
        
        JRadioButton menorQueRadioButton = new JRadioButton("Antes de");
        buttonGroup.add(menorQueRadioButton);
        
        JRadioButton mayorQueRadioButton = new JRadioButton("Despues de");
        buttonGroup.add(mayorQueRadioButton);
        
        datePanel.add(menorQueRadioButton, gbc);


        gbc.gridx++;
        datePanel.add(mayorQueRadioButton, gbc);

        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 0, 0, 0);
        JButton acceptButton = new JButton("Aceptar");
        datePanel.add(acceptButton, gbc);

        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
            	DateModel<?> model = datePicker.getModel();
            	
            	List<Imagen> aux = new ArrayList<Imagen>();
            	aux.addAll(currGallery.getImagenes());
            	
            	
            	Date date = new Date(model.getYear(),model.getMonth(),model.getDay(), 0, 0, 0);
            	
            	
            	if(menorQueRadioButton.isSelected()) {
            		currGallery.getImagenes().removeIf(x-> x.getDateO().compareTo(date)==1);
            	}
            	else if(mayorQueRadioButton.isSelected()) {
            		currGallery.getImagenes().removeIf(x-> x.getDateO().compareTo(date)==-1);
            	}
            	
            	currGallery.save();
            	
            	MainMenu.getInstance().updateTable();
            	MainMenu.getInstance().revalidate();
            	dispose();
            }
        });
		
	}
	
	public static void main(String[] args) {
		Galeria g = new Galeria("A");
		new FilterDialog(null,g,0);
	}

	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	public void setTabbedPane(JTabbedPane tabbedPane) {
		this.tabbedPane = tabbedPane;
	}
}
