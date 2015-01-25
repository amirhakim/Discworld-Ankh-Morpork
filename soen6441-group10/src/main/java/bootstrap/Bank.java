/**
 * @File 
 * Class respresenting bank of game
 */
package bootstrap;


class Bank {
	private int silver;
	private int gold;
	private int silverValue = 1;
	private int goldValue = 5;
	
	/*
	 * Set up silver and gold value
	 */
	public Bank() {
		this.silver = 35 * this.silverValue;
		this.gold = 17 * this.goldValue;
	}
	
	
	/*
	 * @return: int value remaining in bank
	 */
	public int getBalance() {
		return silver + gold;
	}
}
