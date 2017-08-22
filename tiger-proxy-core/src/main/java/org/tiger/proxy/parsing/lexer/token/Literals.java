package org.tiger.proxy.parsing.lexer.token;

/**
 * 词法字面量标记.
 *
 * @author fish
 */
public enum Literals implements TokenType {
    
    INT, FLOAT, HEX, CHARS, IDENTIFIER, VARIABLE
}
