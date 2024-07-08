package lox;

class Binary extends Expr {
    final Expr left;
    final Token operator;
    final Expr right;

    Binary(Expr left, Token operator, Expr right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    @Override
    <R> R accept(ExprVisitor<R> visitor) {
        return visitor.visitBinaryExpr(this);
    }

}