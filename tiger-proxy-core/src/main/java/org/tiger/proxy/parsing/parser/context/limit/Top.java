package org.tiger.proxy.parsing.parser.context.limit;

import org.tiger.proxy.parsing.parser.expression.SQLExpression;

/**
 * SQLServer分页对象.
 * 
 * @author fish
 */
public final class Top implements SQLExpression {
    
    private SQLExpression expr;
    
    private boolean percent;

    public SQLExpression getExpr() {
        return expr;
    }

    public boolean isPercent() {
        return percent;
    }
}
