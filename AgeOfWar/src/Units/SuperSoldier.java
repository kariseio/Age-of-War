package Units;

import javax.swing.ImageIcon;

import Player.Player;

public class SuperSoldier extends Unit {
	public SuperSoldier(boolean isEnemy) {
		maxHealth = 4000;
		health = 4000;
		power = 400;
		range_power = 400;
		range_melee = 40;
		range_shoot = 150;
		speed = 1;
		attackTime = 60;
		
		image = new ImageIcon("src/Images/SuperSoldier.png");
		
		width = image.getIconWidth();
		height = image.getIconHeight();
		
		price = 150000;
		queueTime = 100;
		
		this.isEnemy = isEnemy;
		
		if(isEnemy == false) {
			x = 100;
			y = 450 - height;
			image = new ImageIcon("src/Images/SuperSoldier.png");
		} else {
			x = 870 - width;
			y = 450 - height;
			image = new ImageIcon("src/Images/SuperSoldier_enemy.png");
		}
	}
}