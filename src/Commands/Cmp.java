package Commands;
import ApplicationComponents.Processor;
import ApplicationComponents.Register;

public class Cmp extends Command{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public Cmp(String operand, Processor proc) throws InvalidCommandArgumentException{

		op1 = operand;
		p = proc;

		if  (!(checkCanBeArgument(op1)))
			throw new InvalidCommandArgumentException(op1);
	}
	
	@Override
	public void execute() {
		int op1AsNumber = 0;
		if (isNumeric(op1))
			op1AsNumber = stringToInt(op1);
		else if (p.getRegisters().get(op1) != null)
			op1AsNumber = p.getRegister(op1).getValue();
		//System.out.println("in Cmp.execute: " + ((p.getRegister("A").getValue() - op1AsNumber) == 0) + " op1AsNumber: " + op1AsNumber + " A.value: " + p.getRegister("A").getValue());
		Register f = p.getRegister("F");
		f.setBit(0,(p.getRegister("A").getValue() - op1AsNumber) == 0);
		f.setBit(1, p.getRegister("A").getValue() > op1AsNumber);
	}
}
