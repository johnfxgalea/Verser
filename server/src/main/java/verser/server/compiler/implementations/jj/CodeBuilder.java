package verser.server.compiler.implementations.jj;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import verser.server.helpers.ByteHelper;

public class CodeBuilder {

	private static HashMap<ByteInstructions, byte[]> mnemonicToOpcode;
	private static final int INSTRUCTION_SIZE = 3;
	private static final int DATA_SIZE = 2;
	
	public short instrOffSets;
	private ByteArrayOutputStream bos;
	  
	public CodeBuilder(){

		// initialise mnemonic table 
		if (mnemonicToOpcode == null) {
			initaliseMap();
		}
		this.instrOffSets = 0;
	}


	public void initCodeBuilder() throws FileNotFoundException{
		 bos = new ByteArrayOutputStream();
	}
	
	public byte[] getCode() throws IOException{
		bos.flush();
		return bos.toByteArray();
	}

	public static void initaliseMap() {		
		mnemonicToOpcode = new HashMap<ByteInstructions, byte[]>();
		mnemonicToOpcode.put(ByteInstructions.NOP, bytesFromHex("000000"));
		mnemonicToOpcode.put(ByteInstructions.LDC, bytesFromHex("010000"));
		mnemonicToOpcode.put(ByteInstructions.LD, bytesFromHex("020000"));
		mnemonicToOpcode.put(ByteInstructions.STORE, bytesFromHex("030000"));
		mnemonicToOpcode.put(ByteInstructions.DUP, bytesFromHex("040000"));
		mnemonicToOpcode.put(ByteInstructions.POP, bytesFromHex("050000"));
		mnemonicToOpcode.put(ByteInstructions.HALT, bytesFromHex("060000"));
		mnemonicToOpcode.put(ByteInstructions.ENTER, bytesFromHex("070000"));
		mnemonicToOpcode.put(ByteInstructions.ADD, bytesFromHex("080000"));
		mnemonicToOpcode.put(ByteInstructions.SUB, bytesFromHex("090000"));
		mnemonicToOpcode.put(ByteInstructions.EQ, bytesFromHex("0A0000"));
		mnemonicToOpcode.put(ByteInstructions.NE, bytesFromHex("0B0000"));
		mnemonicToOpcode.put(ByteInstructions.LT, bytesFromHex("0C0000"));
		mnemonicToOpcode.put(ByteInstructions.GT, bytesFromHex("0D0000"));
		mnemonicToOpcode.put(ByteInstructions.LE, bytesFromHex("0E0000"));
		mnemonicToOpcode.put(ByteInstructions.GE, bytesFromHex("0F0000"));
		mnemonicToOpcode.put(ByteInstructions.REQCAP, bytesFromHex("100000"));
		mnemonicToOpcode.put(ByteInstructions.AND, bytesFromHex("110000"));
		mnemonicToOpcode.put(ByteInstructions.OR, bytesFromHex("120000"));
		mnemonicToOpcode.put(ByteInstructions.LDSO, bytesFromHex("130000"));
		mnemonicToOpcode.put(ByteInstructions.LEAVE, bytesFromHex("140000"));
		mnemonicToOpcode.put(ByteInstructions.JMP, bytesFromHex("150000"));
		mnemonicToOpcode.put(ByteInstructions.JZ, bytesFromHex("160000"));
		mnemonicToOpcode.put(ByteInstructions.JNZ, bytesFromHex("170000"));
	}

	public static void printByteArrayAsBits(byte[] raw){
		for (byte b : raw) {
			System.out.print(Integer.toBinaryString(b & 255 | 256).substring(1));
		}
		
		System.out.println();
	}

	public static byte[] bytesFromHex(String hexString) {
		char[] hex = hexString.toCharArray();
		final int length = hex.length / 2;
		final byte[] raw = new byte[length];
		for (int i = 0; i < length; i++) {
			final int high = Character.digit(hex[i * 2], 16);
			final int low = Character.digit(hex[i * 2 + 1], 16);
			int value = (high << 4) | low;
			if (value > 127) {
				value -= 256;
			}
			raw[i] = (byte) value;
		}

		return raw;
	}

	public static byte[] bytesFromInteger(short number, int size) {
		size = 2 * size;
		String hex = Integer.toHexString(number);
		int intermediarySize = hex.length();
		if (intermediarySize < size) {
			String prefix = "";
			for (int i = intermediarySize; i < size; i++) {
				prefix += "0";
			}
			hex = prefix.concat(hex);
			return bytesFromHex(hex);
		} else if (intermediarySize > size) {
			hex = hex.substring(hex.length() - 4, hex.length());
		}
		System.out.println("size: " + intermediarySize + " " + hex);
		return bytesFromHex(hex);
	}

	public void addInstruction(ByteInstructions instruction) {
		byte[] instructionBytes = mnemonicToOpcode.get(instruction);
		try {
			bos.write(instructionBytes);			
			instrOffSets += INSTRUCTION_SIZE;
			//printByteArrayAsBits(instructionBytes);

			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addInstruction(ByteInstructions instruction, short n) {
		byte[] instructionBytes = mnemonicToOpcode.get(instruction);
		byte[] nBytes = bytesFromInteger(n, 2);
		
		for (int i = instructionBytes.length - 2, j = 0; i < instructionBytes.length; i++, j++) {
			instructionBytes[i] = nBytes[j];
		}	
		
		try {
			bos.write(instructionBytes);
			instrOffSets += INSTRUCTION_SIZE;
			//printByteArrayAsBits(instructionBytes);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void writeString(String stringComponent){
		try {
			
			byte[] stringBytes = stringComponent.getBytes("US-ASCII");
			
			bos.write(stringBytes);
			
			instrOffSets += stringBytes.length;
			
			System.out.println(stringComponent);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void backPatch(short adrsToModify, short newAddress) {
		
		System.out.println("loc: "+ adrsToModify + " new add: "+ newAddress);
		
		try {
		byte[] bytes = getCode();
		
		byte[] shortBytes = ByteHelper.shortToBytes(newAddress);
		
		bytes[adrsToModify+1] = shortBytes[1];
		bytes[adrsToModify+2] = shortBytes[0];

		bos = new ByteArrayOutputStream(bytes.length);
		bos.write(bytes, 0, bytes.length);
		
		}catch(Exception e) {
			System.out.println("Error "+ e.getMessage());
		}	
	}
	
	public void closeWriter() {
		try {
			bos.close();
		} catch (IOException e) {
			System.out.println("Error in close!");
		}
	}
	
}
