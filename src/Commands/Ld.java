package Commands;

import ApplicationComponents.Processor;

public class Ld extends Command {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	protected String op2;
	protected String op1Trunced = "";
	protected String op2Trunced = "";

	public Ld(String operand1, String operand2, Processor proc) throws InvalidCommandArgumentException {
		op1 = operand1;
		op2 = operand2;
		p = proc;
		if (op1.length() >= 3)
			op1Trunced = op1.substring(1, op1.length() - 1);
		if (op2.length() >= 3)
			op2Trunced = op2.substring(1, op2.length() - 1);

		if (!checkCanBeFirstArgument(op1))
			throw new InvalidCommandArgumentException(op1);
		if (!checkCanBeSecondArgument(op2))
			throw new InvalidCommandArgumentException(op2);
	}

	public boolean checkCanBeFirstArgument(String operand) {
		String opTrunced = "Z";
		if (operand.length() >= 3)
			opTrunced = operand.substring(1, operand.length() - 1);
		if (p.getRegister(operand) != null)
			return true;
		else if ((operand.charAt(0) == '(') && (operand.charAt(operand.length() - 1) == ')'))
			return (isNumeric(opTrunced) || (p.getRegister(opTrunced) != null));
		else
			return false;
	}

	public boolean checkCanBeArgument(String operand) {
		return false;
	}

	public boolean checkCanBeSecondArgument(String operand) {
		return (checkCanBeFirstArgument(operand) || isNumeric(operand));
	}

	@Override
	public void execute() {
		int value;
		if (op2.charAt(0) == '(') {
			int index = 0;
			if (p.getRegister(op2Trunced) != null)
				index = p.getRegister(op2Trunced).getValue();
			else
				index = stringToInt(op2Trunced);
			value = p.getMemory().getBlock(index);
			System.out.println("LD execute:    index:" + index);
		} else if (isNumeric(op2))
			value = stringToInt(op2);
		else
			value = p.getRegister(op2).getValue();

		if (op1.charAt(0) == '(') {
			int index = 0;
			if (isNumeric(op1Trunced))
				index = stringToInt(op1Trunced);
			else
				index = p.getRegister(op1Trunced).getValue();
			p.getMemory().setBlock(index, value);
		} else
			p.getRegister(op1).setValue(value);
	}
}
