package gameplay;

import java.util.Optional;

import util.Color;
import card.Area;
import card.CityDeck;
import card.PersonalityCard;
import card.PersonalityDeck;
import card.PlayerDeck;
import card.RandomEventDeck;
import error.InvalidGameStateException;

/**
 * This class represents the bulk of the actions available in the game. It sets
 * up the game, and provides a layer of access to the game components for the
 * controller.
 */
public class Game {

	// Game Components.
	private Bank gameBank;
	private Player[] players;
	private PersonalityDeck personalityDeck;
	private PlayerDeck playerDeck;
	private CityDeck areas;
	
	private RandomEventDeck events;

	// Identifies general game status, if it is initiated or not
	private GameStatus status;
	// Identifies whose turn it is, uses index in the player array.
	private int currentTurn;

	public Game() {
		status = GameStatus.UNINITIATED;
	}

	/**
	 * Set up game. Game has not started, but the deck is set up.
	 * 
	 * @param: numberOfPlayers how many people will be playing game
	 * @param: playerNames array of what everybodys name is
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
			players = new Player[numberOfPlayers];

			for (int i = 0; i < players.length; ++i) {
				Player p = new Player();
				p.setName(playerNames[i]);
				p.setColor(Color.forCode(i));
				players[i] = p;
			}
		}

		personalityDeck = new PersonalityDeck();
		playerDeck = new PlayerDeck();
		areas = new CityDeck();
		events = new RandomEventDeck();

		status = GameStatus.READY;
	}

	/**
	 * Start a new game.
	 */
	public void init() {
		// Give each player their money, personality and initial minions.
		// Cards that have an initial state.
		Area Shades = (areas.getCard("The Shades"));
		Area theScoures = (areas.getCard("The Scoures"));
		Area dollySisters = (areas.getCard("Dolly Sisters"));

		for (int i = 0; i < players.length; ++i) {
			// Deal out 10 dollars.
			players[i].increaseMoney(10);
			gameBank.decreaseBalance(10);

			Optional<PersonalityCard> popped = personalityDeck.drawCard();
			// TODO We have to have a check for an empty deck somewhere here
			players[i].setPersonality(popped.get());

			// Add minions to shades, sources and dolly sister regions.
			Shades.addMinion(players[i]);
			theScoures.addMinion(players[i]);
			dollySisters.addMinion(players[i]);

		}

		// Starter regions also have trouble.
		Shades.addTrouble();
		theScoures.addTrouble();
		dollySisters.addTrouble();

		// Set turn to first in game.
		currentTurn = 0;

		// Set game status as playing.
		status = GameStatus.PLAYING;
	}

	/**
	 * Move the game forward by one turn.
	 */
	public void turn() {
		int current = currentTurn;
		players[current].turn();
		if ((current + 1) == players.length) {
			currentTurn = 0;
		} else {
			currentTurn = current + 1;
		}
	}

	/**
	 * @return: Player[] Array of players in game
	 */
	public Player[] getPlayers() {
		return players;
	}

	/**
	 * 
	 * @return Player class of next turn
	 */
	public Player getCurrentTurn() {
		return players[currentTurn];
	}

	/**
	 * @return: Deck of all personalities
	 */
	public PersonalityDeck getPersonalityDeck() {
		return personalityDeck;
	}

	/**
	 * @return Bank class used in the game.
	 */
	public Bank getBank() {
		return gameBank;
	}

	/**
	 * 
	 * @return current game status
	 */
	public GameStatus getStatus() {
		return status;
	}

	/**
	 * 
	 * @return deck of city cards
	 */
	public CityDeck getCities() {
		return areas;
	}

	/**
	 * Gets the Player of the given color.
	 * 
	 * @param c
	 *            - the expected Player color
	 * @return
	 */
	public Player getPlayerOfColor(Color c) {
		// TODO Change the players from an array to a list and then change this
		// to an HOO call
		for (Player p : players) {
			if (p.getColor() == c) {
				return p;
			}
		}

		return null;
	}

	/**
	 * Each players turn has not been implemented so this actions A series of
	 * potential game maneuvers.
	 */
	void simulate() {
		/*areas.getCard("Small Gods").addTrouble();
		areas.getCard("Nap Hill").incTrolls();

		areas.getCard("The Hippo").addMinion(players[1]);
		areas.getCard("The Hippo").addMinion(players[1]);

		areas.getCard("Nap Hill").setBuilding(players[1]);

		players[0].addPlayerCard(playerDeck.drawCard().get());
		players[0].addPlayerCard(playerDeck.drawCard().get());

		players[1].addPlayerCard(playerDeck.drawCard().get());
	*/}
}
