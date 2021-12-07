package Units;

import javax.swing.ImageIcon;

public class Infantry extends Unit {
	public Infantry(boolean isEnemy) {
		maxHealth = 300;
		health = 300;
		power = 60;
		range_power = 30;
		range_melee = 25;
		range_shoot = 130;
		speed = 1;
		attackTime = 60;
		
		image = new ImageIcon("src/Images/Infantry.png");
		
		width = image.getIconWidth();
		height = image.getIconHeight();
		
		price = 2000;
		queueTime = 100;
		
		this.isEnemy = isEnemy;
		
		if(isEnemy == false) {
			x = 100;
			y = 450 - height;
			image = new ImageIcon("src/Images/Infantry.png");
		} else {
			x = 870 - width;
			y = 450 - height;
			image = new ImageIcon("src/Images/Infantry_enemy.png");
		}
	}
}