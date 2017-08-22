package org.tiger.proxy.parsing.parser.context.table;

import com.google.common.base.Optional;

/**
 * 表解析对象.
 * 
 * @author fish
 */
public final class Table {
    
    private final String name;
    
    private final Optional<String> alias;

    public Table(String name, Optional<String> alias) {
        this.name = name;
        this.alias = alias;
    }

    public String getName() {
        return name;
    }

    public Optional<String> getAlias() {
        return alias;
    }
}
