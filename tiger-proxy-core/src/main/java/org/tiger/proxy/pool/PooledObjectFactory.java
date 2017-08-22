package org.tiger.proxy.pool;

/**
 * Created by fish on 17/1/27.
 */
public interface PooledObjectFactory<T> {


    /**
     * 创建实例
     */
    T createObject();

    /**
     * 验证T
     */
    boolean validateObject(T obj) ;

    /**
     * 空闲检测
     */
    boolean idleObject(T obj);

    /**
     * 销废
     */
    void destroyObject(T obj)  ;
}
