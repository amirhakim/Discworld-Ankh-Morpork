/**
 * @File
 * Class continaing all personality cards
 */

package card;

import java.util.Stack;

public class PersonalityDeck extends Deck<PersonalityCard> {

	private final int size = 5;

	// Array of all card posibilities
	private final String[] names = { "Lord Selachii", "Chrysoprase",
			"Lord Vetinaari", "Dragon King of Arms", "Commander Vimes" };

	public PersonalityDeck() {
		populateDeck();
	}

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