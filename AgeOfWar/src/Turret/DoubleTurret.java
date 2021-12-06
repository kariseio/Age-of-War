package Turret;

import javax.swing.ImageIcon;

public class DoubleTurret extends Turret {
	public DoubleTurret(int index, boolean isEnemy) {
		shootSpeed = 44;
		bulletId = 8;
		bulletSpeed = 6;
		damage = 60;
		range = 500;
		price = 14000;
		
		this.isEnemy = isEnemy;
		
		if(isEnemy == false) {
			x = 25;
			y = 350 - 40 * index;
			img = new ImageIcon("src/Images/DoubleTurret.png");
		} else {
			x = 920;
			y = 350 - 40 * index;
			img = new ImageIcon("src/Images/DoubleTurret_enemy.png");
		}
		
	}
}
