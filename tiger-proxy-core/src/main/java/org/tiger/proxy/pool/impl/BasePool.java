package org.tiger.proxy.pool.impl;

import org.tiger.proxy.pool.Pool;
import org.tiger.proxy.pool.PoolObject;
import org.tiger.proxy.pool.PooledObjectFactory;
import org.tiger.proxy.pool.common.PoolConfig;
import org.tiger.proxy.pool.common.PoolException;

import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by fish on 17/1/28.
 */
public class BasePool<T> implements Pool<T> {

    /**
     * 配置信息
     */
    PoolConfig config;

    /**
     * 对外工厂模式
     */
    PooledObjectFactory<T> factory;

    /**
     * 空闲存放队列
     */
    private final ArrayBlockingQueue<PoolObject<T>> idleQueue;

    /**
     * 当前总数
     */
    volatile int totalCount;

    /**
     * 是否已经关闭
     */
    volatile boolean isShutdown = false;

    /**
     * 存储
     */
    Map<T,PoolObject<T>> objectMap = new ConcurrentHashMap<>();

    /**
     * 定时器
     */
    Timer timer;

    /**
     * 检查定时器任务是否在运行中
     */
    boolean isTimerTaskRunning = false;

    public BasePool(PoolConfig config,
                    PooledObjectFactory<T> factory){
        //校验配置文件
        config.validatePropCorrelation();
        //初始化队列
        idleQueue = new ArrayBlockingQueue<PoolObject<T>>( config.getMaxActive());

        this.config = config;
        this.factory = factory;
        this.init();
    }

    private void init(){
        //创建初始化个数的对象,并且放入对象池中
        for (int i = 0; i < config.getInitial(); i++) {
            try {
                idleQueue.offer(this.createObject());
            } catch (Exception e) {
                throw new PoolException(e);
            }
        }

        //需要检测空闲状态的配置
        if (config.getTimeIdleMillis() > 0 && config.isTestWhileIdle()) {
            //检查空闲线程的状态
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    try {

                        if(isTimerTaskRunning){
                            return;
                        }

                        isTimerTaskRunning = true;
                        if(isShutdown){
                            timer.cancel();
                        }
                        evictableIdle();
                    }finally {
                        isTimerTaskRunning = false;
                    }
                }
            },config.getTimeIdleMillis());

        }
    }


    @Override
    public T borrowObject() {
        return borrowObject(config.getMaxWaitMillisOnBorrow(), TimeUnit.MILLISECONDS);
    }

    @Override
    public T borrowObject(long timeout, TimeUnit unit) {
        try {
            PoolObject<T> poolObject = idleQueue.poll(timeout, unit);

            if (poolObject == null) {

                //初始化值,小于最大活动值
                if(totalCount < config.getMaxActive()){
                    poolObject = this.createObject();
                    if(poolObject == null){
                        throw new PoolException("Cannot get a free object from the pool");
                    }
                }
            }
            //验证
            if (poolObject != null && config.isTestOnBorrow()) {
                //验证不通过
                if (!factory.validateObject(poolObject.getObject())) {
                    decreaseObject(poolObject);
                    throw new PoolException("TestOnBorrow poolObject validate fail");
                }
            }
            //借出去时间,也是活跃时间
            poolObject.getState().setLastBorrowAtAndActiveAt(System.currentTimeMillis());
            return poolObject.getObject();
        } catch (Exception e) {
            throw new PoolException(e);
        }
    }

    @Override
    public void returnObject(T object) {
        if (object == null) {
            throw new RuntimeException("return poolObject, poolObject is null");
        }
        PoolObject<T> poolObject = objectMap.get(object);
        if (poolObject == null) {
            throw new PoolException("return poolObject, poolObject is null");
        }
        //归还验证
        if (config.isTestOnReturn()) {
            if (!this.validateObject(poolObject)) {
                decreaseObject(poolObject);
                //验证不通过
                throw new PoolException("return poolObject poolObject validate fail");
            }
        }
        //归还时间
        poolObject.getState().setLastReturnAt(System.currentTimeMillis());
        idleQueue.offer(poolObject);
    }

    @Override
    public void shutdown() {
        while (this.idleQueue.size() > 0) {
            PoolObject<T> poolObject = idleQueue.poll();
            if (poolObject != null) {
                decreaseObject(poolObject);
            }
        }
        isShutdown = true;
    }



    /**
     * 检测对象
     */
    private synchronized void evictableIdle() {
        //空闲队列过大,移除过久未被借出的对象。
        if(idleQueue.size() > config.getMinActive()){
            Iterator<PoolObject<T>> iterator =  idleQueue.iterator();
            while (iterator.hasNext()){
                PoolObject<T> poolObject = iterator.next();
                boolean isRemove = false;
                //最大空闲周期
                long timeAliveIdleMillis = System.currentTimeMillis() - poolObject.getState().getLastBorrowAt().get();
                if(timeAliveIdleMillis >= config.getTimeAliveIdleMillis()){
                    //保证不移除过多
                    if(idleQueue.size() > config.getMinActive()){
                        decreaseObject(poolObject);
                        isRemove = idleQueue.remove(poolObject);
                    }
                }
                //心跳验证
                if(!isRemove){
                    long active = System.currentTimeMillis() - poolObject.getState().getLastActiveAt().get();
                    //大于等于心跳检测时间
                    if(active >= config.getTimeIdleMillis()){
                        //心跳发送不通过
                        if(!idleObject(poolObject)){
                            //自检校验失败,
                            decreaseObject(poolObject);
                            idleQueue.remove(poolObject);
                        }
                    }
                }
            }
        }
        //最小活动个数验证,保证最小存活个数。
        int nowSize = objectMap.size();
        if(objectMap.size() < config.getMinActive()){
            int createCount = config.getMinActive() - nowSize;
            //放入对象池中
            for (int i = 0; i < createCount; i++) {
                try {
                    idleQueue.offer(this.createObject());
                } catch (Exception e) {
                    throw new PoolException(e);
                }
            }
        }

    }



    /**
     * 增加
     */
    private PoolObject<T> createObject() {
        T object = factory.createObject();
        PoolObject<T> poolObject = new BasePoolObject<>(object);
        //存储
        objectMap.put(poolObject.getObject(),poolObject);
        totalCount ++;
        return poolObject;
    }

    /**
     * 减少
     */
    private synchronized void decreaseObject(PoolObject<T> poolObject) {
        try {
            totalCount --;
            //设置对象无效
            poolObject.getState().setValid(false);
            factory.destroyObject(poolObject.getObject());
        }catch (Exception ex){
            throw new PoolException(ex);
        }finally {
            //废弃
            objectMap.remove(poolObject.getObject());
        }

    }

    /**
     * 验证
     */
    private boolean validateObject(PoolObject<T> poolObject){
        return factory.validateObject(poolObject.getObject());
    }

    /**
     * 心跳
     */
    private boolean idleObject(PoolObject<T> poolObject){
        boolean result = factory.idleObject(poolObject.getObject());
        if(result){
            //上次活动时间
            poolObject.getState().setLastActiveAt(System.currentTimeMillis());
        }
        return result;
    }

}
