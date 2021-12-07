package Units;

import javax.swing.ImageIcon;

public class Knight extends Unit {
	public Knight(boolean isEnemy) {
		maxHealth = 300;
		health = 300;
		power = 60;
		range_power = 0;
		range_melee = 60;
		range_shoot = 0;
		speed = 1;
		attackTime = 90;
		
		image = new ImageIcon("src/Images/Knight.png");
		
		width = image.getIconWidth();
		height = image.getIconHeight();
		
		price = 500;
		queueTime = 100;
		
		this.isEnemy = isEnemy;
		
		if(isEnemy == false) {
			x = 100;
			y = 450 - height;
			image = new ImageIcon("src/Images/Knight.png");
		} else {
			x = 870 - width;
			y = 450 - height;
			image = new ImageIcon("src/Images/Knight_enemy.png");
		}
	}
}