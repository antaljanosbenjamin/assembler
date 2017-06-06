# Summary

A simple assembler with JAVA graphical interface and my own assembler language. Structure of the processor and syntax of the language based on [Z80](http://sgate.emt.bme.hu/patai/publications/z80guide/ "See more") architecture. The repo also contains a Hungarian documentation, with the list of the commands and three example assembly code.

You can download a runnable version (JAR file) from [here](https://github.com/antaljanosbenjamin/assembler/blob/master/assembler.jar "Try out!").

# About

## Operand types
  * Constant
    - Decimal: simple numbers, eg.: 26
    - Binary: simple binary numbers with 'b' at the end, eg.: 11010b
    - Hexadecimal: simple hexadecimal numbers with 'h' at the end, eg.: 1Ah
  * Register
    - name of the register. There are eight accessable registers: A, B, C, D, F, H, L.
  * Memory
    - Reference to a memory block by constant, the constant is inside brackets. 
        The constant is the index of the memory block, eg.: (13)
    - Reference to a memory block by value of a register, the name of the register is inside brackets. 
        The value of the register is the index of the memory block, eg.: (C)
  * String
    - Any string that contains only letters and numbers, exlude the name of commands or registers.

## Commands
 
| Cmd  | Functionality                      | number of operands | first operand type | second operand type |
|------|------------------------------------|--------------------|--------------------|---------------------|
| ADD  | Additional to A                    | 1                  | c r                | -                   |
| SUB  | Substraction to A                  | 1                  | c r                | -                   |
| INC  | Increment A                        | 0                  | -                  | -                   |
| DEC  | Decrement A                        | 0                  | -                  | -                   |
| OR   | Bitwise or with A                  | 1                  | c r                | -                   |
| XOR  | Bitwise xor with A                 | 1                  | c r                | -                   |
| NOT  | Bitwise nor with A                 | 0                  | -                  | -                   |
| PUSH | Push value to stack                | 1                  | c r                | -                   |
| POP  | Pop value from stack               | 1                  | c r                | -                   |
| CMP  | Compare, see below                 | 1                  | c r                | -                   |
| JMP  | Jump always                        | 1                  | c                  | -                   |
| JNZ  | Jump, if last bit of F is not zero | 1                  | c                  | -                   |
| LD   | Load value from second to first    | 2                  | r m                | c r m               |
| EQ   | Define constants                   | 2                  | s                  | c                   |

CMP sets the first bit of F to 1, if the operand is equal to accumulate current value, otherwise nothing happens. 

The above mentioned three example contains concrete commands.

If you have questions, contact me antal[dooot]janos[dooot]benjamin[aaat]gmail[dooot]com.
