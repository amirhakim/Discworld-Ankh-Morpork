package gameplay;

import java.util.Random;

public class Die {
	
	private static final int SIDES = 12;
	
	private static Die instance = null;
	
	private Random randomGen = new Random();
	
	// Exists for sole point of testing
	// Force die roll to return a certain value
	private Integer cheat = null;

	private Die() {}
	
	public static Die getDie() {
		if (instance == null) {
			instance = new Die();
		}
		
		return instance;
	}

	/**
	 * @return an integer in [1, 12].
	 */
	public int roll() {
		if (cheat != null) {
			return cheat;
		}
		
		int result = randomGen.nextInt(SIDES) + 1;
		System.out.println("Die roll: " + result);
		return result;
	}
	
	public void setCheat(int cheat) {
		this.cheat = cheat;
	}
	
	/**
	 * Returns an integer between 0 (inclusive) and the given number of players 
	 * (exclusive) which determines which player should play first (the result
	 * is used as an index into an array.
	 * @param numberOfPlayers
	 * @return an integer in [0, number of players).
	 */
	public int determineFirstPlayer(int numberOfPlayers) {
		return (roll() % numberOfPlayers);
	}
	
}
