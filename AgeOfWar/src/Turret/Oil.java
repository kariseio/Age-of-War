package Turret;

import javax.swing.ImageIcon;

import Player.Player;

public class Oil extends Turret {
	private final int shot = 60;
	
	public Oil(int index, boolean isEnemy) {
		shootSpeed = 100;
		bulletId = 5;
		bulletSpeed = 1;
		damage = 4;
		range = 300;
		price = 1000;
		timeCount = 0;
		
		img = new ImageIcon("src/Images/Oil.png");
		
		this.isEnemy = isEnemy;
		
		if(isEnemy == false) {
			x = 25;
			y = 350 - 40 * index;
			img = new ImageIcon("src/Images/Oil.png");
		} else {
			x = 960 - img.getIconWidth();
			y = 350 - 40 * index;
			img = new ImageIcon("src/Images/Oil_enemy.png");
		}
	}
	
	public void shoot(Player player, Player enemy) {
		if(isEnemy == false) { // 내꺼
			if(enemy.getUnits().size() == 0) return;
			
			if(dist(x + img.getIconWidth(), enemy.getUnits().get(0).getX()) <= range) { // 적군이 사거리 안으로 들어왔을때
				if(timeCount >= shootSpeed) {
					if(timeCount < shootSpeed + shot) {
						player.addBullets(new Bullet(bulletId, x + img.getIconWidth() , y+30, x + img.getIconWidth(), enemy.getUnits().get(0).getY(), damage, bulletSpeed));
						timeCount++;
					} else {
						timeCount = 0;
					}
				} else {
					timeCount++;
				}
			} else {
				timeCount = 0;
			}
		} else { // 적군 터렛
			if(player.getUnits().size() == 0) return;
			if(dist(x, player.getUnits().get(0).getX() + player.getUnits().get(0).getWidth()) <= range) { // 적군이 사거리 안으로 들어왔을때
				if(timeCount >= shootSpeed) {
					if(timeCount < shootSpeed + shot) {
						enemy.addBullets(new Bullet(bulletId, x, y+30, x, player.getUnits().get(0).getY(), damage, bulletSpeed));
						timeCount++;
					} else {
						timeCount = 0;
					}
				} else {
					timeCount++;
				}
			} else {
				timeCount = 0;
			}
		}
	}
}
