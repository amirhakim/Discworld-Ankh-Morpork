/**
 * 
 */
package bootstrap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <b> This class represents the city cards of the game <b> 
 * @author Team 10 - SOEN6441
 * @version 1.0
 */
public class CityCard implements Card {

	private String title;
	private transient List<CityCard> neighbours;
	private Map<Player, Integer> minions;
	private boolean troubleMaker;
	private Player buildingOwner;
	private int demons;
	private int trolls;
	private int buildingCost;
	
	public CityCard() {
		this.neighbours = new ArrayList<CityCard>();
		this.minions = new HashMap<Player, Integer>();
		this.troubleMaker = false;
		this.buildingOwner = null;
		this.demons = 0;
		this.trolls = 0;
	}
	
	public CityCard(String title_, int buildingCost_) {
		this();
		title = title_;
		buildingCost = buildingCost_;
	}

	/* (non-Javadoc)
	 * @see bootstrap.Card#getTitle()
	 */
	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return this.title;
	}

	/* (non-Javadoc)
	 * @see bootstrap.Card#setTitle(java.lang.String)
	 */
	@Override
	public void setTitle(String title) {
		// TODO Auto-generated method stub
		this.title=title;
	}
	
	public CityCard addNeighbour(CityCard neighbourCard, boolean recipricate) {
		this.neighbours.add(neighbourCard);
		if(recipricate) neighbourCard.addNeighbour(this,false);
		return this;
	}
	
	public boolean isNeighbour(CityCard card2){
		if(this.neighbours.contains(card2)){
			return true;
		}
		else{
			return false;
		}
	}
	
	public boolean hasTroubleMaker() {
		return troubleMaker;
	}
	
	public Player getBuilding() {
		if(this.buildingOwner == null) {
			return null;
		} else {
			return this.buildingOwner;
		}
	}
	
	public boolean setBuilding(Player p) {
		if(this.buildingOwner == null) {
			this.buildingOwner = p;
			p.decreaseBuilding();
			return true;
		} else {
			return false;
		}
	}
	
	public int numberOfMinions(Player p) {
		if(this.minions.get(p) == null) {
			return 0;
		} else {
			return this.minions.get(p);
		}
	}
	
	public Map<Player, Integer> getMinions() {
		return this.minions;
	}
	
	public void addMinion(Player p) {
		p.decreaseMinion();
		if(this.minions.get(p) == null) {
			this.minions.put(p,1);
		} else {
			int minions = this.minions.get(p);
			this.minions.put(p, minions+1);
		}
	}
	
	public void removeMinion(Player p) {
		if(this.minions.get(p) == null) {
			return;
		} else {
			int minions = this.minions.get(p);
			this.minions.put(p, minions-1);
			p.increaseMinion();
		}
	}
	
	public int getDemons() {
		return this.demons;
	}
	
	public int getTrolls() {
		return this.trolls;
	}
	
	public void incTrolls() {
		this.trolls++;
	}
	
	public void decTrolls() {
		this.trolls--;
	}
	
	public void incDemons() {
		this.demons++;
	}
	
	public void decDemons() {
		this.demons--;
	}
	
	public boolean addTrouble() {
		if(this.troubleMaker == true) {
			return false;
		} else {
			this.troubleMaker = true;
			return true;
		}
	}
	
	
	
	public void setBuildingCost(int cost){
		this.buildingCost = cost;
	}
	
	public int getBuildingCost(){
		return this.buildingCost;
	}
	
			

	

}
