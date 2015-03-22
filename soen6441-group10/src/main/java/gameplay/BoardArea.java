package gameplay;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import util.Color;
import card.city.AnkhMorporkArea;

/**
 * <p><b>
 * This class implements areas on the board, including neighbours, building
 * costs, number of minions on the spot, if there is a building, etc with their
 * related rules and all information relevant to that area on the board.<br>
 * 
 * Thus, it is just the area enumeration plus the state.
 * </b></p>
 * 
 * @author Team 10 - SOEN6441
 * @version 2.0
 */
public class BoardArea {

	private AnkhMorporkArea area;

	private Map<Color, Integer> minions;

	private boolean troubleMarker;

	/**
	 * <b>Recall the each color uniquely identifies a player - in that sense, by
	 * knowing the color of the building, we know which player it belongs to.</b>
	 */
	private Color buildingColor;

	private int demonCount;

	private int trollCount;

	/**
	 * <b>This constructor is invoked to create objects from the class Area.</b>
	 */
	public BoardArea() {
		minions = new HashMap<Color, Integer>();
		troubleMarker = false;
		buildingColor = Color.UNDEFINED;
		demonCount = 0;
		trollCount = 0;
	}

	/**
	 * <b>This constructor is invoked to create objects from the class Area.</b>
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
	 * <b>This method checks to see the neighbour of the city area.</b>
	 * 
	 * @param card2 the city area
	 * @return true if that is adjacent to the city area
	 */
	public boolean isNeighboringWith(BoardArea card2) {
		return area.isNeighboringWith(card2.getArea());
	}
	
	
	/**
	 * <b>Checks to see if there is a trouble marker in the city area.</b>
	 * 
	 * @return true if there is a trouble market in the city area
	 */
	public boolean hasTroubleMarker() {
		return troubleMarker;
	}

	/**
	 * <b>Get the color of the building, which indicates the player to whom the</b>
	 * building belongs.
	 * 
	 * @return the color of the building owner, if any.
	 */
	public Color getBuildingOwner() {
		return buildingColor;
	}

	/**
	 * <b>Adds a building to this board area for the given player.<br> Note that a 
	 * building cannot be added to an area that contains a trouble marker or 
	 * already contains a building.</b>
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
	 * <b>This method determines the number of minions of the player.</b>
	 * 
	 * @param p the player
	 * @return number of the minions of the player
	 */
	public int getMinionCountForPlayer(Player p) {
		return minions.getOrDefault(p.getColor(), 0);
	}

	/**
	 * <b>Get the minions of each player mapped with a color.</b>
	 * 
	 * @return minions mapped to a color
	 */
	public Map<Color, Integer> getMinions() {
		return minions;
	}

	/**
	 * <b>This method adds the minion of the player in the city area, and justifies
	 * the balance of stocked minions.</b>
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
	 * <b>This method removes the minion of the player from the city area, and
	 * justifies the balance of stocked minions. Also, if the area has a troublemarker
	 * it is removed.</b>
	 * 
	 * @param p
	 *            the player
	 */
	public boolean removeMinion(Player p) {
		if (minions.get(p.getColor()) == null) {
			return false;
		}
		
		int numberOfMinions = minions.get(p.getColor());
		minions.put(p.getColor(), numberOfMinions - 1);
		p.increaseMinion();
		troubleMarker = false;
		return true;
	}

	/**
	 * <b>Get the number of demons.</b>
	 * 
	 * @return number of demons on the spot
	 */
	public int getDemonCount() {
		return demonCount;
	}

	/**
	 * <b>Get the number of trolls.</b>
	 * 
	 * @return number of trolls on the spot
	 */
	public int getTrollCount() {
		return trollCount;
	}

	/**
	 * <b>Increments the number of trolls on the area.<br>
	 * Trolls are regarded as minions so they will affect trouble in the area.</b>
	 * 
	 * @return true if adding a troll succeeded, false otherwise.
	 */
	public boolean addTroll() {
		trollCount++;
		troubleMarker = (minions.values().stream().anyMatch(c -> c > 0));
		return true;
	}

