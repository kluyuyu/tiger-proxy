package org.tiger.proxy.pool;

import org.tiger.proxy.pool.common.PoolObjectState;

/**
 * Created by fish on 17/1/27.
 */
public interface PoolObject<T> {

    T getObject();

    PoolObjectState getState();
}
