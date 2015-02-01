package bootstrap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import card.PersonalityCard;

public class DeckTest {
	
		private Deck<PersonalityCardWrapper> personalityDeck;
	
	    @Before
	    public void setUp() throws Exception {
	    	personalityDeck = Deck.getDeck(PersonalityCard.values(), p -> new PersonalityCardWrapper(p));
	    }
	 
	    @Test
	    public void testDeckShuffle() {
	    	// TODO: Rewrite this
	    }
	 
	    @After
	    public void tearDown() throws Exception {
	        // Code executed after each test  
	    	this.personalityDeck = null;
	    }

}
