package lox;

class Assign extends Expr {

    final Token name;
    final Expr value;

    Assign(Token name, Expr value) {
        this.name = name;
        this.value = value;
    }

    @Override
    <R> R accept(ExprVisitor<R> visitor) {
        return visitor.visitAssignExpr(this);
    }

}