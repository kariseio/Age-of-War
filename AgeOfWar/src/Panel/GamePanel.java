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
import Units.DinoRider;
import Units.Slingshotman;
import Units.Unit;

public class GamePanel extends JPanel implements Runnable {
	private Player player = new Player(false);
	private Player enemy = new Player(true);
	
	private ArrayList<Unit> player_Character = player.getUnits();
	private ArrayList<Unit> enemy_Character = enemy.getUnits();
	private Queue<Unit> queue = new LinkedList<>();
	
	private JLabel playerHealth;
	private JLabel enemyHealth;
	
	private JButton[] tSpace;
	
	private int queueCount;
	
	Thread th; // 쓰레드
	Image bufImage; // 더블버퍼링용
	Graphics buffer;
	
	private InfoPanel infoPanel;
	private SelectPanel selectPanel;
	
	private Image background = new ImageIcon("src/Images/background.png").getImage();
	MainFrame mainFrame;
	
	public GamePanel(MainFrame mainFrame) {
		setLayout(null);
		this.mainFrame = mainFrame;
		
		playerHealth = new JLabel();
		playerHealth.setForeground(Color.RED);
		playerHealth.setBounds(25, 140, 50, 30);
		add(playerHealth);
		
		enemyHealth = new JLabel();
		enemyHealth.setForeground(Color.RED);
		enemyHealth.setBounds(935, 140, 50, 30);
		add(enemyHealth);
		
		infoPanel = new InfoPanel();
		infoPanel.setBounds(0, 0, 200, 100);
		add(infoPanel);
		
		selectPanel = new SelectPanel();
		selectPanel.setBounds(634, 0, 350, 100);
		add(selectPanel);
		
		tSpace = new JButton[4];
		
		for(int i = 0; i < 4; i++) {
			tSpace[i] = new JButton(new ImageIcon("src/Images/turretSpace.png"));
			tSpace[i].setBounds(25, 310 - 40 * i, 40, 40);
			tSpace[i].setBorderPainted(false);
			tSpace[i].setContentAreaFilled(false);
			tSpace[i].setFocusPainted(false);
			tSpace[i].setVisible(false);
			add(tSpace[i]);
		}
		
//		for(int i = 0; i < 4; i++) {
//			ts[i] = new JLabel(new ImageIcon("src/Images/TS1.png"));
//			ts[i].setBounds(10, 290 - 40 * i, 60, 60);
//			add(ts[i]);
//			
//			ets[i] = new JLabel(new ImageIcon("src/Images/TS1.png"));
//			ets[i].setBounds(905, 290 - 40 * i, 60, 60);
//			add(ets[i]);
//		}
		
		
		
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
		private JButton buyTurret;
		private JButton buyTurretSpace;
		private JButton sellTurret;
		private JButton upgrade;
		private JButton back;
		
		private JButton clubman;
		private JButton slingshotman;
		private JButton dinoRider;
		
		private JButton rockSlingshot;
		private JButton eggAutomatic;
		private JButton primitiveCatapult;
		
		private JButton cancelButton;
		
		private JLabel menuLabel;
		
		public SelectPanel() {
			menuLabel = new JLabel("Menu");
			menuLabel.setForeground(Color.YELLOW);
			menuLabel.setFont(new Font("Serif", Font.BOLD, 20));
			menuLabel.setBounds(30, 10, 100, 20);
			add(menuLabel);
			
			setLayout(null);
			setBackground(new Color(222, 184, 135));
			
			buyUnit = new JButton(new ImageIcon("src/Images/Unit.png"));
			buyUnit.setContentAreaFilled(false);
			buyUnit.setFocusPainted(false);
			buyUnit.setBounds(30, 40, 40, 40);
			buyUnit.addActionListener(e -> {
					menuOff();
					showUnits();
				}
			);
			add(buyUnit);
			
			buyTurret = new JButton(new ImageIcon("src/Images/Turret.png"));
			buyTurret.setContentAreaFilled(false);
			buyTurret.setFocusPainted(false);
			buyTurret.setBounds(85, 40, 40, 40);
			buyTurret.addActionListener(e -> {
				menuOff();
				showTurrets();
			});
			add(buyTurret);
			
			buyTurretSpace = new JButton(new ImageIcon("src/Images/AddTurret.png"));
			buyTurretSpace.setContentAreaFilled(false);
			buyTurretSpace.setFocusPainted(false);
			buyTurretSpace.setBounds(140, 40, 40, 40);
			buyTurretSpace.addActionListener(e -> {
				if(player.getTurretSpace() == 1) {
					if(player.getGold() > 1000) {
						player.updateGold(-1000);
						player.buildTurretSpace();
					}
				}
			});
			add(buyTurretSpace);
			
			sellTurret = new JButton(new ImageIcon("src/Images/SellTurret.png"));
			sellTurret.setContentAreaFilled(false);
			sellTurret.setFocusPainted(false);
			sellTurret.setBounds(195, 40, 40, 40);
			add(sellTurret);
			
			upgrade = new JButton(new ImageIcon("src/Images/Upgrade.png"));
			upgrade.setContentAreaFilled(false);
			upgrade.setFocusPainted(false);
			upgrade.setBounds(250, 40, 40, 40);
			add(upgrade);
			
			back = new JButton(new ImageIcon("src/Images/back.png"));
			back.setContentAreaFilled(false);
			back.setFocusPainted(false);
			back.setBounds(250, 40, 40, 40);
			back.setVisible(false);
			back.addActionListener(e -> {
				menuOn();
			});
			add(back);
			
			clubman = new JButton(new ImageIcon("src/Images/Clubman_Button.png"));
			clubman.setContentAreaFilled(false);
			clubman.setFocusPainted(false);
			clubman.setBounds(30, 40, 40, 40);
			clubman.addActionListener(e -> {
					Clubman unit = new Clubman(false);
					if(player.getGold() >= unit.getPrice() && queue.size() < 5) {
						player.updateGold(-unit.getPrice());
						queue.add(unit);
					}
				}
			);
			clubman.setVisible(false);
			add(clubman);
			
			slingshotman = new JButton(new ImageIcon("src/Images/Slingshotman_Button.png"));
			slingshotman.setContentAreaFilled(false);
			slingshotman.setFocusPainted(false);
			slingshotman.setBounds(85, 40, 40, 40);
			slingshotman.addActionListener(e -> {
					Slingshotman unit = new Slingshotman(false);
					if(player.getGold() >= unit.getPrice() && queue.size() < 5) {
						player.updateGold(-unit.getPrice());
						queue.add(unit);
					}
				}
			);
			slingshotman.setVisible(false);
			add(slingshotman);
			
			dinoRider = new JButton(new ImageIcon("src/Images/DinoRider_Button.png"));
			dinoRider.setContentAreaFilled(false);
			dinoRider.setFocusPainted(false);
			dinoRider.setBounds(140, 40, 40, 40);
			dinoRider.addActionListener(e -> {
					DinoRider unit = new DinoRider(false);
					if(player.getGold() >= unit.getPrice() && queue.size() < 5) {
						player.updateGold(-unit.getPrice());
						queue.add(unit);
					}
				}
			);
			dinoRider.setVisible(false);
			add(dinoRider);
			
			rockSlingshot = new JButton(new ImageIcon("src/Images/RockSlingshot_Button.png"));
			rockSlingshot.setContentAreaFilled(false);
			rockSlingshot.setFocusPainted(false);
			rockSlingshot.setBounds(30, 40, 40, 40);
			rockSlingshot.addActionListener(e -> {
				showTurretSpace();
				}
			);
			rockSlingshot.setVisible(false);
			add(rockSlingshot);
			
			eggAutomatic = new JButton(new ImageIcon("src/Images/EggAutomatic_Button.png"));
			eggAutomatic.setContentAreaFilled(false);
			eggAutomatic.setFocusPainted(false);
			eggAutomatic.setBounds(85, 40, 40, 40);
			eggAutomatic.addActionListener(e -> {
				showTurretSpace();
				}
			);
			eggAutomatic.setVisible(false);
			add(eggAutomatic);
			
			primitiveCatapult = new JButton(new ImageIcon("src/Images/PrimitiveCatapult_Button.png"));
			primitiveCatapult.setContentAreaFilled(false);
			primitiveCatapult.setFocusPainted(false);
			primitiveCatapult.setBounds(140, 40, 40, 40);
			primitiveCatapult.addActionListener(e -> {
				showTurretSpace();
				}
			);
			primitiveCatapult.setVisible(false);
			add(primitiveCatapult);
			
			cancelButton = new JButton(new ImageIcon("src/Images/cancel.png"));
			cancelButton.setContentAreaFilled(false);
			cancelButton.setFocusPainted(false);
			cancelButton.setBounds(80, 40, 200, 40);
			cancelButton.addActionListener(e -> {
				menuOn();
				closeTurretSpace();
				}
			);
			cancelButton.setVisible(false);
			add(cancelButton);
			
		}
		
		public void menuOn() {
			buyUnit.setVisible(true);
			buyTurret.setVisible(true);
			buyTurretSpace.setVisible(true);
			sellTurret.setVisible(true);
			upgrade.setVisible(true);
			hideAll();
		}
		
		public void menuOff() {
			buyUnit.setVisible(false);
			buyTurret.setVisible(false);
			buyTurretSpace.setVisible(false);
			sellTurret.setVisible(false);
			upgrade.setVisible(false);
			back.setVisible(true);
		}
		
		public void hideAll() { // 다 숨기기
			back.setVisible(false);
			clubman.setVisible(false);
			slingshotman.setVisible(false);
			dinoRider.setVisible(false);
			rockSlingshot.setVisible(false);
			eggAutomatic.setVisible(false);
			primitiveCatapult.setVisible(false);
		}
		
		public void showUnits() { // 구매할 수 있는 유닛 보여줌
			if(player.getTech() == 1) {
				clubman.setVisible(true);
				slingshotman.setVisible(true);
				dinoRider.setVisible(true);
			}
		}
		
		public void showTurrets() { // 구매할 수 있는 터렛 보여줌
			if(player.getTech() == 1) {
				rockSlingshot.setVisible(true);
				eggAutomatic.setVisible(true);
				primitiveCatapult.setVisible(true);
			}
		}
		
		public void showTurretSpace() { // 터렛 공간 보여줌
			for(int i = 0; i < player.getTurretSpace(); i++) {
				tSpace[i].setVisible(true);
			}
			hideAll();
			cancelButton.setVisible(true);
		}
		
		public void closeTurretSpace() { // 터렛 공간 끔
			for(int i = 0; i < player.getTurretSpace(); i++) {
				tSpace[i].setVisible(false);
			}
			menuOn();
			cancelButton.setVisible(false);
		}
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(new ImageIcon("src/Images/SelectPanelImg.png").getImage(), 0, 0, null);
		}


