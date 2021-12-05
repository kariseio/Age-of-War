package Turret;

import javax.swing.ImageIcon;

public class EggAutomatic extends Turret {
	public EggAutomatic(int index, boolean isEnemy) {
		shootSpeed = 22;
		bulletId = 2;
		bulletSpeed = 10;
		damage = 5;
		range = 300;
		price = 200;
		
		this.isEnemy = isEnemy;
		
		if(isEnemy == false) {
			x = 25;
			y = 350 - 40 * index;
			img = new ImageIcon("src/Images/EggAutomatic.png");
		} else {
			x = 920;
			y = 350 - 40 * index;
			img = new ImageIcon("src/Images/EggAutomatic_enemy.png");
		}
	}
}
