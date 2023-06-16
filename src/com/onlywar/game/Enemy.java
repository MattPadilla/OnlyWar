// Matthew A. Padilla
// OnlyWar: Enemy Class


package com.onlywar.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;


public class Enemy extends Entity {
	
	// healthPoints of the boss, when this reaches 0 the player wins
	public int healthPoints;	
	
	// projectile from attack1
	private Projectile projectile1;
	
	private Projectile projectile2;
	
	private boolean risingSpike;
	
	private boolean isAttack1;
	private boolean isAttack2;
	
	// Animations for different actions
	public Animation<Texture> runningRight;
	public Animation<Texture> runningLeft;
	private Animation<Texture> attacking1;
	private Animation<Texture> attacking2;
	
	// The current frame
	private int frame;
	
	// If the enemy is looking left or right
	private boolean isLeft;
	
	// Tracks the last time an Enemy attacked
	public float lastAttackTime;

	


	public Enemy(ArrayList<Texture> texes, String name, Projectile project1, Projectile project2, float startX, float startY) {
		super(texes, 100, name);
		
		setX(startX);
		setY(startY);
		
		projectile1 = project1;
		
		projectile2 = project2;
		
		standardProjectilePos1();
		
		healthPoints = 25;
		
		frame = 1;
		
		isLeft = true;
		
		risingSpike = true;
		
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
	
	
	public int getHealthPoints() {
		return healthPoints;
	}
	
	public void setHealthPoints(int hp) {
		healthPoints = hp;
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
	
	public Animation<Texture> getAttacking1() {
		return attacking1;
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
	
	public void setAttacking1(Animation<Texture> ani) {
		attacking1 = ani;
	}
	
	public void setAttacking1(int first, int last) {
		
		Texture[] arr = new Texture[last-first];
		for(int i = first; i < last; i++) {
			arr[i-first] = this.getTextures().get(i);
		}
		
		attacking1 = new Animation<Texture>(0.25f,arr);
		
	}
	
	public void addToAttacking1(int texturesIndex, int animationIndex) {
		
		Texture[] arr = new Texture[attacking1.getKeyFrames().length+1];
		
		for(int i = 0, k = 0; i < attacking1.getKeyFrames().length+1; i++) {
			if(i == animationIndex) {
				arr[i] = this.getTextures().get(texturesIndex);
			}
			else {
				arr[i] = this.attacking1.getKeyFrames()[k];
				k++;
			}
			
		}
		
		attacking1 = new Animation<Texture>(0.25f,arr);

	}
	
	public Projectile getProjectile1() {
		return projectile1;
	}
	
	public void setProjectile1(Projectile proj) {
		projectile1 = proj;
	}
	
	// changes the sprite for the enemy's projectile attack
	public Texture attack1(float time) {
		this.attacking1.setFrameDuration(0.25f);
		
		frame++;
		return this.attacking1.getKeyFrame(time, true);
		
	}
	
	
	// The default position of the projectile
	public void standardProjectilePos1() {
		if(isLeft)
			projectile1.setX(this.getX() - 20);
		else
			projectile1.setX(this.getX() + 20);
		projectile1.setY(23 + getY());
	}
		
		
	// Controls the movement of the projectile and how long it is visible
	public void manageProjectileLifeTime1(float time) {
		
		if(this.projectile1.isVisible) {
			
			if(isLeft) {
				this.projectile1.moveLeft();
			}
			
			else {
				this.projectile1.moveRight();
			}
			
			this.projectile1.progressTimeAlive();
		}
		
		if(projectile1.getLifeTime() <= projectile1.getTimeAlive()) {
			this.projectile1.isVisible = false;
			standardProjectilePos1();
			projectile1.setTimeAlive(0);
		} 
	}
	
	
	////////////////////////
	//*  Attack2  *//
	////////////////////////
	
	
	public void setAttacking2(Animation<Texture> ani) {
		attacking2 = ani;
	}
	
	public Projectile getProjectile2() {
		return projectile2;
	}

	public void setProjectile2(Projectile projectile2) {
		this.projectile2 = projectile2;
	}

	// changes the sprite for the enemy's spike attack
	public Texture attack2(float time) {
		this.attacking2.setFrameDuration(2f);
		frame++;
		return this.attacking1.getKeyFrame(time, true);
	}
	
	public void standardProjectilePos2() { 
		if(isLeft)
			projectile2.setX(this.getX() - 40);
		else
			projectile2.setX(this.getX() + 40);
		projectile1.setY(-28 + getY());
	}
	
	
	public void manageProjectileLifeTime2(float time) {
		
		if(this.projectile2.isVisible) {
			
			if(projectile2.getY() < 0 && risingSpike) {
				projectile2.moveUp();
			}
			else if(projectile2.getY() >= 0 && projectile2.getTimeAlive() > 0.75) {
				risingSpike = false;
				projectile2.moveDown();
			}
			else if(projectile2.getY() < 0 && !risingSpike) {
				projectile2.moveDown();
			}
				
			this.projectile2.progressTimeAlive();
		}
		
		if(projectile2.getLifeTime() <= projectile2.getTimeAlive()) {
			this.projectile2.isVisible = false;
			standardProjectilePos2();
			projectile2.setTimeAlive(0);
		} 
	}
	
	
	///////////////////////
	//* AI *//
	///////////////////////
	
	public Texture movementPattern(Player p1) {
		
		if(getX() - p1.getX() > 0) {
			isLeft = true;
			moveLeft();
		}
		
		else if(this.getX() - p1.getX() < 0) {
			isLeft = false;
			moveRight();
		}
		return getSprite().getTexture();
	}
	
	
	// The enemy's attack pattern
	public Texture pattern(Player p1, float time) {
			
		this.manageProjectileLifeTime1(time);
		//this.manageProjectileLifeTime2(time);
		
		if(time < 5.0f) {
			frame = 1;
			//projectile1.setTimeAlive(0);
			//projectile2.setTimeAlive(0);
			
			return this.getSprite().getTexture();
		}
		
		else if(frame == 1 && time < lastAttackTime + 5) {
			return this.getSprite().getTexture();
		}
		
		if(frame >= attacking1.getAnimationDuration()*Gdx.graphics.getFramesPerSecond() - 65) {
			
			frame = 1;
			this.projectile1.isVisible = true;
			return this.getSprite().getTexture();
			
		} 
		
		/*if(time - lastAttackTime >= attacking1.getAnimationDuration()) {
			frame = 1;
			this.projectile1.isVisible = true;
			return this.getSprite().getTexture();
		} */
		
		/*else if(frame >= attacking2.getAnimationDuration()*Gdx.graphics.getFramesPerSecond()) {
			frame = 1;
			this.projectile2.isVisible = true;
			return this.getSprite().getTexture();
		} */
		
		/*
		if(time - lastAttackTime < attacking1.getAnimationDuration())
			return attack1(time);
		
		if(this.distX(p1) >= 300 && this.distX(p1) <= 600) {
			
			lastAttackTime = time;
		} */
		
		
		if(this.distX(p1) >= 300 && this.distX(p1) <= 600) {
			
			lastAttackTime = time;
			return attack1(time);
			
		}
		
		else if(this.distX(p1) <= 100) {
			
			lastAttackTime = time;
			return attack2(time);
		}
		
		this.movementPattern(p1);
		
		return getSprite().getTexture(); 
			
	}
	
	
	
	public Texture pattern2(Player p1, float time) { 
		
		
		this.manageProjectileLifeTime1(time);
		this.manageProjectileLifeTime2(time);
		
		if(time < 5.0f) {
			
			return this.getTextures().get(0);
		}
		
		else if(!isAttack1 && time < lastAttackTime + 5) {
			return this.getTextures().get(0);
		}
		
		
		else if(isAttack1 && (time - lastAttackTime < attacking1.getAnimationDuration() ) ) {
			
			return attack1(time-lastAttackTime);
		} 
		
		else if (isAttack1 && (time - lastAttackTime >= attacking1.getAnimationDuration() )) {
			projectile1.isVisible = true;
			isAttack1 = false;
			return this.getTextures().get(0);		
		}
		
		else if(isAttack2 && (time - lastAttackTime < attacking2.getAnimationDuration() ) ) {
			
			return attack2(time-lastAttackTime);
		}
		
		else if(isAttack2 && ( time - lastAttackTime >= attacking2.getAnimationDuration() ) ) {
			projectile2.isVisible = true;
			isAttack2 = false;
			return this.getTextures().get(0);	
		} 
		
		
		if(this.distX(p1) >= 300 && this.distX(p1) <= 600) {
			
			lastAttackTime = time;
			isAttack1 = true;
		}
		
		else if(this.distX(p1) <= 50) {
			
			lastAttackTime = time;
			isAttack2 = true;
		}
		
		this.movementPattern(p1);
		
		return this.getTextures().get(0);
	}
	
	
	
	public void dispose() {
		
		healthPoints = 0;
		lastAttackTime = 0;
		frame = 0;
		
		projectile1.dispose();
		
		
		for(int i = 0; i < attacking1.getKeyFrames().length; i++) {
			attacking1.getKeyFrames()[i].dispose();
		}
		
		for(int i = 0; i < attacking2.getKeyFrames().length; i++) {
			attacking2.getKeyFrames()[i].dispose();
		}
		
		this.getSprite().getTexture().dispose();
		
		for(int i = 0; i < this.getTextures().size(); i++) {
			this.getTextures().get(i).dispose();
		}
		
		this.getTextures().clear();
	}

}
