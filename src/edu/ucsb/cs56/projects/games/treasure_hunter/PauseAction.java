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

class PauseAction extends AbstractAction {
		
	private JPanel pauseMessage = new JPanel();

	public PauseAction() {
		super();
	}

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

		JPanel pauseMessage = new JPanel();
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

	// PRIVATE INNER CLASS
	private class PressAction extends AbstractAction {
		public PressAction(String name) {
			super(name);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			Component comp = (Component) e.getSource();
			Window pauseWindow = SwingUtilities.getWindowAncestor(comp);
			pauseWindow.dispose();
		}
	}
}

