package card;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gameplay.BoardArea;
import gameplay.Die;
import gameplay.Game;
import gameplay.Player;
import io.TextUserInterface;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import util.Color;
import card.random.RandomEventCard;

public class RandomEventTest {

	private Game game;
	private Player player1;
	private Player player2;
	private Player player3;
	private Map<Integer, BoardArea> gameBoard;

	@Before
	public void setUp() throws Exception {
		game = new Game();
		String[] playerNames = { "Ross", "Smith", "Rocco"};
		try {
			game.setUp(playerNames.length, playerNames);
		} catch (Exception e) {
			fail("Exception caught");
		}
		player1 = game.getPlayerOfColor(Color.RED);
		player2 = game.getPlayerOfColor(Color.YELLOW);
		player3 = game.getPlayerOfColor(Color.GREEN);
		
		gameBoard = game.getGameBoard();
		TextUserInterface UI = TextUserInterface.getUI();
		UI.setGame(game);
	}

	@Test
	public void testDragon() {
		// Add pieces to DollySisters
		final int DOLLY_SISTERS_CODE = 1;
		BoardArea dollySisters = gameBoard.get(DOLLY_SISTERS_CODE);
		game.addMinion(DOLLY_SISTERS_CODE, player1);
		player2.increaseMoney(10);
		game.addBuilding(player2, dollySisters);
		game.placeDemon(DOLLY_SISTERS_CODE);
		game.placeTroll(DOLLY_SISTERS_CODE);
		
		// Let the dragon destroy it
		Die.getDie().setCheat(1);
		RandomEventCard.DRAGON.getGameAction().accept(game, player1);
		assertTrue(dollySisters.getBuildingOwner() == Color.UNDEFINED);
		assertTrue(dollySisters.getMinionCount() == 0);
		assertTrue(dollySisters.getDemonCount() == 0);
		assertTrue(dollySisters.getTrollCount() == 0);
	}
	
	@Test
	public void testFlood() {
	}
}
