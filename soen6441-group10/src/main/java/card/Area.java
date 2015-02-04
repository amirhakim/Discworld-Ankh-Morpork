package card;

import gameplay.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.Color;

/**
 * <b> This class implements the city cards of the game with their related rules of movement and all information relevant
 *   to that area on the board.</b>
 */
public class Area implements Card {

	private String title;
	private transient List<Area> neighbours;
	private Map<Color, Integer> minions;
	private boolean troubleMaker;
	private Player buildingOwner;
	private int demons;
	private int trolls;
	private int buildingCost;

	public Area() {
		neighbours = new ArrayList<Area>();
		minions = new HashMap<Color, Integer>();
		troubleMaker = false;
		buildingOwner = null;
		demons = 0;
		trolls = 0;
	}

	public Area(String title_, int buildingCost_) {
		this();
		title = title_;
		buildingCost = buildingCost_;
	}

	/**
	* This method implements getTitle method of interface Card. 
	 * It gets title of city card.
	 */
	@Override
	public String getTitle() {
		return title;
	}

	/**
	 * This method implements setTitle method of interface Card. 
	 * It sets title of city card.
	 */
	@Override
	public void setTitle(String title_) {
		title = title_;
	}

	/**
	 * This method adds the neighbours of the city area.
	 * @param neighbourCard the neighbour of the city area
	 * @param recipricate sets true if it is adjacent to this area
	 * @return adjacent city areas
	 */
	public Area addNeighbour(Area neighbourCard, boolean recipricate) {
		neighbours.add(neighbourCard);
		if (recipricate)
			neighbourCard.addNeighbour(this, false);
		return this;
	}

	/**
	 * This method checks to see the neighbour of the city area.
	 * @param card2 the city area
	 * @return true if that is adjacent to the city area
	 */
	public boolean isNeighbour(Area card2){
		return neighbours.contains(card2);
	}

	/**
	 * This method checks to see if there is a trouble marker in the city area.
	 * @return true if there is a trouble market in the city area
	 */
	public boolean hasTroubleMaker() {
		return troubleMaker;
	}

	// WTF?!
//	public Player getBuilding() {
//		if (buildingOwner == null) {
//			return null;
//		} else {
//			return buildingOwner;
//		}
//	}

	/**
	 * This method specifies the building of the player as building owner.
	 * @return true if the building belongs to the player
	 */
	public Player getBuildingOwner() {
		return buildingOwner;
	}

	/**
	 * This method specifies the building of the player as building owner.
	 * @return true if the building belongs to the player
	 */
	public boolean setBuilding(Player p) {
		if (buildingOwner == null) {
			buildingOwner = p;
			p.decreaseBuilding();
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This method determines the number of minions of the player 
	 * @param p the player
	 * @return number of the minions of the player
	 */
	public int numberOfMinions(Player p) {
		if (minions.get(p) == null) {
			return 0;
		} else {
			return minions.get(p);
		}
	}

	/**
	 * This method maps the minions of each player with a color.
	 * @return minions mapped to a color
	 */
	public Map<Color, Integer> getMinions() {
		return minions;
	}

	/**
	 * This method adds the minion of the player in the city area,
	 *  and justifies the balance of stocked minions.
	 * @param p the player
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
	 * This method removes the minion of the player from the city area,
	 *  and justifies the balance of stocked minions.
	 * @param p the player
	 */
	public void removeMinion(Player p) {
		if (minions.get(p.getColor()) == null) {
			return;
		} else {
			int numberOfMinions = minions.get(p);
			minions.put(p.getColor(), numberOfMinions - 1);
			p.increaseMinion();
		}
	}

	/**
	 * This method gets the number of demons. 
	 * @return number of demons on the spot
	 */
	public int getDemons() {
		return demons;
	}

	/**
	 * This method gets the number of trolls.
	 * @return number of trolls on the spot
	 */
	public int getTrolls() {
		return trolls;
	}

	/**
	 * This method increments the number of trolls on the spot. 
	 */
	public void incTrolls() {
		trolls++;
	}

	/**
	 * This method decrements the number of trolls on the spot. 
	 */
	public void decTrolls() {
		trolls--;
	}

	/**
	 * This method increments the number of demons on the spot. 
	 */
	public void incDemons() {
		demons++;
	}

	/**
	 * This method increments the number of demons on the spot. 
	 */
	public void decDemons() {
		demons--;
	}

	/**
	 * This method adds trouble markers to the city area in case there has not been any before.
	 * @return true if it adds a trouble marker successfully
	 */
	public boolean addTrouble() {
		if (troubleMaker == true) {
			return false;
		} else {
			troubleMaker = true;
			return true;
		}
	}

	/**
	 * This method sets the cost of building in the city area.
	 * @param the cost of building on the spot
	 */
	public void setBuildingCost(int cost) {
		buildingCost = cost;
	}

	/**
	 * This method gets the cost of building in the city area.
	 * @return the cost of building on the spot
	 */
	public int getBuildingCost() {
		return buildingCost;
	}

}
