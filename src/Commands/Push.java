package Commands;
import ApplicationComponents.Processor;

public class Push extends Command{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Push(String operand, Processor proc) throws InvalidCommandArgumentException{
		p = proc;
		op1 = operand;
		if  (!(checkCanBeArgument(op1)))
			throw new InvalidCommandArgumentException(op1);	
	}
	
	@Override
	public void execute() {
		p.decStackCntr();
		//System.out.println("push.execute:    A: " + p.getRegister("A").getValue());
		try {
			(new Ld("(" + p.getStackCntr() + ")",op1,p)).execute();
		} catch (InvalidCommandArgumentException e) {
			// TODO Auto-generated catch block
			System.out.println("HIBAAA");
		}
		// TODO Auto-generated method stub
		
	}
	
}