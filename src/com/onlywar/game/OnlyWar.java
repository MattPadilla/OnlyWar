// Matthew A. Padilla
// OnlyWar: Game Class

package com.onlywar.game;

import java.util.ArrayList;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.physics.box2d.*;

import com.onlywar.game.Location;


public class OnlyWar extends ApplicationAdapter {
	
	// Welcoming screen
	//private Location welcome;
	
	// Where the gameplay takes place, controls the camera and spritebatch
	private Location level1;
	
	// Object instance of a player character-player 1(p1)
	private Player p1;
	
	// Object instance of a Enemy character, specifically the boss character
	private Enemy boss;
	
	// Tracks time passed since the game starts
	private float timeIndex = 0;
	
	//private boolean isRunning = false;
	

	//private Music opening;
	//private Sound clickingSound;
	
	
	// Called when the OnlyWar object is created
	@Override
	public void create () {
		
		Sprite middle = new Sprite(new Texture("Locations/Zone Of Neutrality/ZoneOfNeutrality_Middleground.png"));
		Sprite back = new Sprite(new Texture("Locations/Zone Of Neutrality/ZoneOfNeutrality_Background.png"));
		
		
		// Sets up location, camera, and spritebatch
		level1 = new Location("Zone of Neutrality", 800, 480, 300f, back, middle );
		
		
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
		//p1.setAttackingRight(p1.createAnimation(12, 15, 0.5f));
		
		Projectile proj1 = new Projectile(projectile1Textures(), 250, "Tao Energy", 2.0f);
		
		Projectile proj2 = new Projectile(projectile2Textures(), 1, "Tao Spike", 1.0f);
		
		// Initializes the Enemy object(boss)  with an array of possible textures and a name
		boss = new Enemy(taoTextures(), "Tao", proj1, proj2, 800-256, level1.getGroundLevel());
		
		boss.setAttacking1(boss.createAnimation(3, 10, 0.25f));
		boss.setAttacking2(boss.createAnimation(3, 4, 2f));
		
		boss.addToAttacking1(2, boss.getAttacking1().getKeyFrames().length);
		
		
	}
	
	// Called whenever the OnlyWar class has to render something during the program
	// acts as the game loop
	
	@Override
	public void render () {
		
		p1.isRunning = false;
		
		// updates the time since the program launched
		timeIndex += Gdx.graphics.getDeltaTime();
	
		///////////////////////
		//* Player Jumping *//
		///////////////////////
		
		if(p1.isJumping) {
			p1.setY(level1.getGroundLevel() + p1.getJumpSpeed()*(p1.airTime(timeIndex)) - 0.5f*level1.getGravity()*(p1.airTime(timeIndex))*(p1.airTime(timeIndex)));
			p1.setCurrentSpeedY(p1.getJumpSpeed() - level1.getGravity()*(p1.airTime(timeIndex)));
		}
		
		else {
			p1.setY(level1.getGroundLevel());
		}
		
		
		if(p1.getCurrentSpeedY() < -200f && p1.getY() <= level1.getGroundLevel()) {
			p1.isJumping = false;
			p1.setCurrentSpeedY(0);
		}
		
		
		///////////////////////
		//* Player Attacking *//
		///////////////////////
		
		
		if(p1.isAttacking)
			p1.attack(timeIndex); 
		
		
		///////////////////////
		//* Collisions *//
		///////////////////////
		
		
		// If the Player weapon and boss touch
		if( Entity.collision(p1.getWeapon(), boss) && p1.isAttacking ) {
			boss.setHealthPoints(boss.getHealthPoints() - 1);
			// boss.setLastTimeDamaged(timeIndex);
		}
		
		// If the Player and Boss touch or Player and Boss Projectile touch
		if( ( Entity.collision(p1, boss) || Entity.collision(p1, boss.getProjectile1()) ) && (timeIndex - p1.getLastTimeDamaged() >= 2 || p1.getHealthPoints() == 5)  ) {
			p1.setHealthPoints(p1.getHealthPoints() - 1);
			p1.setLastTimeDamaged(timeIndex);
		}
		
		
		if(p1.getHealthPoints() <= 0) {
			ScreenUtils.clear(0, 0, 0, 1);
			this.pause();
		}
		
		///////////////////////
		//* Rendering *//
		///////////////////////
		
		// clears the screen and sets the area within the camera to be where sprites are rendered
		
		if(p1.getHealthPoints() > 0) {
			
			ScreenUtils.clear(0, 0, 0, 1);
			level1.batchStart();
		
			level1.batchDraw(level1.getBackground().getTexture(), 0, 0);
			level1.batchDraw(level1.getMiddleground().getTexture(), 0, 0);
			
			level1.batchDraw(p1);
			level1.batchDraw(boss);
			
			if(p1.isAttacking)
				level1.batchDraw(p1.getWeapon());
		
			if(boss.getProjectile1().isVisible)
				level1.batchDraw(boss.getProjectile1());
			if(boss.getProjectile2().isVisible)
				level1.batchDraw(boss.getProjectile2());
		
			
		
			level1.batchEnd();
		}
		
		///////////////////////
		//* Keyboard Input *//
		///////////////////////
		
		// moves the player character to the right/left when the right/left arrow key or the 'd'/'a' key is pressed
		// Changes the current texture of p1's sprite to match the timeIndex
		if(Gdx.input.isKeyPressed(Input.Keys.Q) && !p1.isAttacking) {
			p1.setLastTimeAttack(timeIndex);
			p1.isAttacking = true;
			//p1.getSprite().setTexture(p1.getAttackingRight().getKeyFrame(timeIndex, true));
		}
		
		if(!p1.isAttacking && (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D))) { 
			p1.isRunning = true;
			p1.moveRight();
			p1.getSprite().setTexture(p1.getRunningRight().getKeyFrame(timeIndex, true));

		}
		
