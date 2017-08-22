package org.tiger.proxy.parsing.parser.context;

import com.google.common.base.Optional;
import org.tiger.proxy.constant.OrderType;

/**
 * 排序对象.
 *
 * @author fish
 */
public final class OrderBy implements IndexColumn {
    
    private final Optional<String> owner;
    
    private final Optional<String> name;
    
    private final Optional<Integer> index;
    
    private final OrderType orderByType;

    private Optional<String> alias;
    
    private int columnIndex;
    
    public OrderBy(final String name, final OrderType orderByType, final Optional<String> alias) {
        this.owner = Optional.absent();
        this.name = Optional.of(name);
        index = Optional.absent();
        this.orderByType = orderByType;
        this.alias = alias;
    }
    
    public OrderBy(final String owner, final String name, final OrderType orderByType, final Optional<String> alias) {
        this.owner = Optional.of(owner);
        this.name = Optional.of(name);
        index = Optional.absent();
        this.orderByType = orderByType;
        this.alias = alias;
    }
    
    public OrderBy(final int index, final OrderType orderByType) {
        owner = Optional.absent();
        name = Optional.absent();
        this.index = Optional.of(index);
        this.orderByType = orderByType;
        alias = Optional.absent();
        columnIndex = index;
    }
    
    @Override
    public Optional<String> getColumnLabel() {
        return alias;
    }
    
    @Override
    public Optional<String> getColumnName() {
        return name;
    }
    
    @Override
    public void setColumnIndex(final int index) {
        if (this.index.isPresent()) {
            return;
        }
        columnIndex = index;
    }
    
    /**
     * 获取列全名.
     *
     * @return 列全名
     */
    public Optional<String> getQualifiedName() {
        if (!name.isPresent()) {
            return Optional.absent();
        }
        return owner.isPresent() ? Optional.of(owner.get() + "." + name.get()) : name;
    }

    public void setAlias(Optional<String> alias) {
        this.alias = alias;
    }

    public Optional<String> getOwner() {
        return owner;
    }

    public Optional<String> getName() {
        return name;
    }

    public Optional<Integer> getIndex() {
        return index;
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
}
