package verser.server.interpreter.implementations.stacks;

import verser.server.helpers.ByteHelper;
import verser.server.interpreter.implementations.stacks.interfaces.Stack;

public class ByteArrayStack implements Stack{

	private short stackPointer;
	private short basePointer;
	private byte[] stack;
	private final int stackSize;
	
	public ByteArrayStack(int stackSize){
		this.stackSize = stackSize;
	}
	
	public void init() {
		stackPointer = 0;
		basePointer = 0;
		stack = new byte[stackSize];
		
	}
	
	public void push(short stackData) {
		
		byte[] bytes = ByteHelper.shortToBytes(stackData);
		
		stack[stackPointer] = bytes[1];
		stackPointer++;
		stack[stackPointer] = bytes[0];
		stackPointer++;
	}

	public short pop() {
		
		stackPointer--;
		byte secondByte = stack[stackPointer];
		stackPointer--;
		byte firstByte = stack[stackPointer];

		return ByteHelper.bytesToShort(secondByte, firstByte);
	}

	public Short peek() {
		
		byte secondByte = stack[stackPointer - 1];
		byte firstByte = stack[stackPointer - 2];
		
		return ByteHelper.bytesToShort(firstByte, secondByte);
	}

	public int size() {

		return stackPointer;
	}

	public Short fetchVaraibleData(short variableOffset) {
		short index = getVariableIndex(variableOffset);

		byte secondByte = stack[index];
		byte firstByte = stack[index - 1];
		
		return ByteHelper.bytesToShort(firstByte, secondByte);
	}

	public void changeVaraibleData(short variableOffset, Short stackData) {

		byte[] bytes = ByteHelper.shortToBytes(stackData);
		short index = getVariableIndex(variableOffset);

		stack[index] = bytes[1];
		stack[index - 1] = bytes[0];
	}

	private short getVariableIndex(short variableOffset) {
		return (short) (basePointer - ((variableOffset * 2) + 1));
	}

	public void prepareVariableSpace(short numberOfVariables) {

		for (int i = 0; i < numberOfVariables; i++) {
			push((short) 0);
		}

		basePointer = (short) size();

	}
	
}
