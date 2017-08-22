package org.tiger.proxy.parsing.parser.token;

/**
 * 分页偏移量标记对象.
 *
 * @author fish
 */
public final class OffsetLimitToken implements SQLToken {
    
    private final int beginPosition;
    
    private final int offset;
}
