
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game extends JPanel implements KeyListener, ActionListener{

	public static int playerX, playerY, playerXvelocity = 0, playerYvelocity = 0, playerHealth = 0;
	public static int crossbowAmmo = 25, battleRifleAmmo = 75, healthDivider = 50;
	
	public static JLabel image, counterHealth, counterTime, counterScore, currentGun, counterCrossbowAmmo, counterRifleAmmo;
	public static String direction = "right", weaponHeld = "pistol", weaponDirection = "RIGHT.png";
	public static String playfield;
	
	public int survivalTime, survivalTimeBest, survivalTimeRecent, score, scoreBest, scoreRecent;
	public int boltX, boltY, boltXvelocity, boltYvelocity;
	public int tempInt, tempInt2 = 0, zombieCount = 1;
	public int weaponDamage;
	
	public ImageIcon background = new ImageIcon("Dungeon.jpg"), playerModel = new ImageIcon("playerRIGHT.png"), gunModel = new ImageIcon("pistolRIGHT.png"), muzzleFlash = new ImageIcon("muzzleFlashGun.png"), bolt = new ImageIcon("bolt.png"), zombieModel = new ImageIcon("zombie.png");
	
	public JFrame playing = new JFrame("The Lone Survivor: In Game");
	
	public boolean inGame = false, zombieDead = false, shooting = false, boltShot = false;
	
	public String equippedGun = "Pistol", weaponsUnlocked = "0";
	
	public final Color LIGHT_RED = new Color(255, 102, 102);
	
	public Timer t, spawnTimer;
	
	public ArrayList<Zombie> zombies = new ArrayList<Zombie>();
	

    public Game(JLabel location) {
		background = new ImageIcon("Dungeon.jpg");
		playfield = location.getText();
		
		playing = new JFrame("The Lone Survivor: In Game");
		playing.setSize(1100, 700);
		playing.setLocationRelativeTo(null);
		inGame = true;
		setLayout(null);
		
		playerX = 100;
		playerY = 100;
		survivalTime = 0;
		
		zombieDead = false;
		
		t = new Timer(30, this);
		spawnTimer = new Timer(5000, this);
		
		
		//clear everything that was here earlier
		this.removeAll();
		revalidate();
		playing.repaint();
		
		//get the right background and playerHealth for the game
		if (playfield.contains("Dungeon")) {
			background = new ImageIcon("DungeonMap.png");
			playerHealth = 2500;
			healthDivider = 25;
		}
		if (playfield.contains("Highway")) {
			background = new ImageIcon("Highway.png");
			playerHealth = 1200;
			healthDivider = 12;
		}
		if (playfield.contains("Castlegrounds")) {
			background = new ImageIcon("Castlegrounds.jpg");
			playerHealth = 600;
			healthDivider = 6;
		}
		
		//declare health, ammo types, score, and survival time counters
		counterHealth = new JLabel("Health: " + playerHealth/healthDivider + "%");
		counterScore= new JLabel("Score: " + score);
		counterTime = new JLabel("Time: " + survivalTime + "s");
		currentGun = new JLabel("Current Weapon: " + equippedGun);
		
		JLabel counterPistolAmmo = new JLabel("Pistol Ammo: Infinite");
		counterCrossbowAmmo = new JLabel("LOCKED");
		counterRifleAmmo = new JLabel("LOCKED");
		
		//add the counters above
		add(counterHealth);
		add(counterScore);
		add(counterTime);
		add(currentGun);
		
		add(counterPistolAmmo);
		add(counterCrossbowAmmo);
		add(counterRifleAmmo);
		
		//make the counter text bigger and center aligned
		counterHealth.setFont (counterHealth.getFont().deriveFont (25.0f));
		counterHealth.setHorizontalAlignment(JLabel.CENTER);
		
		counterScore.setFont (counterScore.getFont().deriveFont (25.0f));
		counterScore.setHorizontalAlignment(JLabel.CENTER);
		
		counterTime.setFont (counterTime.getFont().deriveFont (25.0f));
		counterTime.setHorizontalAlignment(JLabel.CENTER);
		
		currentGun.setFont (currentGun.getFont().deriveFont (20.0f));
		currentGun.setHorizontalAlignment(JLabel.CENTER);
		
		counterPistolAmmo.setFont (counterPistolAmmo.getFont().deriveFont (15.0f));
		counterPistolAmmo.setHorizontalAlignment(JLabel.CENTER);
		
		counterCrossbowAmmo.setFont (counterCrossbowAmmo.getFont().deriveFont (15.0f));
		counterCrossbowAmmo.setHorizontalAlignment(JLabel.CENTER);
		
		counterRifleAmmo.setFont (counterRifleAmmo.getFont().deriveFont (15.0f));
		counterRifleAmmo.setHorizontalAlignment(JLabel.CENTER);
		
		//position the counters at the bottom of frame
		counterHealth.setBounds(0, 600, 200, 50);
		counterScore.setBounds(200, 600, 200, 50);
		counterTime.setBounds(400, 600, 200, 50);
		currentGun.setBounds(525, 600, 400, 50);
		
		counterPistolAmmo.setBounds(880, 585, 200, 50);
		counterCrossbowAmmo.setBounds(880, 605, 200, 50);
		counterRifleAmmo.setBounds(880, 625, 200, 50);
		
		//background colors
		setBackground(Color.WHITE);
		playing.setBackground(Color.WHITE);
		
		//add key listener
		//clock.start();
		t.start();
		spawnTimer.start();
		addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
		
		//finalizing and displaying  
		playing.add(this);
		playing.setVisible(true);
		playing.setResizable(false);
		playing.repaint();

	}//end playPanel

	public void actionPerformed(ActionEvent choice) {
		if (choice.getSource() == spawnTimer) {
			if(inGame) { 
				zombies.add(new Zombie());
				//zombieCount++;
			}
		}
		
		for(int i = 0; i < zombies.size(); i++) {
			zombies.get(i).move();
			zombies.get(i).collision();
			//zombies.get(i).damageCheck(weaponDamage);
		}
		playerX += playerXvelocity;
		playerY += playerYvelocity;
		repaint();
	}
	
	public void wave() {
		int waveCounter = 0;
		if (playfield.contains("Dungeon"))
			waveCounter += 20;
			
			
		if (playfield.contains("Highway"))
			waveCounter += 30;
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		survivalTime++;
		counterTime.setText("Time: " + survivalTime/300 + "s");
		
		//unlock the crossbow if the user doesn't have it
		//but has survived a minute
		if (survivalTime/300 >= 60 && !(weaponsUnlocked.contains("1")))
			weaponsUnlocked += "1";
		
		//unlock the battle rifle if the user doesn't have it
		//but has survived a 2 minutes
		if (survivalTime/300 >= 120 && !(weaponsUnlocked.contains("2")))
			weaponsUnlocked += "2";
		
		//don't show the ammo text for the guns that aren't unlocked
		if (weaponsUnlocked.contains("1"))
			counterCrossbowAmmo.setText("Launcher Ammo: " + crossbowAmmo);
		if (weaponsUnlocked.contains("2"))
			counterRifleAmmo.setText("Battle Rifle Ammo: " + battleRifleAmmo);
		
		if(inGame && playerHealth > 0) {
			//boundaries to keep the player in the playable area
			//the numbers are used as the player's width and height
			if (playfield.contains("Dungeon")) {
				if (playerX >= 1036) {
					playerX = 1036;
				}
				if (playerX <= 24) {
					playerX = 24;
				}
				if (playerY >= 575) {
					playerY = 575;
				}
				if (playerY <= 24) {
					playerY = 24;
				}
			}
			
			if (playfield.contains("Highway")) {
				if (playerX >= 1060) {
					playerX = 1060;
				}
				if (playerX <= 1) {
					playerX = 1;
				}
				if (playerY >= 485) {
					playerY = 485;
				}
				if (playerY <= 90) {
					playerY = 90;
				}
			}

			//draw background
			g.drawImage(background.getImage(), 0, 0, 1085, 600, null);
			
			//draw player
			g.drawImage(playerModel.getImage(), playerX, playerY, 25, 25, null);
			
			//draw zombie
			//if (zombieHealth > 0) {				
				
			for(int i=0; i < zombies.size();i++) {
					zombies.get(i).draw(g);
			}
			//}
			
			//draw the player's weapon facing the right direction
			if (direction.equals("up")) {
				g.drawImage(gunModel.getImage(), playerX+10, playerY-7, 20, 25, null);
			}
			else if (direction.equals("down")) {
				g.drawImage(gunModel.getImage(), playerX-5, playerY+7, 20, 25, null);
			}
			else if (direction.equals("left")) {
				g.drawImage(gunModel.getImage(), playerX-7, playerY-4, 25, 20, null);
			}
			else if (direction.equals("right")) {
				g.drawImage(gunModel.getImage(), playerX+7, playerY+9, 25, 20, null);
			}
			
			//draw the muzzle flash with the right direction and position relative to player
			if (shooting) {
				tempInt2++;
				if (direction.equals("up") && tempInt2 < 50) {
					g.drawImage(muzzleFlash.getImage(), playerX+17, playerY-9, 5, 5, null);
				}
				else if (direction.equals("down") && tempInt2 < 50) {
					g.drawImage(muzzleFlash.getImage(), playerX+3, playerY+30, 5, 5, null);
				}
				else if (direction.equals("left") && tempInt2 < 50) {
					g.drawImage(muzzleFlash.getImage(), playerX-9, playerY+4, 5, 5, null);
				}
				else if (direction.equals("right") && tempInt2 < 50) {
					g.drawImage(muzzleFlash.getImage(), playerX+30, playerY+16, 5, 5, null);
				}
				else
					shooting = false;
			}
			else {
				//reset the timer
				tempInt2 = 0;
			}
			
			//draw the crossbow bolt travelling
			if (checkCrossbowCollision() && boltShot) {	
				boltX += boltXvelocity;
				boltY += boltYvelocity;
				g.drawImage(bolt.getImage(), boltX, boltY, 10, 10, null);
			}
			else {
				boltXvelocity = 0;
				boltYvelocity = 0;
				boltX = 0;
				boltY = 0;
				boltShot = false;
			}
				
			
		}//end while in game
		else {
		//player dies or exits game
			this.removeAll();
			background = new ImageIcon("gameOver.png");
			g.drawImage(background.getImage(), 0, 0, 1085, 665, null);
			tempInt++;
			t.stop();
			spawnTimer.stop();
			
			if (inGame && tempInt == 1000) {
				endGame();
			}
		}
		
		
		this.revalidate();
		this.repaint();
		
    }//end paintComponent
	
	//checks whether the crossbow bolt has hit anything
	public boolean checkCrossbowCollision() {
		for(int i = 0; i < zombies.size(); i++) {
			 if(!(zombies.get(i).crossbowCollision(boltX, boltY)))
				 boltShot = false;
		}
			
		//set the proper map boundaries
		if (playfield.contains("Dungeon")) {
			//MAP boundaries for the crossbow
			if (boltX < 24 || boltX > 1036 || boltY < 24 || boltY > 590) {
				boltXvelocity = 0;
				boltYvelocity = 0;
				boltX = 0;
				boltY = 0;
				return false;
			}
			//add parameters for zombies X and Y such as above, maybe need a forloop here
			//and when true, the hit zombie's health is reduced by 75  -siraj
		}
		else if (playfield.contains("Highway")) {
			if (boltX >= 1060 || boltX <= 1 || boltY >= 485 || boltY <= 90) {
				boltXvelocity = 0;
				boltYvelocity = 0;
				boltX = 0;
				boltY = 0;
				return false;
			}
		}
		else if (playfield.contains("Castlegrounds")) {
			if (boltX < 24 || boltX > 1036 || boltY < 24 || boltY > 575) {
				boltXvelocity = 0;
				boltYvelocity = 0;
				boltX = 0;
				boltY = 0;
				return false;
			}
		}
		return true;
	}
	
	//update scores
	public void endGame() {
		//update score
		if (score > scoreBest) {
			scoreBest = score;
			score = scoreRecent;
		}
		
		if (survivalTime > survivalTimeBest) {
			survivalTimeBest = survivalTime;
			survivalTime = survivalTimeRecent;
		}
		
		score = 0;
		survivalTime = 0;
		Panel panel = new Panel();
		inGame = false;
		tempInt = 0;
		playing.dispose();
	}


	public void keyPressed(KeyEvent e) {
		int c = e.getKeyCode();
		String fileName = "";

		if(c == KeyEvent.VK_A || c == KeyEvent.VK_LEFT){
			playerModel = new ImageIcon("playerLEFT.png");
			playerXvelocity = -5;
			playerYvelocity = 0;
			direction = "left";
			weaponDirection = "LEFT.png";
		}

		if(c == KeyEvent.VK_D || c == KeyEvent.VK_RIGHT){
			playerModel = new ImageIcon("playerRIGHT.png");
			playerXvelocity = 5;
			playerYvelocity = 0;
			direction = "right";
			weaponDirection = "RIGHT.png";
		}

		if(c == KeyEvent.VK_W || c == KeyEvent.VK_UP){
			playerModel = new ImageIcon("playerUP.png");
			playerXvelocity = 0;
			playerYvelocity = -5;
			direction = "up";
			weaponDirection = "UP.png";
		}

		if(c == KeyEvent.VK_S || c == KeyEvent.VK_DOWN){
			playerModel = new ImageIcon("playerDOWN.png");
			playerXvelocity = 0;
			playerYvelocity = 5;
			direction = "down";
			weaponDirection = "DOWN.png";
		}
		
		//player swapping weapons
		if(c == KeyEvent.VK_COMMA){
			//don't allow user to switch if they haven't unlocked the gun yet
			if(equippedGun.equals("Pistol") && weaponsUnlocked.contains("2")) {
				equippedGun = "Battle Rifle";	
				weaponHeld = "rifle";
			}
			else if (equippedGun.equals("Battle Rifle") && weaponsUnlocked.contains("1")) {
				equippedGun = "Crossbow";
				weaponHeld = "crossbow";
			}
			else if (equippedGun.equals("Crossbow")) {
				equippedGun = "Pistol";
				weaponHeld = "pistol";
			}
		}
		
		if(c == KeyEvent.VK_PERIOD){
			if(equippedGun.equals("Pistol")  && weaponsUnlocked.contains("1")) {
				equippedGun = "Crossbow";
				weaponHeld = "crossbow";
			}
			else if (equippedGun.equals("Battle Rifle")) {
				equippedGun = "Pistol";
				weaponHeld = "pistol";
			}
			else if (equippedGun.equals("Crossbow")  && weaponsUnlocked.contains("2")) {
				equippedGun = "Battle Rifle";
				weaponHeld = "rifle";
			}
		}
		
		//update which gun is held
		currentGun.setText("Current Weapon: " + equippedGun);
		fileName = weaponHeld+weaponDirection;
		gunModel = new ImageIcon(fileName);
    }//end key pressed
	
	public void keyTyped(KeyEvent e) {}//end key typed

    public void keyReleased(KeyEvent e) {
		int input = e.getKeyCode();
		playerXvelocity = 0;
		playerYvelocity = 0;
		
		//player wants to shoot
		if (input == KeyEvent.VK_SLASH) {
			//player using crossbow
			if (equippedGun.equals("Crossbow")) {
				if (crossbowAmmo > 1) {
					if (!boltShot) {
						crossbowAmmo--;
						counterCrossbowAmmo.setText("Launcher Ammo: " + crossbowAmmo);
						weaponDamage = 0;
						
						boltX = playerX;
						boltY = playerY;
						
						//get the player's coords to fire the projectile from
						if (direction.equals("up")) {
							boltY = playerY - 5;
							boltX = playerX + 12;
							boltXvelocity = 0;
							boltYvelocity = -1;
						}
						
						else if (direction.equals("down")) {
							boltY = playerY + 20;
							boltX = playerX + 12;
							boltXvelocity = 0;
							boltYvelocity = 1;
						}
						
						else if (direction.equals("left")) {
							boltY = playerY + 12;
							boltX = playerX - 5;
							boltXvelocity = -1;
							boltYvelocity = 0;
						}
						
						else if (direction.equals("right")) {
							boltY = playerY + 12;
							boltX = playerX + 30;
							boltXvelocity = 1;
							boltYvelocity = 0;
						}
						boltShot = true;
					}
				}
				else {
					counterCrossbowAmmo.setText("Launcher Ammo: EMPTY");
				}
			}
			//player using battle rifle
			else if (equippedGun.equals("Battle Rifle")) {
				if (battleRifleAmmo > 1) {
					battleRifleAmmo--;
					counterRifleAmmo.setText("Battle Rifle Ammo: " + battleRifleAmmo);
					shooting = true;
					weaponDamage = 45;
				}
				else {
					counterRifleAmmo.setText("Battle Rifle Ammo: EMPTY");
				}
			}
			//player using pistol
			else if (equippedGun.equals("Pistol")) {
				shooting = true;
				weaponDamage = 20;	
			}
			for(int i = 0; i < zombies.size(); i++) {
				zombies.get(i).damageCheck(weaponDamage);
			}
		}
	}//end key released
	
	
	
	public int getScoreRecent() {
		return scoreRecent;
	}
	
	public int getScoreBest() {
		return scoreBest;
	}
	
	public int getTimeRecent() {
		return survivalTimeRecent;
	}
	
	public int getTimeBest() {
		return survivalTimeBest;
	}
}//end class

