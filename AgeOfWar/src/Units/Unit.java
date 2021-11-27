package Units;

import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import Player.Player;

public class Unit {
	protected int maxHealth;
	protected int health;
	protected int power;
	protected int range;
	protected double speed;
	
	protected int width;
	protected int height;
	protected int x;
	protected int y;
	
	protected int price;
	protected int attackCount = 0;
	
	protected boolean isEnemy;
	protected ImageIcon image;
	
	public Unit() {
		
	}
	
	public Image getImage() {
		return image.getImage();
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public int getMaxHealth() {
		return maxHealth;
	}
	public int getHealth() {
		return health;
	}
	public int getPrice() {
		return price;
	}
	
	public void action(Player player, Player enemy, int index) {
		ArrayList<Unit> player_Character = player.getUnits();
		ArrayList<Unit> enemy_Character = enemy.getUnits();
		if(isEnemy == false) { // 아군일 때
			if(enemy_Character.size() > 0) { // 적이 있을 때
				if(x + width + range > enemy_Character.get(0).getX()) { // 공격
					attackCount++;
					if(attackCount == 15) {
						enemy_Character.get(0).hit(power);
						attackCount = 0;
					}
				} else
					attackCount = 0;
				
				for(int i = 0; i < index; i++) {
					if(x + width > player_Character.get(i).getX()) { // 내 앞 유닛이 내 바로 앞에 있지 않으면
						return;
					}
				}
				if(x + width > enemy_Character.get(0).getX()) // 적이 내 바로 앞에 있을때
					return;
				x += speed;
				
			} else { // 적이 없을 때
				if(x + width + range > 870) { // 적 기지 공격
					attackCount++;
					if(attackCount == 15) {
						enemy.hit(power);
						attackCount = 0;
					}
				} else
					attackCount = 0;
				
				for(int i = 0; i < index; i++) { // 아군 멈춤
					if(x + width >= player_Character.get(i).getX()) { // 내 앞 유닛이 내 바로 앞에 있지 않으면
						return;
					}
				}
				if(x + width >= 870)
					return;
				x += speed;
			}
		} else { // 적군일때
			if(player_Character.size() > 0) { // 아군이 있을 때
				if(x - range < player_Character.get(0).getX() + player_Character.get(0).getWidth()) { // 아군 공격
					attackCount++;
					if(attackCount == 15) {
						player_Character.get(0).hit(power);
						attackCount = 0;
					}
				} else
					attackCount = 0;
				
				for(int i = 0; i < index; i++) { // 적군끼리 비교
					if(x < enemy_Character.get(i).getX() + enemy_Character.get(i).getWidth()) { // 내 앞 유닛이 내 바로 앞에 있지 않으면
						return;
					}
				}
				if(x < player_Character.get(0).getX() + player_Character.get(0).getWidth()) { // 아군이 적 바로 앞에 있을 때
					return;
				}
				x -= speed;
				
			} else { // 아군이 없을 때
				if(x - range < 100) { // 아군 기지 공격
					attackCount++;
					if(attackCount == 15) {
						player.hit(power);
						attackCount = 0;
					}
				} else
					attackCount = 0;
				
				for(int i = 0; i < index; i++) { // 적군 멈춤
					if(x <= enemy_Character.get(i).getX() + enemy_Character.get(i).getWidth()) { // 내 앞 유닛이 내 바로 앞에 있지 않으면
						return;
					}
				}
				if(x <= 100)
					return;
				x -= speed;
			}
		}
	}
	
	public void hit(int dmg) {
		health -= dmg;
	}
}
