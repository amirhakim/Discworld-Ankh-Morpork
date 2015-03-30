package gameplay;

import io.TextUserInterface;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import util.Color;
import util.Interrupt;
import card.Deck;
import card.city.AnkhMorporkArea;
import card.personality.PersonalityCard;
import card.personality.PersonalityDeck;
import card.player.DiscardPile;
import card.player.GreenPlayerCard;
import card.player.PlayerDeck;
import card.player.Symbol;
import card.random.RandomEventCard;
import card.random.RandomEventDeck;
import error.InvalidGameStateException;

/**
 * <b>This class represents the bulk of the actions available in the game.<br> It sets
 * up the game, and provides a layer of access to the game components for the
 * controller.</b>
 * 
 * @author Team 10 - SOEN6441
 * @version 2.0
 */
public class Game {

	/**
	 * Every time a player cannot pay back for a loan card, 12 points are deducted
	 * from his/her final score.
	 */
	private static final int LOAN_PENALTY = 12;
	
	/**
	 * Each minion on the board gives 5 points.
	 */
	private static final int MINION_POINTS = 5;

	private Bank gameBank;

	private Map<Color, Player> players;
	
	private Color[] playerTurnOrder;

	private PersonalityDeck personalityDeck;

	private PlayerDeck playerDeck;
	
	private DiscardPile discardPile;
	
	private Map<GreenPlayerCard, Color> interrupts;
	
	/**
	 * <b>For most, if not all, use cases, we need to alter the state
	 * of a board area based on its area code (which is the value after
	 * rolling the die).<br> So it makes most sense to keep areas in an
	 * Integer -> BoardArea map.</b>
	 */
	private Map<Integer, BoardArea> gameBoard;

	private RandomEventDeck randomEventDeck;

	/**
	 * <b>Identifies general game status, if it is initiated or not.</b>
	 */
	private GameStatus status;

	/**
	 * <b>Identifies whose turn it is, uses index in the player array.</b>
	 */
	private int currentTurn;

	private GreenPlayerCard currentCardInPlay;

	public Game() {
		status = GameStatus.UNINITIATED;
		gameBoard = new HashMap<Integer, BoardArea>();
		currentCardInPlay = null;
		interrupts = new HashMap<GreenPlayerCard, Color>();
	}

	/**
	 * <b>This method sets up game. Game has not started, but the deck is set up.</b>
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

			playerTurnOrder = new Color[numberOfPlayers];
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
		personalityDeck = new PersonalityDeck();
		discardPile = new DiscardPile();

		status = GameStatus.READY;
	}

	/**
	 * <b>This method performs the necessary actions prior to the start of a new game.</b>
	 */
	public void init() {
		// Give each player their money, personality and initial minions.
		// Cards that have an initial state.
		BoardArea theShades = gameBoard.get(AnkhMorporkArea.THE_SHADES.getAreaCode());
		BoardArea theScours = gameBoard.get(AnkhMorporkArea.THE_SCOURS.getAreaCode());
		BoardArea dollySisters = gameBoard.get(AnkhMorporkArea.DOLLY_SISTERS.getAreaCode());

		for (Entry<Color, Player> p : players.entrySet()) {
			// Deal out 10 dollars
			p.getValue().increaseMoney(10);
			gameBank.decreaseBalance(10);

			// Deal personality card.
			assignPersonality(p.getValue());

			// Based on the rules we have to add minions to these 3 regions:
			theShades.addMinion(p.getValue());
			theScours.addMinion(p.getValue());
			dollySisters.addMinion(p.getValue());
			
			// Deal 5 cards to each player in the beginning
			// TODO: Need to exclude the "Hubert" and "Cosmos Lavish" brown-bordered
			//		 player cards later when we do them 
			for (int i = 0; i < Player.PLAYER_MAX_HAND_SIZE; i++) {
			//	p.getValue().addPlayerCard(playerDeck.drawCard().get());
				addPlayerCard(p.getValue());
			}
		}

		// Starter regions also have trouble.
		theShades.addTroubleMarker();
		theScours.addTroubleMarker();
		dollySisters.addTroubleMarker();

		// Decide who the first player in the game is
		currentTurn = Die.getDie().determineFirstPlayer(players.size());

		status = GameStatus.PLAYING;
	}

