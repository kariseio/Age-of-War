package Turret;

import javax.swing.ImageIcon;

public class EggAutomatic extends Turret {
	public EggAutomatic(int index, boolean isEnemy) {
		shootSpeed = 11;
		bulletId = 2;
		bulletSpeed = 2;
		damage = 5;
		range = 300;
		price = 200;
		
		this.isEnemy = isEnemy;
		x = isEnemy ? 905 : 25;
		y = 350 - 40 * index;
		
		img = new ImageIcon("src/Images/EggAutomatic.png");
	}
}
