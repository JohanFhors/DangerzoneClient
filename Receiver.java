import java.awt.Point;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Receiver implements Runnable {
	private Thread t = new Thread(this);
	private DatagramSocket socket;
	private DefenseBoard db;
	private Game game;
	private boolean active = true;

	public Receiver(int myPort, DefenseBoard db, Game game) {
		try {
			socket = new DatagramSocket(myPort);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		this.db = db;
		this.game = game;
		t.start();
	}

	public void run() {

		while (active) {
			byte[] data = new byte[200];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			try {
				socket.receive(packet);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String s = new String(packet.getData(), 0, packet.getLength());
			if (!s.equals("disconnect")) {
				String[] pointInfo = s.split("x");
				interpret(pointInfo);
			} else
				break;

			// db.takeHit(Integer.parseInt(pointInfo[0]));

		}
		socket.close();
	}

	public void interpret(String[] data) {
		System.out.println("receiving:" + data[0]);
		switch (data[0]) {
		case "startint":
			int myStartint = game.getStartint();
			int oppStartint = Integer.parseInt(data[1]);
			if (oppStartint > myStartint) {
				System.out.println("opp begin");
				JOptionPane.showMessageDialog(null, new ImageIcon(
						"pics\\dielost.gif"));
				game.opponentBegins();
			} else if (myStartint > oppStartint) {
				System.out.println("yo begin");
				JOptionPane.showMessageDialog(null, new ImageIcon(
						"pics\\diewin.gif"));
				game.beginnig();
			} else if (myStartint == oppStartint) {
				game.rollDie();
				game.startingPlayer();
			}
			break;
		case "online":
			game.setOnline(true);
			game.awaitOpponent("alsoonline");
			break;
		case "alsoonline":
			game.setOnline(true);
			break;
		case "fire":
			String hitstatus = db.takeHit(Integer.parseInt(data[1]));
			game.send(hitstatus + "x" + data[1]);
			if (hitstatus.equals("hit"))
				game.getGV().appendStatus(game.getOpponentName()+" HITS!");
			else if (hitstatus.equals("miss"))
				game.getGV().appendStatus(game.getOpponentName()+" MISSES!");
			else if (!hitstatus.equals("dead"))
				game.getGV().appendStatus(game.getOpponentName()+" DESTROYS!");
			if (hitstatus.equals("dead"))
				game.gameOver();
			else
				game.MyTurn();

			break;
		case "hit":
			game.fireUpdate(true, Integer.parseInt(data[1]));
			break;
		case "hitdown":
			game.fireUpdate(true, Integer.parseInt(data[2]));
			game.oppPlaneDown(Integer.parseInt(data[1]));
			break;
		case "miss":
			game.fireUpdate(false, Integer.parseInt(data[1]));
			break;
		case "dead":
			game.gameWon();
			break;
		case "msg":
			game.getGV().appendStatus(game.getOpponentName()+": " + data[1]);
			break;
		case "disconnect":
			game.send("disconnect");
			disconnect();
			break;
		}
	}

	public void disconnect() {
		active = false;
		socket.close();
	}

}