	/**
	 * <b>Deal a player a personality card from deck.</b>
	 * @param p Player
	 */
	public void assignPersonality(Player p) {
		Optional<PersonalityCard> popped = personalityDeck.drawCard();
		// When 2 players are playing, nobody can have Chrysoprase
		if (players.size() == 2 && popped.get() == PersonalityCard.CHRYSOPRASE) {
			popped = personalityDeck.drawCard();
		}
		p.setPersonality(popped.get());
	}
	
	public Collection<BoardArea> getBoard() {
		return gameBoard.values();
	}
		
	/**
	 * <b>Similar to getBoard but returns areas with indexes.</b>
	 */
	public Map<Integer, BoardArea> getGameBoard() {
		return gameBoard;
	}
	
	/**
	 * <b>Get a collection of the game players.</b>
	 * 
	 * @return the players in the game.
	 */
	public Collection<Player> getPlayers() {
		return players.values();
	}
	
	/**
	 * <b>Returns the player who has the given color.</b>
	 * @param c
	 * @return the player who has the given color.
	 */
	public Player getPlayerOfColor(Color c) {
		return players.get(c);
	}
	
	/**
	 * <b>Moves the game forward by one turn and returns the player
	 * whose turn it currently is.</b>
	 * @return the player whose turn it currently is.
	 */
	public Player advanceTurnToNextPlayer() {
		int current = currentTurn;
		//players.get(playerTurnOrder[current]).printTurn();
		currentTurn =  ((current + 1) == playerTurnOrder.length) ?
				0 : current + 1;
		return getPlayerOfCurrentTurn();
	}
	
	/**
	 * <b>Get the player whose turn it currently is.</b>
	 * @return the player whose turn it currently is.
	 */
	public Player getPlayerOfCurrentTurn() {
		return players.get(playerTurnOrder[currentTurn]);
	}
	
	/**
	 * @return a shifted view of the players' order so that it starts
	 * with the player whose turn it currently is. This is useful in cases
	 * where, for example, a random event happens and each player has to 
	 * perform an action in sequence. 
	 * 
	 * <p>For example, if the Blue player is playing
	 * and the original order is Red -> Blue -> Green -> Yellow, the view returned is
	 * Blue -> Green -> Yellow.</p>
	 */
	public Color[] getPlayersFromCurrentPlayer() {
		Color[] shiftedPlayers = new Color[playerTurnOrder.length];
		int i = 0;
		for (int j = currentTurn; j < shiftedPlayers.length; j++) {
			shiftedPlayers[i] = playerTurnOrder[j];
			i++;
		}
		for (int j = 0; j < currentTurn; j++) {
			shiftedPlayers[i] = playerTurnOrder[0];
			i++;
		}
		return shiftedPlayers;
	}
	
	/**
	 * <b>Brings the given player's hand size back to 5 if it is less than that.<br>
	 * If no more player cards are available, the player's hand is left as it is
	 * at the time when the cards are up (the game should end after the player's
	 * turn is finished).</b>
	 * @param p the player whose hand size must be restored.
	 */
	public void restorePlayerHand(Player p) {
		while (hasPlayerCardsLeft() && 
				p.getHandSize() < Player.PLAYER_MAX_HAND_SIZE) {
			//actualPlayer.addPlayerCard(playerDeck.drawCard().get());
			addPlayerCard(p);
		}
	}

	
	/**
	 * <b>Draws player cards.<br>
	 * Adds the given player's hand size (i) if it is less than five.<br>
	 * If no more player cards are available, the player's hand is left as it is
	 * at the time when the cards are up (the game should end after the player's
	 * turn is finished). </b>
	 * @param p the player whose hand size must be restored.
	 */
	public void addPlayerCard(Player p , int i){
		Player actualPlayer = players.get(p.getColor());
		while(i>0){
			if (hasPlayerCardsLeft()) {
				GreenPlayerCard card = playerDeck.drawCard().get();
				addPlayerCard(actualPlayer, card);
				i--;
			} else {
				System.out.println("Out of cards");
				break;
			}
		}
	}
	
	public void addPlayerCard(Player player, GreenPlayerCard card) {
		if(card.getSymbols().contains(Symbol.INTERRUPT)) {
			addInterrupt(card, player);
		}
		player.addPlayerCard(card);
	}
	
	public void addPlayerCard(Player player){
		addPlayerCard(player, 1);
	}
	
