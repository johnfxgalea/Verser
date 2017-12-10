package verser.server.compiler.interfaces;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface VerificationScriptCompiler {
	
	  public byte [] compile(InputStream inputStream) throws VerificationScriptException;  
	  
}
