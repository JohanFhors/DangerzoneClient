
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Ship {
	private String name;
	private ArrayList<Integer> cords;
	private boolean horizontal;
	private ArrayList<ImageIcon> images = new ArrayList<ImageIcon>();
	private int planelen;

	public Ship(ArrayList<Integer> cords, boolean horizontal) {
		planelen = cords.size();
		this.cords = cords;
		String h = "";
		if(!horizontal)
			h = "v";
		for(int i = 1; i<=planelen;i++){
			images.add(new ImageIcon("C:\\Users\\Klas McDie\\Pictures\\tcb\\"+planelen+""+i+h+".gif"));
		}

	}

	public ArrayList<Integer> getCorde() {
		return cords;
	}
	public ArrayList<ImageIcon> getPics() {
		return images;
	}
	public boolean registerHit() {
		if (--planelen == 0)
			return false;
		else
			return true;
	}
}
