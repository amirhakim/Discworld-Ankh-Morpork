
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
		super.cards = new Stack<RandomEventCard>();
		populateDeck();
	}

	@Override
	public void populateDeck() {

		// Initialize each card with a title
		for (RandomEventCard c : RandomEventCard.values()) {
			this.cards.add(c);
		}

		super.shuffle();
	}

}
