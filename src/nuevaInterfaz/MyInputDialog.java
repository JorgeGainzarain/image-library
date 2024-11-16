package nuevaInterfaz;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MyInputDialog extends JDialog {
	
    private JPanel panel = new JPanel(new GridLayout(0,2));
	
	private JLabel nameLabel;
    private JTextField nameField;
    private JButton okButton;
    private JButton cancelButton;


    private String text;
    private String res;

    public MyInputDialog(JFrame parent, String text) {
        super(parent, true);
        this.text = text;
        
        basic();
      
        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }
    
    public void addTextLabel()
    {
    	nameLabel = new JLabel(text);
        nameField = new JTextField(20);
        
        panel.add(nameLabel);
        panel.add(nameField);
    }
    
    public void addButtons()
    {
        okButton = new JButton("OK");
        cancelButton = new JButton("Cancelar");
        
        // Acción del botón "OK"
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                res = nameField.getText();
                dispose(); // Cerrar el diálogo
            }
        });

        // Acción del botón "Cancelar"
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	res = null; // Establecer el nombre como null
                dispose(); // Cerrar el diálogo
            }
        });
        
        panel.add(okButton);
        panel.add(cancelButton);
    }
    
    
    public void basic()
    {
    	getContentPane().removeAll();
    	panel.removeAll();
    	
    	addTextLabel();
    	addButtons();

        getContentPane().add(panel);
    }

    public String showDialog() {
    	res = null; // Reiniciar el nombre
        pack();
        setVisible(true); // Mostrar el diálogo de forma modal
        setLocationRelativeTo(null);
        return res; // Devolver el nombre ingresado
    }

    public String getText() {
    	return nameField.getText();
    }

	public JButton getOkButton() {
		return okButton;
	}

	public void setOkButton(JButton okButton) {
		this.okButton = okButton;
	}

	public JButton getCancelButton() {
		return cancelButton;
	}

	public void setCancelButton(JButton cancelButton) {
		this.cancelButton = cancelButton;
	}

	public JPanel getPanel() {
		return panel;
	}

	public void setPanel(JPanel panel) {
		this.panel = panel;
	}
    
}