package card;

import static org.junit.Assert.*;

import java.util.Map;

import gameplay.Game;
import gameplay.Player;
import card.player.Symbol;

import org.junit.*;

import util.Color;

public class SymbolTest {

		
		    @BeforeClass
		    public static void setUpClass() throws Exception {
		        // Code executed before the first test method    
		    }
		 
		    @Before
		    public void setUp() throws Exception {
		    }
		 
		    @Test
		    public void placeMinionTest() {
		    //	System.out.println("test");
		    	Symbol placeMinionSymbol = Symbol.PLACE_MINION;
		    	Game game = new Game();
		    	String[] playerNames = {"Ross", "Smith"};
		    	try {
		    		game.setUp(playerNames.length, playerNames);
		    	}catch(Exception e) {fail("Exception caught");}
		    	Player player = game.getPlayerOfColor(Color.RED);
		    	Map<Integer, BoardArea> gameBoard = game.getGameBoard();
		    	
		    	BoardArea secondArea = gameBoard.get(1);
		    	while(player.getMinionCount() != 0) {
		    		secondArea.addMinion(player);
		    	}
		    	Map<Integer, BoardArea> ggg = game.getGameBoard();
		    	placeMinionSymbol.getGameAction().accept(player, game);
		    	
		    	
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
