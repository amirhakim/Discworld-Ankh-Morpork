package card;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gameplay.BoardArea;
import gameplay.Die;
import gameplay.Game;
import gameplay.Player;

import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import util.Color;
import card.player.GreenPlayerCard;
import card.player.Symbol;

public class SymbolTest {

	Game game;
	Player player;
	Player player2;
	Map<Integer, BoardArea> gameBoard;

	@BeforeClass
	public static void setUpClass() throws Exception {
		// Code executed before the first test method
	}

	@Before
	public void setUp() throws Exception {
		game = new Game();
		String[] playerNames = { "Ross", "Smith" };
		try {
			game.setUp(playerNames.length, playerNames);
		} catch (Exception e) {
			fail("Exception caught");
		}
		player = game.getPlayerOfColor(Color.RED);
		player2 = game.getPlayerOfColor(Color.YELLOW);
		gameBoard = game.getGameBoard();
//		game.init();
	}

	/**
	 * Test PLACE_MINION symbol
	 * Condition that player has no free minions
	 */
	@Test
	public void placeMinionNoMinionsTest() {

		System.out.println("~~PLACE MINION NO MINIONS TEST~~");

		Symbol placeMinionSymbol = Symbol.PLACE_MINION;
		// Fill up an area so player has no minions
		BoardArea secondArea = gameBoard.get(1);
		while (player.getMinionCount() != 0) {
			secondArea.addMinion(player);
		}
		int secondAreaMinionsBefore = secondArea.getMinionCountForPlayer(player);
		placeMinionSymbol.getGameAction().accept(player, game);
		int secondAreaMinionsAfter = secondArea.getMinionCountForPlayer(player);
		assertEquals(secondAreaMinionsAfter, secondAreaMinionsBefore - 1);
	}

	
	/**
	 * Test PLACE_MINION symbol
	 * Condition that player has only free minions
	 */
	@Test
	public void placeMinionFullMinionsTest() {

		System.out.println("~~PLACE MINION FULL MINIONS TEST~~");

		Symbol placeMinionSymbol = Symbol.PLACE_MINION;
		// Make Sure player has all his minions
		assertTrue(player.getMinionCount() == Player.TOTAL_MINIONS);
		placeMinionSymbol.getGameAction().accept(player, game);
		assertEquals(player.getMinionCount(), Player.TOTAL_MINIONS - 1);

	}

	/**
	 * Test PLACE_MINIONS symbol
	 * Condition that payer has some free minions
	 */
	@Test
	public void placceMinionPartialMinionsTest() {

		System.out.println("~~PLACE MINION PARTIAL MINIONS TEST~~");

		Symbol placeMinionTest = Symbol.PLACE_MINION;

		// Take away some of the players minions
		BoardArea area = gameBoard.get(1);
		area.addMinion(player);
		assertEquals(player.getMinionCount(), Player.TOTAL_MINIONS - 1);
		placeMinionTest.getGameAction().accept(player, game);
		assertEquals(player.getMinionCount(), Player.TOTAL_MINIONS - 2);
	}

	/**
	 * Test symbol place a building if player has no free buildings to place
	 */
	@Test
	public void placeBuildingNoFreeBuildingsTest() {
		
		System.out.println("~~PLACE BUILDING NO BUILDINGS TEST~~");
		
		// Remove all of players buildings
		int i = 1;
		player.increaseMoney(10000);
		int previousPlayerBalance = player.getMoney();
		while(player.getBuildings() != 0) {
			game.addBuilding(player, gameBoard.get(i));
			i++;
		}
		
		Symbol.PLACE_A_BUILDING.getGameAction().accept(player, game);
		
		// Ensure player has not lost any buildings
		assertEquals(player.getBuildings(), 0);
		// Ensure building cost something
		assertTrue(player.getMoney() < previousPlayerBalance);
		
		
		
	}
	
