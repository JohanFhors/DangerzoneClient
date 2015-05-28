import javax.swing.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.*;

public class DefenseBoard extends Board {

	private ArrayList<Ship> planes = new ArrayList<Ship>();
	private ImageIcon miss = new ImageIcon(
			"C:\\Users\\Klas McDie\\Pictures\\tcb\\miss.gif");
	private ImageIcon hit = new ImageIcon(
			"C:\\Users\\Klas McDie\\Pictures\\tcb\\hit.gif");
	private ShipPlacement sp;
	private ShipPlacementWindow spw;

	public DefenseBoard() {
		super();
		sp = new ShipPlacement(super.getGrid(), planes);
		spw = new ShipPlacementWindow(super.getPanel(), sp);
	}

	public boolean awaitPlacement() {
		while (planes.size() != 10) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		getPanel().setFocusable(false);
		spw.setActive(false);
		spw.setVisible(false);
		return true;
	}

	public ArrayList<Ship> getPlanes() {
		return planes;
	}

	public String takeHit(int c) {
		int x = c / 10;
		int y = c % 10;

		for (Ship s : planes) {
			if (s.getCorde().contains(c)) {
				getGrid()[x][y].setIcon(hit);
				getPanel().repaint();
				boolean alive = s.registerHit();
				if (alive)
					return "hit";
				else {
					planes.remove(s);
					if (planes.size() != 0)
						return "hitdownx" + s.getCorde().size();
					else
						return "dead";
				}
			}
		}

		getGrid()[x][y].setIcon(miss);
		getPanel().repaint();
		return "miss";
	}

}
