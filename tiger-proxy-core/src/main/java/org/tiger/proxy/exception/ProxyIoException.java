package org.tiger.proxy.exception;

/**
 * Created by fish on 16/10/29.
 */
public class ProxyIoException extends RuntimeException {

    public ProxyIoException(String message){
       super(message);
    }

    public ProxyIoException(Throwable cause){
        super(cause);
    }

    public ProxyIoException(String message,Throwable cause){
        super(message,cause);
    }
}
