package Turret;

import javax.swing.ImageIcon;

import Player.Player;

public class Turret {
	protected int x;
	protected int y;
	protected int damage;
	protected int bulletId;
	protected int bulletSpeed;
	protected int shootSpeed;
	protected int range;
	protected int price;
	
	
	protected boolean isEnemy;
	
	protected int timeCount;
	protected ImageIcon img;
	
	public Turret() {
		timeCount = 0;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getPrice() {
		return price;
	}
	public ImageIcon getImageIcon() {
		return img;
	}
	
	public void shoot(Player player, Player enemy) {
		if(isEnemy == false) { // 내꺼
			if(enemy.getUnits().size() == 0) return;
			
//			System.out.println(dist(x, y, enemy.getUnits().get(0).getX(), enemy.getUnits().get(0).getY()));
			if(dist(x, y, enemy.getUnits().get(0).getX(), enemy.getUnits().get(0).getY()) <= range) { // 적군이 사거리 안으로 들어왔을때
				if(timeCount >= shootSpeed) {
					player.addBullets(new Bullet(bulletId, x, y, enemy.getUnits().get(0).getX(), enemy.getUnits().get(0).getY() + (int)(enemy.getUnits().get(0).getHeight()*0.3), damage, bulletSpeed));
					timeCount = 0;
				} else {
					timeCount++;
				}
			} else {
				timeCount = 0;
			}
		}
	}
	
	public double dist(int x, int y, int x2, int y2) {
//		System.out.println(x + " " + y + " " + " " + x2 + " " + y2);
		return Math.sqrt((x2 - x) * (x2 - x) + (y2 - y) * (y2 - y));
	}
}
