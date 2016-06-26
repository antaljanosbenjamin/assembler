package ApplicationComponents;
import java.io.Serializable;
import java.util.ArrayList; 
import java.util.Iterator;
import java.util.Map;

import Commands.*;

import java.util.HashMap;


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