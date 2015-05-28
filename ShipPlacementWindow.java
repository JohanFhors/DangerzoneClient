

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ShipPlacementWindow extends JFrame{
	
	private JLabel menu = new JLabel(new ImageIcon("C:\\Users\\Klas McDie\\Pictures\\placement.gif"));
	private ShipPlacement sp;
	private boolean active = true;
	
	public ShipPlacementWindow(JPanel panel, ShipPlacement sp){	
		this.sp = sp;
		panel.addKeyListener(new KeyList());
		panel.setFocusable(true);
		add(panel, BorderLayout.EAST);
		add(menu,BorderLayout.WEST);
		setGraphics();
		addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent ev) {
                checkClosing();
            }
        });
	}
	
	public void setGraphics() {
		setTitle("DangerZone BattleStorm TOP GUN edt");
		setSize(1200,580);
		setVisible(true);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	public class KeyList implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
			if (active) {
				if (e.getKeyCode() == KeyEvent.VK_DOWN)
					sp.moveDown();

				if (e.getKeyCode() == KeyEvent.VK_UP)
					sp.moveUp();

				if (e.getKeyCode() == KeyEvent.VK_RIGHT)
					sp.moveRight();

				if (e.getKeyCode() == KeyEvent.VK_LEFT)
					sp.moveLeft();

				if (e.getKeyCode() == KeyEvent.VK_SPACE)
					sp.flip();

				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					sp.placePlane();

				sp.correctPaint();
				
			}
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub

		}

	}
	
	public void checkClosing(){
		int answer = JOptionPane.showConfirmDialog(this, "Do you want to leave Top Gun, quitter?");
		if (answer == JOptionPane.YES_OPTION)
			System.exit(0);		
	}
}
