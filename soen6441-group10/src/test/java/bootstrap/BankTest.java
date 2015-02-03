package bootstrap;

import static org.junit.Assert.*;

import org.junit.*;

public class BankTest {
	
		private Bank bank;
	
	    @BeforeClass
	    public static void setUpClass() throws Exception {
	        // Code executed before the first test method    
	    }
	 
	    @Before
	    public void setUp() throws Exception {
	        // Code executed before each test 
	    	this.bank = new Bank();
	    }
	 
	    @Test
	    /*
	     * Test bank balance
	     */
	    public void testBankBalance() {
	    	int bal = bank.getBalance();
	    	// Ensure value exists
	    	assertNotNull("Bank balance is null", bal);
	    	// Ensure bank is not in debt
	    	assertTrue("Bank balance is negative", bal >= 0);
	    	// Ensure bank value is not bigger max (17*5 + 35)
	    	assertTrue("Bank balance is too big", bal <= 17*5+35);
	    }
	 
	 
	    @After
	    public void tearDown() throws Exception {
	        // Code executed after each test  
	    	this.bank = null;
	    }
	 
	    @AfterClass
	    public static void tearDownClass() throws Exception {
	        // Code executed after the last test method 
	    }
}
