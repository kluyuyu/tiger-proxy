package org.tiger.proxy.parsing.parser.token;

/**
 * 表标记对象.
 *
 * @author fish
 */
public final class TableToken implements SQLToken {
    
    private final int beginPosition;
    
    private final String originalLiterals;
    
    /**
     * 获取表名称.
     * 
     * @return 表名称
     */
    public String getTableName() {
        return SQLUtil.getExactlyValue(originalLiterals);
    }
}
