package org.tiger.proxy.parsing.parser.expression;

/**
 * 占位符表达式.
 *
 * @author fish
 */
public final class SQLPlaceholderExpression implements SQLExpression {
    
    private final int index;

    public SQLPlaceholderExpression(int index) {
        this.index = index;
    }
}
