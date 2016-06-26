package Commands;
	public class InvalidCommandNameException extends Exception {
		  /**
		 * 
		 */
		private static final long serialVersionUID = -94356290575363142L;
		public InvalidCommandNameException() { super(); }
		  public InvalidCommandNameException(String message) { super(message); }
		  public InvalidCommandNameException(String message, Throwable cause) { super(message, cause); }
		  public InvalidCommandNameException(Throwable cause) { super(cause); }
	}