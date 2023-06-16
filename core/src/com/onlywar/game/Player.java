// Matthew A. Padilla
// OnlyWar: Player Class


package com.onlywar.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;

public class Player extends Entity {
	
	// The Player's weapon
	private Weapon weapon;
	
	// healthPoints of the player, when this reaches 0 the player looses
	private int healthPoints;
	
	// Last time the player took damage
	private float lastTimeDamaged;
	
	// Initial jumping speed of the character
	private final float jumpSpeed = 200f;
	
	// The characters current speed in the y-direction
	private float currentSpeedY;
	
	// The last time the player was on the ground Y = 0
	private float timeGrounded = 0;
	
	// The most recent time when the Player started their attack sequence
	private float lastTimeAttack;
	
	// If the Player is currently jumping or in the air
	public boolean isJumping;
	
	// If the Player is facing Right
	public boolean isRight;
	
	// If the Player is Attacking
	public boolean isAttacking;
	
	// If the Player is Running
	public boolean isRunning;
	
	
	// Puts textures into their appropriate animations which associates each texture with a time
	public Animation<Texture> runningRight;
	public Animation<Texture> runningLeft;
	public Animation<Texture> jumping;
	private Animation<Texture> attackingRight;
	private Animation<Texture> attackingLeft;
	
	
	public Player(ArrayList<Texture> texes, String name, float startX, float startY) {
		
		super(texes, 200, name);
		
		setX(startX);
		setY(startY);
	
		weapon = null;
		runningRight = null;
		runningLeft = null;
		jumping = null;
		attackingRight = null;
		attackingLeft = null;
		
		setCurrentSpeedY(0);
		
		setLastTimeDamaged(0);
		
		setHealthPoints(5);
		
		isRunning = false;
		
		isJumping = false;
		
		isRight = true;
		
		isAttacking = false;
		
		lastTimeAttack = 0;
		
		
	}
	
