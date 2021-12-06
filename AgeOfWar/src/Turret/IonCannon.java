package Turret;

import javax.swing.ImageIcon;

public class IonCannon extends Turret {
	public IonCannon(int index, boolean isEnemy) {
		shootSpeed = 20;
		bulletId = 11;
		bulletSpeed = 6;
		damage = 60;
		range = 500;
		price = 100000;
		
		this.isEnemy = isEnemy;
		
		if(isEnemy == false) {
			x = 25;
			y = 350 - 40 * index;
			img = new ImageIcon("src/Images/IonCannon.png");
		} else {
			x = 920;
			y = 350 - 40 * index;
			img = new ImageIcon("src/Images/IonCannon_enemy.png");
		}
	}
}
