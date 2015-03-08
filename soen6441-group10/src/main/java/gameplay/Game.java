package gameplay;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import util.Color;
import card.AnkhMorporkArea;
import card.BoardArea;
import card.personality.PersonalityCard;
import card.personality.PersonalityDeck;
import card.player.PlayerDeck;
import card.random.RandomEventDeck;
import error.InvalidGameStateException;

/**
 * This class represents the bulk of the actions available in the game. It sets
 * up the game, and provides a layer of access to the game components for the
 * controller.
 * 
 * @author Team 10 - SOEN6441
 * @version 1.0
 */
public class Game {

	private Bank gameBank;

	private Map<Color, Player> players;
	
	private Color[] playerTurnOrder;

	private PersonalityDeck personalityDeck;

	private PlayerDeck playerDeck;
	
	/**
	 * For most, if not all, use cases, we need to alter the state
	 * of a board area based on its area code (which is the value after
	 * rolling the die). So it makes most sense to keep areas in an
	 * Integer -> BoardArea map.
	 */
	private Map<Integer, BoardArea> gameBoard;

	private RandomEventDeck randomEventDeck;

	/**
	 * Identifies general game status, if it is initiated or not.
	 */
	private GameStatus status;

	/**
	 * Identifies whose turn it is, uses index in the player array.
	 */
	private int currentTurn;

	public Game() {
		status = GameStatus.UNINITIATED;
	}

	/**
	 * This method sets up game. Game has not started, but the deck is set up.
	 * 
	 * @param: numberOfPlayers how many people will be playing game
	 * @param: playerNames array of what everybody's name is
	 * @Exception: if invalid number of players
	 */
	public void setUp(int numberOfPlayers, String[] playerNames)
			throws InvalidGameStateException {
		// Create players.
		if (playerNames.length != numberOfPlayers)
			throw new InvalidGameStateException(
					"Number of players and player names not matching");

		// Make sure players are between 2 and 4.
		if (numberOfPlayers > 4 || numberOfPlayers < 2) {
			throw new InvalidGameStateException("Incorrect player number");
		} else {
			gameBank = new Bank();

			players = new HashMap<>();
			for (int i = 0; i < numberOfPlayers; i++) {
				Player p = new Player();
				p.setName(playerNames[i]);
				Color c = Color.forCode(i);
				p.setColor(c);
				players.put(c, p);
				playerTurnOrder[i] = c;
			}
			
			for (AnkhMorporkArea a : AnkhMorporkArea.values()) {
				gameBoard.put(a.getAreaCode(), new BoardArea(a));
			}
		}

		playerDeck = new PlayerDeck();
		randomEventDeck = new RandomEventDeck();

		status = GameStatus.READY;
	}

	/**
	 * This method starts a new game.
	 */
	public void init() {
		// Give each player their money, personality and initial minions.
		// Cards that have an initial state.
		BoardArea theShades = gameBoard.get(AnkhMorporkArea.THE_SHADES.getAreaCode());
		BoardArea theScours = gameBoard.get(AnkhMorporkArea.THE_SCOURS.getAreaCode());
		BoardArea dollySisters = gameBoard.get(AnkhMorporkArea.DOLLY_SISTERS.getAreaCode());

		for (Entry<Color, Player> p : players.entrySet()) {
			// Deal out 10 dollars.
			p.getValue().increaseMoney(10);
			gameBank.decreaseBalance(10);

			Optional<PersonalityCard> popped = personalityDeck.drawCard();
			// When 2 players are playing, nobody can have Chrysoprase
			if (players.size() == 2 && popped.get() == PersonalityCard.CHRYSOPRASE) {
				popped = personalityDeck.drawCard();
			}
			p.getValue().setPersonality(popped.get());

			// Based on the rules we have to add minions to these 3 regions:
			theShades.addMinion(p.getValue());
			theScours.addMinion(p.getValue());
			dollySisters.addMinion(p.getValue());
		}

		// Starter regions also have trouble.
		theShades.addTroubleMarker();
		theScours.addTroubleMarker();
		dollySisters.addTroubleMarker();

		// Set turn to first in game.
		currentTurn = 0;

		// Set game status as playing.
		status = GameStatus.PLAYING;
	}

	public Collection<BoardArea> getBoard() {
		return gameBoard.values();
	}

	/**
	 * Moves the game forward by one turn.
	 */
	public void turn() {
		int current = currentTurn;
		players.get(playerTurnOrder[current]).printTurn();
		currentTurn =  ((current + 1) == playerTurnOrder.length) ?
				0 : current + 1;
	}

	/**
	 * Get a collection of the game players.
	 * 
	 * @return the players in the game.
	 */
	public Collection<Player> getPlayers() {
		return players.values();
	}
	
	/**
	 * Returns the player who has the given color.
	 * @param c
	 * @return the player who has the given color.
	 */
	public Player getPlayerOfColor(Color c) {
		return players.get(c);
	}

	/**
	 * Get the player whose turn is next.
	 * 
	 * @return the player whose turn is next.
	 */
	public Player getCurrentTurn() {
		return players.get(playerTurnOrder[currentTurn]);
	}

	/**
	 * Get the deck of personality cards.
	 * 
	 * @return: deck of all personality cards
	 */
	public PersonalityDeck getPersonalityDeck() {
		return personalityDeck;
	}

