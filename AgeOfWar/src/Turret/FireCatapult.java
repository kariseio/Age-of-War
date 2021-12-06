package Turret;

import javax.swing.ImageIcon;

public class FireCatapult extends Turret {
	public FireCatapult(int index, boolean isEnemy) {
		shootSpeed = 70;
		bulletId = 4;
		bulletSpeed = 2;
		damage = 40;
		range = 400;
		price = 500;
		
		this.isEnemy = isEnemy;
		
		if(isEnemy == false) {
			x = 25;
			y = 350 - 40 * index;
			img = new ImageIcon("src/Images/FireCatapult.png");
		} else {
			x = 920;
			y = 350 - 40 * index;
			img = new ImageIcon("src/Images/FireCatapult_enemy.png");
		}
	}
}
