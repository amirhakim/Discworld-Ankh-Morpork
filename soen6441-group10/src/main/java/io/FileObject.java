package io;

import java.nio.file.Path;
import java.nio.file.Paths;


/**
 * <b> This class encapsulates an object and the filename it is mapped to for saving/loading <b> 
 * 
 * @param <T>
 * @author Team 10 - SOEN6441
 * @version 1.0
 */
public class FileObject<T> {
	
	/**
	 * TODO Make this externally configurable
	 */
	private static final String FILE_ROOT = "src/resources";
	
	private final T pojo;
	private final Path filePath;

	/**
	 * This method makes the path for saving/loading files.
	 * @param obj_ java object
	 * @param _fileName filename
	 */
	public FileObject(T obj_, String _fileName) {
		pojo = obj_;
		filePath = Paths.get(FILE_ROOT).resolve(_fileName);
	}
	
	/**
	 * Get file root (src/resource)
	 * @return file root
	 */
	public static String getFileRoot() {
		return FILE_ROOT;
	}
	
	/**
	 * Gets java object
	 * @return object
	 */
	public T getPOJO() {
		return pojo;
	}
	
	/**
	 * Get file path
	 * @return file path
	 */
	public Path getFilePath() {
		return filePath;
	}

}
