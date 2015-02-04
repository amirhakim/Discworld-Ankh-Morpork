
package card;

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
	public RandomEventCard() {
	}

	/**
	 * This method implements getTitle method of interface Card. 
	 * It gets title of Random Event card.
	 */
	public String getTitle() {
			return this.title;
	}

	/**
	 * This method implements setTitle method of interface Card. 
	 * It sets title of Random Event card.
	 */
	public void setTitle(String title) {
		this.title=title;
	}

}

