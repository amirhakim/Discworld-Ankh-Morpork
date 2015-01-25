/**
 * @File
 * Game class that brings game components together
 */

package bootstrap;

public class Game {

	final private Bank gameBank;
	final Player[] players;
	
	public Game(byte numberOfPlayers) {
		this.gameBank = new Bank();
		this.players = new Player[numberOfPlayers];
		
	}

}
