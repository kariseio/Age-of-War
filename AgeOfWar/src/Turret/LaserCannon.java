package Turret;

import javax.swing.ImageIcon;

public class LaserCannon extends Turret {
	public LaserCannon(int index, boolean isEnemy) {
		shootSpeed = 10;
		bulletId = 10;
		bulletSpeed = 6;
		damage = 40;
		range = 500;
		price = 40000;
		
		this.isEnemy = isEnemy;
		
		if(isEnemy == false) {
			x = 25;
			y = 350 - 40 * index;
			img = new ImageIcon("src/Images/LaserCannon.png");
		} else {
			x = 920;
			y = 350 - 40 * index;
			img = new ImageIcon("src/Images/LaserCannon_enemy.png");
		}
	}
}
