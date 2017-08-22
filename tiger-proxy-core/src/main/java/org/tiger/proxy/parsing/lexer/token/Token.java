package org.tiger.proxy.parsing.lexer.token;

/**
 * 词法标记.
 *
 * @author fish
 */
public final class Token {
    
    private final TokenType type;
    
    private final String literals;
    
    private final int endPosition;

    public Token(TokenType type, String literals, int endPosition) {
        this.type = type;
        this.literals = literals;
        this.endPosition = endPosition;
    }

    public TokenType getType() {
        return type;
    }

    public String getLiterals() {
        return literals;
    }

    public int getEndPosition() {
        return endPosition;
    }
}
