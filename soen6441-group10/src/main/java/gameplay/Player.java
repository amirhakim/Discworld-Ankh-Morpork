package gameplay;

import java.util.ArrayList;
import java.util.List;

import util.Color;
import card.PersonalityCard;
import card.PlayerCard;

/**
 * This class represents the players participating in the game, including the
 * pieces they hold as well as their hands.
 * 
 * It is important to note that a player's color uniquely identifies the player.
 * 
 * @author Team 10 - SOEN6441
 * @version 1.0
 */
public class Player {

	private String name;
	private PersonalityCard personality;
	private Color color;
	private int money;
	private int minions;
	private int buildings;
	private List<PlayerCard> playerCards = new ArrayList<PlayerCard>(5);

	/**
	 * This constructor is invoked to create objects from the class Player.
	 */
	public Player() {
		this.money = 0;
		this.minions = 12;
		this.buildings = 6;
	}

	/**
	 * Sets player's name for the current object.
	 * @param name the name of player
	 * @return true if the name is valid
	 */
	public boolean setName(String name) {
		// Checks for a valid name. Valid name contains only letter.
		if (isAlpha(name)) {
			this.name = name;
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Checks criteria to see if the entry is a valid name.
	 * @param name the name entered by player
	 * @return true if the entry is a valid name
	 */
	private boolean isAlpha(String name) {
		return name.matches("^[a-zA-Z0-9_]*$");
	}

	/**
	 * Get player's name.
	 * @return the player's name 
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * This method assigns the personality card to the player.
	 * @param personality the personality card
	 */
	public void setPersonality(PersonalityCard personality) {
		this.personality = personality;
	}

	/**
	 * Get the personality card of the player.
	 * @return the assigned personality card
	 */
	public PersonalityCard getPersonality() {
		return this.personality;
	}

	/**
	 * This method prints the player's turn
	 */
	public void turn() {
		System.out.println(this.getName() + " turn");
	}

	/**
	 * This method increases the player's money
	 * @param amount the amount of money
	 * @return true if it increases the player's money successfully
	 */
	public boolean increaseMoney(int amount) {
		this.money = this.money + amount;
		return true;
	}

	/**
	 * This method decreases the player's money.
	 * @param amount the amount of money
	 * @return true if it decreases the player's money successfully
	 */
	public boolean decreaseMoney(int amount) {
		if (this.money - amount < 0) {
			return false;
		} else {
			this.money = this.money - amount;
			return true;
		}

	}

	/**
	 * Get the total amount of money the player currently has.
	 * @return the amount
	 */
	public int getAmount() {
		return this.money;
	}

	/**
	 * Get the total number of minions the player currently has.
	 * @return the minions
	 */
	public int getMinions() {
		return this.minions;
	}

	// Decrease the number of minions by one
	/**
	 * This method decrements the player's minions.
	 * @return true if it decrements successfully (there is any minion to be decremented).
	 */
	public boolean decreaseMinion() {
		if ((this.minions - 1) >= 0) {
			this.minions = this.minions - 1;
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This method increments the player's minions.
	 * @return true if it increments successfully (the number of minions wouldn't pass the upper bound).
	 */
	public boolean increaseMinion() {
		if (this.minions < 12) {
			this.minions = this.minions + 1;
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Get the total number of buildings the player currently has
	 * @return the total number of buildings the player currently has
	 */
	public int getBuildings() {
		return this.buildings;
	}

	/**
	 * This method decrements the player's building.
	 * @return true if it decrements successfully (there is any building to be decremented).
	 */
	public boolean decreaseBuilding() {
		if ((this.buildings - 1) > 0) {
			this.buildings = this.buildings - 1;
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This method increments the player's building.
	 * @return true if it increments successfully (there is any building to be incremented).
	 */
	public boolean increaseBuildings() {
		if ((this.buildings + 1) < 6) {
			this.buildings = this.buildings + 1;
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This method adds player's cards one by one.
	 * @param card player card
	 * @return true when adds a player card
	 */
	boolean addPlayerCard(PlayerCard card) {
		this.playerCards.add(card);
		return true;
	}

	// Remove Player's card one by one
	/**
	 * This method removes player's cards one by one.
	 * @param card player card
	 * @return true if it founds the player card and remove it successfully
	 */
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

	/**
	 * Get list of the player's cards
	 * @return the player's cards
	 */
	public List<PlayerCard> getPlayerCards() {
		return this.playerCards;
	}

	
	/**
	 * Set Color for Player's pieces
	 * @param color_ the color
	 */
	public void setColor(Color color_) {
		color = color_;
	}

	/**
	 * Get Player's color
	 * @return the Player's color
	 */
	public Color getColor() {
		return this.color;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
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
		if (!(obj instanceof Player)) {
			return false;
		}
		Player other = (Player) obj;
		if (color != other.color) {
			return false;
		}
		return true;
	}

	
	@Override
	public String toString() {
		return "Player[ name=" + name + ", color=" + color + "]";
	}

}
