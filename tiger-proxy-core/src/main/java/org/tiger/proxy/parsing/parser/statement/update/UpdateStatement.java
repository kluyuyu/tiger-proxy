package org.tiger.proxy.parsing.parser.statement.update;

/**
 * Update SQL语句对象.
 *
 * @author fish
 */
public final class UpdateStatement extends AbstractSQLStatement {
    
    public UpdateStatement() {
        super(SQLType.UPDATE);
    }
}
