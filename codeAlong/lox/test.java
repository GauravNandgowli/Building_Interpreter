package lox;

import java.util.ArrayList;
import java.util.List;

public class test {
    static String source = "alpha123Num123 6ix96ix9";
    static int start = 0;
    static int current = 0;
    static int line = 1;

    public static void main(String[] args) {

        while (!isAtEnd()) {
            start = current;
            scanToken();
        }

    }

    static void scanToken() {
        char c = advance();
        switch (c) {
            case '(':
                System.out.println("(");
                break;

            case ')':
                System.out.println(")");
                break;

            case '{':
                System.out.println("{");
                break;

            case '}':
                System.out.println("}");
                break;

            case ',':
                System.out.println(",");
                break;

            case '.':
                System.out.println(".");
                break;

            case '-':
                System.out.println("-");
                break;

            case '+':
                System.out.println("+");
                break;

            case ';':
                System.out.println(";");
                break;

            case '*':
                System.out.println("*");
                break;

            case '!':
                if (match('=')) {
                    System.out.println("!=");
                } else
                    System.out.println("!");
                break;

            case '=':
                if (match('=')) {
                    System.out.println("==");
                } else
                    System.out.println("=");
                break;

            case '<':
                if (match('=')) {
                    System.out.println("<=");
                } else
                    System.out.println("<");
                break;

            case '>':
                if (match('=')) {
                    System.out.println(">=");
                } else
                    System.out.println(">");
                break;

            case '/':
                if (match('/')) {
                    // A comment goes until the end of the line.
                    while (peek() != '\n' && !isAtEnd())
                        advance();
                } else {
                    System.out.println("/");
                }
                break;

            case ' ':
            case '\r':
            case '\t':
                // Ignore whitespace.
                break;

            case '\n':
                line++;
                break;

            case '"':
                string();
                break;
            default:
                if (isDigit(c)) {
                    number();
                } else if (isAlpha(c)) {
                    identifier();
                } else {
                    lox.error(line, "Unexpected character.");
                }
                break;
        }
    }

    static boolean match(char expected) {
        if (isAtEnd())
            return false;
        if (source.charAt(current) != expected)
            return false;

        current++;
        return true;
    }

    static char advance() {
        return source.charAt(current++);
    }

    static char peek() {
        if (isAtEnd())
            return '\0';
        return source.charAt(current);
    }

    static char peekNext() {
        if (current + 1 >= source.length())
            return '\0';
        return source.charAt(current + 1);
    }

    static boolean isAtEnd() {
        return current >= source.length();

    }

    static void string() {
        while (peek() != '"' && !isAtEnd()) {
            if (peek() == '\n')
                line++;
            advance();
        }

        if (isAtEnd()) {
            lox.error(line, "Unterminated string.");
            return;
        }
        advance();
        String value = source.substring(start + 1, current - 1);
        System.out.println(value);
    }

    static void identifier() {
        while (isAlphaNumeric(peek()))
            advance();
        String text = source.substring(start, current);
        System.out.println(text);

    }

    static void number() {
        while (isDigit(peek()))
            advance();
        if (peek() == '.' && isDigit(peekNext())) {
            // Consume the "."
            advance();

            while (isDigit(peek()))
                advance();
        }

        System.out.println(source.substring(start, current));

    }

    static boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') ||
                (c >= 'A' && c <= 'Z') ||
                c == '_';
    }

    static boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    static boolean isAlphaNumeric(char c) {
        return isAlpha(c) || isDigit(c);
    }
}
