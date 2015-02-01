/**
 * @File
 * Game model that brings game components together
 */

package bootstrap;


public class Game {

	private Bank gameBank;
	private Player[] players;
	private Deck personality;
	private Deck player;
	private CityDeck cities;
	private int status;
	private int currentTurn;
	
	/*
	 * Default class constructor.
	 */
	public Game() {
		// Set game status as uninitiated
		this.status = 0;
	}
	
	/*
	 * Set up game
	 * @Exception: if invalid number of players
	 */
	public void setUp(int numberOfPlayers, String[] playerNames) throws InvalidGameStateException {
		// Create players.
		// Make sure players are between 2 and 4.
		if(numberOfPlayers > 4 || numberOfPlayers < 2) {
			throw new InvalidGameStateException("Incorrect player number");
		} else {
			this.gameBank = new Bank();
			this.players = new Player[numberOfPlayers];
			
			for(int i=0;i<this.players.length;++i){
				Player p = new Player();
				p.setName(playerNames[i]);
				this.players[i] = p;
			}
		}
		
		// Initialize personality deck.
		this.personality = new PersonalityDeck();
		this.player = new PlayerDeck();
		this.cities = new CityDeck();
		
		// Set game status as ready to start.
		this.status = 1;
		
		
	}
	
	/*
	 * Start a new game and assign decks
	 */
	public void init() {
		// Give each player their personality.
		for(int i=0; i<this.players.length; ++i) {
			Card popped = this.personality.pop();
			this.players[i].setPersonality(popped);
			CityCard Shades= (this.cities.getCard("The Shades"));
			Shades.addMinion(this.players[i]);
			Shades.addTrouble();
			CityCard theScoures= (this.cities.getCard("The Scoures"));
			theScoures.addMinion(this.players[i]);
			theScoures.addTrouble();
			CityCard dollySisters= (this.cities.getCard("Dolly Sisters"));
			dollySisters.addMinion(this.players[i]);
			dollySisters.addTrouble();
		}
		
		this.currentTurn = 0;
		this.status = 2;
	}
	
	
	
	public void turn() {
		int current = this.currentTurn;
		this.players[current].turn();
		if((current + 1) == this.players.length) {
			this.currentTurn = 0;
		} else {
			this.currentTurn = current + 1;	
		}
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
	
	/**
	 * 
	 * @return Player class of next turn
	 */
	public Player getCurrentTurn() {
		return this.players[this.currentTurn];
	}
	
	/*
	 * @return: PersnalityDeck
	 */
	public Deck getPersonalityDeck() {
		return this.personality;
	}
	
	public Bank getBank() {
		return this.gameBank;
	}

	int getState() {
		return this.status;
	}
	
	CityDeck getCities() {
		return this.cities;
	}
}
