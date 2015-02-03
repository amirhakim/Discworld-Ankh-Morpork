/**
 * 
 */
package card;

import util.Color;

/**
 * @author Amir Hakim Jan-25th-2015
 * This document is a part of the source code and related artifacts
 * got SOEN6441 group10
 * this class is the interface for all the card types that will be required in the game, 
 * every card class MUST implement it
 */
public class PlayerCard implements Card {
	
	private Color color;
	private String title;
	
	public PlayerCard(){}
	
	public PlayerCard(String title_, Color color_){
		title = title_;
		color = color_;
	}
	
	@Override
	public String getTitle() {
		return this.title;
	}

	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	public void setColor(Color color_) {
		color = color_;
	}
	
	public Color getColor() {
		return color;
	}
	
	@Override
	public Card clone() {
		return new PlayerCard();
	}

}
