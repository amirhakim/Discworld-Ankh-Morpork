package card;

import gameplay.Game;
import gameplay.Player;

import java.util.List;
import java.util.function.BiConsumer;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import card.player.GreenPlayerCard;
import card.player.Symbol;

public class CardTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		// Code executed before the first test method
	}

	@Before
	public void setUp() throws Exception {}

	/*
	 * Test Card Enum Function Create player and Game Get symbols from player
	 * card MR BOGGIS Loop through symbols and execute the functions they hold
	 * Get the text MR BOGGIS has Execute text for MR BOGGIS
	 */
	@Test
	public void testCard() {
		Player p = new Player();
		Game g = new Game();
		p.setName("Rossco");

		List<Symbol> symbols = GreenPlayerCard.MR_BOGGIS.getSymbols();
		for (Symbol s : symbols) {
			s.getGameAction().accept(p, g);
		}

		BiConsumer<Player, Game> text = GreenPlayerCard.MR_BOGGIS.getText();
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
