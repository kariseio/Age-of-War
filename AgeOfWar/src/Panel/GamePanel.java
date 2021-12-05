package Panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Main.MainFrame;
import Player.Player;
import Turret.Bullet;
import Turret.Turret;
import Units.Archer;
import Units.Clubman;
import Units.DinoRider;
import Units.Knight;
import Units.Slingshotman;
import Units.Swordman;
import Units.Unit;

public class GamePanel extends JPanel implements Runnable {
	private Player player = new Player(false); // 플레이어
	private Player enemy = new Player(true); // 적
	
	private ArrayList<Unit> player_Character = player.getUnits(); // 플레이어 유닛 리스트
	private ArrayList<Unit> enemy_Character = enemy.getUnits(); // 적군 유닛 리스트
	private Turret[] player_Turrets = player.getTurrets();
	private Turret[] enemy_Turrets = enemy.getTurrets();
	private ArrayList<Bullet> player_Bullet = player.getBullets(); // 플레이어 총알
	private ArrayList<Bullet> enemy_Bullet = enemy.getBullets(); // 적 총알
	private Queue<Unit> queue = new LinkedList<>(); // 유닛 뽑을 때 대기열
	
	private JLabel playerHealth; // 플레이어 체력 수치
	private JLabel enemyHealth; // 적 체력 수치
	
	private JButton[] tSpace; // 터렛 공간
	private JButton[] sellTS;
	
	private JButton xSpeed;
	private int gameSpeed = 1;
	
	Unit queueUnit = null; // 큐에서 꺼낸 유닛
	boolean isQueue = false; // 큐에서 유닛 꺼냈는지
	private final int maxQueue = 4; // 큐 최대 4 => 5개까지임 (하나는 꺼내서 쓰고 있으니까)
	private int queueCount; // 큐 진행 시간
	
	private String selectedTurret;
	
//	private String[] turret_types = new String[] {"", "RockSlingshot", "EggAutomatic", "PrimitiveCatapult"};
	private String[] unit_types = new String[] {"", "Clubman", "Slingshotman", "DinoRider", "Swordman", "Archer", "Knight"};
	private int unit_type;
	
	private int timer = 0;
	private int tech_timer = 0;
	private int unit_level = 1;
	
	Thread th; // 쓰레드
	Image bufImage; // 더블버퍼링용
	Graphics buffer;
	
	private InfoPanel infoPanel;
	private SpecialPanel specialPanel;
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
		
		specialPanel = new SpecialPanel();
		specialPanel.setBounds(850, 100, 150, 40);
		add(specialPanel);
		
		selectPanel = new SelectPanel();
		selectPanel.setBounds(634, 0, 350, 100);
		add(selectPanel);
		
		tSpace = new JButton[4];
		
		for(int i = 0; i < 4; i++) {
			tSpace[i] = new JButton(new ImageIcon("src/Images/turretSpace.png"));
			tSpace[i].setBounds(25, 350 - 40 * i, 40, 40);
			tSpace[i].setBorderPainted(false);
			tSpace[i].setContentAreaFilled(false);
			tSpace[i].setFocusPainted(false);
			tSpace[i].setVisible(false);
			add(tSpace[i]);
		}
		tSpace[0].addActionListener(e -> {
			player.addTurrets(0, selectedTurret, false);
			selectPanel.closeTurretSpace();
		});
		tSpace[1].addActionListener(e -> {
			player.addTurrets(1, selectedTurret, false);
			selectPanel.closeTurretSpace();
		});
		tSpace[2].addActionListener(e -> {
			player.addTurrets(2, selectedTurret, false);
			selectPanel.closeTurretSpace();
		});
		tSpace[3].addActionListener(e -> {
			player.addTurrets(3, selectedTurret, false);
			selectPanel.closeTurretSpace();
		});
		
		sellTS = new JButton[4];
		
		for(int i = 0; i < 4; i++) {
			sellTS[i] = new JButton(new ImageIcon("src/Images/turretSpace.png"));
			sellTS[i].setBounds(25, 350 - 40 * i, 40, 40);
			sellTS[i].setBorderPainted(false);
			sellTS[i].setContentAreaFilled(false);
			sellTS[i].setFocusPainted(false);
			sellTS[i].setVisible(false);
			add(sellTS[i]);
		}
		
