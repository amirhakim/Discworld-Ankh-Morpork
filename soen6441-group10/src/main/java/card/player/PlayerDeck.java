
package card.player;

import java.util.Stack;

import card.Deck;

/**
 * <b>This class implements deck of player cards of the game. </b>
 * 
 * @author Team 10 - SOEN6441
 * @version 2.0
  */
public class PlayerDeck extends Deck<GreenPlayerCard> {

	public PlayerDeck() {
		super.cards = new Stack<GreenPlayerCard>();
		populateDeck();
	}

	@Override
	public void populateDeck() {

		// TODO: Add the brown cards here whenever appropriate
		for (GreenPlayerCard p : GreenPlayerCard.values()) {
			super.cards.add(p);
		}

		super.shuffle();
	}

}
