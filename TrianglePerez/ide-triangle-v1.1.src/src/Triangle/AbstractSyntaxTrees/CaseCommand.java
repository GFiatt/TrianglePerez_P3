package Triangle.AbstractSyntaxTrees;

import java.util.LinkedHashMap;

import Triangle.SyntacticAnalyzer.SourcePosition;

public class CaseCommand extends Command {

    public CaseCommand(Vname vAST, LinkedHashMap<Object, Command> map, Command cAST, SourcePosition thePosition) {
        super(thePosition);
        V = vAST;
        MAP = map;
        C = cAST;
    }

    public Object visit(Visitor v, Object o) {
        return v.visitCaseCommand(this, o);
    }

    public LinkedHashMap<Object, Command> MAP;
    public Command C;
    public Vname V;
}
