package verser.server.interpreter.implementations;

public class CapabilityInformation {

	private String command;
	private String ip;
	private String inputs;
	private String outputs;
	
	public CapabilityInformation(String command, String ip, String inputs, String outputs) {
		super();
		
		this.command = command;

		this.ip = ip;

		this.inputs = inputs;

		this.outputs = outputs;
		System.out.println(outputs);

	}
	
	public String getCommand() {
		return command;
	}
	public String getIp() {
		return ip;
	}
	public String getInputs() {
		return inputs;
	}
	public String getOutputs() {
		return outputs;
	}
}
