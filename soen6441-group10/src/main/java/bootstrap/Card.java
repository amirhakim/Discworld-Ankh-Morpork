package bootstrap;

/**
 * @author Amir Hakim Jan-25th-2015
 * This document is a part of the source code and related artifacts
 * got SOEN6441 group10
 * this class is the interface for all the card types that will be required in the game, 
 * every card class MUST implement it
 */

public interface Card extends Cloneable {

	// get the title of the card AKA name
	public String getTitle();
	// set the title of the card AKA name
	public void setTitle(String title);

}
