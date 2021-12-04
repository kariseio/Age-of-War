package Turret;

import java.awt.Image;

public class Bullet {
	private int bulletId;
	private double x; // 원활한 삼각함수 계산을 위해서 double로 get할때는 int로 전달
	private double y;
	private int dirX;
	private int dirY;
	private double degree;
	private int damage;
	private double speed;
	
	public Bullet(int bulletId, int x, int y, int dirX, int dirY, int damage, double speed) {
		this.bulletId = bulletId;
		this.x = x;
		this.y = y;
		degree = Math.atan2(dirY - y, dirX - x);
		this.damage = damage;
		this.speed = speed;
	}
	
	public void action() {
//		degree = degree * 0.995;
		x = x + Math.cos(degree) * speed;
		y = y + Math.sin(degree) * speed;
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
