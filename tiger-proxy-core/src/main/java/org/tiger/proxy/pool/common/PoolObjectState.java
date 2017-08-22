package org.tiger.proxy.pool.common;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 连接池对象状态
 *
 * Created by fish on 17/1/27.
 */
public class PoolObjectState {


    /**
     * 上次活跃时间。
     */
    AtomicLong lastActiveAt = new AtomicLong(System.currentTimeMillis());

    /**
     * 上次借出时间。
     */
    AtomicLong lastBorrowAt = new AtomicLong(0);

    /**
     * 上次归还时间。
     */
    AtomicLong lastReturnAt = new AtomicLong(0);

    /**
     * 对象是否有效
     */
    AtomicBoolean valid = new AtomicBoolean(true);


    public AtomicLong getLastActiveAt() {
        return lastActiveAt;
    }

    public void setLastActiveAt(long lastActiveAt) {
        this.lastActiveAt.set(lastActiveAt);
    }

    public boolean isValid() {
        return valid.get();
    }

    public void setValid(Boolean flag) {
        valid.set(flag);
    }

    public AtomicLong getLastReturnAt() {
        return lastReturnAt;
    }

    public void setLastReturnAt(long lastReturnAt) {
        this.lastReturnAt.set(lastReturnAt);
    }

    public AtomicLong getLastBorrowAt() {
        return lastBorrowAt;
    }

    public void setLastBorrowAtAndActiveAt(long lastBorrowAt) {
        this.lastBorrowAt.set(lastBorrowAt);
        this.lastActiveAt.set(lastBorrowAt);
    }
}
