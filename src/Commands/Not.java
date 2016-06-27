package Commands;

import ApplicationComponents.Processor;
import ApplicationComponents.Register;

public class Not extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Not(Processor proc) {
		p = proc;
	}

	@Override
	public void execute() {
		Register akk = p.getRegister("A");
		akk.setValue(~(akk.getValue()));

	}
}