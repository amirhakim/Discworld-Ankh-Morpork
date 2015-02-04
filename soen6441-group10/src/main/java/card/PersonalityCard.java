
package card;

/**
 * <b> This class implements the Personality cards of the game with their related rules<b>
 * 
 * @author Team 10 - SOEN6441
 * @version 1.0
 */
public class PersonalityCard implements Card {

	private String title;
	
	/**
	 * This method implements getTitle method of interface Card. 
	 * It gets the title of personality card.
	 */
	@Override
	public String getTitle() {
		return this.title;
	}

	/**
	 * This method implements setTitle method of interface Card. 
	 * It sets the title of personality card.
	 */
	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof PersonalityCard)) {
			return false;
		}
		PersonalityCard other = (PersonalityCard) obj;
		if (title == null) {
			if (other.title != null) {
				return false;
			}
		} else if (!title.equals(other.title)) {
			return false;
		}
		return true;
	}

	/**
	 * This method overrides implementation of toString in a way to print the title of the personality card.
	 */
	@Override
	public String toString() {
		return "PersonalityCard[ " + title + " ]";
	}

}

