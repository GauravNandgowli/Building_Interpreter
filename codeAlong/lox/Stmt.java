package lox;

abstract class Stmt {

  abstract <R> R accept(Visitor<R> visitor);
}
