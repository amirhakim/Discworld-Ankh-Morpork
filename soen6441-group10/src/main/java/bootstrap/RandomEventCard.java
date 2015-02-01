/**
 * 
 */
package bootstrap;

/**
 * @author Amir
 *
 */
public class RandomEventCard implements Card {

	/**
	 * 
	 */
	private String title;
	
	public RandomEventCard() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see bootstrap.Card#getTitle()
	 */
	public String getTitle() {
		// TODO Auto-generated method stub
		return this.title;
	}

	/* (non-Javadoc)
	 * @see bootstrap.Card#setTitle(java.lang.String)
	 */
	public void setTitle(String title) {
		// TODO Auto-generated method stub
		this.title=title;
	}

}
