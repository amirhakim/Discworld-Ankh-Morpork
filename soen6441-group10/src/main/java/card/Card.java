package card;

/**
 * <b>This interface represents the contract for all the card types that will be required in the game.</b> 
 * Each respective class MUST implement it.
 * @author Team 10 - SOEN6441
 * @version 1.0
 */
public interface Card extends Cloneable {

	/**
	 * Gets the title of card AKA name.
	 */
	public String getTitle();

	/**
	 * Sets the title of card AKA name.
	 * @param title
	 */
	public void setTitle(String title);

}
