
package card.random;

import java.util.Stack;

import card.Deck;

/**
 * This class implements deck of random event cards of the game. 
 * 
 * @author Team 10 - SOEN6441
 * @version 1.0
 */
public class RandomEventDeck extends Deck<RandomEventCard> {

	
	private final int size = 12;

	/**
	 *  Array of all card possibilities
	 */
	private final String[] names = { "The Dragon", "Flood", "Fire", "Fog",
			"Riots", "Explosion", "Mysterious Murders",
			"Demons from the Dungeon Dimensions", "Subsidence",
			"Bloody Stupid Johnson", "Trolls", "Earthquake" };

	/**
	 * This constructor is invoked from the class RandomEventDeck.
	 */
	public RandomEventDeck() {
		populateDeck();
	}

	/**
	 * This method overrides "populateDeck" of super class Deck and
	 *  puts all the random event cards in the deck and shuffle it.
	 */
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
