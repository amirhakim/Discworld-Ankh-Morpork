/**
 * This class implements deck of personality cards of the game. 
 * 
 * @author Team 10 - SOEN6441
 * @version 1.0
 */
package card;

import java.util.Stack;

public class PersonalityDeck extends Deck<PersonalityCard> {

	private final int size = 5;

	/**
	 *  Array of all card possibilities
	 */
	private final String[] names = { "Lord Selachii", "Chrysoprase",
			"Lord Vetinaari", "Dragon King of Arms", "Commander Vimes" };

	/**
	 * This constructor is invoked from the class PersonalityDeck.
	 */
	public PersonalityDeck() {
		populateDeck();
	}
	
	/**
	 * This method overrides "populateDeck" of super class Deck and
	 *  puts all the personality cards in the deck and shuffle it.
	 */
	@Override
	public void populateDeck() {
		this.cards = new Stack<PersonalityCard>();

		// Initialize each card with a title
		for (int i = 0; i < this.size; ++i) {
			PersonalityCard tmp = new PersonalityCard();
			tmp.setTitle(this.names[i]);
			this.cards.add(tmp);
		}

		super.shuffle();
	}

	
	
}
