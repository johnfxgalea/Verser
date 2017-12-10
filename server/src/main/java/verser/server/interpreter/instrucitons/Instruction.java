package verser.server.interpreter.instrucitons;

import java.util.HashMap;
import java.util.Map;

import verser.server.compiler.implementations.jj.ByteInstructions;

public class Instruction {

	private short data;

	private  byte hex;
	
	public static Map<Byte, String> instructionNameMap;
	public static Map<Byte, ByteInstructions> instructionMap;

	
	public static void initialise(){
		
		instructionMap = new HashMap<Byte, ByteInstructions>();
		
		instructionMap.put( Byte.parseByte("00", 16), ByteInstructions.NOP);
		instructionMap.put(Byte.parseByte("01", 16), ByteInstructions.LDC );
		instructionMap.put( Byte.parseByte("02", 16), ByteInstructions.LD);
		instructionMap.put( Byte.parseByte("03", 16), ByteInstructions.STORE);
		instructionMap.put( Byte.parseByte("04", 16), ByteInstructions.DUP);
		instructionMap.put(Byte.parseByte("05", 16), ByteInstructions.POP);
		instructionMap.put(Byte.parseByte("06", 16), ByteInstructions.HALT);
		instructionMap.put(Byte.parseByte("07", 16), ByteInstructions.ENTER);
		instructionMap.put(Byte.parseByte("08", 16), ByteInstructions.ADD);
		instructionMap.put(Byte.parseByte("09", 16), ByteInstructions.SUB);
		instructionMap.put( Byte.parseByte("0A", 16), ByteInstructions.EQ);
		instructionMap.put( Byte.parseByte("0B", 16), ByteInstructions.NE);
		instructionMap.put(Byte.parseByte("0C", 16), ByteInstructions.LT);
		instructionMap.put(Byte.parseByte("0D", 16), ByteInstructions.GT);
		instructionMap.put(Byte.parseByte("0E", 16), ByteInstructions.LE );
		instructionMap.put(Byte.parseByte("0F", 16), ByteInstructions.GE );
		instructionMap.put(Byte.parseByte("10", 16), ByteInstructions.REQCAP );
		instructionMap.put(Byte.parseByte("11", 16), ByteInstructions.AND );
		instructionMap.put( Byte.parseByte("12", 16), ByteInstructions.OR);
		instructionMap.put(Byte.parseByte("13", 16), ByteInstructions.LDSO);
		instructionMap.put(Byte.parseByte("14", 16), ByteInstructions.LEAVE);
		instructionMap.put(Byte.parseByte("15", 16), ByteInstructions.JMP);
		instructionMap.put(Byte.parseByte("16", 16), ByteInstructions.JZ);
		instructionMap.put(Byte.parseByte("17", 16), ByteInstructions.JNZ);
		
		instructionNameMap = new HashMap<Byte, String>();
		instructionNameMap.put( Byte.parseByte("00", 16),"NOP");
		instructionNameMap.put(Byte.parseByte("01", 16), "LDC" );
		instructionNameMap.put( Byte.parseByte("02", 16), "LD");
		instructionNameMap.put( Byte.parseByte("03", 16), "STORE");
		instructionNameMap.put( Byte.parseByte("04", 16), "DUP");
		instructionNameMap.put(Byte.parseByte("05", 16), "POP");
		instructionNameMap.put(Byte.parseByte("06", 16), "HALT");
		instructionNameMap.put(Byte.parseByte("07", 16), "ENTER");
		instructionNameMap.put(Byte.parseByte("08", 16), "ADD");
		instructionNameMap.put(Byte.parseByte("09", 16), "SUB");
		instructionNameMap.put( Byte.parseByte("0A", 16), "EQ");
		instructionNameMap.put( Byte.parseByte("0B", 16), "NE");
		instructionNameMap.put(Byte.parseByte("0C", 16), "LT");
		instructionNameMap.put(Byte.parseByte("0D", 16), "GT");
		instructionNameMap.put(Byte.parseByte("0E", 16), "LE" );
		instructionNameMap.put(Byte.parseByte("0F", 16), "GE" );
		instructionNameMap.put(Byte.parseByte("10", 16), "REQCAP" );
		instructionNameMap.put(Byte.parseByte("11", 16), "AND" );
		instructionNameMap.put( Byte.parseByte("12", 16), "OR");
		instructionNameMap.put(Byte.parseByte("13", 16), "LDSO");
		instructionNameMap.put(Byte.parseByte("14", 16), "LEAVE");
		instructionNameMap.put(Byte.parseByte("15", 16), "JMP");
		instructionNameMap.put(Byte.parseByte("16", 16), "JZ");
		instructionNameMap.put(Byte.parseByte("17", 16), "JNZ");
		
		
		
	}
	
	public Instruction(byte hex, short data) {
		this.data = data;
		this.hex = hex;
	}

	public short getData() {
		return data;
	}

	public byte getHex() {
		return hex;
	}
	
	public ByteInstructions getInstrType(){
		return instructionMap.get(hex);
	}
	
	
	public String toString(){
		return instructionNameMap.get(hex) + " " + data;
	}
	
}
