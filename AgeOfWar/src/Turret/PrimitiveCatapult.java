package Turret;

import javax.swing.ImageIcon;

public class PrimitiveCatapult extends Turret {
	public PrimitiveCatapult(int index, boolean isEnemy) {
		shootSpeed = 70;
		bulletId = 3;
		bulletSpeed = 2;
		damage = 25;
		range = 400;
		price = 500;
		
		this.isEnemy = isEnemy;
		x = isEnemy ? 905 : 25;
		y = 350 - 40 * index;
		
		img = new ImageIcon("src/Images/PrimitiveCatapult.png");
	}
}
