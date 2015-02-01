package bootstrap;

import static org.junit.Assert.*;
import static org.junit.Before.*;

import java.util.ArrayList;

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
	     * Test game creation
	     */
	    public void testPlayerName() {
	    	try {
	    		this.game = new Game();
	    		String playerName = "Test";
	    		this.game.setUp(3, playerName);
	    		
	    		// Set up game and loop through players to make sure this player exists
	    		Player[] players = this.game.getPlayers();
	    		boolean found = false;
	    		for(int i=0; i<players.length;++i){
	    			if(players[i].getName().equals(playerName)) {
	    				found = true;
	    			}
	    		}
	    		assertTrue("Could not find named player in players array", found);
	    		
	    	} catch (Exception e) {
	    		fail("Failed " + e.getMessage());
	    	}
	    	
	    }
	    
	    @Test
	    /*
	     * Test that creating too many players doesnt work
	     */
	    public void testMaxNumberOfPlayers(){
	    	this.game = new Game();
	    	try{
	    		this.game.setUp(5,"test");
	    		fail("Not catching too many players");
	    	} catch (Exception e) {
	    	}
	    }
	 
	    @Test
	    /*
	     * Test that creating too few players doesnt work
	     */
	    public void testMinNumberOfPlayers(){
	    	this.game = new Game();
	    	try{
	    		this.game.setUp(1,"test");
	    		fail("Not catching too many players");
	    	} catch (Exception e) {
	    	}
	    }
	    
	    
	    @Test
	    /*
	     * Test two players do not have the same card
	     */
	    public void testPersonalityCard() {
	    	this.game = new Game();
	    	try {
	    		// Create game
	    		int numberOfPlayers = 3;
	    		this.game.setUp(numberOfPlayers, "Test");
	    		this.game.init();
	    		Player[] players = this.game.getPlayers();
	    		ArrayList<String> cardTitles = new ArrayList<String>();
	    		// Loop through players and look at their personality cards
	    		for(int i=0;i<players.length; ++i) {
	    			// Get players personality
	    			String currentTitle = players[i].getPersonality().getTitle();
	    			// Make sure its not already been given
	    			assertTrue(!cardTitles.contains(currentTitle));
	    			// Add it to the running list
	    			cardTitles.add(currentTitle);
	    		}

	    		
	    	} catch(Exception e){
	    		fail("exception");
	    	}
	    	
	    }
	    
	    @Test
	    /*
	     * Check game status is appropriate
	     */
	    public void testGameStatus(){
	    	this.game = new Game();
	    	assertTrue(game.getStatus() == 0);
	    	try {
	    		this.game.setUp(3,"test");
	    		assertTrue(game.getStatus() == 1);
		    	this.game.init();
		    	assertTrue(game.getStatus() == 2);
		    } catch(Exception e){
		    	fail("Could not set yo gane");
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
