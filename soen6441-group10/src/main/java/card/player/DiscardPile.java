
package card.player;

import java.util.Stack;

import card.Deck;

/**
 *<b>Represents the discard pile of the game. It is needed due to some actions
 * indicated on, say, green player cards.</b>
 * 
 * @author Team 10 - SOEN6441
 * @version 2.0
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
