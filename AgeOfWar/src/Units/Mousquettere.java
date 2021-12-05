package Units;

import javax.swing.ImageIcon;

import Player.Player;

public class Mousquettere extends Unit {
	public Mousquettere(boolean isEnemy) {
		maxHealth = 160;
		health = 160;
		power = 40;
		range_power = 20;
		range_melee = 25;
		range_shoot = 130;
		speed = 1;
		attackTime = 60;
		
		image = new ImageIcon("src/Images/Mousquettere.png");
		
		width = image.getIconWidth();
		height = image.getIconHeight();
		
		price = 400;
		queueTime = 100;
		
		this.isEnemy = isEnemy;
		
		if(isEnemy == false) {
			x = 100;
			y = 450 - height;
			image = new ImageIcon("src/Images/Mousquettere.png");
		} else {
			x = 870 - width;
			y = 450 - height;
			image = new ImageIcon("src/Images/Mousquettere_enemy.png");
		}
	}
}