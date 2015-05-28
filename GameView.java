import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;
import javax.swing.text.DefaultCaret;

public class GameView extends JFrame {
	
	private Game game;
	private JLabel status = new JLabel("Awaiting");
	private JTextArea text = new JTextArea();
	private JScrollPane scroll = new JScrollPane(text);
	private DefaultCaret caret = (DefaultCaret) text.getCaret();
	private DefenseBoard defenseboard;
	private JPanel center = new JPanel();
	private JPanel south = new JPanel();
	private JTextField textfield = new JTextField("Enter msg here");
	private JLabel statuslabel = new JLabel(new ImageIcon("C:\\Users\\Klas McDie\\Pictures\\tcb\\ho.gif"));
//	private JPanel opplanes = new JPanel();	
	private JLabel logo = new JLabel(new ImageIcon("C:\\Users\\Klas McDie\\Pictures\\tcb\\logogame.gif"));
	private ImageIcon holdon = new ImageIcon("C:\\Users\\Klas McDie\\Pictures\\tcb\\ho.gif");
	private ImageIcon yourturn = new ImageIcon("C:\\Users\\Klas McDie\\Pictures\\tcb\\yt.gif");
	
	public GameView(DefenseBoard defenseboard, OffenseBoard offenseboard, Game game) {
		this.game = game;
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		text.setEditable(false);
		text.setLineWrap(true);
		text.setWrapStyleWord(true);
		this.defenseboard = defenseboard;
		add(offenseboard.getPanel(), BorderLayout.WEST);
		add(defenseboard.getPanel(), BorderLayout.EAST);	
		offenseboard.getPanel().setPreferredSize(new Dimension(580,580));
		defenseboard.getPanel().setPreferredSize(new Dimension(580,580));
		paintPlanes();
		setUpCenter();
		center.setPreferredSize(new Dimension(160,580));		
		add(center, BorderLayout.CENTER);
		addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent ev) {
                checkClosing();
            }
        });
		setGraphics();
	}
	
	public void setUpCenter() {
		JPanel forfield = new JPanel();
		JPanel forlogo = new JPanel();
		JPanel forstatus = new JPanel();
		center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));		
		forlogo.add(logo);
		center.add(forlogo);
		center.add(Box.createRigidArea(new Dimension(0, 5)));
		forstatus.add(statuslabel);
		center.add(forstatus);
		center.add(Box.createRigidArea(new Dimension(0, 5)));
		center.add(scroll);
		center.add(Box.createRigidArea(new Dimension(0, 5)));
		forfield.add(new JLabel("Communication:"));
		forfield.add(textfield);
		KeyListener keylistener = new KeyListener() {
		      public void keyPressed(KeyEvent ke) {
		    	  if(ke.getKeyCode() == KeyEvent.VK_ENTER && !textfield.getText().equals("")){
		    		  String msg = textfield.getText();
		    		  textfield.setText("");
		    		  game.send("msgx"+msg);
		    		  text.append("You: "+msg+"\n");
		    	  }
		      }
		      public void keyReleased(KeyEvent keyEvent) {		        
		      }
		      public void keyTyped(KeyEvent keyEvent) {		       
		      }		    
		    };
		textfield.addKeyListener(keylistener);
		center.add(forfield);
		center.add(Box.createRigidArea(new Dimension(0, 5)));
		center.add(Box.createVerticalGlue());
		forlogo.setPreferredSize(new Dimension(150,55));
		scroll.setPreferredSize(new Dimension(150,200));
		forstatus.setPreferredSize(new Dimension(150,110));
		textfield.setPreferredSize(new Dimension(120,20));
		forfield.setPreferredSize(new Dimension(150,50));		
	}
	
	
	public void paintPlanes(){
		for(Ship s: defenseboard.getPlanes()){
			for(int i = 0; i<s.getCorde().size();i++){
				int x = s.getCorde().get(i) / 10;
				int y = s.getCorde().get(i) % 10;
				defenseboard.getGrid()[x][y].setIcon(s.getPics().get(i));
			}
		}
	}

	public void setGraphics() {
		setTitle("DangerZone BattleStorm TOP GUN edt");
		setSize(1310,580);
		setVisible(true);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	}

	public void setStatus(boolean turn) {
		if(turn)
			statuslabel.setIcon(yourturn);
		else
			statuslabel.setIcon(holdon);
		statuslabel.repaint();
	}

	public void appendStatus(String newinfo) {
		text.append(newinfo + "\n");
	}
	
	public void checkClosing() {
		int answer = JOptionPane.showConfirmDialog(null, "Quit?");
		if (answer == JOptionPane.YES_OPTION) {
			game.disconnect();
			System.exit(1);
		}
	}

}
