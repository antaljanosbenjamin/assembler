package Commands;

import ApplicationComponents.Processor;

public class Jbt extends Jmp {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Jbt(String operand, Processor proc) throws InvalidCommandArgumentException {
		super(operand, proc);
	}

	public void execute() {
		if (p.getRegister("F").getBit(1))
			super.execute();
	}

}