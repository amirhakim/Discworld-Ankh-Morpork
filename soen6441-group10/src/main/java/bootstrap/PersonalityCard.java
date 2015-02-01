/**
 * Card continaining information about all the personalities in the game
 */

package bootstrap;

public class PersonalityCard implements Card {
	
	// Build one only has a title to track
	private String title;
	
	public String getTitle() {
		return this.title;		
	}
	public void setTitle(String title) {
		this.title = title;	
	}
	

}
