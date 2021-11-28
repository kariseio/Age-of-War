package Units;

import javax.swing.ImageIcon;

public class Slingshotman extends Unit {
	public Slingshotman(boolean isEnemy) {
		maxHealth = 80;
		health = 80;
		power = 10;
		range = 90;
		speed = 1;
		
		image = new ImageIcon("src/Images/Slingshotman.png");
		
		width = image.getIconWidth();
		height = image.getIconHeight();
		
		price = 15;
		
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