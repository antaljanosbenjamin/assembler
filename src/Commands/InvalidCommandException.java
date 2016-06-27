package Commands;

public class InvalidCommandException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8681423195253856836L;

	public InvalidCommandException() {
		super();
	}

	public InvalidCommandException(String message) {
		super(message);
	}

	public InvalidCommandException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidCommandException(Throwable cause) {
		super(cause);
	}
}