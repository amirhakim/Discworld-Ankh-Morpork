package card;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gameplay.BoardArea;
import gameplay.Game;
import gameplay.Player;
import io.TextUserInterface;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import util.Color;

public class CityAreaCardTest {
	
	private Game game;
	private Player player1;
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
		
		gameBoard = game.getGameBoard();
		TextUserInterface UI = TextUserInterface.getUI();
		UI.setGame(game);
	}
	
	@Test
	public void testSmallGods() {
		player1.increaseMoney(100);
		game.addMinion(1, player1);
		game.addBuilding(player1, gameBoard.get(2)); // I should've made the arguments consistent on the API, dammit
		game.addBuilding(player1, gameBoard.get(4)); // Small Gods :-)
		
		// Now try to mess with me! You think you're tough?!
		game.removeMinion(1, player1); // This ain't happening man
		game.removeBuilding(2); // No
		assertTrue(gameBoard.get(1).getMinionCount() == 1);
		assertTrue(gameBoard.get(2).getBuildingOwner() == player1.getColor());
	}

}
