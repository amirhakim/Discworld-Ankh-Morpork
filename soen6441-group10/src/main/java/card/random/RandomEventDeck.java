
package card.random;

import java.util.Stack;

import card.Deck;

/**
 * <b>This class implements the deck of Random Event cards.</b>
 * 
 * @author Team 10 - SOEN6441
 * @version 2.0
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
