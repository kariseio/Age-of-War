package Player;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.ImageIcon;

import Turret.Bullet;
import Turret.Catapult;
import Turret.EggAutomatic;
import Turret.ExplosivesCannon;
import Turret.FireCatapult;
import Turret.LargeCannon;
import Turret.Oil;
import Turret.PrimitiveCatapult;
import Turret.RockSlingshot;
import Turret.SmallCannon;
import Turret.Turret;
import Units.Archer;
import Units.Cannoner;
import Units.Clubman;
import Units.DinoRider;
import Units.Dueler;
import Units.Knight;
import Units.Mousquettere;
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
	private Queue<Unit> queue = new LinkedList<>(); // 유닛 뽑을 때 대기열
	
	public Player(boolean isEnemy) {
		units = new ArrayList<>();
		turrets = new Turret[4];
		hasTurret = new boolean[4];
		bullets = new ArrayList<>();
		
		maxHealth = 50000;
		health = 50000;
		gold = 1500000;
		exp = 100000;
		turretSpace = 4;
		tech = 3;
		
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
	public void addToQueue(String unit) {
		switch(unit) {
		case "Clubman":
			queue.add(new Clubman(false));
			break;
		case "Slingshotman":
			queue.add(new Slingshotman(false));
			break;
		case "DinoRider":
			queue.add(new DinoRider(false));
			break;
		case "Swordman":
			queue.add(new Swordman(false));
			break;
		case "Archer":
			queue.add(new Archer(false));
			break;
		case "Knight":
			queue.add(new Knight(false));
			break;
		case "Dueler":
			queue.add(new Dueler(false));
			break;
		case "Mousquettere":
			queue.add(new Mousquettere(false));
			break;
		case "Cannoner":
			queue.add(new Cannoner(false));
			break;
		}
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
		case "Dueler":
			units.add(new Dueler(true));
			break;
		case "Mousquettere":
			units.add(new Mousquettere(true));
			break;
		case "Cannoner":
			units.add(new Cannoner(true));
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
		case "SmallCannon":
			turrets[index] = new SmallCannon(index, isEnemy);
			break;
		case "LargeCannon":
			turrets[index] = new LargeCannon(index, isEnemy);
			break;
		case "ExplosivesCannon":
			turrets[index] = new ExplosivesCannon(index, isEnemy);
			break;
		}
		
		updateGold(-turrets[index].getPrice());
		hasTurret[index] = true;
	}
	public void sellTurret(int index) { // 터렛 판매
		updateGold((int)Math.round(turrets[index].getPrice()*0.5)); // 판매시 0.5만큼 돌려줌
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
	public Queue<Unit> getQueue() {
		return queue;
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
