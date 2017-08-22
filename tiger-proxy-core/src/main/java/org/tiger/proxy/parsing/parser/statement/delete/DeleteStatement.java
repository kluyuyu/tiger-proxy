package org.tiger.proxy.parsing.parser.statement.delete;

import org.tiger.proxy.constant.SQLType;
import org.tiger.proxy.parsing.parser.statement.AbstractSQLStatement;

/**
 * Delete SQL语句对象.
 *
 * @author fish
 */
public final class DeleteStatement extends AbstractSQLStatement {
    
    public DeleteStatement() {
        super(SQLType.DELETE);
    }
}
