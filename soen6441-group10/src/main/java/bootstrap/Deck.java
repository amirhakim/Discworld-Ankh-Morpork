/**
 * @File
 * Deck class continaing methods to all decks of cards in game
 */

package bootstrap;

import java.util.ArrayList;
import java.util.Collections;


public class Deck {
	protected ArrayList<Card> cards;
	
	/*
	 * Pop top item in deck
	 */
	public Card pop() {
		return this.cards.remove(this.cards.size() - 1);
	}
	
	/*
	 * Randomize decks order
	 */
	public void shuffle() {
		Collections.shuffle(this.cards);
	}
	
	/*
	 * Get size of deck
	 */
	public int size() {
		return cards.size();
	}
	
	
}
