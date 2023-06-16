// Matthew A. Padilla
// OnlyWar: Game Class

package com.onlywar.game;

import java.util.ArrayList;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.ScreenUtils;


public class OnlyWar extends ApplicationAdapter {
	
	// Welcoming screen
	private Location welcome;
	
	// Where the gameplay takes place, controls the camera and spritebatch
	private Location level1;
	
	// Object instance of a player character-player 1(p1)
	private Player p1;
	
	// Object instance of a Enemy character, specifically the boss character
	private Enemy boss;
	
	// Tracks time passed since the game starts
	private float timeIndex = 0;
	
	private boolean dialogue;
	

	//private Music opening;
	//private Sound clickingSound;
	
	
	// Called when the OnlyWar object is created
	@Override
	public void create () {
		
		
		Sprite middle = new Sprite(new Texture("Locations/Zone Of Neutrality/ZoneOfNeutrality_Middleground.png"));
		Sprite back = new Sprite(new Texture("Locations/Zone Of Neutrality/ZoneOfNeutrality_Background.png"));
		
		welcome = new Location(true, "Beginning Screen", 800, 480, 0, null, null);
		
		welcome.getView().setToOrtho(false, 800, 480);
		
		
		// Sets up location, camera, and spritebatch
		level1 = new Location(false, "Zone of Neutrality", 800, 480, 300f, back, middle);
		
		
		// makes the y direction pointing up
		// Width: 800 pixels, Height: 480 pixels
		level1.getView().setToOrtho(false, 800, 480);
		
		// The Floor starts at 48 pixels high
		level1.setGroundLevel(48);
		
		// Creates a Weapon object with slashing animations
		Weapon w = new Weapon( aquaSwordTextures(),1, "Aqua Sword");
		w.setSlashingRight(w.createAnimation(0, 3, 0.25f));
		w.setSlashingLeft(w.createAnimation(3, 5, 0.25f));
		w.setSlashingLeft(w.addTextureToAnimation(w.getSlashingLeft(), 0, 0));
		
		// Initializes the Player object(p1) with an array of possible textures a name, and starting position
		p1 = new Player(wazziTextures(), "Wazzi", level1.getGroundLevel(), 60);
		p1.setWeapon(w);
		
		// Sets the correct textures from p1's array of textures 
		// as the textures for the Animation<Texture> class
		p1.setRunningRight(p1.createAnimation(1, 5, 0.075f));
		p1.setRunningLeft(p1.createAnimation(5, 9, 0.075f));
		p1.setJumping(p1.createAnimation(9, 12, 0.5f));
		
		// Creates two Projectile Objects for the Enemy object
		Projectile proj1 = new Projectile(projectile1Textures(), 250, "Tao Energy", 2.0f);
		
		Projectile proj2 = new Projectile(projectile2Textures(), 1, "Tao Spike", 1.5f);
		
		// Initializes the Enemy object(boss)  with an array of possible textures and a name
		boss = new Enemy(taoTextures(), "Tao", proj1, proj2, 800-256, level1.getGroundLevel());
		
		boss.setRunningLeft(boss.createAnimation(1, 5, 0.25f));
		boss.setRunningRight(boss.createAnimation(5, 9, 0.25f));
		boss.setAttacking1Left(boss.createAnimation(9, 16, 0.25f));
		boss.setAttacking1Right(boss.createAnimation(16,23, 0.25f));
		
		
		
		
	}
	
	// Called whenever the OnlyWar class has to render something during the program
	// acts as the game loop
	
