/*
 * IDE-Triangle v1.0
 * TreeVisitor.java
 */

package Core.Visitors;

import Triangle.AbstractSyntaxTrees.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.util.Map;

/**
 * Implements the Triangle Visitor interface, which is used to
 * visit an entire AST.
 * <p>
 * Generates DefaultMutableTreeNodes, used to draw a JTree.
 *
 * @author Luis Leopoldo Pérez
 */
public class TreeVisitor implements Visitor {

    /**
     * Creates a new instance of TreeVisitor.
     */
    public TreeVisitor() {
    }

    // <editor-fold defaultstate="collapsed" desc=" Commands ">
    public Object visitAssignCommand(AssignCommand ast, Object o) {
        return (createBinary("Assign Command", ast.V, ast.E));
    }

    public Object visitCallCommand(CallCommand ast, Object o) {
        return (createBinary("Call Command", ast.I, ast.APS));
    }

    public Object visitEmptyCommand(EmptyCommand ast, Object o) {
        return (createNullary("Empty Command"));
    }

    public Object visitIfCommand(IfCommand ast, Object obj) {
        return (createTernary("If Command", ast.E, ast.C1, ast.C2));
    }

    public Object visitLetCommand(LetCommand ast, Object obj) {
        if (ast.D == null || ast.C == null) {
            throw new IllegalArgumentException("Error: LetCommand contiene nodos nulos.");
        }
        return (createBinary("Let Command", ast.D, ast.C));
    }

    public Object visitSequentialCommand(SequentialCommand ast, Object obj) {
        return (createBinary("Sequential Command", ast.C1, ast.C2));
    }

    public Object visitWhileCommand(WhileCommand ast, Object obj) {
        return (createBinary("While Command", ast.E, ast.C));
    }

    public Object visitRepeatCommand(RepeatCommand ast, Object o) {
        if (ast.C == null || ast.E == null) {
            throw new IllegalArgumentException("Error: RepeatCommand contiene nodos nulos.");
        }
        DefaultMutableTreeNode parent = (DefaultMutableTreeNode) o;
        DefaultMutableTreeNode repeatNode = new DefaultMutableTreeNode("Repeat Command");
        parent.add(repeatNode);
        repeatNode.add((DefaultMutableTreeNode) ast.C.visit(this, null));
        repeatNode.add((DefaultMutableTreeNode) ast.E.visit(this, null));
        return null;
    }

    @Override
    public Object visitArrayExpression(ArrayExpression ast, Object o) {
        return createUnary("Array Expression", ast.AA);
    }


    public Object visitForCommand(ForCommand ast, Object o) {
        DefaultMutableTreeNode forNode = new DefaultMutableTreeNode("For Command");

        // Visit variable
        DefaultMutableTreeNode varNode = (DefaultMutableTreeNode) ast.V.visit(this, null);
        if (varNode != null) {
            forNode.add(varNode);
        }

        // Visit start expression
        DefaultMutableTreeNode startNode = (DefaultMutableTreeNode) ast.E1.visit(this, null);
        if (startNode != null) {
            DefaultMutableTreeNode startLabel = new DefaultMutableTreeNode("Start:");
            startLabel.add(startNode);
            forNode.add(startLabel);
        }

        // Visit end expression
        DefaultMutableTreeNode endNode = (DefaultMutableTreeNode) ast.E2.visit(this, null);
        if (endNode != null) {
            DefaultMutableTreeNode endLabel = new DefaultMutableTreeNode("End:");
            endLabel.add(endNode);
            forNode.add(endLabel);
        }

        // Visit step expression (if exists)
        if (ast.E3 != null) {
            DefaultMutableTreeNode stepNode = (DefaultMutableTreeNode) ast.E3.visit(this, null);
            if (stepNode != null) {
                DefaultMutableTreeNode stepLabel = new DefaultMutableTreeNode("Step:");
                stepLabel.add(stepNode);
                forNode.add(stepLabel);
            }
        } else {
            DefaultMutableTreeNode stepLabel = new DefaultMutableTreeNode("Step: default +1");
            forNode.add(stepLabel);
        }

        // Visit body command
        DefaultMutableTreeNode bodyNode = (DefaultMutableTreeNode) ast.C.visit(this, null);
        if (bodyNode != null) {
            DefaultMutableTreeNode bodyLabel = new DefaultMutableTreeNode("Do:");
            bodyLabel.add(bodyNode);
            forNode.add(bodyLabel);
        }

        return forNode;
    }

    // </editor-fold>
    @Override
    public Object visitDoCommand(DoCommand ast, Object o) {
        return null; // Método vacío, sin procesamiento
    }



