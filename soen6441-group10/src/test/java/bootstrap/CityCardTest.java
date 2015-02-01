package bootstrap;

import static org.junit.Assert.*;
import static org.junit.Before.*;

import org.junit.*;

public class CityCardTest {
	
		private CityCard card;
	
	    @BeforeClass
	    public static void setUpClass() throws Exception {
	        // Code executed before the first test method    
	    }
	 
	    @Before
	    public void setUp() throws Exception {
	        // Code executed before each test 
	    	this.card = new CityCard();
	    }
	 
	    @Test
	    /*
	     * Test Card Title
	     */
	    public void testCardTitle() {
	    	card.setTitle("Isle of Gods");
	    	// Ensure value exists
	    	assertNotNull("City card title is null", card.getTitle());
	    	
	    	//Ensure title set correctly
	    	assertTrue("Title is not Isle of Gods",card.getTitle().equals("Isle of Gods"));
	    }
	 
	    @Test
	    /*
	     * Test Card Neighbor
	     */
	    public void addNeighborTest(){
	    	CityCard card1=new CityCard();
	    	CityCard card2=new CityCard();
	    	
	    	card1.setTitle("The Shades");
	    	card2.setTitle("Dolly Sisters");
	    	
	    	card1.addNeighbour(card2 , true);
	    	assertTrue("DollySisters is not added as a neighbor of Shades", card2.isNeighbour(card1));
	    
	    	CityCard card3=new CityCard();
	    	card3.setTitle("Nap Hill");
	    	card1.addNeighbour(card3, false);
	    	assertFalse("Nap Hill is a neighbor of Shades but not vice versa", card3.isNeighbour(card1));
	    	
	    	CityCard card4=new CityCard();
	    	card3.setTitle("Dimwell");
	    	assertFalse("Dimwell is shades neighbor",card3.isNeighbour(card1));
	    }
	 
}
