
public class Dangerzone {
	private BSClient client;
	
	public static void main(String[] args) {		
		new Dangerzone();
	}
	
	Dangerzone() {
		client = new BSClient(this);
	}
	
	public void startNewGame(int toport, int myport, String host, String opp) {
		new Game(toport, myport, host, opp, this);
	}
	
	public void reEnterServer() {
		client.setVisible(true);
		client.sendMsg("backx");
	}
	
	public void logout() {
		client.sendMsg("logout");
	}

}
