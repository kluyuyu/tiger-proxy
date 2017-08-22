package org.tiger.proxy.parsing.parser.token;

/**
 * 分页长度标记对象.
 *
 * @author fish
 */
public final class RowCountLimitToken implements SQLToken {
    
    private final int beginPosition;
    
    private final int rowCount;
}