	@Override
	public void render () {
		
		if(welcome.isActiveLocation()) {
			
			// Instantiates UI
			welcome.createLocationUI();
			welcomeScreen();
			return;
		}
		
		p1.isRunning = false;
		
		// updates the time since the program launched
		if(level1.isActiveLocation())
			timeIndex += Gdx.graphics.getDeltaTime();
		
		
		if(timeIndex < 5) {
			dialogue = true;
		}
		else
			dialogue = false;
	
		///////////////////////
		//* Player Jumping *//
		///////////////////////
		
		// Position in the jump and speed
		// yf = yi + (vi)t - 0.5gt^2
		// vf = vi - gt
		if(p1.isJumping) {
			p1.setY(level1.getGroundLevel() + p1.getJumpSpeed()*(p1.airTime(timeIndex)) - 0.5f*level1.getGravity()*(p1.airTime(timeIndex))*(p1.airTime(timeIndex)));
			p1.setCurrentSpeedY(p1.getJumpSpeed() - level1.getGravity()*(p1.airTime(timeIndex)));
		}
		
		// Ensures that the player does not go lower than ground level
		else {
			p1.setY(level1.getGroundLevel());
		}
		
		// flags jumping as being off when the final speed equals the negative of the initial speed
		if(p1.getCurrentSpeedY() < -200f && p1.getY() <= level1.getGroundLevel()) {
			p1.isJumping = false;
			p1.setCurrentSpeedY(0);
		}
		
		
		///////////////////////
		//* Player Attacking *//
		///////////////////////
		
		// calls p1.attack() when p1 is attacking
		if(p1.isAttacking)
			p1.attack(timeIndex); 
		
		
		///////////////////////
		//* Collisions *//
		///////////////////////
		
		
		// If the Player weapon and boss touch, the boss takes 1 damage
		if( Entity.collision(p1.getWeapon(), boss) && p1.isAttacking && (timeIndex - boss.getLastTimeDamaged() >= 1.5 || boss.getHealthPoints() == 10 )) {
			boss.setHealthPoints(boss.getHealthPoints() - 1);
			boss.setLastTimeDamaged(timeIndex);
		}
		
		// If the Player and Boss touch or Player and Boss Projectile1 touch, the player takes 1 damage
		if( ( Entity.collision(p1, boss) || (Entity.collision(p1, boss.getProjectile1()) && boss.getProjectile2().isVisible()) ) && (timeIndex - p1.getLastTimeDamaged() >= 2 || p1.getHealthPoints() == 5)  ) {
			p1.setHealthPoints(p1.getHealthPoints() - 1);
			p1.setLastTimeDamaged(timeIndex);
		}
		
		// If the Player and Boss touch or Player and Boss Projectile1 touch, the player takes 1 damage
		if(boss.getProjectile2().isVisible() && (Entity.collision(p1, boss.getProjectile2()) ) && (timeIndex - p1.getLastTimeDamaged() >= 2 || p1.getHealthPoints() == 5)  ) {
			p1.setHealthPoints(p1.getHealthPoints() - 1);
			p1.setLastTimeDamaged(timeIndex);
		}
		
		
		///////////////////////
		//* Rendering *//
		///////////////////////
		
		// clears the screen, and if neither the Player or Enemy has died
		// than the camera is set up and relevant sprites are drawn to the screen
		ScreenUtils.clear(0, 0, 0, 1);
		
		if(p1.getHealthPoints() > 0 && boss.getHealthPoints() > 0 && level1.isActiveLocation()) {
			level1.batchStart();
		
			level1.batchDraw(level1.getBackground().getTexture(), 0, 0);
			level1.batchDraw(level1.getMiddleground().getTexture(), 0, 0);
			
			level1.batchDraw(p1);
			level1.batchDraw(boss);
			
			if(p1.isAttacking)
				level1.batchDraw(p1.getWeapon());
		
			if(boss.getProjectile1().isVisible())
				level1.batchDraw(boss.getProjectile1());
			if(boss.getProjectile2().isVisible())
				level1.batchDraw(boss.getProjectile2());
		
			
		
			level1.batchEnd();
		}
		
		///////////////////////
		//* Keyboard Input *//
		///////////////////////
		
		if(!dialogue && p1.getHealthPoints() > 0 && boss.getHealthPoints() > 0 && level1.isActiveLocation()) {
		
			// moves the player character to the right/left when the right/left arrow key or the 'd'/'a' key is pressed
			// Register when the player is attacking
			// Changes the current texture of p1's sprite to match the timeIndex
			
			// Attack
			if(Gdx.input.isKeyPressed(Input.Keys.Q) && !p1.isAttacking) {
				p1.setLastTimeAttack(timeIndex);
				p1.isAttacking = true;
				if(p1.isRight)
					p1.getSprite().setTexture(p1.getTextures().get(12));
				else
					p1.getSprite().setTexture(p1.getTextures().get(13));
			}
		
			// Right
			if(!p1.isAttacking && (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D))) { 
				p1.isRunning = true;
				p1.moveRight();
				p1.getSprite().setTexture(p1.getRunningRight().getKeyFrame(timeIndex, true));
				
			}
		
			// Left
			if(!p1.isAttacking && (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A))) {
				p1.isRunning = true;
				p1.moveLeft();
				p1.getSprite().setTexture(p1.runningLeft.getKeyFrame(timeIndex, true));
			}
		
