
package card;

import java.util.Stack;

import util.Color;


/**
 * This class implements deck of player cards of the game. 
 * 
 * @author Team 10 - SOEN6441
 * @version 1.0
 */
public class PlayerDeck extends Deck<PlayerCard> {

	private final int GREEN_CARD_AMOUNT = 48;
	private final int BROWN_CARD_AMOUNT = 53;

	/**
	 * This constructor is invoked to create objects from the class PlayerDeck.
	 */
	public PlayerDeck() {
		super.cards = new Stack<PlayerCard>();
		populateDeck();
	}

	/**
	 * This method overrides "populateDeck" of super class Deck and
	 *  puts all the player cards in the deck and shuffle it.
	 */
	@Override
	public void populateDeck() {

		for (int i = 0; i < BROWN_CARD_AMOUNT; ++i) {
			super.cards.add(new PlayerCard("Brown card number " + Integer.toString(i),
					Color.BROWN));
		}

		for (int i = 0; i < GREEN_CARD_AMOUNT; ++i) {
			super.cards.add(new PlayerCard("Green card number " + Integer.toString(i),
					Color.GREEN));
		}

		super.shuffle();
	}

}
