package Units;

import javax.swing.ImageIcon;

public class Clubman extends Unit {
	public Clubman(boolean isEnemy) {
		maxHealth = 100;
		health = 100;
		power = 10;
		range = 10;
		speed = 1;
		
		width = 40;
		height = 60;
		
		price = 15;
		
		this.isEnemy = isEnemy;
		
		if(isEnemy == false) {
			x = 100;
			y = 450 - height;
			image = new ImageIcon("src/Images/Clubman.png");
		} else {
			x = 870 - width;
			y = 450 - height;
			image = new ImageIcon("src/Images/Clubman_enemy.png");
		}
	}
}