package Commands;

import ApplicationComponents.Processor;

public abstract class CommandCreater {
	public static Command create(String cmdStr, Processor proc)
			throws InvalidCommandArgumentException, InvalidCommandNameException, InvalidArgumentNumberException {
		String[] parts = cmdStr.split(" ");
		Command cmd = null;
		try {
			switch (parts[0]) {
			case "ADD":
				if (parts.length > 2)
					throw new InvalidArgumentNumberException(parts[2]);
				cmd = new Add(parts[1], proc);
				return cmd;
			case "MUL":
				if (parts.length > 2)
					throw new InvalidArgumentNumberException(parts[2]);
				cmd = new Mul(parts[1], proc);
				return cmd;
			case "DIV":
				if (parts.length > 2)
					throw new InvalidArgumentNumberException(parts[2]);
				cmd = new Div(parts[1], proc);
				return cmd;
			case "MOD":
				if (parts.length > 2)
					throw new InvalidArgumentNumberException(parts[2]);
				cmd = new Mod(parts[1], proc);
				return cmd;
			case "SUB":
				if (parts.length > 3)
					throw new InvalidArgumentNumberException(parts[2]);
				cmd = new Sub(parts[1], proc);
				return cmd;
			case "INC":
				if (parts.length > 1)
					throw new InvalidArgumentNumberException(parts[1]);
				cmd = new Inc(proc);
				return cmd;
			case "DEC":
				if (parts.length > 1)
					throw new InvalidArgumentNumberException(parts[1]);
				cmd = new Dec(proc);
				return cmd;
			case "LD":
				if (parts.length > 3)
					throw new InvalidArgumentNumberException(parts[3]);
				cmd = new Ld(parts[1], parts[2], proc);
				return cmd;
			case "OR":
				if (parts.length > 2)
					throw new InvalidArgumentNumberException(parts[2]);
				cmd = new Or(parts[1], proc);
				return cmd;
			case "AND":
				if (parts.length > 2)
					throw new InvalidArgumentNumberException(parts[2]);
				cmd = new And(parts[1], proc);
				return cmd;
			case "XOR":
				if (parts.length > 2)
					throw new InvalidArgumentNumberException(parts[2]);
				cmd = new Xor(parts[1], proc);
				return cmd;
			case "NOT":
				if (parts.length > 1)
					throw new InvalidArgumentNumberException(parts[1]);
				cmd = new Not(proc);
				return cmd;
			case "PUSH":
				if (parts.length > 2)
					throw new InvalidArgumentNumberException(parts[2]);
				cmd = new Push(parts[1], proc);
				return cmd;
			case "POP":
				if (parts.length > 2)
					throw new InvalidArgumentNumberException(parts[2]);
				cmd = new Pop(parts[1], proc);
				return cmd;
			case "CMP":
				if (parts.length > 2)
					throw new InvalidArgumentNumberException(parts[2]);
				cmd = new Cmp(parts[1], proc);
				return cmd;
			case "JMP":
				if (parts.length > 2)
					throw new InvalidArgumentNumberException(parts[2]);
				cmd = new Jmp(parts[1], proc);
				return cmd;
			case "JNZ":
				if (parts.length > 2)
					throw new InvalidArgumentNumberException(parts[2]);
				cmd = new Jnz(parts[1], proc);
				return cmd;
			case "JBT":
				if (parts.length > 2)
					throw new InvalidArgumentNumberException(parts[2]);
				cmd = new Jbt(parts[1], proc);
				return cmd;
			case "RND":
				if (parts.length > 2)
					throw new InvalidArgumentNumberException(parts[2]);
				cmd = new Rnd(parts[1], proc);
				return cmd;
			case "EQ":
				if (parts.length > 3)
					throw new InvalidArgumentNumberException(parts[3]);
				cmd = new Eq(parts[1], parts[2], proc);
				return cmd;
			default:
				throw new InvalidCommandNameException(parts[0]);
			}
		} catch (ArrayIndexOutOfBoundsException aioobe) {
			throw new InvalidArgumentNumberException(aioobe.getMessage());
		}
	}
}