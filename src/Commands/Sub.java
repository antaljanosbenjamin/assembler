package Commands;
import ApplicationComponents.Processor;

public class Sub extends Command{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public Sub(String operand, Processor proc) throws InvalidCommandArgumentException{
		op1 = operand;
		p = proc;
		if  (!(checkCanBeArgument(op1)))
			throw new InvalidCommandArgumentException(op1);
		int op1AsNumber = 0;
		if (isNumeric(op1))
			op1AsNumber = stringToInt(op1);
		else if (p.getRegisters().get(op1) != null)
			op1AsNumber = p.getRegister(op1).getValue();
		op1 = "" + (op1AsNumber * (-1));
	}

	public void execute(){
		try {
			(new Add(op1, p)).execute();
		} catch (InvalidCommandArgumentException e) {
			
			e.printStackTrace();
		}
	}
}
