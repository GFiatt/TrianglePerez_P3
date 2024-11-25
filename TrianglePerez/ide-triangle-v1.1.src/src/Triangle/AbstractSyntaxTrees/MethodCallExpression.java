
/*
 * @(#)MethodCallExpression.java                        1.0 2024/11/25
 *
 * Copyright (C) 1999, 2003 D.A. Watt and D.F. Brown
 * Modified for handling method calls in records.
 *
 */

package Triangle.AbstractSyntaxTrees;

import Triangle.SyntacticAnalyzer.SourcePosition;

public class MethodCallExpression extends Expression {

  public MethodCallExpression(Vname recordVname, Identifier methodName,
                              ActualParameterSequence parameterSequence,
                              SourcePosition thePosition) {
    super(thePosition);
    this.recordVname = recordVname;
    this.methodName = methodName;
    this.parameterSequence = parameterSequence;
  }

  public Object visit(Visitor v, Object o) {
    return v.visitMethodCallExpression(this, o);
  }

  public Vname recordVname;
  public Identifier methodName;
  public ActualParameterSequence parameterSequence;
}
