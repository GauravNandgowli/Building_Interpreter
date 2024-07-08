package lox;

abstract class Expr {

  abstract <R> R accept(ExprVisitor<R> visitor);

}
