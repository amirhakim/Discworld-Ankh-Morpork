
package gameplay;

import java.util.Collection;

import util.Color;
import card.BoardArea;
import error.InvalidGameStateException;

/**
 * <b> This class represents the Controller layer of MVC pattern. <b> 
 * It is used to interpret UI actions and calls the appropriate model like File IO and class Game.
 * 
 * @author Team 10 - SOEN6441
 * @version 1.0
 */
public class Controller {

	private Game game;
	
	public Controller(){
		game = new Game();
	}
	
	public Controller(Game game_) {
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
	 * This method changes the turn of the players
	 */
	public void nextTurn() {
		game.turn();
	}
	
	/**
	 * This method gets The player whose turn is next.
	 * @return The player whose turn is next.
	 */
	public Player getCurrentTurn() {
		return game.getCurrentTurn();
	}
	
	/**
	 * This method gets array of players in game.
	 * @return the players in game
	 */
	public Collection<Player> getPlayers() {
		return game.getPlayers();
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
	 * Gets the game board in the form of a board area collection. These
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
	
	public void simulate() {
		game.simulate();
	}
	
	/**
	 * Get current game
	 * @return the game
	 */
	public Game getGame() {
		return game;
	}

}
