package Units;

import javax.swing.ImageIcon;

import Player.Player;

public class Clubman extends Unit {
	public Clubman(boolean isEnemy) {
		maxHealth = 55;
		health = 55;
		power = 16;
		range_power = 0;
		range_melee = 20;
		range_shoot = 0;
		speed = 1;
		attackTime = 60;
		
		image = new ImageIcon("src/Images/Clubman.png");
		
		width = image.getIconWidth();
		height = image.getIconHeight();
		
		price = 15;
		queueTime = 40;
		
		this.isEnemy = isEnemy;
		
		if(isEnemy == false) {
			x = 100;
			y = 450 - height;
			image = new ImageIcon("src/Images/Clubman.png");
		} else {
			x = 870 - width;
			y = 450 - height;
			image = new ImageIcon("src/Images/Clubman_enemy.png");
		}
	}
}