package org.tiger.proxy.parsing.parser.token;

import java.util.LinkedList;
import java.util.List;

/**
 * 选择项标记对象.
 *
 * @author fish
 */
public final class ItemsToken implements SQLToken {
    
    private final int beginPosition;
    
    private final List<String> items = new LinkedList<>();
}
