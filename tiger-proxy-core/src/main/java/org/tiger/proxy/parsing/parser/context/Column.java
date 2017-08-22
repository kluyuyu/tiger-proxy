package org.tiger.proxy.parsing.parser.context;

/**
 * 列对象.
 *
 * @author fish
 */
public final class Column {
    
    private final String name;
    
    private final String tableName;

    public Column(String name, String tableName) {
        this.name = name;
        this.tableName = tableName;
    }

    public String getName() {
        return name;
    }

    public String getTableName() {
        return tableName;
    }
}
