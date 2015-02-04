/**
 * 
 */
package card;

import gameplay.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.Color;

/**
 * <b> This class implements the city cards of the game with their related rules of movement and all information relevant
 *   to that area on the board.<b>
 * 
 * @author Team 10 - SOEN6441
 * @version 1.0
 */
public class CityCard implements Card {

	private String title;
	private transient List<CityCard> neighbours;
	private Map<Color, Integer> minions;
	private boolean troubleMaker;
	private Player buildingOwner;
	private int demons;
	private int trolls;
	private int buildingCost;

	/**
	 * This constructor is invoked to create objects from the class CityCard.
	 */
	public CityCard() {
		this.neighbours = new ArrayList<CityCard>();
		this.minions = new HashMap<Color, Integer>();
		this.troubleMaker = false;
		this.buildingOwner = null;
		this.demons = 0;
		this.trolls = 0;
	}
/**
 * This method takes title and building cost of city cards.
 * @param title_ The title of the city card
 * @param buildingCost_ The building cost of that area
 */
	public CityCard(String title_, int buildingCost_) {
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
		return this.title;
	}

	/**
	 * This method implements setTitle method of interface Card. 
	 * It sets title of city card.
	 */
	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * This method adds the neighbours of the city area.
	 * @param neighbourCard the neighbour of the city area
	 * @param recipricate sets true if it is adjacent to this area
	 * @return adjacent city areas
	 */
	public CityCard addNeighbour(CityCard neighbourCard, boolean recipricate) {
		this.neighbours.add(neighbourCard);
		if (recipricate)
			neighbourCard.addNeighbour(this, false);
		return this;
	}

	/**
	 * This method checks to see the neighbour of the city area.
	 * @param card2 the city area
	 * @return true if that is adjacent to the city area
	 */
	public boolean isNeighbour(CityCard card2){
		return this.neighbours.contains(card2);
	}

	/**
	 * This method checks to see if there is a trouble marker in the city area.
	 * @return true if there is a trouble market in the city area
	 */
	public boolean hasTroubleMaker() {
		return troubleMaker;
	}

	/**
	 * This method specifies the building of the player as building owner.
	 * @return true if the building belongs to the player
	 */
	public Player getBuilding() {
		if (this.buildingOwner == null) {
			return null;
		} else {
			return this.buildingOwner;
		}
	}

	/**
	 * This method makes building for the player as building owner in case there is not any building in the city area.
	 * @param p player as the building owner
	 * @return true if the player makes a building successfully in the city area
	 */
	public boolean setBuilding(Player p) {
		if (this.buildingOwner == null) {
			this.buildingOwner = p;
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
		if (this.minions.get(p) == null) {
			return 0;
		} else {
			return this.minions.get(p);
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
		if (this.minions.get(playerColor) == null) {
			this.minions.put(playerColor, 1);
		} else {
			int minions = this.minions.get(playerColor);
			this.minions.put(playerColor, minions + 1);
		}
	}

	/**
	 * This method removes the minion of the player from the city area,
	 *  and justifies the balance of stocked minions.
	 * @param p the player
	 */
	public void removeMinion(Player p) {
		if (this.minions.get(p.getColor()) == null) {
			return;
		} else {
			int minions = this.minions.get(p);
			this.minions.put(p.getColor(), minions - 1);
			p.increaseMinion();
		}
	}
	
	/**
	 * This method gets the number of demons. 
	 * @return number of demons on the spot
	 */
	public int getDemons() {
		return this.demons;
	}
	
	/**
	 * This method gets the number of trolls.
	 * @return number of trolls on the spot
	 */
	public int getTrolls() {
		return this.trolls;
	}

	/**
	 * This method increments the number of trolls on the spot. 
	 */
	public void incTrolls() {
		this.trolls++;
	}

	/**
	 * This method decrements the number of trolls on the spot. 
	 */
	public void decTrolls() {
		this.trolls--;
	}

	/**
	 * This method increments the number of demons on the spot. 
	 */
	public void incDemons() {
		this.demons++;
	}

	/**
	 * This method increments the number of demons on the spot. 
	 */
	public void decDemons() {
		this.demons--;
	}

	/**
	 * This method adds trouble markers to the city area in case there has not been any before.
	 * @return true if it adds a trouble marker successfully
	 */
	public boolean addTrouble() {
		if (this.troubleMaker == true) {
			return false;
		} else {
			this.troubleMaker = true;
			return true;
		}
	}

	/**
	 * This method sets the cost of building in the city area.
	 * @param the cost of building on the spot
	 */
	public void setBuildingCost(int cost) {
		this.buildingCost = cost;
	}

	/**
	 * This method gets the cost of building in the city area.
	 * @return the cost of building on the spot
	 */
	public int getBuildingCost() {
		return this.buildingCost;
	}

}
