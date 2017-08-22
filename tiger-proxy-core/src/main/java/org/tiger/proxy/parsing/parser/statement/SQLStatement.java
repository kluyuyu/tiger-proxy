package org.tiger.proxy.parsing.parser.statement;

import org.tiger.proxy.constant.SQLType;
import org.tiger.proxy.parsing.parser.context.GroupBy;
import org.tiger.proxy.parsing.parser.context.OrderBy;
import org.tiger.proxy.parsing.parser.context.condition.Conditions;
import org.tiger.proxy.parsing.parser.context.limit.Limit;
import org.tiger.proxy.parsing.parser.context.selectitem.AggregationSelectItem;
import org.tiger.proxy.parsing.parser.context.table.Tables;
import org.tiger.proxy.parsing.parser.token.SQLToken;

import java.util.List;

/**
 * SQL语句对象.
 *
 * @author fish
 */
public interface SQLStatement {
    
    /**
     * 获取SQL语句类型.
     *
     * @return SQL语句类型
     */
    SQLType getType();
    
    /**
     * 获取表解析对象集合.
     * 
     * @return 表解析对象集合
     */
    Tables getTables();
    
    /**
     * 获取条件对象集合.
     *
     * @return 条件对象集合
     */
    Conditions getConditions();
    
    /**
     * 获取排序集合.
     * 
     * @return 排序集合
     */
    List<OrderBy> getOrderByList();
    
    /**
     * 获取分组集合.
     * 
     * @return 分组集合
     */
    List<GroupBy> getGroupByList();
    
    /**
     * 获取聚合选择项集合.
     * 
     * @return 聚合选择项
     */
    List<AggregationSelectItem> getAggregationSelectItems();
    
    /**
     * 获取分页.
     * 
     * @return 分页
     */
    Limit getLimit();
    
    /**
     * 设置分页.
     *
     * @param limit  分页
     */
    void setLimit(Limit limit);
    
    /**
     * 获取SQL标记集合.
     * 
     * @return SQL标记集合
     */
    List<SQLToken> getSqlTokens();
}
