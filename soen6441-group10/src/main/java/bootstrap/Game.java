/**
 * @File
 * Game model that brings game components together
 */

package bootstrap;

import card.PersonalityCard;


public class Game {

	private Bank gameBank;
	private Player[] players;
	private Deck<PersonalityCardWrapper> personality;
	private int status;
	
	/*
	 * Default class constructor.
	 */
	public Game() {
		// Set game status as un initiated
		this.status = 0;
	}
	
	/*
	 * Set up game
	 * @Exception: if invalid number of players
	 */
	public void setUp(int numberOfPlayers, String playerName) throws Exception {
		// Create players.
		// Make sure players are between 2 and 4.
		if(numberOfPlayers > 4 || numberOfPlayers < 2) {
			throw new Exception();
		} else {
			this.gameBank = Bank.getBank();
			this.players = new Player[numberOfPlayers];
			// Set human player at 0 index
			this.players[0] = new Player();
			this.players[0].setName(playerName);
			
			// Set all other players as AI
			for(int i=1;i<this.players.length;++i){
				Player p = new Player();
				p.setName("Player_" + i);
				this.players[i] = p;
			}
		}
		
		// Initialize personality deck.
		personality = Deck.getDeck(PersonalityCard.values(), p -> new PersonalityCardWrapper(p));
				
		// Set game status as ready to start.
		this.status = 1;
		
	}
	
	/*
	 * Start a new game and assign decks
	 */
	public void init() {
		// Give each player their personality.
		for(int i=0; i<this.players.length; ++i) {
			PersonalityCardWrapper popped = this.personality.pop();
			this.players[i].setPersonality(popped);
		}
		this.status = 2;
	}
	
	/*
	 * @return: Array of players in game
	 */
	public Player[] getPlayers() {
		return this.players;
	}

	/*
	 * @return:  Game status
	 */
	public int getStatus() {
		return this.status;
	}
	
	/*
	 * @return: PersnalityDeck
	 */
	public Deck<PersonalityCardWrapper> getPersonalityDeck() {
		return personality;
	}
}