    // <editor-fold defaultstate="collapsed" desc=" Expressions ">
    public Object visitBinaryExpression(BinaryExpression ast, Object obj) {
        return (createTernary("Binary Expression", ast.E1, ast.O, ast.E2));
    }

    @Override
    public Object visitCallExpression(CallExpression ast, Object o) {
        return null;
    }

    @Override
    public Object visitCaseCommand(CaseCommand caseCommand, Object o) {
        return null;
    }

    @Override
    public Object visitCharacterExpression(CharacterExpression ast, Object o) {
        return null;
    }

    @Override
    public Object visitEmptyExpression(EmptyExpression ast, Object o) {
        return null;
    }

    @Override
    public Object visitIfExpression(IfExpression ast, Object o) {
        return null;
    }

    public Object visitIntegerExpression(IntegerExpression ast, Object obj) {
        return (createUnary("Integer Expression", ast.IL));
    }

    @Override
    public Object visitLetExpression(LetExpression ast, Object o) {
        return null;
    }

    @Override
    public Object visitRecordExpression(RecordExpression ast, Object o) {
        return null;
    }

    @Override
    public Object visitUnaryExpression(UnaryExpression ast, Object o) {
        return null;
    }

    public Object visitVnameExpression(VnameExpression ast, Object obj) {
        return (createUnary("Vname Expression", ast.V));
    }

    @Override
    public Object visitCaseExpression(CaseExpression ast, Object o) {
        return null;
    }

    @Override
    public Object visitBinaryOperatorDeclaration(BinaryOperatorDeclaration ast, Object o) {
        return null;
    }

    @Override
    public Object visitConstDeclaration(ConstDeclaration ast, Object o) {
        return null;
    }

    @Override
    public Object visitFuncDeclaration(FuncDeclaration ast, Object o) {
        return null;
    }

    @Override
    public Object visitProcDeclaration(ProcDeclaration ast, Object o) {
        return null;
    }

    @Override
    public Object visitSequentialDeclaration(SequentialDeclaration ast, Object o) {
        return null;
    }

    @Override
    public Object visitTypeDeclaration(TypeDeclaration ast, Object o) {
        return null;
    }

    @Override
    public Object visitUnaryOperatorDeclaration(UnaryOperatorDeclaration ast, Object o) {
        return null;
    }

    @Override
    public Object visitVarDeclaration(VarDeclaration ast, Object o) {
        return null;
    }

    @Override
    public Object visitMultipleArrayAggregate(MultipleArrayAggregate ast, Object o) {
        return null;
    }

    @Override
    public Object visitSingleArrayAggregate(SingleArrayAggregate ast, Object o) {
        return null;
    }

    @Override
    public Object visitMultipleRecordAggregate(MultipleRecordAggregate ast, Object o) {
        return null;
    }

    @Override
    public Object visitSingleRecordAggregate(SingleRecordAggregate ast, Object o) {
        return null;
    }

    @Override
    public Object visitConstFormalParameter(ConstFormalParameter ast, Object o) {
        return null;
    }

    @Override
    public Object visitFuncFormalParameter(FuncFormalParameter ast, Object o) {
        return null;
    }

    @Override
    public Object visitProcFormalParameter(ProcFormalParameter ast, Object o) {
        return null;
    }

    @Override
    public Object visitVarFormalParameter(VarFormalParameter ast, Object o) {
        return null;
    }

    @Override
    public Object visitEmptyFormalParameterSequence(EmptyFormalParameterSequence ast, Object o) {
        return null;
    }

    @Override
    public Object visitMultipleFormalParameterSequence(MultipleFormalParameterSequence ast, Object o) {
        return null;
    }

    @Override
    public Object visitSingleFormalParameterSequence(SingleFormalParameterSequence ast, Object o) {
        return null;
    }

    @Override
    public Object visitConstActualParameter(ConstActualParameter ast, Object o) {
        return null;
    }

    @Override
    public Object visitFuncActualParameter(FuncActualParameter ast, Object o) {
        return null;
    }

    @Override
    public Object visitProcActualParameter(ProcActualParameter ast, Object o) {
        return null;
    }

    @Override
    public Object visitVarActualParameter(VarActualParameter ast, Object o) {
        return null;
    }

    @Override
    public Object visitEmptyActualParameterSequence(EmptyActualParameterSequence ast, Object o) {
        return null;
    }

    @Override
    public Object visitMultipleActualParameterSequence(MultipleActualParameterSequence ast, Object o) {
        return null;
    }

    @Override
    public Object visitSingleActualParameterSequence(SingleActualParameterSequence ast, Object o) {
        return null;
    }

