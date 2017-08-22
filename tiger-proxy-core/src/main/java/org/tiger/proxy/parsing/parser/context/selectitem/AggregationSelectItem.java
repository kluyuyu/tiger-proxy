package org.tiger.proxy.parsing.parser.context.selectitem;

import com.google.common.base.Optional;
import org.tiger.proxy.constant.AggregationType;
import org.tiger.proxy.parsing.parser.context.IndexColumn;


import java.util.ArrayList;
import java.util.List;

/**
 * 聚合选择项.
 *
 * @author fish
 */
public final class AggregationSelectItem implements SelectItem, IndexColumn {
    
    private final String innerExpression;
    
    private final Optional<String> alias;

    private int columnIndex = -1;
    
    private final AggregationType aggregationType;
    
    private final List<AggregationSelectItem> derivedAggregationSelectItems = new ArrayList<>(2);
    
    public AggregationSelectItem(final String innerExpression, final Optional<String> alias, final int columnIndex, final AggregationType aggregationType) {
        this.innerExpression = innerExpression;
        this.alias = alias;
        this.columnIndex = columnIndex;
        this.aggregationType = aggregationType;
    }
    
    @Override
    public String getExpression() {
        return aggregationType.name() + innerExpression;
    }
    
    @Override
    public Optional<String> getColumnLabel() {
        return alias;
    }
    
    @Override
    public Optional<String> getColumnName() {
        return Optional.of(getExpression());
    }


    public String getInnerExpression() {
        return innerExpression;
    }

    @Override
    public Optional<String> getAlias() {
        return alias;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public AggregationType getAggregationType() {
        return aggregationType;
    }

    public List<AggregationSelectItem> getDerivedAggregationSelectItems() {
        return derivedAggregationSelectItems;
    }


    @Override
    public void setColumnIndex(int index) {
        this.columnIndex = index;
    }
}
