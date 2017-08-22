package org.tiger.proxy.pool.common;

/**
 * Created by liufish on 17/1/27.
 */
public class PoolException extends RuntimeException {

    public PoolException(Throwable cause){
        super(cause);
    }

    public PoolException(String message){
        super(message);
    }

    public PoolException(String message, Throwable cause){
        super(message,cause);
    }
}