	/**
	 * <b>Draws a player card.</b>
	 * 
	 * @return an object that contains either the player card drawn or
	 *         nothing, if the deck is out of cards.
	 */	
	public Optional<GreenPlayerCard> drawPlayerCard() {
		 return playerDeck.drawCard();
	}
	
	/**
	 * <b>Draws a random event card.</b>
	 * 
	 * @return an object that contains either the random event card drawn or
	 *         nothing, if the deck is out of cards.
	 */
	public Optional<RandomEventCard> drawRandomEventCard() {
		 return randomEventDeck.drawCard();
	}

	/**
	 * <b>Get the deck of personality cards.</b>
	 * 
	 * @return: deck of all personality cards
	 */
	public PersonalityDeck getPersonalityDeck() {
		return personalityDeck;
	}

	/**
	 * <b>Get bank class used in the game.</b>
	 * 
	 * @return the bank used in the game
	 */
	public Bank getBank() {
		return gameBank;
	}

	/**
	 * <b>Get current status of the game.</b>
	 * 
	 * @return current status of the game
	 */
	public GameStatus getStatus() {
		return status;
	}

	/**
	 * <b>Retrieves the total number of minions for the given player on the game
	 * board.</b>
	 * 
	 * @param player
	 * @return the total numbers of minions for the given player on the game
	 *         board.
	 */
	public int getTotalMinionCountForPlayer(Player player) {
		return gameBoard.values().stream()
				.map(area -> area.getMinionCountForPlayer(player))
				.reduce(0, (sumSoFar, next) -> sumSoFar + next);
	}
	
	/**
	 * @return true if the given player has a minion in the given area, false otherwise.
	 */
	public boolean hasMinionInArea(AnkhMorporkArea a, Color p) {
		return gameBoard.get(a.getAreaCode()).getMinionCountForPlayer(getPlayerOfColor(p)) > 0;
	}
	
	/**
	 * <b>Returns the total number of areas controlled by the given player.<br>
	 * An area is controlled by a player if (s)he "has more playing pieces in it
	 * than any single other player (a playing piece being a minion or a building)
	 * and has more pieces than the total number of trolls in the area".<br> An area that
	 * has at least one demon cannot be controlled.</b>
	 * 
	 * @param player
	 * @return the total number of areas controlled by the given player.
	 */
	public int getNumberOfAreasControlled(Player player) {
		int count = 0;
		for (BoardArea ba : gameBoard.values()) {
			if (ba.isControlledBy(player))
				count++;
		}
		return count;
	}
	
	/**
	 * 
	 * @param player
	 * @return Map of boardAreas which player has minions on
	 */
	public Map<Integer, BoardArea> getAreasWithPlayerMinions(Player player) {
		Map<Integer, BoardArea> playersAreas = new HashMap<Integer, BoardArea>();
		
		for (BoardArea ba : gameBoard.values()) {
			AnkhMorporkArea area = ba.getArea();
			if (ba.getMinionCountForPlayer(player) > 0) {
				playersAreas.put(area.getAreaCode(), ba);
			}
		}
		
		return playersAreas;
	}
	
	/**
	 * 
	 * @param player
	 * @return Map of areas player CAN place a minion
	 */
	public Map<Integer, BoardArea> getMinionPlacementAreas(Player player) {
		if(player.getMinionCount() == Player.TOTAL_MINIONS) {
			return gameBoard;
		}
		
		Map<Integer, BoardArea> possibleAreas = new HashMap<Integer, BoardArea>();

		for (BoardArea ba : gameBoard.values()) {
			AnkhMorporkArea area = ba.getArea();
			if (ba.getMinionCountForPlayer(player) != 0) {
				possibleAreas.put(area.getAreaCode(), ba);
				Map<Integer, BoardArea> neighbours = getNeighbours(ba);
				possibleAreas.putAll(neighbours);
			}
		}

		return possibleAreas;
	}
	
	/**
	 * @return a copy of the minions that exist in the given area. 
	 */
	public Optional<Map<Color, Integer>> getMinionsInArea(AnkhMorporkArea a) {
		return gameBoard.get(a.getAreaCode()).getMinionCount() > 0 ?
				Optional.of(new HashMap<>(gameBoard.get(a.getAreaCode()).getMinions())) 
					: Optional.empty();
	}

