/**
 * 
 */
package bootstrap;

class Bank {
	private int silver;
	private int gold;
	private int silverValue = 1;
	private int goldValue = 5;
	
	public Bank() {
		this.silver = 35 * this.silverValue;
		this.gold = 17 * this.goldValue;
	}
	
	
	/*
	 * @return: int value remaining in bank
	 */
	public int getBalance() {
		return 0;
	}
}
