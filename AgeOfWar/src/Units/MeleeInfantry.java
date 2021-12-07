package Units;

import javax.swing.ImageIcon;

public class MeleeInfantry extends Unit {
	public MeleeInfantry(boolean isEnemy) {
		maxHealth = 350;
		health = 350;
		power = 100;
		range_power = 0;
		range_melee = 25;
		range_shoot = 0;
		speed = 1;
		attackTime = 60;
		
		image = new ImageIcon("src/Images/MeleeInfantry.png");
		
		width = image.getIconWidth();
		height = image.getIconHeight();
		
		price = 1500;
		queueTime = 100;
		
		this.isEnemy = isEnemy;
		
		if(isEnemy == false) {
			x = 100;
			y = 450 - height;
			image = new ImageIcon("src/Images/MeleeInfantry.png");
		} else {
			x = 870 - width;
			y = 450 - height;
			image = new ImageIcon("src/Images/MeleeInfantry_enemy.png");
		}
	}
}