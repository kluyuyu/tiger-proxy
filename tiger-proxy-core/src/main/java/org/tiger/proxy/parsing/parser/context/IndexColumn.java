package org.tiger.proxy.parsing.parser.context;

import com.google.common.base.Optional;

/**
 * 基于索引的列.
 *
 * @author fish
 */
public interface IndexColumn {
    
    void setColumnIndex(int index);
    
    int getColumnIndex();
    
    Optional<String> getColumnLabel();
    
    Optional<String> getColumnName();
}
