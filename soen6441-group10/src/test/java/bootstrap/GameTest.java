package bootstrap;

import static org.junit.Assert.*;
import static org.junit.Before.*;

import org.junit.*;

public class GameTest {
	
		private Game game;
	
	   @BeforeClass
	    public static void setUpClass() throws Exception {
	        // Code executed before the first test method    
	    }
	 
	    @Before
	    public void setUp() throws Exception {
	        // Code executed before each test 
	    }
	 
	    @Test
	    /*
	     * Test bank balance
	     */
	    public void gameCreate() {
	    	try {
	    		this.game = new Game(3);
	    	} catch (Exception e) {
	    		fail("Failed " + e.getMessage());
	    	}
	    	
	    }
	 
	 
	    @After
	    public void tearDown() throws Exception {
	        // Code executed after each test  
	    	this.game = null;
	    }
	 
	    @AfterClass
	    public static void tearDownClass() throws Exception {
	        // Code executed after the last test method 
	    }
}
