import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


public abstract class Board {
	
	private JPanel boardpanel = new JPanel();
	private JButton[][] grid = new JButton[10][10];
	
	public Board(){
		setUpBoard();
		boardpanel.setMaximumSize(new Dimension(580,580));
	}
	
	public JPanel getPanel() {
		return boardpanel;
	}
	
	public JButton[][] getGrid() {
		return grid;
		
	}
	public void setUpBoard() {
		boardpanel.setLayout(new GridLayout(11, 11));
		String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I",
				"J" };
		JLabel f = new JLabel();
		boardpanel.add(f);
		for (int i = 0; i < letters.length; i++) {
//			JLabel l = new JLabel("      " + letters[i]);
			JLabel l = new JLabel(new ImageIcon("pics\\"+letters[i]+".gif"));

//			JLabel l = new JLabel(new ImageIcon("C:\\Users\\Klas McDie\\Pictures\\tcb\\"+letters[i]+".gif"));
//			l.setPreferredSize(new Dimension(50, 50));
			boardpanel.add(l);
		}
		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 11; y++) {
				if (y != 0) {
					JButton b = new JButton();
					b.setPreferredSize(new Dimension(50, 50));
					grid[x][y - 1] = b;
					b.setFocusable(false);
					b.setBackground(Color.WHITE);
					boardpanel.add(b);
				} else {
					int n = x + 1;
					JLabel l = new JLabel(new ImageIcon("C:\\Users\\Klas McDie\\Pictures\\tcb\\"+n+".gif"));
//					JLabel l = new JLabel("          " + (x + 1));
					boardpanel.add(l);
				}
			}
		}

	}
}
