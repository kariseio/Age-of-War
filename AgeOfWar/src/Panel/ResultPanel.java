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

public class ResultPanel extends JPanel {
	private JLabel resultLabel;
	private JLabel homeLabel;
	private JLabel exitLabel;
	private boolean isUp;
	MainFrame mainFrame;
	
	private Image background = new ImageIcon("src/Images/background.png").getImage();
	
	public ResultPanel(MainFrame mainFrame, boolean win) {
		setLayout(null);
		
		this.mainFrame = mainFrame;
		
		resultLabel = new JLabel();
		if(win)
			resultLabel.setText("WIN");
		else
			resultLabel.setText("LOSE");
		
		resultLabel.setFont(new Font("Serif", Font.BOLD, 100));
		resultLabel.setBounds(0, 0, 1000, 200);
		resultLabel.setHorizontalAlignment(JLabel.CENTER);
		
		homeLabel = new JLabel("HOME");
		homeLabel.setFont(new Font("Serif", Font.BOLD, 50));
		homeLabel.setBounds(0, 200, 1000, 100);
		homeLabel.setHorizontalAlignment(JLabel.CENTER);
		
		exitLabel = new JLabel("EXIT");
		exitLabel.setFont(new Font("Serif", Font.BOLD, 50));
		exitLabel.setBounds(0, 280, 1000, 100);
		exitLabel.setHorizontalAlignment(JLabel.CENTER);
		
		add(resultLabel);
		add(homeLabel);
		add(exitLabel);
		
		addKeyListener(new MyKeyListener());
		setFocusable(true);
	}
	
	private class MyKeyListener implements KeyListener {
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_UP) {
				isUp = true;
				homeLabel.setText("< HOME >");
				exitLabel.setText("EXIT");
			} else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
				isUp = false;
				homeLabel.setText("HOME");
				exitLabel.setText("< EXIT >");
			} else if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				if(isUp == true) {
					mainFrame.setHomePanel();
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