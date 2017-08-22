package org.tiger.proxy.parsing.parser.expression;

/**
 * 标识表达式.
 *
 * @author fish
 */
public final class SQLIdentifierExpression implements SQLExpression {
    
    private final String name;

    public SQLIdentifierExpression(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
