package Triangle.AbstractSyntaxTrees;

import Triangle.SyntacticAnalyzer.SourcePosition;

public class LetCommand extends Command {

  public LetCommand(Declaration dAST, Command cAST, SourcePosition thePosition) {
    super(thePosition);
    if (dAST == null || cAST == null) {
      System.err.println("Error: LetCommand inicializado con nodos nulos.");
      throw new IllegalArgumentException("Los nodos de LetCommand no pueden ser nulos.");
    }
    this.D = dAST;
    this.C = cAST;
  }

  public Object visit(Visitor v, Object o) {
    return v.visitLetCommand(this, o);
  }

  public Declaration D;
  public Command C;
}
