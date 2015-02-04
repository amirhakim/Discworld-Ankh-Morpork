package card;

import static org.junit.Assert.*;

import org.junit.*;

import card.PersonalityCard;

public class CardTest {
	
		private PersonalityCard personality;
		private PersonalityCard notPersonality;
	
	    @BeforeClass
	    public static void setUpClass() throws Exception {
	        // Code executed before the first test method    
	    }
	 
	    @Before
	    public void setUp() throws Exception {
	        // Code executed before each test 
	    	this.personality = new PersonalityCard();
	    }
	 
	    @Test
	    /*
	     * Test bank balance
	     */
	    public void testTitleSetting() {
	    	assertNull(this.personality.getTitle());
	    	assertEquals(this.personality.hashCode(),31);
	    	String name = "test";
	    	this.personality.setTitle(name);
	    	assertEquals(this.personality.getTitle(), name);
	    	assertNotEquals(this.personality.getTitle(), "sdfsdf");
	    	assertNotEquals(this.personality.hashCode(),31);
	    	assertFalse(this.personality.equals(notPersonality));
	    	assertTrue(this.personality.equals(personality));
	    }
	 
	 
	    @After
	    public void tearDown() throws Exception {
	        // Code executed after each test  
	    }
	 
	    @AfterClass
	    public static void tearDownClass() throws Exception {
	        // Code executed after the last test method 
	    }
}
