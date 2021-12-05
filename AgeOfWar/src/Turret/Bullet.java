package Turret;

import Player.Player;
import Units.Unit;

public class Bullet {
	private int bulletId;
	private double x; // 원활한 삼각함수 계산을 위해서 double로 get할때는 int로 전달
	private double y;
	private int dirX;
	private int dirY;
	private double degree;
	private int damage;
	private double speed;
	private int bulletType;
	private int timer = 0;
	
	public Bullet(int bulletId, double x, double y, int dirX, int dirY, int damage, double speed) { // 생성자
		this.bulletId = bulletId;
		this.x = x;
		this.y = y;
		degree = Math.atan2(dirY - y, dirX - x);
		this.damage = damage;
		this.speed = speed;
		bulletType = 0;
	}
	
	public Bullet(int bulletId, double x, double y, int damage, double speed, double degree) { // 각도를 받은 생성자
		this.bulletId = bulletId;
		this.x = x;
		this.y = y;
		this.damage = damage;
		this.speed = speed;
		this.degree = degree;
		bulletType = 1;
	}
	
	public void action() {
		if(bulletType == 0) {
			x = x + Math.cos(degree) * speed;
			y = y + Math.sin(degree) * speed;
		} else if(bulletType == 1) {
			x = x + Math.cos(degree) * speed;
			y = y + Math.sin(degree) * speed + 0.02*timer++;
		}
	}
	
	public void particle(Player player, Unit unit) { // 갈라진 탄환 생성
		int unitX = (unit.getX() + unit.getWidth() / 2);
		int unitY = (unit.getY() + unit.getHeight() / 2);
		
		// 분할하자마자 다시 맞는걸 방지하기 위해
		if(x > unitX) {
			x = unit.getX() + unit.getWidth() + 5;
		} else {
			x = unit.getX() - 5;
		}
		
		if(y < unitY) {
			y = unit.getY() - 5;
		}
		
		degree = Math.atan2(y - unitY, x - unitX);
		
		System.out.println(degree);
		for(int i = 0; i < 3; i++) {
			player.addBullets(new Bullet(100 + bulletId, x, y, (int) (damage * 0.7), 1.5, degree + (Math.random() * 0.6 -0.3))); // 각도 0.7배 ~ 1.3배
		}
	}
	
	public int getBulletId() {
		return bulletId;
	}
	public int getX() {
		return (int)x;
	}
	public int getY() {
		return (int)y;
	}
	public int getDamage() {
		return damage;
	}
}
