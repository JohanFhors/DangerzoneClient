import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.JOptionPane;

public class ClientHandler implements Runnable {
	private Thread t = new Thread(this);
	private Socket socket;
	private BSClient client;
	private BufferedReader in;
	private boolean alive = true;

	public ClientHandler(Socket socket, BSClient client) {
		this.socket = socket;
		this.client = client;
		t.start();
	}

	public void run() {
		try {
			System.out.println("Client run");
			in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			String msg = "";
			while ((msg = in.readLine()) != null) {
				// msg = in.readLine();
				interpret(msg);
				msg = "";
			}
			System.out.println("här");
			in.close();
			client.closeWriter();
			socket.close();
			client.exit();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void interpret(String msg) {
		System.out.println("Client receiving: " + msg);
		String[] cmd = msg.split("x");
		System.out.println(cmd[0]);
		switch (cmd[0]) {
		case "challenged":
			int answer = JOptionPane.showConfirmDialog(null, "Utmanad av "
					+ cmd[1]);
			if (answer == JOptionPane.YES_OPTION)
				client.sendMsg("acceptx" + cmd[1] + "x"+client.getMyPort()+"x");
			else
				client.sendMsg("declinex" + cmd[1] + "x");
			break;
		case "addplayer":
			client.addPlayer(cmd[1]);
			break;
		case "eisting":
			System.out.println("existing");
			for (int i = 1; i < cmd.length; i++) {
				client.addPlayer(cmd[i]);
				System.out.println(cmd[i]);
			}
			break;
		case "newusername":
			String newname = client
					.getUsernameForm("Username taken, enter new name",false);
			client.sendMsg("initx" + newname + "x");
			client.setTitle(newname+" in DangerZone");
			break;
		case "update":
			client.getListModel().removeElement(cmd[1]);
			break;
		case "declined":
			JOptionPane.showMessageDialog(null, "Declined",cmd[1]+" has declined your challange!",JOptionPane.OK_OPTION);
			break;
		case "accepted":
			client.sendMsg("gamex"+cmd[1]+"x"+client.getMyPort()+"x");
			client.game(Integer.parseInt(cmd[2]), cmd[3],cmd[4]);
			System.out.println(cmd[1]+" port: "+cmd[2]+" inetaddress: "+cmd[3]);
			break;
		case "game":
			client.game(Integer.parseInt(cmd[2]), cmd[3],cmd[4]);
			System.out.println(cmd[1]+" port: "+cmd[2]+" inetaddress: "+cmd[3]);
			break;
		}
	}

}