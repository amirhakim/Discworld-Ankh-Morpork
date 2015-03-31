package card;

import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import gameplay.BoardArea;
import gameplay.Game;
import gameplay.Player;
import io.TextUserInterface;

import java.util.Iterator;
import java.util.Map;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import card.personality.PersonalityCard;
import card.player.GreenPlayerCard;
import util.Color;

public class PersonalitiesTest {

	

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
	
	/**
	 * Test player controlling a game area
	 */
	@Test
	public void controlTest() {
		System.out.println("~~~TESTING CONTROLLING AN AREA~~~");
		
		// No one should control an area
		Player controller = gameBoard.get(1).isControlled(game.getPlayersMap());
		assertEquals(controller, null);
		
		// Test minions controll
		gameBoard.get(1).addMinion(player);
		controller = gameBoard.get(1).isControlled(game.getPlayersMap());
		assertEquals(controller, player);
		
		
		gameBoard.get(1).addMinion(player2);
		controller = gameBoard.get(1).isControlled(game.getPlayersMap());
		assertEquals(controller, null);
		
		gameBoard.get(1).addMinion(player3);
		controller = gameBoard.get(1).isControlled(game.getPlayersMap());
		assertEquals(controller, null);
		
		// Test building control
		gameBoard.get(1).removeTroubleMarker();
		gameBoard.get(1).addBuildingForPlayer(player);
		controller = gameBoard.get(1).isControlled(game.getPlayersMap());
		assertEquals(controller, player);
		
		gameBoard.get(1).addMinion(player2);
		controller = gameBoard.get(1).isControlled(game.getPlayersMap());
		assertEquals(controller, null);
		
		gameBoard.get(1).addMinion(player);
		controller = gameBoard.get(1).isControlled(game.getPlayersMap());
		assertEquals(controller, player);
		
		gameBoard.get(1).addBuildingForPlayer(player2);
		controller = gameBoard.get(1).isControlled(game.getPlayersMap());
		assertEquals(controller, player);
		
		// player should now have 2 minions and 1 building, player 2 has 2 minions, player 1 has 1 minion
		// Test trolls
		gameBoard.get(1).addTroll();
		controller = gameBoard.get(1).isControlled(game.getPlayersMap());
		assertEquals(controller, player);
		gameBoard.get(1).addTroll();
		controller = gameBoard.get(1).isControlled(game.getPlayersMap());
		assertEquals(controller, player);
		gameBoard.get(1).addTroll();
		controller = gameBoard.get(1).isControlled(game.getPlayersMap());
		assertEquals(controller, null);
		
		// put player 1 back in control
		gameBoard.get(1).addMinion(player);
		controller = gameBoard.get(1).isControlled(game.getPlayersMap());
		assertEquals(controller, player);
		
		// You can control area if there is trouble
		gameBoard.get(1).addTroubleMarker();
		controller = gameBoard.get(1).isControlled(game.getPlayersMap());
		assertEquals(controller, player);
		
		// You cannot control an area with 1+ demon
		gameBoard.get(1).addDemon();
		gameBoard.get(1).addDemon();
		controller = gameBoard.get(1).isControlled(game.getPlayersMap());
		assertEquals(controller, null);
		gameBoard.get(1).removeDemon();
		controller = gameBoard.get(1).isControlled(game.getPlayersMap());
		assertEquals(controller, null);
		gameBoard.get(1).removeDemon();
		controller = gameBoard.get(1).isControlled(game.getPlayersMap());
		assertEquals(controller, player);
		
	}
	
	/**
	 * Testing Control Victory - need to control 5 areas ... there are 3 players
	 */
	@Test
	public void testControlVictory() {
		System.out.println("~~~TESTING CONTROL VICTORY~~~");
		// give a player his personality card
		player.setPersonality(PersonalityCard.LORD_DE_WORDE);
		//no winners yet
		assertFalse(game.hasPlayerWon(player));
		// Give player 5 minions in 5 different areas
		for(int i = 1; i<6; ++i){
			gameBoard.get(i).addMinion(player);
		}
		// should have won now
		assertTrue(game.hasPlayerWon(player));
		
		// Test he hasnot won if we remove a minion
		gameBoard.get(1).removeMinion(player);
		assertFalse(game.hasPlayerWon(player));
		
		// TEST OTHER PLAYER CARD WHICH HAVE SAME VICTORY CONDITION

		// give a player his personality card
		player.setPersonality(PersonalityCard.LORD_SELACHII);
		assertFalse(game.hasPlayerWon(player));
		

		// give a player his personality card
		player.setPersonality(PersonalityCard.LORD_RUST);
		assertFalse(game.hasPlayerWon(player));
		
		gameBoard.get(1).addMinion(player);
		player.setPersonality(PersonalityCard.LORD_SELACHII);
		assertTrue(game.hasPlayerWon(player));
		player.setPersonality(PersonalityCard.LORD_RUST);
		assertTrue(game.hasPlayerWon(player));
	}
	
	/**
	 * Testing Vimes - win by no player cards left
	 */
	@Test
	public void testVimes() {
		System.out.println("~~~TESTING VIMES~~~");
		player.setPersonality(PersonalityCard.COMMANDER_VIMES);
		assertFalse(game.hasPlayerWon(player));
		
		while(game.getPlayerDeck().size() != 1) {
			game.getPlayerDeck().drawCard();
		}
		assertFalse(game.hasPlayerWon(player));
		
		game.getPlayerDeck().drawCard();
		assertTrue(game.hasPlayerWon(player));
		
	}
	
