package Commands;
import ApplicationComponents.Processor;
import ApplicationComponents.Register;

public class Add extends Command{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public Add(String operand, Processor proc) throws InvalidCommandArgumentException{
		op1 = operand;
		p = proc;
		if  (!(checkCanBeArgument(op1)))
			throw new InvalidCommandArgumentException(op1);
	}

	public void execute(){
		int op1AsNumber = 0;
		Register akk = p.getRegister("A");
		if (isNumeric(op1))
			op1AsNumber = stringToInt(op1);
		else if (p.getRegisters().get(op1) != null)
			op1AsNumber = p.getRegister(op1).getValue();
		akk.setValue(akk.getValue() + op1AsNumber);
	}
}
