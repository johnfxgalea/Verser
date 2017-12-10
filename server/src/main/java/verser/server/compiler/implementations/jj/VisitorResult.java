package verser.server.compiler.implementations.jj;

public class VisitorResult {
	
	protected boolean success;
	
	VisitorResult(){
		success = true;
	}
	
	public boolean isSuccessful(){
		return success;
	}
	
	public void fail(){
		success = false;
	}
}

