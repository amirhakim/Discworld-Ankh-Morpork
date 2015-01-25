package io;

import java.util.Optional;

public interface GameFileManager {

	public Optional<GameFileObject> open(String path);
	
	public boolean save(GameFileObject game);
	
	public boolean saveAs(GameFileObject game, String path, String fileName);
	
}
