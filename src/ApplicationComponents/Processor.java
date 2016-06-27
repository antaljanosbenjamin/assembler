package ApplicationComponents;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import Commands.*;

import java.util.HashMap;

// Structure and language based on: http://sgate.emt.bme.hu/patai/publications/z80guide/
// Registers: A, B, C, D, F, H, L

// Table of existing commands:
// Cmd - the command
// Functionality - the functionality of the command 
// ParamNum - possible operand count
// First  - first operand type
// Second - second operand type

// Operand types: 
//   * Constant
//     - Decimal: simple numbers, eg.: 26
//     - Binary: simple binary numbers with 'b' at the end, eg.: 11010b
//     - Hexadecimal: simple hexadecimal numbers with 'h' at the end, eg.: 1Ah
//   * Register
//     - name of the register, eg.: B
//   * Memory
//     - Reference to a memory block by constant, the constant is inside brackets. 
//         The index of the memory block is the constant, eg.: (13)
//     - Reference to a memory block by value of a register, the name of the register is inside brackets. 
//         The index of the memory block is the value of the register, eg.: (C)

//            
// | Cmd  | Functionality                      | ParamNum | First | Second |
// |------|------------------------------------|----------|-------|--------|
// | ADD  | Additional to A                    | 1        | cr    | -      |
// | SUB  | Substraction to A                  | 1        | cr    | -      |
// | INC  | Increment A                        | 0        | -     | -      |
// | DEC  | Decrement A                        | 0        | -     | -      |
// | OR   | Bitwise or with A                  | 1        | cr    | -      |
// | XOR  | Bitwise xor with A                 | 1        | cr    | -      |
// | NOT  | Bitwise nor with A                 | 0        | -     | -      |
// | PUSH | Push value to stack                | 1        | cr    | -      |
// | POP  | Pop value from stack               | 1        | cr    | -      |
// | CMP  | Compare, see below                 | 1        | cr    | -      |
// | JMP  | Jump always                        | 1        | c     | -      |
// | JNZ  | Jump, if last bit of F is not zero | 1        | c     | -      |
// | LD   | Load value from second to first    | 2        | rm    | crm    |
// | EQ   | Define constants                   | 2        | s     | c      |

// CMP
//   It sets the first bit of F to 1, if the operand is equal to accumulate current value, otherwise nothing happens. 

public class Processor extends Thread implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Memory mem;
	private int commandCntr;
	private ArrayList<Command> cmds = new ArrayList<Command>();
	private HashMap<String, Register> regs = new HashMap<String, Register>();
	private HashMap<String, Integer> constants = new HashMap<String, Integer>();
	private int stackCntr;
	private int memorySize = 312;

	public Processor() {
		mem = new Memory(memorySize);
		commandCntr = 0;
		stackCntr = memorySize;
		for (Character c = 'A'; c < 'G'; c++)
			regs.put(c.toString(), new Register());
		regs.put("H", new Register());
		regs.put("L", new Register());
	}

	public boolean isConstant(String c) {
		return constants.containsKey(c);
	}

	public int getConstantValue(String c) {
		return constants.get(c);
	}

	public void addConstant(String c, int v) {
		constants.put(c, v);
	}

	public void addCommand(String cmdStr) throws InvalidCommandNameException, InvalidCommandException,
			InvalidCommandArgumentException, InvalidArgumentNumberException {
		cmds.add(CommandCreater.create(cmdStr, this));
	}

	public void setMemory(ArrayList<Integer> m) {
		if (m.size() == memorySize)
			mem.setAll(m);
	}

	public void executeAll() {
		mem.reset();
		registersClear();
		stackCntr = memorySize;
		for (commandCntr = 0; commandCntr < cmds.size(); commandCntr++)
			cmds.get(commandCntr).execute();
	}

	public int getCommandCntr() {
		return commandCntr;
	}

	public void setCommandCntr(int ncc) {
		commandCntr = ncc;
	}

	public void commandsClear() {
		cmds.clear();
	}

	public void registersClear() {
		for (Iterator<Map.Entry<String, Register>> it = regs.entrySet().iterator(); it.hasNext();) {
			Map.Entry<String, Register> pair = it.next();
			regs.get(pair.getKey()).setValue(0);
		}
	}

	public void constansClear() {
		constants.clear();
	}

	public Memory getMemory() {
		return mem;
	}

	public HashMap<String, Register> getRegisters() {
		return this.regs;
	}

	public Register getRegister(String name) {
		return regs.get(name);
	}

	public int getStackCntr() {
		return stackCntr;
	}

	public void decStackCntr() {
		stackCntr--;
	}

	public void incStackCntr() {
		stackCntr++;
	}
}