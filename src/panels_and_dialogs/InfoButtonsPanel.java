package panels_and_dialogs;

import javax.swing.*;
import javax.swing.border.LineBorder;
import imagenes.Imagen;

import java.awt.*;

@SuppressWarnings("serial")
public class InfoButtonsPanel extends JPanel {

    private JLabel infoLabel;

    public InfoButtonsPanel(Imagen img) {
        setLayout(new BorderLayout());

        setBorder(new LineBorder(Color.BLACK,6));
        // Crear panel para los botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout()); // Columna única, espacios de 5 píxeles entre los componentes
        buttonPanel.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.BLACK));
        buttonPanel.setBackground(Color.DARK_GRAY);
        
        GridBagConstraints gbc = new GridBagConstraints();
        //Hacer que los botones sean mas altos
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.gridx = 0;
        
        // Agregar botones al panel de botones
        
        gbc.gridy = 0;
        buttonPanel.add(new JButton("Botón 1"),gbc);
        
        gbc.gridy = 1;
        buttonPanel.add(new JButton("Botón 2"),gbc);
        
        gbc.gridy = 2;
        buttonPanel.add(new JButton("Botón 3"),gbc);

        add(buttonPanel, BorderLayout.SOUTH); // Agregar el panel de botones a la izquierda del panel principal

        
        
        JPanel infoPanel = new JPanel(new GridBagLayout());
        gbc = new GridBagConstraints();
        // Crear el label para mostrar la información en varias columnas
        infoPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
        
        infoPanel.setBackground(Color.DARK_GRAY);
        infoPanel.setSize(getWidth()-buttonPanel.getWidth(),getHeight());
        
        gbc.insets = new Insets(15, 0, 15, 0);
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        
        
        infoLabel = new JLabel("Propiedades");
        infoLabel.setForeground(Color.WHITE);
        infoLabel.setFont(new Font("Arial", Font.BOLD, 15));
        

        
        gbc.gridy = 0;
        infoPanel.add(infoLabel,gbc);

        add(infoPanel, BorderLayout.NORTH); // Agregar el label en el centro del panel principal
        
        
        
        
        Propiedades properties = new Propiedades(img);
        
        
        add(properties, BorderLayout.CENTER);
        
    }
    
    public static void main(String[] args) {
    	InfoButtonsPanel panel = new InfoButtonsPanel(new Imagen("TestImages/venta.Tiff"));
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(panel);
//        frame.pack();
        frame.setSize(300, 1000);
        frame.setVisible(true);
    }
}
