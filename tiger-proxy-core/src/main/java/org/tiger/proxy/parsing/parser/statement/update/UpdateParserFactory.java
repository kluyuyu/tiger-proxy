package org.tiger.proxy.parsing.parser.statement.update;

/**
 * Update语句解析器工厂.
 *
 * @author fish
 */
public class UpdateParserFactory {
    
    /**
     * 创建Update语句解析器.
     * 
     * @param sqlParser SQL解析器
     * @return Update语句解析器
     */
    public static AbstractUpdateParser newInstance(final SQLParser sqlParser) {
        if (sqlParser instanceof MySQLParser) {
            return new MySQLUpdateParser(sqlParser);
        }
        if (sqlParser instanceof OracleParser) {
            return new OracleUpdateParser(sqlParser);
        }
        if (sqlParser instanceof SQLServerParser) {
            return new SQLServerUpdateParser(sqlParser);
        }
        if (sqlParser instanceof PostgreSQLParser) {
            return new PostgreSQLUpdateParser(sqlParser);
        }
        throw new UnsupportedOperationException(String.format("Cannot support sqlParser class [%s].", sqlParser.getClass()));
    } 
}
