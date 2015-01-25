/**
 * @File
 * Game class that brings game components together
 */

package bootstrap;

public class Game {

	private Bank gameBank;
	Player[] players;
	
	/*
	 * Create a game object
	 * @Exception: if invalid number of players
	 */
	public Game(int numberOfPlayers) throws Exception {
		//create players
		if(numberOfPlayers > 4 || numberOfPlayers < 2) {
			throw new Exception();
		} else {
			this.gameBank = new Bank();
			this.players = new Player[numberOfPlayers];
		}

		Deck Personality = new Deck(new PersonalityCard(), 100);
		
		
	}

}
