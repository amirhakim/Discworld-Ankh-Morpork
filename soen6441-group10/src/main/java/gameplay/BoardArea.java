package gameplay;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import util.Color;
import card.city.AnkhMorporkArea;

/**
 * <p>
 * This class implements areas on the board, including neighbours, building
 * costs, number of minions on the spot, if there is a building, etc with their
 * related rules and all information relevant to that area on the board.<br>
 * 
 * Thus, it is just the area enumeration plus the state.
 * </p>
 * 
 * @author Team 10 - SOEN6441
 * @version 2.0
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
	 * @return true if there is a building in the area, false otherwise.
	 */
	public boolean hasBuilding() {
		return buildingColor != Color.UNDEFINED;
	}

	/**
	 * This method checks to see the neighbour of the city area.
	 * 
	 * @param card2 the city area
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
	 * Adds a building to this board area for the given player.<br> Note that a 
	 * building cannot be added to an area that contains a trouble marker or 
	 * already contains a building.
	 * @param p the player
	 * @return true if the building was successfully added, false otherwise.
	 */
	public boolean addBuildingForPlayer(Player p) {
		if (buildingColor != Color.UNDEFINED || hasTroubleMarker()) {
			return false;
		}

		buildingColor = p.getColor();
		
		// TODO move this to game.addBuilding()
		p.decreaseBuilding();
		return true;
	}
	
	public boolean removeBuilding() {
		buildingColor = Color.UNDEFINED;
		return true;
	}

	/**
	 * This method determines the number of minions of the player.
	 * 
	 * @param p the player
	 * @return number of the minions of the player
	 */
	public int getMinionCountForPlayer(Player p) {
		return minions.getOrDefault(p.getColor(), 0);
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
		troubleMarker = 
				(minions.values().stream().anyMatch(c -> c > 0)) || demonCount > 0 || trollCount > 0;
		Color playerColor = p.getColor();
		p.decreaseMinions();
		if (minions.get(playerColor) == null) {
			minions.put(playerColor, 1);
		} else {
			int numberOfMinions = minions.get(playerColor);
			minions.put(playerColor, numberOfMinions + 1);
		}
	}

	/**
	 * This method removes the minion of the player from the city area, and
	 * justifies the balance of stocked minions. Also, if the area has a troublemarker
	 * it is removed.
	 * 
	 * @param p
	 *            the player
	 */
	public boolean removeMinion(Player p) {
		if (minions.getOrDefault(p.getColor(), 0) == 0) {
			return false;
		}
		
		int numberOfMinions = minions.get(p.getColor());
		if (numberOfMinions > 1) {
			minions.put(p.getColor(), numberOfMinions - 1);
		} else {
			minions.remove(p.getColor());
		}

		p.increaseMinions();
		troubleMarker = false;
		return true;
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
	 * Increments the number of trolls on the area.<br>
	 * Trolls are regarded as minions so they will affect trouble in the area.
	 * 
	 * @return true if adding a troll succeeded, false otherwise.
	 */
	public boolean addTroll() {
		troubleMarker = (minions.values().stream().anyMatch(c -> c > 0)) || demonCount > 0 || trollCount > 0;
		trollCount++;
		return true;
	}

	/**
	 * Decrements the number of trolls on the area.<br>
	 * Trolls are regarded as minions so they will affect trouble in the area.
	 * 
	 * @return true if removing a troll succeeded, false otherwise.
	 */
	public boolean removeTroll() {
		trollCount--;
		troubleMarker = false;
		return true;
	}

	/**
	 * Increments the number of demons on the area.<br>
	 * Demons are regarded as minions so they will affect trouble in the area.
	 * If a player has a building in the area, the corresponding city area card 
	 * should have been disabled.
	 * 
	 * @return true if adding a demon succeeded, false otherwise.
	 */
	public boolean addDemon() {
		troubleMarker = true;
		demonCount++;
		return true;
	}

	/**
	 * Decrements the number of demons on the area.<br>
	 * Demons are regarded as minions so they will affect trouble in the area.
	 * Also 
	 * 
	 * @return true if removing a demon succeeded, false otherwise.
	 */
	public boolean removeDemon() {
		demonCount--;
		troubleMarker = false;
		return true;
	}

	/**
	 * Adds trouble markers to the city area in case there were not any before.
	 * 
	 * @return true if it a trouble marker was added successfully.
	 */
	public boolean addTroubleMarker() {
		if (troubleMarker == true) {
			return false;
		} 

		troubleMarker = true;
		return true;
	}
	
	/**
	 * Removes a trouble marker.
	 * @return true if there was previously a trouble marker on area, false
	 * otherwise.
	 */
	public boolean removeTroubleMarker() {
		if (hasTroubleMarker()) {
			troubleMarker = false;
			return true;
		} else {
			troubleMarker = false;
			return false;
		}
	}
	

	/**
	 * @return the cost of placing a building on the area.
	 */
	public int getBuildingCost() {
		return area.getBuildingCost();
	}
	
	/**
	 * Determines whether this board area is controlled by the given player.<br>
	 * An area is controlled by a player if (s)he "has more playing pieces in it
	 * than any single other player (a playing piece being a minion or a building)
	 * and has more pieces than the total number of trolls in the area". An area that
	 * has at least one demon cannot be controlled.
	 * 
	 * @param player
	 * @return true if this area is controlled by the given player, false otherwise.
	 */
	public boolean isControlledBy(Player p) {
		boolean playerOwnsBuilding = (getBuildingOwner() == p.getColor());
		int playerPieces = getMinionCountForPlayer(p) + (playerOwnsBuilding ? 1 : 0);
		Optional<Integer> maxOtherMinions = minions.values().stream().max(Integer::compare);
		int maxPiecesOwnedByAnyOtherPlayer = (maxOtherMinions.isPresent() ? maxOtherMinions.get() : 0)
				+ ((hasBuilding() && !playerOwnsBuilding) ? 1 : 0);
		return (playerPieces > maxPiecesOwnedByAnyOtherPlayer 
				&& playerPieces > getTrollCount() && getDemonCount() == 0);
	}
	
	/**
	 * @return the player who controls this area, if any.
	 */
	public Player isControlled(Map<Color, Player> players) {
		int count = 0;
		Player control = null;
		for (Player p : players.values()) {
			if (isControlledBy(p)) {
				control = p;
				count++;
			}
		}
		if (count != 1) {
			return null;
		} else {
			return control;
		}
	}
	
	/**
	 * @return the total number of minions present in the area (demons and trolls
	 * are not included).
	 */
	public int getMinionCount() {
		return minions
				.values()
				.stream()
				.map(v -> v != null ? v : 0)
				.reduce(0,
						(partialSum, contribution) -> partialSum + contribution);
	}

}
