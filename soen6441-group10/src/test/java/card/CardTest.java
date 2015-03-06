package card;

import static org.junit.Assert.*;

import java.util.List;
import java.util.function.BiConsumer;

import org.junit.*;

import card.personality.PersonalityCard;
import card.player.PlayerCards;
import card.player.Symbol;
import gameplay.Player;
import gameplay.Game;

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
	 
	    @Test
	    /*
	     * Test Card Enum Function
	     * Create player and Game
	     * Get symbols from player card MR BOGGIS
	     * Loop through symbols and execute the functions they hold
	     * Get the text MR BOGGIS has
	     * Execute text for MR BOGGIS
	     */
	    public void testCard(){
	    	Player p = new Player();
	    	Game g = new Game();
	    	p.setName("Rossco");

	    	List<Symbol> symbols = PlayerCards.MR_BOGGIS.getSymbols();	
	    	for(Symbol s : symbols){
	    		s.getGameAction().accept(p, g);
	    	}
	    	
	    	BiConsumer<Player, Game> text = PlayerCards.MR_BOGGIS.getText();
	    	text.accept(p, new Game());
	    	
	    	
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
