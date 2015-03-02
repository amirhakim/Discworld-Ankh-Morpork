
package card.random;

import card.Card;
import gameplay.Game;

/**
 * <b> This class implements the Random Event cards of the game with their related rules<b>
 * 
 * @author Team 10 - SOEN6441
 * @version 1.0
 */
public class RandomEventCard implements Card {

	
	private String title;

	/**
	 * This constructor is invoked to create objects from the class RandomEventCard.
	 */
	public RandomEventCard() {}

	/**
	 * This method implements getTitle method of interface Card. 
	 * Get title of Random Event card.
	 */
	@Override
	public String getTitle() {
			return this.title;
	}

	/**
	 * This method implements setTitle method of interface Card. 
	 * Set title of Random Event card.
	 */
	@Override
	public void setTitle(String title) {
		this.title=title;
	}
	
	public void performAction(Game game) {
		
	}

}

