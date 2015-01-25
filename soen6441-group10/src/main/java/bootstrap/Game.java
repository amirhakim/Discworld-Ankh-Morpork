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
		
		Card[] pc = new PersonalityCard[100];

		for(int i=0; i<pc.length;++i){
			pc[i] = new PersonalityCard();
		}
		System.out.println("1");
		System.out.println(pc[1].getClass());
		System.out.println("2");
		Deck d = new Deck(pc);
		d.test();
		
	}

}
