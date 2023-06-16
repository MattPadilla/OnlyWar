// Matthew A. Padilla
// OnlyWar: Weapon Class


package com.onlywar.game;

import java.util.ArrayList;
 
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.Animation;

public class Weapon extends Entity {
	
	private Animation<Texture> slashingRight;
	private Animation<Texture> slashingLeft;

	public Weapon(ArrayList<Texture> arr, float speed, String name) {
		super(arr, speed, name);
	}

	public Animation<Texture> getSlashingRight() {
		return slashingRight;
	}

	public void setSlashingRight(Animation<Texture> slashingRight) {
		this.slashingRight = slashingRight;
	}

	public Animation<Texture> getSlashingLeft() {
		return slashingLeft;
	}

	public void setSlashingLeft(Animation<Texture> slashingLeft) {
		this.slashingLeft = slashingLeft;
	}
	
	public Texture slash(boolean isRight, float time) {
		if(isRight) 
			return slashingRight.getKeyFrame(time, true);
		
		else
			return slashingLeft.getKeyFrame(time, true);
			
	}
	
	public void dispose() {
		setName("");
		this.getSprite().getTexture().dispose();
		
		if(slashingRight != null) {
			for(int i = 0; i < slashingRight.getKeyFrames().length; i++) {
				slashingRight.getKeyFrames()[i].dispose();
			}
		}
		
		if(slashingLeft != null) {
			for(int i = 0; i < slashingLeft.getKeyFrames().length; i++) {
				slashingLeft.getKeyFrames()[i].dispose();
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