	/**
	 * Get bank class used in the game.
	 * 
	 * @return the bank used in the game
	 */
	public Bank getBank() {
		return gameBank;
	}

	/**
	 * Get current status of the game.
	 * 
	 * @return current status of the game
	 */
	public GameStatus getStatus() {
		return status;
	}

	/**
	 * Each players turn has not been implemented so this actions A series of
	 * potential game maneuvers.
	 */
	void simulate() {
		/*
		 * areas.getCard("Small Gods").addTrouble();
		 * areas.getCard("Nap Hill").incTrolls();
		 * 
		 * areas.getCard("The Hippo").addMinion(players[1]);
		 * areas.getCard("The Hippo").addMinion(players[1]);
		 * 
		 * areas.getCard("Nap Hill").setBuilding(players[1]);
		 * 
		 * players[0].addPlayerCard(playerDeck.drawCard().get());
		 * players[0].addPlayerCard(playerDeck.drawCard().get());
		 * 
		 * players[1].addPlayerCard(playerDeck.drawCard().get());
		 */
	}

	/**
	 * Retrieves the total number of minions for the given player on the game
	 * board.
	 * 
	 * @param player
	 * @return the total numbers of minions for the given player on the game
	 *         board.
	 */
	public int getTotalNumberOfMinions(Player player) {
		// TODO Implement this method
		return 0;
	}
	
	/**
	 * Returns the total number of areas controlled by the given player.
	 * An area is controlled by a player if (s)he "has more playing pieces in it
	 * than any single other player (a playing piece being a minion or a building)
	 * and has more pieces than the total number of trolls in the area". An area that
	 * has at least one demon cannot be controlled.
	 * 
	 * @param player
	 * @return the total number of areas controlled by the given player.
	 */
	public int getNumberOfAreasControlled(Player player) {
		// TODO Implement this method
		return 0;
	}
	
	/**
	 * @return the total number of trouble markers currently placed on the board.
	 */
	public int getTotalNumberOfTroubleMarkers() {
		// TODO Implement this method
		return 0;
	}
	
	/**
	 * Checks if the draw pile still has cards.
	 * @return true if the size of the draw pile is non-zero, false othewise.
	 */
	public boolean hasPlayerCardsLeft() {
		return playerDeck.size() > 0;
	}

	/**
	 * Removes all pieces from the area with the given number.
	 * @param areaId
	 */
	public void removeAllPiecesFromArea(int areaId) {
		// TODO Implement this method
	}
	
	/**
	 * Executes the flooding on the game board:
	 * - Only areas adjacent to the river are affected
	 * - Players have to move their minions from the flooded areas to adjacent areas,
	 *   starting with the player currently taking a turn
	 * - Players can move their minions to areas adjacent to the river
	 * - Buildings, trolls and demons are not affected by the flood
	 * @param firstAreaId
	 * @param secondAreaId
	 */
	public void floodAreas(int firstAreaId, int secondAreaId) {
		// TODO Implement this method
	}
	
	/**
	 * Removes a building from the area with the given area ID.
	 * @param areaId
	 * @return true if a building was removed, false otherwise.
	 */
	public boolean burnBuilding(int areaId) {
		// TODO Implement this method
		return true;
	}
	
	/**
	 * Removes a building on the area with the given ID, if one exists.
	 * @param areaId
	 */
	public boolean removeBuilding(int areaId) {
		// TODO Implement this method
		return false;
	}
	
	/**
	 * Subtracts $2 from each player for each building they own on the board.
	 * If a player does not have enough money to pay for all of the buildings
	 * (s)he owns, the building(s) are removed from the board.
	 */
	public void handleSubsidence() {
		// TODO Implement this method
	}
	
	/**
	 * Disable the City Area Card corresponding to the passed areaID if it is in
	 * effect. Also remove one minion from this area.
	 * 
	 * @return true if the area card for the given area ID was in effect and got
	 *         disabled, false otherwise.
	 */
	public boolean disableAreaCard(int areaID) {
		// TODO Implement this method
		return false;
	}

	/**
	 * Remove one minion belonging to the given player from the area with the
	 * given area ID.
	 * 
	 * @param areaID
	 * @return true if a minion was removed, false otherwise.
	 */
	public boolean removeMinion(int areaID, Player player) {
		// TODO Implement this method
		return false;
	}
	
	/**
	 * Place a troll to the area with the given ID.
	 * 
	 * @param areaID
	 * @return true if the area where the troll was placed already contained a
	 *         minion, false otherwise.
	 */
	public boolean placeTroll(int areaID) {
		// TODO Implement this method
		return false;
	}
	
	/**
	 * Place a demon to the area witht the given ID.
	 * 
	 * @param areaID
	 * @return true if the demon was placed successfully, false otherwise.
	 */
	public boolean placeDemon(int areaID) {
		// TODO Implement this method
		return false;
	}
	
	/**
	 * Add a trouble marker to the area with the given ID.
	 * 
	 * @param areaID
	 * @return true if the trouble marker was added successfully, false
	 *         otherwise.
	 */
	public boolean addTroubleMarker(int areaID) {
		// TODO Implement this method
		return false;
	}

	/**
	 * Figures out which player has won based on the total number of points.
	 * The game is declared finished after that.
	 */
	public void finishOnPoints() {
		// TODO Implement this method
		status = GameStatus.FINISHED;
	}

}
