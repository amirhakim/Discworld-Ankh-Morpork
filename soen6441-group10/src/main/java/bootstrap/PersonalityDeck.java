/**
 * @File
 * Class continaing all personality cards
 */

package bootstrap;

import java.util.ArrayList;

public class PersonalityDeck extends Deck{
	
	private final int size = 5;
	
	// Array of all card posibilities
	private final String[] names = {"Lord Selachii",
			 						"Chrysoprase",
			 						"Loard Vetinaari",
			 						"Dragon King of Arms",
			 						"Commander Vimes"
			 						};
	
	
	public PersonalityDeck() {
		// TODO Auto-generated constructor stub
		deckSize=size;
		populateDeck();
	}


	@Override
	public void populateDeck() {
		// TODO Auto-generated method stub
		this.cards = new ArrayList<Card>();
		// Initizialize eachc card with a title
		for(int i=0; i<this.size; ++i) {
			Card tmp = new PersonalityCard();
			tmp.setTitle(this.names[i]);
			this.cards.add(tmp);
		}
		// Shuffle deck
		super.shuffle();
	}

}
