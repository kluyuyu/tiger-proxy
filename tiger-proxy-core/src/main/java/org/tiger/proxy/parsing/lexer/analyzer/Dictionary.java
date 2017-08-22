package org.tiger.proxy.parsing.lexer.analyzer;


import org.tiger.proxy.parsing.lexer.token.DefaultKeyword;
import org.tiger.proxy.parsing.lexer.token.Keyword;
import org.tiger.proxy.parsing.lexer.token.TokenType;

import java.util.HashMap;
import java.util.Map;

/**
 * 词法标记字典.
 *
 * @author fish
 */
public final class Dictionary {
    
    private final Map<String, Keyword> tokens = new HashMap<>(1024);
    
    public Dictionary(final Keyword... dialectKeywords) {
        fill(dialectKeywords);
    }
    
    private void fill(final Keyword... dialectKeywords) {
        for (DefaultKeyword each : DefaultKeyword.values()) {
            tokens.put(each.name(), each);
        }
        for (Keyword each : dialectKeywords) {
            tokens.put(each.toString(), each);
        }
    }
    
    TokenType findTokenType(final String literals, final TokenType defaultTokenType) {
        String key = null == literals ? null : literals.toUpperCase();
        return tokens.containsKey(key) ? tokens.get(key) : defaultTokenType;
    }
    
    TokenType findTokenType(final String literals) {
        String key = null == literals ? null : literals.toUpperCase();
        if (tokens.containsKey(key)) {
            return tokens.get(key);
        }
        throw new IllegalArgumentException();
    }
}
