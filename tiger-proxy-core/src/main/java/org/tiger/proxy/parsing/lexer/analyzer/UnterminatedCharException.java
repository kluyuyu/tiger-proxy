package org.tiger.proxy.parsing.lexer.analyzer;


/**
 * 符号未正确结束的异常.
 *
 * @author fish
 */
public final class UnterminatedCharException extends RuntimeException {
    
    private static final long serialVersionUID = 8575890835166900925L;
    
    private static final String MESSAGE = "Illegal input, unterminated '%s'.";
    
    public UnterminatedCharException(final char terminatedChar) {
        super(String.format(MESSAGE, terminatedChar));
    }
    
    public UnterminatedCharException(final String terminatedChar) {
        super(String.format(MESSAGE, terminatedChar));
    }
}
