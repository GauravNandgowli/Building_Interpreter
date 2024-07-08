package lox;

class Literal extends Expr {
    Literal(Object value) {
        this.value = value;
    }

    @Override
    <R> R accept(ExprVisitor<R> visitor) {
        return visitor.visitLiteralExpr(this);
    }

    final Object value;
}