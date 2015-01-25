/**
 * @File 
 * Class respresenting bank of game
 */
package bootstrap;


class Bank {
	private int numSilver;
	private int numGold;
	final private int silverValue = 1;
	final private int goldValue = 5;
	
	/*
	 * Set up silver and gold numers
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
