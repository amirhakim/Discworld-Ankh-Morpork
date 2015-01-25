package io;

import java.nio.file.Path;
import java.nio.file.Paths;

import bootstrap.Game;

public class GameFileObject {
	
	/**
	 * TODO Make this externally configurable
	 */
	private static final String GAME_FILE_ROOT = "src/resources";
	
	private final Game game;
	private final Path gameFile;

	protected GameFileObject(Game _game, String _fileName) {
		game = _game;
		gameFile = Paths.get(_fileName).resolve(GAME_FILE_ROOT);
	}
	
	public static String getGameFileRoot() {
		return GAME_FILE_ROOT;
	}
	
	public Game getGame() {
		return game;
	}
	
	public Path getGameFile() {
		return gameFile;
	}

}
