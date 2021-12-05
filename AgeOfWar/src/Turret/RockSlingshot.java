package Turret;

import javax.swing.ImageIcon;

public class RockSlingshot extends Turret {
	public RockSlingshot(int index, boolean isEnemy) {
		shootSpeed = 60;
		bulletId = 1;
		bulletSpeed = 4;
		damage = 12;
		range = 350;
		price = 100;
		
		this.isEnemy = isEnemy;
		
		if(isEnemy == false) {
			x = 25;
			y = 350 - 40 * index;
			img = new ImageIcon("src/Images/RockSlingshot.png");
		} else {
			x = 920;
			y = 350 - 40 * index;
			img = new ImageIcon("src/Images/RockSlingshot_enemy.png");
		}
		
	}
}
