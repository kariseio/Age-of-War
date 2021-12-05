package Units;

import javax.swing.ImageIcon;

import Player.Player;

public class Dueler extends Unit {
	public Dueler(boolean isEnemy) {
		maxHealth = 200;
		health = 200;
		power = 79;
		range_power = 0;
		range_melee = 25;
		range_shoot = 0;
		speed = 1;
		attackTime = 50;
		
		image = new ImageIcon("src/Images/Dueler.png");
		
		width = image.getIconWidth();
		height = image.getIconHeight();
		
		price = 200;
		queueTime = 100;
		
		this.isEnemy = isEnemy;
		
		if(isEnemy == false) {
			x = 100;
			y = 450 - height;
			image = new ImageIcon("src/Images/Dueler.png");
		} else {
			x = 870 - width;
			y = 450 - height;
			image = new ImageIcon("src/Images/Dueler_enemy.png");
		}
	}
}