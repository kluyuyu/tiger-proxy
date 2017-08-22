
package org.tiger.proxy.parsing.parser.context.limit;

/**
 * 分页行数对象.
 *
 * @author caohao
 */
public class RowCountLimit {
    
    private int rowCount;
    
    private int rowCountParameterIndex;

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public int getRowCountParameterIndex() {
        return rowCountParameterIndex;
    }

    public void setRowCountParameterIndex(int rowCountParameterIndex) {
        this.rowCountParameterIndex = rowCountParameterIndex;
    }
}