		sellTS[0].addActionListener(e -> {
			player.sellTurret(0);
			selectPanel.closeTurretSpace();
		});
		sellTS[1].addActionListener(e -> {
			player.sellTurret(1);
			selectPanel.closeTurretSpace();
		});
		sellTS[2].addActionListener(e -> {
			player.sellTurret(2);
			selectPanel.closeTurretSpace();
		});
		sellTS[3].addActionListener(e -> {
			player.sellTurret(3);
			selectPanel.closeTurretSpace();
		});
		
		xSpeed = new JButton("x1");
		xSpeed.setBounds(5, 105, 50, 50);
		xSpeed.setVisible(true);
		xSpeed.addActionListener(e -> {
			if(gameSpeed == 1) {
				gameSpeed = 2;
				xSpeed.setText("x2");
			} else if(gameSpeed == 2) {
				gameSpeed = 4;
				xSpeed.setText("x4");
			} else if(gameSpeed == 4) {
				gameSpeed = 1;
				xSpeed.setText("x1");
			} 
		});
		add(xSpeed);
		
//		enemy.addTurrets(0, "RockSlingshot", true);
//		enemy.addTurrets(1, "EggAutomatic", true);
//		enemy.addTurrets(2, "PrimitiveCatapult", true);
		
		th = new Thread(this);
		th.start();
	}
	
	public void run() {
		while(true) {
			playerAction();
			enemyAction();
			
			// queue action
			if(!queue.isEmpty() || isQueue == true) {
				if(isQueue == false) {
					queueUnit = queue.poll();
					isQueue = true;
				}
				
				if(queueCount == queueUnit.getQueueTime()) {
					player.addUnits(queueUnit);
					queueCount = 0;
					isQueue = false;
				} else {
					queueCount++;
				}
			} else {
				queueCount = 0;
			}
			
			infoPanel.update(player.getGold(), player.getExp());
			
			playerHealth.setText("" + (int)player.getHealth()); // 플레이어 체력 업데이트
			enemyHealth.setText("" + (int)enemy.getHealth()); // 적군 체력 업데이트
			
			repaint();
			
			try {
				Thread.sleep(32 / gameSpeed);
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
	
	private class SpecialPanel extends JPanel {
		private JLabel label;
		private JButton special;
		
		public SpecialPanel() {
			setLayout(null);
			
			label = new JLabel("Special");
			label.setForeground(Color.YELLOW);
			label.setFont(new Font("Serif", Font.PLAIN, 15));
			label.setBounds(10, 0, 50, 30);
			add(label);
			
			special = new JButton(new ImageIcon("src/Images/special" + player.getTech() + ".png"));
			special.setBounds(40, 3, 100, 30);
			special.setBorderPainted(false);
			special.setContentAreaFilled(false);
			special.setFocusPainted(false);
			special.addActionListener(e -> {
				if(player.getTech() == 1) { // 스페셜 1
					for(int i = 0; i < 15; i++) {
						player.addBullets(new Bullet(200 + player.getTech(), (int)(Math.random() * 750 + 100), -(int)(Math.random() * 2000 + 200), (int)(Math.random() * 750 + 100), 500, 100, 2));
					}
				} else if(player.getTech() == 2) { // 스페셜 2
					for(int i = 0; i < 15; i++) {
						player.addBullets(new Bullet(200 + player.getTech(), (int)(Math.random() * 750 + 100), -(int)(Math.random() * 2000 + 200), (int)(Math.random() * 750 + 100), 500, 150, 3));
					}
				}
			});
			add(special);
		}
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(new ImageIcon("src/Images/SpecialPanelImg.png").getImage(), 0, 0, null);
		}
	}
	
	private class SelectPanel extends JPanel {
		// Menu
		private JButton buyUnit;
		private JButton buyTurret;
		private JButton buyTurretSpace;
		private JButton sellTurret;
		private JButton upgrade;
		private JButton back;
		// Unit
		private JButton clubman;
		private JButton slingshotman;
		private JButton dinoRider;
		private JButton swordman;
		private JButton archer;
		private JButton knight;
		// Turret
		private JButton rockSlingshot;
		private JButton eggAutomatic;
		private JButton primitiveCatapult;
		private JButton catapult;
		private JButton fireCatapult;
		private JButton oil;
		
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
				} else if(player.getTurretSpace() == 2) {
					if(player.getGold() > 4000) {
						player.updateGold(-4000);
						player.buildTurretSpace();
					}
				} else if(player.getTurretSpace() == 3) {
					if(player.getGold() > 7500) {
						player.updateGold(-7500);
						player.buildTurretSpace();
					}
				}
			});
			add(buyTurretSpace);
			
			sellTurret = new JButton(new ImageIcon("src/Images/SellTurret.png")); // 터렛 판매
			sellTurret.setContentAreaFilled(false);
			sellTurret.setFocusPainted(false);
			sellTurret.setBounds(195, 40, 40, 40);
			sellTurret.addActionListener(e -> {
				menuOff();
				showSellTurretSpace();
			});
			add(sellTurret);
			
			upgrade = new JButton(new ImageIcon("src/Images/Upgrade.png")); // 테크 업그레이드
			upgrade.setContentAreaFilled(false);
			upgrade.setFocusPainted(false);
			upgrade.setBounds(250, 40, 40, 40);
			upgrade.addActionListener(e -> {
				if(player.getTech() == 1) {
					if(player.getExp() >= 4000) {
						player.techUp();
					}
				} else if(player.getTech() == 2) {
					if(player.getExp() >= 14000) {
						player.techUp();
					}
				} else if(player.getTech() == 3) {
					if(player.getExp() >= 45000) {
						player.techUp();
					}
				} else if(player.getTech() == 4) {
					if(player.getExp() >= 200000) {
						player.techUp();
					}
				}
			});
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
					if(player.getGold() >= unit.getPrice() && queue.size() < maxQueue) {
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
					if(player.getGold() >= unit.getPrice() && queue.size() < maxQueue) {
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
					if(player.getGold() >= unit.getPrice() && queue.size() < maxQueue) {
						player.updateGold(-unit.getPrice());
						queue.add(unit);
					}
				}
			);
			dinoRider.setVisible(false);
			add(dinoRider);
			
			swordman = new JButton(new ImageIcon("src/Images/Swordman_Button.png"));
			swordman.setContentAreaFilled(false);
			swordman.setFocusPainted(false);
			swordman.setBounds(30, 40, 40, 40);
			swordman.addActionListener(e -> {
					Swordman unit = new Swordman(false);
					if(player.getGold() >= unit.getPrice() && queue.size() < maxQueue) {
						player.updateGold(-unit.getPrice());
						queue.add(unit);
					}
				}
			);
			swordman.setVisible(false);
			add(swordman);
			
			archer = new JButton(new ImageIcon("src/Images/Archer_Button.png"));
			archer.setContentAreaFilled(false);
			archer.setFocusPainted(false);
			archer.setBounds(85, 40, 40, 40);
			archer.addActionListener(e -> {
					Archer unit = new Archer(false);
					if(player.getGold() >= unit.getPrice() && queue.size() < maxQueue) {
						player.updateGold(-unit.getPrice());
						queue.add(unit);
					}
				}
			);
			archer.setVisible(false);
			add(archer);
			
			knight = new JButton(new ImageIcon("src/Images/Knight_Button.png"));
			knight.setContentAreaFilled(false);
			knight.setFocusPainted(false);
			knight.setBounds(140, 40, 40, 40);
			knight.addActionListener(e -> {
					Knight unit = new Knight(false);
					if(player.getGold() >= unit.getPrice() && queue.size() < maxQueue) {
						player.updateGold(-unit.getPrice());
						queue.add(unit);
					}
				}
			);
			knight.setVisible(false);
			add(knight);
			
			
			// Turrets
			rockSlingshot = new JButton(new ImageIcon("src/Images/RockSlingshot_Button.png"));
			rockSlingshot.setContentAreaFilled(false);
			rockSlingshot.setFocusPainted(false);
			rockSlingshot.setBounds(30, 40, 40, 40);
			rockSlingshot.addActionListener(e -> {
				if(player.getGold() < 100) return; // 100원
				
				showTurretSpace();
				selectedTurret = "RockSlingshot";
				}
			);
			rockSlingshot.setVisible(false);
			add(rockSlingshot);
			
			eggAutomatic = new JButton(new ImageIcon("src/Images/EggAutomatic_Button.png"));
			eggAutomatic.setContentAreaFilled(false);
			eggAutomatic.setFocusPainted(false);
			eggAutomatic.setBounds(85, 40, 40, 40);
			eggAutomatic.addActionListener(e -> {
				if(player.getGold() < 200) return; // 200원
				
				showTurretSpace();
				selectedTurret = "EggAutomatic";
				}
			);
			eggAutomatic.setVisible(false);
			add(eggAutomatic);
			
			primitiveCatapult = new JButton(new ImageIcon("src/Images/PrimitiveCatapult_Button.png"));
			primitiveCatapult.setContentAreaFilled(false);
			primitiveCatapult.setFocusPainted(false);
			primitiveCatapult.setBounds(140, 40, 40, 40);
			primitiveCatapult.addActionListener(e -> {
				if(player.getGold() < 500) return; // 500원
				
				showTurretSpace();
				selectedTurret = "PrimitiveCatapult";
				}
			);
			primitiveCatapult.setVisible(false);
			add(primitiveCatapult);
			
			catapult = new JButton(new ImageIcon("src/Images/Catapult_Button.png"));
			catapult.setContentAreaFilled(false);
			catapult.setFocusPainted(false);
			catapult.setBounds(30, 40, 40, 40);
			catapult.addActionListener(e -> {
				if(player.getGold() < 500) return; // 500원
				
				showTurretSpace();
				selectedTurret = "Catapult";
				}
			);
			catapult.setVisible(false);
			add(catapult);
			
			fireCatapult = new JButton(new ImageIcon("src/Images/FireCatapult_Button.png"));
			fireCatapult.setContentAreaFilled(false);
			fireCatapult.setFocusPainted(false);
			fireCatapult.setBounds(85, 40, 40, 40);
			fireCatapult.addActionListener(e -> {
				if(player.getGold() < 750) return; // 750원
				
				showTurretSpace();
				selectedTurret = "FireCatapult";
				}
			);
			fireCatapult.setVisible(false);
			add(fireCatapult);
			
			oil = new JButton(new ImageIcon("src/Images/Oil_Button.png"));
			oil.setContentAreaFilled(false);
			oil.setFocusPainted(false);
			oil.setBounds(140, 40, 40, 40);
			oil.addActionListener(e -> {
				if(player.getGold() < 1000) return; // 1000원
				
				showTurretSpace();
				selectedTurret = "Oil";
				}
			);
			oil.setVisible(false);
			add(oil);
			
			
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
			menuLabel.setText("Menu");
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
			// unit
			clubman.setVisible(false);
			slingshotman.setVisible(false);
			dinoRider.setVisible(false);
			swordman.setVisible(false);
			archer.setVisible(false);
			knight.setVisible(false);
			// turret
			rockSlingshot.setVisible(false);
			eggAutomatic.setVisible(false);
			primitiveCatapult.setVisible(false);
			catapult.setVisible(false);
			fireCatapult.setVisible(false);
			oil.setVisible(false);
			
		}
		
		public void showUnits() { // 구매할 수 있는 유닛 보여줌
			if(player.getTech() == 1) {
				clubman.setVisible(true);
				slingshotman.setVisible(true);
				dinoRider.setVisible(true);
			} else if(player.getTech() == 2) {
				swordman.setVisible(true);
				archer.setVisible(true);
				knight.setVisible(true);
			}
			menuLabel.setText("Unit");
		}
		
		public void showTurrets() { // 구매할 수 있는 터렛 보여줌
			if(player.getTech() == 1) {
				rockSlingshot.setVisible(true);
				eggAutomatic.setVisible(true);
				primitiveCatapult.setVisible(true);
			} else if(player.getTech() == 2) {
				catapult.setVisible(true);
				fireCatapult.setVisible(true);
				oil.setVisible(true);
			}
			menuLabel.setText("Turret");
		}
		
		public void showTurretSpace() { // 터렛 공간 보여줌
			for(int i = 0; i < player.getTurretSpace(); i++) {
				if(player.getHasTurret(i) == false)
					tSpace[i].setVisible(true);
			}
			hideAll();
			cancelButton.setVisible(true);
		}
		
		public void closeTurretSpace() { // 터렛 공간 끔
			for(int i = 0; i < player.getTurretSpace(); i++) {
				tSpace[i].setVisible(false);
				sellTS[i].setVisible(false);
			}
			menuOn();
			cancelButton.setVisible(false);
		}
		
		public void showSellTurretSpace() { // 터렛 판매 공간 보여줌
			for(int i = 0; i < player.getTurretSpace(); i++) {
				if(player.getHasTurret(i) == true) {
					sellTS[i].setVisible(true);
				}
			}
			hideAll();
			cancelButton.setVisible(true);
			menuLabel.setText("Sell");
		}
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(new ImageIcon("src/Images/SelectPanelImg.png").getImage(), 0, 0, null);
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
			buffer.fillRect(220, 10, 400/queueUnit.getQueueTime() * queueCount, 15);
		if(isQueue == true)
			buffer.fillRect(220, 35, 15, 15);
		for(int i = 0; i < queue.size(); i++) {
			buffer.fillRect(240+20*i, 35, 15, 15);
		}
		
		// 아군 터렛 공간 그리기
		for(int i = 1; i < player.getTurretSpace(); i++) {
			buffer.drawImage(new ImageIcon("src/Images/TS"+player.getTech()+"_"+i+".png").getImage(), 10, 330 - 40 * i, 70, 70, null);
		}
		
		// 적군 터렛 공간 그리기
		for(int i = 1; i < enemy.getTurretSpace(); i++) {
			buffer.drawImage(new ImageIcon("src/Images/TS"+enemy.getTech()+"_"+i+".png").getImage(), 904, 330 - 40 * i, 70, 70, null);
		}
		
		// 설치된 터렛 그리기
		for(int i = 0; i < 4; i++) {
			if(player.getHasTurret(i) == true) {
				buffer.drawImage(player_Turrets[i].getImageIcon().getImage(), player_Turrets[i].getX(), player_Turrets[i].getY(), player_Turrets[i].getImageIcon().getIconWidth(), player_Turrets[i].getImageIcon().getIconHeight(), null);
			}
			if(enemy.getHasTurret(i) == true) {
				buffer.drawImage(enemy_Turrets[i].getImageIcon().getImage(), enemy_Turrets[i].getX(), enemy_Turrets[i].getY(), enemy_Turrets[i].getImageIcon().getIconWidth(), enemy_Turrets[i].getImageIcon().getIconHeight(), null);
			}
		}
		
		// 아군 bullet 그리기
		for(int i = 0; i < player_Bullet.size(); i++) {
			Bullet bullet = player_Bullet.get(i);
			buffer.drawImage(new ImageIcon("src/Images/Bullet"+ bullet.getBulletId()+".png").getImage(), bullet.getX(), bullet.getY(), null);
		}
		
		// 적군 bullet 그리기
		for(int i = 0; i < enemy_Bullet.size(); i++) {
			Bullet bullet = enemy_Bullet.get(i);
			buffer.drawImage(new ImageIcon("src/Images/Bullet"+ bullet.getBulletId()+".png").getImage(), bullet.getX(), bullet.getY(), null);
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
				int reward = (int) Math.round(unit.getPrice() * 1.3);
				enemy.updateGold(reward);
				enemy.updateExp(reward * 2);
				player.updateExp(Math.round(reward / 2));
				player_Character.remove(i);
			}
		}
		
		for(int i = 0; i < 4; i++) { // 플레이어 터렛 action
			if(player.getHasTurret(i) == true) {
				player_Turrets[i].shoot(player, enemy);
			}
		}
		
		for(int i = 0; i < player_Bullet.size(); i++) { // 플레이어 bullet action
			Bullet bullet = player_Bullet.get(i);
			bullet.action();
			
			if(bullet.getY() > 450) // 바닥에 박힘
				player_Bullet.remove(i);
			
			for(int j = 0; j < enemy_Character.size(); j++) { // 피격 체크
				if(isHit(bullet.getX(), bullet.getY(), enemy_Character.get(j))) {
					if(bullet.getBulletId() == 4) { // 갈라지는 탄
						bullet.particle(player, enemy_Character.get(j)); // 갈라진 탄환 생성
					}
					enemy_Character.get(j).hit(bullet.getDamage());
					player_Bullet.remove(i);
				}
			}
		}
	}
	
	public void enemyAction() { // 적 행동
		// 적군 테크 업
		tech_timer++;
		if(unit_level == 1) {
			if(tech_timer == 1500) {
				unit_level++;
			}
		} else if(unit_level == 2) {
			if(tech_timer == 5000) {
				unit_level++;
			}
		}
		if(tech_timer == 8000) {
			if(enemy.getTech() != 5) {
				enemy.techUp();
				
				unit_level = 0;
				tech_timer = 0;
			}
		}
		
		// 적군 터렛 설치
		if(enemy.getTech() == 1) {
			if(tech_timer == 1000) {
				enemy.addTurrets(0, "RockSlingshot", true);
			} else if(tech_timer == 4000) {
				enemy.sellTurret(0);
				enemy.addTurrets(0, "EggAutomatic", true);
			} else if(tech_timer == 6000) {
				enemy.sellTurret(0);
				enemy.addTurrets(0, "PrimitiveCatapult", true);
			}
		} else if(enemy.getTech() == 2) {
//			if(tech_timer == 1000) {
////				enemy.sellTurret(0);
//				enemy.addTurrets(0, "Catapult", true);
//			} else if(tech_timer == 4000) {
//				enemy.buildTurretSpace();
//				enemy.sellTurret(0);
//				enemy.addTurrets(0, "Oil", true);
//			} else if(tech_timer == 6000) {
//				enemy.sellTurret(0);
//				enemy.addTurrets(1, "FireCatapult", true);
//			}
		}
		
		// 적군 유닛 생산
		timer++;
		if(timer == 60) {
			if(Math.random() < 0.3) {
				if(enemy_Character.size() < 8) { // 8마리까지 생성 제한
					unit_type = (int)(Math.random() * unit_level + 1);
					unit_type = unit_type + (enemy.getTech() - 1) * 3;
					enemy.addEUnits(unit_types[unit_type]);
				}
			}
			timer = 0;
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
				int reward = (int) Math.round(unit.getPrice() * 1.3);
				player.updateGold(reward);
				player.updateExp(reward * 2);
				enemy.updateExp(Math.round(reward / 2));
				enemy_Character.remove(i);
			}
		}
		
		for(int i = 0; i < 4; i++) { // 적군 터렛 action
			if(enemy.getHasTurret(i) == true)
				enemy_Turrets[i].shoot(player, enemy);
		}
		
		for(int i = 0; i < enemy_Bullet.size(); i++) { // 적군 bullet action
			Bullet bullet = enemy_Bullet.get(i);
			bullet.action();
			System.out.println("123");
			
			if(bullet.getY() > 450) // 바닥에 박힘
				enemy_Bullet.remove(i);
			
			for(int j = 0; j < player_Character.size(); j++) { // 피격 체크
				if(isHit(bullet.getX(), bullet.getY(), player_Character.get(j))) {
					if(bullet.getBulletId() == 4) { // 갈라지는 탄
						bullet.particle(enemy, player_Character.get(j)); // 갈라진 탄환 생성
					}
					player_Character.get(j).hit(bullet.getDamage());
					enemy_Bullet.remove(i);
				}
				
			}
		}
	}
	
	public boolean isHit(int x, int y, Unit unit) {
		if(x > unit.getX() && x < unit.getX() + unit.getWidth() && y > unit.getY() && y < unit.getY() + unit.getHeight())
			return true;
		else 
			return false;
	}
}

