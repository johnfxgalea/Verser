/* Generated By:JJTree: Do not edit this line. VS_VerificationScript.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=VS_,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package verser.server.compiler.implementations.jj;

public
class VS_VerificationScript extends SimpleNode {
  public VS_VerificationScript(int id) {
    super(id);
  }

  public VS_VerificationScript(VerificationScriptGrammar p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public VisitorResult jjtAccept(VerificationScriptGrammarVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=536bf4642e9170b70d69fab55e7779fb (do not edit this line) */