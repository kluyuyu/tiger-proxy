package org.tiger.proxy.parsing.parser.context.selectitem;

import com.google.common.base.Optional;

/**
 * 选择项接口.
 *
 * @author fish
 */
public interface SelectItem {
    
    /**
     * 获取表达式.
     * 
     * @return 表达式
     */
    String getExpression();
    
    /**
     * 获取别名.
     * 
     * @return 别名
     */
    Optional<String> getAlias();
}
