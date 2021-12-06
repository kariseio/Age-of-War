package Turret;

import javax.swing.ImageIcon;

public class LargeCannon extends Turret {
	public LargeCannon(int index, boolean isEnemy) {
		shootSpeed = 70;
		bulletId = 6;
		bulletSpeed = 6;
		damage = 70;
		range = 500;
		price = 3000;
		
		this.isEnemy = isEnemy;
		
		if(isEnemy == false) {
			x = 25;
			y = 350 - 40 * index;
			img = new ImageIcon("src/Images/LargeCannon.png");
		} else {
			x = 920;
			y = 350 - 40 * index;
			img = new ImageIcon("src/Images/LargeCannon_enemy.png");
		}
		
	}
}
