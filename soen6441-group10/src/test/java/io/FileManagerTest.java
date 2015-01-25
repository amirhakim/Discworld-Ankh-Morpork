package io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import bootstrap.Game;

public class FileManagerTest {

	private Game testGame;
	private JSONFileManager<Game> gameFileManager;
	
	@Before
	public void setUp() {
		gameFileManager = new JSONFileManager<>(Game.class);
	}
	
	
	@Test
	public void testJsonOpen() {
		Optional<FileObject<Game>> f = gameFileManager.open("test_game.json");
		if (f.isPresent()) {
			Game game = f.get().getPOJO();
			// TODO Add more conditions later
			assertEquals(testGame, game);
		} else {
			fail("The test game JSON file was not found.");
		}
	}
	
	@After
	public void tearDown() {
		
	}

}
