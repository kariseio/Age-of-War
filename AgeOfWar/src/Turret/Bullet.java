package Turret;

import java.awt.Image;

public class Bullet {
	private int bulletId;
	private int x;
	private int y;
	private int dirX;
	private int dirY;
	private double degree;
	private int speed;
	
	public Bullet(int bulletId, int x, int y, int dirX, int dirY, int speed) {
		this.bulletId = bulletId;
		this.x = x;
		this.y = y;
		degree = Math.atan2(dirY - x, dirX - y) * (180.0/Math.PI);
		this.speed = speed;
	}
	
	public void action() {
		x = (int)(x + Math.cos(degree) * speed);
		y = (int)(y + Math.sin(degree) * speed);
	}
	
	public int getBulletId() {
		return bulletId;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
}
