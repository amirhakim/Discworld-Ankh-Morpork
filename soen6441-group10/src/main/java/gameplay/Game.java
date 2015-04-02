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
import java.util.function.BiFunction;
import java.util.function.Function;
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
 * This class represents the bulk of the actions available in the game.<br> It sets
 * up the game, and provides a layer of access to the game components for the
 * controller.
 * 
 * @author Team 10 - SOEN6441
 * @version 2.0
 */
public class Game {

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
	 * For most, if not all, use cases, we need to alter the state
	 * of a board area based on its area code (which is the value after
	 * rolling the die).<br> So it makes most sense to keep areas in an
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

	private GreenPlayerCard currentCardInPlay;

	public Game() {
		status = GameStatus.UNINITIATED;
		gameBoard = new HashMap<>();
		currentCardInPlay = null;
		interrupts = new HashMap<>();
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
	 * This method performs the necessary actions prior to the start of a new game.
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
	 * Deal a player a personality card from deck.
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
	 * Similar to getBoard but returns areas with indexes.
	 */
	public Map<Integer, BoardArea> getGameBoard() {
		return gameBoard;
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
	 * Moves the game forward by one turn and returns the player
	 * whose turn it currently is.
	 * @return the player whose turn it currently is.
	 */
	public Player advanceTurnToNextPlayer() {
		int current = currentTurn;
		currentTurn =  ((current + 1) == playerTurnOrder.length) ?
				0 : current + 1;
		return getPlayerOfCurrentTurn();
	}
	
	/**
	 * Get the player whose turn it currently is.
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
	 * Brings the given player's hand size back to 5 if it is less than that.<br>
	 * If no more player cards are available, the player's hand is left as it is
	 * at the time when the cards are up (the game should end after the player's
	 * turn is finished).
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
	 * Draws player cards.<br>
	 * Adds the given player's hand size (i) if it is less than five.<br>
	 * If no more player cards are available, the player's hand is left as it is
	 * at the time when the cards are up (the game should end after the player's
	 * turn is finished). 
	 * @param p the player whose hand size must be restored.
	 */
	public boolean addPlayerCard(Player p , int i){
		while(i>0){
			if (hasPlayerCardsLeft()) {
				GreenPlayerCard card = playerDeck.drawCard().get();
				addPlayerCard(p, card);
				i--;
			} else {
				System.out.println("Out of cards");
				return false;
			}
		}
		return true;
	}
	
	public void addPlayerCard(Player player, GreenPlayerCard card) {
		if(card.getSymbols().contains(Symbol.INTERRUPT)) {
			addInterrupt(card, player);
		}
		player.addPlayerCard(card);
	}
	
	public boolean addPlayerCard(Player player){
		return addPlayerCard(player, 1);
	}
	
	/**
	 * Draws a player card.
	 * DO NOT USE!  SEE ADD PLAYER CARD ABOVE
	 * @return an object that contains either the player card drawn or
	 *         nothing, if the deck is out of cards.
	 */	
	public Optional<GreenPlayerCard> drawPlayerCard() {
		 return playerDeck.drawCard();
	}
	
	/**
	 * Draws a random event card.
	 * 
	 * @return an object that contains either the random event card drawn or
	 *         nothing, if the deck is out of cards.
	 */
	public Optional<RandomEventCard> drawRandomEventCard() {
		 return randomEventDeck.drawCard();
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
	 * Retrieves the total number of minions for the given player on the game
	 * board. Only areas that do not contain demons contribute towards that number.
	 * 
	 * @param player
	 * @return the total numbers of minions for the given player on the game
	 *         board.
	 */
	public int getTotalMinionCountForPlayer(Player player) {
		return gameBoard.values().stream()
				.map(area -> (area.getDemonCount() == 0) ? area.getMinionCountForPlayer(player) : 0)
				.reduce(0, (sumSoFar, next) -> sumSoFar + next);
	}
	
	/**
	 * @return true if the given player has a minion in the given area, false otherwise.
	 */
	public boolean hasMinionInArea(AnkhMorporkArea a, Color p) {
		return gameBoard.get(a.getAreaCode()).getMinionCountForPlayer(getPlayerOfColor(p)) > 0;
	}
	
	/**
	 * Returns the total number of areas controlled by the given player.<br>
	 * An area is controlled by a player if (s)he "has more playing pieces in it
	 * than any single other player (a playing piece being a minion or a building)
	 * and has more pieces than the total number of trolls in the area".<br> An area that
	 * has at least one demon cannot be controlled.
	 * 
	 * @param player
	 * @return the total number of areas controlled by the given player.
	 */
	public int getNumberOfAreasControlled(Player player) {
		int count = 0;
		for (BoardArea ba : gameBoard.values()) {
			if (ba.isControlledBy(player)) {
				count++;
			}
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
	 * @return Map of boardArea neighboring to boardArea
	 */
	public Map<Integer, BoardArea> getNeighbours(BoardArea boardArea) {
		return gameBoard.values().stream()
				.filter(a -> boardArea.isNeighboringWith(a))
				.collect(Collectors.toMap(a -> a.getArea().getAreaCode(), Function.identity()));
	}
	
	/**
	 * @return the total number of trouble markers currently placed on the board.
	 */
	public int getTotalNumberOfTroubleMarkers() {
		return gameBoard.values().stream()
				.map(area -> area.hasTroubleMarker() ? 1 : 0)
				.reduce(0, (partialSum, current) -> partialSum + current);
	}
	
	/**
	 * Checks if the draw pile still has cards.
	 * @return true if the size of the draw pile is non-zero, false otherwise.
	 */
	public boolean hasPlayerCardsLeft() {
		return playerDeck.size() > 0;
	}

	/**
	 * Removes all pieces from the area with the given number (see 
	 * {@link BoardArea#clearAllPieces()}).
	 * @param areaId the area from which all pieces will be removed.
	 */
	public void removeAllPiecesFromArea(int areaId) {
		BoardArea a = gameBoard.get(areaId);
		
		// Do all the actions separately and using methods from this class
		// (i.e. not those of the board area) to give a chance to any player who
		// has Small Gods to protect his minions/buildings:
		// Remove minions, demons, trolls and buildings in succession

		for (Map.Entry<Color, Integer> e : a.getMinions().entrySet()) {
			for (int i = 0; i < e.getValue(); i++) {
				removeMinion(areaId, getPlayerOfColor(e.getKey()));
			}
		}
		
		for (int i = 0; i < a.getDemonCount(); i++) {
			removeDemon(areaId);
		}
		
		for (int i = 0; i < a.getTrollCount(); i++) {
			a.removeTroll();
		}
		
		removeBuilding(areaId);
	
	}
	
	/**
	 * Finds all buildings a player owns.
	 * @param player
	 * @return Map of boardAreas owned by the player
	 */
	public Map<Integer, BoardArea> getBuildingAreas(Player player) {
		Map<Integer, BoardArea> buildingAreas = new HashMap<Integer, BoardArea>();

		for (BoardArea boardArea : gameBoard.values()) {
			if (boardArea.getBuildingOwner() == player.getColor()) {
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
		for (BoardArea boardArea : gameBoard.values()) {
			if (boardArea.hasTroubleMarker()) {
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
	 * Removes a building on the area with the given ID, if one exists and 
	 * also removes the corresponding city card form the player's hand.
	 * 
	 * @return true if the building was removed successfully in this area, false
	 *         otherwise.
	 */
	public boolean removeBuilding(int areaId) {
		BoardArea a = gameBoard.get(areaId);
		if (a.hasBuilding()) {
			Player owner = getPlayerOfColor(a.getBuildingOwner());
			if (owner.canProtectPieces() && willProtectPiece(owner)) {
				payToProtectPiece(owner);
				System.out.println("Building protected.");
				return false;
			}
			a.removeBuilding();
			owner.increaseBuildings();
			owner.removeCityCard(a.getArea());
			return true;
		}
		return false;
	}
	
	/**
	 * Completes a transaction by giving money to the given player from the game's bank.
	 * @return true if the transaction was completed successfully, false otherwise.
	 */
	public boolean givePlayerMoneyFromBank(Player p, int amount) {
		if (gameBank.decreaseBalance(amount)) {
			p.increaseMoney(amount);
			return true;
		}
		return false;
	}

	/**
	 * Completes a transaction by taking money from the given player and storing it 
	 * in the game's bank.
	 * @return true if the transaction was completed successfully, false otherwise.
	 */
	public boolean giveBankMoneyFromPlayer(Player p, int amount) {
		if (p.decreaseMoney(amount)) {
			gameBank.increaseBalance(amount);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @param player
	 * @param boardArea
	 * @return true if adding building was success
	 */
	public boolean addBuilding(Player player, BoardArea boardArea) {
		if (giveBankMoneyFromPlayer(player, boardArea.getBuildingCost())
				&& boardArea.addBuildingForPlayer(player)) {
			player.addCityCard(boardArea.getArea());
			return true;
		}
		return false;
	}
	
	
	/**
	 * All players must pay $2 for each building they have on the
	 * board.<br> If they cannot pay for a building then it is removed
	 * from the board. 
	 */
	public void handleSubsidence() {
		final int BUILDING_COST = 2;
		for (BoardArea a : gameBoard.values()) {
			AnkhMorporkArea area = a.getArea();
			if (a.getBuildingOwner() != Color.UNDEFINED) {
				Player p = players.get(a.getBuildingOwner());
				if (giveBankMoneyFromPlayer(p, BUILDING_COST)) {
					System.out.println(p.getName() + " (" + p.getColor() + ") " +
							"paying $" + BUILDING_COST + " for " + area.name() + "...");
				} else {
					removeBuilding(area.getAreaCode());
					System.out.println("Building removed for " + p.getName() + "(" +
							p.getColor() + " at " + area.name() + ".");
				}
			}
		}
	}
	
	/**
	 * Sets the state of the city area card corresponding to the area with the given ID
	 * if it is owned by any player.
	 * @param areaID
	 * @return the player who owns the city area card which got enabled/disabled , if any.
	 */
	public Optional<Player> setCityAreaCardState(int areaID, 
			BiFunction<Player, AnkhMorporkArea, Boolean> cardStateChanger) {
		BoardArea a = gameBoard.get(areaID);
		AnkhMorporkArea area = AnkhMorporkArea.forCode(areaID);
		if (a.hasBuilding()) {
			Player owner = players.get(a.getBuildingOwner());
			return cardStateChanger.apply(owner, area) ? Optional.of(owner) : Optional.empty();
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
	 * Remove one minion belonging to the given player from the area with the
	 * given area ID.
	 * 
	 * @param areaID
	 * @return true if a minion was removed, false otherwise.
	 */
	public boolean removeMinion(int areaID, Player player) {
		BoardArea affectedArea = gameBoard.get(areaID);
		if (affectedArea.getMinionCountForPlayer(player) > 0) {
			if (player.canProtectPieces() && willProtectPiece(player)) {
				payToProtectPiece(player);
				System.out.println("Minion saved.");
				return false;
			}
			affectedArea.removeMinion(player);
		}
		return false;
	}
	
	/**
	 * <b:Place a troll to the area with the given ID.
	 * 
	 * @param areaID
	 * @return true if the area where the troll was placed already contained a
	 *         minion, false otherwise.
	 */
	public boolean placeTroll(int areaID) {
		return gameBoard.get(areaID).addTroll();
	}
	
	/**
	 * <b:Remove a troll to the area with the given ID.
	 * 
	 * @param areaID
	 * @return true if successfully remove troll
	 */
	public boolean removeTroll(int areaID) {
		return gameBoard.get(areaID).addTroll();
	}
	
	/**
	 * Place a demon to the area with the given ID. Also, the City Area card corresponding
	 * to that area is disabled (it will only be enabled again if the demon is removed).
	 * 
	 * @param areaID
	 * @return true if the demon was placed successfully, false otherwise.
	 */
	public boolean placeDemon(int areaID) {
		// Check if the building owner can protect his building
		BoardArea boardArea = gameBoard.get(areaID);
		if (boardArea.hasBuilding()) {
			Player buildingOwner = players.get(boardArea.getBuildingOwner());
			if (buildingOwner.canProtectPieces() && willProtectPiece(buildingOwner)) {
				payToProtectPiece(buildingOwner);
				System.out.println("Area protected by demon.");
				return false;
			}
		}
		setCityAreaCardState(areaID, (player, area) -> player.disableCityAreaCard(area));
		return gameBoard.get(areaID).addDemon();
	}
	
	/**
	 * Removes (i.e. assassinates :-) a demon from the area with the given area code.
	 * If this was the last demon in the area, the corresponding city area card should
	 * become active again (if it is owned by any player who has a building in that area).
	 * @param areaID
	 * @return true if the demon was removed successfully, false otherwise.
	 */
	public boolean removeDemon(int areaID) {
		BoardArea affectedArea = gameBoard.get(areaID);
		if (affectedArea.getDemonCount() == 1) {
			setCityAreaCardState(areaID, (player, area) -> player.enableCityAreaCard(area));
		}
		return affectedArea.removeDemon();
	}

	/**
	 * Add a trouble marker to the area with the given ID.
	 * 
	 * @param areaID
	 * @return true if the trouble marker was added successfully, false
	 *         otherwise.
	 */
	public boolean addTroubleMarker(int areaID) {
		return gameBoard.get(areaID).addTroubleMarker();
	}
	
	/**
	 * Remove a trouble marker to the area with the given ID.
	 * 
	 * @param areaID
	 * @return true if the trouble marker was removed successfully, false
	 *         otherwise.
	 */
	public boolean removeTroubleMarker(int areaID) {
		return gameBoard.get(areaID).removeTroubleMarker();
	}

	/**
	 * Checks if the game is over due to a player having won by meeting the conditions
	 * indicated on his/her Personality card.
	 * @param p the player whose winning condition will be checked 
	 * @return true if the game is over because the given player has won, false otherwise.
	 */
	public boolean hasPlayerWon(Player p) {
		if (p.getPersonality().hasWon(players.size(), p, this)) {
			status = GameStatus.FINISHED;
			return true;
		}
		return false;
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
			// The game will continue
			return Collections.emptyList();
		}
		status = GameStatus.FINISHED;
		return getWinnersByPoints();
	}

	/**
	 * Figures out which player has won based on the total number of points:<br>
	 * - Each minion on the board is worth five points. <br>
	 * - Each building is worth a number of points equal to its monetary cost. Each $1 in hand
	 *   is worth one point. <br>
	 * - If you have the Dent card or the Bank card then you must pay back the amount noted on the card. <br>
	 * - If you cannot do so then you lose fifteen points each. <br>
	 * - In the case of a tie the tied player with the highest monetary value City Area card is the winner. <br>
	 * - If there is still a tie then the tied players shared the honours of a joint win.<br>
	 * 
	 * The game is declared finished after that.
	 * 
	 * @return the player(s) who won the game based on points.
	 */
	public List<Player> getWinnersByPoints() {
		Map<Integer, List<Player>> pointsToPlayers =
				players.values().stream().collect(Collectors.groupingBy(p -> getPlayerPoints(p)));
		int maxPoints = pointsToPlayers.keySet().stream().max(Integer::compare).get();
		if (pointsToPlayers.get(maxPoints).size() == 1) {
			System.out.println("The game has a winner with " + maxPoints + " points.");
			return pointsToPlayers.get(maxPoints);
		}
			
		// Tie break: Check the highest-value building card owned
		Map<Integer, List<Player>> highestBuildingsToPlayers =
				players.values().stream().collect(Collectors.groupingBy(p -> p.getMoney()));
		int highestBuildingValue = highestBuildingsToPlayers.keySet().stream().max(Integer::compare).get();
		if (highestBuildingsToPlayers.get(highestBuildingValue).size() == 1) {
			System.out.println("The game has one winner with a building of value $" + highestBuildingValue + ".");
		} else {
			System.out.println("There are multiple winners to the game (same $, highest building value).");
		}

		return highestBuildingsToPlayers.get(highestBuildingValue);
	}

	/**
	 * Retrieves the total number of points for the given player.<br>
	 * - Each minion on the board (on areas which are not occupied by demons) 
	 * 	 is worth five points.<br>
	 * - Each building is worth a number of points equal to its monetary cost unless
	 *   there is a demon in the area. 
	 * - Each $1 in hand is worth one point. <br>
	 * - If you have the Mr. Bent card or the Bank card then you must pay back the amount noted on the card. <br>
	 * - If you cannot do so then you lose fifteen points for each such card. 
	 * 
	 * @return the number of points for the given player.
	 */
	public int getPlayerPoints(Player p) {
		int points = gameBoard
				.values()
				.stream()
				.map(area -> (area.getDemonCount() == 0 ? (area.getMinionCountForPlayer(p) * MINION_POINTS) : 0) + 
						((area.getBuildingOwner() == p.getColor() &&
						area.getDemonCount() == 0) ? area.getBuildingCost() : 0))
				.reduce(0, (s, areaPoints) -> s + areaPoints);
		
		
		int loanBalance = p.getLoanBalance(); // caution: this is non-positive!
		int playerMoney = p.getMoney();
		if (loanBalance != 0) {
			points -= ((playerMoney + loanBalance) < 0) ?
				Bank.LOAN_PENALTY * ((Math.abs(loanBalance) / Bank.LOAN_REPAY_AMOUNT)) :
					Math.abs(loanBalance);
		}
					
		points += playerMoney;
		
		return points;
	}
	
	/**
	 * @return the given player's net worth (cash plus the monetary cost of each 
	 * owned building in demon-free areas - loans taken out * $12)
	 */
	public int getPlayerNetWorth(Player p) {
		return p.getMoney()
				+ gameBoard.values().stream()
					.map(a -> (a.getDemonCount() == 0 && a.getBuildingOwner() == p.getColor()) ? 
							a.getBuildingCost() : 0)
					.reduce(0, (sum, cur) -> sum + cur) + p.getLoanBalance();
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
	 * Discard card by adding it to the pile.
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
	 * Remove the given player card from the given player's hand. 
	 * Similar to {@link #discardCard(GreenPlayerCard, Player)}
	 * but doesn't add the card to the pile discard. 
	 * Ideal for giving a card to another player.
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
		if (willPlay) {
			// Nothing to do here since this notify is done before the scroll
			// gets played
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
	
	/**
	 * Check if the player will protect a piece/building currently affected by 
	 * an upstream random event.
	 * @return true if the player is going to protect his/her piece, false otherwise.
	 */
	private boolean willProtectPiece(Player p) {
		return new TextUserInterface().getUserYesOrNoChoice(p.getName() + "(" + p.getColor() 
				+ ") has Small Gods. Pay $3 to protect a piece/building affected "
				+ "by the ongoing event?");
	}
	
	/**
	 * Completes a transaction to protect a piece belonging to the given player,
	 * which is currently under the effect of a random event. The protection cost
	 * is 3 Ankh-Morpork dollars.
	 * @param p
	 */
	private void payToProtectPiece(Player p) {
		giveBankMoneyFromPlayer(p, Player.PROTECTION_COST);
	}

	public void shuffleDecks() {
		playerDeck.shuffle();
		randomEventDeck.shuffle();
	}
}
