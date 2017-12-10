package verser.server.template.engine.implementations;

import java.io.StringWriter;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import verser.server.template.engine.interfaces.VerificationScriptTempEngine;

public class VelocityTemplateGenerator implements VerificationScriptTempEngine {

	public String generateScript(String name, Map<String, String> data){

		VelocityEngine ve = new VelocityEngine();
		ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		ve.init();
		/*  next, get the Template  */
		Template t = ve.getTemplate("templates/" +name);
		/*  create a context and add data */
		VelocityContext context = new VelocityContext();

		for (String key : data.keySet()){
			context.put(key, data.get(key));
		}

		StringWriter writer = new StringWriter();
		t.merge( context, writer );
		String addon = "service \"verify\" verify( void <- int) by \"u\";\nservice \"random\" random( void <- int) by \"u\";" +
		"\nservice \"ask_owner\" ask_owner( void <- int) by \"u\";\nservice \"owner_action\" owner_action( void <- int) by \"u\";" +
		"\nservice \"delay\" delay( void <- int) by \"u\";\n";
		
		return addon + writer.toString();  
	}

}