			// Jump
			if( !p1.isAttacking & (((Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) && p1.getY() == level1.getGroundLevel()))) {
				p1.isRunning = true;
				p1.jump();
				p1.getSprite().setTexture(p1.getJumping().getKeyFrame(timeIndex, false));
			}
		
		}
		
		
		///////////////////////
		//* UI & Text *//
		///////////////////////
		
		// Instantiates UI
		level1.createLocationUI();
		
		// Adds UI Elements to the locationUI
		level1.getLocationUI().getElements().add(new UIElement(null, 25, 450, p1.getName() + " HP: " + p1.getHealthPoints(), new BitmapFont()));
		level1.getLocationUI().getElements().add(new UIElement(null, 700, 450, boss.getName() + " HP: " + boss.getHealthPoints(), new BitmapFont()));
		level1.getLocationUI().getElements().add(new UIElement(null, level1.getWidth()/2 - 50, level1.getHeight()/2 + 10, "You Lose!!", new BitmapFont()));
		level1.getLocationUI().getElements().add(new UIElement(null, level1.getWidth()/2 - 55, 450, "Zone of Neutrality", new BitmapFont()));
		level1.getLocationUI().getElements().add(new UIElement(null, level1.getWidth()/2 - 50, level1.getHeight()/2 + 10, "You Win!!", new BitmapFont()));
		level1.getLocationUI().getElements().add(new UIElement(new Sprite(new Texture("WazziSprites/WazziTextbox.png")), 105, 275, "You're not catching me\n that easily!", new BitmapFont()));
		level1.getLocationUI().getElements().add(new UIElement(new Sprite(new Texture("TaoSprites/TaoTextbox.png")), 515, 275, "It's over,\nyou have broken the law.\nNow you shall cease to exist.", new BitmapFont()));
				
		// Changes UI text color and determines what text should be shown when
		level1.getLocationUI().getElements().get(0).getWords().setColor(Color.NAVY);
		level1.getLocationUI().getElements().get(1).getWords().setColor(Color.VIOLET);
		level1.getLocationUI().getElements().get(3).getWords().setColor(Color.GRAY);
		
		if(p1.getHealthPoints() > 0) 
			level1.getLocationUI().getElements().get(2).getWords().setColor(Color.CLEAR);
		else
			level1.getLocationUI().getElements().get(2).getWords().setColor(Color.WHITE);
		if(boss.getHealthPoints() > 0)
			level1.getLocationUI().getElements().get(4).getWords().setColor(Color.CLEAR);
		else
			level1.getLocationUI().getElements().get(4).getWords().setColor(Color.WHITE);
		if(dialogue) {
			level1.getLocationUI().getElements().get(5).getWords().setColor(Color.SCARLET);
			level1.getLocationUI().getElements().get(6).getWords().setColor(Color.MAGENTA);
		}
		else {
			level1.getLocationUI().getElements().get(5).getWords().setColor(Color.CLEAR);
			level1.getLocationUI().getElements().get(6).getWords().setColor(Color.CLEAR);
		}
		
		// Sets Positions for UI Dialogue Sprites
		
		if(dialogue) {
			level1.getLocationUI().getElements().get(5).getSprite().setX(90);
			level1.getLocationUI().getElements().get(5).getSprite().setY(200);
			level1.getLocationUI().getElements().get(6).getSprite().setX(500);
			level1.getLocationUI().getElements().get(6).getSprite().setY(200);
		}
		
		// Gets rid of sprites for dialogue
		if(!dialogue) {
			level1.getLocationUI().getElements().get(5).getSprite().getTexture().dispose();
			level1.getLocationUI().getElements().get(5).setSprite(null);
			level1.getLocationUI().getElements().get(6).getSprite().getTexture().dispose();
			level1.getLocationUI().getElements().get(6).setSprite(null);
		}
				
		// Draws text to screen.
		level1.getLocationUI().drawElements();
		
		
		
		///////////////////////
		//* Misc. *//
		///////////////////////
		
		// Checks to see if the time the player has been on the ground has to change
		p1.setTimeGrounded(timeIndex, level1.getGroundLevel());
		
		// When the Player character isn't moving sets the Texture to a default right facing or left facing one
		if(p1.isRight && !p1.isRunning && p1.getY() == level1.getGroundLevel() && !p1.isAttacking) {
			p1.getSprite().setTexture(p1.getTextures().get(1));
		}
		else if(!p1.isRight && !p1.isRunning && p1.getY() == level1.getGroundLevel() && !p1.isAttacking) {
			p1.getSprite().setTexture(p1.getTextures().get(5));
		}
		
		
		// The movement & attack pattern for the boss object
		boss.getSprite().setTexture(boss.pattern(p1, timeIndex));
		
		// Makes sure the p1 or boss objects do not leave the area within the camera's view
		if(p1.getX() < 50) p1.setX(50) ;
		if(p1.getX() > level1.getWidth() - p1.getSprite().getRegionWidth() - 50) p1.setX(level1.getWidth() - p1.getSprite().getRegionWidth() - 50);
		if(p1.getY() < 0) p1.setY(0);
		
		
		if(boss.getX() < 50) boss.setX(50);
		if(boss.getX() > level1.getWidth() - 50) boss.setX(level1.getWidth() - 50);
		if(boss.getY() < level1.getGroundLevel()) boss.setY(level1.getGroundLevel());
		
		
		
	}
	
	
	// method that concerns the welcome Screen for the game
		private void welcomeScreen() {
			
			ScreenUtils.clear(0.2f, 0.2f, 0.2f, 1f);
					
			// Adds UIElements to the UI object of the location
			welcome.getLocationUI().getElements().add(new UIElement(new Sprite(new Texture("OnlyWarLogo.png")), 300, 450, "", new BitmapFont()));
			welcome.getLocationUI().getElements().get(0).getSprite().setX(350);
			welcome.getLocationUI().getElements().get(0).getSprite().setY(350);
			welcome.getLocationUI().getElements().add(new UIElement(new Sprite(new Texture("Locations/WhiteRectangle.png")), 365, 210, "Start Game\nClick Here", new BitmapFont()));
			welcome.getLocationUI().getElements().get(1).getSprite().setX(305);
			welcome.getLocationUI().getElements().get(1).getSprite().setY(150);
			welcome.getLocationUI().getElements().add(new UIElement(null, 100, 220, "Right: D or Right Arrow Key\nLeft A or Left Arrow Key\nJump: W or up Arrow Key\nAttack: Q", new BitmapFont()));
			
							
			// Draws text to screen.
			
			welcome.getLocationUI().getElements().get(1).getWords().setColor(Color.BLACK);
			welcome.getLocationUI().drawElements();
			
			Sprite whiteBox = new Sprite(welcome.getLocationUI().getElements().get(1).getSprite()); 
			
			if( Gdx.input.getPressure() == 1 
			&& Gdx.input.getX() >= whiteBox.getX() 
			&& Gdx.input.getX() <= whiteBox.getX() + whiteBox.getRegionWidth()
			&& Gdx.input.getY() >= welcome.getHeight() - whiteBox.getY() - whiteBox.getRegionHeight() 
			&& Gdx.input.getY() <= welcome.getHeight() -  whiteBox.getY()) {
				welcome.setActiveLocation(false);
				level1.setActiveLocation(true);
			}
			
		}
	
	
	///////////////////////
	//* Texture Binding *//
	///////////////////////
	
	// Called when binding Wazzi.pngs to textures
	private ArrayList<Texture> wazziTextures() {
		
		ArrayList<Texture> arr = new ArrayList<Texture>();
		
		arr.add(new Texture("WazziSprites/WazziStandbyForward.png"));
		arr.add(new Texture("WazziSprites/WazziStandbyRight.png"));
		arr.add(new Texture("WazziSprites/WazziRunRight1.png"));
		arr.add(new Texture("WazziSprites/WazziRunRight2.png"));
		arr.add(new Texture("WazziSprites/WazziRunRight3.png"));
		arr.add(new Texture("WazziSprites/WazziStandbyLeft.png"));
		arr.add(new Texture("WazziSprites/WazziRunLeft1.png"));
		arr.add(new Texture("WazziSprites/WazzirunLeft2.png"));
		arr.add(new Texture("WazziSprites/WazzirunLeft3.png"));
		arr.add(new Texture("WazziSprites/WazziJumpingRight1.png"));
		arr.add(new Texture("WazziSprites/WazziJumpingRight2.png"));
		arr.add(new Texture("WazziSprites/WazziJumpingRight3.png"));
		arr.add(new Texture("WazziSprites/WazziHoldingRight.png"));
		arr.add(new Texture("WazziSprites/WazziHoldingLeft.png"));
		
		return arr;
		
	}
	
	// Called when binding Tao.pngs to textures
	private ArrayList<Texture> taoTextures() {
		
		ArrayList<Texture> arr = new ArrayList<Texture>();
		
		arr.add(new Texture("TaoSprites/TaoStandbyForward.png"));
		arr.add(new Texture("TaoSprites/TaoStandbyLeft.png"));
		for(int i = 1; i <= 3; i++) 
			arr.add(new Texture("TaoSprites/TaoRunLeft" + i + ".png"));
		
		arr.add(new Texture("TaoSprites/TaoStandbyRight.png"));
		for(int i = 1; i <= 3; i++) 
			arr.add(new Texture("TaoSprites/TaoRunRight" + i + ".png"));
		
		
		for(int i = 1; i <= 7; i++) 
			arr.add(new Texture("TaoSprites/TaoShootingLeft" + i + ".png"));
		
		for(int i = 1; i <= 7; i++) 
			arr.add(new Texture("TaoSprites/TaoShootingRight" + i + ".png"));
		
		arr.add(new Texture("TaoSprites/TaoShootingLeft.png"));
		arr.add(new Texture("TaoSprites/TaoShootingRight.png"));
		
		return arr;
		
	}
	
	// Called when binding TaoProjectile1.pngs to textures
	private ArrayList<Texture> projectile1Textures() {
		ArrayList<Texture> arr = new ArrayList<Texture>();
		arr.add(new Texture("TaoSprites/TaoProjectile.png"));
		return arr;
	}
	
	// Called when binding TaoProjectile2.pngs to textures
	private ArrayList<Texture> projectile2Textures() {
		ArrayList<Texture> arr = new ArrayList<Texture>();
		arr.add(new Texture("TaoSprites/BlackSpike.png"));
		arr.add(new Texture("TaoSprites/WhiteSpike.png"));
		return arr;
	}
	
	// Called when binding WazziSword.pngs to Textures
	private ArrayList<Texture> aquaSwordTextures() {
		ArrayList<Texture> arr = new ArrayList<Texture>();
		
		for(int i = 1; i <= 5; i++) 
			arr.add(new Texture("WazziSprites/AquaSword" + i + ".png"));
		return arr;
	}
	
	
	// Called when the OnlyWar object is destroyed
	// Destroys objects and sets primitives to default values
	@Override
	public void dispose () {
		welcome.dispose();
		level1.dispose();
		p1.dispose();
		boss.dispose();
		timeIndex = 0;
	}
	
	
}
