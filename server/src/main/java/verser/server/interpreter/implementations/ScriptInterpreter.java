package verser.server.interpreter.implementations;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.Scanner;

import verser.server.compiler.implementations.jj.ByteInstructions;
import verser.server.interpreter.implementations.stacks.ByteArrayStack;
import verser.server.interpreter.implementations.stacks.interfaces.Stack;
import verser.server.interpreter.interfaces.Instruction;
import verser.server.interpreter.interfaces.InterpreterHelper;
import verser.server.interpreter.interfaces.ScriptExecutor;

public class ScriptInterpreter implements ScriptExecutor {

	private boolean verified;
	private short instructionPointer;
	private Stack stack;
	private final static int stackSize = 9000;
	
	public ScriptInterpreter() {
		init();
		stack = new ByteArrayStack(stackSize);
		stack.init();
	}

	private void init() {
		
		instructionPointer = 0;
		verified = true;
	}

	public boolean executeCode(byte[] code) {
		init();

		while (true) {

			Instruction instr = InterpreterHelper.getInstruction(code[instructionPointer], code[instructionPointer + 2],
					code[instructionPointer + 1]);

			if (instr.getInstrType() == ByteInstructions.HALT || !verified) {
				break;
			}

			System.out.println(instructionPointer + " " + instr);
			executeInstr(instr, code);
			instructionPointer += 3;
		}

		return verified;
	}

	private void executeInstr(Instruction instr, byte[] code) {

		if (instr.getInstrType() == ByteInstructions.NOP) {
			performNop();
		} else if (instr.getInstrType() == ByteInstructions.LDC) {
			performLdc(instr.getData());
		} else if (instr.getInstrType() == ByteInstructions.LDSO) {
			performLdso(instr.getData());
		} else if (instr.getInstrType() == ByteInstructions.LD) {
			performLd(instr.getData());
		} else if (instr.getInstrType() == ByteInstructions.STORE) {
			performStore(instr.getData());
		} else if (instr.getInstrType() == ByteInstructions.DUP) {
			performDup();
		} else if (instr.getInstrType() == ByteInstructions.POP) {
			performPop();
		} else if (instr.getInstrType() == ByteInstructions.HALT) {
			performHalt();
		} else if (instr.getInstrType() == ByteInstructions.ADD) {
			performAdd();
		} else if (instr.getInstrType() == ByteInstructions.SUB) {
			performSub();
		} else if (instr.getInstrType() == ByteInstructions.EQ) {
			performEq();
		} else if (instr.getInstrType() == ByteInstructions.NE) {
			performNe();
		} else if (instr.getInstrType() == ByteInstructions.LT) {
			performLt();
		} else if (instr.getInstrType() == ByteInstructions.GT) {
			performGt();
		} else if (instr.getInstrType() == ByteInstructions.LE) {
			performLe();
		} else if (instr.getInstrType() == ByteInstructions.GE) {
			performGe();
		} else if (instr.getInstrType() == ByteInstructions.REQCAP) {
			performReqCap(code, instr.getData());
		} else if (instr.getInstrType() == ByteInstructions.ENTER) {
			performEnter(instr.getData());
		} else if (instr.getInstrType() == ByteInstructions.LEAVE) {
			performLeave();
		} else if (instr.getInstrType() == ByteInstructions.AND) {
			performAnd();
		} else if (instr.getInstrType() == ByteInstructions.OR) {
			performOr();
		} else if (instr.getInstrType() == ByteInstructions.JMP) {
			performJmp(instr.getData());

		} else if (instr.getInstrType() == ByteInstructions.JZ) {
			performJz(instr.getData());

		} else if (instr.getInstrType() == ByteInstructions.JNZ) {
			performJnz(instr.getData());

		}
	}

	public void performNop() {
		// do Nothing
	}

	public void performLdc(final short constant) {
		stack.push(constant);
	}

	public void performLdso(final short stringOffset) {
		stack.push(stringOffset);
	}

	public void performLd(short variableOffset) {

		Short storedShort = stack.fetchVaraibleData(variableOffset);
		stack.push(storedShort);
	}

	public void performStore(short variableOffset) {
		Short pushedShort = stack.pop();
		stack.changeVaraibleData(variableOffset, pushedShort);
	}

	public void performDup() {

		Short peekedShort = stack.peek();
		stack.push(peekedShort);
	}

	public void performPop() {
		stack.pop();
	}

	public void performHalt() {
		// do nothing
	}

	public void performAdd() {

		short secondIntegral = stack.pop();
		short firstIntegral = stack.pop();

		short addition = (short) (firstIntegral + secondIntegral);

		stack.push(addition);
	}

	public void performSub() {
		short secondIntegral = stack.pop();
		short firstIntegral = stack.pop();

		short subtraction = (short) (firstIntegral - secondIntegral);

		stack.push(subtraction);
	}

	public void performEq() {

		short answer = 0;

		short secondIntegral = stack.pop();
		short firstIntegral = stack.pop();

		if (firstIntegral == secondIntegral)
			answer = 1;

		stack.push(answer);
	}

	public void performNe() {
		short answer = 0;

		short secondIntegral = stack.pop();
		short firstIntegral = stack.pop();

		if (firstIntegral != secondIntegral)
			answer = 1;

		stack.push(answer);

	}

	public void performLt() {
		short answer = 0;

		short secondIntegral = stack.pop();
		short firstIntegral = stack.pop();

		if (firstIntegral < secondIntegral)
			answer = 1;

		stack.push(answer);

	}

