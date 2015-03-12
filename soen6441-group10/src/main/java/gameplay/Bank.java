package gameplay;

/**
 * <b> This class represents the bank of game.</br>
 * It shows how much money is available in the bank by the method getBalance().</b>
 * 
 * @author Team 10 - SOEN6441
 * @version 2.0
 */
public class Bank {

	/**
	 * <b>"Mr. Bent" and "The Bank of Ankh Morpork" allow you to borrow $10.</b>
	 */
	public static final int LOAN_AMOUNT = 10;

	/**
	 * <b>At the end of the game, for each "loan card", you have to pay back $12.</b>
	 */
	public static final int LOAN_REPAY_AMOUNT = 12;
	
	/* Add these items in later builds */
//	private int numSilver;
//	private int numGold;
//	final private int silverValue = 1;
//	final private int goldValue = 5;
	
	private int amount;
	
	/*
	 *It sets up silver and gold numbers. 
	 */
	/**
	 * <b>This constructor is invoked to create objects from the class Bank.</b>
	 */
	public Bank() {
	//	this.numSilver = 35;
	//	this.numGold = 17;
		this.amount = 120;
	}
		
	/**
	 * <b>This method gets amount remaining in the bank.</b>
	 * @return The balance
	 */
	public int getBalance() {
		return this.amount;
	}
	
	/**
	 * <b>This method decreases the balance of Bank.</b>
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
	 * <b>This method increases the balance of Bank.</b>
	 * @param amount the amount
	 */
	public void increaseBalance(int amount) {
		this.amount = this.amount + amount;
	}
	
}