    @Override
    public Object visitAnyTypeDenoter(AnyTypeDenoter ast, Object o) {
        return null;
    }

    @Override
    public Object visitArrayTypeDenoter(ArrayTypeDenoter ast, Object o) {
        return null;
    }

    @Override
    public Object visitBoolTypeDenoter(BoolTypeDenoter ast, Object o) {
        return null;
    }

    @Override
    public Object visitCharTypeDenoter(CharTypeDenoter ast, Object o) {
        return null;
    }

    @Override
    public Object visitErrorTypeDenoter(ErrorTypeDenoter ast, Object o) {
        return null;
    }

    @Override
    public Object visitSimpleTypeDenoter(SimpleTypeDenoter ast, Object o) {
        return null;
    }

    @Override
    public Object visitIntTypeDenoter(IntTypeDenoter ast, Object o) {
        return null;
    }

    @Override
    public Object visitRecordTypeDenoter(RecordTypeDenoter ast, Object o) {
        return null;
    }

    @Override
    public Object visitMultipleFieldTypeDenoter(MultipleFieldTypeDenoter ast, Object o) {
        return null;
    }

    @Override
    public Object visitSingleFieldTypeDenoter(SingleFieldTypeDenoter ast, Object o) {
        return null;
    }

    @Override
    public Object visitCharacterLiteral(CharacterLiteral ast, Object o) {
        return null;
    }

    @Override
    public Object visitIdentifier(Identifier ast, Object o) {
        return null;
    }

    @Override
    public Object visitIntegerLiteral(IntegerLiteral ast, Object o) {
        return null;
    }

    @Override
    public Object visitOperator(Operator ast, Object o) {
        return null;
    }

    @Override
    public Object visitDotVname(DotVname ast, Object o) {
        return null;
    }

    @Override
    public Object visitSimpleVname(SimpleVname ast, Object o) {
        return null;
    }

    @Override
    public Object visitSubscriptVname(SubscriptVname ast, Object o) {
        return null;
    }

    @Override
    public Object visitProgram(Program ast, Object o) {
        return null;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc=" Tree Creation Methods ">

    /**
     * Creates a nullary tree node (doesn't have any children).
     *
     * @param caption The tree's caption (text to be shown when the tree is drawn).
     * @return The tree node.
     */
    public DefaultMutableTreeNode createNullary(String caption) {
        return new DefaultMutableTreeNode(caption);
    }

    /**
     * Creates an unary tree node.
     *
     * @param caption The tree's caption (text to be shown when the tree is drawn).
     * @param child1  The first child node.
     * @return The tree node.
     */
    public DefaultMutableTreeNode createUnary(String caption, AST child1) {
        if (child1 == null) {
            throw new IllegalArgumentException("El nodo hijo en createUnary no puede ser nulo.");
        }
        DefaultMutableTreeNode t = new DefaultMutableTreeNode(caption);
        t.add((DefaultMutableTreeNode) child1.visit(this, null));
        return t;
    }

    /**
     * Creates a binary tree node.
     *
     * @param caption The tree's caption (text to be shown when the tree is drawn).
     * @param child1  The first child node.
     * @param child2  The second child node.
     * @return The tree node.
     */
    public DefaultMutableTreeNode createBinary(String caption, AST child1, AST child2) {
        if (child1 == null || child2 == null) {
            throw new IllegalArgumentException("Los nodos hijos en createBinary no pueden ser nulos.");
        }
        DefaultMutableTreeNode t = new DefaultMutableTreeNode(caption);
        t.add((DefaultMutableTreeNode) child1.visit(this, null));
        t.add((DefaultMutableTreeNode) child2.visit(this, null));
        return t;
    }

    /**
     * Creates a ternary tree node.
     *
     * @param caption The tree's caption (text to be shown when the tree is drawn).
     * @param child1  The first child node.
     * @param child2  The second child node.
     * @param child3  The third child node.
     * @return The tree node.
     */
    public DefaultMutableTreeNode createTernary(String caption, AST child1, AST child2, AST child3) {
        if (child1 == null || child2 == null || child3 == null) {
            throw new IllegalArgumentException("Los nodos hijos en createTernary no pueden ser nulos.");
        }
        DefaultMutableTreeNode t = new DefaultMutableTreeNode(caption);
        t.add((DefaultMutableTreeNode) child1.visit(this, null));
        t.add((DefaultMutableTreeNode) child2.visit(this, null));
        t.add((DefaultMutableTreeNode) child3.visit(this, null));
        return t;
    }

    // </editor-fold>
}
