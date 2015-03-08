
package card.random;

import java.util.Stack;

import card.Deck;

/**
 * This class implements the deck of Random Event cards.
 * 
 * @author Team 10 - SOEN6441
 * @version 1.0
 */
public class RandomEventDeck extends Deck<RandomEventCard> {
	
	public RandomEventDeck() {
		populateDeck();
	}

	/**
	 * This method overrides "populateDeck" of super class Deck and
	 * puts all the random event cards in the deck and shuffle it.
	 */
	@Override
	public void populateDeck() {
		cards = new Stack<RandomEventCard>();

		// Initialize each card with a title
		for (RandomEventCard c : RandomEventCard.values()) {
			this.cards.add(c);
		}

		super.shuffle();
	}

}
