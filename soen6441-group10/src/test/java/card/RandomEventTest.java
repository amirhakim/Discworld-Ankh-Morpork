package card;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gameplay.BoardArea;
import gameplay.Die;
import gameplay.Game;
import gameplay.GameStatus;
import gameplay.Player;
import io.TextUserInterface;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
		// Add minions to DollySisters
		final int DOLLY_SISTERS_CODE = 1;
		final int UNREAL_ESTATE_CODE = 2;
		final int DRAGONS_LANDING_CODE = 3;
		final int NAP_HILL_CODE = 12;

		BoardArea dollySisters = gameBoard.get(DOLLY_SISTERS_CODE);
		game.addMinion(DOLLY_SISTERS_CODE, player1);
		game.addMinion(DOLLY_SISTERS_CODE, player2);
		game.addMinion(DOLLY_SISTERS_CODE, player3);
		
		// Hit Dolly Sisters with a flood
		// Note: Make player 1 move his minion to Dragon's Landing, 
		// player 2 to Unreal Estate and player 3 to Nap Hill
		Die.getDie().setCheat(1);
		RandomEventCard.FLOOD.getGameAction().accept(game, player1);
		assertTrue(dollySisters.getMinionCount() == 0);
		assertTrue(gameBoard.get(UNREAL_ESTATE_CODE).getMinionCount() == 1);
		assertTrue(gameBoard.get(DRAGONS_LANDING_CODE).getMinionCount() == 1);
		assertTrue(gameBoard.get(NAP_HILL_CODE).getMinionCount() == 1);
	}

	@Test
	public void testFire() {
		// Add buildings to the whole board
		// to make sure the fire spread is tested at some point ;-)
		player1.increaseMoney(50);
		player2.increaseMoney(50);
		player3.increaseMoney(50);
		Player[] players = { player1, player2, player3 };
		int i = 0; 
		for (BoardArea a : gameBoard.values()) {
			game.addBuilding(players[i % 3], a);
			i++;
		}
		
		// Hit the board with fire
		// Since we let the spread depend on the die for the test
		// only check that at least one building was burnt
		RandomEventCard.FIRE.getGameAction().accept(game, player1);
		assertTrue(game.getBoard().stream().anyMatch(a -> a.getBuildingOwner() == Color.UNDEFINED));
	}
	
	@Test
	public void testFog() {
		RandomEventCard.FOG.getGameAction().accept(game, player1);
		// Nothing to do here, just display the drawn cards on stdout.
	}
	
	@Test
	public void testRiots() {
		// Give players money to affect their points
		player1.increaseMoney(100);
		player2.increaseMoney(99);
		player3.increaseMoney(98);
		
		// Add trouble markers everywhere to ensure riots happen
		for (BoardArea a : gameBoard.values()) {
			a.addTroubleMarker();
		}

		// Start the riots
		RandomEventCard.RIOTS.getGameAction().accept(game, player1);
		assertTrue(game.getStatus() == GameStatus.FINISHED);
		List<Player> winners = game.getWinnersByPoints();
		assertTrue(winners.size() == 1 && winners.contains(player1));
		player2.increaseMoney(1);
		winners = game.getWinnersByPoints();
		assertTrue(winners.size() == 2);
	}
	
	@Test
	public void testExplosion() {
		// Add a building to Dolly Sisters and hit it with an Explosion
		// to make sure the fire spread is tested at some point ;-)
		BoardArea dollySisters = gameBoard.get(1);
		player1.increaseMoney(50);
		game.addBuilding(player1, gameBoard.get(1));
		assertTrue(dollySisters.getBuildingOwner() == player1.getColor());
		Die.getDie().setCheat(1);
		RandomEventCard.EXPLOSION.getGameAction().accept(game, player1);
		assertTrue(dollySisters.getBuildingOwner() == Color.UNDEFINED);
	}

	@Test
	public void testMysteriousMurders() {
		// Add minions to the whole board
		for (Entry<Integer, BoardArea> a : gameBoard.entrySet()) {
			int i = a.getKey();
			game.addMinion(i, player1);
			if (i % 2 == 0) {
				game.addMinion(i, player2);
			}
			if (i % 3 == 0 || i % 4 == 0) {
				game.addMinion(i, player3);
			}
		}
		
		// Run mysterious murders for each player
		// This test depends on the die rolls
		RandomEventCard.MYSTERIOUS_MURDERS.getGameAction().accept(game, player1);
	}
	
	@Test
	public void testDemonsFromTheDungeonDimensions() {
		// Just run the random event on a board with no demons and
		// check that there are 4 demons on the board as well as 4 trouble markers
		RandomEventCard.DEMONS_FROM_THE_DUNGEON_DIMENSIONS.getGameAction().accept(game, player1);
		assertTrue(game.getBoard().stream().map(a -> a.getDemonCount()).reduce(0, (p, e) -> p + e) == 4);
		assertTrue(game.getBoard().stream().allMatch(a -> (a.getDemonCount() > 0 && a.hasTroubleMarker()
				|| a.getDemonCount() == 0 && !a.hasTroubleMarker())));
	}
	
	@Test
	public void testSubsidence() {
		player1.increaseMoney(8);
		player2.increaseMoney(34);
		player3.increaseMoney(19);
		game.addBuilding(player1, gameBoard.get(1));
		game.addBuilding(player2, gameBoard.get(3));
		game.addBuilding(player2, gameBoard.get(4));
		game.addBuilding(player3, gameBoard.get(2));
		RandomEventCard.SUBSIDENCE.getGameAction().accept(game, player1);
		assertTrue(player1.getMoney() == 0);
		assertTrue(player2.getMoney() == 0);
		assertTrue(player3.getMoney() == 1);
		assertTrue(gameBoard.get(2).getBuildingOwner() == Color.UNDEFINED);
	}

}
