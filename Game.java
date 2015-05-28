import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Game {

	private int toport;
	private int thisport;
	private String remotehost;
	private DefenseBoard db;
	private OffenseBoard ob;
	private Receiver receiver;
	private GameView gv;
	private DatagramSocket socket;
	private InetAddress iA;
	private int startint;
	private boolean opponline = false;
	private PlayerController pc;
	private String opponent;
	private Dangerzone dz;	
	
	public Game(int toport, int thisport, String remotehost, String opp, Dangerzone dz) {
		this.remotehost = remotehost;
		this.thisport = thisport;
		this.toport = toport;
		this.opponent = opp;
		this.dz = dz;
		db = new DefenseBoard();

		db.awaitPlacement();
		rollDie();
		pc = new PlayerController(this);
		ob = new OffenseBoard(pc);
		gv = new GameView(db, ob, this);
		receiver = new Receiver(thisport, db, this);
		try {
			socket = new DatagramSocket();
			iA = InetAddress.getByName(remotehost);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		awaitOpponent("online");
		// startingPlayer();

	}
	
	public void rollDie() {
		Random rand = new Random();
		startint = rand.nextInt(5000);
	}
	
	public GameView getGV() {
		return gv;
	}

	public void gameOver() {
		JOptionPane.showMessageDialog(null, new ImageIcon("pics\\lost.gif"));
		gv.setVisible(false);
		dz.reEnterServer();
	}
	
	public void gameWon() {
		JOptionPane.showMessageDialog(null, new ImageIcon("pics\\win.gif"));
		disconnect();
		gv.setVisible(false);
		dz.reEnterServer();
	}
	public void send(String msg) {
		try {
			String pointData = msg;

			byte[] pointBytes = pointData.getBytes();
			DatagramPacket packet = new DatagramPacket(pointBytes,
					pointBytes.length, iA, toport);
			socket.send(packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void awaitOpponent(String online) {
		send(online + "x");
		// try {
		// String pointData = online + "x";
		// System.out.println("sending" + online);
		// byte[] pointBytes = pointData.getBytes();
		// DatagramPacket packet = new DatagramPacket(pointBytes,
		// pointBytes.length, iA, toport);
		// socket.send(packet);
		//
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		while (!opponline) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (online.equals("online"))
			startingPlayer();

	}

	public boolean online() {
		return opponline;
	}

	public void setOnline(boolean status) {
		opponline = status;
	}

	public void sendPoint(String cord) {
		send("firex" + cord + "x");
		pc.setActive(false);
		gv.setStatus(false);

	}

	public void startingPlayer() {
		send("startintx" + startint + "x");
	}

	public void MyTurn() {
		gv.setStatus(true);
		pc.setActive(true);
	}

	public int getStartint() {
		return startint;
	}

	public void beginnig() {
		ob.addController();
		pc.setActive(true);
		gv.setStatus(true);
	}

	public void opponentBegins() {
		ob.addController();
		gv.setStatus(false);
	}

	public void fireUpdate(boolean hit, int cord) {
		ob.registerFire(hit, cord);
		if (hit)
			gv.appendStatus("HIT!");
		else
			gv.appendStatus("MISS!");
	}
	public void oppPlaneDown(int len) {
		gv.appendStatus("ENEMEY LEVEL-"+len+ " VESSEL DOWN!");
	}
	public void disconnect(){
		dz.logout();
		send("disconnectx");
	}
	public String getOpponentName() {
		return opponent;
	}
}