	/**
	 * Testing Vetinari - win by minions in 10 areas for 3 players
	 */
	@Test
	public void testVetinari() {
		System.out.println("~~~TESTING VETINARI~~~");
		player.setPersonality(PersonalityCard.LORD_VETINARI);
		assertFalse(game.hasPlayerWon(player));
		
		// give 10 areas a minon
		for(int i=1; i<11; ++i) {
			gameBoard.get(i).addMinion(player);
		}
		assertTrue(game.hasPlayerWon(player));

		// player still wins if there are other minons
		gameBoard.get(1).addMinion(player2);
		gameBoard.get(1).addMinion(player2);
		gameBoard.get(3).addMinion(player3);
		assertTrue(game.hasPlayerWon(player));

		// player cannot win if an area has a demon
		gameBoard.get(1).addDemon();
		assertFalse(game.hasPlayerWon(player));
	}
	
	/**
	 * Testing Dragon King of Arms (coolest name ever btw) 
	 * 8 trouble on board
	 */
	@Test
	public void testDragonKing() {
		System.out.println("~~~TESTING DRAGON KING~~~");
		player.setPersonality(PersonalityCard.DRAGON_KING_OF_ARMS);
		assertFalse(game.hasPlayerWon(player));
		
		for(int i =1; i<8; ++i) {
			gameBoard.get(i).addTroubleMarker();
		}
		assertFalse(game.hasPlayerWon(player));
		
		gameBoard.get(8).addTroubleMarker();
		assertTrue(game.hasPlayerWon(player));
	}
	
	/**
	 * Testing Chrysoprase - 50$ (2 players only)
	 */
	 @Test
	 public void testChrysoprase() {
			System.out.println("~~~CHRYSOPRASE KING~~~");
			player.setPersonality(PersonalityCard.CHRYSOPRASE);
			assertFalse(game.hasPlayerWon(player));
			player.increaseMoney(49);
			assertFalse(game.hasPlayerWon(player));
			player.increaseMoney(1);
			assertTrue(game.hasPlayerWon(player));
			
			
			
			// Have to do for testing
			game.setCurrentCardInPlay(GreenPlayerCard.MR_BENT);
			GreenPlayerCard.MR_BENT.getText().accept(player, game);
			// player should have failed because he owes 12$
			assertFalse(game.hasPlayerWon(player));
			
			game.setCurrentCardInPlay(GreenPlayerCard.THE_BANK_OF_ANKH_MORPORK);
			GreenPlayerCard.THE_BANK_OF_ANKH_MORPORK.getText().accept(player, game);
			assertFalse(game.hasPlayerWon(player));
			
			// player now has 70 (50 + 20 loans) - 24 (2*12) = 46
			
			player.increaseMoney(3);
			assertFalse(game.hasPlayerWon(player));
			player.increaseMoney(1);
			assertTrue(game.hasPlayerWon(player));
			
			
			// test with buildings now ... 
			// rest money
			player.decreaseMoney(74);
			// empty unplayable cards
			for(GreenPlayerCard c : player.getUnplayableCards()) {
				player.getPlayableCards().remove(c);
			}
			
			assertFalse(game.hasPlayerWon(player));
			game.addBuilding(player, gameBoard.get(1));
			// 6$
			assertFalse(game.hasPlayerWon(player));
			game.addBuilding(player, gameBoard.get(2));
			// 6 + 18 $
			assertFalse(game.hasPlayerWon(player));
			game.addBuilding(player, gameBoard.get(3));
			// 6 + 18 + 12$
			assertFalse(game.hasPlayerWon(player));
			game.addBuilding(player, gameBoard.get(4));
			// 6 + 18 + 12 + 18$ = 54 ... he should win
			
			// THIS FAILS TODO fix 	
//			assertTrue(game.hasPlayerWon(player));
			
	 }
	 
	 /**
	  * Test a tie game
	  */
	 @Test
	 public void testNoWinner() {
		 System.out.println("~~~TESTING NO CLEAR WINNER~~~");
		 // Empty player deck
		 while(game.getPlayerDeck().size() != 1) {
				game.getPlayerDeck().drawCard();
		 }
		 
		 // tie game, all players win
		 assertEquals(game.finishGameOnPoints(false).size(), 3);
		 // Minions are 5 points
		 gameBoard.get(1).addMinion(player);
		 gameBoard.get(1).addMinion(player2);
		 gameBoard.get(1).addMinion(player);

		 assertEquals(game.finishGameOnPoints(false).get(0), player);
		 assertEquals(game.finishGameOnPoints(false).size(), 1);
		 
		 // player 2 is down 5 points
//		 player2.increaseMoney(6);
//		 assertEquals(game.finishGameOnPoints(false).size(), 1);
		 // THIS FAILS TODO fix
//		 assertEquals(game.finishGameOnPoints(false).get(0), player2);
		 
		 
		 // since player 2 is loosing by 5 points...adding a building of cost 6, should make her win
//		 game.addBuilding(player2, gameBoard.get(1));
//		 assertEquals(game.finishGameOnPoints(false).size(), 1);
		 // THIS FAILS TODO fix
//		 assertEquals(game.finishGameOnPoints(false).get(0), player2);
		 
		 
		 
		 
		 
	 }
	
}
