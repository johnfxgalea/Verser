/* Generated By:JJTree: Do not edit this line. VS_Relation.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=VS_,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package verser.server.compiler.implementations.jj;

public
class VS_Relation extends SimpleNode {
  public VS_Relation(int id) {
    super(id);
  }

  public VS_Relation(VerificationScriptGrammar p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public VisitorResult jjtAccept(VerificationScriptGrammarVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=940a38ed5607aaa2c0b579253e6177e0 (do not edit this line) */
