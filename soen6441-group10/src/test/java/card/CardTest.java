package card;

import static org.junit.Assert.*;

import org.junit.*;

import card.PersonalityCard;

public class CardTest {
	
		private PersonalityCard personality;
	
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
	    	
	    	String name = "test";
	    	this.personality.setTitle(name);
	    	assertEquals(this.personality.getTitle(), name);
	    	assertNotEquals(this.personality.getTitle(), "sdfsdf");
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
