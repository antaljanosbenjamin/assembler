package Commands;

import ApplicationComponents.Processor;

public class Inc extends Add {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Inc(Processor proc) throws InvalidCommandArgumentException {
		super("1", proc);
	}

	public void execute() {
		super.execute();
	}
}