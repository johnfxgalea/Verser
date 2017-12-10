package verser.server;

import java.util.HashMap;
import java.util.Map;

import verser.server.interpreter.interfaces.Instruction;
import verser.server.verification.Verifier;

public class App {
	public static void main(String[] args) {

		Instruction.initialise();
		
		int testCase = 0;
		Map<String, String> data = new HashMap<String, String>();

		Verifier verifier = Verifier.getInstance();

		if (testCase == 0) {
			data.put("command", "s");
			data.put("ip", "192.168.1.177");

		}

		boolean result = verifier.verify("text2", data);

		System.out.println("Done. The result is " + result);
	}
}
