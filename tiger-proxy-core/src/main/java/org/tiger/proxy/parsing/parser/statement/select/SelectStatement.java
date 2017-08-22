package org.tiger.proxy.parsing.parser.statement.select;

import java.util.LinkedList;
import java.util.List;

/**
 * Select SQL语句对象.
 *
 * @author fish
 */
public final class SelectStatement extends AbstractSQLStatement {
    
    private boolean distinct;
    
    private boolean containStar;
    
    private int selectListLastPosition;
    
    private final List<SelectItem> items = new LinkedList<>();
    
    private final List<GroupBy> groupByList = new LinkedList<>();
    
    private final List<OrderBy> orderByList = new LinkedList<>();
    
    private Limit limit;
    
    public SelectStatement() {
        super(SQLType.SELECT);
    }
    
    @Override
    public List<AggregationSelectItem> getAggregationSelectItems() {
        List<AggregationSelectItem> result = new LinkedList<>();
        for (SelectItem each : items) {
            if (each instanceof AggregationSelectItem) {
                AggregationSelectItem aggregationSelectItem = (AggregationSelectItem) each;
                result.add(aggregationSelectItem);
                for (AggregationSelectItem derivedEach: aggregationSelectItem.getDerivedAggregationSelectItems()) {
                    result.add(derivedEach);
                }
            }
        }
        return result;
    }
    
    public Limit getLimit() {
        return limit;
    }
}
