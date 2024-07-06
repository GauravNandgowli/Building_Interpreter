package lox;

import java.util.List;

abstract class Expr {
  abstract <R> R accept(Visitor<R> visitor);
}
