package Commands;

import ApplicationComponents.Processor;

public class Jnz extends Jmp {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Jnz(String operand, Processor proc) throws InvalidCommandArgumentException {
		super(operand, proc);
	}

	public void execute() {
		if (!(p.getRegister("F").getBit(0)))
			super.execute();
	}

}