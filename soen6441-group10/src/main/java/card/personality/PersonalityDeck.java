/**
 * This class implements deck of personality cards of the game. 
 * 
 * @author Team 10 - SOEN6441
 * @version 1.0
 */
package card.personality;

import java.util.Stack;

import card.Deck;

public class PersonalityDeck extends Deck<PersonalityCard> {

	public PersonalityDeck() {
		populateDeck();
	}
	
	@Override
	public void populateDeck() {
		this.cards = new Stack<PersonalityCard>();

		// There is a special rule for not dealing Chrysoprase in the case
		// of 2 players but this will be handled on the client side
		for (PersonalityCard c : PersonalityCard.values()) {
			this.cards.add(c);
		}

		super.shuffle();
	}

}
