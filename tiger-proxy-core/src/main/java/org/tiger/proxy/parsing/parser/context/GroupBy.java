package org.tiger.proxy.parsing.parser.context;

import com.google.common.base.Optional;
import org.tiger.proxy.constant.OrderType;

/**
 * 分组对象.
 *
 * @author fish
 */
public final class GroupBy implements IndexColumn {
    
    private final Optional<String> owner;
    
    private final String name;
    
    private final OrderType orderByType;

    private Optional<String> alias;

    private int columnIndex;
    
    public GroupBy(final Optional<String> owner, final String name, final OrderType orderByType, final Optional<String> alias) {
        this.owner = owner;
        this.name = name;
        this.orderByType = orderByType;
        this.alias = alias;
    }
    
    @Override
    public Optional<String> getColumnLabel() {
        return alias;
    }
    
    @Override
    public Optional<String> getColumnName() {
        return Optional.of(name);
    }
    
    /**
     * 获取列全名.
     * 
     * @return 列全名
     */
    public Optional<String> getQualifiedName() {
        if (null == name) {
            return Optional.absent();
        }
        return owner.isPresent() ? Optional.of(owner.get() + "." + name) : Optional.of(name);
    }

    public Optional<String> getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public OrderType getOrderByType() {
        return orderByType;
    }

    public Optional<String> getAlias() {
        return alias;
    }

    @Override
    public int getColumnIndex() {
        return columnIndex;
    }


    @Override
    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    public void setAlias(Optional<String> alias) {
        this.alias = alias;
    }
}
