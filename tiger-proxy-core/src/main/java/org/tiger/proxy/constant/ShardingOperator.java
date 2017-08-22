
package org.tiger.proxy.constant;

/**
 * 分片运算符.
 *
 * @author fish
 */
public enum ShardingOperator {
    
    EQUAL("="), 
    BETWEEN("BETWEEN"), 
    IN("IN");
    
    private final String expression;

    ShardingOperator(String expression) {
        this.expression = expression;
    }

    public String getExpression() {
        return expression;
    }
}
