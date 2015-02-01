package bootstrap;

public class InvalidGameStateException extends Exception {
	  public InvalidGameStateException() { super(); }
	  public InvalidGameStateException(String message) { super(message); }
	  public InvalidGameStateException(String message, Throwable cause) { super(message, cause); }
	  public InvalidGameStateException(Throwable cause) { super(cause); }
}
