package Commands;
import ApplicationComponents.Processor;

public class Dec extends Sub{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public Dec(Processor proc) throws InvalidCommandArgumentException{
		super("1", proc);
	}
		
	
	public void execute(){
		super.execute();
	}
}