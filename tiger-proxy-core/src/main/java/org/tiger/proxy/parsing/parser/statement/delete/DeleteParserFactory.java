package org.tiger.proxy.parsing.parser.statement.delete;

import org.tiger.proxy.parsing.parser.SQLParser;
import org.tiger.proxy.parsing.parser.dialect.mysql.MySQLDeleteParser;
import org.tiger.proxy.parsing.parser.dialect.mysql.MySQLParser;

/**
 * Delete语句解析器工厂.
 *
 * @author fish
 */
public class DeleteParserFactory {
    
    /**
     * 创建Delete语句解析器.
     * 
     * @param sqlParser SQL解析器
     * @return Delete语句解析器
     */
    public static AbstractDeleteParser newInstance(final SQLParser sqlParser) {
        if (sqlParser instanceof MySQLParser) {
            return new MySQLDeleteParser(sqlParser);
        }

        throw new UnsupportedOperationException(String.format("Cannot support sqlParser class [%s].", sqlParser.getClass()));
    } 
}
