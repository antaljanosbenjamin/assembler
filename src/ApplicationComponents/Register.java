package ApplicationComponents;

import java.io.Serializable;

public class Register implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int value;

	public Register() {
		value = 0;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int v) {
		value = v;
	}

	public void setValue(Register r) {
		value = r.value;
	}

	public boolean getBit(int index) {
		return ((1 << index) & value) > 0;
	}

	public void setBit(int index, boolean b) {
		if (b) { // 1-be kell �ll�tani
			value = ((1 << index) | value);
		} else { // 0-ba kell �ll�tani
			value = ((Integer.MAX_VALUE ^ (1 << index)) & value);
		}
	}
}