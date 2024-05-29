// package com.craftinginterpreters.lox;

package lox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Scanner {
    private final String source;
    private final List<Token> tokens = new ArrayList<>();
    // 'tokens' is a List of Token objects. It's instantiated as an ArrayList,
    // which is a resizable-array implementation of the List interface.
    // This allows flexibility - if we want to change to a different List
    // implementation later (like LinkedList), we only need to change the
    // instantiation, not the usage of 'tokens'.
    private int start = 0;
    private int current = 0;
    private int line = 1;

    Scanner(String source) {
        this.source = source;

    }

    // return a list of token objects
    List<Token> scanTokens() {
        while (!isAtEnd()) {
            start = current;
            scanToken();
        }

        tokens.add(new Token(TokenType.EOF, "", null, line));
        return tokens;
    }

    private boolean isAtEnd() {
        return current >= source.length();
    }

    private char advance() {
        return source.charAt(current++);
    }

    private void addToken(TokenType type) {
        addToken(type, null);
    }

    private void addToken(TokenType type, Object literal) {
        String text = source.substring(start, current);
        tokens.add(new Token(type, text, literal, line));
    }

    private boolean match(char expected) {
        if (isAtEnd())
            return false;
        if (source.charAt(current) != expected)
            return false;

        current++;
        return true;
    }

    private char peek() {
        if (isAtEnd())
            return '\0';
        return source.charAt(current);
    }

    private char peekNext() {
        if (current + 1 >= source.length())
            return '\0';
        return source.charAt(current + 1);
    }

    private void string() {
        while (peek() != '"' && !isAtEnd()) {
            if (peek() == '\n')
                line++;
            advance();
        }

        if (isAtEnd()) {
            lox.error(line, "Unterminated string.");
            return;
        }

        // The closing ".
        advance();

        // Trim the surrounding quotes.
        String value = source.substring(start + 1, current - 1);
        addToken(TokenType.STRING, value);
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private void number() {
        while (isDigit(peek()))
            advance();

        // Look for a fractional part.
        if (peek() == '.' && isDigit(peekNext())) {
            // Consume the "."
            advance();

            while (isDigit(peek()))
                advance();
        }

        addToken(TokenType.NUMBER,
                Double.parseDouble(source.substring(start, current)));
    }

    private void identifier() {
        while (isAlphaNumeric(peek()))
            advance();
        String text = source.substring(start, current);
        TokenType type = keywords.get(text);
        if (type == null)
            type = TokenType.IDENTIFIER;
        addToken(type);

    }

    private boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') ||
                (c >= 'A' && c <= 'Z') ||
                c == '_';
    }

    private boolean isAlphaNumeric(char c) {
        return isAlpha(c) || isDigit(c);
    }

    private static final Map<String, TokenType> keywords;

    static {
        keywords = new HashMap<>();
        keywords.put("and", TokenType.AND);
        keywords.put("class", TokenType.CLASS);
        keywords.put("else", TokenType.ELSE);
        keywords.put("false", TokenType.FALSE);
        keywords.put("for", TokenType.FOR);
        keywords.put("fun", TokenType.FUN);
        keywords.put("if", TokenType.IF);
        keywords.put("nil", TokenType.NIL);
        keywords.put("or", TokenType.OR);
        keywords.put("print", TokenType.PRINT);
        keywords.put("return", TokenType.RETURN);
        keywords.put("super", TokenType.SUPER);
        keywords.put("this", TokenType.THIS);
        keywords.put("true", TokenType.TRUE);
        keywords.put("var", TokenType.VAR);
        keywords.put("while", TokenType.WHILE);
    }

    private void scanToken() {
        char c = advance();
        switch (c) {
            case '(':
                addToken(TokenType.LEFT_PAREN);
                break;

            case ')':
                addToken(TokenType.RIGHT_PAREN);
                break;

            case '{':
                addToken(TokenType.LEFT_BRACE);
                break;

            case '}':
                addToken(TokenType.RIGHT_BRACE);
                break;

            case ',':
                addToken(TokenType.COMMA);
                break;

            case '.':
                addToken(TokenType.DOT);
                break;

            case '-':
                addToken(TokenType.MINUS);
                break;

            case '+':
                addToken(TokenType.PLUS);
                break;

            case ';':
                addToken(TokenType.SEMICOLON);
                break;

            case '*':
                addToken(TokenType.STAR);
                break;

            case '!':
                if (match('=')) {
                    addToken((TokenType.BANG_EQUAL));
                } else
                    addToken(TokenType.BANG);
                break;

            case '=':
                if (match('=')) {
                    addToken((TokenType.EQUAL_EQUAL));
                } else
                    addToken(TokenType.EQUAL);
                break;

            case '<':
                if (match('=')) {
                    addToken((TokenType.LESS_EQUAL));
                } else
                    addToken(TokenType.LESS);
                break;

            case '>':
                if (match('=')) {
                    addToken((TokenType.GREATER_EQUAL));
                } else
                    addToken(TokenType.GREATER);
                break;

            // For type determination, capture conversion is used, wherein for your example
            // int is first boxed to Integer and then the closest common super class of
            // Integer and String is fetched, which is Object class. So the type for the
            // Conditional Expression is Object and so the method with Object as parameter
            // is called.
            // case '>':
            // addToken(match('=') ? TokenType.GREATER_EQUAL : TokenType.GREATER);
            // break; Won't work because of the above reason

            case '/':
                if (match('/')) {
                    // A comment goes until the end of the line.
                    while (peek() != '\n' && !isAtEnd())
                        advance();
                } else {
                    addToken(TokenType.SLASH);
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

}
