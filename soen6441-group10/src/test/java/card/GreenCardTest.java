package card;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.Map;
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
import card.player.DiscardPile;
import card.player.GreenPlayerCard;

public class GreenCardTest {

	Game game;
	Player player;
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
		gameBoard = game.getGameBoard();
	}

	/**
	 */
	@Test
	public void zorgoTest() {
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
		// Give both players in game money
		Collection<Player> players = game.getPlayers();
		
		player.increaseMoney(10);
		Player playerTwo = game.getPlayerOfColor(Color.YELLOW);
		playerTwo.increaseMoney(10);
		gameBoard.get(1).addMinion(player);
	
		GreenPlayerCard.HERE_N_NOW.getText().accept(player, game);
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
