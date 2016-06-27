package Commands;

import org.junit.Assert;
import org.junit.Test;

import ApplicationComponents.Processor;

public class CommandCreateTest {

	// Add.execute()
	@Test
	public void addTest() throws InvalidCommandArgumentException, InvalidCommandNameException,
			InvalidArgumentNumberException, InvalidCommandException {
		Processor proc = new Processor();
		proc.addCommand("ADD 5");
		proc.executeAll();
		int value = proc.getRegister("A").getValue();
		Assert.assertEquals(value, 5);
	}

	// CommandCreater.create()
	@Test(expected = InvalidCommandArgumentException.class)
	public void jmpTest() throws InvalidCommandArgumentException, InvalidCommandNameException,
			InvalidArgumentNumberException, InvalidCommandException {
		Processor proc = new Processor();
		proc.addCommand("JMP (5)");
		proc.executeAll();
		int value = proc.getRegister("A").getValue();
		Assert.assertEquals(value, 5);
	}

	// And.execute()
	@Test
	public void andTest() throws InvalidCommandArgumentException, InvalidCommandNameException,
			InvalidArgumentNumberException, InvalidCommandException {
		Processor proc = new Processor();
		proc.getRegister("A").setValue(85);
		proc.addCommand("AND 31");
		proc.executeAll();
		int value = proc.getRegister("A").getValue();
		Assert.assertEquals(value, 21);
	}

	// CommandCreater.create()
	@Test(expected = InvalidArgumentNumberException.class)
	public void ldTest() throws InvalidCommandArgumentException, InvalidCommandNameException,
			InvalidArgumentNumberException, InvalidCommandException {
		Processor proc = new Processor();
		proc.addCommand("LD 5");
		proc.executeAll();
		int value = proc.getRegister("A").getValue();
		Assert.assertEquals(value, 5);
	}

	// CommandCreater.create()
	@Test(expected = InvalidCommandNameException.class)
	public void popTest() throws InvalidCommandArgumentException, InvalidCommandNameException,
			InvalidArgumentNumberException, InvalidCommandException {
		Processor proc = new Processor();
		proc.addCommand("MUL 5");
		proc.executeAll();
		int value = proc.getRegister("A").getValue();
		Assert.assertEquals(value, 5);
	}

}
