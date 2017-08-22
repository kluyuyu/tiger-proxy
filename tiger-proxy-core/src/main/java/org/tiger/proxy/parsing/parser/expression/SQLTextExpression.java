package org.tiger.proxy.parsing.parser.expression;

/**
 * 字符表达式.
 *
 * @author fish
 */
public final class SQLTextExpression implements SQLExpression {
    
    private final String text;

    public SQLTextExpression(String text) {
        this.text = text;
    }
}
