package verser.server.compiler.implementations.jj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class VisitorGenerateCode implements VerificationScriptGrammarVisitor {


	private CodeBuilder codeBuilder;
	private Map<String, PatchData> stringPatchDataMap;
	private Map<String, PatchData> capabilityPatchDataMap;
	private Map<String, Short> stringMap;
	private Map<String, Short> capabilityMap;
	private String stringPart;
	private String capabilityPart;
	private short numberOfIdentifiers;
	private HashMap<String, Short> semanticVariableMap;
	private short counter;


	public VisitorGenerateCode(CodeBuilder byteCodeBuilder,  short numberOfIdentifiers) {
		codeBuilder = byteCodeBuilder; 
		System.out.println("its " + numberOfIdentifiers);
		this.stringPatchDataMap = new HashMap<String, PatchData>();
		this.capabilityPatchDataMap = new HashMap<String, PatchData>();
		this.semanticVariableMap = new HashMap<String, Short>();
		this.stringMap = new HashMap<String, Short>();
		this.capabilityMap = new HashMap<String, Short>();
		this.numberOfIdentifiers = numberOfIdentifiers;
		this.stringPart = "";
		this.capabilityPart = "";
		this.counter = 0;
	}

	public VisitorResult visit(SimpleNode node, Object data) {
		return null;
	}


	public VisitorResult visit(VS_Identifier node, Object data) {

		Boolean toLoad = (Boolean) data;

		if (semanticVariableMap.get(node.value.toString()) == null){
			semanticVariableMap.put(node.value.toString(), counter);
			counter++;
		}
		
		short varOffset = semanticVariableMap.get(node.value.toString());

		if (toLoad){			
			codeBuilder.addInstruction(ByteInstructions.LD, varOffset);

		}else{
			codeBuilder.addInstruction(ByteInstructions.STORE, varOffset);
		}

		return null;
	}


	public VisitorResult visit(VS_Integer node, Object data) {
		Integer value = Integer.parseInt((String) node.jjtGetValue());
		codeBuilder.addInstruction(ByteInstructions.LDC, value.shortValue());
		return null;
	}


	public VisitorResult visit(VS_String node, Object data) {

		String stringValue = (node.value.toString()).replaceAll("\"", "");
		stringValue = stringValue.concat("\"");

		if (!stringMap.containsKey(stringValue)){
			stringMap.put(stringValue, (short )stringPart.getBytes().length);
			stringPart = stringPart.concat(stringValue);
		}

		recordPatchData(stringValue, codeBuilder.instrOffSets, stringPatchDataMap);
		codeBuilder.addInstruction(ByteInstructions.LDSO, (short) 0);
		return null;
	}


	public VisitorResult visit(VS_Type node, Object data) {				

		if (node.value.equals("int"))
			capabilityPart = capabilityPart.concat("i");
		else if (node.value.equals("string"))
			capabilityPart = capabilityPart.concat("s");

		return null;
	}


	public VisitorResult visit(VS_Void node, Object data) {
		return null;
	}


	public VisitorResult visit(VS_IntegerLiteral node, Object data) {
		node.childrenAccept(this, new Boolean(true));
		return null;
	}


	public VisitorResult visit(VS_AllLiteral node, Object data) {
		node.childrenAccept(this, new Boolean(true));
		return null;
	}


	public VisitorResult visit(VS_IdentifierList node, Object data) {
		node.childrenAccept(this, new Boolean(false));
		return null;
	}


	public VisitorResult visit(VS_TypeList node, Object data) {
		node.childrenAccept(this, null);
		return null;
	}


	public VisitorResult visit(VS_TypeDeclaration node, Object data) {
		if (node.jjtGetNumChildren() > 1){
			node.jjtGetChild(1).jjtAccept(this, null);
		}

		capabilityPart = capabilityPart.concat("\"");

		node.jjtGetChild(0).jjtAccept(this, null);

		return null;

	}


	public VisitorResult visit(VS_IntegerLiteralList node, Object data) {
		node.childrenAccept(this, new Boolean(true));
		return null;

	}


	public VisitorResult visit(VS_ALLLiteralList node, Object data) {
		node.childrenAccept(this, new Boolean(true));
		return null;
	}


	public VisitorResult visit(VS_CapabilityDeclaration node, Object data) {

		String capabilityIdentifier = ((VS_Identifier) node.jjtGetChild(1)).value.toString();

		if (!capabilityMap.containsKey(capabilityIdentifier)){

			capabilityMap.put(capabilityIdentifier, (short ) (capabilityPart.getBytes().length));

			VS_String commandString = (VS_String) node.jjtGetChild(0);
			String command = commandString.value.toString().replaceAll("\"", "");
			capabilityPart = capabilityPart.concat(command.concat("\""));

			VS_String ipString = (VS_String) node.jjtGetChild(3);
			String ip = ipString.value.toString().replaceAll("\"", "");
			capabilityPart = capabilityPart.concat(ip.concat("\""));

			node.jjtGetChild(2).jjtAccept(this, null);
			capabilityPart = capabilityPart.concat("\"");

		}		

		return null;
	}


	public VisitorResult visit(VS_Additive node, Object data) {
		if (node.value.equals("+")){
			codeBuilder.addInstruction(ByteInstructions.ADD);
		}else if (node.value.equals("-")){
			codeBuilder.addInstruction(ByteInstructions.SUB);
		}

		return null;
	}


	public VisitorResult visit(VS_SimpleExpression node, Object data) {
		node.jjtGetChild(0).jjtAccept(this, new Boolean(true));		

		for (int i = 1; i < node.jjtGetNumChildren(); i+= 2){

			node.jjtGetChild(i+1).jjtAccept(this, new Boolean(true));		
			node.jjtGetChild(i).jjtAccept(this, new Boolean(true));		
		}
		return null;
	}


	public VisitorResult visit(VS_RelationExpression node, Object data) {
		node.jjtGetChild(0).jjtAccept(this, new Boolean(true));		

		for (int i = 1; i < node.jjtGetNumChildren(); i+= 2){

			node.jjtGetChild(i+1).jjtAccept(this, new Boolean(true));		
			node.jjtGetChild(i).jjtAccept(this, new Boolean(true));		
		}
		return null;
	}


	public VisitorResult visit(VS_Relation node, Object data) {
		if (node.value.equals(">="))
			codeBuilder.addInstruction(ByteInstructions.GE);
		else if (node.value.equals("=="))
			codeBuilder.addInstruction(ByteInstructions.EQ);
		else if (node.value.equals("!="))
			codeBuilder.addInstruction(ByteInstructions.NE);
		else if (node.value.equals("<="))
			codeBuilder.addInstruction(ByteInstructions.LE);
		else if (node.value.equals("<"))
			codeBuilder.addInstruction(ByteInstructions.LT);
		else if (node.value.equals(">"))
			codeBuilder.addInstruction(ByteInstructions.GT);

		return null;
	}


	public VisitorResult visit(VS_Expression node, Object data) {

		node.jjtGetChild(0).jjtAccept(this, new Boolean(true));		

		for (int i = 1; i < node.jjtGetNumChildren(); i+= 2){

			node.jjtGetChild(i+1).jjtAccept(this, new Boolean(true));		
			node.jjtGetChild(i).jjtAccept(this, new Boolean(true));		
		}

		return null;
	}


	public VisitorResult visit(VS_ArrayALLInNode node, Object data) {
		node.childrenAccept(this, new Boolean(true));
		return null;
	}


	public VisitorResult visit(VS_ArrayIntegerInNode node, Object data) {
		node.childrenAccept(this, new Boolean(true));
		return null;
	}


	public VisitorResult visit(VS_ArrayOutNode node, Object data) {

		VS_IdentifierList identifierList = (VS_IdentifierList) node.jjtGetChild(0);

		for (int i = identifierList.jjtGetNumChildren() - 1; i >= 0; i--){
			identifierList.jjtGetChild(i).jjtAccept(this, new Boolean(false));
		}

		return null;
	}


	public VisitorResult visit(VS_CapabilityNode node, Object data) {

		for (int i = 1; i < node.jjtGetNumChildren(); i++){
			node.jjtGetChild(i).jjtAccept(this, true);
		}

		String capabilityIdentifier = ((VS_Identifier) node.jjtGetChild(0)).value.toString();
		recordPatchData(capabilityIdentifier, codeBuilder.instrOffSets, capabilityPatchDataMap);
		codeBuilder.addInstruction(ByteInstructions.REQCAP, (short) 0);

		return null;
	}


	public VisitorResult visit(VS_IdentifierFlowStatement node, Object data) {

		node.jjtGetChild(1).jjtAccept(this, new Boolean(true));		
		node.jjtGetChild(0).jjtAccept(this, new Boolean(false));		

		return null;
	}


	public VisitorResult visit(VS_Logic node, Object data) {
		if (node.value.equals("&&")){
			codeBuilder.addInstruction(ByteInstructions.AND);
		}else if (node.value.equals("||")){
			codeBuilder.addInstruction(ByteInstructions.OR);
		}
		return null;
	}



	public VisitorResult visit(VS_IfStatement node, Object data) {

		node.jjtGetChild(0).jjtAccept(this, data);

		if (node.jjtGetNumChildren() == 2){
			//no else
			
			short adrOfJZ = codeBuilder.instrOffSets;
			codeBuilder.addInstruction(ByteInstructions.JZ, (short) 0); 
			node.jjtGetChild(1).jjtAccept(this, data);
			short outofThen = codeBuilder.instrOffSets;
			codeBuilder.backPatch(adrOfJZ, outofThen);
			
		}else if (node.jjtGetNumChildren() == 3){
			// with else
			
			short adrOfJZ = codeBuilder.instrOffSets;
			codeBuilder.addInstruction(ByteInstructions.JZ, (short) 0); 
			node.jjtGetChild(1).jjtAccept(this, data);
			short addrOfJmp = codeBuilder.instrOffSets;
			codeBuilder.addInstruction(ByteInstructions.JMP, (short) 0); 
			short locOfElse = codeBuilder.instrOffSets;
			node.jjtGetChild(2).jjtAccept(this, data);
			short outOfIf = codeBuilder.instrOffSets;
			codeBuilder.backPatch(adrOfJZ, locOfElse);
			codeBuilder.backPatch(addrOfJmp, outOfIf);
		}
		
		return null;
	}

	public VisitorResult visit(VS_WhileStatement node, Object data) {
		short locOfCond = codeBuilder.instrOffSets;
		node.jjtGetChild(0).jjtAccept(this, data);
		short locOfJZ = codeBuilder.instrOffSets;
		codeBuilder.addInstruction(ByteInstructions.JZ, (short) 0); 

		for (int i = 1; i < node.jjtGetNumChildren(); i++){
			node.jjtGetChild(i).jjtAccept(this, data);
		}
		codeBuilder.addInstruction(ByteInstructions.JMP, locOfCond); 
		short outOfWhile = codeBuilder.instrOffSets;
		codeBuilder.backPatch(locOfJZ, outOfWhile); 

		return null;
	}

	public VisitorResult visit(VS_ArrayOutNodeFlowStatement node, Object data) {

		node.jjtGetChild(1).jjtAccept(this, new Boolean(true));		
		node.jjtGetChild(0).jjtAccept(this, new Boolean(false));	

		return null;
	}


	public VisitorResult visit(VS_FlowStatement node, Object data) {
		node.childrenAccept(this, null);
		return null;
	}


	public VisitorResult visit(VS_Statement node, Object data) {
		node.childrenAccept(this, null);
		return null;
	}


	public VisitorResult visit(VS_VerificationBlock node, Object data) {
		node.childrenAccept(this, null);
		return null;
	}


	public VisitorResult visit(VS_StatementBlock node, Object data) {
		node.childrenAccept(this, null);
		return null;
	}
	
	public VisitorResult visit(VS_VerificationScript node, Object data) {

		System.out.println("Enter is " + numberOfIdentifiers);
		codeBuilder.addInstruction(ByteInstructions.ENTER, (short) numberOfIdentifiers);

		node.childrenAccept(this, null);

		codeBuilder.addInstruction(ByteInstructions.LEAVE);
		codeBuilder.addInstruction(ByteInstructions.HALT);

		short endOffset = codeBuilder.instrOffSets;

		codeBuilder.writeString(stringPart);
		codeBuilder.writeString(capabilityPart);

		codeBuilder.closeWriter();

		for (String string : stringMap.keySet()){

			PatchData patchData = stringPatchDataMap.get(string);

			short offset = stringMap.get(string);

			for (Short shortNumber : patchData.offsetList){
				codeBuilder.backPatch(shortNumber, (short) (endOffset + offset));
			}
		}		

		endOffset += stringPart.getBytes().length;

		for (String string : capabilityMap.keySet()){

			PatchData patchData = capabilityPatchDataMap.get(string);

			if (patchData != null){

				short offset = capabilityMap.get(string);

				for (Short shortNumber : patchData.offsetList){
					codeBuilder.backPatch(shortNumber, (short) (endOffset + offset));
				}		
			}
		}	

		return null;
	}

	private void recordPatchData(String string, short offset, Map<String, PatchData> patchMap){

		if (patchMap.containsKey(string)){
			PatchData patchData = patchMap.get(string);
			patchData.offsetList.add(offset);
		}else{
			PatchData patchData = new PatchData(string);
			patchData.offsetList.add(offset);
			patchMap.put(string, patchData);
		}		
	}

	private class PatchData{

		private List<Short> offsetList;
		private String identifier;

		private PatchData(String id){
			identifier = id;
			offsetList = new ArrayList<Short>();			
		}	
	}


}
