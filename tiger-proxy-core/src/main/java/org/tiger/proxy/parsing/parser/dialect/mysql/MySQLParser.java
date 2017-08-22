package org.tiger.proxy.parsing.parser.dialect.mysql;

import org.tiger.proxy.parsing.lexer.dialect.mysql.MySQLLexer;
import org.tiger.proxy.parsing.parser.SQLParser;
import org.tiger.proxy.parsing.parser.context.limit.Limit;
import org.tiger.proxy.router.ShardingRule;

/**
 * MySQL解析器.
 *
 * @author fish
 */
public final class MySQLParser extends SQLParser {
    
    public MySQLParser(final String sql, final ShardingRule shardingRule) {
        super(new MySQLLexer(sql), shardingRule);
        getLexer().nextToken();
    }
    
    /**
     * 解析分页.
     * 
     * @param sqlStatement SQL语句对象
     * @param parametersIndex 参数索引
     * @return 分页
     */
    public Limit parseLimit(final SQLStatement sqlStatement, final int parametersIndex) {
        skipIfEqual(MySQLKeyword.LIMIT);
        int valueIndex = -1;
        int valueBeginPosition = getLexer().getCurrentToken().getEndPosition();
        int value;
        boolean isParameterForValue = false;
        if (equalAny(Literals.INT)) {
            value = Integer.parseInt(getLexer().getCurrentToken().getLiterals());
            valueBeginPosition = valueBeginPosition - (value + "").length();
        } else if (equalAny(Symbol.QUESTION)) {
            valueIndex = parametersIndex;
            value = -1;
            valueBeginPosition--;
            isParameterForValue = true;
        } else {
            throw new SQLParsingException(getLexer());
        }
        getLexer().nextToken();
        if (skipIfEqual(Symbol.COMMA)) {
            return getLimitWithComma(sqlStatement, parametersIndex, valueIndex, valueBeginPosition, value, isParameterForValue);
        }
        if (skipIfEqual(MySQLKeyword.OFFSET)) {
            return getLimitWithOffset(sqlStatement, parametersIndex, valueIndex, valueBeginPosition, value, isParameterForValue);
        }
        if (!isParameterForValue) {
            sqlStatement.getSqlTokens().add(new RowCountLimitToken(valueBeginPosition, value));
        }
        return new Limit(new RowCountLimit(value, valueIndex));
    }
    
    private Limit getLimitWithComma(
            final SQLStatement sqlStatement, final int parametersIndex, final int valueIndex, final int valueBeginPosition, final int value, final boolean isParameterForValue) {
        int rowCountBeginPosition = getLexer().getCurrentToken().getEndPosition();
        int rowCount;
        int rowCountIndex = -1;
        boolean isParameterForRowCount = false;
        if (equalAny(Literals.INT)) {
            rowCount = Integer.parseInt(getLexer().getCurrentToken().getLiterals());
            rowCountBeginPosition = rowCountBeginPosition - (rowCount + "").length();
        } else if (equalAny(Symbol.QUESTION)) {
            rowCountIndex = -1 == valueIndex ? parametersIndex : valueIndex + 1;
            rowCount = -1;
            rowCountBeginPosition--;
            isParameterForRowCount = true;
        } else {
            throw new SQLParsingException(getLexer());
        }
        getLexer().nextToken();
        if (!isParameterForValue) {
            sqlStatement.getSqlTokens().add(new OffsetLimitToken(valueBeginPosition, value));
        }
        if (!isParameterForRowCount) {
            sqlStatement.getSqlTokens().add(new RowCountLimitToken(rowCountBeginPosition, rowCount));
        }
        return new Limit(new OffsetLimit(value, valueIndex), new RowCountLimit(rowCount, rowCountIndex));
    }
    
    private Limit getLimitWithOffset(
            final SQLStatement sqlStatement, final int parametersIndex, final int valueIndex, final int valueBeginPosition, final int value, final boolean isParameterForValue) {
        int offsetBeginPosition = getLexer().getCurrentToken().getEndPosition();
        int offset;
        int offsetIndex = -1;
        boolean isParameterForOffset = false;
        if (equalAny(Literals.INT)) {
            offset = Integer.parseInt(getLexer().getCurrentToken().getLiterals());
            offsetBeginPosition = offsetBeginPosition - (offset + "").length();
        } else if (equalAny(Symbol.QUESTION)) {
            offsetIndex = -1 == valueIndex ? parametersIndex : valueIndex + 1;
            offset = -1;
            offsetBeginPosition--;
            isParameterForOffset = true;
        } else {
            throw new SQLParsingException(getLexer());
        }
        getLexer().nextToken();
        if (!isParameterForOffset) {
            sqlStatement.getSqlTokens().add(new OffsetLimitToken(offsetBeginPosition, offset));
        }
        if (!isParameterForValue) {
            sqlStatement.getSqlTokens().add(new RowCountLimitToken(valueBeginPosition, value));
        }
        return new Limit(new OffsetLimit(offset, offsetIndex), new RowCountLimit(value, valueIndex));
    }
}
