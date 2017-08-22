package org.tiger.proxy.pool.impl;

import org.tiger.proxy.pool.PoolObject;
import org.tiger.proxy.pool.common.PoolObjectState;

/**
 * 对象T 封装
 *
 * @author liufish
 */
public class BasePoolObject<T> implements PoolObject<T> {

    final T object;

    final PoolObjectState state;

    public BasePoolObject(T object) {
        super();
        this.object = object;
        this.state = new PoolObjectState();
    }


    @Override
    public T getObject() {
        return object;
    }


    @Override
    public PoolObjectState getState() {
        return state;
    }

}
