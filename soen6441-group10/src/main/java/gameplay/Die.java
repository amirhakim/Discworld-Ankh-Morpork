package gameplay;

import java.util.Random;

public class Die {
	
	private static final int SIDES = 12;
	
	private static Die instance = null;
	
	private Random randomGen = new Random();

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
		return randomGen.nextInt(SIDES + 1);
	}
	
	/**
	 * Returns an integer between 1 (inclusive) and the given number of players (inclusive) which 
	 * determines which player should play first.
	 * @param numberOfPlayers
	 * @return an integer in [1, number of players].
	 */
	public int determineFirstPlayer(int numberOfPlayers) {
		return (roll() % numberOfPlayers) + 1;
	}
	
}
