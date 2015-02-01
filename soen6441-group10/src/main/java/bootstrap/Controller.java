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
	public boolean newGame(int numberOfPlayers, String playerName) {
		try {
			this.game.setUp(numberOfPlayers, playerName);
			this.game.init();
		} catch (Exception e) {
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

}
