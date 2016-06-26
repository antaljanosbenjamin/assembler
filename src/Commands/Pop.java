package Commands;
import ApplicationComponents.Processor;

public class Pop extends Command{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Pop(String operand, Processor proc) throws InvalidCommandArgumentException{
		p = proc;
		op1 = operand;
		if (! (p.getRegister(op1) != null) )
			throw new InvalidCommandArgumentException(op1);
	}
	
	@Override
	public void execute() {
		int value = p.getMemory().getBlock(p.getStackCntr());
		try {
			(new Ld(op1,"" + value,p )).execute();
		} catch (InvalidCommandArgumentException e) {
			System.out.println("HIBA");
		}
		p.incStackCntr();
	}
	
}