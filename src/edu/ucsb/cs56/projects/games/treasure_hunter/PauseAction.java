package edu.ucsb.cs56.projects.games.treasure_hunter;

import javax.swing.*;
import java.awt.*;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;

/*
  Created by Yusuf Alnawakhtha and Sang Min Oh (CS56, F17, 11/07/2017)
 */

/**
   The <tt>PauseAction</tt> class builds the pause menu and handles the behavior of the game while the game is paused. This class also handles getting quit from the pause menu and resuming the game. 

   @author Yusuf Alnawakhtha and Sang Min Oh
   @version for UCSB CS56, F17, 11/07/2017
*/

public class PauseAction {
    
    private JPanel pauseMessage;

    /**
       Constructs a <tt>PauseAction</tt> object. The superclass' constructor is called.
     */
    public PauseAction() {
	super();
    }

    /**
       Creates a pause menu that takes over the game screen. Within the panel that takes over the screen, a <tt>JDialog</tt> is created and it contains a <tt>JButton</tt> that can be pressed to quit from the pause menu and resume the game.

       @param frame The JFrame that the pause menu will be drawn on
     */
    public long drawPauseMenu(JFrame frame) {
    	long startPause = System.currentTimeMillis();
		pauseMessage = new JPanel();
		    
		JLabel label = new JLabel("PAUSED");
		label.setForeground(Color.BLACK);
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.add(label);
	
		pauseMessage.setBackground(new Color(123, 63, 0));
		pauseMessage.setLayout(new GridLayout(2, 1, 10, 10));
		pauseMessage.add(panel);
		pauseMessage.add(new JButton(new PressAction("RESUME")));
		pauseMessage.setVisible(true);
	
		JDialog dialog = new JDialog(frame, "", ModalityType.APPLICATION_MODAL);
		dialog.getContentPane().add(pauseMessage);
		dialog.setUndecorated(true);
		dialog.pack();
		dialog.setLocationRelativeTo(frame);
		dialog.setVisible(true);

		return (System.currentTimeMillis() - startPause);
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

