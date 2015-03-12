
package gameplay;

import java.util.Collection;

import util.Color;
import card.player.Symbol;
import error.InvalidGameStateException;

/**
 * <b> This class represents the Controller layer of MVC pattern.
 * It is used to interpret UI actions and calls the appropriate model like File IO and class Game. </b> 
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
	
	/**
	 * <b>This method starts a new game - initialize all the required data structures etc.</b>
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
	 * <b>Advances the turn to the next player and returns the player whose turn
	 * it currently is.</b>
	 * @return the player whose turn it currently is.
	 */
	public Player advanceToNextTurn() {
		return game.advanceTurnToNextPlayer();
	}
	
	/**
	 * <b>Gets the player whose turn it currently is.</b>
	 * @return the player whose turn it currently is.
	 */
	public Player getPlayerOfCurrentTurn() {
		return game.getPlayerOfCurrentTurn();
	}
	
	/**
	 * <b>This method gets array of players in game.</b>
	 * @return the players in game
	 */
	public Collection<Player> getPlayers() {
		return game.getPlayers();
	}
	
	public Player getPlayerOfColor(Color c) {
		return game.getPlayerOfColor(c);
	}
	
	/**
	 * <b>This method gets bank class used in the game.</b>
	 * @return the bank used in the game
	 */
	public Bank getBank() {
		return game.getBank();
	}
	
	/**
	 * <b>This method checks to see if the game is playing.</b>
	 * @return true if the game is playing
	 */
	public boolean gameExists() {
		return game.getStatus() == GameStatus.PLAYING;
	}

	/**
	 * <b>Gets the game board in the form of a board area collection.<br> These
	 * board areas don't just contain the value of the underlying area 
	 * (e.g. "Dolly Sisters") - they contain their state as well
	 * (see {@link BoardArea}).</b>
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
	
	public Game getGame() {
		return game;
	}
	
	public void performSymbolAction(Player p, Symbol s) {
		s.getGameAction().accept(p, game);
	}
	
	public void restorePlayerHand(Player p) {
		game.restorePlayerHand(p);
	}
	
	public boolean isGameOver()  {
		return game.isOver();
	}

	
}
