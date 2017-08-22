package org.tiger.proxy.parsing.parser.exception;


import org.tiger.proxy.parsing.lexer.Lexer;
import org.tiger.proxy.parsing.lexer.token.TokenType;

public class SQLParsingException extends RuntimeException {
    
    private static final long serialVersionUID = -6408790652103666096L;
    
    private static final String UNMATCH_MESSAGE = "SQL syntax error, expected token is '%s', actual token is '%s', literals is '%s'.";
    
    private static final String TOKEN_ERROR_MESSAGE = "SQL syntax error, token is '%s', literals is '%s'.";
    
    public SQLParsingException(final String message, final Object... args) {
        super(String.format(message, args));
    }
    
    public SQLParsingException(final Lexer lexer, final TokenType expectedTokenType) {
        super(String.format(UNMATCH_MESSAGE, expectedTokenType, lexer.getCurrentToken().getType(), lexer.getCurrentToken().getLiterals()));
    }
    
    public SQLParsingException(final Lexer lexer) {
        super(String.format(TOKEN_ERROR_MESSAGE, lexer.getCurrentToken().getType(), lexer.getCurrentToken().getLiterals()));
    }
}
