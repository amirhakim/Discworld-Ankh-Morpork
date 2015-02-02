/**
 * @File
 * Class continaing all personality cards
 */

package bootstrap;

import java.util.Collections;
import java.util.Stack;

public class PlayerDeck extends Deck<PlayerCard> {

	final int numGreenCards = 48;
	final int numBrownCards = 53;
	final int brown = 0;
	final int green = 1;

	public PlayerDeck() {
		this.populateDeck();
	}

	@Override
	public void populateDeck() {

		this.cards = new Stack<PlayerCard>();

		for (int i = 0; i < this.numBrownCards; ++i) {
			PlayerCard tmp = new PlayerCard();
			tmp.setTitle("Brown card number " + Integer.toString(i));
			tmp.setColor(0);
			this.cards.add(tmp);
		}

		super.shuffle();

		Stack<PlayerCard> subDeck = new Stack<PlayerCard>();
		for (int i = 0; i < this.numGreenCards; ++i) {
			PlayerCard tmp = new PlayerCard();
			tmp.setTitle("Green card number " + String.valueOf(i));
			tmp.setColor(1);
			subDeck.add(tmp);
		}

		Collections.shuffle(subDeck);

		this.cards.addAll(subDeck);
	}

}
