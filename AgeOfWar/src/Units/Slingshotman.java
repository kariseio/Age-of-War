package Units;

import javax.swing.ImageIcon;

public class Slingshotman extends Unit {
	public Slingshotman(boolean isEnemy) {
		maxHealth = 42;
		health = 42;
		power = 8;
		range = 100;
		speed = 1;
		
		image = new ImageIcon("src/Images/Slingshotman.png");
		
		width = image.getIconWidth();
		height = image.getIconHeight();
		
		price = 25;
		
		this.isEnemy = isEnemy;
		
		if(isEnemy == false) {
			x = 100;
			y = 450 - height;
			image = new ImageIcon("src/Images/Slingshotman.png");
		} else {
			x = 870 - width;
			y = 450 - height;
			image = new ImageIcon("src/Images/Slingshotman_enemy.png");
		}
		
		
	}
}