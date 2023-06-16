package com.onlywar.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class UIElement {

	private Sprite sprite;
	private BitmapFont words;
	private String str;
	private float stringX;
	private float stringY;
	
	
	public UIElement(Sprite sprite, float stringX, float stringY, String str, BitmapFont words) {
		this.sprite = sprite;
		this.stringX = stringX;
		this.stringY = stringY;
		this.str = str;
		this.words = words;
	}


	public Sprite getSprite() {
		return sprite;
	}


	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}


	public BitmapFont getWords() {
		return words;
	}


	public void setWords(BitmapFont words) {
		this.words = words;
	}


	public String getStr() {
		return str;
	}


	public void setStr(String str) {
		this.str = str;
	}


	public float getStringX() {
		return stringX;
	}


	public void setStringX(float stringX) {
		this.stringX = stringX;
	}


	public float getStringY() {
		return stringY;
	}


	public void setStringY(float stringY) {
		this.stringY = stringY;
	}
	
	public void dispose() {
		if(sprite != null) 
			sprite.getTexture().dispose();
		if(words != null)
			words.dispose();
		str = "";
		stringX = 0;
		stringY = 0;
	}
	
}
