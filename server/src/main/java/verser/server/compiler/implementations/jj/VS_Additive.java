/* Generated By:JJTree: Do not edit this line. VS_Additive.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=VS_,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package verser.server.compiler.implementations.jj;

public
class VS_Additive extends SimpleNode {
  public VS_Additive(int id) {
    super(id);
  }

  public VS_Additive(VerificationScriptGrammar p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public VisitorResult jjtAccept(VerificationScriptGrammarVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=8419b63953d93e8d8dbd8544905d88dd (do not edit this line) */
