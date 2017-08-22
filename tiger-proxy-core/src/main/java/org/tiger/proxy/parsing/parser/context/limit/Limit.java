package org.tiger.proxy.parsing.parser.context.limit;



import org.tiger.proxy.parsing.parser.exception.SQLParsingException;

import java.util.List;

/**
 * 分页对象.
 *
 * @author fish
 * @author caohao
 */
public final class Limit {
    
    private OffsetLimit offsetLimit;
    
    private RowCountLimit rowCountLimit;
    
    public Limit(final RowCountLimit rowCount) {
        this.rowCountLimit = rowCount;
    }
    
    public Limit(final OffsetLimit offsetLimit) {
        this.offsetLimit = offsetLimit;
    }
    
    /**
     * 获取分页偏移量.
     * 
     * @return 分页偏移量
     */
    public int getOffset() {
        return null != offsetLimit ? offsetLimit.getOffset() : 0;
    }
    
    /**
     * 获取分页行数.
     *
     * @return 分页行数
     */
    public int getRowCount() {
        return null != rowCountLimit ? rowCountLimit.getRowCount() : 0;
    }
    
    /**
     * 填充改写分页参数.
     *
     * @param parameters 参数
     * @param isRewrite 是否重写参数
     */
    public void processParameters(final List<Object> parameters, final boolean isRewrite) {
        fill(parameters);
        if (isRewrite) {
            rewrite(parameters);
        }
    }
    
    private void fill(final List<Object> parameters) {
        int offset = 0;
        if (null != offsetLimit) {
            offset = -1 == offsetLimit.getOffsetParameterIndex() ? getOffset() : roundHalfUp(parameters.get(offsetLimit.getOffsetParameterIndex()));
            offsetLimit.setOffset(offset);
        }
        int rowCount = 0;
        if (null != rowCountLimit) {
            rowCount = -1 == rowCountLimit.getRowCountParameterIndex() ? getRowCount() : roundHalfUp(parameters.get(rowCountLimit.getRowCountParameterIndex()));
            rowCountLimit.setRowCount(rowCount);
        }
        if (offset < 0 || rowCount < 0) {
            throw new SQLParsingException("LIMIT offset and row count can not be a negative value.");
        }
    }
    
    private void rewrite(final List<Object> parameters) {
        int rewriteOffset = 0;
        int rewriteRowCount = getOffset() + rowCountLimit.getRowCount();
        if (null != offsetLimit && offsetLimit.getOffsetParameterIndex() > -1) {
            parameters.set(offsetLimit.getOffsetParameterIndex(), rewriteOffset);
        }
        if (null != rowCountLimit && rowCountLimit.getRowCountParameterIndex() > -1) {
            parameters.set(rowCountLimit.getRowCountParameterIndex(), rewriteRowCount);
        }
    }
}
