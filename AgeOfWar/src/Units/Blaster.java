package Units;

import javax.swing.ImageIcon;

public class Blaster extends Unit {
	public Blaster(boolean isEnemy) {
		maxHealth = 800;
		health = 800;
		power = 130;
		range_power = 80;
		range_melee = 40;
		range_shoot = 130;
		speed = 1;
		attackTime = 60;
		
		image = new ImageIcon("src/Images/Blaster.png");
		
		width = image.getIconWidth();
		height = image.getIconHeight();
		
		price = 6000;
		queueTime = 100;
		
		this.isEnemy = isEnemy;
		
		if(isEnemy == false) {
			x = 100;
			y = 450 - height;
			image = new ImageIcon("src/Images/Blaster.png");
		} else {
			x = 870 - width;
			y = 450 - height;
			image = new ImageIcon("src/Images/Blaster_enemy.png");
		}
	}
}