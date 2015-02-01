package io;

import java.util.Optional;

public interface FileManager<T> {

	public Optional<FileObject<T>> open(String path);
	
	public boolean save(FileObject<T> game);
	
	public boolean saveAs(FileObject<T> game, String fileName);
	
}
