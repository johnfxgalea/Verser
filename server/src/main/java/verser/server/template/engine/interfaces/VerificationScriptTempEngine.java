package verser.server.template.engine.interfaces;

import java.util.Map;

public interface VerificationScriptTempEngine {

	public String generateScript(String path, Map<String, String> data);

	
}
