package Turret;

import javax.swing.ImageIcon;

public class SingleTurret extends Turret {
	public SingleTurret(int index, boolean isEnemy) {
		shootSpeed = 80;
		bulletId = 8;
		bulletSpeed = 6;
		damage = 70;
		range = 500;
		price = 7000;
		
		this.isEnemy = isEnemy;
		
		if(isEnemy == false) {
			x = 25;
			y = 350 - 40 * index;
			img = new ImageIcon("src/Images/SingleTurret.png");
		} else {
			x = 920;
			y = 350 - 40 * index;
			img = new ImageIcon("src/Images/SingleTurret_enemy.png");
		}
		
	}
}
