package Triangle.AbstractSyntaxTrees;

import Triangle.SyntacticAnalyzer.SourcePosition;

public class DoCommand extends Command {
    public DoCommand(Command cAST, Expression eAST, SourcePosition position) {
        super(position);
        C = cAST;
        E = eAST;
    }

    public Object visit(Visitor v, Object o) {
        return v.visitDoCommand(this, o);
    }

    public Command C;
    public Expression E;
}
