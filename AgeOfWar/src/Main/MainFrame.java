package Main;

import javax.swing.JFrame;

import Panel.GamePanel;
import Panel.HomePanel;
import Panel.ResultPanel;

public class MainFrame extends JFrame {
	HomePanel homePanel;
	GamePanel gamePanel;
	ResultPanel resultPanel;
	
	public MainFrame() {
		setTitle("Age Of War");
		setSize(1000, 500);
		setLocation(300, 200);
		
		homePanel = new HomePanel(this);
		add(homePanel);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public void setHomePanel() {
		homePanel = new HomePanel(this);
		remove(resultPanel);
		add(homePanel);
		setVisible(false);
		setVisible(true);
	}
	
	public void setGamePanel() {
		gamePanel = new GamePanel(this);
		remove(homePanel);
		add(gamePanel);
		setVisible(false);
		setVisible(true);
	}
	
	public void setResultPanel(boolean win) {
		resultPanel = new ResultPanel(this, win);
		remove(gamePanel);
		add(resultPanel);
		setVisible(false);
		setVisible(true);
		resultPanel.requestFocus();
	}
	
	public static void main(String[] args) {
		new MainFrame();
	}
}