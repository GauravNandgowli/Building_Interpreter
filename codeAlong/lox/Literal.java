package lox;

public class Literal extends Expr {
    Literal(Object value) {
        this.value = value;
    }

    @Override
    <R> R accept(Visitor<R> visitor) {
        return visitor.visitLiteralExpr(this);
    }

    final Object value;
}
