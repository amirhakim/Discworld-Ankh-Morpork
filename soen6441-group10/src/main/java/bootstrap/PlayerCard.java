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
	
	

	/* (non-Javadoc)
	 * @see bootstrap.Card#getTitle()
	 */
	public String getTitle() {
		return null;
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see bootstrap.Card#setTitle()
	 */
	public void setTitle() {
		// TODO Auto-generated method stub

	}

	public void setTitle(String title) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Card clone() {
		return this.clone();
	}

}
