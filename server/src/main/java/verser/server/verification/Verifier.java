package verser.server.verification;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Map;

import verser.server.compiler.implementations.jj.ByteInstructions;
import verser.server.compiler.implementations.jj.VerificationScriptGrammar;
import verser.server.compiler.interfaces.VerificationScriptCompiler;
import verser.server.interpreter.implementations.ScriptInterpreter;
import verser.server.interpreter.interfaces.Instruction;
import verser.server.interpreter.interfaces.InterpreterHelper;
import verser.server.interpreter.interfaces.ScriptExecutor;
import verser.server.template.engine.implementations.VelocityTemplateGenerator;
import verser.server.template.engine.interfaces.VerificationScriptTempEngine;

public class Verifier {

	private static Verifier verifier = null;
	private VerificationScriptTempEngine tempEngine;
	private VerificationScriptCompiler compiler;
	private ScriptExecutor executor;

	protected Verifier(){

		compiler = new VerificationScriptGrammar();
		tempEngine = new VelocityTemplateGenerator();
		executor = new ScriptInterpreter();
	}

	public static Verifier getInstance(){
		if (verifier == null){
			verifier = new Verifier();
		}

		return verifier;
	}

	public boolean verify(String scriptTemplatePath, Map<String, String> data){


		try{

			// Generate script
			String script = tempEngine.generateScript(scriptTemplatePath, data);
			System.out.println(script);

			InputStream stream = new ByteArrayInputStream(script.getBytes(StandardCharsets.UTF_8));

			// Compile Code
			byte [] compiledCode = compiler.compile(stream);



			// Execute Code
			return executor.executeCode(compiledCode);

		}catch(Exception e){
			System.out.println("tHIS IS Error ");
			e.printStackTrace();
		}
		return false;
	}
}
