package Turret;

import javax.swing.ImageIcon;

public class Catapult extends Turret {
	public Catapult(int index, boolean isEnemy) {
		shootSpeed = 140;
		bulletId = 3;
		bulletSpeed = 2;
		damage = 40;
		range = 400;
		price = 500;
		
		this.isEnemy = isEnemy;
		
		if(isEnemy == false) {
			x = 25;
			y = 350 - 40 * index;
			img = new ImageIcon("src/Images/Catapult.png");
		} else {
			x = 920;
			y = 350 - 40 * index;
			img = new ImageIcon("src/Images/Catapult_enemy.png");
		}
		
	}
}
