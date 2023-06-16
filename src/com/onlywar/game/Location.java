// Matthew A. Padilla
// OnlyWar: Location Class


package com.onlywar.game;

//import java.util.ArrayList;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.Disposable;
import java.util.ArrayList;
import com.badlogic.gdx.graphics.g2d.BitmapFont;


//import com.badlogic.gdx.physics.box2d.*;

public class Location {
	
	// Boundaries of the location
	private float width, height;
	
	// Camera: controls what the player sees
	private OrthographicCamera view;
	
	// Sprites for the locations background and foreground
	private Sprite background, middleground;
	
	// Spritebatch for all sprites loaded in this location
	private SpriteBatch batch;
	
	// Acceleration of gravity
	private float gravity;
	
	private String name;
	
	private UI locationUI;
	
	private float groundLevel;
	
	// Creates a location with its name,width, height, gravity, background, and foreground sprites
	public Location(String name, float width, float height, float gravity, Sprite back, Sprite mid) {
		
		this.setWidth(width);
		this.setHeight(height);
		setBackground(back);
		setForeground(mid);
		view = new OrthographicCamera();
		view.setToOrtho(false, width, height);
		
		this.gravity = gravity;
		
		batch = new SpriteBatch();

		
	}

	public Location(String name, float width, float height, float gravity) {
		this.setWidth(width);
		this.setHeight(height);
		view = new OrthographicCamera();
		view.setToOrtho(false, width, height);
		
		this.gravity = gravity;
		
		batch = new SpriteBatch();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public Sprite getBackground() {
		return background;
	}

	public void setBackground(Sprite background) {
		this.background = background;
	}

	public Sprite getMiddleground() {
		return middleground;
	}

	public void setForeground(Sprite foreground) {
		this.middleground = foreground;
	}

	public UI getLocationUI() {
		return locationUI;
	}

	public void setLocationUI(UI locationUI) {
		this.locationUI = locationUI;
	}
	
	public void createLocationUI(ArrayList<Sprite> textSprites, String texts[]) {
		
		ArrayList<BitmapFont> bitmaps = new ArrayList<BitmapFont>();
		for(int i = 0; i < texts.length; i++) {
			bitmaps.add(new BitmapFont());
		}
		
		locationUI = new UI(this.batch, textSprites, bitmaps, texts);
	}

	public float getGravity() {
		return this.gravity;
	}

	public void setGravity(float gravity) {
		this.gravity = gravity;
	}
	
	public float getGroundLevel() {
		return groundLevel;
	}

	public void setGroundLevel(float groundLevel) {
		this.groundLevel = groundLevel;
	}

	public void batchStart() {
		batch.setProjectionMatrix(view.combined);
		batch.begin();
	}
	
	public void batchEnd() {
		batch.end();
	}
	
	public void batchDraw(Texture tex, float x, float y) {
		this.batch.draw(tex, x, y);
	}
	
	public void batchDraw(Sprite spr) {
		this.batch.draw(spr.getTexture(), spr.getX(), spr.getY());
	}
	
	public void batchDraw(Entity ent) {
		this.batch.draw(ent.getSprite().getTexture(), ent.getX(), ent.getY());
	}
	
	public OrthographicCamera getView() {
		return view;
	
	}
	
	public void setView(OrthographicCamera view) {
		this.view = view;
	}
	
	
	public void dispose() {
		name = "";
		width = 0;
		height = 0; 
		gravity = 0;
		
		locationUI.dispose();
		
		batch.dispose();
		if(middleground != null)
			middleground.getTexture().dispose();
		if(background != null)
			background.getTexture().dispose(); 
		
	}
	
}