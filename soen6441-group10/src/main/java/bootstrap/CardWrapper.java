package bootstrap;

import card.AnkhMorporkCard;

/**
 * @author Amir Hakim, gkentr
 * 
 * <p>This class serves as a common wrapper for all the card subtypes that will be 
 * required in the game.</p>
 */
public abstract class CardWrapper<C extends AnkhMorporkCard> {

	protected final C card;
	
	protected CardWrapper(C card_) {
		card = card_;
	}
	
	/**
	 * @return the name/title of the card
	 */
	protected C getCard() {
		return card;
	}
	
}
