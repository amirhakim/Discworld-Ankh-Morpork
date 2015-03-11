package gameplay;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import util.Color;
import card.city.AnkhMorporkArea;
import card.personality.PersonalityCard;
import card.personality.PersonalityDeck;
import card.player.DiscardPile;
import card.player.GreenPlayerCard;
import card.player.PlayerDeck;
import card.random.RandomEventCard;
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

	/**
	 * Every time a player cannot pay back for a loan card, 15 points are deducted
	 * from his/her final score.
	 */
	private static final int LOAN_PENALTY = 15;
	
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

	private GreenPlayerCard currentCardInPlay;

	public Game() {
		status = GameStatus.UNINITIATED;
		gameBoard = new HashMap<Integer, BoardArea>();
		currentCardInPlay = null;
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
				p.getValue().addPlayerCard(playerDeck.drawCard().get());
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
	 * Deal a player a personality card from deck
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
	 * Similar to getBoard but returns areas with indexes
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
		players.get(playerTurnOrder[current]).printTurn();
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
	 * Brings the given player's hand size back to 5 if it is less than that.
	 * If no more player cards are available, the player's hand is left as it is
	 * at the time when the cards are up (the game should end after the player's
	 * turn is finished).
	 * @param p the player whose hand size must be restored.
	 */
	public void restorePlayerHand(Player p) {
		Player actualPlayer = players.get(p.getColor());
		while (hasPlayerCardsLeft() && 
				actualPlayer.getHandSize() < Player.PLAYER_MAX_HAND_SIZE) {
			actualPlayer.addPlayerCard(playerDeck.drawCard().get());
		}
	}

	
	/**
	 * Draws player cards.
	 * Adds the given player's hand size (i) if it is less than five.
	 * If no more player cards are available, the player's hand is left as it is
	 * at the time when the cards are up (the game should end after the player's
	 * turn is finished). 
	 * @param p the player whose hand size must be restored.
	 */
	public void drawPlayerCard(Player p , int i){
		Player actualPlayer = players.get(p.getColor());
		if (hasPlayerCardsLeft() && 
				actualPlayer.getHandSize() < Player.PLAYER_MAX_HAND_SIZE) {
			while(i>0){
			actualPlayer.addPlayerCard(playerDeck.drawCard().get());
			i--;
			}
		}
	}
	
	
	/**
	 * Draws a player card.
	 * 
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
	public int getTotalMinionCountForPlayer(Player player) {
		return gameBoard.values().stream()
				.map(area -> area.getMinionCountForPlayer(player))
				.reduce(0, (sumSoFar, next) -> sumSoFar + next);
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
		return gameBoard
				.values()
				.stream()
				.map(a -> a.isControlledBy(player) ? 1 : 0)
				.reduce(0, (partialSum, areaContribution) -> partialSum + areaContribution);
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
	 * Checks if the draw pile still has cards.
	 * @return true if the size of the draw pile is non-zero, false othewise.
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
		gameBoard.get(areaId).clearAllPieces();
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
	 * Finds all buildings a player owns
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
	public Map<Integer, BoardArea> getBuidlingFreeAreas(){
		Map<Integer, BoardArea> freeAreas = new HashMap<Integer, BoardArea>();
		
		for(BoardArea boardArea: gameBoard.values()) {
			if(boardArea.getBuildingOwner() == Color.UNDEFINED) {
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
	 * 
	 * @param areaId
	 * @return true if the building was removed successfully in this area, false
	 *         otherwise.
	 */
	public boolean removeBuilding(int areaId) {
		BoardArea a = gameBoard.get(areaId);
		if (a.removeBuilding()) {
			getPlayerOfColor(a.getBuildingOwner()).increaseBuildings();
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param player
	 * @param boardArea
	 * @return true if adding building was success
	 */
	public boolean addBuilding(Player player, BoardArea boardArea) {
		if(boardArea.addBuildingForPlayer(player)) {
			if(player.decreaseMoney(boardArea.getBuildingCost())) {
				getBank().increaseBalance(boardArea.getBuildingCost());
				return true;
			}
		} 
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
		return gameBoard.get(areaID).removeMinion(player);
	}
	
	/**
	 * Place a troll to the area with the given ID.
	 * 
	 * @param areaID
	 * @return true if the area where the troll was placed already contained a
	 *         minion, false otherwise.
	 */
	public boolean placeTroll(int areaID) {
		return gameBoard.get(areaID).addTroll();
	}
	
	/**
	 * Place a demon to the area witht the given ID.
	 * 
	 * @param areaID
	 * @return true if the demon was placed successfully, false otherwise.
	 */
	public boolean placeDemon(int areaID) {
		return gameBoard.get(areaID).addDemon();
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
	 * Figures out which player has won based on the total number of points:
	 * - Each minion on the board is worth five points. 
	 * - Each building is worth a number of points equal to its monetary cost. Each $1 in hand
	 *   is worth one point. 
	 * - If you have the Dent card or the Bank card then you must pay back the amount noted on the card. 
	 * - If you cannot do so then you lose fifteen points each. 
	 * - In the case of a tie the tied player with the highest monetary value City Area
	 *   card is the winner. 
	 * - If there is still a tie then the tied players shared the honours of a joint win.
	 * 
	 * The game is declared finished after that.
	 * 
	 * @return the player(s) who won the game based on points.
	 */
	public List<Player> getWinnersByPoints() {
		Map<Integer, List<Player>> pointsToPlayers =
				players.values().stream().collect(Collectors.groupingBy(p -> getPlayerPoints(p)));
		int maxPoints = pointsToPlayers.keySet().stream().max((a, b) -> a - b).get();
		if (pointsToPlayers.get(maxPoints).size() == 1) {
			return pointsToPlayers.get(maxPoints);
		}
			
		Map<Integer, List<Player>> moneyToPlayers =
				players.values().stream().collect(Collectors.groupingBy(p -> p.getMoney()));
		int maxMoney = moneyToPlayers.keySet().stream().max((a, b) -> a - b).get();
		return moneyToPlayers.get(maxMoney);
	}

	/**
	 * Checks if the game is over either due to not having any player cards left
	 * on the draw pile or a player having won by meeting the conditions
	 * indicated on his/her Personality card.
	 * 
	 * @return true if the game is over, false otherwise.
	 */
	public boolean isOver() {
		if (status == GameStatus.FINISHED) {
			return true;
		}

		boolean hasPlayerCardsLeft = hasPlayerCardsLeft();
		boolean hasAnyPlayerWon = hasAnyPlayerWon();
		boolean isOver = !hasPlayerCardsLeft || hasAnyPlayerWon;
		
		if (!hasPlayerCardsLeft && !hasAnyPlayerWon) {
			// Nobody had Commander Vimes so end the game on points.
			getWinnersByPoints();
		}

		if (isOver) {
			status = GameStatus.FINISHED;
		}

		return isOver;
	}

	/**
	 * Checks the personality cards of all the players to find out whether
	 * someone has won the game.
	 * 
	 * @return true if someone has won the game, false otherwise.
	 */
	private boolean hasAnyPlayerWon() {
		return players
				.values()
				.stream()
				.anyMatch(
						p -> p.getPersonality().hasWon(players.size(), p, this));
	}
	
	/**
	 * Retrieves the total number of points for the given player.
	 * - Each minion on the board is worth five points. 
	 * - Each building is worth a number of points equal to its monetary cost. Each $1 in hand
	 *   is worth one point. 
	 * - If you have the Mr. Bent card or the Bank card then you must pay back the amount noted on the card. 
	 * - If you cannot do so then you lose fifteen points each. 
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
	 * If not asking for too many cards, draw these cards and assign to player
	 * 
	 * @param player
	 * @param numberOfCards
	 * @return false if asking for too many cards
	 */
	public boolean drawDiscardCards(Player player, int numberOfCards) {

		if(getDiscardPile().size() >= numberOfCards) {
			while(numberOfCards > 0) {
				player.addPlayerCard(discardPile.drawCard().get());		
				numberOfCards--;
			}
			return true;
		} else {
			return false;
		}
		
	}

	/**
	 * 
	 * @return discardPile used by game
	 */
	public DiscardPile getDiscardPile() {
		return discardPile;
	}
	
	/**
	 * Discar card by adding it to the pile
	 * @param card
	 */
	public void discardCard(GreenPlayerCard card) {
		discardPile.addCard(card);
	}
	
}
