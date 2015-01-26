package io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

public class FileManagerTest {

	private static final String TEST_FILE_PATH = "src/resources/test_game.json";
	private MockGame testGame;
	private List<MockPlayer> players = new ArrayList<>();
	private JSONFileManager<MockGame> gameFileManager;
	
	@Before
	public void setUp() {
		gameFileManager = new JSONFileManager<>(MockGame.class);
		testGame = new MockGame("Foo");

		List<MockCard> handOne = new ArrayList<>();
		MockCard greenCardOne = new MockCard(Color.GREEN, "One");
		MockCard redCardOne = new MockCard(Color.RED, "One");
		handOne.add(greenCardOne);
		handOne.add(redCardOne);
		
		List<MockCard> handTwo = new ArrayList<>();
		MockCard redCardTwo = new MockCard(Color.RED, "Two");
		handTwo.add(redCardTwo);

		MockPlayer player1 = new MockPlayer("Player 1", handOne);
		players.add(player1);
		MockPlayer player2 = new MockPlayer("Player 2", handTwo);
		players.add(player2);

		testGame.setPlayers(players);
	}
	
	
	@Test
	public void testJsonOpen() {
		Optional<FileObject<MockGame>> f = gameFileManager.open(TEST_FILE_PATH);
		if (f.isPresent()) {
			MockGame game = f.get().getPOJO();
			// TODO Add more conditions later
			assertEquals(testGame.getName(), game.getName());

			List<MockPlayer> players = game.getPlayers();
			assertNotNull(players);
			assertEquals(2, players.size());
			assertEquals("Player 1", players.get(0).getName());
			assertEquals("Player 2", players.get(1).getName());
			
			List<MockCard> handOne = players.get(0).getHand();
			assertNotNull(handOne);
			assertEquals(Color.RED, handOne.get(0).getColor());
		} else {
			fail("The test game JSON file was not found.");
		}
	}
	
	/**
	 * Using this class for testing until Game is finalized.
	 */
	class MockGame {

		private String name;
		private List<MockPlayer> players;
		
		public MockGame() {}
		
		public MockGame(String name_) {
			name = name_;
		}
		
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public List<MockPlayer> getPlayers() {
			return players;
		}

		public void setPlayers(List<MockPlayer> players_) {
			players = players_;
		}
		
	}
	
	/**
	 * Using this class for testing until Player is finalized.
	 */
	class MockPlayer {

		private String name;
		private List<MockCard> hand;

		public MockPlayer(){}
		
		public MockPlayer(String name_, List<MockCard> hand_) {
			name = name_;
			hand = hand_;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public List<MockCard> getHand() {
			return hand;
		}

		public void setHand(List<MockCard> hand) {
			this.hand = hand;
		}
		
	}
	
	/**
	 * Using this class until Card is finalized.
	 */
	class MockCard {

		private Color color;
		private String name;
		
		public MockCard(){}
		
		public MockCard(Color color_, String name_) {
			color = color_;
			name = name_;
		}

		public Color getColor() {
			return color;
		}

		public void setColor(Color color) {
			this.color = color;
		}
		
		public String getName() {
			return name;
		}
		
		public void setName(String name) {
			this.name = name;
		}
	}
	
	enum Color {
		GREEN, RED
	}
	
}
