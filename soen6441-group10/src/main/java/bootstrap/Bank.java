

package bootstrap;

/**
 * <b> This class represents the bank of game <b> 
 * It shows how much money is available in the bank by the method getBalance()
 * @author Team 10 - SOEN6441
 * @version 1.0
 */

class Bank {
	private int numSilver;
	private int numGold;
	final private int silverValue = 1;
	final private int goldValue = 5;
	
	/*
	 * Set up silver and gold numbers
	 */
	public Bank() {
		this.numSilver = 35;
		this.numGold = 17;
	}
		
	/*
	 * @return: int value remaining in bank
	 */
	public int getBalance() {
		return this.numSilver * this.silverValue + this.numGold * this.goldValue;
	}
}
