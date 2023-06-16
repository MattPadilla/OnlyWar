// Matthew A. Padilla
// OnlyWar: Location Class


package com.onlywar.game;

//import java.util.ArrayList;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;


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
	
	private boolean activeLocation;
	
	// Creates a location with its name, width, height, gravity, background, and foreground sprites
	public Location(boolean active, String name, float width, float height, float gravity, Sprite back, Sprite mid) {
		
		setActiveLocation(active);
		this.setWidth(width);
		this.setHeight(height);
		setBackground(back);
		setForeground(mid);
		view = new OrthographicCamera();
		view.setToOrtho(false, width, height);
		
		this.gravity = gravity;
		
		batch = new SpriteBatch();

		
	}

	// Creates a location with its name, width, height, and gravity
	public Location(String name, float width, float height, float gravity) {
		this.setWidth(width);
		this.setHeight(height);
		view = new OrthographicCamera();
		view.setToOrtho(false, width, height);
		
		this.gravity = gravity;
		
		batch = new SpriteBatch();
	}

	// returns true if this Location is active and meant to be drawn to the screen
	public boolean isActiveLocation() {
		return activeLocation;
	}

	// sets if a Location is active
	public void setActiveLocation(boolean activeLocation) {
		this.activeLocation = activeLocation;
	}

	// Getter for name
	public String getName() {
		return name;
	}

	// Setter for name
	public void setName(String name) {
		this.name = name;
	}

	// Getter for width
	public float getWidth() {
		return width;
	}

	// Setter for name
	public void setWidth(float width) {
		this.width = width;
	}

	// Getter for height
	public float getHeight() {
		return height;
	}

	// Setter for height
	public void setHeight(float height) {
		this.height = height;
	}

	// Getter for background sprite
	public Sprite getBackground() {
		return background;
	}

	// Setter for background sprite
	public void setBackground(Sprite background) {
		this.background = background;
	}

	// Getter for middleground sprite
	public Sprite getMiddleground() {
		return middleground;
	}

	// Setter for middleground sprite
	public void setForeground(Sprite foreground) {
		this.middleground = foreground;
	}

	// Getter for UI
	public UI getLocationUI() {
		return locationUI;
	}

	// Setter for UI
	public void setLocationUI(UI locationUI) {
		this.locationUI = locationUI;
	}
	
	// Creates a UI using new
	public void createLocationUI() {
		
		locationUI = new UI(this.batch);
	}

	// Getter for Gravity
	public float getGravity() {
		return this.gravity;
	}

	// Setter for Gravity
	public void setGravity(float gravity) {
		this.gravity = gravity;
	}
	
	// Getter for GroundLevel
	public float getGroundLevel() {
		return groundLevel;
	}

	// Setter for Gravity
	public void setGroundLevel(float groundLevel) {
		this.groundLevel = groundLevel;
	}

	// Starts the batch preparing to draw textures and 
	// only prints object inside the camera
	public void batchStart() {
		batch.setProjectionMatrix(view.combined);
		batch.begin();
	}
	
	// Ends the batch which means this object will no longer draw to the screen this way
	public void batchEnd() {
		batch.end();
	}
	
	// draws a texture at specific x and y values
	public void batchDraw(Texture tex, float x, float y) {
		this.batch.draw(tex, x, y);
	}
	
	// draws a sprite
	public void batchDraw(Sprite spr) {
		this.batch.draw(spr.getTexture(), spr.getX(), spr.getY());
	}
	
	// draws an entity
	public void batchDraw(Entity ent) {
		this.batch.draw(ent.getSprite().getTexture(), ent.getX(), ent.getY());
	}
	
	// Getter for the OrthographicCamera
	public OrthographicCamera getView() {
		return view;
	
	}
	
	// Setter for the OrthographicCamera
	public void setView(OrthographicCamera view) {
		this.view = view;
	}
	
	
	// destroys objects and sets primitives to null
	public void dispose() {
		name = "";
		width = 0;
		height = 0; 
		gravity = 0;
		if(locationUI != null)
			locationUI.dispose();
		
		if(batch != null)
			batch.dispose();
		if(middleground != null)
			middleground.getTexture().dispose();
		if(background != null)
			background.getTexture().dispose(); 
		
	}
	
}