	public int getMinionCountForArea(AnkhMorporkArea a) {
		return gameBoard.get(a.getAreaCode()).getMinionCount();
	}
	
	/**
	 * 
	 * @param boardArea
	 * @return Map of baordArea neighbouring to boardArea
	 */
	public Map<Integer, BoardArea> getNeighbours(BoardArea boardArea) {
		Map<Integer, BoardArea> neighbours = new HashMap<Integer, BoardArea>();
		for (BoardArea otherBoardArea : gameBoard.values()) {
			if (boardArea.isNeighboringWith(otherBoardArea)) {
				neighbours.put(otherBoardArea.getArea().getAreaCode(),
						otherBoardArea);
			}
		}
		return neighbours;
	}
	
	/**
	 * @return the total number of trouble markers currently placed on the board.
	 */
	public int getTotalNumberOfTroubleMarkers() {
		return gameBoard
				.values()
				.stream()
				.map(area -> area.hasTroubleMarker() ? 1 : 0)
				.reduce(0, (partialSum, current) -> partialSum + current);
	}
	
	/**
	 * <b>Checks if the draw pile still has cards.</b>
	 * @return true if the size of the draw pile is non-zero, false othewise.
	 */
	public boolean hasPlayerCardsLeft() {
		return playerDeck.size() > 0;
	}

	/**
	 * <b>Removes all pieces from the area with the given number (see 
	 * {@link BoardArea#clearAllPieces()}).</b>
	 * @param areaId the area from which all pieces will be removed.
	 */
	public void removeAllPiecesFromArea(int areaId) {
		gameBoard.get(areaId).clearAllPieces();
	}
	
	/**
	 * <b>Finds all buildings a player owns.</b>
	 * @param player
	 * @return Map of boardAreas owned by the player
	 */
	public Map<Integer, BoardArea> getBuildingAreas(Player player) {
		Map<Integer, BoardArea> buildingAreas = new HashMap<Integer, BoardArea>();
		
		for(BoardArea boardArea: gameBoard.values()) {
			if(boardArea.getBuildingOwner() == player.getColor()) {
				buildingAreas.put(boardArea.getArea().getAreaCode(), boardArea);
			}	
		}
		
		return buildingAreas;	
	}
	
	/**
	 * 
	 * @return Map of areas that a player could use assassinate on
	 */
	public Map<Integer, BoardArea> getTroubleAreas() {
		Map<Integer, BoardArea> possibilities = new HashMap<Integer, BoardArea>();
		
		for(BoardArea boardArea : gameBoard.values()) {
			if(boardArea.hasTroubleMarker()) {
				possibilities.put(boardArea.getArea().getAreaCode(), boardArea);
			}
		}
		return possibilities;
	}
	
	/**
	 * 
	 * @return Map of all areas which have no buildings
	 */
	public Map<Integer, BoardArea> getBuildingFreeAreas(Player player) {
		Map<Integer, BoardArea> freeAreas = new HashMap<Integer, BoardArea>();

		for (BoardArea boardArea : gameBoard.values()) {
			if (boardArea.getBuildingOwner() == Color.UNDEFINED &&
					boardArea.hasTroubleMarker() == false &&
					boardArea.getMinionCountForPlayer(player) != 0) {
				freeAreas.put(boardArea.getArea().getAreaCode(), boardArea);
			}
		}

		return freeAreas;
	}
	
	public void setCurrentCardInPlay(GreenPlayerCard c) {
		this.currentCardInPlay = c;
	}
	
	public GreenPlayerCard getCurrentCardInPlay() {
		return this.currentCardInPlay;
	}
	
	/**
	 * <b>Removes a building on the area with the given ID, if one exists and 
	 * also removes the corresponding city card form the player's hand.</b>
	 * 
	 * @return true if the building was removed successfully in this area, false
	 *         otherwise.
	 */
	public boolean removeBuilding(int areaId) {
		BoardArea a = gameBoard.get(areaId);
		if (a.hasBuilding()) {
			Player owner = getPlayerOfColor(a.getBuildingOwner());
			a.removeBuilding();
			owner.increaseBuildings();
			owner.removeCityCard(a.getArea());
			return true;
		}
		return false;
	}
	
	/**
	 * <b>Completes a transaction by giving money to the given player from the game's bank.</b>
	 */
	public void givePlayerMoneyFromBank(Player p, int amount) {
		if (gameBank.decreaseBalance(amount)) {
			p.increaseMoney(amount);
		}
	}

