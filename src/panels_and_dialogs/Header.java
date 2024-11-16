package panels_and_dialogs;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;

import nuevaInterfaz.MainMenu;
import utils.Func;

import java.util.List;

@SuppressWarnings("serial")
public class Header extends JPanel {
    
    public Header(File folder, List<File> lastFiles) {

    	super(new BorderLayout());
        this.setBackground(Color.RED);
        this.setPreferredSize(new Dimension(620,50));
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));

        // Panel para los botones
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBackground(Color.LIGHT_GRAY);
        add(buttonPanel, BorderLayout.SOUTH);
        
//        JPanel auxPanel = new JPanel(new BorderLayout());
//        auxPanel.setPreferredSize(Func.redim(getPreferredSize(), 0.9,0.4));
        
        // Botones
    	if(folder.getParentFile()!= null & folder.getParent().compareTo("Galerias")!=0)	
    	{
            JButton backButton = new JButton(Func.reSize(new ImageIcon("Icons/backButton.jpg"), new Dimension(30,30)));
            backButton.setPreferredSize(new Dimension(30,30));
            ActionListener[] actions = backButton.getActionListeners();
            for(int i = 0; i < actions.length; i++)
            {
            	backButton.removeActionListener(actions[i]);
            }
            
            backButton.setAlignmentX(Component.LEFT_ALIGNMENT);
    		backButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					File file = folder.getParentFile();
					lastFiles.add(folder);
					MainMenu.getInstance().updateExplorer(file);
				}
    			
    		});
    		backButton.setVisible(true);
            add(backButton, BorderLayout.WEST);
            
    	}
    	else 
    	{
    		JPanel filler = new JPanel();
    		filler.setPreferredSize(new Dimension(30,30));
    		filler.setBackground(Color.GRAY);
    		add(filler, BorderLayout.WEST);
    	}

    	
    	if (lastFiles.size() != 0)
    	{
            JButton nextButton = new JButton(Func.reSize(new ImageIcon("Icons/nextButton.jpg"), new Dimension(30,30)));
            nextButton.setPreferredSize(new Dimension(30,30));
            ActionListener[] actions = nextButton.getActionListeners();
            for(int i = 0; i < actions.length; i++)
            {
            	nextButton.removeActionListener(actions[i]);
            }
            
            nextButton.setAlignmentX(Component.RIGHT_ALIGNMENT);

    		nextButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					File file = lastFiles.get(lastFiles.size()-1);
					System.out.println("Archivo: " + file.getPath());
					lastFiles.remove(file);
					MainMenu.getInstance().updateExplorer(file);
				}
    			
    		});
    		nextButton.setVisible(true);
    		add(nextButton, BorderLayout.EAST);
    	}
    	else
    	{
    		JPanel filler = new JPanel();
    		filler.setPreferredSize(new Dimension(30,30));
    		filler.setBackground(Color.GRAY);
    		add(filler, BorderLayout.EAST);
    	}
        
        
        JPanel boxPanel = new JPanel(new BorderLayout());
        boxPanel.setPreferredSize(new Dimension(150, 50));
        boxPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        
        
        JPanel boxCenterPanel = new JPanel();
        
        boxCenterPanel.setPreferredSize(new Dimension(50, 20));
        boxCenterPanel.setMaximumSize(new Dimension(50,20));

        boxCenterPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1,true));
        JLabel rutaLabel = new JLabel(folder.getPath());
        rutaLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        rutaLabel.setPreferredSize(new Dimension(160,15));
        rutaLabel.setHorizontalAlignment(SwingConstants.CENTER);
        rutaLabel.setVerticalAlignment(SwingConstants.CENTER);
        rutaLabel.setVerticalTextPosition(JLabel.CENTER);
        rutaLabel.setHorizontalTextPosition(JLabel.CENTER);
        boxCenterPanel.add(rutaLabel,BorderLayout.CENTER);
        
        
        JPanel fillGaps1 = new JPanel();
        fillGaps1.setBackground(Color.LIGHT_GRAY);
        boxPanel.add(fillGaps1, BorderLayout.NORTH);
        
        JPanel fillGaps2 = new JPanel();
        fillGaps2.setBackground(Color.LIGHT_GRAY);
        boxPanel.add(fillGaps2, BorderLayout.WEST);
        
        JPanel fillGaps3 = new JPanel();
        fillGaps3.setBackground(Color.LIGHT_GRAY);
        boxPanel.add(fillGaps3, BorderLayout.EAST);
        
        JPanel fillGaps4 = new JPanel();
        fillGaps4.setBackground(Color.LIGHT_GRAY);
        boxPanel.add(fillGaps4, BorderLayout.SOUTH);
        

        boxPanel.add(boxCenterPanel,BorderLayout.CENTER);
        
        

        add(boxPanel, BorderLayout.CENTER);
        
        
        
        setVisible(true);
    }
    
}








