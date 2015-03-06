package card;

import static org.junit.Assert.*;

import org.junit.*;

import card.Area;

public class CityCardTest {
	
		private Area card;
	
	    @BeforeClass
	    public static void setUpClass() throws Exception {
	        // Code executed before the first test method    
	    }
	 
	    @Before
	    public void setUp() throws Exception {
	        // Code executed before each test 
	    	this.card = new Area();
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
	    	Area card1=new Area();
	    	Area card2=new Area();
	    	
	    	card1.setTitle("The Shades");
	    	card2.setTitle("Dolly Sisters");
	    	
	    	card1.addNeighbour(card2 , true);
	    	assertTrue("DollySisters is not added as a neighbor of Shades", card2.isNeighbour(card1));
	    
	    	Area card3=new Area();
	    	card3.setTitle("Nap Hill");
	    	card1.addNeighbour(card3, false);
	    	assertFalse("Nap Hill is a neighbor of Shades but not vice versa", card3.isNeighbour(card1));
	    	
	    	Area card4=new Area();
	    	card3.setTitle("Dimwell");
	    	assertFalse("Dimwell is shades neighbor",card3.isNeighbour(card1));
	    }
	 
}
