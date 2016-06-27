package Commands;

public class InvalidCommandArgumentException extends Exception {
	/**
	* 
	*/
	private static final long serialVersionUID = -94356290575363142L;

	public InvalidCommandArgumentException() {
		super();
	}

	public InvalidCommandArgumentException(String message) {
		super(message);
	}

	public InvalidCommandArgumentException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidCommandArgumentException(Throwable cause) {
		super(cause);
	}
}