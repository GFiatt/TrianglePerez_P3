package Triangle.AbstractSyntaxTrees;

import java.util.LinkedHashMap;

import Triangle.SyntacticAnalyzer.SourcePosition;

public class CaseExpression extends Expression {

    public CaseExpression(Vname vAST, LinkedHashMap<Object, Expression> map, SourcePosition thePosition) {
        super(thePosition);
        V = vAST;
        MAP = map;
    }

    public Object visit(Visitor v, Object o) {
        return v.visitCaseExpression(this, o);
    }

    public Vname V;
    public LinkedHashMap<Object, Expression> MAP;
}
