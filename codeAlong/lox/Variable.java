package lox;

class Variable extends Expr {
    Variable(Token name) {
        this.name = name;
    }

    @Override
    <R> R accept(ExprVisitor<R> visitor) {
        return visitor.visitVariableExpr(this);
    }

    final Token name;
}
