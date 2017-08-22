package org.tiger.proxy.parsing.parser.context.condition;

import com.google.common.collect.BoundType;
import com.google.common.collect.Range;
import org.tiger.proxy.constant.ShardingOperator;
import org.tiger.proxy.parsing.parser.context.Column;
import org.tiger.proxy.parsing.parser.expression.SQLExpression;
import org.tiger.proxy.parsing.parser.expression.SQLNumberExpression;
import org.tiger.proxy.parsing.parser.expression.SQLPlaceholderExpression;
import org.tiger.proxy.parsing.parser.expression.SQLTextExpression;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 条件对象.
 *
 * @author fish
 */
public final class Condition {

    private final Column column;

    private final ShardingOperator operator;

    private final Map<Integer, Comparable<?>> positionValueMap = new LinkedHashMap<>();
    
    private final Map<Integer, Integer> positionIndexMap = new LinkedHashMap<>();

    public Condition(Column column, ShardingOperator operator) {
        this.column = column;
        this.operator = operator;
    }

    public Condition(final Column column, final SQLExpression sqlExpression) {
        this(column, ShardingOperator.EQUAL);
        init(sqlExpression, 0);
    }
    
    public Condition(final Column column, final SQLExpression beginSQLExpression, final SQLExpression endSQLExpression) {
        this(column, ShardingOperator.BETWEEN);
        init(beginSQLExpression, 0);
        init(endSQLExpression, 1);
    }
    
    public Condition(final Column column, final List<SQLExpression> sqlExpressions) {
        this(column, ShardingOperator.IN);
        int count = 0;
        for (SQLExpression each : sqlExpressions) {
            init(each, count);
            count++;
        }
    }
    
    private void init(final SQLExpression sqlExpression, final int position) {
        if (sqlExpression instanceof SQLPlaceholderExpression) {
            positionIndexMap.put(position, ((SQLPlaceholderExpression) sqlExpression).getIndex());
        } else if (sqlExpression instanceof SQLTextExpression) {
            positionValueMap.put(position, ((SQLTextExpression) sqlExpression).getText());
        } else if (sqlExpression instanceof SQLNumberExpression) {
            positionValueMap.put(position, (Comparable) ((SQLNumberExpression) sqlExpression).getNumber());
        }
    }
    
    /**
     * 将条件对象转换为分片值.
     *
     * @param parameters 参数列表
     * @return 分片值
     */
    public ShardingValue<?> getShardingValue(final List<Object> parameters) {
        List<Comparable<?>> conditionValues = getValues(parameters);
        switch (operator) {
            case EQUAL:
                return new ShardingValue<Comparable<?>>(column.getTableName(), column.getName(), conditionValues.get(0));
            case IN:
                return new ShardingValue<>(column.getTableName(), column.getName(), conditionValues);
            case BETWEEN:
                return new ShardingValue<>(column.getTableName(), column.getName(), Range.range(conditionValues.get(0), BoundType.CLOSED, conditionValues.get(1), BoundType.CLOSED));
            default:
                throw new UnsupportedOperationException(operator.getExpression());
        }
    }
    
    private List<Comparable<?>> getValues(final List<Object> parameters) {
        List<Comparable<?>> result = new LinkedList<>(positionValueMap.values());
        for (Entry<Integer, Integer> entry : positionIndexMap.entrySet()) {
            Object parameter = parameters.get(entry.getValue());
            if (!(parameter instanceof Comparable<?>)) {
                throw new ShardingJdbcException("Parameter `%s` should extends Comparable for sharding value.", parameter);
            }
            if (entry.getKey() < result.size()) {
                result.add(entry.getKey(), (Comparable<?>) parameter);
            } else {
                result.add((Comparable<?>) parameter);
            }
        }
        return result;
    }


    public Column getColumn() {
        return column;
    }

    public ShardingOperator getOperator() {
        return operator;
    }

    public Map<Integer, Comparable<?>> getPositionValueMap() {
        return positionValueMap;
    }

    public Map<Integer, Integer> getPositionIndexMap() {
        return positionIndexMap;
    }
}
