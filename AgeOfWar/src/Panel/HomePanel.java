package Panel;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Main.MainFrame;

public class HomePanel extends JPanel {
	private JLabel titleLabel;
	private JLabel startLabel;
	private JLabel exitLabel;
	private boolean isUp;
	MainFrame mainFrame;
	
	private Image background = new ImageIcon("src/Images/background.png").getImage();
	
	public HomePanel(MainFrame mainFrame) {
		setLayout(null);
		
		this.mainFrame = mainFrame;
		
		titleLabel = new JLabel("Age Of War");
		titleLabel.setFont(new Font("Serif", Font.BOLD, 100));
		titleLabel.setBounds(0, 0, 1000, 200);
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		
		startLabel = new JLabel("START");
		startLabel.setFont(new Font("Serif", Font.BOLD, 50));
		startLabel.setBounds(0, 200, 1000, 100);
		startLabel.setHorizontalAlignment(JLabel.CENTER);
		
		exitLabel = new JLabel("EXIT");
		exitLabel.setFont(new Font("Serif", Font.BOLD, 50));
		exitLabel.setBounds(0, 280, 1000, 100);
		exitLabel.setHorizontalAlignment(JLabel.CENTER);
		
		add(titleLabel);
		add(startLabel);
		add(exitLabel);
		
		addKeyListener(new MyKeyListener());
		setFocusable(true);
		
	}
	
	private class MyKeyListener implements KeyListener {
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_UP) {
				isUp = true;
				startLabel.setText("< START >");
				exitLabel.setText("EXIT");
			} else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
				isUp = false;
				startLabel.setText("START");
				exitLabel.setText("< EXIT >");
			} else if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				if(isUp == true) {
					mainFrame.setGamePanel();
				} else {
					System.exit(0);
				}
			}
		}
		
		public void keyTyped(KeyEvent e) {}
		public void keyReleased(KeyEvent e) {}
	}
	
	public void paintComponent(Graphics g) {
		g.drawImage(background, 0, 0, null);
	}
}