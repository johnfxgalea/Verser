package verser.server.compiler.implementations.jj;

public enum ByteInstructions {
	NOP, // no instruction
	LDC, // load constant instruction
	LDSO, // load string offset instruction
	LD, // load variable instruction
	STORE, // store to variable instruction
	DUP, // duplicate stack instruction
	POP, // pop stack instructions
	HALT, // end script instruction
	ADD, // add instruction
	SUB, // subtract instruction
	EQ, // check equality instruction
	NE, // check not equality instruction
	LT, // check less than instruction
	GT, // check greater than instruction
	LE, // check less than instruction
	GE, // check greater than instruction
	REQCAP, // request capability instruction
	ENTER, // enter instruction (defining number of variables)
	LEAVE, // Leave stack frame
	AND, // Logical AND
	OR, // Logical OR
	JMP, //JMP
	JZ, //Jump if Zero
	JNZ // Jump if not zero
}
