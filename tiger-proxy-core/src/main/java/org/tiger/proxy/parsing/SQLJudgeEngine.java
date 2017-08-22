package org.tiger.proxy.parsing;

import org.tiger.proxy.parsing.lexer.Lexer;
import org.tiger.proxy.parsing.lexer.analyzer.Dictionary;
import org.tiger.proxy.parsing.lexer.token.Assist;
import org.tiger.proxy.parsing.lexer.token.DefaultKeyword;
import org.tiger.proxy.parsing.lexer.token.Keyword;
import org.tiger.proxy.parsing.lexer.token.TokenType;
import org.tiger.proxy.parsing.parser.exception.SQLParsingException;
import org.tiger.proxy.parsing.parser.statement.SQLStatement;
import org.tiger.proxy.parsing.parser.statement.delete.DeleteStatement;
import org.tiger.proxy.parsing.parser.statement.insert.InsertStatement;
import org.tiger.proxy.parsing.parser.statement.select.SelectStatement;
import org.tiger.proxy.parsing.parser.statement.update.UpdateStatement;

/**
 * SQL判断引擎.
 *
 * @author fish
 */
public final class SQLJudgeEngine {
    
    private final String sql;
    
    /**
     * 判断并获取SQL类型.
     *
     * @return SQL语句对象
     */
    public SQLStatement judge() {
        Lexer lexer = new Lexer(sql, new Dictionary());
        lexer.nextToken();
        while (true) {
            TokenType tokenType = lexer.getCurrentToken().getType();
            if (tokenType instanceof Keyword) {
                if (tokenType.equals(DefaultKeyword.SELECT)) {
                    return new SelectStatement();
                } else if (tokenType.equals(DefaultKeyword.UPDATE)) {
                    return new UpdateStatement();
                } else if (tokenType.equals(DefaultKeyword.INSERT)) {
                    return new InsertStatement();
                } else if (tokenType.equals(DefaultKeyword.DELETE)) {
                    return new DeleteStatement();
                }
            }
            if (tokenType instanceof Assist && tokenType.equals(Assist.END)) {
                throw new SQLParsingException("Unsupported SQL statement: [%s]", sql);
            }
            lexer.nextToken();
        }
    }
}
