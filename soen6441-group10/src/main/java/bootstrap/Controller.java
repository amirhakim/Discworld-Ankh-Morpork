/**
 * @File
 * Controller used to interpret UI actions
 */
package bootstrap;

public class Controller {

	/*
	 * Start a new game
	 * @param: numberOfPlayers required for game
	 */
	public boolean newGame(int numberOfPlayers) {
		try {
			Game game = new Game(numberOfPlayers);
		} catch (Exception e) {
			// TODO add log message
			return false;
		}
		return true;
	}

}
