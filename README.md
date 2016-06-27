A simple assembler with JAVA graphical interface and my own assembler language. The repo also contains a Hungarian documentation, with the list of the commands. An English one will be added. Structure and language based on: http://sgate.emt.bme.hu/patai/publications/z80guide/ .

Registers: A, B, C, D, F, H, L

Table of existing commands:
Cmd - the command
Functionality - the functionality of the command 
ParamNum - possible operand count
First  - first operand type
Second - second operand type

Operand types: 
  * Constant
    - Decimal: simple numbers, eg.: 26
    - Binary: simple binary numbers with 'b' at the end, eg.: 11010b
    - Hexadecimal: simple hexadecimal numbers with 'h' at the end, eg.: 1Ah
  * Register
    - name of the register, eg.: B
  * Memory
    - Reference to a memory block by constant, the constant is inside brackets. 
        The index of the memory block is the constant, eg.: (13)
    - Reference to a memory block by value of a register, the name of the register is inside brackets. 
        The index of the memory block is the value of the register, eg.: (C)

           
| Cmd  | Functionality                      | ParamNum | First | Second |
|------|------------------------------------|----------|-------|--------|
| ADD  | Additional to A                    | 1        | cr    | -      |
| SUB  | Substraction to A                  | 1        | cr    | -      |
| INC  | Increment A                        | 0        | -     | -      |
| DEC  | Decrement A                        | 0        | -     | -      |
| OR   | Bitwise or with A                  | 1        | cr    | -      |
| XOR  | Bitwise xor with A                 | 1        | cr    | -      |
| NOT  | Bitwise nor with A                 | 0        | -     | -      |
| PUSH | Push value to stack                | 1        | cr    | -      |
| POP  | Pop value from stack               | 1        | cr    | -      |
| CMP  | Compare, see below                 | 1        | cr    | -      |
| JMP  | Jump always                        | 1        | c     | -      |
| JNZ  | Jump, if last bit of F is not zero | 1        | c     | -      |
| LD   | Load value from second to first    | 2        | rm    | crm    |
| EQ   | Define constants                   | 2        | s     | c      |

CMP
  It sets the first bit of F to 1, if the operand is equal to accumulate current value, otherwise nothing happens. 

You can see three examples, that can help you to understand the language. You can download a runnable version (JAR file) from here: https://goo.gl/Bxvr7P .

If you have questions, contact me antal[dooot]janos[dooot]benjamin[aaat]gmail[dooot]com.