		public void actionPerformed(ActionEvent e) {
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		bufImage = createImage(1000, 500);
		buffer = bufImage.getGraphics();
		
		buffer.drawImage(background, 0, 0, null); // 배경 이미지
		buffer.drawImage(player.getBaseImg().getImage(), -100, 290, null); // 아군 기지 시작 X : 100
		buffer.drawImage(enemy.getBaseImg().getImage(), 850+enemy.getBaseImg().getIconWidth(), 290, 850, 290+enemy.getBaseImg().getIconHeight(), 0, 0, enemy.getBaseImg().getIconWidth(), enemy.getBaseImg().getIconHeight(), this); // 적군 기지 시작 X : 870
		
		// 대기열
		buffer.setColor(Color.GRAY);
		buffer.drawRect(220, 10, 400, 15);
		for(int i = 0; i < 5; i++) {
			buffer.drawRect(220+20*i, 35, 15, 15);
		}
		
		buffer.setColor(Color.RED);
		if(queueCount != 0)
			buffer.fillRect(220, 10, 400/50 * queueCount, 15);
		for(int i = 0; i < queue.size(); i++) {
			buffer.fillRect(220+20*i, 35, 15, 15);
		}
		
		// 터렛 그리기
		for(int i = 0; i < player.getTurretSpace(); i++) {
			buffer.drawImage(new ImageIcon("src/Images/TS"+player.getTech()+"_"+(i+1)+".png").getImage(), 10, 290 - 40 * i, 70, 70, null);
			buffer.drawImage(new ImageIcon("src/Images/TS"+enemy.getTech()+"_"+(i+1)+".png").getImage(), 904, 290 - 40 * i, 70, 70, null);
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
		buffer.fillRect(5, 150, 10, 150);
		
		if(player.getHealth() < player.getMaxHealth()) {
			buffer.setColor(Color.GRAY);
			buffer.fillRect(5, 150, 10, (int)(150 / (player.getMaxHealth() / (double)(player.getMaxHealth() - player.getHealth()))));
		}
		
		// 적군 체력 바
		buffer.setColor(Color.RED);
		buffer.fillRect(965, 150, 10, 150);
		
		if(enemy.getHealth() < enemy.getMaxHealth()) {
			buffer.setColor(Color.GRAY);
			buffer.fillRect(965, 150, 10, (int)(150 / (enemy.getMaxHealth() / (double)(enemy.getMaxHealth() - enemy.getHealth()))));
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
		if(Math.random()*5000 < 10) {
			enemy_Character.add(new Clubman(true));
		}
		
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

