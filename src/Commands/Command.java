//ADD,   	// �sszead�s   	- 1 param - reg | const
//SUB		// Szorz�s
//INC,		// Akkumul�tor n�vel�se 1-gyel == ADD 1
//DEC  		// Akkumul�tor cs�kkent�se 1-gyel == SUB 1
//AND,		// �s 		   	- 1 param - reg | const
//OR,		// Vagy			- 1 param - reg | const
//XOR,		// Xor			- 1 param - reg | const
//NOT,		// Nem			- 0 param
//PUSH,		// Regiszter berak�sa a verembe
//POP,		// Regiszter kivev�se a veremb�l
//CMP, 		// a jelz�bitek �ll�t�sa az "akkumul�tor tartalma m�nusz a megadott �rt�k" m�velet eredm�nye szerint
//JMP,		// Felt�tel n�lk�li ugr�s
//JNZ,		// Ugr�s, ha az el�z� aritmetikai/logikai m�velet �rt�ke nem 0
//LD,		// Bet�lt�s (A <- B,  B <- 10, (3000h) <- D, A <- H)

//EQ		// Constans deklar�l�sa
package Commands;

import java.io.Serializable;

import ApplicationComponents.*;

public abstract class Command implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	protected Processor p;
	protected String op1;

	public abstract void execute();

	public int stringToInt(String nStr) {
		char c = nStr.charAt(nStr.length() - 1);
		switch (c) {
		case '0':
		case '1':
		case '2':
		case '3':
		case '4':
		case '5':
		case '6':
		case '7':
		case '8':
		case '9':
			return Integer.parseInt(nStr);
		default: {
			if (p.isConstant(nStr))
				return p.getConstantValue(nStr);
			else if (c == 'H')
				return (int) Long.parseLong(nStr.replaceAll("H", ""), 16);
			else
				return (int) Long.parseLong(nStr.replaceAll("B", ""), 2);
		}
		}
	}

	public boolean isNumeric(String str) {
		try {
			char last = str.charAt(str.length() - 1);
			if (p.isConstant(str))
				return true;
			if (last == 'H')
				Long.parseLong(str.substring(0, str.length() - 1), 16);
			else if (last == 'B')
				Long.parseLong(str.substring(0, str.length() - 1), 2);
			else
				Integer.parseInt(str);
		} catch (NumberFormatException nfe) {
			// System.out.println("str: " + str +"| ssbustring: " +
			// str.substring(0,str.length()-1)+ "| visszat�r�s: " + false);
			return false;
		}
		// System.out.println("isNumeric: str: " + str +"| visszat�r�s: " +
		// true);
		return true;
	}

	public boolean checkCanBeArgument(String operand) {
		if (!isNumeric(operand)) {
			// System.out.println("command.checkcanbeargument: isConstant: " +
			// operand + "->" + p.isConstant(operand) );
			if (p.getRegister(operand) != null)
				return true;
			else if (p.isConstant(operand))
				return true;
			else
				return false;
		} else
			return true;
	}
}
