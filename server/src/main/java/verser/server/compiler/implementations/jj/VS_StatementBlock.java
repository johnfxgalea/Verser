/* Generated By:JJTree: Do not edit this line. VS_StatementBlock.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=VS_,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package verser.server.compiler.implementations.jj;

public
class VS_StatementBlock extends SimpleNode {
  public VS_StatementBlock(int id) {
    super(id);
  }

  public VS_StatementBlock(VerificationScriptGrammar p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public VisitorResult jjtAccept(VerificationScriptGrammarVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=d885fdc957271fd92078dc58883047e0 (do not edit this line) */