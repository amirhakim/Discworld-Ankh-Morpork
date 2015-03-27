package card.city;

/**
 * <b>This is a wrapper around the board areas to hold the state of a city card.<br>
 * City cards belong to players and can be played once per round (except for
 * {@link AnkhMorporkArea#SMALL_GODS}. So we need to keep track of that 
 * "played" vs. "not played" state.</b>
 * 
 * @author Team 10 - SOEN6441
 * @version 2.0
 */
 
public class CityAreaCard {
	
	private AnkhMorporkArea area;
	
	private boolean hasBeenPlayed = false;
	
	public CityAreaCard(AnkhMorporkArea a) {
		area = a;
	}
	
	/**
	 * Checks if this City Area card can be played more than once per round.
	 * @return false for all City Area cards except for "Small Gods".
	 */
	public boolean isPlayableMoreThanOnce() {
		return area == AnkhMorporkArea.SMALL_GODS;
	}
	
	public boolean hasBeenPlayed() {
		return hasBeenPlayed;
	}

	public void setHasBeenPlayed(boolean b) {
		hasBeenPlayed = b;
	}
	
	/**
	 * Resets this cards "played" state to false.
	 */
	public void reset() {
		hasBeenPlayed = false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((area == null) ? 0 : area.hashCode());
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
		if (!(obj instanceof CityAreaCard)) {
			return false;
		}
		CityAreaCard other = (CityAreaCard) obj;
		if (area != other.area) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CityAreaCard [area=").append(area)
				.append(", hasBeenPlayed=").append(hasBeenPlayed).append(", isPlayableMoreThanOnce= ")
				.append(isPlayableMoreThanOnce()).append("]");
		return builder.toString();
	}

}
