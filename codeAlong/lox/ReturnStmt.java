package lox;

class ReturnStmt extends Stmt {
    ReturnStmt(Token keyword, Expr value) {
        this.keyword = keyword;
        this.value = value;
    }

    @Override
    <R> R accept(Visitor<R> visitor) {
        return visitor.visitReturnStmt(this);
    }

    final Token keyword;
    final Expr value;
}