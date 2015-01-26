/**
 * 
 */
package bootstrap;

import java.util.ArrayList;

/**
 * @author Amir
 *
 */
public class RandomEventDeck extends Deck {

	/**
	 * 
	 */
	private final int size = 12;
	
	// Array of all card posibilities
	private final String[] names = {"The Dragon",
			 						"Flood",
			 						"Fire",
			 						"Fog",
			 						"Riots",
			 						"Explosion",
			 						"Mysterious Murders",
			 						"Demons from the Dungeon Dimensions",
			 						"Subsidence",
			 						"Bloody Stupid Johnson",
			 						"Trolls",
			 						"Earthquake"
			 						};
	public RandomEventDeck() {
		// TODO Auto-generated constructor stub
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
