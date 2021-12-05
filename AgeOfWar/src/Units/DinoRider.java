package Units;

import javax.swing.ImageIcon;

import Player.Player;

public class DinoRider extends Unit {
	public DinoRider(boolean isEnemy) {
		maxHealth = 160;
		health = 160;
		power = 40;
		range_power = 0;
		range_melee = 45;
		range_shoot = 0;
		speed = 1;
		
		image = new ImageIcon("src/Images/DinoRider.png");
		
		width = image.getIconWidth();
		height = image.getIconHeight();
		
		price = 100;
		queueTime = 100;
		
		this.isEnemy = isEnemy;
		
		if(isEnemy == false) {
			x = 100;
			y = 450 - height;
			image = new ImageIcon("src/Images/DinoRider.png");
		} else {
			x = 870 - width;
			y = 450 - height;
			image = new ImageIcon("src/Images/DinoRider_enemy.png");
		}
		
		
	}
}