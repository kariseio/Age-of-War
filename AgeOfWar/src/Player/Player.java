package Player;

import java.util.ArrayList;

import javax.swing.ImageIcon;

import Turret.Missle;
import Units.Unit;

public class Player {
	private int maxHealth;
	private int health;
	private int gold;
	private int exp;
	private int turretSpace;
	private boolean isEnemy;
	private int tech;
	private ImageIcon baseImg;
	
	private ArrayList<Unit> units;
	private ArrayList<Missle> missle;
	
	public Player(boolean isEnemy) {
		units = new ArrayList<>();
		
		maxHealth = 500;
		health = 500;
		gold = 150;
		exp = 1;
		turretSpace = 0;
		tech = 1;
		
		this.isEnemy = isEnemy;
		
		if(isEnemy == false) {
			baseImg = new ImageIcon("src/Images/base1.png");
		} else {
			baseImg = new ImageIcon("src/Images/base1.png");
		}
	}
	
	public void updateGold(int gold) {
		this.gold += gold;
	}
	public void updateExp(int exp) {
		this.exp += exp;
	}
	public ArrayList<Unit> getUnits() {
		return units;
	}
	public void addUnits(Unit unit) {
		units.add(unit);
	}
	public int numberOfUnits() {
		return units.size();
	}
	public int getMaxHealth() {
		return maxHealth;
	}
	public int getHealth() {
		return health;
	}
	public int getGold() {
		return gold;
	}
	
	public int getExp() {
		return exp;
	}
	public int getTech() {
		return tech;
	}
	public ImageIcon getBaseImg() {
		return baseImg;
	}
	
	public void hit(int power) {
		if(health > 0)
			health -= power;
	}
}
