package org.tiger.proxy.parsing.parser.statement;

/**
 * SQL语句解析器.
 *
 * @author fish
 */
public interface SQLStatementParser {
    
    /**
     * 解析SQL语句.
     *
     * @return 解析结果
     */
    SQLStatement parse();
}
