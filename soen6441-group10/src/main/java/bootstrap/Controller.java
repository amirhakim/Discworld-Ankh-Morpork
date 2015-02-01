/**
 * @File
 * Controller used to interpret UI actions
 */
package bootstrap;

public class Controller {

	private Game game = new Game();
	
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
	
	Player[] getPlayers() {
		return game.getPlayers();
	}
	
	Bank getBank() {
		return game.getBank();
	}
	
	boolean gameExists() {
		if(game.getState() < 2){
			return false;
		} else {
			return true;
		}
		
	}

}
