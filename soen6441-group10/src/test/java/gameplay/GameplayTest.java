package gameplay;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import io.TextUserInterface;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import gameplay.BoardArea;
import gameplay.Die;
import gameplay.Game;
import gameplay.Player;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import util.Color;
import card.city.AnkhMorporkArea;
import card.player.DiscardPile;
import card.player.GreenPlayerCard;
import card.player.Symbol;
import card.random.RandomEventCard;

public class GameplayTest {

	Game game;
	Player player;
	Player player2;
	Player player3;
	Map<Integer, BoardArea> gameBoard;
	
	@BeforeClass
	public static void setUpClass() throws Exception {
		// Code executed before the first test method
	}

	@Before
	public void setUp() throws Exception {
		game = new Game();
		String[] playerNames = { "Ross", "Smith", "Rocco"};
		try {
			game.setUp(playerNames.length, playerNames);
		} catch (Exception e) {
			fail("Exception caught");
		}
		player = game.getPlayerOfColor(Color.RED);
		player2 = game.getPlayerOfColor(Color.YELLOW);
		player3 = game.getPlayerOfColor(Color.GREEN);
		
		gameBoard = game.getGameBoard();
		TextUserInterface UI = TextUserInterface.getUI();
		UI.setGame(game);
	}

	
	@Test
	public void initialGameTest(){
		BoardArea shades = gameBoard.get(AnkhMorporkArea.THE_SHADES.getAreaCode());
		BoardArea scours = gameBoard.get(AnkhMorporkArea.THE_SCOURS.getAreaCode());
		BoardArea dolly = gameBoard.get(AnkhMorporkArea.DOLLY_SISTERS.getAreaCode());

		assertFalse(shades.hasTroubleMarker());
		assertFalse(scours.hasTroubleMarker());
		assertFalse(dolly.hasTroubleMarker());
		game.init();
		assertTrue(shades.hasTroubleMarker());
		assertTrue(scours.hasTroubleMarker());
		assertTrue(dolly.hasTroubleMarker());
	}
	
	@Test
	public void troubleTest() {
		// Test adding trouble to area with trouble
		assertFalse(gameBoard.get(1).hasTroubleMarker());
		gameBoard.get(1).addTroubleMarker();
		assertTrue(gameBoard.get(1).hasTroubleMarker());
		assertFalse(gameBoard.get(1).addTroubleMarker());
		assertTrue(gameBoard.get(1).hasTroubleMarker());
		
		gameBoard.get(1).removeTroubleMarker();
		assertFalse(gameBoard.get(1).hasTroubleMarker());
		
		
		// Test adding one minion does not create trouble 
		gameBoard.get(1).addMinion(player);
		assertFalse(gameBoard.get(1).hasTroubleMarker());
		// Test adding second minion DOES create trouble
		gameBoard.get(1).addMinion(player2);
		assertTrue(gameBoard.get(1).hasTroubleMarker());
		gameBoard.get(1).addMinion(player);
		assertTrue(gameBoard.get(1).hasTroubleMarker());
		
		// Test removing minion removes trouble
		gameBoard.get(1).removeMinion(player);
		assertFalse(gameBoard.get(1).hasTroubleMarker());
		
		// Test that building cannot be built if there is trouble
		gameBoard.get(1).addMinion(player);
		assertTrue(gameBoard.get(1).hasTroubleMarker());
		gameBoard.get(1).addBuildingForPlayer(player);
		assertTrue(gameBoard.get(1).getBuildingOwner() == Color.UNDEFINED);
		// Now make sure that removing a minion the player can add a building no problem
		gameBoard.get(1).removeMinion(player);
		gameBoard.get(1).addBuildingForPlayer(player);
		assertTrue(gameBoard.get(1).getBuildingOwner() == player.getColor());
		
		// Make sure you cannot assainate on this area...because there is no trouble
		Symbol.ASSASINATION.getGameAction().accept(player2, game);
		assertEquals(gameBoard.get(1).getMinions().size(), 2);
		
		// Now add trouble and ensure an assination occues
		gameBoard.get(1).addTroubleMarker();
		Symbol.ASSASINATION.getGameAction().accept(player2, game);
		assertEquals(gameBoard.get(1).getMinions().size(), 1);
		// Make sure this removes the trouble
		assertFalse(gameBoard.get(1).hasTroubleMarker());
		
		// Lets make sure THE DRAGON removes the troulbe
		gameBoard.get(1).addTroubleMarker();
		Die.getDie().setCheat(1);
		RandomEventCard.DRAGON.getGameAction().accept(game, player);
	}
	
	
	@Test
	public void demonTest() {
		// Demons affect trouble the same way as minions...so adding a demon, means adding trouble
		assertFalse(gameBoard.get(1).hasTroubleMarker());
		gameBoard.get(1).addDemon();
		assertTrue(gameBoard.get(1).hasTroubleMarker());		
		gameBoard.get(1).removeDemon();
		assertFalse(gameBoard.get(1).hasTroubleMarker());
		
		// ensure assasination kills demon
		gameBoard.get(1).addDemon();
		Symbol.ASSASINATION.getGameAction().accept(player, game);
		assertTrue(gameBoard.get(1).getDemonCount() == 0);
		
	
	}
	
	@Test
	public void minionTest() {
		
	}
	
	@Test
	public void buildingTest() {
		
	}
	
	@Test
	public void trollTest() {
		
	}
	
	
	
	
	
	@After
	public void tearDown() throws Exception {
		// Code executed after each test
		System.out.println();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		// Code executed after the last test method
	}
}