	/**
	 * Test symbol place a building if player has free buildings to place
	 */
	@Test
	public void placeBuildingFreeBuildingsTest() {
		
		System.out.println("~~PLACE BUILDING FREE BUILDINGS TEST~~");
		
		// Give player unlimited funds
		player.increaseMoney(10000);
		int previousPlayerBalance = player.getMoney();
		
		Symbol.PLACE_A_BUILDING.getGameAction().accept(player, game);
		// Ensure that the players buildings have decreased
		assertEquals(player.getBuildings(), Player.TOTAL_BUILDINGS - 1);
		// Ensure building cost something
		assertTrue(player.getMoney() < previousPlayerBalance);
		
	}
	
	
	/**
	 * Test that if all areas have trouble marker
	 * your presented with the only area that doesn't
	 * have a trouble marker
	 */
	@Test
	public void placeBuildingNoTroubleTest() {
		
		System.out.println("~~PLACE BUILDING NO TROUBLE TEST~~");
		
		// Give player unlimited funds
		player.increaseMoney(10000);
		int previousPlayerBalance = player.getMoney();
		// Can not add building to build area with trouble
		for(BoardArea ba : gameBoard.values()) {
			ba.addTroubleMarker();
		}
		gameBoard.get(1).removeTroubleMarker();
		Symbol.PLACE_A_BUILDING.getGameAction().accept(player, game);
		
		// Ensure that the players buildings have decreased
		assertEquals(player.getBuildings(), Player.TOTAL_BUILDINGS - 1);
		// Ensure building cost something
		assertTrue(player.getMoney() < previousPlayerBalance);
		
		//Only one place to place a building, area(1) which doesn't have trouble marker
		//Ensure player chose this area
		assertTrue(gameBoard.get(1).getBuildingOwner() == player.getColor());
	}
	
	/**
	 * Test that you cannot place a building if they have trouble markers
	 */
	@Test
	public void impossibleBuildingPlacementTest() {
		player.increaseMoney(1000);
		// Can not add building to build area with trouble
		for(BoardArea ba : gameBoard.values()) {
			ba.addTroubleMarker();
		}
		int buildingCount = player.getBuildings();
		Symbol.PLACE_A_BUILDING.getGameAction().accept(player, game);
		assertEquals(player.getBuildings(), buildingCount);
	}
	
	/**
	 * Test assination symbol
	 * 
	 */
	@Test
	public void assinateTest(){
		System.out.println("~~ASSINATE TEST~~");
		
		BoardArea area = gameBoard.get(1);
		
		// Set up troubled areas
		area.addTroubleMarker();
		// Set up a minion to kill
		Player player2 = game.getPlayerOfColor(Color.YELLOW);
		area.addMinion(player2);
		area.addTroll();
		area.addDemon();
		
		
		// Get count of pieces before
		int trolls = area.getTrollCount();
		int demon = area.getDemonCount();
		int player2Minions = area.getMinionCountForPlayer(player2);
		

		Symbol.ASSASINATION.getGameAction().accept(player, game);
		
		// Get count of pieces after
		int trollsAfter = area.getTrollCount();
		int demonAfter = area.getDemonCount();
		int player2MinionsAfter = area.getMinionCountForPlayer(player2);
		
		// Ensure one piece is decreased
		boolean trollsBool = trollsAfter < trolls;
		boolean demonBool = demonAfter < demon;
		boolean player2MinionsBool = player2MinionsAfter < player2Minions;
		
		assertTrue(trollsBool || demonBool || player2MinionsBool);
		
	}
	
	/**
	 * Test Symbole.REMOVE_TROUBLE_MARKER
	 */
	@Test
	public void troubleMarkerTest() {
		System.out.println("~~TROUBLE MARKER TEST~~");
		// Add trouble marker to area
		BoardArea area = gameBoard.get(1);
		area.addTroubleMarker();
		boolean hasTroubleBefore = area.hasTroubleMarker();
		Symbol.REMOVE_TROUBLE_MARKER.getGameAction().accept(player, game);
		assertNotEquals(hasTroubleBefore, area.hasTroubleMarker());
	}
	
	
	/**
	 * Test TAKE_MONEY symbol
	 */
	@Test
	public void takeMoneyTest() {
		System.out.println("~~TAKE MONEY TEST~~");	
		Integer money = GreenPlayerCard.INIGO_SKIMMER.getMoney();
		Integer bankBefore = game.getBank().getBalance();
		Integer playerBefore = player.getMoney();
		game.setCurrentCardInPlay(GreenPlayerCard.INIGO_SKIMMER);
		Symbol.TAKE_MONEY.getGameAction().accept(player, game);
		
		assertEquals(bankBefore - money, game.getBank().getBalance());
		assertEquals(playerBefore + money, player.getMoney());
		
		
		
	}
	
