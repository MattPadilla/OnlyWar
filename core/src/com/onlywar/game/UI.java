// Matthew A. Padilla
// OnlyWar: User Interface Class


package com.onlywar.game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
public class UI {
	
	private ArrayList<UIElement> elements;
	private SpriteBatch textBatch;
	
public UI(SpriteBatch textBatch) {
		
		this.textBatch = textBatch;
		this.elements = new ArrayList<UIElement>();
	}
	
	public UI(SpriteBatch textBatch, ArrayList<UIElement> elements) {
		
		this.textBatch = textBatch;
		this.elements = elements;
	}
	

	public void setTextBatch(SpriteBatch textBatch) {
		this.textBatch = textBatch;
	}

	public ArrayList<UIElement> getElements() {
		return this.elements;
	}
	
	public void setElements(ArrayList<UIElement> elements) {
		this.elements = elements;
	}
	
	
	public void drawElements() {
		
		textBatch.begin();
		
		for(int i = 0; i < elements.size(); i++) {
			if(elements.get(i).getSprite() != null)
				textBatch.draw(elements.get(i).getSprite().getTexture(), elements.get(i).getSprite().getX(), elements.get(i).getSprite().getY());
			elements.get(i).getWords().draw(textBatch, elements.get(i).getStr(), elements.get(i).getStringX() , elements.get(i).getStringY());
		}
		
		textBatch.end();
		
	}
	
	public void dispose() {
		
		
		if(elements != null) {
			for(int i = 0; i < elements.size(); i++) {
				if(elements.get(i) != null)
					elements.get(i).dispose();
			}
			elements.clear();
		}
		
		

	}
	
	
	
	
	

}
