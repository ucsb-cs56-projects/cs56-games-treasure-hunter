package edu.ucsb.cs56.projects.games.treasure_hunter;

import java.awt.Graphics;
import javax.swing.JPanel;

class PausePanel extends JPanel {
	
	public PausePanel() {
		super();
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
		paintComponent(g);
	}
	
}
