// Matthew A. Padilla
// OnlyWar: Entity Class


package com.onlywar.game;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Entity {
	
	private ArrayList<Texture> entityTextures;
	private Sprite entitySprite;
	protected float speed;
	private String name;
	
	
	

	public Entity(ArrayList<Texture> arr, float speed, String name) {
		
		entityTextures = new ArrayList<Texture>();
		
		for(int i = 0; i < arr.size(); i++) {
			this.entityTextures.add(arr.get(i));
		}
		
		entitySprite = new Sprite(this.entityTextures.get(0));
		
		this.speed = speed;
		this.setName(name);
		
	}

	public Entity(Texture tex, float speed, String name) {
		
		entityTextures = null;
		
		entitySprite = new Sprite(tex);
		
		this.speed = speed;
		this.setName(name);
		
	}
	
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public float getSpeed() {
		return this.speed;
	}
	
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
	public Sprite getSprite() {
		
		return entitySprite;
	}
	
	public void setSprite(Sprite spr) {
		
		this.entitySprite.set(spr);
	}
	
	public ArrayList<Texture> getTextures() {
		
		return entityTextures;
	}
	
	
	public void setTextures(ArrayList<Texture> newTextures) {
		this.entityTextures = newTextures;
	}
	
	public float getX() {
		return this.getSprite().getX();
	}
	
	public void setX(float x) {
		this.getSprite().setX(x);
	}
	
	public float getY() {
		return this.getSprite().getY();
	}
	
	public void setY(float y) {
		this.getSprite().setY(y);
	}
	
	public float distX(Entity other) {
		if(this.getX() < other.getX())
			return Math.abs(this.getX() - other.getX()) - this.getSprite().getRegionWidth();
		else if(this.getX() > other.getX())
			return Math.abs(this.getX() - other.getX()) - other.getSprite().getRegionWidth();
		else
			return 0.0f;
	}
	
	// Creates an Animation<Texture> from an entities available Textures
	public Animation<Texture> createAnimation(int first, int last, float frameDuration) {
		Texture[] arr = new Texture[last-first];
		for(int i = first; i < last; i++) {
			arr[i-first] = getTextures().get(i);
		}
		
		return new Animation<Texture>(frameDuration,arr);
	}
	
	// Adds a texture to the RunningLeft animation sequence
		public Animation<Texture> addTextureToAnimation(Animation<Texture> ani, int texturesIndex, int animationIndex) {
			
			Texture[] arr = new Texture[ani.getKeyFrames().length+1];
			
			for(int i = 0, k = 0; i < ani.getKeyFrames().length+1; i++) {
				if(i == animationIndex) {
					arr[i] = this.getTextures().get(texturesIndex);
				}
				else {
					arr[i] = ani.getKeyFrames()[k];
					k++;
				}
				
			}
			return new Animation<Texture>(ani.getFrameDuration(), arr);

		}
	
	public static boolean collision(Entity one, Entity two) {
		boolean collideX = false;
		boolean collideY = false;
		if(one.getX() + one.getSprite().getRegionWidth() >= two.getX() && one.getX() + one.getSprite().getRegionWidth()/2 <= two.getX() + two.getSprite().getRegionWidth()) {
			collideX = true;
		}
		
		else if(two.getX() + two.getSprite().getRegionWidth() >= one.getX()  && two.getX() + two.getSprite().getRegionWidth() <= one.getX() + one.getSprite().getRegionWidth()) {
			collideX = true;
		}
		
		if(one.getY() + one.getSprite().getRegionHeight() >= two.getY() && one.getY() + one.getSprite().getRegionHeight() <= two.getY() + two.getSprite().getRegionHeight()) {
			collideY = true;
		}
		
		else if(two.getY() + two.getSprite().getRegionHeight() >= one.getY() && two.getY() + two.getSprite().getRegionHeight() <= one.getY() + one.getSprite().getRegionHeight()) {
			collideY = true;
		}
		
		
		return (collideX && collideY);
	}
	
	public void moveLeft() {}
	
	public void moveRight() {}
	
	public void moveUp() {}
	
	public void moveDown() {}
	
	

	
	

}