	/**
	 * Test RANDOM EVENT symbol
	 * 
	 */
	@Test
	public void randomEventTest() {
		System.out.println("~~~RANDOM EVENT TEST~~~");
		Symbol.RANDOM_EVENT.getGameAction().accept(player, game);
		// No test really .. just make sure its called in IO
	}
	
	
	/**
	 * Test Interrupt
	 */
	@Test
	public void intteruptGaspodeTest() {
		System.out.println("~~INTERRUPT GASPODE TEST~~~");
		gameBoard.get(1).addTroubleMarker();
		gameBoard.get(1).addMinion(player2);
		
		// Give player 2 GASPODE
		game.addPlayerCard(player2, GreenPlayerCard.GASPODE);
		
		
		Symbol.ASSASINATION.getGameAction().accept(player, game);
		// Check if assasination was played
		if(player2.getPlayerCards().size() == 0) {
			// Interrupt was called
			// Assert he still has his minion on the board
			assertEquals(gameBoard.get(1).getMinionCountForPlayer(player2), 1);
		} else {
			// interrupt was not called
			assertEquals(gameBoard.get(1).getMinionCountForPlayer(player2), 0);	
		}
	}
	
	@Test
	public void interruptFreshStartTest(){
		System.out.println("~~INTERRUPT FRESH START CLUB TEST~~~");
		gameBoard.get(1).addTroubleMarker();
		gameBoard.get(1).addMinion(player2);
		
		game.addPlayerCard(player2, GreenPlayerCard.THE_FRESH_START_CLUB);
		
		Symbol.ASSASINATION.getGameAction().accept(player, game);
		
		// Check if interrupt was played
		if(player2.getPlayerCards().size() == 0) {
			// It was played
			assertEquals(gameBoard.get(1).getMinionCount(), 0);
			// Make sure one area has the removed minion
			int count = 0;
			for(BoardArea ba : gameBoard.values()) {
				if(ba.getMinionCount() == 1) {
					count++;
				}
			}
			assertEquals(count, 1);
		} else {
			// interrupt wasnt played, make sure the minion was killed and that was all
			assertEquals(gameBoard.get(1).getMinionCount(), 0);
			// Make sure one area has the removed minion
			int count = 0;
			for(BoardArea ba : gameBoard.values()) {
				if(ba.getMinionCount() == 1) {
					count++;
				}
			}
			assertEquals(count, 0);
		}
		
	};
	
	@Test
	public void interruptTakeMoneyTest() {
		System.out.println("~~INTERRUPT CARDS THAT TAKE MONEY~~");
		
		// Cheat the dice roll
		Die.getDie().setCheat(7);
		
		// give player2 a card that stops text action takes money
		game.addPlayerCard(player2, GreenPlayerCard.WALLACE_SONKY);
		// make sure players are well funded
		player2.increaseMoney(10);
		player.increaseMoney(10);
		
		//have player2 act out take money card
		// choices are HERE_N_NOW, NOBBY_NOBBS
		
		GreenPlayerCard.HERE_N_NOW.getText().accept(player, game);
		
		// Check if player2 played the interrupt card
		if(player2.getPlayerCards().size() == 0) {
			// player 2 did player interrupt
			// both players should still have 10$
			assertEquals(player2.getMoney(), 10);
			assertEquals(player.getMoney(), 10);
		} else {
			assertEquals(player2.getMoney(), 7);
			assertEquals(player.getMoney(), 13);
		}
		
		
	}
	
	@Test
	public void interruptCardForMoney() {
		System.out.println("~~INTERRUPT CARD TAKE EXHCANGE MONEY FOR CARDS~~");
		
		// give player2 a card that stops text
		game.addPlayerCard(player2, GreenPlayerCard.WALLACE_SONKY);
		// make sure players are well funded
		player2.increaseMoney(10);
		player.increaseMoney(10);
		//since player will give a card from player 2, lets give him a card to give
		// since in real game, if player only has one card, he is playing rosie_palm, 
		// so he needs two cards
		game.addPlayerCard(player, GreenPlayerCard.HERE_N_NOW);
		game.addPlayerCard(player, GreenPlayerCard.HEX);
		
		
		GreenPlayerCard.ROSIE_PALM.getText().accept(player, game);
		
		// Check if player2 played the interrupt card
		if(!player2.getPlayerCards().contains(GreenPlayerCard.WALLACE_SONKY)) {
			//interrupt was played
			//player should both have 10$
			// player 2 should have 0 cards
			// player should have his original 2 cards
			assertEquals(player2.getMoney(), 10);
			assertEquals(player.getMoney(), 10);
			assertEquals(player.getPlayerCards().size(), 2);
		} else {
			assertEquals(player.getMoney(), 12);
			assertEquals(player2.getMoney(), 8);
			assertEquals(player.getPlayerCards().size(), 1);
		}
		
		
		
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
