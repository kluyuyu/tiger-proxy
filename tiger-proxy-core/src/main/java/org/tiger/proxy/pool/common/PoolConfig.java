package org.tiger.proxy.pool.common;

/**
 * 连接池配置对象
 * <p>
 * Created by fish on 17/1/27.
 */
public class PoolConfig {


    /**
     * 最大活跃值
     */
    int maxActive = 128;

    /**
     * 初始化值
     */
    int initial = 16;

    /**
     * 最小活跃值
     */
    int minActive = 32;

    /**
     * 最大等待时间,单位为毫秒
     */
    long maxWaitMillisOnBorrow = 500;

    /**
     * 取出验证
     */
    boolean testOnBorrow = true;

    /**
     * 连接超时,3秒,单位毫秒.
     */
    int connectionTimeout = 3000;

    /**
     * 回收时验证
     */
    boolean testOnReturn = false;

    /**
     * 空闲检验是验证
     */
    boolean testWhileIdle = true;

    /**
     * 空闲检测时间,即心跳时间。默认3分钟
     */
    long timeIdleMillis = 3 *  60 * 1000;

    /**
     * 在队列里面的最大的空闲周期
     * 如果空闲队列里面的值大于最小活动数,就移除至最小活动数,移除的条件为该链接存活timeAliveIdleMillis时间,默认3分钟。
     */
    long timeAliveIdleMillis = 3 * 60 * 1000;

    public void validatePropCorrelation() {

        if (maxActive < initial){
            throw new PoolException( "maxActive < initial");
        }

        if (maxActive < minActive){
            throw new PoolException("maxActive < minActive");
        }

        if (initial < minActive){
            throw new PoolException("initial < minActive");
        }
        if (timeAliveIdleMillis < timeIdleMillis){
            throw new PoolException("timeAliveIdleMillis < timeIdleMillis");
        }

    }

    public long getTimeAliveIdleMillis() {
        return timeAliveIdleMillis;
    }

    public void setTimeAliveIdleMillis(long timeAliveIdleMillis) {
        this.timeAliveIdleMillis = timeAliveIdleMillis;
    }

    public int getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    public int getInitial() {
        return initial;
    }

    public void setInitial(int initial) {
        this.initial = initial;
    }

    public long getMaxWaitMillisOnBorrow() {
        return maxWaitMillisOnBorrow;
    }

    public void setMaxWaitMillisOnBorrow(long maxWaitMillisOnBorrow) {
        this.maxWaitMillisOnBorrow = maxWaitMillisOnBorrow;
    }

    public boolean isTestOnBorrow() {
        return testOnBorrow;
    }

    public void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    public boolean isTestOnReturn() {
        return testOnReturn;
    }

    public void setTestOnReturn(boolean testOnReturn) {
        this.testOnReturn = testOnReturn;
    }

    public boolean isTestWhileIdle() {
        return testWhileIdle;
    }

    public void setTestWhileIdle(boolean testWhileIdle) {
        this.testWhileIdle = testWhileIdle;
    }

    public int getMinActive() {
        return minActive;
    }

    public void setMinActive(int minActive) {
        this.minActive = minActive;
    }

    public long getTimeIdleMillis() {
        return timeIdleMillis;
    }

    public void setTimeIdleMillis(long timeIdleMillis) {
        this.timeIdleMillis = timeIdleMillis;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }


}
