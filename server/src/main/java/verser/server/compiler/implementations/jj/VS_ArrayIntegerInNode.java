/* Generated By:JJTree: Do not edit this line. VS_ArrayIntegerInNode.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=VS_,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package verser.server.compiler.implementations.jj;

public
class VS_ArrayIntegerInNode extends SimpleNode {
  public VS_ArrayIntegerInNode(int id) {
    super(id);
  }

  public VS_ArrayIntegerInNode(VerificationScriptGrammar p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public VisitorResult jjtAccept(VerificationScriptGrammarVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=4a26f388d23f115c7e883b5995bad994 (do not edit this line) */