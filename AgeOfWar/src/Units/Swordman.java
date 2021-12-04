package Units;

import javax.swing.ImageIcon;

import Player.Player;

public class Swordman extends Unit {
	public Swordman(boolean isEnemy) {
		maxHealth = 100;
		health = 100;
		power = 35;
		range = 10;
		speed = 1;
		
		image = new ImageIcon("src/Images/Swordman.png");
		
		width = image.getIconWidth();
		height = image.getIconHeight();
		
		price = 50;
		
		this.isEnemy = isEnemy;
		
		if(isEnemy == false) {
			x = 100;
			y = 450 - height;
			image = new ImageIcon("src/Images/Swordman.png");
		} else {
			x = 870 - width;
			y = 450 - height;
			image = new ImageIcon("src/Images/Swordman_enemy.png");
		}
	}
}