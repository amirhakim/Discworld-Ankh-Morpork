/**
 * @File
 * Class continaing all personality cards
 */

package bootstrap;

import java.util.ArrayList;
import java.util.Collections;

public class PlayerDeck extends Deck{
	
	final int numGreenCards = 48;
	final int numBrownCards = 53;
	final int brown = 0;
	final int green = 1;
	
	
	
	public PlayerDeck() {
		// TODO Auto-generated constructor stub
		this.deckSize=this.numGreenCards + this.numBrownCards;
		this.populateDeck();
	}


	@Override
	public void populateDeck() {
		
		this.cards = new ArrayList<Card>();
		
		//arraylist will act as a stack brown on bottom, green on top
		for(int i=0;i<this.numBrownCards; ++i) {
			PlayerCard tmp = new PlayerCard();
			tmp.setTitle("Brown card number " + Integer.toString(i));
			tmp.setColor(0);
			this.cards.add(tmp);
		}

		super.shuffle();
		
		ArrayList<Card> subDeck = new ArrayList<Card>();
		for(int i=0; i<this.numGreenCards; ++i) {
			PlayerCard tmp = new PlayerCard();
			tmp.setTitle("Green card number " + String.valueOf(i));
			tmp.setColor(1);
			subDeck.add(tmp);
		}

		Collections.shuffle(subDeck);
		
		this.cards.addAll(subDeck);
		
		for(Card c: this.cards) {
			System.out.println(c.getTitle());
		}
	}

}
