package Turret;

import javax.swing.ImageIcon;

public class ExplosivesCannon extends Turret {
	public ExplosivesCannon(int index, boolean isEnemy) {
		shootSpeed = 140;
		bulletId = 7;
		bulletSpeed = 6;
		damage = 100;
		range = 500;
		price = 6000;
		
		this.isEnemy = isEnemy;
		
		if(isEnemy == false) {
			x = 25;
			y = 350 - 40 * index;
			img = new ImageIcon("src/Images/ExplosivesCannon.png");
		} else {
			x = 920;
			y = 350 - 40 * index;
			img = new ImageIcon("src/Images/ExplosivesCannon_enemy.png");
		}
		
	}
}
