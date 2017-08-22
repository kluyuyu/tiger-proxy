package org.tiger.proxy.parsing.lexer.dialect.mysql;

import org.tiger.proxy.parsing.lexer.Lexer;
import org.tiger.proxy.parsing.lexer.analyzer.Dictionary;

/**
 * MySQL词法解析器.
 *
 * @author fish
 */
public final class MySQLLexer extends Lexer {
    
    private static Dictionary dictionary = new Dictionary(MySQLKeyword.values());
    
    public MySQLLexer(final String input) {
        super(input, dictionary);
    }
    
    @Override
    protected boolean isHintBegin() {
        return '/' == getCurrentChar(0) && '*' == getCurrentChar(1) && '!' == getCurrentChar(2);
    }
    
    @Override
    protected boolean isCommentBegin() {
        return '#' == getCurrentChar(0) || super.isCommentBegin();
    }
    
    @Override
    protected boolean isVariableBegin() {
        return '@' == getCurrentChar(0);
    }
}
