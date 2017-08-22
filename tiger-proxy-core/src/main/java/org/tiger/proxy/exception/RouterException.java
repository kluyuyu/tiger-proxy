package org.tiger.proxy.exception;

/**
 * Created by fish on 16/8/11.
 */
public class RouterException extends RuntimeException{

    public RouterException(String message){
        super(message);
    }

    public RouterException(Throwable cause){
        super(cause);
    }

    public RouterException(String message,Throwable cause){
        super(message,cause);
    }

}
