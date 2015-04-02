
package gameplay;

import java.util.Collection;
import java.util.List;

import util.Color;
import card.player.Symbol;
import error.InvalidGameStateException;

/**
 * This class represents the Controller layer of MVC pattern.
 * It is used to interpret UI actions and calls the appropriate model like File IO and class Game.  
 * 
 * @author Team 10 - SOEN6441
 * @version 2.0
 */
public class Controller {

	private Game game;
	
	public Controller(){
		game = new Game();
	}
	
	public Controller(Game game_) {
		game = game_;
	}
	
	public void setGame(Game game_){
		game = game_;
	}
	
	/**
	 * This method starts a new game - initialize all the required data structures etc.
	 * @param: numberOfPlayers required for game, humane players name
	 * @return true if the game was successfully initialized, false otherwise.
	 */
	public boolean newGame(int numberOfPlayers, String[] playerNames) {
		try {
			game.setUp(numberOfPlayers, playerNames);
			game.init();
		} catch (InvalidGameStateException e) {
			System.out.println(e.getMessage());
			return false;
		}

		return true;
	}
	
	/**
	 * Advances the turn to the next player and returns the player whose turn
	 * it currently is.
	 * @return the player whose turn it currently is.
	 */
	public Player advanceToNextTurn() {
		return game.advanceTurnToNextPlayer();
	}
	
	/**
	 * Gets the player whose turn it currently is.
	 * @return the player whose turn it currently is.
	 */
	public Player getPlayerOfCurrentTurn() {
		return game.getPlayerOfCurrentTurn();
	}
	
	/**
	 * This method gets array of players in game.
	 * @return the players in game
	 */
	public Collection<Player> getPlayers() {
		return game.getPlayers();
	}
	
	public Player getPlayerOfColor(Color c) {
		return game.getPlayerOfColor(c);
	}
	
	/**
	 * This method gets bank class used in the game.
	 * @return the bank used in the game
	 */
	public Bank getBank() {
		return game.getBank();
	}
	
	/**
	 * This method checks to see if the game is playing.
	 * @return true if the game is playing
	 */
	public boolean gameExists() {
		return game.getStatus() == GameStatus.PLAYING;
	}

	/**
	 * Gets the game board in the form of a board area collection.<br> These
	 * board areas don't just contain the value of the underlying area 
	 * (e.g. "Dolly Sisters") - they contain their state as well
	 * (see {@link BoardArea}).
	 * @return a collection of the game's board areas.
	 */
	public Collection<BoardArea> getBoard() {
		return game.getBoard();
	}
	
	public Player getPlayerForColor(Color c) {
		return game.getPlayerOfColor(c);
	}
	
	public Game getGame() {
		return game;
	}
	
	public GameStatus getGameStatus() {
		return game.getStatus();
	}
	
	public void performSymbolAction(Player p, Symbol s) {
		s.getGameAction().accept(p, game);
	}
	
	public void restorePlayerHand(Player p) {
		game.restorePlayerHand(p);
	}
	
	public boolean hasPlayerWon(Player p) {
		return game.hasPlayerWon(p);
	}

	/**
	 * see {@link Game#finishGameOnPoints()}
	 */
	public List<Player> finishGameOnPoints(boolean checkForEmptyDeck) {
		return game.finishGameOnPoints(checkForEmptyDeck);
	}

	/**
	 * Shuffles the game decks if necessary, prior to the start of a new game.
	 */
	public void shuffleDecks() {
		game.shuffleDecks();
	}
	
}
