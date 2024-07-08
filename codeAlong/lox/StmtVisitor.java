package lox;

interface Visitor<R> {
    R visitBlockStmt(Block stmt);

    R visitExpressionStmt(Expression stmt);

    R visitFunctionStmt(Function stmt);

    R visitIfStmt(If stmt);

    R visitPrintStmt(Print stmt);

    R visitReturnStmt(ReturnStmt stmt);

    R visitVarStmt(Var stmt);

    R visitWhileStmt(While stmt);
}