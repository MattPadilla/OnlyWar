// Matthew A. Padilla
// OnlyWar: Enemy Class


package com.onlywar.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;


public class Enemy extends Entity {
	
	// healthPoints of the boss, when this reaches 0 the player wins
	private int healthPoints;	
	
	// projectile from attack1
	private Projectile projectile1;
	
	private Projectile projectile2;
	
	// If attack1 is in progress
	private boolean isAttack1;
	
	// If attack2 is in progress
	private boolean isAttack2;
	
	// Animations for different actions(running & attacking)
	private Animation<Texture> runningLeft;
	private Animation<Texture> runningRight;
	private Animation<Texture> attacking1Left;
	private Animation<Texture> attacking1Right;
	
	// The current frame
	private int frame;
	
	// If the enemy is looking left or right
	private boolean isLeft;
	
	// Tracks the last time an Enemy attacked
	public float lastAttackTime;
	
	// Tracks the last time this Enemy took damage
	private float lastTimeDamaged;

	
	///////////////////////
	//* Constructors *//
	///////////////////////


	public Enemy(ArrayList<Texture> texes, String name, Projectile project1, Projectile project2, float startX, float startY) {
		super(texes, 100, name);
		
		setX(startX);
		setY(startY);
		
		projectile1 = project1;
		
		projectile2 = project2;
		
		standardProjectilePos1();
		standardProjectilePos2();
		
		setHealthPoints(10);
		
		isLeft = true;
		
		isAttack1 = false;
		isAttack2 = false;
		
	}
	
	public Enemy(Enemy e) {
		
		super(e.getTextures(), e.speed, e.getName());
		setX(e.getX());
		setY(e.getY());
		
		projectile1 = e.projectile1;
		projectile1.setX(e.getX());
		projectile1.setY(e.getY());
		
		healthPoints = e.healthPoints;
		
		frame = e.frame;
		isLeft = e.isLeft;
	}
	
	
	///////////////////////
	//* HP & Damage *//
	///////////////////////
	
	
	public int getHealthPoints() {
		return healthPoints;
	}
	
	public void setHealthPoints(int hp) {
		healthPoints = hp;
	}
	
	
	public float getLastTimeDamaged() {
		return lastTimeDamaged;
	}

	public void setLastTimeDamaged(float lastTimeDamaged) {
		this.lastTimeDamaged = lastTimeDamaged;
	}
	
	
	///////////////////////
	//* Movement *//
	///////////////////////
	

	public void moveRight(float deltaX) {
		this.setX(this.getX() + deltaX);
		isLeft = false;
	}
	
	public void moveRight() {
		this.setX(this.getX() + getSpeed()*Gdx.graphics.getDeltaTime());
		isLeft = false;
	}
	
	public void moveLeft(float deltaX) {
		this.setX(this.getX() - deltaX);
		isLeft = true;
	}
	
	public void moveLeft() {
		this.setX(this.getX() - getSpeed()*Gdx.graphics.getDeltaTime());
		isLeft = true;
	}
	
	public Animation<Texture> getRunningLeft() {
		return runningLeft;
	}

	public void setRunningLeft(Animation<Texture> runningLeft) {
		this.runningLeft = runningLeft;
	}

	public Animation<Texture> getRunningRight() {
		return runningRight;
	}

	public void setRunningRight(Animation<Texture> runningRight) {
		this.runningRight = runningRight;
	}

	
	public boolean getIsLeft() {
		return isLeft;
	}
	
	public void setIsLeft(boolean bool) {
		isLeft = bool;
	}
	
	
	////////////////////////
	//*  Attack1  *//
	////////////////////////
	
	// Getter for attack1 projectile left animation
	public Animation<Texture> getAttacking1Left() {
		return attacking1Left;
	}
	
	// Setter for attack1 projectile left animation
	public void setAttacking1Left(Animation<Texture> ani) {
		attacking1Left = ani;
	}
	
	// Getter for attack1 projectile right animation
	public Animation<Texture> getAttacking1Right() {
		return attacking1Right;
	}
	
	// Setter for attack1 projectile right animation
	public void setAttacking1Right(Animation<Texture> ani) {
		attacking1Right = ani;
	}
	
	// Getter for attack1 projectile
	public Projectile getProjectile1() {
		return projectile1;
	}
	
	// Setter for attack1 projectile
	public void setProjectile1(Projectile proj) {
		projectile1 = proj;
	}
	
	// returns the necessary sprite for the enemy's projectile attack based on the time
	public Texture attack1(float time) {
		
		this.attacking1Left.setFrameDuration(0.25f);
		this.attacking1Right.setFrameDuration(0.25f);
		if(isLeft)
			return this.attacking1Left.getKeyFrame(time, true);
		else
			return this.attacking1Right.getKeyFrame(time, true);
		
	}
	
	// The default position of the projectile
	public void standardProjectilePos1() {
		if(isLeft)
			projectile1.setX(this.getX() - 20);
		else
			projectile1.setX(this.getX() + 20);
		projectile1.setY(23 + 48);
	}
		
		
	// Controls the movement of the (attack1)projectile and how long it is visible
	public void manageProjectileLifeTime1(float time) {
		
		if(this.projectile1.isVisible()) {
			projectile1.setY(23 + 48);
			
			if(isLeft) {
				this.projectile1.moveLeft();
			}
			
			else {
				this.projectile1.moveRight();
			}
			
			this.projectile1.progressTimeAlive();
		}
		
		if(projectile1.getLifeTime() <= projectile1.getTimeAlive()) {
			this.projectile1.setVisible(false);
			standardProjectilePos1();
			projectile1.setTimeAlive(0);
		} 
	}
	
	
	////////////////////////
	//*  Attack2  *//
	////////////////////////
	
