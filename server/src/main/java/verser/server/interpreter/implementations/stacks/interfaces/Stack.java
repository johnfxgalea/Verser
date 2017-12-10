package verser.server.interpreter.implementations.stacks.interfaces;

public interface Stack {

	public void init();
	public void push(short stackData);
	public short pop();
	public Short peek();
	public int size();
	public Short fetchVaraibleData(short variableOffset);
	public void changeVaraibleData(short variableOffset, Short stackData);
	public void prepareVariableSpace(short numberOfVariables);

}
