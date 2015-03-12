
package card.player;

import java.util.Stack;

import card.Deck;

/**
 * Represents the discard pile of the game. It is needed due to some actions
 * indicated on, say, green player cards.
 */
public class DiscardPile extends Deck<GreenPlayerCard> {

	public DiscardPile() {
		super.cards = new Stack<GreenPlayerCard>();
	}
	
	@Override
	public void populateDeck() {
		super.cards = new Stack<GreenPlayerCard>();
	}
	
	public void addCard(GreenPlayerCard card) {
		super.cards.push(card);
	}

}
