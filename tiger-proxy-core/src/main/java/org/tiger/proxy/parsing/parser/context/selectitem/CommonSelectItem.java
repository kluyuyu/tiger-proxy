package org.tiger.proxy.parsing.parser.context.selectitem;

import com.google.common.base.Optional;


/**
 * 选择项.
 *
 * @author fish
 */
public final class CommonSelectItem implements SelectItem {
    
    private final String expression;
    
    private final Optional<String> alias;
    
    private final boolean star;

    public CommonSelectItem(String expression, Optional<String> alias, boolean star) {
        this.expression = expression;
        this.alias = alias;
        this.star = star;
    }

    @Override
    public String getExpression() {
        return expression;
    }

    @Override
    public Optional<String> getAlias() {
        return alias;
    }

    public boolean isStar() {
        return star;
    }
}
