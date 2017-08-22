package org.tiger.proxy.utils;

import com.google.common.base.CharMatcher;
import org.tiger.proxy.constant.SQLType;
import org.tiger.proxy.parsing.lexer.Lexer;
import org.tiger.proxy.parsing.lexer.analyzer.Dictionary;
import org.tiger.proxy.parsing.lexer.dialect.mysql.MySQLKeyword;
import org.tiger.proxy.parsing.lexer.token.Assist;
import org.tiger.proxy.parsing.lexer.token.Keyword;
import org.tiger.proxy.parsing.lexer.token.TokenType;
import org.tiger.proxy.parsing.parser.exception.SQLParsingException;

import static org.tiger.proxy.parsing.lexer.token.Assist.END;

/**
 * Created by fish on 16/7/24.
 */
public class SQLUtil {

    /**
     * 去掉SQL表达式的特殊字符.
     *
     * @param value SQL表达式
     * @return 去掉SQL特殊字符的表达式
     */
    public static String getExactlyValue(final String value) {
        return null == value ? null : CharMatcher.anyOf("[]`'\"").removeFrom(value);
    }

    /**
     * 根据SQL第一个单词判断SQL类型.
     *
     * @param sql SQL语句
     * @return SQL类型
     */
    public static SQLType getTypeByStart(final String sql) {
        Lexer lexer = new Lexer(sql, new Dictionary(MySQLKeyword.values()));
        lexer.nextToken();
        while (true) {
            TokenType tokenType = lexer.getCurrentToken().getType();
            if (tokenType instanceof Keyword) {
                if (tokenType.equals(SQLType.SELECT)) {
                    return SQLType.SELECT;
                } else if (tokenType.equals(SQLType.UPDATE)) {
                    return SQLType.UPDATE;
                } else if (tokenType.equals(SQLType.INSERT)) {
                    return SQLType.INSERT;
                } else if (tokenType.equals(SQLType.DELETE)) {
                    return SQLType.DELETE;
                }
            }
            if (tokenType instanceof Assist) {
                if (tokenType.equals(END)) {
                    throw new SQLParsingException(String.format("Unsupported SQL statement: [%s]", sql));
                }
            }
            lexer.nextToken();
        }
    }

}
