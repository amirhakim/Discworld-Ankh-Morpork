/**
 * @File
 * Player class representing human player of game
 */
package bootstrap;

import java.util.ArrayList;
import java.util.List;

public class Player {

	private String name;
	private PersonalityCard personality;
	private int color;
	private int money;
	private int minions;
	private int buildings;
	private List<PlayerCard> playerCards = new ArrayList<PlayerCard>(5);

	public Player() {
		this.money = 0;
		this.minions = 12;
		this.buildings = 6;
	}

	// Sets player's name for the current object.
	// Returns true for a valid name otherwise returns false.
	boolean setName(String name) {
		// Checks for a valid name. Valid name contains only letter.
		if (isAlpha(name)) {
			this.name = name;
			return true;
		} else {
			return false;
		}
	}

	private boolean isAlpha(String name) {
		// TODO this needs to allow numbers
		return name.matches("^[a-zA-Z0-9_]*$");
	}

	// Returns name value
	String getName() {
		return this.name;
	}

	void setPersonality(PersonalityCard personality) {
		this.personality = personality;
	}

	PersonalityCard getPersonality() {
		return this.personality;
	}

	void turn() {
		System.out.println(this.getName() + " turn");
	}

	// Increase player's money
	boolean increaseMoney(int amount) {
		this.money = this.money + amount;
		return true;
	}

	// Decrease player's money
	boolean decreaseMoney(int amount) {
		if (this.money - amount < 0) {
			return false;
		} else {
			this.money = this.money - amount;
			return true;
		}

	}

	// Returns the total amount of money player currently has
	int getAmount() {
		return this.money;
	}

	// Returns the total number of minions the player currently has
	int getMinions() {
		return this.minions;
	}

	// Decrease the number of minions by one
	boolean decreaseMinion() {
		if ((this.minions - 1) >= 0) {
			this.minions = this.minions - 1;
			return true;
		} else {
			return false;
		}
	}

	// Increase the number of minions by one
	boolean increaseMinion() {
		if (this.minions < 12) {
			this.minions = this.minions + 1;
			return true;
		} else {
			return false;
		}
	}

	// Returns the total number of buildings the player currently has
	int getBuildings() {
		return this.buildings;
	}

	// Decrease the number of buildings by one
	boolean decreaseBuilding() {
		if ((this.buildings - 1) > 0) {
			this.buildings = this.buildings - 1;
			return true;
		} else {
			return false;
		}
	}

	// Increase the number of buildings by one
	boolean increaseBuildings() {
		if ((this.buildings + 1) < 6) {
			this.buildings = this.buildings + 1;
			return true;
		} else {
			return false;
		}
	}

	// Set Player's cards one by one
	boolean addPlayerCard(PlayerCard card) {
		this.playerCards.add(card);
		return true;
	}

	// Remove Player's card one by one
	boolean removePlayerCard(PlayerCard card) {
		int size = playerCards.size(); // Size of arrayList (equal to the
										// number of cards in player's hand)

		// Looking for the specific card in players hand
		// Remove it if found and returns true else returns false
		for (int i = 0; i < size; i++) {
			if (playerCards.get(i).getTitle().equals(card.getTitle())) {
				playerCards.remove(i);
				return true;
			}
		}
		return false;
	}

	public List<PlayerCard> getPlayerCards() {
		return this.playerCards;
	}

	// Set Color for Player's pieces
	boolean setColor(int color) {
		this.color = color;
		return true;
	}

	// Get Player's color
	String getColor() {
		String[] colors = {"Red", "Yellow", "Green", "Blue"};
		return colors[this.color];
	}

}
