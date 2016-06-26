package Commands;

import java.util.Random;

import ApplicationComponents.Processor;

public class Rnd extends Command{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public Rnd(String operand,Processor proc) throws InvalidCommandArgumentException{
		op1 = operand;
		p = proc;
		if  (!checkCanBeArgument(op1))
			throw new InvalidCommandArgumentException(op1);
	}
	
	public void execute(){
		Random rand = new Random();
		if (p.getRegister(op1) != null)
			p.getRegister("A").setValue(rand.nextInt(p.getRegister(op1).getValue()));
		else
			p.getRegister("A").setValue(rand.nextInt(stringToInt(op1)));
	}
}