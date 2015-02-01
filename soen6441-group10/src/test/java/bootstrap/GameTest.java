package bootstrap;

import static org.junit.Assert.*;
import static org.junit.Before.*;

import java.util.ArrayList;

import org.junit.*;

public class GameTest {
	
		private Game game;
		private String[] names = {"test1", "test2", "test3"};
	
	 
	    @Before
	    public void setUp() throws Exception {
    		this.game = new Game();
    		this.game.setUp(3, names);
	        // Code executed before each test 
	    }
	 
	    @Test
	    /**
	     * Test players names match
	     */
	    public void testPlayerName() {
	    	try {
	    		// Loop through players to make sure this player exists.
	    		Player[] players = this.game.getPlayers();
	    		for(int i=0; i<players.length;++i) {
	    			assertTrue(players[i].getName().equals(this.names[i]));
	    		}
	    	} catch (Exception e) {
	    		fail("Failed " + e.getMessage());
	    	}
	    }
	    
	    @Test
	    /**
	     * Test that creating too many players doesn't work
	     */
	    public void testMaxNumberOfPlayers(){
	    	try{
	    		String[] names2 = {"test1", "test2", "test3", "test4", "test5"};
	    		this.game.setUp(5,names2);
	    		fail("Not catching too many players");
	    	} catch (Exception e) {
	    	}
	    }
	 
	    @Test
	    /**
	     * Test that names and player size don't match
	     */
	    public void testDiffSizes() {
	    	
	    }
	    
	    @Test
	    /*
	     * Test that creating too few players doesnt work
	     */
	    public void testMinNumberOfPlayers(){
	    	this.game = new Game();
	    	try{

	    		String[] names = {"test1", "test2", "test3"};
	    		this.game.setUp(1,names);
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
	    		String[] names = {"test1", "test2", "test3"};
	    		this.game.setUp(numberOfPlayers, names);
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
	    	assertTrue(game.getState() == 0);
	    	try {
	    		String[] names = {"test1", "test2", "test3"};
	    		this.game.setUp(3,names);
	    		assertTrue(game.getState() == 1);
		    	this.game.init();
		    	assertTrue(game.getState() == 2);
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
