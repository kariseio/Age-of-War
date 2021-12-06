package Units;

import javax.swing.ImageIcon;

import Player.Player;

public class Tank extends Unit {
	public Tank(boolean isEnemy) {
		maxHealth = 1200;
		health = 1200;
		power = 300;
		range_power = 0;
		range_melee = 100;
		range_shoot = 0;
		speed = 1;
		attackTime = 90;
		
		image = new ImageIcon("src/Images/Tank.png");
		
		width = image.getIconWidth();
		height = image.getIconHeight();
		
		price = 7000;
		queueTime = 300;
		
		this.isEnemy = isEnemy;
		
		if(isEnemy == false) {
			x = 100;
			y = 450 - height;
			image = new ImageIcon("src/Images/Tank.png");
		} else {
			x = 870 - width;
			y = 450 - height;
			image = new ImageIcon("src/Images/Tank_enemy.png");
		}
	}
}