package Triangle.AbstractSyntaxTrees;

import Triangle.SyntacticAnalyzer.SourcePosition;
import java.util.List;

public class RecordTypeDenoter extends TypeDenoter {

  public FieldTypeDenoter FT;
  public List<FuncDeclaration> functions;
  public List<ProcDeclaration> procedures;
  public List<Declaration> methods;

  public RecordTypeDenoter(FieldTypeDenoter f, List<FuncDeclaration> funcs, List<ProcDeclaration> procs, SourcePosition pos) {
    super(pos);
    this.FT = f;
    this.functions = funcs;
    this.procedures = procs;
    this.methods = methods;
  }

  public RecordTypeDenoter(FieldTypeDenoter ftAST, SourcePosition thePosition) {
    super(thePosition);
    FT = ftAST;
  }

  public Object visit(Visitor v, Object o) {
    return v.visitRecordTypeDenoter(this, o);
  }

  public Declaration findMethod(Identifier methodName) {
    for (Declaration d : this.methods) {
      if (d instanceof FuncDeclaration) {
        FuncDeclaration func = (FuncDeclaration) d;
        if (func.I.spelling.equals(methodName.spelling)) {
          return func;
        }
      } else if (d instanceof ProcDeclaration) {
        ProcDeclaration proc = (ProcDeclaration) d;
        if (proc.I.spelling.equals(methodName.spelling)) {
          return proc;
        }
      }
    }
    return null;
  }


  public boolean equals(Object obj) {
    if (obj != null && obj instanceof ErrorTypeDenoter)
      return true;
    else if (obj != null && obj instanceof RecordTypeDenoter)
      return this.FT.equals(((RecordTypeDenoter) obj).FT);
    else
      return false;
  }
}
