package panels_and_dialogs;

import javax.swing.*;

import galerias.Galeria;
import galerias.GestorGalerias;
import nuevaInterfaz.MainMenu;
import utils.Func;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

@SuppressWarnings("serial")
public class CreationMenu extends JDialog {
    private JTextField nameField;
    private JSpinner maxFoldersSpinner;
    private JSpinner numLevelsSpinner;
    private JComboBox<String> populateOptionsComboBox;
    private JSpinner selectNumberSpinner;
    private JLabel selectNumberLabel;
//    private JTextField numImagesField;
   
    private GestorGalerias galGestor;
    private Galeria currentGallery;
    
    private static CreationMenu instance;

    public CreationMenu(GestorGalerias gals) {
    	instance = this;
    	galGestor = gals;
    	
//        setTitle("Galería de imágenes");
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(400, 300));
        setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Nombre
        JLabel nameLabel = new JLabel("Nombre de la galería:");
        nameField = new JTextField(9);
//        nameField.setColumns(100);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(nameLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(nameField, gbc);


        // Máximo de carpetas por nivel
        JLabel maxFoldersLabel = new JLabel("Máximo de carpetas por nivel:");
        SpinnerModel maxFoldersModel = new SpinnerNumberModel(1, 1, 50, 1);
        maxFoldersSpinner = new JSpinner(maxFoldersModel);
        maxFoldersSpinner.setPreferredSize(new Dimension(100, maxFoldersSpinner.getPreferredSize().height));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        add(maxFoldersLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        add(maxFoldersSpinner, gbc);

        // Número de niveles
        JLabel numLevelsLabel = new JLabel("Número de niveles (máx. 5):");
        SpinnerModel numLevelsModel = new SpinnerNumberModel(1, 1, 5, 1);
        numLevelsSpinner = new JSpinner(numLevelsModel);
        numLevelsSpinner.setPreferredSize(new Dimension(100, numLevelsSpinner.getPreferredSize().height));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        add(numLevelsLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        add(numLevelsSpinner, gbc);

        // Botón "Crear galería"
        
//        Imagenes.crearImagenes(new File("Galerias/Test"), 300, 20);
        

            
            
            // Selector de opciones de población
            JLabel populateOptionsLabel = new JLabel("Opciones de población:");
            String[] populateOptions = {"Vacia", "Random", "Elegir nº"};
            populateOptionsComboBox = new JComboBox<>(populateOptions);
            populateOptionsComboBox.setSelectedIndex(0);
            gbc.gridx = 0;
            gbc.gridy = 7;
            gbc.anchor = GridBagConstraints.WEST;
            add(populateOptionsLabel, gbc);
            gbc.gridx = 1;
            gbc.gridy = 7;
            gbc.anchor = GridBagConstraints.WEST;
            add(populateOptionsComboBox, gbc);

            // Selector de número
            selectNumberLabel = new JLabel("Seleccionar nº de imagenes: (max:1000)");
            SpinnerModel selectNumberModel = new SpinnerNumberModel(0, 0, 1000, 1);
            selectNumberSpinner = new JSpinner(selectNumberModel);
            selectNumberSpinner.setPreferredSize(new Dimension(100, selectNumberSpinner.getPreferredSize().height));
            gbc.gridx = 0;
            gbc.gridy = 8;
            gbc.anchor = GridBagConstraints.WEST;
            add(selectNumberLabel, gbc);
            gbc.gridx = 1;
            gbc.gridy = 8;
            gbc.anchor = GridBagConstraints.WEST;
            add(selectNumberSpinner, gbc);
            selectNumberLabel.setVisible(false);
            selectNumberSpinner.setVisible(false);

            // Action listener for populateOptionsComboBox
            populateOptionsComboBox.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String selectedOption = (String) populateOptionsComboBox.getSelectedItem();
                    boolean showSelectNumber = selectedOption.equals("Elegir nº");
                    selectNumberLabel.setVisible(showSelectNumber);
                    selectNumberSpinner.setVisible(showSelectNumber);

                    pack(); // Adjust the frame size to fit the components
                }
            });
            
            // Deshabilitar edición directa de los campos de texto en los JSpinners
            JFormattedTextField maxFoldersField = ((JSpinner.DefaultEditor) maxFoldersSpinner.getEditor()).getTextField();
            maxFoldersField.setEditable(false);
            JFormattedTextField numLevelsField = ((JSpinner.DefaultEditor) numLevelsSpinner.getEditor()).getTextField();
            numLevelsField.setEditable(false);

            
            JButton createButton = new JButton("Crear galería");
            createButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String name = nameField.getText();
                    int maxFolders = -1;
                    int numLevels = -1;
                    
                    maxFolders = (int) maxFoldersSpinner.getValue();
                    numLevels = (int) numLevelsSpinner.getValue();
                    if(maxFolders == 0) maxFolders++;
                    if(numLevels == 0) numLevels++;

                    if (name.isEmpty()) 
                    {
                        JOptionPane.showMessageDialog(null, "Debe ingresar un nombre para la galería.");
                    } 
                    else if (new File("Galerias/" + name).exists()) 
                    {
                        JOptionPane.showMessageDialog(null, "Ya existe una galeria con ese nombre.");
                    } 
                    else 
                    {
                        
                    	
                    	if(maxFolders != -1 & numLevels != -1) currentGallery = galGestor.createEmptyGallery(name, maxFolders, numLevels);
                    	else currentGallery = galGestor.createEmptyGallery(name, Func.rnd(2,7), Func.rnd(1,5));
                    		

                        if (populateOptionsComboBox.getSelectedIndex() == 1) {
                        	currentGallery.poblar(Func.rnd(1,1000));
                        }
                        else if (populateOptionsComboBox.getSelectedIndex() == 2) {
                        	int numImgs = (int) selectNumberSpinner.getValue();
                        	currentGallery.poblar(numImgs);
                        }

//                        GestorGalerias.save(currentGallery);
                        
                        dispose();
                        MainMenu.getInstance().update();
                    }
                }
            });

            
                gbc.gridx = 0;
                gbc.gridy = 9;
                gbc.anchor = GridBagConstraints.CENTER;
                gbc.gridwidth = 2;
                add(createButton, gbc);
            
            
            setVisible(false);
            pack();
            setLocationRelativeTo(null);
            
        }

	public static CreationMenu getInstance() {
		return instance;
	}

	public static void setInstance(CreationMenu instance) {
		CreationMenu.instance = instance;
	}

}


