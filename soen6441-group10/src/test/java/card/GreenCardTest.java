package card;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import gameplay.BoardArea;
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

public class GreenCardTest {

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
	}

	@Test
	public void zorgoTest() {
		System.out.println("~~~TESTING ZARGO~~~");
		game.assignPersonality(player);
		Card personalityBefore = player.getPersonality();
		GreenPlayerCard card = GreenPlayerCard.ZORGO_THE_RETRO_PHRENOLOGIST;
		card.getText().accept(player, game);
		Card personalityAfter = player.getPersonality();
		// Not sure if I can compare enums..
		assertTrue(personalityBefore != personalityAfter);
		
	}

	@Test
	public void historyMonksTest() {
		System.out.println("~~~TESTING HISTORY MONKS~~~");
		// Set up some fake discard files
		game.discardCard(GreenPlayerCard.INIGO_SKIMMER, null);
		game.discardCard(GreenPlayerCard.HEX, null);
		game.discardCard(GreenPlayerCard.HERE_N_NOW, null);
		game.discardCard(GreenPlayerCard.HARRY_KING, null);
		
		int cardsBeforeSize = player.getPlayerCards().size();
		DiscardPile pile = game.getDiscardPile();
		int pileSizeBefore = pile.size();

		GreenPlayerCard.HISTORY_MONKS.getText().accept(player, game);

		int cardsAfterSize= player.getPlayerCards().size();
		int pileSizeAfter = pile.size();
		
		assertEquals(pileSizeAfter, pileSizeBefore - 4);
		assertEquals(cardsAfterSize, cardsBeforeSize + 4);
	}
	
	@Test
	public void hereNowTest() {

		System.out.println("~~~TESTING NERE NOW~~~");
		// Give both players in game money
		Collection<Player> players = game.getPlayers();
		
		player.increaseMoney(10);
		Player playerTwo = game.getPlayerOfColor(Color.YELLOW);
		playerTwo.increaseMoney(10);
		gameBoard.get(1).addMinion(player);
	
		GreenPlayerCard.HERE_N_NOW.getText().accept(player, game);
	}
	
	
	/**
	 * Test size of player deck before and after
	 * Test player hand size before and after
	 */
	@Test
	public void HexTest() {
		
		System.out.println("~~~TESTING HEX~~~");
		
		int playerDeck1 = game.getPlayerDeck().size();
		
		for(int i=0;i<3;++i){
			Optional<GreenPlayerCard> card = game.drawPlayerCard();
			player.addPlayerCard(card.get());
		}
		
		// Make sure set up ran correctly
		assertEquals(playerDeck1, game.getPlayerDeck().size()+3);
		assertEquals(player.getPlayerCards().size(), 3);
		
		GreenPlayerCard.HEX.getText().accept(player, game);
		
		// Assert player has 6 cards (3 from start, 3 green card)
		assertEquals(player.getPlayerCards().size(), 6);
		// Assert game has 48-6 cards (6 from 3 before, 3 from green card);
		assertEquals(game.getPlayerDeck().size(), 48-6);
		
	}
	
	@Test
	public void harrKingTest(){
		
		System.out.println("~~TESTING HARRY KING~~");
		
		System.out.println("...testing cant remove any cards if dont have any");
		GreenPlayerCard.HARRY_KING.getText().accept(player, game);
		// Ensure that with no cards the player couldn't remove any
		assertEquals(player.getPlayerCards().size(),0);
		// Give player 3 cards
		for(int i=0;i<3;++i) {
			Optional<GreenPlayerCard> card = game.drawPlayerCard();
			player.addPlayerCard(card.get());
		}
		System.out.println("...testing removing cards - no restrictions");
		GreenPlayerCard.HARRY_KING.getText().accept(player, game);
		// Ensure player chose at least one card
		assertFalse(player.getPlayerCards().size() == 3);
		
		GreenPlayerCard gp = GreenPlayerCard.HARRY_KING;
		player.addPlayerCard(gp);
		System.out.println("...testing removing cards with HARRY KING as option");
		GreenPlayerCard.HARRY_KING.getText().accept(player, game);
	}
	
	@Test
	public void operaHouseTest() {
		System.out.println("~~TESTING THE_OPERA_HOUSE ~~");
		
		// Assert that player can not get any money if has no minions
		assertEquals(player.getMoney(), 0);
		BoardArea isles = null;
		for(BoardArea ba: gameBoard.values()) {
			if(ba.getArea() == AnkhMorporkArea.ISLE_OF_GODS) {
				isles = ba;
				break;
			}
		}
		// add 3 minions to isles
		isles.addMinion(player);
		isles.addMinion(player);
		isles.addMinion(player);
		
		GreenPlayerCard.THE_OPERA_HOUSE.getText().accept(player, game);
		// Assert that the player now has 3 dollars
		assertEquals(player.getMoney(), 3);
		
		// Assert player stays at 3 dollars if bank has no money
		game.getBank().decreaseBalance(game.getBank().getBalance());
		GreenPlayerCard.THE_OPERA_HOUSE.getText().accept(player, game);
		assertEquals(player.getMoney(),3);	
	}
	
	@Test
	public void nobbyNobbTest() {
		System.out.println("~~Testing NOBBY_NOBBS~~");
		
		// Currently player doesn't have any money
		// Assert that we didn't get any money
		GreenPlayerCard.NOBBY_NOBBS.getText().accept(player,game);
		assertEquals(player.getMoney(), 0);
		//give all players 3 dollars
		for(Player p : game.getPlayers()) {
			p.increaseMoney(3);
		}
		
		GreenPlayerCard.NOBBY_NOBBS.getText().accept(player, game);
		// Assert player got +3 dollars
		assertEquals(player.getMoney(), 6);
		// Assert that some player lost 3 dollars
		int playersLessThan3 = 0;
		for(Player p : game.getPlayers()) {
			if(p.getMoney() < 3) {
				playersLessThan3++;
			}
		}
		
		assertEquals(playersLessThan3, 1);
	}
	
//	@Test
	public void modoTest() {	
		// Test discarding with 1+ cards
		player.addPlayerCard(GreenPlayerCard.NOBBY_NOBBS);
		player.addPlayerCard(GreenPlayerCard.MODO);
		GreenPlayerCard.MODO.getText().accept(player, game);
		
		assertEquals(game.getDiscardPile().size(), 1);
		assertEquals(player.getPlayerCards().size(), 1);
		
		// Test discarding with only modo card
		player2.addPlayerCard(GreenPlayerCard.MODO);
		GreenPlayerCard.MODO.getText().accept(player, game);
		
		// No change in discard pile or players hand
		assertEquals(game.getDiscardPile().size(), 1);
		assertEquals(player2.getPlayerCards().size(), 1);
		
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