	public Weapon getWeapon() {
		return weapon;
	}

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}

	public void weaponStandardPos(float time) {
		weapon.setY(getY() + 23);
		if(isRight) {
			weapon.setX(getX() + this.getSprite().getRegionWidth()-5);
			if(this.timeSinceAttack(time) > weapon.getSlashingRight().getFrameDuration() && this.timeSinceAttack(time) <= weapon.getSlashingRight().getFrameDuration()*2) {
				weapon.setX(weapon.getX()+3);
				weapon.setY(weapon.getY());
			}
			else if(this.timeSinceAttack(time) > weapon.getSlashingRight().getFrameDuration()*2 && this.timeSinceAttack(time) <= weapon.getSlashingRight().getFrameDuration()*3) {
				weapon.setX(weapon.getX()+4);
				weapon.setY(weapon.getY()-5);
			}
		}
		else  {
			weapon.setX(getX() - this.getSprite().getRegionWidth()/2 + 7);
			if(this.timeSinceAttack(time) > weapon.getSlashingLeft().getFrameDuration() && this.timeSinceAttack(time) <= weapon.getSlashingLeft().getFrameDuration()*2) {
				weapon.setX(weapon.getX()-15);
				weapon.setY(weapon.getY());
			}
			else if(this.timeSinceAttack(time) > weapon.getSlashingLeft().getFrameDuration()*2 && this.timeSinceAttack(time) <= weapon.getSlashingLeft().getFrameDuration()*3) {
				weapon.setX(weapon.getX()-20);
				weapon.setY(weapon.getY()-5);
			}
		}
	}
	
	// Getter for healthpoints
	public int getHealthPoints() {
		return healthPoints;
	}

	// Setter for healthpoints
	public void setHealthPoints(int healthPoints) {
		this.healthPoints = healthPoints;
	}

	public float getLastTimeDamaged() {
		return lastTimeDamaged;
	}

	public void setLastTimeDamaged(float lastTimeDamaged) {
		this.lastTimeDamaged = lastTimeDamaged;
	}

	// Moves the character right a certain change in X
	public void moveRight(float deltaX) {
		this.setX(this.getX() + deltaX);
		isRight = true;
	}
	
	public void moveRight() {
		this.setX(this.getX() + getSpeed()*Gdx.graphics.getDeltaTime());
		isRight = true;
	}
	
	public void moveLeft(float deltaX) {
		this.setX(this.getX() - deltaX);
		isRight = false;
	}
	
	public void moveLeft() {
		this.setX(this.getX() - getSpeed()*Gdx.graphics.getDeltaTime());
		isRight = false;
	}
	
	public void moveUp() {
		this.setY(this.getY() + currentSpeedY*Gdx.graphics.getDeltaTime()/2);
	}
	
	public void moveUp(float deltaY) {
		this.setY(this.getY() + deltaY);
	}
	
	public void moveDown() {
		this.setY(this.getY() - currentSpeedY*Gdx.graphics.getDeltaTime());
	}
	
	public void moveDown(float deltaY) {
		this.setY(this.getY() - deltaY);
	}
	
	// Sets the current speed to the initial jump speed
	public void jump() {
		currentSpeedY = jumpSpeed;
		isJumping = true;
	}
	
	// Resets the last time the player was touching the ground
	public void setTimeGrounded(float time, float groundLevel) {
		if(getY() <= groundLevel) {
			timeGrounded = time;
		}
	}
	
	// How long the player is in the air
	public float airTime(float time) {
		return time - timeGrounded;
	}
	
	// Getter for the player's current speed in the y-direction
	public float getCurrentSpeedY() {
		return currentSpeedY;
	}

	// Setter for the player's current speed in the y-direction
	public void setCurrentSpeedY(float currentSpeedY) {
		this.currentSpeedY = currentSpeedY;
	}

	// Getter for the player's initial jump speed
	public float getJumpSpeed() {
		return jumpSpeed;
	}
	
	public Animation<Texture> getRunningRight() {
		return runningRight;
	}
	
	public void setRunningRight(Animation<Texture> ani) {
		runningRight = ani;
	}
	
	public Animation<Texture> getRunningLeft() {
		return runningLeft;
	}
	
	public void setRunningLeft(Animation<Texture> ani) {
		runningLeft = ani;
	}
	
	public Animation<Texture> getJumping() {
		return jumping;
	}
	
	public void setJumping(Animation<Texture> ani) {
		jumping = ani;
	}
	
	public Animation<Texture> getAttackingRight() {
		return attackingRight;
	}
	
	public void setAttackingRight(Animation<Texture> ani) {
		attackingRight = ani;
	}
	
	public Animation<Texture> getAttackingLeft() {
		return attackingLeft;
	}
	
	public void setAttackingLeft(Animation<Texture> ani) {
		attackingLeft = ani;
	}
	
	
	public void attack(float time) {
		weaponStandardPos(time);
		if(isRight) {
			if(timeSinceAttack(time) <= weapon.getSlashingRight().getAnimationDuration()) {
				isAttacking = true;
				weapon.getSprite().setTexture(weapon.slash(isRight,timeSinceAttack(time)));
			}
			else 
				isAttacking = false;
	
		}
		
		else {
			if(timeSinceAttack(time) <= weapon.getSlashingLeft().getAnimationDuration()) {
				isAttacking = true;
				weapon.getSprite().setTexture(weapon.slash(isRight,timeSinceAttack(time)));
			}
			
			else 
				isAttacking = false;
		}
	}
	
	
	public float getLastTimeAttack() {
		return lastTimeAttack;
	}
	
	public float timeSinceAttack(float time) {
		return time - lastTimeAttack;
	}

	public void setLastTimeAttack(float lastTimeAttack) {
		this.lastTimeAttack = lastTimeAttack;
	}

	// Adds a texture to the RunningLeft animation sequence
	public void addToRunningLeft(int texturesIndex, int animationIndex) {
		
		Texture[] arr = new Texture[runningLeft.getKeyFrames().length+1];
		
		for(int i = 0, k = 0; i < runningLeft.getKeyFrames().length+1; i++) {
			if(i == animationIndex) {
				arr[i] = this.getTextures().get(texturesIndex);
			}
			else {
				arr[i] = this.runningLeft.getKeyFrames()[k];
				k++;
			}
			
		}
		
		runningLeft = new Animation<Texture>(0.5f,arr);

	}

	// Destroys/Releases all object references sets primitive to default values
	// calls the dispose method of any objects which releases their resources
	public void dispose() {
		
		setHealthPoints(0);
		setName("");
		this.getSprite().getTexture().dispose();
		
		weapon.dispose();
		
		if(runningRight != null) {
			for(int i = 0; i < runningRight.getKeyFrames().length; i++) {
				runningRight.getKeyFrames()[i].dispose();
			}
		}
		
		if(runningLeft != null) {
			for(int i = 0; i < runningLeft.getKeyFrames().length; i++) {
				runningLeft.getKeyFrames()[i].dispose();
			}
		}
		
		if(jumping != null) {
			for(int i = 0; i < jumping.getKeyFrames().length; i++) {
				jumping.getKeyFrames()[i].dispose();
			}
		}
		
		if(attackingRight != null) {
			for(int i = 0; i < attackingRight.getKeyFrames().length; i++) {
				attackingRight.getKeyFrames()[i].dispose();
			}
		}
		
		if(this.getTextures() != null) {
			for(int i = 0; i < this.getTextures().size(); i++) {
				this.getTextures().get(i).dispose();
			}
			this.getTextures().clear();
		}
	}

}
