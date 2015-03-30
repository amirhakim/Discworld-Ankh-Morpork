package card.city;

import gameplay.Game;
import gameplay.Player;

import java.util.function.BiConsumer;

import card.Card;

/**
 * This is a wrapper around the board areas to hold the state of a city card.<br>
 * City cards belong to players and can be played once per round (except for
 * {@link AnkhMorporkArea#SMALL_GODS}). Also, they can only be played on areas that
 * do not contain demons. So we need to keep track of that "played" vs. "not played"
 * state. 
 * 
 * @author Team 10 - SOEN6441
 * @version 2.0
 */
public class CityAreaCard implements Card {
	
	private AnkhMorporkArea area;
	
	private boolean hasBeenPlayed = true; // can't use it at the same round it was acquired
	
	private boolean isDisabled = false;
	
	public CityAreaCard(AnkhMorporkArea a) {
		area = a;
	}
	
	/**
	 * Checks if this City Area card contains {@link AnkhMorporkArea#SMALL_GODS}. 
	 * {@link AnkhMorporkArea#SMALL_GODS} is only applicable when a player's pieces
	 * are affected and is playable multiple times.
	 * @return false for all City Area cards except for "Small Gods".
	 */
	public boolean isSmallGods() {
		return area == AnkhMorporkArea.SMALL_GODS;
	}
	
	public boolean hasBeenPlayed() {
		return hasBeenPlayed;
	}

	public void setHasBeenPlayed(boolean b) {
		hasBeenPlayed = b;
	}
	
	public int getBuildingCost() {
		return area.getBuildingCost();
	}
	
	public boolean isDisabled() {
		return isDisabled;
	}
	
	public void setDisabled(boolean disabled) {
		isDisabled = disabled;
	}
	
	public AnkhMorporkArea getArea() {
		return area;
	}
	
	/**
	 * Resets this cards "played" state to false.
	 */
	public void reset() {
		hasBeenPlayed = false;
	}
	
	public BiConsumer<Player, Game> getCardAction() {
		return AnkhMorporkArea.getAreaAction(area);
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
		builder.append("CityAreaCard [area=").append(area).append("]");
		return builder.toString();
	}

}
