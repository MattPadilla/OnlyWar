// Matthew A. Padilla
// OnlyWar: User Interface Class


package com.onlywar.game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
public class UI {
	
	private ArrayList<Sprite> textSprites;
	private ArrayList<BitmapFont> texts;
	private String strings[];
	private SpriteBatch textBatch;
	
	public UI(SpriteBatch textBatch, ArrayList<Sprite> sprites, ArrayList<BitmapFont> texts, String strings[]) {
		
		this.textBatch = textBatch;
		this.textSprites = sprites;
		this.texts = texts;
		this.strings = strings;
	}
	

	public void setTextBatch(SpriteBatch textBatch) {
		this.textBatch = textBatch;
	}

	public ArrayList<Sprite> getSprites() {
		return this.textSprites;
	}
	
	public void setSprites(ArrayList<Sprite> sprites) {
		this.textSprites = sprites;
	}
	
	public ArrayList<BitmapFont> getTexts() {
		return this.texts;
	}
	
	public void setTexts(ArrayList<Sprite> sprites) {
		this.textSprites = sprites;
	}
	
	public void drawText(float xValues[], float yValues[]) {
		
		textBatch.begin();
		for(int i = 0; i < texts.size(); i++) {
			texts.get(i).draw(textBatch, strings[i], xValues[i] , yValues[i]);
		}
		textBatch.end();
		
	}
	
	public void dispose() {
		
		
		if(textSprites != null) {
			for(int i = 0; i < textSprites.size(); i++) {
				textSprites.get(i).getTexture().dispose();
			}
			textSprites.clear();
		}
		
		if(texts != null) {
			for(int i = 0; i < texts.size(); i++) {
				texts.get(i).dispose();
			}
			texts.clear();
		}
		
		if(strings != null) {
			for(int i = 0; i < strings.length; i++) {
				strings[i] = "";
			}
		}

	}
	
	
	
	
	

}
