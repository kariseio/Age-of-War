package Turret;

import javax.swing.ImageIcon;

public class RocketTurret extends Turret {
	public RocketTurret(int index, boolean isEnemy) {
		shootSpeed = 50;
		bulletId = 9;
		bulletSpeed = 6;
		damage = 100;
		range = 500;
		price = 7000;
		
		this.isEnemy = isEnemy;
		
		if(isEnemy == false) {
			x = 25;
			y = 350 - 40 * index;
			img = new ImageIcon("src/Images/RocketTurret.png");
		} else {
			x = 920;
			y = 350 - 40 * index;
			img = new ImageIcon("src/Images/RocketTurret_enemy.png");
		}
		
	}
}
