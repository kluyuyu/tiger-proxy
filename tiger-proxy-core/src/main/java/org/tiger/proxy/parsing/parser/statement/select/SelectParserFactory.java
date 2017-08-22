package org.tiger.proxy.parsing.parser.statement.select;

/**
 * Select语句解析器工厂.
 *
 * @author fish
 */
public class SelectParserFactory {
    
    /**
     * 创建Select语句解析器.
     * 
     * @param sqlParser SQL解析器
     * @return Select语句解析器
     */
    public static AbstractSelectParser newInstance(final SQLParser sqlParser) {
        if (sqlParser instanceof MySQLParser) {
            return new MySQLSelectParser(sqlParser);
        }
        if (sqlParser instanceof OracleParser) {
            return new OracleSelectParser(sqlParser);
        }
        if (sqlParser instanceof SQLServerParser) {
            return new SQLServerSelectParser(sqlParser);
        }
        if (sqlParser instanceof PostgreSQLParser) {
            return new PostgreSQLSelectParser(sqlParser);
        }
        throw new UnsupportedOperationException(String.format("Cannot support sqlParser class [%s].", sqlParser.getClass()));
    } 
}
