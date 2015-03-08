package card;

import gameplay.Player;

import java.util.HashMap;
import java.util.Map;

import util.Color;

/**
 * <p>
 * This class implements areas on the board, including neighbours, building
 * costs, number of minions on the spot, if there is a building, etc with their
 * related rules and all information relevant to that area on the board.
 * 
 * Thus, it is just the area enumeration plus the state.
 * </p>
 */
public class BoardArea {

	private AnkhMorporkArea area;

	private Map<Color, Integer> minions;

	private boolean troubleMarker;

	/**
	 * Recall the each color uniquely identifies a player - in that sense, by
	 * knowing the color of the building, we know which player it belongs to.
	 */
	private Color buildingColor;

	private int demonCount;

	private int trollCount;

	/**
	 * This constructor is invoked to create objects from the class Area.
	 */
	public BoardArea() {
		minions = new HashMap<Color, Integer>();
		troubleMarker = false;
		buildingColor = Color.UNDEFINED;
		demonCount = 0;
		trollCount = 0;
	}

	/**
	 * This constructor is invoked to create objects from the class Area.
	 */
	public BoardArea(AnkhMorporkArea area_) {
		this();
		area = area_;
	}

	/**
	 * @return the name of the underlying city.
	 */
	public AnkhMorporkArea getArea() {
		return area;
	}

	/**
	 * This method checks to see the neighbour of the city area.
	 * 
	 * @param card2
	 *            the city area
	 * @return true if that is adjacent to the city area
	 */
	public boolean isNeighboringWith(BoardArea card2) {
		return area.isNeighboringWith(card2.getArea());
	}

	/**
	 * Checks to see if there is a trouble marker in the city area.
	 * 
	 * @return true if there is a trouble market in the city area
	 */
	public boolean hasTroubleMarker() {
		return troubleMarker;
	}

	/**
	 * Get the color of the building, which indicates the player to whom the
	 * building belongs.
	 * 
	 * @return the color of the building owner, if any.
	 */
	public Color getBuildingOwner() {
		return buildingColor;
	}

	/**
	 * This method specifies the building of the player as building owner.
	 * 
	 * @return true if the building belongs to the player
	 */
	public boolean setBuilding(Player p) {
		if (buildingColor != Color.UNDEFINED) {
			return false;
		}

		buildingColor = p.getColor();
		p.decreaseBuilding();
		return true;
	}

	/**
	 * This method determines the number of minions of the player
	 * 
	 * @param p
	 *            the player
	 * @return number of the minions of the player
	 */
	public int numberOfMinions(Player p) {
		return (minions.get(p.getColor()) == null) ? 0 : minions.get(p.getColor());
	}

	/**
	 * Get the minions of each player mapped with a color.
	 * 
	 * @return minions mapped to a color
	 */
	public Map<Color, Integer> getMinions() {
		return minions;
	}

	/**
	 * This method adds the minion of the player in the city area, and justifies
	 * the balance of stocked minions.
	 * 
	 * @param p
	 *            the player
	 */
	public void addMinion(Player p) {
		Color playerColor = p.getColor();
		p.decreaseMinion();
		if (minions.get(playerColor) == null) {
			minions.put(playerColor, 1);
		} else {
			int numberOfMinions = minions.get(playerColor);
			minions.put(playerColor, numberOfMinions + 1);
		}
	}

	/**
	 * This method removes the minion of the player from the city area, and
	 * justifies the balance of stocked minions.
	 * 
	 * @param p
	 *            the player
	 */
	public void removeMinion(Player p) {
		if (minions.get(p.getColor()) == null) {
			return;
		} else {
			int numberOfMinions = minions.get(p.getColor());
			minions.put(p.getColor(), numberOfMinions - 1);
			p.increaseMinion();
		}
	}

	/**
	 * Get the number of demons.
	 * 
	 * @return number of demons on the spot
	 */
	public int getDemonCount() {
		return demonCount;
	}

	/**
	 * Get the number of trolls.
	 * 
	 * @return number of trolls on the spot
	 */
	public int getTrollCount() {
		return trollCount;
	}

	/**
	 * This method increments the number of trolls on the spot.
	 */
	public void addTroll() {
		trollCount++;
	}

	/**
	 * This method decrements the number of trolls on the spot.
	 */
	public void removeTroll() {
		trollCount--;
	}

	/**
	 * This method increments the number of demons on the spot.
	 */
	public void addDemon() {
		demonCount++;
	}

	/**
	 * This method increments the number of demons on the spot.
	 */
	public void removeDemon() {
		demonCount--;
	}

	/**
	 * This method adds trouble markers to the city area in case there has not
	 * been any before.
	 * 
	 * @return true if it adds a trouble marker successfully
	 */
	public boolean addTroubleMarker() {
		if (troubleMarker == true) {
			return false;
		} else {
			troubleMarker = true;
			return true;
		}
	}

	/**
	 * This method gets the cost of building in the city area.
	 * 
	 * @return the cost of building on the spot
	 */
	public int getBuildingCost() {
		return area.getBuildingCost();
	}

}
