/**
 * 
 */
package card;

import java.util.Stack;

/**
 * @author Amir
 *
 */
public class RandomEventDeck extends Deck<RandomEventCard> {

	/**
	 * 
	 */
	private final int size = 12;

	// Array of all card posibilities
	private final String[] names = { "The Dragon", "Flood", "Fire", "Fog",
			"Riots", "Explosion", "Mysterious Murders",
			"Demons from the Dungeon Dimensions", "Subsidence",
			"Bloody Stupid Johnson", "Trolls", "Earthquake" };

	public RandomEventDeck() {
		populateDeck();
	}

	@Override
	public void populateDeck() {
		cards = new Stack<RandomEventCard>();

		// Initialize each card with a title
		for (int i = 0; i < this.size; ++i) {
			RandomEventCard tmp = new RandomEventCard();
			tmp.setTitle(this.names[i]);
			this.cards.add(tmp);
		}

		super.shuffle();
	}

}
