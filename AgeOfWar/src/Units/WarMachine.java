package Units;

import javax.swing.ImageIcon;

public class WarMachine extends Unit {
	public WarMachine(boolean isEnemy) {
		maxHealth = 3000;
		health = 3000;
		power = 600;
		range_power = 0;
		range_melee = 100;
		range_shoot = 0;
		speed = 1;
		attackTime = 90;
		
		image = new ImageIcon("src/Images/WarMachine.png");
		
		width = image.getIconWidth();
		height = image.getIconHeight();
		
		price = 20000;
		queueTime = 300;
		
		this.isEnemy = isEnemy;
		
		if(isEnemy == false) {
			x = 100;
			y = 450 - height;
			image = new ImageIcon("src/Images/WarMachine.png");
		} else {
			x = 870 - width;
			y = 450 - height;
			image = new ImageIcon("src/Images/WarMachine_enemy.png");
		}
	}
}