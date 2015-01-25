/**
 * 
 */
package bootstrap;

import java.util.ArrayList;

/**
 * @author Amir
 *
 */
public class CityDeck extends Deck {

	/**
	 * 
	 */
	
	private final int size = 12;
	
	private final String[] names = {"The Shades",
			 						"Dolly Sisters",
			 						"The Hippo",
			 						"Dragon's Landing",
			 						"Ils of Gods",
			 						"The Scoures",
			 						"Small Gods",
			 						"Dimwell",
			 						"Nap Hill",
			 						"Seven Sleepers",
			 						"Unreal Estate",
			 						"Longwall"
			 						};
	
	public CityDeck() {
		// TODO Auto-generated constructor stub
		
		this.cards = new ArrayList<Card>();
		for(int i=0; i<this.size; ++i) {
			Card tmp = new PersonalityCard();
			tmp.setTitle(this.names[i]);
			this.cards.add(tmp);
			}
		}
}
