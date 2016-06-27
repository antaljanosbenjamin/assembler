package Commands;

import ApplicationComponents.Processor;

public class Jmp extends Command {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public Jmp(String operand, Processor proc) throws InvalidCommandArgumentException {
		op1 = operand;
		p = proc;
		if (!(isNumeric(op1)))
			throw new InvalidCommandArgumentException(op1);
	}

	@Override
	public void execute() {
		int value = stringToInt(op1) - 1;
		// System.out.println("int Jmp.execute: op1: |" + op1 +"| value: " +
		// value );
		p.setCommandCntr(p.getCommandCntr() + value);
	}
}

// mekkora a piac?
// mi�rt lesz piac?
// mi�rt tol�dik ki az �letkor?
// f�rumok?
