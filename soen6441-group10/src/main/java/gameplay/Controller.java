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
	
	/*
	 * Start a new game
	 * @param: numberOfPlayers required for game, humane players name
	 */
	public boolean newGame(int numberOfPlayers, String[] playerNames) {
		try {
			this.game.setUp(numberOfPlayers, playerNames);
			this.game.init();
		} catch (InvalidGameStateException e) {
			System.out.println(e.getMessage());
			// TODO add log message
			return false;
		}
		return true;
	}
	
	public void nextTurn() {
		game.turn();
	}
	
	/**
	 * 
	 * @return Player class of next turn
	 */
	public Player getCurrentTurn() {
		return this.game.getCurrentTurn();
	}
	
	public Player[] getPlayers() {
		return game.getPlayers();
	}
	
	public Bank getBank() {
		return game.getBank();
	}
	
	public boolean gameExists() {
		if(game.getState() < 2){
			return false;
		} else {
			return true;
		}
		
	}

	public List<Area> getCities() {
		return game.getCities().getCards();
	}
	
	public void simulate() {
		this.game.simulate();
	}
	
	public Game getGame() {
		return game;
	}
}
