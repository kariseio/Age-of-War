package Turret;

import javax.swing.ImageIcon;

public class RockSlingshot extends Turret {
	public RockSlingshot(int index, boolean isEnemy) {
		shootSpeed = 30;
		bulletId = 1;
		bulletSpeed = 1;
		damage = 12;
		range = 350;
		price = 100;
		
		this.isEnemy = isEnemy;
		x = isEnemy ? 905 : 25;
		y = 350 - 40 * index;
		
		img = new ImageIcon("src/Images/RockSlingshot.png");
	}
}
