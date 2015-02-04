package card;

import gameplay.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.Color;

/**
 * This class represents the areas of the board - it is a placeholder for
 * items to be placed.
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

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public void setTitle(String title_) {
		title = title_;
	}

	public Area addNeighbour(Area neighbourCard, boolean recipricate) {
		neighbours.add(neighbourCard);
		if (recipricate)
			neighbourCard.addNeighbour(this, false);
		return this;
	}

	public boolean isNeighbour(Area card2){
		return neighbours.contains(card2);
	}

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

	public Player getBuildingOwner() {
		return buildingOwner;
	}

	public boolean setBuilding(Player p) {
		if (buildingOwner == null) {
			buildingOwner = p;
			p.decreaseBuilding();
			return true;
		} else {
			return false;
		}
	}

	public int numberOfMinions(Player p) {
		if (minions.get(p) == null) {
			return 0;
		} else {
			return minions.get(p);
		}
	}

	public Map<Color, Integer> getMinions() {
		return minions;
	}

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

	public void removeMinion(Player p) {
		if (minions.get(p.getColor()) == null) {
			return;
		} else {
			int numberOfMinions = minions.get(p);
			minions.put(p.getColor(), numberOfMinions - 1);
			p.increaseMinion();
		}
	}

	public int getDemons() {
		return demons;
	}

	public int getTrolls() {
		return trolls;
	}

	public void incTrolls() {
		trolls++;
	}

	public void decTrolls() {
		trolls--;
	}

	public void incDemons() {
		demons++;
	}

	public void decDemons() {
		demons--;
	}

	public boolean addTrouble() {
		if (troubleMaker == true) {
			return false;
		} else {
			troubleMaker = true;
			return true;
		}
	}

	public void setBuildingCost(int cost) {
		buildingCost = cost;
	}

	public int getBuildingCost() {
		return buildingCost;
	}

}
