package org.tiger.proxy.pool;

import java.util.concurrent.TimeUnit;


/**
 * Created by liufish on 17/1/27.
 */
public interface Pool<T> {


    T borrowObject();

    T borrowObject(long timeout, TimeUnit unit);

    void returnObject(T object);

    void shutdown();
}
