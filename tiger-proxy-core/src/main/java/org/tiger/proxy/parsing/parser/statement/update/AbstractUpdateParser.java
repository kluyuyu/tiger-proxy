package org.tiger.proxy.parsing.parser.statement.update;

/**
 * Update语句解析器.
 *
 * @author fish
 */
public abstract class AbstractUpdateParser implements SQLStatementParser {
    
    private final com.dangdang.ddframe.rdb.sharding.parsing.parser.SQLParser sqlParser;
    
    private final UpdateStatement updateStatement;
    
    private int parametersIndex;
    
    public AbstractUpdateParser(final com.dangdang.ddframe.rdb.sharding.parsing.parser.SQLParser sqlParser) {
        this.sqlParser = sqlParser;
        updateStatement = new UpdateStatement();
    }
    
    @Override
    public UpdateStatement parse() {
        sqlParser.getLexer().nextToken();
        skipBetweenUpdateAndTable();
        sqlParser.parseSingleTable(updateStatement);
        parseSetItems();
        sqlParser.skipUntil(DefaultKeyword.WHERE);
        sqlParser.setParametersIndex(parametersIndex);
        sqlParser.parseWhere(updateStatement);
        return updateStatement;
    }
    
    protected abstract void skipBetweenUpdateAndTable();
    
    private void parseSetItems() {
        sqlParser.accept(DefaultKeyword.SET);
        do {
            parseSetItem();
        } while (sqlParser.skipIfEqual(Symbol.COMMA));
    }
    
    private void parseSetItem() {
        if (sqlParser.equalAny(Symbol.LEFT_PAREN)) {
            sqlParser.skipParentheses();
        } else {
            int beginPosition = sqlParser.getLexer().getCurrentToken().getEndPosition();
            String literals = sqlParser.getLexer().getCurrentToken().getLiterals();
            sqlParser.getLexer().nextToken();
            if (sqlParser.skipIfEqual(Symbol.DOT)) {
                if (updateStatement.getTables().getSingleTableName().equalsIgnoreCase(SQLUtil.getExactlyValue(literals))) {
                    updateStatement.getSqlTokens().add(new TableToken(beginPosition - literals.length(), literals));
                }
                sqlParser.getLexer().nextToken();
            }
        }
        sqlParser.skipIfEqual(Symbol.EQ, Symbol.COLON_EQ);
        sqlParser.parseExpression(updateStatement);
        parametersIndex = sqlParser.getParametersIndex();
    }
}
