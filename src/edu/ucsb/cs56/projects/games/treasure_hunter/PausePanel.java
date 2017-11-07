package edu.ucsb.cs56.projects.games.treasure_hunter;

import javax.swing.*;
import java.awt.*;

class PausePanel extends JPanel {
	
	public PausePanel() {
		super();
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
	}
	
}
