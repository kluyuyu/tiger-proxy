package org.tiger.proxy.model;

/**
 * 数据库节点信息,时间都是毫秒单位
 *
 * 认证的时候不再做字符集的发送校验
 *
 * 统一由前端同步后端
 *
 * Created by fish on 16/7/27.
 */
public class BackendDataSource {

    /**
     * index 很重要,这个决定了分库的数据方向
     */
    private Integer index;

    /**
     * 数据库连接主机地址
     */
    private String host;

    /**
     * 数据库连接端口
     */
    private int port;

    /**
     * 数据库实例名称
     */
    private String schema;

    /**
     * 认证用户名
     */
    private String user;

    /**
     * 认证密码
     */
    private String password;

    /**
     * 最大活动个数
     */
    private int maxActive = 64;

    /**
     * 初始化个数
     */
    private int initIdle = 16;

    /**
     * 连接超时时间
     */
    private int connectTimeout =  10 * 1000;

    /**
     * mysql心跳时间
     */
    private int heartbeatTime =  15 * 60 * 1000;

    /**
     * 对象池检查时间
     */
    private int timeBetweenEvictionRunsMillis = 3 * 60 * 1000;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchemas(String schema) {
        this.schema = schema;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    public int getInitIdle() {
        return initIdle;
    }

    public void setInitIdle(int initIdle) {
        this.initIdle = initIdle;
    }

    public int getHeartbeatTime() {
        return heartbeatTime;
    }

    public void setHeartbeatTime(int heartbeatTime) {
        this.heartbeatTime = heartbeatTime;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public int getTimeBetweenEvictionRunsMillis() {
        return timeBetweenEvictionRunsMillis;
    }

    public void setTimeBetweenEvictionRunsMillis(int timeBetweenEvictionRunsMillis) {
        this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
    }
}
