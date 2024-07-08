package lox;

class Grouping extends Expr {
    Grouping(Expr expression) {
        this.expression = expression;
    }

    @Override
    <R> R accept(ExprVisitor<R> visitor) {
        return visitor.visitGroupingExpr(this);
    }

    final Expr expression;
}