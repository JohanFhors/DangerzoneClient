
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

public class PlayerController implements ActionListener {

	private boolean active = false;
	private Game game;

	public PlayerController(Game game) {
		// buttonId = x;
		this.game = game;
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (active) {
			JButton b = (JButton) ae.getSource();
			game.sendPoint(b.getName());
			b.removeActionListener(this);
		}

	}
	
	public void setActive(boolean active) {
		this.active = active;
	}

}
