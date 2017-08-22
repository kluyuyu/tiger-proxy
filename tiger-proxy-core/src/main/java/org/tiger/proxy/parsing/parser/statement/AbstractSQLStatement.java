package org.tiger.proxy.parsing.parser.statement;

import org.tiger.proxy.constant.SQLType;
import org.tiger.proxy.parsing.parser.context.GroupBy;
import org.tiger.proxy.parsing.parser.context.OrderBy;
import org.tiger.proxy.parsing.parser.context.condition.Conditions;
import org.tiger.proxy.parsing.parser.context.limit.Limit;
import org.tiger.proxy.parsing.parser.context.table.Tables;
import org.tiger.proxy.parsing.parser.token.SQLToken;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * SQL语句对象抽象类.
 *
 * @author fish
 */
public abstract class AbstractSQLStatement implements SQLStatement {
    
    private final SQLType type;
    
    private final Tables tables = new Tables();
    
    private final Conditions conditions = new Conditions();
    
    private final List<SQLToken> sqlTokens = new LinkedList<>();
    
    @Override
    public final SQLType getType() {
        return type;
    }
    
    public List<OrderBy> getOrderByList() {
        return Collections.emptyList();
    }
    
    public List<GroupBy> getGroupByList() {
        return Collections.emptyList();
    }
    
    @Override
    public List<AggregationSelectItem> getAggregationSelectItems() {
        return Collections.emptyList();
    }
    
    public Limit getLimit() {
        return null;
    }
    
    public void setLimit(final Limit limit) {
    }
}
