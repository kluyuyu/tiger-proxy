package org.tiger.proxy.parsing.parser.context.limit;

/**
 * 分页偏移量对象.
 *
 * @author caohao
 */
public class OffsetLimit {
    
    private int offset;
    
    private int offsetParameterIndex;

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getOffsetParameterIndex() {
        return offsetParameterIndex;
    }

    public void setOffsetParameterIndex(int offsetParameterIndex) {
        this.offsetParameterIndex = offsetParameterIndex;
    }
}