		if(!p1.isAttacking && (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A))) {
			p1.isRunning = true;
			p1.moveLeft();
			p1.getSprite().setTexture(p1.runningLeft.getKeyFrame(timeIndex, true));
		}
		
		if( !p1.isAttacking & (((Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) && p1.getY() == level1.getGroundLevel()))) {
			p1.isRunning = true;
			p1.jump();
			p1.getSprite().setTexture(p1.getJumping().getKeyFrame(timeIndex, false));
		}
		
		
		
		///////////////////////
		//* UI & Text *//
		///////////////////////
		
		// Positions for text 
		float x[] = {25, 700, level1.getWidth()/2 - 50, level1.getWidth()/2 - 55};
		float y[] = {450, 450, level1.getHeight()/2 + 10, 450};
				
		// text to be drawn to screen
		String words[] = {p1.getName() + " HP: " + p1.getHealthPoints(), boss.getName() + " HP: " + boss.healthPoints,
									"You Lose!!", "Zone of Neutrality"};
				
		// Instantiates UI
		level1.createLocationUI(null, words);
				
		// Changes UI color
		level1.getLocationUI().getTexts().get(0).setColor(Color.NAVY);
		level1.getLocationUI().getTexts().get(1).setColor(Color.VIOLET);
		
		// Displays "You lose!!" if player HP is less than or equal to 0
		if(p1.getHealthPoints() > 0)
			level1.getLocationUI().getTexts().get(2).setColor(Color.CLEAR);
		else 
			level1.getLocationUI().getTexts().get(2).setColor(Color.WHITE);
		
				
		// Draws text to screen.
		level1.getLocationUI().drawText(x, y);
		
		
		p1.setTimeGrounded(timeIndex, level1.getGroundLevel());
		
		///////////////////////
		//* Misc. *//
		///////////////////////
		
		if(p1.isRight && !p1.isRunning && p1.getY() == level1.getGroundLevel() && !p1.isAttacking) {
			p1.getSprite().setTexture(p1.getTextures().get(1));
		}
		else if(!p1.isRight && !p1.isRunning && p1.getY() == level1.getGroundLevel() && !p1.isAttacking) {
			p1.getSprite().setTexture(p1.getTextures().get(5));
		}
		
		// The movement & attack pattern for the boss object
		boss.getSprite().setTexture(boss.pattern2(p1, timeIndex));
		
		// Makes sure the p1 or boss objects do not leave the area within the camera's view
		if(p1.getX() < 50) p1.setX(50) ;
		if(p1.getX() > level1.getWidth() - p1.getSprite().getRegionWidth() - 50) p1.setX(level1.getWidth() - p1.getSprite().getRegionWidth() - 50);
		if(p1.getY() < 0) p1.setY(0);
		
		
		if(boss.getX() < 50) boss.setX(50);
		if(boss.getX() > level1.getWidth() - 50) boss.setX(level1.getWidth() - 50);
		if(boss.getY() < level1.getGroundLevel()) boss.setY(level1.getGroundLevel());
		
		
		
	}
	
	
	// Called when the OnlyWar object is destroyed
	@Override
	public void dispose () {

		level1.dispose();
		p1.dispose();
		boss.dispose();
		timeIndex = 0;
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
		arr.add(new Texture("WazziSprites/WazziAttackRight1.png"));
		arr.add(new Texture("WazziSprites/WazziAttackRight2.png"));
		arr.add(new Texture("WazziSprites/WazziAttackRight3.png"));
		
		return arr;
		
	}
	
	// Called when binding Tao.pngs to textures
	private ArrayList<Texture> taoTextures() {
		
		ArrayList<Texture> arr = new ArrayList<Texture>();
		String tS = "TaoSprites/TaoShootingLeft";
		
		
		arr.add(new Texture("TaoSprites/TaoStandbyForward.png"));
		arr.add(new Texture("TaoSprites/TaoStandbyRight.png"));
		arr.add(new Texture("TaoSprites/TaoStandbyLeft.png"));
		for(int i = 1; i <= 7; i++) 
			arr.add(new Texture(tS + i + ".png"));
		
		
		return arr;
		
	}
	
	private ArrayList<Texture> projectile1Textures() {
		ArrayList<Texture> arr = new ArrayList<Texture>();
		arr.add(new Texture("TaoSprites/TaoProjectile.png"));
		return arr;
	}
	
	private ArrayList<Texture> projectile2Textures() {
		ArrayList<Texture> arr = new ArrayList<Texture>();
		arr.add(new Texture("TaoSprites/BlackSpike.png"));
		arr.add(new Texture("TaoSprites/WhiteSpike.png"));
		return arr;
	}
	
	private ArrayList<Texture> aquaSwordTextures() {
		ArrayList<Texture> arr = new ArrayList<Texture>();
		arr.add(new Texture("WazziSprites/AquaSword1.png"));
		arr.add(new Texture("WazziSprites/AquaSword2.png"));
		arr.add(new Texture("WazziSprites/AquaSword3.png"));
		arr.add(new Texture("WazziSprites/AquaSword4.png"));
		arr.add(new Texture("WazziSprites/AquaSword5.png"));
		return arr;
	}
	
	
	
}
