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
	
	/*
	 * Returns status of players
	 * TODO add more items to return
	 */
	public Player[] getStatus() throws Exception{
		if(this.game.getStatus() < 2) {
			throw new Exception();
		}
		return game.getPlayers();
	}

}
