package io;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import bootstrap.Game;

import com.google.gson.Gson;

public class JSONGameFileManager implements GameFileManager {
	
	private static final Gson gson = new Gson();
	
	@Override
	public Optional<GameFileObject> open(String fileName) {
		try (FileReader jsonFile = new FileReader(fileName)) {
			Game game = gson.fromJson(jsonFile, Game.class);
			return Optional.of(new GameFileObject(game, fileName));
		} catch (IOException e) {
			return Optional.empty();
		}
	}

	@Override
	public boolean save(GameFileObject gameObj) {
		String gameJson = gson.toJson(gameObj.getGame());
		try {
			Files.write(gameObj.getGameFile(), gameJson.getBytes());
		} catch (IOException e) {
			// TODO Log exception
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	@Override
	public boolean saveAs(GameFileObject gameObj, String path, String fileName) {
		String gameJson = gson.toJson(gameObj.getGame());
		try {
			Files.write(Paths.get(GameFileObject.getGameFileRoot()), gameJson.getBytes());
		} catch (IOException e) {
			// TODO Log exception
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
}
