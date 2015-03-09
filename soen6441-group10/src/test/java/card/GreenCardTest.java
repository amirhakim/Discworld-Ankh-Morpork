package card;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gameplay.Game;
import gameplay.Player;

import java.util.Map;
import java.util.Optional;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import util.Color;
import card.personality.PersonalityCard;
import card.personality.PersonalityDeck;
import card.player.GreenPlayerCard;
import card.player.Symbol;

public class GreenCardTest {

	Game game;
	Player player;

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
