package org.tiger.proxy.parsing.parser.expression;

/**
 * 数字表达式.
 *
 * @author fish
 */
public final class SQLNumberExpression implements SQLExpression {
    
    private final Number number;

    public SQLNumberExpression(Number number) {
        this.number = number;
    }

    public Number getNumber() {
        return number;
    }
}
