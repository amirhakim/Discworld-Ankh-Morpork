package pieces;

import java.util.Random;

public class Die {
	
	private static final int DIE_SIDES = 12;
	
	private Random randomGen = new Random();
	private static Die instance = new Die();
	
	public static Die getInstance() {
		return instance;
	}
	
	public int roll() {
		return randomGen.nextInt(DIE_SIDES + 1);
	}

}