	/**
	 * <b>Decrements the number of trolls on the area.<br>
	 * Trolls are regarded as minions so they will affect trouble in the area.</b>
	 * 
	 * @return true if removing a troll succeeded, false otherwise.
	 */
	public boolean removeTroll() {
		trollCount--;
		troubleMarker = false;
		return true;
	}

	/**
	 * <b>Increments the number of demons on the area.<br>
	 * Demons are regarded as minions so they will affect trouble in the area.</b>
	 * 
	 * @return true if adding a demon succeeded, false otherwise.
	 */
	public boolean addDemon() {
		demonCount++;
		troubleMarker = (minions.values().stream().anyMatch(c -> c > 0));
		return true;
	}

	/**
	 * <b>Decrements the number of demons on the area.<br>
	 * Demons are regarded as minions so they will affect trouble in the area.</b>
	 * 
	 * @return true if removing a demon succeeded, false otherwise.
	 */
	public boolean removeDemon() {
		demonCount--;
		troubleMarker = false;
		return true;
	}

	/**
	 * <b>This method adds trouble markers to the city area in case there has not
	 * been any before.</b>
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
	 * <b>This method removes a trouble marker.<br>
	 * Returns true if there was previously a trouble marker on area.</b>
	 */
	public boolean removeTroubleMarker() {
		if(hasTroubleMarker()){
			troubleMarker = false;
			return true;
		} else {
			troubleMarker = false;
			return false;
		}
		
	}
	

	/**
	 * <b>Retrieves the cost of placing a building in the area.</b>
	 * 
	 * @return the cost of building on the area.
	 */
	public int getBuildingCost() {
		return area.getBuildingCost();
	}
	
	/**
	 * <b>Removes all the pieces from this area (minions, trouble marker, building).<br>
	 * From what I understand these are not handed back to the players.</b>
	 */
	public void clearAllPieces() {
		minions.clear();
		demonCount = 0;
		trollCount = 0;
		troubleMarker = false;
		buildingColor = Color.UNDEFINED;
	}

	/**
	 * <b>Determines whether this board area is controlled by the given player.<br>
	 * An area is controlled by a player if (s)he "has more playing pieces in it
	 * than any single other player (a playing piece being a minion or a building)
	 * and has more pieces than the total number of trolls in the area".<br> An area that
	 * has at least one demon cannot be controlled.</b>
	 * 
	 * @param player
	 * @return the total number of areas controlled by the given player.
	 */
	public boolean isControlledBy(Player p) {
		int pieceCountForPlayer = getPieceCountForPlayer(p);
		if (demonCount > 0 || pieceCountForPlayer == 0) {
			return false;
		}

		// Find if this player has the maximum number of pieces on the board
		Optional<Integer> maxPiecesOwnedByPlayer = getMaximumNumberOfPiecesAmongPlayers();
		return (pieceCountForPlayer == (maxPiecesOwnedByPlayer.isPresent() ? maxPiecesOwnedByPlayer
				.get() : 0))
				&& pieceCountForPlayer > trollCount;
	}
	
	/**
	 * <b>Counts the pieces owned by the given player.<br>
	 * Pieces include minions and buildings.</b>
	 * @param p the player
	 * @return the number of pieces owned by the given player.
	 */
	private int getPieceCountForPlayer(Player p) {
		return minions.getOrDefault(p.getColor(), 0) + (buildingColor == p.getColor() ? 1 : 0);
	}
	
	/**
	 * <b>Retrieves the maximum number of pieces owned by any player on this area.<br>
	 * Pieces include minions and buildings.</b>
	 * 
	 * @return the maximum number of pieces owned by any player on this area.
	 */
	private Optional<Integer> getMaximumNumberOfPiecesAmongPlayers() {
		return minions
				.entrySet()
				.stream()
				.map(minionCountEntry -> minionCountEntry.getValue()
						+ (buildingColor == minionCountEntry.getKey() ? 1 : 0))
				.max((a, b) -> a - b);
	}
	
	public int getMinionCount() {
		return minions
				.values()
				.stream()
				.map(v -> v != null ? v : 0)
				.reduce(0,
						(partialSum, contribution) -> partialSum + contribution);
	}

}
