// Matthew A. Padilla
// OnlyWar: Projectile Class


package com.onlywar.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import java.util.ArrayList;

public class Projectile extends Entity {
	
	private boolean isVisible;
	
	private float lifeTime;
	
	private float timeAlive;

	public Projectile(ArrayList<Texture> tex, float speed, String name, float lT) {
		super(tex, speed, name);
		
		setX(0);
		setY(0);
		setVisible(false);
		lifeTime = lT;
		timeAlive = 0;
		
		
	}
	
	
	public boolean isVisible() {
		return isVisible;
	}


	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
	
	public void moveRight(float deltaX) {
		this.setX(this.getX() + deltaX);
	}
	
	
	public void moveRight() {
		this.setX(this.getX() + getSpeed()*Gdx.graphics.getDeltaTime());
	}
	
	public void moveLeft(float deltaX) {
		this.setX(this.getX() - deltaX);
	}
	
	public void moveLeft() {
		this.setX(this.getX() - getSpeed()*Gdx.graphics.getDeltaTime());
	}	
	
	public void moveUp(float deltaY) {
		this.setY(this.getY() + deltaY);
	}
	
	public void moveUp() {
		this.setY(this.getY() + getSpeed()*Gdx.graphics.getDeltaTime());
	}
	
	public void moveDown(float deltaY) {
		this.setY(this.getY() - deltaY);
	}
	
	public void moveDown() {
		this.setY(this.getY() - getSpeed()*Gdx.graphics.getDeltaTime());
	}
	
	public void setLifeTime(float time) {
		lifeTime = time;
	}
	
	public float getLifeTime() {
		return this.lifeTime;
	}
	
	public void progressTimeAlive() {
		timeAlive += Gdx.graphics.getDeltaTime();
	}
	
	public float getTimeAlive() {
		return timeAlive;
	}
	
	public void setTimeAlive(float time) {
		timeAlive = time;
	}
	
	public void dispose() {
		setVisible(false);
		
		lifeTime = 0;
		
		timeAlive = 0;
		
		for(int i = 0; i < this.getTextures().size(); i++) {
			this.getTextures().get(i).dispose();
		}
		
		this.getTextures().clear();
		this.getSprite().getTexture().dispose();
	}
	

}
