

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ShipPlacement {

	private ArrayList<Ship> planes;
	private ArrayList<Integer> cords = new ArrayList<Integer>();
	private JButton[][] buttons;
	private int shiplen;
	private boolean horizontal;
	private int bottom = 0;
	private int top = 0;
	private int left = 0;
	private int right;
	private int shipnr = 0;
	private int[] shiplist = { 5, 4, 4, 3, 3, 3, 2, 2, 2, 2 };
	private boolean active = true;

	public ShipPlacement(JButton[][] buttongrid, 
		ArrayList<Ship> planes) {
		buttons = buttongrid;
		this.planes = planes;
		placeNew(shiplist[shipnr]);

	}

	public void placeNew(int len) {

		top = 0;
		bottom = 0;
		left = 0;
		this.shiplen = len;
		for (int i = 0; i < shiplen; i++)
			buttons[0][i].setBackground(Color.BLACK);
		horizontal = true;
		right = shiplen - 1;
		shipnr++;

	}

	public void moveDown() {
		if (horizontal && bottom < 9) {
			bottom++;
			for (int i = left; i < right + 1; i++) {
				buttons[top][i].setBackground(Color.WHITE);
				buttons[bottom][i].setBackground(Color.BLACK);
			}
			top++;
		} else if (!horizontal && bottom < 9) {
			bottom++;
			buttons[top][left].setBackground(Color.WHITE);
			buttons[bottom][left].setBackground(Color.BLACK);
			top++;
		}
	}

	public void moveUp() {
		if (horizontal && bottom > 0) {
			top--;
			for (int i = left; i < right + 1; i++) {
				buttons[bottom][i].setBackground(Color.WHITE);
				buttons[top][i].setBackground(Color.BLACK);
			}
			bottom--;
		} else if (!horizontal && top > 0) {
			top--;
			buttons[bottom][left].setBackground(Color.WHITE);
			buttons[top][left].setBackground(Color.BLACK);
			bottom--;
		}
	}

	public void moveLeft() {
		if (horizontal && left > 0) {
			left--;
			buttons[bottom][right].setBackground(Color.WHITE);
			buttons[bottom][left].setBackground(Color.BLACK);
			right--;
		} else if (!horizontal && left > 0) {
			left--;
			for (int i = top; i <= bottom; i++) {
				buttons[i][right].setBackground(Color.WHITE);
				buttons[i][left].setBackground(Color.BLACK);
			}
			right--;
		}
	}

	public void moveRight() {
		if (horizontal && right < 9) {
			right++;
			buttons[bottom][left].setBackground(Color.WHITE);
			buttons[bottom][right].setBackground(Color.BLACK);
			left++;
		} else if (!horizontal && right < 9) {
			right++;
			for (int i = top; i <= bottom; i++) {
				buttons[i][left].setBackground(Color.WHITE);
				buttons[i][right].setBackground(Color.BLACK);
			}
			left++;
		}
	}

	public void flip() {
		if (horizontal && bottom <= 10 - shiplen) {
			for (int i = left + 1; i <= right; i++)
				buttons[bottom][i].setBackground(Color.WHITE);
			bottom = bottom + (shiplen - 1);
			for (int i = top; i <= bottom; i++)
				buttons[i][left].setBackground(Color.BLACK);
			right = left;
			horizontal = false;

		} else if (!horizontal && right <= 10 - shiplen) {

			for (int i = top + 1; i <= bottom; i++)
				buttons[i][left].setBackground(Color.WHITE);
			right = right + (shiplen - 1);
			for (int i = left; i <= right; i++)
				buttons[top][i].setBackground(Color.BLACK);
			bottom = top;
			horizontal = true;
		}
	}

	public boolean checkValid() {
		if (horizontal) {
			for (int i = left; i <= right; i++)
				if (cords.contains((top * 10 + i)))
					return false;
		} else {
			for (int i = top; i <= bottom; i++)
				if (cords.contains((i * 10 + right)))
					return false;
		}
		return true;
	}

	public void placePlane() {
		ArrayList<Integer> newPlane = new ArrayList<Integer>();
		if (checkValid()) {
			if (horizontal) {
				for (int i = left; i <= right; i++) {
					cords.add(top * 10 + i);
					newPlane.add(top * 10 + i);
				}
			} else {
				for (int i = top; i <= bottom; i++) {
					cords.add(i * 10 + right);
					newPlane.add(i * 10 + right);
				}
			}
			planes.add(new Ship(newPlane, horizontal));
			for (Integer i : cords) {
				int x = i / 10;
				int y = i % 10;
				buttons[x][y].setBackground(Color.GREEN);
			}
			if (shipnr != 10)
				placeNew(shiplist[shipnr]);
			else {
				active = false;
			}
		} else {
			JOptionPane.showMessageDialog(null, "INTERSECTION",
					"PLANES INTERSECT", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void correctPaint() {
		for (Integer i : cords) {
			int x = i / 10;
			int y = i % 10;
			if (buttons[x][y].getBackground().equals(Color.BLACK)
					|| buttons[x][y].getBackground().equals(Color.RED))
				buttons[x][y].setBackground(Color.RED);
			else
				buttons[x][y].setBackground(Color.GREEN);
		}
	}

	

}
