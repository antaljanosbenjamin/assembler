package ApplicationComponents;

import org.junit.Assert;
import org.junit.Test;

public class RegisterTest {

	@Test
	public void negativTest() {
		Register reg = new Register();
		reg.setValue(-1);
		Assert.assertEquals(-1,reg.getValue());
	}
	
	@Test
	public void overflowTest() {
		Register reg = new Register();
		reg.setValue(129);
		Assert.assertEquals(-127,reg.getValue());
	}
	
	@Test
	public void setBitTest() {
		Register reg = new Register();
		reg.setValue(0);
		reg.setBit(3, true);
		Assert.assertEquals(true, reg.getBit(3));
	}
}
