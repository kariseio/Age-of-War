package Units;

import javax.swing.ImageIcon;

import Player.Player;

public class Cannoner extends Unit {
	public Cannoner(boolean isEnemy) {
		maxHealth = 600;
		health = 600;
		power = 120;
		range_power = 0;
		range_melee = 25;
		range_shoot = 0;
		speed = 1;
		attackTime = 100;
		
		image = new ImageIcon("src/Images/Cannoner.png");
		
		width = image.getIconWidth();
		height = image.getIconHeight();
		
		price = 1000;
		queueTime = 200;
		
		this.isEnemy = isEnemy;
		
		if(isEnemy == false) {
			x = 100;
			y = 450 - height;
			image = new ImageIcon("src/Images/Cannoner.png");
		} else {
			x = 870 - width;
			y = 450 - height;
			image = new ImageIcon("src/Images/Cannoner_enemy.png");
		}
	}
}