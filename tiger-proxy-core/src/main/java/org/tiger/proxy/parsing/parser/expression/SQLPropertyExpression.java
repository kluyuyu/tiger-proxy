package org.tiger.proxy.parsing.parser.expression;

/**
 * 属性表达式.
 *
 * @author fish
 */
public final class SQLPropertyExpression implements SQLExpression {
    
    private final SQLIdentifierExpression owner;
    
    private final String name;


    public SQLPropertyExpression(SQLIdentifierExpression owner, String name) {
        this.owner = owner;
        this.name = name;
    }
}
