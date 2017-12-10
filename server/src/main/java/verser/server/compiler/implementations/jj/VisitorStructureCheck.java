package verser.server.compiler.implementations.jj;

import java.util.HashSet;

public class VisitorStructureCheck implements VerificationScriptGrammarVisitor {

	private HashSet<String> identifiers = new HashSet<String>();
	
	public short getNumberOfIdentifiers(){
		
		for (String s: identifiers){
			System.out.println(s);
		}
		
		return (short) identifiers.size();
	}
	
	public VisitorResult visit(SimpleNode node, Object data) {
		return new StructureCheck();
	}

	public VisitorResult visit(VS_Integer node, Object data) {
		return new StructureCheck();
	}

	public VisitorResult visit(VS_String node, Object data) {
		return new StructureCheck();
	}

	public VisitorResult visit(VS_AllLiteral node, Object data) {
		return iterateAndPropagate(node, data);
	}

	public VisitorResult visit(VS_IntegerLiteral node, Object data) {
		return iterateAndPropagate(node, data);
	}

	public VisitorResult visit(VS_IdentifierList node, Object data) {
		return iterateAndPropagate(node, data);
	}

	public VisitorResult visit(VS_ALLLiteralList node, Object data) {
		return iterateAndPropagate(node, data);
	}

	public VisitorResult visit(VS_CapabilityNode node, Object data) {
		return new StructureCheck();
	}


	public VisitorResult visit(VS_Additive node, Object data) {
		return iterateAndPropagate(node, data);
	}

	public VisitorResult visit(VS_SimpleExpression node, Object data) {
		return iterateAndPropagate(node, data);
	}

	public VisitorResult visit(VS_RelationExpression node, Object data) {
		return iterateAndPropagate(node, data);
	}
	
	public VisitorResult visit(VS_Relation node, Object data) {
		return iterateAndPropagate(node, data);
	}

	public VisitorResult visit(VS_Logic node, Object data) {
		return iterateAndPropagate(node, data);
	}
	
	public VisitorResult visit(VS_Expression node, Object data) {
		return iterateAndPropagate(node, data);
	}

	public VisitorResult visit(VS_FlowStatement node, Object data) {
		return iterateAndPropagate(node, data);	
	}

	public VisitorResult visit(VS_IfStatement node, Object data) {
		return iterateAndPropagate(node, data);	
	}
	
	public VisitorResult visit(VS_WhileStatement node, Object data) {
		return iterateAndPropagate(node, data);	
	}
	
	public VisitorResult visit(VS_VerificationScript node, Object data) {
		return iterateAndPropagate(node, data);
	}
	
	public VisitorResult visit(VS_Identifier node, Object data) {
		identifiers.add(node.value.toString());
		return new StructureCheck();
	}

	public VisitorResult visit(VS_IntegerLiteralList node, Object data) {
		return iterateAndPropagate(node, data);
	}

	public VisitorResult visit(VS_ArrayALLInNode node, Object data) {
		return iterateAndPropagate(node, data);
	}

	public VisitorResult visit(VS_ArrayIntegerInNode node, Object data) {
		StructureCheck check = iterateAndPropagate(node, data);		
		check.setInList();
		check.setElementsInList(node.jjtGetChild(0).jjtGetNumChildren());
		return check;
	}

	public VisitorResult visit(VS_StatementBlock node, Object data) {
		return iterateAndPropagate(node, data);

	}
	
	public VisitorResult visit(VS_ArrayOutNode node, Object data) {
		
		StructureCheck check = iterateAndPropagate(node, data);		
		check.setOutList();
		check.setElementsOutList(node.jjtGetChild(0).jjtGetNumChildren());
		return check;
	}

	public VisitorResult visit(VS_IdentifierFlowStatement node, Object data) {
		return iterateAndPropagate(node, data);
	}


	public VisitorResult visit(VS_CapabilityDeclaration node, Object data) {
		return new StructureCheck();
	}

	public VisitorResult visit(VS_Statement node, Object data) {
		return iterateAndPropagate(node, data);
	}

	public VisitorResult visit(VS_VerificationBlock node, Object data) {
		return iterateAndPropagate(node, data);
	}

	public VisitorResult visit(VS_ArrayOutNodeFlowStatement node, Object data) {
		StructureCheck check = iterateAndPropagate(node, data);
		node.childrenAccept(this, check);
		
		if(!check.isArrayStructureCorrect() ){
			System.out.println("Error!");
			check.fail();
		}
		return check;
	}
	
	public VisitorResult visit(VS_Type node, Object data) {
		return new StructureCheck();
	}

	public VisitorResult visit(VS_TypeList node, Object data) {
		return iterateAndPropagate(node, data);
	}

	public VisitorResult visit(VS_TypeDeclaration node, Object data) {
		return iterateAndPropagate(node, data);
	}
	
	public VisitorResult visit(VS_Void node, Object data) {
		return new StructureCheck();
	}
	
	private StructureCheck iterateAndPropagate(SimpleNode node, Object data){
		StructureCheck finalResult = new StructureCheck();
		for (int i = 0; i < node.jjtGetNumChildren(); i++) {
			StructureCheck result = (StructureCheck) node.jjtGetChild(i).jjtAccept(this, data);
			finalResult.propagateCheckData(result);
		}
		
		return finalResult;
	}


}