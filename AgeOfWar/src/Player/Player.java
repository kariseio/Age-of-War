package Player;

import java.util.ArrayList;

import javax.swing.ImageIcon;

import Turret.Bullet;
import Turret.Catapult;
import Turret.EggAutomatic;
import Turret.FireCatapult;
import Turret.Oil;
import Turret.PrimitiveCatapult;
import Turret.RockSlingshot;
import Turret.Turret;
import Units.Archer;
import Units.Clubman;
import Units.DinoRider;
import Units.Knight;
import Units.Slingshotman;
import Units.Swordman;
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
	private Turret[] turrets;
	public boolean[] hasTurret;
	private ArrayList<Bullet> bullets;
	
	public Player(boolean isEnemy) {
		units = new ArrayList<>();
		turrets = new Turret[4];
		hasTurret = new boolean[4];
		bullets = new ArrayList<>();
		
		maxHealth = 500;
		health = 500;
		gold = 150;
		exp = 0;
		turretSpace = 1;
		tech = 1;
		
		this.isEnemy = isEnemy;
		
		baseImg = new ImageIcon("src/Images/base"+tech+".png");
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
	public void addEUnits(String unit) {
		switch(unit) {
		case "Clubman":
			units.add(new Clubman(true));
			break;
		case "Slingshotman":
			units.add(new Slingshotman(true));
			break;
		case "DinoRider":
			units.add(new DinoRider(true));
			break;
		case "Swordman":
			units.add(new Swordman(true));
			break;
		case "Archer":
			units.add(new Archer(true));
			break;
		case "Knight":
			units.add(new Knight(true));
			break;
		}
	}
	public Turret[] getTurrets() {
		return turrets;
	}
	public void addTurrets(int index, String turret, boolean isEnemy) {
		switch(turret) {
		case "RockSlingshot":
			turrets[index] = new RockSlingshot(index, isEnemy);
			break;
		case "EggAutomatic":
			turrets[index] = new EggAutomatic(index, isEnemy);
			break;
		case "PrimitiveCatapult":
			turrets[index] = new PrimitiveCatapult(index, isEnemy);
			break;
		case "Catapult":
			turrets[index] = new Catapult(index, isEnemy);
			break;
		case "FireCatapult":
			turrets[index] = new FireCatapult(index, isEnemy);
			break;
		case "Oil":
			turrets[index] = new Oil(index, isEnemy);
			break;
		}
		
		updateGold(-turrets[index].getPrice());
		hasTurret[index] = true;
	}
	public void sellTurret(int index) { // 터렛 판매
		updateGold((int)(turrets[index].getPrice()*0.5)); // 판매시 0.5만큼 돌려줌
		turrets[index] = null; // 할당 해제하고
		hasTurret[index] = false; // 터렛 없음으로 변경
	}
	public boolean getHasTurret(int index) {
		return hasTurret[index];
	}
	public ArrayList<Bullet> getBullets() {
		return bullets;
	}
	public void addBullets(Bullet bullet) {
		bullets.add(bullet);
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
	public int getTurretSpace() {
		return turretSpace;
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
	public void techUp() {
		tech++;
		maxHealth += 300 * tech;
		health += 300 * tech;
		baseImg = new ImageIcon("src/Images/base"+tech+".png");
	}
	
	public void buildTurretSpace() {
		turretSpace++;
	}
}
