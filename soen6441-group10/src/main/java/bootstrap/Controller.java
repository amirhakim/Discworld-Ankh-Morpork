/**
 * @File
 * Controller used to interpret UI actions
 */
package bootstrap;

public class Controller {

	public boolean newGame(int numberOfPlayers) {
		try {
			Game game = new Game(numberOfPlayers);
		} catch (Exception e) {
			// Log message
			return false;
		}
		return true;
	}

}
