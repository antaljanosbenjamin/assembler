//ADD,   	// Összeadás   	- 1 param - reg | const
//SUB		// Szorzás
//INC,		// Akkumulátor növelése 1-gyel == ADD 1
//DEC  		// Akkumulátor csökkentése 1-gyel == SUB 1
//AND,		// És 		   	- 1 param - reg | const
//OR,		// Vagy			- 1 param - reg | const
//XOR,		// Xor			- 1 param - reg | const
//NOT,		// Nem			- 0 param
//PUSH,		// Regiszter berakása a verembe
//POP,		// Regiszter kivevése a verembõl
//CMP, 		// a jelzõbitek állítása az "akkumulátor tartalma mínusz a megadott érték" mûvelet eredménye szerint
//JMP,		// Feltétel nélküli ugrás
//JNZ,		// Ugrás, ha az elõzõ aritmetikai/logikai mûvelet értéke nem 0
//LD,		// Betöltés (A <- B,  B <- 10, (3000h) <- D, A <- H)

//EQ		// Constans deklarálása
package Commands;
import java.io.Serializable;

import ApplicationComponents.*;


public abstract class Command implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	protected Processor p;
	protected String op1;

	public abstract void execute();

	public int stringToInt(String nStr){
		char c = nStr.charAt(nStr.length()-1);
		switch(c){
			case '0' :
			case '1' :
			case '2' :
			case '3' :
			case '4' :
			case '5' :
			case '6' :
			case '7' :
			case '8' :
			case '9' : return Integer.parseInt(nStr);
			default  : {
				if (p.isConstant(nStr))
					return p.getConstantValue(nStr);
				else if(c == 'H')
					return Integer.parseInt(nStr.replaceAll("H", ""), 16);
				else
					return Integer.parseInt(nStr.replaceAll("B", ""), 2);
			}
		}
	}

	public boolean isNumeric(String str){
		try
		  {
			char last = str.charAt(str.length()-1);
			if (p.isConstant(str)) return true;
			if (last == 'H')
				Integer.parseInt(str.substring(0,str.length()-1), 16);
			else if (last == 'B')
				Integer.parseInt(str.substring(0,str.length()-1), 2);
			else
				Integer.parseInt(str);
		  }
		  catch(NumberFormatException nfe)
		  {
			//System.out.println("str: " + str +"| ssbustring: " + str.substring(0,str.length()-1)+ "| visszatérés: " + false);
		    return false;
		  }
		  //System.out.println("isNumeric: str: " + str +"| visszatérés: " + true);
		  return true;
	}

	public boolean checkCanBeArgument(String operand) {
		if (!isNumeric(operand)){
			//System.out.println("command.checkcanbeargument:   isConstant: " + operand + "->" + p.isConstant(operand) );
			if (p.getRegister(operand) != null)
				return true;
			else if (p.isConstant(operand))
				return true;
			else
				return false;
		}
		else
			return true;
	}
}
