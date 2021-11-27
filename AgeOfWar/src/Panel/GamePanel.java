package Panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Main.MainFrame;
import Player.Player;
import Units.Clubman;
import Units.Slingshotman;
import Units.Unit;

public class GamePanel extends JPanel implements Runnable {
	private Player player= new Player(false);
	private Player enemy = new Player(true);
	
	private ArrayList<Unit> player_Character = player.getUnits();
	private ArrayList<Unit> enemy_Character = enemy.getUnits();
	private Queue<Unit> queue = new LinkedList<>();
	
	public JLabel playerHealth;
	public JLabel enemyHealth;
	
	JButton button;
	JButton button2;
	private int queueCount;
	
	Thread th;
	Image bufImage;
	Graphics buffer;
	
	private InfoPanel infoPanel;
	private SelectPanel selectPanel;
	
	private Image background = new ImageIcon("src/Images/background.png").getImage();
	MainFrame mainFrame;
	
	public GamePanel(MainFrame mainFrame) {
		setLayout(null);
		this.mainFrame = mainFrame;
		
		playerHealth = new JLabel("123");
		playerHealth.setForeground(Color.RED);
		playerHealth.setBounds(30, 140, 50, 30);
		add(playerHealth);
		
		enemyHealth = new JLabel("123");
		enemyHealth.setForeground(Color.RED);
		enemyHealth.setBounds(915, 140, 50, 30);
		add(enemyHealth);
		
		button = new JButton("1");
		button.setBounds(750, 150, 50, 50);
		button.addActionListener(e -> {
			enemy.addUnits(new Clubman(true));
		});
		add(button);
		
		button = new JButton("2");
		button.setBounds(820, 150, 50, 50);
		button.addActionListener(e -> {
			enemy.addUnits(new Slingshotman(true));
		});
		add(button);
		
		infoPanel = new InfoPanel();
		infoPanel.setBounds(0, 0, 200, 100);
		add(infoPanel);
		
		selectPanel = new SelectPanel();
		selectPanel.setBounds(634, 0, 350, 100);
		add(selectPanel);
		
		th = new Thread(this);
		th.start();
	}
	
	public void run() {
		while(true) {
			playerAction();
			enemyAction();
			
			// queue action
			if(!queue.isEmpty()) {
				if(queueCount == 50) {
					player.addUnits(queue.poll());
					queueCount = 0;
				} else {
					queueCount++;
				}
			} else {
				queueCount = 0;
			}
			
			infoPanel.update(player.getGold(), player.getExp());
			
			playerHealth.setText("" + (int)player.getHealth());
			enemyHealth.setText("" + (int)enemy.getHealth());
			
			repaint();
			
			try {
				Thread.sleep(16);
			}
			catch(Exception e) {
				return;
			}
		}
	}
	
	private class InfoPanel extends JPanel {
		private JLabel goldLabel;
		private JLabel expLabel;
		
		public InfoPanel() {
			setLayout(null);
			
			goldLabel = new JLabel();
			goldLabel.setFont(new Font("Serif", Font.PLAIN, 20));
			goldLabel.setBounds(57, 7, 200, 30);
			
			expLabel = new JLabel();
			expLabel.setFont(new Font("Serif", Font.PLAIN, 20));
			expLabel.setBounds(10, 30, 200, 50);
			
			add(goldLabel);
			add(expLabel);
		}
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(new ImageIcon("src/Images/InfoPanelImg.png").getImage(), 0, 0, null);
			
			g.setColor(Color.YELLOW);
			g.fillOval(13, 13, 25, 25);
		}
		
