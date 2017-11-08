package edu.ucsb.cs56.projects.games.treasure_hunter;

import javax.swing.RootPaneContainer;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JDialog;
import javax.swing.JComponent;
import javax.swing.AbstractAction;
import javax.swing.SwingUtilities;
import java.awt.event.ActionEvent;
import java.awt.Component;
import java.awt.Window;
import java.awt.Dialog.ModalityType;
import java.awt.GridLayout;
import java.awt.Color;

/*
  Created by Yusuf Alnawakhtha and Sang Min Oh (CS56, F17, 11/07/2017)
 */

/**
   The <tt>PauseAction</tt> class builds the pause menu and handles the behavior of the game while the game is paused. This class also handles getting quit from the pause menu and resuming the game. 

   @author Yusuf Alnawakhtha and Sang Min Oh
   @version for UCSB CS56, F17, 11/07/2017
*/

class PauseAction extends AbstractAction {
    
    private JPanel pauseMessage = new JPanel();

    /**
       Constructs a <tt>PauseAction</tt> object. The superclass' constructor is called.
     */
    public PauseAction() {
	super();
    }

    /**
       Creates a pause menu that takes over the entire screen. Within the panel that takes over the screen, a <tt>JDialog</tt> is created and it contains a <tt>JButton</tt> that can be used to quit from the pause menu and resume the game.

       @param e An <tt>ActionEvent</tt> object
     */
    @Override
    public void actionPerformed(ActionEvent e) {
	Component comp = (Component) e.getSource();
	if(comp == null)
	    return;
	
	PausePanel glass = new PausePanel();
	glass.setOpaque(false);
	glass.setBackground(new Color(0, 0, 0, 175));
	
	RootPaneContainer gameWindow = (RootPaneContainer) SwingUtilities.getWindowAncestor(comp);
	gameWindow.setGlassPane(glass);
	glass.setVisible(true);
        
	JLabel label = new JLabel("PAUSED");
	label.setForeground(Color.BLACK);
	JPanel panel = new JPanel();
	panel.setOpaque(false);
	panel.add(label);
	
	pauseMessage.setBackground(new Color(123, 63, 0));
	pauseMessage.setLayout(new GridLayout(0, 1, 10, 10));
	pauseMessage.add(panel);
	pauseMessage.add(new JButton(new PressAction("RESUME")));
	
	JDialog dialog = new JDialog((Window) gameWindow, "", ModalityType.APPLICATION_MODAL);
	dialog.getContentPane().add(pauseMessage);
	dialog.setUndecorated(true);
	dialog.pack();
	dialog.setLocationRelativeTo((Window) gameWindow);
	dialog.setVisible(true);
	
	glass.setVisible(false);
    }
    
    /**
       A private inner class that takes action when the "RESUME" button is pressed in the pause menu. 
     */
    private class PressAction extends AbstractAction {

	/**
	   Constructs a <tt>PressAction</tt> object.

	   @param text The text that appears on the <tt>JButton</tt> and describes the action taken when the <tt>JButton</tt> is pressed
	 */
	public PressAction(String text) {
	    super(text);
	}

	/**
	   Disposes of the pause menu when the resume button is clicked. 

	   @param e An <tt>ActionEvent</tt> object
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
	    Component comp = (Component) e.getSource();
	    Window pauseWindow = SwingUtilities.getWindowAncestor(comp);
	    pauseWindow.dispose();
	}
    }
}

