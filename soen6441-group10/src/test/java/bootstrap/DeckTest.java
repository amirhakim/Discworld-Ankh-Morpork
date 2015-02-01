package bootstrap;

import static org.junit.Assert.*;
import static org.junit.Before.*;

import java.util.ArrayList;

import org.junit.*;

public class DeckTest {
	
		private Deck personalityDeck;
	
	    @BeforeClass
	    public static void setUpClass() throws Exception {
	        // Code executed before the first test method    
	    }
	 
	    @Before
	    public void setUp() throws Exception {
	        // Code executed before each test 
	    	this.personalityDeck = new PersonalityDeck();
	    }
	 
	    @Test
	    /*
	     * Test Deck is shuffled appropriately
	     */
	    public void testDeckShuffle() {
	    	// Create a large array of decks
	    	int size = 1000;
	    	PersonalityDeck[] pd = new PersonalityDeck[size];
	    	for(int i=0;i<pd.length;++i){
	    		pd[i] = new PersonalityDeck();
	    	}
	    	
	    	// Get the last card title in this array of decks
	    	String lastCardTitle = pd[0].pop().getTitle();
	    	
	    	int count = 1;
	    	for(int i=1;i<pd.length;++i) {
	    		// Get the last card of each deck
	    		Card tmp = pd[i].pop();
	    		// If it is the same as the last of the first, increase count
	    		if(lastCardTitle.equals(tmp.getTitle())){
	    			count++;
	    		}
	    	}
	    	// If count is the same size of array of decks, it isnt being shuffled.
	    	assertFalse(count == size);
	    	
	    	
	    	
	    }
	 
	 
	    @After
	    public void tearDown() throws Exception {
	        // Code executed after each test  
	    	this.personalityDeck = null;
	    }
	 
	    @AfterClass
	    public static void tearDownClass() throws Exception {
	        // Code executed after the last test method 
	    }
}
