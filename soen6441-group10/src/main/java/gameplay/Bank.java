package gameplay;

/**
 *  This class represents the bank of game.</br>
 * It shows how much money is available in the bank by the method getBalance().
 * 
 * @author Team 10 - SOEN6441
 * @version 2.0
 */
public class Bank {

	/**
	 * "Mr. Bent" and "The Bank of Ankh Morpork" allow you to borrow $10.
	 */
	public static final int LOAN_AMOUNT = 10;

	/**
	 * At the end of the game, for each "loan card", you have to pay back $12.
	 */
	public static final int LOAN_REPAY_AMOUNT = 12;
	
	/**
	 * The penalty that a player pays (in points) if (s)he cannot pay back a loan at the
	 * end of the game.
	 */
	public static final int LOAN_PENALTY = 15;
	
	private int amount;
	
	/*
	 *It sets up silver and gold numbers. 
	 */
	/**
	 * This constructor is invoked to create objects from the class Bank.
	 */
	public Bank() {
	//	this.numSilver = 35;
	//	this.numGold = 17;
		this.amount = 120;
	}
		
	/**
	 * This method gets amount remaining in the bank.
	 * @return The balance
	 */
	public int getBalance() {
		return this.amount;
	}
	
	/**
	 * This method decreases the balance of Bank.
	 * @param amount the amount
	 */
	public boolean decreaseBalance(int amount) {
		// Check if the bank has enough money first
		if (this.amount - amount < 0) {
			return false;
		}
		this.amount = this.amount - amount;
		return true;
	}
	
	/**
	 * This method increases the balance of Bank.
	 * @param amount the amount
	 */
	public void increaseBalance(int amount) {
		this.amount = this.amount + amount;
	}
	
}
