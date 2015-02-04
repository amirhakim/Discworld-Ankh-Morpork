/**
 * @File
 * Class continaing all personality cards
 */

package card;

import java.util.Stack;

import util.Color;

public class PlayerDeck extends Deck<PlayerCard> {

	private final int GREEN_CARD_AMOUNT = 48;
	private final int BROWN_CARD_AMOUNT = 53;

	public PlayerDeck() {
		super.cards = new Stack<PlayerCard>();
		populateDeck();
	}

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
