package bootstrap;

public class Bank {

	private int numSilver;
	private int numGold;

	private static final int SILVER_VALUE = 1;
	private static final int GOLD_VALUE = 5;
	private static final int INIT_SILVER_AMOUNT = 35;
	private static final int INIT_GOLD_AMOUNT = 17;
	
	private static Bank instance;
	
	private Bank() {
		numSilver = INIT_SILVER_AMOUNT;
		numGold = INIT_GOLD_AMOUNT;
	}
	
	public static Bank getBank() {
		if (instance == null) {
			instance = new Bank();
		}
		
		return instance;
	}
		
	public int getBalance() {
		return this.numSilver * SILVER_VALUE + this.numGold * GOLD_VALUE;
	}

}
