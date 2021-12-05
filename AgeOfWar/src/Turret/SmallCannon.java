package Turret;

import javax.swing.ImageIcon;

public class SmallCannon extends Turret {
	public SmallCannon(int index, boolean isEnemy) {
		shootSpeed = 140;
		bulletId = 6;
		bulletSpeed = 6;
		damage = 30;
		range = 500;
		price = 1500;
		
		this.isEnemy = isEnemy;
		
		if(isEnemy == false) {
			x = 25;
			y = 350 - 40 * index;
			img = new ImageIcon("src/Images/SmallCannon.png");
		} else {
			x = 920;
			y = 350 - 40 * index;
			img = new ImageIcon("src/Images/SmallCannon_enemy.png");
		}
		
	}
}
