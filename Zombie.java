import javax.swing.*;
import java.awt.*;

public class Zombie {
	
	public int zombieX, zombieY;
	public int spawnSide = 0;
	public ImageIcon zombieModel, zombieModelDead;
	public int zombieHealth;
	public int random;
	public String fileName = "";
	
	public Zombie() {
		getModel();
		spawnZombie();
		//System.out.println("hello");
	}
	
	public void getModel() {
		random = (int)Math.random()*3+1;
		fileName = "zombie" + random + ".png";
		zombieModel = new ImageIcon(fileName);
		fileName = "zombieDead" + random + ".png";
		zombieModelDead = new ImageIcon(fileName);
	}
	
	public void collision() {
		if(zombieHealth > 0) { 
			if ((zombieX >= Game.playerX && zombieX <= Game.playerX+25) && (zombieY >= Game.playerY && zombieY <= Game.playerY+25)) {
				Game.playerHealth--;
				Game.counterHealth.setText("Health: " + Game.playerHealth/Game.healthDivider + "%");
			}
		}
	}
		
	public void damageCheck(int gunDamage) {
	
		//player shot to his left
		if (Game.direction.equals("left")) {
			if (zombieX <= Game.playerX && zombieY >= Game.playerY && zombieY <= Game.playerY+25) {
				zombieHealth -= gunDamage;
			}
		}
		
		//player shot to their right
		else if (Game.direction.equals("right")) {
			if (zombieX >= Game.playerX && zombieY >= Game.playerY && zombieY <= Game.playerY+25) {
				zombieHealth -= gunDamage;
			}
			
		}
		
		//player shot up
		else if (Game.direction.equals("up")) {
			if (zombieY >= Game.playerY && zombieX >= Game.playerX && zombieX <= Game.playerX+25) {
				zombieHealth -= gunDamage;
			}
		}
		
		//player shot down
		else if (Game.direction.equals("down")) {
			if (zombieY >= Game.playerY && zombieX >= Game.playerX && zombieX <= Game.playerX+25) {
				zombieHealth -= gunDamage;
			}
		}
		
			
	}//end damageCheck
	
	public void move() {
		if(zombieHealth > 0) { 
			if (zombieX > Game.playerX) {
				zombieX--;
						
			}
			else if (zombieX < Game.playerX) {
				zombieX++;
			}
					
			//move zombie to player's Y
			if (zombieY > Game.playerY) {
				zombieY--;
			}
			else if (zombieY < Game.playerY) {
				zombieY++;
			}
		}
	}//end move
	
	public boolean crossbowCollision(int boltX, int boltY) {
		if (boltX >= zombieX && boltX+5 <= zombieX+25 && boltY < zombieY+25 && boltY+5 > zombieY && zombieHealth > 0) {
            zombieHealth -= 80;
			return false;
        }
		return true;
	}
	
	public void spawnZombie() {
		if (Game.playfield.contains("Dungeon")) {
			zombieX = (int)(Math.random()*950)+50;
			zombieY = 580;
			zombieHealth = 100;
		}
		
		if (Game.playfield.contains("Highway")) {
			zombieY = (int)(Math.random()*450)+100;
			spawnSide = (int)(Math.random()*2);
			if(spawnSide == 0) { 
				zombieX = 10;
			}
			if(spawnSide == 1) {
				zombieX = 1050;
			}
			zombieHealth = 150;
		
		}
	}
	
	public void draw(Graphics g) {
		if(zombieHealth > 0)
			g.drawImage(zombieModel.getImage(), zombieX, zombieY, 25, 25, null);
		else {  
			g.drawImage(zombieModelDead.getImage(), zombieX, zombieY, 25, 25, null);
		}
	}
	
}
