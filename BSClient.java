import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

import javax.swing.*;

@SuppressWarnings("serial")
public class BSClient extends JFrame {
	private JButton button = new JButton("CHALLENGE!");
	private JButton logout = new JButton("Quit n Logout");
	private DefaultListModel<String> model = new DefaultListModel<String>();
	private JList<String> list = new JList<String>(model);
	private JScrollPane scroll = new JScrollPane(list);
	private Socket socket;
	private static String host = "localhost";
	private static int port = 2000;
	private String username;
	private Writer out;
	private int myport;
	private Dangerzone dz;

	public BSClient(Dangerzone ctrl) {
		dz = ctrl;
		try {
			socket = new Socket(host, port);
			out = new PrintWriter(new OutputStreamWriter(
					socket.getOutputStream(), "ISO-8859-1"), true);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		username = getUsernameForm("Enter username", true);
		setUpClient();
		new ClientHandler(socket, this);
		System.out.println("Client before send");
		sendMsg("initx" + username + "x");
		setTitle(username + " in DangerZone");
	}

	public DefaultListModel<String> getListModel() {
		return model;
	}

	public String getUsernameForm(String msg, boolean port) {
		boolean done = false;
		JPanel p = new JPanel();
		JPanel main = new JPanel();
		// main.setLayout(new GridLayout(2, 1, 0, 10));
		JLabel logo = new JLabel(new ImageIcon("pics\\logo.gif"));
		main.add(logo);
		p.setLayout(new GridLayout(2, 2, 0, 10));
		JTextField user = new JTextField(10);
		JComboBox<Integer> cb = new JComboBox<Integer>();

		if (port) {
			cb.setSelectedItem((int) 2000);
			for (int i = 2000; i < 2050; i++)
				cb.addItem(i);
			cb.setSelectedItem((int) 2000);
			p.add(new JLabel("Port: "));
			p.add(cb);
			p.add(Box.createHorizontalStrut(15));
		}
		p.add(new JLabel("Username:"));
		p.add(user);
		p.add(Box.createHorizontalStrut(15));

		main.add(p);
		while (!done) {
			int ans = JOptionPane.showConfirmDialog(null, main, msg,
					JOptionPane.OK_OPTION);
			if (!user.getText().equals("") && ans == JOptionPane.OK_OPTION)
				done = true;
			else if (ans != JOptionPane.OK_OPTION) {
				int quit = JOptionPane.showConfirmDialog(null,
						"Are you sure you want to leave DangerZone?", msg,
						JOptionPane.YES_NO_OPTION);
				if (quit != JOptionPane.NO_OPTION)
					System.exit(1);
			}
		}
		if (port)
			myport = (int) cb.getSelectedItem();
		return user.getText();
	}

	public void presentMsg(String msg) {
		// area.append(msg + "\n");
	}

	public void sendMsg(String msg) {
		try {
			out.write(msg + "\n");
			out.flush();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	private void setUpClient() {
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent ev) {
				logout();
			}
		});
		setTitle("DangerZone Client @" + host + " : " + port);
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.weightx = 0.5;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		panel.add(button, c);
		// panel.add(button, BorderLayout.NORTH);
		c.gridx = 1;
		panel.add(logout, c);
		// panel.add(logout, BorderLayout.NORTH);
		c.ipady = 50;
		c.weightx = 0.0;
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 1;
		panel.add(new JLabel(new ImageIcon("pics\\logogame.gif")), c);
		add(panel, BorderLayout.EAST);
		add(scroll, BorderLayout.WEST);
		scroll.setPreferredSize(new Dimension(200, 180));
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		setSize(400, 200);
		setVisible(true);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				challenge((String) list.getSelectedValue());
			}
		});
		logout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				logout();
			}
		});
	}

	public void logout() {
		int a = JOptionPane.showConfirmDialog(null,
				"Do you want to leave the DangerZone?", "Exit",
				JOptionPane.YES_NO_OPTION);
		if (a == JOptionPane.YES_OPTION) {
			sendMsg("logout");
			exit();
		}
	}

	public void exit() {
		System.exit(1);
	}

	public void closeWriter() {
		try {
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void challenge(String opponent) {
		if (opponent != null)
			sendMsg("challengex" + opponent + "x");
		else
			JOptionPane.showMessageDialog(null, "No opponenet selected.");
	}

	public void addPlayer(String player) {
		model.addElement(player);
		// System.out.println(model.size());
	}

	public void game(int toport, String host, String opp) {
		model.clear();
		this.sendMsg("removex" + username + "x");
		setVisible(false);
		dz.startNewGame(toport, this.myport, host, opp);
	}

	public int getMyPort() {
		return myport;
	}

}