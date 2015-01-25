package bootstrap;

/**
 * This document is a part of the source code and related artifacts
 * got SOEN6441 group10
 * this class is the interface for all the card types that will be required in the game, 
 * every card class MUST implement it
 */

public interface Card {
	// get the title of the card AKA name
	public void getTitle();
	// set the title of the card AKA name
	public void setTitle();

}
