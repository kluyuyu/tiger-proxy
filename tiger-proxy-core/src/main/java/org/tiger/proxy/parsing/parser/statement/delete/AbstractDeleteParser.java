package org.tiger.proxy.parsing.parser.statement.delete;

import org.tiger.proxy.parsing.lexer.token.DefaultKeyword;
import org.tiger.proxy.parsing.parser.SQLParser;
import org.tiger.proxy.parsing.parser.statement.SQLStatementParser;

/**
 * Delete语句解析器.
 *
 * @author fish
 */
public abstract class AbstractDeleteParser implements SQLStatementParser {
    

    private final SQLParser sqlParser;
    
    private final DeleteStatement deleteStatement;
    
    public AbstractDeleteParser(final SQLParser sqlParser) {
        this.sqlParser = sqlParser;
        deleteStatement = new DeleteStatement();
    }
    
    @Override
    public DeleteStatement parse() {
        sqlParser.getLexer().nextToken();
        skipBetweenDeleteAndTable();
        sqlParser.parseSingleTable(deleteStatement);
        sqlParser.skipUntil(DefaultKeyword.WHERE);
        sqlParser.parseWhere(deleteStatement);
        return deleteStatement;
    }
    
    protected abstract void skipBetweenDeleteAndTable();
}
