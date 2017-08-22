package org.tiger.proxy.constant;

/**
 * Created by liufish on 16/7/16.
 */
public interface VersionsType {

    /** 协议版本 */
   byte PROTOCOL_VERSION = 10;

    /** 服务器版本 */
   byte[] SERVER_VERSION = "proxy-1.0.0".getBytes();

}
