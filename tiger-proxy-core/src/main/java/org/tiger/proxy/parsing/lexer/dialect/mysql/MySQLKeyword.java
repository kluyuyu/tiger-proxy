package org.tiger.proxy.parsing.lexer.dialect.mysql;


import org.tiger.proxy.parsing.lexer.token.Keyword;

/**
 * MySQL词法关键词.
 * 
 * @author fish
 */
public enum MySQLKeyword implements Keyword {
    
    SHOW,
    DUAL,
    LIMIT,
    OFFSET,
    VALUE,
    BEGIN,
    IF,
    TRUE,
    FALSE,
    FORCE,
    ROW,
    PARTITION,
    KILL,
    QUICK,
    BINARY,
    CACHE,
    SQL_CACHE,
    SQL_NO_CACHE,
    SQL_SMALL_RESULT,
    SQL_BIG_RESULT,
    SQL_BUFFER_RESULT,
    SQL_CALC_FOUND_ROWS,
    LOW_PRIORITY,
    HIGH_PRIORITY,
    OPTIMIZE,
    ANALYZE,
    IGNORE,
    IDENTIFIED
}
