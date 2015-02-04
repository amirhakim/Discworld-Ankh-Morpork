/**
 * @File
 * Controller used to interpret UI actions
 */
package gameplay;

import java.util.List;

import card.Area;
import error.InvalidGameStateException;

public class Controller {

	private Game game;
	
	public Controller(){
		game = new Game();
	}
	
	public Controller(Game game_) {
		game = game_;
	}
	
	/**
	 * Start a new game - initialize all the required data structures etc.
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
	
	public void nextTurn() {
		game.turn();
	}
	
	/**
	 * @return The player whose turn is next.
	 */
	public Player getCurrentTurn() {
		return game.getCurrentTurn();
	}
	
	public Player[] getPlayers() {
		return game.getPlayers();
	}
	
	public Bank getBank() {
		return game.getBank();
	}
	
	public boolean gameExists() {
		return game.getStatus() == GameStatus.PLAYING;
	}

	public List<Area> getCities() {
		return game.getCities().getCards();
	}
	
	public void simulate() {
		game.simulate();
	}
	
	public Game getGame() {
		return game;
	}

}
