import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;

public class OffenseBoard extends Board {

	private ActionListener controller;
	private ImageIcon miss = new ImageIcon("C:\\Users\\Klas McDie\\Pictures\\tcb\\miss.gif");
	private ImageIcon hit = new ImageIcon("C:\\Users\\Klas McDie\\Pictures\\tcb\\hit.gif");
	public OffenseBoard(ActionListener controller) {
		super();
		this.controller = controller;
		

	}
	
	public void registerFire(boolean hit, int cord) {
		int x = cord / 10;
		int y = cord % 10;
		if(hit){
			getGrid()[x][y].setIcon(this.hit);
			
		}else
			getGrid()[x][y].setIcon(miss);		
	}
	
	public void setTurn(boolean turn) {
		
	}

	public void addController() {
		for (int y = 0; y < 10; y++) {
			for (int x = 0; x < 10; x++){
				getGrid()[x][y].addActionListener(controller);
				getGrid()[x][y].setName(Integer.toString(x*10+y));
			}
			// new PlayerController(x*10+y+1, this));
		}
	}
}