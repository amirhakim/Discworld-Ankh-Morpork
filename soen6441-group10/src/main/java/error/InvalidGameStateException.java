package error;

public class InvalidGameStateException extends Exception {

	private static final long serialVersionUID = -1992779502880671507L;

	public InvalidGameStateException() {
		super();
	}

	public InvalidGameStateException(String message) {
		super(message);
	}

	public InvalidGameStateException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidGameStateException(Throwable cause) {
		super(cause);
	}
}
