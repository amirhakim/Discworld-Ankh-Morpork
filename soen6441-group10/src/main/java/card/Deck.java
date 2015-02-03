package card;

import java.util.Collections;
import java.util.Optional;
import java.util.Stack;

public abstract class Deck<C extends Card> {

	protected Stack<C> cards;
	
	/**
	 * Draw a card if the deck has any cards at all.
	 */
	public Optional<C> drawCard() {
		return !cards.isEmpty() ? Optional.of(cards.pop()) : Optional.empty();
	}
	
	/**
	 * Shuffle the deck.
	 */
	public void shuffle() {
		Collections.shuffle(cards);
	}
	
	/**
	 * Get the number of cards in the deck.
	 */
	public int size() {
		return cards.size();
	}
	
	public abstract void populateDeck();
	
}
