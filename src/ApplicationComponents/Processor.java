package ApplicationComponents;
import java.io.Serializable;
import java.util.ArrayList; 
import java.util.Iterator;
import java.util.Map;

import Commands.*;

import java.util.HashMap;


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

public class Processor extends Thread implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Memory mem;
	private int commandCntr;
	private ArrayList<Command> cmds = new ArrayList<Command>();
	private HashMap<String,Register> regs = new HashMap<String,Register>();
	private HashMap<String,Integer> constants = new HashMap<String,Integer>();
	private int stackCntr;
	
	public Processor(){
		mem = new Memory(2048);
		commandCntr = 0;
		stackCntr = 2048;
		for(Character c = 'A'; c < 'G'; c++)
			regs.put(c.toString(), new Register());
		regs.put("H", new Register());
		regs.put("L", new Register());
	}
	
	public boolean isConstant(String c){
	//	System.out.println("key: " + c);
		return constants.containsKey(c);
	}
	
	public int getConstantValue(String c){
		return constants.get(c);
	}
	
	public void addConstant(String c, int v){
		constants.put(c,v);
	}
	
	public void  addCommand(String cmdStr) throws InvalidCommandNameException, InvalidCommandException, 
												  InvalidCommandArgumentException, InvalidArgumentNumberException{
		cmds.add(CommandCreater.create(cmdStr,this));
	}
	
	public void setMemory(ArrayList<Integer> m){
		if (m.size() == 2048)
			mem.setAll(m);
	}	
	
	public void executeAll(){
		mem.reset();
		registersClear();
		stackCntr = 2048;
		for(commandCntr = 0; commandCntr < cmds.size(); commandCntr++)
			cmds.get(commandCntr).execute();
	}
	
	public int getCommandCntr(){
		return commandCntr;
	}
	
	public void setCommandCntr(int ncc){
		commandCntr = ncc;
	}
	
	public void commandsClear(){
		cmds.clear();
	}
	
	public void registersClear(){
	    for(Iterator<Map.Entry<String,Register>> it = regs.entrySet().iterator(); it.hasNext();){
	    	Map.Entry<String, Register> pair = it.next();
	    	regs.get(pair.getKey()).setValue(0);
	    }
	}
	
	public void constansClear(){
		constants.clear();
	}
	
	public Memory getMemory(){
		return mem;
	}
	
	public HashMap<String,Register> getRegisters(){
		return this.regs;
	}
	
	public Register getRegister(String name){
		return regs.get(name);
	}
	
	public int getStackCntr(){
		return stackCntr;
	}
	
	public void decStackCntr(){
		stackCntr--;
	}
	
	public void incStackCntr(){
		stackCntr++;
	}
}