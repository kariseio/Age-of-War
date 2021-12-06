package Units;

import javax.swing.ImageIcon;

import Player.Player;

public class GodsBlade extends Unit {
	public GodsBlade(boolean isEnemy) {
		maxHealth = 1000;
		health = 1000;
		power = 250;
		range_power = 0;
		range_melee = 40;
		range_shoot = 0;
		speed = 1;
		attackTime = 80;
		
		image = new ImageIcon("src/Images/GodsBlade.png");
		
		width = image.getIconWidth();
		height = image.getIconHeight();
		
		price = 5000;
		queueTime = 100;
		
		this.isEnemy = isEnemy;
		
		if(isEnemy == false) {
			x = 100;
			y = 450 - height;
			image = new ImageIcon("src/Images/GodsBlade.png");
		} else {
			x = 870 - width;
			y = 450 - height;
			image = new ImageIcon("src/Images/GodsBlade_enemy.png");
		}
	}
}