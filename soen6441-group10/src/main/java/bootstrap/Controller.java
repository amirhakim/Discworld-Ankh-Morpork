/**
 * @File
 * Controller used to interpret UI actions
 */
package bootstrap;

public class Controller {

	public void newGame(byte numberOfPlayers) {
		Game game = new Game(numberOfPlayers);
	}
	

}