		public void update(int gold, int exp) {
			goldLabel.setText("" + gold);
			expLabel.setText("Exp : " + exp);
		}
	}
	
	private class SelectPanel extends JPanel implements ActionListener {
		private JButton buyUnit;
		private JButton unit1;
		private JButton unit2;
		private JButton unit3;
		private JButton buyTurret;
		private JButton buyTurretSpace;
		private JButton sellTurret;
		private JButton upgrade;
		
		private JLabel menuLabel;
		
		public SelectPanel() {
			menuLabel = new JLabel("Menu");
			menuLabel.setForeground(Color.YELLOW);
			menuLabel.setFont(new Font("Serif", Font.BOLD, 20));
			menuLabel.setBounds(30, 10, 100, 20);
			add(menuLabel);
			
			setLayout(null);
			setBackground(new Color(222, 184, 135));
			
			buyUnit = new JButton(new ImageIcon("src/Images/Clubman_Button.png"));
			buyUnit.setBorderPainted(false);
			buyUnit.setContentAreaFilled(false);
			buyUnit.setFocusPainted(false);
			buyUnit.setBounds(30, 40, 40, 40);
			buyUnit.addActionListener(e -> {
					Clubman unit = new Clubman(false);
					if(player.getGold() >= unit.getPrice() && queue.size() < 5) {
						player.updateGold(-unit.getPrice());
						queue.add(unit);
					}
				}
			);
			add(buyUnit);
			
			unit1 = new JButton(new ImageIcon("src/Images/Clubman_Button.png"));
			
			buyTurret = new JButton(new ImageIcon("src/Images/Slingshotman_Button.png"));
			buyTurret.setBounds(85, 40, 40, 40);
			buyTurret.addActionListener(e -> {
					Slingshotman unit = new Slingshotman(false);
					if(player.getGold() >= unit.getPrice() && queue.size() < 5) {
						player.updateGold(-unit.getPrice());
						queue.add(unit);
					}
				}
			);
			add(buyTurret);
			
			buyTurretSpace = new JButton();
			buyTurretSpace.setBounds(140, 40, 40, 40);
			add(buyTurretSpace);
			
			sellTurret = new JButton();
			sellTurret.setBounds(195, 40, 40, 40);
			add(sellTurret);
			
			upgrade = new JButton();
			upgrade.setBounds(250, 40, 40, 40);
			add(upgrade);
		}
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(new ImageIcon("src/Images/SelectPanelImg.png").getImage(), 0, 0, null);
		}


		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == unit1 || e.getSource() == unit2 || e.getSource() == unit3) {
				
			}
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		bufImage = createImage(1000, 500);
		buffer = bufImage.getGraphics();
		
		buffer.drawImage(background, 0, 0, null); // 배경 이미지
		buffer.drawImage(player.getBaseImg().getImage(), -30, 310, null); // 아군 기지 시작 X : 100
		buffer.drawImage(enemy.getBaseImg().getImage(), 860, 310, null); // 적군 기지 시작 X : 870
		
		buffer.setColor(Color.GRAY);
		buffer.drawRect(220, 10, 400, 15);
		if(queueCount != 0)
			buffer.fillRect(220, 10, 400/50 * queueCount, 15);
		
		for(int i = 0; i < 5; i++) {
			buffer.drawRect(220+20*i, 35, 15, 15);
		}
		for(int i = 0; i < queue.size(); i++) {
			buffer.fillRect(220+20*i, 35, 15, 15);
		}
		
		// 아군 유닛 그리기
		for(int i = 0; i < player_Character.size(); i++) {
			Unit unit = player_Character.get(i);
			buffer.drawImage(unit.getImage(), unit.getX(), unit.getY(), null);
			
			// 체력 바
			if(unit.getHealth() < unit.getMaxHealth()) {
				buffer.setColor(Color.RED);
				buffer.fillRect(unit.getX() + 5, unit.getY() - 5, unit.getWidth(), 3);
				buffer.setColor(Color.GRAY);
				buffer.fillRect(unit.getX() + 5, unit.getY() - 5, (int) (unit.getWidth() / (unit.getMaxHealth() / (double)(unit.getMaxHealth() - unit.getHealth()))), 3);
			}
		}
		
		// 적군 유닛 그리기
		for(int i = 0; i < enemy_Character.size(); i++) {
			Unit unit = enemy_Character.get(i);
			buffer.drawImage(unit.getImage(), unit.getX(), unit.getY(), null);
			
			// 체력 바
			if(unit.getHealth() < unit.getMaxHealth()) {
				buffer.setColor(Color.RED);
				buffer.fillRect(unit.getX() + 5, unit.getY() - 5, unit.getWidth(), 3);
				buffer.setColor(Color.GRAY);
				buffer.fillRect(unit.getX() + 5, unit.getY() - 5, (int) (unit.getWidth() / (unit.getMaxHealth() / (double)(unit.getMaxHealth() - unit.getHealth()))) , 3);
			}
		}
		
		// 플레이어 체력 바
		buffer.setColor(Color.RED);
		buffer.fillRect(10, 150, 15, 150);
		
		if(player.getHealth() < player.getMaxHealth()) {
			buffer.setColor(Color.GRAY);
			buffer.fillRect(10, 150, 15, (int)(150 / (player.getMaxHealth() / (double)(player.getMaxHealth() - player.getHealth()))));
		}
		
		// 적군 체력 바
		buffer.setColor(Color.RED);
		buffer.fillRect(955, 150, 15, 150);
		
		if(enemy.getHealth() < enemy.getMaxHealth()) {
			buffer.setColor(Color.GRAY);
			buffer.fillRect(955, 150, 15, (int)(150 / (enemy.getMaxHealth() / (double)(enemy.getMaxHealth() - enemy.getHealth()))));
		}
		g.drawImage(bufImage, 0, 0, this);
		drawGrid(g);
	}
	
	public void drawGrid(Graphics g) {
		for(int i = 0; i < 11; i++) {
			g.drawLine(i * 100, 0, i * 100, 500);
			g.drawLine(0, i * 100, 1000, i * 100);
		}
	}
	
	public void playerAction() { // 플레이어 행동들
		// 체력 0
		if(player.getHealth() <= 0) {
			th.interrupt();
			mainFrame.setResultPanel(false);
		}
			
		for(int i = 0; i < player_Character.size(); i++) {
			Unit unit = player_Character.get(i);
			unit.action(player, enemy, i);
			
			if(unit.getHealth() <= 0) { // 삭제되는 조건 (체력0) 
				enemy.updateGold((int)(unit.getPrice() * 1.5));
				player_Character.remove(i);
			}
		}
	}
	
	public void enemyAction() { // 적 행동
		// 체력 0
		if(enemy.getHealth() <= 0) {
			th.interrupt();
			mainFrame.setResultPanel(true);
		}
		
		for(int i = 0; i < enemy_Character.size(); i++) {
			Unit unit = enemy_Character.get(i);
			unit.action(player, enemy, i);
			
			if(unit.getHealth() <= 0) { // 삭제되는 조건 (체력0)
				player.updateGold((int)(unit.getPrice() * 1.5));
				enemy_Character.remove(i);
			}
		}
	}
}
