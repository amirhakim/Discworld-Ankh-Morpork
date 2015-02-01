package bootstrap;

/**
 * <b> This interface represents the contract for all the card types that will be required in the game <b> 
 * every respective class MUST implement it.
 * @author Team 10 - SOEN6441
 * @version 1.0
 */

public interface Card extends Cloneable {

	/** gets the title of the card AKA name
		 */
	public String getTitle();
	/** sets the title of the card AKA name
	 */
	public void setTitle(String title);

}
