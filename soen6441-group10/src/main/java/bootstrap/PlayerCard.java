/**
 * 
 */
package bootstrap;

/**
 * @author Amir Hakim Jan-25th-2015
 * This document is a part of the source code and related artifacts
 * got SOEN6441 group10
 * this class is the interface for all the card types that will be required in the game, 
 * every card class MUST implement it
 */
public class PlayerCard implements Card {
	
	private int color; //brown 0, green 1
	private String title;
	
	
	

	/* (non-Javadoc)
	 * @see bootstrap.Card#getTitle()
	 */
	public String getTitle() {
		return this.title;
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see bootstrap.Card#setTitle()
	 */
	public void setTitle(String title) {
		this.title = title;
		// TODO Auto-generated method stub

	}

	public void setColor(int color) {
		this.color = color;
	}
	
	public int getColor() {
		return this.color;
	}
	
	@Override
	public Card clone() {
		return new PlayerCard();
	}

}