	public Projectile getProjectile2() {
		return projectile2;
	}

	public void setProjectile2(Projectile projectile2) {
		this.projectile2 = projectile2;
	}

	// changes the sprite for the enemy's spike attack
	public Texture attack2(float time) {
		if(isLeft)
			return this.getTextures().get(23);
		else
			return this.getTextures().get(24);
	}
	
	// Standard position for projectile 2
	public void standardProjectilePos2() { 
		if(isLeft)
			projectile2.setX(this.getX() - 20);
		else
			projectile2.setX(this.getX() + getSprite().getRegionWidth() + 20);
		projectile1.setY(48);
	}
	
	
	public void manageProjectileLifeTime2(float time) {
		
		if(this.projectile2.isVisible() ) {
			projectile2.setY(48);
			if(projectile2.getTimeAlive() < 0.5) {
				projectile2.getSprite().setTexture(projectile2.getTextures().get(0));
			}
			else if(projectile2.getTimeAlive() > 0.5 && projectile2.getTimeAlive() < 1) {
				if(isLeft)
					projectile2.setX(getX() - 60);
				else
					projectile2.setX(getX() + 60 + getSprite().getRegionWidth());
				projectile2.getSprite().setTexture(projectile2.getTextures().get(1));
			}
			
			else if(projectile2.getTimeAlive() > 1 && projectile2.getTimeAlive() < 1.5) {
				if(isLeft)
					projectile2.setX(getX() - 100);
				else
					projectile2.setX(getX() + 100 + getSprite().getRegionWidth());
				projectile2.getSprite().setTexture(projectile2.getTextures().get(0));
			}
			
			this.projectile2.progressTimeAlive();
			
		}
		if(projectile2.getLifeTime() <= projectile2.getTimeAlive()) {
			this.projectile2.setVisible(false);
			standardProjectilePos2();
			projectile2.setTimeAlive(0);
		} 
	}
	
	
	///////////////////////
	//* AI *//
	///////////////////////
	
	// Controls how the Enemy moves
	public Texture movementPattern(Player p1, float time) {
		
		if(this.getX() - p1.getX() > 0) {
			isLeft = true;
			moveLeft();
			return runningLeft.getKeyFrame(time, true);
		}
		
		else if(this.getX() - p1.getX() < 0) {
			isLeft = false;
			moveRight();
			getSprite().setTexture(runningRight.getKeyFrame(time, true));
			return runningRight.getKeyFrame(time, true);
		}
		return getSprite().getTexture();
	}
	
	
	// Enemy's attack pattern
	public Texture pattern(Player p1, float time) { 
		
		
		this.manageProjectileLifeTime1(time);
		this.manageProjectileLifeTime2(time);
		
		// Do nothing first 5 seconds
		if(time < 5.0f) {
			
			return this.getTextures().get(0);
		}
		
		// Idle textures
		else if(!isAttack1 && time < lastAttackTime + 5) {
			if(isLeft)
				return this.getTextures().get(0);
			else
				return this.getTextures().get(5);
		}
		
		// During attack1
		else if(isAttack1 && (time - lastAttackTime < attacking1Left.getAnimationDuration() ) ) {
			
			return attack1(time-lastAttackTime);
		} 
		
		// After attack1 
		else if (isAttack1 && (time - lastAttackTime >= attacking1Left.getAnimationDuration() )) {
			standardProjectilePos1();
			projectile1.setVisible(true);
			isAttack1 = false;
			if(isLeft)
				return this.getTextures().get(0);
			else
				return this.getTextures().get(5);	
		}
		
		// During attack2
		else if(isAttack2 && time - lastAttackTime < 1.5  ) {
			
			return attack2(time-lastAttackTime);
		}
		
		// After attack2
		else if(isAttack2 && ( time - lastAttackTime >= 1.5)  ) {
			standardProjectilePos2();
			projectile2.setVisible(true);
			isAttack2 = false;
			if(isLeft)
				return this.getTextures().get(0);
			else
				return this.getTextures().get(5);
		} 
		
		
		// Requirements for attack 1
		if(this.distX(p1) >= 300 && this.distX(p1) <= 600) {
			
			lastAttackTime = time;
			isAttack1 = true;
		}
		
		// Requirements for attack 2
		else if(this.distX(p1) <= 50) {
			
			lastAttackTime = time;
			isAttack2 = true;
		}
		
		return movementPattern(p1, time);
		
	}
	
	
	
	public void dispose() {
		
		healthPoints = 0;
		lastAttackTime = 0;
		frame = 0;
		
		projectile1.dispose();
		
		
		for(int i = 0; i < attacking1Left.getKeyFrames().length; i++) {
			attacking1Left.getKeyFrames()[i].dispose();
		}
		
		for(int i = 0; i < attacking1Right.getKeyFrames().length; i++) {
			attacking1Right.getKeyFrames()[i].dispose();
		}
		
		this.getSprite().getTexture().dispose();
		
		for(int i = 0; i < this.getTextures().size(); i++) {
			this.getTextures().get(i).dispose();
		}
		
		this.getTextures().clear();
	}

}
