package ApplicationComponents;

import java.io.Serializable;
import java.util.ArrayList;

public class Memory implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Integer> memory;

	public Memory(int size) {
		memory = new ArrayList<Integer>(size);
		for (int i = 0; i < size; i++)
			memory.add(new Integer(0));
	}

	public void reset() {
		for (int i = 0; i < memory.size(); i++)
			memory.set(i, 0);
	}

	public void setBlock(int index, int value) {
		memory.set(index, value);
	}

	public int getBlock(int index) {
		int value = memory.get(index);
		return value;
	}

	public int size() {
		int size = memory.size();
		return size;
	}

	public void setAll(ArrayList<Integer> m) {
		if (m.size() == memory.size())
			memory = m;
	}

	public String toString(int width, int numbers) {
		int w = 0;
		int h = 0;
		StringBuilder sb = new StringBuilder();

		for (h = 0; h < size() / width; h++) {
			sb.append(String.format("%0" + numbers + "X.:", h * width).replace(' ', '0'));
			for (w = 0; w < width; w++) {
				sb.append(String.format(" %08X", memory.get(h * width + w)));
			}
			sb.append("\n");
		}
		sb.deleteCharAt(sb.length() - 1);

		if (w + h * width < memory.size()) {
			sb.append("\n" + String.format("%" + numbers + "d.:", h).replace(' ', '0'));
			for (w = 0; w + h * width < memory.size(); w++) {
				sb.append(String.format(" %02X", memory.get(h * width + w)));
			}
		}
		return sb.toString();
	}
}