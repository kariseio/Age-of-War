package Units;

import javax.swing.ImageIcon;

import Player.Player;

public class Archer extends Unit {
	public Archer(boolean isEnemy) {
		maxHealth = 80;
		health = 80;
		power = 20;
		range_power = 9;
		range_melee = 20;
		range_shoot = 130;
		speed = 1;
		attackTime = 60;
		
		image = new ImageIcon("src/Images/Archer.png");
		
		width = image.getIconWidth();
		height = image.getIconHeight();
		
		price = 75;
		queueTime = 50;
		
		this.isEnemy = isEnemy;
		
		if(isEnemy == false) {
			x = 100;
			y = 450 - height;
			image = new ImageIcon("src/Images/Archer.png");
		} else {
			x = 870 - width;
			y = 450 - height;
			image = new ImageIcon("src/Images/Archer_enemy.png");
		}
	}
}