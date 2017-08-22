
package org.tiger.proxy.parsing.parser.exception;

import org.tiger.proxy.parsing.lexer.token.TokenType;

public class SQLParsingUnsupportedException extends RuntimeException {
    
    private static final long serialVersionUID = -4968036951399076811L;
    
    private static final String MESSAGE = "Not supported token '%s'.";
    
    public SQLParsingUnsupportedException(final TokenType tokenType) {
        super(String.format(MESSAGE, tokenType.toString()));
    }
}
