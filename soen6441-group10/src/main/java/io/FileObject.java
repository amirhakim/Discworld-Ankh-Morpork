package io;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * A class to encapsulate an object
 * and the filename it is mapped to for saving/loading.
 * @author (nothing)
 *
 * @param <T>
 */
public class FileObject<T> {
	
	/**
	 * TODO Make this externally configurable
	 */
	private static final String FILE_ROOT = "src/resources";
	
	private final T pojo;
	private final Path filePath;

	public FileObject(T obj_, String _fileName) {
		pojo = obj_;
		filePath = Paths.get(FILE_ROOT).resolve(_fileName);
	}
	
	public static String getFileRoot() {
		return FILE_ROOT;
	}
	
	public T getPOJO() {
		return pojo;
	}
	
	public Path getFilePath() {
		return filePath;
	}

}
