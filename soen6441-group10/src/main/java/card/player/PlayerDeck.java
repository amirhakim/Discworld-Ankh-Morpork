
package card.player;

import java.util.Stack;

import card.Deck;

/**
 * This class implements deck of player cards of the game. 
 * 
 * @author Team 10 - SOEN6441
 * @version 1.0
 */
public class PlayerDeck extends Deck<GreenPlayerCard> {

	/**
	 * This constructor is invoked to create objects from the class PlayerDeck.
	 */
	public PlayerDeck() {
		super.cards = new Stack<GreenPlayerCard>();
		populateDeck();
	}

	/**
	 * This method overrides "populateDeck" of super class Deck and
	 * puts all the player cards in the deck and shuffles it.
	 */
	@Override
	public void populateDeck() {

		// TODO: Add the brown cards here whenever appropriate
		for (GreenPlayerCard p : GreenPlayerCard.values()) {
			super.cards.add(p);
		}

		super.shuffle();
	}

}