	public void performGt() {
		short answer = 0;

		short secondIntegral = stack.pop();
		short firstIntegral = stack.pop();

		if (firstIntegral > secondIntegral)
			answer = 1;

		stack.push(answer);

	}

	public void performLe() {
		short answer = 0;

		short secondIntegral = stack.pop();
		short firstIntegral = stack.pop();

		if (firstIntegral <= secondIntegral)
			answer = 1;

		stack.push(answer);
	}

	public void performGe() {
		short answer = 0;

		short secondIntegral = stack.pop();
		short firstIntegral = stack.pop();

		if (firstIntegral >= secondIntegral)
			answer = 1;

		stack.push(answer);
	}

	public void performReqCap(byte[] code, short capabilityOffset) {

		CapabilityInformation capabilityInformation = getCapabilityString(capabilityOffset, code);

		handleProcess(capabilityInformation, code);

	}

	public void handleProcess(CapabilityInformation info, byte[] code) {

		if (info.getCommand().equals("random")) {

			short secondIntegral = stack.pop();
			short firstIntegral = stack.pop();

			Random random = new Random();
			int randomNum = random.nextInt((secondIntegral - firstIntegral) + 1) + firstIntegral;

			stack.push((short) randomNum);

		} else if (info.getCommand().equals("verify")) {
			short integral = stack.pop();

			boolean answer = integral > 0;

			verified = verified && answer;

		} else if (info.getCommand().equals("delay")) {
			short delay = stack.pop();

			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		} else if (info.getCommand().equals("ask_owner")) {

			String prompt = fetchString(code);

			System.out.println(prompt);
			Scanner scanner = new Scanner(System.in);
			int reply = scanner.nextInt();

			if (reply != 0) {
				stack.push((short) 1);
			} else {
				stack.push((short) 0);
			}

		} else if (info.getCommand().equals("owner_action")) {

			String prompt = fetchString(code);

			System.out.println(prompt);
			Scanner scanner = new Scanner(System.in);
			scanner.nextLine();

		} else {
			short input = 0;
			for (int i = 0; i < info.getInputs().length(); i++) {

				String request = info.getCommand() + ":";
				if (info.getInputs().charAt(i) == 'i') {
					 input = stack.pop();
					request += String.valueOf(input);
				} else {
					request += fetchString(code) + ",";
				}
			}
			try {
				String response = sendGet(info.getIp(), String.valueOf(input));
				
				
				String[] responses = response.split(",");
				for (int i = 0; i < info.getOutputs().length(); i++) {
					stack.push(Short.parseShort((responses[i])));
				}
			} catch (Exception e) {
				
			}
			

		}
	}
	
	private String sendGet(String ip, String command) throws Exception {

		String url = "http://"+ ip +"/$"+command;
		System.out.println(url);
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		return response.toString();

	}

	private String fetchString(byte[] code) {
		short stringoffset = stack.pop();

		String string = "";

		byte[] bytearray = new byte[1];
		bytearray[0] = code[stringoffset];
		String str = new String(bytearray, StandardCharsets.UTF_8);
		char c = str.charAt(0);

		while (c != '\"') {



			string += c;
			stringoffset++;
			
			bytearray = new byte[1];
			bytearray[0] = code[stringoffset];
			str = new String(bytearray, StandardCharsets.UTF_8);
			c = str.charAt(0);
		}

		return string;
	}

	private CapabilityInformation getCapabilityString(short offset, byte[] code) {

		short seperatorCounter = 0;

		short codeIndex = offset;

		String command = "";
		String ip = "";
		String inputs = "";
		String outputs = "";

		while (seperatorCounter < 4) {

			byte[] bytearray = new byte[1];
			bytearray[0] = code[codeIndex];
			String str = new String(bytearray, StandardCharsets.UTF_8);
			char mychar = str.charAt(0);

			if (mychar == '\"') {
				seperatorCounter++;

			} else if (seperatorCounter == 0) {
				command += mychar;

			} else if (seperatorCounter == 1) {
				ip += mychar;

			} else if (seperatorCounter == 2) {
				inputs += mychar;

			} else if (seperatorCounter == 3) {
				outputs += mychar;
			}

			codeIndex++;
		}

		return new CapabilityInformation(command, ip, inputs, outputs);
	}

	public void performEnter(short numberOfVariables) {

		stack.prepareVariableSpace(numberOfVariables);
	}

	public void performLeave() {
	}

	public void performAnd() {
		short answer = 0;
		short secondIntegral = stack.pop();
		short firstIntegral = stack.pop();

		if (secondIntegral > 0 && firstIntegral > 0)
			answer = 1;

		stack.push(answer);
	}

	public void performOr() {
		short answer = 0;
		short secondIntegral = stack.pop();
		short firstIntegral = stack.pop();

		if (secondIntegral > 0 || firstIntegral > 0)
			answer = 1;

		stack.push(answer);
	}

	public void performJmp(short jmpAddres) {
		instructionPointer = jmpAddres;
		instructionPointer -= 3;

	}

	public void performJz(short jmpAddres) {

		short integral = stack.pop();

		if (integral == 0) {
			instructionPointer = jmpAddres;
			instructionPointer -= 3;
		}
	}

	public void performJnz(short jmpAddres) {

		short integral = stack.pop();

		if (integral != 0) {
			instructionPointer = jmpAddres;
			instructionPointer -= 3;

		}
	}
}