	/**
	 * <b>Completes a transaction by taking money from the given player and storing it 
	 * in the game's bank.</b>
	 */
	public void giveBankMoneyFromPlayer(Player p, int amount) {
		if (gameBank.decreaseBalance(amount)) {
			p.increaseMoney(amount);
		}
	}

	/**
	 * 
	 * @param player
	 * @param boardArea
	 * @return true if adding building was success
	 */
	public boolean addBuilding(Player player, BoardArea boardArea) {
		if (boardArea.addBuildingForPlayer(player)) {
			if (player.decreaseMoney(boardArea.getBuildingCost())) {
				getBank().increaseBalance(boardArea.getBuildingCost());
				player.addCityCard(boardArea.getArea());
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * <b>All players must pay $2 for each building they have on the
	 * board.<br> If they cannot pay for a building then it is removed
	 * from the board. </b>
	 */
	public void handleSubsidence() {
		for (BoardArea a : gameBoard.values()) {
			if (a.getBuildingOwner() != Color.UNDEFINED) {
				Player p = players.get(a.getBuildingOwner());
				if (p.getMoney() - 2 >= 0) {
					p.decreaseMoney(2);
				} else {
					removeBuilding(a.getArea().getAreaCode());
				}
			}
		}
	}
	
	/**
	 * Disable the City Area Card corresponding to the passed areaID, if it is in
	 * effect.
	 * 
	 * @return Some(<player who owned the City Area Card if it was in effect>), None otherwise.
	 */
	public Optional<Player> disableAreaCard(int areaID) {
		BoardArea a = gameBoard.get(areaID);
		AnkhMorporkArea area = AnkhMorporkArea.forCode(areaID);
		if (a.hasBuilding()) {
			Player owner = players.get(a.getBuildingOwner());
			return (owner.removeCityCard(area)) ? 
				Optional.of(owner) : Optional.empty();
		}
		return Optional.empty();
	}

	/**
	 * Adds a minion for the given player in the area with the given area code.
	 */
	public void addMinion(int areaID, Player player) {
		gameBoard.get(areaID).addMinion(player);
	}
	
	/**
	 * <b>Remove one minion belonging to the given player from the area with the
	 * given area ID.</b>
	 * 
	 * @param areaID
	 * @return true if a minion was removed, false otherwise.
	 */
	public boolean removeMinion(int areaID, Player player) {
		return gameBoard.get(areaID).removeMinion(player);
	}
	
	/**
	 * <b:Place a troll to the area with the given ID.</b>
	 * 
	 * @param areaID
	 * @return true if the area where the troll was placed already contained a
	 *         minion, false otherwise.
	 */
	public boolean placeTroll(int areaID) {
		return gameBoard.get(areaID).addTroll();
	}
	
	/**
	 * <b>Place a demon to the area witht the given ID.</b>
	 * 
	 * @param areaID
	 * @return true if the demon was placed successfully, false otherwise.
	 */
	public boolean placeDemon(int areaID) {
		return gameBoard.get(areaID).addDemon();
	}
	
	/**
	 * <b>Add a trouble marker to the area with the given ID.</b>
	 * 
	 * @param areaID
	 * @return true if the trouble marker was added successfully, false
	 *         otherwise.
	 */
	public boolean addTroubleMarker(int areaID) {
		return gameBoard.get(areaID).addTroubleMarker();
	}

	/**
	 * Checks if the game is over due to a player having won by meeting the conditions
	 * indicated on his/her Personality card.
	 * @param p the player whose winning condition will be checked 
	 * @return true if the game is over because the given player has won, false otherwise.
	 */
	public boolean hasPlayerWon(Player p) {
		return p.getPersonality().hasWon(players.size(), p, this);
	}
	
	/**
	 * Finishes the game on points either due to not having any player cards left
	 * on the draw pile or because of Riots.
	 * @param checkForEmptyDeck
	 * @return a list containing one or multiple winners. If there are still cards
	 * on the draw pile, an empty list is returned.
	 */
	public List<Player> finishGameOnPoints(boolean checkForEmptyDeck) {
		if (checkForEmptyDeck && hasPlayerCardsLeft()) {
			return Collections.emptyList();
		}
		status = GameStatus.FINISHED;
		return getWinnersByPoints();
	}

	/**
	 * <b>Figures out which player has won based on the total number of points:<br>
	 * - Each minion on the board is worth five points. <br>
	 * - Each building is worth a number of points equal to its monetary cost. Each $1 in hand
	 *   is worth one point. <br>
	 * - If you have the Dent card or the Bank card then you must pay back the amount noted on the card. <br>
	 * - If you cannot do so then you lose fifteen points each. <br>
	 * - In the case of a tie the tied player with the highest monetary value City Area card is the winner. <br>
	 * - If there is still a tie then the tied players shared the honours of a joint win.<br>
	 * 
	 * The game is declared finished after that.</b>
	 * 
	 * @return the player(s) who won the game based on points.
	 */
	public List<Player> getWinnersByPoints() {
		Map<Integer, List<Player>> pointsToPlayers =
				players.values().stream().collect(Collectors.groupingBy(p -> getPlayerPoints(p)));
		int maxPoints = pointsToPlayers.keySet().stream().max((a, b) -> a - b).get();
		if (pointsToPlayers.get(maxPoints).size() == 1) {
			System.out.println("The game has a winner with " + maxPoints + " points.");
			return pointsToPlayers.get(maxPoints);
		}
			
		// Tie break: Check the highest-value building card owned
		Map<Integer, List<Player>> highestBuildingsToPlayers =
				players.values().stream().collect(Collectors.groupingBy(p -> p.getMoney()));
		int highestBuildingValue = highestBuildingsToPlayers.keySet().stream().max((a, b) -> a - b).get();
		if (highestBuildingsToPlayers.get(highestBuildingValue).size() == 1) {
			System.out.println("The game has one winner with a building of value $" + highestBuildingValue + ".");
		} else {
			System.out.println("There are multiple winners to the game (same $, highest building value).");
		}

		return highestBuildingsToPlayers.get(highestBuildingValue);
	}

	/**
	 * <b>Retrieves the total number of points for the given player.<br>
	 * - Each minion on the board is worth five points. <br>
	 * - Each building is worth a number of points equal to its monetary cost. Each $1 in hand
	 *   is worth one point. <br>
	 * - If you have the Mr. Bent card or the Bank card then you must pay back the amount noted on the card. <br>
	 * - If you cannot do so then you lose fifteen points each. </b>
	 * 
	 * @return the number of points for the given player.
	 */
	public int getPlayerPoints(Player p) {
		int points = gameBoard
				.values()
				.stream()
				.map(area -> (area.getMinionCountForPlayer(p) * MINION_POINTS + (area
						.getBuildingOwner() == p.getColor() ? area
						.getBuildingCost() : 0)))
				.reduce(0, (s, areaPoints) -> s + areaPoints);
		
		
		int playerMoneyMinusLoans = p.getMoney() - p.getLoanBalance();
		if (playerMoneyMinusLoans < 0) {
			points -= LOAN_PENALTY
					* ((Math.abs(playerMoneyMinusLoans) + Bank.LOAN_REPAY_AMOUNT)
					/ Bank.LOAN_REPAY_AMOUNT);
		}
		
		return points;
	}

	/**
	 * 
	 * @return discardPile used by game
	 */
	public DiscardPile getDiscardPile() {
		return discardPile;
	}
	
	/**
	 * If not asking for too many cards, draw these cards and assign to
	 * player
	 * 
	 * @param player
	 * @param numberOfCards
	 * @return false if asking for too many cards
	 */
	public boolean drawDiscardCards(Player player, int numberOfCards) {

		if (getDiscardPile().size() >= numberOfCards) {
			while (numberOfCards > 0) {
				player.addPlayerCard(discardPile.drawCard().get());
				numberOfCards--;
			}
			return true;
		} else {
			return false;
		}

	}
	
	/**
	 * <b>Discard card by adding it to the pile.</b>
	 * 
	 * @param card
	 */
	public boolean discardCard(GreenPlayerCard card, Player p) {
		if (p.getUnplayableCards().contains(card)) {
			return false;
		}
		removePlayerCard(card, p);
		discardPile.addCard(card);
		return true;
	}

	/**
	 * Remove player card from hand Similar to discarCard but doesnt add card to
	 * pile Ideal for giving a card to another player
	 * 
	 * @param card
	 * @param p
	 */
	public void removePlayerCard(GreenPlayerCard card, Player p) {
		if (p != null) {
			p.removePlayerCard(card);
			// Remove interrupt listener
			if (card.getSymbols().contains(Symbol.INTERRUPT)) {
				removeInterrupt(card);
			}
		}
	}

	public Map<Color, Player> getPlayersMap(){
		return this.players;
	}

	/**
	 * 
	 * @return game player deck
	 */
	public Deck<GreenPlayerCard> getPlayerDeck() {
		return this.playerDeck;
	}

	
	/**
	 * GASPODE && FRESH START
	 * @param interrupt
	 * @param affectedPlayer
	 * @param affectedArea
	 * @return true if interrupt was played
	 */
	public boolean notifyInterrupt(Interrupt interrupt, Player affectedPlayer, BoardArea affectedArea) {
		GreenPlayerCard interruptCard = null;

		boolean willPlay = setUpNotify(GreenPlayerCard.GASPODE, affectedPlayer,
				interrupt, Interrupt.ASSASINATION);

		if (willPlay) {
			affectedArea.addMinion(affectedPlayer);
			interruptCard = GreenPlayerCard.GASPODE;
		}

		if (!willPlay) {
			willPlay = setUpNotify(GreenPlayerCard.THE_FRESH_START_CLUB,
					affectedPlayer, interrupt, Interrupt.ASSASINATION);
		}

		// Check if will play this card
		// Important to check that interrupt card isn't already set
		// if it is, we've already played a card
		if (willPlay && interruptCard == null) {
			// get Areas to place minion
			Map<Integer, BoardArea> possibilities = getMinionPlacementAreas(affectedPlayer);
			ArrayList<Integer> excludeList = new ArrayList<Integer>();
			excludeList.add(affectedArea.getArea().getAreaCode());
			TextUserInterface UI = new TextUserInterface();
			BoardArea chosenArea = UI.getAreaChoice(possibilities,
					"Select area to replace assasinated minion.",
					"Choose area:", true, excludeList);
			chosenArea.addMinion(affectedPlayer);
			interruptCard = GreenPlayerCard.THE_FRESH_START_CLUB;
		}

		if (willPlay) {
			discardCard(interruptCard, affectedPlayer);

		}
		return willPlay;
	}
	
	public boolean notifyInterrupt(Interrupt interrupt, Player affectedPlayer) {
		GreenPlayerCard interruptCard = null;
		boolean willPlay = setUpNotify(GreenPlayerCard.WALLACE_SONKY,
				affectedPlayer, interrupt, Interrupt.SCROLL);
		if(willPlay) {
			// Nothing to do here since this notify is done before the scroll gets played
			// just remove card
			interruptCard = GreenPlayerCard.WALLACE_SONKY;
			discardCard(interruptCard, affectedPlayer);
		}

		return willPlay;
	}

	/**
	 * Helper function to find out if interrupt will be played
	 * 
	 * @param card
	 *            Card that has interrupt symbol
	 * @param affectedPlayer
	 *            Player that may be able to play interrupt card
	 * @param interrupt
	 *            Interrupt that was signaled from elsewhere
	 * @param searchInterrupt
	 *            Interrupt that if found allows some change to take place
	 * @param played
	 *            If a previous interrupt has been played in this turn
	 * @return
	 */
	public boolean setUpNotify(GreenPlayerCard card, Player affectedPlayer,
			Interrupt interrupt, Interrupt searchInterrupt) {

		if (interrupt == searchInterrupt) {
			Color playerColor = interrupts.get(card);
			if (playerColor != null) {
				Player player = getPlayerOfColor(playerColor);
				if (affectedPlayer.getColor() == player.getColor()) {
					TextUserInterface UI = TextUserInterface.getUI();
					System.out.println(affectedPlayer.getColor().getAnsi());
					if (UI.playInterrupt(affectedPlayer, card)) {
						System.out.println(getPlayerOfCurrentTurn().getColor().getAnsi());	
						return true;
					}
				}
			}
		}
		return false;
	}

	public void addInterrupt(GreenPlayerCard card, Player player) {
		interrupts.put(card, player.getColor());
	}

	public void removeInterrupt(GreenPlayerCard card) {
		if (interrupts.get(card) != null)
			interrupts.remove(card);
	}
	
	public Map<GreenPlayerCard, Color> getInterrupts() {
		return interrupts;
	}
	
}
