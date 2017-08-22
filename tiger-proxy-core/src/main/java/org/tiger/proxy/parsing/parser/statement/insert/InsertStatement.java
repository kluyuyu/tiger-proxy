package org.tiger.proxy.parsing.parser.statement.insert;


import com.google.common.base.Optional;


import java.util.Collection;
import java.util.LinkedList;

/**
 * Insert SQL语句对象.
 *
 * @author fish
 */
public final class InsertStatement extends AbstractSQLStatement {
    
    private final Collection<Column> columns = new LinkedList<>();
    
    private GeneratedKey generatedKey;
    
    private int columnsListLastPosition;
    
    private int valuesListLastPosition;
    
    public InsertStatement() {
        super(SQLType.INSERT);
    }
    
    /**
     * 追加自增主键标记对象.
     *
     * @param shardingRule 分片规则
     * @param parametersSize 参数个数
     */
    public void appendGenerateKeyToken(final ShardingRule shardingRule, final int parametersSize) {
        if (null != generatedKey) {
            return;
        }
        Optional<TableRule> tableRule = shardingRule.tryFindTableRule(getTables().getSingleTableName());
        if (!tableRule.isPresent()) {
            return;
        }
        Optional<GeneratedKeyToken> generatedKeysToken = findGeneratedKeyToken();
        if (!generatedKeysToken.isPresent()) {
            return;
        }
        ItemsToken valuesToken = new ItemsToken(generatedKeysToken.get().getBeginPosition());
        if (0 == parametersSize) {
            appendGenerateKeyToken(shardingRule, tableRule.get(), valuesToken);
        } else {
            appendGenerateKeyToken(shardingRule, tableRule.get(), valuesToken, parametersSize);
        }
        getSqlTokens().remove(generatedKeysToken.get());
        getSqlTokens().add(valuesToken);
    }
    
    private void appendGenerateKeyToken(final ShardingRule shardingRule, final TableRule tableRule, final ItemsToken valuesToken) {
        Number generatedKey = shardingRule.generateKey(tableRule.getLogicTable());
        valuesToken.getItems().add(generatedKey.toString());
        getConditions().add(new Condition(new Column(tableRule.getGenerateKeyColumn(), tableRule.getLogicTable()), new SQLNumberExpression(generatedKey)), shardingRule);
        this.generatedKey = new GeneratedKey(tableRule.getLogicTable(), -1, generatedKey);
    }
    
    private void appendGenerateKeyToken(final ShardingRule shardingRule, final TableRule tableRule, final ItemsToken valuesToken, final int parametersSize) {
        valuesToken.getItems().add("?");
        getConditions().add(new Condition(new Column(tableRule.getGenerateKeyColumn(), tableRule.getLogicTable()), new SQLPlaceholderExpression(parametersSize)), shardingRule);
        generatedKey = new GeneratedKey(tableRule.getGenerateKeyColumn(), parametersSize, null);
    }
    
    private Optional<GeneratedKeyToken> findGeneratedKeyToken() {
        for (SQLToken each : getSqlTokens()) {
            if (each instanceof GeneratedKeyToken) {
                return Optional.of((GeneratedKeyToken) each);
            }
        }
        return Optional.absent();
    }
}
