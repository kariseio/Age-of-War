package Turret;

import javax.swing.ImageIcon;

public class TitaniumShooter extends Turret {
	public TitaniumShooter(int index, boolean isEnemy) {
		shootSpeed = 80;
		bulletId = 12;
		bulletSpeed = 6;
		damage = 100;
		range = 400;
		price = 24000;
		
		this.isEnemy = isEnemy;
		
		if(isEnemy == false) {
			x = 25;
			y = 350 - 40 * index;
			img = new ImageIcon("src/Images/TitaniumShooter.png");
		} else {
			x = 920;
			y = 350 - 40 * index;
			img = new ImageIcon("src/Images/TitaniumShooter_enemy.png");
		}
	}
}
