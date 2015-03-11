
package card.player;

import java.util.Stack;

import card.Deck;

/**
 * This class implements deck of player cards of the game. 
 * 
 * @author Team 10 - SOEN6441
 * @version 1.0
 */
public class DiscardPile extends Deck<GreenPlayerCard> {

	public DiscardPile() {
		super.cards = new Stack<GreenPlayerCard>();
	}
	
	public void populateDeck() {
		// Populating a discard pile really means emptying
		super.cards = new Stack<GreenPlayerCard>();
	}
	
	public void addCard(GreenPlayerCard card) {
		cards.push(card);
	}

	

}
