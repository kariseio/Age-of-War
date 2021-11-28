package Units;

import javax.swing.ImageIcon;

import Player.Player;

public class DinoRider extends Unit {
	public DinoRider(boolean isEnemy) {
		maxHealth = 100;
		health = 100;
		power = 10;
		range = 10;
		speed = 1;
		
		width = 100;
		height = 60;
		
		price = 100;
		
		this.isEnemy = isEnemy;
		
		if(isEnemy == false) {
			x = 100;
			y = 450 - height;
			image = new ImageIcon("src/Images/DinoRider.png");
		} else {
			x = 900 - width;
			y = 450 - height;
			image = new ImageIcon("src/Images/DinoRider.png");
		}
	}
}