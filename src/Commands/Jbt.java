package Commands;
import ApplicationComponents.Processor;

public class Jbt extends Jmp{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Jbt(String operand, Processor proc) throws InvalidCommandArgumentException{
		super(operand,proc);
	}

	public void execute(){
		//System.out.println("in Jnz.execute: NotZero: " +!( p.getRegister("F").getBit(0)));
		if (p.getRegister("F").getBit(1))
			super.execute();
	}
	
}