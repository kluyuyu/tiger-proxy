package org.tiger.proxy.parsing.parser.context;

/**
 * 自动生成键.
 * 
 * @author fish
 */
public final class GeneratedKey {
    
    private final String column;
    
    private final int index;
    
    private final Number value;


    public GeneratedKey(String column, int index, Number value) {
        this.column = column;
        this.index = index;
        this.value = value;
    }

    public String getColumn() {
        return column;
    }

    public int getIndex() {
        return index;
    }

    public Number getValue() {
        return value;
    }
}
