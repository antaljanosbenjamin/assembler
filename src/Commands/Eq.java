package Commands;

import ApplicationComponents.Processor;

public class Eq extends Command{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	protected String op2;

	public Eq(String operand1, String operand2, Processor proc) throws InvalidCommandArgumentException{
		op1 = operand1;
		op2 = operand2;
		p = proc;
		if (isNumeric(op1) || (p.getRegister(op1) != null))
			throw new InvalidCommandArgumentException(op1);
		if (!isNumeric(op2))
			throw new InvalidCommandArgumentException(op2);
		p.addConstant(op1, stringToInt(op2));
	}

	@Override
	public void execute() {
		//p.addConstant(op1, stringToInt(op2));
	}


}
