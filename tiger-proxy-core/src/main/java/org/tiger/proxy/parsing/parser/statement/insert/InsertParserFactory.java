package org.tiger.proxy.parsing.parser.statement.insert;

/**
 * Insert语句解析器工厂.
 *
 * @author fish
 */
public class InsertParserFactory {
    
    /**
     * 创建Insert语句解析器.
     * 
     * @param shardingRule 分库分表规则配置
     * @param sqlParser SQL解析器
     * @return Insert语句解析器
     */
    public static AbstractInsertParser newInstance(final ShardingRule shardingRule, final SQLParser sqlParser) {
        if (sqlParser instanceof MySQLParser) {
            return new MySQLInsertParser(shardingRule, sqlParser);
        }
        if (sqlParser instanceof OracleParser) {
            return new OracleInsertParser(shardingRule, sqlParser);
        }
        if (sqlParser instanceof SQLServerParser) {
            return new SQLServerInsertParser(shardingRule, sqlParser);
        }
        if (sqlParser instanceof PostgreSQLParser) {
            return new PostgreSQLInsertParser(shardingRule, sqlParser);
        }
        throw new UnsupportedOperationException(String.format("Cannot support sqlParser class [%s].", sqlParser.getClass()));
    } 
}
