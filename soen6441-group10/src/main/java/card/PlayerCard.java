
package card;

import util.Color;

/**
 * <b> This class implements the player cards of the game.<b>
 * 
 * @author Team 10 - SOEN6441
 * @version 1.0
 */
public class PlayerCard implements Card {
	
	private int color; //brown 0, green 1
	private String title;
	
	public PlayerCard(String string, Color clr) {
		// TODO Auto-generated constructor stub
		this.color= clr.getColorCode() ;
		this.title= string;
	}

	public PlayerCard() {
		// TODO Auto-generated constructor stub
		this.color=-1;
		this.title=null;
	}

	/**
	 * This method implements getTitle method of interface Card. 
	 * It gets title of player card.
	 */	
		public String getTitle() {
		return this.title;
	}

	/**
	 * This method implements setTitle method of interface Card. 
	 * It sets title of player card.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * This method sets the colors of player cards: 0 for brown & 1 for green.
	 * @param color the color of the player card
	 */
	public void setColor(int color) {
		this.color = color;
	}
	
	/**
	 * This method gets the colors of player cards: 0 for brown & 1 for green.
	 * @return the color of the player card
	 */
	public int getColor() {
		return this.color;
	}
	
	
	@Override
	public Card clone() {
		return new PlayerCard();
	}

}

